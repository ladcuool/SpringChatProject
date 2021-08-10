<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%
	if(session.getAttribute("id")!=null) {
		session.setAttribute("messageContent","이미 로그인이 되어있습니다.");
		response.sendRedirect("/");
		return;
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CHAT STORY</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="webjars/bootstrap/5.0.0-beta1/css/bootstrap.min.css">
<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.0.0-beta1/js/bootstrap.min.js"></script>

</head>
<body>
	
	<!-- nav bar -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">Chat Story</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarText">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link" href="/">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="companyinfomation">Company</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="list2">공지사항</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="list">자유게시판</a>
					</li>
				</ul>
				<a href="userjoin" class="btn btn-primary" style="margin-right:10px;">회원가입</a>
				<a href="loginForm" class="btn btn-primary">로그인</a>
			</div>
		</div>
	</nav>
	
	<!-- 로그인 양식 -->
	<div class="container d-flex flex-column" style="border:1px solid #4B89Dc; margin-top:70px; padding-bottom:50px; margin-bottom:30px; max-width:500px;">
		<form class="row g-3" method="post" action="<%=application.getContextPath()%>/member_join" >
			<div class="col-12 d-flex justify-content-center">
				<h2 class="text-primary" style="margin-top:40px; margin-bottom:25px;">ChatStory</h2>
			</div>
			<div class="col-md-12" style="padding-left:80px; padding-right:80px;">
				<label for="Password" class="form-label">사원번호</label>
				<input type="password" class="form-control" id="memberPassword" name="memberPassword" placeholder="사원번호를 입력해주세요.">
			</div>
			<div class="col-12 d-flex justify-content-center" style="padding-top:30px;">
				<button type="submit" class="btn btn-primary">확인</button>
			</div>
		</form>
	</div>


	<!-- 모달창 -->
	<%
		String messageContent=null;
		if(session.getAttribute("messageContent")!=null) {
			messageContent=(String)session.getAttribute("messageContent");
		}
		if(messageContent!=null) {
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">확인 메시지</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
				<%=messageContent%>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			$('#messageModal').modal("show");
		});
	</script>
	<%
		session.removeAttribute("messageContent");
		}
	%>
	

</body>
</html>