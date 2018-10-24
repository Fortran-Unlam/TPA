package pool;

import java.util.ArrayList;
import java.util.List;

public class Partida {
	private int idPartida;
	private boolean partidaEnCurso = false;
	List<Ronda> rondas = new ArrayList<>();
	
	public Partida(int idPartida) {
		this.idPartida = idPartida;
		this.partidaEnCurso = true;
	}
	
	public boolean agregarRonda()
	{
		return rondas.add(new Ronda() {
		});
	}
	
	
	
	
}
