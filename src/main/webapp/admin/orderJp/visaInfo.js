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
    
    
  //单位名称检索
    $("#name").on('input',function(){
    	var temp = $(this).val();
    	if(temp.length == 0){
    	$("#name").nextAll("ul.ui-autocomplete").remove();
    	$.ajax({
    		type : 'POST',
    		async: false,
    		data : {
    			searchStr : $("#name").val()
    		},
    		url : BASE_PATH+'/admin/simple/getUnitname.html',
    		success : function(data) {
    			if(data != ""){
    				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
    				$.each(data,function(index,element) { 
    					liStr += "<li onclick='setName("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
    				});
    				liStr += "</ul>";
    				$("#name").after(liStr);
    			}
    		}
    	});
    	}
    });
    //单位名称上下键
    var nameindex = -1;
    $(document).on('keydown','#name',function(e){
    	
    	if(e == undefined)
    		e = window.event;
    	
    	switch(e.keyCode){
    	case 38:
    		e.preventDefault();
    		nameindex--;
    		if(nameindex ==0) nameindex = 0;
    		break;
    	case 40:
    		e.preventDefault();
    		nameindex++;
    		if(nameindex ==5) nameindex = 0;
    		break;
    	case 13:
    		
    		$(this).val($(this).next().find('li:eq('+nameindex+')').children().html());
    		$("#name").nextAll("ul.ui-autocomplete").remove();
    		$("#name").blur();
    		var name = $("#name").val();
    		setName(name);
    		nameindex = -1;
    		break;
    	}
    	var li = $(this).next().find('li:eq('+nameindex+')');
    	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
    });
    //单位名称 检索下拉项
    function setName(name){
    	$("#name").nextAll("ul.ui-autocomplete").remove();
    	$("#name").val(name.substring(0,name.indexOf(" "))).change();
    	var regex=/\ (.+?)\ /g;
        var result;
        while((result=regex.exec(name))!=null) {
          $("#telephone").val(result[1]);
        }
        $("#address").val(name.substring(name.lastIndexOf(" ")+1,name.length));
    } 
    $("#nameDiv").mouseleave(function(){
    	$("#name").nextAll("ul.ui-autocomplete").remove();
    });

    //单位电话检索
    $("#telephone").on('input',function(){
    	var temp = $(this).val();
    	if(temp.length == 0){
    	$("#telephone").nextAll("ul.ui-autocomplete").remove();
    	$.ajax({
    		type : 'POST',
    		async: false,
    		data : {
    			searchStr : $("#telephone").val()
    		},
    		url : BASE_PATH+'/admin/simple/getUnittelephone.html',
    		success : function(data) {
    			if(data != ""){
    				var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
    				$.each(data,function(index,element) { 
    					liStr += "<li onclick='setTelephone("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><span id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</span></li>";
    				});
    				liStr += "</ul>";
    				$("#telephone").after(liStr);
    			}
    		}
    	});
    	}
    });
    //单位电话
    var telephoneindex = -1;
    $(document).on('keydown','#telephone',function(e){
    	
    	if(e == undefined)
    		e = window.event;
    	
    	switch(e.keyCode){
    	case 38:
    		e.preventDefault();
    		telephoneindex--;
    		if(telephoneindex ==0) telephoneindex = 0;
    		break;
    	case 40:
    		e.preventDefault();
    		telephoneindex++;
    		if(telephoneindex ==5) telephoneindex = 0;
    		break;
    	case 13:
    		
    		$(this).val($(this).next().find('li:eq('+telephoneindex+')').children().html());
    		$("#telephone").nextAll("ul.ui-autocomplete").remove();
    		$("#telephone").blur();
    		var telephone = $("#telephone").val();
    		setTelephone(telephone);
    		telephoneindex = -1;
    		break;
    	}
    	var li = $(this).next().find('li:eq('+telephoneindex+')');
    	li.css({'background':'#1e90ff','color':'#FFF'}).siblings().css({'background':'#FFF','color':'#000'});
    });
    //单位电话 检索下拉项
    function setTelephone(telephone){
    	$("#telephone").nextAll("ul.ui-autocomplete").remove();
    	
    	var regex=/\ (.+?)\ /g;
        var result;
        while((result=regex.exec(telephone))!=null) {
          $("#telephone").val(result[1]);
        }
        $("#name").val(telephone.substring(0,telephone.indexOf(" ")));
        $("#address").val(telephone.substring(telephone.lastIndexOf(" ")+1,telephone.length));
    	
    } 
    $("#telephoneDiv").mouseleave(function(){
    	$("#telephone").nextAll("ul.ui-autocomplete").remove();
    });
    
    $(document).on("input","#name",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var name = $(this).val();
    	if(name == ""){
    		$("#telephone").val("");
    		$("#address").val("");
    	}
    });
    /*$(document).on("input","#telephone",function(){
    	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
    		return;
    	}
    	var telephone = $(this).val();
    	if(telephone == ""){
    		$("#address").val("");
    	}
    });*/
    
