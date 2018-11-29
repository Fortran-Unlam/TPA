package cliente.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

import config.Posicion;

public class Teclado implements KeyListener, Serializable {

	private static final long serialVersionUID = 4497479950513572684L;
	public Posicion ultimaPulsada = null;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			ultimaPulsada = Posicion.SUR;
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			ultimaPulsada = Posicion.NORTE;
			
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			ultimaPulsada = Posicion.OESTE;
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			ultimaPulsada = Posicion.ESTE;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
