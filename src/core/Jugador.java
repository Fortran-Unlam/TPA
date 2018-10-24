package core;

import core.entidad.Vibora;

public class Jugador {

	Vibora vibora;
	String nombre;
	
	public Jugador(final Vibora vibora, final String nombre) {
		super();
		this.vibora = vibora;
		this.nombre = nombre;
	}

	public Vibora getVibora() {
		return vibora;
	}

	public void setVibora(final Vibora vibora) {
		this.vibora = vibora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	
}
