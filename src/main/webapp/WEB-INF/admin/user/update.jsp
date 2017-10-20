<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/user" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<title>更新</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	
	<div class="modal-content">
		<form id="userUpdateForm">
			<div class="modal-header">
			<span class="heading">编辑</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消"/>
				<input id="updateBtn" type="button" onclick="save()" class="btn btn-primary pull-right btn-sm btn-right" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<input name="id" type="hidden" value="${obj.id}">
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>公司id：</label>
										<input id="comId" name="comId" value="${obj.comId}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																												<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>用户姓名：</label>
												<input id="name" name="name" value="${obj.name}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																																																				</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>用户名/手机号码：</label>
										<input id="mobile" name="mobile" value="${obj.mobile}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																														<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>联系QQ：</label>
												<input id="qq" name="qq" value="${obj.qq}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																																																		</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>电子邮箱：</label>
										<input id="email" name="email" value="${obj.email}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>所属部门id：</label>
												<input id="departmentId" name="departmentId" value="${obj.departmentId}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																																																																</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>用户职位id：</label>
										<input id="jobId" name="jobId" value="${obj.jobId}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																		<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>用户是否禁用：</label>
												<input id="isDisable" name="isDisable" value="${obj.isDisable}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																																																														</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>密码：</label>
										<input id="password" name="password" value="${obj.password}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																				<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>用户类型：</label>
												<input id="userType" name="userType" value="${obj.userType}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																																																												</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>上次登陆时间：</label>
										<input id="lastLoginTime" name="lastLoginTime" value="${obj.lastLoginTime}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																						<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>创建时间：</label>
												<input id="createTime" name="createTime" value="${obj.createTime}" type="text" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
																																																										</div>
											
										
											
										
													<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>修改时间：</label>
										<input id="updateTime" name="updateTime" value="${obj.updateTime}" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
																
																																																																																																																																																																																																																																																								<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>操作人id：</label>
												<input id="opId" name="opId" value="${obj.opId}" type="text" class="form-control input-sm" placeholder=" " />
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

		function initvalidate(){
			//校验
			$('#userUpdateForm').bootstrapValidator({
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
											name : {
							validators : {
								notEmpty : {
									message : '用户姓名不能为空'
								}
							}
						},
											mobile : {
							validators : {
								notEmpty : {
									message : '用户名/手机号码不能为空'
								}
							}
						},
											qq : {
							validators : {
								notEmpty : {
									message : '联系QQ不能为空'
								}
							}
						},
											email : {
							validators : {
								notEmpty : {
									message : '电子邮箱不能为空'
								}
							}
						},
											departmentId : {
							validators : {
								notEmpty : {
									message : '所属部门id不能为空'
								}
							}
						},
											jobId : {
							validators : {
								notEmpty : {
									message : '用户职位id不能为空'
								}
							}
						},
											isDisable : {
							validators : {
								notEmpty : {
									message : '用户是否禁用不能为空'
								}
							}
						},
											password : {
							validators : {
								notEmpty : {
									message : '密码不能为空'
								}
							}
						},
											userType : {
							validators : {
								notEmpty : {
									message : '用户类型不能为空'
								}
							}
						},
											lastLoginTime : {
							validators : {
								notEmpty : {
									message : '上次登陆时间不能为空'
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
											opId : {
							validators : {
								notEmpty : {
									message : '操作人id不能为空'
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
		$('#userUpdateForm').bootstrapValidator('validate');
		function save() {
			$('#userUpdateForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#userUpdateForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
			
				//获取必填项信息
									var comId = $("#comId").val();
					if(comId==""){
						layer.msg('comId不能为空');
						return;
					}
									var name = $("#name").val();
					if(name==""){
						layer.msg('name不能为空');
						return;
					}
									var mobile = $("#mobile").val();
					if(mobile==""){
						layer.msg('mobile不能为空');
						return;
					}
									var qq = $("#qq").val();
					if(qq==""){
						layer.msg('qq不能为空');
						return;
					}
									var email = $("#email").val();
					if(email==""){
						layer.msg('email不能为空');
						return;
					}
									var departmentId = $("#departmentId").val();
					if(departmentId==""){
						layer.msg('departmentId不能为空');
						return;
					}
									var jobId = $("#jobId").val();
					if(jobId==""){
						layer.msg('jobId不能为空');
						return;
					}
									var isDisable = $("#isDisable").val();
					if(isDisable==""){
						layer.msg('isDisable不能为空');
						return;
					}
									var password = $("#password").val();
					if(password==""){
						layer.msg('password不能为空');
						return;
					}
									var userType = $("#userType").val();
					if(userType==""){
						layer.msg('userType不能为空');
						return;
					}
									var lastLoginTime = $("#lastLoginTime").val();
					if(lastLoginTime==""){
						layer.msg('lastLoginTime不能为空');
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
									var opId = $("#opId").val();
					if(opId==""){
						layer.msg('opId不能为空');
						return;
					}
								
				
				$.ajax({
					type : 'POST',
					data : $("#userUpdateForm").serialize(),
					url : '${base}/admin/user/update.html',
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

