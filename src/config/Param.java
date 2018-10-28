package config;

public abstract class Param {

	public static final int VENTANA_MAPA_WIDTH = 600;
	public static final int VENTANA_MAPA_HEIGHT = 700;
	
	public static final int BOTON_WIDTH = 120;
	public static final int BOTON_HEIGHT = 40;
	
	public static final int VENTANA_CLIENTE_WIDTH = 500;
	public static final int VENTANA_CLIENTE_HEIGHT = 400;
	
	public static final int VENTANA_SALA_WIDTH = 600;
	public static final int VENTANA_SALA_HEIGHT = 450;

	public static final int MAPA_WIDTH = 600;
	public static final int MAPA_HEIGHT = 600;

	public static final int PIXEL_RESIZE = 5;

	public static final int MAPA_MAX_X = MAPA_WIDTH / PIXEL_RESIZE;
	public static final int MAPA_MAX_Y = MAPA_HEIGHT / PIXEL_RESIZE;

	public static final int PUERTO = 9000;
	public static final int MAXIMASCONEXIONESIMULTANEAS = 100;
	public static final String HOST = "localhost";
}
