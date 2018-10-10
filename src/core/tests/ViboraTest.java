package core.tests;

import org.junit.Assert;
import org.junit.Test;

import config.Param;
import core.Coordenada;
import core.Vibora;

public class ViboraTest {

	@Test
	public void creacionEnPosicionCorrecta() {
		Coordenada[] coordenada = {new Coordenada(2, 3)};
		Vibora vibora = new Vibora(coordenada);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Coordenada[] coordenada2 = {new Coordenada(4, 1)};
		Vibora vibora2 = new Vibora(coordenada2);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());

	}

	@Test
	public void creacionCabezaEnPosicionCorrecta() {
		Coordenada[] coordenada = {new Coordenada(2, 3)};
		Vibora vibora = new Vibora(coordenada);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Coordenada[] coordenada2 = {new Coordenada(4, 1)};
		Vibora vibora2 = new Vibora(coordenada2);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());
	}

	@Test
	public void movimientoRectoAlEste() {
		Coordenada[] coordenada = {new Coordenada(2, 3)};
		Vibora vibora = new Vibora(coordenada, Param.POSICION.ESTE);

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
		Coordenada[] coordenada = {new Coordenada(2, 3)};
		Vibora vibora = new Vibora(coordenada, Param.POSICION.OESTE);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(1, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(0, vibora.getX());
		Assert.assertEquals(3, vibora.getY());
	}

	@Test
	public void movimientoRectoAlSur() {
		Coordenada[] coordenada = {new Coordenada(3, 4)};
		Vibora vibora = new Vibora(coordenada, Param.POSICION.SUR);

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(4, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(2, vibora.getY());
	}

	@Test
	public void movimientoRectoAlNorte() {
		Coordenada[] coordenada = {new Coordenada(3, 4)};
		Vibora vibora = new Vibora(coordenada, Param.POSICION.NORTE) ;

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(4, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(5, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(6, vibora.getY());
	}

	@Test
	public void noIrDeNorteASur() {
		Coordenada[] coordenadas = { new Coordenada(1, 4), new Coordenada(2, 4), new Coordenada(3, 4) };
		Vibora vibora = new Vibora(coordenadas, Param.POSICION.NORTE);

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(4, vibora.getY());
		
		// hago que cabecee sin elimiar cola
		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(5, vibora.getY());
		vibora.setSentido(Param.POSICION.SUR);
		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(6, vibora.getY());
	}
}
