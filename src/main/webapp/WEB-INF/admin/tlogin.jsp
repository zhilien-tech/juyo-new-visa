<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>游客登录</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/login.css" />
	<link rel="stylesheet" href="${base}/public/dist/css/bootstrapValidator.css"/>
	<style type="text/css">
		html {height: 100%;width:100%;display: table;}
		body {width:100%;display: table-cell;height: 100%;}
	</style>
</head>
<body>
	<div class="login">
			<content class="login-content" style="height:360px;">
				<ul class="tab-ul">
					<li class="active">短信登录</li>
					<li id="passwordlogin">密码登录</li>
				</ul>
				<div class="shortMessage"><!-- 短信登录 -->
						<form action="${base }/admin/messageLogin.html" method="post">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<input id="loginMobile" name="loginName" type="text" class="form-control login-txt" placeholder="手机号码" />
									<button type="button" class="phoneBtn" onclick="getMessageCode()">获取验证码</button>
								</div>
							</div>
						</div>
						<div class="row top-8">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="validateCode" name="validateCode" type="text" class="form-control login-txt" placeholder="验证码" />
								</div>
							</div>
						</div>
						<div class="row top-60">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="submit" class="login-btn" value="登 录"/>
								</div>
							</div>
						</div>
						</form>
					</div><!-- end 短信登录 -->
					<div class="password none"><!-- 密码登录 -->
						<form action="${base}/admin/tlogin.html" method="post">
						<font color="red">${obj.errMsg}</font>
						<div class="row">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="loginName" name="loginName" type="text" class="form-control login-txt" placeholder="游客账号" />
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
						<div class="row top-38">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="submit" class="login-btn" value="登 录"/>
								</div>
							</div>
						</div>
						<input id="isTourist" name="isTourist" type="hidden" value="1">
						</form>
					</div><!-- end 密码登录 -->
			</content>
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/login.js"></script><!-- 本页面js文件 -->
		<script type="text/javascript">
			var passwordlogin = '${obj.passwordlogin}';
			if(passwordlogin == 1){
				$('#passwordlogin').trigger("click");
			}
			
			function getMessageCode(){
				var loginMobile = $('#loginMobile').val();
				
				if(loginMobile){
					var regu =/^1[3|4|5|7|8][0-9]\d{8}$/;
					var re = new RegExp(regu);
					if (re.test(loginMobile)) {
						$.ajax({ 
							url: "${base}/admin/validateMobile.html", 
							type: "POST",
							data:{mobile:loginMobile}, 
							success: function(data){
					        	if(data){
					        		sendMessage(loginMobile);
					        	}else{
					        		layer.msg('游客不存在！');
					        	}
					        }
						});
				    }else{
				       layer.msg('手机号格式错误！');
				       return;
				    }
					
				}else{
					layer.msg('请输入手机号！');
				}
			}
			function sendMessage(loginMobile){
				$.ajax({ 
					url: "${base}/admin/sendValidateCode.html", 
					type: "POST",
					data:{mobile:loginMobile}, 
					success: function(data){
						layer.msg(data);
			        }
				});
			}
		</script>
</body>
</html>