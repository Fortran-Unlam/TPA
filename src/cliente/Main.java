package cliente;

import java.io.IOException;
import java.net.Socket;

import config.Param;

public class Main {

	private Socket socket;

	public Main() {
		try {
			this.socket = new Socket(Param.HOST, Param.PUERTO);
			ConexionServidor cs = new ConexionServidor(this.socket);
			cs.logear("a", "b");
			cs.recibirMensajesServidor();
		} catch (IOException ex) {
			System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
		}

	}

	public static void main(String[] args) {
		new Main();
	}

}
