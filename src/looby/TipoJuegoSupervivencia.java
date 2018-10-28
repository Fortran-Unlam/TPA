package looby;

public class TipoJuegoSupervivencia extends TipoDeJuegoDecorator {

	protected TipoJuego tipoJuego;
	
	public TipoJuegoSupervivencia(TipoJuego tipoJuego) {
		super(tipoJuego);
		this.tipoJuego = tipoJuego;
	}

	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos, int segundos) {
		System.out.println("supervivencia");
		if (tipoJuego.termina(cantidadJugadores, puntosMaximos, segundos)) {
			return true;
		}
		if (cantidadJugadores <= 1) {
			return true;
		}
		return false;
	}
	
	public TipoJuego getTipoJuego() {
		return this.tipoJuego;
	}
}
