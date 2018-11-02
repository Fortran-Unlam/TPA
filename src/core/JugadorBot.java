package core;

import java.util.Random;

import config.Posicion;
import core.entidad.Vibora;
import looby.Usuario;

public class JugadorBot extends Jugador {

	private static final long serialVersionUID = 6951778505924532094L;

	public JugadorBot(Vibora vibora, String nombre) {
		super(vibora, nombre);
	}

	public JugadorBot(Usuario usuario) {
		super(usuario);
	}

	/**
	 * Cambio de sentido un 35 por ciento de las veces. Mayormente va en el mismo
	 * sentido
	 */
	@Override
	public void determinarMovimiento() {
		if (new Random().nextFloat() > 0.65) {
			if (this.getVibora() != null) {
				this.getVibora().setSentido(Posicion.values()[new Random().nextInt(Posicion.values().length)]);
			}
		}
	}

}
