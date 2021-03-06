
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/hotel" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>添加</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<style>
	.select2-selection{
		height:35px !important;
	}
</style>
</head>
<body>
	<div class="modal-content">
		<form id="hotelAddForm">
			<div class="modal-header" style="width:100%;z-index:10000;position:fixed;top:0;left:0;height:62px; background:#FFF;">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:100%;margin-top:62px;">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店名称(中文)：</label> <input id="name"
									name="name" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店名称(原文)：</label> <input id="namejp"
									name="namejp" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店地址(中文)：</label> <input id="address"
									name="address" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店地址(原文)：</label> <input id="addressjp"
									name="addressjp" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>电话：</label> <input id="mobile"
									name="mobile" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>所属城市：</label> 
								<select id = "cityId" name="cityId"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
								
								<!-- <input id="cityId"
									name="cityId" type="text" class="form-control input-sm"
									placeholder=" " /> -->
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>所属区域：</label> 
								
								<input id="region"
									name="region" type="text" class="form-control input-sm"
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
		src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/admin/city/customerNeeds.js"></script>
	<script src="${base}/references/common/js/select2/initSelect2.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#hotelAddForm').bootstrapValidator({
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
								message : '酒店名称(中文)不能为空'
							}
						}
					},
					namejp : {
						validators : {
							notEmpty : {
								message : '酒店名称(原文)不能为空'
							}
						}
					},
					address : {
						validators : {
							notEmpty : {
								message : '酒店地址(中文)不能为空'
							}
						}
					},
					addressjp : {
						validators : {
							notEmpty : {
								message : '酒店地址(原文)不能为空'
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
					cityId : {
						validators : {
							notEmpty : {
								message : '所属城市不能为空'
							}
						}
					},
				}
			});
			initCityNeedsSelect2();
		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		function save() {
			//初始化验证插件
			$('#hotelAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#hotelAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				var name = $("#name").val();
				if (name == "") {
					layer.msg('name不能为空');
					return;
				}
				var namejp = $("#namejp").val();
				if (namejp == "") {
					layer.msg('namejp不能为空');
					return;
				}
				var address = $("#address").val();
				if (address == "") {
					layer.msg('address不能为空');
					return;
				}
				var addressjp = $("#addressjp").val();
				if (addressjp == "") {
					layer.msg('addressjp不能为空');
					return;
				}
				var mobile = $("#mobile").val();
				if (mobile == "") {
					layer.msg('mobile不能为空');
					return;
				}
				var cityId = $("#cityId").val();
				if (cityId == "") {
					layer.msg('cityId不能为空');
					return;
				}

				$.ajax({
					type : 'POST',
					data : $("#hotelAddForm").serialize(),
					url : '${base}/admin/hotel/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("添加成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload();
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
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
