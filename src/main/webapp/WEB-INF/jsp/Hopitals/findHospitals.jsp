<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>Find Hospital</h2>

    <spring:url value="/hospitals.html" var="formUrl"/>
    <form:form modelAttribute="hospital" action="${fn:escapeXml(formUrl)}" method="get" class="form-horizontal"
               id="search-hospital-form">
        <fieldset>
            <div class="control-group" id="name">
                <label class="control-label">Enter Zip Code </label>
                <form:input path="name" size="30" maxlength="80"/>
                <span class="help-inline"><form:errors path="*"/></span>
            </div>
            <div class="form-actions">
                <button type="submit">Find</button>
            </div>
        </fieldset>
    </form:form>

    <br/>
    <a href='<spring:url value="/hospitals/new" htmlEscape="true"/>'>Add Hospital</a>

    <jsp:include page="../fragments/footer.jsp"/>

</div>
</body>

</html>
