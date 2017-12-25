<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/company" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>公司信息-编辑</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/company.css">
	<style type="text/css">
		img#sqImg {top: 0;}
		#sgImg{top:1%}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyInfoForm">
			<div class="modal-header">
				<span class="heading">编辑送签社</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="updateBtn" type="button" onclick="update();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input id="id" name="id" type="hidden" value="${obj.id }">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> 
								<input id="fullname" name="fullname" value="${obj.fullname }" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> 
								<input id="shortname" name="shortname" value="${obj.shortname }" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>指定番号：</label> 
								<input id="designatedNum" name="designatedNum" value="${obj.designatedNum }" type="text" style="text-transform:uppercase" class="form-control input-sm" placeholder="必须大写字母" />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>联系人：</label> 
								<input id="linkman" name="linkman" value="${obj.linkman }"  type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>电话：</label> 
								<input id="mobile" name="mobile" value="${obj.mobile }" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label>邮箱：</label> <input id="email" name="email" value="${obj.email }" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label>地址：</label> 
								<input id="address" name="address" value="${obj.address }" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var PAGE_TYPE = "EDIT";
		var URL_INFO = BASE_PATH+"/admin/companyInfo/update.html";
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 本页面js文件 -->
	<script src="${base}/admin/companyInfo/companyInfo.js"></script>
	<script type="text/javascript">
		/*保存页面*/
		function update() {
			//初始化验证插件
			$('#companyInfoForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#companyInfoForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					data : $("#companyInfoForm").serialize(),
					url : '${base}/admin/companyInfo/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						parent.successCallBack(2);
						parent.layer.close(index);
					},
					error : function(xhr) {
						layer.msg("修改失败");
					}
				});
			}
		}
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>

</body>
</html>