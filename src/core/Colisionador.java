package core;

import java.io.Serializable;

import core.entidad.CuerpoVibora;
import core.entidad.Fruta;

public abstract class Colisionador implements Serializable {

	private static final long serialVersionUID = -4900826847817515391L;

	/**
	 * Vibora colisiona con fruta
	 * 
	 * @param jugador Vibora que come fruta
	 * @param fruta   La fruta a comer
	 */
	public static void colisionar(Jugador jugador, Fruta fruta) {
		fruta.setFueComida();
		jugador.getVibora().comer();
		jugador.getVibora().marcarCrecimiento();
	}

	/**
	 * Colision entre viboras. Marca la muerte de quien sea cabeza
	 * 
	 * @param vibora
	 * @param cuerpoViboraChocada
	 */

	public static void colisionar(Jugador jugadorA, Jugador jugadorB) {
		CuerpoVibora headA = jugadorA.getVibora().getHead();
		CuerpoVibora headB = jugadorB.getVibora().getHead();

		// Choque de cabezas
		if (headA.getX() == headB.getX() && headA.getY() == headB.getY()) {
			jugadorA.getVibora().matar();
			jugadorB.getVibora().matar();
			return;
		}

		// Cabeza de A contra B
		for (CuerpoVibora cB : jugadorB.getVibora().getCuerpos()) {
			if (headA.getX() == cB.getX() && headA.getY() == cB.getY()) {
				jugadorA.getVibora().matar();
				return;
			}
		}

		// Cabeza de B contra A
		for (CuerpoVibora cA : jugadorA.getVibora().getCuerpos()) {
			if (headB.getX() == cA.getX() && headB.getY() == cA.getY()) {
				jugadorB.getVibora().matar();
				return;
			}
		}
	}

	public static void colisionar(Jugador jugador, Obstaculo obs) {
		jugador.getVibora().matar();
	}

}
