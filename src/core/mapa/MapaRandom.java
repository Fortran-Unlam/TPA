package core.mapa;

import java.util.LinkedList;
import java.util.Random;

import config.Param;
import config.Posicion;
import core.Coordenada;
import core.Fruta;
import core.Muro;
import core.Obstaculo;
import core.Vibora;
import core.ViboraBot;

public class MapaRandom extends Mapa {

	private static final long serialVersionUID = 9176450957074794826L;

	public MapaRandom() {
		super(Param.MAPA_WIDTH / 5, Param.MAPA_HEIGHT / 5);
		this.setBounds(Param.VENTANA_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			this.add(new Fruta(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego obstaculos
		for (int i = 0; i < 10; i++) {
			this.add(new Obstaculo(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego muro
		LinkedList<Obstaculo> piedras = new LinkedList<>();
		for (int i = 30; i <= 160; i++) {
			piedras.add(new Obstaculo(new Coordenada(i, 50)));
		}
		Muro pared = new Muro(piedras);
		this.add(pared);

		Vibora vibora = new Vibora(new Coordenada(30, 20), 10, Posicion.ESTE);
		this.add(vibora);

		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 100));
		this.add(viboraBot);

		ViboraBot viboraBot2 = new ViboraBot(new Coordenada(100, 70));
		this.add(viboraBot2);

	}

}
