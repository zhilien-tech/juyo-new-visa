/**
 * 登录js文件  login.js
 *
 * 2017-10-24
 */
function tab(){
	if(($(".tab-ul").find("li:eq(0)")).hasClass("active")){//短信登录
		$(".shortMessage").removeClass("none");//展示短信登录框
		$(".password").addClass("none");//隐藏密码登录框
	}else if(($(".tab-ul").find("li:eq(1)")).hasClass("active")){//密码登录
		$(".password").removeClass("none");//展示密码登录框
		$(".shortMessage").addClass("none");//隐藏短信登录框
	}
}


$(function(){
	tab();
	$(".tab-ul li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		tab();
	});
});










