package core.mapa;

import java.util.Random;

import config.Param;
import core.Coordenada;
import core.entidad.Fruta;
import core.entidad.Obstaculo;

public class MapaTres extends Mapa {

	public MapaTres() {
		super(Param.MAPA_WIDTH / Param.PIXEL_RESIZE, Param.MAPA_HEIGHT / Param.PIXEL_RESIZE);

		Random random = new Random();
		//Dibujo ambas apredes.
		for (int i = 15; i < (Param.MAPA_HEIGHT / Param.PIXEL_RESIZE)-15; i++) {
			this.add(new Obstaculo((Param.MAPA_WIDTH / Param.PIXEL_RESIZE)-25,i));
			this.add(new Obstaculo(15,i));
		}
		//Dibujo la F.
		int posicion = ((Param.MAPA_WIDTH / Param.PIXEL_RESIZE) - ((Param.MAPA_WIDTH / Param.PIXEL_RESIZE)-15));
		int mitadPared = ((Param.MAPA_HEIGHT / Param.PIXEL_RESIZE)/2);
		for (int i = 15; i < 30; i++) {
			this.add(new Obstaculo(i,posicion));
		}
		for (int i = 15; i < 23; i++) {
			this.add(new Obstaculo(i,mitadPared));
		}
		
		//Dibujo la T.
		for (int i = ((Param.MAPA_WIDTH / Param.PIXEL_RESIZE)-35); i < ((Param.MAPA_WIDTH / Param.PIXEL_RESIZE)-14);i++){
			//Agrega obstaculos de arriba
			this.add(new Obstaculo(i,posicion));
		}
		
		for (int i = 0; i < Param.CANTIDAD_FRUTA_MINIMAS; i++) {
			int rand1 = random.nextInt(Param.MAPA_WIDTH / Param.PIXEL_RESIZE);
			int rand2 = random.nextInt(Param.MAPA_HEIGHT / Param.PIXEL_RESIZE);
			if (!this.getObstaculos().contains(new Coordenada(rand1, rand2)))
				this.add(new Fruta(rand1,rand2));
		}
	}
}
