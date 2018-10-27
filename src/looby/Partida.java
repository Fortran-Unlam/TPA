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

	public void start() {
		this.enCurso = true;
		this.juegos.getLast().start();
	}
	
	public void stop() {
		this.enCurso = false;
		this.juegos.getLast().stop();
	}
	
	public boolean enCurso() {
		return this.enCurso;
	}
	
	public boolean add(final Jugador jugador) {
		if (this.juegos.getLast().add(jugador)) {
			this.jugadores.add(jugador);
			return true;
		}
		return false;
	}
//	
//	public Jugador crearJugador(final String nombre) throws Exception {
//		Jugador jugador = new Jugador(new Vibora(new Coordenada(3,2), 4, Posicion.NORTE), nombre);
//		this.jugadores.add(jugador);
//		
//		return jugador;
//	}
}
