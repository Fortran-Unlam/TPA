package looby;

import java.util.ArrayList;
import java.util.List;

public class ManejadorSala {
	private List<Sala> salasActivas = new ArrayList<>();
	private List<Usuario> usuariosActivos = new ArrayList<>();

	public boolean agregarASalasActivas(Sala sala) {
		return this.salasActivas.add(sala);
	}

	public boolean agregarAUsuariosActivos(Usuario usuario) {
		return this.usuariosActivos.add(usuario);
	}

	public static void main(String[] args) {
		ManejadorSala controlador = new ManejadorSala();
		Usuario usuarioPrueba = new Usuario("Emiliano", "123");
		Sala salaPrueba = usuarioPrueba.crearSala("Sala 1", 4);
		controlador.agregarASalasActivas(salaPrueba);
		controlador.agregarAUsuariosActivos(usuarioPrueba);
	}

}
