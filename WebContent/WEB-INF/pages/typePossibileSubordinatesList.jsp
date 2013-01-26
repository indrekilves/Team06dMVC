<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="typeSubOrdList.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/typePossibileSubordinatesList.js"></script> 
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
			<b><spring:message code="typeSubOrdList.label.selectSubOrd"/> <c:out value="${type.name}"/></b><br><br>
			
			<form method="POST"	id="typePossibileSubordinatesList" action="addTypeSubordinateAction">

				<input type="hidden" id="id"	name="id"		value="">
				<input type="hidden" id="subId" name="subId"	value="">		
				<input type="hidden" id="mode"	name="mode"		value="">
				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px"><spring:message code="typeSubOrdList.label.code"/></th>
						<th width="150px"><spring:message code="typeSubOrdList.label.name"/></th>
						<th width="50px"></th>
					</tr>
					
						<c:forEach var="possibleSubordinate" items="${possibleSubordinates}">
							<tr>
								<td>
									 <c:out value="${possibleSubordinate.code}" />
								</td>
								<td>
									<c:out value="${possibleSubordinate.name}" />
								</td>
								<td>
									<input 	type    = "button" 
								  			value   = "<spring:message code="typeSubOrdList.button.select"/>" 
								  			name    = "btnSelectSubOrdinate" 
								  			class   = "largeButton" 
								  			onclick = "selectSubOrdinate('${type.id}', '${possibleSubordinate.id}')">	
								</td>
							
							</tr>
						</c:forEach>
						
<!-- Cancel Select -->						
						<tr height="40px" valign="bottom">
							<td></td>
							<td></td>
							<td align="right">
								  	<input 	type    = "button"
								  			value   = "<spring:message code="typeSubOrdList.button.cancel"/>" 
								  			name    = "btnCancelSelect" 
								  			class   = "largeButton"
								  			onclick = "cancelSubordinateSelect('${type.id}')">
							</td>					
						</tr>
				</table>
				<br><br><br>
				NB:<br>
				<spring:message code="typeSubOrdList.label.disclaimer"/> <c:out value="${type.name}"/>.
				
			</form>
		    
	    </div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>