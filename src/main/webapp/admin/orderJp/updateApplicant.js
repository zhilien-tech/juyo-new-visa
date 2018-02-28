$(function(){

	if(userType != 2){
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
							message: '手机号格式错误'
						}
					}
				},
				cardId : {
					validators : {
						regexp: {
							regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
							message: '身份证号格式错误'
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
	}else{
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
				cardId : {
					validators : {
						regexp: {
							regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
							message: '身份证号格式错误'
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
	}
	$('#applicantInfo').bootstrapValidator('validate');

	var remark = $("#baseRemark").val();
	if(remark != ""){
		$(".ipt-info").show();
	}

	//初审环节，显示合格不合格按钮
	if(isTrailOrder==1){
		$("#qualifiedBtn").show();
		$("#unqualifiedBtn").show();
		$("#baseRemark").attr("disabled", false);
	}else{
		$("#baseRemark").attr("disabled", true);
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
});

function applyValidate(){
	//身份证图片验证
	if(userType == 2){
		var cardFront = $("#cardFront").val();
		if(cardFront == ""){
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        $(".help-blockFront").attr("style","display: block;");  
	        $("#borderColorFront").attr("style", "border-color:#ff1a1a");
		}else{
			$(".front").attr("class", "info-imgUpload front has-success");  
	        $(".help-blockFront").attr("data-bv-result","IVALID");  
	        $(".help-blockFront").attr("style","display: none;");  
	        $("#borderColorFront").attr("style", null);
		}
		
		var cardBack = $("#cardBack").val();
		if(cardBack == ""){
			$(".back").attr("class", "info-imgUpload back has-error");  
	        $(".help-blockBack").attr("data-bv-result","INVALID");  
	        $(".help-blockBack").attr("style","display: block;");  
	        $("#borderColorBack").attr("style", "border-color:#ff1a1a");
		}else{
			$(".back").attr("class", "info-imgUpload back has-success");  
	        $(".help-blockBack").attr("data-bv-result","IVALID");  
	        $(".help-blockBack").attr("style","display: none;"); 
	        $("#borderColorBack").attr("style", null);
		}
	}
	
	if(userType != 2){
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
	}else{
		$('#applicantInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				
				firstName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '姓不能为空'
						}
					}
				},
				lastName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '名不能为空'
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
							message : '身份证不能为空'
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
				otherFirstName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '曾用姓不能为空'
						}
					}
				},
				otherLastName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '曾用名不能为空'
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
				birthday : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '出生日期不能为空'
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
				validStartDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效期限不能为空'
						}
					}
				},
				validEndDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效期限不能为空'
						}
					}
				},
				province : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '现居住地省份不能为空'
						}
					}
				},
				city : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '现居住地城市不能为空'
						}
					}
				},
				detailedAddress : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '详细地址不能为空'
						}
					}
				},
				emergencyTelephone : {
					trigger:"change keyup",
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
			}
		});
	} 
	$('#applicantInfo').bootstrapValidator('validate');
}

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
              if(received_msg){
                  var receiveMessage = JSON.parse(received_msg);
                  if(receiveMessage.applicantid == applicantId){
                	  if(receiveMessage.messagetype == 1){
	                	  window.location.reload();
                	  }else if(receiveMessage.messagetype == 2){
                		  window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrailOrder+'&orderProcessType='+orderProcessType;
                	  }
                  }
              }
              console.log('message received!');  
              //showMessage(received_msg);  
          };  

      } else {  
        console.log('Websocket not supported');  
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
					$(".front").attr("class", "info-imgUpload front has-success");  
			        $(".help-blockFront").attr("data-bv-result","IVALID");  
			        $(".help-blockFront").attr("style","display: none;");
			        $("#borderColorFront").attr("style", null);
					$('#address').val(obj.address).change();
					$('#nation').val(obj.nationality).change();
					$('#cardId').val(obj.num).change();
					var str="";  
					//是否同身份证相同
					$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
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
					$(".back").attr("class", "info-imgUpload back has-success");  
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
		$("#otherFirstName").val("").change();
		$("#otherFirstNameEn").val("");
		$("#otherLastName").val("").change();
		$("#otherLastNameEn").val("");
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
		$("#nationality").val("").change();
	}else {
		
		$(".nationalityHide").hide();
	}
});

//居住地与身份证相同
$(".nowProvince").change(function(){
	var str="";  
	//是否同身份证相同
	$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
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

//根据身份证号搜索是否有游客信息
$("#cardId").change(function(){
	if(userType == 2){
		$.ajax({
			type : "post",
			async : false,
			data : {
				applyId : applicantId
			},
			url : BASE_PATH + '/admin/myData/isPrompted.html',
			success :function(apply) {
				if(apply.base== 0){//没有提示过
					$.ajax({
						type : "post",
						data : {
							cardId : $("#cardId").val(),
							applicantId : applicantId
						},
						url : BASE_PATH + '/admin/myData/getTouristInfoByCard.html',
						success :function(data) {
							if(data.base){
								layer.confirm("您的信息已存在，是否使用？", {
									title:"提示",
									btn: ["是","否"], //按钮
									shade: false //不显示遮罩
								}, function(index){
									if(data.base != null){
										toSet(data);
										//$("#telephone").unbind("change");
										$("#telephone").val(data.base.telephone); 
										$("#telephone").next().next().css('display','none');
										$("#telephone").next().next().attr('data-bv-result','VALID');
										$("#telephone").next().attr('class','form-control-feedback glyphicon glyphicon-ok');
										$("#telephone").parent().attr('class','form-group has-feedback has-success');
										//$("#applicantInfo").data("bootstrapValidator").updateStatus("cardId",  "NOT_VALIDATED",  null );
										$('#applicantInfo').data('bootstrapValidator').enableFieldValidators('telephone', true);
										//var btn1Obj = document.getElementById("telephone");
										//btn1Obj.addEventListener("change",false);
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId,
												updateOrNot : "YES"
											},
											url : BASE_PATH + '/admin/myVisa/updateOrNot.html',
											success :function(data) {
												
											}
										});
										
										layer.load(1);
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId
											},
											url : BASE_PATH + '/admin/myVisa/copyAllInfoToPersonnel.html',
											success :function(data) {
												layer.closeAll("loading");
												layer.msg("已同步", {
    												time: 1000,
    												end: function () {
														layer.close(index);
    												}
    											});
											}
										});
									}
								},
								function(index){
									layer.close(index);
									$.ajax({
										type : "post",
										async : false,
										data : {
											applyid : applicantId,
											updateOrNot : "NO"
										},
										url : BASE_PATH + '/admin/myVisa/updateOrNot.html',
										success :function(data) {
											
										}
									});
								});
							}
						}
					});
				}else if(apply.base == 1){//提示过
					if(apply.updateIsOrNot == 1){//更新
						$.ajax({
							type : "post",
							async : false,
							data : {
								cardId : $("#cardId").val(),
								applicantId : applicantId
							},
							url : BASE_PATH + '/admin/myData/getTouristInfoByCard.html',
							success :function(data) {
								if(data.base != null){
									toSet(data);
									//$("#telephone").unbind("change");
									$("#telephone").val(data.base.telephone); 
									$("#telephone").next().next().css('display','none');
									$("#telephone").next().next().attr('data-bv-result','VALID');
									$("#telephone").next().attr('class','form-control-feedback glyphicon glyphicon-ok');
									$("#telephone").parent().attr('class','form-group has-feedback has-success');
									//$("#applicantInfo").data("bootstrapValidator").updateStatus("telephone",  "NOT_VALIDATED",  null );
									$('#applicantInfo').data('bootstrapValidator').enableFieldValidators('telephone', true);
									layer.load(1);
									$.ajax({
										type : "post",
										async : false,
										data : {
											applyid : applicantId
										},
										url : BASE_PATH + '/admin/myVisa/copyAllInfoToPersonnel.html',
										success :function(data) {
											layer.closeAll("loading");
											layer.msg("已同步", {
												time: 1000,
												end: function () {
												}
											});
										}
									});
								}
							}
						});
					
					}
				}
			}
		});
	}
});

//根据电话搜索是否有游客信息
$("#telephone").change(function(){
	if(userType == 2){
		$.ajax({
			type : "post",
			async : false,
			data : {
				applyId : applicantId
			},
			url : BASE_PATH + '/admin/myData/isPrompted.html',
			success :function(apply) {
				if(apply.base == 0){//没有提示过
					$.ajax({
						type : "post",
						async : false,
						data : {
							telephone : $("#telephone").val(),
							applicantId : applicantId
						},
						url : BASE_PATH + '/admin/myData/getTouristInfoByTelephone.html',
						success :function(data) {
							console.log(JSON.stringify(data));
							if(data.base){
								layer.confirm("您的信息已存在，是否使用？", {
									title:"提示",
									btn: ["是","否"], //按钮
									shade: false //不显示遮罩
								}, function(index){
									if(data.base != null){
										toSet(data);
										//$("#cardId").unbind("change");
										$("#cardId").val(data.base.cardId); 
										$("#cardId").next().next().css('display','none');
										$("#cardId").next().next().attr('data-bv-result','VALID');
										$("#cardId").next().attr('class','form-control-feedback glyphicon glyphicon-ok');
										$("#cardId").parent().attr('class','form-group has-feedback has-success');
										$('#applicantInfo').data('bootstrapValidator').enableFieldValidators('cardId', true);
										//$("#applicantInfo").data("bootstrapValidator").updateStatus("cardId",  "NOT_VALIDATED",  null );
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId,
												updateOrNot : "YES"
											},
											url : BASE_PATH + '/admin/myVisa/updateOrNot.html',
											success :function(data) {
												
											}
										});
										layer.load(1);
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId
											},
											url : BASE_PATH + '/admin/myVisa/copyAllInfoToPersonnel.html',
											success :function(data) {
												layer.closeAll("loading");
												layer.msg("已同步", {
    												time: 1000,
    												end: function () {
														layer.close(index);
    												}
    											});
											}
										});
									}
								},function(index){
									layer.close(index);
									$.ajax({
										type : "post",
										async : false,
										data : {
											applyid : applicantId,
											updateOrNot : "NO"
										},
										url : BASE_PATH + '/admin/myVisa/updateOrNot.html',
										success :function(data) {
											
										}
									});
								});
							}
						}
					});
				}else if(apply.base == 1){//提示过,查询是否更新
					if(apply.updateIsOrNot == 1){//更新
						$.ajax({
							type : "post",
							async : false,
							data : {
								telephone : $("#telephone").val(),
								applicantId : applicantId
							},
							url : BASE_PATH + '/admin/myData/getTouristInfoByTelephone.html',
							success :function(data) {
								if(data.base != null){
									toSet(data);
									//$("#cardId").unbind("change");
									$("#cardId").val(data.base.cardId); 
									$("#cardId").next().next().css('display','none');
									$("#cardId").next().next().attr('data-bv-result','VALID');
									$("#cardId").next().attr('class','form-control-feedback glyphicon glyphicon-ok');
									$("#cardId").parent().attr('class','form-group has-feedback has-success');
									$('#applicantInfo').data('bootstrapValidator').enableFieldValidators('cardId', true);
									layer.load(1);
									$.ajax({
										type : "post",
										async : false,
										data : {
											applyid : applicantId
										},
										url : BASE_PATH + '/admin/myVisa/copyAllInfoToPersonnel.html',
										success :function(data) {
											layer.closeAll("loading");
											layer.msg("已同步", {
												time: 1000,
												end: function () {
												}
											});
										}
									});
								}
							}
						});
					}
				}
			}
		});
	}
});

function toSet(data){
	$("#firstName").val(data.base.firstName).change();
	$("#firstNameEn").val("/"+data.base.firstNameEn).change();
	$("#lastName").val(data.base.lastName).change();
	$("#lastNameEn").val("/"+data.base.lastNameEn).change(); 
	$('#sqImgBack').attr('src', data.base.cardBack).change();
	$('#sqImg').attr('src', data.base.cardFront).change();
	$("#cardFront").val(data.base.cardFront).change(); 
	$("#uploadFile").siblings("i").css("display","block");
	$(".front").attr("class", "info-imgUpload front has-success");  
    $(".help-blockFront").attr("data-bv-result","IVALID");  
    $(".help-blockFront").attr("style","display: none;");
    $("#borderColorFront").attr("style", null);
	$("#cardBack").val(data.base.cardBack).change(); 
	$("#uploadFileBack").siblings("i").css("display","block");
	$(".back").attr("class", "info-imgUpload back has-success");  
    $(".help-blockBack").attr("data-bv-result","IVALID");  
    $(".help-blockBack").attr("style","display: none;"); 
    $("#borderColorBack").attr("style", null);
	$("#issueOrganization").val(data.base.issueOrganization).change(); 
	$("#otherFirstName").val(data.base.otherFirstName).change(); 
	$("#otherFirstNameEn").val("/"+data.base.otherFirstNameEn).change(); 
	$("#otherLastNameEn").val("/"+data.base.otherLastNameEn).change(); 
	$("#otherLastName").val(data.base.otherLastName).change(); 
	$("#nationality").val(data.base.nationality).change(); 
	$("#email").val(data.base.email).change(); 
	$("#sex").val(data.base.sex).change(); 
	$("#nation").val(data.base.nation).change(); 
	$("#birthday").val(data.birthday).change(); 
	$("#address").val(data.base.address).change(); 
	$("#validStartDate").val(data.validStartDate).change(); 
	$("#validEndDate").val(data.validEndDate).change(); 
	$("#province").val(data.base.province).change(); 
	$("#city").val(data.base.city).change(); 
	$("#detailedAddress").val(data.base.detailedAddress).change(); 
	$("#emergencyLinkman").val(data.base.emergencyLinkman).change(); 
	$("#emergencyTelephone").val(data.base.emergencyTelephone).change(); 
	$("input[name='hasOtherNationality'][value='"+data.base.hasOtherNationality+"']").attr("checked",'checked');
	$("input[name='hasOtherName'][value='"+data.base.hasOtherName+"']").attr("checked",'checked');
	if(data.base.addressIsSameWithCard == 1){
		var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",true);
	}else{
		var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",false);
	}
	var nation = data.base.hasOtherNationality;
	var otherName = data.base.hasOtherName;
	if(nation == 1){
		$(".nameBeforeTop").css('float','none');
		$(".nationalityHide").show();
		$(".onceIDTop").css('float','left');
	}else {
		$(".nationalityHide").hide();
	}
	
	if(otherName == 1){
		$(".nameBeforeTop").css('float','none');
		$(".nameBeforeHide").show();
		$(".wordSpell").show();
		$(".onceIDTop").removeClass('col-sm-offset-1');
		$(".onceIDTop").css('padding-left','15px');
	}else {
		
		$(".nameBeforeHide").hide();
		$(".wordSpell").hide();
	}
}

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

//合格/不合格
$(".Unqualified").click(function(){
	$(".ipt-info").slideDown();
});
$(".qualified").click(function(){
	$(".ipt-info").slideUp();
	layer.load(1);
	$.ajax({
		type: 'POST',
		async : false,
		data : {
			applicantId : applicantId,
			orderid : orderid,
			orderjpid : orderJpId,
			infoType : infoType
		},
		url: BASE_PATH + '/admin/qualifiedApplicant/qualified.html',
		success :function(data) {
			console.log(JSON.stringify(data));
			layer.closeAll('loading');
			$("#baseRemark").val("");
			passportBtn();
		}
	});
});

//保存申请人基本信息
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
		if(userType == 2){
			if($(".back").hasClass("has-error")){
				return;
			}
			if($(".front").hasClass("has-error")){
				return;
			}
		}
		
	var str="";
	var applicantInfo;
	$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
	    str=$(this).val();     
	});
	if(str != 1){
		applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
	}else{
		applicantInfo = $("#applicantInfo").serialize();
	}
	applicantInfo.id = applicantId;
	
	if(userType == 2){
		layer.load(1);
		$.ajax({
			async: false,
			type: 'POST',
			data : applicantInfo,
			url: BASE_PATH + '/admin/myData/baseIsChanged.html',
			success :function(data) {
				layer.closeAll("loading");
				if(status == 2){
					if(data == 1 || data == 2){//改变了保存，没改变不保存
						layer.load(1);
						$.ajax({
							async: false,
							type: 'POST',
							data : applicantInfo,
							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
							success :function(data) {
								layer.closeAll("loading");
								socket.onclose();
								window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrailOrder+'&orderProcessType';
							}
						});
					}else{
						socket.onclose();
						window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrailOrder+'&orderProcessType';
					}
				}else{
					if(data == 2){
						layer.load(1);
						$.ajax({
							async: false,
							type: 'POST',
							data : applicantInfo,
							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
							success :function(data) {
								layer.closeAll("loading");
								layer.msg("已修改", {
    								time: 1000,
    								end: function () {
    									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    									parent.layer.close(index);
    									parent.successCallBack(8);
    								}
    							});
							}
						});
						$.ajax({ 
    			    		url: BASE_PATH + '/admin/myVisa/copyAllInfoToTourist.html',
    			    		dataType:"json",
    			    		data:{
    			    			applyid:applicantId,
    			    			emptyInfo:"YES"
    			    			},
    			    		type:'post',
    			    		success: function(data){
    			    					    		
    			    		}
    			    	}); 
					}else{
						layer.load(1);
						$.ajax({
							async: false,
							type: 'POST',
							data : {applyid:applicantId},
							url: BASE_PATH + '/admin/myData/infoIsChanged.html',
							success :function(data) {
								layer.closeAll("loading");
								if(data.isPrompted == 0){//没有提示过
									if(data.base == 0){//如果返回0则说明游客信息改变，提示是否更新
			    						layer.confirm("信息已改变，您是否要更新？", {
			    							title:"提示",
			    							btn: ["是","否"], //按钮
			    							shade: false //不显示遮罩
			    						}, 
			    						function(){
			    							layer.load(1);
			    							$.ajax({
				    							async: false,
				    							type: 'POST',
				    							data : applicantInfo,
				    							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				    							success :function(data) {
				    								layer.closeAll("loading");
				    								console.log(JSON.stringify(data));
				    								if(status == 1){
						    							layer.msg("已同步", {
						    								time: 1000,
						    								end: function () {
						    									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						    									parent.layer.close(index);
						    									parent.successCallBack(8);
						    								}
						    							});
				    								}
				    							}
				    						});
							    					$.ajax({ 
							    			    		url: BASE_PATH + '/admin/myVisa/copyAllInfoToTourist.html',
							    			    		dataType:"json",
							    			    		data:{applyid:applicantId},
							    			    		type:'post',
							    			    		success: function(data){
							    			    					    		
							    			    		}
							    			    	}); 
							    					
							    					$.ajax({ 
							    			    		url: BASE_PATH + '/admin/myVisa/saveIsOrNot.html',
							    			    		dataType:"json",
							    			    		data:{
							    			    			applyid:applicantId,
							    			    			updateOrNot : "YES"
							    			    		},
							    			    		type:'post',
							    			    		success: function(data){
							    			    					    		
							    			    		}
							    			    	}); 
			    						},function(){
			    							layer.load(1);
			    							$.ajax({
				    							async: false,
				    							type: 'POST',
				    							data : applicantInfo,
				    							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				    							success :function(data) {
				    								layer.closeAll("loading");
					    							if(status == 1){
					    								layer.msg("修改成功", {
					    									time: 1000,
					    									end: function () {
					    										var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    										parent.layer.close(index);
					    										parent.successCallBack(8);
					    									}
					    								});
													}
				    							}
				    						});
			    							
			    							
			    							$.ajax({ 
					    			    		url: BASE_PATH + '/admin/myVisa/saveIsOrNot.html',
					    			    		dataType:"json",
					    			    		data:{
					    			    			applyid:applicantId,
					    			    			updateOrNot : "NO"
					    			    		},
					    			    		type:'post',
					    			    		success: function(data){
					    			    					    		
					    			    		}
					    			    	}); 
			    						});
									}else{
										layer.load(1);
										$.ajax({
											async: false,
											type: 'POST',
											data : applicantInfo,
											url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
											success :function(data) {
												layer.closeAll("loading");
												console.log(JSON.stringify(data));
												if(status == 1){
													layer.msg("修改成功", {
														time: 1000,
														end: function () {
															var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
															parent.layer.close(index);
														}
													});
												}
											}
										});
									}
								}else{//提示过
									
									if(data.base == 0){//改变了需要保存
										if(data.isUpdated == 1){//更新
											layer.load(1);
											$.ajax({
				    							async: false,
				    							type: 'POST',
				    							data : applicantInfo,
				    							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				    							success :function(data) {
				    								layer.closeAll("loading");
													if(status == 1){
														layer.msg("已同步", {
															time: 1000,
															end: function () {
																var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																parent.layer.close(index);
																parent.successCallBack(8);
															}
														});
													}
				    							}
				    						});
											$.ajax({ 
					    			    		url: BASE_PATH + '/admin/myVisa/copyAllInfoToTourist.html',
					    			    		dataType:"json",
					    			    		data:{applyid:applicantId},
					    			    		type:'post',
					    			    		success: function(data){
					    			    					    		
					    			    		}
					    			    	});
										}else{//不更新，只保存
											layer.load(1);
											$.ajax({
				    							async: false,
				    							type: 'POST',
				    							data : applicantInfo,
				    							url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				    							success :function(data) {
				    								layer.closeAll("loading");
													if(status == 1){
														layer.msg("修改成功", {
															time: 1000,
															end: function () {
																var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																parent.layer.close(index);
																parent.successCallBack(8);
															}
														});
													}
				    							}
				    						});
											
										}
									}else{//没改变不需要保存
										layer.msg("修改成功", {
											time: 1000,
											end: function () {
												var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
												parent.layer.close(index);
												parent.successCallBack(8);
											}
										});
									}
								}
								
							}
						});
						
					}
				}
			}
		});
	}else{
		if(status == 2){
			socket.onclose();
			window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrailOrder+'&orderProcessType='+orderProcessType+'&addApply='+addApply;
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				success :function(data) {
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : applicantInfo,
				url: BASE_PATH + '/admin/orderJp/saveEditApplicant.html',
				success :function(data) {
					layer.closeAll("loading");
					console.log(JSON.stringify(data));
					//var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					//layer.close(index);
						closeWindow();
						if(addApply == 1){
							parent.successCallBack(3);
						}else{
							parent.successCallBack(1);
						}
				}
			});
		}
	}
	}
}

//返回 
function closeWindow() {
	if(userType == 2){
		$.ajax({
			async: false,
			type: 'POST',
			data : {applyid:applicantId},
			url: BASE_PATH + '/admin/myData/infoIsChanged.html',
			success :function(data) {
				if(data.isPrompted == 0){//没有提示过
					if(data.base == 0){//如果返回0则说明游客信息改变，提示是否更新
						layer.confirm("信息已改变，您是否要更新？", {
							title:"提示",
							btn: ["是","否"], //按钮
							shade: false //不显示遮罩
						}, 
						function(){
							layer.msg("已同步", {
								time: 1000,
								end: function () {
									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
									parent.layer.close(index);
								}
							});
							//parent.cancelCallBack(1);
							$.ajax({ 
	    			    		url: BASE_PATH + '/admin/myVisa/copyAllInfoToTourist.html',
	    			    		dataType:"json",
	    			    		data:{applyid:applicantId},
	    			    		type:'post',
	    			    		success: function(data){
	    			    					    		
	    			    		}
	    			    	}); 
	    					
	    					$.ajax({ 
	    			    		url: BASE_PATH + '/admin/myVisa/saveIsOrNot.html',
	    			    		dataType:"json",
	    			    		data:{
	    			    			applyid:applicantId,
	    			    			updateOrNot : "YES"
	    			    		},
	    			    		type:'post',
	    			    		success: function(data){
	    			    					    		
	    			    		}
	    			    	}); 
						},
						function(){
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							//parent.cancelCallBack(1);
							$.ajax({ 
	    			    		url: BASE_PATH + '/admin/myVisa/saveIsOrNot.html',
	    			    		dataType:"json",
	    			    		data:{
	    			    			applyid:applicantId,
	    			    			updateOrNot : "NO"
	    			    		},
	    			    		type:'post',
	    			    		success: function(data){
	    			    					    		
	    			    		}
	    			    	}); 
						});
					}else{//信息没变
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
						//parent.cancelCallBack(1);
					}
				}else if(data.isPrompted == 1){//提示过
					//parent.cancelCallBack(1);
					if(data.base == 0){//内容改变
						if(data.isUpdated == 1){//更新
							layer.msg("已同步", {
								time: 1000,
								end: function () {
									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
									parent.layer.close(index);
								}
							});
							$.ajax({ 
	    			    		url: BASE_PATH + '/admin/myVisa/copyAllInfoToTourist.html',
	    			    		dataType:"json",
	    			    		data:{applyid:applicantId},
	    			    		type:'post',
	    			    		success: function(data){
	    			    					    		
	    			    		}
	    			    	});
						}else{
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
						}
					}else{
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
					}
				}else{
					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					parent.layer.close(index);
				}
			}
		});
		parent.successCallBack();
	}else{
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.cancelCallBack(1);
	}
}