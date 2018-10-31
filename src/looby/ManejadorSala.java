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

	public static void main(String[] args) throws Exception {
		ManejadorSala controlador = new ManejadorSala();
		Usuario usuarioPruebaCreadorDeSala = new Usuario("Admin", "123");
		Usuario usuarioPruebaInvitado = new Usuario("Invitado","123");
		controlador.agregarAUsuariosActivos(usuarioPruebaCreadorDeSala);
		controlador.agregarAUsuariosActivos(usuarioPruebaInvitado);
		Sala salaPrueba = usuarioPruebaCreadorDeSala.crearSala("Sala 1", 4);
		controlador.agregarASalasActivas(salaPrueba);
		controlador.salasActivas.get(0).agregarUsuarioASala(usuarioPruebaInvitado);
		
		TipoJuego tipoJuego = new TipoJuego();
		tipoJuego = new TipoJuegoFruta(tipoJuego);
		tipoJuego = new TipoJuegoTiempo(tipoJuego);
		tipoJuego = new TipoJuegoSupervivencia(tipoJuego);
		tipoJuego.setFrutasMaximas(2);
		System.out.println(tipoJuego.termina(2, 3, 3));
		
		salaPrueba.crearPartida(2,tipoJuego);
	}
}
