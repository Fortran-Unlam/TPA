package core;

import java.awt.Color;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;

import cliente.input.GestorInput;
import config.Posicion;
import core.entidad.Vibora;
import core.mapa.Mapa;
import looby.Usuario;

public class Jugador implements Comparable<Jugador> {

	private Vibora vibora;
	private Color color;
	private String nombre;
	private int frutasComidasEnRonda;
	//private int frutasComidasEnPartida;
	private int puntosEnPartida;
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
		this.puntosEnPartida = 0;
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
		this.puntosEnPartida = 0;
		Random rand = new Random();
		this.color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}

	public Color getColor() {
		return color;
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
		this.puntosEnPartida++;
		if (this.getVibora() != null) {
			this.getVibora().comer();
			this.getVibora().marcarCrecimiento();			
		}
	}

	public int getFrutasComidas() {
		return this.frutasComidasEnRonda;
	}
	
	public int getPuntosEnPartida() {
		return this.puntosEnPartida;
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
	
	public void sumarPuntosSobrevivirRonda() {
		this.puntosEnPartida += 20;
	}
	
	public void resetEstadisticasRonda() {
		this.frutasComidasEnRonda = 0;
	}

	@Override
	public int compareTo(Jugador otro) {
		return otro.frutasComidasEnRonda - this.frutasComidasEnRonda;
	}

	@Override
	public String toString() {
		return this.nombre + "            " + this.frutasComidasEnRonda;
	}
	
	public JsonObject toJson() {
		return Json.createObjectBuilder()
				.add("vibora", this.vibora.toJson())
				.add("nombre", this.nombre)
				.add("color_red", this.color.getRed())
				.add("color_green", this.color.getGreen())
				.add("color_blue", this.color.getBlue())
				.add("frutasComidasEnRonda", this.frutasComidasEnRonda)
				.build();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + frutasComidasEnRonda;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((teclado == null) ? 0 : teclado.hashCode());
		result = prime * result + ((vibora == null) ? 0 : vibora.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		if (frutasComidasEnRonda != other.frutasComidasEnRonda)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (teclado == null) {
			if (other.teclado != null)
				return false;
		} else if (!teclado.equals(other.teclado))
			return false;
		if (vibora == null) {
			if (other.vibora != null)
				return false;
		} else if (!vibora.equals(other.vibora))
			return false;
		return true;
	}
}
