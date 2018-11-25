package looby;

import java.util.ArrayList;

import core.Jugador;

public class TipoJuegoTiempo extends TipoJuegoDecorator {

	private static final long serialVersionUID = -8815423268230065610L;
	protected TipoJuego tipoJuego;
	
	public TipoJuegoTiempo(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		if (tipoJuego.termina(jugadores, segundos)) {
			return true;
		}
		if (segundos >= this.segundosMaximos) {
			return true;
		}
		return true;
	}
	
	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
}
