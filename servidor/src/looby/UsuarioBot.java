package looby;

import java.io.Serializable;

import config.Param;

public class UsuarioBot extends Usuario implements Serializable {

	private static final long serialVersionUID = -3023794049432417557L;
	
	public UsuarioBot() {
		super("Bot" + Param.BOT_NUMBER++, "");
	}
}
