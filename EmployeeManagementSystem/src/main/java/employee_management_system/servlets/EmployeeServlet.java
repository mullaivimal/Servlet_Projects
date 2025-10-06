package employee_management_system.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import employee_management_system.dao.EmployeeDao;
import employee_management_system.entities.Employee;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/employee", initParams = {@WebInitParam(name="dept", value="engineering")})
public class EmployeeServlet extends HttpServlet{

	private EmployeeDao dao = new EmployeeDao();
	
	@Override
	public void init() throws ServletException {
		if(getServletContext().getAttribute("employeeMap")==null) {
			Map<Long, Employee> map = new HashMap<>();
			getServletContext().setAttribute("employeeMap", map);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		switch(action) {
		case "create":
			String name = req.getParameter("employeeName");
			String email = req.getParameter("employeeEmail");
			String dept = req.getParameter("employeeDept");
			
			if(dept==null || dept.trim().isEmpty())
				dept = getServletConfig().getInitParameter("dept");
			Employee employee = new Employee(name, email, dept);
			//Storing Employee inside ServletContext
			Map<Long, Employee> map = (Map<Long, Employee>) getServletContext().getAttribute("employeeMap");
			map.put(employee.getId(), employee);
			
			dao.saveEmployee(employee);
			//Redirect to employee List
			resp.sendRedirect("employee");
			break;
			
		case "edit":
			int id = Integer.parseInt(req.getParameter("id"));
			String name1 = req.getParameter("employeeName");
			String email1 = req.getParameter("employeeEmail");
			String dept1 = req.getParameter("employeeDept");
			
			Employee updated = new Employee(id, name1, email1, dept1);
			
			//Updating Global Map
			Map<Long, Employee> map1 = (Map<Long, Employee>) getServletContext().getAttribute("employeeMap");
			map1.put(updated.getId(), updated);
			
			//Storing Employee ID in the HTTPSession
			HttpSession session= req.getSession();
			session.setAttribute("id", updated.getId());
			
			//Storing Employee name in Cookie
			Cookie cookie = new Cookie("name", updated.getName());
			cookie.setMaxAge(60 * 60 * 24); // 1 day
			resp.addCookie(cookie);
			
			dao.updateEmployee(updated);
			
			//Redirecting to Employee List
			resp.sendRedirect("employee");
			break;
		}
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getParameter("action");

	    if ("edit".equals(action)) {
	        int id = Integer.parseInt(req.getParameter("id"));
	        Employee employee = dao.getEmployee(id);
	        req.setAttribute("employee", employee);
	        req.getRequestDispatcher("employeeForm.jsp").forward(req, resp);
	    } else if ("delete".equals(action)) {
	    	long id = Integer.parseInt(req.getParameter("id"));
            dao.deleteEmployee(id);
            
            // Remove Employee from Global Map
            Map<Long, Employee> map1 = (Map<Long, Employee>) getServletContext().getAttribute("employeeMap");
            map1.remove(id);
            
            resp.sendRedirect("employee");
        } else {
	        List<Employee> list = dao.findAllEmployees();
	        req.setAttribute("employees", list);
	        req.getRequestDispatcher("employeeList.jsp").forward(req, resp);
	    }
	}
}
