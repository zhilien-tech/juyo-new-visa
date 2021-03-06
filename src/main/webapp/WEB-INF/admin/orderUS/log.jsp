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
	<title>销售 - 日志</title>
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
	<div class="modal-content">
			<div class="modal-header">
				<span class="heading">日志</span>
				<c:if test="${obj.userType == 1 }">
					<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="关闭" /> 
				</c:if>
				<c:if test="${obj.userType != 1 }">
					<select id="principal" name="principal" class="input-sm" style="margin-left:55%;" >
						<option value="">负责人</option>
						<c:forEach var="user" items="${obj.users}">
							<c:if test="${obj.opid ==  user.id}">
								<option value="${user.id}" selected="selected">${user.name}</option>
							</c:if>
							<c:if test="${obj.opid !=  user.id}">
								<option value="${user.id}">${user.name}</option>
							</c:if>
							
						</c:forEach>
					</select>
					<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
					<input id="saveBtn" type="button" onclick="savePrincipal()" style="margin:10px 10px 0 10px;" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="保存" /> 
				</c:if>
				<input id="orderProcessType" name="orderProcessType" type="hidden" value="${obj.orderProcessType }">
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>时间</span></th>
								<th><span>状态</span></th>
								<th><span>操作人</span></th>
							</tr>
						</thead>
						<tbody >
							<tr v-cloak v-for="data in loginfo">
								<td >{{data.createtime}}</td>
								<td >{{data.orderstatus}}</td>
								<td >{{data.name}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
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
		function dataReload(){
			parent.dataReload();
		}
	</script>
</body>
</html>
