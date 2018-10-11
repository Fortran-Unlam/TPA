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
	public static void colisionar(CuerpoVibora cuerpoViboraA, CuerpoVibora cuerpoViboraB) {
		//si chocan las cabezas, no importa la direción: por ahora mueren las dos.
		if(cuerpoViboraA.isCabeza() && cuerpoViboraB.isCabeza()) {
			cuerpoViboraA.getVibora().matar();
			cuerpoViboraB.getVibora().matar();
		}else if(cuerpoViboraA.isCabeza() && !cuerpoViboraB.isCabeza()) 	//si choca cabeza de A con cuerpo de B
			cuerpoViboraA.getVibora().matar();
		else	//si choca cabeza de B con cuerpo de A
			cuerpoViboraB.getVibora().matar();
	}

}
