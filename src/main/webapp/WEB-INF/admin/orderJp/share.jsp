<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>销售 - 分享</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId{position: relative;top: 10px;}
		#datatableId tbody tr{cursor: pointer;}
		.trColor{color: rgb(48, 135, 240)}
		[v-cloak]{display:none;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">分享</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="确定" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="row" id="orderremark">
						<div class="col-sm-4">
							<div class="form-group">
								<label>请选择分享方式：</label> 
								<select id="shareType" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.shareTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<table id="datatableId" class="table table-hover" style="width:100%;" >
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
							</tr>
						</thead>
						<tbody>
							<tr v-cloak v-for="data in shareInfo" class="tableTr">
								<td style="display: none">{{data.id}}</td>
								<td>{{data.applyname}}</td>
								<td>{{data.telephone}}</td>
								<td>{{data.email}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var orderid = '${obj.orderId}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/admin/orderJp/share.js"></script>
	
	<script type="text/javascript">
		
	</script>
</body>
</html>
