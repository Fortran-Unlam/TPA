package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Sala {
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private Usuario usuarioAdministrador;
	private List<Partida> partidas = new ArrayList<>();
	private List<Usuario> usuarios = new LinkedList<Usuario>();

	public Sala(String nombreSala, int cantidadUsuarioMaximos, Usuario usuarioAdministrador) {
		this.nombreSala = nombreSala;
		this.cantidadUsuarioActuales = 1;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.usuarioAdministrador = usuarioAdministrador;
		this.usuarios.add(usuarioAdministrador);
	}

	public boolean agregarJugadorASala(Usuario usrNuevo) {
		if (this.cantidadUsuarioActuales < this.cantidadUsuarioMaximos) {
			this.usuarios.add(usrNuevo);
			this.cantidadUsuarioActuales++;
			return true;
		}
		return false;
	}

	public boolean agregarPartida() {
		return partidas.add(new Partida(++cantidadDePartidas));
	}

}
