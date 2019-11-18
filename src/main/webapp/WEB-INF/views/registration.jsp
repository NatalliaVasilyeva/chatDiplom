<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<jsp:include page="header.jsp"/>

<div class="row m-4">
    <div class="col-lg-2"></div>
    <div class="col-lg-8">
        <div class="card">
            <spring:message code="registration.registration" var="registration"/>
            <div class="card-header ">${registration}</div>
            <div class="card-body">
                <form:form action="${contextPath}/registration"
                           modelAttribute="user"
                           class="form-horizontal" method="post">

                    <!-- Name -->

                    <div class="form-group row">
                        <spring:message code="registration.name" var="name"/>
                        <label class="col-lg-3 col-form-lable">${name}</label>
                        <div class="col-lg-9">
                            <form:input path="name" required="true" class="form-control"/>
                            <form:errors path="name" cssClass="text-danger"/>
                        </div>
                    </div>

                    <!-- Email -->

                    <div class="form-group row">
                        <spring:message code="registration.email" var="email"/>
                        <label class="col-lg-3 col-form-lable">${email}</label>
                        <div class="col-lg-9">
                            <form:input path="email" class="form-control" required="true"/>
                            <form:errors path="email" cssClass="text-danger"/>
                        </div>
                    </div>

                    <!-- Password -->

                    <div class="form-group row">
                        <spring:message code="registration.password" var="password"/>
                        <label class="col-lg-3 col-form-lable">${password}</label>
                        <div class="col-lg-9">
                            <form:password path="password" pattern="(?=.*\d)(?=.*[a-zA-Z]).{8,}"
                                           title="Must contain at least one number and one letter, and at least 8 or more characters"
                                           class="form-control"
                                           required="true"/>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>
                    </div>

                    <!-- Confirm Password -->

                    <div class="form-group row">
                        <spring:message code="registration.confirm_password" var="confirmPassword"/>
                        <label class="col-lg-3 col-form-lable">${confirmPassword}</label>
                        <div class="col-lg-9">
                            <input type="password" name="confirm_password" id="confirm_password" class="form-control"
                                   required/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label"></label>
                        <div class="col-lg-9">
                            <spring:message code="registration.submit" var="submit"/>
                            <button type="submit" class="btn btn-primary">${submit}</button>
                        </div>
                    </div>

                </form:form>
            </div>
        </div>
    </div>
    <div class="col-lg-2"></div>
</div>
<jsp:include page="footer.jsp"/>