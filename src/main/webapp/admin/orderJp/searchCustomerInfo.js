function customerTypeSelect2(){
/* 加载客户姓名下拉 */
$("#linkman").select2({
	ajax : {
		url : 'getLinkNameSelect',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				linkman : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.linkman; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

/* 加载公司全称下拉 */
$("#compName").select2({
	ajax : {
		url : 'getcompNameSelect',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				compName : params.term, // search term
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
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

/* 加载公司简称下拉 */
$("#comShortName").select2({
	ajax : {
		url : 'getComShortNameSelect',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				comShortName : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.shortname; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

/* 加载邮箱下拉 */
$("#email").select2({
	ajax : {
		url : 'getEmailSelect',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				email : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.email; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});



/* 加载电话下拉 */
$("#mobile").select2({
	ajax : {
		url : 'getPhoneNumSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				mobile : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.mobile; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});

/* 选中联系人 */
$("#linkman").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	var id = parseInt($("#linkman").val());
	$.ajax({
		url : 'getCustomerById',
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			//var dataJson = jQuery.parseJSON(data); 
			var sourceName;
			if(data.source == 1){
				sourceName = "线上"
			}
			if(data.source == 2){
				sourceName = "OTS"
			}
			if(data.source == 3){
				sourceName = "线下"
			}
			var phoneNum = data.mobile;
			/* 电话补全 */
			$("#mobile").append('<option selected="true" value='+ id +'>'+phoneNum+'</option>'); 
			/*公司全称补全*/
			$("#compName").append('<option selected="true" value='+ id +'>'+data.name+'</option>'); 
			/*公司简称补全*/
			$("#comShortName").append('<option selected="true" value='+ id +'>'+data.shortname+'</option>');
			/*邮箱补全*/
			$("#email").append('<option selected="true" value='+ id +'>'+data.email+'</option>');
			/*客户来源补全*/
			$("#payType").val(data.payType);
			$("#customerType").val(data.source);
			/*if(data.isArrearsRed){
				$('#fontLSqk').css("color","red");
				$("#custInfoName").css("color","red");
			}*/
			
			/*var id = dataJson.customerInfoEntity.id;
			var payType = dataJson.customerInfoEntity.payType;
			var creditLine = dataJson.customerInfoEntity.creditLine;
			var arrears = dataJson.customerInfoEntity.arrears;
			var preDeposit = dataJson.customerInfoEntity.preDeposit;
			$("#addressId").val(dataJson.customerInfoEntity.address);
			$("#shortNameId").val(dataJson.customerInfoEntity.shortName);
			$("#responsibleId").val(dataJson.responsibleName);
			$("#siteUrlId").val(dataJson.customerInfoEntity.siteUrl);
			$("#faxId").val(dataJson.customerInfoEntity.fax);
			$("#outCityName").val(dataJson.customerInfoEntity.outCityName);
			
			票价折扣
			$("#discountHidden").val(dataJson.customerInfoEntity.discountFare);
			手续费
			$("#feeHidden").val(dataJson.customerInfoEntity.fees);
			汇率
			$("#ratesHidden").val(dataJson.customerInfoEntity.exchangeRates);
			
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
			if(creditLine){
				$("#creditLineId").html(dataJson.customerInfoEntity.creditLine);
			}else{
				$("#creditLineId").html("0.00");
			}
			if(arrears){
				$("#arrearsId").html(dataJson.customerInfoEntity.arrears);
			}else{
				$("#arrearsId").html("0.00");
			}
			if(preDeposit){
				$("#preDepositId").html(dataJson.customerInfoEntity.preDeposit);
			}else{
				$("#preDepositId").html("0.00");
			}*/
		},
		error : function() {
		}
	});

});

/* 选中电话 */
$("#mobile").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	var id = parseInt($("#mobile").val());
	$.ajax({
		url : 'getCustomerById',
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			var sourceName;
			if(data.source == 1){
				sourceName = "线上"
			}
			if(data.source == 2){
				sourceName = "OTS"
			}
			if(data.source == 3){
				sourceName = "线下"
			}
			/* 联系人补全 */
			$("#linkman").append('<option selected="true" value='+ id +'>'+data.linkman+'</option>'); 
			/*公司全称补全*/
			$("#compName").append('<option selected="true" value='+ id +'>'+data.name+'</option>'); 
			/*公司简称补全*/
			$("#comShortName").append('<option selected="true" value='+ id +'>'+data.shortname+'</option>');
			/*邮箱补全*/
			$("#email").append('<option selected="true" value='+ id +'>'+data.email+'</option>');
			/*客户来源补全*/
			$("#customerType").val(data.source);
			$("#payType").val(data.payType);
		},
		error : function() {
		}
	});

});
/* 选中公司全称 */
$("#compName").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	var id = parseInt($("#compName").val());
	$.ajax({
		url : 'getCustomerById',
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			var sourceName;
			if(data.source == 1){
				sourceName = "线上"
			}
			if(data.source == 2){
				sourceName = "OTS"
			}
			if(data.source == 3){
				sourceName = "线下"
			}
			/* 联系人补全 */
			$("#linkman").append('<option selected="true" value='+ id +'>'+data.linkman+'</option>'); 
			/*电话补全*/
			$("#mobile").append('<option selected="true" value='+ id +'>'+data.mobile+'</option>'); 
			/*公司简称补全*/
			$("#comShortName").append('<option selected="true" value='+ id +'>'+data.shortname+'</option>');
			/*邮箱补全*/
			$("#email").append('<option selected="true" value='+ id +'>'+data.email+'</option>');
			/*客户来源补全*/
			$("#customerType").val(data.source);
			$("#payType").val(data.payType);
		},
		error : function() {
		}
	});

});

/* 选中公司简称 */
$("#comShortName").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	var id = parseInt($("#comShortName").val());
	$.ajax({
		url : 'getCustomerById',
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			var sourceName;
			if(data.source == 1){
				sourceName = "线上"
			}
			if(data.source == 2){
				sourceName = "OTS"
			}
			if(data.source == 3){
				sourceName = "线下"
			}
			/* 联系人补全 */
			$("#linkman").append('<option selected="true" value='+ id +'>'+data.linkman+'</option>'); 
			/*公司全称补全*/
			$("#compName").append('<option selected="true" value='+ id +'>'+data.name+'</option>'); 
			/*电话补全*/
			$("#mobile").append('<option selected="true" value='+ id +'>'+data.mobile+'</option>');
			/*邮箱补全*/
			$("#email").append('<option selected="true" value='+ id +'>'+data.email+'</option>');
			/*客户来源补全*/
			$("#customerType").val(data.source);
			$("#payType").val(data.payType);
		},
		error : function() {
		}
	});

});

/* 选中邮箱 */
$("#email").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	var id = parseInt($("#email").val());
	$.ajax({
		url : 'getCustomerById',
		type : 'POST',
		data : {
			'id' : id
		},
		dataType:'json',
		success : function(data) {
			var sourceName;
			if(data.source == 1){
				sourceName = "线上"
			}
			if(data.source == 2){
				sourceName = "OTS"
			}
			if(data.source == 3){
				sourceName = "线下"
			}
			/* 联系人补全 */
			$("#linkman").append('<option selected="true" value='+ id +'>'+data.linkman+'</option>'); 
			/*公司全称补全*/
			$("#compName").append('<option selected="true" value='+ id +'>'+data.name+'</option>'); 
			/*公司简称补全*/
			$("#comShortName").append('<option selected="true" value='+ id +'>'+data.shortname+'</option>');
			/*电话补全*/
			$("#mobile").append('<option selected="true" value='+ id +'>'+data.mobile+'</option>');
			/*客户来源补全*/
			$("#customerType").val(data.source);
			$("#payType").val(data.payType);
		},
		error : function() {
		}
	});

});

/*客户信息 清除回显内容*/
function clearText(){
	//客户姓名清空
	$("#linkman").val(null).trigger("change");
	//电话清空
	$("#mobile").val(null).trigger("change");
	//公司全称
	$("#compName").val(null).trigger("change");
	//公司简称
	$("#comShortName").val(null).trigger("change");
	//邮箱清空
	$("#email").val(null).trigger("change");
	$("#payType").val("");
}

/* 取消选中时 */
$("#linkman").on('select2:unselect', function (evt) {
	clearText();
}); 
$("#mobile").on('select2:unselect', function (evt) {
	clearText();
});
$("#compName").on('select2:unselect', function (evt) {
	clearText();
});
$("#comShortName").on('select2:unselect', function (evt) {
	clearText();
});
$("#email").on('select2:unselect', function (evt) {
	clearText();
});


/* 清除按钮 */
$("#clearBtn").click(function(){
	clearText();
});


}