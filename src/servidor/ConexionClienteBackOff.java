package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import looby.Sala;
import looby.Usuario;


public class ConexionClienteBackOff extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;
	private Usuario usuario;
	private Sala sala;

	public ConexionClienteBackOff(Socket socket, Socket socketOut) {
		this.socket = socket;

		try {
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			this.salidaDatos = new ObjectOutputStream(socketOut.getOutputStream());

		} catch (IOException ex) {
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;

		while (conectado) {

			try {
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());

			} catch (IOException ex) {
				System.out.println(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado del backOff.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					System.out.println("Error al cerrar los stream de entrada y salida del backoff:" + ex2.getMessage());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		Servidor.desconectarBackOff(this);

	}
}
