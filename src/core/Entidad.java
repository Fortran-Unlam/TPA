package core;

public class Entidad {

	private boolean muere = false;
	protected Coordenada coordenada;

	/**
	 * Crea una entidad a partir de una coordenada No se verifica si esta dentro del
	 * mapa porque la entidad no conoce el mapa
	 * 
	 * @param coordenada
	 */
	public Entidad(Coordenada coordenada) {
		super();
		this.coordenada = coordenada;
	}

	/**
	 * Crea una entidad a partir de un X y un Y No se verifica si esta dentro del
	 * mapa porque la entidad no conoce el mapa
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordenada == null) ? 0 : coordenada.hashCode());
		result = prime * result + (muere ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidad other = (Entidad) obj;
		if (coordenada == null) {
			if (other.coordenada != null)
				return false;
		} else if (!coordenada.equals(other.coordenada))
			return false;
		if (muere != other.muere)
			return false;
		return true;
	}
	
	
	

}
