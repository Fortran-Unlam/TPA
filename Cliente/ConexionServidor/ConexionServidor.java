package ConexionServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Login.PantallaLogin;
import Constantes.TipoMensaje;

public class ConexionServidor implements Runnable
{
    private Socket socket; 
    private DataOutputStream salidaDatos;
    
    public ConexionServidor(Socket socket) 
    {
        this.socket = socket;
        try 
        {
            this.salidaDatos = new DataOutputStream(this.socket.getOutputStream());
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al crear el stream de salida : " + ex.getMessage());
        } 
        catch (NullPointerException ex) 
        {
            System.out.println("El socket no se creo correctamente. ");
        }
    }
    
    public void logear(String usuario, String password) 
    {
    	//Intento escribir en el buffer de salida.
        try 
        {
            salidaDatos.writeUTF(TipoMensaje.MENSAJEINGRESAR + usuario + ";" + password);
            //Al realizar salidaDatos.writeUTF estaria "llamando" al entradaDatos.readUTF(); del servidor.
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
    
    public void registrar(String usuario, String password)
    {
    	//Intento escribir en el buffer de salida.
        try 
        {
            salidaDatos.writeUTF(TipoMensaje.MENSAJEREGISTRAR + usuario + ";" + password);
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
    
    public void recibirMensajesServidor()
    {
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        try 
        {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al crear el stream de entrada: " + ex.getMessage());
        } 
        catch (NullPointerException ex) 
        {
            System.out.println("El socket no se creo correctamente. ");
        }
        
        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) 
        {
            try 
            {
            	//Chequea si hay datos provenientes del servidor a traves del socket.
            	if(entradaDatos.available()!=0)
            	{
	                String mensajeRecibido = entradaDatos.readUTF();
	                TratamientoMensajeCliente.tratarMensaje(mensajeRecibido);
            	}
            } 
            catch (IOException ex) 
            {
                System.out.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } 
            catch (NullPointerException ex) 
            {
                System.out.println("El socket no se creo correctamente. ");
                conectado = false;
            } 
        }
    }

	@Override
	public void run() 
	{
		this.recibirMensajesServidor();
	}
}