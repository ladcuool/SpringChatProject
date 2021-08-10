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
	
	<!-- 브레드 크럼 -->
	<div class="container" style="height:25%;">
		<hr>
		<div class="row">
			<nav aria-label="breadcrumb">
		  		<ol class="breadcrumb" style="margin-top:20px; background-color:white;">
    				<li class="breadcrumb-item active" aria-current="page">관리자 페이지</li>
  			  	</ol>
			</nav>
		</div>
		<hr>
	</div>
	
	<!-- 내용 -->
	<div class="container">
	<!-- 테이블 -->
	<div class="d-flex justify-content-center">
		<table class="table table-hover col-6">
		<thead>
			<tr>
				<th scope="col">아이디</th>
				<th scope="col">이름</th>
				<th scope="col">이메일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="dto">
			<tr>
				<c:choose>
					<c:when test="${dto.userID=='ladool'}">
						<td scope="col">관리자</td>
					</c:when>
					<c:otherwise>
						<td scope="col"><a href="user_management?userID=${dto.userID}">${dto.userID}</a></td>				
					</c:otherwise>
				</c:choose>
				<td scope="col">${dto.userName}</td>
				<td>${dto.userEmail}</td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="5">
				<!-- 처음 -->
					<nav class="d-flex justify-content-center align-items-center" aria-label="...">
  						<ul class="pagination">
  							<!-- 처음 -->
  							<c:choose>
  							<c:when test="${(page.curPage-1)<1}">
  								<li class="page-item disabled">
						    		<a class="page-link" href="#" tabindex="-1" aria-disabled="true">처음으로</a>
    							</li>
  							</c:when>
  							<c:otherwise>
  								<li class="page-item">
						    		<a class="page-link" href="managerPage?page=1" tabindex="-1" aria-disabled="true">처음으로</a>
    							</li>
  							</c:otherwise>
  							</c:choose>
  							<!-- 이전 -->
  							<c:choose>
  							<c:when test="${(page.curPage-1)<1}">
  								<li class="page-item disabled">
						    		<a class="page-link" href="#" tabindex="-1" aria-disabled="true">이전</a>
    							</li>
  							</c:when>
  							<c:otherwise>
  								<li class="page-item">
						    		<a class="page-link" href="managerPage?page=${page.curPage-1}" tabindex="-1" aria-disabled="true">이전</a>
    							</li>
  							</c:otherwise>
  							</c:choose>
  							
  							<!-- 개별 페이지 -->
  							<c:forEach var="fEach" begin="${page.startPage}" end="${page.endPage}" step="1">
  								<c:choose>
  								<c:when test="${page.curPage==fEach}">
  									<li class="page-item disabled">
					    				<a class="page-link" href="#">${fEach}</a>
						    		</li>
  								</c:when>
  								<c:otherwise>
  									<li class="page-item">
					    				<a class="page-link" href="managerPage?page=${fEach}">${fEach}</a>
						   			 </li>
  								</c:otherwise>
  								</c:choose>
  							</c:forEach>
  							
  							<!-- 다음 -->
  							<c:choose>
  							<c:when test="${(page.curPage+1)>page.totalPage}">
  								<li class="page-item disabled">
      								<a class="page-link" href="#">다음</a>
					    		</li>
					    	</c:when>
					    	<c:otherwise>
					    		<li class="page-item">
      								<a class="page-link" href="managerPage?page=${page.curPage+1}">다음</a>
					    		</li>	
					    	</c:otherwise>
					    	</c:choose>
					    	<!-- 끝 -->
					    	<c:choose>
					    	<c:when test="${page.curPage==page.totalPage}">
					    		<li class="page-item disabled">
      								<a class="page-link" href="#">끝으로</a>
					    		</li>	
					    	</c:when>
					    	<c:otherwise>
					    		<li class="page-item">
      								<a class="page-link" href="managerPage?page=${page.totalPage}">끝으로</a>
					    		</li>	
					    	</c:otherwise>
					    	</c:choose>
  						</ul>
					</nav>
				</td>
			</tr>
		</tbody>
	</table>

	</div>
	</div>	
	<div class="d-flex justify-content-center" style="max-height:60px;">
		<form class="d-flex" action="<%=application.getContextPath()%>/manager_user_search">
			<select class="form-select form-select-sm" aria-label=".form-select-sm example" name="Sel" id="Sel">
	  			<option selected value="">선택</option>
  				<option value="아이디">아이디</option>
  				<option value="이름">이름</option>
			</select>
        	<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="strTitle" id="strTitle">
        	<button class="btn btn-outline-success" type="submit" style="min-width:80px;">검색</button>
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