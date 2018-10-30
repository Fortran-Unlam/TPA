package servidor;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import config.Param;
import hibernateUtils.HibernateUtils;
import looby.Sala;
import looby.TipoJuego;
import looby.Usuario;
import looby.UsuarioDAO;

public class ConexionCliente extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;

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

	@Override
	public void run() {
		boolean conectado = true;

		Sala sala = null;
		Usuario usuario = null;
		
		while (conectado) {
			try {
				System.out.println("A la espera de un mensaje");
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());

				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					usuario = UsuarioDAO.loguear((String)((ArrayList) message.getData()).get(0), (String)((ArrayList) message.getData()).get(1));
					
					if (usuario == null) {
						System.out.println("Usuario y/o contrase�a incorrectos");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_INCORRECTO, usuario));
						return;
					} else {
						Servidor.usuariosActivos.add(usuario);

						System.out.println("ACCESO OK!!!");
						this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_CORRECTO, usuario));
					}

					break;

				case Param.REQUEST_REGISTRAR_USUARIO:

					String usernameNew = "'" + ((ArrayList) message.getData()).get(0) + "'";
					String hashPasswordNew = "'" + ((ArrayList) message.getData()).get(1) + "'";

					Transaction txReg = null;

					try {
						txReg = Servidor.getSessionHibernate().beginTransaction();
						txReg.commit();

						Query queryRegistrar = Servidor.getSessionHibernate()
								.createQuery("SELECT u FROM Usuario u WHERE u.username = " + usernameNew);

						List<Usuario> userReg = queryRegistrar.getResultList();

						if (userReg.isEmpty()) {
							System.out.println("Usuario disponible");
							Servidor.getSessionHibernate().createQuery("INSERT INTO Usuario (username,password) VALUES ('"
									+ usernameNew + "','" + hashPasswordNew + "')");
							this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRO_CORRECTO, userReg.get(0)));
						} else {
							System.out.println("Usuario no disponilbe, debe ingresar otro usuario");
							this.salidaDatos
									.writeObject(new Message(Param.REQUEST_REGISTRO_INCORRECTO, userReg.get(0)));
						}

					} catch (HibernateException e) {
						if (txReg != null)
							txReg.rollback();
						e.printStackTrace();
					} finally {
						Servidor.getSessionHibernate().close();
					}
					break;
				case Param.REQUEST_GET_ALL_SALAS:
					System.out.println("envio las salas");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()));
					break;
				case Param.REQUEST_CREAR_SALA:

					ArrayList data = ((ArrayList) message.getData());
					usuario = (Usuario) data.get(2);
					sala = usuario.crearSala((String) data.get(0), (int) data.get(1));

					Servidor.manejadorSala.agregarASalasActivas(sala);

					this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, sala));
					break;
				case Param.REQUEST_EMPEZAR_JUEGO:

					sala = (Sala) message.getData();
					sala.crearPartida(1, new TipoJuego());
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								// TODO: para empezar la partida hacer un metodo que llame a la sala y comience
								// la partida
								// sala.comenzarPartida();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

					this.salidaDatos.writeObject(new Message(Param.REQUEST_JUEGO_EMPEZADO, sala));
					break;
				default:
					break;
				}

			} catch (IOException ex) {
				String mensaje = ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado.";
				System.out.println(mensaje);
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					String mensajeError2 = "Error al cerrar los stream de entrada y salida :" + ex2.getMessage();
					System.out.println(mensajeError2);
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}
