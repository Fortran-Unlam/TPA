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


	public void enviarAlServer(JsonObject paquete) {

		
		/*Las respuestas del server las recibe la clase Sincronismo, la cual funcinoa como thread.
		 * No lo puse acá porque ponerlo acá porque necesitaba que las actualizaciones se hagan
		 * en todo momento, a pesar de que el usuario esté jugando.
		 */
		
		try {
			this.salidaDatos.writeObject(paquete.toString());
		} catch (IOException e) {
			System.err.println("HUBO UNE ERROR EN EL ENVIO DE DATOS AL SERVIDOR MEDIANTE BACKOFF");
			e.printStackTrace();
		}

	}

	public ObjectInputStream getEntradaDatos() {
		return entradaDatos;
	}


}
