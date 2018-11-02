package core.mapa;

import java.util.Random;

import config.Param;
import core.entidad.Fruta;
import core.entidad.Obstaculo;

public class MapaUno extends Mapa {

	private static final long serialVersionUID = 3707232195274752719L;

	public MapaUno() {
		super(Param.MAPA_WIDTH / 5, Param.MAPA_HEIGHT / 5);
//		this.setBounds(Param.VENTANA_WIDTH - Param.MAPA_WIDTH, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			this.add(new Fruta(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

		// Agrego obstaculos
		for (int i = 0; i < 10; i++) {
			this.add(new Obstaculo(random.nextInt(Param.MAPA_WIDTH / 5), random.nextInt(Param.MAPA_HEIGHT / 5)));
		}

//		Vibora vibora = new Vibora(new Coordenada(30, 20), 10, Posicion.ESTE);
//		this.add(vibora);
//
//		ViboraBot viboraBot = new ViboraBot(new Coordenada(50, 100));
//		this.add(viboraBot);
//
//		ViboraBot viboraBot2 = new ViboraBot(new Coordenada(100, 70));
//		this.add(viboraBot2);
	}
}
