package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import config.Param;
import looby.Sala;
import looby.Usuario;

public class ConexionClienteBackOff extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;
	private Usuario usuario;

	public ConexionClienteBackOff(Socket socket, Socket socketOut) {
		this.socket = socket;

		try {
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			this.salidaDatos = new ObjectOutputStream(socketOut.getOutputStream());

		} catch (IOException ex) {
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;

		while (conectado) {

			try {
				String entrada = (String) this.entradaDatos.readObject();
				JsonReader jsonReader = Json.createReader(new StringReader(entrada));
				JsonObject entradaJson = jsonReader.readObject();
				jsonReader.close();

				String tipoDeMensaje = entradaJson.getString("type");


				// Guardo el usuario dentro de mi conexiónBackOff
				if (tipoDeMensaje.equals(Param.REQUEST_LOGUEO_BACKOFF_CLIENTE)) {
					for (Usuario u : Servidor.getUsuariosActivos()) {
						if (u.getUsername().equals(entradaJson.getString("username"))) {
							this.usuario = u;
						}
					}

					if (this.usuario != null) {
						String respuestaLogueoOk = Json.createObjectBuilder()
								.add("type", Param.NOTICE_LOGUEO_BACKOFF_OK).build().toString();
						this.salidaDatos.writeObject(respuestaLogueoOk);
					}
				}

				// Este if es el que actualiza la ventana sala
				if (tipoDeMensaje.equals(Param.NOTICE_CREACION_SALA) || tipoDeMensaje.equals(Param.NOTICE_UNION_SALA)
						|| tipoDeMensaje.equals(Param.REQUEST_INGRESO_VENTANA_UNIR_SALA)
						|| tipoDeMensaje.equals(Param.NOTICE_SALIR_SALA)) {
					enviarActualizacionSalasALosClientes();
				}

				/*
				 * Aguante boca. Cada vez que se crea una nueva sala Empiezo a gatillar, como
				 * policia a trabajador despedido, a todos los usuarios de esa sala, para
				 * avisarle los cambios que hizo el admin o bien si un usuario entró o se fue de
				 * la sala
				 */

				if (tipoDeMensaje.equals(Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR)
						|| tipoDeMensaje.equals(Param.NOTICE_REFRESCAR_USUARIOS_PARTICULAR)) {
					enviarActualizacionAClientesDeUnaSalaParticular(entradaJson);
				}

			} catch (IOException ex) {
				System.out.println(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado del backOff.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					System.out
							.println("Error al cerrar los stream de entrada y salida del backoff:" + ex2.getMessage());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		Servidor.desconectarBackOff(this);
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void enviarActualizacionSalasALosClientes() {
		// Pido los datos de las salas
		JsonArray datosDeSalas = Servidor.getAllSalas();
		JsonObjectBuilder paqueteActualizacionDeSalas = Json.createObjectBuilder();
		paqueteActualizacionDeSalas.add("type", Param.NOTICE_ACTUALIZAR_SALAS).add("datosDeSalas", datosDeSalas);

		for (ConexionClienteBackOff conexion : Servidor.getConexionesClientesBackOff()) {
			// Recorro todo el ArrayList de Conexiones de clientes de backoff y le envio las
			// salas actualizadas
			try {
				conexion.salidaDatos.writeObject(paqueteActualizacionDeSalas.build().toString());
			} catch (IOException e) {
				System.err.println(
						"Hubo un error, del lado del servidor, en la actualizacion de las salas a los clientes");
				e.printStackTrace();
			}
		}
	}

	public void enviarActualizacionAClientesDeUnaSalaParticular(JsonObject entradaJson) {

		String tipoDeMensaje = entradaJson.getString("type");

		Sala salaARefrescar = Servidor.getSalaPorNombre(entradaJson.getString("sala"));

		JsonObject paqueteAEnviar;
		if (tipoDeMensaje.equals(Param.NOTICE_REFRESCAR_USUARIOS_PARTICULAR)) {


			JsonArrayBuilder usernamesConectadosALaSala = Json.createArrayBuilder();

			for (Usuario u : salaARefrescar.getUsuariosActivos()) {
				usernamesConectadosALaSala.add(u.getUsername());
			}

			paqueteAEnviar = Json.createObjectBuilder().add("type", Param.NOTICE_REFRESCAR_USUARIOS_PARTICULAR)
					.add("usuarios", usernamesConectadosALaSala.build())
					.add("admin", salaARefrescar.getAdministrador().getUsername()).build();
		} else {

			String tipoJugabilidad = "Aun no se ha determinado";

			if (entradaJson.getBoolean("fruta")) {
				tipoJugabilidad = "frutas";
			}
			if (entradaJson.getBoolean("supervivencia"))
				tipoJugabilidad += " supervivencia";

			if (entradaJson.getBoolean("tiempo"))
				tipoJugabilidad += " tiempo";

			paqueteAEnviar = Json.createObjectBuilder().add("type", Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR)
					.add("tipoJugabilidad", tipoJugabilidad).add("tipoMapa", entradaJson.getString("mapa"))
					.add("bots", entradaJson.getString("bots")).build();
		}

		for (ConexionClienteBackOff c : Servidor.getConexionesClientesBackOff()) {
			try {
				if (usuarioEstaEnLaSala(c.getUsuario(), salaARefrescar)) {
					c.salidaDatos.writeObject(paqueteAEnviar.toString());
				}
			} catch (IOException e) {
				System.err.println("Fallo la escritura de datos de actualizar parametros sala");
				e.printStackTrace();
			}
		}

	}

	/*
	 * Dada una sala y un usuario, busca si ese usuario está en esa sala.
	 */
	private boolean usuarioEstaEnLaSala(Usuario user, Sala salaARefrescar) {
		for (Usuario usuarioActual : salaARefrescar.getUsuariosActivos()) {
			if (usuarioActual.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

}
