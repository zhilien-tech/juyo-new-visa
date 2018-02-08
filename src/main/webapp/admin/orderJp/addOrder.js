$(function(){
	customerTypeSelect2();
	$("#customerType").change(function(){
		$("#linkman2").val("");
		$("#compName2").val("");
		$("#comShortName2").val("");
		$("#mobile2").val("");
		$("#email2").val("");
		$("#payType").val("");
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
		var thisval = $(this).val();
		if(thisval == 4){
			$(".on-line").hide();//隐藏select2部分字段
			$(".zhiKe").removeClass("none");

		}else{
			$(".on-line").show();//显示select2部分字段
			$(".zhiKe").addClass("none");
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
			$('#sixCounty').removeClass("none");
			$('#threefangwen').removeClass("none");
		}else{
			$('#sixCounty').addClass("none");
			$('#threefangwen').addClass("none");
		}
	});

	$('#isVisit').change(function(){
		var thisval = $(this).val();
		if(thisval == 1){
			$('#threeCounty').removeClass("none");
		}else{
			$('#threeCounty').addClass("none");
		}
	});

	$('#urgentType').change(function(){
		var urgentType = $(this).val();
		if(urgentType != 1){
			$("#urgentDays").removeClass("none");
		}else{
			$("#urgentDays").addClass("none");
		}
	});
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

//添加申请人(大按钮)
$(".addApplicantBtn").click(function(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/addApplicantSale.html'
	});
	
});

//添加申请人(右上角小添加按钮)
function addApplicant(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/addApplicantSale.html'
	});
}

//获取申请人表格
function getApplys(){
	var appId = document.getElementById("appId").value;
	$("#applicantsTable").each(function(){
		var applicants = $(this);
		var result = '';
		$.ajax({ 
	    	url: '/admin/orderJp/getApplicant',
	    	async : false,
	    	dataType:"json",
	    	data:{applicantId:appId},
	    	type:'post',
	    	success: function(data){
	    		if( data.length > 0){
					$("#mySwitch").removeClass("none");//显示申请人信息列表
					$("#applicantInfo").hide();//添加申请人 按钮 隐藏
				for(var i = 0; i < data.length; i++){
					result += '<tr>';
					if(data[i].mainid == data[i].id){
						//为主申请人
						if(data[i].applyname != undefined){
							result += '<td><font color="blue">主   </font></td>';
							result += '<td> ' + data[i].applyname + '</td>';
						}
						else{
							result += '<td></td>';
							result += '<td></td>';
						}
					}else{
						if(data[i].applyname != undefined){
							result += '<td></td>';
							result += '<td>' + data[i].applyname + '</td>';
						}
						else{
							result += '<td></td>';
							result += '<td></td>';
						}
					}
					if(data[i].telephone != undefined){
						result += '<td>' + data[i].telephone + '</td>';
					}else{
						result += '<td></td>';
					}
					
					if(data[i].email != undefined){
						result += '<td>' + data[i].email + '</td>';
					}else{
						result += '<td></td>';
					}
					
					if(data[i].passport != undefined){
						result += '<td>' + data[i].passport + '</td>';
					}else{
						result += '<td></td>';
					}
					
					if(data[i].sex != undefined){
						result += '<td>' + data[i].sex + '</td>';
					}else{
						result += '<td></td>';
					}
					
					result += '<td> <a href="javascript:updateApplicant('+data[i].id+');">基本信息</a>&nbsp;&nbsp;<a href="javascript:passportInfo('+data[i].id+');">护照信息</a>&nbsp;&nbsp;<a href="javascript:visaInfo('+data[i].id+');">签证信息</a>&nbsp;&nbsp;<a href="javascript:visaInput('+data[i].applicantjpid+','+data[i].orderid+');">签证录入</a>&nbsp;&nbsp;<a href="javascript:backmailInfo('+data[i].id+');">回邮</a>&nbsp;&nbsp;<a href="javascript:deleteApplicant('+data[i].id+');">删除</a></td>';
					
					result += '</tr>';
				}
				applicants.html(result);
				
				}else{
					$("#mySwitch").addClass("none");//显示申请人信息列表
					$("#applicantInfo").show();//添加申请人 按钮 隐藏
				}
	    	}
		});
    });
}

//刷新申请人列表
function successCallBack(status,data){
	if(status == 1){
		layer.msg('修改成功');
		getApplys();
	}
	if(status == 2){
		layer.msg('删除成功');
		getApplys();
	}
	if(status == 3){
	    layer.msg('添加成功');
		saveAddOrder(3);
	}
	if(status == 4){
		saveAddOrder(3);
	}
}

function cancelCallBack(status){
	successCallBack(6);
}

//修改申请人基本信息
function updateApplicant(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/updateApplicant.html?id='+id+'&orderid='+'&isTrial=0'
	});
}
	
//修改申请人护照信息

function passportInfo(applicantId){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid'+'&isTrial=0'
	});
}

//签证信息
function visaInfo(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/orderJp/visaInfo.html?id='+id+'&orderid'+'&isOrderUpTime&isTrial=0'
	});
}

//签证录入
function visaInput(id,orderid){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content: '/admin/visaJapan/visaInput.html?applyid='+id+'&orderid='+orderid
	});
}

//回邮信息
function backmailInfo(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/backMailJp/backMailInfo.html?applicantId='+id+'&isAfterMarket=0&orderProcessType=1'

	});
}

//删除申请人
function deleteApplicant(id){
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
}

//下单保存
function saveAddOrder(status){
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
	
	if($("#urgentDays").hasClass("none") == true){
		$('#urgentDay').val("");
		console.log(JSON.stringify( $("#orderInfo").serialize()));
	}
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
	var orderinfo = $.param({"visacounty":visacounty, "threecounty":threecounty}) + "&" + $("#orderInfo").serialize();
	//orderinfo.backMailInfos = JSON.stringify(backMails);
	
	
	$.ajax({
		type : 'POST',
		data : orderinfo ,
		async: false,
		url : '/admin/orderJp/saveAddOrderinfo',
		success : function(data) {
			console.log(JSON.stringify(data));
			$("#orderid").val(data.id);
			if(status == 1){
				window.location.href = '/admin/orderJp/list';
			}
			if(status == 3){
				getApplys();
			}
		},
		error : function() {
			console.log("error");
		}
	}); 
}

//下单取消
function cancelAddOrder(){
	window.location.href = '/list';
}

$("#stayDay").keyup(function(){
	var go = $("#goTripDate").val();
	var back = $("#backTripDate").val();
	var day = $("#stayDay").val();
	if(go != "" && day != ""){
		var days = getNewDay(go,day-1);
		$("#backTripDate").val(days); 
		//orderobj.orderInfo.backtripdate = days;
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

//时间插件格式化  出行时间>送签时间 >今天
var now = new Date();
$("#goTripDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
    $("#goTripDate").datetimepicker("setEndDate",$("#backTripDate").val());  
}); 
$("#backTripDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});

$("#sendVisaDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate: now,//日期大于今天
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){
	$("#sendVisaDate").datetimepicker("setEndDate",getNewDaySub($("#goTripDate").val(),8));
}).on("changeDate",function(){
	//自动计算预计出签时间
	var stayday = 7;
	var sendvisadate = $("#sendVisaDate").val();
	var days = getNewDay(sendvisadate,stayday);
	$("#outVisaDate").val(days); 
});  
$("#outVisaDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
