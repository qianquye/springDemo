<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script type="text/javascript">
   var websocket = null;
   var host = document.location.host;
   
   var username = '${loginUserName}';
   var webName = '${webName}';
   
   if('WebSocket' in window){
	   websocket = new WebSocket('ws://'+host+'/'+webName+'/websocket/'+username);
   }else{
	   
   }
   
   websocket.onerror = function(){
	   setMessageInnerHTML("websocket连接发生错误");
   };
   
   websocket.onopen = function(){
	   setMessageInnerHTML("websocket连接成功");
   }
   
 //接收到消息的回调方法
   websocket.onmessage = function (event) {
       alertTip(event.data);
       // websocket.close();
       // alert("webSocket已关闭！")
   };

   //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
   window.onbeforeunload = function () {
       closeWebSocket();
   };

   //关闭WebSocket连接
   function closeWebSocket() {
       websocket.close();
   }

   //连接关闭的回调方法
   websocket.onclose = function () {
       setMessageInnerHTML("WebSocket连接关闭");
   };

   //将消息显示在网页上
   function setMessageInnerHTML(innerHTML) {
       // document.getElementById('message').innerHTML += innerHTML + '<br/>';
   }
   
 </script>
</head>
<body>
  <div id="message">
  
  </div>
</body>
</html>