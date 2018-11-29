package cliente.input;

import config.Posicion;

public class GestorInput {

	
	private Posicion ultimaPulsada;

	public Posicion getUltimaTecla() {
		return this.ultimaPulsada;
	}
	
	public void setUltimaTecla(Posicion posicion) {
		this.ultimaPulsada = posicion;
	}
}
