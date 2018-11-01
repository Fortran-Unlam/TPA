package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import config.Param;
import core.mapa.Mapa;
import hibernateUtils.HibernateUtils;
import looby.Sala;
import looby.Usuario;

public class Servidor {

	private static List<Sala> salasActivas = new ArrayList<>();
	public static List<Usuario> usuariosActivos = new ArrayList<>();
	private static Session sessionHibernate = HibernateUtils.getSessionFactory().openSession();
	private static ConexionCliente conexionCliente;

	public static void main(String[] args) {

		ServerSocket servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerSocket(Param.PUERTO, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);
			System.out.println("Corriendo en " + Param.PUERTO);

			while (true) {
				socket = servidor.accept();

				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostAddress() + " conectado.");

				conexionCliente = new ConexionCliente(socket);

				conexionCliente.start();

			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (servidor != null) {
					servidor.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static boolean agregarASalasActivas(Sala sala) {
		return Servidor.salasActivas.add(sala);
	}

	public boolean agregarAUsuariosActivos(Usuario usuario) {
		return Servidor.usuariosActivos.add(usuario);
	}

	public static List<Sala> getAllSalas() {
		return Servidor.salasActivas;
	}

	public static Session getSessionHibernate() {
		return sessionHibernate;
	}

	public static void actualizarMapa(Mapa mapa) {
		try {
			if (conexionCliente != null && conexionCliente.salidaDatos != null) {
				conexionCliente.salidaDatos.reset();
				conexionCliente.salidaDatos.writeObject(new Message(Param.REQUEST_MOSTRAR_MAPA, mapa));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
