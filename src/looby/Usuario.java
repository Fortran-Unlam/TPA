package looby;

public class Usuario {
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

	public Sala crearSala() {
		return new Sala();
	}
	

}
