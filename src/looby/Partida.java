package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import core.Jugador;
import core.mapa.Juego;

public class Partida {
	private int id;
	private boolean PartidaEnCurso = false;
	private List<Juego> rondasJugadas = new ArrayList<Juego>();
	private List<Jugador> jugadoresEnPartida = new ArrayList<Jugador>();
	private Juego rondaEnCurso;
	private int cantidadDeRondasAJugar;
	private TipoJuego tipoDeJuegoDeLaPartida;

	public Partida(int idPartida, List<Usuario> usuariosActivos, int cantidadDeRondasDePartida, TipoJuego tipo) {
		this.id = idPartida;
		for (Usuario usr : usuariosActivos)
			this.jugadoresEnPartida.add(new Jugador(usr));
		this.cantidadDeRondasAJugar = cantidadDeRondasDePartida;
		this.tipoDeJuegoDeLaPartida = tipo;
	}

	public void empezarPartida() {
		for (int i = 0; i < this.cantidadDeRondasAJugar; i++) {
			try {
				this.PartidaEnCurso = true;
				this.crearJuego();
				// this.agregarJugadoresAJuegoEnCurso();
				this.comienzoDeJuego();
				this.rondasJugadas.add(this.rondaEnCurso);
				this.rondaEnCurso = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Crea un juego de acuerdo al tipo dado y lo agrega al listado de juegos
	 * 
	 * @param tipoJuego
	 * @return Si puede agregarlo
	 */
	public void crearJuego() {
		this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida);
	}

	/**
	 * Comienza el juego si puede
	 * 
	 * @return False si no puede empezar el juego.
	 */
	public boolean comienzoDeJuego() {
		if (this.rondaEnCurso == null) {
			return false;
		}
		if (!this.rondaEnCurso.puedeEmpezar()) {
			return false;
		}
		this.rondaEnCurso.start();
		return true;
	}

	/**
	 * Para el juego que esta en curso
	 */
	public void pararJuegoEnCurso() {
		this.PartidaEnCurso = false;
		this.rondaEnCurso.stop();
	}

	/**
	 * Devuelve true si hay un juego en curso
	 * 
	 * @return
	 */
	public boolean enCurso() {
		return this.PartidaEnCurso;
	}

	// AGREGA JUGADORES A LA RONDA EN CURSO
	public boolean agregarJugadoresAJuegoEnCurso() {
		// for (Jugador jugador : this.jugadoresEnPartida) {
		Jugador jugador1 = this.jugadoresEnPartida.get(0);
		Jugador jugador2 = this.jugadoresEnPartida.get(1);
		rondaEnCurso.add(jugador1);
		rondaEnCurso.add(jugador2);
		/*
		 * if (!rondaEnCurso.add(jugador)) { //throw new
		 * Exception("No se pudo agregar un jugador en el mapa."); }
		 */
		// }
		return true;
	}
}
