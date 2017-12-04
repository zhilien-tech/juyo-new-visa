<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>验证</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId{position: relative;top: 10px;}
		#datatableId tbody tr{cursor: pointer;}
		.trColor{color: rgb(48, 135, 240)}
	</style>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${empty obj.telephone && empty obj.email}">  
						手机号、邮箱不能为空							  
			</c:when>
			<c:when test="${empty obj.telephone }"> 
						手机号不能为空
			</c:when>
			<c:otherwise > 
						邮箱不能为空
			</c:otherwise>
		</c:choose>
	
		
	</div>
	<div>
		<input type="hidden" value="${obj.applicantId }"/>
		<input type="button" value="马上补充" onclick="fillIn();"/>  <input  id="cancel" type="button" value="以后再说" onclick="cancelBtn();"/>
	</div>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		var applicantId = ${obj.applicantId};
		function fillIn(){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'${base}/admin/orderJp/updateApplicant.html?id='+applicantId
			});
		}
		
		function cancelBtn(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			
		}
		
		function successCallBack(status){
			if(status == 1){
				layer.msg('修改成功');
			}
			$("#cancel").click();
			parent.successCallBack(1);
			//parent.location.reload();
		}
	
	</script>

</body>
</html>
