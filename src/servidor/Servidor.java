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
	private static ArrayList<ConexionCliente> conexionClientes = new ArrayList<ConexionCliente>();

	public static void main(String[] args) {

		ServerSocket servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerSocket(Param.PUERTO, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);
			System.out.println("Corriendo en " + Param.PUERTO);

			while (true) {
				socket = servidor.accept();

				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostAddress() + " conectado.");

				ConexionCliente conexionCliente = new ConexionCliente(socket);
				
				conexionClientes.add(conexionCliente);

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
	
	public static boolean existeSala(String nameRoom) {
		for(Sala s: Servidor.salasActivas) {
			if(s.getNombre() == nameRoom)
				return true;
		}
		return false;
	}

	public boolean agregarAUsuariosActivos(Usuario usuario) {
		return Servidor.usuariosActivos.add(usuario);
	}
	
	public static ArrayList<String> getAllSalas(){
		ArrayList<String> salas = new ArrayList<>();
		
		for(Sala s: Servidor.salasActivas) {
			salas.add(s.getNombre());
		}
		
		return salas;
	}

	public static Session getSessionHibernate() {
		return sessionHibernate;
	}

	public static void actualizarMapa(Mapa mapa) {
		try {
			for (ConexionCliente conexionCliente : conexionClientes) {				
				if (conexionCliente != null && conexionCliente.getSalidaDatos() != null && mapa != null) {
					try {
						conexionCliente.getSalidaDatos().reset();
						conexionCliente.getSalidaDatos().flush();
						conexionCliente.getSalidaDatos().writeObject(new Message(Param.REQUEST_MOSTRAR_MAPA, mapa));
					} catch(IOException e) {
						System.out.println("sucede porque todavia no envie un mapa");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void desconectar(ConexionCliente conexionCliente) {
		conexionCliente.interrupt();
		conexionClientes.remove(conexionCliente);
	}
	
}
