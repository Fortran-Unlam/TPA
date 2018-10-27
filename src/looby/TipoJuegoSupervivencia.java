package looby;

public class TipoJuegoSupervivencia extends TipoDeJuegoDecorator {

	public TipoJuegoSupervivencia(TipoJuego tipoJuego) {
		super(tipoJuego);
	}

	@Override
	public boolean termina(int cantidadJugadores, int puntosMaximos) {
		if (!super.termina(cantidadJugadores, puntosMaximos)) {
			return false;
		}
		//TODO: logica para ver si termina
		return true;
	}
}
