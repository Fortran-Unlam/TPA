package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Obstaculo extends JPanel {
	
	private static final long serialVersionUID = 1628026311474738784L;
	
	private Coordenada ubicacion;
	
	public Obstaculo(Coordenada ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Obstaculo(int x, int y) {
		this.ubicacion = new Coordenada(x, y);
	}
	
	public int getX() {
		return this.ubicacion.getX();
	}
	
	public int getY() {
		return this.ubicacion.getY();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ubicacion == null) ? 0 : ubicacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Obstaculo other = (Obstaculo) obj;
		if (ubicacion == null) {
			if (other.ubicacion != null)
				return false;
		} else if (!ubicacion.equals(other.ubicacion))
			return false;
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(this.getX() * 5, this.getY() * 5, 5, 5);
	}

	
	

}
