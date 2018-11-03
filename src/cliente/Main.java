package cliente;

import java.io.IOException;
import java.net.Socket;

import cliente.ventana.usuario.Login;
import config.Param;

public class Main {

	private static Socket socketOut;
	private static Socket socketIn;
	private static ConexionServidor conexionServidor;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		try {
			socketOut = new Socket(Param.HOST, Param.PORT_1);
			socketIn = new Socket(Param.HOST, Param.PORT_2);
			conexionServidor = new ConexionServidor(socketOut, socketIn);

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
		return socketOut;
	}

}
