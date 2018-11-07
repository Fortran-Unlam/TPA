package core;

import java.util.Random;

import config.Param;
import config.Posicion;
import core.entidad.CuerpoVibora;
import core.entidad.Fruta;
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

			if (this.chocara(mapa) || numberRandom < 0.3) {
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
			return true;
		}

		if (x == this.getVibora().getX() && y == this.getVibora().getY()) {
			return true;
		}

		for (CuerpoVibora cuerpoVibora : this.getVibora().getCuerpos()) {
			if (x == cuerpoVibora.getX() && y == cuerpoVibora.getY()) {
				return true;
			}
		}

		return false;
	}

	public Fruta frutaMasCercana(Mapa mapa) {
		float distancia = 0;
		float distanciaActual;
		Fruta frutaCercana = null;
		for (Fruta fruta : mapa.getfrutas()) {
			distanciaActual = this.getVibora().getCoordenada().distancia(fruta.getCoordenada());
			if (distancia != 0 && distanciaActual < distancia) {
				distancia = distanciaActual;
				frutaCercana = fruta;
			}
		}
		return frutaCercana;
	}

	public Posicion nuevaDireccion(Mapa mapa) {
		Fruta fruta = this.frutaMasCercana(mapa);
		// TODO:la posicion en la que voy es igual a una posicion de la fruta mas
		// cercana la dejo que vaya por ese camino
		return Posicion.ESTE;
	}
}
