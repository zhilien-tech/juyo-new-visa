new Vue({
	el : '#wrapper',
	data : {
		customerInfo : "",
		orderInfo : "",
		applicantInfo : ""
	},
	methods : {
		//修改申请人信息
		updatePhoto : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/pcVisa/updatePhoto.html?staffid='+id
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