package core.mapa;

import java.util.List;
import java.util.Random;

import javax.swing.JList;

import config.Param;
import config.Posicion;
import core.Coordenada;
import core.Jugador;
import core.Score;
import core.entidad.Vibora;
import core.entidad.ViboraBot;

public class Juego {

	private List<Jugador> jugadores;
	private Mapa mapa;
	private JList jListScore;
	
	public Juego(List<Jugador> jugadores, Mapa mapa, JList score) {
		this.jugadores = jugadores;
		this.mapa = mapa;
		this.jListScore = score;
	}

	/**
	 * Le da comienzo a la ronda actualizando el mapa cada cierto tiempo
	 */
	public void start() {
		// TODO: VALIDAR QUE HAYA DOS VIBORAS MINIMO PARA JUGAR
//		this.crearViboraBot();
//		this.crearViboraBot();
//		this.crearViboraBot();

		Score score = new Score();
		score.add(this.mapa.getJugadores());

		try {
			Thread.sleep(1000);

			while (true) {
				this.mapa.actualizar();

				this.jListScore.setModel(score.ScoreToModel());

				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	public void stop() {
//		this.run = false;
//	}

	/**
	 * Intenta n veces crear una vibora en el mapa y la agrega al mapa. Osea que
	 * cuando el mapa se actualiza, se actualiza la vibora
	 * 
	 * @return La vibora si se pudo crear sino null-
	 */
	public Vibora crearVibora() {
		Vibora vibora = null;
		Random random = new Random();

		for (int intento = 0; intento < 20; intento++) {
			vibora = new Vibora(new Coordenada(random.nextInt(Param.MAPA_MAX_X), random.nextInt(Param.MAPA_MAX_Y)), 10,
					Posicion.ESTE);
			if (mapa.add(vibora)){
				return vibora;
			}
		}
		return null;
	}

	/**
	 * Intenta n veces crear una vibora en el mapa y la agrega al mapa. Osea que
	 * cuando el mapa se actualiza, se actualiza la vibora
	 * 
	 * @return La vibora si se pudo crear sino null-
	 */
	public Jugador crearViboraBot() {
		Jugador vibora = null;
		Random random = new Random();

		for (int intento = 0; intento < 20; intento++) {
			vibora = new ViboraBot(new Coordenada(random.nextInt(Param.MAPA_MAX_X), random.nextInt(Param.MAPA_MAX_Y)));
			if (mapa.add(vibora)) {
				return vibora;
			}
		}
		return null;
	}
}
