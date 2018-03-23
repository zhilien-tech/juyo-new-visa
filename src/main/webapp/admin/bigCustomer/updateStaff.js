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
	//身份证图片验证
	$('#applicantInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {

			firstname : {
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			lastname : {
				validators : {
					notEmpty : {
						message : '名不能为空'
					}
				}
			},
			telephone : {
				validators : {
					regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '手机号格式错误'
					}
				}
			},
			emergencyTelephone : {
				validators : {
					regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '手机号格式错误'
					}
				}
			},
			email : {
				validators : {
					regexp: {
						regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
						message: '邮箱格式错误'
					}
				}
			}
		}
	});
	$('#applicantInfo').bootstrapValidator('validate');
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
//国籍检索下拉项
function setNationality(nationality){
	$("#nationality").nextAll("ul.ui-autocomplete").remove();
	$("#nationality").val(nationality).change();
} 
$("#nationalityDiv").mouseleave(function(){
	$("#nationality").nextAll("ul.ui-autocomplete").remove();
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
					//是否同身份证相同
					$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
						str=$(this).val();     
					});     
					if(str == 1){//相同
						searchByCard();
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

//二寸免冠照片,扫描
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
					$('#imgInch').attr('src', obj.url);
					$("#uploadFileInchImg").siblings("i").css("display","block");
//					$(".front").attr("class", "info-imgUpload front has-success");  
					$(".help-blockFront").attr("data-bv-result","IVALID");  
					$(".help-blockFront").attr("style","display: none;");
					$("#borderColorFront").attr("style", null);
					$('#address').val(obj.address).change();
					$('#nation').val(obj.nationality).change();
					$('#cardId').val(obj.num).change();
					var str="";  
					//是否同身份证相同
					$("input:checkbox[name='addressIssamewithcard']:checked").each(function(){     
						str=$(this).val();     
					});     
					if(str == 1){//相同
						searchByCard();
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

//checkbox 曾用名
$(".usedBefore").change(function(){
	var usedBeforeVal = $("input[name='usedBefore']:checked").val();
	if(usedBeforeVal == 1){
		
		$(".usedBeforeTrue").show();
		$(".usedBeforeUS1").trigger("click");
	}else{
		$(".usedBeforeTrue").hide();
		$(".usedBeforeUS2").trigger("click");
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
	var usedNationalityVal = $("input[name='usedNationality']:checked").val();
	if(usedNationalityVal == 1){
		
		$(".usedNationalityTrue").show();
		$(".usedNationalityUS1").trigger("click");
	}else{
		$(".usedNationalityTrue").hide();
		$(".usedNationalityUS2").trigger("click");
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
	var permanentVal = $("input[name='permanent']:checked").val();
	if(permanentVal == 1){
		
		$(".permanentTrue").show();
		$(".permanentUS1").trigger("click");
	}else{
		$(".permanentTrue").hide();
		$(".permanentUS2").trigger("click");
	}
});
$(".permanentUS1").click(function(){
	$(".permanentUSTrue").show();
});
$(".permanentUS2").click(function(){
	$(".permanentUSTrue").hide();
});
//居住地与身份证相同
$(".nowProvince").change(function(){
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
});

function searchByCard(){

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
			$("#detailedAddress").val($("#address").val()).change();
			if(data != null){
				$("#province").val(data.province).change();
				$("#city").val(data.city).change();
			}
		}
	});

}

//更新保存基本信息
function saveApplicant(status){
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
}

function saveApplicant(status){
	$("#applicantInfo").data('bootstrapValidator').destroy();
	$("#applicantInfo").data('bootstrapValidator', null);
	applyValidate();
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#applicantInfo").data(
	'bootstrapValidator');
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
			//右箭头跳转
			window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId;
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
						parent.successCallback(2);
						closeWindow();
					}
				}
			});
		}
		layer.closeAll("loading");
	}
}