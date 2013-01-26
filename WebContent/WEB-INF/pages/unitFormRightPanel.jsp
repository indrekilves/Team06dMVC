<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>
<body>
	<table width="300px">
    	<tr class = "tableWithBorder">
    		<th align="left"><spring:message code="unitForm.label.subordinates"/></th>
    		<th></th>				    						    		
    	</tr>
    	
<!-- list of subordinates -->
		<c:forEach var="subOrdinate" items="${unit.subOrdinates}">
			<tr style="border: 1px solid black;">
				<td>
					<c:out value="${subOrdinate.name}" />
				</td>
				<td align="right">
				  	<input 	type    = "button" 
				  			value   = "<spring:message code="unitForm.button.remove"/>" 
				  			id		= "removeSubOrdinateUnit_${subOrdinate.id}"
				  			name    = "btnRemoveSubType" 
				  			class   = "largeButton" 
				  			onclick = "removeSubOrdinate('${unit.id}', '${subOrdinate.id}')">		  			
				</td>			
			</tr>
		</c:forEach>
		
<!-- add new subordinate -->		
		<tr height="40px" valign="bottom">
			<td></td>
			<td align="right">

					
				  	<input 	type    = "button"
				  			id		= "addSubOridinateUnit" 
				  			value   = "<spring:message code="unitForm.button.add"/>" 
				  			name    = "btnAddSubType" 
				  			class   = "largeButton"
				  			onclick = "addSubOrdinate('${unit.id}')">
			</td>
		</tr>
   	</table>
</body>
</html>