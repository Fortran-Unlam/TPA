package ConexionServidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Login.PantallaLogin;

public class ConexionServidor
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
            salidaDatos.writeUTF("0;" + usuario + ";" + password);
            //Al realizar salidaDatos.writeUTF estaria "llamando" al entradaDatos.readUTF(); del servidor.
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
                String mensajeRecibido = entradaDatos.readUTF();
                //Siempre voy a recibir un string separados por ; donde el primer valor me indica a que corresponde el mensaje.
                String[] recepcion = mensajeRecibido.split(";");
                int tipoMensaje = Integer.valueOf(recepcion[0]);
                //Intento de login.
                if(tipoMensaje == 0)
                {
                	String respuesta = recepcion[1];
                	if(respuesta.equals("ok"))
                	{
                		PantallaLogin.lblEstado.setText("Conectado");
                		//Al recibir respuesta corto la escucha por el momento, esto fixear despues xd
                		conectado = false;
                	}
                	else
                	{
                		PantallaLogin.lblEstado.setText("Usuario y/o contrasena invalido");
                		//Al recibir respuesta corto la escucha por el momento, esto fixear despues xd
                		conectado = false;
                	}
                		
                }
                if(mensajeRecibido!=null)
                	conectado = false;
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
}