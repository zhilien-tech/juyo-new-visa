<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/function" />

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
		<form id="functionUpdateForm">
			<div class="modal-header">
				<span class="heading">编辑</span> <input id="backBtn" type="button"
					onclick="closeWindow()" class="btn btn-primary pull-right btn-sm"
					data-dismiss="modal" value="取消" /> <input id="updateBtn"
					type="button" onclick="save()"
					class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.id}">

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>功能名称：</label> <input id="funName"
									name="funName" value="${obj.funName}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>上级功能：</label> <input id="parentId"
									name="parentId" value="${obj.parentId}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>功能等级：</label> <input
									id="level" name="level" value="${obj.level}" type="text"
									class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>访问地址：</label> <input id="url" name="url"
									value="${obj.url}" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>序号：</label> <input id="sort" name="sort"
									value="${obj.sort}" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>备注：</label> <input id="remark"
									name="remark" value="${obj.remark}" type="text"
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

		function initvalidate(){
			//校验
			$('#functionUpdateForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
											parentId : {
							validators : {
								notEmpty : {
									message : '上级功能id不能为空'
								}
							}
						},
											funName : {
							validators : {
								notEmpty : {
									message : '功能名称不能为空'
								}
							}
						},
											url : {
							validators : {
								notEmpty : {
									message : '访问地址不能为空'
								}
							}
						},
											level : {
							validators : {
								notEmpty : {
									message : '功能等级，是指在功能树中所处的层级不能为空'
								}
							}
						},
											createTime : {
							validators : {
								notEmpty : {
									message : '创建时间不能为空'
								}
							}
						},
											updateTime : {
							validators : {
								notEmpty : {
									message : '更新时间不能为空'
								}
							}
						},
											remark : {
							validators : {
								notEmpty : {
									message : '备注不能为空'
								}
							}
						},
											sort : {
							validators : {
								notEmpty : {
									message : '序号不能为空'
								}
							}
						},
											portrait : {
							validators : {
								notEmpty : {
									message : '菜单栏图标不能为空'
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
		$('#functionUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#functionUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#functionUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
									var parentId = $("#parentId").val();
					if(parentId==""){
						layer.msg('parentId不能为空');
						return;
					}
									var funName = $("#funName").val();
					if(funName==""){
						layer.msg('funName不能为空');
						return;
					}
									var url = $("#url").val();
					if(url==""){
						layer.msg('url不能为空');
						return;
					}
									var level = $("#level").val();
					if(level==""){
						layer.msg('level不能为空');
						return;
					}
									var createTime = $("#createTime").val();
					if(createTime==""){
						layer.msg('createTime不能为空');
						return;
					}
									var updateTime = $("#updateTime").val();
					if(updateTime==""){
						layer.msg('updateTime不能为空');
						return;
					}
									var remark = $("#remark").val();
					if(remark==""){
						layer.msg('remark不能为空');
						return;
					}
									var sort = $("#sort").val();
					if(sort==""){
						layer.msg('sort不能为空');
						return;
					}
									var portrait = $("#portrait").val();
					if(portrait==""){
						layer.msg('portrait不能为空');
						return;
					}
								
				
				$.ajax({
					type : 'POST',
					data : $("#functionUpdateForm").serialize(),
					url : '${base}/admin/function/update.html',
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

