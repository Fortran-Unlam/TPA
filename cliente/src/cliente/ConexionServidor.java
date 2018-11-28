package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.json.Json;

import com.google.gson.Gson;

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
			Cliente.LOGGER.error("No puede abrir la conexion con el servidor " + ex.getMessage());
		} catch (NullPointerException ex) {
			Cliente.LOGGER.error("Algun socket es null " + ex.getMessage());
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
				return null;
			case Param.REQUEST_LOGUEO_DUPLICADO:
				return new Usuario(-1);
			default:
				return null;
			}
		} catch (Exception e) {
			Cliente.LOGGER.error("no pudo loguear " + e.getMessage());
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
			Cliente.LOGGER.error("no pudo registrar " + e.getMessage());
		}
		return new Message(Param.REQUEST_REGISTRO_INCORRECTO, null);
	}

	public Message cerrarSesionUsuario(Usuario usuario) {
		try {
			this.salidaDatos.writeObject(new Message(Param.REQUEST_CERRAR_SESION, new Gson().toJson(usuario)).toJson());

			this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			return this.message;

		} catch (Exception e) {
			Cliente.LOGGER.error("No se pudo cerrar sesion" + e.getMessage());
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
			this.salidaDatos.writeObject(this.message.toJson());
			while (true) {

				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
				// Cuando creo una partida y salgo y vuelvo a crear una sala, no tiene que decir
				// MostrarMapa.
				// Esto pasa porque me desconecte, pero el servidor me sigue mandando
				// informacion del juego
				// Y yo estoy esperando otros mensajes no informacion de un juego al que no
				// pertenezco.
				switch (this.message.getType()) {
				case Param.REQUEST_SALA_CREADA:
					return true;
				case Param.REQUEST_ERROR_CREAR_SALA:
					return false;
				}
			}

		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en creacion sala " + ex.getMessage());
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
			this.salidaDatos.writeObject(this.message.toJson());
		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en creacion sala " + ex.getMessage());
		}
	}

	public boolean comenzarJuego(int cantidadBots, int cantidadRondas,String mapa,boolean tipoDeJuegoFruta,int cantidadDeFrutas,boolean tipoDeJuegoTiempo,int cantidadDeTiempo) {
		try {
			String request = "{\"" + Param.CANTIDAD_DE_BOTS + "\":\"" + cantidadBots + "\",\"" + Param.TIPO_JUEGO_FRUTA + "\":\""
					+ tipoDeJuegoFruta + "\",\"" + Param.CANTIDAD_DE_FRUTAS + "\":\"" + cantidadDeFrutas + "\",\""
					+ Param.TIPO_JUEGO_TIEMPO + "\":\"" + tipoDeJuegoTiempo + "\",\"" + Param.CANTIDAD_DE_TIEMPO + "\":\""
					+ cantidadDeTiempo + "\",\"" + Param.CANTIDAD_RONDAS + "\":\"" + cantidadRondas + "\"}";

			this.message = new Message(Param.REQUEST_EMPEZAR_JUEGO, request);
			this.salidaDatos.writeObject(this.message.toJson());

			while (socketIn.isClosed() == false && recibirMapa) {
				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);

				switch (this.message.getType()) {
				case Param.REQUEST_JUEGO_EMPEZADO:
					return (boolean) this.message.getData();
				}
			}

		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en comenzar juego " + ex.getMessage());
		}
		return false;
	}

	// Este metodo es invocado por VentanaJuego y detiene la accion iniciada por
	// ComenzarJuego.
	public void detenerJuego() {
		this.recibirMapa = false;
		this.message = new Message(Param.REQUEST_SALIR_JUEGO, "");
		try {
			this.salidaDatos.writeObject(this.message.toJson());
		} catch (IOException ex) {
			Cliente.LOGGER.error("Error al detener juego " + ex.getMessage());
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

		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en creacion sala " + ex.getMessage());
		}
	}
	
	/**
	 * Recibir el ganador de cada partida. Pendiente.
	 * 
	 * @param partidaTerminada
	 * @return
	 */
	public String[] recibirGanador(boolean partidaTerminada) {
		String[] datosGanador = {"Jugador1","0","0"};
		
		try {
			this.message = new Message(Param.REQUEST_MOSTRAR_GANADOR, true);
			this.salidaDatos.writeObject(this.message.toJson());
			
			Message retorno = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
			datosGanador = ((String)retorno.getData()).split(";");
			
			switch (this.message.getType()) {
			case Param.REQUEST_GANADOR_ENVIADO:
				return datosGanador;
//				"vibora"
//				"nombre"
//				"color_red",
//				"color_green"
//				"color_blue"
//				"frutasComidasEnRonda"
				
			}
		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en recibir ganador " + ex.getMessage());
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
		} catch (IOException ex) {
			Cliente.LOGGER.error("Error al enviar tecla " + ex.getMessage());
		}

	}
	
	/**
	 * Se queda esperando que el servidor le envie las salas
	 * @return
	 */
	public String recibirActualizacionDeSala() {
		try {
			while(true) {				
				this.message = (Message) new Gson().fromJson((String) entradaDatos.readObject(), Message.class);
				
				if (message.getType() == Param.REQUEST_ACTUALIZAR_SALAS) {
					return (String) message.getData();
				}
			}
		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en recibir actualizar sala" + ex.getMessage());
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
			this.salidaDatos.writeObject(new Message(Param.REQUEST_INGRESO_SALA, salaSeleccionada).toJson());
			Message retorno = (Message) new Gson().fromJson((String) this.entradaDatos.readObject(), Message.class);
			return (String) retorno.getData();
		} catch (Exception ex) {
			Cliente.LOGGER.error("Error en unirse a sala " + ex.getMessage());
		}
		return null;
	}
}
