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
	
	<!-- 회원탈퇴 양식 -->
	<div class="container d-flex flex-column" style="border:1px solid #4B89Dc; margin-top:70px; padding-bottom:50px; margin-bottom:30px; max-width:800px;">
		<form class="row g-3" method="post" action="./delete" id="userdelete" name="userdelete">
			<h2 class="text-primary" style="margin-top:40px; margin-bottom:25px; text-align:center;">Chat Company</h2>
			<h4 class="text" style="margin-top:40px;">이용약관</h4>
			<textarea class="form-control" aria-label="textarea" style="background-color:white; min-height:200px; font-size:13px; overflow-y:auto" disabled>
제26조("이용자"의 회원탈퇴·해지) ① "회사"와 "콘텐츠"의 이용에 관한 계약을 체결한 "이용자"는 수신확인의 통지를 받은 날로부터 7일 이내에는 청약의 철회를 할 수 있습니다. 다만, "회사"가 다음 각 호중 하나의 조치를 취한 경우에는 "이용자"의 청약철회권이 제한될 수 있습니다.
1. 청약의 철회가 불가능한 "콘텐츠"에 대한 사실을 표시사항에 포함한 경우
2. 시용상품을 제공한 경우
3. 한시적 또는 일부이용 등의 방법을 제공한 경우
	② "이용자"는 다음 각 호의 사유가 있을 때에는 당해 "콘텐츠"를 공급받은 날로부터 3월 이내 또는 그 사실을 안 날 또는 알 수 있었던 날부터 30일 이내에 콘텐츠이용계약을 해제·해지할 수 있습니다.
1. 이용계약에서 약정한 "콘텐츠"가 제공되지 않는 경우
2. 제공되는 "콘텐츠"가 표시·광고 등과 상이하거나 현저한 차이가 있는 경우
3. 기타 "콘텐츠"의 결함으로 정상적인 이용이 현저히 불가능한 경우
	③ 제1항의 청약철회와 제2항의 계약해제·해지는 "이용자"가 전화, 전자우편 또는 모사전송으로 "회사"에 그 의사를 표시한 때에 효력이 발생합니다.
	④ "회사"는 제3항에 따라 "이용자"가 표시한 청약철회 또는 계약해제·해지의 의사표시를 수신한 후 지체 없이 이러한 사실을 "이용자"에게 회신합니다.
	⑤ "이용자"는 제2항의 사유로 계약해제·해지의 의사표시를 하기 전에 상당한 기간을 정하여 완전한 "콘텐츠" 혹은 서비스이용의 하자에 대한 치유를 요구할 수 있습니다.
				
제12조(회원탈퇴 및 자격 상실 등) ① "회원"은 "회사"에 언제든지 탈퇴를 요청할 수 있으며 "회사"는 즉시 회원탈퇴를 처리합니다.
	② "회원"이 다음 각호의 사유에 해당하는 경우, "회사"는 회원자격을 제한 및 정지시킬 수 있습니다.
1. 가입신청 시에 허위내용을 등록한 경우
2. "회사"의 서비스이용대금, 기타 "회사"의 서비스이용에 관련하여 회원이 부담하는 채무를 기일에 이행하지 않는 경우
3. 다른 사람의 "회사"의 서비스이용을 방해하거나 그 정보를 도용하는 등 전자상거래 질서를 위협하는 경우
4. "회사"를 이용하여 법령 또는 이 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우
	③ "회사"가 회원자격을 제한·정지시킨 후, 동일한 행위가 2회 이상 반복되거나 30일 이내에 그 사유가 시정되지 아니하는 경우 "회사"는 회원자격을 상실시킬 수 있습니다.
	④ "회사"가 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 "회원"에게 이를 통지하고, 회원등록 말소 전에 최소한 30일 이상의 기간을 정하여 소명할 기회를 부여합니다.
			</textarea>
			<div class="col-12 d-flex justify-content-end">
			  	<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="동의함">
  				<label class="form-check-label" for="inlineRadio1">동의함</label>
  				<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="동의안함" style="margin-left:10px;">
  				<label class="form-check-label" for="inlineRadio2">동의안함</label>
			</div>
			<h4 class="text" style="margin-top:40px;">탈퇴 시 유의사항</h4>
			<textarea class="form-control" aria-label="textarea" style="background-color:white; min-height:200px; font-size:13px; overflow-y:auto" disabled>
제12조(회원탈퇴 및 자격 상실 등) ① "회원"은 "회사"에 언제든지 탈퇴를 요청할 수 있으며 "회사"는 즉시 회원탈퇴를 처리합니다.
	② "회원"이 다음 각호의 사유에 해당하는 경우, "회사"는 회원자격을 제한 및 정지시킬 수 있습니다.
1. 가입신청 시에 허위내용을 등록한 경우
2. "회사"의 서비스이용대금, 기타 "회사"의 서비스이용에 관련하여 회원이 부담하는 채무를 기일에 이행하지 않는 경우
3. 다른 사람의 "회사"의 서비스이용을 방해하거나 그 정보를 도용하는 등 전자상거래 질서를 위협하는 경우
4. "회사"를 이용하여 법령 또는 이 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우
	③ "회사"가 회원자격을 제한·정지시킨 후, 동일한 행위가 2회 이상 반복되거나 30일 이내에 그 사유가 시정되지 아니하는 경우 "회사"는 회원자격을 상실시킬 수 있습니다.
	④ "회사"가 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 "회원"에게 이를 통지하고, 회원등록 말소 전에 최소한 30일 이상의 기간을 정하여 소명할 기회를 부여합니다.

제26조("이용자"의 회원탈퇴·해지) ① "회사"와 "콘텐츠"의 이용에 관한 계약을 체결한 "이용자"는 수신확인의 통지를 받은 날로부터 7일 이내에는 청약의 철회를 할 수 있습니다. 다만, "회사"가 다음 각 호중 하나의 조치를 취한 경우에는 "이용자"의 청약철회권이 제한될 수 있습니다.
1. 청약의 철회가 불가능한 "콘텐츠"에 대한 사실을 표시사항에 포함한 경우
2. 시용상품을 제공한 경우
3. 한시적 또는 일부이용 등의 방법을 제공한 경우
	② "이용자"는 다음 각 호의 사유가 있을 때에는 당해 "콘텐츠"를 공급받은 날로부터 3월 이내 또는 그 사실을 안 날 또는 알 수 있었던 날부터 30일 이내에 콘텐츠이용계약을 해제·해지할 수 있습니다.
1. 이용계약에서 약정한 "콘텐츠"가 제공되지 않는 경우
2. 제공되는 "콘텐츠"가 표시·광고 등과 상이하거나 현저한 차이가 있는 경우
3. 기타 "콘텐츠"의 결함으로 정상적인 이용이 현저히 불가능한 경우
	③ 제1항의 청약철회와 제2항의 계약해제·해지는 "이용자"가 전화, 전자우편 또는 모사전송으로 "회사"에 그 의사를 표시한 때에 효력이 발생합니다.
	④ "회사"는 제3항에 따라 "이용자"가 표시한 청약철회 또는 계약해제·해지의 의사표시를 수신한 후 지체 없이 이러한 사실을 "이용자"에게 회신합니다.
	⑤ "이용자"는 제2항의 사유로 계약해제·해지의 의사표시를 하기 전에 상당한 기간을 정하여 완전한 "콘텐츠" 혹은 서비스이용의 하자에 대한 치유를 요구할 수 있습니다.
			</textarea>
			<div class="col-12 d-flex justify-content-end">
			  	<input class="form-check-input" type="radio" name="inlineRadioOptions2" id="inlineRadio3" value="동의함">
  				<label class="form-check-label" for="inlineRadio3">동의함</label>
  				<input class="form-check-input" type="radio" name="inlineRadioOptions2" id="inlineRadio4" value="동의안함" style="margin-left:10px;">
  				<label class="form-check-label" for="inlineRadio4">동의안함</label>
			</div>
			<div class="col-12 d-flex justify-content" style="margin-top:60px;">
				<img src="/navercaptcha/${ncaptcha.Image}" class="img-thumbnail" id="img" name="img" style="max-width:300px;"/>
				<div class="col-md-4 d-flex align-items-center" style="padding-left:40px;">
					<input type="text" class="form-control" id="capvalue" name="capvalue" placeholder="보이는 글자를 입력해주세요." style="font-size:15px;">	
				</div>
			</div>
			<div class="col-12 d-flex">
				<a onclick="window.location.reload()" class="btn-sm btn-light" style="text-decoration:none; cursor:pointer; border:0.5px solid #dddddd">새로고침</a>
			</div>
			<input type="text" class="form-control" id="key" name="key" value="${ncaptcha.key}" style="display:none;" readonly>
			<p style="text-align:center; margin-top:30px;">이 페이지는 회원탈퇴 페이지입니다.</p>
			<div class="col-12 d-flex justify-content-end" style="padding-top:30px;">
				<button type="button" id="usersubmit" name="usersubmit" onclick="userMessage();" class="btn btn-primary">회원탈퇴</button>
			</div>
			
			<!-- 회원탈퇴 메시지 -->
			<div class="modal fade" id="userModal" tabindex="-1" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">확인 메시지</h5>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">
							회원 탈퇴를 진행할 경우 그동안 활동하신 내역이 모두 삭제됩니다. <br/>
							그래도 진행하시겠습니까?
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">진행</button>
							<button type="button" class="btn btn-primary" data-bs-dismiss="modal">취소</button>
						</div>
					</div>
				</div>
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
	
	<!-- 회원탈퇴 모달 출력 -->
	<script>
		function userMessage() {
			$('#userModal').modal("show");
		}
	</script>
	
</body>
</html>