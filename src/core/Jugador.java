package core;

import java.io.Serializable;

import core.entidad.Vibora;
import looby.Usuario;

public class Jugador implements Comparable<Jugador>, Serializable {

	private static final long serialVersionUID = -1963732901425813952L;
	private Vibora vibora;
	private String nombre;
	private int frutasComidasEnRonda;

	/**
	 * 
	 * @param vibora
	 * @param nombre
	 */
	public Jugador(final Vibora vibora, final String nombre) {
		this.vibora = vibora;
		this.nombre = nombre;
		this.frutasComidasEnRonda = 0;
	}

	/**
	 * Convierte el usuario en un jugador, todavia no tiene vibora
	 * 
	 * @param vibora
	 * @param nombre
	 */
	public Jugador(final Usuario usuario) {
		this.nombre = usuario.getUsername();
		this.frutasComidasEnRonda = 0;
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

	public void aumentarFrutasComidas() {
		this.frutasComidasEnRonda++;
	}

	public int getFrutasComidas() {
		return this.frutasComidasEnRonda;
	}

	@Override
	public int compareTo(Jugador otro) {
		return otro.frutasComidasEnRonda - this.frutasComidasEnRonda;
	}

	@Override
	public String toString() {
		return this.nombre + "            " + this.frutasComidasEnRonda;
	}
}
