package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.json.Json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cliente.ventana.VentanaJuego;
import config.Param;
import config.Posicion;
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
			String request = Json.createObjectBuilder().add("username", username).add("hashPassword", hashPassword)
					.build().toString();
			System.out.println("envio loguear");
			this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEAR, request).toJson());
			System.out.println("espero logueo");

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			System.out.println("recibo el logueo");
			switch (this.message.getType()) {
			case Param.REQUEST_LOGUEO_CORRECTO:
				this.usuario = new Gson().fromJson((String) message.getData(), Usuario.class);
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
			String request = Json.createObjectBuilder().add("username", username).add("hashPassword", hashPassword)
					.build().toString();

			System.err.println("registrar usuario");
			this.salidaDatos.writeObject(new Message(Param.REQUEST_REGISTRAR_USUARIO, request).toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			return this.message;

		} catch (Exception e) {
			System.out.println("no pudo registrar " + e.getMessage());
		}
		return new Message(Param.REQUEST_REGISTRO_INCORRECTO, null);
	}

	public Message cerrarSesionUsuario(Usuario usuario) {
		try {
			System.err.println("cerrar sesion");
			this.salidaDatos.writeObject(new Message(Param.REQUEST_CERRAR_SESION, new Gson().toJson(usuario)).toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			return this.message;

		} catch (Exception e) {
			System.out.println("No se pudo cerrar sesion" + e.getMessage());
			e.printStackTrace();
		}
		return new Message(Param.REQUEST_CERRAR_SESION, null);
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
			System.err.println("all salas");
			this.salidaDatos.writeObject(message.toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
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
			System.err.println("crear sala");
			this.salidaDatos.writeObject(this.message.toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
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

	/**
	 * Se informa al servidor que voy a salir de la sala y quiza, si soy el unico
	 * que esta en la sala y salgo la sala debe borrarse. O directamente eliminar la
	 * sala si el usuario creador sale de la misma.
	 * 
	 */
	public void SalirSala(String nombreSala) {
		try {
			this.message = new Message(Param.REQUEST_SALIR_SALA, nombreSala);
			System.err.println("salir sala");
			this.salidaDatos.writeObject(this.message.toJson());
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean comenzarJuego(String cantidadBots) {
		try {
			String request = "{\"cantidadBots\":" + cantidadBots + ",\"" + Param.TIPO_JUEGO_FRUTA + "\": true, \""
					+ Param.TIPO_JUEGO_SUPERVIVENCIA + "\": true, \"" + Param.TIPO_JUEGO_TIEMPO + "\": false }";
			System.out.println("GGGGGGG " + request);
			this.message = new Message(Param.REQUEST_EMPEZAR_JUEGO, request);
			System.err.println("empezar juego");
			this.salidaDatos.writeObject(this.message.toJson());

			while (socketIn.isClosed() == false) {
				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
				String ret = this.message.getType();
				System.out.println(ret);
				switch (ret) {
				case Param.REQUEST_JUEGO_EMPEZADO:
					return (boolean) this.message.getData();
				}
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

				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);

				switch (this.message.getType()) {
				case Param.REQUEST_MOSTRAR_MAPA:
					System.err.println("mapa " + System.currentTimeMillis());
					ventanaJuego.dibujarMapaJson((String) this.message.getData());
				default:
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
			this.salidaDatos.writeObject(this.message.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String recibirActualizacionDeSala() {
		try {
			// se queda esperando que el server env�e alg�n tipo de actualizacion;
			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);

			if (message.getType() == Param.REQUEST_ACTUALIZAR_SALAS) {
				return (String) message.getData();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Le envio un mensaje al servidor indicando que me voy a unir a la
	 * salaSeleccionada. La representancion del usuario no es necesario mandarla, ya
	 * que se encuentra implicito en el Socket.
	 */
	public String unirseASala(String salaSeleccionada) {
		try {
			System.err.println("ingreso sala");
			this.salidaDatos.writeObject(new Message(Param.REQUEST_INGRESO_SALA, salaSeleccionada).toJson());
			Message retorno = (Message) new Gson().fromJson((String) this.entradaDatos.readObject(), Message.class);
			return (String) retorno.getData();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
