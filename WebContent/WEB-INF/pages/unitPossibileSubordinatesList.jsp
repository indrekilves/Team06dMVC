<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Possible Subordinates for Admin Unit</title>
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
			<b>Select subordinate for <c:out value="${unit.name}"/></b><br><br>
			
			<form method="POST"	id="unitPossibileSubordinatesList" action="addUnitSubordinateAction">

				<input type="hidden" id="id"	name="id"		value="">
				<input type="hidden" id="subId" name="subId"	value="">		
				<input type="hidden" id="mode"	name="mode"		value="">
				
				<table>
<!-- Select subOrdinate -->						
				
					<tr align="left">
						<th width="100px">Code</th>
						<th width="150px">Name</th>
						<th width="100px">Type</th>						
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
								  			value   = "Select" 
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
								  			value   = "Cancel" 
								  			name    = "btnCancelSelect" 
								  			class   = "largeButton"
								  			onclick = "cancelSubordinateSelect('${unit.id}')">
							</td>					
						</tr>
				</table>
				<br><br><br>
				Note:<br>
				Shown are only these Units that can be subordinates of <c:out value="${unit.name}"/>.
				
			</form>
		    
	    </div>
	</div>
	
	<div id="footer">
		<jsp:include page="footer.jsp"/>
	</div>

</body>
</html>