package core.tests;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import config.Posicion;
import core.Coordenada;
import core.Muro;
import core.Puntaje;
import core.entidad.CuerpoVibora;
import core.entidad.Fruta;
import core.entidad.Obstaculo;
import core.entidad.Vibora;
import core.mapa.Mapa;

public class MapaTest {

	@Test
	public void TestAgregadoDeFrutas() {
		Mapa mapa = new Mapa(5, 5);
		Fruta fruta = new Fruta(2, 2);
		mapa.add(fruta);

		Assert.assertEquals(new Coordenada(2, 2), mapa.getFruta(2, 2).getCoordenada());
	}

	@Test
	public void TestAgregadoDeVibora() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(0, 2), 1);

		mapa.add(vibora);

		// verifico que tenga el mapa tenga el cuerpo
		Assert.assertEquals(vibora, mapa.getJugador(0, 2).getVibora());

		// y que sea la cabeza pues esta viborita es de un solo cuerpo
		Assert.assertEquals(new Coordenada(0, 2), mapa.getJugador(0, 2).getVibora().getHead().getCoordenada());
	}

	@Test
	public void TestMovimientoSinCambiarDireccionVibora() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(0, 2), 1, Posicion.ESTE);
		mapa.add(vibora);

		// Actualizo el ciclo de juego
		mapa.actualizar();

		// verifico que se haya movido y siga siendo la cabeza
		Assert.assertEquals(vibora, mapa.getJugador(1, 2).getVibora());
		Assert.assertEquals(new Coordenada(1, 2), mapa.getJugador(1, 2).getVibora().getHead().getCoordenada());

	}

	@Test
	public void TestMovimientoCambiandoDireccionesVibora() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(0, 2), 1, Posicion.ESTE);
		mapa.add(vibora);

		// Basicamente lo que hago es que de un giro y vuelva al mismo lugar
		Assert.assertEquals(vibora, mapa.getJugador(0, 2).getVibora());
		// Cambio el sentido y actualizo el ciclo de juego
		vibora.setSentido(Posicion.NORTE);
		mapa.actualizar();

		Assert.assertEquals(vibora, mapa.getJugador(0, 3).getVibora());

		vibora.setSentido(Posicion.ESTE);
		mapa.actualizar();
		Assert.assertEquals(vibora, mapa.getJugador(1, 3).getVibora());

		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();
		Assert.assertEquals(vibora, mapa.getJugador(1, 2).getVibora());

		// vuelve a su lugar de inicio
		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();
		Assert.assertEquals(vibora, mapa.getJugador(0, 2).getVibora());
	}

	@Test
	public void vivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaEsteOeste() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.ESTE);
		mapa.add(vibora);

		// cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		// moverse en sentido contrario
		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(1, 3), mapa.getJugador(1, 3).getVibora().getHead());

	}

	@Test
	public void vivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaOesteEste() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.OESTE);
		mapa.add(vibora);

		/*
		 * cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		 * moverse en sentido contrario
		 */
		vibora.setSentido(Posicion.ESTE);
		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(3, 3), mapa.getJugador(3, 3).getVibora().getHead());
		Assert.assertEquals(true, mapa.getJugador(3, 3).getVibora().getCuerpos().isEmpty());
	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaNorteSur() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.NORTE);
		mapa.add(vibora);

		// cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		// moverse en sentido contrario
		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();

		Assert.assertEquals(vibora, mapa.getJugador(2, 2).getVibora());
	}

	@Test
	public void TestVivoraDeUnSoloCuerpoSePuedeMoverEnDireccionContrariaSurNorte() {
		MapaStub mapa = new MapaStub(6, 6);
		Vibora vibora = new Vibora(new Coordenada(2, 3), 1, Posicion.SUR);
		mapa.add(vibora);

		Assert.assertEquals(vibora, mapa.getJugador(2, 3).getVibora());

		/*
		 * cambio de este a oeste y como tiene una sola unidad en su cuerpo puede
		 * moverse en sentido contrario
		 */
		vibora.setSentido(Posicion.NORTE);
		mapa.actualizar();

		Assert.assertEquals(vibora, mapa.getJugador(2, 4).getVibora());
	}

	@Test
	public void viboraMasDeUnCuerpoNoSePuedeMoverEnDireccionContraria() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(3, 2), 2, Posicion.NORTE);

		mapa.add(vibora);

		Assert.assertEquals(vibora, mapa.getJugador(3, 1).getVibora());
		Assert.assertEquals(vibora, mapa.getJugador(3, 2).getVibora());
		Assert.assertEquals(new Coordenada(3, 2), mapa.getJugador(3, 2).getVibora().getHead().getCoordenada());

		/*
		 * le pongo que se mueva al sur pero en el proximo ciclo de maquina no la mueve
		 * al sur sino la mueve en el sentido que venia (norte), porque tiene mas de un
		 * cuerpo y ya no puede moverse para atras.
		 */
		vibora.setSentido(Posicion.SUR);
		mapa.actualizar();

		Assert.assertEquals(vibora, mapa.getJugador(3, 3).getVibora());
		Assert.assertEquals(vibora, mapa.getJugador(3, 2).getVibora());
		Assert.assertEquals(new Coordenada(3, 3), mapa.getJugador(3, 3).getVibora().getCoordenada());
	}

	@Test
	public void testViboraComeFruta() {

		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(3, 1), 1, Posicion.NORTE);

		Fruta fruta = new Fruta(3, 2);
		// TODO: hay que hacer un mapa maniqui para que agregue jugadores o viboras en
		// lugares especificos solo para el test
		mapa.add(vibora);
		mapa.add(fruta);

		Assert.assertEquals(new CuerpoVibora(3, 1, true), mapa.getJugador(3, 1).getVibora().getHead());
		Assert.assertEquals(vibora, mapa.getJugador(3, 1).getVibora());

		mapa.actualizar();

		Assert.assertEquals(new CuerpoVibora(3, 2, true), mapa.getJugador(3, 2).getVibora().getHead());
		Assert.assertEquals(vibora, mapa.getJugador(3, 2).getVibora());

		Assert.assertEquals(new CuerpoVibora(3, 1), mapa.getJugador(3, 1).getVibora().getCuerpos().getLast());
		Assert.assertEquals(vibora, mapa.getJugador(3, 1).getVibora());

	}

	@Test
	public void seChocanDosDeCabezaViboras() {
		MapaStub mapa = new MapaStub(6, 6);
		Vibora vibora = new Vibora(new Coordenada(3, 1), 1, Posicion.NORTE);
		mapa.add(vibora);

		Vibora vibora2 = new Vibora(new Coordenada(3, 3), 2, Posicion.SUR);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(true, vibora2.isDead());
		Assert.assertEquals(true, vibora.isDead());
	}

	@Test
	public void seChocanDosUnaSobreviveViboras() {
		MapaStub mapa = new MapaStub(4, 4);
		Vibora vibora = new Vibora(new Coordenada(2, 2), 1, Posicion.ESTE);
		mapa.add(vibora);

		Vibora vibora2 = new Vibora(new Coordenada(3, 2), 2, Posicion.SUR);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(true, vibora.isDead());
		Assert.assertEquals(false, vibora2.isDead());
	}

	@Test
	public void viboraSigueAOtra() {
		MapaStub mapa = new MapaStub(10, 10);
		Vibora vibora = new Vibora(new Coordenada(3, 2), 2, Posicion.ESTE);
		mapa.add(vibora);

		Vibora vibora2 = new Vibora(new Coordenada(5, 2), 2, Posicion.ESTE);
		mapa.add(vibora2);

		mapa.actualizar();
		Assert.assertEquals(false, vibora.isDead());
		Assert.assertEquals(false, vibora2.isDead());

		Assert.assertEquals(6, vibora2.getX());
		Assert.assertEquals(4, vibora.getX());
	}

	@Test
	public void viboraMuereChocandoConElMargen() {
		MapaStub mapa = new MapaStub(4, 4);
		Vibora vibora = new Vibora(new Coordenada(0, 1), 1, Posicion.NORTE);
		mapa.add(vibora);

		vibora.setSentido(Posicion.OESTE);
		mapa.actualizar();

		Assert.assertEquals(true, vibora.isDead());
	}

	@Test
	public void fueraDelMapa() {
		Mapa mapa = new Mapa(3, 3);
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(0, 3)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(3, 0)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(0, 4)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(4, 0)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(3, 3)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(-1, 3)));
		Assert.assertEquals(false, mapa.estaDentro(new Coordenada(0, -1)));
		Assert.assertEquals(true, mapa.estaDentro(new Coordenada(0, 0)));
	}

	@Test
	public void verSiAgregaViborasFueraDelMapaCoordenadaY() {
		MapaStub mapa = new MapaStub(4, 2);
		Vibora vibora = new Vibora(new Coordenada(4, 3), 1, Posicion.NORTE);

		Assert.assertEquals(false, mapa.add(vibora));

	}

	@Test
	public void verSiAgregaViborasFueraDelMapaCoordenadaX() {
		MapaStub mapa = new MapaStub(4, 2);
		Vibora vibora = new Vibora(new Coordenada(5, 3), 1, Posicion.NORTE);

		Assert.assertEquals(false, mapa.add(vibora));

	}

	@Test
	public void verSiAgregaFrutasFueraDelMapaCoordenadaX() {
		Mapa mapa = new Mapa(10, 4);
		Fruta fruta = new Fruta(new Coordenada(11, 1));

		Assert.assertEquals(false, mapa.add(fruta));
	}

	@Test
	public void verSiAgregaFrutasFueraDelMapaCoordenadaY() {
		Mapa mapa = new Mapa(10, 4);
		Fruta fruta = new Fruta(new Coordenada(1, 5));

		Assert.assertEquals(false, mapa.add(fruta));
	}

	@Test
	public void verSiAgregaViboraDondeHabiaAlgo() {
		MapaStub mapa = new MapaStub(4, 3);
		Vibora vibora = new Vibora(new Coordenada(2, 1), 3, Posicion.ESTE);
		Fruta fruta = new Fruta(1, 1);
		Assert.assertEquals(true, mapa.add(fruta));
		Assert.assertEquals(false, mapa.add(vibora));
	}

	@Test
	public void verSiAgregaFrutaDondeHabiaAlgo() {
		MapaStub mapa = new MapaStub(4, 2);
		Vibora vibora = new Vibora(new Coordenada(2, 1), 3, Posicion.ESTE);
		Fruta fruta = new Fruta(1, 1);

		Assert.assertEquals(true, mapa.add(vibora));
		Assert.assertEquals(false, mapa.add(fruta));

	}

	@Test
	public void cargarObstaculo() {
		Mapa mapa = new Mapa(5, 5);
		Obstaculo obstaculo = new Obstaculo(new Coordenada(4, 4));

		Assert.assertEquals(true, mapa.add(obstaculo));
	}

	@Test
	public void obstaculoEncimaDeVibora() {
		MapaStub mapa = new MapaStub(5, 5);
		Vibora vibora = new Vibora(new Coordenada(2, 2), 3, Posicion.ESTE);
		Obstaculo obstaculo = new Obstaculo(new Coordenada(2, 2));

		mapa.add(vibora);

		Assert.assertEquals(false, mapa.add(obstaculo));
	}

	@Test
	public void obstaculoEncimaDeFruta() {
		Mapa mapa = new Mapa(5, 5);
		Fruta fruta = new Fruta(new Coordenada(2, 2));
		Obstaculo obstaculo = new Obstaculo(new Coordenada(2, 2));

		mapa.add(fruta);

		Assert.assertEquals(false, mapa.add(obstaculo));
	}

	@Test
	public void obstaculoEncimaDeObstaculo() {
		Mapa mapa = new Mapa(5, 5);
		Obstaculo obstaculo1 = new Obstaculo(new Coordenada(2, 2));
		Obstaculo obstaculo2 = new Obstaculo(new Coordenada(2, 2));

		mapa.add(obstaculo1);

		Assert.assertEquals(false, mapa.add(obstaculo2));
	}

	@Test
	public void viboraContraObstaculo() {
		MapaStub mapa = new MapaStub(5, 5);
		Obstaculo obstaculo = new Obstaculo(new Coordenada(2, 2));
		Vibora vibora = new Vibora(new Coordenada(1, 2), 2, Posicion.ESTE);

		mapa.add(vibora);
		mapa.add(obstaculo);

		Assert.assertEquals(false, vibora.isDead()); // vibora viba

		mapa.actualizar();

		Assert.assertEquals(true, vibora.isDead()); // vibora muerta

	}

	@Test
	public void viboraContraObstaculoCambiandoDireccion() {
		MapaStub mapa = new MapaStub(5, 5);
		Obstaculo obstaculo = new Obstaculo(new Coordenada(1, 3));
		Vibora vibora = new Vibora(new Coordenada(1, 2), 2, Posicion.ESTE);

		mapa.add(vibora);
		mapa.add(obstaculo);

		Assert.assertEquals(false, vibora.isDead()); // vibora viba
		vibora.setSentido(Posicion.NORTE);

		mapa.actualizar();

		Assert.assertEquals(true, vibora.isDead()); // vibora muerta

	}

	@Test
	public void viboraContraMargenConObstaculos() {
		MapaStub mapa = new MapaStub(5, 5);
		Obstaculo obstaculo = new Obstaculo(new Coordenada(1, 3));
		Vibora vibora = new Vibora(new Coordenada(4, 3), 2, Posicion.ESTE);

		mapa.add(vibora);
		mapa.add(obstaculo);
		mapa.actualizar();
	}

	@Test
	public void cargarMuro() {
		Mapa mapa = new Mapa(5, 5);
		LinkedList<Obstaculo> piedras = new LinkedList<>();
		// Vibora vibora = new Vibora(new Coordenada(3,2),3,Posicion.NORTE);

		for (int i = 2; i <= 4; i++) {
			piedras.add(new Obstaculo(new Coordenada(i, 3)));
		}

		Muro pared = new Muro(piedras);

		mapa.add(pared);

		Assert.assertEquals(new Coordenada(2, 3), mapa.getObstaculo(2, 3).getUbicacion());
		Assert.assertEquals(new Coordenada(3, 3), mapa.getObstaculo(3, 3).getUbicacion());
		Assert.assertEquals(new Coordenada(4, 3), mapa.getObstaculo(4, 3).getUbicacion());
	}

	@Test
	public void viboraMuereContraMuro() {
		MapaStub mapa = new MapaStub(5, 5);
		LinkedList<Obstaculo> piedras = new LinkedList<>();
		Vibora vibora = new Vibora(new Coordenada(3, 2), 3, Posicion.NORTE);

		for (int i = 2; i <= 4; i++) {
			piedras.add(new Obstaculo(new Coordenada(i, 3)));
		}

		Muro pared = new Muro(piedras);

		mapa.add(pared);
		mapa.add(vibora);
		mapa.actualizar();

		Assert.assertEquals(new Coordenada(2, 3), mapa.getObstaculo(2, 3).getUbicacion());
		Assert.assertEquals(new Coordenada(3, 3), mapa.getObstaculo(3, 3).getUbicacion());
		Assert.assertEquals(new Coordenada(4, 3), mapa.getObstaculo(4, 3).getUbicacion());
		Assert.assertEquals(true, vibora.isDead());
	}

	@Test
	public void testRanking() {
		// Dos viboras comen frutas, una come 3 y la otra 1. Pruebo que el ranking
		// funcione ok
		MapaStub mapa = new MapaStub(6, 6);
		Vibora vibA = new Vibora(new Coordenada(1, 3), 1, Posicion.ESTE);
		Vibora vibB = new Vibora(new Coordenada(1, 2), 1, Posicion.ESTE);
		Fruta fr1 = new Fruta(2, 3);
		Fruta fr2 = new Fruta(3, 3);
		Fruta fr3 = new Fruta(4, 3);

		mapa.add(vibA);
		mapa.add(vibB);

		mapa.add(fr1);
		mapa.add(fr2);
		mapa.add(fr3);

		mapa.actualizar();
		mapa.actualizar();
		mapa.add(new Fruta(4, 2));
		mapa.actualizar();

		ArrayList<Puntaje> rank = mapa.scoring();
		System.out.println("Vibora  Puntaje");
		for (Puntaje p : rank) {
			System.out.println(p);
		}
	}

}
