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

	@Override
	public void run() {
		boolean conectado = true;

		while (conectado) {
			
		}
	}

	
	public void avisarAlServerActualizacionSalas(String parametro) {
		
		
		try {
			/*Le aviso al sv que hubo una actualizacion
			 * acá el server empieza a tirotear a todos los clientes con los
			 * datos de las salas actualizadas, tengo que esperar que sea mi
			 * turno y me lleguen los datos de la sala, recien ahi puedo
			 * continuar.
			 */
			this.message.setType(parametro);
			this.salidaDatos.writeObject(message);

			this.message = (Message) this.entradaDatos.readObject();
			
			if(message.getType().equals(Param.NOTICE_ACTUALIZAR_SALAS)){
				Cliente.setDatosDeSalas((ArrayList<String>) message.getData());
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
