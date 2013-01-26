<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="typeList.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/typeList.js"></script> 
</head>
<body>
	
	<div id="header">
		<jsp:include page="header.jsp"/>
	</div>
	
	<div id="navigation">
   		<jsp:include page="navigation.jsp"/>
	</div>	
	
	
	<div id="content">
	 	<div class="pad10">
			<b><spring:message code="typeList.label.listTitle"/></b><br><br>


			<form method="POST" id="typeList" action="typeListAction">
			
				<input type="hidden" id="id"	name="id"	value="">					
				<input type="hidden" id="mode"	name="mode"	value="">
	
				<table>
					<tr align="left">
						<th width="40px"><spring:message code="typeList.label.id"/></th>
						<th width="100px"><spring:message code="typeList.label.code"/></th>
						<th width="150px"><spring:message code="typeList.label.name"/></th>
						<th></th>
						<th width="20px"></th>
						<th></th>
					</tr>
					
						<c:forEach var="type" items="${types}">
							<tr>
<!-- Id -->						<td>
									 <c:out value="${type.id}" />
								</td>
<!-- Code -->					<td>
									 <c:out value="${type.code}" />
								</td>
<!-- Name -->					<td>
									<c:out value="${type.name}" />
								</td>
<!-- Edit button -->			<td>
									<input 	type    = "button" 
											id		= "editType_${type.id}"
								  			value   = "<spring:message code="typeList.button.edit"/>" 
								  			name    = "btnEdit" 
								  			class   = "largeButton" 
								  			onclick = "showSelectedEntry('${type.id}')">	
								</td>
								<td></td>	
<!-- Remove button -->			<td>
									<input 	type    = "button" 
											id		= "removeType_${type.id}"
								  			value   = "<spring:message code="typeList.button.remove"/>" 
								  			name    = "btnRemove" 
								  			class   = "largeButton" 
								  			onclick = "removeSelectedEntry('${type.id}')">	
								</td>							
							</tr>
						</c:forEach>
					<tr height="60px" valign="bottom">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
<!-- Add button -->		<td>
						 	<input 	type	= "button" 
						 			id		= "addType"
						 			value   = "<spring:message code="typeList.button.add"/>" 
						 			name    = "btnAdd" 
						 			class   = "largeButton"  
						 			onclick = "addEntry()">
						</td>
					</tr>
				</table>
			</form>	
		</div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>
	


</body>
</html>