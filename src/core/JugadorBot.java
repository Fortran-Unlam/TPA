package core;

import core.entidad.Vibora;
import looby.Usuario;

public class JugadorBot extends Jugador {

	private static final long serialVersionUID = 6951778505924532094L;

	public JugadorBot(Vibora vibora, String nombre) {
		super(vibora, nombre);
	}

	public JugadorBot(Usuario usuario) {
		super(usuario);
	}

}
