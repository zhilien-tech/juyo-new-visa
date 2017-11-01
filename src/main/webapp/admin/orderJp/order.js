/*
 * 销售 下单页面js文件    order.js
 * @author fan
 *
 */

$(function(){
	//签证类型  按钮的点击状态
	$(".viseType-btn input").click(function(){
		if($(this).hasClass('btnState-true')){
			$(this).removeClass('btnState-true');
		}else{
			$(this).addClass('btnState-true');
			var btnInfo=$(this).val();//获取按钮的信息
			console.log(btnInfo);
		}
	});
	
	//添加申请人 按钮 click
	$(".addApplicantBtn").click(function(){
		layer.open({
			type: 2,
			title: false,
			closeBtn:false,
			fix: false,
			maxmin: false,
			shadeClose: false,
			scrollbar: false,
			area: ['900px', '551px'],
			content:'/admin/orderJp/addApplicant.html'
		});
	});
	
	//添加回邮信息 按钮  click
	$(".addExpressInfoBtn").click(function(){
		$(".expressInfo").removeClass("none");
	});
});