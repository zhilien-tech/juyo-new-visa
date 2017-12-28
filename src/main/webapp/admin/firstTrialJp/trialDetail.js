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
	$.ajax({
		type : 'POST',
		data : {
			orderjpid:orderjpid
		},
		url : '/admin/firstTrialJp/getCareerStatus.html',
		success : function(data) {
			var isEmpty = data.isEmpty;
			if(isEmpty == false){
				/*layer.msg('申请人：'+data.names+' 职业未选择');*/
				var applyids = data.applyids;
				$.each(applyids, function(i, applyid) { 
					layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '551px'],
						content:'/admin/firstTrialJp/validApplicantInfo.html?applicantId='+applyid+'&orderid='+orderid,
						success : function(index, layero){
							var iframeWin = window[index.find('iframe')[0]['name']]; 
						}
					}); 
				}); 
				
				return;
			}else{
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
								area: ['900px', '80%'],
								content: '/admin/firstTrialJp/express.html?orderid='+orderid+'&orderjpid='+orderjpid
							});
						}else{
							layer.msg('申请人不合格');
							return;
						}
					}
				});
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

		var source = $(this).find('[name=source]').val();
		if(source != 1){
			infoLength += source;
		}
		backInfo.source = source;

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

				if(orderobj.orderinfo.urgenttype == 1){
					$("#urgentDays").addClass("none");
				}else{
					$("#urgentDays").removeClass("none");
				}

				if(orderobj.orderinfo.money != undefined ){
					var money = orderobj.orderinfo.money;
					if(money != "" || money != null || money != "NaN"){
						orderobj.orderinfo.money = returnFloat(money);
					}
				}

				if(backMailInfos.length>0){
					$(".addExpressInfoBtn").hide();
				}
				//console.log(JSON.stringify(backMailInfos) +"====="+ JSON.stringify(backMailInfos.length));
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
				area: ['900px', '80%'],
				content:'/admin/orderJp/updateApplicant.html?id='+applyId+'&orderid='+orderid+'&isTrial=1'
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
				area: ['900px', '80%'],
				content:'/admin/orderJp/passportInfo.html?applicantId='+applyId+'&orderid='+orderid+'&isTrial=1'
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
				area: ['900px', '80%'],
				content:'/admin/orderJp/visaInfo.html?id='+applyId+'&orderid='+orderid+'&isOrderUpTime=1&isTrial=1'
			});
		},
		backmailInfo:function(applyId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/backMailJp/backMailInfo.html?applicantId='+applyId+'&isAfterMarket=0'
			});
		},
		qualified:function(applyid){
			//判断申请人是否合格
			layer.confirm('您是确定要合格吗？', {
				btn: ['是','否'] //按钮
			}, function(){
				$.ajax({
					type : 'POST',
					data : {
						applicantId:applyid
					},
					url : '/admin/firstTrialJp/isQualifiedByApplicantId.html',
					success : function(data) {
						var isQualified = data.isQualified;
						var applicantName = data.name;
						if(isQualified){
							$.ajax({
								type : 'POST',
								data : {
									applyid:applyid,
									orderid:orderid,
									orderjpid:orderjpid
								},
								url : '/admin/firstTrialJp/qualified.html',
								success : function(data) {
									qualifiedCallBack(data);
								},
								error : function(xhr) {
									layer.msg("操作失败");
								}
							});
						}else{
							layer.msg(applicantName+" 信息不合格");
						}
					},
					error : function(xhr) {
						layer.msg("操作失败");
					}
				});
			}, function(){

			});
		},
		unqualified:function(applyId){
			/*layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '402px'],
				content: '/admin/firstTrialJp/unqualified.html?applyid='+applyId+'&orderid='+orderid
			});*/
			$.ajax({
				type : 'POST',
				data : {
					applicantId:applyId,
					orderId:orderid
				},
				url : '/admin/firstTrialJp/sendUnqualifiedMsg.html',
				success : function(data) {
					unqualifiedCallBack(data);
				},
				error : function(xhr) {
					layer.msg("操作失败");
				}
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
				area: ['700px', '80%'],
				content:'/admin/orderJp/log.html?id='+orderid
			});
		},
		urgentTypeChange:function(){
			if($('#urgentType').val() == 1){
				$("#urgentDays").addClass("none");
			}else{
				$("#urgentDays").removeClass("none");
				orderobj.orderinfo.urgentday = 1;
			}
		}

	}
});

function reloadData(){
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
			orderobj.orderinfo = data.orderinfo;
		}
	});
}

function successCallBack(status){
	if(status == 1){
		layer.msg('修改成功');
	}else if(status == 2){
		layer.msg('发送成功');
	}
	reloadData();
}

function qualifiedCallBack(username){
	layer.msg('合格 已短信邮件通知 '+username);
	reloadData();
}

function unqualifiedCallBack(username){
	layer.msg('不合格 已短信邮件通知 '+username);
	reloadData();
}


function cancelCallBack(status){

}

//添加回邮信息 按钮  click
$(".addExpressInfoBtn").click(function(){
	$(".expressInfo").removeClass("none");
	$(this).hide();
});

//时间插件格式化  出行时间>送签时间 >今天
var now = new Date();
$("#gotripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
	$("#gotripdate").datetimepicker("setEndDate",$("#backtripdate").val());  
}); 
$("#backtripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});

$("#sendvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate: now,//日期大于今天
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){
	$("#sendVisaDate").datetimepicker("setEndDate",$("#gotripdate").val());
}).on("changeDate",function(){
	//自动计算预计出签时间
	var stayday = 7;
	var sendvisadate = $("#sendvisadate").val();
	var days = getNewDay(sendvisadate,stayday);
	$("#outvisadate").val(days); 
}); 
$("#outvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});

//日期转换
function getNewDay(dateTemp, days) {  
	var dateTemp = dateTemp.split("-");  
	var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
	var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
	var rDate = new Date(millSeconds);  
	var year = rDate.getFullYear();  
	var month = rDate.getMonth() + 1;  
	if (month < 10) month = "0" + month;  
	var date = rDate.getDate();  
	if (date < 10) date = "0" + date;  
	return (year + "-" + month + "-" + date);  
}

//金额保留两位小数
$("#money").blur(function(){
	var money = $("#money").val();
	if(money != "" ){
		var moneys = returnFloat(money);
		if(moneys == "NaN.00"){
			moneys = "";
		}
		$("#money").val(moneys); 
	}
});

//数字保留两位小数
function returnFloat(value){
	var value=Math.round(parseFloat(value)*100)/100;
	var xsd=value.toString().split(".");
	if(xsd.length==1){
		value=value.toString()+".00";
		if(value == "NaN.00"){
			value = "";
		}
		return value;
	}
	if(xsd.length>1){
		if(xsd[1].length<2){
			value=value.toString()+"0";
		}
		if(value == "NaN.00"){
			value = "";
		}
		return value;
	}
}
