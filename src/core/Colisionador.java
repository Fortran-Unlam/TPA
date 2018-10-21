package core;

public abstract class Colisionador {

	/**
	 * Vibora colisiona con fruta
	 * 
	 * @param vibora Vibora que come fruta
	 * @param fruta  La fruta a comar
	 */
	public static void colisionar(Vibora vibora, Fruta fruta) {
		fruta.matar();
		vibora.comer();
		vibora.marcarCrecimiento();
	}

	/**
	 * Colision entre viboras. Marca la muerte de quien sea cabeza
	 * 
	 * @param vibora
	 * @param cuerpoViboraChocada
	 */
	public static void colisionar(Vibora viboraA, Vibora viboraB) {
		if (viboraA.equals(viboraB)) {
			viboraA.matar();
		}
		for (CuerpoVibora cuerpoVibora : viboraB.getCuerpos()) {	
			if(viboraA.getCabeza().equals(cuerpoVibora)) {
				viboraA.matar();
			}
		}
		
		for (CuerpoVibora cuerpoVibora : viboraA.getCuerpos()) {
			if(viboraB.getCabeza().equals(cuerpoVibora)) {
				viboraB.matar();
			}
		}
	}

}
