 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="header.jsp"/>
<div class="container my-4">
	<div class="chat-container">
		<div class="row h-100 ">
			<div class="col-4  px-0 border  h-100" id="sidebar">
				<div id="sidebar-content" class="w-100 h-100">
					<div class="d-flex flex-row ">
						<div class="col px-0 ">
							<div class="input-group p-0 d-xs-none border" id="search-group">
								<input type="text" class="form-control border-0"
								<spring:message code="chat.placeholder_search" var="search"/>
									placeholder=${search} id="search"
									style="border-radius: 0 !important; outline: 0px !important; -webkit-appearance: none; box-shadow: none !important;">
								<div class="input-group-prepend">
									<div class="btn-group dropright">
										<button type="button"
											class="btn border-0 bg-white  hover-color-darkblue dropdown dropleft"
											style="border-radius: 0 !important; outline: 0px !important; -webkit-appearance: none; box-shadow: none !important;"
											data-toggle="dropdown">
											<i class="fas fa-ellipsis-v mr-3"></i>
										</button>
										<div class="dropdown-menu">
											<spring:message code="chat.create_group" var="createGroup"/>
											<a class="dropdown-item" href="#!"
												id="createGroupOpenModelButton">${createGroup}</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="w-100 sidebar-scroll" id="list-group">
						<ul class="list-group h-100 w-100" id="friend-list">
							<c:if test="${ fn:length(users) lt 1}">
								<li class="p-3">
									<div
										class="alert alert-danger alert-dismissible fade show box-shadow "
										role="alert">
										<spring:message code="chat.find_people" var="findPeople"/>
										<a href="${pageContext.request.contextPath}/discoverUser">${findPeople}</a>
										<button type="button" class="close" data-dismiss="alert"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
								</li>
							</c:if>
							<c:set var="count" value="1"/>
							<c:forEach items="${users}" var="user">
								<li class="list-group-item  hover-bg-lightgray py-2 w-100"
									style="cursor: default">
									<div class="row">
										<div class="col-lg-3 text-center chat_img px-0">
											<img src="${profileImage}/${user.profileImagePath}"
												style="width: 50px; height: 50px;"
												class="rounded-circle chat-sidebar-profile-image seeProfileButton pointer"
												data-id="${user.id}">
										</div>
										<div class="col-lg-8 chat_ib p-o my-0 my-auto pointer"
											data-id="${user.id}" data-email="${user.email }"
											data-isleader="${user.isOnline}">
											<div class="row">
												<div class="col-lg-8">
													<h6 class="name">
														${user.name}
														<c:if test="${fn:substring(user.id,0,5)=='GROUP'}">
															<i class='fa fa-users pl-2' aria-hidden='true'></i>
														</c:if>
													</h6>
												</div>
												<div class="col-lg-2">
													<c:if test="${user.countMessage!=0}">
														<i
															class="badge badge-pill badge-success messageCount mx-2">${user.countMessage}</i>
													</c:if>
													<c:if test="${user.countMessage==0}">
														<i
															class="badge badge-pill badge-success messageCount mx-2"
															style="display: none">0</i>
													</c:if>
												</div>
												<c:if test="${fn:substring(user.id,0,5)!='GROUP'}">
													<div class="col-lg-2 text-left mt-1">
														<c:if test="${user.isOnline}">
															<i class="fas fa-circle text-danger fa-xs redDot"
																style="display: none"></i>
															<i
																class="spinner-grow text-success spinner-grow-sm greenDot"></i>
														</c:if>
														<c:if test="${user.isOnline eq false}">
															<i class="fas fa-circle text-danger  fa-xs redDot"></i>
															<i
																class="spinner-grow text-success spinner-grow-sm  greenDot"
																style="display: none"></i>
														</c:if>
													</div>
												</c:if>
											</div>

											<div class="row">
												<div class="col-lg-12">
													<c:if test="${fn:substring(user.id,0,5)=='GROUP'}">
														<span class="chat_date text-success text-left lastSeen"
															id="${user.id}OnlineStatusLabel"><small></small></span>
													</c:if>
													<c:if test="${fn:substring(user.id,0,5)!='GROUP'}">
														<c:if test="${user.isOnline}">
															<spring:message code="chat.online" var="online"/>
															<span class="chat_date text-success text-left lastSeen"
																id="${user.id}OnlineStatusLabel"><small>${online}</small></span>
														</c:if>
														<c:if test="${ user.isOnline eq false}">
															<span class="chat_date text-danger text-left"
																id="${user.id}OnlineStatusLabel"><small>${user.activeTimeAgo }</small></span>
														</c:if>
													</c:if>
												</div>
											</div>

										</div>
									</div>
								</li>
								<c:set var="count" value="${count+1}"/>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col d-flex p-0  h-100 chat-border">
				<div class="card mesgs rounded-0 w-100" style="display: none">
					<div class="card-header bg-light  py-1 px-1 w-100"
						style="flex: 1 1">
						<div class="d-flex flex-row justify-content-start my-auto">
							<div class="col-1 py-2 sizeBar my-auto ">
								<i class="fas fa-bars fa-2x pointer"></i>
							</div>
							<div class="col p-auto ml-2  my-auto">
								<div class="my-0">
									<b id="chatUserName"></b>
								</div>
								<div class="my-0">
									<small id="chatUserLastSeen"></small>
								</div>
							</div>
							<div class="col-1 py-3" style="font-size: 20px;">

								<div class="btn-group dropleft">
									<button type="button"
										class="btn border-0  hover-color-darkblue dropdown "
										style="border-radius: 0 !important; outline: 0px !important; -webkit-appearance: none; box-shadow: none !important;"
										data-toggle="dropdown">
										<i class="fas fa-ellipsis-v mr-3"></i>
									</button>
									<div class="dropdown-menu" data-id=""
										id="editGroupAndUserDropDownButton">
										<spring:message code="chat.edit_members" var="editMembers"/>
										<a class="dropdown-item" href="#!"
											id="editMemberDropdownButton">${editMembers}</a>
										<spring:message code="chat.edit_group" var="editGroup"/>
										<a	class="dropdown-item" href="#!"
											id="editGroupDetailDropdownButton">${editGroup}</a>
										<spring:message code="chat.clear_chat" var="clearChat"/>
											<a
											class="dropdown-item" href="#!" id="clearChatDropdownButton">${clearChat}</a>
									</div>
								</div>
							</div>

						</div>
					</div>
					<div class="card-body d-flex flex-column p-0 sidebar-scroll w-100"
						style="flex: 9 1;background-image: url(${image}/bg-chat.jpg);">
						<div class="container-fluid message-scroll msg_history"
							style="flex: 1 1;"></div>
						<div class="input-group">
							<div class="file btn  rounded-0 btn-light border"
								style="position: relative; overflow: hidden;">
								<span class="my-auto"><i class="fas fa-folder-open"></i></span>
								<input type="file" id="exampleFormControlFile1" name="file"
									style="position: absolute; font-size: 50px; opacity: 0; right: 0; top: 0;" />
							</div>
							<spring:message code="chat.placeholder_send_message" var="sendMessage"/>
							<input type="text" id="messageSendTextBox"
								style="border-radius: 0 !important; outline: 0px !important; -webkit-appearance: none; box-shadow: none !important;"
								class="form-control" placeholder=${sendMessage}>
							<div class="input-group-append pointer" id="messageSendButton">
								<span class="input-group-text"
									style="border-radius: 0 !important;"><i
									class="fab fa-telegram-plane"></i></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- row -->
	</div>
</div>

<input type="hidden" id="hiddenTextBoxFromId" name=""
	value="${userSession.id}">


<!-- open personal profile models -->
<div class="modal  fade" id="createGroupModel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<spring:message code="chat.create_group" var="createGroup"/>
				<h4 class="modal-title">${createGroup}</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form id="createGroupForm">
							<!-- Name  -->

							<div class="form-group row">
								<spring:message code="chat.group_name" var="groupName"/>
								<label class="col-lg-3 col-form-lable">${groupName}</label>
								<div class="col-lg-9">
									<input type="text" name="name" id="groupName" required
										class="form-control" />

								</div>
							</div>

							<!-- Email  -->

							<div class="form-group row">
								<spring:message code="chat.status" var="status"/>
								<label class="col-lg-3 col-form-lable">${status}</label>
								<div class="col-lg-9">
									<input type="text" name="status" id="groupStatus"
										class="form-control" required />
								</div>
							</div>

							<!-- Group Members  -->

							<div class="form-group row">
								<spring:message code="chat.members" var="members"/>
								<label class="col-lg-3 col-form-lable">${members}</label>
								<div class="col-lg-9" id="createGroupModelCheckBoxContent">

								</div>
							</div>
							<!-- Submit button -->
							<div class="form-group row">
								<label class="col-lg-3 col-form-label"></label>
								<div class="col-lg-9">
									<spring:message code="chat.create" var="create"/>
									<button type="submit" class="btn btn-primary">${create}</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="hiddenTextGroupIdForModel" name="" value="">
<!-- Edit Member of group model -->
<div class="modal  fade" id="editGroupMemberModel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<spring:message code="chat.update_group_members" var="updateGroupMembers"/>
				<h4 class="modal-title">${updateGroupMembers}</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form id="editGroupMemberForm">


							<!-- Group Members  -->
							<div class="form-group row">
								<label class="col-lg-3 col-form-lable">${members}</label>
								<div class="col-lg-9" id="unGroupMemberEditMemberContentModel">

								</div>
							</div>
							<!-- Submit button -->
							<div class="form-group row">
								<label class="col-lg-3 col-form-label"></label>
								<div class="col-lg-9">
									<spring:message code="chat.add" var="add"/>
									<button type="submit" class="btn btn-primary">${add}</button>
								</div>
							</div>
						</form>
						<div>
							<div class="table-responsive">
								<table class="table table-bordered">
									<thead>
										<tr>
											<spring:message code="chat.number" var="number"/>
											<th>${number}</th>
											<spring:message code="chat.name" var="name"/>
											<th>${name}</th>
											<spring:message code="chat.email" var="email"/>
											<th>${email}</th>
											<th>#</th>
										</tr>
									</thead>
									<tbody id="groupMemberEditMemberContentModel">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Edit Member of group model -->
<div class="modal  fade" id="editGroupProfileModel">
	<div class="modal-dialog ">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<spring:message code="chat.edit_group" var="editGroup"/>
				<h4 class="modal-title">${editGroup}</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<form id="editGroupDetailForm">
							<div class="row ">
								<div class="col-lg-3"></div>
								<div class="col-lg-9 text-center">
									<img src="${profileImage}/"
										class="mx-auto rounded-circle d-block"
										style="width: 100px; height: 100px;"
										id="profileImageEditGroupDetailModel" alt="avatar">
								</div>
							</div>
							<div class="form-group row my-1">
								<div class="col-lg-3"></div>
								<div class="col-lg-9 text-center">
									<div class="file btn btn-sm  rounded-0 btn-light border"
										style="position: relative; overflow: hidden;">
										<spring:message code="chat.choose_profile" var="chooseProfile"/>
										<span class="">${chooseProfile}<i
											class="fas fa-folder-open"></i></span> <input type="file"
											id="profileImageFileInputEditGroupDetailModel" name="file"
											value=null
											style="position: absolute; font-size: 50px; opacity: 0; right: 0; top: 0;" />
									</div>
								</div>
							</div>

							<div class="form-group row">
								<label class="col-lg-3 col-form-lable">${name}:</label>
								<div class="col-lg-9">
									<input type="text" id="groupNameEditGroupDetailModel" required
										class="form-control" />
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-lable">${status}:</label>
								<div class="col-lg-9">
									<input type="text" id="groupStatusEditGroupDetailModel"
										required class="form-control" />
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label"></label>
								<div class="col-lg-9">
									<spring:message code="chat.edit" var="edit"/>
									<button type="submit" class="btn btn-primary">${edit}</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Show group Profile -->
<div class="modal  fade" id="showGroupProfileModel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<spring:message code="chat.group_profile" var="groupProfile"/>
				<h4 class="modal-title">${groupProfile}</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<div>
							<div class="table-responsive">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>${number}</th>
											<th>${name}</th>
											<th>${email}</th>
										</tr>
									</thead>
									<tbody id="groupMemberShowProfileModel">
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script  src="${js}/compressor.js" type="text/javascript"></script>
<jsp:include page="footer.jsp"/>