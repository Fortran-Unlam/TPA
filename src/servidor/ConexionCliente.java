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

	/**
	 * Es el constructor de la clase ConexionCliente, recibe un socket
	 * 
	 * @param socket Un socket ya creado por una conexion recibida por
	 *               ServerSocket.accept();
	 */
	public ConexionCliente(Socket socket) {
		this.socket = socket;

		try {
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			this.salidaDatos = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException ex) {
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		boolean conectado = true;

		Sala sala = null;
		Usuario usuario = null;

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
						Servidor.usuariosActivos.add(usuario);
						this.usuario = usuario;
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_CORRECTO, usuario));
					}

					break;

				case Param.REQUEST_REGISTRAR_USUARIO:

					int resultado = UsuarioDAO.registrar((String) ((ArrayList) message.getData()).get(0),
							(String) ((ArrayList) message.getData()).get(1));

					switch (resultado) {
					case -1:
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_INCORRECTO, null));
						break;
					case 0:
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_CORRECTO, null));
						break;
					case 1:
						this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_DUPLICADO, null));
						break;
					}

					break;

				case Param.REQUEST_GET_ALL_SALAS:
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()));
					break;

				case Param.REQUEST_CREAR_SALA:
					ArrayList<String> dataSala = (ArrayList<String>) message.getData();
					if (!Servidor.existeSala(dataSala.get(0))) { // me fijo si existe
						sala = usuario.crearSala(dataSala.get(0), Integer.valueOf(dataSala.get(1)));

						Servidor.agregarASalasActivas(sala);
						this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, true));
					} else {
						this.salidaDatos.writeObject(new Message(Param.REQUEST_ERROR_CREAR_SALA, false));
					}

					break;
				case Param.REQUEST_EMPEZAR_JUEGO:
					// TODO: no es necesario mandar la sala ya que referencia a una posicion de
					// memoria en el cliente y no de este lado que es el servidor
//					sala = (Sala) message.getData();
					sala.agregarUsuarioASala(new UsuarioBot("j", "a"));
					sala.agregarUsuarioASala(new UsuarioBot("j0", "a"));
					sala.agregarUsuarioASala(new UsuarioBot("j0n", "a"));
					sala.crearPartida(2, new TipoJuego());

					this.salidaDatos.writeObject(new Message(Param.REQUEST_JUEGO_EMPEZADO, true));
					break;
				case Param.REQUEST_ENVIAR_TECLA:

					Posicion posicion = (Posicion) message.getData();
					if (posicion != null) {
						System.out.println("recibe " + posicion.ordinal());
						this.usuario.getJugador().setTecla(posicion);
					}
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
