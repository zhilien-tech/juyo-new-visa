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
	var gotripdate = $('#gotripdate').val();
	var backtripdate = $('#backtripdate').val();
	if(thisval == 1){
		$('#wangfan').removeClass('none');
		$('#duocheng').addClass('none');
		//往返设置出发日期
		var goDate = $('#goDate').val();
		if(!goDate){
			$('#goDate').val(gotripdate);
		}
		//往返设置返回日期
		var returnDate = $('#returnDate').val();
		if(!returnDate){
			$('#returnDate').val(backtripdate);
		}
	}else{
		$('#wangfan').addClass('none');
		$('#duocheng').removeClass('none');
		var firstdiv = $('.duochengdiv').first().find('[name=departuredate]');
		if(!firstdiv.val()){
			firstdiv.val(gotripdate);
		}
		var lastdiv = $('.duochengdiv').last().find('[name=departuredate]');
		if(!lastdiv.val()){
			lastdiv.val(backtripdate);
		}
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
	duochengdivlast.prev().before(clonediv);
	//删除上一个div绑定的select2的事件
	offSelectEvent(clonediv.prev());
	//绑定上一个div下的select2的事件
	initSelectEvent(clonediv.prev());
	//绑定此div下的select2的事件
	initSelectEvent(clonediv);
});
//删除行程
/*$('.removIcon').click(function(){
});*/
$(document).on('click','.removeIcon',function(){
	var xingchengdivobj = $(this).parent();
	var prevdiv = xingchengdivobj.prev();
	//删除绑定的事件
	xingchengdivobj.remove();
	//绑定选中事件
	offSelectEvent(prevdiv);
	initSelectEvent(prevdiv);
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
			url : BASE_PATH + "/admin/tripairline/getTripAirlineSelect.html",
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
					obj.id = obj.flightnum; // replace pk with your identifier
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
//initSelectEvent();
$('.duochengdiv').each(function(index,name){
	var divsize = $('.duochengdiv').length;
	if(index < divsize - 1){
		initSelectEvent($(this));
	}
});
//接触绑定上一个元素的事件
function offSelectEvent(duochengdiv){
	duochengdiv.find('[name=arrivedcity]').off("select2:select");
	duochengdiv.find('[name=arrivedcity]').off("select2:unselect");
}
//初始化绑定事件
function initSelectEvent(duochengdiv){
	//console.log('index:'+index);
	var divsize = $('.duochengdiv').length;
	var nextdiv = duochengdiv.next();
	//绑定抵达城市选中事件
	duochengdiv.find('[name=arrivedcity]').on("select2:select",function(e){
		var cityvalue = $(this).val();
		if (cityvalue) {
			cityvalue = cityvalue.join(',');
		}else{
			cityvalue += '';
		}
		var citytext = $(this).text();
		nextdiv.find('[name=departurecity]').html('<option selected="selected" value="'+cityvalue+'">'+citytext+'</option>');
	});
	//清空
	duochengdiv.find('[name=arrivedcity]').on("select2:unselect",function(e){
		$(this).text('');
		nextdiv.find('[name=departurecity]').empty();
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
	$.ajax({ 
		url: '/admin/visaJapan/autoCalculateBackDate.html',
		dataType:"json",
		data:{gotripdate:sendvisadate,stayday:stayday+1},
		type:'post',
		success: function(data){
			$("#outvisadate").val(data); 
		}
	});
	//var days = getNewDay(sendvisadate,stayday);
	
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
//出行日期选中事件
$("#gotripdate").datetimepicker().on('changeDate', function(ev){
	//alert(ev.date);
	console.log(ev.target.value);
	var gotripdate = ev.target.value;
	var triptype = $('#triptype').val();
	if(triptype == 1){
		//往返设置出发日期
		$('#goDate').val(gotripdate);
	}else if(triptype == 2){
		//多程设置第一程为出行日期
		$('.duochengdiv').each(function(index){
			if(index == 0){
				$(this).find('[name=departuredate]').val(gotripdate);
			}
		});
	}
});
$("#backtripdate").datetimepicker().on('changeDate', function(ev){
	//alert(ev.date);
	console.log(ev.target.value);
	var backtripdate = ev.target.value;
	var triptype = $('#triptype').val();
	if(triptype == 1){
		//往返设置返回日期
		$('#returnDate').val(backtripdate);
	}else if(triptype == 2){
		//多程设置最后一程为返回日期
		$('.duochengdiv').each(function(index){
			if(index == ($('.duochengdiv').length-1)){
				$(this).find('[name=departuredate]').val(backtripdate);
			}
		});
	}
});