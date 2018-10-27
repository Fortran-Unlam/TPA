package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.sala.VentanaCreacionSala;
import view.sala.VentanaSala;

public class Sala {
	private int idSala; //Ver si sacar, pq no van a estar en la bd.
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private Usuario usuarioAdministrador;
	private List<Partida> partidas = new ArrayList<>();
	private List<Usuario> usuarios = new LinkedList<Usuario>();

	
	
	public Sala(String nombreSala, int cantidadUsuarioMaximos,
			int cantidadDePartidas, Usuario usuarioAdministrador) {
		//this.idSala = idSala;
		this.nombreSala = nombreSala;
		this.cantidadUsuarioActuales = 1;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.cantidadDePartidas = cantidadDePartidas;
		this.usuarioAdministrador = usuarioAdministrador;
		this.usuarios.add(usuarioAdministrador);
	}

	public void setDatos(String nombreSala, int usuariosMax) {
		this.nombreSala = nombreSala;
		this.cantidadUsuarioMaximos = usuariosMax;
	}

	public boolean agregarJugadorASala(Usuario usrNuevo) {
		return this.usuarios.add(usrNuevo);
	}

	public boolean agregarPartida() {
		return partidas.add(new Partida(++cantidadDePartidas));
	}

}
