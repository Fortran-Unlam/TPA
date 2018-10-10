package core;


public class CuerpoVibora extends Entidad {

	private Vibora vibora;

	/**
	 * Constructor a partir de la vibora y la coordenada
	 * 
	 * @param vibora
	 * @param coordenada
	 */
	public CuerpoVibora(Vibora vibora, Coordenada coordenada) {
		super(coordenada);
		this.vibora = vibora;
	}

	/**
	 * Constructor a partir de la vibora y sus posiciones X,Y
	 * 
	 * @param vibora
	 * @param x
	 * @param y
	 */
	public CuerpoVibora(Vibora vibora, int x, int y) {
		super(x, y);
		this.vibora = vibora;
	}

	/**
	 * Devuelve la Vibora a la que pertenece
	 * 
	 * @return Vibora
	 */
	public Vibora getVibora() {
		return this.vibora;
	}

	/**
	 * Chequea si esta parte del cuerpo es la cabeza
	 * 
	 * @return True si es la cabeza
	 */
	public boolean isCabeza() {
		return this.vibora.getCabeza().equals(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vibora == null) ? 0 : vibora.hashCode());
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
		if (vibora == null) {
			if (other.vibora != null)
				return false;
		} else if (!vibora.equals(other.vibora))
			return false;
		return true;
	}
}
