package core;

public class Colision {
	
	/**
	 * Vibora colisiona con fruta
	 * 
	 * @param vibora Vibora que come fruta
	 * @param fruta  La fruta a comar
	 */
	public Colision(Vibora vibora, Fruta fruta) {
		fruta.setMuere(true);
	}
	
	/**
	 * Colision entre viboras
	 * 
	 * @param vibora
	 * @param cuerpoViboraChocada
	 */
	public Colision(Vibora vibora, CuerpoVibora cuerpoViboraChocada) {
		
	}

}
