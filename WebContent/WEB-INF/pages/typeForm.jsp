<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  	uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="typeForm.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/typeForm.js"></script> 
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
	
			
			<div id="messages">
			    <c:if test="${not empty statusMessageKey}">
			       <p><fmt:message key="${statusMessageKey}"/></p>
			    </c:if>
			
			    <spring:hasBindErrors name="type">
			        <div class="formError">
			        	<b><spring:message code="typeForm.label.errors"/>:</b>
			            <ul>
				            <c:forEach var="error" items="${errors.allErrors}">
				                <li>${error.defaultMessage}</li>
				            </c:forEach>
			            </ul>
			        </div>
			    </spring:hasBindErrors>
			</div>
		
		
 			<form method="POST" id="typeForm" action = "typeFormAction"> 
	
				<input type="hidden" id="id"	name="id"		value="">					
				<input type="hidden" id="subId" name="subId"	value="">					
				<input type="hidden" id="mode"	name="mode"		value="">
					
				<table>
					<tr>
<!-- Left panel --> 	<td>
 	  						<jsp:include page="typeFormLeftPanel.jsp"/>			    	
						</td>
					
					
<!-- Center panel -->	<td width="15px"></td>
						
<!-- Right panel -->	<td valign="top">
							<jsp:include page="typeFormRightPanel.jsp"/>			    	
						</td>
						
					</tr>
					<tr>
						<td></td>
					    <td></td>
						<td align="right">	
							<table>
								<tr height="60px" valign="bottom">
<!-- Save button -->				<td>			
									 	<input 	type	= "button" 
									 			id		= "saveType"
									 			value	= "<spring:message code="typeForm.button.save"/>" 
									 			class 	= "largeButton"  
									 			onclick	= "saveForm('${type.id}')">
									</td>
<!-- Cancel button -->				<td>
									 	<input 	type	= "button" 
									 			id 		= "cancelType"
									 			value	= "<spring:message code="typeForm.button.cancel"/>" 
									 			class 	= "largeButton"  
									 			onclick	= "cancelForm('${type.id}')">
									</td>
								</tr>					
							</table>
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