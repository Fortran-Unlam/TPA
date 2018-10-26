package pool;

import java.util.LinkedList;
import java.util.List;

import core.Jugador;
import core.entidad.Vibora;
import core.mapa.Ronda;

public class Partida {
	private int id;
	private boolean enCurso = false;
	LinkedList<Ronda> rondas = new LinkedList<Ronda>();
	private List<Jugador> jugadores = new LinkedList<Jugador>();

	public Partida(final int id) {
		this.id = id;
		this.enCurso = true;
	}

	public boolean agregarRonda() {
		return rondas.add(new Ronda());

	}

	public void start() {
		this.enCurso = true;
		this.rondas.getLast().start();
	}
	
	public void stop() {
		this.enCurso = false;
		this.rondas.getLast().stop();
	}
	
	public boolean enCurso() {
		return this.enCurso;
	}
	
	public void add(final Jugador jugador) {
		this.jugadores.add(jugador);
		this.rondas.getLast().add(jugador);
	}
	
	public Jugador crearJugador(final String nombre) throws Exception {
		
		Vibora vibora = this.rondas.getLast().crearVibora();

		if (vibora == null) {
			throw new Exception();
		}
		
		Jugador jugador = new Jugador(vibora, nombre);
		
		this.jugadores.add(jugador);
		
		return jugador;
	}
}
