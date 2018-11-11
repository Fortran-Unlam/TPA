package core;

public class Puntaje implements Comparable<Puntaje> {

	private String nombre;
	private int frutasComidas;

	public Puntaje(String nombre, int frutasComidas) {
		this.nombre = nombre;
		this.frutasComidas = frutasComidas;
	}

	@Override
	public int compareTo(Puntaje otro) {
		return otro.frutasComidas - this.frutasComidas;
	}

	@Override
	public String toString() {
		return nombre + ":" + frutasComidas;
	}

	public String getNombre() {
		return nombre;
	}

	public int getFrutasComidas() {
		return frutasComidas;
	}

}
