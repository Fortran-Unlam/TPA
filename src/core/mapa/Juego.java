package core.mapa;

import java.io.Serializable;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

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
	private long currentTimeMillis;

	public Juego(List<Jugador> jugadores, TipoJuego tipoJuego, Mapa mapa) {
		this.jugadoresEnJuego = jugadores; // Revisar si apuntar la referencia o poner los objetos en su lista
		this.mapa = mapa;
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
		boolean puedeActualizar = true;
		this.juegoEnCurso = true;
		try {

			Servidor.actualizarJuego(this);
			Thread.sleep(1000);
			long tiempoInicial = System.currentTimeMillis();
			this.tipoJuego = new TipoJuego(); // REVISAR TIPO DE JUEGO
			while (puedeActualizar && this.juegoEnCurso && !this.tipoJuego.termina(this.mapa.getJugadores(), this.segundosTranscurridos)) {
				this.currentTimeMillis = System.currentTimeMillis();
				this.mapa.actualizar();
				
				puedeActualizar = Servidor.actualizarJuego(this);
				
				//Reflejo 09/11 CUIDADO CON ESTO!!
				/* En realidad no es la mejor forma de sincronizar usar un Thread.sleep
				 * Cuando el sleep es mas alto hay menos "problemas" de sincronizacion
				 * con 100 lo probe y anda fluido y responde a las teclas instantaneamente
				 * antes estaba en 1000/40 = 25, cuanto mas bajo hay mas posibilidad de que haya
				 * "problemas" entre tanto thread, socket bla bla bla
				 * Se deberia solucionar con buffer, semaforos y todo ese humo xd.
				 * Mover entre 50 ~ 100.
				 * 
				 */
				Thread.sleep(100);
				this.segundosTranscurridos = (int) (System.currentTimeMillis() - tiempoInicial) / 1000;
			}
			this.juegoEnCurso = false;
			Servidor.actualizarJuego(this);
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
	
	public JsonObject toJson() {
		return Json.createObjectBuilder()
				.add("mapa", this.mapa.toJson())
				.add("terminado", this.terminado())
				.add("tiempoTranscurrido", this.segundosTranscurridos)
				.add("currentTimeMillis", this.currentTimeMillis)
				.build();
	}
}
