var ws=null;

function chatbox() {
	//이전 위치 저장
	prepoint=document.getElementById("mydiv");
	$('#mydiv').remove();
	conpath=getContextPath();
	
	$('body').prepend('<div class="inbox_people" id="mydiv" style="resize:both;">'+
          '<div class="headind_srch" id="mydivheader">'+
            '<div class="recent_heading">'+
              '<h4>ChatFriends</h4>'+
            '</div>'+
            '<div class="srch_bar">'+
              '<div class="stylish-input-group">'+
                  '<a type="button" onclick="listfunction();">직원목록</a>&nbsp;&nbsp;&nbsp;'+
                  '<a type="button" onclick="chatremovefunction();">닫기</a>'+
                '</div>'+
            '</div>'+
          '</div>'+
          '<div class="inbox_chat" id="inbox_chat">'+
          '</div>'+
        '</div>')


	//이전 위치에서 출력
	oripoint=document.getElementById("mydiv");
	oripoint.style.top=prepoint.style.top;
	oripoint.style.left=prepoint.style.left;
	oripoint.style.right=prepoint.style.right;
	
	dragElement(document.getElementById("mydiv"));
	
			//이전 채팅리스트 가져오기
	$.ajax({
		type:"POST",
		url:"/rooms",
		data: {id:sessionStorage.getItem("id")},
		success:function(data) {
			if(data=="") return;
			$.each(data, function(index, value) {
				if(value.unread==0) {
					value.unread="";
				}
				$('#inbox_chat').append(
						'<div class="chat_list" onclick="chatsocketfunction(\''+value.roomNumber+'\')">'+
    	          			'<div class="chat_people">'+
        	        			'<div class="chat_img"><img class="rounded-circle" src="'+conpath+value.userProfile+'" alt="sunil"></div>'+
            	    			'<div class="chat_ib">'+
                	  				'<h5>'+value.roomUsers+'<span class="badge bg-primary" style="position:absolute; margin-left:2%;">'+value.unread+'</span><span class="chat_date">'+
									value.nowTime+'</span></h5>'+
                  					'<p>'+value.msg+'</p>'+
	                			'</div>'+
    	          			'</div>'+
        	    		'</div>'+
          			'</div>'
					)
				});
			}
		});

}



function dragElement(elmnt) {
  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  if (document.getElementById(elmnt.id + "header")) {
    // if present, the header is where you move the DIV from:
    document.getElementById(elmnt.id + "header").onmousedown = dragMouseDown;
  } else {
    // otherwise, move the DIV from anywhere inside the DIV:
    elmnt.onmousedown = dragMouseDown;
  }

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    // get the mouse cursor position at startup:
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    pos1 = pos3 - e.clientX;
    pos2 = pos4 - e.clientY;
    pos3 = e.clientX;
    pos4 = e.clientY;
    largebox=document.querySelector('body').getBoundingClientRect();
    // set the element's new position:
    if((elmnt.offsetTop-pos2)<0) {
		elmnt.style.top=largebox.height;
    } else {
        elmnt.style.top=(elmnt.offsetTop - pos2) + "px";
    }
    elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
  }

  function closeDragElement() {
    // stop moving when mouse button is released:
    document.onmouseup = null;
    document.onmousemove = null;
  }

}


function listfunction() {
	conpath=getContextPath();
	prepoint=document.getElementById("mydiv");
	
	$('#mydiv').remove();
	$('body').prepend(
        '<div class="inbox_people" id="mydiv" style="resize:both;">'+
          '<div class="headind_srch" id="mydivheader">'+
            '<div class="recent_heading">'+
              '<h4>ChatFriends</h4>'+
            '</div>'+
            '<div class="srch_bar">'+
              '<div class="stylish-input-group">'+
                '<a type="button" onclick="chatbox();">채팅</a>&nbsp;&nbsp;&nbsp;'+
                '<a type="button" onclick="chatremovefunction();">닫기</a>'+
               '</div>'+
            '</div>'+
          '</div>'+
         '<div class="inbox_chat">'+
            '<div id="memberlist"></div>'+
          '</div>'+
        '</div>'
	)
	
	oripoint=document.getElementById("mydiv");
	dragElement(document.getElementById("mydiv"));
	
	$.ajax({
		type:"POST",
		url:"/memberlist",
		data: {},
		success:function(data) {
			if(data=="") return;
			$.each(data, function(index, value) {
				if(value.userID==sessionStorage.getItem("id")) {
					$('.inbox_chat').prepend(
					   '<div class="chat_list" onclick="userInfo(\''+value.userID+'\')">'+
					     '<div class="chat_people">'+
					       '<div class="chat_img"> <img class="rounded-circle" src="'+conpath+value.userProfile+'" alt="sunil" style="min-height:28px;"></div>'+
					         '<div class="chat_ib">'+
					           '<h5>나</h5>'+
				    	     '</div>'+
				      	'</div>'+
					   '</div>'
					);
				} else if(value.authority=="ROLE_MEMBER") {
					$('#memberlist').append(
				   		'<div class="chat_list" onclick="userInfo(\''+value.userID+'\')">'+
				     	'<div class="chat_people">'+
				       		'<div class="chat_img"> <img class="rounded-circle" src="'+conpath+value.userProfile+'" alt="sunil" style="min-height:28px;"></div>'+
				         	'<div class="chat_ib">'+
				           		'<h5>'+value.userName+'</h5>'+
				         	'</div>'+
					      '</div>'+
					   '</div>'	
					);					
				} else {
				}
			});
		}
	});
	
	if(prepoint!=null) {
		oripoint.style.top=prepoint.style.top;
		oripoint.style.left=prepoint.style.left;
		oripoint.style.right=prepoint.style.right;
	}
}

function chatremovefunction() {
	$('#mydiv').remove();
}

function getContextPath() {
  return window.location.pathname.substring(0, window.location.pathname.indexOf("/",0))+'/upload/';
}

//유저 정보창
function userInfo(userID) {
		$('#userdiv').remove();
		conpath=getContextPath();
		$.ajax({
			type:"POST",
			url:"/chatuserInfo",
			data: {userID:userID},
			success:function(data) {
				if(data=="") return;
				if(data.userID==sessionStorage.getItem("id")) {
					$('body').prepend(
        				'<div class="inbox_people" id="userdiv">'+
	          				'<div class="headind_srch" id="userdivheader">'+
    	        				'<div class="recent_heading">'+
        	      					'<h4>ChatFriends</h4>'+
            					'</div>'+
            					'<div class="srch_bar">'+
              						'<div class="stylish-input-group">'+
                						'<a type="button" onclick="userdivremovefunction();">X</a>'+
               						'</div>'+
	            				'</div>'+
    	      				'</div>'+
        	  				'<div class="userInfo_box">'+
          						'<img class="rounded-circle" src="'+conpath+data.userProfile+'" alt="sunil" style="height:60%; width:60%;">'+
          						'<h4 style="padding-top:10px; padding-right:5px;">나</h4>'+
	          				'</div>'+
    	    			'</div>'
					);
				} else {
					$('body').prepend(
        				'<div class="inbox_people" id="userdiv">'+
	          				'<div class="headind_srch" id="userdivheader">'+
    	        				'<div class="recent_heading">'+
        	      					'<h4>ChatFriends</h4>'+
            					'</div>'+
            					'<div class="srch_bar">'+
              						'<div class="stylish-input-group">'+
                						'<a type="button" onclick="userdivremovefunction();">X</a>'+
               						'</div>'+
	            				'</div>'+
    	      				'</div>'+
        	  				'<div class="userInfo_box">'+
          						'<img class="rounded-circle" src="'+conpath+data.userProfile+'" alt="sunil" style="height:60%; width:60%;">'+
          						'<h4 style="padding-top:10px; padding-right:5px;">'+data.userID+'</h4>'+
          						'<a type="button" style="padding-top:10px;" onclick="socketfunction(\''+userID+'\');">1:1 대화하기</a>'+
	          				'</div>'+
    	    			'</div>'
					);
				}
					//초기 div생성 시 가운데 정렬
					document.getElementById("userdiv").style.position='absolute';
					document.getElementById("userdiv").style.top='50%';
					document.getElementById("userdiv").style.left='50%';
					document.getElementById("userdiv").style.transform='translate(-50%,-50%)';
					
					dragElement(document.getElementById("userdiv"));
				}
			});
		}

var ws;

function userdivremovefunction() {
	$('#userdiv').remove();
}

//1:1 채팅 구현부
function socketfunction(userID) {
	$('#chatdiv').remove();
		$.ajax({
			type:"POST",
			url:"/createRoom",
			data: {userID:userID},
			success:function(data) {
				if(data=="") return;
				$('body').prepend(
					'<div class="mesgs" id="chatdiv">'+
						'<input type="hidden" id="roomNumber" value="'+data+'">'+
	          			'<div class="headind_srch" id="chatdivheader">'+
    	        			'<div class="recent_heading">'+
        	      				'<h4>ChatFriends</h4>'+
            				'</div>'+
            				'<div class="srch_bar">'+
              					'<div class="stylish-input-group">'+
                					'<a type="button" onclick="chatingremovefunction();">닫기</a>'+
               					'</div>'+
	           				'</div>'+
    	     			'</div>'+
        	 			'<div class="msg_history" id="msg_history">'+
          				'</div>'+
          				'<form id="messageForm" name="messageForm">'+
          					'<div class="type_msg">'+
            					'<div class="input_msg_write">'+
              						'<input type="text" class="write_msg" id="usermessage" placeholder="Type a message" />'+
              						'<a class="msg_send_btn" onkeyup="enterkey();" onclick="send()"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></a>'+
            					'</div>'+
          					'</div>'+
	          			'</form>'+
    	    		'</div>'
					)				

					dragElement(document.getElementById("chatdiv"));
					if(ws!=null) {
						ws.close();
					}
					ws=new WebSocket("ws://"+location.host+"/chating/"+$("#roomNumber").val());
					wsEvt();
					gethistoryfunction(data);
				}
			});
}


//두번 입력 오류 잡기

function gethistoryfunction(roomNumber) {
	$.ajax({
		type:"POST",
		url:"/chathistory",
		data: {roomNumber:roomNumber},
		success:function(data) {
			if(data!=null) {
				var date; var myear; var mmonth; var mday; var mhour; var mminutes; var format;
				
				$.each(data, function(index, value) {
					date=new Date(value.nowTime);
					var s=
						leadingZeros(date.getFullYear(),4)+'-'+
						leadingZeros(date.getMonth()+1,2)+'-'+
						leadingZeros(date.getDate(),2)+' '+
						
						leadingZeros(date.getHours(),2)+':'+
						leadingZeros(date.getMinutes(),2);
					if(value.userID==sessionStorage.getItem("id")) {
						$('#msg_history').append(
							'<div class="outgoing_msg">'+
              					'<div class="sent_msg">'+
                					'<p>'+value.msg+'</p>'+
                					'<span class="time_date">'+s+'</span> </div>'+
		            			'</div>'						
						)
					} else {
						$('#msg_history').append(
		            		'<div class="incoming_msg">'+
        	    	  			'<div class="incoming_msg_img"> <img class="rounded-circle" src="'+conpath+value.userProfile+'" alt="sunil">'+
            	  				'</div><span class="username">'+value.userID+'</span>'+
              					'<div class="received_msg">'+
                					'<div class="received_withd_msg">'+
                  						'<p>'+value.msg+'</p>'+
		                  				'<span class="time_date">'+s+'</span></div>'+
        		      				'</div>'+
            					'</div>'					
						)
					}

				});						
			}
		}
	});	
}

function wsEvt() {
	ws.onopen=function(data) {
		//소켓이 열리면 초기화 세팅하기
		$(document).keypress(function (e) {
			if(e.keyCode==13)
				e.preventDefault();
		});
		
		var objDiv=document.getElementById("msg_history");
		objDiv.scrollTop=objDiv.scrollHeight;
	}
	
	ws.onmessage=function(data) {
		//메시지를 받으면 동작
		conpath=getContextPath();
		var msg=data.data;
		if(data.roomNumber!=null) {
			$.ajax({
				type:"POST",
				url:"/read",
				data: {roomNumber:data.roomNumber},
				success:function(data) {}
			});			
		}
		if(msg!=null&&msg.trim()!="") {
			var d=JSON.parse(msg);
			var count=d.read;
			if(d.userID!=sessionStorage.getItem("id")) {
				count="";
			}
			
			if(d.msg!=null) {
				if(d.userID==sessionStorage.getItem("id")) {
					$('#msg_history').append(
						'<div class="outgoing_msg">'+
        	      			'<div class="sent_msg">'+
            	    		'<p>'+d.msg+'</p>'+
                			'<span class="time_date">'+d.time+'&nbsp;&nbsp;<span style="color:#dddddd;">'+count+'</span></span></div>'+
	            		'</div>'
					);
				} else {
					$('#msg_history').append(
						'<div class="incoming_msg">'+
              				'<div class="incoming_msg_img">'+
								'<img src="'+conpath+d.profile+'" alt="sunil">'+
							'</div>'+
							'<span class="username">'+d.userID+'&nbsp<span style="color:#dddddd;">'+count+'</span></span>'+
        	      			'<div class="received_msg">'+
            	    			'<div class="received_withd_msg">'+
                	  				'<p>'+d.msg+'</p>'+
                  					'<span class="time_date">'+d.time+'</span>'+
								'</div>'+
	              			'</div>'+
    	        		'</div>'
					)
				}
			}
			var objDiv=document.getElementById("msg_history");
			objDiv.scrollTop=objDiv.scrollHeight;
		}
		
	}

}

function send() {
	var date=new Date();
	var profile;
	var s=
		leadingZeros(date.getFullYear(),4)+'-'+
		leadingZeros(date.getMonth()+1,2)+'-'+
		leadingZeros(date.getDate(),2)+' '+
				
		leadingZeros(date.getHours(),2)+':'+
		leadingZeros(date.getMinutes(),2);
		
	$.ajax({
		type:"POST",
		url:"/chatprofile",
		data: {id:sessionStorage.getItem("id")},
		success:function(data) {
			profile=data;
		}
	});
	
	var option= {
		roomNumber:$("#roomNumber").val(),
		userID:sessionStorage.getItem("id"),
		msg:$("#usermessage").val(),
		time:s,
		read:"1",
		profile:profile
	}
	
	ws.send(JSON.stringify(option));
	$("#usermessage").val("");
}



//이전 대화 기록 가져와서 만들어진 채팅창
//여기서부터 다시
function chatsocketfunction(roomNumber) {
	$.ajax({
		type:"POST",
		url:"/read",
		data: {roomNumber:roomNumber},
		success:function() {
			chatbox();
		}
	});
	$(document).ready(function() {
	$('#chatdiv').remove();
		$.ajax({
			type:"POST",
			url:"/chathistory",
			data: {roomNumber:roomNumber},
			success:function(data) {
				if(data=="") return;
				$('body').prepend(
					'<div class="mesgs" id="chatdiv">'+
						'<input type="hidden" id="roomNumber" value="'+roomNumber+'">'+
          				'<div class="headind_srch" id="chatdivheader">'+
            				'<div class="recent_heading">'+
              					'<h4>ChatFriends</h4>'+
            				'</div>'+
            				'<div class="srch_bar">'+
              					'<div class="stylish-input-group">'+
                					'<a type="button" onclick="chatingremovefunction();">닫기</a>'+
               					'</div>'+
            				'</div>'+
          				'</div>'+
          			'<div class="msg_history" id="msg_history">'+
          			'</div>'+
          			'<form id="messageForm" name="messageForm">'+
          				'<div class="type_msg">'+
            				'<div class="input_msg_write">'+
              					'<input type="text" class="write_msg" id="usermessage" placeholder="Type a message" />'+
              					'<a class="msg_send_btn" onclick="send()"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></a>'+
            				'</div>'+
          				'</div>'+
          			'</form>'+
        			'</div>'
				)
				dragElement(document.getElementById("chatdiv"));
				if(ws!=null) {
					ws.close();
				}
				ws=new WebSocket("ws://"+location.host+"/chating/"+roomNumber);
				wsEvt();
				var date; var myear; var mmonth; var mday; var mhour; var mminutes; var format;
				
				$.each(data, function(index, value) {
					date=new Date(value.nowTime);
					var count=value.read;
					if(count==0) {
						count="";
					}
					var s=
						leadingZeros(date.getFullYear(),4)+'-'+
						leadingZeros(date.getMonth()+1,2)+'-'+
						leadingZeros(date.getDate(),2)+' '+
						
						leadingZeros(date.getHours(),2)+':'+
						leadingZeros(date.getMinutes(),2);
					if(value.userID==sessionStorage.getItem("id")) {
						$('#msg_history').append(
							'<div class="outgoing_msg">'+
              					'<div class="sent_msg">'+
                					'<p>'+value.msg+'</p>'+
                					'<span class="time_date">'+s+'&nbsp;&nbsp;<span style="color:#dddddd;">'+count+'</span></span></div>'+
            					'</div>'						
						)
					} else {
						$('#msg_history').append(
            				'<div class="incoming_msg">'+
              					'<div class="incoming_msg_img"> <img class="rounded-circle" src="'+conpath+value.userProfile+'" alt="sunil">'+
              					'</div><span class="username">'+value.userID+'&nbsp<span style="color:#dddddd;">'+count+'</span></span>'+
              					'<div class="received_msg">'+
                					'<div class="received_withd_msg">'+
                  						'<p>'+value.msg+'</p>'+
                  						'<span class="time_date">'+s+'</span></div>'+
              						'</div>'+
            					'</div>'					
						)
					}

				});

				}
			});		
	});
}


//시간 포맷
function leadingZeros(n, digits) {
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}

function chatingremovefunction() {
	$('#chatdiv').remove();
}

function unreadfunction(id) {
	$.ajax({
		type:"POST",
		url:"/unread",
		data:{id:id},
		success:function(data) {
			if(data=="") return;
			$('#getunread').html(data);
		}
	});
}