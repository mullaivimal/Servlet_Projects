package employee_management_system.utility;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("vimal");
	}
}
