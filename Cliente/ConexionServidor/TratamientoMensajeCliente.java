package ConexionServidor;

import Constantes.TipoMensaje;
import Entidades.Usuario;
import Entidades.UsuarioHibernate;
import Login.PantallaLogin;
import Seguridad.Seguridad;

public class TratamientoMensajeCliente 
{
	public static String tratarMensaje(String mensajeRecibido)
	{
		String[] recepcion = mensajeRecibido.split(";");
        int tipoMensaje = Integer.valueOf(recepcion[0]);
        //RECEPCION LOGIN(SERVIDOR).
        if(tipoMensaje == TipoMensaje.SERVIDORINGRESAR)
        {
        	String respuesta = recepcion[1];
        	if(respuesta.equals("ok"))
        		PantallaLogin.lblEstado.setText("Conectado");
        	else
        		PantallaLogin.lblEstado.setText("Usuario y/o contrasena invalido");
        }
        //RECEPCION REGISTRAR(SERVIDOR).
        else if(tipoMensaje == TipoMensaje.SERVIDOREGISTRAR)
        {
        	String respuesta = recepcion[1];
        	if(respuesta.equals("ok"))
        		PantallaLogin.lblEstado.setText("Usuario registrado");
        	else
        		PantallaLogin.lblEstado.setText("Usuario ya existente");
        }
        return null;
	}
}
