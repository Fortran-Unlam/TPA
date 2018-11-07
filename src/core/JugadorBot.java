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
		Random random = new Random(System.nanoTime());

		if (this.getVibora() != null) {
			Float numberRandom = random.nextFloat();
			
			if (this.chocara(mapa) || numberRandom < 0.2) {
				int nuevoSentido = this.getVibora().getSentido().ordinal();
				int intentos = 0;
				do {
					if (numberRandom < 0.5) {
						nuevoSentido--;
					} else {
						nuevoSentido++;						
					}
					intentos++;
					Posicion sentido = Posicion.values()[Math.abs(nuevoSentido) % Posicion.values().length];
					this.getVibora().setSentido(sentido);
				} while (this.chocara(mapa) && intentos <= Posicion.values().length);
			}
		}
	}

	private boolean chocara(Mapa mapa) {
		int x = this.getVibora().getX();
		int y = this.getVibora().getY();

		switch (this.getVibora().getSentido()) {
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

		if (x > Param.MAPA_MAX_X || y > Param.MAPA_MAX_Y || y < 0 || x < 0 || mapa.getObstaculo(x, y) != null) {
			System.out.println("choca con mapa u obs");
			return true;
		}

		if (x == this.getVibora().getX() && y == this.getVibora().getY()) {
			System.out.println("choca con su cabeza");
			return true;
		}
		
		for (CuerpoVibora cuerpoVibora : this.getVibora().getCuerpos()) {
			if (x == cuerpoVibora.getX() && y == cuerpoVibora.getY()) {
				System.out.println("choca con su cuerpo");
				return true;
			}
		}

		return false;
	}
}
