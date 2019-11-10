<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="header.jsp"/>
<div class="row m-3" >
	<div class="col-lg-2"></div>
	<div class="col-lg-8">
		<div class="card">
		<div class="card-header">
			<spring:message code="login.login" var="login"/>
			${login}
		</div>
			<div class="card-body">
				<form action="${contextPath}/login" method="post">
					<!-- Email  -->

					<div class="form-group row">
						<spring:message code="login.email" var="email"/>
						<label class="col-lg-3 col-form-lable">${email}:</label>
						<div class="col-lg-9">
							<input type="text" name="email" class="form-control" required/>
							
						</div>
					</div>

					<!-- Password  -->

					<div class="form-group row">
						<spring:message code="login.password" var="password"/>
						<label class="col-lg-3 col-form-lable">${password}:</label>
						<div class="col-lg-9">
							<input type="password" name="password" class="form-control"
								required />
							
						</div>
					</div>
					<div class="form-group row">
						<label class="col-lg-3 col-form-lable"></label>
						<div class="col-lg-5">
							<spring:message code="login.login" var="login"/>
							<button type="submit" class="btn btn-primary">${login}</button>
							
							
						</div>
						<spring:message code="login.forgot_password" var="forgotPassword"/>
						<label class="col-lg-4 text-right"><a href="${contextPath}/showAddEmail">${forgotPassword}</a></label>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	<div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"/>