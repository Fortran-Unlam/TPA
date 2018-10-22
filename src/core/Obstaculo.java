package core;

public class Obstaculo {
	
	private Coordenada ubicacion;
	private int largo;
	
	public Obstaculo(Coordenada ubicacion, int largo) {
		this.ubicacion = ubicacion;
		this.largo = largo;
	}

	public Obstaculo(int x, int y, int largo) {
		this.ubicacion = new Coordenada(x, y);
		this.largo = largo;
	}
	
	public int getX() {
		return this.ubicacion.getX();
	}
	
	public int getY() {
		return this.ubicacion.getY();
	}

}
