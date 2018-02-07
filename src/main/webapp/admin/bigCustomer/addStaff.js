function addStaff(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/bigCustomer/addStaff.html'
	});
}

$(function(){
	$('#applicantInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
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
});

function applyValidate(){
	//校验
	$('#applicantInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {

			firstName : {
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			lastName : {
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
						message: '电话号格式错误'
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
};

//居住地与身份证相同
$(".nowProvince").change(function(){
	searchByCard();
});

$("#cardId").change(function(){
	searchByCard();
});

function searchByCard(){
	var str=""; 
	//是否同身份证相同
	$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
		str=$(this).val();     
	});     
	if(str == 1){//相同
		var cardId = $("#cardId").val();
		layer.load(1);
		$.ajax({
			type: 'POST',
			data : {
				cardId : cardId
			},
			dataType : 'json',
			url: BASE_PATH+'/admin/orderJp/getInfoByCard',
			success :function(data) {
				console.log(JSON.stringify(data));
				layer.closeAll('loading');
				$("#province").val(data.province);
				$("#city").val(data.city);
				$("#detailedAddress").val($("#address").val());
			}
		});
	}else{
		$("#province").val("");
		$("#city").val("");
		$("#detailedAddress").val("");
	}
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
	$("#nationality").val(nationality);
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
	$("#province").val(province);
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
	$("#city").val(city);
} 
$("#cityDiv").mouseleave(function(){
	$("#city").nextAll("ul.ui-autocomplete").remove();
});

//正面上传,扫描
$('#uploadFile').change(function() {
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
					$('#sqImg').attr('src', obj.url);
					$("#uploadFile").siblings("i").css("display","block");
					$('#address').val(obj.address);
					$('#nation').val(obj.nationality);
					$('#cardId').val(obj.num);
					searchByCard();
					$('#cardProvince').val(obj.province);
					$('#cardCity').val(obj.city);
					$('#birthday').val(obj.birth);
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
$('#uploadFileBack').change(function() {
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
					$('#sqImgBack').attr('src', obj.url);
					$("#uploadFileBack").siblings("i").css("display","block");
					$('#validStartDate').val(obj.starttime);
					$('#validEndDate').val(obj.endtime);
					$('#issueOrganization').val(obj.issue);
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
$(".nameBefore").change(function(){
	let checked = $("input[name='hasOtherName']:checked").val();
	let checked2 = $("input[name='hasOtherNationality']:checked").val();
	if(checked == 1){
		$(".nameBeforeTop").css('float','none');
		$(".nameBeforeHide").show();
		$(".wordSpell").show();
		$(".onceIDTop").removeClass('col-sm-offset-1');
		$(".onceIDTop").css('margin-left','45px');
	}else {
		
		$(".nameBeforeHide").hide();
		$(".wordSpell").hide();
		if(checked2 == 1){
			
		}else {
			$(".nameBeforeTop").css('float','left');
			$(".onceIDTop").css('margin-left','0px');
		}
	}
});
//曾用国籍
$(".onceID").change(function(){
	let checked = $("input[name='hasOtherNationality']:checked").val();
	let checked2 = $("input[name='hasOtherName']:checked").val();
	if(checked == 1){
		$(".nameBeforeTop").css('float','none');
		$(".nationalityHide").show();
		$(".onceIDTop").css('float','left');
		$(".onceIDTop").removeClass('col-sm-offset-1');
		$(".onceIDTop").css('margin-left','45px');
	}else {
		
		$(".nationalityHide").hide();
		if(checked2 == 1){
			
		}else {
			$(".nameBeforeTop").css('float','left');
			$(".onceIDTop").css('margin-left','0px');
		}
	}
});	

