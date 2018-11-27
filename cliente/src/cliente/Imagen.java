package cliente;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagen {

	public static BufferedImage cargar(String path) {
		Image imagen;
		BufferedImage bufferedImage = null;
		try {
			imagen = ImageIO.read(ClassLoader.class.getResource(path));
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration();

			bufferedImage = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null),
					Transparency.TRANSLUCENT);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(imagen, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}
}
