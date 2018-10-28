package HibernateUtils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils 
{
	private static SessionFactory sessionFactory;  
	
	static
	{
		sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}
