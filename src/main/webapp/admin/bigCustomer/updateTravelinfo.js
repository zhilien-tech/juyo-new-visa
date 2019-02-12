
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
		//url : '/admin/translate/translate',
		url : '/admin/neworderUS/baiduTranslate',
		data : {
			/*api : 'google',
			strType : to,
			en : 'en',*/
			q : toval
		},
		type : 'GET',
		dataType : 'json',
		success : function(data) {
			$("#" + to).val(data).change();
		}
	});
	/*$.getJSON("/admin/translate/google", {q: $(from).val()}, function (result) {
        $("#" + to).val(result.data);
    });*/
}

//日期相关
$(document).on("input","#arrivedate,#issueddate,#servicestartdate,#serviceenddate",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	if(temp.indexOf("-") > 0){
		if(temp.length == 10){
			var tmd = temp.split("-");
			var result = "";
			for(var i = 0;i < tmd.length;i++){
				result += tmd[i].toString();
			}
			$(this).val(getNewDate(result));
		}
	}else{
		if($(this).val().length == 8){
			$(this).val(getNewDate(temp));
		}
	}
});

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

//是否与其他人同行radio
$(".companyInfo").change(function () {

	let checked = $("input[name='istravelwithother']:checked").val();
	if (checked == 1) {
		$(".teamnamefalse").show();
		$(".companysave").show();
	} else {
		$(".teamnamefalse").hide();
		$(".companysave").hide();
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

//支付人修改
$("#costpayer").change(function(){
	var thisval = $(this).val();
	if(thisval == 2){
		let checked = $("input[name='payaddressissamewithyou']:checked").val();
		if (checked == 1) {
			$(".addressPay").hide();
		} else {
			$(".addressPay").show();
		}
		$(".otherPay").show();
    	$(".companyPay").hide();
	}else if(thisval == 3){
		$(".companyPay").show();
    	$(".addressPay").show();
    	$(".otherPay").hide();
	}else{
		$(".companyPay").hide();
    	$(".addressPay").hide();
    	$(".otherPay").hide();
	}
});
$(".payaddressissamewithyou").change(function(){
	
	let checked = $("input[name='payaddressissamewithyou']:checked").val();
	if (checked == 1) {
		$(".addressPay").hide();
	} else {
		$(".addressPay").show();
	}
});
//是否去过美国radio
$(".goUS").change(function () {
	
	let checked = $("input[name='hasbeeninus']:checked").val();
	if (checked == 1) {
		$(".goUSInfo").show();
	} else {
		$(".goUSInfo").hide();
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
//是否丢失过radio
$(".lose").change(function () {
	let checked = $("input[name='islost']:checked").val();
	if (checked == 1) {
		$(".lostExplain").show();
	} else {
		$(".lostExplain").hide();
	}
});
//是否被取消过radio
$(".revoke").change(function () {
	let checked = $("input[name='iscancelled']:checked").val();
	if (checked == 1) {
		$(".cancelExplain").show();
	} else {
		$(".cancelExplain").hide();
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
		$(".gocountrysave").show();
	} else {
		$(".travelCountry").hide();
		$(".gocountrysave").hide();
	}
});
//是否为慈善组织工作radio
$(".isworkedcharitableorganization").change(function () {
	let checked = $("input[name='isworkedcharitableorganization']:checked").val();
	if (checked == 1) {
		$(".organizationName").show();
	} else {
		$(".organizationName").hide();
	}
});
//是否服兵役radio
$(".hasservedinmilitary").change(function () {
	let checked = $("input[name='hasservedinmilitary']:checked").val();
	if (checked == 1) {
		$(".military").show();
	} else {
		$(".military").hide();
	}
});

$(".isclan").change(function () {
	let checked = $("input[name='isclan']:checked").val();
	if (checked == 1) {
		$(".clanName").show();
	} else {
		$(".clanName").hide();
	}
});

$(".issecuritynumberapply").change(function () {
	let checked = $("input[name='issecuritynumberapply']:checked").val();
	if (checked == 1) {
		$(".socialsecuritynumber").show();
	} else {
		$(".socialsecuritynumber").hide();
	}
});

$(".istaxpayernumberapply").change(function () {
	let checked = $("input[name='istaxpayernumberapply']:checked").val();
	if (checked == 1) {
		$(".taxpayernumber").show();
	} else {
		$(".taxpayernumber").hide();
	}
});

$(".hasspecializedskill").change(function () {
	let checked = $("input[name='hasspecializedskill']:checked").val();
	if (checked == 1) {
		$(".skillexplain").show();
	} else {
		$(".skillexplain").hide();
	}
});

$(".isservedinrebelgroup").change(function () {
	let checked = $("input[name='isservedinrebelgroup']:checked").val();
	if (checked == 1) {
		$(".paramilitaryunitexplain").show();
	} else {
		$(".paramilitaryunitexplain").hide();
	}
});

//国家
$('#paycountry').select2({
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
$('#payprovince').select2({
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

/* 取消选中时 */
$("#payprovince").on('select2:unselect', function (evt) {
	//市清空
	$("#paycity").val(null).trigger("change");
});

//市
$('#paycity').select2({
	ajax : {
		url : "/admin/neworderUS/selectCity.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
		    var province = $('#payprovince').val();
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



//去过的国家
/*$('#traveledcountry').select2({
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
});*/

//服兵役国家
$('#militarycountry').select2({
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

$(document).on("input","#payfirstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#payfirstnameen").val("").change();
	}else{
		$("#payfirstnameen").val(pinyinchar).change();
	}
});
$(document).on("input","#paylastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#paylastnameen").val("").change();
	}else{
		$("#paylastnameen").val(pinyinchar).change();
	}
});
$(document).on("input","#firstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var Index = $(this).parent().parent().parent().index();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$(".firstnameen").eq(Index).val("").change();
	}else{
		$(".firstnameen").eq(Index).val(pinyinchar).change();
	}
});
$(document).on("input","#lastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var Index = $(this).parent().parent().parent().index();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$(".lastnameen").eq(Index).val("").change();
	}else{
		$(".lastnameen").eq(Index).val(pinyinchar).change();
	}
});


/*function addSegmentsTranslateZhToPinYin(from, to, param){
	var toval = "";
	
	var Index = $(from).parent().parent().parent().index();
	//var Indexen = $("#"+to).parent().parent().index();
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	var pinyinchar = getPinYinStr(toval);
	$("." + to).eq(Index).val(pinyinchar).change();
}*/

//获取拼音字符串
function getPinYinStr(hanzi){
	var onehanzi = hanzi.split('');
	var pinyinchar = '';
	for(var i=0;i<onehanzi.length;i++){
		pinyinchar += PinYin.getPinYin(onehanzi[i]);
	}
	return pinyinchar.toUpperCase();
}

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
				obj.text = obj.name; // replace pk with your identifier
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

//获取旅伴信息集合
function getCompanionList(){
	var companionList = [];
	$('.teamnamefalseDivzh').each(function(index,i){
		
		var companionLength = '';
		var companion = {};
		var firstname = $(this).find('[name=firstname]').val();
		var lastname = $(this).find('[name=lastname]').val();
		var relationship = $(this).find('[name=relationship]').val();
		
		companionLength += firstname;
		companionLength += lastname;
		//下拉框
		if (relationship != 0) {
			companionLength += relationship;
		}else{
			companionLength += '';
		}
		
		//英文
		var firstnameen = $(".firstnameen").eq(index).val();
		var lastnameen = $(".lastnameen").eq(index).val();
		//var relationshipen = $(".relationship").eq(index).val();
		
		/*if (relationshipen != 0) {
			companionLength += relationshipen;
		}else{
			companionLength += '';
		}*/
		
		companionLength += firstnameen;
		companionLength += lastnameen;
		//companionLength += relationshipen;
		
		if(companionLength.length > 0){
			companion.staffid = staffId;
			companion.firstname = firstname;
			companion.lastname = lastname;
			companion.relationship = relationship;
			companion.companionexplain = "";
			
			//英文
			companion.firstnameen = firstnameen;
			companion.lastnameen = lastnameen;
			companion.relationshipen = relationship;
			companion.companionexplainen = "";
			
			
			companionList.push(companion);
		}
	});
	console.log(JSON.stringify(companionList));
	return companionList;
}

//保存工作信息
function save(status){
	
		var companioninfoList = getCompanionList();
		var	familyinfo = $.param({companionList:JSON.stringify(companioninfoList)}) + "&" +  $("#travelinfo").serialize();
		//familyinfo.companioninfoList = JSON.stringify(getCompanionList());
		console.log(familyinfo);
		if(status == 2){
			//左箭头跳转 
			window.location.href = '/admin/neworderUS/updateWorkInfo.html?staffid='+staffId;
			/*$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveTravelinfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});*/
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


//只能输入数字
$(document).on("input","#taxpayernumber",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	
	var reg = /[^0-9]/g;
	$("#taxpayernumber").val(temp.replace(reg, ""));
});
//只能输入数字
$(document).on("input","#socialsecuritynumber",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	
	var reg = /[^0-9]/g;
	$("#socialsecuritynumber").val(temp.replace(reg, ""));
});

