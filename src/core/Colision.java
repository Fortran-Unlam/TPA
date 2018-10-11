package core;

public abstract class Colision {

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
	public static void colisionar(CuerpoVibora cuerpoViboraA, CuerpoVibora cuerpoViboraB) {
		if(cuerpoViboraA.isCabeza()) {
			cuerpoViboraA.getVibora().matar();
		}
		if (cuerpoViboraB.isCabeza()) {
			cuerpoViboraB.getVibora().matar();
		}
	}

}
