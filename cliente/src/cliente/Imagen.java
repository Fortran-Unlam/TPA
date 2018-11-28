package cliente;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import config.Param;

public class Imagen {

	private static BufferedImage bufferedImage;

	/**
	 * Crea una imagen bafareada con tamano de un pixel
	 * 
	 * @param path
	 * @param tamano_pixel
	 * @return Imagen
	 */
	public static BufferedImage cargar(String path, boolean tamano_pixel) {
		Image imagen;
		try {
			imagen = ImageIO.read(ClassLoader.class.getResource(path));
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration();

			if (tamano_pixel) {
				bufferedImage = gc.createCompatibleImage(Param.PIXEL_RESIZE, Param.PIXEL_RESIZE,
						Transparency.TRANSLUCENT);
				Graphics g = bufferedImage.getGraphics();
				g.drawImage(imagen, 0, 0, Param.PIXEL_RESIZE, Param.PIXEL_RESIZE, null);
			} else {
				bufferedImage = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null),
						Transparency.TRANSLUCENT);
				Graphics g = bufferedImage.getGraphics();
				g.drawImage(imagen, 0, 0, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	/**
	 * Crea una imagen buffered
	 * @param path
	 * @return
	 */
	public static BufferedImage cargar(String path) {
		return cargar(path, false);
	}
}
