package core;

public class Entidad {
	
	private boolean muere = false;
	private Coordenada coordenada;

	/**
	 * Crea una entidad a partir de una coordenada
	 * No se verifica si esta dentro del mapa porque la entidad no conoce el mapa
	 * 
	 * @param coordenada
	 */
	public Entidad(Coordenada coordenada) {
		super();
		this.coordenada = coordenada;
	}
	
	/**
	 * Crea una entidad a partir de un X y un Y
	 * No se verifica si esta dentro del mapa porque la entidad no conoce el mapa
	 * 
	 * @param x Horizontal
	 * @param y Vertical
	 */
	public Entidad(int x, int y) {
		super();
		this.coordenada = new Coordenada(x, y);
	}

	/**
	 * Devuelva la coordenada
	 * 
	 * @return La coordenada
	 */
	public Coordenada getCoordenada() {
		return coordenada;
	}
	
	/**
	 * Devuelve la posicion X
	 * 
	 * @return X
	 */
	public int getX() {
		return this.coordenada.getX();
	}
	
	/**
	 * Devuelve la posicion Y
	 * 
	 * @return Y
	 */
	public int getY() {
		return this.coordenada.getY();
	}
	
	/**
	 * Marca la vibora para que muera
	 */
	public void matar() {
		this.muere = true;
	}
	
	/**
	 * Devuelve si la vibora ha muerto
	 * 
	 * @return True si murio
	 */
	public boolean getMuerte() {
		return this.muere;
	}

	/**
	 * Setea la muerte
	 * 
	 * @param muere
	 */
	public void setMuerte(boolean muere) {
		this.muere = muere;
	}

}
