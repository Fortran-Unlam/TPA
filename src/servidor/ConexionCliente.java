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
import core.mapa.Mapa;
import core.mapa.MapaUno;
import looby.Sala;
import looby.TipoJuego;
import looby.TipoJuegoFruta;
import looby.TipoJuegoSupervivencia;
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
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;
		Properties properties;

		while (conectado) {
			try {
//				System.out.println("try");
				Message message = (Message) new Gson().fromJson((String) this.entradaDatos.readObject(), Message.class);
//				System.out.println("El cliente solicita " + message.getType());

				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					properties = new Gson().fromJson((String) message.getData(), Properties.class);

					usuario = UsuarioDAO.loguear(properties.getProperty("username"),
							properties.getProperty("hashPassword"));

					if (usuario == null) {
						System.out.println("Usuario y/o contrasenia incorrectos");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_INCORRECTO, null).toJson());
					} else {
						boolean usuarioInexistente = true;
						for (Usuario usuarioActivo : Servidor.getUsuariosActivos()) {

							if (usuarioActivo.getId() == usuario.getId()) {
								System.out.println("Usuario ya logeado");
								this.salidaDatos
										.writeObject(new Message(Param.REQUEST_LOGUEO_DUPLICADO, null).toJson());
								usuarioInexistente = false;
								break;
							}

						}
						if (usuarioInexistente) {
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
						System.err.println("registro incorrecto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_INCORRECTO, null).toJson());
						break;
					case 0:
						System.err.println("registro correcto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_CORRECTO, null).toJson());
						break;
					case 1:
						System.err.println("registro duplicado");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_DUPLICADO, null).toJson());
						break;
					}

					break;

				case Param.REQUEST_GET_ALL_SALAS:
					System.err.println("all salas");
					this.salidaDatos
							.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()).toJson());
					break;

				case Param.REQUEST_CREAR_SALA:
					@SuppressWarnings("unchecked")
					ArrayList<String> dataSala = (ArrayList<String>) message.getData();

					if (!Servidor.existeSala(dataSala.get(0))) {
						sala = usuario.crearSala(dataSala.get(0), Integer.valueOf(dataSala.get(1)));

						Servidor.agregarASalasActivas(sala);
						System.err.println("sala cdreada");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, true).toJson());

						// Envio a los clientes que estaban en "unir sala" la actualizaci�n de la
						// nueva
						// sala. Esto deber�a mandarse por el canal de syncro pero por ahora va.
						String datosSalaNueva;

						datosSalaNueva = sala.getNombre() + Param.SEPARADOR_EN_MENSAJES
								+ sala.getCantidadUsuarioActuales() + Param.SEPARADOR_EN_MENSAJES
								+ sala.getCantidadUsuarioMaximos();

						System.err.println("actualizar salas");
						this.salidaDatos
								.writeObject(new Message(Param.REQUEST_ACTUALIZAR_SALAS, datosSalaNueva).toJson());
					} else {
						System.err.println("crear sala");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_ERROR_CREAR_SALA, false).toJson());
					}

					break;
				case Param.REQUEST_SALIR_SALA:
					usuario.salirDeSala();
					String nombreSala = (String) message.getData();
					sala = Servidor.getSalaPorNombre(nombreSala);
					// Es similar al usuario.SalirSala() ? Estoy duplicando la accion.
					sala.sacarUsuarioDeSala(usuario);
					// Debug para comprobar verdaderamente la cantidad de usuarios con los que quedo
					// la sala.
					// System.out.println("ASD:"+s.getCantidadUsuarioActuales());
					// Si tras la salida del usuario, la sala se quedo con 0 usuarios entonces debe
					// eliminarse de las salas activas.

					if (sala.getCantidadUsuarioActuales() == 0)
						Servidor.removerDeSalasActivas(sala);
					break;
				case Param.REQUEST_INGRESO_SALA:
					// Obtengo la sala a la que me quiero unir en base al nombre.
					String nombreSala1 = (String) message.getData();
					sala = Servidor.getSalaPorNombre(nombreSala1);
					/*
					 * Me agrego(en realidad es desde la perspectiva del servidor) asi que el
					 * servidor me agrega a la sala.
					 */
					sala.agregarUsuarioASala(usuario);
					/*
					 * El servidor me devuelve los datos de la sala, para que la vista me represente
					 * los datos de la sala que me importan como usuario
					 */
					int cantidadUsuariosActuales = sala.getCantidadUsuarioActuales();
					int cantidadUsuarioMaximos = sala.getCantidadUsuarioMaximos();
					String usuariosActivos = sala.getUsuariosSeparadosporComa();
					System.err.println("datos sala");
					this.salidaDatos.writeObject(new Message(Param.DATOS_SALA,
							cantidadUsuariosActuales + ";" + cantidadUsuarioMaximos + ";" + usuariosActivos).toJson());
					break;
				case Param.REQUEST_EMPEZAR_JUEGO:
					properties = new Gson().fromJson((String) message.getData(), Properties.class);

					int cantidadBots = Integer.valueOf(properties.getProperty("cantidadBots"));
					boolean tipoJuegoFruta = Boolean.valueOf(properties.getProperty(Param.TIPO_JUEGO_FRUTA));
					boolean tipoJuegoSupervivencia = Boolean
							.valueOf(properties.getProperty(Param.TIPO_JUEGO_SUPERVIVENCIA));
					boolean tipoJuegoTiempo = Boolean.valueOf(properties.getProperty(Param.TIPO_JUEGO_TIEMPO));
					int cantidadTotalRondas = Integer.valueOf(properties.getProperty(Param.CANTIDAD_RONDAS,"1"));

					for (int i = 0; i < cantidadBots; i++) {
						sala.agregarUsuarioASala(new UsuarioBot());
					}

					System.err.println("juego empezado");
					TipoJuego tipoJuego = new TipoJuego();

					if (tipoJuegoFruta) {
						tipoJuego = new TipoJuegoFruta(tipoJuego);
					}
					if (tipoJuegoSupervivencia) {
						tipoJuego = new TipoJuegoSupervivencia(tipoJuego);
					}
					if (tipoJuegoTiempo) {
						tipoJuego = new TipoJuegoTiempo(tipoJuego);
					}
					
					//Traer desde la conexion.
					int tipoMapa = 1;
					
					this.salidaDatos.writeObject(
							new Message(Param.REQUEST_JUEGO_EMPEZADO, sala.crearPartida(cantidadBots, tipoJuego, tipoMapa, cantidadTotalRondas))
									.toJson());
					break;
				case Param.REQUEST_ENVIAR_TECLA:

					Posicion posicion = Posicion.values()[((Double)message.getData()).intValue()];
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
					break;
				default:
					break;
				}

			} catch (IOException ex) {
				System.out.println(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					System.out.println("Error al cerrar los stream de entrada y salida :" + ex2.getMessage());
				}
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
