<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="unitSubOrdList.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitPossibileSubordinatesList.js"></script> 
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
			<b><spring:message code="unitSubOrdList.label.selectSubOrd"/> <c:out value="${unit.name}"/></b><br><br>
			
			<form method="POST"	id="unitPossibileSubordinatesList" action="addUnitSubordinateAction">

				<input type="hidden" id="id"	name="id"		value="">
				<input type="hidden" id="subId" name="subId"	value="">		
				<input type="hidden" id="mode"	name="mode"		value="">
				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px"><spring:message code="unitSubOrdList.label.code"/></th>
						<th width="150px"><spring:message code="unitSubOrdList.label.name"/></th>
						<th width="100px"><spring:message code="unitSubOrdList.label.type"/></th>						
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
									<c:out value="${possibleSubordinate.type.name}" />
								</td>
								<td>
									<input 	type    = "button" 
								  			value   = "<spring:message code="unitSubOrdList.button.select"/>" 
								  			name    = "btnSelectSubOrdinate" 
								  			class   = "largeButton" 
								  			onclick = "selectSubOrdinate('${unit.id}', '${possibleSubordinate.id}')">	
								</td>
							
							</tr>
						</c:forEach>
						
<!-- Cancel Select -->						
						<tr height="40px" valign="bottom">
							<td></td>
							<td></td>
							<td></td>
							<td align="right">
								  	<input 	type    = "button"
								  			value   = "<spring:message code="unitSubOrdList.button.cancel"/>" 
								  			name    = "btnCancelSelect" 
								  			class   = "largeButton"
								  			onclick = "cancelSubordinateSelect('${unit.id}')">
							</td>					
						</tr>
				</table>
				<br><br><br>
				NB:<br>
				<spring:message code="unitSubOrdList.label.disclaimer"/> <c:out value="${unit.name}"/>.
				
			</form>
		    
	    </div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>