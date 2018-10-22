package core;

public abstract class Colisionador {

	/**
	 * Vibora colisiona con fruta
	 * 
	 * @param vibora Vibora que come fruta
	 * @param fruta  La fruta a comer
	 */
	public static void colisionar(Vibora vibora, Fruta fruta) {
		fruta.setFueComida();
		vibora.comer();
		vibora.marcarCrecimiento();
	}

	/**
	 * Colision entre viboras. Marca la muerte de quien sea cabeza
	 * 
	 * @param vibora
	 * @param cuerpoViboraChocada
	 */

	public static void colisionar(Vibora vibA, Vibora vibB) {
		CuerpoVibora headA = vibA.getHead();
		CuerpoVibora headB = vibB.getHead();

		// Choque de cabezas
		if (headA.getX() == headB.getX() && headA.getY() == headB.getY()) {
			vibA.matar();
			vibB.matar();
			return;
		}

		// Cabeza de A contra B
		for (CuerpoVibora cB : vibB.getCuerpos()) {
			if (headA.getX() == cB.getX() && headA.getY() == cB.getY()) {
				vibA.matar();
				return;
			}
		}

		// Cabeza de B contra A
		for (CuerpoVibora cA : vibA.getCuerpos()) {
			if (headB.getX() == cA.getX() && headB.getY() == cA.getY()) {
				vibB.matar();
				return;
			}
		}
	}
	
	
	public static void colisionar(Vibora vibora, Obstaculo obs) {
		vibora.matar();
	}

}
