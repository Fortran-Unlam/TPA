package core;

import java.util.ArrayList;
import java.util.Collections;

public class Score {
	
	public static ArrayList<Puntaje> calcularScore(ArrayList<Vibora> viboras) {
		ArrayList<Puntaje> ranking = new ArrayList<>();
		
		for(Vibora vib: viboras) {
			ranking.add(new Puntaje(vib.getId(), vib.getFrutasComidas()));
		}
		
		Collections.sort(ranking);
		
		return ranking;
	}
}
