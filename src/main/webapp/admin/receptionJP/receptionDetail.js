/*
 * 签证日本 编辑   visaDetail.js
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
		var thisval = $(this).val();
		if(thisval == 2 || thisval == 3 || thisval == 4){
			$('#visacounty').show();
			$('#threefangwen').show();
		}else{
			$('#visacounty').hide();
			$('#threefangwen').hide();
		}
	});

	$('#isVisit').change(function(){
		var thisval = $(this).val();
		if(thisval == 1){
			$('#threexian').show();
		}else{
			$('#threexian').hide();
		}
	});
	
	
	
});


var url = BASE_PATH + '/admin/receptionJP/getJpVisaDetailData.html';

var orderobj;
//VUE准备数据
new Vue({
	el: '#wrapper',
	data: {orderinfo:"",
		applyinfo:""

	},
	created:function(){
		orderobj=this;
		$.ajax({ 
			url: url,
			dataType:"json",
			data:{orderid:orderid},
			type:'post',
			success: function(data){
				orderobj.orderinfo = data.orderinfo;
				orderobj.applyinfo = data.applyinfo;
				if(orderobj.orderinfo.urgenttype == 1){
					$("#urgentDays").addClass("none");
				}else{
					$("#urgentDays").removeClass("none");
				}
				$('#urgentType').change(function(){
					var thisval = $(this).val();
					if(thisval == 1){
						$("#urgentDays").addClass("none");
					}else{
						$("#urgentDays").removeClass("none");
						orderobj.orderinfo.urgentday = 1;
					}
				});

			}
		});
	},
	methods:{
		
		passport:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/receptionJP/passportInfo.html?applyId='+applyId
			});
		}
	}
});
function commitdata(){
	//绑定日期数据
	orderobj.orderinfo.gotripdate = $('#gotripdate').val();
	orderobj.orderinfo.backtripdate = $('#backtripdate').val();
	orderobj.orderinfo.sendvisadate = $('#sendvisadate').val();
	orderobj.orderinfo.number = $('#number').val();
	orderobj.orderinfo.money = $('#money').val();
	orderobj.orderinfo.stayday = $('#stayday').val();
	orderobj.orderinfo.outvisadate = $('#outvisadate').val();
	//绑定签证城市
	var visacounty = "";
	$('[name=visacounty]').each(function(){
		if($(this).hasClass('btnState-true')){
			visacounty += $(this).val() + ',';
		}
	});
	if(visacounty){
		visacounty = visacounty.substr(0,visacounty.length-1);
	}
	orderobj.orderinfo.visacounty = visacounty;
	//绑定三年城市
	var threecounty = "";
	$('[name=threecounty]').each(function(){
		if($(this).hasClass('btnState-true')){
			threecounty += $(this).val() + ',';
		}
	});
	if(threecounty){
		threecounty = threecounty.substr(0,threecounty.length-1);
	}
	orderobj.orderinfo.threecounty = threecounty;

	var editdata = orderobj.orderinfo;
	console.log("orderinfo:"+JSON.stringify(editdata));
	layer.load(1);
	$.ajax({ 
		url: BASE_PATH + '/admin/receptionJP/saveJpVisaDetailInfo',
		dataType:"json",
		data:editdata,
		type:'post',
		success: function(data){
			layer.closeAll('loading');
			window.location.reload();
		}
	}); 
}

function successCallBack(status){
	$.ajax({ 
		url: BASE_PATH + '/admin/receptionJP/getJpVisaDetailData.html',
		async: false,
		dataType:"json",
		data:{orderid:orderid},
		type:'post',
		success: function(data){
			orderobj.orderinfo = data.orderinfo;
			orderobj.applyinfo = data.applyinfo;
			$("#orStatus_p").html(data.orderinfo.status);
			if(orderobj.orderinfo.urgenttype == 1){
				$("#urgentDays").addClass("none");
			}else{
				$("#urgentDays").removeClass("none");
			}
			$('#urgentType').change(function(){
				var thisval = $(this).val();
				if(thisval == 1){
					$("#urgentDays").addClass("none");
				}else{
					$("#urgentDays").removeClass("none");
					orderobj.orderinfo.urgentday = 1;
				}
			});

		}
	});
	
	$.ajax({ 
    	url: BASE_PATH + '/admin/orderJp/getOrderStatus',
    	dataType:"json",
    	data:{orderid:orderid},
    	type:'post',
    	success: function(data){
    		$("#orStatus_p").text(data.status);
    	}
    });
	
	
	if(status == 1){
		layer.msg('修改成功<br>订单进入"我的"标签页');
	}
	if(status == 2){
		layer.msg('保存成功<br>订单进入"我的"标签页');
	}
	if(status == 3){
		layer.msg('移交签证成功<br>订单进入"我的"标签页');
	}
	if(status == 88){
		layer.msg('负责人变更成功');
	}
}


