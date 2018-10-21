package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import config.Posicion;

public class Vibora extends Entidad {

	private String nombre;
	private int frutasComidas;
	private LinkedList<CuerpoVibora> cuerpos = new LinkedList<CuerpoVibora>();
	private Posicion sentido;
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

		this.sentido = Posicion.values()[new Random().nextInt(4)];
	}

	/**
	 * Crea una vibora con cuerpos en las coordenadas pasadas y con un sentido dado
	 * Se puede pasar una coordenada y esta sera la cabeza
	 * 
	 * @param coordenada
	 * @param sentido
	 */
	public Vibora(Coordenada[] coordenadas, Posicion sentido) {
		super(coordenadas[coordenadas.length - 1]);

		for (Coordenada coordenada : coordenadas) {
			CuerpoVibora cuerpoVibora = new CuerpoVibora(this, coordenada);
			this.cuerpos.add(cuerpoVibora);
		}

		this.sentido = sentido;
	}

	
	/**
	 * 
	 * Crea una vibora del largo pasado por parametro en forma horizontal
	 * si el sentido es OESTE o ESTE, y en vertical si el sentido es NORTE
	 * o SUR. La coordenada pasada por parámetro es la cabeza.
	 * @param coordenada
	 * @param largo
	 * @param sentido
	 */
	public Vibora(int x, int y, int largo, Posicion sentido) {
		super(new Coordenada(x, y)); // cabeza de la vibora
		this.cabeza = new CuerpoVibora(this, x, y);
		this.sentido = sentido;
		Coordenada coordenada = new Coordenada(x, y);

		if (sentido == Posicion.ESTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX() - i, coordenada.getY()));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.OESTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX() + i, coordenada.getY()));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.NORTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX(), coordenada.getY() - i));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.SUR) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX(), coordenada.getY() + i));
				this.cuerpos.add(cuerpoVibora);
			}
		}
	}
	
	/**
	 * 
	 * Crea una vibora del largo pasado por parametro en forma horizontal
	 * si el sentido(RANDOM) es OESTE o ESTE, y en vertical si el sentido es NORTE
	 * o SUR. La coordenada pasada por parámetro es la cabeza.
	 * @param coordenada
	 * @param largo
	 * @param sentido
	 */
	public Vibora(int x, int y, int largo) {
		super(new Coordenada(x, y)); // cabeza de la vibora
		
		Coordenada coordenada = new Coordenada(x, y);
		Posicion sentido  = Posicion.values()[new Random().nextInt(4)];
		this.sentido = sentido;
		this.cabeza = new CuerpoVibora(this, x, y);
		
		if (sentido == Posicion.ESTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX() - i, coordenada.getY()));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.OESTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX() + i, coordenada.getY()));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.NORTE) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX(), coordenada.getY() - i));
				this.cuerpos.add(cuerpoVibora);
			}
		}

		if (sentido == Posicion.SUR) {
			for (int i = 0; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(this,
						new Coordenada(coordenada.getX(), coordenada.getY() + i));
				this.cuerpos.add(cuerpoVibora);
			}
		}
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
		return this.cuerpos.getFirst();
	}

	/**
	 * Mueve la vibora hacia el ultimo sentido. Para moverse se elimina la cola y se
	 * agrega un cuerpo delante de la cabeza. El movimiento no implica la quita de
	 * la cola
	 */
	public void cabecear() {

		CuerpoVibora cuerpoVibora = new CuerpoVibora(this, null);
		switch (this.sentido) {
		case ESTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX() + 1, this.getCabeza().getY());
			break;
		case OESTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX() - 1, this.getCabeza().getY());
			break;
		case NORTE:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() + 1);
			break;
		case SUR:
			cuerpoVibora = new CuerpoVibora(this, this.getCabeza().getX(), this.getCabeza().getY() - 1);
			break;
		}
		this.cabeza = cuerpoVibora;
		this.coordenada = this.cabeza.getCoordenada();

		this.cuerpos.addFirst(cuerpoVibora);
	}

	/**
	 * Setea un nuevo sentido si es que puede Si la vibora tiene UN cuerpo (UN
	 * cuadrado/pixel) se puede mover para su direccion contraria
	 * 
	 * Si la vibora tiene MAS DE UN cuerpo (MAS DE UN cuadrado/pixel) NO se puede
	 * mover para su direccion contraria pues "se comerï¿½a su propio cuerpo".
	 * 
	 * @param sentido
	 */
	public void setSentido(Posicion sentido) {

		if (this.cuerpos.size() == 1 || Math.abs(this.sentido.ordinal() - sentido.ordinal()) != 2) {
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
	public LinkedList<CuerpoVibora> getCuerpos() {
		return this.cuerpos;
	}

	/**
	 * Quita la cola si en ese ciclo de juego no va a crecer. Si crece no hace nada.
	 */
	public void crecerOMover() {
		if (!this.crece) {
			this.cuerpos.removeLast();
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
		if (other.getCuerpos().size() != this.getCuerpos().size())
			return false;
		for (int i = 0; i < this.getCuerpos().size(); i++) {
			if (this.getCuerpos().get(i) != other.getCuerpos().get(i))
				return false;
		}
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
