<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<c:set var="url" value="${base}/admin/user" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>登录</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/login.css" />
	</head>
	<body>
		<div class="login">
			<content class="login-content">
				<ul class="tab-ul">
					<li class="active">用户登录</li>
					<li>游客登录</li>
				</ul>
				<div class="userLogin">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<input id="" name="" type="text" class="form-control login-txt" placeholder="用户账号" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">                                                                                                
							<div class="form-group">
								<input id="" name="" type="text" class="form-control login-txt" placeholder="密码" />
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
					<div class="checkbox marTop36">
						<input id="" name="" type="checkbox" class="login-check"/>记住密码
					</div>
					<div class="row">
						<div class="col-sm-12">                                                                                                
							<div class="form-group">
								<input id="" name="" type="button" class="login-btn" value="登 录"/>
							</div>
						</div>
					</div>
				</div>
				<div class="touristLogin none">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<select class="form-control login-txt login-select">
									<option>短信登录</option>
									<option>密码登录</option>
								</select>
							</div>
						</div>
					</div>
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
									<input id="" name="" type="text" class="form-control login-txt" placeholder="密码" />
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
				</div>
			</content>
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/admin/login.js"></script><!-- 本页面js文件 -->
	</body>
</html>