package looby;

import java.util.ArrayList;

import core.Jugador;

public class TipoJuegoSupervivencia extends TipoJuegoDecorator {

	protected TipoJuego tipoJuego;
	
	public TipoJuegoSupervivencia(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		System.out.println("supervivencia");
		if (tipoJuego.termina(jugadores, segundos)) {
			return true;
		}
		if (jugadores.size() <= 1) {
			return true;
		}
		return false;
	}
	
	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
}
