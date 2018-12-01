package looby;

import javax.json.Json;
import javax.json.JsonObject;

import config.Param;
import core.Jugador;

public class Usuario {

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
	private boolean inJuego = false;

	/**
	 * Constructor de usuario que puede llegar a usa GSON
	 */
	public Usuario() {
	}
	
	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor de usuario solamente utilizando el Id
	 * 
	 * @param id
	 */
	public Usuario(int id) {
		this.id = id;
	}

	/**
	 * Crea un usuario 
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param puntos
	 * @param cantidadFrutaComida
	 * @param asesinatos
	 * @param muertes
	 * @param partidasGanadas
	 * @param rondasGanadas
	 */
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

	/**
	 * Crea un usuario a partir de un objeto json
	 * 
	 * @param jsonObject
	 */
	public Usuario(JsonObject jsonObject) {
		this.id = Integer.valueOf(jsonObject.get("id").toString());
		this.username = jsonObject.get("username").toString();
		this.password = jsonObject.get("password").toString();
		this.puntos = Integer.valueOf(jsonObject.get("password").toString());
	}

	/**
	 * Crea una sala
	 * 
	 * @param nombreSala
	 * @param cantDeUsrMaximos
	 * @return
	 */
	public Sala crearSala(String nombreSala, int cantDeUsrMaximos) {
		Sala sala = new Sala(nombreSala, cantDeUsrMaximos, this);
		sala.agregarUsuarioASala(this);
		return sala;
	}

	public void salirDeSala() {
		this.sala = null;
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
	
	public boolean inJuego() {
		return this.inJuego;
	}
	
	public void setInJuego(boolean valor) {
		this.inJuego = valor;
	}

	public String getUsuarioLogueado() {
		return Json.createObjectBuilder().add("request", Param.REQUEST_LOGUEO_CORRECTO).add("id", this.id)
				.add("username", this.username).add("password", this.password).build().toString();
	}
	/*
	 * @Override public String toString() { return "Usuario [id=" + id +
	 * ", username=" + username + ", password=" + password + ", puntos=" + puntos +
	 * ", cantidadFrutaComida=" + cantidadFrutaComida + ", asesinatos=" + asesinatos
	 * + ", muertes=" + muertes + ", partidasGanadas=" + partidasGanadas +
	 * ", rondasGanadas=" + rondasGanadas + "]"; }
	 */

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	/**
	 * Retorna el jugador
	 * 
	 * @return Jugador
	 */
	public Jugador getJugador() {
		if (this.jugador != null) {
			return this.jugador;
		}

		if (this.sala != null && this.sala.getPartidaActual() != null
				&& this.sala.getPartidaActual().getUsuariosActivosEnSala() != null) {
			return this.sala.getPartidaActual().getUsuariosActivosEnSala().get(0).getJugador();
		}
		return null;
	}

	/**
	 * Retorna la sala
	 * 
	 * @return Sala
	 */
	public Sala getSala() {
		return sala;
	}

	/**
	 * Setea una sala
	 * 
	 * @param sala
	 */
	public void setSala(Sala sala) {
		this.sala = sala;
	}

	/**
	 * Actualizaa las estadisticas de ronda
	 * 
	 * @param sobrevivio
	 * @param frutasComidas
	 * @return Boolean Si pudo guardarlo en el la DB
	 */
	public boolean actualizarEstadisticasRonda(boolean sobrevivio, int frutasComidas) {
		// this.partidasJugadas++;
		if (!sobrevivio)
			this.muertes++;

		if (sobrevivio) {
			this.puntos += 20;
			this.rondasGanadas++;
		}

		this.puntos += frutasComidas;
		this.cantidadFrutaComida += frutasComidas;

		if (UsuarioDAO.updateEstadisticas(this))
			return true;
		return false;
	}

	/**
	 * Actualiza la estadistica de partida
	 * 
	 * @return Boolean Si pudo guardarlo en la DB
	 */
	public boolean actualizarEstadisticasPartidasGanadas() {
		this.partidasGanadas++;

		if (UsuarioDAO.updateEstadisticas(this))
			return true;
		return false;
	}

}
