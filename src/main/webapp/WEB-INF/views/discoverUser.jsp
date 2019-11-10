<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="header.jsp"/>
<div class="container my-5">
	<c:if test="${fn:length(users) lt 1}">
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<div
					class="alert alert-danger alert-dismissible fade show box-shadow"
					role="alert">
					<spring:message code="discover.sorry" var="sorry"/>
					${sorry}
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
									class="img rounded img-fluid" />
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
											<td><i class="fas fa-envelope"></i>&nbsp;&nbsp;&nbsp; <small>${user.email }</small></td>
										</tr>
										<tr>
											<td></td>
										</tr>
										<tr>
											<td>
												<spring:message code="discover.see_profile" var="seeProfile"/>
												<button
													class="seeProfileButton btn btn-primary btn-sm"
													data-id="${user.id}">${seeProfile}</button>
												<button class="btn btn-primary m-3 btn-sm"
													data-id="${user.id}" data-email="${user.email}">
													<spring:message code="discover.send" var="send"/>
													<i class="py-1 friendRequestSentLabel"
														style="display: none">&nbsp;${send}</i><i
														class="fas fa-user-plus  py-1 friendRequestSentButton"></i>
												</button></td>
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