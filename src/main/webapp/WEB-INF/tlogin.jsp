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
		.login { position: relative;}
		.login .login-content { position:absolute !important;right:10%;}
	</style>
</head>
<body>
	<div class="login">
			<content class="login-content" style="height:360px;">
				<ul class="tab-ul">
					<li class="active">短信登录</li>
					<li>密码登录</li>
				</ul>
				<div class="shortMessage"><!-- 短信登录 -->
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<input id="" name="" type="text" class="form-control login-txt" placeholder="手机号码" />
									<button class="phoneBtn">获取验证码</button>
								</div>
							</div>
						</div>
						<div class="row top-8">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="text" class="form-control login-txt" placeholder="验证码" />
								</div>
							</div>
						</div>
						<div class="row top-60">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="button" class="login-btn" value="登 录"/>
								</div>
							</div>
						</div>
					</div><!-- end 短信登录 -->
					<div class="password none"><!-- 密码登录 -->
						<div class="row">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="text" class="form-control login-txt" placeholder="游客账号" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="password" class="form-control login-txt" placeholder="密码" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<input id="" name="" type="text" class="form-control login-txt" placeholder="验证码" />
									<span class="verificationCode"></span>
								</div>
							</div>
						</div>
						<div class="checkbox top-30">
							<input id="" name="" type="checkbox" class="login-check"/>记住密码
						</div>
						<div class="row top-38">
							<div class="col-sm-12">                                                                                                
								<div class="form-group">
									<input id="" name="" type="button" class="login-btn" value="登 录"/>
								</div>
							</div>
						</div>
					</div><!-- end 密码登录 -->
			</content>
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/login.js"></script><!-- 本页面js文件 -->
</body>
</html>