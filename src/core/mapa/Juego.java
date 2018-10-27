package core.mapa;

import java.util.List;

import javax.swing.JList;

import core.Jugador;
import core.Score;
import looby.TipoDeJuego;

public class Juego {

	private List<Jugador> jugadores;
	private Mapa mapa;
	private JList jListScore;
	private TipoDeJuego tipoJuego;
	private boolean run = false;

	public Juego(List<Jugador> jugadores, TipoDeJuego tipoJuego) {
		this.jugadores = jugadores;
		this.mapa = new MapaUno();

		for (Jugador jugador : jugadores) {
			this.mapa.add(jugador);
		}

		this.jListScore = new JList<Jugador>();
		this.tipoJuego = tipoJuego;
	}

	public boolean add(Jugador jugador) {
		if (!this.mapa.add(jugador)) {
			System.out.println("no pudo agregar jugador");
			return false;
		}
		this.jugadores.add(jugador);
		return true;
	}

	/**
	 * Le da comienzo a la ronda actualizando el mapa cada cierto tiempo
	 */
	public void start() {
		// TODO: VALIDAR QUE HAYA DOS VIBORAS MINIMO PARA JUGAR

		Score score = new Score();
		score.add(this.mapa.getJugadores());
		this.run = true;
		try {
			Thread.sleep(1000);

			while (this.run) {
				this.mapa.actualizar();

				this.jListScore.setModel(score.ScoreToModel());

				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.run = false;
	}

	/**
	 * Intenta n veces crear una vibora en el mapa y la agrega al mapa. Osea que
	 * cuando el mapa se actualiza, se actualiza la vibora
	 * 
	 * @return La vibora si se pudo crear sino null-
	 */
//	public Jugador crearViboraBot() {
//		Jugador vibora = null;
//		Random random = new Random();
//
//		for (int intento = 0; intento < 20; intento++) {
//			vibora = new ViboraBot(new Coordenada(random.nextInt(Param.MAPA_MAX_X), random.nextInt(Param.MAPA_MAX_Y)));
//			if (mapa.add(vibora)) {
//				return vibora;
//			}
//		}
//		return null;
//	}
}
