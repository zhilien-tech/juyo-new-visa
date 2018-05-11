<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>填写快递单号</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-switch.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="expressForm" >
			<div class="modal-header">
				<span class="heading">填写快递单号</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content" id="el">
					<div class="row">
						<div class="col-sm-3">
							<div class="form-group">
								<label><span>*</span>快递方式：</label>
								<select id="expressType" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.expressType}">
										<c:if test="${map.key == obj.expressInfo.expressType }">
											<option value="${map.key}" selected="selected">${map.value}</option>
										</c:if>
										<c:if test="${map.key != obj.expressInfo.expressType }">
											<option value="${map.key}">${map.value}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>快递单号：</label>
								<input id="expressNum" name="expressNum" value="${obj.expressInfo.expressNum }" type="text" class="form-control input-sm"/>
							</div>
						</div>
						<input id="applicantId" name="applicantId" value="${obj.applicantId }" type="hidden">
						<input id="orderId" name="orderId" value="${obj.orderId }" type="hidden">
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-switch.min.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	
	<script type="text/javascript">
		//保存快递单号
		function save(){
			var orderid = $("#orderId").val();
			var applyid = $("#applicantId").val();
			var layerIndex =  layer.load(1, {shade: "#000"});
			$.ajax({ 
				url: BASE_PATH+'/admin/myVisa/saveExpressInfo.html',
				type:'post',
				data:{
					expressType : $("#expressType").val(),
					expressNum : $("#expressNum").val(),
					applicantId : applyid,
					orderId : orderid
				},
				success: function(data){
					//window.parent.location.reload();
					//layer.close(layerIndex);
					parent.successCallBack(2);
					closeWindow();
				}
			});
		}
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
	
</body>
</html>
