package core.tests;

import org.junit.Assert;
import org.junit.Test;

import config.Posicion;
import core.Coordenada;
import core.CuerpoVibora;
import core.Fruta;
import core.Mapa;
import core.Vibora;

public class MapaTest {

	@Test
	public void TestAgregadoDeFrutas() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Fruta fruta = new Fruta(2, 2);
		mapa.add(fruta);

		Assert.assertEquals(new Coordenada(2, 2), mapa.getFruta(2, 2).getCoordenada());
	}

	@Test
	public void TestAgregadoDeVibora() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(0, 2) };
		Vibora vibora = new Vibora(coordenada);

		mapa.add(vibora);

		// verifico que tenga el mapa tenga el cuerpo
		Assert.assertEquals(new CuerpoVibora(vibora, 0, 2), mapa.getCuerpoVibora(0, 2));

		// y que sea la cabeza pues esta viborita es de un solo cuerpo
		Assert.assertEquals(true, mapa.getCuerpoVibora(0, 2).isCabeza());
	}

	@Test
	public void TestMovimientoSinCambiarDireccionVibora() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(0, 2) };
		Vibora vibora = new Vibora(coordenada, Posicion.ESTE);
		mapa.add(vibora);

		// verifico que tenga el mapa tenga el cuerpo y que sea la cabeza
		Assert.assertEquals(new CuerpoVibora(vibora, 0, 2), mapa.getCuerpoVibora(0, 2));
		Assert.assertEquals(true, mapa.getCuerpoVibora(0, 2).isCabeza());

		// Actualizo el ciclo de juego
		mapa.actualizar();

		// verifico que se haya movido y siga siendo la cabeza
		Assert.assertEquals(new CuerpoVibora(vibora, 1, 2), mapa.getCuerpoVibora(1, 2));
		Assert.assertEquals(true, mapa.getCuerpoVibora(1, 2).isCabeza());

	}

	@Test
	public void TestMovimientoCambiandoDireccionesVibora() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(0, 2) };
		Vibora vibora = new Vibora(coordenada, Posicion.ESTE);
		mapa.add(vibora);

		// Basicamente lo que hago es que de un giro y vuelva al mismo lugar
		Assert.assertEquals(new CuerpoVibora(vibora, 0, 2), mapa.getCuerpoVibora(0, 2));
		// Cambio el sentido y actualizo el ciclo de juego
		vibora.setSentido(Posicion.NORTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 0, 3), mapa.getCuerpoVibora(0, 3));

		vibora.setSentido(Posicion.ESTE);
		mapa.actualizar();
		Assert.assertEquals(new CuerpoVibora(vibora, 1, 3), mapa.getCuerpoVibora(1, 3));

		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();
		Assert.assertEquals(new CuerpoVibora(vibora, 1, 2), mapa.getCuerpoVibora(1, 2));

		// vuelve a su lugar de inicio
		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();
		Assert.assertEquals(new CuerpoVibora(vibora, 0, 2), mapa.getCuerpoVibora(0, 2));
	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaEsteOeste() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(2, 3) };
		Vibora vibora = new Vibora(coordenada, Posicion.ESTE);
		mapa.add(vibora);

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 3), mapa.getCuerpoVibora(2, 3));

		// cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		// moverse en sentido contrario
		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 1, 3), mapa.getCuerpoVibora(1, 3));

	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaOesteEste() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(2, 3) };
		Vibora vibora = new Vibora(coordenada, Posicion.OESTE);
		mapa.add(vibora);

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 3), mapa.getCuerpoVibora(2, 3));

		/*
		 * cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		 * moverse en sentido contrario
		 */
		vibora.setSentido(Posicion.ESTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 3, 3), mapa.getCuerpoVibora(3, 3));

	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaNorteSur() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(2, 3) };
		Vibora vibora = new Vibora(coordenada, Posicion.NORTE);
		mapa.add(vibora);

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 3), mapa.getCuerpoVibora(2, 3));

		// cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		// moverse en sentido contrario
		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 2), mapa.getCuerpoVibora(2, 2));
	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaSurNorte() {
		Mapa mapa = new Mapa(5, 5);
		Coordenada[] coordenada = { new Coordenada(2, 3) };
		Vibora vibora = new Vibora(coordenada, Posicion.SUR);
		mapa.add(vibora);

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 3), mapa.getCuerpoVibora(2, 3));

		/*
		 * cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		 * moverse en sentido contrario
		 */
		vibora.setSentido(Posicion.NORTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 2, 4), mapa.getCuerpoVibora(2, 4));
	}

	@Test
	public void TestViboraMasDeUnCuerpoNoSePuedeMoverEnDireccionContraria() {
		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(3, 1), new Coordenada(3, 2) };
		Vibora vibora = new Vibora(coordenada, Posicion.NORTE);

		mapa.add(vibora);

		Assert.assertEquals(new CuerpoVibora(vibora, 3, 1), mapa.getCuerpoVibora(3, 1));
		Assert.assertEquals(new CuerpoVibora(vibora, 3, 2), mapa.getCuerpoVibora(3, 2));
		Assert.assertEquals(true, mapa.getCuerpoVibora(3, 2).isCabeza());

		/*
		 * le pongo que se mueva al sur pero en el proximo ciclo de maquina no la mueve
		 * al sur sino la mueve en el sentido que venia (norte), porque tiene mas de un
		 * cuerpo y ya no puede moverse para atras.
		 */
		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(vibora, 3, 3), mapa.getCuerpoVibora(3, 3));
		Assert.assertEquals(new CuerpoVibora(vibora, 3, 2), mapa.getCuerpoVibora(3, 2));
		Assert.assertEquals(true, mapa.getCuerpoVibora(3, 3).isCabeza());
	}

	@Test
	public void testViboraComeFruta() {

		// mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(3, 1) };
		Vibora vibora = new Vibora(coordenada, Posicion.NORTE);

		Fruta fruta = new Fruta(3, 2);
		mapa.add(vibora);
		mapa.add(fruta);

		Assert.assertEquals(new CuerpoVibora(vibora, 3, 1), mapa.getCuerpoVibora(3, 1));
		Assert.assertEquals(true, mapa.getCuerpoVibora(3, 1).isCabeza());

		mapa.actualizar(); // actualizo el ciclo

		Assert.assertEquals(new CuerpoVibora(vibora, 3, 2), mapa.getCuerpoVibora(3, 2));
		Assert.assertEquals(true, mapa.getCuerpoVibora(3, 2).isCabeza());
		Assert.assertEquals(new CuerpoVibora(vibora, 3, 1), mapa.getCuerpoVibora(3, 1));
		Assert.assertEquals(false, mapa.getCuerpoVibora(3, 1).isCabeza());

	}

	@Test
	public void seChocanDosDeCabezaViboras() {
		Mapa mapa = new Mapa(4, 5);
		Coordenada[] coordenada = { new Coordenada(3, 1) };
		Vibora vibora = new Vibora(coordenada, Posicion.NORTE);
		mapa.add(vibora);

		Coordenada[] coordenadas = { new Coordenada(3, 4), new Coordenada(3, 3) };
		Vibora vibora2 = new Vibora(coordenadas, Posicion.SUR);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(true, vibora2.getMuerte());
		Assert.assertEquals(true, vibora.getMuerte());
	}
	
	@Test
	public void seChocanDosUnaSobreviveViboras() {
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = { new Coordenada(2, 2) };
		Vibora vibora = new Vibora(coordenada, Posicion.ESTE);
		mapa.add(vibora);

		Coordenada[] coordenadas = { new Coordenada(3, 3), new Coordenada(3, 2) };
		Vibora vibora2 = new Vibora(coordenadas, Posicion.SUR);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(true, vibora.getMuerte());
		Assert.assertEquals(false, vibora2.getMuerte());
	}
	
	@Test
	public void viboraSigueAOtra() {
		Mapa mapa = new Mapa(10, 10);
		Coordenada[] coordenada = { new Coordenada(2, 2),  new Coordenada(3, 2)};
		Vibora vibora = new Vibora(coordenada, Posicion.ESTE);
		mapa.add(vibora);

		Coordenada[] coordenadas = { new Coordenada(4, 2), new Coordenada(5, 2) };
		Vibora vibora2 = new Vibora(coordenadas, Posicion.ESTE);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(false, vibora.getMuerte());
		Assert.assertEquals(false, vibora2.getMuerte());
		
		Assert.assertEquals(6, vibora2.getX());
		Assert.assertEquals(4, vibora.getX());
	}
	
	@Test
	public void viboraMuereChocandoConElMargen() {
		Mapa mapa = new Mapa(4, 4);
		Coordenada[] coordenada = {new Coordenada(0, 1)};
		Vibora vibora = new Vibora(coordenada, Posicion.NORTE);
		mapa.add(vibora);

		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();
		
		Assert.assertEquals(true, vibora.getMuerte());
	}
	
	@Test
	public void fueraDelMapa() {
		Mapa mapa = new Mapa(3,3);
		Assert.assertEquals(false, mapa.estaDentro(0, 3));
		Assert.assertEquals(false, mapa.estaDentro(3, 0));
		Assert.assertEquals(false, mapa.estaDentro(0, 4));
		Assert.assertEquals(false, mapa.estaDentro(4, 0));
		Assert.assertEquals(false, mapa.estaDentro(3, 3));
		Assert.assertEquals(false, mapa.estaDentro(-1, 3));
		Assert.assertEquals(false, mapa.estaDentro(0, -1));
		Assert.assertEquals(true, mapa.estaDentro(0, 0));
	}

	@Test
	public void verSiAgregaViborasDentroDelMapa() {
		
	}
	
	@Test
	public void verSiAgregaFrutasDentroDelMapa() {
		
	}
}
