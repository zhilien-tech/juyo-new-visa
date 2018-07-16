// 定义一个触发焦点事件的开关，默认为不开启状态 || 也可以给input设置一个属性，来判断
	var isBox = false; 
    	$(".dropdown").hide(); 
	// input绑定焦点事件，触发时打开焦点开关
    $(".input").focus(function () { 
      	$(this).siblings(".dropdown").show(); 
     	isBox = true;
    });
    // 鼠标进入input-box区域内打开焦点开关
    $(".input-box").mousemove(function () { 
      isBox = true;
    });
    // 鼠标离开input-box区域内关闭焦点开关
    $(".input-box").mouseout(function () { 
      isBox = false;
    });
    // input失去焦点时通过焦点开关状态判断鼠标所在区域
    $(".input").blur(function () { 
      if (isBox == true) return false;
      $(this).siblings(".dropdown").hide();
    });
    //input键盘输入触发事件
    $(".input").keyup(function(){
    	let $val = $('.input').val();
		    	if( $val == null || $val == ''){
		    		$(this).siblings(".dropdown").show();
		    	}else{
		    		$(this).siblings(".dropdown").hide();
		    	}
		    	isBox = false;
    })
	// 传值给input，同时关闭焦点开关
    $(".dropdown").find('li').each(function () { 
      $(this).click( function () {
        isBox = false;
        let text = $(this).text();
        $(this).parent().siblings(".input").val(text).change();
        $(".dropdown").hide();
      })
    });
    $(document).on("input","#deposit",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit != ""){
    		$(".deposits").attr("class", "col-xs-6 deposits has-success");
    		$(".deposits").css({"display":"none"});
    		$("#deposit").attr("style", null);
    	}
    });
    $(document).on("input","#vehicle",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit != ""){
    		$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
    		$(".vehicles").css({"display":"none"});
    		$("#vehicle").attr("style", "border-color:none;");
    	}
    });
    $(document).on("input","#houseProperty",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit != ""){
    		$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");  
 	        $(".housePropertys").css({"display":"none"});
 	        $("#houseProperty").attr("style", null);
    	}
    });
    $(document).on("input","#financial",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit != ""){
    		$(".financials").attr("class", "col-xs-6 financials has-success");
    		$(".financials").css({"display":"none"});
    		$("#financial").attr("style", null);
    	}
    });
    $(document).on("input","#bankflow",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var bankflow = $(this).val();
    	if(bankflow != ""){
    		$(".bankflows").attr("class", "col-xs-6 bankflows has-success");
    		$(".bankflows").css({"display":"none"});
    		$("#bankflow").attr("style", null);
    	}
    });
    $(document).on("input","#certificate",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var certificate = $(this).val();
    	if(certificate != ""){
    		$(".certificates").attr("class", "col-xs-6 certificates has-success");
    		$(".certificates").css({"display":"none"});
    		$("#certificate").attr("style", null);
    	}
    });
    $(document).on("input","#taxbill",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var taxbill = $(this).val();
    	if(taxbill != ""){
    		$(".taxbills").attr("class", "col-xs-6 taxbills has-success");
    		$(".taxbills").css({"display":"none"});
    		$("#taxbill").attr("style", null);
    	}
    });
    $(document).on("input","#taxproof",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var taxproof = $(this).val();
    	if(taxproof != ""){
    		$(".taxproofs").attr("class", "col-xs-6 taxproofs has-success");
    		$(".taxproofs").css({"display":"none"});
    		$("#taxproof").attr("style", null);
    	}
    });
    $(document).on("input","#readstudent",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var readstudent = $(this).val();
    	if(readstudent != ""){
    		$(".readstudents").attr("class", "col-xs-6 readstudents has-success");
    		$(".readstudents").css({"display":"none"});
    		$("#readstudent").attr("style", null);
    	}
    });
    $(document).on("input","#graduate",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var readstudent = $(this).val();
    	if(readstudent != ""){
    		$(".graduates").attr("class", "col-xs-6 graduates has-success");
    		$(".graduates").css({"display":"none"});
    		$("#graduate").attr("style", null);
    	}
    });
