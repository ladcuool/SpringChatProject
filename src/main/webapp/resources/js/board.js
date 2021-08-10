var conpath=window.location.pathname.substring(0, window.location.pathname.indexOf("/",0));
	

function revisefunction(bId, replyGroup, replyContent) {
	$('#Users').remove();
	$('body').append(
		'<div class="container" style="width:50%" id="Users">'+
			'<div class="container">'+
				'<form action="'+conpath+'/reply_revise?bId='+bId+'&replyGroup='+replyGroup+'" method="post">'+
					'<div class="form-group">'+
	   					'<label for="exampleFormControlTextarea1"">댓글</label><span class="counter" id="counter" name="counter">(0 /  200)</span>'+
    					'<textarea class="form-control" name="TContent" id="TContent3" rows="5">'+replyContent+'</textarea>'+
					'</div>'+
	  		  		'<div class="d-flex justify-content-end" style="margin-top:10px;"><button class="btn btn-primary btn active" type="submit">수정</button></div>'+
				'</form>'+
			'</div>'+
		'</div>'
	);
	
	$('#TContent3').keyup(function (e){
    	var content = $(this).val();
    	$('#counter').html("("+content.length+" / 200)");    //글자수 실시간 카운팅

    	if (content.length > 200){
        	alert("최대 200자까지 입력 가능합니다.");
        	$(this).val(content.substring(0, 200));
        	$('#counter').html("(200 / 최대 200자)");
    	}
	});
}

function revisefunction2(bId, replyGroup, replyStep ,replyContent) {
	$('#replys').remove();
	$('#write').remove();
	
	$('body').append(
		'<div class="container" style="width:50%" id="write">'+
			'<div class="container" style="width:60%; margin-top:30px;">'+
				'<form action="'+conpath+'/reply_reply_revise?bId='+bId+'&replyGroup='+replyGroup+'&replyStep='+replyStep+'" method="post">'+
					'<div class="form-group">'+
	   					'<label for="exampleFormControlTextarea1"">답글</label><span class="counter" id="counter" name="counter">(0 /  200)</span>'+
	    				'<textarea class="form-control" name="TContent" id="TContent4" rows="5">'+replyContent+'</textarea>'+
					'</div>'+
	  			  	'<div class="d-flex justify-content-end" style="margin-top:10px;"><button class="btn btn-primary btn active" type="submit">수정</button></div>'+
				'</form>'+
			'</div>'+
		'</div>'
	);
	
	$('#TContent4').keyup(function (e){
    	var content = $(this).val();
    	$('#counter').html("("+content.length+" / 200)");    //글자수 실시간 카운팅

    	if (content.length > 200){
        	alert("최대 200자까지 입력 가능합니다.");
        	$(this).val(content.substring(0, 200));
        	$('#counter').html("(200 / 최대 200자)");
    	}
	});
}