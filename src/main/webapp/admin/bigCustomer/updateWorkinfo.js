
//翻译
function translateZhToEn(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	$.ajax({
		//async : false,
		url : '/admin/translate/translate',
		data : {
			api : 'google',
			strType : to,
			en : 'en',
			q : toval
		},
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			$("#" + to).val(data).change();
		}
	});
	/*$.getJSON("/admin/translate/google", {q: $(from).val()}, function (result) {
        $("#" + to).val(result.data);
    });*/
}

//以前的工作radio
$(".beforeWork").change(function () {

	let checked = $("input[name='isemployed']:checked").val();
	if (checked == 1) {
		$(".beforeWorkInfo").show();
	} else {
		$(".beforeWorkInfo").hide();
	}
});
//以前的教育radio
$(".education").change(function () {
	
	let checked = $("input[name='issecondarylevel']:checked").val();
	if (checked == 1) {
		$(".educationInfo").show();
	} else {
		$(".educationInfo").hide();
	}
});


//专业处理

$("#highesteducation").change(function(){
	var highesteducation = $(this).val();
	if(highesteducation > 2){
		$(".courseClass").show();
	}else{
		$(".courseClass").hide();
	}
})


//工作国家
$('#jobcountry,#employercountry,#institutioncountry').select2({
	ajax : {
		url : "/admin/neworderUS/selectCountry.html",
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
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.chinesename; // replace pk with your identifier
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
//省
$('#jobprovince,#employerprovince,#institutionprovince').select2({
	ajax : {
		url : "/admin/neworderUS/selectProvince.html",
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
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.province; // replace pk with your identifier
				obj.text = obj.province; // replace pk with your identifier
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


//现工作所在市
$('#city').select2({
	ajax : {
		url : "/admin/neworderUS/selectCity.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
		    var province = $('#jobprovince').val();
		    if(province){
		    	province = province.join(',');
			}
			return {
				province : province,
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.city; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
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
//以前工作所在市
$('#employercity').select2({
	ajax : {
		url : "/admin/neworderUS/selectCity.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			var province = $('#employerprovince').val();
			if(province){
				province = province.join(',');
			}
			return {
				province : province,
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.city; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
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
//以前教育所在市
$('#institutioncity').select2({
	ajax : {
		url : "/admin/neworderUS/selectCity.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			var province = $('#institutionprovince').val();
			if(province){
				province = province.join(',');
			}
			return {
				province : province,
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.city; // replace pk with your identifier
				obj.text = obj.city; // replace pk with your identifier
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


//取消关闭窗口
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

//保存工作信息
function save(status){
	
		var	familyinfo = $("#workinfo").serialize();
		console.log(familyinfo);
		if(status == 2){
			//左箭头跳转 
			window.location.href = '/admin/neworderUS/updateFamilyInfo.html?staffid='+staffId;
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveWorkandeducation.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else if(status == 3){
			//右箭头跳转
			window.location.href = '/admin/neworderUS/updateTravelInfo.html?staffid='+staffId;
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveWorkandeducation.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveWorkandeducation.html',
				success :function(data) {
					layer.closeAll("loading");
					closeWindow();
					parent.successCallback(2);
				}
			});
		}
}

