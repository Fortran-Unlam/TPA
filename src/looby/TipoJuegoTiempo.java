package looby;

public class TipoJuegoTiempo extends TipoDeJuegoDecorator {

	protected TipoJuego tipoJuego;
	
	public TipoJuegoTiempo(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		System.out.println("tiempo");
		if (tipoJuego.termina(cantidadJugadores, puntosMaximos, segundos)) {
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
