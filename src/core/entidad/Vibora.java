package core.entidad;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import cliente.input.GestorInput;
import config.Posicion;
import core.Coordenada;

public class Vibora implements Serializable {

	private static final long serialVersionUID = -4700905402985527264L;
	private String nombre;
	private int id;
	private int frutasComidas;
	protected LinkedList<CuerpoVibora> bodies = new LinkedList<CuerpoVibora>();
	private Posicion sentido;
	private CuerpoVibora head;
	private boolean crece = false;
	private boolean muerta = false;

	/**
	 * Crea una vibora con cuerpos en las coordenadas pasadas y con un sentido
	 * random Se puede pasar una coordenada y esta sera la cabeza.
	 * 
	 * @param coordenadas Array de coordenadas donde iran todos los cuerpos
	 */
	public Vibora(Coordenada[] coordenadas) {

		this.head = new CuerpoVibora(coordenadas[0]);

		for (int i = 1; i < coordenadas.length; i++)
			this.bodies.add(new CuerpoVibora(coordenadas[i]));
		this.sentido = Posicion.values()[new Random().nextInt(4)];
	}

	/**
	 * Crea una vibora con cuerpos en las coordenadas pasadas y con un sentido dado
	 * Se puede pasar una coordenada y esta sera la cabeza
	 * 
	 * @param coordenadas Array de coordenadas donde iran todos los cuerpos
	 * @param sentido     indica la direccion a la que quede apuntando la vibora
	 *                    cuando se crea
	 */
	public Vibora(Coordenada[] coordenadas, Posicion sentido) {
		this.head = new CuerpoVibora(coordenadas[0]);

		for (int i = 1; i < coordenadas.length; i++)
			this.bodies.add(new CuerpoVibora(coordenadas[i]));

		this.sentido = Posicion.values()[new Random().nextInt(4)];

		this.sentido = sentido;
	}

	/**
	 * Crea una vibora del largo pasado por parametro en forma horizontal si el
	 * sentido es OESTE o ESTE, y en vertical si el sentido es NORTE o SUR. La
	 * coordenada pasada por par�metro es la cabeza.
	 * 
	 * @param head    indica la coordenada de la cabeza.
	 * @param largo   indica el largo de la vibora a construir
	 * @param sentido indica la direccion a la que quede apuntando la vibora cuando
	 *                se crea
	 */
	public Vibora(Coordenada head, int largo, Posicion sentido) {
		this.head = new CuerpoVibora(head);
		this.sentido = sentido;
		generaCuerpo(largo);
	}

	/**
	 * 
	 * Crea una vibora del largo pasado por parametro en forma horizontal si el
	 * sentido(RANDOM) es OESTE o ESTE, y en vertical si el sentido es NORTE o SUR.
	 * La coordenada pasada por par�metro es la cabeza.
	 * 
	 * @param head  indica la coordenada de la cabeza.
	 * @param largo indica el largo de la vibora a construir
	 */
	public Vibora(Coordenada head, int largo) {
		Posicion sentido = Posicion.values()[new Random().nextInt(4)];
		this.sentido = sentido;
		this.head = new CuerpoVibora(head);
		generaCuerpo(largo);
	}

	/**
	 * Retorna la cabeza de la vibora
	 * 
	 * @return CuerpoVibora
	 */
	public CuerpoVibora getHead() {
		return this.head;
	}

	/**
	 * Agrega un cuerpo de vibora justo detras de la cabeza de la misma, luego el
	 * mapa se encargar� de determinar si le tiene que quitar o no la ultima
	 * posici�n de la lista de cuerpos de vibora.
	 */
	public void cabecear() {
		CuerpoVibora newHead = new CuerpoVibora(null, true);

		switch (this.sentido) {
		case ESTE:
			newHead = new CuerpoVibora(new Coordenada(this.head.getX() + 1, this.head.getY()));
			break;
		case OESTE:
			newHead = new CuerpoVibora(new Coordenada(this.head.getX() - 1, this.head.getY()));
			break;
		case NORTE:
			newHead = new CuerpoVibora(new Coordenada(this.head.getX(), this.head.getY() + 1));
			break;
		case SUR:
			newHead = new CuerpoVibora(new Coordenada(this.head.getX(), this.head.getY() - 1));
			break;
		}

		this.head.setHead(false); // ya no va a ser mas la cabeza
		this.bodies.addFirst(this.head); // ahora es un cuerpo

		newHead.setHead(true);
		this.head = newHead; // y tiene esta nueva cabeza
	}

	/**
	 * Setea un nuevo sentido si es que puede si la vibora tiene unicamente una
	 * cabeza entonces se puede mover para su direccion contraria
	 * 
	 * Si la vibora tiene aunque sea un cuerpo (cabeza + 1 cuerpo = longitud 2) no
	 * puede girar 180 grados,
	 * 
	 * @param sentido
	 */

	public void setSentido(final Posicion sentido) {

		if (this.bodies.size() == 0 || Math.abs(this.sentido.ordinal() - sentido.ordinal()) != 2) {
			this.sentido = sentido;
		}
	}

	private void generaCuerpo(final int largo) {
		if (sentido == Posicion.ESTE) {
			for (int i = 1; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(new Coordenada(head.getX() - i, head.getY()));
				this.bodies.add(cuerpoVibora);
			}
		}

		else if (sentido == Posicion.OESTE) {
			for (int i = 1; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(new Coordenada(head.getX() + i, head.getY()));
				this.bodies.add(cuerpoVibora);
			}
		}

		else if (sentido == Posicion.NORTE) {
			for (int i = 1; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(new Coordenada(head.getX(), head.getY() - i));
				this.bodies.add(cuerpoVibora);
			}
		}

		else if (sentido == Posicion.SUR) {
			for (int i = 1; i < largo; i++) {
				CuerpoVibora cuerpoVibora = new CuerpoVibora(new Coordenada(head.getX(), head.getY() + i));
				this.bodies.add(cuerpoVibora);
			}
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
		return this.bodies;
	}

	/**
	 * Quita la cola si en ese ciclo de juego no va a crecer. Si crece no hace nada.
	 */
	public void removerCola() {
		if (!this.crece) {
			this.bodies.removeLast(); // le saco el cuerpo si es que no crece.
		}
		this.crece = false;
	}

	/**
	 * Marca que crece
	 */
	public void marcarCrecimiento() {
		this.crece = true;
	}

	public boolean isDead() {
		return muerta;
	}

	public void matar() {
		this.muerta = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
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

	/**
	 * Retorna la posici�n X de la cabeza
	 * 
	 * @return coordeanda x de la cabeza
	 */
	public int getX() {
		return this.head.getX();
	}

	/**
	 * Retorna la posici�n Y de la cabeza
	 * 
	 * @return coordeanda Y de la cabeza
	 */
	public int getY() {
		return this.head.getY();
	}

	public Coordenada getCoordenada() {
		return this.head.getCoordenada();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
