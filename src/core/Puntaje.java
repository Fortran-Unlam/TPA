package core;

import java.io.Serializable;

public class Puntaje implements Comparable<Puntaje>, Serializable {
	
	private static final long serialVersionUID = 3730006646350110115L;
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
		return nombre + "         " + frutasComidas;
	}

	public String getNombre() {
		return nombre;
	}

	public int getFrutasComidas() {
		return frutasComidas;
	}
	
	
	

}
