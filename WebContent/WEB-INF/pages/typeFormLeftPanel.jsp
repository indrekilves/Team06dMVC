<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
</head>
<body>
	<table>
	   	<!-- Code -->
	   	<tr>
	    	<td width="100px"><spring:message code="typeForm.label.code"/></td> 
	    			
		   	<td><input name="code" 	value="${type.code}"></td>	
	    </tr>
	
    	<!-- Name -->
	    <tr class="tall">
		    <td><spring:message code="typeForm.label.name"/></td>		
		    <td>
		    <input name="name" 	value="${type.name}">
<%-- 		    <form:input path="name"  /> --%>
<%-- 			<form:errors path="name"/> --%>
			</td>	
	    </tr>
	    
    	<!-- Comment -->
		<tr class="tall">
			<td valign="top"><spring:message code="typeForm.label.comment"/></td>
			<td>
				<textarea name="comment">${type.comment}</textarea>
			</td>
	    </tr>
	    
	    <!-- Subordinate -->
	    
	   	<tr class="tall">
			<td><spring:message code="typeForm.label.subordinateOf"/></td>
			<td>
	
				 
			<select name="bossId">
				<option value=""></option>
				
				<c:forEach var="boss" items="${bossTypes}">
			    	<c:set var="selected" value=""/>
			    	
			    	<c:if test="${boss.id == type.boss.id}">
			     		<c:set var="selected" value="selected=\"selected\""/>
			    	</c:if>
	
					<option value="${boss.id}" ${selected}>${boss.name}</option>
				</c:forEach>
			</select>
		
			</td>
	
		</tr>
		
		<!-- From date  -->
		<tr class="tall">
			<td><spring:message code="typeForm.label.from"/></td>
			<td>
				 <input name  = "fromDate" 
						type  = "text" 
						class = "datePicker"
						value ="<fmt:formatDate 	value   = "${type.fromDate}"  
 													type    = "date" 
 													pattern = "dd.MM.yyyy"/>" 
 				 /> 
			</td>
			
		</tr>
		
		<!-- To date  -->
		<tr class="tall">
			<td><spring:message code="typeForm.label.to"/></td>
			<td>
				 <input name  = "toDate"
						class = "datePicker" 
						type  = "text" 						
						value ="<fmt:formatDate 	value   = "${type.toDate}"  
													type    = "date" 
													pattern = "dd.MM.yyyy"/>"
				 />
			</td>
			
		</tr>
		
	</table>
</body>
</html>