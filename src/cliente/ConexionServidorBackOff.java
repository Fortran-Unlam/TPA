package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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

	public void avisarAlSvQueMandeActualizacionSalas(String parametro) {

		/*
		 * Le aviso al sv que hubo una actualizacion ac� el server empieza a tirotear a
		 * todos los clientes con los datos de las salas actualizadas, tengo que esperar
		 * que sea mi turno y me lleguen los datos de la sala, recien ahi puedo
		 * continuar.
		 */
		/*this.message.setType(parametro);
		try {
			this.salidaDatos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	public ObjectInputStream getEntradaDatos() {
		return entradaDatos;
	}

}
