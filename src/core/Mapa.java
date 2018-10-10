package core;

import java.util.ArrayList;
import java.util.Iterator;

public class Mapa {

	private Coordenada tamano;

	private ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();

	private Fruta[][] posiconesDeFrutas;

	private boolean cambioEnFrutas;

	private boolean cambioEnVibora;

	private CuerpoVibora[][] posicionesDecuerpoViboras;

	/**
	 * Crea un mapa a partir de las coordenadas, si se quiere un mapa de 5x5 enviar
	 * 4,4. Las posiciones van desde el 0.
	 * 
	 * @param ancho
	 * @param alto
	 */
	public Mapa(int x, int y) {
		this.tamano = new Coordenada(x, y);
	}

	/**
	 * Agrega una vibora en el mapa
	 * 
	 * @param vibora
	 */
	public void add(Vibora vibora) {
		this.cambioEnVibora = true;
		this.viboras.add(vibora);
	}

	/**
	 * Agrega una fruta al mapa
	 * 
	 * @param fruta
	 */
	public void add(Fruta fruta) {
		this.cambioEnFrutas = true;
		this.frutas.add(fruta);
	}

	/**
	 * Actualiza todo el mapa, esto implica mover sus entidades, verificar si se
	 * chocan, matar a las que corresponde y quitarlas del mapa si mueren
	 */
	public void actualizar() {
		this.cambioEnFrutas = true;
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
				this.frutas.remove(i);
			}
		}

		for (Vibora vibora : this.viboras) {
			CuerpoVibora cuerpoVibora = this.getCuerpoVibora(vibora.getCabeza().getX(), vibora.getCabeza().getY());
			if (cuerpoVibora != null) {
				Colision.colisionar(vibora, cuerpoVibora);
			}
		}

		for (Iterator<Vibora> viboras = this.viboras.iterator(); viboras.hasNext();) {
			Vibora vibora = viboras.next();
			if (vibora.getMuerte()) {
				this.viboras.remove(vibora);
			}
		}

	}

	private void cargarFrutas() {
		this.posiconesDeFrutas = new Fruta[this.tamano.getX()+1][this.tamano.getY()+1];
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
		if (this.cambioEnFrutas) {
			this.cargarFrutas();
			this.cambioEnFrutas = false;
		}
		return this.posiconesDeFrutas[x][y];
	}

	private void cargarCuerposViboras() {
		this.posicionesDecuerpoViboras = new CuerpoVibora[this.tamano.getX()+1][this.tamano.getY()+1];
		for (Vibora vibora : this.viboras) {
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				// TODO: re pensar esto
				if (this.posicionesDecuerpoViboras[cuerpoVibora.getX()][cuerpoVibora.getY()] != null) {
					Colision.colisionar(vibora, this.posicionesDecuerpoViboras[cuerpoVibora.getX()][cuerpoVibora.getY()]);
				}
				this.posicionesDecuerpoViboras[cuerpoVibora.getX()][cuerpoVibora.getY()] = cuerpoVibora; 
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
}
