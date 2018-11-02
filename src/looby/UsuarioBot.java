package looby;

import java.io.Serializable;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;

import config.Param;
import config.Posicion;

public class UsuarioBot extends Usuario implements Serializable {

	private static final long serialVersionUID = -3023794049432417557L;
	
	public UsuarioBot(String username, String password) {
		super(username, password);
	}
}
