<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%
	String name = (String) pageContext.getAttribute("user", PageContext.SESSION_SCOPE);
%>

<%-- <jstl:if
	test="${(isPrincipal || position.id ==0) && not empty actorBrother.zone}"> --%>
<form:form modelAttribute="position" action="position/edit.do" id="form">
	<fieldset>
		<br>
		<form:hidden path="id" />
		<form:hidden path="isDraft" />
		<form:hidden path="isCancelled" />

		<acme:textbox code="position.title" path="title" />
		<br /> <br />

		<acme:textarea code="position.description" path="description" />
		<br /> <br />

		<acme:textbox code="position.deadline" path="deadline" />
		<br /> <br />

		<acme:textarea code="position.profileRequired" path="profileRequired" />
		<br /> <br />

		<acme:textarea code="position.technologiesRequired"
			path="technologiesRequired" />
		<br /> <br />

		<acme:textbox code="position.salary" path="salary" />
		<br /> <br />

		<acme:textarea code="position.skillsRequired" path="skillsRequired" />
		<br /> <br />
	</fieldset>
	<fieldset>

		<form:label path="problems">
			<spring:message code="position.problems" />
		</form:label>
		<br>
		<form:select multiple="true" path="problems" items="${problems}"
			itemLabel="title" />
		<br> <br>
	</fieldset>
	<br />
	<jstl:if test="${position.isDraft == true || position.id == 0}">
		<acme:submit code="position.save" name="save" />&nbsp;
		<acme:submit code="position.save.final" name="saveFinal" />&nbsp; 
	</jstl:if>
	<jstl:if test="${position.id != 0}">
		<button
			onClick="window.location.href='position/delete.do?Id=${position.id}'">
			<spring:message code="position.confirm.delete" />
		</button>
	</jstl:if>
	<acme:cancel code="position.cancel" url="position/list.do" />
	<br />

</form:form>
<%-- </jstl:if> --%>