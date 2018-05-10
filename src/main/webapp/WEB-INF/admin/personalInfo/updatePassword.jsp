<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/personalInfo" />

<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<div class="modal-header">
				<span class="heading">修改密码</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="updateBtn" type="button" onclick="save()" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
		 <form id="passwordForm" method="post">
		 	<div class="modal-body" style="height: 100%;">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>旧密码：</label> 
									<input id="oldPass" name="password" type="password" placeholder="请输入旧密码"  class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>新密码：</label> 
									<input id="newPass" name="newPass" type="password" placeholder="请输入新密码" class="form-control input-sm">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>新密码：</label> 
									<input id="repeatPass" name="repeatPass" type="password" placeholder="请再输入新密码" class="form-control input-sm">
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
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		function initvalidate() {
			//校验
			$('#passwordForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					password: {
						validators: {
							notEmpty: {
								message: '原密码不能为空'
							},
							remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/personalInfo/checkPassword.html',//验证地址
								message: '原密码不正确，请重新输入!',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										password:$('#oldPass').val()
									};
								}
							}
						}
					},
					newPass: {
						validators: {
							notEmpty: {
								message: '新密码不能为空'
							},
							
						}
					},
					repeatPass: {
						validators: {
							notEmpty: {
								message: '新密码不能为空'
							},
							remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/personalInfo/samePassword.html',//验证地址
								message: '两次输入不一致，请重新输入!',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										newPass:$('#newPass').val(),
										repeatPass:$('#repeatPass').val()
										
									};
								}
							}
						}
					}
					
				}
			});
		}

		//更新时刷新页面
		function update() {
			window.location.reload();
		}

		initvalidate();
		$('#passwordForm').bootstrapValidator('validate');
		function save() {
			$('#passwordForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#passwordForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				if ($('#newPass').val()!= $('#repeatPass').val()) {
					layer.msg('新密码两次输入不一致');
					return;
				}
				parent.layer.confirm('您确认修改密码吗？', {
					   btn: ['是','否'], //按钮
					   shade: false //不显示遮罩
					}, function(){
						$.ajax({
							type : 'POST',
							data : $("#passwordForm").serialize(),
							url : '${base}/admin/personalInfo/updatePassword.html',
							success : function(data) {
								if ("200" == data.status) {
									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							      	parent.layer.close(index);
							    	parent.layer.msg('修改成功');
									window.location.href="${base}/admin/logout.html?logintype";
								} else {
									parent.layer.msg("操作失败!"); 
								}
							},
							error : function(xhr) {
								layer.msg("修改失败", "", 3000);
							}
						});
					}, function(){
						 //取消之后不做任何操作
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

