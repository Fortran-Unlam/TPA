package looby;

import java.util.ArrayList;
import java.util.List;

import core.Jugador;
import core.mapa.Juego;

public class Partida {
	int id;
	private boolean PartidaEnCurso = false;
	private List<Juego> rondasJugadas = new ArrayList<Juego>();
	private List<Jugador> jugadoresEnPartida = new ArrayList<Jugador>();
	private Juego rondaEnCurso;
	private TipoJuego tipoDeJuegoDeLaPartida;
	private int cantidadDeRondasAJugar;

	public Partida(int idPartida, List<Usuario> usuariosActivosEnSala, int cantidadDeRondasDePartida, TipoJuego tipo) {
		this.id = idPartida;
		for (Usuario usr : usuariosActivosEnSala)
			this.jugadoresEnPartida.add(new Jugador(usr));
		this.cantidadDeRondasAJugar = cantidadDeRondasDePartida;
		this.tipoDeJuegoDeLaPartida = tipo;
	}

	public void empezarPartida() {
		for (int i = 0; i < this.cantidadDeRondasAJugar; i++) {
			try {
				this.PartidaEnCurso = true;
				this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida);
				this.comienzoDeJuego();
				this.rondasJugadas.add(this.rondaEnCurso);
				this.rondaEnCurso = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.PartidaEnCurso = false;
		}
	}

	public boolean comienzoDeJuego() {
		if (this.rondaEnCurso == null) {
			return false;
		} else if (!this.rondaEnCurso.puedeEmpezar()) {
			this.PartidaEnCurso = false;
			this.rondaEnCurso = null;
			return false;
		}
		this.rondaEnCurso.start();
		return true;
	}

	public boolean enCurso() {
		return this.PartidaEnCurso;
	}

	public void pararJuegoEnCurso() {
		this.PartidaEnCurso = false;
		this.rondaEnCurso.stop();
		this.rondaEnCurso = null;
	}
	
	/*
	 * public boolean agregarJugadoresAJuegoEnCurso() { // for (Jugador jugador :
	 * this.jugadoresEnPartida) { Jugador jugador1 = this.jugadoresEnPartida.get(0);
	 * Jugador jugador2 = this.jugadoresEnPartida.get(1);
	 * rondaEnCurso.add(jugador1); rondaEnCurso.add(jugador2);
	 * 
	 * if (!rondaEnCurso.add(jugador)) { //throw new
	 * Exception("No se pudo agregar un jugador en el mapa."); }
	 * 
	 * } return true; }
	 */
}
