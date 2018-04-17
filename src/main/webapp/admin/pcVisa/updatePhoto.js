new Vue({
	el : '#wrapper',
	data : {
		customerInfo : "",
		orderInfo : "",
		applicantInfo : ""
	},
	methods : {
		visa : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/bigCustomer/updateVisaInfo.html?staffId='+id+'&isDisable'
			});
		},
		//修改申请人拍摄资料信息
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
				content:'/admin/pcVisa/updatePhoto.html?staffid='+id+'&isDisable'
			});
			this.$emit('updatePhoto');
		},
		//基本信息
		baseInfo : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/bigCustomer/updateBaseInfo.html?staffId='+id+'&isDisable&flag=1'
			});
		},
		//护照信息
		passport : function(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/bigCustomer/updatePassportInfo.html?passportId='+id+'&isDisable'
			});
		}
		
		
	},
	
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

