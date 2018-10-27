package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

public class Score {
	private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
	
	public ArrayList<Jugador> calcularScore() {
		
		Collections.sort(this.jugadores);
		
		return this.jugadores;
	}

	public DefaultListModel<String> ScoreToModel() {
		this.calcularScore();
		DefaultListModel<String> modelo = new DefaultListModel<>();

		for (Jugador jugador : this.jugadores) {
			modelo.addElement(jugador.toString());
		}

		return modelo;
	}

	public void add(ArrayList<Jugador> jugadores) {
		for (Jugador jugador : jugadores) {
			this.jugadores.add(jugador);
		}
	}
}
