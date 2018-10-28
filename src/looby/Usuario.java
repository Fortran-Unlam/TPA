package looby;

import javax.json.Json;
import javax.json.JsonObject;

import config.Param;

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
	private boolean enSala = false;

	public Usuario(String usrName, String password) {
		this.username = usrName;
		this.password = password;
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
		// this.password = jsonObject.get("password").toString();
		// this.puntos = Integer.valueOf(jsonObject.get("password").toString());
	}

	public Sala crearSala(String nombreSala, int cantDeUsrMaximos) {
		this.enSala = true;
		return new Sala(nombreSala, cantDeUsrMaximos, this);
	}

	public Usuario unirseASala() {
		this.enSala = true;
		return this;
	}

	public Usuario salirDeSala() {
		this.enSala = false;
		return this;
	}

	public int getIdUsuario() {
		return id;
	}

	public void setIdUsuario(int idUsuario) {
		this.id = idUsuario;
	}

	public String getUsrName() {
		return username;
	}

	public void setUsrName(String usrName) {
		this.username = usrName;
	}
	
	public boolean isEnSala() {
		return enSala;
	}

	public String getUsuarioLogueado() {
		return Json.createObjectBuilder().add("request", Param.REQUEST_LOGUEO_CORRECTO).add("id", this.id)
				.add("username", this.username).add("password", this.password).build().toString();

	}
}
