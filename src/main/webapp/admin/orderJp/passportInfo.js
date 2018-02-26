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
			passport : {
				trigger:"change keyup",
				validators : {
					remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
						url: '/admin/orderJp/checkPassport.html',
						async:false,
						message: '护照号已存在，请重新输入',//提示消息
						delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
						type: 'POST',//请求方式
						//自定义提交数据，默认值提交当前input value
						data: function(validator) {
							return {
								passport:$('#passport').val(),
								adminId:$('#id').val(),
								orderid:$('#orderid').val()
							};
						}
					}
				}
			}
		}
	});
	$('#passportInfo').bootstrapValidator('validate');
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

	//初审环节，显示合格不合格按钮
	if(isTrail == 1){
		$("#qualifiedBtn").show();
		$("#unqualifiedBtn").show();
		$("#passRemark").attr("disabled", false);
	}else{
		$("#passRemark").attr("disabled", true);
	}

	var remark = $("#passRemark").val();
	if(remark != ""){
		$(".ipt-info").show();
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
				$('#validEndDate').val(getNewDate($('#issuedDate').val(), 5));
			}else{
				$('#validEndDate').val(getNewDate($('#issuedDate').val(), 10));
			}
		}
	});
	
	$("#validType").change(function(){
		var type = $(this).val();
		if(type == 1){
			$('#validEndDate').val(getNewDate($('#issuedDate').val(), 5));
		}else{
			$('#validEndDate').val(getNewDate($('#issuedDate').val(), 10));
		}
		
	});
});

function getNewDate(dateTemp, days){
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
	if(userType == 2){
		$(".front").attr("class", "info-imgUpload front has-error");  
        $(".help-blockFront").attr("data-bv-result","INVALID");  
        //$(".help-blockFront").attr("style","display: block;");
        //$("#borderColor").attr("style", "border-color:#ff1a1a");
	}
}

function passValidate(){
	if(userType == 2){
		//护照图片验证
		var passportUrl = $("#passportUrl").val();
		if(passportUrl == ""){
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        $(".help-blockFront").attr("style","display: block;");  
	        $("#borderColor").attr("style", "border-color:#ff1a1a");
		}else{
			$(".front").attr("class", "info-imgUpload front has-success");  
	        $(".help-blockFront").attr("data-bv-result","IVALID");  
	        $(".help-blockFront").attr("style","display: none;");
	        $("#borderColor").attr("style", null);
		}
		
		$('#passportInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				passport : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '护照号不能为空'
						},
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
							url: '/admin/orderJp/checkPassport.html',
							async:false,
							message: '护照号已存在，请重新输入',//提示消息
							delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
							type: 'POST',//请求方式
							//自定义提交数据，默认值提交当前input value
							data: function(validator) {
								return {
									passport:$('#passport').val(),
									adminId:$('#id').val(),
									orderid:$('#orderid').val()
								};
							}
						}
					}
				},
				type : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '类型不能为空'
						}
					}
				},
				birthAddress : {
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
				issuedPlace : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '签发地点不能为空'
						}
					}
				},
				issuedDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '签发日期不能为空'
						}
					}
				},
				validEndDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效日期不能为空'
						}
					}
				}
			}
		});
	}
	$('#passportInfo').bootstrapValidator('validate');
}

//连接websocket
connectWebSocket();
function connectWebSocket(){
	 if ('WebSocket' in window){  
        console.log('Websocket supported');  
        socket = new WebSocket('ws://'+localAddr+':' + localPort+ '/' + websocketaddr);   

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
                	  if(receiveMessage.messagetype == 2){
	                	  window.location.reload();
                	  }else if(receiveMessage.messagetype == 3){
                		  window.location.href = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderid+'&isOrderUpTime&isTrial='+isTrail;
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
						$("#validType").val(1);
					}else{
						$("#validType").val(2);
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

//根据护照号查询游客信息
$("#passport").change(function(){
	if(userType == 2){
		$.ajax({
			type : "post",
			async : false,
			data : {
				applyId : applicantId
			},
			url : '/admin/myData/isPrompted.html',
			success :function(apply) {
				if(apply.base == 0){//没有提示过
					$.ajax({
						type : "post",
						async : false,
						data : {
							passport : $("#passport").val(),
							id : applicantId
						},
						url : '/admin/myData/getTouristInfoByPass.html',
						success :function(data) {
							console.log(JSON.stringify(data));
							if(data.pass){
								layer.confirm("您的信息已存在，是否使用？", {
									title:"提示",
									btn: ["是","否"], //按钮
									shade: false //不显示遮罩
								}, function(index){
									if(data.pass != null){
										toSet(data);
										//同步信息
										layer.load(1);
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId
											},
											url : '/admin/myVisa/copyAllInfoToPersonnel.html',
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
										//记录选择的是同步
										$.ajax({
											type : "post",
											async : false,
											data : {
												applyid : applicantId,
												updateOrNot : "YES"
											},
											url : '/admin/myVisa/updateOrNot.html',
											success :function(data) {

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
										url : '/admin/myVisa/updateOrNot.html',
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
								passport : $("#passport").val(),
								id : applicantId
							},
							url : '/admin/myData/getTouristInfoByPass.html',
							success :function(data) {
								if(data.pass != null){
									toSet(data);
									layer.load(1);
									$.ajax({
										type : "post",
										async : false,
										data : {
											applyid : applicantId
										},
										url : '/admin/myVisa/copyAllInfoToPersonnel.html',
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
	$("#passportUrl").val(data.pass.passportUrl);
	$("#type").val(data.pass.type).change();
	$("#sex").val(data.pass.sex).change();
	$("#sexEn").val(data.pass.sexEn).change();
	$("#birthAddress").val(data.pass.birthAddress).change();
	$("#birthAddressEn").val(data.pass.birthAddressEn).change();
	$("#birthday").val(data.birthday).change();
	$("#issuedPlace").val(data.pass.issuedPlace).change();
	$("#issuedPlaceEn").val(data.pass.issuedPlaceEn).change();
	$("#issuedDate").val(data.issuedDate).change();
	$("#validType").val(data.pass.validType).change();
	$("#validEndDate").val(data.validEndDate).change();
	$('#sqImg').attr('src', data.pass.passportUrl);
	$("#uploadFile").siblings("i").css("display","block");
	$("#borderColor").attr("style", null);
	$(".front").attr("class", "info-imgUpload front has-success");  
    $(".help-blockFront").attr("data-bv-result","IVALID");  
    $(".help-blockFront").attr("style","display: none;");
}

//保存
function save(status){
	if(userType == 2){
		$("#passportInfo").data('bootstrapValidator').destroy();
		$("#passportInfo").data('bootstrapValidator', null);
		passValidate();
	}
	//得到获取validator对象或实例 
	var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
	//alert(bootstrapValidator.isValid());
	//bootstrapValidator.validate();
	if(userType == 2 && status != 2){
		if($(".front").hasClass("has-error")){
			$(".help-blockFront").attr("style","display: block;");  
			$("#borderColor").attr("style", "border-color:#ff1a1a");
			return;
		}
	}
	setTimeout(function(){
		if(bootstrapValidator.isValid()){
			/* if (!bootstrapValidator.isValid()) {
						return;
				} */
			var passportInfo = $("#passportInfo").serialize();
			if(userType == 2){
				layer.load(1);
				$.ajax({
					async: false,
					type: 'POST',
					data : passportInfo,
					url: '/admin/myData/passIsChanged.html',
					success :function(data) {
						layer.closeAll("loading");
						if(status == 2){
							if(data == 1 || data == 2){//1是变了
								layer.load(1);
								$.ajax({
									async: false,
									type: 'POST',
									data : passportInfo,
									url: '/admin/orderJp/saveEditPassport.html',
									success :function(data) {
										layer.closeAll("loading");
										socket.onclose();
										window.location.href = '/admin/orderJp/updateApplicant.html?id='+applicantId+'&orderid='+'&isTrial='+isTrail+'&orderProcessType';
									}
								});
							}else{
								socket.onclose();
								window.location.href = '/admin/orderJp/updateApplicant.html?id='+applicantId+'&orderid='+'&isTrial='+isTrail+'&orderProcessType';
							}
						}else if(status == 3){
							if(data == 1 || data == 2){
								layer.load(1);
								$.ajax({
									async: false,
									type: 'POST',
									data : passportInfo,
									url: '/admin/orderJp/saveEditPassport.html',
									success :function(data) {
										layer.closeAll("loading");
										socket.onclose();
										window.location.href = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderid+'&isOrderUpTime&isTrial='+isTrail+'&orderProcessType';
									}
								});
							}else{
								socket.onclose();
								window.location.href = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderid+'&isOrderUpTime&isTrial='+isTrail+'&orderProcessType';
							}
						}else{
							if(data == 2){//游客表为空
								layer.load(1);
								$.ajax({
									async: false,
									type: 'POST',
									data : passportInfo,
									url: '/admin/orderJp/saveEditPassport.html',
									success :function(data) {
										layer.closeAll("loading");
										if(status == 1){
											layer.msg("已修改", {
												time: 1000,
												end: function () {
													var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
													parent.layer.close(index);
													parent.successCallBack();
												}
											});
										}
									}
								});
								$.ajax({ 
									url: '/admin/myVisa/copyAllInfoToTourist',
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
									url: '/admin/myData/infoIsChanged.html',
									success :function(data) {
										layer.closeAll("loading");
										if(data.isPrompted == 0){//没有提示过
											if(data.base == 0){//如果返回0则说明游客信息改变，提示是否更新
												layer.confirm("信息已改变，您是否要更新？", {
													title:"提示",
													btn: ["是","否"], //按钮
													shade: false //不显示遮罩
												}, function(){
													layer.load(1);
													$.ajax({
														async: false,
														type: 'POST',
														data : passportInfo,
														url: '/admin/orderJp/saveEditPassport.html',
														success :function(data) {
															layer.closeAll("loading");
															console.log(JSON.stringify(data));
															if(status == 1){
																layer.msg("已同步", {
																	time: 1000,
																	end: function () {
																		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																		parent.layer.close(index);
																		parent.successCallBack();
																	}
																});
															}
														}
													});
													$.ajax({ 
														url: '/admin/myVisa/copyAllInfoToTourist',
														dataType:"json",
														data:{applyid:applicantId},
														type:'post',
														success: function(data){

														}
													}); 

													$.ajax({ 
														url: '/admin/myVisa/saveIsOrNot.html',
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
														data : passportInfo,
														url: '/admin/orderJp/saveEditPassport.html',
														success :function(data) {
															layer.closeAll("loading");
															if(status == 1){
																layer.msg("修改成功", {
																	time: 1000,
																	end: function () {
																		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																		parent.layer.close(index);
																		parent.successCallBack();
																	}
																});
															}
														}
													});
													$.ajax({ 
														url: '/admin/myVisa/saveIsOrNot.html',
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
													data : passportInfo,
													url: '/admin/orderJp/saveEditPassport.html',
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
											if(data.base == 0){
												if(data.isUpdated == 1){//更新
													layer.load(1);
													$.ajax({
														async: false,
														type: 'POST',
														data : passportInfo,
														url: '/admin/orderJp/saveEditPassport.html',
														success :function(data) {
															console.log(JSON.stringify(data));
															layer.closeAll("loading");
															if(status == 1){
																layer.msg("已同步", {
																	time: 1000,
																	end: function () {
																		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																		parent.layer.close(index);
																		parent.successCallBack();
																	}
																});
															}
														}
													});
													$.ajax({ 
														url: '/admin/myVisa/copyAllInfoToTourist.html',
														dataType:"json",
														data:{applyid:applicantId},
														type:'post',
														success: function(data){

														}
													});
												}else{
													layer.load(1);
													$.ajax({
														async: false,
														type: 'POST',
														data : passportInfo,
														url: '/admin/orderJp/saveEditPassport.html',
														success :function(data) {
															console.log(JSON.stringify(data));
															layer.closeAll("loading");
															if(status == 1){
																layer.msg("修改成功", {
																	time: 1000,
																	end: function () {
																		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
																		parent.layer.close(index);
																		parent.successCallBack();
																	}
																});
															}
														}
													});

												}
											}else{
												layer.msg("修改成功", {
													time: 1000,
													end: function () {
														var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
														parent.layer.close(index);
														parent.successCallBack();
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
					window.location.href = '/admin/orderJp/updateApplicant.html?id='+applicantId+'&orderid='+'&isTrial='+isTrail+'&orderProcessType='+orderProcessType+'&addApply='+addApply;
					$.ajax({
						type: 'POST',
						data : passportInfo,
						url: '/admin/orderJp/saveEditPassport',
						success :function(data) {
						}
					});
				}
				if(status == 3){
					socket.onclose();
					window.location.href = '/admin/orderJp/visaInfo.html?id='+applicantId+'&orderid='+orderid+'&isOrderUpTime&isTrial='+isTrail+'&orderProcessType='+orderProcessType+'&addApply='+addApply;
					$.ajax({
						type: 'POST',
						data : passportInfo,
						url: '/admin/orderJp/saveEditPassport',
						success :function(data) {
						}
					});
				}
				if(status == 1){
					layer.load(1);
					$.ajax({
						type: 'POST',
						data : passportInfo,
						url: '/admin/orderJp/saveEditPassport',
						success :function(data) {
							layer.closeAll("loading");
							console.log(JSON.stringify(data));
							/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								layer.close(index); */
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
	}, 500);
}

function applyBtn(){
	save(2);
 }
	
 function visaBtn(){
	save(3);
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
		url: '/admin/qualifiedApplicant/qualified.html',
		success :function(data) {
			console.log(JSON.stringify(data));
			layer.closeAll('loading');
			$("#passRemark").val("");
			visaBtn();
		}
	});
});

//返回 
function closeWindow() {
	if(userType == 2){
		$.ajax({
			async: false,
			type: 'POST',
			data : {applyid:applicantId},
			url: '/admin/myData/infoIsChanged.html',
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
							$.ajax({ 
								url: '/admin/myVisa/copyAllInfoToTourist.html',
								dataType:"json",
								data:{applyid:applicantId},
								type:'post',
								success: function(data){

								}
							}); 

							$.ajax({ 
								url: '/admin/myVisa/saveIsOrNot.html',
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
								url: '/admin/myVisa/saveIsOrNot.html',
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
					if(data.base == 0){
						if(data.isUpdated == 1){//更新
							layer.msg("已同步", {
								time: 1000,
								end: function () {
									var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
									parent.layer.close(index);
								}
							});
							$.ajax({ 
								url: '/admin/myVisa/copyAllInfoToTourist.html',
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

function cancelCallBack(status){
	closeWindow();
	parent.cancelCallBack(1);
}
function successCallBack(status){
		parent.successCallBack(1);
		closeWindow();
}