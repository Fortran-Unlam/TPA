package core;

import java.util.LinkedList;

public class Obstaculo {
	
	private Coordenada ubicacion;
	
	public Obstaculo(Coordenada ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Obstaculo(int x, int y) {
		this.ubicacion = new Coordenada(x, y);
	}
	
	public int getX() {
		return this.ubicacion.getX();
	}
	
	public int getY() {
		return this.ubicacion.getY();
	}

}
