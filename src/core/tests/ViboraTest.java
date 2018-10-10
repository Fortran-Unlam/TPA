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
		Vibora vibora = new Vibora(coordenada);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Coordenada coordenada2 = new Coordenada(4, 1);
		Vibora vibora2 = new Vibora(coordenada2);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());

	}

	@Test
	public void creacionCabezaEnPosicionCorrecta() {
		Coordenada coordenada = new Coordenada(2, 3);
		Vibora vibora = new Vibora(coordenada);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Coordenada coordenada2 = new Coordenada(4, 1);
		Vibora vibora2 = new Vibora(coordenada2);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());
	}

	@Test
	public void movimientoRectoAlEste() {
		Coordenada coordenada = new Coordenada(2, 3);
		Vibora vibora = new Vibora(coordenada, Param.POSICION_ESTE);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(4, vibora.getX());
		Assert.assertEquals(3, vibora.getY());
	}

	@Test
	public void movimientoRectoAlOeste() {
		Coordenada coordenada = new Coordenada(2, 3);
		Vibora vibora = new Vibora(coordenada, Param.POSICION_OESTE);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(1, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(0, vibora.getX());
		Assert.assertEquals(3, vibora.getY());
	}
}
