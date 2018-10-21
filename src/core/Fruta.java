package core;

public class Fruta {

	private Coordenada coordenada;
	private boolean fueComida;

	public Fruta(Coordenada ubicacion) {
		this.coordenada = ubicacion;
		this.fueComida = false;
	}

	public Fruta(int x, int y) {
		this.coordenada = new Coordenada(x, y);
		this.fueComida = false;
	}

	public int getX() {
		return this.coordenada.getX();
	}

	public int getY() {
		return this.coordenada.getY();
	}

	public Coordenada getCoordenada() {
		return this.coordenada;
	}

	public void setFueComida() {
		this.fueComida = true;
	}

	public boolean getFueComida() {
		return this.fueComida;
	}

}
