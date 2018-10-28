package cliente;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;

import javax.json.Json;
import javax.json.JsonObject;

import cliente.ventana.VentanaMenu;
import config.Param;
import looby.Usuario;

public class ConexionServidor {
	private Socket socket;
	private DataOutputStream salidaDatos;
	private Usuario usuario;

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

	public void loguear(String username, String password) {
		try {
			JsonObject jsonObject = Json.createObjectBuilder().add("request", Param.REQUEST_LOGUEAR).add("username", username)
					.add("password", password).build();
			System.err.println("envio loguear");
			salidaDatos.writeUTF(jsonObject.toString());
		} catch (IOException ex) {
			System.err.println("Error al intentar enviar un mensaje: " + ex.getMessage());
		}
	}

	public void recibirMensajesServidor() {
		DataInputStream entradaDatos = null;
		try {
			entradaDatos = new DataInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		}

		boolean conectado = true;
		while (conectado) {
			try {
				JsonObject mensajeRecibido = Json.createReader(new StringReader(entradaDatos.readUTF())).readObject();
				switch (mensajeRecibido.get("request").toString()) {
				case Param.REQUEST_LOGUEO_CORRECTO:
					System.out.println("loguee");
					usuario = new Usuario(mensajeRecibido);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								VentanaMenu frame = new VentanaMenu();
								frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					break;
				case Param.REQUEST_LOGUEO_INCORRECTO:

					System.out.println("no loguee");
					break;
				}

			} catch (IOException ex) {
				System.err.println("Error al leer del stream de entrada: " + ex.getMessage());
				conectado = false;
			} catch (NullPointerException ex) {
				System.err.println("El socket no se creo correctamente. ");
				conectado = false;
			}
		}
	}
}