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
	<title>验证</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId{position: relative;top: 10px;}
		#datatableId tbody tr{cursor: pointer;}
		.heading { font-size: 20px;}
		.closed {width:20px;text-align:center; display:inline-block;color:#000;float:right;}
		.closed::after { content: ""; clear:both;}
		.trColor{color: rgb(48, 135, 240)}
		.selectBtn { width:80px;height:30px;line-height:30px;text-align:center;border-radius:7px;background:#169bd5;border:0; color:#FFF;}
		.selectMargin { margin-right:5%;}
		/* .modal-body { height:220px !important;} */
		.modal-header { padding: 5px 10px !important;}
		.modal-body { height:260px !important;}
	</style>
</head>
<body>
<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">验证</span> 
				<a class="closed" onclick="cancelBtn()" style="width: 20px;">X</a>
			</div>
			<div class="modal-body">
		 		<div style="text-align:center; margin-top:15%;">
		 			<div style="font-size:16px;">
						${obj.data }
	     			</div>
					<div style="margin-top:10%;">
						<input type="button" class="selectBtn selectMargin"  value="马上补充" onclick="fillIn();"/>  <input  id="cancel" class="selectBtn" type="button" value="以后再说" onclick="cancelBtn();"/>
					</div>
		 		</div>
			</div>
		</form>
	</div>
	
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		var orderid = '${obj.orderid}';
		console.log('${obj.data}');
		function fillIn(){
			var type = '${obj.type}';
			if(type == 1){
				if('${obj.ordertype}' == 1){
					window.open('${base}/admin/simple/zhaobaoOrder.html?orderid='+orderid);
				}else{
					window.open('${base}/admin/simple/editOrder.html?orderid='+orderid);
				}
			}else{
				window.open('${base}/admin/visaJapan/visaDetail.html?orderid='+orderid);
			}
			
			cancelBtn();
		}
		function cancelBtn(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>

</body>
</html>
