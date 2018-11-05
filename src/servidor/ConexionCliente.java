package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import config.Param;
import config.Posicion;
import looby.Sala;
import looby.TipoJuego;
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

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		boolean conectado = true;

//		Sala sala = null;
//		Usuario usuario = null;

		while (conectado) {
			try {
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());

				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					usuario = UsuarioDAO.loguear((String) ((ArrayList) message.getData()).get(0),
							(String) ((ArrayList) message.getData()).get(1));

					if (usuario == null) {
						System.out.println("Usuario y/o contrasenia incorrectos");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_INCORRECTO, null));
					} else {
						boolean usuarioInexistente = true;
						for (Usuario usuarioActivo : Servidor.usuariosActivos) {

							if (usuarioActivo.getId() == usuario.getId()) {
								System.out.println("Usuario ya logeado");
								this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_DUPLICADO, null));
								usuarioInexistente = false;
								break;
							}

						}
						if (usuarioInexistente) {
							Servidor.usuariosActivos.add(usuario);
							usuario.setConexion(this);
							this.salidaDatos.flush();
							System.err.println("logeo correcto");
							this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_CORRECTO, usuario.getId()));
						}
					}
					break;

				case Param.REQUEST_REGISTRAR_USUARIO:

					int resultado = UsuarioDAO.registrar((String) ((ArrayList) message.getData()).get(0),
							(String) ((ArrayList) message.getData()).get(1));

					switch (resultado) {
					case -1:
						System.err.println("registro incorrecto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_INCORRECTO, null));
						break;
					case 0:
						System.err.println("registro correcto");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_CORRECTO, null));
						break;
					case 1:
						System.err.println("registro duplicado");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_DUPLICADO, null));
						break;
					}

					break;

				case Param.REQUEST_GET_ALL_SALAS:
					System.err.println("all salas");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()));
					break;

				case Param.REQUEST_CREAR_SALA:
					ArrayList<String> dataSala = (ArrayList<String>) message.getData();
					if (!Servidor.existeSala(dataSala.get(0))) { // me fijo si existe
						sala = usuario.crearSala(dataSala.get(0), Integer.valueOf(dataSala.get(1)));

						Servidor.agregarASalasActivas(sala);
						System.err.println("sala cdreada");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, true));
						
						
						//Envio a los clientes que estaban en "unir sala" la actualizaci�n de la nueva sala
						//Esto deber�a mandarse por el canal de syncro pero por ahora va.
						String datosSalaNueva;
						
						datosSalaNueva = sala.getNombre() + Param.SEPARADOR_EN_MENSAJES + sala.getCantidadUsuarioActuales()
						+ Param.SEPARADOR_EN_MENSAJES + sala.getCantidadUsuarioMaximos();
						
						System.err.println("actualizar salas");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_ACTUALIZAR_SALAS, datosSalaNueva));
					} else {
						System.err.println("crear sala");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_ERROR_CREAR_SALA, false));
					}

					break;
				case Param.REQUEST_SALIR_SALA:
					usuario.salirDeSala();
					String nombreSala = (String) message.getData();
					Sala s = Servidor.getSalaPorNombre(nombreSala);
					//Es similar al usuario.SalirSala() ? Estoy duplicando la accion.
					s.sacarUsuarioDeSala(usuario);
					//Debug para comprobar verdaderamente la cantidad de usuarios con los que quedo la sala.
					//System.out.println("ASD:"+s.getCantidadUsuarioActuales());
					//Si tras la salida del usuario, la sala se quedo con 0 usuarios entonces debe eliminarse de las salas activas.
					if(s.getCantidadUsuarioActuales()==0)
						Servidor.removerDeSalasActivas(s);
					break;
				case Param.REQUEST_INGRESO_SALA:
					//Obtengo la sala a la que me quiero unir en base al nombre.
					String nombreSala1 = (String) message.getData();
					Sala s1 = Servidor.getSalaPorNombre(nombreSala1);
					/*Me agrego(en realidad es desde la perspectiva del servidor) asi que
					el servidor me agrega a la sala.*/
					s1.agregarUsuarioASala(usuario);
					/*El servidor me devuelve los datos de la sala, para que la vista
					me represente los datos de la sala que me importan como usuario*/
					int ux = s1.getCantidadUsuarioActuales();
					int uxx = s1.getCantidadUsuarioMaximos();
					String usuariosActivos = s1.getUsuariosSeparadosporComa();
					System.err.println("datos sala");
					this.salidaDatos.writeObject(new Message(Param.DATOS_SALA, ux+";"+uxx+";"+usuariosActivos));
					break;
				case Param.REQUEST_EMPEZAR_JUEGO:
					// TODO: no es necesario mandar la sala ya que referencia a una posicion de
					// memoria en el cliente y no de este lado que es el servidor
//					sala = (Sala) message.getData();
					sala.agregarUsuarioASala(new UsuarioBot("j", "a"));
					sala.agregarUsuarioASala(new UsuarioBot("j0", "a"));
					sala.agregarUsuarioASala(new UsuarioBot("j0n", "a"));

					System.err.println("juego empezado");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_JUEGO_EMPEZADO, sala.crearPartida(2, new TipoJuego())));
					break;
				case Param.REQUEST_ENVIAR_TECLA:

					Posicion posicion = (Posicion) message.getData();
					if (posicion != null) {
						System.out.println("recibe " + posicion.ordinal());
						this.usuario.getJugador().setTecla(posicion);
					}
					break;
				case Param.REQUEST_CERRAR_SESION:
					Usuario usuarioActivo = (Usuario) message.getData();
					
					for (Usuario u : Servidor.usuariosActivos) {
						if (u.getId() == usuarioActivo.getId()) {
							Servidor.usuariosActivos.remove(Servidor.usuariosActivos.indexOf(u));
							break;
						}
					}
					
					this.salidaDatos.flush();
					System.err.println("cerrar sesion");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_CERRAR_SESION_OK, null));
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
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
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
