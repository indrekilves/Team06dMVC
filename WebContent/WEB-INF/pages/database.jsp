<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>BoarderGuard - Team06d</title>
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
		<div id="innerContainer">	
			<p class="lPad100">			
				Test data inserted.
				<br><br>Types<br>
				<c:forEach var = "type" items="${types}" >
					<c:out value="${type.name}"/><br>
				</c:forEach>
			
			    <br><br>TypeSubordinates<br>	
				<c:forEach var = "ts" items="${typeSubordinates}" >
					<c:out value="${ts.bossId}"/> - <c:out value="${ts.subOrdinateId}"/><br>
				</c:forEach>
	
			</p>
		</div>	
	</div>
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>