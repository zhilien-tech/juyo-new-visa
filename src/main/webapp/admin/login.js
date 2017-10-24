/**
 * 登录js文件  login.js
 *
 * 2017-10-24
 */
function tab(){
	if(($(".tab-ul").find("li:eq(0)")).hasClass("active")){//用户登录
		$(".userLogin").removeClass("none");//展示用户登录框
		$(".touristLogin").addClass("none");//隐藏游客登录框
	}else if(($(".tab-ul").find("li:eq(1)")).hasClass("active")){//游客登录
		$(".touristLogin").removeClass("none");//展示游客登录框
		$(".userLogin").addClass("none");//隐藏用户登录框
	}
}


$(function(){
	tab();
	$(".tab-ul li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		tab();
	});
	
	$(".login-select").change(function(){
		var optionTxt = $(".login-select option:selected").text();
		if(optionTxt == "短信登录"){
			$(".shortMessage").removeClass("none");//展示 短信登录部分
			$(".password").addClass("none");//隐藏 密码登录部分
		}else if(optionTxt == "密码登录"){
			$(".shortMessage").addClass("none");//展示 短信登录部分
			$(".password").removeClass("none");//隐藏 密码登录部分
		}
	});
});










