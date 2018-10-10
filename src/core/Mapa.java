package core;

import java.util.ArrayList;
import java.util.Iterator;

public class Mapa {

	private Coordenada tamano;

	private ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();

	/**
	 * Crea un mapa a partir de las coordenadas, si se quiere un mapa de 5x5 enviar 4,4. Las posiciones van desde el 0.
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
		this.viboras.add(vibora);
	}

	/**
	 * Agrega una fruta al mapa
	 * 
	 * @param fruta
	 */
	public void add(Fruta fruta) {
		this.frutas.add(fruta);
	}

	/**
	 * Mueve sus entidades
	 */
	public void actualizar() {
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
		
		for (int i=0; i<this.frutas.size();i++) {
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

	/**
	 * Consigue una fruta si lo hay en la posicion dada
	 * 
	 * @param x Horizontal
	 * @param y Vertical
	 * 
	 * @return Fruta | null
	 */
	public Fruta getFruta(int x, int y) {
		for (Fruta fruta : frutas) {
			if (fruta.getCoordenada().getX() == x && fruta.getCoordenada().getY() == y) {
				return fruta;
			}
		}
		return null;
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
		for (Vibora vibora : this.viboras) {
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				if (cuerpoVibora.getCoordenada().getX() == x && cuerpoVibora.getCoordenada().getY() == y) {
					return cuerpoVibora;
				}
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
}
