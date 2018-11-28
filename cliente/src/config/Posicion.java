package config;

public enum Posicion {
	ESTE, SUR, OESTE, NORTE;

	public static double rotacion(int posicion) {
		switch (Posicion.values()[posicion % Posicion.values().length]) {
		case OESTE:
			return 0;
		case SUR:
			return Math.PI / 2;
		case ESTE:
			return Math.PI;
		case NORTE:
			return Math.PI / 2 * 3;
		}
		return 0;
	}

	/**
	 * Determina la posicion en la que se encuentra la primera coordenada respecto
	 * de la segundo
	 * 
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @return Retorna la posicion de la primera. Si esta en diagonal retorna ESTE
	 */
	public static Posicion getPosicion(int x, int y, int x2, int y2) {
		if (x == x2) {
			if (y < y2) {
				return Posicion.ESTE;
			} else {
				return Posicion.OESTE;
			}
		}
		if (y == y2) {
			if (x < x2) {
				return Posicion.NORTE;
			} else {
				return Posicion.SUR;
			}
		}
		
		return Posicion.ESTE;
	}
}
