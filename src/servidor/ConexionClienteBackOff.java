package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import config.Param;

public class ConexionClienteBackOff extends Thread {

	private Socket socket;
	private ObjectInputStream entradaDatos;
	private ObjectOutputStream salidaDatos;

	public ConexionClienteBackOff(Socket socket, Socket socketOut) {
		this.socket = socket;

		try {
			this.entradaDatos = new ObjectInputStream(socket.getInputStream());
			this.salidaDatos = new ObjectOutputStream(socketOut.getOutputStream());

		} catch (IOException ex) {
			System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
		}
	}

	@Override
	public void run() {
		boolean conectado = true;

		while (conectado) {

			try {
				Message message = (Message) this.entradaDatos.readObject();
				System.out.println("El cliente solicita " + message.getType());
				String tipoDeMensaje = message.getType();
				
				/*Puede que haga falta sumarle mas casos acá, como el de
				 * una sala que el admin la cerró 
				 */
				if(tipoDeMensaje.equals(Param.NOTICE_CREACION_SALA) ||
						tipoDeMensaje.equals(Param.NOTICE_UNION_SALA) ||
						tipoDeMensaje.equals(Param.REQUEST_INGRESO_VENTANA_UNIR_SALA) ||
						tipoDeMensaje.equals(Param.NOTICE_SALIR_SALA)){
					enviarActualizacionSalasALosClientes();
				}

			} catch (IOException ex) {
				System.out.println(ex.getMessage() + " Cliente con la IP " + socket.getInetAddress().getHostAddress()
						+ " desconectado del backOff.");
				conectado = false;
				try {
					this.entradaDatos.close();
					this.salidaDatos.close();
				} catch (IOException ex2) {
					System.out
							.println("Error al cerrar los stream de entrada y salida del backoff:" + ex2.getMessage());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		Servidor.desconectarBackOff(this);
	}
	
	public ObjectOutputStream getSalidaDatos() {
		return this.salidaDatos;
	}

	public void enviarActualizacionSalasALosClientes() {
		//Pido los datos de las salas
		ArrayList <String> datosDeSalas = Servidor.getAllSalas();
		Message messageConActualizacionDeSalas = new Message(Param.NOTICE_ACTUALIZAR_SALAS, datosDeSalas);
		
		for(ConexionClienteBackOff c: Servidor.getConexionesClientesBackOff()) {
			//Recorro todo el ArrayList de Conexiones de clientes de backoff y le envio las salas actualizadas
			try {
				c.salidaDatos.writeObject(messageConActualizacionDeSalas);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
