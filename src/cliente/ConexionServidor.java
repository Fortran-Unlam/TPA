package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;

import PasswordUtils.HashSalt;
import config.Param;
import looby.Sala;
import looby.Usuario;

public class ConexionServidor {
	private Socket socket;
	private DataOutputStream salidaDatos;
	private DataInputStream entradaDatos;

	public ConexionServidor(Socket socket) {
		this.socket = socket;
		try {
			this.salidaDatos = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de salida : " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		}
	}

	public void loguear(String username, HashSalt hsPassword) {
		try {
			JsonObject jsonObject = Json.createObjectBuilder().add("request", Param.REQUEST_LOGUEAR)
					.add("username", username).add("hsPassword", hsPassword).build();
			System.err.println("envio loguear");
			salidaDatos.writeUTF(jsonObject.toString());
		} catch (IOException ex) {
			System.err.println("Error al intentar enviar un mensaje: " + ex.getMessage());
		}
	}

	public Usuario recibirLogueo() {
		try {
			entradaDatos = new DataInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		}

		while (true) {
			try {
				JsonObject mensajeRecibido = Json.createReader(new StringReader(entradaDatos.readUTF())).readObject();
				switch (mensajeRecibido.get("request").toString()) {
				case Param.REQUEST_LOGUEO_CORRECTO:
					return new Usuario(mensajeRecibido);
				case Param.REQUEST_LOGUEO_INCORRECTO:
					System.out.println("no loguee");
					return null;
				default:
					return null;
				}

			} catch (IOException ex) {
				System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
				return null;
			} catch (NullPointerException ex) {
				System.err.println("El socket no se creo correctamente. ");
				return null;
			}
		}
	}

	/**
	 * Pide las salas al servidor y espera a que este le responda
	 * @return
	 */
	public List<Sala> getAllSalas() {
		try {
			entradaDatos = new DataInputStream(socket.getInputStream());
			JsonObject jsonObject = Json.createObjectBuilder().add("request", Param.REQUEST_GET_ALL_SALAS).build();
			salidaDatos.writeUTF(jsonObject.toString());
			
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		}

		while (true) {
			try {
				JsonObject mensajeRecibido = Json.createReader(new StringReader(entradaDatos.readUTF())).readObject();
				switch (mensajeRecibido.get("request").toString()) {
				case Param.REQUEST_GET_ALL_SALAS:
					List<Sala> salas = new ArrayList<Sala>();
					System.out.println(mensajeRecibido.get("salas").toString());
					if (!mensajeRecibido.get("salas").toString().equals("[]")) {
						JsonArrayBuilder arrayBuilder = (JsonArrayBuilder) mensajeRecibido.get("salas");
						for (JsonValue jsonValue : arrayBuilder.build()) {
							salas.add(new Sala((JsonObject)jsonValue));
						}
					}
					return salas;
				default:
					return null;
				}

			} catch (IOException ex) {
				System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
				return null;
			} catch (NullPointerException ex) {
				System.err.println("El socket no se creo correctamente. ");
				return null;
			}
		}
	}
}