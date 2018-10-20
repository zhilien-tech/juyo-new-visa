<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderUS" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<title>US</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	
	<img id="vcode" alt="" src="http://oyu1xyxxk.bkt.clouddn.com/9ee3d197-8c26-4ca5-a768-77959e0f5be0.jpg">
	<form id="vcodeForm" onsubmit="return false;">
		<div class="col-sm-4" style="margin-top:20px;margin-left:20px;">
			<input type="text" id="vcode" name="vcode" onkeypress="onkeyEnter()">	
			<input id="backBtn" type="button" onclick="closeWindow()"  value="确定" />	
		</div>
	</form>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		
	</script>
</body>
<script>
function closeWindow(){
	var vcodeForm = $("#vcodeForm").serialize();
	$.ajax({
		async: false,
		type: 'POST',
		dataType:"json",
		data : vcodeForm,
		url: '/admin/orderUS/returnVcode.html',
		success :function(data) {
			
		}
	})
}

//回车事件
function onkeyEnter(){
    var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
    	closeWindow();
	 }
}

connectWebSocket();
function connectWebSocket(){
	 if ('WebSocket' in window){  
        console.log('Websocket supported');  
        socket = new WebSocket('ws://192.168.2.198:8080/vcodewebsocket');   

        console.log('Connection attempted');  

        socket.onopen = function(){  
             console.log('Connection open!');  
             //setConnected(true);  
         };

        socket.onclose = function(){  
            console.log('Disconnecting connection');  
        };

        socket.onmessage = function (evt){
              var received_msg = evt.data;  
              if(received_msg){
            	  document.getElementById("vcode").src=received_msg;
            	  /* layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['300px', '200px'],
						content:'/admin/orderUS/writeVcode.html'
					}); */
              }
              console.log('message received!');  
              //showMessage(received_msg);  
         };  

      } else {  
        console.log('Websocket not supported');  
      }  
}
</script>
</html>
