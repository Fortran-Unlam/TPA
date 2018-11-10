package looby;

import java.util.ArrayList;

import core.Jugador;

public abstract class TipoJuegoDecorator extends TipoJuego {

	private static final long serialVersionUID = 6947929207632670874L;

	public TipoJuegoDecorator(TipoJuego tipoJuego) {
		this.tipoJuego = new TipoJuego();
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		return tipoJuego.termina(jugadores, segundos);
	}

}
