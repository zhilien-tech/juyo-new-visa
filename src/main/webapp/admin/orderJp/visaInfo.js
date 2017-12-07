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
        $(this).parent().siblings(".input").val(text);
        $(".dropdown").hide();
      })
    })