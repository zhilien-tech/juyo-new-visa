$(function() {

	var remark = $("#visaRemark").val();
	if(remark != ""){
		$(".ipt-info").show();
	}

	//初审环节，显示合格不合格按钮
	if(isTrail == 1){
		$("#qualifiedBtn").show();
		$("#unqualifiedBtn").show();
		$("#visaRemark").attr("disabled", false);
	}else{
		$("#visaRemark").attr("disabled", true);
	}

	var marry = $("#marryUrl").val();
	if(marry != ""){
		$(".delete").css("display","block");
	}else{
		$(".delete").css("display","none");
	}

	//婚姻状况为单身和丧偶时没有上传图片接口
	var marryStatus = $("#marryStatus").val();
	if(marryStatus == 3 || marryStatus == 4 || !marryStatus){
		$(".info-imgUpload").hide();
		$("#unitNameLabel").html("<span>*</span>父母职业");
	}else{
		$(".info-imgUpload").show();
		if(marryStatus == 1){
			$("#unitNameLabel").html("<span>*</span>配偶职业");
		}else{
			$("#unitNameLabel").html("<span>*</span>父母职业");
		}
	}
	$("#marryStatus").change(function(){
		var status = $(this).val();
		if(status == 3 || status == 4 || !status){
			$("#unitNameLabel").html("<span>*</span>父母职业");
			$(".info-imgUpload").hide();
			$('#marryUrl').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
			if(tourist == 1){
				$(".front").attr("class", "info-imgUpload front has-success");  
				$(".help-blockFront").attr("data-bv-result","IVALID");  
				$(".help-blockFront").attr("style","display: none;");
				$("#borderColor").attr("style",null);
			}
		}else{
			if(status == 1){
				$("#unitNameLabel").html("<span>*</span>配偶职业");
			}else{
				$("#unitNameLabel").html("<span>*</span>父母职业");
			}
			$(".delete").css("display","none");
			if(tourist == 1){
				if($("#sqImg").attr("src") == ""){
					$(".front").attr("class", "info-imgUpload front has-error");  
					$(".help-blockFront").attr("data-bv-result","INVALID");
					//$(".help-blockFront").attr("style","display: block;");
					//$("#borderColor").attr("style","border-color:#ff1a1a");
				}
			}
			$(".info-imgUpload").show();
		}
	});

	var career = $("#careerStatus").val();
	if(career == 4){
		$("#schoolName").html("<span>*</span>学校名称");
		$("#schoolTelephone").html("<span>*</span>学校电话");
		$("#schoolAddress").html("<span>*</span>学校地址");
	}
	if(career == 5){
		$(".preSchool").hide();
		$("#name").val("").change();
		$("#telephone").val("").change();
		$("#address").val("").change();
	}
	$("#careerStatus").change(function(){
		$("#name").val("").change();
		$("#telephone").val("").change();
		$("#address").val("").change();
		var career = $(this).val();
		if(career == 5){
			$(".preSchool").hide();
		}else if(career == 4){
			$(".preSchool").show();
			$("#schoolName").html("<span>*</span>学校名称");
			$("#schoolTelephone").html("<span>*</span>学校电话");
			$("#schoolAddress").html("<span>*</span>学校地址");
		}else{
			$(".preSchool").show();
			$("#schoolName").html("<span>*</span>单位名称");
			$("#schoolTelephone").html("<span>*</span>单位电话");
			$("#schoolAddress").html("<span>*</span>单位地址");
		}
	});

	//主申请人 or 副申请人
	var applicVal = $("#applicant").val();
	if(applicVal == "1"){//主申请人
		$(".applyvice").hide();
		$(".tripvice").hide();
		//$(".workvice").hide();
		$(".wealthvice").hide();
		$(".applymain").show();
		//$(".workmain").show();
		$(".wealthmain").show();
		$("#mainApply").text("主申请人");
	}else{//副申请人
		$(".applyvice").show();
		$(".tripvice").show();
		$(".wealthvice").show();
		//$(".workvice").show();
		$(".applymain").hide();
		//$(".workmain").hide();
		$(".wealthmain").hide();
		$("#mainApply").text("副申请人");
	}

	//主申请人 or 副申请人
	$("#applicant").change(function(){
		var applicVal = $(this).val();
		if(applicVal == "1"){//主申请人
			$(".applyvice").hide();
			$(".tripvice").hide();
			//$(".workvice").hide();
			$(".wealthvice").hide();
			$(".applymain").show();
			//$(".workmain").show();
			$(".wealthmain").show();
			$("#mainApply").text("主申请人");
			wealthShow();
		}else{//副申请人
			$(".applyvice").show();
			$(".tripvice").show();
			$(".wealthvice").show();
			//$(".workvice").show();
			$("#wealth").val(1);
			$(".applymain").hide();
			$(".workmain").hide();
			$(".wealthmain").hide();
			$("#mainApply").text("副申请人");
			$(".deposit").css("display","none");
			$(".vehicle").css("display","none");
			$(".houseProperty").css("display","none");
			$(".financial").css("display","none");
		}
	});

	wealthShow();
	
	var wealth = $("#wealth").val();
	if(wealth == 0){
		$(".wealthmain").show();
		//$(".address").show();
	}else{
		if(applicVal == 1){

		}else{
			$(".deposit").css("display","none");
			$(".vehicle").css("display","none");
			$(".houseProperty").css("display","none");
			$(".financial").css("display","none");
		}
	}
	$("#wealth").change(function(){
		if($(this).val() == 1){
			$(".wealthmain").hide();
			$(".deposit").css("display","none");
			$(".vehicle").css("display","none");
			$(".houseProperty").css("display","none");
			$(".financial").css("display","none");
		}else{
			$(".wealthmain").show();
			wealthShow();
			/*$('[name=wealthType]').each(function(){
				$(this).removeClass("btnState-true");
			});*/
		}
	});


	//财务信息 部分按钮效果
	$(".finance-btn input").click(function(){
		var financeBtnInfo=$(this).val();
		if(financeBtnInfo == "银行存款"){
			if($(this).hasClass("btnState-true")){
				$(".deposit").css("display","none");
				$(this).removeClass("btnState-true");
				$("#deposit").val("");
				$(".deposits").css({"display":"none"});
				$(".deposits").attr("class", "col-xs-6 deposits has-success");
				$("#deposit").attr("style", null);
			}else{
				$(".deposit").css("display","block");
				$(this).addClass("btnState-true");
				$("#deposit").val("");
				if(tourist == 1){
					$(".help-blockdeposit").attr("data-bv-result","INVALID");  
					// $(".deposits").css({"display":"block"});
					$(".deposits").attr("class", "col-xs-6 deposits has-error");
					// $("#deposit").attr("style", "border-color:#ff1a1a");
				}
				//$("#deposit").placeholder("万");
			}
		}else if(financeBtnInfo == "车产"){
			if($(this).hasClass("btnState-true")){
				$(".vehicle").css("display","none");
				$(this).removeClass("btnState-true");
				$("#vehicle").val("");
				$(".vehicles").css({"display":"none"});
				$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
				$("#vehicle").attr("style", null);
			}else{
				$(".vehicle").css("display","block");
				$(this).addClass("btnState-true");
				$("#vehicle").val("");
				if(tourist == 1){
					$(".help-blockvehicle").attr("data-bv-result","INVALID");  
					//$(".vehicles").css({"display":"block"});
					$(".vehicles").attr("class", "col-xs-6 vehicles has-error");
					//$("#vehicle").attr("style", "border-color:#ff1a1a");
				}
			}
		}else if(financeBtnInfo == "房产"){
			if($(this).hasClass("btnState-true")){
				$(".houseProperty").css("display","none");
				$(this).removeClass("btnState-true");
				$("#houseProperty").val("");
				$(".housePropertys").css({"display":"none"});
				$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
				$("#houseProperty").attr("style", null);
			}else{
				$(".houseProperty").css("display","block");
				$(this).addClass("btnState-true");
				$("#houseProperty").val("");
				if(tourist == 1){
					$(".help-blockhouseProperty").attr("data-bv-result","INVALID");  
					// $(".housePropertys").css({"display":"block"});
					$(".housePropertys").attr("class", "col-xs-6 housePropertys has-error");
					//$("#houseProperty").attr("style", "border-color:#ff1a1a");
				}
				//$("#houseProperty").placeholder("平米");
			}
		}else if(financeBtnInfo == "理财"){
			if($(this).hasClass("btnState-true")){
				$(".financial").css("display","none");
				$(this).removeClass("btnState-true");
				$("#financial").val("");
				$(".financials").css({"display":"none"});
				$(".financials").attr("class", "col-xs-6 financials has-success");
				$("#financial").attr("style", null);
			}else{
				$(".financial").css("display","block");
				$(this).addClass("btnState-true");
				$("#financial").val("");
				if(tourist == 1){
					$(".help-blockfinancial").attr("data-bv-result","INVALID");  
					//$(".financials").css({"display":"block"});
					$(".financials").attr("class", "col-xs-6 financials has-error");
					//$("#financial").attr("style", "border-color:#ff1a1a");
				}
				//$("#financial").placeholder("万");
			}
		}
	});

	$(".remove-btn").click(function(){
		//$(this).parent().css("display","none");
		if($(this).parent().is(".deposit")){
			$(".deposit").css("display","none");
			$("#depositType").removeClass("btnState-true");
			$("#deposit").val("");
			$(".deposits").css({"display":"none"});
			$(".deposits").attr("class", "col-xs-6 deposits has-success");
			$("#deposite").attr("style", null);
		}
		if($(this).parent().is(".vehicle")){
			$(".vehicle").css("display","none");
			$("#vehicleType").removeClass("btnState-true");
			$("#vehicle").val("");
			$(".vehicles").css({"display":"none"});
			$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
			$("#vehicle").attr("style", null);
		}
		if($(this).parent().is(".houseProperty")){
			$(".houseProperty").css("display","none");
			$("#housePropertyType").removeClass("btnState-true");
			$("#houseProperty").val("");
			$(".housePropertys").css({"display":"none"});
			$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
			$("#houseProperty").attr("style", null);
		}
		if($(this).parent().is(".financial")){
			$(".financial").css("display","none");
			$("#financialType").removeClass("btnState-true");
			$("#financial").val("");
			$(".financials").css({"display":"none"});
			$(".financials").attr("class", "col-xs-6 financials has-success");
			$("#financial").attr("style", null);
		}
	});

});

function wealthShow(){
	if(wealthType){
		$('[name=wealthType]').each(function(){
			var wealth = $(this);
			$.each(JSON.parse(wealthType), function(i, item){     
				if(item.type == wealth.val()){
					if(wealth.val() == "银行存款"){
						$(".deposit").css("display","block");
						wealth.addClass("btnState-true");
						$("#deposit").val(item.details);
					}
					if(wealth.val() == "车产"){
						$(".vehicle").css("display","block");
						wealth.addClass("btnState-true");
						$("#vehicle").val(item.details);
					}
					if(wealth.val() == "房产"){
						$(".houseProperty").css("display","block");
						wealth.addClass("btnState-true");
						$("#houseProperty").val(item.details);
					}
					if(wealth.val() == "理财"){
						$(".financial").css("display","block");
						wealth.addClass("btnState-true");
						$("#financial").val(item.details);
					}
				}
			});
		});

	}
}

function visaValidate(){
//	护照图片验证
	if(tourist == 1){
		var marryUrl = $("#marryUrl").val();
		var marrystatus = $("#marryStatus").val();
		if(marryUrl == ""){
			if(marrystatus == 3 || marrystatus == 4){
				$(".front").attr("class", "info-imgUpload front has-success");  
				$(".help-blockFront").attr("data-bv-result","IVALID");  
				$(".help-blockFront").attr("style","display: none;");
				$("#borderColor").attr("style",null);
			}else{
				$(".front").attr("class", "info-imgUpload front has-error");  
				$(".help-blockFront").attr("data-bv-result","INVALID");  
				$(".help-blockFront").attr("style","display: block;"); 
				$("#borderColor").attr("style","border-color:#ff1a1a");
			}
		}else{
			$(".front").attr("class", "info-imgUpload front has-success");  
			$(".help-blockFront").attr("data-bv-result","IVALID");  
			$(".help-blockFront").attr("style","display: none;");
			$("#borderColor").attr("style",null);
		}
	}
//	财产验证
	if(tourist == 1){
		var deposit = $("#deposit").val();
		var vehicle = $("#vehicle").val();
		var houseProperty = $("#houseProperty").val();
		var financial = $("#financial").val();
		if($(".deposit").css("display") != "none"){
			if(deposit == ""){
				$(".deposits").attr("class", "col-xs-6 deposits has-error");  
				$(".help-blockdeposit").attr("data-bv-result","INVALID");  
				$(".deposits").css({"display":"block"});
				$("#deposit").attr("style", "border-color:#ff1a1a");
			}else{
				$(".deposits").attr("class", "col-xs-6 deposits has-success");
				$(".deposits").css({"display":"none"});
				$("#deposit").attr("style", null);
			}
		}
		if($(".vehicle").css("display") != "none"){
			if(vehicle == ""){
				$(".vehicles").attr("class", "col-xs-6 vehicles has-error");  
				$(".help-blockvehicle").attr("data-bv-result","INVALID");  
				$(".vehicles").css({"display":"block"});
				$("#vehicle").attr("style", "border-color:#ff1a1a");
			}else{
				$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
				$(".vehicles").css({"display":"none"});
				$("#vehicle").attr("style", null);
			}
		}
		if($(".houseProperty").css("display") != "none"){
			if(houseProperty == ""){
				$(".housePropertys").attr("class", "col-xs-6 housePropertys has-error");  
				$(".help-blockhouseProperty").attr("data-bv-result","INVALID");  
				$(".housePropertys").css({"display":"block"});
				$("#houseProperty").attr("style", "border-color:#ff1a1a");
			}else{
				$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
				$(".housePropertys").css({"display":"none"});
				$("#houseProperty").attr("style", null);
			}
		}
		if($(".financial").css("display") != "none"){
			if(financial == ""){
				$(".financials").attr("class", "col-xs-6 financials has-error");  
				$(".help-blockfinancial").attr("data-bv-result","INVALID");  
				$(".financials").css({"display":"block"});
				$("#financial").attr("style", "border-color:#ff1a1a");
			}else{
				$(".financials").attr("class", "col-xs-6 financials has-success");
				$(".financials").css({"display":"none"});
				$("#financial").attr("style", null);
			}
		}
	}

	if(tourist == 1){
		//校验
		$('#passportInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				relationRemark : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '备注不能为空'
						}
					}
				},
				careerStatus : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '职业不能为空'
						}
					}
				},
				marryStatus : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '结婚状况不能为空'
						}
					}
				},
				mainRelation : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '与主申请人关系不能为空'
						}
					}
				},
				name : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '名称不能为空'
						}
					}
				},
				telephone : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '电话不能为空'
						}
					}
				},
				address : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '地址不能为空'
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
                  if(receiveMessage.messagetype == 3 && receiveMessage.applicantid == applicantId){
                	  window.location.reload();
                  }
              }
              console.log('message received!');  
              //showMessage(received_msg);  
         };  

      } else {  
        console.log('Websocket not supported');  
      }  
}

$("#addBtn").click(function(){
	save(1);
});

//上传结婚证
function dataURLtoBlob(dataurl) { 
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
	bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
}

$('#uploadFile').change(function() {
	var layerIndex = layer.load(1, {
		shade : "#000"
	});
	$("#addBtn").attr('disabled', true);
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
			url : '/admin/orderJp/marryUpload',//请求地址  
			async : true,
			processData : false, //当FormData在jquery中使用的时候需要设置此项
			contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
			//请求数据  
			data : formData,
			success : function(obj) {//请求成功后的函数 
				//关闭加载层
				layer.close(layerIndex);
				if (200 == obj.status) {
					$('#marryUrl').val(obj.data);
					$('#sqImg').attr('src', obj.data);
					$(".delete").css("display","block");
					$(".front").attr("class", "info-imgUpload front has-success");  
					$(".help-blockFront").attr("data-bv-result","IVALID");  
					$(".help-blockFront").attr("style","display: none;");
					$("#borderColor").attr("style",null);
				}
				$("#addBtn").attr('disabled', false);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.close(layerIndex);
				$("#addBtn").attr('disabled', false);
			}
		}); // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

function deleteApplicantFrontImg(){
	$('#marryUrl').val("");
	$('#sqImg').attr('src', "");
	$(".delete").css("display","none");
	if(tourist == 1){
		$(".front").attr("class", "info-imgUpload front has-error");  
        $(".help-blockFront").attr("data-bv-result","INVALID");  
        //$(".help-blockFront").attr("style","display: block;");
        //$("#borderColor").attr("style","border-color:#ff1a1a");
	}
}

//保存
function save(status){
	if(status != 2){
		visaValidate();
		//得到获取validator对象或实例 
		var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
		bootstrapValidator.validate();
		if (!bootstrapValidator.isValid()) {
			return;
		}
	}
	if(tourist == 1 && status == 1){
		if($(".front").hasClass("has-error")){
			return;
		}
		if($(".deposits").hasClass("has-error")){
			return;
		}
		if($(".vehicles").hasClass("has-error")){
			return;
		}
		if($(".housePropertys").hasClass("has-error")){
			return;
		}
		if($(".financials").hasClass("has-error")){
			return;
		}
	}
	
	//绑定财产类型
	var wealthType = "";
	$('[name=wealthType]').each(function(){
		if($(this).hasClass('btnState-true')){
			wealthType += $(this).val() + ',';
		}
	});
	if(wealthType){
		wealthType = wealthType.substr(0,wealthType.length-1);
	}
	var applicVal = $("#applicant").val();
	//主申请人时，是否同主申请人设置为空，不然默认为1
	if(applicVal == 1){
		$("#wealth").val(0);
	}
	var passportInfo = $.param({"wealthType":wealthType}) + "&" +  $("#passportInfo").serialize();
	/*if(tourist == 1){
		layer.load(1);
		$.ajax({
			type: 'POST',
			async: false,
			data : passportInfo,
			url: '/admin/myData/visaIsChanged.html',
			success :function(data) {
				layer.closeAll("loading");
				if(status == 2){
					if(data == 1 || data == 2){
						layer.load(1);
						$.ajax({
							type: 'POST',
							async: false,
							data : passportInfo,
							url: '/admin/orderJp/saveEditVisa',
							success :function(data) {
								layer.closeAll("loading");
								socket.onclose();
								window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrail+'&orderProcessType&tourist=1';
							}
						});
					}else{
						socket.onclose();
						window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrail+'&orderProcessType&tourist=1';
					}
				}else{
					if(data == 2){
						layer.load(1);
						$.ajax({
							type: 'POST',
							async: false,
							data : passportInfo,
							url: '/admin/orderJp/saveEditVisa',
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
    			    		url: '/admin/myVisa/copyAllInfoToTourist.html',
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
						$.ajax({
							type: 'POST',
							async: false,
							data : {applyid:applicantId},
							url: '/admin/myData/infoIsChanged.html',
							success :function(data) {
								if(data.isPrompted == 0){//没有提示过
									if(data.base == 0){//改变了
										layer.confirm("信息已改变，您是否要更新？", {
			    							title:"提示",
			    							btn: ["是","否"], //按钮
			    							shade: false //不显示遮罩
			    						}, 
			    						function(){
			    							layer.load(1);
			    							$.ajax({
			    								type: 'POST',
			    								async: false,
			    								data : passportInfo,
			    								url: '/admin/orderJp/saveEditVisa',
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
			    							layer.load(1);
			    							$.ajax({
			    								type: 'POST',
			    								async: false,
			    								data : passportInfo,
			    								url: '/admin/orderJp/saveEditVisa',
			    								success :function(data) {
			    									layer.closeAll("loading");
			    									console.log(JSON.stringify(data));
			    									socket.onclose();
			    									window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&tourist=1&isTrial='+isTrail;
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
									}else{//没变
										layer.load(1);
										$.ajax({
											type: 'POST',
											async: false,
											data : passportInfo,
											url: '/admin/orderJp/saveEditVisa',
											success :function(data) {
												closeAll("loading");
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
												type: 'POST',
												async: false,
												data : passportInfo,
												url: '/admin/orderJp/saveEditVisa',
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
												type: 'POST',
												async: false,
												data : passportInfo,
												url: '/admin/orderJp/saveEditVisa',
												success :function(data) {
													layer.closeAll("loading");
													console.log(JSON.stringify(data));
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
		
	}else{*/
		if(status == 2){
			socket.onclose();
			window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrail+'&orderProcessType='+orderProcessType+'&addApply='+addApply;
			$.ajax({
				type: 'POST',
				data : passportInfo,
				url: '/admin/orderJp/saveEditVisa',
				success :function(data) {
				}
			});
		}else{
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : passportInfo,
				url: '/admin/orderJp/saveEditVisa',
				success :function(data) {
					layer.closeAll("loading");
						closeWindow();
						if(addApply == 1){
							parent.successCallBack(3);
						}else{
							parent.successCallBack(1);
						}
				}
			});
		}
	//}
}

//返回 
function closeWindow() {
	/*if(tourist == 1){
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
	}else{*/
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.cancelCallBack(1);
	//}
}

function cancelCallBack(status){
	closeWindow();
}
function successCallBack(status){
	parent.successCallBack(1);
	closeWindow();
}
function passportBtn(){
	save(2);
}

//合格/不合格
$(".Unqualified").click(function(){
	$(".ipt-info").slideDown();
});
$(".qualifiedBtn").click(function(){
	$(".ipt-info").slideUp();
	$("#visaRemark").val("");
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
			save(1);
		}
	});
});