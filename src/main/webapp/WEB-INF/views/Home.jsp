<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<c:url value="/resources" var="conpath"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CHAT STORY</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="webjars/bootstrap/5.0.0-beta1/css/bootstrap.min.css">
<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.0.0-beta1/js/bootstrap.min.js"></script>
<script src="${conpath}/js/chatbox.js"></script>
<link href="${conpath}/css/chating.css" rel="stylesheet">
<%
	String id=null;
%>
<style>
	#jumbo {
		color:white;
	}
</style>
<%
	if(session.getAttribute("id")!=null) {
%>
<script>
	sessionStorage.setItem("id", '<%=(String)session.getAttribute("id")%>');

	unreadfunction('<%=(String)session.getAttribute("id")%>');
</script>
<%
	}
%>
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
						<a class="nav-link active" aria-current="page" href="/">Home</a>
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
				<!-- 로그인 상태 -->
				<%
					if(session.getAttribute("id")==null) {
				%>
						<a href="userjoin" class="btn btn-primary" style="margin-right:10px;">회원가입</a>
						<a href="loginForm" class="btn btn-primary">로그인</a>
				<%
					} else {
						id=(String)session.getAttribute("id");
				%>
						<a onclick="listfunction();" style="margin-right:3%; cursor:pointer">
							<img class="col-sm-3 rounded-circle" id="ChatCircle" name="ChatCircle"
								 style="height:45px; width:45px;" src="PageImage/message.jpg">
							<span class="badge bg-primary" style="position:absolute;" id="getunread"></span>
						</a>
						<a href="userInfo" style="margin-right:10px; text-decoration:none;">[<%=id%>]</a><a style="margin-right:15px;">님 안녕하세요!!</a>
						<a href="/logout" class="btn btn-primary">로그아웃</a>
						<%if(id.equals("ladool")) { %>
							<a href="/managerPage" class="btn btn-primary" style="margin-left:5px;">관리자</a>
						<%} %>
				<%
					}
				%>
			</div>
		</div>
	</nav>
	
	<!-- Home 상단 image -->
	<img src="PageImage/HomeImage.jpg" class="d-block w-100">
	
	<br/><br/>
	<!-- 회사 소개 -->
	<div class="container">
		<div class="row justify-content-center">
			<h2 style="margin-bottom:20px;">우리가 추구하는 가치입니다.<hr/></h2>
			<div class="card" style="width:18rem; margin:20px;">
				<img src="PageImage/paper-3213924_1920.jpg" class="card-img-top" style="height:45%">
				<div class="card-body">
					<h5 class="card-title" style="margin-bottom:20px;">화합</h5>
					<p class="card-text">언제나 우리가 앞서 나갈 수 있는 이유, chatstory 구성원들간의 화합이 회사의 미래를 만듭니다.</p>
				</div>
			</div>
			<div class="card" style="width:18rem; margin:20px;">
				<img src="PageImage/hotel-638023_1920.jpg" class="card-img-top" style="height:45%">
				<div class="card-body">
					<h5 class="card-title" style="margin-bottom:20px;">휴식</h5>
					<p class="card-text">언제나 당신과 함께하는 chatstory, 피곤한 일상에 지친 당신, 당신에게 편안한 휴식을 제공합니다.</p>
				</div>
			</div>
			<div class="card" style="width:18rem; margin:20px;">
				<img src="PageImage/notepad-1130743_1920.jpg" class="card-img-top" style="height:45%">
				<div class="card-body">
					<h5 class="card-title" style="margin-bottom:20px;">열정</h5>
					<p class="card-text">항상 열정적인 chatstory, chatstory 구성원들은 열정을 쏟아 여러분에게 최선을 제공합니다.</p>
				</div>
			</div>
		</div>
	</div>
	<br/><br/><br/>

	<!-- Home body -->
	<div class="container">
		<h2 style="margin-bottom:50px; text-align:end;">최신 소식을 접해보세요.<hr/></h2>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/news-2389226_1280.png" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>CHATSTORY 오늘의 소식을 접해보세요! 최신 소식이 여러분을 기다리고 있습니다.</h4></div>
		</div>
	</div>
	<br/><br/>
	<div class="container">
		<div class="row">
			<h2 style="margin-bottom:50px;">게시글을 작성해보세요.<hr/></h2>
			<img id="ChatCircle" name="ChatCircle" class="col-sm-5"
			src="PageImage/keyboard-2104009_1280.png">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>우리 회사는 여러분의 의견을 기다립니다. 자유게시판에 글을 작성해보세요!</h4></div>
		</div>
	</div>

	
	<br/><br/>
	
	<!-- footer -->
	<div class="container" style="margin-bottom:50px;">
		<div class="row justify-content-end">
			<h2 style="margin-bottom:50px; text-align:end;">여러분의 연락을 기다립니다.<hr/></h2>
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>Call : 010-9223-9737</h4></div>
			<img id="ChatCircle" name="ChatCircle" class="col-sm-5"
			src="PageImage/callicon.jpg">
		</div>
		<hr/>
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