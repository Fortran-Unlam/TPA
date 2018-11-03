package looby;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObject;

import config.Param;
import core.Jugador;
import servidor.ConexionCliente;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -2637338189236421497L;
	private int id;
	private String username;
	private String password;
	private int puntos;
	private int cantidadFrutaComida;
	private int asesinatos;
	private int muertes;
	private int partidasGanadas;
	private int rondasGanadas;
	private Sala sala;
	private Jugador jugador;
	private ConexionCliente conexionCliente;

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Constructor necesario para el Hibernate
	public Usuario() {
	}
	
	//Constructor de usuario solamente utilizando el Id
	public Usuario(int id) 
	{
		this.id = id;
	}

	public Usuario(int id, String username, String password, int puntos, int cantidadFrutaComida, int asesinatos,
			int muertes, int partidasGanadas, int rondasGanadas) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.puntos = puntos;
		this.cantidadFrutaComida = cantidadFrutaComida;
		this.asesinatos = asesinatos;
		this.muertes = muertes;
		this.partidasGanadas = partidasGanadas;
		this.rondasGanadas = rondasGanadas;
	}

	public Usuario(JsonObject jsonObject) {
		this.id = Integer.valueOf(jsonObject.get("id").toString());
		this.username = jsonObject.get("username").toString();
		this.password = jsonObject.get("password").toString();
		this.puntos = Integer.valueOf(jsonObject.get("password").toString());
	}

	public Sala crearSala(String nombreSala, int cantDeUsrMaximos) {
		Sala sala = new Sala(nombreSala, cantDeUsrMaximos, this);
		sala.agregarUsuarioASala(this);
		return sala;
	}

	public Usuario salirDeSala() {
		if (this.sala != null) {
			this.sala.quitarUsuario(this);
			this.sala = null;
		}
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getCantidadFrutaComida() {
		return cantidadFrutaComida;
	}

	public void setCantidadFrutaComida(int cantidadFrutaComida) {
		this.cantidadFrutaComida = cantidadFrutaComida;
	}

	public int getAsesinatos() {
		return asesinatos;
	}

	public void setAsesinatos(int asesinatos) {
		this.asesinatos = asesinatos;
	}

	public int getMuertes() {
		return muertes;
	}

	public void setMuertes(int muertes) {
		this.muertes = muertes;
	}

	public int getPartidasGanadas() {
		return partidasGanadas;
	}

	public void setPartidasGanadas(int partidasGanadas) {
		this.partidasGanadas = partidasGanadas;
	}

	public int getRondasGanadas() {
		return rondasGanadas;
	}

	public void setRondasGanadas(int rondasGanadas) {
		this.rondasGanadas = rondasGanadas;
	}

	public String getUsuarioLogueado() {
		return Json.createObjectBuilder().add("request", Param.REQUEST_LOGUEO_CORRECTO).add("id", this.id)
				.add("username", this.username).add("password", this.password).build().toString();
	}
/*
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", puntos=" + puntos
				+ ", cantidadFrutaComida=" + cantidadFrutaComida + ", asesinatos=" + asesinatos + ", muertes=" + muertes
				+ ", partidasGanadas=" + partidasGanadas + ", rondasGanadas=" + rondasGanadas + "]";
	}*/

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Jugador getJugador() {
		if (this.jugador != null) {
			return this.jugador;			
		}
		
		return this.sala.getPartidaActual().getUsuariosActivosEnSala().get(0).getJugador();
	}

	public void setConexion(ConexionCliente conexionCliente) {
		this.conexionCliente = conexionCliente;
	}
	
	public ConexionCliente getConexion() {
		return this.conexionCliente;
	}
}
