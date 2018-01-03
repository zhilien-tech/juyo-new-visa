// 定义一个触发焦点事件的开关，默认为不开启状态 || 也可以给input设置一个属性，来判断
	let isBox = false; 
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
		    		$(".dropdown").show();
		    	}else{
		    		$(".dropdown").hide();
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
    	if(deposit == ""){
    		$(".deposits").attr("class", "col-xs-6 deposits has-error");  
	        $(".deposits").attr("data-bv-result","INVALID");  
	        $(".deposits").css({"display":"block"});
    	}else{
    		$(".deposits").css({"display":"none"});
    	}
    });
    $(document).on("input","#vehicle",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit == ""){
    		$(".vehicles").attr("class", "col-xs-6 vehicles has-error");  
	        $(".help-blockvehicle").attr("data-bv-result","INVALID");  
	        $(".vehicles").css({"display":"block"});
    	}else{
    		$(".vehicles").css({"display":"none"});
    	}
    });
    $(document).on("input","#houseProperty",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit == ""){
    		$(".housePropertys").attr("class", "col-xs-6 housePropertys has-error");  
	        $(".help-blockhouseProperty").attr("data-bv-result","INVALID");  
	        $(".housePropertys").css({"display":"block"});
    	}else{
 	        $(".housePropertys").css({"display":"none"});
    	}
    });
    $(document).on("input","#financial",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var deposit = $(this).val();
    	if(deposit == ""){
    		$(".financials").attr("class", "col-xs-6 financials has-error");  
	        $(".help-blockfinancial").attr("data-bv-result","INVALID");  
	        $(".financials").css({"display":"block"});
    	}else{
    		$(".financials").css({"display":"none"});
    	}
    });
