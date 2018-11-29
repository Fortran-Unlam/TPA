package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import config.Param;
import config.Posicion;
import core.Jugador;
import core.mapa.Juego;
import looby.Partida;
import looby.Sala;
import looby.TipoJuego;
import looby.TipoJuegoFruta;
import looby.TipoJuegoTiempo;
import looby.Usuario;
import looby.UsuarioBot;
import looby.UsuarioDAO;

public class ConexionCliente extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;
	private Usuario usuario;
	private Sala sala;

	/**
	 * Es el constructor de la clase ConexionCliente, recibe un socket
	 * 
	 * @param socket    Un socket ya creado por una conexion recibida por
	 *                  ServerSocket.accept();
	 * @param socketOut
	 */
	public ConexionCliente(Socket socket, Socket socketOut) {
		this.socket = socket;

		try {
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			this.salidaDatos = new ObjectOutputStream(socketOut.getOutputStream());

		} catch (IOException ex) {
			Servidor.LOGGER.error("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;
		Properties properties;

		while (conectado) {
			try {
				Message message = (Message) new Gson().fromJson((String) this.entradaDatos.readObject(), Message.class);

				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					properties = new Gson().fromJson((String) message.getData(), Properties.class);

					usuario = UsuarioDAO.loguear(properties.getProperty("username"),
							properties.getProperty("hashPassword"));

					if (usuario == null) {
						this.salidaDatos.flush();
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_INCORRECTO, null).toJson());
					} else {
						boolean usuarioDuplicado = false;
						for (Usuario usuarioActivo : Servidor.getUsuariosActivos()) {

							if (usuarioActivo.getId() == usuario.getId()) {
								this.salidaDatos.flush();
								this.salidaDatos
										.writeObject(new Message(Param.REQUEST_LOGUEO_DUPLICADO, null).toJson());
								usuarioDuplicado = true;
								break;
							}

						}
						if (!usuarioDuplicado) {
							Servidor.agregarAUsuariosActivos(usuario);
							this.salidaDatos.flush();
							this.salidaDatos.writeObject(
									new Message(Param.REQUEST_LOGUEO_CORRECTO, new Gson().toJson(usuario)).toJson());
						}
					}
					break;

				case Param.REQUEST_REGISTRAR_USUARIO:
					properties = new Gson().fromJson((String) message.getData(), Properties.class);

					int resultado = UsuarioDAO.registrar(properties.getProperty("username"),
							properties.getProperty("hashPassword"));

					switch (resultado) {
					case -1:
						// System.err.println("registro incorrecto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_INCORRECTO, null).toJson());
						break;
					case 0:
						// System.err.println("registro correcto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_CORRECTO, null).toJson());
						break;
					case 1:
						// System.err.println("registro duplicado");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_DUPLICADO, null).toJson());
						break;
					}

					break;

				case Param.REQUEST_GET_ALL_SALAS:
					// System.err.println("Obtener todas las Salas");
					this.salidaDatos
							.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()).toJson());
					break;

				case Param.REQUEST_CREAR_SALA:
					@SuppressWarnings("unchecked")
					ArrayList<String> dataSala = (ArrayList<String>) message.getData();

					if (!Servidor.existeSala(dataSala.get(0))) {
						sala = usuario.crearSala(dataSala.get(0), Integer.valueOf(dataSala.get(1)));

						Servidor.agregarASalasActivas(sala);
						// System.err.println("Sala creada");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, true).toJson());

						// Envio a los clientes que estaban en "unir sala" la actualizacion de la
						// nueva
						// sala. Esto deberia mandarse por el canal de syncro pero por ahora va.
						String datosSalaNueva;

						datosSalaNueva = sala.getNombre() + Param.SEPARADOR_EN_MENSAJES
								+ sala.getCantidadUsuarioActuales() + Param.SEPARADOR_EN_MENSAJES
								+ sala.getCantidadUsuarioMaximos();

						this.salidaDatos
								.writeObject(new Message(Param.REQUEST_ACTUALIZAR_SALAS, datosSalaNueva).toJson());
					} else {
						this.salidaDatos.writeObject(new Message(Param.REQUEST_ERROR_CREAR_SALA, false).toJson());
					}

					break;
				case Param.REQUEST_SALIR_SALA:
					usuario.salirDeSala();
					sala = Servidor.getSalaPorNombre((String) message.getData());
					sala.sacarUsuarioDeSala(usuario);
					
					if (sala.getCantidadUsuarioActuales() == 0 || sala.esElAdmin(usuario)) {
						Servidor.removerDeSalasActivas(sala);
						Servidor.avisarALosClientesQueLaSalaTermino(sala);
					}
					break;
				case Param.REQUEST_INGRESO_SALA:
					sala = Servidor.getSalaPorNombre((String) message.getData());

					/*
					 * El servidor me devuelve los datos de la sala, para que la vista me represente
					 * los datos de la sala que me importan como usuario
					 */
//						int cantidadUsuariosActuales = sala.getCantidadUsuarioActuales();
//						int cantidadUsuarioMaximos = sala.getCantidadUsuarioMaximos();
//						String usuariosActivos = sala.getUsuariosSeparadosporComa();
//						this.salidaDatos.writeObject(new Message(Param.DATOS_SALA,
//								cantidadUsuariosActuales + ";" + cantidadUsuarioMaximos + ";" + usuariosActivos)
//										.toJson());
					this.salidaDatos.writeObject(
							new Message(Param.NOTICE_INGRESAR_SALA, sala.agregarUsuarioASala(usuario)).toJson());
					break;
				case Param.REQUEST_SALIR_JUEGO:
					this.usuario.inJuego = false;
					Jugador jugador = this.usuario.getJugador();
					Partida partidaActual = this.sala.getPartidaActual();
					Juego juego = partidaActual.getJuegoEnCurso();

					/// 25/11 Reflejo remueve pero parece al cliente seguir enviandole la info ver
					/// crearSala en ConexionServidor.
					// Lo saco de los jugadores en el juego actual.
					if (juego.getJugadoresEnJuego().remove(jugador)) {
						partidaActual.getJugadoresEnPartida().remove(jugador);
						jugador.getVibora().matar();
					}
					break;
				case Param.REQUEST_EMPEZAR_JUEGO:
					properties = new Gson().fromJson((String) message.getData(), Properties.class);

					int cantidadBots = Integer.valueOf(properties.getProperty(Param.CANTIDAD_DE_BOTS));
					boolean tipoJuegoFruta = Boolean.valueOf(properties.getProperty(Param.TIPO_JUEGO_FRUTA));
					int cantidadDeFrutas = Integer.valueOf(properties.getProperty(Param.CANTIDAD_DE_FRUTAS));
					boolean tipoJuegoTiempo = Boolean.valueOf(properties.getProperty(Param.TIPO_JUEGO_TIEMPO));
					int cantidadDeTiempo = Integer.valueOf(properties.getProperty(Param.CANTIDAD_DE_TIEMPO));
					int cantidadTotalRondas = Integer.valueOf(properties.getProperty(Param.CANTIDAD_RONDAS));
					String[] mapaDeJuego = String.valueOf(properties.get(Param.MAPA_DE_JUEGO)).split(" ");
					int numeroDeMapaDeJuego = Integer.parseInt(mapaDeJuego[1]);
					for (int i = 0; i < cantidadBots; i++) {
						sala.agregarUsuarioASala(new UsuarioBot());
					}

					TipoJuego tipoJuego = new TipoJuego();

					if (tipoJuegoFruta) {
						tipoJuego = new TipoJuegoFruta(tipoJuego);
						tipoJuego.setFrutasMaximas(cantidadDeFrutas);
					}

					if (tipoJuegoTiempo) {
						tipoJuego = new TipoJuegoTiempo(tipoJuego);
						tipoJuego.setSegundos(cantidadDeTiempo);
					}

					this.salidaDatos.writeObject(new Message(Param.REQUEST_JUEGO_EMPEZADO,
							sala.crearPartida(cantidadBots, tipoJuego, numeroDeMapaDeJuego, cantidadTotalRondas))
									.toJson());
					break;
				case Param.REQUEST_ENVIAR_TECLA:

					Posicion posicion = Posicion.values()[((Double) message.getData()).intValue()];
					if (posicion != null) {
						this.usuario.getJugador().setTecla(posicion);
					}
					break;
				case Param.REQUEST_CERRAR_SESION:
					usuario = new Gson().fromJson((String) message.getData(), Usuario.class);

					for (Usuario usuarioEnServer : Servidor.getUsuariosActivos()) {
						if (usuarioEnServer.getId() == usuario.getId()) {
							Servidor.removerUsuarioActivo(usuarioEnServer);
							break;
						}
					}

					this.salidaDatos.flush();
					this.salidaDatos.writeObject(new Message(Param.REQUEST_CERRAR_SESION_OK, null).toJson());
					conectado = false;
					break;
				case Param.REQUEST_MOSTRAR_GANADOR:
					Jugador jugadorGanador = sala.getPartidaActual().calcularGanadorPartida();

					this.salidaDatos.flush();
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GANADOR_ENVIADO, jugadorGanador.getNombre()
							+ ";" + jugadorGanador.getFrutasComidas() + ";" + jugadorGanador.getPuntosEnPartida())
									.toJson());
					break;
				default:
					break;
				}

			} catch (IOException ex) {
				Servidor.LOGGER.error(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					Servidor.LOGGER.error("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
				}
			} catch (JsonSyntaxException e) {
				Servidor.LOGGER.error("Error de sintaxis en el json " + e.getMessage());
			} catch (ClassNotFoundException e) {
				Servidor.LOGGER.error("No se encuentra una clase " + e.getMessage());
			}
		}
		Servidor.desconectar(this);
	}

	public ObjectOutputStream getSalidaDatos() {
		return this.salidaDatos;
	}

	/**
	 * Return el usuario de la conexion pero ojo porque no es el mismo que el del
	 * lado del cliente
	 * 
	 * @return
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}
}
