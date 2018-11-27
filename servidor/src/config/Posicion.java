package config;

public enum Posicion {
	ESTE, SUR, OESTE, NORTE;

	public static double rotacion(int posicion) {
		switch (Posicion.values()[posicion % Posicion.values().length]) {
		case OESTE:
			return 0;
		case SUR:
			return Math.PI/2;
		case ESTE:
			return Math.PI;
		case NORTE:
			return Math.PI/2*3;
		}
		return 0;
	}
}
