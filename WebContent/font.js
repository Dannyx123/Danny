$(function() {
//	var username = '${sessionScope.username}' ;
	var webSocket = null ;
	var myDate = new Date();//时间实例
	var round = parseInt(100*Math.random()); 
	var name = myDate.getYear()+""+myDate.getMonth()+""+myDate.getDate()+""+myDate.getHours()+""+myDate.getMinutes()+""+myDate.getMilliseconds()+""+round;
	var url = "ws://192.168.2.102:8080/chat/testSocket"; //通讯地址
	init() ;
	webSocket.onopen = function(e) { //打开websocket连接
		$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/Android.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">欢迎使用用户服务！</div><div class="clear"></div></div></li>');
	}
	webSocket.onclose = function(e) {//关闭websocket连接
		$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/Android.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">退出交谈。。。</div><div class="clear"></div></div></li>');
	}
	$(".sent").on("click",function(){
		var question = $(".question").val();
		if (   question==""   ) {
			$(".tip").text("请输入问题！");
		} else{
			$(".tip").text("");
			var info = $(".question").val();
			$(".question").val("");
			var str = '{ "name": "'+name+'","content":"'+info+'"} ';
			webSocket.send(str) ; // 消息发送
			$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_r" src="img/user.jpg" /><div class="f_r b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+question+'</div><div class="clear"></div></div></li>');
		};
		$("textarea").focus();
	});
	webSocket.onmessage = function(e) { //websocket回应处理
		if(e.data.indexOf("messageId")!=-1){
			var data = JSON.parse(e.data);
			var messageId = data.messageId;
			if(messageId!=null && messageId!= ""){
				$.ajax({  
		            type:'post',      
		            url:'/chat/isReady',  
		            data:{
		            	messageId : messageId
		            },  
		            cache:false,  
		            success:function(data){  
//		                alert("已读提交成功");
		            }  ,
		            error:function(data){  
//		                alert("已读提交失败");
		            }  
		      });  
			}
			$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/kefu.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+data.reply+'</div><div class="clear"></div></div></li>');
			$(".co_list").scrollTop( $(".co_list")[0].scrollHeight );
		}else{
			$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/kefu.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+e.data+'</div><div class="clear"></div></div></li>');
			$(".co_list").scrollTop( $(".co_list")[0].scrollHeight );
		}
		
	}
	function init() {// 初始化操作，判断浏览器是否支持websocket通讯
		if ('WebSocket' in window) {
			webSocket = new WebSocket(url) ;
		} else {
			if ('MozWebSocket' in window) {
				webSocket = new MozWebSocket(url) ;
			} else {
				alert("此浏览器不支持websocket。。。") ;
			}
		}
	}
})