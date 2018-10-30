package core.mapa;

import java.util.List;

import javax.swing.JList;

import core.Jugador;
import core.Score;
import looby.TipoJuego;
import looby.TipoJuegoSupervivencia;

public class Juego {

	private List<Jugador> jugadores;
	private Mapa mapa;
	private JList jListScore;
	private TipoJuego tipoJuego;
	private boolean run = false;

	public Juego(List<Jugador> jugadores, TipoJuego tipoJuego) {
		this.jugadores = jugadores;
		this.mapa = new MapaUno();

		for (Jugador jugador : jugadores) {
			this.mapa.add(jugador);
		}

		this.jListScore = new JList<Jugador>();
		this.tipoJuego = tipoJuego;
	}

	public boolean add(Jugador jugador) {
		System.out.println("NO DEBE LLEGAR");
		if (!this.mapa.add(jugador)) {
			System.out.println("No pudo agregar jugador");
			return false;
		}
		this.jugadores.add(jugador);
		return true;
	}

	/**
	 * Verifica si puede empezar el juego
	 * 
	 * @return True si puede empezar, false si no
	 */
	public boolean puedeEmpezar() {
		if (this.jugadores.size() > 1) {
			return true;
		}
		return false;
	}

	/**
	 * Le da comienzo a la ronda actualizando el mapa cada cierto tiempo
	 */
	public void start() {
		Score score = new Score();
		score.add(this.mapa.getJugadores());
		this.run = true;
		try {
			Thread.sleep(1000);
			this.tipoJuego = new TipoJuego();
			while (this.run && !this.tipoJuego.termina(this.mapa.jugadores.size(),100,10) ) {
				this.mapa.actualizar();
				System.out.println("jugando");
				this.jListScore.setModel(score.ScoreToModel());

				Thread.sleep(150);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.run = false;
	}
}
