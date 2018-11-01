package looby;

import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObject;

import config.Param;

public class UsuarioBot extends Usuario implements Serializable {

	public UsuarioBot(String username, String password) {
		super(username, password);
	}

	private static final long serialVersionUID = -3023794049432417557L;

}
