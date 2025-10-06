<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="jakarta.servlet.http.Cookie"%>
<%@ page import="employee_management_system.entities.Employee"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee List</title>
</head>
<body>
	<%
	List<Employee> employees = (List<Employee>) request.getAttribute("employees");
	%>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Email</th>
			<th>Department</th>
		</tr>
		<%
		if (employees != null) {
			for (Employee e : employees) {
		%>
		<tr>
			<td><%=e.getId()%></td>
			<td><%=e.getName()%></td>
			<td><%=e.getEmail()%></td>
			<td><%=e.getDepartment()%></td>
			<td>
				<a href="<%= request.getContextPath() %>/employee?action=edit&id=<%= e.getId() %>">Edit</a>
				<a href="<%= request.getContextPath() %>/employee?action=delete&id=<%= e.getId() %>"
				   onclick="return confirm('Are you sure you want to delete this employee?');">
				   Delete
				</a>
			</td>
		</tr>
		<%
		}
		}
		%>
	</table>
	
	<%
 
    Long lastUpdatedId = (Long) session.getAttribute("id");
	
    String lastUpdatedName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("name".equals(c.getName())) {
                lastUpdatedName = c.getValue();
                break;
            }
        }
    }
%>
<h3>Last Updated Employee Details:</h3>
<p>
    <strong>ID:</strong> <%= (lastUpdatedId != null) ? lastUpdatedId : "N/A" %><br>
    <strong>Name:</strong> <%= (lastUpdatedName != null) ? lastUpdatedName : "N/A" %>
</p>

</body>
</html>