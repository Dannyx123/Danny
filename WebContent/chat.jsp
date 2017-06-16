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
<script type="text/javascript" src="jquery.min.js"></script>
  <script type="text/javascript">
     var username = '${sessionScope.username}' ;
     var target = "ws://192.168.2.102:8080/chat/chatSocket?username=" + username ;
     var ws ;
     window.onload = function(){
        	 if ('WebSocket' in window) {
                 ws = new WebSocket(target);
             } else if ('MozWebSocket' in window) {
                 ws = new MozWebSocket(target);
             } else {
                 alert('浏览器不支持WebSocket');
                 return;
             }
            ws.onmessage = function(event){
        	eval("var msg=" + event.data + ";") ;
        	if(undefined != msg.Welcome){
             	$("#content").append(msg.Welcome) ;
        	}
        	
        	if(undefined != msg.usernames){
        		$("#userList").html("") ;
        		$(msg.usernames).each(function(){
	        		$("#userList").append("<input type=checkbox value='"+this+"'/>"+this+"<br/>") ;
        		}) ;
        	}
        	if(undefined != content){
        		$("#content").append(msg.content) ;
        	}
        }	
     }
        function subSend(){
        	/* var ss = $("#userList input:[type='checkbox']:checked")
        	console.info(ss) ; */
        	var ss = false;
        	$('#userList input[type="checkbox"]')
        	.each(function(item) {
        		if ($(this)[0].checked) {
        			ss = $(this).val()
        		}
        	})
        	console.log(ss)
         	var msg = $("#msg").val();
         	var obj ;
         	if(!ss){
         		obj = {
    	       			 msg:msg,
    	       			 type:1 //1群聊  2私聊
    	       	    }
         	}else{
         		var to = ss;
         		obj = {
    	       			 to:to,
    	       			 msg:msg,
    	       			 type:2 //1群聊  2私聊
    	       	    }
         	}
         	var str = JSON.stringify(obj) ;
	       	 ws.send(str) ; 
	       	 $("#msg").val('') ;  
        }
  </script>
</head>
<body>
  <div id = "container" style="border:1px solid black;  width:400px ;height:400px ;float:left">
	  <div id="content" style="height:350px"> 
	  </div>
	  <div style="border-top:1px solid black ;width:400px ;height:50px">
	     <input id="msg"/><button onclick="subSend();">send</button>
	  </div>
  </div>
  
  <div style="border:1px solid black;  width:100px ;height:400px ;float:left">
    <div style=" width:100px ;height:20px ;float:left">选择私聊：</div>
  	<div id="userList" style="  width:100px ;height:380px ;float:left"></div>
  </div>
</body>
</html>










