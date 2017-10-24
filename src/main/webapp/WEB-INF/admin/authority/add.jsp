
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/authority" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="authorityAddForm">
			<div class="modal-header">
				<span class="heading">添加</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消"/>
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="tab-content">
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>公司id：</label>
										<input id="comId" name="comId" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>部门名称：</label>
												<input id="deptName" name="deptName" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																												</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>创建时间：</label>
										<input id="createTime" name="createTime" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>修改时间：</label>
												<input id="updateTime" name="updateTime" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																										</div>
												
										
												
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>备注：</label>
										<input id="remark" name="remark" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>操作人id：</label>
												<input id="opId" name="opId" type="text" class="form-control input-sm" placeholder=" " />
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
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#authorityAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
				
									comId : {
						validators : {
							notEmpty : {
								message : '公司id不能为空'
							}
						}
					},
									deptName : {
						validators : {
							notEmpty : {
								message : '部门名称不能为空'
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
								message : '修改时间不能为空'
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
									opId : {
						validators : {
							notEmpty : {
								message : '操作人id不能为空'
							}
						}
					},
									
				}
			});
		});
		/* 页面初始化加载完毕 */
		
		/*保存页面*/ 
		function save() {
			//初始化验证插件
			$('#authorityAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#authorityAddForm").data('bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
									var comId = $("#comId").val();
					if(comId==""){
						layer.msg('comId不能为空');
						return;
					}
									var deptName = $("#deptName").val();
					if(deptName==""){
						layer.msg('deptName不能为空');
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
									var opId = $("#opId").val();
					if(opId==""){
						layer.msg('opId不能为空');
						return;
					}
								
				$.ajax({
					type : 'POST',
					data : $("#authorityAddForm").serialize(),
					url : '${base}/admin/authority/add.html',
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
