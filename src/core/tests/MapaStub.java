package core.tests;

import core.Jugador;
import core.entidad.Vibora;
import core.mapa.Mapa;

public class MapaStub extends Mapa {

	public MapaStub(int x, int y) {
		super(x, y);
	}
	
	public boolean add(Vibora vibora) {
		Jugador jugador = new Jugador(vibora, "as");
		if (super.add(vibora)) {
			super.jugadores.add(jugador);
			return true;
		}
		return false;
	}

}
