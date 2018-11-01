package core.entidad;

import java.io.Serializable;

import core.Coordenada;

public class CuerpoVibora implements Serializable {

	private static final long serialVersionUID = 2485552979729067616L;
	private Coordenada coordenada;
	private boolean isHead = false;

	public CuerpoVibora(Coordenada ubicacion) {
		this.coordenada = ubicacion;
	}

	public CuerpoVibora(int x, int y) {
		this.coordenada = new Coordenada(x, y);
	}

	/**
	 * Ubicacion: posicion x,y del cuerpo Head: marcar con true si ese cuerpo va a
	 * ser una cabeza.
	 * 
	 * @param ubicacion
	 * @param head
	 */
	public CuerpoVibora(Coordenada ubicacion, boolean head) {
		this.coordenada = ubicacion;
		this.isHead = head;
	}

	/**
	 * Ubicacion: posicion x,y del cuerpo Head: marcar con true si ese cuerpo va a
	 * ser una cabeza.
	 * 
	 * @param ubicacion
	 * @param head
	 */
	public CuerpoVibora(int x, int y, boolean head) {
		this.coordenada = new Coordenada(x, y);
		this.isHead = head;
	}

	public int getX() {
		return this.coordenada.getX();
	}

	public int getY() {
		return this.coordenada.getY();
	}

	public Coordenada getCoordenada() {
		return this.coordenada;
	}

	public boolean isHead() {
		return isHead;
	}

	public void setHead(boolean isHead) {
		this.isHead = isHead;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordenada == null) ? 0 : coordenada.hashCode());
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
		CuerpoVibora other = (CuerpoVibora) obj;
		if (coordenada == null) {
			if (other.coordenada != null)
				return false;
		} else if (!coordenada.equals(other.coordenada))
			return false;
		return true;
	}
}
