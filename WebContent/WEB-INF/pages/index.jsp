<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    
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
		<p class="lPad100">		
		
					<br><br><br><br><br>

			Welcome to BoarderGuard webApplication.<br><br>
 			First please <a href="insertTestData">insert test data</a>.<br><br><br><br><br><br><br>
 			Note: If something fails use <a href="clearDbLock">clear db.lck</a> option. <br>
 			Delete database requires admin role, superuser / kti.
 			
 			
 			
<!-- 			<br><br><br><br><br> -->
<%-- 			<spring:message code="label.indexPage_line1"/><br><br> --%>
<%-- 			<spring:message code="label.indexPage_line2"/> --%>
<!-- 			<a href="insertTestData"> -->
<%-- 				<spring:message code="label.indexPage_line2_link"/> --%>
<!-- 			</a><br><br><br><br><br><br><br>  -->
<%-- 			<spring:message code="label.indexPage_line3"/><br> --%>
<!-- 			<a href="clearDbLock"> -->
<%-- 				<spring:message code="label.indexPage_line3_link1"/> --%>
<!-- 			</a> |   -->
<!-- 			<a href="deleteDatabase"> -->
<%-- 				<spring:message code="label.indexPage_line3_link2"/> --%>
<!-- 			</a> -->
			
<!-- 			<br><br> -->
<%--  			Current Locale : ${pageContext.response.locale} --%>
 			
		</p>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>