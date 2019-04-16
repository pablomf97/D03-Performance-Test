<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->



<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">
	<!-- Attributes-->

	<display:column titleKey="position.title" sortable="true">
		<jstl:out value="${row.title }"></jstl:out>
	</display:column>
	<display:column titleKey="position.deadline" sortable="true">
		<jstl:out value="${row.deadline }"></jstl:out>
	</display:column>
	<display:column titleKey="position.profileRequired" sortable="true">
		<jstl:out value="${row.profileRequired }"></jstl:out>
	</display:column>
	<display:column titleKey="position.salary" sortable="true">
		<jstl:out value="${row.salary }"></jstl:out>
	</display:column>
	<display:column titleKey="position.ticker" sortable="true">
		<jstl:out value="${row.ticker }"></jstl:out>
	</display:column>
	<display:column titleKey="position.company" sortable="true">
		<jstl:out value="${row.company.userAccount.username }"></jstl:out>
	</display:column>
	<%-- 	<jstl:if test="${name == row.company.commercialName} }">
 --%>
	<display:column titleKey="position.isDraft" sortable="true">
		<jstl:out value="${row.isDraft }"></jstl:out>
	</display:column>
	<display:column titleKey="position.isCancelled" sortable="true">
		<jstl:out value="${row.isCancelled }"></jstl:out>
	</display:column>

	<!-- Action links -->
	<display:column titleKey="position.edit" sortable="true">
		<jstl:if test="${row.isDraft eq true}">
			<a href="position/edit.do?Id=${row.id}"> <spring:message
					code="position.edit" />
			</a>
		</jstl:if>
	</display:column>
	<%-- 	</jstl:if>
 --%>
	<display:column>
		<a href="position/display.do?Id=${row.id}"> <spring:message
				code="position.display" />
		</a>
	</display:column>

</display:table>
