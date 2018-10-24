package pool;

import java.util.ArrayList;
import java.util.List;

public class Sala {
	private int idSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private List<Partida> partidas = new ArrayList<>();

	public Sala(int idSala, int cantidadUsuarioMaximos) {
		this.idSala = idSala;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.cantidadUsuarioActuales = 0;
	}

	public Sala() {
	
	}

	public boolean agregarPartida() {
		return partidas.add(new Partida(++cantidadDePartidas));
	}

}
