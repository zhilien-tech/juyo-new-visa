<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/customer" />

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
		<form id="customerUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.customer.id}">

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>客户来源：</label> <select id="customerType"
									name="source" class="form-control input-sm inpImportant"
									onchange="selectListData();">
									<%-- <option value="${obj.sourceType }" selected="selected">${obj.sourceType }</option> --%>
									<c:forEach var="map" items="${obj.customerTypeEnum}">
										<option value="${map.key}"
											${map.key==obj.customer.source?'selected':''}>${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-8">
							<div class="form-group">
								<label><span>*</span>公司全称：</label> <input id="name" name="name"
									value="${obj.customer.name}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>公司简称：</label> <input id="shortname"
									name="shortname" value="${obj.customer.shortname}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>联系人：</label> <input id="linkman"
									name="linkman" value="${obj.customer.linkman}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>手机：</label> <input id="mobile"
									name="mobile" value="${obj.customer.mobile}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>邮箱：</label> <input id="email" name="email"
									value="${obj.customer.email}" type="text"
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
			$('#customerUpdateForm')
					.bootstrapValidator(
							{
								message : '验证不通过',
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								fields : {
									userId : {
										validators : {
											notEmpty : {
												message : '用户不能为空'
											}
										}
									},
									compId : {
										validators : {
											notEmpty : {
												message : '公司不能为空'
											}
										}
									},
									name : {
										validators : {
											notEmpty : {
												message : '公司名称不能为空'
											}
										}
									},
									shortname : {
										validators : {
											notEmpty : {
												message : '公司简称不能为空'
											}
										}
									},
									source : {
										validators : {
											notEmpty : {
												message : '客户来源不能为空'
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
												message : '手机不能为空'
											},
											regexp : {
												regexp : /^[1][34578][0-9]{9}$/,
												message : '手机号格式错误'
											}
										}
									},
									email : {
										validators : {
											notEmpty : {
												message : '邮箱不能为空'
											},
											regexp : {
												regexp : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
												message : '邮箱格式错误'
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
		$('#customerUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#customerUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customerUpdateForm").data(
					'bootstrapValidator');
			if (bootstrapValidator.isValid()) {

				//获取必填项信息
				var userId = $("#userId").val();
				if (userId == "") {
					layer.msg('userId不能为空');
					return;
				}
				var compId = $("#compId").val();
				if (compId == "") {
					layer.msg('compId不能为空');
					return;
				}
				var name = $("#name").val();
				if (name == "") {
					layer.msg('name不能为空');
					return;
				}
				var shortname = $("#shortname").val();
				if (shortname == "") {
					layer.msg('shortname不能为空');
					return;
				}
				var source = $("#customerType").val();
				if (source == "") {
					layer.msg('source不能为空');
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

				$.ajax({
					type : 'POST',
					data : $("#customerUpdateForm").serialize(),
					url : '${base}/admin/customer/update.html',
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

