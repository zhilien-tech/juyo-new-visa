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
	$('#visatype').change(function(){
		alert(12121);
		var thisval = $(this).val();
		if(thisval == 2){
			$('#visacounty').show();
			$('#threefangwen').show();
		}else{
			$('#visacounty').hide();
			$('#threefangwen').hide();
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
		$("#mySwitch").removeClass("none");//显示申请人信息列表
		$("#applicantInfo").hide();//添加申请人 按钮 隐藏
	});
	
	//添加回邮信息 按钮  click
	$(".addExpressInfoBtn").click(function(){
		$(".expressInfo").removeClass("none");
		$(this).hide();
	});
	
	//保存
	/*$("#saveOrder").click(function(){
		var customer = customerInfo;
		var order = $("#orderInfo").serialize();
		var applicants = $("#applicantInfo").serialize();
		if($(".expressInfo").hasClass("none")){
			var backmail = $("#backmailInfo").serialize();
		}
		alert(customer);
		alert(order);
		alert(applicants);
		alert(backmail);
		$.ajax({
			type:'POST',
			data : {
				customerInfo:customer,
				orderInfo:order,
				applicantInfo:applicants,
				backmailInfo:backmail
				
			},
			url:BASE_PATH + '/admin/orderJp/order',
			success:function(data){
				alert(JSON.stringify(data));
				console.log(JSON.stringify(data));
			},
			error:function(){
				alert("error");
			}
		});
	});*/
});
