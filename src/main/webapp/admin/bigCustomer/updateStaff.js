$(function(){
	if(isDisable != 1){
	//校验
	if(flag == 1){
		touristValidate();
	}else{
		applyValidate();
	}

	var front = $("#cardFront").val();
	var back = $("#cardBack").val();
	if(front != ""){
		$("#uploadFile").siblings("i").css("display","block");
	}else{
		$("#uploadFile").siblings("i").css("display","none");
	}

	if(back != ""){
		$("#uploadFileBck").siblings("i").css("display","block");
	}else{
		$("#uploadFileBack").siblings("i").css("display","none");
	} 
	}
});
//工作人员进入，验证所有内容
function applyValidate(){
	$('#applicantInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			detailedaddressen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '详细地址英文不能为空'
					},
					stringLength: {//检测长度
                        max: 60,
                        message: '详细地址英文不能超过60个字符'
                    }
				}
			},
			mailaddress: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '街道地址不能为空'
					}
				}
			},
			mailcountry: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '国家不能为空'
					}
				}
			},
			mailprovince: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '省份不能为空'
					}
				}
			},
			mailcity: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '城市不能为空'
					}
				}
			},
			mailaddressen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '街道地址英文不能为空'
					},
					stringLength: {//检测长度
						max: 80,
						message: '街道地址地址英文不能超过80个字符'
					}
				}
			},
			telephone : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '手机号不能为空'
					},
					regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '手机号格式错误'
					}
				}
			},
			telephoneen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '手机号不能为空'
					},
					regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '手机号格式错误'
					}
				}
			},
			email : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '邮箱不能为空'
					},
					regexp: {
						regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						message: '邮箱格式错误'
					}
				}
			},
			emailen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '邮箱不能为空'
					},
					regexp: {
						regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						message: '邮箱格式错误'
					}
				}
			},
			cardId : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '身份证号不能为空'
					}
				}
			},
			cardIden : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '身份证号不能为空'
					}
				}
			},
			nation : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '民族不能为空'
					}
				}
			},
			nationen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '民族不能为空'
					}
				}
			},
			/*cardnum : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},*/
			/*cardnumen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},*/
			address : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '住址不能为空'
					}
				}
			},
			addressen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '住址不能为空'
					}
				}
			},
			province : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '省不能为空'
					}
				}
			},
			provinceen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '省不能为空'
					}
				}
			},
			city : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '市不能为空'
					}
				}
			},
			cityen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '市不能为空'
					}
				}
			},
			detailedaddress : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '详细地址不能为空'
					}
				}
			},
			marryexplain : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '说明不能为空'
					}
				}
			},
			marrystatus : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '婚姻状况不能为空'
					}
				}
			},
			marryexplainen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '说明不能为空'
					}
				}
			},
			otherfirstname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			otherfirstnameen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			otherlastname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '名不能为空'
					}
				}
			},
			otherlastnameen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '名不能为空'
					}
				}
			},
			nationality : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '出生国家不能为空'
					}
				}
			},
			nationalityen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国籍不能为空'
					}
				}
			},
			othercountry : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国家不能为空'
					}
				}
			},
			othercountryen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国家不能为空'
					}
				}
			},
		}
	});
	//$('#applicantInfo').bootstrapValidator('validate');
}
//从游客进入，不验证英文
function touristValidate(){
	$('#applicantInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			telephone : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '手机号不能为空'
					},
					regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '手机号格式错误'
					}
				}
			},
			email : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '邮箱不能为空'
					},
					regexp: {
						regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						message: '邮箱格式错误'
					}
				}
			},
			cardId : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '身份证号不能为空'
					}
				}
			},
			nation : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '民族不能为空'
					}
				}
			},
			/*cardnum : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},*/
			/*cardnumen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},*/
			address : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '住址不能为空'
					}
				}
			},
			province : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '省不能为空'
					}
				}
			},
			city : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '市不能为空'
					}
				}
			},
			detailedaddress : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '详细地址不能为空'
					}
				}
			},
			marryexplain : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '说明不能为空'
					}
				}
			},
			otherfirstname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			otherlastname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '名不能为空'
					}
				}
			},
			nationality : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国籍不能为空'
					}
				}
			},
			othercountry : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国家不能为空'
					}
				}
			},
		}
	});
	//$('#applicantInfo').bootstrapValidator('validate');
}


$(document).on("input","#cardprovince",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val().replace(/\s*/g,"");
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#cardprovinceen").val("").change();
	}else{
		if(temp.endsWith("省") || temp.endsWith("市") || temp.endsWith("区")){
			temp = temp.substr(0,temp.length - 1);
		}
		console.log(temp);
		
		if(temp == "内蒙古" || temp == "内蒙古自治区"){
			$("#cardprovinceen").val("NEI MONGOL").change();
		}else if(temp == "陕西" || temp == "陕西省"){
			$("#cardprovinceen").val("SHAANXI").change();
		}else{
			$("#cardprovinceen").val(pinyinchar.toUpperCase()).change();
		}
	}
});
$(document).on("input","#cardcity",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val().replace(/\s*/g,"");
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#cardcityen").val("").change();
	}else{
		if(temp.endsWith("省") || temp.endsWith("市") || temp.endsWith("区")){
			temp = temp.substr(0,temp.length - 1);
		}
		console.log(temp);
		
		if(temp == "内蒙古" || temp == "内蒙古自治区"){
			$("#cardcityen").val("NEI MONGOL").change();
		}else if(temp == "陕西" || temp == "陕西省"){
			$("#cardcityen").val("SHAANXI").change();
		}else{
			$("#cardcityen").val(pinyinchar.toUpperCase()).change();
		}
	}
});
$(document).on("input","#mailprovince",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val().replace(/\s*/g,"");
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#mailprovinceen").val("").change();
	}else{
		if(temp.endsWith("省") || temp.endsWith("市") || temp.endsWith("区")){
			temp = temp.substr(0,temp.length - 1);
		}
		
		if(temp == "内蒙古" || temp == "内蒙古自治区"){
			$("#mailprovinceen").val("NEI MONGOL").change();
		}else if(temp == "陕西" || temp == "陕西省"){
			$("#mailprovinceen").val("SHAANXI").change();
		}else{
			$("#mailprovinceen").val(pinyinchar.toUpperCase()).change();
			console.log($("#mailprovinceen").val());
		}
	}
});
$(document).on("input","#mailcity",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var pos=$(this).getCurPos();//保存原始光标位置
	var temp = $(this).val().replace(/\s*/g,"");
	var pinyinchar = getPinYinStr(temp);
	if($(this).val().length == 0){
		$("#mailcityen").val("").change();
	}else{
		if(temp.endsWith("省") || temp.endsWith("市") || temp.endsWith("区")){
			temp = temp.substr(0,temp.length - 1);
		}
		console.log(temp);
		
		if(temp == "内蒙古" || temp == "内蒙古自治区"){
			$("#mailcityen").val("NEI MONGOL").change();
		}else if(temp == "陕西" || temp == "陕西省"){
			$("#mailcityen").val("SHAANXI").change();
		}else{
			$("#mailcityen").val(pinyinchar.toUpperCase()).change();
		}
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

function translateZhToEn(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	$.ajax({
		//async : false,
		url : BASE_PATH+'/admin/neworderUS/baiduTranslate',
		data : {
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


//国籍检索
/*$("#nationality").on('input',function(){
	$("#nationality").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#nationality").val()
		},
		url : BASE_PATH+'/admin/orderJp/getNationality.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setNationality("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#nationality").after(liStr);
			}
		}
	});
});*/
//国籍上下键控制
//写成公共方法
var index = -1;
$(document).on('keydown','#nationality',function(e){
	var lilength = $(this).next().children().length;
		if(e == undefined)
			e = window.event;
		
		switch(e.keyCode){
		case 38:
			 
			index--;
			if(index == 0) index = 0;
			e.preventDefault();
			break;
		case 40:
			
			index++;
			if(index ==lilength) index = 0;
			e.preventDefault();
			break;
		case 13:
			
			$(this).val($('#ui-id-1').find('li:eq('+index+')').children().html());
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$("#nationality").blur();
			translateZhToEn('#nationality','nationalityen','');
			var nationality = $("#nationality").val();
			setNationality(nationality);
			index = -1;
			break;
		}
		var li = $('#ui-id-1').find('li:eq('+index+')');
		li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
});

//end
$("#nationalityen").on('input',function(){
	$("#nationalityen").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#nationalityen").val()
		},
		url : BASE_PATH+'/admin/orderJp/getNationalityen.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setNationalityen("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#nationalityen").after(liStr);
			}
		}
	});
});
//国籍上下键控制英文
var indexen = -1;
$(document).on('keydown','#nationalityen',function(e){
	var lilength = $(this).next().children().length;
	if(e == undefined)
		e = window.event;
	
	switch(e.keyCode){
	case 38:
		e.preventDefault();
		indexen--;
		if(indexen == 0) indexen = 0;
		break;
	case 40:
		e.preventDefault();
		indexen++;
		if(indexen == lilength) indexen = 0;
		break;
	case 13:
		
		$(this).val($(this).next().find('li:eq('+indexen+')').children().html());
		$("#nationalityen").nextAll("ul.ui-autocomplete").remove();
		$("#nationalityen").blur();
		var nationalityen = $("#nationalityen").val();
		setNationalityen(nationalityen);
		indexen = -1;
		break;
	}
	var li = $(this).next().find('li:eq('+indexen+')');
	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
});
//国籍检索下拉项
function setNationality(nationality){
	$("#nationality").nextAll("ul.ui-autocomplete").remove();
	$("#nationality").val(nationality).change();
} 
function setNationalityen(nationality){
	$("#nationalityen").nextAll("ul.ui-autocomplete").remove();
	$("#nationalityen").val(nationality).change();
} 
$("#nationalityDiv").mouseleave(function(){
	$("#nationality").nextAll("ul.ui-autocomplete").remove();
	$("#nationalityen").nextAll("ul.ui-autocomplete").remove();
});

//省份检索
$("#province").on('input',function(){
	$("#province").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#province").val()
		},
		url : BASE_PATH+'/admin/orderJp/getProvince.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setProvince("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#province").after(liStr);
			}
		}
	});
});
//省份上下键
var provinceindex = -1;
$(document).on('keydown','#province',function(e){
	
	if(e == undefined)
		e = window.event;
	
	switch(e.keyCode){
	case 38:
		e.preventDefault();
		provinceindex--;
		if(provinceindex ==0) provinceindex = 0;
		break;
	case 40:
		e.preventDefault();
		provinceindex++;
		if(provinceindex ==5) provinceindex = 0;
		break;
	case 13:
		
		$(this).val($(this).next().find('li:eq('+provinceindex+')').children().html());
		$("#province").nextAll("ul.ui-autocomplete").remove();
		$("#province").blur();
		var province = $("#province").val();
		setProvince(province);
		provinceindex = -1;
		break;
	}
	var li = $(this).next().find('li:eq('+provinceindex+')');
	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
});
//省份 检索下拉项
function setProvince(province){
	$("#province").nextAll("ul.ui-autocomplete").remove();
	$("#province").val(province).change();
} 
$("#provinceDiv").mouseleave(function(){
	$("#province").nextAll("ul.ui-autocomplete").remove();
});

//市检索
$("#city").on('input',function(){
	$("#city").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			province : $("#province").val(),
			searchStr : $("#city").val()
		},
		url : BASE_PATH+'/admin/orderJp/getCity.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setCity("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#city").after(liStr);
			}
		}
	});
});
//市
var cityindex = -1;
$(document).on('keydown','#city',function(e){
	
	if(e == undefined)
		e = window.event;
	
	switch(e.keyCode){
	case 38:
		e.preventDefault();
		cityindex--;
		if(cityindex ==0) cityindex = 0;
		break;
	case 40:
		e.preventDefault();
		cityindex++;
		if(cityindex ==5) cityindex = 0;
		break;
	case 13:
		
		$(this).val($(this).next().find('li:eq('+provinceindex+')').children().html());
		$("#city").nextAll("ul.ui-autocomplete").remove();
		$("#city").blur();
		var city = $("#city").val();
		setCity(city);
		cityindex = -1;
		break;
	}
	var li = $(this).next().find('li:eq('+cityindex+')');
	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
});
//市 检索下拉项
function setCity(city){
	$("#city").nextAll("ul.ui-autocomplete").remove();
	$("#city").val(city).change();
} 
$("#cityDiv").mouseleave(function(){
	$("#city").nextAll("ul.ui-autocomplete").remove();
});


//婚姻状况处理
//左边
if($("#marrystatus").val() == 2 ){//其他有婚姻说明
	$(".marryexplain").show();
}else{
	$(".marryexplain").hide();
}

$("#marrystatus").change(function(){
	var status = $(this).val();
	$("#marrystatusen").val(status);
	if($("#marrystatusen").val() == 2 ){//其他有婚姻说明
		$(".marryexplainen").show();
	}else{
		$(".marryexplainen").hide();
	}
	if(status == 2 ){
		$(".marryexplain").show();
	}else{
		$(".marryexplain").hide();
	}
});
//右边
if(marrystatusen == 8 ){//其他有婚姻说明
	$(".marryexplainen").show();
}else{
	$(".marryexplainen").hide();
}

$("#marrystatusen").change(function(){
	var status = $(this).val();
	if(status == 8 ){
		$(".marryexplainen").show();
	}else{
		$(".marryexplainen").hide();
	}
})

//radio 邮寄地址
$(".mailinfo").change(function(){
	var usedBeforeVal = $("input[name='ismailsamewithlive']:checked").val();
	if(usedBeforeVal == 2){
		$(".mailingAddressTrue").show();
		$(".mailingAddressUSTrue").show();
		$("input[name='ismailsamewithliveen'][value='2']").trigger("click");
		//$(".usedBeforeUS1").trigger("click");
	}else{
		$(".mailingAddressTrue").hide();
		$(".mailingAddressUSTrue").hide();
		$("input[name='ismailsamewithliveen'][value='1']").trigger("click");
		//$(".usedBeforeUS2").trigger("click");
	}
});
$(".mailinfoUS").change(function(){
	var usedBeforeVal = $("input[name='ismailsamewithliveen']:checked").val();
	if(usedBeforeVal == 2){
		$(".mailingAddressUSTrue").show();
	}else{
		$(".mailingAddressUSTrue").hide();
	}
});
//radio 曾用名
$(".usedBefore").change(function(){
	var usedBeforeVal = $("input[name='hasothername']:checked").val();
	if(usedBeforeVal == 1){
		
		$(".usedBeforeTrue").show();
		$(".usedBeforeUSTrue").show();
		$("input[name='hasothernameen'][value='1']").trigger("click");
		//$(".usedBeforeUS1").trigger("click");
	}else{
		$(".usedBeforeTrue").hide();
		$(".usedBeforeUSTrue").hide();
		$("input[name='hasothernameen'][value='2']").trigger("click");
		//$(".usedBeforeUS2").trigger("click");
	}
});
$(".usedBeforeUS").change(function(){
	var usedBeforeVal = $("input[name='hasothernameen']:checked").val();
	if(usedBeforeVal == 1){
		$(".usedBeforeUSTrue").show();
	}else{
		$(".usedBeforeUSTrue").hide();
	}
});
$(".usedBeforeUS1").click(function(){
	$(".usedBeforeUSTrue").show();
});
$(".usedBeforeUS2").click(function(){
	$(".usedBeforeUSTrue").hide();
});
//曾用国籍
$(".usedNationality").change(function(){
	var usedNationalityVal = $("input[name='hasothernationality']:checked").val();
	if(usedNationalityVal == 1){
		
		$(".usedNationalityTrue").show();
		$("input[name='hasothernationalityen'][value='1']").trigger("click");
		//$(".usedNationalityUS1").trigger("click");
	}else{
		$(".usedNationalityTrue").hide();
		$("input[name='hasothernationalityen'][value='2']").trigger("click");
		//$(".usedNationalityUS2").trigger("click");
	}
});
$(".usedNationalityUS").change(function(){
	var usedNationalityVal = $("input[name='hasothernationalityen']:checked").val();
	if(usedNationalityVal == 1){
		$(".usedNationalityUSTrue").show();
	}else{
		$(".usedNationalityUSTrue").hide();
	}
});
$(".usedNationalityUS1").click(function(){
	$(".usedNationalityUSTrue").show();
});
$(".usedNationalityUS2").click(function(){
	$(".usedNationalityUSTrue").hide();
});
//永久居民
$(".permanent").change(function(){
	var permanentVal = $("input[name='isothercountrypermanentresident']:checked").val();
	if(permanentVal == 1){
		
		$(".permanentTrue").show();
		$("input[name='isothercountrypermanentresidenten'][value='1']").trigger("click");
		//$(".permanentUS1").trigger("click");
	}else{
		$(".permanentTrue").hide();
		$("input[name='isothercountrypermanentresidenten'][value='2']").trigger("click");
		//$(".permanentUS2").trigger("click");
	}
});
$(".permanentUS").change(function(){
	var permanentVal = $("input[name='isothercountrypermanentresidenten']:checked").val();
	if(permanentVal == 1){
		$(".permanentUSTrue").show();
	}else{
		$(".permanentUSTrue").hide();
	}
});
$(".permanentUS1").click(function(){
	$(".permanentUSTrue").show();
});
$(".permanentUS2").click(function(){
	$(".permanentUSTrue").hide();
});

//checkbox居住地与身份证相同
/*$(".nowProvince").change(function(){
	var str="";  
	//是否同身份证相同
	$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
		str=$(this).val();     
	});     
	if(str == 1){//相同
		searchByCard();
	}else{
		$("#province").val("").change();
		$("#city").val("").change();
		$("#detailedAddress").val("").change();
	}
});*/
//居住地与身份证相同
$(".nowProvince").click(function(){
	if(this.checked){
		$(".nowProvinceen").prop("checked",true);
		searchByCard(1);
	}else{
		$(".nowProvinceen").prop("checked",false);
		$("#province").val("").change();
		$("#city").val("").change();
		$("#detailedAddress").val("").change();
	}
});
$(".nowProvinceen").click(function(){
	if(this.checked){
		searchByCard(2);
	}else{
		$("#provinceen").val("").change();
		$("#cityen").val("").change();
		$("#detailedAddressen").val("").change();
	}
});

function searchByCard(status){

	var cardId = $("#cardId").val();
	layer.load(1);
	$.ajax({
		type: 'POST',
		data : {
			cardId : cardId
		},
		url: BASE_PATH + '/admin/orderJp/getInfoByCard',
		success :function(data) {
			console.log(JSON.stringify(data));
			layer.closeAll('loading');
			if(status == 1){
				$("#detailedAddress").val($("#address").val()).change();
				if(data != null){
					$("#province").val(data.province).change();
					$("#city").val(data.city).change();
				}
			}else{
				translateZhToEn("#address","detailedAddressen","");
				if(data != null){
					translateZhToEn("#province","provinceen",data.province);
					translateZhToEn("#city","cityen",data.city);
				}
			}
		}
	});

}

//更新保存基本信息
function saveApplicant(status){
	
	var str="";
	var	applicantInfo = $("#applicantInfo").serialize();
	if(status != 2){
		$("#applicantInfo").data('bootstrapValidator').destroy();
		$("#applicantInfo").data('bootstrapValidator', null);
		applyValidate();
		//得到获取validator对象或实例 
		var bootstrapValidator = $("#applicantInfo").data('bootstrapValidator');
		// 执行表单验证 
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()){
			
			if(status == 2){
				//左箭头跳转 
				window.location.href = '/admin/neworderUS/updatePhoto.html?staffid='+staffId;
				/*$.ajax({
					type: 'POST',
					data : applicantInfo,
					url: BASE_PATH + '/admin/neworderUS/saveBasicinfo.html',
					success :function(data) {
						parent.successCallback(2);
					}
				});*/
			}else if(status == 3){
				//右箭头跳转
				window.location.href = '/admin/neworderUS/updatePassportInfo.html?staffid='+staffId;
				$.ajax({
					type: 'POST',
					data : applicantInfo,
					url: BASE_PATH + '/admin/neworderUS/saveBasicinfo.html',
					success :function(data) {
						parent.successCallback(2);
					}
				});
			}else{
				layer.load(1);
				$.ajax({
					type: 'POST',
					data : applicantInfo,
					url: BASE_PATH + '/admin/neworderUS/saveBasicinfo.html',
					success :function(data) {
						console.log(data);
						closeWindow();
						layer.closeAll("loading");
						parent.successCallback(2);
						layer.msg("保存成功");
					},error: function (XMLHttpRequest, textStatus, errorThrown) {
						layer.closeAll("loading");
						console.log(XMLHttpRequest);
	                    // 状态码
	                    console.log(XMLHttpRequest.status);
	                    // 状态
	                    console.log(XMLHttpRequest.readyState);
	                    // 错误信息   
	                    console.log(textStatus);
	                }
				});
			}
		}
		
	}else{
		//左箭头跳转 
		window.location.href = '/admin/neworderUS/updatePhoto.html?staffid='+staffId;
		/*$.ajax({
			type: 'POST',
			data : applicantInfo,
			url: BASE_PATH + '/admin/neworderUS/saveBasicinfo.html',
			success :function(data) {
				parent.successCallback(2);
			}
		});*/
	}
	
}

/*function saveApplicant(status){
	var urlName;
	if(isDisable != 1){
	$("#applicantInfo").data('bootstrapValidator').destroy();
	$("#applicantInfo").data('bootstrapValidator', null);
	if(flag == 1){
		//游客
		touristValidate();
		//游客跳转
		urlName = "touristVisaInfo";
	}else{
		//员工
		applyValidate();
		//员工跳转
		urlName = "updateVisaInfo";
	}
	
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#applicantInfo").data('bootstrapValidator');
	// 执行表单验证 
	bootstrapValidator.validate();
	
	
	var country = $("#nationalidentificationnumber").val();
	if(country == ""){
			if(!$(".isidentificationnumberapply").is(":checked")){
				$(".countryNum").attr("class", "countryNum has-error");  
				$(".help-countryNum").attr("data-bv-result","INVALID");  
				$(".help-countryNum").attr("style","display: block;");  
				$("#nationalidentificationnumber").attr("style", "border-color:#ff1a1a");
			}else{
				$(".countryNum").attr("class", "countryNum has-success");  
				$(".help-countryNum").attr("data-bv-result","IVALID");  
				$(".help-countryNum").attr("style","display: none;");  
				$("#nationalidentificationnumber").attr("style", null);
			}
	}else{
		$(".countryNum").attr("class", "countryNum has-success");  
        $(".help-countryNum").attr("data-bv-result","IVALID");  
        $(".help-countryNum").attr("style","display: none;");  
        $("#nationalidentificationnumber").attr("style", null);
	}
	if(flag != 1){
		var countryen = $("#nationalidentificationnumberen").val();
		if(countryen == ""){
			if(!$(".isidentificationnumberapplyen").is(":checked")){
				$(".countryNumen").attr("class", "countryNumen has-error");  
				$(".help-countryNumen").attr("data-bv-result","INVALID");  
				$(".help-countryNumen").attr("style","display: block;");  
				$("#nationalidentificationnumberen").attr("style", "border-color:#ff1a1a");
			}else{
				$(".countryNumen").attr("class", "countryNumen has-success");  
				$(".help-countryNumen").attr("data-bv-result","IVALID");  
				$(".help-countryNumen").attr("style","display: none;");  
				$("#nationalidentificationnumberen").attr("style", null);
			}
		}else{
			$(".countryNumen").attr("class", "countryNumen has-success");  
			$(".help-countryNumen").attr("data-bv-result","IVALID");  
			$(".help-countryNumen").attr("style","display: none;");  
			$("#nationalidentificationnumberen").attr("style", null);
		}
		
		var safeNumberen = $("#socialsecuritynumberen").val();
		if(safeNumberen == ""){
			if(!$(".issecuritynumberapplyen").is(":checked")){
				$(".safeNumen").attr("class", "safeNumen has-error");  
				$(".help-blocksafeen").attr("data-bv-result","INVALID");  
				$(".help-blocksafeen").attr("style","display: block;");  
				$("#socialsecuritynumberen").attr("style", "border-color:#ff1a1a");
			}else{
				$(".safeNumen").attr("class", "safeNumen has-success");  
				$(".help-blocksafeen").attr("data-bv-result","IVALID");  
				$(".help-blocksafeen").attr("style","display: none;");  
				$("#socialsecuritynumberen").attr("style", null);
			}
		}else{
			$(".safeNumen").attr("class", "safeNumen has-success");  
	        $(".help-blocksafeen").attr("data-bv-result","IVALID");  
	        $(".help-blocksafeen").attr("style","display: none;");  
	        $("#socialsecuritynumberen").attr("style", null);
		}
		
		var taxpayernumberen = $("#taxpayernumberen").val();
		if(taxpayernumberen == ""){
			if(!$(".istaxpayernumberapplyen").is(":checked")){
				$(".safepayen").attr("class", "safepayen has-error");  
				$(".help-ratepayingen").attr("data-bv-result","INVALID");  
				$(".help-ratepayingen").attr("style","display: block;");  
				$("#taxpayernumberen").attr("style", "border-color:#ff1a1a");
			}else{
				$(".safepayen").attr("class", "safepayen has-success");  
				$(".help-ratepayingen").attr("data-bv-result","IVALID");  
				$(".help-ratepayingen").attr("style","display: none;");  
				$("#taxpayernumberen").attr("style", null);
			}
		}else{
			$(".safepayen").attr("class", "safepayen has-success");  
	        $(".help-ratepayingen").attr("data-bv-result","IVALID");  
	        $(".help-ratepayingen").attr("style","display: none;");  
	        $("#taxpayernumberen").attr("style", null);
		}
		
		if($(".countryNumen").hasClass("has-error")){
			return;
		}
		
		if($(".safeNumen").hasClass("has-error")){
			return;
		}
		
		if($(".safepayen").hasClass("has-error")){
			return;
		}
	}
	
	var safeNumber = $("#socialsecuritynumber").val();
	if(safeNumber == ""){
			if(!$(".issecuritynumberapply").is(":checked")){
				$(".safeNum").attr("class", "safeNum has-error");  
				$(".help-blocksafe").attr("data-bv-result","INVALID");  
				$(".help-blocksafe").attr("style","display: block;");  
				$("#socialsecuritynumber").attr("style", "border-color:#ff1a1a");
			}else{
				$(".safeNum").attr("class", "safeNum front has-success");  
				$(".help-blocksafe").attr("data-bv-result","IVALID");  
				$(".help-blocksafe").attr("style","display: none;");  
				$("#socialsecuritynumber").attr("style", null);
			}
	}else{
		$(".safeNum").attr("class", "safeNum front has-success");  
        $(".help-blocksafe").attr("data-bv-result","IVALID");  
        $(".help-blocksafe").attr("style","display: none;");  
        $("#socialsecuritynumber").attr("style", null);
	}
	
	
	var taxpayernumber = $("#taxpayernumber").val();
	if(taxpayernumber == ""){
			if(!$(".istaxpayernumberapply").is(":checked")){
				$(".safepay").attr("class", "safepay has-error");  
				$(".help-ratepaying").attr("data-bv-result","INVALID");  
				$(".help-ratepaying").attr("style","display: block;");  
				$("#taxpayernumber").attr("style", "border-color:#ff1a1a");
			}else{
				$(".safepay").attr("class", "safepayen has-success");  
				$(".help-ratepaying").attr("data-bv-result","IVALID");  
				$(".help-ratepaying").attr("style","display: none;");  
				$("#taxpayernumber").attr("style", null);
			}
	}else{
		$(".safepay").attr("class", "safepayen has-success");  
        $(".help-ratepaying").attr("data-bv-result","IVALID");  
        $(".help-ratepaying").attr("style","display: none;");  
        $("#taxpayernumber").attr("style", null);
	}
	
	
	
	if($(".countryNum").hasClass("has-error")){
		return;
	}
	
	if($(".safeNum").hasClass("has-error")){
		return;
	}
	
	if($(".safepay").hasClass("has-error")){
		return;
	}
	
	
	if (bootstrapValidator.isValid()){

		var str="";
		var applicantInfo;
		$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
			str=$(this).val();     
		});
		if(str != 1){
			applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
		}else{
			applicantInfo = $("#applicantInfo").serialize();
		}
		applicantInfo = $("#applicantInfo").serialize();
		applicantInfo.id = staffId;

		if(status == 2){
			//左箭头跳转 添加不可编辑
			window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId+'&isDisable='+isDisable;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else if(status == 3){
			//右箭头跳转
			window.location.href = '/admin/bigCustomer/'+urlName+'.html?staffId='+staffId+'&isDisable='+isDisable+'&flag='+flag;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
					if(data>0){
						closeWindow();
						parent.successCallback(2);
					}
				}
			});
		}
		layer.closeAll("loading");
	}
	}else{
		applicantInfo = $("#applicantInfo").serialize();
		applicantInfo.id = staffId;

		if(status == 2){
			//左箭头跳转 添加不可编辑
			window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId+'&isDisable='+isDisable;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
				}
			});
		}else if(status == 3){
			//右箭头跳转
			window.location.href = '/admin/bigCustomer/updateVisaInfo.html?staffId='+staffId+'&isDisable='+isDisable;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
					if(data>0){
						closeWindow();
						parent.successCallback();
					}
				}
			});
		}
		layer.closeAll("loading");
	}
}*/

$("#nationalidentificationnumber").on('input',function(){
	$(".countryNum").attr("class", "countryNum has-success");  
    $(".help-countryNum").attr("data-bv-result","IVALID");  
    $(".help-countryNum").attr("style","display: none;");  
    $("#nationalidentificationnumber").attr("style", null);
});
$("#nationalidentificationnumberen").on('input',function(){
	$(".countryNumen").attr("class", "countryNumen has-success");  
    $(".help-countryNumen").attr("data-bv-result","IVALID");  
    $(".help-countryNumen").attr("style","display: none;");  
    $("#nationalidentificationnumberen").attr("style", null);
});

$("#socialsecuritynumber").on('input',function(){
	$(".safeNum").attr("class", "safeNum has-success");  
    $(".help-blocksafe").attr("data-bv-result","IVALID");  
    $(".help-blocksafe").attr("style","display: none;");  
    $("#socialsecuritynumber").attr("style", null);
});
$("#socialsecuritynumberen").on('input',function(){
	$(".safeNumen").attr("class", "safeNumen has-success");  
    $(".help-blocksafeen").attr("data-bv-result","IVALID");  
    $(".help-blocksafeen").attr("style","display: none;");  
    $("#socialsecuritynumberen").attr("style", null);
});

$("#taxpayernumber").on('input',function(){
	$(".safepay").attr("class", "safepay has-success");  
    $(".help-ratepaying").attr("data-bv-result","IVALID");  
    $(".help-ratepaying").attr("style","display: none;");  
    $("#taxpayernumber").attr("style", null);
});
$("#taxpayernumberen").on('input',function(){
	$(".safepayen").attr("class", "safepayen has-success");  
    $(".help-ratepayingen").attr("data-bv-result","IVALID");  
    $(".help-ratepayingen").attr("style","display: none;");  
    $("#taxpayernumberen").attr("style", null);
});