<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<jsp:include page="header.jsp"/>
<link href="${css}/dataTables.bootstrap4.min.css" rel="stylesheet">
<div class="container my-4">
    <div class="row">
        <c:set var="count" value="1"/>
        <div class="table-responsive">

            <table class="table table-bordered table-hover nowrap" id="logTable"
                   style="width: 100%">
                <thead>
                <tr>
                    <spring:message code="log.number" var="number"/>
                    <th>${number}</th>
                    <spring:message code="log.name" var="name"/>
                    <th>${name}</th>
                    <spring:message code="log.email" var="email"/>
                    <th>${email}</th>
                    <spring:message code="log.login_time" var="loginTime"/>
                    <th>${loginTime}</th>
                    <spring:message code="log.logout_time" var="logoutTime"/>
                    <th>${logoutTime}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${logs}" var="log">
                    <tr>
                        <td>${count }</td>
                        <td>${log.name}</td>
                        <td>${log.email}</td>
                        <td><fmt:formatDate value="${log.loginTime}" pattern="hh:mm a dd-MM-yyyy "/></td>
                        <td><fmt:formatDate value="${log.logoutTime}" pattern="hh:mm a dd-MM-yyyy "/></td>
                    </tr>
                    <c:set var="count" value="${count+1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
<script src="${js}/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${js}/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $('#logTable').DataTable();
    });
</script>