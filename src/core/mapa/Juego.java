package core.mapa;

import java.io.Serializable;
import java.util.List;

import core.Jugador;
import core.JugadorBot;
import looby.TipoJuego;
import servidor.Servidor;

public class Juego implements Serializable {

	private static final long serialVersionUID = -3765719165739391662L;
	private List<Jugador> jugadoresEnJuego;
	private Mapa mapa;

	// private JList jListScore;
	private TipoJuego tipoJuego;
	private boolean juegoEnCurso = false;
	private int segundosTranscurridos = 0;

	public Juego(List<Jugador> jugadores, TipoJuego tipoJuego) {
		this.jugadoresEnJuego = jugadores; // Revisar si apuntar la referencia o poner los objetos en su lista
		this.mapa = new MapaUno();
		for (Jugador jugador : this.jugadoresEnJuego) {
			this.mapa.add(jugador, jugador instanceof JugadorBot);
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

			Servidor.actualizarMapa(this);
			Thread.sleep(1000);
			long tiempoInicial = System.currentTimeMillis();
			this.tipoJuego = new TipoJuego(); // REVISAR TIPO DE JUEGO
			while (this.juegoEnCurso && !this.tipoJuego.termina(this.mapa.getJugadores(), this.segundosTranscurridos)) {
				this.mapa.actualizar();
				Servidor.actualizarMapa(this);
				// this.jListScore.setModel(score.ScoreToModel());
				Thread.sleep(1000 / 50);
				this.segundosTranscurridos = (int) (System.currentTimeMillis() - tiempoInicial) / 1000;
			}
			this.juegoEnCurso = false;
			Servidor.actualizarMapa(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		this.juegoEnCurso = false;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public boolean terminado() {
		return this.juegoEnCurso == false;
	}

	public int getSegundosTranscurridos() {
		return this.segundosTranscurridos;
	}
}
