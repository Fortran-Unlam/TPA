package core;

public class Puntaje implements Comparable<Puntaje> {
	private int idVibora;
	private int frutasComidas;
	
	public Puntaje(int idVibora, int frutasComidas) {
		this.idVibora = idVibora;
		this.frutasComidas = frutasComidas;
	}

	@Override
	public int compareTo(Puntaje otro) {
		return otro.frutasComidas - this.frutasComidas;
	}

	@Override
	public String toString() {
		return idVibora + "\t" + frutasComidas;
	}
	
	
	

}
