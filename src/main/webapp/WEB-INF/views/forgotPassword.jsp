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
                <spring:message code="forgot.forgot_password" var="forgotPassword"/>
                ${forgotPassword}
            </div>
            <div class="card-body">
                <form action="${contextPath}/forgotPassword" method="post">

                    <!-- Hidden Email -->

                    <input type="hidden" name="email" value="${email}"/>


                    <!-- Password  -->

                    <div class="form-group row">
                        <spring:message code="forgot.password" var="password"/>
                        <label class="col-lg-3 col-form-lable">${password}:</label>
                        <div class="col-lg-9">
                            <input type="password" name="password" id="password" class="form-control"
                                   required/>
                        </div>
                    </div>

                    <!-- Confirm Password  -->

                    <div class="form-group row">
                        <spring:message code="forgot.conform_password" var="confirmPassword"/>
                        <label class="col-lg-3 col-form-lable">${confirmPassword}:</label>
                        <div class="col-lg-9">
                            <input type="password" id="confirm_password" name="confirm_password" class="form-control"
                                   required/>

                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-lg-3 col-form-lable"></label>
                        <div class="col-lg-5">
                            <spring:message code="forgot.forgot" var="forgot"/>
                            <button type="submit" class="btn btn-primary">${forgot}</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"/>