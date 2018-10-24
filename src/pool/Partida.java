package pool;

import java.util.LinkedList;
import java.util.List;

import core.Jugador;
import core.entidad.Vibora;
import core.mapa.Ronda;

public class Partida {
	private int idPartida;
	private boolean partidaEnCurso = false;
	LinkedList<Ronda> rondas = new LinkedList<Ronda>();
	private List<Jugador> jugadores = new LinkedList<Jugador>();

	public Partida(int idPartida) {
		this.idPartida = idPartida;
		this.partidaEnCurso = true;
	}

	public boolean agregarRonda() {
		return rondas.add(new Ronda());
	}

	public void start() {
		this.rondas.getLast().start();
	}
	
	public void stop() {
		this.rondas.getLast().stop();
	}
	
	public void add(Jugador jugador) {
		this.jugadores.add(jugador);
		this.rondas.getLast().add(jugador);
	}
	
	public Jugador crearJugador(String nombre) throws Exception {
		
		Vibora vibora = this.rondas.getLast().crearVibora();

		if (vibora == null) {
			throw new Exception();
		}
		
		Jugador jugador = new Jugador(vibora, nombre);
		
		this.jugadores.add(jugador);
		
		return jugador;
	}
}
