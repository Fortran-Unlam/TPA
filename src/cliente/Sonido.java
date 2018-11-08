package cliente;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sonido {

	private Clip sonido;
	
	public Sonido(final String ruta) {
		
		try {
			InputStream is = ClassLoader.class.getResourceAsStream(ruta);
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			this.sonido = (Clip) AudioSystem.getLine(info);
			this.sonido.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reproducir() {
		this.sonido.stop();
		this.sonido.flush();
		this.sonido.setMicrosecondPosition(0);
		this.sonido.start();
	}
	
	public void repetir() {
		this.sonido.stop();
		this.sonido.flush();
		this.sonido.setMicrosecondPosition(0);
		
		this.sonido.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
