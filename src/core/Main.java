package core;

import pool.Partida;

public class Main {

	public static void main(String[] args) {
		Partida partida = new Partida(1);
		
		partida.agregarRonda();
		
		partida.start();
	}
}
