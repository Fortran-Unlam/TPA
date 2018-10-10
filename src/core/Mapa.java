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
		// colision con fruta para ver si no le saco la cola
		for (Vibora vibora : this.viboras) {
			Fruta fruta = this.getFrutaByPosition(vibora.getCabeza().getX(), vibora.getCabeza().getY());
			if (fruta != null) {
				Colision.colisionar(vibora, fruta);
			}
		}
		
		// colision de la cabeza con otra entidad que no sea fruta
		for (Vibora vibora : this.viboras) {
			CuerpoVibora cuerpoVibora = this.getCuerpoViboraByPosition(vibora.getCabeza().getX(), vibora.getCabeza().getY());
			if (cuerpoVibora != null) {
				Colision.colisionar(vibora, cuerpoVibora);
			}
		}
		
		for (Vibora vibora : this.viboras) {
			vibora.crecerOMover();
			if (vibora.getMuere()) {
				this.viboras.remove(vibora);
			}
		}
		
	}

	public Fruta getFrutaByPosition(int x, int y) {
		for (Fruta fruta : frutas) {
			if (fruta.getCoordenada().getX() == x && fruta.getCoordenada().getY() == y) {
				return fruta;
			}
		}
		return null;
	}
	
	/**
	 * Retorna el primer cuerpo de vibora en esa posicion si hay
	 * 
	 * @param x
	 * @param y
	 * @return Cuerpo de vibora
	 */
	public CuerpoVibora getCuerpoViboraByPosition(int x, int y) {
		for (Vibora vibora : this.viboras) {
			for (CuerpoVibora cuerpoVibora : vibora.getCuerpos()) {
				if (cuerpoVibora.getCoordenada().getX() == x && cuerpoVibora.getCoordenada().getY() == y) {
					return cuerpoVibora;
				}
			}
		}

		return null;
	}
	
	public Coordenada getTamano() {
		return this.tamano;
	}
}
