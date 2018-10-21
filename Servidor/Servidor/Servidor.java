package Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Conexion.Conexion;

public class Servidor {

	//Parametrizacion estática de configuraciones.
	private static final int PUERTO = 1234;
	private static final int MAXIMASCONEXIONESIMULTANEAS = 10;
    
    public static void main(String[] args) {
    	
    	ServerSocket servidor = null;
    	Socket socket = null;
    	String estadoConexion = Conexion.connectDatabase();
    	//Creo el acceso a la base de datos.
        System.out.println(estadoConexion);
        try 
        {
            // Se crea el serverSocket para empezar a escuchar a los clientes.
            servidor = new ServerSocket(PUERTO, MAXIMASCONEXIONESIMULTANEAS);

            //Se esperan las conexiones, se empieza a escuchar a los clientes durante tiempo indefinido.
            while (true) 
            {
            	System.out.println("Servidor a la espera de conexiones.");
                socket = servidor.accept();
                //Muestra de informacion por pantalla y por archivo.
                String mensajeNuevaConexion = "Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.";
                System.out.println(mensajeNuevaConexion);
                ConexionCliente cc = new ConexionCliente(socket);
                //Comienza la ejecucion del thread que corresponde a una instancia del objeto ConexionCliente.
                cc.start();
                
            }
        } 
        catch (IOException ex) 
        {
        	//Muestra de errores por pantalla y por archivo.
        	String mensajeError = ex.getMessage();
        	System.out.println(mensajeError);
        } 
        finally
        {
        	//Cierro todos los sockets abiertos tras ocurrir un error.
            try 
            {
                socket.close();
                servidor.close();
            } 
            catch (IOException ex) 
            {
            	//Muestra de errores por pantalla y por archivo.
            	String mensajeError = ex.getMessage();
            	System.out.println(mensajeError);
            }
        }
    }
}