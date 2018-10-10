package core;

public class CuerpoVibora extends Entidad {

	private Vibora vibora;
	
	public CuerpoVibora(Vibora vibora, Coordenada coordenada) {
		super(coordenada);
		this.vibora = vibora;
	}

	public CuerpoVibora(Vibora vibora, int x, int y) {
		super(x, y);
		this.vibora = vibora;
	}
	
	public Vibora getVibora() {
		return this.vibora;
	}
}
