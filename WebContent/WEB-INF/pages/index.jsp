<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="index.title"/></title>
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
			<spring:message code="index.label.welcome"/><br><br>
			<sec:authorize ifAnyGranted="ROLE_ADMIN">
				<spring:message code="index.label.insertTestData"/>
				<a href="insertTestData">
					<spring:message code="index.link.insertTestData"/>
				</a><br><br><br><br><br><br><br> 
				<spring:message code="index.label.ifFails"/><br>
				<a href="clearDbLock">
					<spring:message code="index.link.clearDbLock"/>
				</a> |  
				<a href="deleteDatabase">
					<spring:message code="index.link.deleteDb"/>
				</a>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_USER">
				<spring:message code="index.label.userProblems"/>
			</sec:authorize>
			
<!-- 			<br><br> -->
<%--  			Current Locale : ${pageContext.response.locale} --%>
 			
		</p>
	</div>

	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>