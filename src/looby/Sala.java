package looby;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Sala implements Serializable {

	private static final long serialVersionUID = -3003995871552317389L;
	private String nombre;
	private boolean salaLlena = false;
	private int cantidadUsuarioActuales;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidasJugadas = 0;
	private List<Partida> partidasJugadas = new ArrayList<Partida>();
	private List<Usuario> usuariosActivos = new ArrayList<Usuario>();
	private Partida partidaActual;
	private Usuario usuarioCreador;

	public Sala(String nombreSala, int cantidadUsuarioMaximos, Usuario usuarioCreador) {
		this.nombre = nombreSala;
		this.cantidadUsuarioActuales++;
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
		this.salaLlena = true;
		return false; // SALA LLENA
	}

	public boolean sacarUsuarioDeSala(Usuario usuario) { // VER TEMA SI SACAN AL ADMIN DE LA SALA
		if (this.usuariosActivos.remove(usuario)) {
			this.cantidadUsuarioActuales--;
			return true;
		}
		if (this.salaLlena)
			this.salaLlena = false;
		return false;

	}

	public boolean crearPartida(int cantidadDeRondasDePartida, TipoJuego tipo) {
		if (this.partidaActual == null && this.cantidadUsuarioActuales > 1) {
			this.partidaActual = new Partida(++this.cantidadDePartidasJugadas, this.usuariosActivos,
					cantidadDeRondasDePartida, tipo);
			this.comenzarPartida();
			return true;
		}
		return false; // HAY UNA PARTIDA EN CURSO O HAY MENOS DE DOS USUARIOS, RETORNAR EXCEP DE SALA
	}

	public boolean comenzarPartida() {
		System.out.println("esto llega null ... " + this.partidaActual);
		if (this.partidaActual == null) {
			return false;
		}
		this.partidaActual.empezarPartida();
		this.partidasJugadas.add(this.partidaActual);
		this.partidaActual = null;
		return true;
	}

	public void stopPartida() {
		this.partidaActual.pararJuegoEnCurso();
	}

	public Usuario getAdministrador() {
		return this.usuarioCreador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public JsonObjectBuilder jsonify() {
		return Json.createObjectBuilder().add("nombreSala", this.nombre)
				.add("cantidadUsuarioActuales", this.cantidadUsuarioActuales)
				.add("cantidadUsuarioMaximos", this.cantidadUsuarioMaximos)
				.add("cantidadDePartidasJugadas", this.cantidadDePartidasJugadas);
	}

	public Sala(JsonObject jsonObject) {
		this.nombre = jsonObject.get("nombreSala").toString();
		this.cantidadUsuarioActuales = Integer.valueOf(jsonObject.get("cantidadUsuarioActuales").toString());
		this.cantidadUsuarioMaximos = Integer.valueOf(jsonObject.get("cantidadUsuarioMaximos").toString());
		this.cantidadDePartidasJugadas = Integer.valueOf(jsonObject.get("cantidadDePartidasJugadas").toString());
	}

	@Override
	public String toString() {
		return this.nombre;
	}
}
