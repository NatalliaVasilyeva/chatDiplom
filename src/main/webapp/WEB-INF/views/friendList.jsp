<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="header.jsp"/>

<div class="container my-4">
    <c:if test="${fn:length(users) lt 1}">
        <div class="row">
            <div class="col-lg-2"></div>
            <div class="col-lg-8">
                <div
                        class="alert alert-danger alert-dismissible fade show box-shadow"
                        role="alert">
                    <spring:message code="friendList.have_no_friend" var="haveNoFriends"/>
                        ${haveNoFriends}
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </div>
    </c:if>

    <div class="row">
        <c:set var="count" value="1"/>
        <c:forEach items="${users}" var="user">
            <div class="col-lg-6">
                <div class="card my-1">
                    <div class="card-body ">
                        <div class="row">
                            <div class="col-lg-4 text-center">
                                <img src="${profileImage}/${user.profileImagePath}"
                                     class="img rounded img-fluid"/>
                            </div>
                            <div class="col-lg-8">
                                <table class="table table-borderless  ">
                                    <thead class="">
                                    <tr class="Primary">
                                        <th>${user.name }</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><i class="fas fa-envelope"></i>&nbsp;&nbsp;&nbsp;
                                            <small>${user.email }</small></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <spring:message code="friendList.see_profile" var="seeProfile"/>
                                            <button
                                                    class="seeProfileButton btn btn-primary btn-sm"
                                                    data-id="${user.id}">${seeProfile}</button>
                                            <button class="btn btn-primary  btn-sm unfiendRequestLink"
                                                    data-id="${user.id}" data-email="${user.email}">
                                                <spring:message code="friendList.success" var="success"/>
                                                <i class="py-1 unfriendRequestLable"
                                                   style="display: none">&nbsp;${success}</i>
                                                <spring:message code="friendList.unfriend" var="unfriend"/>
                                                <span
                                                        class=" py-1 unfiendRequestButton">${unfriend}</span>
                                            </button>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <c:set var="count" value="${count+1}"/>

        </c:forEach>
    </div>
</div>
<jsp:include page="footer.jsp"/>