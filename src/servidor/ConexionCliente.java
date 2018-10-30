package servidor;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cliente.ventana.VentanaMenu;
import config.Param;
import hibernateUtils.HibernateUtils;
import looby.ManejadorSala;
import looby.Sala;
import looby.TipoJuego;
import looby.Usuario;

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
		
		while (conectado) {
			try {
				System.out.println("A la espera de un mensaje");
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());
				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					String username = "'" + ((ArrayList) message.getData()).get(0) + "'";
					String hashPassword = "'" + ((ArrayList) message.getData()).get(1) + "'";

					Session session = HibernateUtils.getSessionFactory().openSession();
					Transaction tx = null;

					try {
						tx = session.beginTransaction();
						tx.commit();

						Query queryLogueo = session.createQuery("SELECT u FROM Usuario u WHERE u.username = " + username
								+ "AND u.password = " + hashPassword);

						List<Usuario> user = queryLogueo.getResultList();

						if (user.isEmpty()) {
							System.out.println("Usuario y/o contrase�a incorrectos");
							this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_INCORRECTO, user.get(0)));
							return;
						} else {
							// TODO: agregar todos los datos del usuario
							Usuario usuario = new Usuario(username, hashPassword);
							Servidor.usuariosActivos.add(usuario);
							
							System.out.println("ACCESO OK!!!");
							this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEO_CORRECTO, user.get(0)));
						}

					} catch (HibernateException e) {
						if (tx != null)
							tx.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}

					break;

				case Param.REQUEST_REGISTRAR_USUARIO:

					String usernameNew = "'" + ((ArrayList) message.getData()).get(0) + "'";
					String hashPasswordNew = "'" + ((ArrayList) message.getData()).get(1) + "'";

					Session sessionRegistrar = HibernateUtils.getSessionFactory().openSession();
					Transaction txReg = null;

					try {
						txReg = sessionRegistrar.beginTransaction();
						txReg.commit();

						Query queryRegistrar = sessionRegistrar
								.createQuery("SELECT u FROM Usuario u WHERE u.username = " + usernameNew);

						List<Usuario> userReg = queryRegistrar.getResultList();

						if (userReg.isEmpty()) {
							System.out.println("Usuario disponible");
							sessionRegistrar.createQuery("INSERT INTO Usuario (username,password) VALUES ('"
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
						sessionRegistrar.close();
					}
					break;
				case Param.REQUEST_GET_ALL_SALAS:
					System.out.println("envio las salas");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()));
					break;
				case Param.REQUEST_CREAR_SALA:

					ArrayList data = ((ArrayList) message.getData());
					Usuario usuario = (Usuario)data.get(2);
					sala = usuario.crearSala((String)data.get(0), (int)data.get(1));
					
					Servidor.manejadorSala.agregarASalasActivas(sala);
					
					this.salidaDatos.writeObject(new Message(Param.REQUEST_SALA_CREADA, sala));
					break;
				case Param.REQUEST_EMPEZAR_JUEGO:

					sala = (Sala) message.getData();
					sala.crearPartida(1, new TipoJuego());
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								// TODO: para empezar la partida hacer un metodo que llame a la sala y comience la partida
//								sala.comenzarPartida();
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
