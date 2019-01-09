//加载城市的select2
$('#newgodeparturecity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'newgodeparturecity',
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	//allowClear: true,
	tags : false //设置必须存在的选项 才能选中
});
$('#goDepartureCity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'goDepartureCity',
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
$('#newgoarrivedcity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'newgoarrivedcity',
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
$('#newreturndeparturecity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'newreturndeparturecity',
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
$('#newreturnarrivedcity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'newreturnarrivedcity',
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
$('#gotransferarrivedcity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'gotransferarrivedcity',
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
	//allowClear: true,//清除
	tags : false //设置必须存在的选项 才能选中
});
$('#gotransferdeparturecity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				//exname : cArrivalcity,
				cityname : params.term, // search term
				citytype : 'gotransferdeparturecity',
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
	//allowClear: true,//清空功能
	tags : false //设置必须存在的选项 才能选中
});
$('#goArrivedCity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
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
				citytype : 'goArrivedCity',
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
$('#returnDepartureCity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
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
				citytype : 'returnDepartureCity',
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
$('#returntransferarrivedcity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
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
				citytype : 'returntransferarrivedcity',
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
$('#returntransferdeparturecity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
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
				citytype : 'returntransferdeparturecity',
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
	//allowClear: true,//清空功能
	tags : false //设置必须存在的选项 才能选中
});
$('#returnArrivedCity').select2({
	ajax : {
		url : "/admin/simple/getCustomerCitySelect.html",
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
				citytype : 'returnArrivedCity',
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

$('#gotransferflightnum').select2({
	ajax : {
		url : "/admin/tripairline/getTripAirlineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			//去程出发城市
			var goDepartureCity = $('#newgodeparturecity').val();
			if (goDepartureCity) {
				goDepartureCity = goDepartureCity.join(',');
			}else{
				goDepartureCity = '';
			}
			//去程抵达城市
			var goArrivedCity = $('#gotransferarrivedcity').val();
			if (goArrivedCity) {
				goArrivedCity = goArrivedCity.join(',');
			}else{
				goArrivedCity = '';
			}
			var date = $('#goDate').val();
			return {
				date:date,
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
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
$('#newgoflightnum').select2({
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
			var goDepartureCity = $('#gotransferdeparturecity').val();
			if (goDepartureCity) {
				goDepartureCity = goDepartureCity.join(',');
			}else{
				goDepartureCity = '';
			}
			//去程抵达城市
			var goArrivedCity = $('#newgoarrivedcity').val();
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
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
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
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
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

/*$("#goFlightNum").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	
	$.ajax({ 
		url: '/admin/tripairline/getGoandReturncity.html',
		dataType:"json",
		data:{airline:thisval},
		type:'post',
		success: function(data){
			//设置去程出发城市和抵达城市
			$("#goDepartureCity").html('<option selected="selected" value="'+data.takeoffcityid+'">'+data.takeoffname+'</option>');
			$("#goArrivedCity").html('<option selected="selected" value="'+data.landingcityid+'">'+data.landingname+'</option>');
		}
	});
	
	//查询航班接口到缓存
	//initFlightByInterface(goDate,thisval,goArrivedCity);
	//initFlightByInterface(returnDate,goArrivedCity,thisval);
});*/


$('#returntransferflightnum').select2({
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
			var returnDepartureCity = $('#newreturndeparturecity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			}else{
				returnDepartureCity += '';
			}
			//去程抵达城市
			var returnArrivedCity = $('#returntransferarrivedcity').val();
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
			/*data.sort(function(a,b){
				if(a.zhuanflightnum() != undefined){
					return 1;
				}
	            });*/
			var selectdata = $.map(data, function (obj) {
				//obj.id = obj.id; // replace pk with your identifier
				//obj.id = obj.flightnum; // replace pk with your identifier
				//obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
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
$('#newreturnflightnum').select2({
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
			var returnDepartureCity = $('#returntransferdeparturecity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			}else{
				returnDepartureCity += '';
			}
			//去程抵达城市
			var returnArrivedCity = $('#newreturnarrivedcity').val();
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
			/*data.sort(function(a,b){
				if(a.zhuanflightnum() != undefined){
					return 1;
				}
	            });*/
			var selectdata = $.map(data, function (obj) {
				//obj.id = obj.id; // replace pk with your identifier
				//obj.id = obj.flightnum; // replace pk with your identifier
				//obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
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
			/*data.sort(function(a,b){
				if(a.zhuanflightnum() != undefined){
					return 1;
				}
	            });*/
			var selectdata = $.map(data, function (obj) {
				//obj.id = obj.id; // replace pk with your identifier
				//obj.id = obj.flightnum; // replace pk with your identifier
				//obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
				if(obj.zhuanflightnum != undefined){
					obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
				}else{
					//obj.id = obj.flightnum; // replace pk with your identifier
					obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
					obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
				}
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
/*$("#returnFlightNum").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	
	$.ajax({ 
		url: '/admin/tripairline/getGoandReturncity.html',
		dataType:"json",
		data:{airline:thisval},
		type:'post',
		success: function(data){
			//设置去程出发城市和抵达城市
			$("#returnDepartureCity").html('<option selected="selected" value="'+data.takeoffcityid+'">'+data.takeoffname+'</option>');
			$("#returnArrivedCity").html('<option selected="selected" value="'+data.landingcityid+'">'+data.landingname+'</option>');
		}
	});
	
	//查询航班接口到缓存
	//initFlightByInterface(goDate,thisval,goArrivedCity);
	//initFlightByInterface(returnDate,goArrivedCity,thisval);
});*/

$("#newgodeparturecity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程抵达城市
	var thistext = $(this).text();
	$("#newreturnarrivedcity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
});
$("#newgodeparturecity").on("select2:unselect",function(e){
	$(this).text('');
	$('#newreturnarrivedcity').empty();
	$('#gotransferflightnum').empty();
	$('#newreturnflightnum').empty();
});
$("#newgoarrivedcity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程抵达城市
	var thistext = $(this).text();
	$("#newreturndeparturecity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
});
$("#newgoarrivedcity").on("select2:unselect",function(e){
	$(this).text('');
	$('#newreturndeparturecity').empty();
	$('#newgoflightnum').empty();
	$('#returntransferflightnum').empty();
});
$("#gotransferdeparturecity").on("select2:unselect",function(e){
	$(this).text('');
	$('#newgoflightnum').empty();
});
$("#newreturndeparturecity").on("select2:unselect",function(e){
	$(this).text('');
	$('#returntransferflightnum').empty();
});
$("#returntransferdeparturecity").on("select2:unselect",function(e){
	$(this).text('');
	$('#newreturnflightnum').empty();
});
$("#newreturnarrivedcity").on("select2:unselect",function(e){
	$(this).text('');
	$('#newreturnflightnum').empty();
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
	//initFlightByInterface(goDate,thisval,goArrivedCity);
	//initFlightByInterface(returnDate,goArrivedCity,thisval);
});
$("#goDepartureCity").on("select2:unselect",function(e){
	$(this).text('');
	$("#returnArrivedCity").html('');
	$('#goFlightNum').empty();
	$('#returnFlightNum').empty();
});
$("#gotransferarrivedcity").on("select2:select",function(e){
	var thisval = $(this).val();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程抵达城市
	var thistext = $(this).text();
	$("#gotransferdeparturecity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	$("#returntransferarrivedcity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
	$("#returntransferdeparturecity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
});
$("#gotransferarrivedcity").on("select2:unselect",function(e){
	$(this).text('');
	$('#gotransferdeparturecity').empty();
	$('#returntransferarrivedcity').empty();
	$('#returntransferdeparturecity').empty();
	$('#gotransferflightnum').empty();
	$('#newgoflightnum').empty();
	$('#returntransferflightnum').empty();
	$('#newreturnflightnum').empty();
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
	//initFlightByInterface(goDate,goDepartureCity,thisval);
	//initFlightByInterface(returnDate,thisval,goDepartureCity);
});
$("#goArrivedCity").on("select2:unselect",function(e){
	$(this).text('');
	$("#returnDepartureCity").html('');
	$('#goFlightNum').empty();
	$('#returnFlightNum').empty();
});
$("#returntransferarrivedcity").on("select2:select",function(e){
	var thisval = $(this).val();
	var thistext = $(this).text();
	if (thisval) {
		thisval = thisval.join(',');
	}else{
		thisval += '';
	}
	//设置回程出发城市
	$("#returntransferdeparturecity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
});
$("#returntransferarrivedcity").on("select2:unselect",function(e){
	$(this).text('');
	$('#returntransferdeparturecity').empty();
	$('#returntransferflightnum').empty();
	$('#newreturnflightnum').empty();
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
	//initFlightByInterface(returnDate,thisval,returnArrivedCity);
});
$("#returnDepartureCity").on("select2:unselect", function(e) {
	$(this).text('');
	$("#returnDepartureCity").html('');
	$('#returnFlightNum').empty();
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
	//initFlightByInterface(returnDate,returnDepartureCity,thisval);
});
$("#returnArrivedCity").on("select2:unselect", function(e) {
	$(this).text('');
	$("#returnArrivedCity").html('');
	$('#returnFlightNum').empty();
});

//加载航班号到缓存并同步到数据库
function initFlightByInterface(departuredate,departurecity,arrivedcity){
	$.ajax({ 
		url: '/admin/tripairline/getAirlines.html',
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
	var visatype = $('#visatype').val();
	var cityid = $('#cityid').val();
	console.log(visatype);
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
	
	
	var gotransferarrivedcity = $('#gotransferarrivedcity').val();
	if (gotransferarrivedcity) {
		gotransferarrivedcity = gotransferarrivedcity.join(',');
	}else{
		gotransferarrivedcity += '';
	}
	var gotransferdeparturecity = $('#gotransferdeparturecity').val();
	if (gotransferdeparturecity) {
		gotransferdeparturecity = gotransferdeparturecity.join(',');
	}else{
		gotransferdeparturecity += '';
	}
	var returntransferarrivedcity = $('#returntransferarrivedcity').val();
	if (returntransferarrivedcity) {
		returntransferarrivedcity = returntransferarrivedcity.join(',');
	}else{
		returntransferarrivedcity += '';
	}
	var returntransferdeparturecity = $('#returntransferdeparturecity').val();
	if (returntransferdeparturecity) {
		returntransferdeparturecity = returntransferdeparturecity.join(',');
	}else{
		returntransferdeparturecity += '';
	}
	var gotransferflightnum = $('#gotransferflightnum').val();
	if (gotransferflightnum) {
		gotransferflightnum = gotransferflightnum.join(',');
	}else{
		gotransferflightnum += '';
	}
	var returntransferflightnum = $('#returntransferflightnum').val();
	if (returntransferflightnum) {
		returntransferflightnum = returntransferflightnum.join(',');
	}else{
		returntransferflightnum += '';
	}
	var newgodeparturecity = $('#newgodeparturecity').val();
	if (newgodeparturecity) {
		newgodeparturecity = newgodeparturecity.join(',');
	}else{
		newgodeparturecity += '';
	}
	var newgoarrivedcity = $('#newgoarrivedcity').val();
	if (newgoarrivedcity) {
		newgoarrivedcity = newgoarrivedcity.join(',');
	}else{
		newgoarrivedcity += '';
	}
	var newgoflightnum = $('#newgoflightnum').val();
	if (newgoflightnum) {
		newgoflightnum = newgoflightnum.join(',');
	}else{
		newgoflightnum += '';
	}
	var newreturndeparturecity = $('#newreturndeparturecity').val();
	if (newreturndeparturecity) {
		newreturndeparturecity = newreturndeparturecity.join(',');
	}else{
		newreturndeparturecity += '';
	}
	var newreturnarrivedcity = $('#newreturnarrivedcity').val();
	if (newreturnarrivedcity) {
		newreturnarrivedcity = newreturnarrivedcity.join(',');
	}else{
		newreturnarrivedcity += '';
	}
	var newreturnflightnum = $('#newreturnflightnum').val();
	if (newreturnflightnum) {
		newreturnflightnum = newreturnflightnum.join(',');
	}else{
		newreturnflightnum += '';
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
				visatype:visatype,
				cityid:cityid,
				gotransferarrivedcity:gotransferarrivedcity,
				gotransferdeparturecity:gotransferdeparturecity,
				returntransferarrivedcity:returntransferarrivedcity,
				returntransferdeparturecity:returntransferdeparturecity,
				gotransferflightnum:gotransferflightnum,
				returntransferflightnum:returntransferflightnum,
				newgodeparturecity:newgodeparturecity,
				newgoarrivedcity:newgoarrivedcity,
				newgoflightnum:newgoflightnum,
				newreturndeparturecity:newreturndeparturecity,
				newreturnarrivedcity:newreturnarrivedcity,
				newreturnflightnum:newreturnflightnum
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
			//html += '<td>'+value.hotelname+'</td>';
			if(index != data.length - 1){
				html += '<td><table style="width:100%;"><tr><td style="text-align:center;">'+value.hotelname+'</td></tr><tr><td style="text-align:center;">'+value.hoteladdress+'</td></tr><tr><td style="text-align:center;">'+value.hotelmobile+'</td></tr></table></td>';
			}else{
				html += '<td></td>';
			}
		}else{
			if(index != data.length - 1){
				html += '<td>連泊</td>';
			}else{
				html += '<td></td>';
			}
		}
		if(index != data.length - 1){
			html += '<td><i class="editHui" onclick="schedulingEdit('+value.id+')"></i><i class="resetHui" onclick="resetPlan('+value.id+')"></i><i class="addHui" onclick="addHotel('+value.id+')"></i></td>';
		}
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
	pickerPosition: "bottom-right",//显示位置
	minView: "month"//只显示年月日
}).on("click", function () {
	console.log('click..');
	
	$(this).datetimepicker(
		"setEndDate", $("#goDate").val()
	);
	
}).on('changeDate', function (ev) {
	console.log('change..');
	var stayday;
	if (cityidstr == 1 || cityidstr == "") {
		stayday = 7;
	} else {
		stayday = 6;
	}
	var startDate = $("#sendVisaDate").val();
	$.ajax({
		url: '/admin/visaJapan/autoCalculateBackDateSpecial.html',
		dataType: "json",
		data: { gotripdate: startDate, stayday: stayday + 1 },
		type: 'post',
		success: function (data) {
			$("#outVisaDate").val(data);
			$("#goDate").datetimepicker("setStartDate", data);
		}
	});
}).on('blur', function() {
	
});

(function () {
	
	var stayday;
	if (cityidstr == 1 || cityidstr == "") {
		stayday = 7;
	} else {
		stayday = 6;
	}

	if (sendVisaDateVal == '') {
		$.ajax({
			url: '/admin/visaJapan/autofillsendvisatime.html',
			dataType: "json",
			type: 'post',
			success: function (data) {
				$('#sendVisaDate').val(data);
				$.ajax({
					url: '/admin/visaJapan/autoCalculateBackDateSpecial.html',
					dataType: "json",
					data: { gotripdate: data, stayday: stayday + 1 },
					type: 'post',
					success: function (data) {
						$("#outVisaDate").val(data);
						$("#goDate").datetimepicker("setStartDate", data);
						$("#returnDate").datetimepicker("setStartDate", data);
					}
				});
			}
		});
	}
})();

function getNewDate(temp, fn){
	var tmd = temp.substring(0, 4)+'-' +temp.substring(4,6) + '-' + temp.substring(6,8);
	var year = temp.substring(0,4);
	var month = temp.substring(4,6);
	var day = temp.substring(6,8);
	if(month<1||month>12){
		alert("月份必须在01和12之间!");
		$(this).val("");
		return;
	}    
    if(day<1||day>31){
    	alert("日期必须在01和31之间!");
    	$(this).val("");
    	return;
    }else{  
    	if(month==2){      
    		if((year%4)==0&&day>29){
    			alert("二月份日期必须在01到29之间!");
    			$(this).val("");
    			return;
    		}                
            if((year%4)>0&&day>28){
            	alert("二月份日期必须在01到28之间!");
            	$(this).val("");
            	return;
            }    
         }    
         if((month==4||month==6||month==9||month==11)&&(day>30)){
        	 alert(" 在四，六，九，十一月份   /n日期必须在01到30之间!");
        	 $(this).val("");
        	 return;
         }    
    }
    var date =  new Date(tmd);
	var YYYY = date.getFullYear();
	var MM = date.getMonth()+1;
	if (MM < 10) MM = "0" + MM;
	var DD = date.getDate();
	if (DD < 10) DD = "0" + DD;
    return (YYYY+"-"+MM+"-"+DD);
}

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
}).on('changeDate', function (ev) {
	console.log('change..');
	$("#returnDate").datetimepicker("setStartDate",$("#goDate").val());
	
	var stayday = $("#stayday").val();
	var startDate = $("#goDate").val();
	var returnDate = $("#returnDate").val();
	if(stayday != ""){
		$.ajax({
			url: '/admin/neworderUS/autoCalculateBackDate.html',
			dataType: "json",
			data: { gotripdate: startDate, stayday: stayday },
			type: 'post',
			success: function (data) {
				$("#returnDate").val(data);
			}
		});
	}
	if(returnDate != "" && stayday == ""){
		$.ajax({
			url: '/admin/neworderUS/autoCalCulateStayday.html',
			dataType: "json",
			data: { gotripdate: startDate, returnDate: returnDate },
			type: 'post',
			success: function (data) {
				$("#stayday").val(data);
			}
		});
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
	var sendVisaDate = $('#goDate').val();
	if(sendVisaDate){
		$("#returnDate").datetimepicker("setStartDate",sendVisaDate);
	}
}).on('changeDate', function (ev) {
	console.log('change..');
	var stayday = $("#stayday").val();
	var startDate = $("#goDate").val();
	var returnDate = $("#returnDate").val();
	if(returnDate != "" && startDate != ""){
		$.ajax({
			url: '/admin/neworderUS/autoCalCulateStayday.html',
			dataType: "json",
			data: { gotripdate: startDate, returnDate: returnDate },
			type: 'post',
			success: function (data) {
				$("#stayday").val(data);
			}
		});
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