<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="header.jsp"/>
<link href="${css}/jquery.toast.min.css" rel="stylesheet">
<!--/ Intro Skew Star /-->
  <div id="home" class="intro route bg-image" style="background-image: url(${image}/intro-bg.jpg)">
    <div class="overlay-itro"></div>
    <div class="intro-content display-table">
      <div class="table-cell">
        <div class="container">
          <spring:message code="home.main" var="main"/>
          <h1 class="intro-title mb-4">${main}</h1>
          <spring:message code="home.name" var="name"/>
          <p class="intro-subtitle"><span class="text-slider-items">${name}</span><strong class="text-slider"></strong></p>
           <c:if test="${userSession!=null}">
             <spring:message code="home.discover_friend" var="discoverFriend"/>
           	<p class="pt-5"><a class="btn btn-primary btn js-scroll px-4" href="${contextPath}/discoverUser" role="button">${discoverFriend}</a></p>
           </c:if> 
        </div>
      </div>
    </div>
  </div>
  <div class="icon-bar">
  <a href="https://www.facebook.com/natalia.vasilyeva.777" target="_blank" class="facebook"><i class="fab fa-facebook-f"></i></a>
  <a href="https://www.linkedin.com/in/natallia-vasilyeva-2bb99b77/" target="_blank" class="linkedin"><i class="fab fa-linkedin"></i></a>
  
</div>
<script src="${js}/typed.min.js" type="text/javascript"></script>  
<jsp:include page="footer.jsp"/>