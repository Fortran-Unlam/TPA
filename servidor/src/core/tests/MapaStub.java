package core.tests;

import core.Jugador;
import core.entidad.Vibora;
import core.mapa.Mapa;

public class MapaStub extends Mapa {

	private static final long serialVersionUID = 1241592794734543707L;
	private static int numeroJugador = 0;

	public MapaStub(int x, int y) {
		super(x, y);
	}

	public boolean add(Vibora vibora) {
		Jugador jugador = new Jugador(vibora, String.valueOf(numeroJugador));
		MapaStub.numeroJugador++;
		if (super.add(vibora)) {
			super.getJugadores().add(jugador);
			return true;
		}
		return false;
	}

}
