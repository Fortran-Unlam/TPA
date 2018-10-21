package core;

public class Fruta {

	private Coordenada ubicacion;
	private boolean fueComida;

	public Fruta(Coordenada ubicacion) {
		this.ubicacion = ubicacion;
		this.fueComida = false;
	}

	public Fruta(int x, int y) {
		this.ubicacion = new Coordenada(x, y);
		this.fueComida = false;
	}

	public int getX() {
		return this.ubicacion.getX();
	}

	public int getY() {
		return this.ubicacion.getY();
	}

	public Coordenada getUbicacion() {
		return this.ubicacion;
	}

	public void setFueComida() {
		this.fueComida = true;
	}

	public boolean getFueComida() {
		return this.fueComida;
	}

}
