package looby;

import java.util.LinkedList;
import java.util.List;

import core.Jugador;
import core.mapa.Juego;

public class Partida {
	private int id;
	private boolean enCurso = false;
	private LinkedList<Juego> juegos = new LinkedList<Juego>();
	private List<Jugador> jugadores = new LinkedList<Jugador>();

	public Partida(final int id) {
		this.id = id;
		this.enCurso = true;
	}

	public boolean crearJuego(TipoDeJuego tipoJuego) {
		return juegos.add(new Juego(this.jugadores, tipoJuego));
	}

	/**
	 * Comienza el juego si puede
	 * 
	 * @return False si no puede empezar el juego.
	 */
	public boolean start() {
		
		if (this.juegos.size() == 0) {
			return false;
		}
		
		Juego juego = this.juegos.getLast();
		
		if (!juego.puedeEmpezar()) {
			return false;
		}
		juego.start();
		this.enCurso = true;
		return true;
	}

	/**
	 * Para el juego que esta en curso
	 */
	public void stop() {
		this.enCurso = false;
		this.juegos.getLast().stop();
	}

	/**
	 * Devuelve true si hay un juego en curso
	 * 
	 * @return
	 */
	public boolean enCurso() {
		return this.enCurso;
	}

	/**
	 * Agregar un jugador al juego que está en curso
	 * 
	 * @param jugador
	 * 
	 * @return True si puede agregar
	 */
	public boolean add(final Jugador jugador) {
		// TODO: ver si no es necesario agregar el jugador en el juego en curso, por ahi
		// es solo necesario agregarlo a la partida
		if (this.juegos.getLast().add(jugador)) {
			this.jugadores.add(jugador);
			return true;
		}
		return false;
	}
}
