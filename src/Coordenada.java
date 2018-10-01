
public class Coordenada {

	int x;
	int y;

	/**
	 * Crea una coordenada dentro del mapa
	 * 
	 * @param x Posicion horizontal
	 * @param y Posicion vertical
	 */
	public Coordenada(int x, int y) {
		super();
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
}
