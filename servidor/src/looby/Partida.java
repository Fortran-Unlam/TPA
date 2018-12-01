package looby;

import java.io.Serializable;
import java.util.ArrayList;
import core.Jugador;
import core.JugadorBot;
import core.mapa.Juego;
import core.mapa.Mapa;
import core.mapa.MapaDos;
import core.mapa.MapaTres;
import core.mapa.MapaUno;
import servidor.Servidor;

public class Partida implements Serializable {

	private static final long serialVersionUID = 5614028727088648640L;
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

	public Partida(int id, ArrayList<Usuario> usuariosActivosEnSala, TipoJuego tipo,
			int tipoMapa, int cantidadTotalRondas) {
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
		this.ganadorPartida = null;
	}

	public void empezarPartida() {
		// TODO: ojo porque el juego va a comenzar asincronicamente y esto va a iterar
		// deberiamos decir que cuando termine el juego cree otro juego
		//System.out.println("empezar partida " + this.numeroRonda + " " + this.cantidadDeRondasAJugar);
		if (this.numeroRonda < this.cantidadDeRondasAJugar) {
			//System.out.println("empezar partida rondas");
			try {
				this.partidaEnCurso = true;
				this.numeroRonda++;
				
				// Inicia el mapa antes de cada ronda.
				this.mapa = crearMapaTipo(tipoMapa);
				this.rondaEnCurso = new Juego(this.jugadoresEnPartida, this.tipoDeJuegoDeLaPartida, this.mapa);
				if (this.comienzoDeJuego()) {
					this.rondasJugadas.add(this.rondaEnCurso);
				}
				//System.out.println("no se si comienza");
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
			//System.out.println("no comenzo por ronda en curso null");
			return false;
		} else if (!this.rondaEnCurso.puedeEmpezar()) {
			//System.out.println("no puede empezar por ronda");
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
					
					if (numeroRonda == cantidadDeRondasAJugar) {
						calcularGanadorPartida();
					} else {
						Thread.sleep(1500);
					}
				} catch (InterruptedException e) {
					Servidor.LOGGER.error("Error al realizar un wait del thread entre Ronda y ronda.", e);					
				}
				empezarPartida();
			}
		};
		thread.start();

		return true;
	}

	public void calcularGanadorPartida() {
		for (Jugador jug : this.jugadoresEnPartida) {
			if (jug.getPuntosEnPartida() > this.puntajeMaximo) {
				this.ganadorPartida = jug;
				this.puntajeMaximo = jug.getPuntosEnPartida();
			}
		}
	}

	public Mapa crearMapaTipo(int tipoMapa) {
		// Devuelve un mapa en base al tipo enviado.
		switch (tipoMapa) {
		case 1:
			return new MapaUno();
		case 2:
			return new MapaDos();
		case 3:
			return new MapaTres();
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

	public String getGanador() {
		return "El ganador es: " + this.ganadorPartida.getNombre() + ". Frutas comidas: " 
				+ this.ganadorPartida.getFrutasComidasEnPartida() + " con un puntaje de: " + 
				this.puntajeMaximo;
	}
}
