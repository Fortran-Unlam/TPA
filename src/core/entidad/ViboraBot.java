package core.entidad;

import java.util.Random;

import config.Posicion;
import core.Coordenada;

public class ViboraBot extends Vibora {

	private static final long serialVersionUID = -6266915663507093207L;

	public ViboraBot(Coordenada head) {
		super(head, 10);
	}

	/**
	 * Cambio de sentido un 35 por ciento de las veces. Mayormente va en el mismo
	 * sentido
	 */
	public void determinarMovimiento() {
		if (new Random().nextFloat() > 0.65) {
			this.setSentido(Posicion.values()[new Random().nextInt(Posicion.values().length)]);
		}
	}

	/**
	 * Dibuja la cabeza y luego el cuerpo
	 */
/*	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);
		g2d.fillRect(this.getX() * Param.PIXEL_RESIZE, this.getY() * Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
				Param.PIXEL_RESIZE);

		for (CuerpoVibora cuerpoVibora : this.bodies) {
			cuerpoVibora.paint(g2d);
		}
	}
	*/
}
