package cliente.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cliente.Main;
import config.Posicion;

public class Teclado implements KeyListener {

	public Posicion ultimaPulsada = null;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			ultimaPulsada = Posicion.SUR;
			Main.getConexionServidor().enviarTecla(Posicion.SUR);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			ultimaPulsada = Posicion.NORTE;
			Main.getConexionServidor().enviarTecla(Posicion.NORTE);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			ultimaPulsada = Posicion.OESTE;
			Main.getConexionServidor().enviarTecla(Posicion.OESTE);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			ultimaPulsada = Posicion.ESTE;
			Main.getConexionServidor().enviarTecla(Posicion.ESTE);
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		default:
			System.out.print(e.getKeyCode() + " " + KeyEvent.getKeyText(e.getKeyCode()) + "\n");
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
