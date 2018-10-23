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
		if(this.frutasComidas>otro.frutasComidas)
			return 1;
		if(this.frutasComidas<otro.frutasComidas)
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return idVibora + "\t" + frutasComidas;
	}
	
	
	

}
