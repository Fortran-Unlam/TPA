package core;

import java.util.Random;

import config.Posicion;

public class ViboraBot extends Vibora {

	private static final long serialVersionUID = -6266915663507093207L;
	private int ultimaPosicion = 3;

	public ViboraBot(Coordenada head) {
		super(head, 10);
	}

	/**
	 * Cambio de sentido un 35 por ciento de las veces. Mayormente va en el mismo
	 * sentido
	 */
	public void determinarMovimiento() {
		int num = new Random().nextInt(Posicion.values().length);
		this.ultimaPosicion = (int) Math.round((0.35 * num) + (0.65 * this.ultimaPosicion));
		this.setSentido(Posicion.values()[this.ultimaPosicion]);
	}
}
