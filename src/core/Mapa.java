package core;

import java.util.ArrayList;

public class Mapa {

	private Coordenada tamano;

	private ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();

	private Fruta[][] posiconesDeFrutas;
	private CuerpoVibora[][] posicionesDecuerpoViboras;

	private boolean cambioEnFrutas;
	private boolean cambioEnVibora;

	/**
	 * Crea un mapa a partir de las coordenadas, si se quiere un mapa de 5x5 enviar
	 * 4,4. Las posiciones van desde el 0.
	 * 
	 * @param x
	 * @param y
	 */
	public Mapa(int x, int y) {
		this.tamano = new Coordenada(x, y);
	}

	/**
	 * Agrega una vibora en el mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo.
	 * 
	 * @param vibora
	 */
	public boolean add(Vibora vibora) {
		for (CuerpoVibora cuerpo : vibora.getCuerpos()) {
			if (!this.estaDentro(cuerpo.getX(), cuerpo.getY())
					|| this.getCuerpoVibora(cuerpo.getX(), cuerpo.getY()) != null
					|| this.getFruta(cuerpo.getX(), cuerpo.getY()) != null) {
				return false;
			}
		}

		this.cambioEnVibora = true;
		this.viboras.add(vibora);
		return true;
	}

	/**
	 * Agrega una fruta al mapa. Su coordenada tiene que estar dentro del mapa.
	 * Tampoco puede agregarlo donde haya algo.
	 * 
	 * @param fruta
	 */
	public boolean add(Fruta fruta) {
		if (!this.estaDentro(fruta.getX(), fruta.getY()) || this.getCuerpoVibora(fruta.getX(), fruta.getY()) != null
				|| this.getFruta(fruta.getX(), fruta.getY()) != null) {
			return false;
		}

		this.cambioEnFrutas = true;
		this.frutas.add(fruta);
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
			Fruta fruta = this.getFruta(vibora.getCabeza().getX(), vibora.getCabeza().getY());
			if (fruta != null) {
				Colision.colisionar(vibora, fruta);
			}
		}

		for (Vibora vibora : this.viboras) {
			vibora.crecerOMover();
		}

		for (int i = 0; i < this.frutas.size(); i++) {
			Fruta fruta = this.frutas.get(i);
			if (fruta.getMuerte()) {
				this.cambioEnFrutas = true;
				this.frutas.remove(i);
			}
		}

		this.cargarCuerposViboras();

		for (int i = 0; i < this.viboras.size(); i++) {
			Vibora vibora = this.viboras.get(i);
			if (vibora.getMuerte()) {
				this.viboras.remove(i);
			}
		}

	}

	/**
	 * Carga todas las frutas en una matriz. No deberia haber dos frutas en la misma
	 * posicion. Esta se deberia llamar cuando se agrega una fruta o cuando se
	 * remueve una fruta.
	 */
	private void cargarFrutas() {
		this.posiconesDeFrutas = new Fruta[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Fruta fruta : frutas) {
			this.posiconesDeFrutas[fruta.getX()][fruta.getY()] = fruta;
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
			if (this.posiconesDeFrutas != null) {
				return this.posiconesDeFrutas[x][y];
			}
		}

		return null;
	}

	/**
	 * Carga en una matriz las viboras. Si dos viboras se van a guardar en la misma
	 * posicion crea una colision para que vea quien tiene que morir. Esto se
	 * deberia llamar cuando hay una nueva vibora o cuando se mueven las viboras
	 */
	private void cargarCuerposViboras() {
		int coordenadaX, coordenadaY;
		this.posicionesDecuerpoViboras = new CuerpoVibora[this.tamano.getX() + 1][this.tamano.getY() + 1];
		for (Vibora vibora : this.viboras) {
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				coordenadaX = cuerpoVibora.getX();
				coordenadaY = cuerpoVibora.getY();
				if (!this.estaDentro(coordenadaX, coordenadaY)) {
					cuerpoVibora.getVibora().matar();
					continue;
				}
				if (this.posicionesDecuerpoViboras[coordenadaX][coordenadaY] != null) {
					Colision.colisionar(this.posicionesDecuerpoViboras[coordenadaX][coordenadaY], cuerpoVibora);
				}
				this.posicionesDecuerpoViboras[coordenadaX][coordenadaY] = cuerpoVibora;
			}
		}
	}

	/**
	 * Retorna el primer cuerpo de vibora si hay en la posicion dada
	 * 
	 * @param x Horizontal
	 * @param y Vertical
	 * 
	 * @return Cuerpo de vibora | null
	 */
	public CuerpoVibora getCuerpoVibora(int x, int y) {
		if (this.cambioEnVibora) {
			this.cargarCuerposViboras();
		}

		return this.posicionesDecuerpoViboras[x][y];
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
		return x >= 0 && y >= 0 && this.tamano.getX() > x && this.tamano.getY() > y;
	}
}
