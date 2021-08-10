<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%
	String id=null;
	if(session.getAttribute("id")!=null) {
		id=(String)session.getAttribute("id");
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

</head>
<body>


	<!-- 입력 양식 -->
<form action="<%= application.getContextPath() %>/manager_write" method="post" enctype="multipart/form-data" id="smartForm" style="margin-top:50px;">
	<div class="container">
   		<div class="from-group">
  	  		<label for="exampleFormControlInput1">제목</label>
      		<input type="text" class="form-control" id="bTitle" name="bTitle" placeholder="제목을 입력해주세요.">
    	</div>		
  		<div class="form-group">
   			<label for="exampleFormControlTextarea1">내용</label>
    		<textarea class="form-control" name="bContent" id="bContent" rows="25"></textarea>
	  	</div>
		<div class="form-group" style="border:1px solid #dddddd">
			<div class="custom-file">
  				<input type="file" class="custom-file-input" id="inputGroupFile02" name="customfile">
			</div>	
		</div>	
  		<div class="d-flex justify-content-between" style="margin-top:10px;">
			<a href='list2' class='btn btn-primary btn-lg active' role='button' aria-pressed='true'>목록보기</a>
  		  	<button class="btn btn-primary btn-lg active" type="button" id="smartButton">입력</button>
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