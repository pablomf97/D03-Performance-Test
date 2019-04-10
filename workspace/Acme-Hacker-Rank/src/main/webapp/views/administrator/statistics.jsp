<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	
		<table class="displayStyle" style="width: 50%">
			<tr>
				<th colspan="2"><spring:message
						code="administrator.statistics.B" /></th>
			</tr>
			<tr>
				<td><spring:message code="administrator.AvgCurriculaPerHacker" /></td>
				<td style="text-align: right">${AvgCurriculaPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.MaxCurriculaPerHacker" /></td>
				<td style="text-align: right">${MaxCurriculaPerHacker}</td>
			</tr>
				<tr>
				<td><spring:message code="administrator.MinCurriculaPerHacker" /></td>
				<td style="text-align: right">${MinCurriculaPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.stdevCurriculaPerHacker" /></td>
				<td style="text-align: right">${stdevCurriculaPerHacker}</td>
			</tr>
			
			
			<tr>
				<td><spring:message code="administrator.emptyFinder" /></td>
				<td style="text-align: right">${ratioFinders}</td>
			</tr>
			
			<tr>
				<td><spring:message code="administrator.resultsFinder" /></td>
				<td style="text-align: right">${statsFinder[0]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.min" /></td>
				<td style="text-align: right">${statsFinder[1]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.avg" /></td>
				<td style="text-align: right">${statsFinder[2]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.stdev" /></td>
				<td style="text-align: right">${statsFinder[3]}</td>
			</tr>
	
	
	
			

		</table>

	</security:authorize>