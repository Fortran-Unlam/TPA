package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import config.Param;
import looby.Sala;
import looby.Usuario;

public class Servidor {

	private List<Sala> salasActivas = new ArrayList<>();
	private List<Usuario> usuariosActivos = new ArrayList<>();

	public boolean agregarASalasActivas(Sala sala) {
		return this.salasActivas.add(sala);
	}

	public boolean agregarAUsuariosActivos(Usuario usuario) {
		return this.usuariosActivos.add(usuario);
	}
	
	public static void main(String[] args) {

		ServerSocket servidor = null;
		Socket socket = null;

		try {
			servidor = new ServerSocket(Param.PUERTO, Param.MAXIMAS_CONEXIONES_SIMULTANEAS);
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
