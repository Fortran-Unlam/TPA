package looby;

import java.util.LinkedList;
import java.util.List;

import core.Jugador;
import core.mapa.Juego;

public class Partida {
	private int id;
	private boolean PartidaEnCurso = false;
	private LinkedList<Juego> rondasJugadas = new LinkedList<Juego>();
	private List<Jugador> jugadoresEnPartida = new LinkedList<Jugador>();
	private Juego rondaEnCurso;
	private int cantidadDeRondasAJugar;
	private TipoJuego tipoDeJuegoDeLaPartida;

	public Partida(final int id, final Jugador jugador) throws Exception {
		this.id = id;
		// Esto se puede borrar pero lo dejo para probar por ahora
		TipoJuego tipoJuego = new TipoJuego();
		tipoJuego = new TipoJuegoFruta(tipoJuego);
		tipoJuego = new TipoJuegoTiempo(tipoJuego);
		tipoJuego = new TipoJuegoSupervivencia(tipoJuego);

		tipoJuego.setFrutasMaximas(2);
		System.out.println(tipoJuego.termina(2, 3, 3));

		this.crearJuego(new TipoJuego());
		if (!this.add(jugador)) {
			throw new Exception("No se pudo agregar un jugador en el mapa");
		}
		this.jugadoresEnPartida.add(jugador);
	}

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
				this.crearJuego(this.tipoDeJuegoDeLaPartida);
				this.agregarJugadoresAJuegoEnCurso();
				this.comienzoDeJuego();
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
	public void crearJuego(TipoJuego tipoJuego) {
		this.rondaEnCurso = new Juego(this.jugadoresEnPartida, tipoJuego);
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
		this.PartidaEnCurso = true;
		return true;
	}

	/**
	 * Para el juego que esta en curso
	 */
	public void pararJuegoEnCurso() {
		this.PartidaEnCurso = false;
		this.rondasJugadas.getLast().stop();
	}

	/**
	 * Devuelve true si hay un juego en curso
	 * 
	 * @return
	 */
	public boolean enCurso() {
		return this.PartidaEnCurso;
	}

	public boolean add(final Jugador jugador) { //BORRARRRRRRRRRRRRRRRRRR
		// TODO: ver si no es necesario agregar el jugador en el juego en curso, por ahi
		// es solo necesario agregarlo a la partida
		if (this.rondasJugadas.getLast().add(jugador)) {
			this.jugadoresEnPartida.add(jugador);
			return true;
		}
		return false;
	}

	// AGREGA JUGADORES A LA RONDA EN CURSO
	public boolean agregarJugadoresAJuegoEnCurso(){
		for (Jugador jugador : this.jugadoresEnPartida) {
			rondaEnCurso.add(jugador);
			/*if (!rondaEnCurso.add(jugador)) {
				//throw new Exception("No se pudo agregar un jugador en el mapa.");
			}*/
		}
		return true;
	}
}
