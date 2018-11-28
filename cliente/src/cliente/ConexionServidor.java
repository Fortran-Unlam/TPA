package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cliente.ventana.VentanaJuego;
import config.Param;
import config.Posicion;

public class ConexionServidor {
	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;

	private Message message;
	private Usuario usuario;

	private Socket socketIn;
	private Socket socketOut;

	private boolean recibirMapa; // Fix

	/**
	 * A partir del socket prepara el stream de entrada y salida
	 * 
	 * @param socketOut
	 * @param socketIn
	 */
	public ConexionServidor(Socket socketOut, Socket socketIn) {
		this.socketOut = socketOut;
		this.socketIn = socketIn;
		this.recibirMapa = true;
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
			this.salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEAR, request).toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
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
			while (true) {

				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
				// Cuando creo una partida y salgo y vuelvo a crear una sala, no tiene que decir
				// MostrarMapa.
				// Esto pasa porque me desconecte, pero el servidor me sigue mandando
				// informacion del juego
				// Y yo estoy esperando otros mensajes no informacion de un juego al que no
				// pertenezco.
				System.out.println(this.message.getType());
				switch (this.message.getType()) {
				case Param.REQUEST_SALA_CREADA:
					return true;
				case Param.REQUEST_ERROR_CREAR_SALA:
					return false;
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

	public boolean comenzarJuego(int cantidadBots, int cantidadRondas) {
		try {
			String request = "{\"cantidadBots\":" + cantidadBots + ",\"" + Param.TIPO_JUEGO_FRUTA + "\": true, \""
					+ Param.TIPO_JUEGO_SUPERVIVENCIA + "\": true, \"" + Param.TIPO_JUEGO_TIEMPO + "\": false, \""
					+ Param.CANTIDAD_RONDAS + "\": " + cantidadRondas + "}";

			this.message = new Message(Param.REQUEST_EMPEZAR_JUEGO, request);
			System.err.println("empezar juego");
			this.salidaDatos.writeObject(this.message.toJson());

			while (socketIn.isClosed() == false && recibirMapa) {
				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);

				switch (this.message.getType()) {
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

	// Este metodo es invocado por VentanaJuego y detiene la accion iniciada por
	// ComenzarJuego.
	public void detenerJuego() {
		this.recibirMapa = false;
		this.message = new Message(Param.REQUEST_SALIR_JUEGO, "");
		System.err.println("Salir juego");
		try {
			this.salidaDatos.writeObject(this.message.toJson());
		} catch (IOException e) {
		}
	}

	public void recibirMapa(VentanaJuego ventanaJuego) {
		try {
			while (true && recibirMapa) {

				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);

				switch (this.message.getType()) {
				case Param.REQUEST_MOSTRAR_MAPA:
					ventanaJuego.dibujarMapaJson((String) this.message.getData());
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
	
	public String[] recibirGanador(boolean partidaTerminada) {
		//Recibir el ganador de cada partida. Pendiente.
		String[] datosGanador = {"Jugador1","0","0"};
		
		try {
			//El request esta al pedo me pa.
			//String request = "{\"partidaTerminada\":" + partidaTerminada + "}";
			this.message = new Message(Param.REQUEST_MOSTRAR_GANADOR, true);
			this.salidaDatos.writeObject(this.message.toJson());
			
			Message retorno = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			datosGanador = ((String)retorno.getData()).split(";");
			
			switch (this.message.getType()) {
			case Param.REQUEST_GANADOR_ENVIADO:
				return datosGanador;
//				
//				"vibora"
//				"nombre"
//				"color_red",
//				"color_green"
//				"color_blue"
//				"frutasComidasEnRonda"
				
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datosGanador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void enviarTecla(Posicion posicion) {
		if (posicion == null) {
			return;
		}
		this.message = new Message(Param.REQUEST_ENVIAR_TECLA, posicion.ordinal());
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
			while(true) {				
				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
				
				if (message.getType() == Param.REQUEST_ACTUALIZAR_SALAS) {
					return (String) message.getData();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			//TODO: log
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			//TODO: log
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//TODO: log
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
