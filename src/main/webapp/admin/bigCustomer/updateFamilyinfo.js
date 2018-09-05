
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

//汉字转拼音
$(document).on("input","#spousefirstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#spousefirstnameen").val("").change();
	}else{
		$("#spousefirstnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#spouselastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#spouselastnameen").val("").change();
	}else{
		$("#spouselastnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#fatherfirstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#fatherfirstnameen").val("").change();
	}else{
		$("#fatherfirstnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#fatherlastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#fatherlastnameen").val("").change();
	}else{
		$("#fatherlastnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#motherfirstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#motherfirstnameen").val("").change();
	}else{
		$("#motherfirstnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#motherlastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#motherlastnameen").val("").change();
	}else{
		$("#motherlastnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#relativesfirstname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#relativesfirstnameen").val("").change();
	}else{
		$("#relativesfirstnameen").val(pinyinchar.toUpperCase()).change();
	}
});
$(document).on("input","#relativeslastname",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#relativeslastnameen").val("").change();
	}else{
		$("#relativeslastnameen").val(pinyinchar.toUpperCase()).change();
	}
});

//获取拼音字符串
function getPinYinStr(hanzi){
	var onehanzi = hanzi.split('');
	var pinyinchar = '';
	for(var i=0;i<onehanzi.length;i++){
		pinyinchar += PinYin.getPinYin(onehanzi[i]);
	}
	return pinyinchar.toUpperCase();
}

//配偶信息
/*function changeSpouseShow(){
	var opt = $("#spouseaddress").val();
	if(opt == 5){
		$(".otherSpouseInfo").show();
	}else{
		$(".otherSpouseInfo").hide();
		emptyContentByObj($("div.otherSpouseInfo"));
	}
}*/

//母亲是否在美国radio
$(".motherUS").change(function () {
	let checked = $("input[name='ismotherinus']:checked").val();
	if (checked == 1) {
		$(".motherUSYes").show();
	} else {
		$(".motherUSYes").hide();
	}
});
//父亲是否在美国radio
$(".fatherUS").change(function () {
	let checked = $("input[name='isfatherinus']:checked").val();
	if (checked == 1) {
		$(".fatherUSYes").show();
	} else {
		$(".fatherUSYes").hide();
	}
});
//在美国除了父母还有没有直系亲属radio
$(".directUSRelatives").change(function () {
	let checked = $("input[name='hasimmediaterelatives']:checked").val();
	if (checked == 1) {
		$(".directRelatives").show();
	} else {
		$(".directRelatives").hide();
	}
});

//配偶国籍
$('#spousenationality,#spousecountry').select2({
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
//配偶出生城市
$('#spousecity').select2({
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


//取消关闭窗口
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

//保存家庭信息
function save(status){
	
		var	familyinfo = $("#familyinfo").serialize();
		console.log(familyinfo);
		if(status == 2){
			//左箭头跳转 
			window.location.href = '/admin/neworderUS/updatePassportInfo.html?staffid='+staffId;
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveFamilyinfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else if(status == 3){
			//右箭头跳转
			window.location.href = '/admin/neworderUS/updateWorkInfo.html?staffid='+staffId;
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveFamilyinfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveFamilyinfo.html',
				success :function(data) {
					layer.closeAll("loading");
					closeWindow();
					parent.successCallback(2);
				}
			});
		}
}

