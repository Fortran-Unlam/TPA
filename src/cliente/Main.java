package cliente;

import java.io.IOException;
import java.net.Socket;

import cliente.ventana.usuario.Login;
import config.Param;

public class Main {

	private static Socket socket;
	private static ConexionServidor conexionServidor;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		try {
			socket = new Socket(Param.HOST, Param.PUERTO);
			conexionServidor = new ConexionServidor(socket);

			Login login = new Login();
			login.setVisible(true);
			
		} catch (IOException ex) {
			System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
		}
	}

	public static ConexionServidor getConexionServidor() {
		return conexionServidor;
	}
	
	public static Socket getSocket() {
		return socket;
	}

}
