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

	private int cantidadUsuarioActuales =0;
	private int cantidadUsuarioMaximos;
	private int cantidadDePartidasJugadas = 0;
	private ArrayList<Partida> partidasJugadas = new ArrayList<Partida>();
	private ArrayList<Usuario> usuariosActivos = new ArrayList<Usuario>();
	private Partida partidaActual;
	private Usuario usuarioCreador;

	public Sala(String nombreSala, int cantidadUsuarioMaximos, Usuario usuarioCreador) {
		this.nombre = nombreSala;
		this.cantidadUsuarioMaximos = cantidadUsuarioMaximos;
		this.usuarioCreador = usuarioCreador;
//		this.usuariosActivos.add(usuarioCreador);
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

	/**
	 * Crea la partida y la comienza
	 * 
	 * @param cantidadDeRondasDePartida
	 * @param tipoJuego
	 * 
	 * @return
	 */
	public boolean crearPartida(int cantidadDeRondasDePartida, TipoJuego tipoJuego) {
		if (this.partidaActual == null && this.cantidadUsuarioActuales > 1) {

			this.partidaActual = new Partida(++this.cantidadDePartidasJugadas, this.usuariosActivos,
					cantidadDeRondasDePartida, tipoJuego);
			
			return this.comenzarPartida();
		}
		return false; // HAY UNA PARTIDA EN CURSO O HAY MENOS DE DOS USUARIOS, RETORNAR EXCEP DE SALA
	}

	public boolean comenzarPartida() {
		if (this.partidaActual == null) {
			return false;
		}
		this.partidaActual.empezarPartida();
		this.partidasJugadas.add(this.partidaActual);
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

	public Partida getPartidaActual() {
		return partidaActual;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	public void quitarUsuario(Usuario usuarioOtro) {
		this.usuariosActivos.remove(usuarioOtro);
	}

	public ArrayList<Usuario> getUsuariosActivos() {
		return this.usuariosActivos;
	}

	public int getCantidadUsuarioActuales() {
		return cantidadUsuarioActuales;
	}

	public int getCantidadUsuarioMaximos() {
		return cantidadUsuarioMaximos;
	}
	
	/*Obtengo los usuarios activos en la sala separados por coma
	esto se utiliza por ejemplo para devolver los usuarios a la vista
	en forma de String.*/
	public String getUsuariosSeparadosporComa()
	{
		String retorno = "";
		for(Usuario u : this.getUsuariosActivos())
			retorno+=u.getUsername()+",";
		//Elimino la ultima coma.
		retorno=retorno.substring(0, retorno.length()-1);
		return retorno;
	}
}
