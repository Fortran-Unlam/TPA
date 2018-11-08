package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import cliente.ventana.VentanaCrearSala;
import cliente.ventana.VentanaJuego;
import cliente.ventana.VentanaSala;
import cliente.ventana.VentanaUnirSala;
import cliente.ventana.usuario.VentanaCrearUsuario;
import cliente.ventana.usuario.VentanaLoginUsuario;
import config.Param;

public class Cliente {

	private static Socket socketOut;
	private static Socket socketIn;
	private static Socket socketOutBackOff;
	private static Socket socketInBackOff;
	private static ConexionServidor conexionServidor;
	private static ConexionServidorBackOff conexionServidorBackOff;
	private static ArrayList<String> datosDeSalas;
	
	
	static VentanaLoginUsuario ventanaLoginUsuario;
	static VentanaCrearUsuario ventanaCrearUsuario;
	static VentanaJuego ventanaMenu;
	static VentanaUnirSala ventanaUnirSala;
	static VentanaCrearSala ventanaCrearSala;
	static VentanaSala ventanaSala;
	static VentanaJuego ventanaJuego;

	
	public static void main(String[] args) {
		new Cliente();
	}

	public Cliente() {
		try {
			socketOut = new Socket(Param.HOST, Param.PORT_1);
			socketIn = new Socket(Param.HOST, Param.PORT_2);

			conexionServidor = new ConexionServidor(socketOut, socketIn);

			socketOutBackOff = new Socket(Param.HOST, Param.PORT_3);
			socketInBackOff = new Socket(Param.HOST, Param.PORT_4);
			conexionServidorBackOff = new ConexionServidorBackOff(socketOutBackOff, socketInBackOff);

			conexionServidorBackOff.start();
			
			Cliente.ventanaLoginUsuario = new VentanaLoginUsuario();
			Cliente.ventanaLoginUsuario.setVisible(true);

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

	public static ConexionServidorBackOff getconexionServidorBackOff() {
		return conexionServidorBackOff;
	}
	
	public static void setDatosDeSalas(ArrayList<String> datosDeSalasNuevo){
		datosDeSalas = datosDeSalasNuevo;
	}
	
	public static ArrayList<String> getDatosDeSalas(){
		return datosDeSalas;
	}
	
}
