package DeprecadoConexion;
import java.sql.*;
import Seguridad.*;

public class DeprecadoConexion
{
	private static Connection connection = null;
	/** Es estatico porque el servidor siempre va a estar conectado a la base de datos.
	 *  No es necesario instanciar varias conexiones por asi decirlo.
	 *  Aunque no se si es conveniente tener una conexion a la BD por thread.
	 *  Esto se vera despues.
	 *  Sobre una misma conexion se realizaran distintas consultas.
	 * 
	 * @return 
	 */
    public static String connectDatabase() 
    {
        try 
        {
            connection = DriverManager.getConnection(DeprecadoConfiguraciones.HOSTBD,DeprecadoConfiguraciones.USER, DeprecadoConfiguraciones.PASSWORD);
            boolean valid = connection.isValid(500);
            return (valid) ? "Conexion correcta a la BD" : "Ocurrio un problema en la conexion a la BD";
        } 
        catch (java.sql.SQLException e) 
        {
            return e.getMessage();
        }
    }  

    
    public static boolean login(String usuario, String contrasena)
    {
    	boolean acceso = false;
    	try
    	{
    		PreparedStatement query = connection.prepareStatement("SELECT COUNT(*) AS coincidencia FROM usuarios WHERE usuario = ? AND contrasena = ?");
    		query.setString(1, Seguridad.decrypt(usuario));
    		query.setString(2, contrasena); //La contrasena se guarda encriptada en la base de datos por eso no es necesario desencriptarla.
    		ResultSet rs = query.executeQuery();
    		rs.next();
    		int resultado = rs.getInt("coincidencia");
    		rs.close();
    		if(resultado == 1)
    		{
    			System.out.println("Usuario: "+ Seguridad.decrypt(usuario) + " logeado");
    			acceso = true;
    		}
    			
    	}
    	catch(SQLException e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return acceso;
    }
}
