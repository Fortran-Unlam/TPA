package core.entidad;

import javax.json.Json;
import javax.json.JsonObject;

import core.Coordenada;

public class Fruta implements Coordenable {

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

	public JsonObject toJson() {
		return Json.createObjectBuilder().add("x", this.getX()).add("y", this.getY()).build();
	}
}
