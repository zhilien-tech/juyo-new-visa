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
<body style="min-width:0 !important;">
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
						<input id="id" name="id" type="hidden" v-model="backmailinfo.id">
						<input id="applicantJPId" name="applicantJPId" type="hidden" v-model="backmailinfo.applicantjpid">
						<div class="row body-from-input">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>资料来源：</label> 
									<select id="source" name="source" v-model="backmailinfo.source" class="form-control input-sm"/>
										<c:forEach var="map" items="${obj.mainSourceTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>快递号：</label> 
									<input id="expressNum" name="expressNum" v-model="backmailinfo.expressnum" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						
						<div class="row body-from-input">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>团队名称：</label> 
									<input id="teamName" name="teamName" v-model="backmailinfo.teamname" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>回邮方式：</label> 
									<select id="expressType" name="expressType" v-model="backmailinfo.expresstype" class="form-control input-sm">
										<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>

						<div class="row body-from-input">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>联系人：</label> 
									<input id="linkman" name="linkman" v-model="backmailinfo.linkman" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>电话：</label> 
									<input id="telephone" name="telephone" v-model="backmailinfo.telephone" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<div class="row body-from-input" style="padding-left:0;">
							<div class="col-sm-12">
								<div class="form-group">
									<label><span>*</span>回邮地址：</label> 
									<input id="expressAddress" name="expressAddress" v-model="backmailinfo.expressaddress" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
	
						<div class="row body-from-input">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>发票项目内容：</label> 
									<input id="invoiceContent" name="invoiceContent" v-model="backmailinfo.invoicecontent" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>发票抬头：</label> 
									<input id="invoiceHead" name="invoiceHead" v-model="backmailinfo.invoicehead" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<div class="row body-from-input" style="padding-left:0;">
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>税号：</label> 
									<input id="taxNum" name="taxNum" v-model="backmailinfo.taxnum" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>备注：</label> 
									<input id="remark" name="remark" v-model="backmailinfo.remark" type="text" class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/admin/backMailJp/backMailInfo.js"></script>
	<script>
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
