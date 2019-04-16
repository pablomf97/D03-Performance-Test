<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="problem/edit.do" modelAttribute="problem" id="form">
	<fieldset>
		<br>
		<form:hidden path="id" />
		<form:hidden path="isDraft" />

		<acme:textbox code="problem.title" path="title" />
		<br> <br>

		<acme:textbox code="problem.statement" path="statement" />
		<br> <br>

		<acme:textbox code="problem.optionalHint" path="optionalHint" />
		<br> <br>

		<acme:textbox code="problem.attachments" path="attachments" />
		<br /> <br />
	</fieldset>
	<br />

	<jstl:if test="${problem.isDraft == true || problem.id == 0}">
		<acme:submit code="problem.save" name="save" />&nbsp;
					<acme:submit code="problem.save.final" name="saveFinal" />&nbsp; 
				</jstl:if>
	<jstl:if test="${problem.id != 0}">
		<button
			onClick="window.location.href='problem/delete.do?Id=${problem.id}'">
			<spring:message code="position.confirm.delete" />
		</button>
	</jstl:if>
	<acme:cancel code="problem.cancel" url="problem/list.do" />
	<br />
	<br />

</form:form>
