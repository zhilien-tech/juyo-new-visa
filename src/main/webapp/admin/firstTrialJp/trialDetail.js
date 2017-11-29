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

//快递
function expressFun(){
	//alert(orderid +"==="+ orderjpid);
	$.ajax({
		type : 'POST',
		data : {
			orderjpid:orderjpid
		},
		url : '/admin/firstTrialJp/isQualified.html',
		success : function(data) {
			if(data){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '550px'],
					content: '/admin/firstTrialJp/express.html?orderid='+orderid
				});
			}else{
				layer.msg('申请人不合格');
				return;
			}
		}
	});
}

//回邮信息
function getMailInfos(){
	var backMails = [];
	$('.backmail-div').each(function(i){
		var infoLength = '';
		var backInfo = {};
		
		var obmId = $(this).find('[name=obmId]').val();
		infoLength += obmId;
		backInfo.id = obmId;
		
		var datasour = $(this).find('[name=datasour]').val();
		if(datasour != 1){
			infoLength += datasour;
		}
		backInfo.datasour = datasour;
		
		var expressType = $(this).find('[name=expressType]').val();
		if(expressType != 1){
			infoLength += expressType;
		}
		backInfo.expressType = expressType;
		
		var expressAddress = $(this).find('[name=expressAddress]').val();
		infoLength += expressAddress;
		backInfo.expressAddress = expressAddress;
		
		var linkman = $(this).find('[name=linkman]').val();
		infoLength += linkman;
		backInfo.linkman = linkman;
		
		var telephone = $(this).find('[name=telephone]').val();
		infoLength += telephone;
		backInfo.telephone = telephone;
		
		var invoiceContent = $(this).find('[name=invoiceContent]').val();
		infoLength += invoiceContent;
		backInfo.invoiceContent = invoiceContent;
		
		var invoiceHead = $(this).find('[name=invoiceHead]').val();
		infoLength += invoiceHead;
		backInfo.invoiceHead = invoiceHead;
		
		var teamName = $(this).find('[name=teamName]').val();
		infoLength += teamName;
		backInfo.teamName = teamName;
		
		var expressNum = $(this).find('[name=expressNum]').val();
		infoLength += expressNum;
		backInfo.expressNum = expressNum;
		
		var taxNum = $(this).find('[name=taxNum]').val();
		infoLength += taxNum;
		backInfo.taxNum = taxNum;
		
		var remark = $(this).find('[name=remark]').val();
		infoLength += remark;
		backInfo.remark = remark;

		if(infoLength.length > 0){
			backMails.push(backInfo);
		}
	});
	
	return backMails;
}


//保存初审订单
function saveorder(){
	//绑定订单信息
	orderobj.orderinfo.number = $('#number').val();
	orderobj.orderinfo.money = $('#money').val();
	orderobj.orderinfo.gotripdate = $('#gotripdate').val();
	orderobj.orderinfo.stayday = $('#stayday').val();
	orderobj.orderinfo.backtripdate = $('#backtripdate').val();
	orderobj.orderinfo.sendvisadate = $('#sendvisadate').val();
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

	var backMails = getMailInfos();
	orderobj.orderinfo.backMailInfos = JSON.stringify(backMails);

	var editdata = orderobj.orderinfo;
	console.log("=============orderinfo=================:"+JSON.stringify(editdata));
	layer.load(1);
	$.ajax({ 
		url: '/admin/firstTrialJp/saveJpTrialDetailInfo.html',
		dataType:"json",
		data:editdata,
		type:'post',
		success: function(data){
			layer.closeAll('loading');
			window.location.reload();
		}
	}); 
}

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
var backMailInfos;
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
				orderid:orderid,
				orderjpid:orderjpid
			},
			success: function(data){
				orderobj.orderinfo = data.orderinfo;
				orderobj.applyinfo = data.applyinfo;
				backMailInfos = data.backinfo;
				
				if(backMailInfos.length>0){
					$(".addExpressInfoBtn").hide();
				}
				
				
				console.log(JSON.stringify(backMailInfos) +"====="+ JSON.stringify(backMailInfos.length));
			}
		});
	},
	methods:{
		basicInfo : function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/updateApplicant.html?id='+applyId
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
    		    content:'/admin/orderJp/passportInfo.html?id='+applyId
    	    });
		},
		visaInfo:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/visaInfo.html?id='+applyId+'&orderid='+orderid
			});
		},
		qualified:function(applyId){
			layer.confirm('您确认合格吗？', {
				btn: ['是','否'], //按钮
				shade: false //不显示遮罩
			}, function(index){
				$.ajax({
					type : 'POST',
					data : {
						applyid:applyId
					},
					url : '/admin/firstTrialJp/qualified.html',
					success : function(data) {
						layer.close(index);
						parent.successCallBack(1);
					},
					error : function(xhr) {
						layer.msg("修改失败", "", 3000);
					}
				});
			}, function(){
				//取消之后不做任何操作
			});
		},
		unqualified:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '402px'],
				content: '/admin/firstTrialJp/unqualified.html?applyid='+applyId
			});
		},
		logs:function(){//日志
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['700px', '551px'],
				content:'/admin/orderJp/log.html?id='+orderid
			});
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
				orderid:orderid,
				orderjpid:orderjpid
			},
			success: function(data){
				orderobj.applyinfo = data.applyinfo;
			}
		}); 
	}
}

//添加回邮信息 按钮  click
$(".addExpressInfoBtn").click(function(){
	$(".expressInfo").removeClass("none");
	$(this).hide();
});

//时间插件格式化
$("#gotripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
$("#backtripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
$("#sendvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
$("#outvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});

