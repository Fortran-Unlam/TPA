package core;

public class CuerpoVibora extends Entidad {

	/**
	 * Constructor a partir de la vibora y la coordenada
	 * 
	 * @param vibora
	 * @param coordenada
	 */
	public CuerpoVibora(Coordenada coordenada) {
		super(coordenada);
	}

	/**
	 * Constructor a partir de la vibora y sus posiciones X,Y
	 * 
	 * @param vibora
	 * @param x
	 * @param y
	 */
	public CuerpoVibora(int x, int y) {
		super(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuerpoVibora other = (CuerpoVibora) obj;
		if (other.getX() != this.getX()) {
			return false;
		}
		if (other.getY() != this.getY()) {
			return false;
		}
		return true;
	}
	
}
