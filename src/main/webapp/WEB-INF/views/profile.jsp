<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="header.jsp"/>
<link href="${css}/jquery-ui.css" rel="stylesheet">

<div class="container ">
	<div class="row my-2 py-5"
		style="box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 2px 10px 0 rgba(0, 0, 0, 0.19);">
		<div class="col-lg-4  text-center">
			<img src="${profileImage}/${user.profileImagePath}"
				class="mx-auto rounded-circle d-block"
				style="width: 200px; height: 200px;" id="profileImage" alt="avatar">

			<div class="form-group row m-2 text-center" >
				<div class="col-lg-2 "></div>
				<div class="col-lg-8 ">
					<div class="file btn  rounded-0 btn-light border"
						style="position: relative; overflow: hidden;">
						<spring:message code="profile.choose_profile" var="chooseProfile"/>
						<span class="">${chooseProfile}<i class="fas fa-folder-open"></i></span> <input
							type="file" id="profileImageFileInput" name="file"
							style="position: absolute; font-size: 50px; opacity: 0; right: 0; top: 0;" />
					</div>
				</div>
			</div>
			<div class="row m-2">
				<div class="col-lg-3"></div>
				<div class="col-lg-6">
					<spring:message code="profile.update" var="update"/>
					<button type="submit" class="btn btn-primary"
						id="updateProfileImage" style="display: none">${update}</button>
				</div>		
			</div>
		</div>
		<div class="col-lg-8 order-lg-2">
			<ul class="nav nav-tabs">
				<spring:message code="profile.profile" var="profile"/>
				<li class="nav-item"><a  data-target="#profile"
					data-toggle="tab" class="nav-link active pointer">${profile}</a></li>
				<spring:message code="profile.edit" var="edit"/>
				<li class="nav-item"><a  data-target="#edit"
					data-toggle="tab" class="nav-link pointer">${edit}</a></li>
			</ul>
			<div class="tab-content py-4">
				<div class="tab-pane active" id="profile">
					<h5>${user.name}</h5>
					<p style="color: #999;">
						<small>${user.address != null ? user.address : '----'}&nbsp;&nbsp;<i
							class="fas fa-map-marker-alt"></i></small>
					</p>
					<div class="row">
						<div class="col-md-6">
							<h6>
								<i class="fas fa-envelope"></i>&nbsp;&nbsp;&nbsp;${user.email != null ? user.email : '----'}
							</h6>
							<h6>
								<i class="fas fa-birthday-cake"></i>&nbsp;&nbsp;&nbsp;
								<fmt:formatDate value="${user.birthdate }" pattern="dd-MMM-yyyy" />
							</h6>
							<h6>
								<i class="fas fa-user"></i>&nbsp;&nbsp;&nbsp;${user.gender != null ? user.gender : '----'}
							</h6>
							<h6>
								<i class="far fa-comment-alt"></i>&nbsp;&nbsp;&nbsp;${user.status != null ? user.status : '----'}
							</h6>

						</div>

					</div>

				</div>

				<div class="tab-pane" id="edit">

					<form:form action="${contextPath}/handleEditProfile"
						modelAttribute="user" enctype="multipart/form-data"
						class="form-horizontal" method="post">

						<!-- Name  -->

						<div class="form-group row">
							<spring:message code="profile.name" var="name"/>
							<label class="col-lg-3 col-form-lable">${name}:</label>
							<div class="col-lg-9">
								<form:input path="name" required="true" class="form-control" />
								<form:errors path="name" cssClass="text-danger"/>
							</div>
						</div>

						<!-- Birthdate -->

						<div class="form-group row">
							<spring:message code="profile.birthday" var="birthday"/>
							<label class="col-lg-3 col-form-lable">${birthday}:</label>
							<div class="col-lg-9">

								<form:input path="birthdate" id="idBirthdateField"
									required="true" readonly='true' class="form-control " />
								<form:errors path="birthdate" cssClass="text-danger"/>
							</div>
						</div>

						<!-- Gender  -->

						<div class="form-group row">
							<spring:message code="profile.gender" var="gender"/>
							<label class="col-lg-3 col-form-label">${gender}</label>
							<div class="col-lg-9">
								<div class="form-check form-check-inline">
									<form:radiobutton class="form-check-input" path="gender"
										value="Male" required="true"/>
									<spring:message code="profile.male" var="male"/>
									<label class="form-check-label">${male}</label>
								</div>
								<div class="form-check form-check-inline">
									<form:radiobutton class="form-check-input" path="gender"
										value="Female" required="true"/>
									<spring:message code="profile.female" var="female"/>
									<label class="form-check-label">${female}</label>
								</div>
								<form:errors path="gender" cssClass="text-danger"/>
							</div>
						</div>

						<!-- Address  -->

						<div class="form-group row">
							<spring:message code="profile.address" var="address"/>
							<label class="col-lg-3 col-form-label">${address}</label>
							<div class="col-lg-9">
								<form:textarea class="form-control" path="address" rows="3"
									required="true"/>
								<form:errors path="address" cssClass="text-danger"/>
							</div>
						</div>



						<!-- Profile Status  -->

						<div class="form-group row">
							<spring:message code="profile.profile_status" var="profileStatus"/>
							<label class="col-lg-3 col-form-lable">${profileStatus}:</label>
							<div class="col-lg-9">
								<form:input path="status" required="true" class="form-control" />
								<form:errors path="status" cssClass="text-danger"/>
							</div>
						</div>

						<!-- profile picture   

						<div class="form-group row">
							<label class="col-lg-3 col-form-label">Select Profile Picture:</label>
							<div class="col-lg-9">

								<div class="custom-file">
									<input type="file" name="file" id="profileImageInputFile" class="custom-file-input" value="">
									<label class="custom-file-label" for="customFile">${user.profileImagePath }</label>
								</div>
							</div>
						</div>
						
						-->

						<form:hidden path="id" />
						<form:hidden path="email" />
						<form:hidden path="password" />
						<form:hidden path="enabled" />
						<form:hidden path="isBlock" />
						<form:hidden path="createdAt" />


						<div class="form-group row">
							<label class="col-lg-3 col-form-label"></label>
							<div class="col-lg-9">
								<spring:message code="profile.save_changes" var="saveChanges"/>
								<button type="submit" class="btn btn-primary">${saveChanges}</button>
							</div>
						</div>


					</form:form>
				</div>
			</div>
		</div>

	</div>
</div>
<jsp:include page="footer.jsp"/>
<script  src="${js}/compressor.js" type="text/javascript"></script>
<script  src="${js}/jquery-ui.min.js" type="text/javascript"></script>
<script>
//date picker for registration form birthdate field
	$('#idBirthdateField').datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
		yearRange: "-100:+0",
		maxDate: new Date()
	});
</script>