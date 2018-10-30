package core.tests;

import core.Jugador;
import core.entidad.Vibora;
import core.mapa.Mapa;

public class MapaStub extends Mapa {

	private static int numeroJugador = 0;

	public MapaStub(int x, int y) {
		super(x, y);
	}

	public boolean add(Vibora vibora) {
		Jugador jugador = new Jugador(vibora, String.valueOf(numeroJugador));
		MapaStub.numeroJugador++;
		if (super.add(vibora)) {
			super.jugadores.add(jugador);
			return true;
		}
		return false;
	}

}
