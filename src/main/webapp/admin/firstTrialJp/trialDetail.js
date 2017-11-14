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
		if(thisval == 2){
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

//签证县回显
if(visaCounty){
	var visaCountys = visaCounty.split(",");
	for (var i = 0; i < visaCountys.length; i++) {
		$('[name=visacounty]').each(function(){
			if(visaCountys[i] == $(this).val()){
				$(this).addClass('btnState-true');
			}
		});
	}
}
//三年访问县回显
if(threecounty){
	var threecountys = threecounty.split(",");
	for (var i = 0; i < threecountys.length; i++) {
		$('[name=threecounty]').each(function(){
			if(threecountys[i] == $(this).val()){
				$(this).addClass('btnState-true');
			}
		});
	}
}

var orderobj;
//VUE准备数据
//orderinfo订单信息  applyinfo申请人信息  回邮信息
new Vue({
	el: '#wrapper',
	data: {
		orderinfo:"",
		applyinfo:""

	},
	created:function(){
		orderobj=this;
		var url = '/admin/firstTrialJp/getJpTrialDetailData.html';
		$.ajax({ 
			url: url,
			type:'post',
			dataType:"json",
			data:{
				orderid:orderid
			},
			success: function(data){
				orderobj.orderinfo = data.orderinfo;
				orderobj.applyinfo = data.applyinfo;
			}
		});
	},
	methods:{
		basicInfo : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/updateApplicant.html?id='+id
			});
		},
		passport:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '550px'],
				content: '/admin/visaJapan/passportInfo.html?applyId='+applyId
			});
		},
		visaInfo:function(applyId){
			return null;
		},
		qualified:function(applyId){
			return null;
		},
		unqualified:function(applyId){
			return null;
		}
	}
});

function successCallBack(status){
	if(status == 1){
		layer.msg('修改成功');
		var url = '/admin/firstTrialJp/getJpTrialDetailData.html';
		$.ajax({ 
			url: url,
			type:'post',
			dataType:"json",
			data:{
				orderid:orderid
			},
			success: function(data){
				orderobj.applyinfo = data.applyinfo;
			}
		}); 
	}
}