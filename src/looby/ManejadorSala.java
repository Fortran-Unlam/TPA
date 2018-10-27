package looby;

import java.util.ArrayList;
import java.util.List;

import core.Jugador;

public class ManejadorSala {
	private List<Sala> salasActivas = new ArrayList<>();
	private List<Usuario> usuariosActivos = new ArrayList<>();

	public boolean agregarASalasActivas(Sala sala) {
		return this.salasActivas.add(sala);
	}

}
