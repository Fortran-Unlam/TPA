package core.entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import core.Coordenada;

public class Fruta extends JPanel {

	private static final long serialVersionUID = 7895934053840920299L;
	private Coordenada coordenada;
	private boolean fueComida = false;

	public Fruta(Coordenada ubicacion) {
		this.coordenada = ubicacion;
	}

	public Fruta(int x, int y) {
		this.coordenada = new Coordenada(x, y);
	}

	public int getX() {
		return this.coordenada.getX();
	}

	public int getY() {
		return this.coordenada.getY();
	}

	public Coordenada getCoordenada() {
		return this.coordenada;
	}

	public void setFueComida() {
		this.fueComida = true;
	}

	public boolean getFueComida() {
		return this.fueComida;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillRect(this.getX() * 5, this.getY() * 5, 5, 5);
	}
}
