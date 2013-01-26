<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="unitTypesList.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitTypesList.js"></script> 
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
			<b><spring:message code="unitTypesList.label.selectType"/> <c:out value="${unit.name}"/></b><br><br>
			
			<form method="POST"	id="unitTypesList" action="changeTypeListAction">

				<input type="hidden" id="id"		name="id"		value="">
				<input type="hidden" id="typeId" 	name="typeId"	value="">		
				<input type="hidden" id="mode"		name="mode"		value="">

				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px"><spring:message code="unitTypesList.label.code"/></th>
						<th width="150px"><spring:message code="unitTypesList.label.name"/></th>
						<th width="50px"></th>
					</tr>
					
						<c:forEach var="type" items="${types}">
							<tr>
								<td>
									 <c:out value="${type.code}" />
								</td>
								<td>
									<c:out value="${type.name}" />
								</td>
								<td>
									<input 	type    = "button" 
								  			value   = "<spring:message code="unitTypesList.button.select"/>" 
								  			name    = "btnSelectSubOrdinate" 
								  			class   = "largeButton" 
								  			onclick = "selectType('${unit.id}', '${type.id}')">	
								</td>
							
							</tr>
						</c:forEach>
						
<!-- Cancel Select -->						
						<tr height="40px" valign="bottom">
							<td></td>
							<td></td>
							<td align="right">
								  	<input 	type    = "button"
								  			value   = "<spring:message code="unitTypesList.button.cancel"/>" 
								  			name    = "btnCancelSelect" 
								  			class   = "largeButton"
								  			onclick = "cancelTypeSelect('${unit.id}')">
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