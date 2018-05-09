//加载城市的select2
$('.select2City').select2({
	ajax : {
		url : "/admin/city/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

//出发航班select2
$('#goFlightNum').select2({
	ajax : {
		url : "/admin/tripairline/getTripAirlineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			//去程出发城市
			var goDepartureCity = $('#goDepartureCity').val();
			if (goDepartureCity) {
				goDepartureCity = goDepartureCity.join(',');
			}else{
				goDepartureCity = '';
			}
			//去程抵达城市
			var goArrivedCity = $('#goArrivedCity').val();
			if (goArrivedCity) {
				goArrivedCity = goArrivedCity.join(',');
			}else{
				goArrivedCity = '';
			}
			var date = $('#goDate').val();
			return {
				date:date,
				//exname : cArrivalcity,
				gocity:goDepartureCity,
				arrivecity:goArrivedCity,
				flight : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				//obj.id = obj.id; // replace pk with your identifier
				obj.id = obj.flightnum; // replace pk with your identifier
				obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//返程航班select2
$('#returnFlightNum').select2({
	ajax : {
		url : "/admin/tripairline/getTripAirlineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			/*var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}*/
			//去程出发城市
			var returnDepartureCity = $('#returnDepartureCity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			}else{
				returnDepartureCity += '';
			}
			//去程抵达城市
			var returnArrivedCity = $('#returnArrivedCity').val();
			if (returnArrivedCity) {
				returnArrivedCity = returnArrivedCity.join(',');
			}else{
				returnArrivedCity += '';
			}
			var date = $('#returnDate').val();
			return {
				//exname : cArrivalcity,
				date:date,
				gocity:returnDepartureCity,
				arrivecity:returnArrivedCity,
				flight : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				//obj.id = obj.id; // replace pk with your identifier
				obj.id = obj.flightnum; // replace pk with your identifier
				obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				/*obj.text = obj.dictCode;*/
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	//templateSelection: formatRepoSelection,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});


//去程出发城市
$("#goDepartureCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程抵达城市
	var thistext = $(this).text();
	$("#returnArrivedCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	var goArrivedCity = $('#goArrivedCity').val();
	if (goArrivedCity) {
		goArrivedCity = goArrivedCity.join(',');
	}else{
		goArrivedCity += '';
	}
	var goDate = $('#goDate').val();
	var returnDate = $('#returnDate').val();
	//查询航班接口到缓存
	initFlightByInterface(goDate,thisval,goArrivedCity);
	initFlightByInterface(returnDate,goArrivedCity,thisval);
});
$("#goDepartureCity").on("select2:unselect",function(e){
	$(this).text('');
	$("#returnArrivedCity").html('');
});
//去程抵达城市
$("#goArrivedCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程出发城市
	var thistext = $(this).text();
	$("#returnDepartureCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	var goDepartureCity = $('#goDepartureCity').val();
	if (goDepartureCity) {
		goDepartureCity = goDepartureCity.join(',');
	}else{
		goDepartureCity += '';
	}
	var goDate = $('#goDate').val();
	var returnDate = $('#returnDate').val();
	//查询航班接口到缓存
	initFlightByInterface(goDate,goDepartureCity,thisval);
	initFlightByInterface(returnDate,thisval,goDepartureCity);
});
$("#goArrivedCity").on("select2:unselect",function(e){
	$(this).text('');
	$("#returnDepartureCity").html('');
});
//返程出发城市
$("#returnDepartureCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	var returnArrivedCity = $('#returnArrivedCity').val();
	if (returnArrivedCity) {
		returnArrivedCity = returnArrivedCity.join(',');
	}else{
		returnArrivedCity += '';
	}
	var returnDate = $('#returnDate').val();
	//查询航班接口到缓存
	initFlightByInterface(returnDate,thisval,returnArrivedCity);
});
//返程抵达城市
$("#returnArrivedCity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	var returnDepartureCity = $('#returnDepartureCity').val();
	if (returnDepartureCity) {
		returnDepartureCity = returnDepartureCity.join(',');
	}else{
		returnDepartureCity += '';
	}
	var returnDate = $('#returnDate').val();
	//查询航班接口到缓存
	initFlightByInterface(returnDate,returnDepartureCity,thisval);
});

//加载航班号到缓存并同步到数据库
function initFlightByInterface(departuredate,departurecity,arrivedcity){
	$.ajax({ 
		url: '/admin/tripairline/getAirLineByInterfate.html',
		dataType:"json",
		data:{date:departuredate,gocity:departurecity,arrivecity:arrivedcity},
		type:'post',
		success: function(data){
		}
	});
}

//生成行程安排
$('.schedulingBtn').click(function(){
	var orderid = $('#orderid').val();
	var triptype = $('#triptype').val();
	var goDate = $('#goDate').val();
	var returnDate = $('#returnDate').val();
	var goDepartureCity = $('#goDepartureCity').val();
	if (goDepartureCity) {
		goDepartureCity = goDepartureCity.join(',');
	}else{
		goDepartureCity += '';
	}
	var goArrivedCity = $('#goArrivedCity').val();
	if (goArrivedCity) {
		goArrivedCity = goArrivedCity.join(',');
	}else{
		goArrivedCity += '';
	}
	var goFlightNum = $('#goFlightNum').val();
	if (goFlightNum) {
		goFlightNum = goFlightNum.join(',');
	}else{
		goFlightNum += '';
	}
	var returnDepartureCity = $('#returnDepartureCity').val();
	if (returnDepartureCity) {
		returnDepartureCity = returnDepartureCity.join(',');
	}else{
		returnDepartureCity += '';
	}
	var returnArrivedCity = $('#returnArrivedCity').val();
	if (returnArrivedCity) {
		returnArrivedCity = returnArrivedCity.join(',');
	}else{
		returnArrivedCity += '';
	}
	var returnFlightNum = $('#returnFlightNum').val();
	if (returnFlightNum) {
		returnFlightNum = returnFlightNum.join(',');
	}else{
		returnFlightNum += '';
	}
	layer.load(1);
	$.ajax({ 
		url: '/admin/simple/generateTravelPlan.html',
		dataType:"json",
		data:{
				orderid:orderid,
				triptype:triptype,
				goDate:goDate,
				returnDate:returnDate,
				goDepartureCity:goDepartureCity,
				goArrivedCity:goArrivedCity,
				goFlightNum:goFlightNum,
				returnDepartureCity:returnDepartureCity,
				returnArrivedCity:returnArrivedCity,
				returnFlightNum:returnFlightNum,
			},
		type:'post',
		success: function(data){
			if(data.status == 'success'){
				layer.msg('生成成功');
				layer.closeAll('loading');
				$('#orderid').val(data.orderid);
				//加载行程安排表格
				initTravalPlanTable(data.data);
				saveAddOrder(2);
			}else{
				layer.msg(data.message);
				layer.closeAll('loading');
			}
		},
		error:function(meg){
			layer.msg('生成失败');
			layer.closeAll('loading');
		}
	});
});
//加载行程安排表格
function initTravalPlanTable(data){
	var html = '';
	$.each(data,function(index,value){
		html += '<tr>';
		html += '<td>第'+value.day+'天</td>';
		html += '<td>'+value.outdate+'</td>';
		html += '<td>'+value.cityname+'</td>';
		if(value.scenic != undefined){
			html += '<td>'+value.scenic+'</td>';
		}else{
			html += '<td></td>';
		}
		if(value.hotelname != undefined){
			html += '<td>'+value.hotelname+'</td>';
		}else{
			html += '<td></td>';
		}
		html += '<td><i class="editHui" onclick="schedulingEdit('+value.id+')"></i><i class="resetHui" onclick="resetPlan('+value.id+')"></i></td>';
		html += '</tr>';
	});
	$('#travelplantbody').html(html);
}
//时间插件格式化  出行时间>今天>送签时间 
var now = new Date();
$("#sendVisaDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate: now,//日期小于今天
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
    $("#sendVisaDate").datetimepicker("setEndDate",$("#goDate").val()); 
}).on('changeDate', function(ev){
	var stayday = 7;
	var startDate = $("#sendVisaDate").val();
	$.ajax({ 
		url: '/admin/visaJapan/autoCalculateBackDate.html',
		dataType:"json",
		data:{gotripdate:startDate,stayday:stayday+1},
		type:'post',
		success: function(data){
			$("#outVisaDate").val(data);
			$("#goDate").datetimepicker("setStartDate",data);
		}
	});
});
$("#outVisaDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate: now,//日期小于今天
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
	$("#outVisaDate").datetimepicker("setEndDate",$("#goDate").val());  
});

$("#goDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
	var sendVisaDate = $('#sendVisaDate').val();
	if(sendVisaDate){
		var sendVisaDates = sendVisaDate.split(' - ');
		$("#goDate").datetimepicker("setStartDate",sendVisaDates[1]);  
	}else{
		$("#goDate").datetimepicker("setStartDate",now);  
	}
});
$("#returnDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
	var sendVisaDate = $('#sendVisaDate').val();
	if(sendVisaDate){
		var sendVisaDates = sendVisaDate.split(' - ');
		$("#goDate").datetimepicker("setStartDate",sendVisaDates[1]);
	}else{
		$("#goDate").datetimepicker("setStartDate",now);  
	}
});

/*$('#sendVisaDate').daterangepicker({
  	startDate:now
	},function(start, end, label) {
	
}).on("click",function(){  
	var goDate = $("#goDate").val();
	if(goDate){
		$("#sendVisaDate").data('daterangepicker').setEndDate(goDate);  
	}
}).on('apply.daterangepicker',function(ev, picker){
	var startDate = picker.startDate.format('YYYY-MM-DD');
	var endDate = picker.endDate.format('YYYY-MM-DD');
	console.log(startDate);
	console.log(endDate);
	//自动计算预计出签时间
	var stayday = 7;
	var sendvisadate = $("#sendVisaDate").val();
	$.ajax({ 
		url: '/admin/visaJapan/autoCalculateBackDate.html',
		dataType:"json",
		data:{gotripdate:startDate,stayday:stayday+1},
		type:'post',
		success: function(data){
			var outstartDate = data;
			$.ajax({ 
				url: '/admin/visaJapan/autoCalculateBackDate.html',
				dataType:"json",
				data:{gotripdate:endDate,stayday:stayday+1},
				type:'post',
				success: function(data1){
					var outendDate = data1;
					var outVisaDate = outstartDate + ' - ' + outendDate;
					$('#outVisaDate').val(outVisaDate);
				}
			});
		}
	});
});
$('#outVisaDate').daterangepicker({
	startDate:now
	},function(start, end, label) {
	
}).on("click",function(){  
	var goDate = $("#goDate").val();
	if(goDate){
		$("#outVisaDate").data('daterangepicker').setEndDate(goDate);  
	}
});*/