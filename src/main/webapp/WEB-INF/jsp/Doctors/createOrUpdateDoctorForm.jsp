<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>
<body>

<script>
    $(function () {
        $("#birthDate").datepicker({ dateFormat: 'yy/mm/dd'});
    });
</script>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <c:choose>
        <c:when test="${doctor['new']}">
            <c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
            <c:set var="method" value="put"/>
        </c:otherwise>
    </c:choose>

    <h2>
        <c:if test="${doctor['new']}">New </c:if>
        Pet
    </h2>

    <form:form modelAttribute="doctor" method="${method}"
               class="form-horizontal">
        <div class="control-group" id="hospital">
            <label class="control-label">Hospital </label>

            <c:out value="${doctor.hospital.name}"/>
        </div>
        <petclinic:inputField label="Name" name="name"/>
        <petclinic:inputField label="Birth Date" name="birthDate"/>
        <div class="control-group">
            <petclinic:selectField name="type" label="Type " names="${types}" size="5"/>
        </div>
        <div class="form-actions">
            <c:choose>
                <c:when test="${doctor['new']}">
                    <button type="submit">Add Doctor</button>
                </c:when>
                <c:otherwise>
                    <button type="submit">Update Doctor</button>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
    <c:if test="${!doctor['new']}">
    </c:if>
    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
