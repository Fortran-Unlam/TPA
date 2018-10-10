package core;

import java.util.ArrayList;

public class Mapa {

	private Coordenada tamano;

	private ArrayList<Vibora> viboras = new ArrayList<Vibora>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();

	public Mapa(int ancho, int alto) {
		this.tamano = new Coordenada(ancho, alto);
	}

	public void add(Vibora vibora) {
		this.viboras.add(vibora);
	}

	public void add(Fruta fruta) {
		this.frutas.add(fruta);
	}

	public void mover() {
		for (Vibora vibora : this.viboras) {
			vibora.mover();
		}
		for (Vibora vibora : this.viboras) {
			Entidad entidad = this.getByPosition(vibora.getCabeza().getX(), vibora.getCabeza().getY());
			if (entidad != null) {
				// TODO: colision
			}
		}
	}

	public Entidad getByPosition(int x, int y) {
		for (Vibora vibora : this.viboras) {
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				if (cuerpoVibora.getCoordenada().getX() == x && cuerpoVibora.getCoordenada().getY() == y) {
					return cuerpoVibora;
				}
			}
		}

		for (Fruta fruta : frutas) {
			if (fruta.getCoordenada().getX() == x && fruta.getCoordenada().getY() == y) {
				return fruta;
			}
		}
		return null;
	}
	
	public Coordenada getTamano() {
		return this.tamano;
	}
}
