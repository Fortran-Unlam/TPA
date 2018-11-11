package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import config.Param;
import looby.Usuario;
import servidor.Message;

public class ConexionServidorBackOff extends Thread {

	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;

	private Message message = new Message(null, null);

	private Socket socketIn;
	private Socket socketOut;

	public ConexionServidorBackOff(Socket socketOut, Socket socketIn) {
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
	 * Le aviso al sv que hubo una actualizacion acá el server empieza a tirotear a
	 * todos los clientes con los datos de las salas actualizadas, tengo que esperar
	 * que sea mi turno y me lleguen los datos de la sala, recien ahi puedo
	 * continuar.
	 * 
	 * @param paquete
	 */
	public void avisarAlSvQueHagaActualizaciones(JsonObject paquete) {

		System.out.println(paquete.toString());
		try {
			this.salidaDatos.writeObject(paquete.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ObjectInputStream getEntradaDatos() {
		return entradaDatos;
	}


}
