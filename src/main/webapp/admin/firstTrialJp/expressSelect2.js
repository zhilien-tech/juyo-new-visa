
$(function() {

	/*	$("#applicant_tbody tr").each(function(i,ele_tr){
		var applicantId = $(this).children().eq(0).html();
		var ids = shareIds.split(",");
		$.each(ids, function(j,shareid){
			if(applicantId == shareid){
				$("#tableId tbody tr").eq(i).addClass("trColor");
			} 
		});
	});*/

	$(document).on("click",".tableTr",function(){
		var sharetype = $("#shareType").val();
		if(sharetype == 2){//单独分享
			if($(this).hasClass("trColor")){
				$(this).removeClass("trColor");
			}else{
				$(this).addClass("trColor");
			}
		}else if(sharetype == 1){//统一联系人
			$(this).addClass("trColor").siblings("tr").removeClass("trColor");
		}
	});

	$("#shareType").change(function(){
		$("#tableId tbody tr").each(function(){
			if($(this).hasClass("trColor")){
				$(this).removeClass("trColor");
			}
		});
	});

});
function clearText(){
	$("#receiver").val(null).trigger("change");
	$("#mobile").val(null).trigger("change");
	$("#receiver").val("");
	$("#mobile").val("");
	$("#address").val("");
}

//保存
function save(orderid,orderjpid){

	var receiver = $("#receiver").val();
	if (receiver == "") {
		layer.msg('收件人不能为空');
		return;
	}
	var mobile = $("#mobile").val();
	if (mobile == "") {
		layer.msg('电话不能为空');
		return;
	}
	var address = $("#address").val();
	if (address == "") {
		layer.msg('回邮地址不能为空');
		return;
	}

	/*	var applicant_tbody = $("#applicant_tbody").is(":empty");
	if (applicant_tbody) {
		layer.msg('申请人信息不能为空');
		return;
	}*/
	var applicant_share = true;
	var shareManIds = "";
	$("#tableId tbody tr").each(function(){
		if($(this).hasClass("trColor")){
			applicant_share = false;
			var applicantId = $(this).children().eq(0).html();
			shareManIds += applicantId + ",";
		}
	});
	if (applicant_share || shareManIds=="") {
		layer.msg('申请人未选择');
		return;
	}

	var layerIndex =  layer.load(1, {shade: "#000"});

	$.ajax({
		type : 'POST',
		data : {
			shareManIds:shareManIds
		},
		url : '/admin/firstTrialJp/checkExpressManInfo.html',
		success : function(data) {
			var isEmpty = data.isEmpty;
			if(isEmpty == false){
				var applyids = data.applyids;
				$.each(applyids, function(i, applyid) { 
					layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '80%'],
						content:'/admin/firstTrialJp/validExpressManInfo.html?applicantId='+applyid+'&orderId='+orderid,
						success : function(index, layero){
							var iframeWin = window[index.find('iframe')[0]['name']]; 
						}
					}); 
				}); 
				layer.close(layerIndex);
				return;
			}else{
				$.ajax({ 
					url: BASE_PATH+'/admin/firstTrialJp/saveExpressInfo.html',
					type:'post',
					data:{
						orderid:orderid,
						orderjpid:orderjpid,
						expresstype:$("#express").val(),
						sharetype:$("#shareType").val(),
						receiver:$("#receiver").val(),
						mobile:$("#mobile").val(),
						expressaddress:$("#address").val(),
						shareManIds:shareManIds
						//receiveAddressId:$("#receiveAddressId").val()
					},
					success: function(data){
						if(data.stauts == 200){
							layer.close(layerIndex);
						}
						closeWindow();
						parent.successCallBack(2);
					}
				});
			}
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

//收件人检索
$("#receiver").on('input',function(){
	$("#receiver").nextAll("ul.ui-autocomplete").remove();
	/*$("#mobile").val("");
	$("#address").val("");*/
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#receiver").val(),
			type:"usernameType"
		},
		url : BASE_PATH+'/admin/firstTrialJp/getRAddressSelect.html',
		success : function(data) {
			var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
			$.each(data,function(index,element) { 
				liStr += "<li onclick='setReceiveInfo("+JSON.stringify(element.receiver)+","+JSON.stringify(element.mobile)+","+JSON.stringify(element.address)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element.receiver+"</span></li>";
			});
			liStr += "</ul>";
			$("#receiver").after(liStr);

			$('.ui-menu-item').first().addClass('bg');
			$('.ui-menu-item').hover(function(){
				$(this).addClass('bg').siblings().removeClass('bg');
			});
		}
	});
})

//电话检索
$("#mobile").on('input',function(){
	/*$("#receiver").val("");
	$("#address").val("");*/
	$("#mobile").nextAll("ul.ui-autocomplete").remove();
	$.ajax({
		type : 'POST',
		async: false,
		data : {
			searchStr : $("#mobile").val(),
			type:"mobileType"
		},
		url : BASE_PATH+'/admin/firstTrialJp/getRAddressSelect.html',
		success : function(data) {
			var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
			$.each(data,function(index,element) { 
				liStr += "<li onclick='setReceiveInfo("+JSON.stringify(element.receiver)+","+JSON.stringify(element.mobile)+","+JSON.stringify(element.address)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element.mobile+"</span></li>";
			});
			liStr += "</ul>";
			$("#mobile").after(liStr);

			$('.ui-menu-item').first().addClass('bg');
			$('.ui-menu-item').hover(function(){
				$(this).addClass('bg').siblings().removeClass('bg');
			});
		}
	});
})

/*$("#address").on('input',function(){
	$("#receiver").val("");
	$("#mobile").val("");
})*/

//收件人 检索下拉项
function setReceiveInfo(receiver,mobile,address){
	$("#receiver").nextAll("ul.ui-autocomplete").remove();
	$("#mobile").nextAll("ul.ui-autocomplete").remove();
	$("#receiver").val(receiver);
	$("#mobile").val(mobile);
	$("#address").val(address);
}

$("#receiverDiv").mouseleave(function(){
	$("#receiver").nextAll("ul.ui-autocomplete").remove();
});

$('#mobileDiv').mouseleave(function(){  
	$("#mobile").nextAll("ul.ui-autocomplete").remove();
});  






/*$(function() {
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
});*/

/*$("#receiver").on('select2:select', function (evt) {
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
		$("#mobile option").remove(); 
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
		$("#receiver option").remove(); 
		$("#receiver").append('<option selected="true" value='+ addressId +'>'+data.receiver+'</option>'); 
	},
	error : function() {
	}
});

});

取消时 
$("#receiver").on('select2:unselect', function (evt) {
clearText();
}); 
$("#mobile").on('select2:unselect', function (evt) {
clearText();
}); */
