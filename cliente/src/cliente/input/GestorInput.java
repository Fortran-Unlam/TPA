package cliente.input;

import java.io.Serializable;

import config.Posicion;

public class GestorInput implements Serializable {

	private static final long serialVersionUID = 7550591053335557972L;
	public Teclado teclado = new Teclado();
	public Teclado joystick = new Teclado();
	
	public void GestorInput() {
		
	}
	
	public Posicion getUltimaTecla() {
		return this.teclado.ultimaPulsada;
	}
	
	public void setUltimaTecla(Posicion posicion) {
		this.teclado.ultimaPulsada = posicion;
	}
}
