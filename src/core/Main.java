package core;

import pool.Partida;

public class Main {

	public static void main(String[] args) throws Exception {

		// SalaIndex index = new SalaIndex();
		// Si solo ejecutan la linea de arriba hace el flujo de crear sala y partida y
		// ejecutar el juego pero no me funciona por ahora, me aparece el juego todo
		// negro
		Partida partida = new Partida(1);

		partida.agregarRonda();

		partida.crearJugador("Player 1");

		partida.start();

	}
}
