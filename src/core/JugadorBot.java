package core;

import java.util.Random;

import config.Param;
import config.Posicion;
import core.entidad.CuerpoVibora;
import core.entidad.Vibora;
import core.mapa.Mapa;
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
	public void determinarMovimiento(Mapa mapa) {
		if (new Random().nextFloat() > 0.65) {
			if (this.getVibora() != null) {
				Posicion sentido = Posicion.values()[new Random().nextInt(Posicion.values().length)];
				this.getVibora().setSentido(sentido);
				int x = 0;
				int y = 0;
				switch (sentido) {
				case ESTE:
					x = this.getVibora().getX() + 1;
					y = this.getVibora().getY();
					break;
				case OESTE:
					x = this.getVibora().getX() - 1;
					y = this.getVibora().getY();
					break;
				case NORTE:
					x = this.getVibora().getX();
					y = this.getVibora().getY() + 1;
					break;
				case SUR:
					x = this.getVibora().getX();
					y = this.getVibora().getY() - 1;
					break;
				}
				boolean cambiar = false;
				for (CuerpoVibora cuerpoVibora : this.getVibora().getCuerpos()) {
					if (x == cuerpoVibora.getX() && y == cuerpoVibora.getY()) {
						cambiar = true;
					}
				}
				if (mapa.getObstaculo(x, y) != null || x > Param.MAPA_MAX_X || y > Param.MAPA_MAX_Y || y < 0 || x < 0) {
					cambiar = true;
				}
				if (cambiar) {
					sentido = Posicion.values()[new Random().nextInt(Posicion.values().length)];
					this.getVibora().setSentido(sentido);					
				}
			}
		}
	}
}
