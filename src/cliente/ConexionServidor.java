package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cliente.ventana.VentanaJuego;
import config.Param;
import config.Posicion;
import core.mapa.Mapa;
import looby.Sala;
import looby.Usuario;
import servidor.Message;

public class ConexionServidor {
	private Socket socket;
	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;
	private Message message;
	private Usuario usuario;

	/**
	 * A partir del socket prepara el stream de entrada y salida
	 * 
	 * @param socket
	 */
	public ConexionServidor(Socket socket) {
		this.socket = socket;
		try {
			this.salidaDatos = new ObjectOutputStream(this.socket.getOutputStream());

			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.err.println("Error al crear el stream: " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		}
	}

	/**
	 * Envia peticion al servidor para loguear y espera la recepcion de la respuesta
	 * 
	 * @param username
	 * @param hashPassword
	 * 
	 * @return Usuario null si no pudo loguear
	 */
	public Usuario loguear(String username, String hashPassword) {
		try {
			ArrayList<String> ret = new ArrayList<String>();
			ret.add(username);
			ret.add(hashPassword);
			this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEAR, ret));

			this.message = (Message) entradaDatos.readObject();

			switch (this.message.getType()) {
			case Param.REQUEST_LOGUEO_CORRECTO:
				this.usuario = (Usuario) message.getData();
				return this.usuario;
			case Param.REQUEST_LOGUEO_INCORRECTO:
				System.out.println("no loguee");
				return null;
			default:
				return null;
			}
		} catch (Exception e) {
			System.out.println("no pudo loguear " + e.getMessage());
		}
		return null;
	}

	
	public Usuario registrar(String username, String hashPassword) {
		try {
			ArrayList<String> ret = new ArrayList<String>();
			ret.add(username);
			ret.add(hashPassword);
			this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRAR_USUARIO, ret));

			this.message = (Message) entradaDatos.readObject();

			switch (this.message.getType()) {
			case Param.REQUEST_REGISTRO_CORRECTO:
				this.usuario = (Usuario) message.getData();
				return this.usuario;
			case Param.REQUEST_REGISTRO_INCORRECTO:
				return null;
			default:
				return null;
			}
		} catch (Exception e) {
			System.out.println("no pudo registrar " + e.getMessage());
		}
		return null;
	}
	
	
	
	
	/**
	 * Pide las salas al servidor y espera a que este le responda
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List<Sala> getAllSalas() throws ClassNotFoundException {
		try {
			this.message = new Message(Param.REQUEST_GET_ALL_SALAS, "");
			this.salidaDatos.writeObject(message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_GET_ALL_SALAS:
				return (List<Sala>) this.message.getData();
			default:
				return null;
			}
		} catch (IOException ex) {
			System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Pide al servidor que cree una sala a partir de los datos y espera a que el
	 * servidor responda
	 * 
	 * @param nombreSala
	 * @param cantidadUsuariosMaximo
	 * 
	 * @return Sala, null si no la creo
	 */
	public Sala craerSala(String nombreSala, int cantidadUsuariosMaximo) {
		try {
			ArrayList<Object> data = new ArrayList<>();
			data.add(nombreSala);
			data.add(cantidadUsuariosMaximo);
			data.add(this.usuario);

			this.message = new Message(Param.REQUEST_CREAR_SALA, data);
			this.salidaDatos.writeObject(this.message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_SALA_CREADA:
				return (Sala) this.message.getData();
			default:
				return null;
			}

		} catch (IOException ex) {
			System.err.println("Error al leer del stream: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Sala comenzarJuego(Sala sala) {
		try {

			this.message = new Message(Param.REQUEST_EMPEZAR_JUEGO, sala);
			this.salidaDatos.writeObject(this.message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_JUEGO_EMPEZADO:
				return (Sala) this.message.getData();
			default:
				return null;
			}

		} catch (IOException ex) {
			System.err.println("Error al leer del stream: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void recibirMapa(VentanaJuego ventanaJuego) {
		try {
			while (true) {
				this.message = (Message) entradaDatos.readObject();
				switch (this.message.getType()) {
				case Param.REQUEST_MOSTRAR_MAPA:
					ventanaJuego.dibujarMapa((Mapa) this.message.getData());
				default:
				}
			}

		} catch (IOException ex) {
			System.err.println("Error al leer del stream: " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void enviarTecla(Posicion sur) {
		this.message = new Message(Param.REQUEST_ENVIAR_TECLA, sur);
		try {
			this.salidaDatos.reset();
			this.salidaDatos.writeObject(this.message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}