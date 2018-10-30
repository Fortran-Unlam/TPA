package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import config.Param;
import looby.ManejadorSala;
import looby.Sala;
import looby.Usuario;

public class Servidor {

	private static List<Sala> salasActivas = new ArrayList<>();
	public static List<Usuario> usuariosActivos = new ArrayList<>();
	public static ManejadorSala manejadorSala = new ManejadorSala();
	
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

	public boolean agregarASalasActivas(Sala sala) {
		return Servidor.salasActivas.add(sala);
	}

	public boolean agregarAUsuariosActivos(Usuario usuario) {
		return Servidor.usuariosActivos.add(usuario);
	}
	
	public static List<Sala> getAllSalas() {
		return Servidor.salasActivas;
	}
	
	public static String requestgetAllSalas() {
		JsonObjectBuilder json =  Json.createObjectBuilder().add("request", Param.REQUEST_GET_ALL_SALAS);
		JsonArrayBuilder salas = Json.createArrayBuilder();
		for (Sala sala : salasActivas) {
			salas.add(sala.jsonify());
		}
		json.add("salas", salas);
		return json.build().toString();
		
	}
	
}
