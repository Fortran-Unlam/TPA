package core;

public class Entidad {

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

}
