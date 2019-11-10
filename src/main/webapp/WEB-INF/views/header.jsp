<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>

<spring:url value="resources/css" var="css" scope="session"/>
<spring:url value="resources/images" var="image" scope="session"/>
<spring:url value="resources/profileImage" var="profileImage"
            scope="session"/>
<spring:url value="resources/shareImage" var="shareImage"
            scope="session"/>
<spring:url value="resources/js" var="js" scope="session"/>
<c:set var="contextPath" value="${pageContext.request.contextPath }"
       scope="session"/>

<html>
<head>
    <meta charset="utf-8">
    <meta content="text/html; charset=UTF-8; X-Content-Type-Options=nosniff"
          http-equiv="Content-Type"/>

    <title>CHAT</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">


    <!--  CHAT title icon -->
    <link rel="icon" href="${image}/chat.png" type="image/x-icon">


    <!-- Bootstrap CSS File -->
    <link href="${css}/bootstrap.min.css" rel="stylesheet">

    <!-- Libraries CSS Files -->

    <link href="${css}/jquery.toast.min.css" rel="stylesheet">
    <link href="${css}/fontawesome-all.min.css" rel="stylesheet">

    <link href="${css}/devfolio.css" rel="stylesheet">

    <!-- Main Stylesheet File -->
    <link href="${css}/mycss.css" rel="stylesheet">


</head>
<body id="body">
<div id="header">
    <nav
            class="navbar navbar-expand-lg navbar-light bg-light navigation-bar "
            style="background-color: white !important; box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19);">
        <a class="navbar-brand"><img src="${image}/chat.png"
                                     width="40px;"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav ">
                <li class="nav-item "><a class="nav-link"
                                         href="${contextPath}/home"><i class="fa fa-home fa-lg py-1"></i>
                </a></li>

            </ul>


            <c:if test="${userSession!=null}">
                <!-- Hidden field -->
                <input type="hidden" id="contextPath" name=""
                       value="${contextPath}">
                <input type="hidden" id="hiddenTextBoxUserId" name=""
                       value="${user.id}">

                <ul class="navbar-nav">


                    <li class="nav-item"><a class="nav-link"
                                            href="${contextPath}/chat"> <i
                            class="far fa-comment-dots  py-1"></i><sup><span
                            class="nav-link badge badge-warning px-2" style="display: none"
                            id="totalCountMessages">0</span></sup>
                    </a></li>

                    <li class="nav-item dropdown "><a
                            class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">
                        <i class="fas fa-user-friends  fa -lg py-1"></i>
                    </a>
                        <div class="dropdown-menu">
                            <spring:message code="header.search" var="search"/>
                            <a class="dropdown-item" href="${contextPath}/discoverUser">${search}<i
                                    class="fa fa-search px-2" aria-hidden="true"></i>
                            </a>
                            <spring:message code="header.pending_request" var="pendingRequest"/>
                            <a class="dropdown-item"
                               href="${contextPath}/friendPendingRequest">${pendingRequest}<i
                                    class="fas fa-clock px-2"></i>
                            </a>
                            <spring:message code="header.friend_request" var="friendRequest"/>
                            <a class="dropdown-item" href="${contextPath}/friendRequest">${friendRequest}<i
                                    class="fab fa-get-pocket px-2"></i>
                            </a>
                            <spring:message code="header.friends" var="friends"/>
                            <a class="dropdown-item" href="${contextPath}/friendList">${friends}<i
                                    class="fas fa-list px-2"></i>
                            </a>
                        </div>
                    </li>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authorize access="hasAuthority('ADMIN') ">
                            <li class="nav-item"><a class="nav-link"
                                                    href="${contextPath}/log"><i class="fas fa-history   py-1"></i></a>
                            </li>
                        </sec:authorize>
                    </sec:authorize>
                </ul>


                <ul class="navbar-nav ml-auto .dropdown-menu-right">
                    <li class="nav-item"><a class="nav-link"
                                            id="notifivationPopup" href="#!"><i class="fas fa-bell pt-1"
                                                                                id="notificationButton"></i><sup><span
                            class="nav-link badge badge-warning px-2" style="display: none"
                            id="totalCountNotification">0</span></sup> </a></li>
                    <li class="nav-item">
                    </li>
                    <li class="nav-item dropdown   form-inline  mr-5 "
                        data-display="dynamic"><a
                            class="nav-link dropdown-toggle navbar-brand" href="#"
                            id="navbarDropdown" role="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false"> <img
                            src="${profileImage}/${userSession.profileImagePath}"
                            class="rounded" width="30" height="30" id="navbarProfileImage">
                    </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <spring:message code="header.profile" var="profile"/>
                            <a class="dropdown-item " href="${contextPath}/showProfile"
                               title="click to show profile">${profile}</a>
                            <div class="dropdown-divider"></div>
                            <spring:message code="header.logout" var="logout"/>
                            <a class="dropdown-item" href="${contextPath}/logout">${logout}</a>
                        </div>
                    </li>

                    <li class="nav-item">
                            <%--Интернационализация--%>
                        <span style="float: left">
                                  <a href="?lang=en"><font color="blue">en</font></a>
                                  <a href="?lang=ru"><font color="blue">ru</font></a>
                                  </span>
                    </li>
                </ul>
            </c:if>
            <c:if test="${userSession==null}">
                <ul class="navbar-nav ml-auto mr-2">
                    <li class="nav-item">
                        <spring:message code="header.login" var="login"/>
                        <a class="nav-link" href="${contextPath}/login">${login}</a></li>
                    <spring:message code="header.registration" var="registration"/>
                    <li class="nav-item"><a class="nav-link"
                                            href="${contextPath}/registration"> <i class="fa fa-user"
                                                                                   aria-hidden="true"></i>&nbsp;${registration}
                    </a></li>

                    <li class="nav-item">
                            <%--Интернационализация--%>
                        <span style="float: left">
                                  <a href="?lang=en"><font color="blue">en</font></a>
                                  <a href="?lang=ru"><font color="blue">ru</font></a>
                                  </span>
                    </li>


                </ul>
            </c:if>
        </div>
    </nav>
</div>
<div id="main" style="background-color: #FFFFFF !important">
<div>
<c:if test="${message!=null}">
    <div class="row">
        <div class=" col-lg-8 mx-auto mt-3">
            <div
                    class="alert alert-success alert-dismissible fade show box-shadow"
                    role="alert">
                <spring:message code="header.message" var="message"/>
                <strong>${message} : </strong> ${message}
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </div>

</c:if>

<c:if test="${error!=null}">
    <div class="row ">
        <div class=" col-lg-8 mx-auto mt-3">
            <div
                    class="alert alert-danger alert-dismissible fade show box-shadow"
                    role="alert">
                <spring:message code="header.error" var="error"/>
                <strong>${error} :</strong> ${error}
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true"> &times; </span>
                </button>
            </div>
        </div>
    </div>

</c:if>