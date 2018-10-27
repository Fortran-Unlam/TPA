package core;

import core.entidad.Vibora;

/**
 * @author Joni
 *
 */
public class Jugador {

	private Vibora vibora;
	private String nombre;
	/**
	 *  Cantidad de frutas comidas en la ronda
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
	
}
