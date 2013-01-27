<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="unitForm.title"/></title>
	<meta 	http-equiv="Content-Type" 	content="text/html; charset=UTF-8">
	<link  	type="text/css"				rel="stylesheet"	 href="./css/style.css">
 	<script type="text/javascript" 		src="js/unitForm.js"></script> 
</head>


<body  onload="onLoadUnitForm()">

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
			
			    <spring:hasBindErrors name="unit">
			        <div class="formError">
			        	<b><spring:message code="unitForm.label.errors"/>:</b>
			            <ul>
				            <c:forEach var="error" items="${errors.allErrors}">
				                <li>${error.defaultMessage}</li>
				            </c:forEach>
			            </ul>
			        </div>
			    </spring:hasBindErrors>
			</div>
		
			<form method="POST" id="unitForm" action = "unitFormAction"> 
	
				<input type="hidden" id="id"		name="id"		value="">					
				<input type="hidden" id="subId" 	name="subId"	value="">					
				<input type="hidden" id="typeId"	name="typeId"	value="${unit.type.id}">
				<input type="hidden" id="mode"		name="mode"		value="">
				<input type="hidden" id="origin" 	name="origin"	value="${origin}">					
				
					
				<table>
					<tr>
<!-- Left panel --> 	<td>
 	  						<jsp:include page="unitFormLeftPanel.jsp"/>			    	
						</td>
					
					
<!-- Center panel -->	<td width="15px"></td>
						
<!-- Right panel -->	<td valign="top">
							<jsp:include page="unitFormRightPanel.jsp"/>			    	
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
									 			id		= "saveUnit"
									 			value	= "<spring:message code="unitForm.button.save"/>" 
									 			class 	= "largeButton"  
									 			onclick	= "saveForm('${unit.id}')">
									</td>
<!-- Cancel button -->				<td>
									 	<input 	type	= "button" 
									 			id 		= "cancelUnit"
									 			value	= "<spring:message code="unitForm.button.cancel"/>" 
									 			class 	= "largeButton"  
									 			onclick	= "cancelForm('${unit.id}')">
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
