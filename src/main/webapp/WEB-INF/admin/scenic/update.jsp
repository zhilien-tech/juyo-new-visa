<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/scenic" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>更新</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet"
	href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>

	<div class="modal-content">
		<form id="scenicUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body" style="height:368px;">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.scenic.id}">

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>景点(中文)：</label> <input id="name"
									name="name" value="${obj.scenic.name}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>景点(原文)：</label> <input id="namejp"
									name="namejp" value="${obj.scenic.namejp}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>

					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>所属城市：</label> 
								<select id = "cityId" name="cityId"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
										<c:if test="${ !empty obj.city.id }">
										<option value="${obj.city.id }" selected="selected">${obj.city.city }</option>
										</c:if>
								</select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>所属区域：</label> 
									<input id="region"
									name="region" value="${obj.scenic.region}" type="text"
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
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
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

		function initvalidate(){
			//校验
			$('#scenicUpdateForm').bootstrapValidator({
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
									message : '景点(中文)不能为空'
								}
							}
						},
											namejp : {
							validators : {
								notEmpty : {
									message : '景点(原文)不能为空'
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
		}
		
		//更新时刷新页面
		function update() {
			window.location.reload();
		}
		
	    initvalidate();
	    initCityNeedsSelect2();
		$('#scenicUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#scenicUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#scenicUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
									var name = $("#name").val();
					if(name==""){
						layer.msg('name不能为空');
						return;
					}
									var namejp = $("#namejp").val();
					if(namejp==""){
						layer.msg('namejp不能为空');
						return;
					}
									var cityId = $("#cityId").val();
					if(cityId==""){
						layer.msg('cityId不能为空');
						return;
					}
				$.ajax({
					type : 'POST',
					data : $("#scenicUpdateForm").serialize(),
					url : '${base}/admin/scenic/update.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						layer.close(index);
						window.parent.layer.msg("编辑成功", "", 3000);
						parent.layer.close(index);
						parent.datatable.ajax.reload(null,false);
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

