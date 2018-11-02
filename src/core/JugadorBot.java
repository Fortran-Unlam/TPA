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
				Random random = new Random(System.currentTimeMillis());
				boolean cambiar = true;
				while (cambiar) {
					cambiar = false;
					Posicion sentido = Posicion.values()[random.nextInt(Posicion.values().length)];
					this.getVibora().setSentido(sentido);

					int x = this.getVibora().getX();
					int y = this.getVibora().getY();

					switch (sentido) {
					case ESTE:
						x++;
						break;
					case OESTE:
						x--;
						break;
					case NORTE:
						y++;
						break;
					case SUR:
						y--;
						break;
					}
					
					for (CuerpoVibora cuerpoVibora : this.getVibora().getCuerpos()) {
						if (x == cuerpoVibora.getX() && y == cuerpoVibora.getY()) {
							cambiar = true;
							break;
						}
					}
					
					if (x >= Param.MAPA_MAX_X || y >= Param.MAPA_MAX_Y || y <= 0
							|| x <= 0 || mapa.getObstaculo(x, y) != null) {
						cambiar = true;
					}
				}
			}
		}
	}
}
