package looby;

import java.util.ArrayList;

import core.Jugador;

public class TipoJuegoFruta extends TipoJuegoDecorator {

	private static final long serialVersionUID = 6833548333737491753L;
	protected TipoJuego tipoJuego;

	public TipoJuegoFruta(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		if (tipoJuego.termina(jugadores, segundos)) {
			return true;
		}
		jugadores.sort(null);
		if (jugadores.size() > 0) {
			
			if (jugadores.get(0).getFrutasComidas() >= this.frutasMaximas) {
				return true;
			}
		}
		return false;
	}

	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
}
