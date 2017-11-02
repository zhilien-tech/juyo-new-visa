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
	//添加回邮信息 按钮  click
	$(".addExpressInfoBtn").click(function(){
		$(".expressInfo").removeClass("none");
	});
	
	//保存
	$("#saveOrder").click(function(){
		$.ajax({
			url:BASE_PATH + '/admin/orderJp/order',
			type:'post',
			data : {
				$("#customerType").val(),
				$("#compName").val()
			}
			success:function(data){
				console.log(JSON.stringify(data));
			},
			error:function(){
				alert("error");
			}
		});
	});
});