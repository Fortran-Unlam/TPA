package looby;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

	public static Usuario loguear(Session sessionHibernate, String username, String hashPassword) {
		Transaction tx = null;

		try {
			tx = sessionHibernate.beginTransaction();
			tx.commit();

			Query queryLogueo = sessionHibernate.createQuery("SELECT u FROM Usuario u WHERE u.username = " + username
					+ "AND u.password = " + hashPassword);

			List<Usuario> user = queryLogueo.getResultList();

			if (user.isEmpty()) {
				return null;
			}
			
			// TODO: agregar todos los datos del usuario
			return new Usuario(username, hashPassword);

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			sessionHibernate.close();
		}
		
		return null;
	}
}
