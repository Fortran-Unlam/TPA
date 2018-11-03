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
			String query = "SELECT u FROM Usuario u WHERE u.username = '" + username + "' AND u.password = '"
					+ hashPassword + "'";
			System.out.println(query);
			Query queryLogueo = Servidor.getSessionHibernate()
					.createQuery(query);

			Usuario user = (Usuario) queryLogueo.getSingleResult();

			if (user == null) {
				return null;
			}

			return new Usuario(user.getId(), username, hashPassword, user.getPuntos(),
					user.getCantidadFrutaComida(), user.getAsesinatos(), user.getMuertes(),
					user.getPartidasGanadas(), user.getRondasGanadas());

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static int registrar(String username, String hashPassword) {
		Transaction txReg = null;

		try {
			txReg = Servidor.getSessionHibernate().beginTransaction();

			Query chequearDuplicado = Servidor.getSessionHibernate()
					.createQuery("SELECT u FROM Usuario u WHERE u.username = '" + username + "'");

			List<Usuario> resultList = chequearDuplicado.getResultList();

			if (!resultList.isEmpty()) {

				System.out.println("Ese nombre de usuario ya existe");
				return 1;
				
			} else {

				Query queryMaxID = Servidor.getSessionHibernate().createQuery("SELECT max(u.id) FROM Usuario u");
				List<Integer> id = queryMaxID.getResultList();
				Usuario registrar = new Usuario(id.get(0).intValue() + 1, username, hashPassword, 0, 0, 0, 0, 0, 0);
				Servidor.getSessionHibernate().save(registrar);
				return 0;
			}

		} catch (HibernateException e) {
			if (txReg != null)
				txReg.rollback();
			e.printStackTrace();
		} finally {
			txReg.commit();
		}
		return -1;
	}
}
