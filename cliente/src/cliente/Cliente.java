package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import cliente.ventana.Sincronismo;
import cliente.ventana.usuario.VentanaLoginUsuario;
import config.Param;
import javassist.bytecode.stackmap.TypeData.ClassName;

public class Cliente {

	private static Socket socketOut;
	private static Socket socketIn;
	private static Socket socketOutBackOff;
	private static Socket socketInBackOff;
	private static ConexionServidor conexionServidor;
	private static ConexionServidorBackOff conexionServidorBackOff;
	private static Sincronismo sincronismo;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		new Cliente();
	}

	public Cliente() {
		try {
			FileHandler fh = null;
			try {
	            fh = new FileHandler("logger.log", true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        fh.setFormatter(new SimpleFormatter());
	        LOGGER.addHandler(fh);
	        
			socketOut = new Socket(Param.HOST, Param.PORT_1);
			socketIn = new Socket(Param.HOST, Param.PORT_2);

			conexionServidor = new ConexionServidor(socketOut, socketIn);

			socketOutBackOff = new Socket(Param.HOST, Param.PORT_3);
			socketInBackOff = new Socket(Param.HOST, Param.PORT_4);
			conexionServidorBackOff = new ConexionServidorBackOff(socketOutBackOff, socketInBackOff);

			conexionServidorBackOff.start();
			sincronismo = new Sincronismo();

			VentanaLoginUsuario login = new VentanaLoginUsuario();
			login.setVisible(true);

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
		}
	}

	public static ConexionServidor getConexionServidor() {
		return conexionServidor;
	}

	public static Socket getSocket() {
		return socketOut;
	}

	public static ConexionServidorBackOff getconexionServidorBackOff() {
		return conexionServidorBackOff;
	}

	public static Sincronismo getSincronismo() {
		return sincronismo;
	}

}
