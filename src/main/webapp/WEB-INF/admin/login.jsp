<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<c:set var="url" value="${base}/admin/user" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>用户登录</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/login.css?v='20180510'" />
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
								<input id="validateCode" name="validateCode" type="text" class="form-control login-txt" onkeypress="onkeyEnter()" placeholder="验证码" />
								<span class="verificationCode">
									<img title="看不清，点击换一张" onclick="changeValidateCode()" id="confirmCode" src="${base}/validateImage.html"/>
								</span>
							</div>
						</div>
					</div>
					<div class="checkbox">
						<input id="savepassword" name="savepassword" type="checkbox" class="" style="display: block;"/>记住密码
					</div>
					<div class="row">
						<div class="col-sm-12">                                                                                                
							<div class="form-group">
								<input id="" name="" type="button" onclick="submitlogin()" class="login-btn" value="登 录"/>
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
		<%-- <!-- iCheck -->
		<script src="${base}/references/public/plugins/iCheck/icheck.min.js"></script> --%>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script type="text/javascript">
		//解决登录页面嵌套框架问题
		if (top != window){
		    top.location.href = window.location.href; 
		}
		$(function () {
		    
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
		    //从cookie中获取用户名密码
		    var loginName = getCookie('loginName');
		    $('#loginName').val(loginName);
		    var password = getCookie('password');
		    $('#password').val(password);
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
	  function submitlogin(){
		  var ischecked = $('#savepassword').prop("checked");
		  if(ischecked){
			  var loginName = $('#loginName').val();
			  var password = $('#password').val();
			  setCookie('loginName',loginName,30);
			  setCookie('password',password,30);
		  }
		  $('#userform').submit();
	  }
	  //设置cookie
	  function setCookie(cname,cvalue,exdays){
	    var d = new Date();
	    d.setTime(d.getTime()+(exdays*24*60*60*1000));
	    var expires = "expires="+d.toGMTString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	  }
	  //获取cookie中的值
	  function getCookie(cname){
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0; i<ca.length; i++){
	      var c = ca[i].trim();
	      if (c.indexOf(name)==0) return c.substring(name.length,c.length);
	    }
	    return "";
	  }
	  
	  function onkeyEnter(){
		  var e = window.event || arguments.callee.caller.arguments[0];
	      if(e && e.keyCode == 13){
	    	  submitlogin();
		  }
	  }
	  $('#loginName').on('input',function(){
		  var loginName = getCookie('loginName');
		  var thisval = $(this).val();
		  var password = getCookie('password');
		  if(thisval == loginName){
		     $('#password').val(password);
		  }else{
			 $('#password').val('');
		  }
	  });
		</script>
	</body>
</html>