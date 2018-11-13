package config;

public enum Posicion {
	ESTE, SUR, OESTE, NORTE;

	public int rotacion() {
		switch (this) {
		case OESTE:
			return 0;
		case SUR:
			return 90;
		case ESTE:
			return 180;
		case NORTE:
			return 270;
		}
		return 0;
	}
}
