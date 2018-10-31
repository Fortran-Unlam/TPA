package looby;

import java.util.List;
import java.util.Random;

import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import servidor.Servidor;

public class UsuarioDAO {

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public static Usuario registrar(String username, String hashPassword) {
		Transaction txReg = null;

		try {
			txReg = Servidor.getSessionHibernate().beginTransaction();

			Query chequearDuplicado = Servidor.getSessionHibernate()
					.createQuery("SELECT u FROM Usuario u WHERE u.username = '" + username + "'");

			List<Usuario> resultList = chequearDuplicado.getResultList();

			if (!resultList.isEmpty()) {
				System.out.println("Ese nombre de usuario ya existe");
			} else {
				Usuario registrar = new Usuario(5, username, hashPassword, 0, 0, 0, 0, 0, 0);
				Servidor.getSessionHibernate().save(registrar);
				txReg.commit();
				
				return registrar;
			}

		} catch (HibernateException e) {
			if (txReg != null)
				txReg.rollback();
			e.printStackTrace();
		} finally {
			Servidor.getSessionHibernate().close();
		}
		return null;
	}
}
