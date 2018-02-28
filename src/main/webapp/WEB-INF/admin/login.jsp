<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<c:set var="url" value="${base}/admin/user" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>用户登录</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/login.css" />
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
		<style>
			html {height: 100%;width:100%;display: table;}
			body {width:100%;height: 100%;}
			.login { position: relative;}
			.login .login-content { position:absolute !important;right:10%;}
		</style>
	</head>
	<body>
		<div class="login">
			<content class="login-content">
				<ul class="tab-ul">
					<li class="active userLoginTitle">用户登录</li>
				</ul>
				<form action="${base}/admin/login.html" method="post" id="userform" name="userform">
				<div class="userLogin">
					<font color="red">${obj.errMsg}</font>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<input id="loginName" name="loginName" type="text" class="form-control login-txt" placeholder="用户账号" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">                                                                                                
							<div class="form-group">
								<input id="password" name="password" type="password" class="form-control login-txt" placeholder="密码" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<input id="validateCode" name="validateCode" type="text" class="form-control login-txt" placeholder="验证码" />
								<span class="verificationCode">
									<img title="看不清，点击换一张" onclick="changeValidateCode()" id="confirmCode" src="${base}/validateImage.html"/>
								</span>
							</div>
						</div>
					</div>
					<div class="checkbox top-30">
							<input id="" name="" type="checkbox" class="login-check"/>记住密码
					</div>
					<div class="row">
						<div class="col-sm-12">                                                                                                
							<div class="form-group">
								<input id="" name="" type="submit" class="login-btn" value="登 录"/>
							</div>
						</div>
					</div>
				</div>
				</form>
			</content>
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/login.js"></script><!-- 本页面js文件 -->
		<!-- iCheck -->
		<script src="${base}/references/public/plugins/iCheck/icheck.min.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script type="text/javascript">
		//解决登录页面嵌套框架问题
		if (top != window){
		    top.location.href = window.location.href; 
		}
		$(function () {
		    $('input').iCheck({
		      checkboxClass: 'icheckbox_square-blue',
		      radioClass: 'iradio_square-blue',
		      increaseArea: '20%' // optional
		    });
		    
		 	// validate form
		    $("form.required-validate").each(function() {
		        var $form = $(this);
		        $form.bootstrapValidator();
		    });
		 	
		    $("input").each(function() {
		        var $input = $(this);
		        $input.blur(function(e) {
		           $("label.toolText").addClass("none");
		        });
		    });
	  });
	  
	  function validateCallback(form, callback, confirmMsg) {
		    var $form = $(form);

		    var data = $form.data('bootstrapValidator');
		    if (data) {
		    	// 修复记忆的组件不验证
		        data.validate();

		        if (!data.isValid()) {
		            return false;
		        }
		    }
		    return true;
	  }
	  
	  function changeValidateCode(){
	      var timenow = new Date().getTime(); 
	      var _obj = $("#confirmCode");
	      _obj.attr("src","${base}/validateImage.html?d="+timenow);
	  }
		</script>
	</body>
</html>