<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>
<body>
	<br>
	<table style="width: 100%">
		<tr>
			<td style="width: 20%;text-align: left;padding-left: 10px">
			  <a href="?lang=en">en</a> | <a href="?lang=et">et</a>
			</td>
			
			<td align="center">
				<spring:message code="header.label.name"/>
			</td>
			
			<td style="width: 20%;text-align: right;padding-right: 10px">
				<a href="j_spring_security_logout"><spring:message code="header.link.logout"/></a>
			</td>
			
		</tr>
	</table>
	<br>
</body>
</html>