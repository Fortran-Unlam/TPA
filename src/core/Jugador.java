package core;

import core.entidad.Vibora;

public class Jugador {

	Vibora vibora;
	String nombre;
	
	public Jugador(Vibora vibora, String nombre) {
		super();
		this.vibora = vibora;
		this.nombre = nombre;
	}

	public Vibora getVibora() {
		return vibora;
	}

	public void setVibora(Vibora vibora) {
		this.vibora = vibora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
