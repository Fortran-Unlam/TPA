package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.hibernate.Session;

import config.Param;
import core.Jugador;
import core.mapa.Mapa;
import hibernateUtils.HibernateUtils;
import looby.Sala;
import looby.Usuario;

public class Servidor {

	private static ArrayList<Sala> salasActivas = new ArrayList<>();
	public static ArrayList<Usuario> usuariosActivos = new ArrayList<>();
	private static Session sessionHibernate = HibernateUtils.getSessionFactory().openSession();
	private static ArrayList<ConexionCliente> conexionClientes = new ArrayList<ConexionCliente>();

	public static void main(String[] args) {

		ServerSocket servidor = null;
		ServerSocket servidorOut = null;
		Socket socketIn = null;
		Socket socketOut = null;

		try {
			servidor = new ServerSocket(Param.PORT_1, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);
			System.out.println("Corriendo en " + Param.PORT_1);

			servidorOut = new ServerSocket(Param.PORT_2, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);
			
			while (true) {
				socketIn = servidor.accept();
				socketOut = servidorOut.accept();

				System.out.println("Cliente con la IP " + socketIn.getInetAddress().getHostAddress() + " conectado.");

				ConexionCliente conexionCliente = new ConexionCliente(socketIn, socketOut);
				
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
				if (socketIn != null) {
					socketIn.close();
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
			for (Usuario usuario : usuariosActivos) {
				if (usuario != null && usuario.getConexion().getSalidaDatos() != null && mapa != null) {
					try {
						for (Jugador jugadorMapa : mapa.getJugadores()) {
							
							if (usuario.getJugador().equals(jugadorMapa)) {
								usuario.getConexion().getSalidaDatos().reset();
								usuario.getConexion().getSalidaDatos().flush();
								usuario.getConexion().getSalidaDatos().writeObject(new Message(Param.REQUEST_MOSTRAR_MAPA, mapa));
							}
						}
						
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

	public static void avisarFinJuego() {
		
	}
	
}
