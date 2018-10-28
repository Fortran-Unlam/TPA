package looby;

public abstract class TipoDeJuegoDecorator extends TipoJuego {

	public TipoDeJuegoDecorator(TipoJuego tipoJuego) {
		this.tipoJuego = new TipoJuego();
	}

	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		return tipoJuego.termina(cantidadJugadores, puntosMaximos, segundos);
	}

}
