package core;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import cliente.input.GestorInput;
import config.Posicion;
import core.entidad.Vibora;
import core.mapa.Mapa;
import looby.Usuario;

public class Jugador implements Comparable<Jugador> {
	private Vibora vibora;
	private String nombre;
	private int frutasComidasEnRonda;
	private GestorInput teclado = new GestorInput();
	
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

	//Necesario para el Json.
	public Jugador(){}

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
		if (this.getVibora() != null) {
			this.getVibora().comer();
			this.getVibora().marcarCrecimiento();			
		}
	}
	@JsonIgnore
	public int getFrutasComidas() {
		return this.frutasComidasEnRonda;
	}

	public void setTecla(Posicion posicion) {
		this.teclado.setUltimaTecla(posicion);
	}

	public Posicion getTecla() {
		if (this.teclado != null) {
			return this.teclado.getUltimaTecla();			
		}
		return null;
	}
	
	public void determinarMovimiento(Mapa mapa) {
		if (this.getTecla() != null) {
			if (this.getVibora() != null) {
				this.getVibora().setSentido(this.getTecla());				
			}
		}
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
