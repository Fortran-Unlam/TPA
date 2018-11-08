package hibernateUtils;

import java.util.logging.Level;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory sessionFactory;

	static {

		try {
			java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
			sessionFactory = new Configuration().configure("/hibernateUtils/hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Consigue la conexion con la base de datos
	 * @return
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
