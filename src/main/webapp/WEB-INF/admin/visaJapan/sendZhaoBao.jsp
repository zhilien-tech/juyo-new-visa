<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<title>发招保弹框</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<div class="modal-header">
			<span class="heading">添加</span> 
			<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
			<input id="addBtn" type="button" onclick="sendZhaobao();" class="btn btn-primary pull-right btn-sm btn-right" value="发招宝" />
			<input id="orderid" name="orderid" type="hidden" value="${obj.orderid }">
		</div>
		<div class="modal-body">
			<div class="tab-content">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<label><span>*</span>送签社：</label> 
							<select class="form-control input-sm selectHeight" id="sendsignid" name="sendsignid">
								<option value="">请选择</option>
								<c:forEach var="map" items="${obj.songqianlist}">
									<c:choose>
										<c:when test="${obj.orderjpinfo.sendsignid eq map.id }">
											<option value="${map.id}" selected="selected">${map.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${map.id}">${map.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label><span>*</span>地接社：</label> 
							<select class="form-control input-sm selectHeight" id="groundconnectid" name="groundconnectid">
								<option value="">请选择</option>
								<c:forEach var="map" items="${obj.dijielist}">
									<c:choose>
										<c:when test="${obj.orderjpinfo.groundconnectid eq map.id }">
											<option value="${map.id}" selected="selected">${map.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${map.id}">${map.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- uploadify -->
	<%-- <script src="${base}/references/public/plugins/uploadify/jquery.uploadify.min.js"></script> --%>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	//发招宝
	function sendZhaobao(){
		layer.load(1);
		var orderid = $('#orderid').val();
		var sendsignid = $('#sendsignid').val();
		var groundconnectid = $('#groundconnectid').val();
		$.ajax({ 
         	url: '${base}/admin/visaJapan/saveZhaoBao.html',
         	data:{orderId:orderid,sendsignid:sendsignid,groundconnectid:groundconnectid},
         	dataType:"json",
         	type:'post',
         	success: function(data){
           		layer.closeAll('loading');
           		window.parent.successCallBack(1);
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