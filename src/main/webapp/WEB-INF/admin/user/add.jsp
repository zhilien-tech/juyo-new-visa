
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/user" />

<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title>添加</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
<!-- 本页css -->
<link rel="stylesheet" href="${base}/references/common/css/user.css?v='20180510'">
</head>
<body>
	<div class="modal-content">
		<form id="userAddForm">
			<div class="modal-header">
				<span class="heading">添加</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">

					<div class="row">

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>员工姓名：</label> <input id="name" name="name"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>

						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>手机号：</label> <input id="mobile"
									name="mobile" type="text" class="form-control input-sm"
									placeholder="用户名" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>QQ：</label> <input id="qq" name="qq"
									type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>E-mail：</label> <input id="email"
									name="email" type="text" class="form-control input-sm"
									placeholder=" " />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>部门：</label> 
								<select id="departmentId" name="departmentId" class="form-control input-sm selectHeight" onchange="selectListData();">
									<option value="">--请选择--</option>
									<c:forEach var="map" items="${obj.department}">
										<option value="${map.id}">${map.deptName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>职位：</label> 
								<select id="jobId" name="jobId" class="form-control input-sm selectHeight">
									<option value="">--请选择--</option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label><span>*</span>禁用：</label> 
								<select id="isDisable" name="isDisable" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.isDisableEnum}">
										<option value="${map.key}" ${map.key==1?'selected':''}>${map.value}</option>
									</c:forEach>
								</select>
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

	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			//校验
			$('#userAddForm').bootstrapValidator({
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
								message : '员工姓名不能为空'
							}
						}
					},
					mobile : {
						validators : {
							notEmpty : {
								message : '手机号不能为空'
							},
							regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '手机号格式错误'
		                    },
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/user/checkMobile.html',
								message: '手机号已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										mobile:$('#mobile').val(),
										adminId:""
									};
								}
							}
						}
					},
					departmentId : {
						validators : {
							notEmpty : {
								message : '所属部门不能为空'
							}
						}
					},
					jobId : {
						validators : {
							notEmpty : {
								message : '用户职位不能为空'
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
				}
			});

			/*加载job*/
			$("#departmentId").change(
					function() {
						$("#jobId").empty();
						var departmentId = $("#departmentId").val();
						$.ajax({
							type : "post",
							url : BASE_PATH + "/admin/user/getJob",
							data : {
								departmentId : departmentId
							},
							success : function(data) {
								$('#jobId').append(
										"<option value=''  >" + '--请选择--'
												+ "</option>");
								for (var i = 0; i < data.length; i++) {
									$('#jobId').append(
											"<option value='" + data[i].id + "' >"
													+ data[i].jobName
													+ "</option>");
								}
							},
							error : function() {
								alert("加载职位失败");
							}
						});
					});
		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		function save() {
			//初始化验证插件
			$('#userAddForm').bootstrapValidator('validate');
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#userAddForm").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			setTimeout(function(){
			if (bootstrapValidator.isValid()) {
				//获取必填项信息
				$.ajax({
					type : 'POST',
					data : $("#userAddForm").serialize(),
					url : '${base}/admin/user/add.html',
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
		},500);
		}

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
