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

			while (true) {
				System.out.println("Servidor a la espera de conexiones.");
				socket = servidor.accept();
				// Muestra de informacion por pantalla y por archivo.
				System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
				ConexionCliente cc = new ConexionCliente(socket);
				
				cc.start();

			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Cierro todos los sockets abiertos tras ocurrir un error.
			try {
				socket.close();
				servidor.close();
			} catch (IOException ex) {
				// Muestra de errores por pantalla y por archivo.
				System.out.println(ex.getMessage());
			}
		}
	}
}
