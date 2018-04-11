$(function(){
	//校验
	applyValidate();

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
});

function applyValidate(){
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
			cardnum : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},
			cardnumen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '卡号不能为空'
					}
				}
			},
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
			detailedaddressen : {
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

function translateZhToEn(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	$.ajax({
		//async : false,
		url : BASE_PATH+'/admin/translate/translate',
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


//国籍检索
$("#nationality").on('input',function(){
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
					liStr += "<li onclick='setNationality("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
				});
				liStr += "</ul>";
				$("#nationality").after(liStr);
			}
		}
	});
});
$("#nationalityen").on('input',function(){
	$("#nationalityen").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#nationalityen").val()
		},
		url : BASE_PATH+'/admin/orderJp/getNationality.html',
		success : function(data) {
			if(data != ""){
				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
				$.each(data,function(index,element) { 
					liStr += "<li onclick='setNationalityen("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
				});
				liStr += "</ul>";
				$("#nationalityen").after(liStr);
			}
		}
	});
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
					liStr += "<li onclick='setProvince("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
				});
				liStr += "</ul>";
				$("#province").after(liStr);
			}
		}
	});
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
					liStr += "<li onclick='setCity("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
				});
				liStr += "</ul>";
				$("#city").after(liStr);
			}
		}
	});
});

//市 检索下拉项
function setCity(city){
	$("#city").nextAll("ul.ui-autocomplete").remove();
	$("#city").val(city).change();
} 
$("#cityDiv").mouseleave(function(){
	$("#city").nextAll("ul.ui-autocomplete").remove();
});

//正面上传,扫描
$('#uploadFileImg').change(function() {
	var layerIndex = layer.load(1, {
		shade : "#000"
	});
	$("#addBtn").attr('disabled', true);
	$("#updateBtn").attr('disabled', true);
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl);
		var formData = new FormData();
		formData.append("image", blob, file.name);
		$.ajax({
			type : "POST",//提交类型  
			//dataType : "json",//返回结果格式  
			url : BASE_PATH + '/admin/orderJp/IDCardRecognition',//请求地址  
			async : true,
			processData : false, //当FormData在jquery中使用的时候需要设置此项
			contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
			//请求数据  
			data : formData,
			success : function(obj) {//请求成功后的函数 
				//关闭加载层
				layer.close(layerIndex);
				if (true === obj.success) {
					layer.msg("识别成功");
					$('#cardFront').val(obj.url);
					$('#imgShow').attr('src', obj.url);
					$("#uploadFileImg").siblings("i").css("display","block");
//					$(".front").attr("class", "info-imgUpload front has-success");  
					$(".help-blockFront").attr("data-bv-result","IVALID");  
					$(".help-blockFront").attr("style","display: none;");
					$("#borderColorFront").attr("style", null);
					$('#address').val(obj.address).change();
					$('#nation').val(obj.nationality).change();
					$('#cardId').val(obj.num).change();
					var str=""; 
					var stren="";
					//是否同身份证相同
					$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
						str=$(this).val();     
					});     
					if(str == 1){//相同
						searchByCard(1);
					}
					$("input:checkbox[name='addressIssamewithcarden']:checked").each(function(){     
						stren=$(this).val();     
					});     
					if(stren == 1){//相同
						searchByCard(2);
					}
					
					$('#cardProvince').val(obj.province).change();
					$('#cardCity').val(obj.city).change();
					$('#birthday').val(obj.birth).change();
					$('#sex').val(obj.sex);
				}
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.close(layerIndex);
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			}
		}); // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

//背面上传,扫描
$('#uploadFileImgBack').change(function() {
	var layerIndex = layer.load(1, {
		shade : "#000"
	});
	$("#addBtn").attr('disabled', true);
	$("#updateBtn").attr('disabled', true);
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl);
		var formData = new FormData();
		formData.append("image", blob, file.name);
		$.ajax({
			type : "POST",//提交类型  
			//dataType : "json",//返回结果格式  
			url : BASE_PATH + '/admin/orderJp/IDCardRecognitionBack',//请求地址  
			async : true,
			processData : false, //当FormData在jquery中使用的时候需要设置此项
			contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
			//请求数据  
			data : formData,
			success : function(obj) {//请求成功后的函数 
				//关闭加载层
				layer.close(layerIndex);
				if (true === obj.success) {
					layer.msg("识别成功");
					$('#cardBack').val(obj.url);
					$('#imgShowBack').attr('src', obj.url);
					$("#uploadFileImgBack").siblings("i").css("display","block");
//					$(".back").attr("class", "info-imgUpload back has-success");  
					$(".help-blockBack").attr("data-bv-result","IVALID");  
					$(".help-blockBack").attr("style","display: none;"); 
					$("#borderColorBack").attr("style", null);
					$('#validStartDate').val(obj.starttime).change();
					$('#validEndDate').val(obj.endtime).change();
					$('#issueOrganization').val(obj.issue).change();
				}
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.close(layerIndex);
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			}
		}); // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

//二寸免冠照片上传
$('#uploadFileInchImg').change(function() {
	var layerIndex = layer.load(1, {
		shade : "#000"
	});
	$("#addBtn").attr('disabled', true);
	$("#updateBtn").attr('disabled', true);
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl);
		var formData = new FormData();
		formData.append("image", blob, file.name);
		$.ajax({
			type : "POST",//提交类型  
			//dataType : "json",//返回结果格式  
			url : BASE_PATH + '/admin/company/uploadFile.html',//请求地址  
			async : true,
			processData : false, //当FormData在jquery中使用的时候需要设置此项
			contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
			//请求数据  
			data : formData,
			success : function(obj) {//请求成功后的函数 
				//关闭加载层
				layer.close(layerIndex);
				if ('200' === obj.status) {
					layer.msg("识别成功");
					$('#cardInch').val(obj.data);
					$('#imgInch').attr('src', obj.data);
					$("#uploadFileInchImg").siblings("i").css("display","block");
//					$(".front").attr("class", "info-imgUpload front has-success");  
					$(".help-blockInch").attr("data-bv-result","IVALID");  
					$(".help-blockInch").attr("style","display: none;");
					//$("#borderColorFront").attr("style", null);
				}
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.close(layerIndex);
				$("#addBtn").attr('disabled', false);
				$("#updateBtn").attr('disabled', false);
			}
		}); // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) {
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
			n);
	while (n--) {
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([ u8arr ], {
		type : mime
	});
}

//婚姻状况处理
//左边
if(marrystatus == 8 ){//其他有婚姻说明
	$(".marryexplain").show();
}else{
	$(".marryexplain").hide();
}

$("#marrystatus").change(function(){
	var status = $(this).val();
	$("#marrystatusen").val(status);
	if($("#marrystatusen").val() == 8 ){//其他有婚姻说明
		$(".marryexplainen").show();
	}else{
		$(".marryexplainen").hide();
	}
	if(status == 8 ){
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

//radio 曾用名
$(".usedBefore").change(function(){
	var usedBeforeVal = $("input[name='hasothername']:checked").val();
	if(usedBeforeVal == 1){
		
		$(".usedBeforeTrue").show();
		$("input[name='hasothernameen'][value='1']").trigger("click");
		//$(".usedBeforeUS1").trigger("click");
	}else{
		$(".usedBeforeTrue").hide();
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
//国家注册码
$(".isidentificationnumberapply").click(function(){
	if(this.checked){
		$("#nationalidentificationnumber").val("").change();
		$("#nationalidentificationnumber").attr("disabled",true);
		$(".isidentificationnumberapplyen").prop("checked",true);
		$("#nationalidentificationnumberen").val("").change();
		$("#nationalidentificationnumberen").attr("disabled",true);
		
		$(".countryNum").attr("class", "countryNum has-success");  
	    $(".help-countryNum").attr("data-bv-result","IVALID");  
	    $(".help-countryNum").attr("style","display: none;");  
	    $("#nationalidentificationnumber").attr("style", null);
	    
	    $(".countryNumen").attr("class", "countryNumen has-success");  
	    $(".help-countryNumen").attr("data-bv-result","IVALID");  
	    $(".help-countryNumen").attr("style","display: none;");  
	    $("#nationalidentificationnumberen").attr("style", null);
	}else{
		$("#nationalidentificationnumber").attr("disabled",false);
		$(".isidentificationnumberapplyen").prop("checked",false);
		$("#nationalidentificationnumberen").attr("disabled",false);
	}
});
$(".isidentificationnumberapplyen").click(function(){
	if(this.checked){
		$("#nationalidentificationnumberen").val("").change();
		$("#nationalidentificationnumberen").attr("disabled",true);
		
		$(".countryNumen").attr("class", "countryNumen has-success");  
	    $(".help-countryNumen").attr("data-bv-result","IVALID");  
	    $(".help-countryNumen").attr("style","display: none;");  
	    $("#nationalidentificationnumberen").attr("style", null);
	}else{
		$("#nationalidentificationnumberen").attr("disabled",false);
	}
});
//美国社会安全码
$(".issecuritynumberapplyen").click(function(){
	if(this.checked){
		$("#socialsecuritynumberen").val("").change();
		$("#socialsecuritynumberen").attr("disabled",true);
		
		$(".safeNumen").attr("class", "safeNumen has-success");  
	    $(".help-blocksafeen").attr("data-bv-result","IVALID");  
	    $(".help-blocksafeen").attr("style","display: none;");  
	    $("#socialsecuritynumberen").attr("style", null);
	}else{
		$("#socialsecuritynumberen").attr("disabled",false);
	}
});
$(".issecuritynumberapply").click(function(){
	if(this.checked){
		$("#socialsecuritynumber").val("").change();
		$("#socialsecuritynumber").attr("disabled",true);
		$(".issecuritynumberapplyen").prop("checked",true);
		$("#socialsecuritynumberen").val("").change();
		$("#socialsecuritynumberen").attr("disabled",true);
		
		$(".safeNum").attr("class", "safeNum has-success");  
	    $(".help-blocksafe").attr("data-bv-result","IVALID");  
	    $(".help-blocksafe").attr("style","display: none;");  
	    $("#socialsecuritynumber").attr("style", null);
	    
	    $(".safeNumen").attr("class", "safeNumen has-success");  
	    $(".help-blocksafeen").attr("data-bv-result","IVALID");  
	    $(".help-blocksafeen").attr("style","display: none;");  
	    $("#socialsecuritynumberen").attr("style", null);
	}else{
		$("#socialsecuritynumber").attr("disabled",false);
		$(".issecuritynumberapplyen").prop("checked",false);
		$("#socialsecuritynumberen").attr("disabled",false);
	}
});
//美国纳税人证件号
$(".istaxpayernumberapplyen").click(function(){
	if(this.checked){
		$("#taxpayernumberen").val("").change();
		$("#taxpayernumberen").attr("disabled",true);
		
		$(".safepayen").attr("class", "safepayen has-success");  
	    $(".help-ratepayingen").attr("data-bv-result","IVALID");  
	    $(".help-ratepayingen").attr("style","display: none;");  
	    $("#taxpayernumberen").attr("style", null);
	}else{
		$("#taxpayernumberen").attr("disabled",false);
	}
});
$(".istaxpayernumberapply").click(function(){
	if(this.checked){
		$("#taxpayernumber").val("").change();
		$("#taxpayernumber").attr("disabled",true);
		$(".istaxpayernumberapplyen").prop("checked",true);
		$("#taxpayernumberen").val("").change();
		$("#taxpayernumberen").attr("disabled",true);
		
		$(".safepay").attr("class", "safepay has-success");  
	    $(".help-ratepaying").attr("data-bv-result","IVALID");  
	    $(".help-ratepaying").attr("style","display: none;");  
	    $("#taxpayernumber").attr("style", null);
	    
	    $(".safepayen").attr("class", "safepayen has-success");  
	    $(".help-ratepayingen").attr("data-bv-result","IVALID");  
	    $(".help-ratepayingen").attr("style","display: none;");  
	    $("#taxpayernumberen").attr("style", null);
	}else{
		$("#taxpayernumber").attr("disabled",false);
		$(".istaxpayernumberapplyen").prop("checked",false);
		$("#taxpayernumberen").attr("disabled",false);
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
/*function saveApplicant(status){
	$("#applicantInfo").data('bootstrapValidator').destroy();
	$("#applicantInfo").data('bootstrapValidator', null);
	applyValidate();
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#applicantInfo").data('bootstrapValidator');
	// 执行表单验证 
	bootstrapValidator.validate();
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
		applicantInfo.id = staffId;

		if(status == 2){
			//向右跳转

		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/update.html',
				success :function(data) {
					layer.closeAll("loading");
					closeWindow();
					parent.successCallBack(2);
				}
			});
		}
	}
}*/

function saveApplicant(status){
	$("#applicantInfo").data('bootstrapValidator').destroy();
	$("#applicantInfo").data('bootstrapValidator', null);
	applyValidate();
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#applicantInfo").data(
	'bootstrapValidator');
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
	
	if($(".countryNum").hasClass("has-error")){
		return;
	}
	if($(".countryNumen").hasClass("has-error")){
		return;
	}
	if($(".safeNum").hasClass("has-error")){
		return;
	}
	if($(".safeNumen").hasClass("has-error")){
		return;
	}
	if($(".safepay").hasClass("has-error")){
		return;
	}
	if($(".safepayen").hasClass("has-error")){
		return;
	}
	
	if (bootstrapValidator.isValid()){

		/*var str="";
		var applicantInfo;
		$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
			str=$(this).val();     
		});
		if(str != 1){
			applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
		}else{
			applicantInfo = $("#applicantInfo").serialize();
		}*/
		applicantInfo = $("#applicantInfo").serialize();
		applicantInfo.id = staffId;

		if(status == 2){
			//左箭头跳转
			window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/bigCustomer/updateStaffInfo.html',
				success :function(data) {
				}
			});
		}else if(status == 3){
			//右箭头跳转
			window.location.href = '/admin/bigCustomer/updateVisaInfo.html?staffId='+staffId;
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
						//parent.successCallback(2);
						closeWindow();
					}
				}
			});
		}
		layer.closeAll("loading");
	}
}

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