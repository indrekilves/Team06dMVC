<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="database.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>
<body>

	<div id="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<div id="navigation">
   		<jsp:include page="navigation.jsp"/>
	</div>	
	
	<div id="content">
		<br><br><br><br><br>
		<p class="lPad100">			
			<c:out value="${status}"/>
			

			<c:if test="${not empty types}">
				<br><br><b>Types:</b><br>
				<c:forEach var = "type" items="${types}" >
					<c:out value="${type.name}"/>, 
				</c:forEach>
			</c:if>
			
			<c:if test="${not empty typeAssociations}">
			    <br><br><b>Type Associations:</b><br>	
				<c:forEach var = "ta" items="${typeAssociations}" >
					<c:out value="${ta.subOrdinate.name}"/> > <c:out value="${ta.boss.name}"/>, 
				</c:forEach>
			</c:if>

			<c:if test="${not empty units}">
				<br><br><b>Units:</b><br>
				<c:forEach var = "unit" items="${units}" >
					<c:out value="${unit.name}"/>, 
				</c:forEach>
			</c:if>
			
			<c:if test="${not empty unitAssociations}">
			    <br><br><b>Unit Associations:</b><br>	
				<c:forEach var = "ua" items="${unitAssociations}" >
					<c:out value="${ua.subOrdinate.name}"/> > <c:out value="${ua.boss.name}"/>, 
				</c:forEach>
			</c:if>

			
		</p>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>