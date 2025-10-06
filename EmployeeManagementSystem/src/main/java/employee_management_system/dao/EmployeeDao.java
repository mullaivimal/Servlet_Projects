package employee_management_system.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import employee_management_system.entities.Employee;
import employee_management_system.utility.HibernateUtil;

public class EmployeeDao {
	
	private EntityManagerFactory factory = HibernateUtil.getEntityManagerFactory();
	private EntityManager manager = factory.createEntityManager();
	private EntityTransaction transaction = manager.getTransaction();
	
	
	public void saveEmployee(Employee employee) {
		
		transaction.begin();
		manager.persist(employee);
		transaction.commit();
	}
	
	public List<Employee> findAllEmployees(){
		Query query =manager.createQuery("select e from Employee e");
		return query.getResultList();
	}
	
	public Employee getEmployee(long id) {
		return manager.find(Employee.class, id);
	}
	
	public void updateEmployee(Employee employee) {
		transaction.begin();
		manager.merge(employee);
		transaction.commit();
	}
	
	public void deleteEmployee(long id) {
		Employee employee = getEmployee(id);
		
		transaction.begin();
		manager.remove(employee);
		transaction.commit();
	}
}
