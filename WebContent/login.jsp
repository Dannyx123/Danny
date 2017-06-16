<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery.min.js"></script>
</head>
<body>
   <form action="LoginServlet" method="post">
      昵称:<input name="username"/><br/>
      <input type="submit" value="进入群聊"/>
   </form>
   <br/><br/><br/>
   <form class="chat-room2" action="custServlet" method="post">
      姓名:<input class="xxx" name="custName"/><br/>
      <input type="submit" value="联系客服"/>
   </form>
   <script>
   		$(function() {
   			$('form.chat-room2').on('submit', function (e) {
   				e.preventDefault();
   				var name = $('.xxx').val()
   				if (!name) {
   					return;
   				}
   				$.ajax({
   					type:'post',
   					url:'custServlet',
   					data: {custName: name},
   					success: function () {
	   					window.open('/chat/test.jsp');
   					}
   				})
   			})
   		})
   </script>
</body>
</html>