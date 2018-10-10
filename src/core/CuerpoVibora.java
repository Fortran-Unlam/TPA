package core;

import java.util.List;

public class CuerpoVibora extends Entidad {

	private Vibora vibora;
	
	public CuerpoVibora(Vibora vibora, Coordenada coordenada) {
		super(coordenada);
		this.vibora = vibora;
	}

	public CuerpoVibora(Vibora vibora, int x, int y) {
		super(x, y);
		this.vibora = vibora;
	}
	
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
