package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import looby.Usuario;
import servidor.Message;

public class ConexionServidorBackOff {
	
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
		
}
