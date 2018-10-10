package core;

import java.util.List;

import config.Param;

public class Vibora {
	private String nombre;
	private int frutasComidas;
	private List<CuerpoVibora> cuerpos;
	private int sentido;
	private CuerpoVibora cabeza;
	private CuerpoVibora colaRemovida;

	/**
	 * Consigue la cabeza que está en la ultima posicion Asume que la cabeza se
	 * actualiza en cada movimiento
	 * 
	 * @return
	 */
	public CuerpoVibora getCabeza() {
		if (this.cabeza != null) {
			return this.cabeza;
		}
		return this.cuerpos.get(this.cuerpos.size());
	}

	/**
	 * Mueve la vibora hacia el ultimo sentido. Para moverse se elimina la cola y se
	 * agrega un cuerpo delante de la cabeza
	 */
	public void mover() {
		CuerpoVibora cv = new CuerpoVibora(this, null);
		switch (this.sentido) {
		case Param.POSICION_ESTE:
			cv = new CuerpoVibora(this, this.getCabeza().getX() + 1, this.getCabeza().getY());
			break;
		case Param.POSICION_OESTE:
			cv = new CuerpoVibora(this, this.getCabeza().getX() - 1, this.getCabeza().getY());
			break;
		case Param.POSICION_NORTE:
			cv = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() + 1);
			break;
		case Param.POSICION_SUR:
			cv = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() + 1);
			break;
		}
		this.cabeza = cv;
		this.cuerpos.add(cv);
		this.colaRemovida = this.cuerpos.remove(0);

	}

	/**
	 * Setea un nuevo sentido
	 * 
	 * @param sentido
	 */
	public void setSentido(int sentido) {
		this.sentido = sentido;
	}

	/**
	 * Verifica si choca con algo del mapa
	 * 
	 * @return true si choca
	 */
	public boolean choque() {
		// TODO: ver si devolver true o devolver una entidad con la que colisiona
		return false;
	}

	/**
	 * Se vuelve a cargar la cola que se removio. Esto implica tiene que haber una
	 * cola removida previamente
	 */
	public void crecer() {
		if (this.colaRemovida != null) {
			this.cuerpos.add(0, this.colaRemovida);
			this.colaRemovida = null;
		}
	}

	/**
	 * Al comer incrementa la cantidad de frutas comidas
	 */
	public void commer() {
		this.frutasComidas++;
	}

	/**
	 * Devuelve la cantidad de frutas comidas
	 * 
	 * @return El numero de frutas comidas
	 */
	public int getFrutasComidas() {
		return this.frutasComidas;
	}

	public List<CuerpoVibora> getCuerpos() {
		return this.cuerpos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Vibora other = (Vibora) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
