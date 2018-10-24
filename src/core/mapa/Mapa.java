package core.mapa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import config.Param;
import core.Colisionador;
import core.Coordenada;
import core.Muro;
import core.Obstaculo;
import core.Puntaje;
import core.Score;
import core.entidad.CuerpoVibora;
import core.entidad.Fruta;
import core.entidad.Vibora;

public class Mapa extends JPanel {

	private static final long serialVersionUID = -7166030434212474238L;

	private Coordenada tamano;

	public ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Fruta> frutas = new ArrayList<>();
	private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
	private ArrayList<Puntaje> score = new ArrayList<>();
	
	private Fruta[][] posicionesDeFrutas;
	private Vibora[][] posicionesDeViboras;
	private Obstaculo[][] posicionesDeObstaculos;

	private boolean cambioEnFrutas;
	private boolean cambioEnVibora;
	private boolean cambioEnObstaculos;
	
	private int idVibora = 1;

	/**
	 * Crea un mapa a partir de las coordenadas. Las posiciones van desde el 0.
	 * 
	 * @param x
	 * @param y
	 */
	public Mapa(int x, int y) {
		this.tamano = new Coordenada(x - 1, y - 1);
	}

	/**
	 * Agrega una vibora en el mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo.
	 * 
	 * @param vibora
	 */
	public boolean add(Vibora vibora) {
		if (!this.estaDentro(vibora.getHead().getX(), vibora.getHead().getY())
				|| this.getVibora(vibora.getHead().getX(), vibora.getHead().getY()) != null
				|| this.getFruta(vibora.getHead().getX(), vibora.getHead().getY()) != null
				|| this.getObstaculo(vibora.getX(), vibora.getY()) != null) {
			return false;
		}

		for (CuerpoVibora cuerpo : vibora.getCuerpos()) {
			if (!this.estaDentro(cuerpo.getX(), cuerpo.getY()) || this.getVibora(cuerpo.getX(), cuerpo.getY()) != null
					|| this.getFruta(cuerpo.getX(), cuerpo.getY()) != null
					|| this.getObstaculo(cuerpo.getX(), cuerpo.getY()) != null) {
				return false;
			}
		}

		this.cambioEnVibora = true;
		this.viboras.add(vibora);
		vibora.setId(idVibora++);
		return true;
	}

	/**
	 * Agrega una fruta al mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo.
	 * 
	 * @param fruta
	 */
	public boolean add(Fruta fruta) {
		if (!this.estaDentro(fruta.getX(), fruta.getY()) || this.getVibora(fruta.getX(), fruta.getY()) != null
				|| this.getFruta(fruta.getX(), fruta.getY()) != null
				|| this.getObstaculo(fruta.getX(), fruta.getY()) != null) {
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
	public boolean add(Obstaculo obstaculo) {
		if (!this.estaDentro(obstaculo.getX(), obstaculo.getY())
				|| this.getVibora(obstaculo.getX(), obstaculo.getY()) != null
				|| this.getFruta(obstaculo.getX(), obstaculo.getY()) != null
				|| this.getObstaculo(obstaculo.getX(), obstaculo.getY()) != null) {
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
	public boolean add(Muro muro) {
		// recorro de atras hacia adelante borrando las piedras agregadas
		LinkedList<Obstaculo> piedras = muro.getPiedras();

		while(!piedras.isEmpty()) {
			if (!this.estaDentro(piedras.getFirst().getX(), piedras.getFirst().getY())
					|| this.getVibora(piedras.getFirst().getX(), piedras.getFirst().getY()) != null
					|| this.getFruta(piedras.getFirst().getX(), piedras.getFirst().getY()) != null
					|| this.getObstaculo(piedras.getFirst().getX(), piedras.getFirst().getY()) != null) {
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

		for (Vibora vibora : this.viboras) {
			vibora.cabecear();
		}

		for (Vibora vibora : this.viboras) {
			Obstaculo obstaculo = this.getObstaculo(vibora.getHead().getX(), vibora.getHead().getY());
			if (obstaculo != null) {
				Colisionador.colisionar(vibora, obstaculo);
			}
		}

		for (Vibora vibora : this.viboras) {
			Fruta fruta = this.getFruta(vibora.getHead().getX(), vibora.getHead().getY());
			if (fruta != null) {
				Colisionador.colisionar(vibora, fruta);
			}
		}
		
		this.score = Score.calcularScore(viboras);

		for (Vibora vibora : this.viboras) {
			vibora.crecerOMover();
		}

		for (int i = 0; i < this.frutas.size(); i++) {
			Fruta fruta = this.frutas.get(i);
			if (fruta.getFueComida()) {
				this.cambioEnFrutas = true;
				this.frutas.remove(i);
			}
		}

		this.cargarViboras();

		for (int i = 0; i < this.viboras.size(); i++) {
			Vibora vibora = this.viboras.get(i);
			if (vibora.isDead()) {
				this.viboras.remove(i);
				// despues de eliminar las viboras transformo sus cuerpos en fruta
				for(CuerpoVibora cuerpo : vibora.getCuerpos()) {
					this.add(new Fruta(cuerpo.getX(), cuerpo.getY()));
				}
			}
		}
		repaint();
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
	public Fruta getFruta(int x, int y) {
		if (this.estaDentro(x, y)) {

			if (this.cambioEnFrutas) {
				this.cargarFrutas();
				this.cambioEnFrutas = false;
			}
			if (this.posicionesDeFrutas != null) {
				return this.posicionesDeFrutas[x][y];
			}
		}

		return null;
	}

	/**
	 * Carga en una matriz las viboras. Si dos viboras se van a guardar en la misma
	 * posicion crea una colision para que vea quien tiene que morir. Esto se
	 * deberia llamar cuando hay una nueva vibora o cuando se mueven las viboras
	 */
	private void cargarViboras() {
		int coordenadaX, coordenadaY;
		this.posicionesDeViboras = new Vibora[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Vibora vibora : this.viboras) {
			if (!this.estaDentro(vibora.getX(), vibora.getY())) {
				vibora.matar();
				continue;
			}

			// verifico la cabeza
			coordenadaX = vibora.getX();
			coordenadaY = vibora.getY();
			if (this.posicionesDeViboras[coordenadaX][coordenadaY] != null) {
				Colisionador.colisionar(this.posicionesDeViboras[coordenadaX][coordenadaY], vibora);
			}
			this.posicionesDeViboras[coordenadaX][coordenadaY] = vibora;

			// verifico los cuerpos
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				coordenadaX = cuerpoVibora.getX();
				coordenadaY = cuerpoVibora.getY();
				if (this.posicionesDeViboras[coordenadaX][coordenadaY] != null) {
					Colisionador.colisionar(this.posicionesDeViboras[coordenadaX][coordenadaY], vibora);
				}
				this.posicionesDeViboras[coordenadaX][coordenadaY] = vibora;
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
	public Vibora getVibora(int x, int y) {
		if (this.cambioEnVibora) {
			this.cargarViboras();
		}
		if (this.posicionesDeViboras != null) {
			return this.posicionesDeViboras[x][y];
		}
		return null;
	}

	private void cargarObstaculos() {
		this.posicionesDeObstaculos = new Obstaculo[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Obstaculo obs : obstaculos) {
			this.posicionesDeObstaculos[obs.getX()][obs.getY()] = obs;
		}
	}

	public Obstaculo getObstaculo(int x, int y) {
		if (this.estaDentro(x, y)) {
			if (this.cambioEnObstaculos) {
				this.cargarObstaculos();
			}
			if (this.posicionesDeObstaculos != null) {
				return this.posicionesDeObstaculos[x][y];
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
	public boolean estaDentro(int x, int y) {
		return x >= 0 && y >= 0 && this.tamano.getX() >= x && this.tamano.getY() >= y;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Param.MAPA_WIDTH, Param.MAPA_HEIGHT);

		for (Fruta fruta : this.frutas) {
			fruta.paint(g2d);
		}

		for (Vibora vibora : this.viboras) {
			vibora.paint(g2d);
		}

		for (Obstaculo obstaculo : this.obstaculos) {
			obstaculo.paint(g2d);
		}
	}

	public ArrayList<Puntaje> getScore() {
		return score;
	}
	
	
}
