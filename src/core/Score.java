package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

import core.entidad.Vibora;

public class Score {
	private ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Puntaje> ranking = new ArrayList<Puntaje>();

	public ArrayList<Puntaje> calcularScore() {

		ranking = new ArrayList<Puntaje>();

		for (Vibora vib : this.viboras) {
			ranking.add(new Puntaje(vib.getId(), vib.getFrutasComidas()));
		}

		Collections.sort(ranking);

		return ranking;
	}

	public DefaultListModel<String> ScoreToModel() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		for (Puntaje p : ranking) {
			modelo.addElement(p.toString());
		}

		return modelo;
	}

	public void add(ArrayList<Vibora> viboras) {
		for (Vibora vibora : viboras) {
			this.viboras.add(vibora);
		}

	}

}
