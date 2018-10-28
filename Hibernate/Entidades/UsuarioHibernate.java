package Entidades;
import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import Servidor.*;

/**
 * Se separa de la clase Usuario, para diferenciar bien la logica que podria llegar a tener
 * la clase Usuario, con respecto a UsuarioHibernate que contiene las operaciones que se pueden
 * efectuar en la base de datos con un objeto o entidad tipo Usuario.
 */
public class UsuarioHibernate 
{
	@SuppressWarnings("deprecation")
	/**
	 * 
	 * @param Usuario que intenta logearse.
	 * @return true / false, si las creedenciales coinciden.
	 */
	public static boolean ingresar(Usuario u)
	{
		Session s = Servidor.getSession();
		Query<?> query = s.createQuery(
		        "SELECT COUNT(*) from usuarios AS U WHERE U.apodo=:apodo and U.contrasena=:contrasena");
		query.setString("apodo", u.getApodo());
		query.setString("contrasena", u.getContrasena());
		Long count = (Long)query.uniqueResult();
		return (count==1)? true:false;
	}
	/**
	 * 
	 * @param Usuario a registrar
	 * @return true  / false, el unico fallo posible, es que se intente registrar un usuario
	 * con un apodo ya existente. Pueden existir mas fallos pero que no sean propiamente de la logica
	 * del registro de un nuevo usuario, sino externas interrupcion de la conexion con la BD, etc.
	 */
	public static boolean registrar(Usuario u)
	{
		try
		{
			Session s = Servidor.getSession();
			Transaction t = s.beginTransaction();
			int id = (Integer)s.save(u);
			System.out.println("Registro de un nuevo usuario con id:"+id);
			t.commit();
			return true;
		}
		catch(ConstraintViolationException e)
		{
			return false;
		}
	}
}
