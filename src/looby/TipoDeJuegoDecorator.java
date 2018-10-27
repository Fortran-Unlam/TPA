package looby;

public abstract class TipoDeJuegoDecorator implements TipoJuego {
	private TipoJuego tipoJuego;
	
	public TipoDeJuegoDecorator(TipoJuego tipoJuego) {
		this.tipoJuego = tipoJuego;
	}
	
	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
	
	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos) {
		return false;
	}
}
