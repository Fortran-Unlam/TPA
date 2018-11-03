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
			System.err.println("Couldn't create session factory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
