package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import config.Param;

public class Servidor {

	public static void main(String[] args) {

		ServerSocket servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerSocket(Param.PUERTO, Param.MAXIMASCONEXIONESIMULTANEAS);
			System.out.println("Corriendo en " + Param.PUERTO);

			while (true) {
				socket = servidor.accept();
				
				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostAddress() + " conectado.");
				
				ConexionCliente cc = new ConexionCliente(socket);
				
				cc.start();

			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				socket.close();
				servidor.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
