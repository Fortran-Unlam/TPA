package looby;

public class Usuario {
	private int idUsuario;
	private String usrName;
	private String password;
	private int puntos;
	private int cantidadFrutaComida;
	private int asesinatos;
	private int muertes;
	private int partidasGanadas;
	private int rondasGanadas;

	public Usuario(String usrName, String password) {
		this.usrName = usrName;
		this.password = password;
	}

	public Usuario(int idUsuario, String usrName, String password, int puntos, int cantidadFrutaComida, int asesinatos,
		int muertes, int partidasGanadas, int rondasGanadas) {
		this.idUsuario = idUsuario;
		this.usrName = usrName;
		this.password = password;
		this.puntos = puntos;
		this.cantidadFrutaComida = cantidadFrutaComida;
		this.asesinatos = asesinatos;
		this.muertes = muertes;
		this.partidasGanadas = partidasGanadas;
		this.rondasGanadas = rondasGanadas;
	}

	public Sala crearSala(String nombreSala, int cantDeUsrMaximos) {
		return new Sala(nombreSala, cantDeUsrMaximos, this);
	}

	public Usuario unirseASala() {
		return this;
	}

}
