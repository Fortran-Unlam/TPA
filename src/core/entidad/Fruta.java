package core.entidad;

import org.codehaus.jackson.annotate.JsonIgnore;

import core.Coordenada;

public class Fruta{

	private Coordenada coordenada;
	private boolean fueComida = false;

	public Fruta(Coordenada ubicacion) {
		this.coordenada = ubicacion;
	}
	
	public Fruta() {}

	public Fruta(int x, int y) {
		this.coordenada = new Coordenada(x, y);
	}
	@JsonIgnore
	public int getX() {
		return this.coordenada.getX();
	}

	@JsonIgnore
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
