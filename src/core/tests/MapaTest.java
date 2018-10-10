package core.tests;


import org.junit.Assert;
import org.junit.Test;

import config.Param;
import core.Coordenada;

import core.Fruta;
import core.Mapa;
import core.Vibora;


public class MapaTest {
	
	@Test
	public void TestAgregadoDeFrutas() {
		//mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Fruta fruta = new Fruta(new Coordenada(2, 2));
		
		mapa.add(fruta);
		
		Assert.assertEquals(new Coordenada(2, 2), mapa.getFruta(2, 2).getCoordenada());							
	}
	
	@Test
	public void TestAgregadoDeVibora() {
		//mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Vibora vibora = new Vibora(new Coordenada(0, 2),Param.POSICION_ESTE);
		
		mapa.add(vibora);
		
		Assert.assertEquals(new Coordenada(0, 2), mapa.getCuerpoVibora(0,2).getCoordenada());							
	}
	
	
	@Test
	public void TestMovimientoVibora() {
		//mapa de 5x5
		Mapa mapa = new Mapa(4, 4);
		Vibora vibora = new Vibora(new Coordenada(0, 2),Param.POSICION_ESTE);
		Fruta fruta = new Fruta(new Coordenada(2, 2));
		mapa.add(vibora);
		mapa.add(fruta);
		
		Assert.assertEquals(new Coordenada(0, 2), mapa.getCuerpoVibora(0,2).getCoordenada());	
		//ahora muevo la vibora (cambiando el sentido) y hago un ciclo de máquina para actualizar el juego
		vibora.setSentido(Param.POSICION_ESTE);
		mapa.actualizar();
		
		//Assert.assertEquals(new Coordenada(0, 2), mapa.getCuerpoVibora(1,2).getCoordenada());	
	}
	


}
