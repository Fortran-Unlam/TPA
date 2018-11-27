package core.tests;

import org.junit.Assert;
import org.junit.Test;

import config.Posicion;
import core.Coordenada;
import core.entidad.Vibora;

public class ViboraTest {

	@Test
	public void creacionCabezaEnPosicionCorrecta() {
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		Vibora vibora2 = new Vibora(new Coordenada(4, 1), 1);

		Assert.assertEquals(4, vibora2.getX());
		Assert.assertEquals(1, vibora2.getY());
	}

	@Test
	public void movimientoRectoAlEste() {
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.ESTE);

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
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.OESTE);

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
		Vibora vibora = new Vibora(new Coordenada(3, 4), 1, Posicion.SUR);

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
		Vibora vibora = new Vibora(new Coordenada(3, 4), 1, Posicion.NORTE);

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
		Vibora vibora = new Vibora(new Coordenada(3, 4), 3, Posicion.NORTE);

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(4, vibora.getY());

		//Hago que cabecee sin elimiar cola
		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(5, vibora.getY());
		
		vibora.setSentido(Posicion.SUR);
		vibora.cabecear();
		
		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(6, vibora.getY());
	}

	@Test
	public void noIrDeSurANorte() {
		Vibora vibora = new Vibora(new Coordenada(3, 3), 3, Posicion.SUR);

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(3, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(2, vibora.getY());
		vibora.setSentido(Posicion.NORTE);
		vibora.cabecear();

		Assert.assertEquals(3, vibora.getX());
		Assert.assertEquals(1, vibora.getY());
	}

	@Test
	public void noIrDeEsteAOeste() {
		Vibora vibora = new Vibora(new Coordenada(4, 1), 3, Posicion.ESTE);

		Assert.assertEquals(4, vibora.getX());
		Assert.assertEquals(1, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(5, vibora.getX());
		Assert.assertEquals(1, vibora.getY());
		vibora.setSentido(Posicion.OESTE);
		vibora.cabecear();

		Assert.assertEquals(6, vibora.getX());
		Assert.assertEquals(1, vibora.getY());
	}

	@Test
	public void noIrDeOesteAEste() {
		Vibora vibora = new Vibora(new Coordenada(2, 1), 3, Posicion.OESTE);

		Assert.assertEquals(2, vibora.getX());
		Assert.assertEquals(1, vibora.getY());

		vibora.cabecear();

		Assert.assertEquals(1, vibora.getX());
		Assert.assertEquals(1, vibora.getY());
		vibora.setSentido(Posicion.ESTE);
		vibora.cabecear();

		Assert.assertEquals(0, vibora.getX());
		Assert.assertEquals(1, vibora.getY());
	}

}
