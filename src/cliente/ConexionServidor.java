package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import cliente.ventana.VentanaJuego;
import config.Param;
import config.Posicion;
import core.mapa.Mapa;
import looby.Usuario;
import servidor.Message;

public class ConexionServidor {
	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;
	private Message message;
	private Usuario usuario;

	private Socket socketIn;
	private Socket socketOut;

	/**
	 * A partir del socket prepara el stream de entrada y salida
	 * 
	 * @param socketOut
	 * @param socketIn
	 */
	public ConexionServidor(Socket socketOut, Socket socketIn) {
		this.socketOut = socketOut;
		this.socketIn = socketIn;
		try {
			this.salidaDatos = new ObjectOutputStream(this.socketOut.getOutputStream());

			this.entradaDatos = new ObjectInputStream(this.socketIn.getInputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
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
			this.usuario = new Usuario(username, hashPassword);
			
			switch (this.message.getType()) {
			case Param.REQUEST_LOGUEO_CORRECTO:
				// TODO: deberia dar mas info como puntos
				this.usuario.setId((int) message.getData());
				System.out.println("id de usuario " + this.usuario.getId());
				return this.usuario;
			case Param.REQUEST_LOGUEO_INCORRECTO:
				System.out.println("no loguee");
				return null;
			case Param.REQUEST_LOGUEO_DUPLICADO:
				System.out.println("no loguee, usuario ya logeado.");
				return new Usuario(-1);
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("no pudo loguear " + e.getMessage());
		}
		return null;
	}

	public Message registrar(String username, String hashPassword) {
		try {
			ArrayList<String> ret = new ArrayList<String>();
			ret.add(username);
			ret.add(hashPassword);
			this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRAR_USUARIO, ret));

			this.message = (Message) entradaDatos.readObject();
			return this.message;

		} catch (Exception e) {
			System.out.println("no pudo registrar " + e.getMessage());
		}
		return new Message(Param.REQUEST_REGISTRO_INCORRECTO, null);
	}

	/**
	 * Pide las salas al servidor (solo trae los nombres) y espera a que este le
	 * responda
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAllSalas() {
		try {
			this.message = new Message(Param.REQUEST_GET_ALL_SALAS, "");
			this.salidaDatos.writeObject(message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_GET_ALL_SALAS:
				return (ArrayList<String>) this.message.getData();
			default:
				return null;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
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
	public boolean crearSala(ArrayList<String> datosSala) {
		try {

			this.message = new Message(Param.REQUEST_CREAR_SALA, datosSala);
			this.salidaDatos.writeObject(this.message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_SALA_CREADA:
				return true;
			case Param.REQUEST_ERROR_CREAR_SALA:
				return false;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean comenzarJuego() {
		try {

			this.message = new Message(Param.REQUEST_EMPEZAR_JUEGO, null);
			this.salidaDatos.writeObject(this.message);

			this.message = (Message) entradaDatos.readObject();
			switch (this.message.getType()) {
			case Param.REQUEST_JUEGO_EMPEZADO:
				return (boolean) this.message.getData();
			default:
				return false;
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void recibirMapa(VentanaJuego ventanaJuego) {
		try {
			while (true) {

				Object ret = entradaDatos.readObject();
				if (ret instanceof Boolean == false && ret instanceof String == false) {
					//TODO: preguntar al profe
					this.message = (Message) ret;

					switch (this.message.getType()) {
					case Param.REQUEST_MOSTRAR_MAPA:
						ventanaJuego.dibujarMapa((Mapa) this.message.getData());
					default:
					}
				} else {
					System.err.println("FANTASMIN::: " + ret);
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
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

	public void enviarTecla(Posicion posicion) {
		this.message = new Message(Param.REQUEST_ENVIAR_TECLA, posicion);
		try {
			this.salidaDatos.reset();
			this.salidaDatos.writeObject(this.message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void recibirActualizacionDeSala() {
		while(true) {
			try {
				Object loQueMeDaElServer = this.entradaDatos.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
