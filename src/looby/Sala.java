package looby;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import core.Jugador;

public class Sala {
	private String nombreSala;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidasJugadas;
	private Usuario usuarioCreador;
	private ArrayList<Partida> partidasJugadas = new ArrayList<>();
	private List<Usuario> usuariosActivos = new LinkedList<Usuario>();
	private Partida partidaActual;

	public Sala(String nombreSala, int cantidadUsuarioMaximos, Usuario usuarioCreador) {
		this.nombreSala = nombreSala;
		this.cantidadUsuarioActuales = 1;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.usuarioCreador = usuarioCreador;
		this.usuariosActivos.add(usuarioCreador);
	}

	public boolean agregarUsuarioASala(Usuario usuario) {
		if (this.cantidadUsuarioActuales < this.cantidadUsuarioMaximos) {
			this.usuariosActivos.add(usuario);
			this.cantidadUsuarioActuales++;
			return true;
		}
		return false; // SALA LLENA
	}

	public boolean sacarUsuarioDeSala(Usuario usuario) {

		if (this.usuariosActivos.remove(usuario)) {
			this.cantidadUsuarioActuales--;
			return true;
		}
		return false;

	}

	public boolean agregarPartida(Usuario usuario) throws Exception {
		Jugador jugador = new Jugador(usuario);
		this.partidaActual = new Partida(++cantidadDePartidasJugadas, jugador);
		return partidasJugadas.add(this.partidaActual);
	}

	public boolean crearPartida(int cantidadDeRondasDePartida) {
		if (partidaActual == null) {
			this.partidaActual = new Partida(++this.cantidadDePartidasJugadas, this.usuariosActivos,
					cantidadDeRondasDePartida); // ACA SE DEBERIA INDICAR EL TIPO DE JEUGO
			return true;
		}

		return false;

	}

	public boolean partidaTerminada() {
		if (this.partidaActual != null) {
			this.partidasJugadas.add(this.partidaActual);
			this.partidaActual = null;
			return true;
		}

		return false; // NO HAY PARTIDA ACTUAL
	}

	public boolean startPartida() {
		if (this.partidaActual != null) {
			return this.partidaActual.start();
		}
		return false;

	}

	public void stopPartida() {
		this.partidaActual.stop();
	}

	public Usuario getAdministrador() {
		return this.usuarioCreador;
	}

}
