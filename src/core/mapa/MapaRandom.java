package core.mapa;

import java.util.LinkedList;
import java.util.Random;

import config.Param;
import core.Coordenada;
import core.Muro;
import core.entidad.Fruta;
import core.entidad.Obstaculo;

public class MapaRandom extends Mapa {

	public MapaRandom() {
		super(Param.MAPA_WIDTH / Param.PIXEL_RESIZE, Param.MAPA_HEIGHT / Param.PIXEL_RESIZE);

		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			this.add(new Fruta(random.nextInt(Param.MAPA_WIDTH / Param.PIXEL_RESIZE), random.nextInt(Param.MAPA_HEIGHT / Param.PIXEL_RESIZE)));
		}

		// Agrego obstaculos
		for (int i = 0; i < 10; i++) {
			this.add(new Obstaculo(random.nextInt(Param.MAPA_WIDTH / Param.PIXEL_RESIZE), random.nextInt(Param.MAPA_HEIGHT / Param.PIXEL_RESIZE)));
		}

		// Agrego muro
		LinkedList<Obstaculo> piedras = new LinkedList<>();
		for (int i = 30; i <= 160; i++) {
			piedras.add(new Obstaculo(new Coordenada(i, 50)));
		}
		Muro pared = new Muro(piedras);
		this.add(pared);
	}

}
