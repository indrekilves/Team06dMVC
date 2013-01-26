<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="unitList.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitList.js"></script> 
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
			<b><spring:message code="unitList.title"/></b><br><br>


			<form method="POST" id="unitList" action="unitListAction">
			
				<input type="hidden" id="id"	name="id"	value="">					
				<input type="hidden" id="mode"  name="mode"	value="">
					
				<table>
					<tr align="left">
						<th width="40px"><spring:message code="unitList.label.id"/></th>
						<th width="100px"><spring:message code="unitList.label.code"/></th>
						<th width="150px"><spring:message code="unitList.label.name"/></th>
						<th width="100px"><spring:message code="unitList.label.type"/></th>
						<th></th>
						<th width="20px"></th>
						<th></th>
					</tr>
					
						<c:forEach var="unit" items="${units}">
							<tr>
<!-- Id -->						<td>
									 <c:out value="${unit.id}" />
								</td>
<!-- Code -->					<td>
									 <c:out value="${unit.code}" />
								</td>
<!-- Name -->					<td>
									<c:out value="${unit.name}" />
								</td>
<!-- Type -->					<td>
									<c:out value="${unit.type.name}" />
								</td>

<!-- Edit button -->			<td>
									<input 	type    = "button" 
											id		= "editUnit_${unit.id}"
								  			value   = "<spring:message code="unitList.button.edit"/>" 
								  			name    = "btnEdit" 
								  			class   = "largeButton" 
								  			onclick = "showSelectedEntry('${unit.id}')">	
								</td>
								<td></td>	
<!-- Remove button -->			<td>
									<input 	type    = "button" 
											id 		= "removeUnit_${unit.id}"
								  			value   = "<spring:message code="unitList.button.remove"/>" 
								  			name    = "btnRemove" 
								  			class   = "largeButton" 
								  			onclick = "removeSelectedEntry('${unit.id}')">	
								</td>							
							</tr>
						</c:forEach>
					<tr height="60px" valign="bottom">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
<!-- Add button -->		<td>
						 	<input 	type	= "button" 
						 			id 		= "addUnit"
						 			value   = "<spring:message code="unitList.button.add"/>" 
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