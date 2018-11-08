package cliente.ventana;

import java.io.IOException;
import java.util.ArrayList;

import cliente.Cliente;
import config.Param;
import servidor.Message;

public class Sincronismo extends Thread{
	
	private static VentanaUnirSala ventanaUnirSala;
	private static VentanaSala ventanaSala;
	private static ArrayList<String> datosDeSalasDisponibles;
	private Message message;
	
	public Sincronismo() {
		this.start();
	}
	
	
	public void run() {
		boolean conectado = true;

		while (conectado) {
			try {
				message = (Message) Cliente.getconexionServidorBackOff().getEntradaDatos().readObject();
				
				if(message.getType().equals(Param.NOTICE_ACTUALIZAR_SALAS)) {
					this.datosDeSalasDisponibles = (ArrayList<String>) message.getData();
				}
				
				if(ventanaUnirSala != null) {
					ventanaUnirSala.refrescarListaDeSalas(datosDeSalasDisponibles);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setVentanaSala(VentanaSala ventanaSala) {
		Sincronismo.ventanaSala = ventanaSala;
	}
	
	public static void setVentanaUnirSala(VentanaUnirSala ventanaUnirSala) {
		Sincronismo.ventanaUnirSala = ventanaUnirSala;
	}


}
