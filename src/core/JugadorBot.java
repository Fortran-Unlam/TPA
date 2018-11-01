package core;

import core.entidad.Vibora;
import looby.Usuario;

public class JugadorBot extends Jugador {

	public JugadorBot(Vibora vibora, String nombre) {
		super(vibora, nombre);
	}

	public JugadorBot(Usuario usuario) {
		super(usuario);
	}

}
