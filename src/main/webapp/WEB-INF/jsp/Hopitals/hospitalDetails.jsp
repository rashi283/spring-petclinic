<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>Hospital Information</h2>

    <table class="table table-striped" style="width:600px;">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${hospital.name} ${hospital.name}"/></b></td>
        </tr>
        <tr>
            <th>Address</th>
            <td><c:out value="${hospital.address}"/></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${hospital.city}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${hospital.telephone}"/></td>
        </tr>
         <tr>
            <td> 
            	<spring:url value="{hospitalId}/edit.html" var="editUrl">
                    <spring:param name="hospitalId" value="${hospital.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(editUrl)}" class="btn btn-info">Edit Hospital</a></td>
            <td>
            	<spring:url value="{hospitalId}/doctors/new.html" var="addUrl">
                    <spring:param name="hospitalId" value="${hospital.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(addUrl)}"  class="btn btn-success">Add New Doctor</a></td>
        </tr>
    </table>

    <h2>Doctors and Visits</h2>

    <c:forEach var="doctor" items="${hospital.doctors}">
        <table class="table" style="width:600px;">
            <tr>
                <td valign="top" style="width: 120px;">
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${doctor.name}"/></dd>
                        <dt>Birth Date</dt>
                        <dd><joda:format value="${doctor.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt>Type</dt>
                        <dd><c:out value="${doctor.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>Visit Date</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${doctor.visits}">
                            <tr>
                                <td><joda:format value="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td> 
                            	<spring:url value="/hospitals/{hospitalId}/doctors/{doctorId}/edit" var="doctorUrl">
			                        <spring:param name="hospitalId" value="${hospital.id}"/>
			                        <spring:param name="doctorId" value="${doctor.id}"/>
			                    </spring:url>
			                    <a href="${fn:escapeXml(doctorUrl)}">Edit Doctor</a>
			                </td>
                            <td>
			                    <spring:url value="/hospitals/{hospiatlId}/doctors/{doctorId}/visits/new" var="visitUrl">
			                        <spring:param name="hospitalId" value="${hospital.id}"/>
			                        <spring:param name="doctorId" value="${doctor.id}"/>
			                    </spring:url>
			                    <a href="${fn:escapeXml(visitUrl)}">Add Visit</a>
                            </td>
                       	</tr>
                    </table>
                </td>
            </tr>
        </table>
    </c:forEach>

    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>

</html>
