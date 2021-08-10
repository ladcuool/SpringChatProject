<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<%
	String id=null;
	if(session.getAttribute("id")!=null) {
		id=(String)session.getAttribute("id");
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CHAT STORY</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="webjars/bootstrap/5.0.0-beta1/css/bootstrap.min.css">
<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.0.0-beta1/js/bootstrap.min.js"></script>
<c:url value="/resources" var="conpath"/>
<script src="${conpath}/js/chatbox.js"></script>
<link href="${conpath}/css/chating.css" rel="stylesheet">

<style>
	html, body {
		height:100%;
		width:100%;
	}
</style>

<%
	if(session.getAttribute("id")!=null) {
%>
<script>	
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
	
	<div style="height:8%"></div>
	<!-- 브래드 크럼 -->
	<div class="container" style="height:25%;">
		<hr>
		<div class="row">
			<nav aria-label="breadcrumb">
		  	<ol class="breadcrumb" style="margin:10px; background-color:white;">
			    <li class="breadcrumb-item active" aria-current="page">Infomation</li>
    			<li class="breadcrumb-item active" aria-current="page"><a href="company">Location</a></li>
  			  </ol>
			</nav>
		</div>
		<hr>
	</div>
	
	<div class="container" style="margin-bottom:5%">
		<h2 style="margin-bottom:50px;">ChatStory 인재상<hr/></h2>
		<img src="PageImage/travel-3141441_1280.jpg" class="d-block w-100">
	</div>
	
<!-- 회사 인재상 -->
	<div class="container">
		<h2 style="margin-bottom:50px;">도전<hr/></h2>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/achievement.jpg" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>끊임없이 도전하는 도전정신</h4></div>
		</div>
	</div>
	<div class="container" style="margin-top:8%">
		<h2 style="margin-bottom:50px;">팀워크<hr/></h2>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/meeting.jpg" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>팀원들과의 팀워크</h4></div>
		</div>
	</div>
	<div class="container" style="margin-top:8%">
		<h2 style="margin-bottom:50px;">창의성<hr/></h2>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/creativity.jpg" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>새로운 기술을 만들어내는 창의성</h4></div>
		</div>
	</div>
	
	<div style="height:20%"></div>
	
</body>
</html>