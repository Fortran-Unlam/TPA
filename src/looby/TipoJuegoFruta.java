package looby;

public class TipoJuegoFruta extends TipoDeJuegoDecorator {

	protected TipoJuego tipoJuego;

	public TipoJuegoFruta(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		System.out.println("fruta");
		if (tipoJuego.termina(cantidadJugadores, puntosMaximos, segundos)) {
			return true;
		}
		if (puntosMaximos >= this.frutasMaximas) {
			return true;
		}
		return false;
	}

	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
}
