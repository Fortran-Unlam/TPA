package core;

import java.util.List;

import config.Param;

public class Vibora {
	private int frutasComidas;
	private List<CuerpoVibora> cuerpos;
	private int sentido;
	private CuerpoVibora cabeza;

	/**
	 * Consigue la cabeza que está en la ultima posicion
	 * Asume que la cabeza se actualiza en cada movimiento
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
		CuerpoVibora cv = new CuerpoVibora(null);
		switch (this.sentido) {
		case Param.POSICION_ESTE:
			cv = new CuerpoVibora(new Coordenada(this.getCabeza().getCoordenada().getX(),
					this.getCabeza().getCoordenada().getY() + 1));
			break;
		case Param.POSICION_OESTE:
			cv = new CuerpoVibora(new Coordenada(this.getCabeza().getCoordenada().getX(),
					this.getCabeza().getCoordenada().getY() - 1));
			break;
		case Param.POSICION_NORTE:
			cv = new CuerpoVibora(new Coordenada(this.getCabeza().getCoordenada().getX() + 1,
					this.getCabeza().getCoordenada().getY()));
			break;
		case Param.POSICION_SUR:
			cv = new CuerpoVibora(new Coordenada(this.getCabeza().getCoordenada().getX() - 1,
					this.getCabeza().getCoordenada().getY() + 1));
			break;
		}
		this.cabeza = cv;
		this.cuerpos.add(cv);
		this.cuerpos.remove(0);

	}

	/**
	 * Mueve la vibora habia el sentido dado
	 * 
	 * @param sentido
	 */
	public void mover(int sentido) {
		this.sentido = sentido;
		this.mover();
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
	 * No elimina la cola al moverse
	 */
	public void crecer() {

	}

	/**
	 * Devuelve la cantidad de frutas comidas
	 * 
	 * @return El numero de frutas comidas
	 */
	public int getFrutasComidas() {
		return this.frutasComidas;
	}
}
