<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>加跟进</title>
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
	<div>
			<div class="modal-header">
				<span class="heading">加跟进</span>
					<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
					<input id="saveBtn" type="button" onclick="saveFollow('${obj.orderid}')" style="margin:10px 10px 0 10px;" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="保存" /> 
			</div>
			<div class="modal-body">
				<p><span style="color:red;">*</span>跟进内容</p>
				<context>
				<div>
				<textarea rows="20" cols="50" wrap="hard" id="content" name="content" style="width:100%;height:200px;"></textarea>
				</div>
				</context>
			</div>
	</div>

	<script type="text/javascript">
		var orderid = '${obj.orderid}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/admin/orderUS/log.js"></script><!-- 日志 js -->
	<script type="text/javascript">
		var addorder = '${obj.addorder}';
		function dataReload(){
			parent.dataReload(addorder);
		}
		function saveFollow(orderid){
			var temp = $("#content").val().replace(/[\r\n|\n]/g,"<br>");  
			$.ajax({
				url : '/admin/orderUS/saveFollow.html',
				data : {
					orderid : orderid,
					content : temp
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					console.log(data);
					parent.dataReload(addorder);
					closeWindow();
				}
			});
		}
		function textareaTo(str){
		    var reg=new RegExp("\n","g");
		    var regSpace=new RegExp(" ","g");
		    
		    str = str.replace(reg,"<br>");
		    str = str.replace(regSpace,"&nbsp;");
		    
		    return str;
		}
		
		//取消
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
