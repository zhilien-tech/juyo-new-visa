$(function() {
	//校验
	$('#passportInfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			firstname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '姓不能为空'
					}
				}
			},
			lastname : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '名不能为空'
					}
				}
			},
			passport : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '护照号不能为空'
					},
					stringLength: {
                   	    min: 1,
                   	    max: 9,
                   	    message: '护照号不能超过9位'
                   	},
					remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
						url: '/admin/bigCustomer/checkPassport.html',
						async:false,
						message: '护照号已存在，请重新输入',//提示消息
						delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
						type: 'POST',//请求方式
						//自定义提交数据，默认值提交当前input value
						data: function(validator) {
							return {
								passport:$('#passport').val(),
								passportId:$('#id').val()
							};
						}
					}
				}
			},
			birthaddress : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '出生地点不能为空'
					}
				}
			},
			birthday : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '出生日期不能为空'
					}
				}
			},
			issuedplace : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '签发地点不能为空'
					}
				}
			},
			issueddate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '签发日期不能为空'
					}
				}
			},
			validenddate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '有效日期不能为空'
					}
				}
			}
		}
	});
	//$('#passportInfo').bootstrapValidator('validate');
	//护照图片验证
	var passportUrl = $("#passportUrl").val();
	if(passportUrl == ""){
		$(".front").attr("class", "info-imgUpload front has-error");  
		$(".help-blockFront").attr("data-bv-result","INVALID");
		$("#uploadFile").siblings("i").css("display","none");
		//$(".help-blockFront").attr("style","display: block;");  
		//$("#borderColor").attr("style", "border-color:#ff1a1a");
	}else{
		$(".front").attr("class", "info-imgUpload front has-success");  
		$(".help-blockFront").attr("data-bv-result","IVALID");  
		$(".help-blockFront").attr("style","display: none;");
		$("#borderColor").attr("style", null);
		$("#uploadFile").siblings("i").css("display","block");
	}


	if($("#sex").val() == "男"){
		$("#sexEn").val("M");
	}else{
		$("#sexEn").val("F");
	}

	$("#sex").change(function(){
		var sex = $(this).val();
		if(sex == "男"){
			$("#sexEn").val("M");
		}else{
			$("#sexEn").val("F");
		}
	});

	$("#issuedDate").change(function(){
		if($("#issuedDate").val() != ""){
			if($("#validType").val() == 1){
				$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10)).change();
			}else{
				$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5)).change();
			}
		}
	});

	$("#validType").change(function(){
		var type = $(this).val();
		if(type == 1){
			$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10)).change();
		}else{
			$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5)).change();
		}

	});
});

function passportValidate(){
	//护照图片验证
	var passportUrl = $("#passportUrl").val();
	if(passportUrl == ""){
		$("#borderColor").attr("style", "border-color:#ff1a1a");  
		$(".front").attr("class", "info-imgUpload front has-error");  
        $(".help-blockFront").attr("data-bv-result","INVALID");  
        $(".help-blockFront").attr("style","display: block;");  
	}else{
		$("#borderColor").attr("style", null);
		$(".front").attr("class", "info-imgUpload front has-success");  
        $(".help-blockFront").attr("data-bv-result","IVALID");  
        $(".help-blockFront").attr("style","display: none;");  
	}
}

function getNewDates(dateTemp, days){
	var d1 = new Date(dateTemp);
	var d2 = new Date(d1);
	d2.setFullYear(d2.getFullYear()+days);
	d2.setDate(d2.getDate()-1);
	var year = d2.getFullYear();  
	var month = d2.getMonth() + 1;  
	if (month < 10) month = "0" + month;  
	var date = d2.getDate();  
	if (date < 10) date = "0" + date;  
	return (year + "-" + month + "-" + date);
}

function getNewDay(dateTemp, days) {  
	var dateTemp = dateTemp.split("-");  
	var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
	var millSeconds = Math.abs(nDate) + (days * 365.2 * 24 * 60 * 60 * 1000);  
	var rDate = new Date(millSeconds);  
	var year = rDate.getFullYear();  
	var month = rDate.getMonth() + 1;  
	if (month < 10) month = "0" + month;  
	var date = rDate.getDate();  
	if (date < 10) date = "0" + date;  
	return (year + "-" + month + "-" + date);  
}

function returnYears(year){
	if(((year%4==0)&&(year%100!=0))||(year%400==0)){
		return 366;
	}else{
		return 365; 
	}
}

function getPinYinStr(hanzi){
	var onehanzi = hanzi.split('');
	var pinyinchar = '';
	for(var i=0;i<onehanzi.length;i++){
		pinyinchar += PinYin.getPinYin(onehanzi[i]);
	}
	return pinyinchar.toUpperCase();
}

function getDateYearSub(startDateStr, endDateStr) {
	var day = 24 * 60 * 60 *1000; 

	var sDate = new Date(Date.parse(startDateStr.replace(/-/g, "/")));
	var eDate = new Date(Date.parse(endDateStr.replace(/-/g, "/")));

	//得到前一天(算头不算尾)
	sDate = new Date(sDate.getTime() - day);

	//获得各自的年、月、日
	var sY  = sDate.getFullYear();     
	var sM  = sDate.getMonth()+1;
	var sD  = sDate.getDate();
	var eY  = eDate.getFullYear();
	var eM  = eDate.getMonth()+1;
	var eD  = eDate.getDate();

	if(eY > sY && sM == eM && sD == eD) {
		return eY - sY;
	} else {
		//alert("两个日期之间并非整年，请重新选择");
		return 0;
	}
}

function deleteApplicantFrontImg(){
	$('#passportUrl').val("");
	$('#sqImg').attr('src', "");
	$("#uploadFile").siblings("i").css("display","none");
}

function passValidate(){
	$('#passportInfo').bootstrapValidator('validate');
}

/**/

//护照上传,扫描

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
			url : '/admin/orderJp/passportRecognition',//请求地址  
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
					$('#firstName').val(obj.xingCn).change();
					$('#firstNameEn').val("/"+getPinYinStr(obj.xingCn));
					$('#lastName').val(obj.mingCn).change();
					$('#lastNameEn').val("/"+getPinYinStr(obj.mingCn));
					$('#passportUrl').val(obj.url);
					$('#sqImg').attr('src', obj.url);
					$("#uploadFile").siblings("i").css("display","block");
					$(".front").attr("class", "info-imgUpload front has-success");  
					$(".help-blockFront").attr("data-bv-result","IVALID");  
					$(".help-blockFront").attr("style","display: none;");
					$('#type').val(obj.type).change();
					$('#passport').val(obj.num).change();
					$('#sex').val(obj.sex);
					$('#sexEn').val(obj.sexEn);
					$('#birthAddress').val(obj.birthCountry).change();
					$('#birthAddressEn').val("/"+getPinYinStr(obj.birthCountry));
					$('#birthday').val(obj.birth).change();
					$('#issuedPlace').val(obj.visaCountry).change();
					$('#issuedPlaceEn').val("/"+getPinYinStr(obj.visaCountry));
					$('#issuedDate').val(obj.issueDate).change();
					$('#validEndDate').val(obj.expiryDay).change();
					$('#OCRline1').val(obj.OCRline1);
					$('#OCRline2').val(obj.OCRline2);
					$("#borderColor").attr("style", null);
					var years = getDateYearSub($('#issuedDate').val(),$('#validEndDate').val());
					if(years == 5){
						$("#validType").val(2);
					}else{
						$("#validType").val(1);
					}

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

//保存
function save(status){
	passportValidate();
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
	bootstrapValidator.validate();
	if($(".front").hasClass("has-error")){
		return;
	}
	//延时校验
	setTimeout(function(){
		if(bootstrapValidator.isValid()){
			var passportInfo = $("#passportInfo").serialize();
			if(status == 1){
				layer.load(1);
				$.ajax({
					type: 'POST',
					data : passportInfo,
					url: '/admin/bigCustomer/saveEditPassport',
					success :function(data) {
						console.log(data);
						if(data.status == 200){
							closeWindow();
							parent.successCallback(2);
						}
					}
				});

			}else if(status == 2){
				//基本信息
				window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable;
				$.ajax({
					type: 'POST',
					data : passportInfo,
					url: '/admin/bigCustomer/saveEditPassport',
					success :function(data) {
					}
				});
			}else if(status ==3){
				//拍摄资料
				window.location.href = '/admin/orderJp/visaInfo.html?id='+visaId+'&isDisable='+isDisable;
				$.ajax({
					type: 'POST',
					data : passportInfo,
					url: '/admin/orderJp/saveEditPassport',
					success :function(data) {
					}
				});
			}
		}
	}, 500);
}

function baseBtn(){
	save(2);
}

function visaBtn(){
	save(3);
}


//返回 
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
