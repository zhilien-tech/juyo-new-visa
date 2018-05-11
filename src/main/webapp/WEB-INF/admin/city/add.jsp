
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/city" />

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
</head>
<body>
	<div class="modal-content">
		<form id="cityAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="addBtn"
					type="button" onclick="save();"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height: 368px;">
				<div class="tab-content">

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>国家：</label> 
								<select id = "country" name="country"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
								<!-- <input id="country"
									name="country" type="text" class="form-control input-sm"
									placeholder=" " /> -->
							</div>
						</div>


					</div>


					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>省/州/县：</label>
								<!-- <select id = "province" name="province"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select> -->
								
								 <input id="province"
									name="province" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>城市：</label> 
								<!-- <select id = "city" name="city"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select> -->
								
								<input id="city" name="city"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>城市代码：</label> 
								<!-- <select id = "city" name="city"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select> -->
								
								<input id="code" name="code"
									type="text" class="form-control input-sm" placeholder=" " />
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
	<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/admin/city/customerNeeds.js"></script>
	<script src="${base}/references/common/js/select2/initSelect2.js"></script>
	<script
		src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#cityAddForm').bootstrapValidator({
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
					code : {
						validators : {
							notEmpty : {
								message : '城市代码不能为空'
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
			$('#cityAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#cityAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
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
				var code = $("#code").val();
				if (code == "") {
					layer.msg('city代码不能为空');
					return;
				}
				$.ajax({
					type : 'POST',
					data : $("#cityAddForm").serialize(),
					url : '${base}/admin/city/add.html',
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
