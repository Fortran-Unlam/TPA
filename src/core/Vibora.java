package core;

public class Vibora {
	private int frutasComidas;
	private CuerpoVibora[] cuerpos;
	private int sentido;

	/**
	 * Mueve la vibora hacia el ultimo sentido. Para moverse se elimina la cola y se
	 * agrega un cuerpo delante de la cabeza
	 */
	public void mover() {

	}

	/**
	 * Mueve la vibora habia el sentido dado
	 * 
	 * @param sentido
	 */
	public void mover(int sentido) {

	}

	/**
	 * Verifica si choca con algo del mapa
	 * 
	 * @return true si choca
	 */
	public boolean choque() {
		// ver si devolver true o devolver una entidad con la que colisiona
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
