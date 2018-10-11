package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Fruta extends Entidad {

	private static final long serialVersionUID = -8137854050781755054L;

	public Fruta(Coordenada coordenada) {
		super(coordenada);
	}
	
	public Fruta(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillRect(this.getX(), this.getY(), 10, 10);
	}
}
