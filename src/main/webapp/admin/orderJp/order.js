$(function(){
	//签证六县，访问三县选中状态
	if(visacounty){
		var threecountys = visacounty.split(",");
		for (var i = 0; i < threecountys.length; i++) {
			$('[name=visacounty]').each(function(){
				if(threecountys[i] == $(this).val()){
					$(this).addClass('btnState-true');
				}
			});
		}
	}

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
	
	customerTypeSelect2();
	
	var customerType = $("#customerType").val();
	if(customerType == 4){
		$(".on-line").hide();//隐藏select2部分字段
		$(".zhiKe").removeClass("none");
	}else{
		$(".on-line").show();//显示select2部分字段
		$(".zhiKe").addClass("none");
	}
});

$("#addCustomer").click(function(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['800px', '400px'],
		content: '/admin/customer/add.html?isCustomerAdd=0'
	});
});

function successAddCustomer(data){
	$(".on-line").show();//显示select2部分字段
	$(".zhiKe").addClass("none");
	$("#linkman2").val("");
	$("#compName2").val("");
	$("#comShortName2").val("");
	$("#mobile2").val("");
	$("#email2").val("");
	//客户姓名清空
	$("#linkman").val(null).trigger("change");
	//电话清空
	$("#mobile").val(null).trigger("change");
	//公司全称
	$("#compName").val(null).trigger("change");
	//公司简称
	$("#comShortName").val(null).trigger("change");
	//邮箱清空
	$("#email").val(null).trigger("change");
	$("#mobile").append('<option selected="true" value='+ data.id +'>'+data.mobile+'</option>'); 
	/*公司全称补全*/
	$("#compName").append('<option selected="true" value='+ data.id +'>'+data.name+'</option>'); 
	/*公司简称补全*/
	$("#comShortName").append('<option selected="true" value='+ data.id +'>'+data.shortname+'</option>');
	/*邮箱补全*/
	$("#email").append('<option selected="true" value='+ data.id +'>'+data.email+'</option>');
	$("#linkman").append('<option selected="true" value='+ data.id +'>'+data.linkman+'</option>');
	$("#customerType").val(data.source);
	$("#payType").val(data.payType);
}

var orderobj;
new Vue({
	el : '#wrapper',
	data : {
		customerInfo : "",
		orderInfo : "",
		applicantInfo : ""
	},
	created : function() {
		orderobj = this;
		$.ajax({
			url : '/admin/orderJp/getOrder.html',
			dataType : 'json',
			type : 'POST',
			data : {
				id : orderid
			},
			success : function(data) {
				orderobj.customerInfo = data.customerInfo;
				orderobj.orderInfo = data.orderInfo;
				orderobj.applicantInfo = data.applicantInfo;
				
				var isVisited = orderobj.orderInfo.isvisit;
				var visaType = orderobj.orderInfo.visatype;
				var mainSaleUrgentEnum = orderobj.orderInfo.urgenttype;
				var orderId = data.orderInfo.id;
				if( orderobj.applicantInfo.length <= 0){
					$("#mySwitch").addClass("none");
					$("#applicantInfo").show();
				}else{
					$("#mySwitch").removeClass("none");//显示申请人信息列表
					$("#applicantInfo").hide();//添加申请人 按钮 隐藏
				}
				
				if(orderobj.orderInfo.urgenttype == 1){
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
						orderobj.orderInfo.urgentday = 1;
					}
				});
				
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
				$('#visaType').change(function(){
					var thisval = $(this).val();
					if(thisval == 2 || thisval == 3 || thisval == 4){
						$('#visacounty').show();
						$('#threefangwen').show();
					}else{
						$('#visacounty').hide();
						$('#threefangwen').hide();
					}
				});
				
				$("#customerType").change(function(){
					$("#linkman2").val("");
					$("#compName2").val("");
					$("#comShortName2").val("");
					$("#mobile2").val("");
					$("#email2").val("");
					//客户姓名清空
					$("#linkman").val(null).trigger("change");
					//电话清空
					$("#mobile").val(null).trigger("change");
					//公司全称
					$("#compName").val(null).trigger("change");
					//公司简称
					$("#comShortName").val(null).trigger("change");
					//邮箱清空
					$("#email").val(null).trigger("change");
					$("#payType").val("");
					var thisval = $(this).val();
					if(thisval == 4){
						$(".on-line").hide();//隐藏select2部分字段
						$(".zhiKe").removeClass("none");
					}else{
						$(".on-line").show();//显示select2部分字段
						$(".zhiKe").addClass("none");
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
			},
			error : function() {
				console.log("error");
			}
		});
	},
	methods : {
		order : function() {
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
			orderobj.orderInfo.visacounty = visacounty;
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
			orderobj.orderInfo.threecounty = threecounty;
			
			//绑定日期数据
			orderobj.orderInfo.gotripdate = $('#goTripDate').val();
			orderobj.orderInfo.backtripdate = $('#backTripDate').val();
			orderobj.orderInfo.sendvisadate = $('#sendVisaDate').val();
			orderobj.orderInfo.number = $('#number').val();
			orderobj.orderInfo.money = $('#money').val();
			orderobj.orderInfo.stayday = $('#stayDay').val();
			orderobj.orderInfo.outvisadate = $('#outVisaDate').val();
			orderobj.orderInfo.paytype = $('#payType').val();
			var editdata = orderobj.orderInfo;
			
			var customerType = $("#customerType").val();
			if(customerType == 4){
				orderobj.customerInfo.linkman = $("#linkman2").val();
				orderobj.customerInfo.name = $("#compName2").val();
				orderobj.customerInfo.shortname = $("#comShortName2").val();
				orderobj.customerInfo.mobile = $("#mobile2").val();
				orderobj.customerInfo.email = $("#email2").val();
				orderobj.customerInfo.source = 4;
				editdata.customerinfo = JSON.stringify(orderobj.customerInfo);
				$.ajax({
					type : 'POST',
					data : editdata,
					url : '/admin/orderJp/order',
					success : function(data) {
						layer.closeAll('loading');
						layer.msg("保存成功", {
							time: 500,
							end: function () {
								self.location.reload();
							}
						});
						//window.location.href = '${base}/admin/orderJp/list';
					},
					error : function() {
						console.log("error");
					}
				}); 
			}else{
				var cusId = parseInt($("#linkman").val());
				$.ajax({
					async:false,
					url : 'getCustomerById',
					type : 'POST',
					data : {
						'id' : cusId
					},
					dataType:'json',
					success : function(data) {
						orderobj.customerInfo.id = data.id;
						orderobj.customerInfo.linkman = data.linkman;
						orderobj.customerInfo.name = data.name;
						orderobj.customerInfo.shortname = data.shortname;
						orderobj.customerInfo.mobile = data.mobile;
						orderobj.customerInfo.email = data.email;
						orderobj.customerInfo.source = customerType;
						editdata.customerinfo = JSON.stringify(orderobj.customerInfo);
						$.ajax({
							async:false,
							type : 'POST',
							data : editdata,
							url : '/admin/orderJp/order',
							success : function(data) {
								layer.closeAll('loading');
								layer.msg("保存成功", {
									time: 500,
									end: function () {
										self.location.reload();
									}
								});
								//window.location.href = '${base}/admin/orderJp/list';
							},
							error : function() {
								console.log("error");
							}
						}); 
					},
					error : function() {
					}
				});
			}
		},
		addCustomer : function(){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '400px'],
				content: '/admin/customer/add.html?isCustomerAdd=0'
			});
		},
		
		//修改申请人信息
		updateApplicant : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/updateApplicant.html?id='+id+'&orderid&isTrial=0&orderProcessType=1'
			});
		},
		//修改护照信息
		passport : function(applicantId,orderid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial=0&orderProcessType=1'
			});
		},
		//删除申请人
		deleteApplicant : function(id){
			layer.confirm("您确认要删除吗？", {
				title:"删除",
				btn: ["是","否"], //按钮
				shade: false //不显示遮罩
			}, function(){
			$.ajax({ 
		    	url: '/admin/orderJp/deleteApplicant',
		    	dataType:"json",
		    	data:{applicantId:id},
		    	type:'post',
		    	success: function(data){
		    		successCallBack(2);
		      	}
		    }); 
			});
		},
		share:function(id){//分享
			//window.location.href = '/admin/orderJp/share.html?id='+id;
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/share.html?id='+id
			});
		},
		log:function(id){//日志
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['700px', '80%'],
				content:'/admin/orderJp/log.html?id='+id+'&orderProcessType=1'
			});
		},
		//签证录入
		visaInput : function (applicantId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['1000px', '80%'],
				content: '/admin/visaJapan/visaInput.html?applyid='+applicantId+'&orderid='+orderJpId
			});
		},
		//签证信息
		visa : function(id,orderid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/visaInfo.html?id='+id+'&orderid='+orderid+'&isOrderUpTime&isTrial=0&orderProcessType=1'
			});
		},
		//回邮信息
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
				content:'/admin/backMailJp/backMailInfo.html?applicantId='+applyId+'&isAfterMarket=0&orderProcessType=1'

			});
		},
		//初审按钮
		firtTrialJp : function(id){
			layer.load(1);
			$.ajax({ 
		    	url: '/admin/orderJp/firtTrialJp',
		    	dataType:"json",
		    	data:{orderId:id},
		    	type:'post',
		    	success: function(data){
		    		layer.closeAll('loading');
		    		layer.msg('进入初审');
		    		successCallBack(7);
		      	}
		    }); 
		}
	}
});

//添加申请人
function cancelCallBack(status){
	successCallBack(6);
}
//刷新申请人表格
function successCallBack(status){
	if(status == 1){
		layer.msg('修改成功');
	}
	if(status == 2){
		layer.msg('删除成功');
	}
	if(status == 3){
		layer.msg('添加成功');
	}
	if(status == 88){
		layer.msg('负责人变更成功');
	}

	$.ajax({ 
		url: '/admin/orderJp/getOrderStatus',
		dataType:"json",
		data:{orderid:orderid},
		type:'post',
		success: function(data){
			$("#spanStatus").text(data.status);
		}
	}); 

	$.ajax({ 
		url: '/admin/orderJp/getEditApplicant',
		dataType:"json",
		data:{orderid:orderid},
		type:'post',
		success: function(data){
			if(data.length <= 0 || data.length == undefined){
				$("#mySwitch").addClass("none");//显示申请人信息列表
				$("#applicantInfo").show();//添加申请人 按钮 隐藏
			}else{
				$("#mySwitch").removeClass("none");
				$("#applicantInfo").hide();
				orderobj.applicantInfo = data;
			}
		}
	}); 
}

//添加申请人
function addApplicant(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/addApplicantSale.html?id='+id
	});
}

//根据形成天数自动计算返回时间
$("#stayDay").keyup(function(){
	var go = $("#goTripDate").val();
	var back = $("#backTripDate").val();
	var day = $("#stayDay").val();
	if(go != "" && day != ""){
		var days = getNewDay(go,day-1);
		$("#backTripDate").val(days); 
		orderobj.orderInfo.backtripdate = days;
	}
});
//根据送签时间自动加7天计算出签时间
$("#sendVisaDate").keyup(function(){
	var stayday = 7;
	var sendvisadate = $("#sendVisaDate").val();
	if(sendvisadate.length == 10){
		var days = getNewDay(sendvisadate,stayday);
		$("#outVisaDate").val(days); 
		orderobj.orderInfo.outvisadate = days;
	}
});
//日期转换 加上指定天数
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
//减去指定天数
function getNewDaySub(dateTemp, days) {  
    var dateTemp = dateTemp.split("-");  
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
    var millSeconds = Math.abs(nDate) - (days * 24 * 60 * 60 * 1000);  
    var rDate = new Date(millSeconds);  
    var year = rDate.getFullYear();  
    var month = rDate.getMonth() + 1;  
    if (month < 10) month = "0" + month;  
    var date = rDate.getDate();  
    if (date < 10) date = "0" + date;  
    return (year + "-" + month + "-" + date);  
}  

$("#money").blur(function(){
	var money = $("#money").val();
	if(money != "" ){
		var moneys = returnFloat(money);
		$("#money").val(moneys); 
	}
});
//数字保留两位小数
function returnFloat(value){
	var value=Math.round(parseFloat(value)*100)/100;
	var xsd=value.toString().split(".");
	if(xsd.length==1){
		value=value.toString()+".00";
	 	return value;
	}
	if(xsd.length>1){
		if(xsd[1].length<2){
	  		value=value.toString()+"0";
	 	}
	 	return value;
	 }
}

function cancel(){
	self.window.close();
	parent.window.reload();
}