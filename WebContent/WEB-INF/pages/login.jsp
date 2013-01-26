<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="index.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="./css/style.css">
</head>
<body onload='document.f.j_username.focus();'>
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>

	<div id="navigation">
	</div>

	<div id="content">
		<div class="pad10">
			<%
				try {
					if (request.getParameter("failed").equals(""))
						out.println("<div class='formError'>Login failed!</div>");
				} catch (Exception e) {
				}
			%>
			<h3>Login with Username and Password</h3>
			<form name='f' action='/Team06dMVC/j_spring_security_check'
				method='POST'>
				<table>
					<tr>
						<td>User:</td>
						<td><input type='text' name='j_username' value=''></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='j_password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit"
							value="Login" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>