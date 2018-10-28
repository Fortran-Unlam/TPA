package looby;

public class TipoJuego {

	protected TipoJuego tipoJuego;
	protected int segundos;
	protected int frutasMaximas;

	public TipoJuego() {

	}

	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		System.out.println("tipo");
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
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
}
