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

	private Message message;

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
			try {
				message = (Message) this.entradaDatos.readObject();
				
				if(message.getType().equals(Param.NOTICE_ACTUALIZAR_SALAS)){
					Main.setDatosDeSalas((ArrayList<String>) message.getData());
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void avisarAlServerActualizacionSalas(String parametro) {
		
		Message message = new Message(parametro, null);
		try {
			this.salidaDatos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
