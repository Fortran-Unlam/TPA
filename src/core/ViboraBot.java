package core;

import java.util.Random;

import config.Posicion;

public class ViboraBot extends Vibora {

	private static final long serialVersionUID = -6266915663507093207L;
	private int ultimaPosicion = 2;

	public ViboraBot(Coordenada head) {
		super(head, 10);
	}

	/**
	 * Cambio de sentido un 35 por ciento de las veces. Mayormente va en el mismo
	 * sentido
	 */
	public void determinarMovimiento() {
		if (new Random().nextFloat() > 0.65) {
			this.setSentido(Posicion.values()[new Random().nextInt(Posicion.values().length)]);
		}
	}
}
