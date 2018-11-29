package cliente;

import java.io.Serializable;
import javax.json.JsonObject;

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

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Usuario(int id) {
		this.id = id;
	}

	public Usuario(JsonObject jsonObject) {
		this.id = Integer.valueOf(jsonObject.get("id").toString());
		this.username = jsonObject.get("username").toString();
		this.password = jsonObject.get("password").toString();
		this.puntos = Integer.valueOf(jsonObject.get("password").toString());
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

}
