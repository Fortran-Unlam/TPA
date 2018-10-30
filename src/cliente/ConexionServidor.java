package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import PasswordUtils.HashSalt;
import config.Param;
import looby.Sala;
import looby.Usuario;
import servidor.Message;

public class ConexionServidor {
	private Socket socket;
	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;
	private Message message;

	public ConexionServidor(Socket socket) {
		this.socket = socket;
		try {
			System.out.println("creando ObjectOutputStream");
			this.salidaDatos = new ObjectOutputStream(this.socket.getOutputStream());
			System.out.println("creo ObjectOutputStream");
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de salida : " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		}

		try {
			System.out.println("creando ObjectInputStream");
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			System.out.println("creo ObjectintputStream");
		} catch (IOException ex) {
			System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
		}
	}

	public void loguear(String username, HashSalt hsPassword) {
		try {
			System.err.println("antes envio loguear");
			ArrayList<String> ret = new ArrayList<String>();
			ret.add(username);
			ret.add(hsPassword.toString());
			salidaDatos.writeObject(new Message(Param.REQUEST_LOGUEAR, ret));
			System.out.println("envio loguear");
		} catch (IOException ex) {
			System.err.println("Error al intentar enviar un mensaje: " + ex.getMessage());
		}
	}

	public Usuario recibirLogueo() throws IOException {

		while (true) {
			try {
				Message message = (Message) entradaDatos.readObject();

				switch (message.getType()) {
				case Param.REQUEST_LOGUEO_CORRECTO:
					return (Usuario) message.getData();
				case Param.REQUEST_LOGUEO_INCORRECTO:
					System.out.println("no loguee");
					return null;
				default:
					return null;
				}

			} catch (IOException ex) {
				throw ex;
			} catch (NullPointerException ex) {
				System.err.println("El socket no se creo correctamente. ");
				return null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pide las salas al servidor y espera a que este le responda
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	public List<Sala> getAllSalas() throws ClassNotFoundException {
		try {
			message = new Message(Param.REQUEST_GET_ALL_SALAS, "");
			ObjectOutputStream salidaDatos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("envio");
			salidaDatos.writeObject(message);

		} catch (IOException ex) {
			System.err.println("Error al crear el stream de entrada: " + ex.getMessage());
			return null;
		} catch (NullPointerException ex) {
			System.err.println("El socket no se creo correctamente. ");
			return null;
		}

		while (true) {
			try {
				message = (Message) entradaDatos.readObject();
				switch (message.getType()) {
				case Param.REQUEST_GET_ALL_SALAS:
					return (List<Sala>) message.getData();
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
		}
	}
}