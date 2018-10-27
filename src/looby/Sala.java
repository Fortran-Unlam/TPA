package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.sala.VentanaCreacionSala;
import view.sala.VentanaSala;

public class Sala {
	private int idSala;
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private List<Partida> partidas = new ArrayList<>();
	private List<Usuario> usuarios = new LinkedList<Usuario>();
	
	public void setDatos(String nombreSala, int usuariosMax) {
		this.idSala = 1;	//por ahora lo dejo harcodeado, habrìa que ver como hacer para asignarle un id unico
		this.nombreSala = nombreSala;
		this.cantidadUsuarioMaximos = usuariosMax;
	}
	

	public boolean agregarPartida() {
		return partidas.add(new Partida(++cantidadDePartidas));
	}

}
