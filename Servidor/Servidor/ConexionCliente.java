package Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import Conexion.*;


public class ConexionCliente extends Thread
{
    
    private Socket socket; 
    //Buffer de entrada del Servidor, que a su vez seria el input de salida del Cliente.
    private DataInputStream entradaDatos;
    //Inp
    private DataOutputStream salidaDatos;
    
    /** Es el constructor de la clase ConexionCliente, recibe 
     * 
     * @param socket Un socket ya creado por una conexion recibida por ServerSocket.accept();
     * @param mensajes -
     */
    public ConexionCliente (Socket socket)
    {
        this.socket = socket;
        
        /**Intento instanciar la entradaDatos y salidaDatos en base a la informacion
         * de los mismos obtenida por el socket.*/
        try 
        {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } 
        catch (IOException ex) 
        {
        	String mensajeError = "Error al crear los stream de entrada y salida : " + ex.getMessage();
        	System.out.println(mensajeError);
        }
    }
    

    @Override
    public void run()
    {
        String mensajeRecibido;
        boolean conectado = true;
        
        while (conectado) 
        {
            try 
            {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaDatos.readUTF();
                String[] recepcion = mensajeRecibido.split(";");
                int tipoMensaje = Integer.valueOf(recepcion[0]);
                //Intento de login.
                if(tipoMensaje == 0)
                {
                	String usuario = recepcion[1];
                	String contrasena = recepcion[2];
                	//Observo la recepcion encriptada.
                	/*System.out.println(usuario);
                	System.out.println(contrasena);*/
                	//Intento logear y mando la respuesta al cliente.
                	boolean resultado = Conexion.login(usuario, contrasena);
                	salidaDatos.writeUTF("0;" +((resultado)?"ok":"nok"));
                }
            } 
            catch (IOException ex) 
            {
            	String mensaje = "Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.";
            	System.out.println(mensaje);
                conectado = false; 
                // Si se ha producido un error al recibir datos del cliente se cierra la conexion con el.
                try 
                {
                    entradaDatos.close();
                    salidaDatos.close();
                } 
                catch (IOException ex2) 
                {
                	String mensajeError2 = "Error al cerrar los stream de entrada y salida :" + ex2.getMessage();
                	System.out.println(mensajeError2);
                }
            }
        }   
    }
} 