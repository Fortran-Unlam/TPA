package core.mapa;

import java.util.LinkedList;
import java.util.Random;

import config.Param;
import core.Coordenada;
import core.Fruta;
import core.Muro;
import core.Obstaculo;

public class MapaRandom extends Mapa {

	private static final long serialVersionUID = 9176450957074794826L;

	public MapaRandom() {
		super(Param.MAPA_WIDTH / Param.PIXEL_RESIZE, Param.MAPA_HEIGHT / Param.PIXEL_RESIZE);
		this.setBounds(Param.VENTANA_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

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
