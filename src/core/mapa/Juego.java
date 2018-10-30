package core.mapa;

import java.io.Serializable;
import java.util.List;

import core.Jugador;
import looby.TipoJuego;

public class Juego  implements Serializable {

	private static final long serialVersionUID = -3765719165739391662L;
	private List<Jugador> jugadoresEnJuego;
	private Mapa mapa;
	// private JList jListScore;
	private TipoJuego tipoJuego;
	private boolean juegoEnCurso = false;

	public Juego(List<Jugador> jugadores, TipoJuego tipoJuego) {
		this.jugadoresEnJuego = jugadores; // Revisar si apuntar la referencia o poner los objetos en su lista
		this.mapa = new MapaUno();
		for (Jugador jugador : this.jugadoresEnJuego) {
			this.mapa.add(jugador);
		}
		// this.jListScore = new JList<Jugador>();
		this.tipoJuego = tipoJuego;
	}

	public boolean puedeEmpezar() {
		if (this.jugadoresEnJuego.size() > 1) {
			return true;
		}
		return false;
	}

	/*
	 * COMIENZA EL JUEGO (RONDA) REFRESCANDO EL MAPA CADA CIERTO TIEMPO
	 */
	public void start() {
		// Score score = new Score();
		// score.add(this.mapa.getJugadores());
		this.juegoEnCurso = true;
		try {
			Thread.sleep(1000);
			this.tipoJuego = new TipoJuego(); // REVISAR TIPO DE JUEGO
			while (this.juegoEnCurso && !this.tipoJuego.termina(this.mapa.jugadores.size(), 100, 10)) {
				this.mapa.actualizar();
				System.out.println("J U G A N D O");
				// this.jListScore.setModel(score.ScoreToModel());
				Thread.sleep(150);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.juegoEnCurso = false;
	}
}
