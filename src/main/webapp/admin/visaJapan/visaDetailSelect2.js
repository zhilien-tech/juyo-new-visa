//加载出行信息
loadtravelinfo();
function loadtravelinfo(){
	if(triptype == 1){
		$('#wangfan').removeClass('none');
		$('#duocheng').addClass('none');
	}else{
		$('#wangfan').addClass('none');
		$('#duocheng').removeClass('none');
	}
	multitripjson = JSON.parse(multitripjson);
	$('.duochengdiv').each(function(index,name){
		var thisdiv = $(this);
		//加载多程的div
		initDuochengSelect(thisdiv);
	});
}

$('#triptype').change(function(){
	var thisval = $(this).val();
	if(thisval == 1){
		$('#wangfan').removeClass('none');
		$('#duocheng').addClass('none');
	}else{
		$('#wangfan').addClass('none');
		$('#duocheng').removeClass('none');
	}
});
//添加行程
$('.addIcon').click(function(){
	var duochengdivfirst = $('.duochengdiv').first();
	var duochengdivlast = $('.duochengdiv').last();
	var clonediv = duochengdivfirst.clone(false,true);
	clonediv.find('[name=departuredate]').val('');
	clonediv.find('[name=departurecity]').next().remove();
	clonediv.find('[name=departurecity]').empty();
	clonediv.find('[name=arrivedcity]').next().remove();
	clonediv.find('[name=arrivedcity]').empty();
	clonediv.find('[name=flightnum]').next().remove();
	clonediv.find('[name=flightnum]').empty();
	var addiconobj = clonediv.find('.addIcon');
	addiconobj.addClass('removeIcon');
	addiconobj.addClass('glyphicon-minus');
	addiconobj.removeClass('addIcon');
	addiconobj.removeClass('glyphicon-plus');
	//加载多程select2插件
	initDuochengSelect(clonediv);
	duochengdivlast.after(clonediv);
	//绑定事件
	initSelectEvent();
});
//删除行程
/*$('.removIcon').click(function(){
});*/
$(document).on('click','.removeIcon',function(){
	var xingchengdivobj = $(this).parent();
	xingchengdivobj.remove();
	//绑定选中事件
	initSelectEvent();
});


//加载多程select2
function initDuochengSelect(divobj){
	divobj.find('.duochengselectcity').select2({
		ajax : {
			url : BASE_PATH + "/admin/city/getCustomerCitySelect.html",
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
	divobj.find('[name=flightnum]').select2({
		ajax : {
			url : BASE_PATH + "/admin/flight/getFlightSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				/*var cArrivalcity = $('#cArrivalcity').val();
				if(cArrivalcity){
					cArrivalcity = cArrivalcity.join(',');
				}*/
				//去程出发城市
				var goDepartureCity = divobj.find('[name=departurecity]').val();
				if (goDepartureCity) {
					goDepartureCity = goDepartureCity.join(',');
				}else{
					goDepartureCity += '';
				}
				//去程抵达城市
				var goArrivedCity = divobj.find('[name=arrivedcity]').val();
				if (goArrivedCity) {
					goArrivedCity = goArrivedCity.join(',');
				}else{
					goArrivedCity += '';
				}
				return {
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
					obj.id = obj.id; // replace pk with your identifier
					obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime; // replace pk with your identifier
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
	
	//加载日期
	divobj.find('.datetimepickertoday').datetimepicker({
		format: 'yyyy-mm-dd',
		startDate: new Date(),
		language: 'zh-CN',
		autoclose: true,//选中日期后 自动关闭
		pickerPosition:"top-left",//显示位置
		minView: "month"//只显示年月日
	});
}
//选中事件
initSelectEvent();
function initSelectEvent(){
	$('.duochengdiv').each(function(index,name){
		//console.log('index:'+index);
		var divsize = $('.duochengdiv').length;
		var nextdiv = $(this).next();
		//绑定抵达城市选中事件
		$(this).find('[name=arrivedcity]').on("select2:select",function(e){
			var cityvalue = $(this).val();
			if (cityvalue) {
				cityvalue = cityvalue.join(',');
			}else{
				cityvalue += '';
			}
			var citytext = $(this).text();
			if(index < divsize - 1){
				nextdiv.find('[name=departurecity]').html('<option selected="selected" value="'+cityvalue+'">'+citytext+'</option>');
			}
		});
		//清空
		$(this).find('[name=arrivedcity]').on("select2:unselect",function(e){
			$(this).text('');
			if(index < divsize - 1){
				nextdiv.find('[name=departurecity]').empty();
			}
		});
	});
}

/*//加载日期插件
$('.datetimepickercss').each(function(){
	$(this).datetimepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		autoclose: true,//选中日期后 自动关闭
		pickerPosition:"top-left",//显示位置
		minView: "month"//只显示年月日
	});
});
//加载日期插件
$('.datetimepickertoday').each(function(){
	$(this).datetimepicker({
		format: 'yyyy-mm-dd',
		startDate: new Date(),
		language: 'zh-CN',
		autoclose: true,//选中日期后 自动关闭
		pickerPosition:"top-left",//显示位置
		minView: "month"//只显示年月日
	});
});*/
//时间插件格式化  出行时间>今天>送签时间 
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
	endDate: now,//日期小于今天
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
}).on("click",function(){  
    $("#sendvisadate").datetimepicker("setEndDate",$("#outvisadate").val());  
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

$("#goDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});
$("#returnDate").datetimepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN',
	startDate:now,
	autoclose: true,//选中日期后 自动关闭
	pickerPosition:"top-left",//显示位置
	minView: "month"//只显示年月日
});