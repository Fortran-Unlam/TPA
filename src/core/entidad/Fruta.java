package core.entidad;

import java.io.Serializable;

import core.Coordenada;

public class Fruta implements Serializable {

	private static final long serialVersionUID = 7895934053840920299L;
	private Coordenada coordenada;
	private boolean fueComida = false;

	public Fruta(Coordenada ubicacion) {
		this.coordenada = ubicacion;
	}

	public Fruta(int x, int y) {
		this.coordenada = new Coordenada(x, y);
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

	public String toJson() {
		return "{x:" + this.getX() + ",y:" + this.getX() + "}";
	}
}
