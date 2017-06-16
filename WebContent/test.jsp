<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	#content {
		overflow: scroll;
	}
</style>
<link rel="stylesheet" type="text/css" href="css/swiper-3.4.2.min.css" />
<link rel="stylesheet" href="css/HZ.css" id="css_" />
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="font.js"></script>
</head>
<body>
  <div class="p_f custom_service bottom5 cursor right5 f20 bgc_61b4eb text_center w150 c_ffffff h50 l_h50">
		<img class="w20" src="img/mq_03.png" />客户服务
	</div>
	<div class="p_f chat h630 bottom30_ bgc_f8f2f2 right50_ m_r_330" style="z-index:99;">
		<div class="f16 bgc_61b4eb c_ffffff f18 padding3 h50 w650 bgc_55c5f5">
				<img class="w66 f_l m_l10" style="display:block;" src="img/border_Android.png" />
				<img class="f_r w20 m_t15 m_r20 close cursor" src="img/back.png" />
		</div>
		<div class="padding10 w97_ m_t5">
			<ul class="f18 h320 co_list" style="overflow-y: auto;">
			</ul>
			<div class="f16 c_989898 bgc_ffffff padding10 b_r10 cursor quick_btn">
				请选择您的问题
				<img class="up_down f_r w15 m_t5" src="img/down.png" />
			</div>
			<ul class="quick_list f16 bgc_ffffff padding10 m_t_10 h155 c_989898 b_l_r10" style="overflow-y: auto;display:none;">
				
			</ul>
			
			<div class="p_a bottom10 w97_ z_1">
				<textarea class="f20 display_block w99_ padding3 border0 question" placeholder="请输入您的问题"></textarea>
				<p class="c_red f16 text_center tip h20"></p>
				<input class="display_block cursor bgc_61b4eb border0 c_ffffff f_r f16 sent w80 h40" type="button" value="发送" />
				<div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>










