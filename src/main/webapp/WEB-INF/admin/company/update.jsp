<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/company" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<title>更新</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>

	<div class="modal-content">
		<form id="companyUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.company.id}">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> <input id="name" name="name"
									value="${obj.company.name}" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> <input id="shortName"
									name="shortName" value="${obj.company.shortName}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>用户名：</label> <input id="" name="" value=""
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>联系人：</label> <input id="linkman"
									name="linkman" value="${obj.company.linkman}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>电话：</label> <input id="mobile"
									name="mobile" value="${obj.company.mobile}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>邮箱：</label> <input id="email" name="email"
									value="${obj.company.email}" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>地址：</label> <input id="address"
									name="address" value="${obj.company.address}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>公司类型：</label>
								<%-- <input id="comType"
									name="comType" value="${obj.company.comType}" type="text"
									class="form-control input-sm" placeholder=" " /> --%>
								<select class="form-control input-sm inpImportant"
									name="comType" id="comType">
									<option value="">请选择</option>
									<option value="1"
										<c:if test="${'1' eq obj.company.comType}">selected</c:if>>送签社</option>
									<option value="2"
										<c:if test="${'2' eq obj.company.comType}">selected</c:if>>地接社</option>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>经营范围：</label> <input id="" name="" value=""
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>营业执照：</label> <input id="license"
									name="license" value="${obj.company.license}" type="text"
									class="form-control input-sm" placeholder=" " />
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
	<script
		src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";

		function initvalidate() {
			//校验
			$('#companyUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							notEmpty : {
								message : '公司全称不能为空'
							}
						}
					},
					shortName : {
						validators : {
							notEmpty : {
								message : '公司简称不能为空'
							}
						}
					},
					adminId : {
						validators : {
							notEmpty : {
								message : '管理员账号id不能为空'
							}
						}
					},
					linkman : {
						validators : {
							notEmpty : {
								message : '联系人不能为空'
							}
						}
					},
					mobile : {
						validators : {
							notEmpty : {
								message : '电话不能为空'
							}
						}
					},
					email : {
						validators : {
							notEmpty : {
								message : '邮箱不能为空'
							}
						}
					},
					address : {
						validators : {
							notEmpty : {
								message : '地址不能为空'
							}
						}
					},
					comType : {
						validators : {
							notEmpty : {
								message : '公司类型不能为空'
							}
						}
					},
					license : {
						validators : {
							notEmpty : {
								message : '营业执照不能为空'
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
		$('#companyUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#companyUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#companyUpdateForm").data(
					'bootstrapValidator');
			if (bootstrapValidator.isValid()) {

				//获取必填项信息
				var name = $("#name").val();
				if (name == "") {
					layer.msg('name不能为空');
					return;
				}
				var shortName = $("#shortName").val();
				if (shortName == "") {
					layer.msg('shortName不能为空');
					return;
				}
				var adminId = $("#adminId").val();
				if (adminId == "") {
					layer.msg('adminId不能为空');
					return;
				}
				var linkman = $("#linkman").val();
				if (linkman == "") {
					layer.msg('linkman不能为空');
					return;
				}
				var mobile = $("#mobile").val();
				if (mobile == "") {
					layer.msg('mobile不能为空');
					return;
				}
				var email = $("#email").val();
				if (email == "") {
					layer.msg('email不能为空');
					return;
				}
				var address = $("#address").val();
				if (address == "") {
					layer.msg('address不能为空');
					return;
				}
				var comType = $("#comType").val();
				if (comType == "") {
					layer.msg('comType不能为空');
					return;
				}
				var license = $("#license").val();
				if (license == "") {
					layer.msg('license不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#companyUpdateForm").serialize(),
					url : '${base}/admin/company/update.html',
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

