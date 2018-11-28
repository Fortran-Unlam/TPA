package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.json.JsonObject;

public class ConexionServidorBackOff extends Thread {

	private ObjectOutputStream salidaDatos;
	private ObjectInputStream entradaDatos;

	private Socket socketIn;
	private Socket socketOut;

	public ConexionServidorBackOff(Socket socketOut, Socket socketIn) {
		this.socketOut = socketOut;
		this.socketIn = socketIn;
		try {
			this.salidaDatos = new ObjectOutputStream(this.socketOut.getOutputStream());
			this.entradaDatos = new ObjectInputStream(this.socketIn.getInputStream());

		} catch (Exception ex) {
			Cliente.LOGGER.error("No pudo crear la conexion backoff");
		}
	}


	/**
	 * Las respuestas del server las recibe la clase Sincronismo, la cual funcinoa como thread.
	 * No lo puse aca porque ponerlo aca necesitaba que las actualizaciones se hagan
	 * en todo momento, a pesar de que el usuario esta jugando.
	 * 
	 * @param paquete
	 */
	public void enviarAlServer(JsonObject paquete) {
		
		try {
			this.salidaDatos.writeObject(paquete.toString());
		} catch (IOException e) {
			Cliente.LOGGER.error("HUBO UNE ERROR EN EL ENVIO DE DATOS AL SERVIDOR MEDIANTE BACKOFF " + paquete.toString());
		}

	}

	public ObjectInputStream getEntradaDatos() {
		return entradaDatos;
	}


}
