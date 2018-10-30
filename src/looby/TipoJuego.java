package looby;

import java.io.Serializable;

public class TipoJuego implements Serializable  {

	private static final long serialVersionUID = 1L;
	protected TipoJuego tipoJuego;
	protected int segundosMaximos;
	protected int frutasMaximas;

	public TipoJuego() {

	}

	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		if (cantidadJugadores <= 1) {
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
