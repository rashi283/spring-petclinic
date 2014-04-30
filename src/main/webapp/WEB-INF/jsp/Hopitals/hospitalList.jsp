<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <h2>Hospitals</h2>
    
    <datatables:table id="hospitals" data="${selections}" cdn="true" row="hospital" theme="bootstrap2" 
                      cssClass="table table-striped" paginate="false" info="false" export="pdf">
        <datatables:column title="Name" cssStyle="width: 150px;" display="html">
            <spring:url value="/hospitals/{hospitalId}.html" var="hospitalUrl">
                <spring:param name="hospitalId" value="${hospital.id}"/>
            </spring:url>
            <a href="${fn:escapeXml(hospitalUrl)}"><c:out value="${hospital.name}"/></a>
        </datatables:column>
        <datatables:column title="Name" display="pdf">
            <c:out value="${hospital.name} ${hospital.name}"/>
        </datatables:column>
        <datatables:column title="Address" property="address" cssStyle="width: 200px;"/>
        <datatables:column title="City" property="city"/>
        <datatables:column title="Telephone" property="telephone"/>
        <datatables:column title="Doctors" cssStyle="width: 100px;">
            <c:forEach var="doctor" items="${hospital.doctors}">
                <c:out value="${doctor.name}"/>
            </c:forEach>
        </datatables:column>
        <datatables:export type="pdf" cssClass="btn btn-small" />
    </datatables:table>
    
    <jsp:include page="../fragments/footer.jsp"/>

</div>
</body>

</html>
