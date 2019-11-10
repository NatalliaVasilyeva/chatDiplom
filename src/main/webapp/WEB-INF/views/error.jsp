<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="header.jsp"/>
<div id="notfound">
	<div class="notfound">
		<div class="notfound-404">
			<spring:message code="error.ops" var="ops"/>
			<h1>${ops}!</h1>
		</div>
		<h2>${from}</h2>
		<spring:message code="error.homepage" var="homePage"/>
		<a href="${contextPath}/home">${homePage}</a>


	</div>
</div>
<div class="row">
	<div class="col-lg-1"></div>
	<div class="col-lg-10">
		<p class="text-danger">${exception }</p>
	</div>

</div>
<jsp:include page="footer.jsp"/>