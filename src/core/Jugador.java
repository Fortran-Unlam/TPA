package core;

import core.entidad.Vibora;
import looby.Usuario;

/**
 * @author Joni
 *
 */
public class Jugador implements Comparable<Jugador> {

	/**
	 * 
	 */
	private Vibora vibora;

	/**
	 * 
	 */
	private String nombre;
	/**
	 * Cantidad de frutas comidas en la ronda
	 */
	private int frutasComidas;

	/**
	 * 
	 * @param vibora
	 * @param nombre
	 */
	public Jugador(final Vibora vibora, final String nombre) {
		super();
		this.vibora = vibora;
		this.nombre = nombre;
		this.frutasComidas = 0;
	}

	/**
	 * Convierte el usuario en un jugador, todavia no tiene vibora
	 * @param vibora
	 * @param nombre
	 */
	public Jugador(final Usuario usuario) {
		super();
		this.nombre = usuario.getUsrName();
		
		this.frutasComidas = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vibora getVibora() {
		return vibora;
	}

	/**
	 * 
	 * @param vibora
	 */
	public void setVibora(final Vibora vibora) {
		this.vibora = vibora;
	}

	/**
	 * 
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * 
	 * @param nombre
	 */
	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 */
	public void aumentarFrutasComidas() {
		this.frutasComidas++;
	}

	/**
	 * 
	 * @return
	 */
	public int getFrutasComidas() {
		return this.frutasComidas;
	}

	@Override
	public int compareTo(Jugador otro) {
		return otro.frutasComidas - this.frutasComidas;
	}

	@Override
	public String toString() {
		return this.nombre + "            " + this.frutasComidas;
	}
}
