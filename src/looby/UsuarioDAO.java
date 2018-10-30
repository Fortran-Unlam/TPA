package looby;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import servidor.Servidor;

public class UsuarioDAO {

	public static Usuario loguear(String username, String hashPassword) {
		Transaction tx = null;

		try {
			tx = Servidor.getSessionHibernate().beginTransaction();
			tx.commit();

			Query queryLogueo = Servidor.getSessionHibernate()
					.createQuery("SELECT u FROM Usuario u WHERE u.username = '" + username + "' AND u.password = '"
							+ hashPassword + "'");

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
			Servidor.getSessionHibernate().close();
		}

		return null;
	}
}
