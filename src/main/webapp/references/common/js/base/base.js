/*
 *
 *公用的js文件 base.js
 * 
 */
	var BASE_PATH = '${base}';

	/*------------------------------------------aside JS部分------------------------------------------------*/
	var _index = sessionStorage.currentPageIndex;//当前下标
	$(".sidebar-menu > li").click(function(){
		_index = $(this).attr("id")-1;
		sessionStorage.currentPageIndex = _index;
		$(".sidebar-menu > li").removeClass('active');
	    $(this).addClass("active");//同时 添加记录样式
	});
	//cookie记录已点击的index
	if(_index!=null && undefined != _index && _index != ""){
	    $(".sidebar-menu > li").eq(_index).siblings().removeClass('active');
	    $(".sidebar-menu > li").eq(_index).addClass("active");//当前下标的元素添加样式
	    var imgUrl = $(".sidebar-menu > li").eq(_index).find('img').attr('src');//获取当前icon的路径
	    var iconNameAll = imgUrl.substr(28,imgUrl.lastIndexOf("."));//图标全名
	    //var iconName = iconNameAll.split(".");
	    //iconName.pop();
	    //iconName = iconName.join(".");//图标名（不包含后缀名）
	    var iconSrc = imgUrl.split(".");
	    iconSrc.pop();//图标文件名之前的图标路径
	    var flieSuffix = imgUrl.substr(-4);//图标 文件后缀名
	    var newIconUrl = iconSrc + "1" + flieSuffix; //拼接新图标路径
	    //alert(newIconUrl);
	    $(".sidebar-menu > li").eq(_index).find("img").attr("src",newIconUrl);//更改选中菜单状态的图标颜色
	}else{
	    $(".sidebar-menu > li").eq(0).siblings().removeClass('active');
	    $(".sidebar-menu > li").eq(0).addClass("active");//当前下标的元素添加样式
	}
		 
	//二级菜单 显示隐藏
	if($('.menu1').parent().is('.active')){
		$(this).find('.menu-ul').show();
	}else{
		$(this).find('.menu-ul').hide();
	}
		
	$('.menu1').click(function(){//点击一级菜单 二级显示/隐藏
		$(this).next('ul').toggle();
	}); 
	
	var sessionMenu = sessionStorage.menuWhetherMini;
	//alert(sessionMenu);
	if(sessionMenu == "noMini" || sessionMenu == undefined){
		$('aside').removeClass('min-sidebar');
		$('.content-wrapper').removeClass('min-content-wrapper');
	}else if(sessionMenu == "isMini"){
		$('aside').addClass('min-sidebar');
		$('.content-wrapper').addClass('min-content-wrapper');
	}

	//点击汉堡图 左菜单栏收起 
	$('.hamburgerImg').click(function(){
		//$('.main-sidebar').toggleClass('min-sidebar');
		//$('.content-wrapper').toggleClass('min-content-wrapper');
		//$('.logo').toggleClass('min-logo');
		//$('.logo-lg').toggle();
		//$('.navbar-static-top').toggleClass('min-navbar-static-top');
		var menuWhetherMini = $('.main-sidebar').hasClass('min-sidebar');//左菜单栏 是否缩进
		if(menuWhetherMini == false){//缩进
			$('.main-sidebar').addClass('min-sidebar');
			$('.content-wrapper').addClass('min-content-wrapper');
			sessionStorage.menuWhetherMini = "isMini"; //false
		}else if(menuWhetherMini == true){//展开
			$('.main-sidebar').removeClass('min-sidebar');
			$('.content-wrapper').removeClass('min-content-wrapper');
			sessionStorage.menuWhetherMini = "noMini"; //true
		}

	});
	
	/*----------------------------------------end aside JS部分---------------------------------------------*/  


	//点击 蓝色加号图标 事件
	$('.add-btn').click(function(){
    	var $html=$(this).parent().clone();//克隆标签模块
    	$(this).parents('.info').append($html);//添加克隆的内容
    	$html.find('.add-btn').remove();
    	$html.append('<i class="remove-btn"></i>');
	});

	//点击 蓝色叉号图标 事件
	$(".info").on("click", ".remove-btn", function(){
		$(this).parent().remove();//删除 对相应的本模块
	});

	//点击 灯泡 变亮
	$('.bulb').click(function(){
		$(this).toggleClass('bulb1');
	});

