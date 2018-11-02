package cliente.input;

import config.Posicion;

public class GestorInput {

	public Teclado teclado = new Teclado();
	
	public Posicion getUltimaTecla() {
		return this.teclado.ultimaPulsada;
	}
	
	public void setUltimaTecla(Posicion posicion) {
		this.teclado.ultimaPulsada = posicion;
	}
}
