package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.hibernate.Session;

import config.Param;
import core.Jugador;
import core.mapa.Juego;
import core.mapa.Mapa;
import hibernateUtils.HibernateUtils;
import looby.Sala;
import looby.Usuario;

public class Servidor {

	private static Session sessionHibernate = HibernateUtils.getSessionFactory().openSession();
	private static ArrayList<Sala> salasActivas = new ArrayList<Sala>();
	private static ArrayList<Usuario> usuariosActivos = new ArrayList<Usuario>();
	private static ArrayList<ConexionCliente> conexionClientes = new ArrayList<ConexionCliente>();
	private static ArrayList<ConexionClienteBackOff> conexionesClientesBackOff = new ArrayList<ConexionClienteBackOff>();
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String[] args) {

		FileHandler fh = null;
		try {
			fh = new FileHandler("logger.log", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		fh.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fh);

		ServerSocket servidorIn = null;
		ServerSocket servidorOut = null;
		ServerSocket servidorBackOffIn = null;
		ServerSocket servidorBackOffOut = null;
		Socket socketIn = null;
		Socket socketOut = null;
		Socket socketBackOffIn = null;
		Socket socketBackOffOut = null;

		try {
			servidorIn = new ServerSocket(Param.PORT_1, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);

			servidorOut = new ServerSocket(Param.PORT_2, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);

			servidorBackOffIn = new ServerSocket(Param.PORT_3, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);

			servidorBackOffOut = new ServerSocket(Param.PORT_4, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);

			System.out.println("Recibiendo en el puerto: " + Param.PORT_4);
			while (true) {
				socketIn = servidorIn.accept();
				socketOut = servidorOut.accept();

				ConexionCliente conexionCliente = new ConexionCliente(socketIn, socketOut);
				conexionCliente.start();
				conexionClientes.add(conexionCliente);

				socketBackOffIn = servidorBackOffIn.accept();
				socketBackOffOut = servidorBackOffOut.accept();

				ConexionClienteBackOff conexionClienteBackOff = new ConexionClienteBackOff(socketBackOffIn,
						socketBackOffOut);

				conexionClienteBackOff.start();
				conexionesClientesBackOff.add(conexionClienteBackOff);

				LOGGER.log(Level.INFO,
						"Cliente con la IP " + socketIn.getInetAddress().getHostAddress() + " conectado.");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (servidorIn != null) {
					servidorIn.close();
				}
				if (socketIn != null) {
					socketIn.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected static boolean agregarASalasActivas(Sala sala) {
		return Servidor.salasActivas.add(sala);
	}

	protected static void removerDeSalasActivas(Sala sala) {
		Servidor.salasActivas.remove(sala);
	}

	/**
	 * Verifica si la sala existe en las salas activas
	 * 
	 * @param nombre El nombre de la sala a buscar
	 * @return True si existe
	 */
	protected static boolean existeSala(String nombre) {
		for (Sala sala : Servidor.salasActivas) {
			if (sala.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remueve un usuario activo
	 * 
	 * @param usuario
	 * @return
	 */
	protected static boolean removerUsuarioActivo(Usuario usuario) {
		return Servidor.usuariosActivos.remove(usuario);
	}

	/**
	 * Agrega un usuario a activo
	 * 
	 * @param usuario
	 * @return
	 */
	protected static boolean agregarAUsuariosActivos(Usuario usuario) {
		return Servidor.usuariosActivos.add(usuario);
	}

	/**
	 * Consigue los usuarios activos
	 * 
	 * @param usuario
	 * @return
	 */
	protected static ArrayList<Usuario> getUsuariosActivos() {
		return Servidor.usuariosActivos;
	}

	/**
	 * Consigue las todas salas en el servidor
	 * 
	 * @return
	 */
	/**
	 * @return
	 */
	protected static JsonArray getAllSalas() {
		JsonArrayBuilder datosDeSalas = Json.createArrayBuilder();
		for (Sala salaActiva : Servidor.salasActivas) {
			JsonObjectBuilder sala = Json.createObjectBuilder();
			sala.add("nombre", salaActiva.getNombre())
					.add("cantidadUsuariosActivos", String.valueOf(salaActiva.getCantidadUsuarioActuales()))
					.add("cantidadUsuariosMaximos", String.valueOf(salaActiva.getCantidadUsuarioMaximos()))
					.add("administrador", salaActiva.getAdministrador().getUsername()).build();
			datosDeSalas.add(sala);
		}
		return datosDeSalas.build();
	}

	/**
	 * Consigue la session de Hibernate
	 * 
	 * @return
	 */
	public static Session getSessionHibernate() {
		return sessionHibernate;
	}

	/**
	 * Actualizar juego, deberia ser usado para dibujar lo que hay en el.
	 * 
	 * @param juego
	 */
	public static boolean actualizarJuego(Juego juego) {
		Mapa mapa = juego.getMapa();
		Message message = new Message(Param.REQUEST_MOSTRAR_MAPA, juego.toJson().toString());
		boolean enviar = false;
		for (ConexionCliente conexionCliente : conexionClientes) {
			enviar = false;
			Usuario usuario = conexionCliente.getUsuario();
			if (usuario != null && conexionCliente.getSalidaDatos() != null && mapa != null && usuario.inJuego) {
				for (Jugador jugadorMapa : mapa.getJugadores()) {
					if (usuario.getJugador().equals(jugadorMapa)) {
						enviar = true;
						break;
					}
				}

				if (enviar == false) {
					for (Jugador espectador : mapa.getEspectadores()) {
						if (usuario.getJugador().equals(espectador)) {
							enviar = true;
							break;
						}
					}
				}

				if (enviar) {
					try {
						conexionCliente.getSalidaDatos().reset();
						conexionCliente.getSalidaDatos().flush();
						conexionCliente.getSalidaDatos().writeObject(message.toJson());
					} catch (IOException e) {
						LOGGER.log(Level.INFO, "No se pudo actualizar el mapa ");
						return false;
					}
				}

			}

		}
		return true;
	}

	/**
	 * Desconectar un cliente
	 * 
	 * @param conexionCliente
	 */
	public static void desconectar(ConexionCliente conexionCliente) {
		conexionCliente.interrupt();
		conexionClientes.remove(conexionCliente);
	}

	/**
	 * Desconectar el backoff de un cliente
	 * 
	 * @param conexionClienteBackOff
	 */
	public static void desconectarBackOff(ConexionClienteBackOff conexionClienteBackOff) {
		conexionClienteBackOff.interrupt();
		conexionesClientesBackOff.remove(conexionClienteBackOff);
	}

	/*
	 * Metodo para encontrar la sala por nombre, ya que en las ventanas, se maneja
	 * el nombre de la sala. no el id, y la informacion que envia el cliente sobre
	 * la sala, esta basado en los datos de las ventanas.
	 */
	public static Sala getSalaPorNombre(String Nombre) {
		for (int i = 0; i < salasActivas.size(); i++) {
			if (salasActivas.get(i).getNombre().equals(Nombre)) {
				return salasActivas.get(i);
			}
		}
		return null;
	}

	/**
	 * Consigue las conexiones backoff de los clientes
	 * 
	 * @return
	 */
	public static ArrayList<ConexionClienteBackOff> getConexionesClientesBackOff() {
		return Servidor.conexionesClientesBackOff;
	}

}
