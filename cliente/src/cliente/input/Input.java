package cliente.input;

import config.Posicion;

public class Input  {

	public Teclado teclado = new Teclado();
//	public Joystick joystick = new Joystick();
	private DispositivoEntrada dispositivo = null;
	
	public Input() {
//		if (joystick.isActive()) {
//			joystick.run();
//			this.dispositivo = joystick;
//		} else {
//			this.dispositivo = teclado;
//		}
	}
	
	public Posicion getUltimaTecla() {
		return this.dispositivo.getUltimaPulsada();			
	}
	
	public void setUltimaTecla(Posicion posicion) {
		this.dispositivo.setUltimaPulsada(posicion);
	}
}
