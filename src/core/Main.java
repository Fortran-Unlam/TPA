package core;

import pool.Partida;

public class Main {

	public static void main(String[] args) throws Exception {
		Partida partida = new Partida(1);
		
		partida.agregarRonda();
		
		partida.crearJugador("Player 1");
		
		partida.start();
	}
}
