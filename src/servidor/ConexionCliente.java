package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import config.Param;
import hibernateUtils.HibernateUtils;
import looby.Usuario;


public class ConexionCliente extends Thread {

	private Socket socket;
	private DataInputStream entradaDatos;
	private DataOutputStream salidaDatos;

	/**
	 * Es el constructor de la clase ConexionCliente, recibe un socket
	 * 
	 * @param socket Un socket ya creado por una conexion recibida por
	 *               ServerSocket.accept();
	 */
	public ConexionCliente(Socket socket) {
		this.socket = socket;

		try {
			this.entradaDatos = new DataInputStream(socket.getInputStream());
			this.salidaDatos = new DataOutputStream(socket.getOutputStream());
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
				mensajeRecibido = Json.createReader(new StringReader(this.entradaDatos.readUTF())).readObject();
				System.out.println("El cliente solicita " + mensajeRecibido.get("request"));
				switch (mensajeRecibido.get("request").toString()) {
				case Param.REQUEST_LOGUEAR:
					// TODO: logica para loguear

					String username = "'" + mensajeRecibido.get("username").toString() + "'";
					String password = "'" + mensajeRecibido.get("hsPassword").toString() + "'";

					System.out.println(password);
					
					Session session = HibernateUtils.getSessionFactory().openSession();
					Transaction tx = null;

					try {
						tx = session.beginTransaction();
						tx.commit();
						
						Query queryLogueo = session.createQuery(
								"SELECT u FROM Usuario u WHERE u.username = " + username + "AND " + "u.password= " + password);
						
						int res =0;
						res = queryLogueo.getFirstResult();
						
						if(res ==0)
							System.out.println("Usuario y/o contraseña incorrectos");
						else
							System.out.println("ACCESO OK!!!");
				
						
					} catch (HibernateException e) {
						if (tx != null)
							tx.rollback();
						e.printStackTrace();
					} finally {
						session.close();
					}

					

					Usuario usuario = new Usuario(1, "a", "b", 0, 0, 0, 0, 0, 0);
					this.salidaDatos.writeUTF(usuario.getUsuarioLogueado());
					break;
				case Param.REQUEST_GET_ALL_SALAS:
					this.salidaDatos.writeUTF(Servidor.requestgetAllSalas());
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
			}
		}
	}
}
