<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/city" />

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
		<form id="cityUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body"  style="height: 368px;">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.id}">

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>国家：</label> <input id="country"
									name="country" value="${obj.country}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>省/州/县：</label> <input id="province"
									name="province" value="${obj.province}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

					</div>


					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>城市：</label> <input id="city" name="city"
									value="${obj.city}" type="text" class="form-control input-sm"
									placeholder=" " />
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
			$('#cityUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					country : {
						validators : {
							notEmpty : {
								message : '国家不能为空'
							}
						}
					},
					province : {
						validators : {
							notEmpty : {
								message : '省/州/县不能为空'
							}
						}
					},
					city : {
						validators : {
							notEmpty : {
								message : '城市不能为空'
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
		$('#cityUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#cityUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#cityUpdateForm").data(
					'bootstrapValidator');
			if (bootstrapValidator.isValid()) {

				//获取必填项信息
				var country = $("#country").val();
				if (country == "") {
					layer.msg('country不能为空');
					return;
				}
				var province = $("#province").val();
				if (province == "") {
					layer.msg('province不能为空');
					return;
				}
				var city = $("#city").val();
				if (city == "") {
					layer.msg('city不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#cityUpdateForm").serialize(),
					url : '${base}/admin/city/update.html',
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

