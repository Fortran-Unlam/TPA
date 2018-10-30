package core;

import java.io.Serializable;

public class Coordenada implements Serializable {

	private static final long serialVersionUID = -8552338515159103928L;
	private int x;
	private int y;

	/**
	 * Crea una coordenada dentro del mapa
	 * 
	 * @param x Posicion horizontal
	 * @param y Posicion vertical
	 */
	public Coordenada(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Establece una coordenada (x,y)
	 * 
	 * @param x Posicion x
	 * @param y Posicion y
	 */
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Devuelve la posicion horizontal x
	 * 
	 * @return La posicion horizontal x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Establece la posicion horizontal x
	 * 
	 * @param x La posicion horizontal x
	 * 
	 * @return La misma coordenada
	 */
	public Coordenada setX(int x) {
		this.x = x;
		return this;
	}

	/**
	 * Devuelve la posicion vertical Y
	 * 
	 * @return La posicion vertical Y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Devuelve la posicion vertical Y
	 * 
	 * @param y La posicion vertical Y
	 * @return La misma coordenada
	 */
	public Coordenada setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Coordenada other = (Coordenada) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	
	
}
