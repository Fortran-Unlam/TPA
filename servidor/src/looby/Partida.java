package looby;

import java.io.Serializable;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;

import core.Jugador;
import core.JugadorBot;
import core.mapa.Juego;
import core.mapa.Mapa;
import core.mapa.MapaUno;

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
	private int tipoMapa;
	private Jugador ganadorPartida;
	private int puntajeMaximo;

	public Partida(int id, ArrayList<Usuario> usuariosActivosEnSala, int cantidadTotalRondas, TipoJuego tipo,
			int tipoMapa) {
		this.id = id;
		this.usuariosActivosEnSala = usuariosActivosEnSala;
		for (Usuario usuario : usuariosActivosEnSala) {
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
		this.cantidadDeRondasAJugar = cantidadTotalRondas;
		this.tipoDeJuegoDeLaPartida = tipo;
		this.puntajeMaximo = 0;
		this.tipoMapa = tipoMapa;
	}

	public void empezarPartida() {
		// TODO: ojo porque el juego va a comenzar asincronicamente y esto va a iterar
		// deberiamos decir que cuando termine el juego cree otro juego

		if (this.numeroRonda < this.cantidadDeRondasAJugar) {
			try {
				this.partidaEnCurso = true;
				this.numeroRonda++;
				
				// Inicia el mapa antes de cada ronda.
				this.mapa = crearMapaTipo(tipoMapa);
				this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida, this.mapa);
				if (this.comienzoDeJuego()) {
					this.rondasJugadas.add(this.rondaEnCurso);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (this.numeroRonda == this.cantidadDeRondasAJugar) {
			if (!(ganadorPartida instanceof JugadorBot)) {
				for (Usuario u : usuariosActivosEnSala) {
					if (u.getJugador().equals(this.ganadorPartida)) {
						usuariosActivosEnSala.get(usuariosActivosEnSala.indexOf(u))
								.actualizarEstadisticasPartidasGanadas();
						break;
					}
				}
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
				rondaEnCurso.setRonda(numeroRonda);
				rondaEnCurso.start();

				// Termina una ronda y comienza otra.
				try {

					for (Jugador jug : rondaEnCurso.getJugadoresEnJuego()) {
						//Itero por jugadores, no bots.
							boolean sobrevivioRonda = false;
							
							//Determino el ganador de cada ronda.
							if (!jug.getVibora().isDead()) {
								sobrevivioRonda = true;
								jug.sumarPuntosSobrevivirRonda();
							}
							
							if (numeroRonda== cantidadDeRondasAJugar)
								calcularGanadorPartida();
							
							//Guardo las estadisticas en la base.
							if (!(jug instanceof JugadorBot)) {
								for (Usuario u : usuariosActivosEnSala) {
									if (u.getJugador().equals(jug)) {
										usuariosActivosEnSala.get(usuariosActivosEnSala.indexOf(u)).actualizarEstadisticasRonda(sobrevivioRonda,jug.getFrutasComidas());
										break;
									}
								}
							}
							jug.resetEstadisticasRonda();
						}	
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Poner algo por si falla el update del usuario.

					e.printStackTrace();
				}
				empezarPartida();
			}
		};
		thread.start();

		return true;
	}

	public Jugador calcularGanadorPartida() {
		for (Jugador jug : this.jugadoresEnPartida) {
			if (jug.getPuntosEnPartida() > this.puntajeMaximo) {
				this.ganadorPartida = jug;
				this.puntajeMaximo = jug.getPuntosEnPartida();
			}
		}
		return this.ganadorPartida;
	}

	public Mapa crearMapaTipo(int tipoMapa) {
		// Devuelve un mapa en base al tipo enviado.
		switch (tipoMapa) {
		case 1:
			return new MapaUno();
//			case 2:
//				return new MapaDos();
//			case 3:
//				return new MapaTres();
		}
		return new MapaUno();
	}

	public boolean getPartidaEnCurso() {
		return this.partidaEnCurso;
	}

	public void pararJuegoEnCurso() {
		this.partidaEnCurso = false;
		this.rondaEnCurso.stop();
		this.rondaEnCurso = null;
	}

	public Juego getJuegoEnCurso() {
		return rondaEnCurso;
	}

	public ArrayList<Usuario> getUsuariosActivosEnSala() {
		return this.usuariosActivosEnSala;
	}

	public ArrayList<Jugador> getJugadoresEnPartida() {
		return jugadoresEnPartida;
	}

	public void setJugadoresEnPartida(ArrayList<Jugador> jugadoresEnPartida) {
		this.jugadoresEnPartida = jugadoresEnPartida;
	}

	public void setRondaEnCurso(Juego rondaEnCurso) {
		this.rondaEnCurso = rondaEnCurso;
	}

	public void setPartidaEnCurso(boolean partidaEnCurso) {
		this.partidaEnCurso = partidaEnCurso;
	}

	public Jugador getGanador() {
		return this.ganadorPartida;
	}
}
