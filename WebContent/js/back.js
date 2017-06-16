$(function() {
	var userFlag = 1;
	var webSocket = null ;
	var url = "ws://192.168.2.102:8080/chat/testSocket2" ; //通讯地址
	init() ;
	webSocket.onopen = function(e) { //打开websocket连接
		$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/Android.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">客服后台连接成功。。。</div><div class="clear"></div></div></li>');
		webSocket.send("login") ; // 消息发送
	}
	webSocket.onclose = function(e) {//关闭websocket连接
		$(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/Android.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">客服后台退出成功。。。</div><div class="clear"></div></div></li>');
	}
	
	
	$(document).on("click",".sent",function(){
		var name = $(this).next("div").children(".name").val();
		var info = $(this).prev().prev(".answer").val();
		var chatId = $(this).next("div").children(".chatId").val();
		var content = $(this).next("div").children(".content").val();
		
		var question = $(this).prev().prev("textarea").val();
		if (   question==""   ) {
			$(".tip").text("请输入回答！");
		} else{
			$(".tip").text("");
			$(".answer").val("");
			var str = '{"name": "'+name+'","reply":"'+info+'","messageId":"'+chatId+'","content":"'+content+'"}';
			webSocket.send(str) ; // 消息发送
			console.log(str);
			$(this).parent().prev(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_r" src="img/kefu.png" /><div class="f_r b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+info+'</div><div class="clear"></div></div></li>');
			$(this).parent().prev(".co_list").scrollTop( $(".co_list")[0].scrollHeight );
			$("textarea").focus();
		};});
	webSocket.onmessage = function(e) { //websocket回应处理
		var data = JSON.parse(e.data);
		
		
		if(   data.content=="5245485247545$%^&2343--=90096833674&*^&%%#75"   ){
			$('.'+data.name+'').children(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/Android.png" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">用户已退出，是否关闭当前对话？<span class="colse_ThisChat cursor" style="color:red;"><input value="'+data.name+'" type="hidden"/>关闭</span></div><div class="clear"></div></div></li>');
		}else{
			
			console.log(data);
			
			var class_name = data.name
			
			/*判断用户是否存在*/
			if(   !$("li").hasClass(class_name)   ){
				/*不存在，新建对话窗口*/
				$(".customer_list").append('<li class=" border_b_ffffff h50 l_h50 cursor customer c_989898 '+data.name+'"><input value="'+data.name+'" type="hidden"/>客户<span class="customer_num">'+(userFlag++)+'</span></li>');
				$(".customer_chat_list").append('<div class="padding10 w98_ co_menu '+data.name+'" style="display:none;"><input class="show_name" value="'+data.name+'" type="hidden"/><ul class="f18 h345 co_list" style="overflow-y: auto;"><li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/user.jpg" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+data.content+'</div><div class="clear"></div></div></li></ul><div class="w98_"><textarea class="f20 border0 display_block w99_ padding3 answer" placeholder="请输入回复"></textarea><p class="c_red f16 text_center tip h20"></p><input class="display_block cursor bgc_61b4eb border0 c_ffffff f_r f16 sent w80 h40" type="button" value="发送" /><div><input class="name" value="'+data.name+'" type="hidden" /><input class="chatId" value="'+data.chatId+'" type="hidden" /><input class="content" value="'+data.content+'" type="hidden" /></div><div class="clear"></div></div></div>');
				$('.'+class_name+'').eq(0).addClass("bgc_fdea6b");
			}else{
				/*存在，寻找相对应的对话窗口，并添加相应对话*/
				$('.'+data.name+'').children(".co_list").append('<li class="p_b10"><div><img class="w60 b_r50 f_l" src="img/user.jpg" /><div class="f_l b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+data.content+'</div><div class="clear"></div></div></li>')
				
				if(   !$('.'+data.name+'').hasClass("bgc_f7f7f7")   ){
					$('.'+class_name+'').eq(0).addClass("bgc_fdea6b");
				};
				
				
				
			};
		}	
			
			
			$(".co_list").scrollTop( $(".co_list")[0].scrollHeight );
		
	}
	$(document).on("click",".customer",function(){
		var i=$(this).index();				
		$(".co_menu").hide();
		$(".co_menu").eq(i).show();
		$(".customer").removeClass("bgc_f7f7f7");
		$(this).addClass("bgc_f7f7f7 c_989898");
		$(this).removeClass("bgc_fdea6b");
		$("textarea").focus();
	});
	
	$(document).on("click",".colse_ThisChat",function(){
		var colse_name = $(this).children("input").val();
		$('.'+colse_name+'').remove();
	});
	
	
	
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