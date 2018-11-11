package core.mapa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import config.Param;
import config.Posicion;
import core.Colisionador;
import core.Coordenada;
import core.Jugador;
import core.Muro;
import core.Puntaje;
import core.entidad.Coordenable;
import core.entidad.CuerpoVibora;
import core.entidad.Fruta;
import core.entidad.Obstaculo;
import core.entidad.Vibora;
import core.entidad.ViboraBot;

public class Mapa {

	private Coordenada tamano;

	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();
	private ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

	private Fruta[][] posicionesDeFrutas;
	private Jugador[][] posicionesDeJugadores;
	private Obstaculo[][] posicionesDeObstaculos;

	private boolean cambioEnFrutas;
	private boolean cambioEnVibora;
	private boolean cambioEnObstaculos;

	public Object viboras;

	private ArrayList<Jugador> espectadores = new ArrayList<Jugador>();

	private boolean murioUnJugador;

	/**
	 * Crea un mapa a partir de las coordenadas. Las posiciones van desde el 0.
	 * 
	 * @param x
	 * @param y
	 */
	public Mapa(final int x, final int y) {
		this.tamano = new Coordenada(x - 1, y - 1);
	}

	/**
	 * Agrega un jugador al mapa creando una vibora para ese jugador y estableciendo
	 * su posicion
	 * 
	 * @param Jugador
	 * 
	 * @return true si lo agrega, false si no puede agregarlo
	 */
	public boolean add(final Jugador jugador, boolean bot) {
		Vibora vibora = this.intentarCrearVibora(bot);

		if (vibora != null) {
			jugador.setVibora(vibora);
			this.jugadores.add(jugador);
			return true;
		}
		return false; // RETONAR EXCEP PORQUE UN JUGADOR SE QUEDO SIN VIBORA
	}

	/**
	 * Agrega una vibora en el mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo. Solo el mapa agregar vibora porque
	 * se deberia agregar jugadores.
	 * 
	 * @param vibora
	 */
	protected boolean add(final Vibora vibora) {
		if (!this.estaDentro(vibora) || this.getJugador(vibora) != null || this.getFruta(vibora) != null
				|| this.getObstaculo(vibora) != null) {
			return false;
		}

		for (CuerpoVibora cuerpo : vibora.getCuerpos()) {
			if (!this.estaDentro(cuerpo) || this.getJugador(cuerpo) != null || this.getFruta(cuerpo) != null
					|| this.getObstaculo(cuerpo) != null) {
				return false;
			}
		}

		this.cambioEnVibora = true;
		return true;
	}

	/**
	 * Intenta agregar una vibora al mapa.
	 * 
	 * @return Si no puede return null
	 */
	public Vibora intentarCrearVibora(boolean bot) {
		Vibora vibora = null;
		Random random = new Random();

		for (int intento = 0; intento < 100; intento++) {
			if (bot) {
				vibora = new ViboraBot(
						new Coordenada(random.nextInt(Param.MAPA_MAX_X - 10), random.nextInt(Param.MAPA_MAX_Y - 10)));
			} else {
				vibora = new Vibora(
						new Coordenada(random.nextInt(Param.MAPA_MAX_X - 10), random.nextInt(Param.MAPA_MAX_Y - 10)),
						10, Posicion.ESTE);
			}

			if (this.add(vibora)) {
				return vibora;
			}
		}
		return null;
	}

	/**
	 * Agrega una fruta al mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo.
	 * 
	 * @param fruta
	 */
	public boolean add(final Fruta fruta) {
		if (!this.estaDentro(fruta) || this.getJugador(fruta) != null || this.getFruta(fruta) != null
				|| this.getObstaculo(fruta) != null) {
			return false;
		}

		this.cambioEnFrutas = true;
		this.frutas.add(fruta);
		return true;
	}

	/**
	 * Agrega un obstaculo al mapa. Su coordenada tiene que estar dentro del mapa.
	 * No puede agregarlo donde haya algo.
	 * 
	 * @param obstaculo
	 */
	public boolean add(final Obstaculo obstaculo) {
		if (!this.estaDentro(obstaculo) || this.getJugador(obstaculo) != null || this.getFruta(obstaculo) != null
				|| this.getObstaculo(obstaculo) != null) {
			return false;
		}

		this.cambioEnObstaculos = true;
		this.obstaculos.add(obstaculo);
		return true;
	}

	/**
	 * Agrega un muro al mapa. No es mas que un list de obstaculos.
	 * 
	 * @param obstaculo
	 */
	public boolean add(final Muro muro) {
		// recorro de atras hacia adelante borrando las piedras agregadas
		LinkedList<Obstaculo> piedras = muro.getPiedras();

		while (!piedras.isEmpty()) {
			if (!this.estaDentro(piedras.getFirst()) || this.getJugador(piedras.getFirst()) != null
					|| this.getFruta(piedras.getFirst()) != null || this.getObstaculo(piedras.getFirst()) != null) {
				return false;
			}

			this.cambioEnObstaculos = true;
			this.obstaculos.add(piedras.getFirst());
			piedras.removeFirst();
		}

		return true;
	}

	/**
	 * Actualiza todo el mapa, esto implica mover sus entidades, verificar si se
	 * chocan, matar a las que corresponde y quitarlas del mapa si mueren
	 */
	public void actualizar() {
		this.cambioEnVibora = true;
		this.murioUnJugador = false;

		ArrayList<Fruta> frutasComidas = new ArrayList<Fruta>();

		for (Jugador jugador : this.jugadores) {
			jugador.determinarMovimiento(this);

			jugador.getVibora().cabecear();

			Obstaculo obstaculo = this.getObstaculo(jugador.getVibora());
			if (obstaculo != null) {
				Colisionador.colisionar(jugador, obstaculo);
			}

			Fruta fruta = this.getFruta(jugador.getVibora());
			if (fruta != null) {
				Colisionador.colisionar(jugador, fruta);
				frutasComidas.add(fruta);
			}
			jugador.getVibora().removerCola();
		}

		for (int i = 0; i < frutasComidas.size(); i++) {
			Fruta fruta = frutasComidas.get(i);
			if (fruta.getFueComida()) {
				this.cambioEnFrutas = true;
				this.frutas.remove(fruta);
			}
		}

		this.cargarYVerSiColisionanViboras();

		for (int i = 0; i < this.jugadores.size(); i++) {
			Vibora vibora = this.jugadores.get(i).getVibora();
			if (vibora.isDead()) {
				this.espectadores.add(this.jugadores.get(i));
				this.jugadores.remove(i);
				this.murioUnJugador = true;
				// Despues de eliminar las viboras transformo sus cuerpos en fruta
				for (CuerpoVibora cuerpo : vibora.getCuerpos()) {
					this.add(new Fruta(cuerpo.getX(), cuerpo.getY()));
				}
			}
		}

		while (this.frutas.size() < Param.CANTIDAD_FRUTA_MINIMAS) {
			Random random = new Random();
			this.add(new Fruta(random.nextInt(Param.MAPA_MAX_X), random.nextInt(Param.MAPA_MAX_Y)));
		}

		this.scoring();
	}

	/**
	 * Carga todas las frutas en una matriz. No deberia haber dos frutas en la misma
	 * posicion. Esta se deberia llamar cuando se agrega una fruta o cuando se
	 * remueve una fruta.
	 */
	private void cargarFrutas() {
		this.posicionesDeFrutas = new Fruta[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Fruta fruta : frutas) {
			this.posicionesDeFrutas[fruta.getX()][fruta.getY()] = fruta;
		}
	}

	/**
	 * Consigue una fruta si lo hay en la posicion dada
	 * 
	 * @param x Horizontal
	 * @param y Vertical
	 * 
	 * @return Fruta | null
	 */
	public Fruta getFruta(final Coordenable object) {
		if (this.estaDentro(object)) {

			if (this.cambioEnFrutas) {
				this.cargarFrutas();
				this.cambioEnFrutas = false;
			}
			if (this.posicionesDeFrutas != null) {
				return this.posicionesDeFrutas[object.getX()][object.getY()];
			}
		}

		return null;
	}

	/**
	 * Carga en una matriz las viboras. Si dos viboras se van a guardar en la misma
	 * posicion crea una colision para que vea quien tiene que morir. Esto se
	 * deberia llamar cuando hay una nueva vibora o cuando se mueven las viboras
	 */
	private void cargarYVerSiColisionanViboras() {
		int coordenadaX, coordenadaY;
		this.posicionesDeJugadores = new Jugador[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Jugador jugador : this.jugadores) {
			Vibora vibora = jugador.getVibora();
			if (!this.estaDentro(vibora)) {
				vibora.matar();
				continue;
			}

			// verifico la cabeza
			coordenadaX = vibora.getX();
			coordenadaY = vibora.getY();
			if (this.posicionesDeJugadores[coordenadaX][coordenadaY] != null) {
				Colisionador.colisionar(this.posicionesDeJugadores[coordenadaX][coordenadaY], jugador);
			}
			this.posicionesDeJugadores[coordenadaX][coordenadaY] = jugador;

			// verifico los cuerpos
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				coordenadaX = cuerpoVibora.getX();
				coordenadaY = cuerpoVibora.getY();
				if (this.posicionesDeJugadores[coordenadaX][coordenadaY] != null) {
					Colisionador.colisionar(this.posicionesDeJugadores[coordenadaX][coordenadaY], jugador);
				}
				this.posicionesDeJugadores[coordenadaX][coordenadaY] = jugador;
			}
		}
	}

	/**
	 * Retorna la primera vibora si hay en la posicion dada
	 * 
	 * @param x Horizontal
	 * @param y Vertical
	 * 
	 * @return Cuerpo de vibora | null
	 */
	public Jugador getJugador(final Coordenable object) {
		if (this.cambioEnVibora) {
			this.cargarYVerSiColisionanViboras();
		}
		if (this.posicionesDeJugadores != null) {
			return this.posicionesDeJugadores[object.getX()][object.getY()];
		}
		return null;
	}

	private void cargarObstaculos() {
		this.posicionesDeObstaculos = new Obstaculo[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Obstaculo obs : obstaculos) {
			this.posicionesDeObstaculos[obs.getX()][obs.getY()] = obs;
		}
	}

	public Obstaculo getObstaculo(final Coordenable object) {
		if (this.estaDentro(object)) {
			if (this.cambioEnObstaculos) {
				this.cargarObstaculos();
			}
			if (this.posicionesDeObstaculos != null) {
				return this.posicionesDeObstaculos[object.getX()][object.getY()];
			}
		}
		return null;
	}

	/**
	 * Retorna la dimension del mapa
	 * 
	 * @return Coordenada
	 */
	public Coordenada getTamano() {
		return this.tamano;
	}

	/**
	 * Retorna si la coordenada (x,y) esta dentro del mapa
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return True si esta adentro
	 */
	public boolean estaDentro(final Coordenable object) {
		return object.getX() >= 0 && object.getY() >= 0 && this.tamano.getX() >= object.getX()
				&& this.tamano.getY() >= object.getY();
	}

	public ArrayList<Puntaje> scoring() {
		ArrayList<Puntaje> score = new ArrayList<Puntaje>();
		for (Jugador jugador : this.jugadores) {
			score.add(new Puntaje(jugador.getNombre(), jugador.getFrutasComidas()));
		}
		for (Jugador jugador : this.espectadores) {
			score.add(new Puntaje(jugador.getNombre(), jugador.getFrutasComidas()));
		}
		score.sort(null);
		return score;
	}

	public JsonArray scoringJson() {
		ArrayList<Puntaje> score = new ArrayList<Puntaje>();
		for (Jugador jugador : this.jugadores) {
			score.add(new Puntaje(jugador.getNombre(), jugador.getFrutasComidas()));
		}
		for (Jugador jugador : this.espectadores) {
			score.add(new Puntaje(jugador.getNombre(), jugador.getFrutasComidas()));
		}
		score.sort(null);
		JsonArrayBuilder scoreArrayBuilder = Json.createArrayBuilder();

		for (Puntaje puntaje : score) {
			scoreArrayBuilder.add(puntaje.toString());
		}
		return scoreArrayBuilder.build();
	}

	public ArrayList<Jugador> getJugadores() {
		return this.jugadores;
	}

	public ArrayList<Fruta> getfrutas() {
		return this.frutas;
	}

	public ArrayList<Obstaculo> getObstaculos() {
		return this.obstaculos;
	}

	public ArrayList<Jugador> getEspectadores() {
		return this.espectadores;
	}

	public boolean getMurioUnJugador() {
		return this.murioUnJugador;
	}

	public JsonObject toJson() {
		// Mando en el orde: jugador -> fruta -> obstaculo
		JsonArrayBuilder jugadores = Json.createArrayBuilder();
		JsonArrayBuilder frutas = Json.createArrayBuilder();
		JsonArrayBuilder obstaculos = Json.createArrayBuilder();

		for (Jugador j : this.jugadores) {
			jugadores.add(j.toJson());
		}

		for (Fruta f : this.frutas) {
			frutas.add(f.toJson());
		}

		for (Obstaculo o : this.obstaculos) {
			obstaculos.add(o.toJson());
		}

		return Json.createObjectBuilder()
				// Orden: tamaño->jugadores->frutas->obstaculos->score
				.add("tamano", this.tamano.toJson()).add("jugadores", jugadores).add("frutas", frutas)
				.add("obstaculos", obstaculos).add("murioUnJugador", this.murioUnJugador)
				.add("score", this.scoringJson()).build();
	}
}
