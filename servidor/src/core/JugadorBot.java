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
			int nuevoSentido = this.cambiarDireccion(mapa).ordinal();
			boolean cambiar = nuevoSentido != this.getVibora().getSentido().ordinal();
			if (this.chocara(mapa) || cambiar) {
				int intentos = 0;
				this.getVibora().setSentido(Posicion.values()[nuevoSentido]);
				while (this.chocara(mapa) && intentos <= Posicion.values().length) {
					if (numberRandom < 0.5) {
						nuevoSentido--;
					} else {
						nuevoSentido++;
					}
					intentos++;
					Posicion sentido = Posicion.values()[Math.abs(nuevoSentido) % Posicion.values().length];
					this.getVibora().setSentido(sentido);
				}
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

		if (x > Param.MAPA_MAX_X || y > Param.MAPA_MAX_Y || y < 0 || x < 0 || mapa.getObstaculo(new Coordenada(x, y)) != null) {
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
		if (mapa.getfrutas() == null || mapa.getfrutas().size() == 0) {
			return null;
		}
		Fruta frutaCercana = mapa.getfrutas().get(0);
		for (Fruta fruta : mapa.getfrutas()) {
			distanciaActual = this.getVibora().getCoordenada().distancia(fruta.getCoordenada());

			if (distancia == 0) {
				distancia = distanciaActual;
			}
			if (distanciaActual < distancia) {
				distancia = distanciaActual;
				frutaCercana = fruta;
			}
		}
		return frutaCercana;
	}

	public Posicion cambiarDireccion(Mapa mapa) {
		Fruta fruta = this.frutaMasCercana(mapa);

		int x = this.getVibora().getX();
		int y = this.getVibora().getY();

		if (fruta == null) {
			return Posicion.ESTE;
		}
		
		if (y == fruta.getY()) {
			if (x <= fruta.getX()) {
				if (this.getVibora().getSentido() == Posicion.OESTE) {
					return Posicion.NORTE;
				}
				return Posicion.ESTE;
			} else {
				if (this.getVibora().getSentido() == Posicion.ESTE) {
					return Posicion.SUR;
				}
				return Posicion.OESTE;
			}
		} else if (x == fruta.getX()) {
			if (y <= fruta.getY()) {
				if (this.getVibora().getSentido() == Posicion.SUR) {
					return Posicion.ESTE;
				}
				return Posicion.NORTE;
			} else {
				if (this.getVibora().getSentido() == Posicion.NORTE) {
					return Posicion.OESTE;
				}
				return Posicion.SUR;
			}
		} else {
			if (x <= fruta.getX()) {
				if (this.getVibora().getSentido() == Posicion.OESTE) {
					return Posicion.NORTE;
				}
				return Posicion.ESTE;
			} else {
				if (this.getVibora().getSentido() == Posicion.ESTE) {
					return Posicion.SUR;
				}
				return Posicion.OESTE;
			}
		}
	}
}
