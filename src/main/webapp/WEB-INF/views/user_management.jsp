<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>

<%
	String id=null;
	if(session.getAttribute("id")!=null) {
		id=(String)session.getAttribute("id");
		if(!id.equals("ladool")) {
			session.setAttribute("messageContent","관리자 외에 접근할 수 없는 페이지입니다.");
			response.sendRedirect("/");
		}
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
<script src="${conpath}/js/chatbox.js"></script>
<link href="${conpath}/css/chating.css" rel="stylesheet">

<%
	if(session.getAttribute("id")!=null) {
%>
<script>	
	unreadfunction('<%=(String)session.getAttribute("id")%>');
</script>
<%
	}
%>

	<!-- 비밀번호 확인 -->
<script type="text/javascript">
	function passwordCheckfunction() {
		var userPassword1=$('#userPassword').val();
		var userPassword2=$('#userPassword2').val();
		if(userPassword1!=userPassword2) {
			$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
		} else {
			$('#passwordCheckMessage').html('');
		}
	}

	<!-- 프로필 사진 미리보기 -->
	$(document).ready(function () {
		$('#userProfile').on("change", setProfile);
	});
	
	function setProfile(e) {
		var files=e.target.files;
		var filesArr=Array.prototype.slice.call(files);

		filesArr.forEach(function(f) {
			if(!f.type.match("image.*")) {
				alert("확장자는 이미지 확장자만 가능합니다.");
				return;
			}

			set_file=f;

			var reader=new FileReader();
			reader.onload=function(e) {
				$('#img').attr("src", e.target.result);
			}
			reader.readAsDataURL(f);
		})
	}

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
	
	<!-- 회원정보 수정 -->
	<c:url value="upload/${dto.userProfile}" var="conpath"/>
	<div class="container d-flex flex-column" style="border:1px solid #4B89Dc; margin-top:70px; padding-bottom:50px; margin-bottom:30px;">
		<h1 class="text-primary" style="text-align:center; margin:30px;">Chat Story</h1>
		<div class="row">
			<div class="container col-md-1">
			</div>
			<div class="container col-md-4" style="margin-top:5%; padding-left:5%">
				<img src="${conpath}" class="img-thumbnail" id="img" name="img"/>
			</div>
			<div class="container col-md-6" style="margin-top:30px; padding-right:5%">
				<div class="row justify-content-end">
					<div class="col-md-9">
						<label for="userID" class="form-label">아이디</label>
						<input type="text" class="form-control" id="userID" name="userID" value="${dto.userID}" readonly>
					</div>					
				</div>
				<div class="row justify-content-end">					
					<div class="col-md-9">
						<label for="userName" class="form-label">이름</label>
						<input type="text" class="form-control" id="userName" name="userName" placeholder="이름을 입력해주세요." value="${dto.userName}" readonly>
					</div>
				</div>
				<div class="row justify-content-end">					
					<div class="col-md-9">
						<label for="userGender" class="form-label">성별</label>
						<input type="text" class="form-control" id="userGender" name="userGender" value="${dto.userGender}" readonly>
					</div>
				</div>
				<div class="row justify-content-end">					
					<div class="col-md-9">
						<label for="userAge" class="form-label">생년월일</label>
						<input type="number" class="form-control" id="userAge" name="userAge" value="${dto.userAge}" readonly> 
					</div>
				</div>
			</div>
			<div class="container col-md-1">
			</div>
		</div>
		<div class="container col-md-10" style="margin-top:30px;">
			<div class="row justify-content-center">
				<div class="col-12">
					<label for="userAddress" class="form-label">주소</label>
					<input type="text" class="form-control" id="userAddress" name="userAddress" value="${dto.userAddress}" readonly>
				</div>
			</div>
			<div class="row justify-content-center" style="margin-top:10px;">				
				<div class="col-md-12">
					<label for="userEmail" class="form-label">이메일</label>
					<input type="email" class="form-control" id="userEmail" name="userEmail" value="${dto.userEmail}" readonly>
				</div>
			</div>
			<div class="row justify-content-end">
				<button class="col-md-2 btn btn-primary" onclick="userMessage()" style="margin-top:50px;">회원삭제</button>
			</div>			
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
		<!-- 회원삭제 메시지 -->
	<div class="modal fade" id="userModal" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">확인 메시지</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					해당 아이디가 영구히 삭제됩니다.<br/>
					그래도 삭제하시겠습니까?
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" href="manager_userremove?userID=${dto.userID}&userProfile=${dto.userProfile}">진행</a>
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
	
		<!-- 회원탈퇴 모달 출력 -->
	<script>
		function userMessage() {
			$('#userModal').modal("show");
		}
	</script>
</body>
</html>