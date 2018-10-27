package core.mapa;

import java.util.List;

import core.Jugador;

public class Ronda {

	private int id;
	private VentanaJuego ventanaJuego;
	private Mapa mapa;
	
	public void iniciarRonda(List<Jugador> jugadores) {
		this.mapa = new MapaUno();	//por ahora lo dejo harcodeado
		this.ventanaJuego = new VentanaJuego(jugadores, mapa);
		this.mapa.setVisible(true);
	}
	
	
	
}
