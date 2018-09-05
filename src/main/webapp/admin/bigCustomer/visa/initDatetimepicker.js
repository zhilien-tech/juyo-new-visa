//以前的美国旅游信息---抵达日期
$("#arrivedate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
	/* $("#XXXX").datetimepicker("setEndDate",$("#arrivedate").val());  */
}); 

//以前的美国旅游信息---最后一次签证的签发日期
$("#issueddate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}); 

//家庭信息---配偶的生日
$("#spousebirthday").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});
//父亲的生日
$("#fatherbirthday").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});
//母亲的生日
$("#motherbirthday").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}); 

//工作教育信息---开始日期 
$("#workstartdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}); 
//工作教育培训信息---入职日期
$("#employstartdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});
//工作教育培训信息---离职日期
$("#employenddate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});

//工作教育培训信息---参加课程开始日期
$("#coursestartdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});
//工作教育培训信息---参加课程结束日期
$("#courseenddate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});


//工作教育培训信息---服兵役开始日期
$("#servicestartdate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});
//工作教育培训信息---服兵役结束日期
$("#serviceenddate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
});