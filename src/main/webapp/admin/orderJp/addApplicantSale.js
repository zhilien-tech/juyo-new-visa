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

//连接websocket
connectWebSocket();
function connectWebSocket(){
	 if ('WebSocket' in window){  
        console.log('Websocket supported');  
        socket = new WebSocket('ws://'+localAddr+':'+localPort+'/'+websocketaddr);   

        console.log('Connection attempted');  

        socket.onopen = function(){  
             console.log('Connection open!');  
             //setConnected(true);  
         };

        socket.onclose = function(){
            console.log('Disconnecting connection'); 
        };

        socket.onmessage = function (evt){
              var received_msg = evt.data;
              var sessionid = sessionId;
              if(received_msg){
                  var receiveMessage = JSON.parse(received_msg);
                  if(receiveMessage.messagetype == 4 && sessionid == receiveMessage.sessionid){
                	  window.parent.document.getElementById('orderid').value = receiveMessage.orderid;
                	  var appid = window.parent.document.getElementById('appId').value;
                	  appid +=  receiveMessage.applicantid+',';
                	  window.parent.document.getElementById('appId').value = appid;
                	  window.parent.successCallBack(5,receiveMessage);
                	  window.location.href = '/admin/orderJp/updateApplicant.html?id='+receiveMessage.applicantid+'&orderid&isTrial=0';
                	  socket.onclose();
                  }
              }
              console.log('message received!');  
              //showMessage(received_msg);  
         };  

      } else {  
        console.log('Websocket not supported');  
      }  
}
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
					liStr += "<li onclick='setNationality("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#nationality").after(liStr);
			}
		}
	});
});
//国籍上下键控制
//写成公共方法
var index = -1;
$(document).on('keydown','#nationality',function(e){
	var lilength = $(this).next().children().length;
		if(e == undefined)
			e = window.event;
		
		switch(e.keyCode){
		case 38:
			e.preventDefault();
			index--;
			if(index == 0) index = 0;
			break;
		case 40:
			e.preventDefault();
			index++;
			if(index == lilength) index = 0;
			break;
		case 13:
			
			$(this).val($('#ui-id-1').find('li:eq('+index+')').children().html());
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$("#nationality").blur();
			var nationality = $("#nationality").val();
			setNationality(nationality);
			index = -1;
			break;
		}
		var li = $('#ui-id-1').find('li:eq('+index+')');
		li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
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
					liStr += "<li onclick='setProvince("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#province").after(liStr);
			}
		}
	});
});
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
					liStr += "<li onclick='setCity("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
				});
				liStr += "</ul>";
				$("#city").after(liStr);
			}
		}
	});
});
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
	if(checked == 1){
		$(".nameBeforeHide").show();
		$(".wordSpell").show();
	}else {
		
		$(".nameBeforeHide").hide();
		$(".wordSpell").hide();
	}
});
//曾用国籍
$(".onceID").change(function(){
	let checked = $("input[name='hasOtherNationality']:checked").val();
	if(checked == 1){
		$(".nationalityHide").show();
	}else {
		
		$(".nationalityHide").hide();
	}
});	

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
		//获取必填项信息
		var firstName = $("#firstName").val();
		if (firstName == "") {
			layer.msg('姓不能为空');
			return;
		}
		var lastName = $("#lastName").val();
		if (lastName == "") {
			layer.msg('名不能为空');
			return;
		}
		var str="";     
	    $("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
	    	str=$(this).val();     
	    });     
		if(str != 1){
			var applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
		}else{
			var applicantInfo = $("#applicantInfo").serialize();
		}
		
		
		$.ajax({
			type : 'POST',
			data : applicantInfo,
			async : false,
			url : BASE_PATH+'/admin/orderJp/saveAddApplicant',
			success : function(data) {
				var applicantIdParent = window.parent.document.getElementById("appId").value;
				applicantIdParent += data.id +",";
				window.parent.document.getElementById("appId").value = applicantIdParent;
				$("#applyId").val(data.id);
				layer.closeAll('loading');
				if(status == 1){
					parent.successCallBack(3,data);
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				}
				if(status == 2){
					//console.log(data);
					parent.successCallBack(4,data);
					var applyId = $("#applyId").val();
					socket.onclose();
					window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applyId+'&orderid'+'&isTrial=0&orderProcessType='+orderProcessType+'&addApply=1';
				}
			},
			error : function() {
				console.log("error");
			}
		}); 
	}
	
}
