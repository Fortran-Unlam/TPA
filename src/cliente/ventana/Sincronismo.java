package cliente.ventana;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

import cliente.Cliente;
import config.Param;
import servidor.Message;

public class Sincronismo extends Thread{
	
	private static VentanaUnirSala ventanaUnirSala;
	private static VentanaSala ventanaSala;
	private static JsonArray datosDeSalasDisponibles;
	
	public Sincronismo() {
		this.start();
	}
	
	
	public void run() {
		boolean conectado = true;

		while (conectado) {
			try {
				String stringEntrada =(String) Cliente.getconexionServidorBackOff().getEntradaDatos().readObject();
				
				JsonReader jsonReader = Json.createReader(new StringReader(stringEntrada));
				JsonObject entradaJson = jsonReader.readObject();
				jsonReader.close();

				String tipoMensaje = entradaJson.getString("type");
				
				if(tipoMensaje.equals(Param.NOTICE_ACTUALIZAR_SALAS)) {
					datosDeSalasDisponibles = entradaJson.getJsonArray("datosDeSalas");
				}
				
				if(ventanaUnirSala != null) {
					ventanaUnirSala.refrescarListaDeSalas(datosDeSalasDisponibles);
				}
				
				
				if(tipoMensaje.equals(Param.NOTICE_ACTUALIZAR_SALAS)) {
					datosDeSalasDisponibles = entradaJson.getJsonArray("datosDeSalas");
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
