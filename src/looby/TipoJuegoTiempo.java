package looby;

import java.util.ArrayList;

import core.Jugador;

public class TipoJuegoTiempo extends TipoJuegoDecorator {

	protected TipoJuego tipoJuego;
	
	public TipoJuegoTiempo(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		System.out.println("tiempo");
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
