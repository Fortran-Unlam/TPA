package looby;

import java.awt.EventQueue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import core.Jugador;
import core.JugadorBot;
import core.mapa.Juego;

public class Partida implements Serializable {

	private static final long serialVersionUID = 5614028727088648640L;
	private int id;
	private boolean partidaEnCurso = false;
	private List<Juego> rondasJugadas = new ArrayList<Juego>();
	private List<Jugador> jugadoresEnPartida = new ArrayList<Jugador>();
	private Juego rondaEnCurso;
	private TipoJuego tipoDeJuegoDeLaPartida;
	private int cantidadDeRondasAJugar;
	private int numeroRonda = 0;

	public Partida(int id, List<Usuario> usuariosActivosEnSala, int cantidadDeRondasDePartida, TipoJuego tipo) {
		this.id = id;
		for (Usuario usuario : usuariosActivosEnSala) {
			if (usuario instanceof UsuarioBot) {
				this.jugadoresEnPartida.add(new JugadorBot(usuario));
			} else {
				this.jugadoresEnPartida.add(new Jugador(usuario));				
			}
		}
		this.cantidadDeRondasAJugar = cantidadDeRondasDePartida;
		this.tipoDeJuegoDeLaPartida = tipo;
	}

	public void empezarPartida() {
		// TODO: ojo porque el juego va a comenzar asincronicamente y esto va a iterar
		// deberiamos decir que cuando termine el juego cree otro juego
		try {
			System.out.println("Ronda " + (numeroRonda + 1));
			this.partidaEnCurso = true;
			this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida);
			this.comienzoDeJuego();
			this.rondasJugadas.add(this.rondaEnCurso);
//			this.rondaEnCurso = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.partidaEnCurso = false;
	}

	public boolean comienzoDeJuego() {
		if (this.rondaEnCurso == null) {
			return false;
		} else if (!this.rondaEnCurso.puedeEmpezar()) {
			this.partidaEnCurso = false;
			this.rondaEnCurso = null;
			return false;
		}
		// TODO: OJO al cambiar esto, hay que avisar cuando termina
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				rondaEnCurso.start();
			}
		});
		return true;
	}

	public boolean getPartidaEnCurso() {
		return this.partidaEnCurso;
	}

	public void pararJuegoEnCurso() {
		this.partidaEnCurso = false;
		this.rondaEnCurso.stop();
		this.rondaEnCurso = null;
	}

	public Juego getRondaEnCurso() {
		return rondaEnCurso;
	}
}

