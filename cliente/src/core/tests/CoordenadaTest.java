package core.tests;

import org.junit.Assert;
import org.junit.Test;

import core.Coordenada;

public class CoordenadaTest {

	@Test
	public void seCreaDeFormaCorrecta() {
		Coordenada coordenada = new Coordenada(4, 5);
		Assert.assertEquals(4, coordenada.getX());
		Assert.assertEquals(5, coordenada.getY());
	}

	@Test
	public void seModificaDeFormaCorrecta() {
		Coordenada coordenada = new Coordenada(4, 5);
		coordenada.set(6, 7);
		Assert.assertEquals(6, coordenada.getX());
		Assert.assertEquals(7, coordenada.getY());

		coordenada.setX(2).setY(8);

		Assert.assertEquals(2, coordenada.getX());
		Assert.assertEquals(8, coordenada.getY());
	}

	@Test
	public void iguales() {
		Coordenada coordenada = new Coordenada(4, 5);
		Coordenada coordenada2 = new Coordenada(4, 5);

		Assert.assertTrue(coordenada.equals(coordenada2));

		Assert.assertTrue(coordenada2.equals(coordenada));

		coordenada2.setX(5);
		Assert.assertFalse(coordenada2.equals(coordenada));

		coordenada2.setX(4);
		Assert.assertTrue(coordenada2.equals(coordenada));
	}
}
