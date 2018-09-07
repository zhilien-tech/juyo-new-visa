
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

//是否与其他人同行radio
$(".companyInfo").change(function () {

	let checked = $("input[name='istravelwithother']:checked").val();
	if (checked == 1) {
		$(".teamnamefalse").show();
	} else {
		$(".teamnamefalse").hide();
	}
});
//旅伴信息多段操作
$(".companysave").click(function(){
	cloneMoreDiv("teamaddfalse");
	// $(".companysaveen").trigger("click");
});
//国家多段操作
$(".gocountrysave").click(function(){
	cloneMoreDiv("travelCountry");
	$(".gocountrysaveen").trigger("click");
});
//删除
$(".gocountrycancel").click(function(){
	deleteMoreDiv("travelCountry");
	$(".gocountrycancelen").trigger("click");
});
//删除多段
function deleteMoreDiv(divClass){
	if($("."+divClass).length>1){
		$("."+divClass+":last").remove();
	}
}
//清空子元素内容
function emptyContentByObj(obj){
	obj.find("input[type='text']").each(function() {
		$(this).val("");
		$(this).prop("disabled",false);
	});
	obj.find("select").each(function() {
		$(this).val(0);
	});
	obj.find("textarea").each(function() {
		$(this).val("");
	});
	obj.find("input[type='checkbox']").each(function() {
		$(this).prop('checked', false);
	});
	obj.find("input[type='radio']").each(function() {
		if($(this).val() == 2){
			$(this).prop("checked", "checked");
		}
	});
	
}
//克隆添加多段
function cloneMoreDiv(divClass){
	let i = $("."+divClass).eq(0).find('a').attr('data-index');
	var cloneDiv = $("."+divClass).eq(0).clone();
	emptyContentByObj(cloneDiv);
	i++;
	cloneDiv.find('a').attr('data-index', i);
	$("."+divClass).last().after(cloneDiv);
	//initDateTimePicker($("."+divClass));
}
//是否去过美国radio
$(".goUS").change(function () {
	
	let checked = $("input[name='hasbeeninus']:checked").val();
	if (checked == 1) {
		$(".gotousInfo").show();
	} else {
		$(".gotousInfo").hide();
	}
});
//是否有美国驾照radio
$(".license").change(function () {
	let checked = $("input[name='hasdriverlicense']:checked").val();
	if (checked == 1) {
		$(".driverInfo").show();
	} else {
		$(".driverInfo").hide();
	}
});
//是否有美国签证radio
$(".visaUS").change(function () {
	let checked = $("input[name='isissuedvisa']:checked").val();
	if (checked == 1) {
		$(".dateIssue").show();
	} else {
		$(".dateIssue").hide();
	}
});
//是否被拒签过radio
$(".refuse").change(function () {
	let checked = $("input[name='isrefused']:checked").val();
	if (checked == 1) {
		$(".refuseExplain").show();
	} else {
		$(".refuseExplain").hide();
	}
});
//是否在申请美国移民radio
$(".isfiledimmigrantpetition").change(function () {
	let checked = $("input[name='isfiledimmigrantpetition']:checked").val();
	if (checked == 1) {
		$(".immigrantpetition_US").show();
	} else {
		$(".immigrantpetition_US").hide();
	}
});
//是否有出境记录radio
$(".istraveledanycountry").change(function () {
	let checked = $("input[name='istraveledanycountry']:checked").val();
	if (checked == 1) {
		$(".travelCountry").show();
	} else {
		$(".travelCountry").hide();
	}
});

//去过的国家
$('#traveledcountry').select2({
	ajax : {
		url : "/admin/neworderUS/selectCountry.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.chinesename; // replace pk with your identifier
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

//美国州下拉
$('#witchstateofdriver').select2({
	ajax : {
		url : "/admin/neworderUS/selectUSstate.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			var cArrivalcity = $('#cArrivalcity').val();
			if(cArrivalcity){
				cArrivalcity = cArrivalcity.join(',');
			}
			return {
				searchstr : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.statecn; // replace pk with your identifier
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
	
		var	familyinfo = $("#travelinfo").serialize();
		console.log(familyinfo);
		if(status == 2){
			//左箭头跳转 
			window.location.href = '/admin/neworderUS/updateWorkInfo.html?staffid='+staffId;
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveTravelinfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveTravelinfo.html',
				success :function(data) {
					layer.closeAll("loading");
					closeWindow();
					parent.successCallback(2);
				}
			});
		}
}

