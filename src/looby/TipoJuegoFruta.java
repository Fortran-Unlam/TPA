package looby;

import java.util.ArrayList;

import core.Jugador;

public class TipoJuegoFruta extends TipoJuegoDecorator {

	protected TipoJuego tipoJuego;

	public TipoJuegoFruta(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		System.out.println("fruta");
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
