<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="employee_management_system.entities.Employee"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Form</title>
</head>
<body>
<%
    Employee emp = (Employee) request.getAttribute("employee");
    boolean editing = (emp != null);
%>
	<form action="employee" method="post">
	 <input type="hidden" name="action" value="<%= editing ? "edit" : "create" %>">
	 <% if (editing) { %>
        <input type="hidden" name="id" value="<%= emp.getId() %>">
    <% } %>
	Name       : <input type = "text" name = "employeeName" value = <%= editing ? emp.getName() : "" %>><br>
	Email      : <input type = "text" name = "employeeEmail" value = <%= editing ? emp.getEmail() : "" %>><br>
	Department : <input type = "text" name = "employeeDept" value = <%= editing ? emp.getDepartment() : "" %>><br>
	 <input type="submit" value="<%= editing ? "Update Employee" : "Add Employee" %>">
	</form>
</body>

</html>
