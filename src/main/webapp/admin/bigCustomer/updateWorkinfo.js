
/*$(function(){
	workinfoValidate();
});*/

//翻译
function translateZhToEn(from, to, param){
	var toval = "";
	if(param == "select2"){
		toval = $(from).val();
		if(toval){
			toval = toval.join(",");
		}
	}else{
		if(param != ""){
			toval = param;
		}else{
			toval = $(from).val();
		}
	}
	$.ajax({
		//async : false,
		url : '/admin/neworderUS/baiduTranslate',
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

//日期相关
$(document).on("input","#workstartdate,#employstartdate,#employenddate,#coursestartdate,#courseenddate",function(){
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


function workinfoValidate(){
	$('#workinfo').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			addressen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '详细地址英文不能为空'
					},
					stringLength: {//检测长度
                        max: 80,
                        message: '详细地址英文不能超过80个字符'
                    }
				}
			},
			employeraddressen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '详细地址英文不能为空'
					},
					stringLength: {//检测长度
						max: 80,
						message: '详细地址英文不能超过80个字符'
					}
				}
			},
			institutionaddressen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '详细地址英文不能为空'
					},
					stringLength: {//检测长度
						max: 80,
						message: '详细地址英文不能超过80个字符'
					}
				}
			},
			unitname: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '工作单位名称不能为空'
					}
				}
			},
			unitnameen: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '工作单位名称英文不能为空'
					}
				}
			},
			telephone: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '电话号码不能为空'
					}
				}
			},
			country: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '工作国家不能为空'
					}
				}
			},
			province: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '省份不能为空'
					}
				}
			},
			city: {
				trigger: "change keyup",
				validators: {
					notEmpty: {
						message: '城市不能为空'
					}
				}
			},
			address : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '详细地址不能为空'
					}
				}
			},
			workstartdate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '入职日期不能为空'
					}
				}
			},
			position : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '职位不能为空'
					}
				}
			},
			salary : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '月收入不能为空'
					}
				}
			},
			duty : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '职责不能为空'
					}
				}
			},
			dutyen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '职责英文不能为空'
					}
				}
			},
			employername : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '单位名称不能为空'
					}
				}
			},
			employernameen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '单位名称英文不能为空'
					}
				}
			},
			employertelephone : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '电话号码不能为空'
					}
				}
			},
			employercountry : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国家不能为空'
					}
				}
			},
			employerprovince : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '省份不能为空'
					}
				}
			},
			employercity : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '城市不能为空'
					}
				}
			},
			employeraddress : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '详细地址不能为空'
					}
				}
			},
			employstartdate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '入职时间不能为空'
					}
				}
			},
			employenddate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '离职时间不能为空'
					}
				}
			},
			jobtitle : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '职务不能为空'
					}
				}
			},
			previousduty : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '职责不能为空'
					}
				}
			},
			highesteducation : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '最高学历不能为空'
					}
				}
			},
			institution : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '学校名称不能为空'
					}
				}
			},
			institutionen : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '学校名称英文不能为空'
					}
				}
			},
			institutioncountry : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '国家不能为空'
					}
				}
			},
			institutionprovince : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '省份不能为空'
					}
				}
			},
			institutioncity : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '城市不能为空'
					}
				}
			},
			institutionaddress : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '详细地址不能为空'
					}
				}
			},
			coursestartdate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '开始时间不能为空'
					}
				}
			},
			courseenddate : {
				trigger:"change keyup",
				validators : {
					notEmpty : {
						message : '结束时间不能为空'
					}
				}
			},
		}
	});
	//$('#workinfo').bootstrapValidator('validate');
}



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

/* 取消选中时 */
$("#jobprovince").on('select2:unselect', function (evt) {
	//市清空
	$("#city").val(null).trigger("change");
});
$("#employerprovince").on('select2:unselect', function (evt) {
	//市清空
	$("#employercity").val(null).trigger("change");
});
$("#institutionprovince").on('select2:unselect', function (evt) {
	//市清空
	$("#institutioncity").val(null).trigger("change");
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
	
	
	/*workinfoValidate();
	var bootstrapValidator = $("#workinfo").data('bootstrapValidator');
	// 执行表单验证 
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()){*/
		if(status == 2){
			//左箭头跳转 
			window.location.href = '/admin/neworderUS/updateFamilyInfo.html?staffid='+staffId;
			/*$.ajax({
				type: 'POST',
				data : familyinfo,
				url: '/admin/neworderUS/saveWorkandeducation.html',
				success :function(data) {
					parent.successCallback(2);
				}
			});*/
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
	//}
}

