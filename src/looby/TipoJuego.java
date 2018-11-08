package looby;

import java.io.Serializable;
import java.util.ArrayList;

import core.Jugador;

public class TipoJuego implements Serializable  {

	private static final long serialVersionUID = 1L;
	protected TipoJuego tipoJuego;
	protected int segundosMaximos;
	protected int frutasMaximas;

	public TipoJuego() {

	}

	public boolean termina(ArrayList<Jugador> jugadores, int segundos) {
		if (jugadores.size() <= 1) {
			return true;
		}
		return false;
	}

	public int getFrutasMaximas() {
		return frutasMaximas;
	}

	public void setFrutasMaximas(int frutasMaximas) {
		this.frutasMaximas = frutasMaximas;
	}

	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}

	public int getSegundos() {
		return segundosMaximos;
	}

	public void setSegundos(int segundos) {
		this.segundosMaximos = segundos;
	}
}
