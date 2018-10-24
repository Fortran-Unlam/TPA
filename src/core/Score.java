package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

import core.entidad.Vibora;

public class Score {
	
	public static ArrayList<Puntaje> calcularScore(ArrayList<Vibora> viboras) {
		ArrayList<Puntaje> ranking = new ArrayList<>();
		
		for(Vibora vib: viboras) {
			ranking.add(new Puntaje(vib.getId(), vib.getFrutasComidas()));
		}
		
		Collections.sort(ranking);
		
		return ranking;
	}
	
	public static DefaultListModel<String> ScoreToModel(ArrayList<Puntaje> rank) {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		for(Puntaje p: rank) {
			modelo.addElement(p.toString());
		}
		
		return modelo;
	}
}
