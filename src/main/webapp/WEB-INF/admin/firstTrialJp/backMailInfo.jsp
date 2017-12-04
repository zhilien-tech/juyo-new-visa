<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>回邮信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/css/style.css">
	
</head>
<body>
	<div class="modal-content">
		<form id="backmail_wrapper">
			<div class="modal-header">
				<span class="heading">回邮信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="info-body-from backmail-div">
					
						<div class="row body-from-input">
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>资料来源：</label> 
									<select name="source" v-model="backmailinfo.source" class="form-control input-sm">
										<c:forEach var="map" items="${obj.mainSourceTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>快递号：</label> 
									<input name="expressNum" v-model="backmailinfo.expressnum" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						
						<div class="row body-from-input">
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>团队名称：</label> 
									<input name="teamName" v-model="backmailinfo.teamname" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>回邮方式：</label> 
									<select name="expressType" v-model="backmailinfo.expresstype" class="form-control input-sm">
										<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div class="row body-from-input">
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>联系人：</label> 
									<input name="linkman" v-model="backmailinfo.linkman" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>电话：</label> 
									<input name="telephone" v-model="backmailinfo.telephone" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<div class="row body-from-input" style="padding-left:0;">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>回邮地址：</label> 
									<input name="expressAddress" v-model="backmailinfo.expressaddress" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
	
						<div class="row body-from-input">
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票项目内容：</label> 
									<input name="invoiceContent" v-model="backmailinfo.invoicecontent" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票抬头：</label> 
									<input name="invoiceHead" v-model="backmailinfo.invoiceHead" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<div class="row body-from-input" style="padding-left:0;">
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>税号：</label> 
									<input name="taxNum" v-model="backmailinfo.taxnum" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>备注：</label> 
									<input name="remark" v-model="backmailinfo.remark" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var applicantId = '${obj.applicantId}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<!-- 本页面js文件 -->
	<script src="${base}/admin/firstTrialJp/backMailInfo.js"></script>
	
</body>
</html>
