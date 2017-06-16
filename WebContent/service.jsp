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
     var custName = '${sessionScope.custName}' ;
     var target = "ws://192.168.2.102:8080/chat/serviceSocket?custName=" + custName ;
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
    		 console.log(event.data)
         	eval("var msg=" + event.data + ";") ;
         	if(undefined != msg.Welcome){
              	$("#content").append(msg.Welcome) ;
         	}
         	
//          	if(undefined != msg.usernames){
//          		$("#userList").html("") ;
//          		$(msg.usernames).each(function(){
//  	        		$("#userList").append("<input type=checkbox value='"+this+"'/>"+this+"<br/>") ;
//          		}) ;
//          	}
         	if(undefined != content){
         		$("#content").append(msg.content) ;
         	}
    	 }
     }
     function subSend(){
    	 var msg = $("#msg").val() ;
    	 console.log(msg);
    	 var obj = {
       			 msg:msg,
       	    }
    	 var str = JSON.stringify(obj) ;
       	 ws.send(str) ; 
	     $("#msg").val('') ;  
     }
  </script>
</head>
<body>
  <div id = "container" style="border:1px solid black;  width:500px ;height:600px ;float:left">
	  <div id="content" style="height:550px ; width:500px "> 
	  </div> 
	  <div style="border-top:1px solid black ;width:500px ;height:50px"> 
	     <input id="msg" style="width:450px ;"/><button onclick="subSend()">send</button>
	  </div>
  </div>
</body>
</html>










