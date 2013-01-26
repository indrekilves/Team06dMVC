<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>
<body>
	<div class="pad10">
	 
		<a href="/Team06dMVC"		class="navigation"><spring:message code="navigation.link.homepage"/></a><br>	
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
			<a href="showTypeList"		class="navigation"><spring:message code="navigation.link.types"/></a><br>
			<a href="showUnitList"		class="navigation"><spring:message code="navigation.link.units"/></a><br>
		</sec:authorize>
			
		<a href="showUnitReport"	class="navigation"><spring:message code="navigation.link.unitReport"/></a><br>
		<a href="showTypeTree"		class="navigation"><spring:message code="navigation.link.typesTree"/></a><br>
		<br><br><br> 			

		<sec:authorize ifAnyGranted="ROLE_ADMIN">
			<a href="insertTestData"	class="navigation"><spring:message code="navigation.link.insertTestData"/></a><br>
			<a href="deleteAllData"		class="navigation"><spring:message code="navigation.link.deleteAllData"/></a><br>
	 		<a href="clearDbLock"		class="navigation"><spring:message code="navigation.link.clearDbLock"/></a><br>
	 		<a href="deleteDatabase"	class="navigation"><spring:message code="navigation.link.deleteDb"/></a><br>
		</sec:authorize>

	</div>
</body>
</html>