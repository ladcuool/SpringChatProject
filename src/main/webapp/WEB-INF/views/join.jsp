<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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



<!-- 아이디 중복 확인 -->
<script type="text/javascript">
	function registerCheckfunction() {
		var userID=$('#userID').val();
		$.ajax({
			type:'POST',
			url:'./IdCheck',
			data:{userID:userID},
			success:function(result) {
				if(result==0) {
					$('#checkMessage').html('사용할 수 있는 아이디입니다.');
				} else {
					$('#checkMessage').html('사용할 수 없는 아이디입니다.');
				}
				$('#checkModal').modal("show");
			}
		});
	}
</script>


</head>
<body>
	
	<!-- nav bar -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">Chat with Friend</a>
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
	
	<!-- 회원가입 양식 -->
	<div class="container d-flex flex-column" style="border:1px solid #4B89Dc; margin-top:70px; padding-bottom:50px; margin-bottom:30px;">
		<form class="row g-3" method="post" action="./register" id="userRegister" name="userRegister">
			<h1 class="text-primary" style="text-align:center; margin:30px;">Chat Story</h1>
			<div class="col-md-6">
				<label for="userID" class="form-label">아이디</label>
				<input type="text" class="form-control" id="userID" name="userID" placeholder="아이디를 입력해주세요." value="${dto.userID}">
				<button class="btn btn-primary" type="button" onclick="registerCheckfunction()" style="margin-top:5px;">중복확인</button>
			</div>
			<div class="col-md-6">
				<label for="userPassword" class="form-label">비밀번호</label>
				<input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="비밀번호를 입력해주세요." value="${dto.userPassword}">
			</div>
			<div class="col-md-4">
				<label for="userName" class="form-label">이름</label>
				<input type="text" class="form-control" id="userName" name="userName" placeholder="이름을 입력해주세요." value="${dto.userName}">
			</div>
			<div class="col-md-2">
				<label for="userGender" class="form-label">성별</label>
				<select id="userGender" class="form-select" name="userGender">
					<option selected>선택</option>
					<option>남자</option>
					<option>여자</option>
				</select>
			</div>
			<div class="col-md-4">
				<label for="userAge" class="form-label">생년월일</label>
				<input type="number" class="form-control" id="userAge" name="userAge" placeholder="-생략 ex)19950213" value="${dto.userAge}"> 
			</div>

			<div class="col-12">
				<label for="userAddress" class="form-label">주소</label>
				<input type="text" class="form-control" id="userAddress" name="userAddress" placeholder="주소를 입력해주세요."  value="${dto.userAddress}">
			</div>
			<div class="col-md-12">
				<label for="userEmail" class="form-label">이메일</label>
				<input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="이메일을 입력해주세요." value="${dto.userEmail}">
			</div>
			<div class="row justify-content-end">
				<input class="col-md-1 btn btn-primary" type="submit" value="등록"  style="margin-top:50px;">
			</div>
		</form>
	</div>

	<!-- 아이디 중복체크 모달 -->
	<div class="modal fade" id="checkModal" tabindex="-1" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModallabel">확인 메시지</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body" id="checkMessage">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal">확인</button>
				</div>
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
	

</body>
</html>