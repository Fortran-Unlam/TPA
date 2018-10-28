package Servidor;

import Constantes.TipoMensaje;
import Entidades.Usuario;
import Entidades.UsuarioHibernate;
import Seguridad.Seguridad;

public class TratamientoMensajeServidor 
{
	public static String tratarMensaje(String mensajeRecibido)
	{
		String[] recepcion = mensajeRecibido.split(";");
        int tipoMensaje = Integer.valueOf(recepcion[0]);
        //PETICION LOGIN(CLIENTE).
        if(tipoMensaje == TipoMensaje.CLIENTEINGRESAR)
        {
        	String apodo = Seguridad.decrypt(recepcion[1]);
        	String contrasena = recepcion[2];
        	//Observo la recepcion encriptada.
        	/*System.out.println(usuario);
        	System.out.println(contrasena);*/
        	//Intento logear y mando la respuesta al cliente.
        	boolean resultado = UsuarioHibernate.ingresar(new Usuario(apodo, contrasena));
        	return (TipoMensaje.MENSAJEINGRESAR +((resultado)?"ok":"nok"));
        }
        else if(tipoMensaje == TipoMensaje.CLIENTEREGISTRAR)
        {
        	String apodo = Seguridad.decrypt(recepcion[1]);
        	String contrasena = recepcion[2];
        	//Observo la recepcion encriptada.
        	/*System.out.println(usuario);
        	System.out.println(contrasena);*/
        	//Intento logear y mando la respuesta al cliente.
        	boolean resultado = UsuarioHibernate.registrar(new Usuario(apodo, contrasena));
        	return (TipoMensaje.MENSAJEREGISTRAR +((resultado)?"ok":"nok"));
        }
        return null;
	}
}
