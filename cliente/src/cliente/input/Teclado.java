package cliente.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cliente.Cliente;
import config.Posicion;

public class Teclado implements KeyListener {

	public Posicion ultimaPulsada = null;
	public int teclaArriba = KeyEvent.VK_UP;
	public int teclaAbajo = KeyEvent.VK_DOWN;
	public int teclaIzquierda = KeyEvent.VK_LEFT;
	public int teclaDerecha = KeyEvent.VK_RIGHT;
	

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		if (e.getKeyCode() == teclaArriba) {
			ultimaPulsada = Posicion.SUR;
		}
		
		if (e.getKeyCode() == teclaAbajo) {
			ultimaPulsada = Posicion.NORTE;
		}
		
		if (e.getKeyCode() == teclaIzquierda) {
			ultimaPulsada = Posicion.OESTE;
		}
		
		if (e.getKeyCode() == teclaDerecha) {
			ultimaPulsada = Posicion.ESTE;
		}

		Cliente.getConexionServidor().enviarTecla(ultimaPulsada);
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
