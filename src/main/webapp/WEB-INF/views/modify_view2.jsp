<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.project.springboot.dto.BDto"%>
<%@page import="java.io.File" %>
<!DOCTYPE html>
<html>

<%
	String id=null;
	String bName=null;
	BDto dto=null;
	if(session.getAttribute("id")!=null) {
		id=(String)session.getAttribute("id");
		dto=(BDto)request.getAttribute("content_view");
		bName=dto.getBName();
		int bId=dto.getBId();
	} else {
		response.sendRedirect("/loginForm");
	}
	
%>



<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CHAT STORY</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="webjars/bootstrap/5.0.0-beta1/css/bootstrap.min.css">
<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="webjars/bootstrap/5.0.0-beta1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/smarteditor2/js/HuskyEZCreator.js" charset="utf-8"></script>
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
			</div>
		</div>
	</nav>
	<br>
	<!-- 브래드 크럼 -->
	<div class="container" style="height:25%;">
		<hr>
		<div class="row">
			<nav aria-label="breadcrumb">
		  	<ol class="breadcrumb" style="margin:10px; background-color:white;">
			    <li class="breadcrumb-item active" aria-current="page">공지사항</li>
    			<li class="breadcrumb-item"><a href="list">자유게시판</a></li>
  			  </ol>
			</nav>
		</div>
		<hr>
	</div>	
	
	<!-- 수정 양식 -->
	<form action="<%= application.getContextPath()%>/manager_modify?bId=<%=dto.getBId()%>" method="post" enctype="multipart/form-data" id="smartForm" style="margin-top:50px;">
		<div class="container">
   			<div class="from-group">
 	 	  		<label for="exampleFormControlInput1">제목</label>
    	  		<input type="text" class="form-control" id="bTitle" name="bTitle" value="${content_view.BTitle}">
    		</div>		
  			<div class="form-group">
   				<label for="exampleFormControlTextarea1">내용</label>
	    		<textarea class="form-control" name="bContent" id="bContent" rows="25"><%=dto.getBContent()%></textarea>
		  	</div>
			<div class="form-group" style="border:1px solid #dddddd">
				<div class="custom-file">
  					<input type="file" class="custom-file-input" id="inputGroupFile02" name="customfile">
  					<a id='prevfile'><%if(dto.getBfileName()!=null) out.println(dto.getBfileName());%></a>
				</div>	
			</div>	
  			<div class="d-flex justify-content-between" style="margin-top:10px;">
				<a href='list2' class='btn btn-primary btn-lg active' role='button' aria-pressed='true'>목록보기</a>
  		  		<button class="btn btn-primary btn-lg active" type="button" id="smartButton">수정</button>
	  		</div>
		</div>
	</form>
	
	<div class="container" style="height:10%; margin-top:20px;">
	</div>

		<!-- SmartEditor -->
	<script>
		var oEditors=[];

		nhn.husky.EZCreator.createInIFrame({
			oAppRef:oEditors,
			elPlaceHolder:"bContent",
			sSkinURI:"/smarteditor2/SmartEditor2Skin.html",
			fCreator:"createSEditor2"
		});

		$("#smartButton").click(function () {
			oEditors.getById["bContent"].exec("UPDATE_CONTENTS_FIELD",[]);
			$('#smartForm').submit();
		});
	</script>

	<!-- 파일 업로드 시 이전 파일 이름 삭제 -->
	<script>	
		$('#inputGroupFile02').on('change',function(){
 			$('#prevfile').html('');
		});
	</script>

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