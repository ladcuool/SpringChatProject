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
<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=" ></script>
<c:url value="/resources" var="conpath"/>
<script src="${conpath}/js/chatbox.js"></script>
<link href="${conpath}/css/chating.css" rel="stylesheet">

<style>
	@import url(https://fonts.googleapis.com/earlyaccess/notosanskr.css);
	html, body {
		height:100%;
		width:100%;
	}
	
	#map_ma {width:40%; height:400px; clear:both;}
	#fontpoint{font-family: 'Noto Sans KR';}
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
						<a class="nav-link" href="list2">????????????</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="list">???????????????</a>
					</li>
				</ul>
				<!-- ????????? ?????? -->
				<%
					if(session.getAttribute("id")==null) {
				%>
						<a href="userjoin" class="btn btn-primary" style="margin-right:10px;">????????????</a>
						<a href="loginForm" class="btn btn-primary">?????????</a>
				<%
					} else {
						id=(String)session.getAttribute("id");
				%>
						<a onclick="listfunction();" style="margin-right:3%; cursor:pointer">
							<img class="col-sm-3 rounded-circle" id="ChatCircle" name="ChatCircle"
								 style="height:45px; width:45px;" src="PageImage/message.jpg">
							<span class="badge bg-primary" style="position:absolute;" id="getunread"></span>
						</a>
						<a href="userInfo" style="margin-right:10px; text-decoration:none;">[<%=id%>]</a><a style="margin-right:15px;">??? ???????????????!!</a>
						<a href="/logout" class="btn btn-primary">????????????</a>
						<%if(id.equals("ladool")) { %>
							<a href="/managerPage" class="btn btn-primary" style="margin-left:5px;">?????????</a>
						<%} %>
				<%
					}
				%>
			</div>
		</div>
	</nav>
	
	<div style="height:8%"></div>
	<!-- ????????? ?????? -->
	<div class="container" style="height:25%;">
		<hr>
		<div class="row">
			<nav aria-label="breadcrumb">
		  	<ol class="breadcrumb" style="margin:10px; background-color:white;">
			    <li class="breadcrumb-item"><a href="companyinfomation">Infomation</a></li>
    			<li class="breadcrumb-item active" aria-current="page">Location</li>
  			  </ol>
			</nav>
		</div>
		<hr>
	</div>
	<div class="d-flex justify-content-center" style="margin-bottom:5%;">
		<div id="map_ma"></div>		
	</div>

	<div class="container">
		<hr/>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/location.png" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>??????????????? ????????? ??????2??? ?????????????????? 130?????? Chat Story ??????</h4></div>
		</div>
	</div>
	<div class="container">
		<hr/>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/bus.png" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>16??? ?????? ???????????? ????????? ??????</h4></div>
		</div>
	</div>
	<div class="container">
		<hr/>
		<div class="row">
			<img id="ChatCircle" class="col-sm-5" name="ChatCircle"
				src="PageImage/subway.png" style="padding-top:10px;">
			<div class="col-sm-1"></div>
			<div class="col-sm-5 d-flex align-items-center"><h4>?????????????????? ?????? 5??? ???????????? ????????? 10???</h4></div>
		</div>
	</div>
	
	<div style="height:10%"></div>	
	
	<!-- ?????? ??? -->
	<script type="text/javascript">
		$(document).ready(function() {
			var myLatlng = new google.maps.LatLng(35.837143,128.558612); // ????????? ?????? ??????
			var Y_point			= 37.392864;		// Y ??????
			var X_point			= 126.644800;		// X ??????
			var zoomLevel		= 18;				// ????????? ?????? ?????? : ????????? ????????? ??????????????? ???
			var markerTitle		= "??????????????? ??????";		// ?????? ?????? ????????? ???????????? ???????????? ???????????? ??????

			var myLatlng = new google.maps.LatLng(Y_point, X_point);
			var mapOptions = {
				zoom: zoomLevel,
				center: myLatlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			var map = new google.maps.Map(document.getElementById('map_ma'), mapOptions);
			var marker = new google.maps.Marker({
				position: myLatlng,
				map: map,
				title: markerTitle
			});
			var infowindow = new google.maps.InfoWindow(
			{
				content: contentString,
				maxWizzzdth: markerMaxWidth
			}
			);
			google.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map, marker);
			});
		});
</script>
	
</body>
</html>