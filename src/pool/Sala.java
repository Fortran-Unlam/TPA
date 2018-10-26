package pool;

import java.util.ArrayList;
import java.util.List;

import view.sala.VentanaSala;

public class Sala {
	private int idSala;
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private List<Partida> partidas = new ArrayList<>();

	public Sala(String nombreSala, int cantidadUsuarioMaximos) {
		this.nombreSala = nombreSala;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.cantidadUsuarioActuales = 0;
	}

	public Sala() {
	
	}

	public boolean agregarPartida() {
		return partidas.add(new Partida(++cantidadDePartidas));
	}

}
