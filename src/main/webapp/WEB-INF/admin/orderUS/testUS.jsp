<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderUS" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>US</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
	[v-cloak]{display:none;}
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:9999; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    .modal-body { background-color:#FFF !important; margin-top:50px; height:100%; } 	
    .modal-content { box-shadow:0 2px 3px rgba(255, 255, 255, 0.125); -webkit-box-shadow:0 2px 3px rgba(255, 255, 255, 0.125);-moz-box-shadow:0 2px 3px rgba(255, 255, 255, 0.125);}
	</style>
</head>
<body>
	
	<img id="vcode" alt="" src="">
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
            	  layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '100%'],
						content:'/admin/orderUS/writeVcode.html'
					});
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
