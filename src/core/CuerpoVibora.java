package core;

import java.util.List;

public class CuerpoVibora extends Entidad {

	private Vibora vibora;

	/**
	 * Constructor a partir de la vibora y la coordenada
	 * 
	 * @param vibora
	 * @param coordenada
	 */
	public CuerpoVibora(Vibora vibora, Coordenada coordenada) {
		super(coordenada);
		this.vibora = vibora;
	}

	/**
	 * Constructor a partir de la vibora y sus posiciones X,Y
	 * 
	 * @param vibora
	 * @param x
	 * @param y
	 */
	public CuerpoVibora(Vibora vibora, int x, int y) {
		super(x, y);
		this.vibora = vibora;
	}
	
	/**
	 * Devuelve la Vibora a la que pertenece
	 * 
	 * @return Vibora
	 */
	public Vibora getVibora() {
		return this.vibora;
	}
	
	/**
	 * Chequea si esta parte del cuerpo es la cabeza
	 * 
	 * @return True si es la cabeza
	 */
	public boolean isCabeza() {
		List<CuerpoVibora> cuerpos = this.vibora.getCuerpos();
		return cuerpos.get(cuerpos.size()).equals(this);
	}
}
