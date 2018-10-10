package core.tests;

import org.junit.Assert;
import org.junit.Test;

import config.Param;
import core.Coordenada;
import core.Vibora;

public class ViboraTest {

	@Test
	public void creacionEnPosicionCorrecta() {
		Coordenada coordenada = new Coordenada(2, 3);
		Vibora vibora = new Vibora(coordenada, Param.POSICION_ESTE);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Coordenada coordenada2 = new Coordenada(4, 1);
		Vibora vibora2 = new Vibora(coordenada2, Param.POSICION_ESTE);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());

	}
	
	@Test
	public void creacionCabezaEnPosicionCorrecta() {
		Coordenada coordenada = new Coordenada(2, 3);
		Vibora vibora = new Vibora(coordenada, Param.POSICION_ESTE);

		Assert.assertEquals(2, vibora.getCabeza().getX());
		Assert.assertEquals(3, vibora.getCabeza().getY());

		Coordenada coordenada2 = new Coordenada(4, 1);
		Vibora vibora2 = new Vibora(coordenada2, Param.POSICION_ESTE);

		Assert.assertEquals(4, vibora2.getCabeza().getX());
		Assert.assertEquals(1, vibora2.getCabeza().getY());

	}
}
