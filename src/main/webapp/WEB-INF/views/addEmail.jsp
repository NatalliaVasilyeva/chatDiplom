<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="header.jsp"/>
<div class="row my-4">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <spring:message code="addEmail.forgot_password" var="forgotPassword"/>
                ${forgotPassword}
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/sendforgotPasswordEmailToken" method="post">
                    <!-- Email  -->

                    <div class="form-group row">
                        <spring:message code="addEmail.email" var="email"/>
                        <label class="col-lg-3 col-form-lable">${email}:</label>
                        <div class="col-lg-9">
                            <input type="email" name="email" class="form-control" required/>
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-lg-3 col-form-lable"></label>
                        <div class="col-lg-5">
                            <spring:message code="addEmail.send_button" var="sendButton"/>
                            <button type="submit" class="btn btn btn-outline-primary">${sendButton}</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"/>