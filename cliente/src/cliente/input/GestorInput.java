package cliente.input;

import java.io.Serializable;
import config.Posicion;

public class GestorInput implements Serializable {

	private static final long serialVersionUID = 7550591053335557972L;
	public Teclado teclado = new Teclado();
	public Joystick joystick = new Joystick();
	
	public Posicion getUltimaTecla() {
		if (joystick.isActive()) {
			return this.joystick.ultimaPulsada;			
		}
		return this.teclado.ultimaPulsada;
	}
	
	public void setUltimaTecla(Posicion posicion) {
		if (joystick.isActive()) {
			this.joystick.ultimaPulsada = posicion;			
		}
		this.teclado.ultimaPulsada = posicion;
	}
}
