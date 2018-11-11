package core.mapa;

import java.util.Random;

import config.Param;
import core.entidad.Fruta;
import core.entidad.Obstaculo;

public class MapaUno extends Mapa {

	public MapaUno() {
		super(Param.MAPA_WIDTH / 5, Param.MAPA_HEIGHT / 5);

		Random random = new Random();
		for (int i = 0; i < Param.CANTIDAD_FRUTA_MINIMAS; i++) {
			this.add(new Fruta(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego obstaculos
		for (int i = 0; i < 10; i++) {
			this.add(new Obstaculo(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}
	}
}
