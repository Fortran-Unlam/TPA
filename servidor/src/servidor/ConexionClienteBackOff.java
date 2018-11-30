package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.Socket;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import config.Param;
import looby.Sala;
import looby.Usuario;

public class ConexionClienteBackOff extends Thread {

	private Socket socket;
	private DataInputStream entradaDatos;
	private DataOutputStream salidaDatos;
	private Usuario usuario;

	public ConexionClienteBackOff(Socket socket, Socket socketOut) {
		this.socket = socket;

		try {
			this.entradaDatos = new DataInputStream(socket.getInputStream());
			this.salidaDatos = new DataOutputStream(socketOut.getOutputStream());

		} catch (IOException ex) {
			Servidor.LOGGER.error("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;

		while (conectado) {

			try {
				String entrada = (String) this.entradaDatos.readUTF();
				JsonReader jsonReader = Json.createReader(new StringReader(entrada));
				JsonObject entradaJson = jsonReader.readObject();
				jsonReader.close();

				String tipoDeMensaje = entradaJson.getString("type");

				if (tipoDeMensaje.equals(Param.REQUEST_LOGUEO_BACKOFF_CLIENTE)) {
					for (Usuario u : Servidor.getUsuariosActivos()) {
						if (u.getUsername().equals(entradaJson.getString("username"))) {
							this.usuario = u;
						}
					}

					if (this.usuario != null) {
						String respuestaLogueoOk = Json.createObjectBuilder()
								.add("type", Param.NOTICE_LOGUEO_BACKOFF_OK).build().toString();
						this.salidaDatos.writeUTF(respuestaLogueoOk);
					}
				}

				// A todas las ventanasUnirSala
				if (tipoDeMensaje.equals(Param.NOTICE_CREACION_SALA) || tipoDeMensaje.equals(Param.NOTICE_UNION_SALA)
						|| tipoDeMensaje.equals(Param.NOTICE_ENTRAR_A_VER_SALAS)
						|| tipoDeMensaje.equals(Param.NOTICE_SALIR_SALA)) {
					enviarActualizacionSalasALosClientes();
				}

				// A una sala particular
				if (tipoDeMensaje.equals(Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR)
						|| tipoDeMensaje.equals(Param.NOTICE_SALIR_SALA)
						|| tipoDeMensaje.equals(Param.NOTICE_UNION_SALA)) {
					enviarActualizacionAClientesDeUnaSalaParticular(entradaJson);
				}

				/*
				 * Si recibo un mensaje de que se empezo un juego, informo a todos los clientes
				 * o usuarios de su sala.
				 */
				if (tipoDeMensaje.equals(Param.NOTICE_EMPEZAR_JUEGO)) {
					enviarEmpezarJuegoAClientesDeUnaSalaParticular(entradaJson);
				}

			} catch (IOException ex) {
				Servidor.LOGGER.error(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado del backOff.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					Servidor.LOGGER
							.error("Error al cerrar los stream de entrada y salida del backoff:" + ex2.getMessage());
				}
			}
		}
		Servidor.desconectarBackOff(this);
	}

	/**
	 * Metodo que pretende avisarle a todos los usuarios de una sala que el juego ya
	 * empezo. Menos al admin que es el que va a ejecutar el juego
	 * 
	 * @param entradaJson
	 */
	private void enviarEmpezarJuegoAClientesDeUnaSalaParticular(JsonObject entradaJson) {
		Sala salaARefrescar = Servidor.getSalaPorNombre(entradaJson.getString("sala"));
		JsonObject paqueteAEnviar;
		paqueteAEnviar = Json.createObjectBuilder().add("type", Param.NOTICE_EMPEZA_JUEGO_CLIENTE).build();

		for (ConexionClienteBackOff c : Servidor.getConexionesClientesBackOff()) {
			try {
				if (usuarioEstaEnLaSala(c.getUsuario(), salaARefrescar)
						&& c.getUsuario() != salaARefrescar.getAdministrador()) {
					c.salidaDatos.writeUTF(paqueteAEnviar.toString());
				}
			} catch (IOException e) {
				Servidor.LOGGER.error("Fallo la escritura de datos de actualizar parametros sala");
			}
		}
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * Envia a todos los clientes las salas
	 */
	public void enviarActualizacionSalasALosClientes() {
		JsonArray datosDeSalas = Servidor.getAllSalas();
		JsonObjectBuilder paqueteActualizacionDeSalas = Json.createObjectBuilder();
		paqueteActualizacionDeSalas.add("type", Param.NOTICE_ACTUALIZAR_SALAS_DISPONIBLES).add("datosDeSalas",
				datosDeSalas);

		for (ConexionClienteBackOff conexion : Servidor.getConexionesClientesBackOff()) {
			try {
				conexion.salidaDatos.writeUTF(paqueteActualizacionDeSalas.build().toString());
			} catch (IOException e) {
				Servidor.LOGGER
						.error("Hubo un error, del lado del servidor, en la actualizacion de las salas a los clientes");
			}
		}
	}

	public void enviarActualizacionAClientesDeUnaSalaParticular(JsonObject entradaJson) {

		String tipoDeMensaje = entradaJson.getString("type");

		Sala salaARefrescar = Servidor.getSalaPorNombre(entradaJson.getString("sala"));

		JsonObject paqueteAEnviar;
		if (salaARefrescar != null) {

			// Si alguien se unio o salio de una sala actualizo los usuarios de esa sala
			if (tipoDeMensaje.equals(Param.NOTICE_UNION_SALA) || tipoDeMensaje.equals(Param.NOTICE_SALIR_SALA)) {

				JsonArrayBuilder usernamesConectadosALaSala = Json.createArrayBuilder();

				for (Usuario u : salaARefrescar.getUsuariosActivos()) {
					usernamesConectadosALaSala.add(u.getUsername());
				}

				paqueteAEnviar = Json.createObjectBuilder().add("type", Param.NOTICE_REFRESCAR_USUARIOS_SALA_PARTICULAR)
						.add("usuarios", usernamesConectadosALaSala.build())
						.add("admin", salaARefrescar.getAdministrador().getUsername()).build();
			} else {
				paqueteAEnviar = armarPaqueteParamSala(entradaJson);
			}

			for (ConexionClienteBackOff c : Servidor.getConexionesClientesBackOff()) {
				try {
					if (usuarioEstaEnLaSala(c.getUsuario(), salaARefrescar)) {
						c.salidaDatos.writeUTF(paqueteAEnviar.toString());
					}
				} catch (IOException e) {
					Servidor.LOGGER.error("Fallo la escritura de datos de actualizar parametros sala");
				}
			}
			
			//Le pido al admin que envie un refresh de los parametros de la sala porque se unio un user nuevo
			if(tipoDeMensaje.equals(Param.NOTICE_UNION_SALA)) {
				for(ConexionClienteBackOff c: Servidor.getConexionesClientesBackOff()) {
					
					if(c.getUsuario().equals(salaARefrescar.getAdministrador())) {
						paqueteAEnviar = 
								Json.createObjectBuilder().add("type", Param.NOTICE_ADMIN_DAME_PARAM_SALA).build();
						try {
							c.salidaDatos.writeUTF(paqueteAEnviar.toString());
						} catch (IOException e) {
							e.printStackTrace();
						} 
						break;
					}
				}
			}
		}
	}

	/*
	 * Dada una sala y un usuario, busca si ese usuario est� en esa sala.
	 */
	private boolean usuarioEstaEnLaSala(Usuario user, Sala sala) {
		for (Usuario usuarioActual : sala.getUsuariosActivos()) {
			if (user != null && usuarioActual.getId() == user.getId())
				return true;
		}
		return false;
	}

	void escribirSalida(JsonObject dato) {
		try {
			this.salidaDatos.writeUTF(dato.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JsonObject armarPaqueteParamSala(JsonObject entradaJson) {
		return Json.createObjectBuilder().add("type", Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR)
				.add("fruta", entradaJson.getBoolean("fruta"))
				.add("cantidadDeFrutas", entradaJson.getString("cantidadDeFrutas"))
				.add("tiempo", entradaJson.getBoolean("tiempo")).add("cantTiempo", entradaJson.getString("cantTiempo"))
				.add("tipoMapa", entradaJson.getString("mapa")).add("rondas", entradaJson.getString("rondas"))
				.add("bots", entradaJson.getString("bots")).build();
	}

}
