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
	
	public int getX() {
		return this.coordenada.getX();
	}
	
	public int getY() {
		return this.coordenada.getY();
	}
	
	public boolean getMuere() {
		return this.muere;
	}
	
	public boolean setMuere(boolean muere) {
		return this.muere = muere;
	}

}
