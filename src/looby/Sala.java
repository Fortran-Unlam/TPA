package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import core.Jugador;

public class Sala {
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidas;
	private Usuario usuarioAdministrador;
	private ArrayList<Partida> partidas = new ArrayList<>();
	private List<Usuario> usuarios = new LinkedList<Usuario>();
	private Partida partidaActual;

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

	public boolean agregarPartida(Usuario usuario) throws Exception {
		Jugador jugador = new Jugador(usuario);
		this.partidaActual = new Partida(++cantidadDePartidas, jugador);
		return partidas.add(this.partidaActual);
	}

	public Usuario getAdministrador() {
		return this.usuarioAdministrador;
	}

	public boolean startPartida() {
		return this.partidaActual.start();
	}

}
