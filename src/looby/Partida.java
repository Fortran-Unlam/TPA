package looby;

import java.io.Serializable;
import java.util.ArrayList;

import core.Jugador;
import core.JugadorBot;
import core.mapa.Juego;
import core.mapa.Mapa;

public class Partida implements Serializable {

	private static final long serialVersionUID = 5614028727088648640L;
	private int id;
	private boolean partidaEnCurso = false;
	private ArrayList<Juego> rondasJugadas = new ArrayList<Juego>();
	private ArrayList<Jugador> jugadoresEnPartida = new ArrayList<Jugador>();
	private Juego rondaEnCurso;
	private TipoJuego tipoDeJuegoDeLaPartida;
	private int cantidadDeRondasAJugar;
	private int numeroRonda = 0;
	private ArrayList<Usuario> usuariosActivosEnSala;
	private Mapa mapa;

	public Partida(int id, ArrayList<Usuario> usuariosActivosEnSala, int cantidadDeRondasDePartida, TipoJuego tipo,
			Mapa mapa) {
		this.id = id;
		this.usuariosActivosEnSala = usuariosActivosEnSala;
		for (Usuario usuario : usuariosActivosEnSala) {
			System.out.println("partida " + usuario);
			Jugador jugador;
			if (usuario instanceof UsuarioBot) {
				jugador = new JugadorBot(usuario);
				this.jugadoresEnPartida.add(jugador);
			} else {
				jugador = new Jugador(usuario);
				this.jugadoresEnPartida.add(jugador);
			}
			usuario.setJugador(jugador);
		}
		this.cantidadDeRondasAJugar = cantidadDeRondasDePartida;
		this.tipoDeJuegoDeLaPartida = tipo;
		this.mapa = mapa;
	}

	public void empezarPartida() {
		// TODO: ojo porque el juego va a comenzar asincronicamente y esto va a iterar
		// deberiamos decir que cuando termine el juego cree otro juego
		System.out.println("numeroRonda " + numeroRonda + " " + " cantidadDeRondasAJugar " + cantidadDeRondasAJugar);
		if (numeroRonda < this.cantidadDeRondasAJugar) {
			try {
				System.out.println("Ronda " + (++numeroRonda));
				this.partidaEnCurso = true;
				this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida, this.mapa);
				if (this.comienzoDeJuego()) {
					this.rondasJugadas.add(this.rondaEnCurso);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		Thread thread = new Thread() {
			public synchronized void run() {
				try {
					/*
					 * Espero un segundo porque al mismo tiempo que creamos la partida, le estamos
					 * enviando el mapa. Entonces tendria que avisar que empezo el juego y luego
					 * enviar el mapa a dibujar
					 */
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rondaEnCurso.start();
				empezarPartida();

			}
		};
		thread.start();

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

	public ArrayList<Usuario> getUsuariosActivosEnSala() {
		return this.usuariosActivosEnSala;
	}
}
