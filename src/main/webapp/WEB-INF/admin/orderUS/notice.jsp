<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>销售 - 日志</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<!-- 本页样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/notice.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<style type="text/css">
	[v-cloak]{display:none;}
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:9999; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    .modal-body { background-color:#FFF !important; margin-top:50px; height:100%; } 	
    .modal-content { box-shadow:0 2px 3px rgba(255, 255, 255, 0.125); -webkit-box-shadow:0 2px 3px rgba(255, 255, 255, 0.125);-moz-box-shadow:0 2px 3px rgba(255, 255, 255, 0.125);}
	</style>
</head>
<body>
	<div class="head">
			<span>短信邮件通知</span>
			<i onclick="closeWindow()"></i>
		</div>
		<div class="section">
			<div class="shareInfo">
				<a onclick="shareFun()" class="share">分享</a>
				<span>短信通知参会人可通过邮件内的链接登录并填写所需的资料</span>
			</div>
			<div onclick="qualifiedFun()" class="qualifiedInfo">
				<a class="qualified">合格</a>
				<span>短信、邮件通知参会人资料已合格</span>
			</div>
			<div onclick="interviewFun()" class="InterviewInfo">
				<a class="Interview">通知面试</a>
				<span>短信通知面签世家你及所带资料，并发面试通知书（文件下载）</span>
			</div>
		</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var orderid = '${obj.orderid}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		var orderid = '${obj.orderid}';
		var staffid = '${obj.staffid}';
		function shareFun(){
			layer.load(1);
			$.ajax({
				url : '/admin/orderUS/sendShareMsg.html',
				data : {
					staffId : staffid,
					orderid : orderid,
					sendType : 'share'
				},
				dataType : "json",
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					console.log(data);
					if(data){
						layer.msg("发送成功");
					}
				}
			});
		}
		function qualifiedFun(){
			layer.load(1);
			$.ajax({
				url : '/admin/orderUS/sendShareMsg.html',
				data : {
					staffId : staffid,
					orderid : orderid,
					sendType : 'qualified'
				},
				dataType : "json",
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					console.log(data);
					if(data){
						layer.msg("发送成功");
					}
				}
			});
		}
		function interviewFun(){
			layer.load(1);
			$.ajax({
				url : '/admin/orderUS/sendShareMsg.html',
				data : {
					staffId : staffid,
					orderid : orderid,
					sendType : 'interview'
				},
				dataType : "json",
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					console.log(data);
					if(data){
						layer.msg("发送成功");
					}
				}
			});
		}
		function closeWindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
