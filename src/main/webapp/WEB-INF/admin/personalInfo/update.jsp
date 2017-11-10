<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/personalInfo" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<title>编辑个人信息</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="updatePersonalInfoForm">
			<div class="modal-header">
				<span class="heading">编辑个人信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="updateBtn" type="button" onclick="save()" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height: 388px;">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.user.id}">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>员工姓名：</label> 
									<input id="username" name="username" value="${obj.user.name}" readonly="value" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>用户名/手机号：</label> 
									<input id="mobile" name="mobile" value="${obj.user.mobile}" readonly="value" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>QQ：</label> 
									<input id="qq" name="qq" value="${obj.user.qq}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>E-mail：</label> 
									<input id="email" name="email" value="${obj.user.email}" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>部门：</label> 
									<input id="department" name="department" value="" readonly="value" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>职位：</label> 
									<input id="job" name="job" value="" readonly="value" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		function initvalidate() {
			//校验
			$('#updatePersonalInfoForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					qq : {
						validators : {
							notEmpty : {
								message : '联系QQ不能为空'
							}
						}
					},
					email : {
						validators : {
							/* notEmpty : {
								message : '电子邮箱不能为空'
							}, */
							regexp : {
								regexp : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
								message : '电子邮箱格式错误'
							}
						}
					},
				}
			});
		}

		//更新时刷新页面
		function update() {
			window.location.reload();
		}

		initvalidate();
		$('#updatePersonalInfoForm').bootstrapValidator('validate');
		function save() {
			$('#updatePersonalInfoForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#updatePersonalInfoForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					data : $("#updatePersonalInfoForm").serialize(),
					url : '${base}/admin/personalInfo/toUpdatePersonal.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("编辑失败", "", 3000);
					}
				});
			}
		}

		//返回刷新页面 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>

