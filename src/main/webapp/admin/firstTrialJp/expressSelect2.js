$(function() {
	$("#receiver").select2({
		ajax : {
			url : BASE_PATH+'/admin/firstTrialJp/getRAddressSelect.html',
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					searchStr : params.term, // search term
					type: "usernameType",
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.id; // replace pk with your identifier
					obj.text = obj.receiver; // replace pk with your identifier
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

	$("#mobile").select2({
		ajax : {
			url : BASE_PATH+'/admin/firstTrialJp/getRAddressSelect.html',
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					searchStr : params.term, // search term
					type: "mobileType",
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
});

$("#receiver").on('select2:select', function (evt) {
	var addressId = $(this).select2("val");
	$("#receiveAddressId").val(addressId);
	$.ajax({
		type : 'POST',
		data : {
			"addressId":$("#receiveAddressId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/firstTrialJp/getRAddressById.html',
		success : function(data) {
			$("#receiveAddressId").val(data.id);
			$("#address").val(data.address);
			$("#mobile").append('<option selected="true" value='+ addressId +'>'+data.mobile+'</option>'); 
		},
		error : function() {
		}
	});

});
$("#mobile").on('select2:select', function (evt) {
	var addressId = $(this).select2("val");
	$("#receiveAddressId").val(addressId);
	$.ajax({
		type : 'POST',
		data : {
			"addressId":$("#receiveAddressId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/firstTrialJp/getRAddressById.html',
		success : function(data) {
			$("#receiveAddressId").val(data.id);
			$("#address").val(data.address);
			$("#receiver").append('<option selected="true" value='+ addressId +'>'+data.receiver+'</option>'); 
		},
		error : function() {
		}
	});

});

/* 取消时 */
$("#receiver").on('select2:unselect', function (evt) {
	clearText();
}); 
$("#mobile").on('select2:unselect', function (evt) {
	clearText();
}); 

function clearText(){
	$("#receiver").val(null).trigger("change");
	$("#mobile").val(null).trigger("change");
	$("#receiveAddressId").val("");
	$("#address").val("");
}

//保存
function save(orderid){
	var receiveAddress = $("#receiveAddressId").val();
	if (receiveAddress == "") {
		layer.msg('收件人信息不能为空');
		return;
	}
	var layerIndex =  layer.load(1, {shade: "#000"});
	$.ajax({ 
		url: BASE_PATH+'/admin/firstTrialJp/saveExpressInfo.html',
		type:'post',
		data:{
			orderid:orderid,
			expresstype:$("#express").val(),
			receiveAddressId:$("#receiveAddressId").val()
		},
		success: function(data){
			if(data.stauts == 200){
				layer.close(layerIndex);
			}
			closeWindow();
		}
	});
}


/* $('#multiPass_roundTrip').bootstrapSwitch();//统一联系人
$('#multiPass_roundTrip').on('switchChange.bootstrapSwitch', function (event,state) { 
    if(state){//统一
    	console.log("统一");
    	event.target.parentNode.parentNode.style="background-color:#3087f1 !important";
    } else {//不统一
    	console.log("不统一");
    	event.target.parentNode.parentNode.style="background-color:#ddd !important";
    }
}); */

//快递下拉框
/* $("#express").change(function(){
	var expressVal = $(this).val();
	if(expressVal == "1"){//快递
		$(".form-div").show();
	 	$("#tableId").show();
	 	$("#tableId").find("th:eq(5)").show();//显示统一联系人
	 	$("#tableId").find("td:eq(5)").show();
	}else if(expressVal == "2"){//门店送
		$(".form-div").show();
	 	$("#tableId").show();
		$("#tableId").find("th:eq(5)").hide();//隐藏统一联系人
		$("#tableId").find("td:eq(5)").hide();
	}else{//自送
		$(".form-div").hide();
	 	$("#tableId").hide();
	}

}); */


