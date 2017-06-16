$(function(){

	$(".sent").click(function(){
		var question = $(this).prev().prev("textarea").val();
		if (   question==""   ) {
			$(".tip").text("请输入问题！");
		} else{
			var co = '<li class="p_b10"><div><img class="w60 b_r50 f_r" src="/img/kefu.png" /><div class="f_r b_r10 padding15 maxw500 text_left bgc_ffffff word_break">'+question+'</div><div class="clear"></div></div></li>'
			$(this).parent().prev(".co_list").append(co);
			$(".tip").text("");
			$(".co_list").scrollTop( $(".co_list")[0].scrollHeight );
		};
	});
	
});