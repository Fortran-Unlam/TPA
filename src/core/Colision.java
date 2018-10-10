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
	 * Colision entre viboras
	 * 
	 * @param vibora
	 * @param cuerpoViboraChocada
	 */
	public static void colisionar(Vibora vibora, CuerpoVibora cuerpoVibora) {
		
		if (vibora.getCabeza().getCoordenada().equals(cuerpoVibora.getCoordenada())) {

			if (!vibora.getCabeza().equals(cuerpoVibora)) {
			
				vibora.matar();
				
				if (cuerpoVibora.isCabeza()) {
					cuerpoVibora.getVibora().matar();
				}
			}
		}
	}

}
