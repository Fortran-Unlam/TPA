package looby;

import java.util.List;
import javax.persistence.Query;
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

			return new Usuario(user.get(0).getId(), username, hashPassword, user.get(0).getPuntos(),
					user.get(0).getCantidadFrutaComida(), user.get(0).getAsesinatos(), user.get(0).getMuertes(),
					user.get(0).getPartidasGanadas(), user.get(0).getRondasGanadas());

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
				return null;
				
			} else {

				Query queryMaxID = Servidor.getSessionHibernate().createQuery("SELECT max(u.id) FROM Usuario u");
				List<Integer> id = queryMaxID.getResultList();
				Usuario registrar = new Usuario(id.get(0).intValue() + 1, username, hashPassword, 0, 0, 0, 0, 0, 0);
				Servidor.getSessionHibernate().save(registrar);
				return registrar;
			}

		} catch (HibernateException e) {
			if (txReg != null)
				txReg.rollback();
			e.printStackTrace();
		} finally {
			txReg.commit();
			Servidor.getSessionHibernate().close();
		}
		return null;
	}
}
