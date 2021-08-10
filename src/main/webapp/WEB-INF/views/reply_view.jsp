<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.project.springboot.dto.BDto"%>
<%@page import="com.project.springboot.dto.replyDto"%>
<%@page import="java.util.ArrayList"%>
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
<c:url value="/resources" var="conpath"/>
<script src="${conpath}/js/board.js"></script>
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
	
<script>
	$(document).ready(function (){
		<%if(id.equals(bName) || id.equals("ladool")) {%>
		$("#UserCheck").append("<a href='modify_view?bId=<%=dto.getBId()%>' class='btn btn-primary btn-lg active' role='button' aria-pressed='true'>수정</a>");
		$("#UserCheck").append('<button type="button" id="remove" name="remove" onclick="removeMessage();" class="btn btn-primary btn-lg active" style="margin-left:15px;">삭제</button>');
		<%}%>
		document.getElementById('focusItem').scrollIntoView();
	});
</script>

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
			    <li class="breadcrumb-item"><a href="list2">공지사항</a></li>
    			<li class="breadcrumb-item active" aria-current="page">자유게시판</li>
  			  </ol>
			</nav>
		</div>
		<hr>
	</div>	
	<!-- 입력 양식 -->
	<form>
		<div class="container">
			<div class="from-group">
  		  		<label for="exampleFormControlInput1">작성자</label>
	    	    <input type="text" name="bName" id="bName" class="form-control" placeholder="${content_view.BName}" disabled style="background-color:white;">
	    	</div>
   			<div class="from-group">
  	  			<label for="exampleFormControlInput1">제목</label>
      			<input type="text" class="form-control" id="bTitle" name="bTitle" placeholder="${content_view.BTitle}" disabled style="background-color:white;">
	    	</div>		
  			<div class="form-group">
   				<label for="exampleFormControlTextarea1">내용</label>
    			<textarea class="form-control" name="bContent" id="bContent" placeholder="${content_view.BContent}" rows="6"  disabled style="background-color:white;"></textarea>
	  		</div>
			<div class="form-group">
  		  		<label for="exampleFormControlInput1">첨부파일 :
  	  					<!-- 파일 다운로드 -->
						<%
							String FId=dto.getBfileName();
							if(FId!=null)
							{
								out.write("<a href=downloadAction?file="+FId+">"+FId+"</a><br>");	
							}
						%>
  	  			</label>
			</div>
			<div class="row" style="margin-top:10px;">
				<div class="col-9">
					<a href='list' class='btn btn-primary btn-lg active' role='button' aria-pressed='true'>목록</a>
				</div>
				<div class="d-flex justify-content-end col-3" id="UserCheck" class=""></div>
			</div>
		</div>
	</form>
	<div class="container" style="height:10%; margin-top:20px;">
		<hr>
	</div>
	

	
	<div class="container" style="width:50%" id="replys"></div>
	
		<!-- 댓글창 출력  -->
	<%
		ArrayList<replyDto> list=(ArrayList<replyDto>)request.getAttribute("reply_view");
		String reply_Group=null;

		for(int i=0; i<list.size();i++) {
			replyDto replydto=list.get(i);
			reply_Group=replydto.getReplyGroup();
			if(i==0) {
	%>
			<script>
				$(document).ready(function (){
					$("#replys").prepend('<div class="form-group">'+
											'<label for="exampleFormControlTextarea1"><%=replydto.getReplyId()%></label>'+
											"<label for='exampleFormControlTextarea1' class='d-flex justify-content-end'><%=replydto.getReplyDate()%></label>"+
											'<textarea class="form-control" name="TContent" id="TContent" rows="5" disabled style="background-color:white;"><%=replydto.getReplyContent()%></textarea>'+
										'</div>');
				});
			</script>
		<%} else {%>
			<script>
				$(document).ready(function (){
					$("#replys").append(
								'<div class="form-group" style="width:80%; padding-left:20%; margin-top:30px;">'+
									'<label for="exampleFormControlTextarea1"><%=replydto.getReplyId()%></label>'+
									"<label for='exampleFormControlTextarea1' class='d-flex justify-content-end'><%=replydto.getReplyDate()%></label>"+
    								'<textarea class="form-control" name="TContent" id="TContent" rows="5" disabled style="background-color:white;"><%=replydto.getReplyContent()%></textarea>'+
    					  		  	'<%if(id.equals(replydto.getReplyId())) {%><div class="d-flex justify-content-end" style="margin-top:10px;"><button class="btn btn-primary btn active" onclick="revisefunction2(<%=replydto.getBId()%>,<%=replydto.getReplyGroup()%>,<%=replydto.getReplyStep()%>,\'<%=replydto.getReplyContent()%>\')">수정</button><a class="btn btn-primary btn active" href="/reply_reply_delete?bId=<%=replydto.getBId()%>&replyGroup=<%=replydto.getReplyGroup()%>&replyStep=<%=replydto.getReplyStep()%>" style="margin-left:5px;">삭제</a></div><%}%>'+
								'</div>');
				});
			</script>

	<%
			}
		}
	%>

	<div class="container" style="width:50%" id="write">
		<div class="container" style="width:60%; margin-top:30px;">
			<form action="<%=application.getContextPath()%>/reply_reply?bId=${content_view.BId}&replyGroup=<%=reply_Group%>" method="post">
				<div class="form-group">
	   				<label for="exampleFormControlTextarea1" id="focusItem">답글</label><span class="counter" id="counter" name="counter">(0 /  200)</span>
    				<textarea class="form-control" name="TContent" id="TContent" rows="5"></textarea>
				</div>
	  		  	<div class="d-flex justify-content-end" style="margin-top:10px;"><button class="btn btn-primary btn active" type="submit">입력</button></div>
			</form>
		</div>
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
	
	<!-- 댓글 글자수 처리 -->
<script>
	$('#TContent').keyup(function (e){
    	var content = $(this).val();
    	$('#counter').html("("+content.length+" / 200)");    //글자수 실시간 카운팅

    	if (content.length > 200){
        	alert("최대 200자까지 입력 가능합니다.");
        	$(this).val(content.substring(0, 200));
        	$('#counter').html("(200 / 최대 200자)");
    	}
	});
</script>

	<!-- 게시글 삭제 모달 -->
	<div class="modal fade" id="userModal" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">확인 메시지</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					현재 게시글이 영구히 삭제됩니다. 진행하시겠습니까?
				</div>
				<div class="modal-footer">
					<a href="/bdelete?bId=${content_view.BId}" class="btn btn-primary">삭제</a>
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 게시글 삭제 모달 출력 -->
	<script>
		function removeMessage() {
			$('#userModal').modal("show");
		}
	</script>

	
</body>
</html>