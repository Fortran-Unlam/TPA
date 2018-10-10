package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import config.Param;

public class Vibora extends Entidad {

	private String nombre;
	private int frutasComidas;
	private List<CuerpoVibora> cuerpos = new ArrayList<CuerpoVibora>();
	private int sentido;
	private CuerpoVibora cabeza;
	private boolean crece = false;

	/**
	 * Crea una vibora con cuerpos en las coordenadas pasadas y con un sentido
	 * random Se puede pasar una coordenada y esta sera la cabeza
	 * 
	 * @param coordenada
	 */
	public Vibora(Coordenada[] coordenadas) {
		super(coordenadas[coordenadas.length - 1]);

		for (Coordenada coordenada : coordenadas) {
			CuerpoVibora cuerpoVibora = new CuerpoVibora(this, coordenada);
			this.cuerpos.add(cuerpoVibora);
		}

		this.sentido = new Random().nextInt(4);
	}

	/**
	 * Crea una vibora con cuerpos en las coordenadas pasadas y con un sentido dado
	 * Se puede pasar una coordenada y esta sera la cabeza
	 * 
	 * @param coordenada
	 * @param sentido
	 */
	public Vibora(Coordenada[] coordenadas, int sentido) {
		super(coordenadas[coordenadas.length - 1]);

		for (Coordenada coordenada : coordenadas) {
			CuerpoVibora cuerpoVibora = new CuerpoVibora(this, coordenada);
			this.cuerpos.add(cuerpoVibora);
		}

		this.sentido = sentido;
	}

	/**
	 * Consigue la cabeza que esta en la ultima posicion. Asume que la cabeza se
	 * actualiza en cada movimiento
	 * 
	 * @return
	 */
	public CuerpoVibora getCabeza() {
		if (this.cabeza != null) {
			return this.cabeza;
		}
		return this.cuerpos.get(this.cuerpos.size() - 1);
	}

	/**
	 * Mueve la vibora hacia el ultimo sentido. Para moverse se elimina la cola y se
	 * agrega un cuerpo delante de la cabeza. El movimiento no implica la quita de
	 * la cola
	 */
	public void cabecear() {
		CuerpoVibora cuerpoVibora = new CuerpoVibora(this, null);
		switch (this.sentido) {
		case Param.POSICION_ESTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX() + 1, this.getCabeza().getY());
			break;
		case Param.POSICION_OESTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX() - 1, this.getCabeza().getY());
			break;
		case Param.POSICION_NORTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() + 1);
			break;
		case Param.POSICION_SUR:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() - 1);
			break;
		}
		this.cabeza = cuerpoVibora;
		this.coordenada = this.cabeza.getCoordenada();

		this.cuerpos.add(cuerpoVibora);
	}

	/**
	 * Setea un nuevo sentido si es que puede Si la vibora tiene UN cuerpo (UN
	 * cuadrado/pixel) se puede mover para su direccion contraria
	 * 
	 * Si la vibora tiene MAS DE UN cuerpo (MAS DE UN cuadrado/pixel) NO se puede
	 * mover para su direccion contraria pues "se comería su propio cuerpo".
	 * 
	 * @param sentido
	 */
	public void setSentido(int sentido) {

		if (this.cuerpos.size() == 1 || Math.abs(this.sentido - sentido) != 2) {
			this.sentido = sentido;
		}
	}

	/**
	 * Al comer incrementa la cantidad de frutas comidas
	 */
	public void comer() {
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

	/**
	 * Devuelve la lista de sus CuerpoVibora
	 * 
	 * @return Lista de CuerpoVibora
	 */
	public List<CuerpoVibora> getCuerpos() {
		return this.cuerpos;
	}

	/**
	 * Quita la cola si en ese ciclo de juego no va a crecer. Si crece no hace nada.
	 */
	public void crecerOMover() {
		if (!this.crece) {
			this.cuerpos.remove(0);
		}
		this.crece = false;
	}

	/**
	 * Marca que crece
	 */
	public void marcarCrecimiento() {
		this.crece = true;
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
