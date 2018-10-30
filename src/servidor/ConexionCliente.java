package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import config.Param;
import hibernateUtils.HibernateUtils;
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
		JsonObject mensajeRecibido;
		boolean conectado = true;

		while (conectado) {
			try {
				System.out.println("A la espera de un mensaje");
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());
				switch (message.getType()) {
				case Param.REQUEST_LOGUEAR:
					// TODO: logica para loguear

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
							System.out.println("Usuario y/o contraseña incorrectos");
						} else {
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
				case Param.REQUEST_GET_ALL_SALAS:
					System.out.println("envio las salas");
					this.salidaDatos.writeObject(new Message(Param.REQUEST_GET_ALL_SALAS, Servidor.getAllSalas()));
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
