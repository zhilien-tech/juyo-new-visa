//出行日期  年月日
$("#gotripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
    weekStart: 1,
    todayBtn: 1,
	autoclose: true,
	todayHighlight: true,//高亮
	startView: 4,//从年开始选择
	forceParse: 0,
    showMeridian: false,
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
//返回日期
$("#backtripdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	weekStart: 1,
	todayBtn: 1,
	autoclose: true,
	todayHighlight: true,//高亮
	startView: 4,//从年开始选择
	forceParse: 0,
	showMeridian: false,
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
//送签时间
$("#sendvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	weekStart: 1,
	todayBtn: 1,
	autoclose: true,
	todayHighlight: true,//高亮
	startView: 4,//从年开始选择
	forceParse: 0,
	showMeridian: false,
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
//出签时间
$("#outvisadate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	weekStart: 1,
	todayBtn: 1,
	autoclose: true,
	todayHighlight: true,//高亮
	startView: 4,//从年开始选择
	forceParse: 0,
	showMeridian: false,
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
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

//金额保留两位小数
/*$("#money").blur(function(){
	var money = $("#money").val();
	if(money != "" ){
		var moneys = returnFloat(money);
		$("#money").val(moneys); 
	}
});*/

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

//自动计算出行日期
$("#stayday").keyup(function(){
	var gotripdate = $("#gotripdate").val();
	var backtripdate = $("#backtripdate").val();
	var stayday = $("#stayday").val();
	if(gotripdate != "" && backtripdate != ""){
		var days = getNewDay(gotripdate,stayday-1);
		$("#backtripdate").val(days); 
	}
});