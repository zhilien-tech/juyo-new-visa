/*
 *
 *公用的js文件   base.js
 * 
 */
	var BASE_PATH = '${base}';
	/*------------------------------------------aside JS部分------------------------------------------------*/
	//默认点击状态
	$(".sidebar-menu li").first().addClass('active');
	//默认添加点击样式
	$(".menu-ul li").first().addClass('activeTwo');
	//默认展开
	$(".sidebar-menu li.active").find('.menu-ul').slideDown('slow');
	//默认more向下
	$(".sidebar-menu li.active").find("i").removeClass("more").addClass("more_unfold");
	
	//点击一级导航触发事件
	var _index = sessionStorage.currentPageIndex;//当前下标
	$(".sidebar-menu > li").click(function(){
		//获取ID
		_index = $(this).attr("id");
		//存放在sessionStorage里面
		sessionStorage.currentPageIndex = _index;
		//移除样式
		$(".sidebar-menu > li").removeClass('active');
		//同时 添加记录样式
	    $(this).addClass("active");
	    //当选中一级菜单时
	    if($(this).hasClass("active")){
	    	
	    	//判断有没有二级菜单
	    	var ulVal = $("#"+_index).find("ul").html();
	    	
	    	if(ulVal != null && ulVal != "" && ulVal != undefined){
	    		
	    		//有
	    		$(".sidebar-menu li.active").find('.menu-ul').slideDown('slow');//显示/子菜单栏
	    		//向下图标
	    		$(".sidebar-menu li.active").find("i").removeClass("more").addClass("more_unfold");
	    		
	    	}else{
	    		//没有二级菜单
	    		$('.active').addClass('activeTwo');
	    		$('.active a').css('padding-left','0px');
	    		//移除二级导航样式
	    		$(".menu-ul li.activeTwo").removeClass('activeTwo');
	    		//隐藏所有二级导航
	    		$(".sidebar-menu li").find('.menu-ul').slideUp();
	    		//向右图标
	    		$(".sidebar-menu li i.more_unfold").removeClass("more_unfold").addClass("more");
	    	}
	    }else{
	    	$("#"+_index).find('.menu-ul').slideUp('slow');
	    }
	});
	//二级菜单  点击事件
	$(".menu-ul li").click(function(){
		liId = $(this).attr("id");
		sessionStorage.menuSecondLevelID = liId;
		//移除二级导航样式
		$(".menu-ul li.activeTwo").removeClass('activeTwo');
		//移除一级导航的样式
		$(".sidebar-menu li.activeTwo").removeClass('activeTwo');
		//添加点击的一级导航的样式
		$("#"+liId).addClass("activeTwo");
		//移除所有的二级导航
		$(".sidebar-menu li").find('.menu-ul').hide();
		$("#"+liId).parent().css('display','block');
		//向右图标
		$(".sidebar-menu li i.more_unfold").removeClass("more_unfold").addClass("more");
		
	});
	//当左侧菜单栏 默认是缩进的形式 进行判断
	var sessionMenu = sessionStorage.menuWhetherMini;
	//判断伸展状态
	if(sessionMenu == "noMini" || sessionMenu == undefined){
		//改变aside的样式
		$('aside').removeClass('min-sidebar');
		$('.content-wrapper').removeClass('min-content-wrapper');
		
		
	}else if(sessionMenu == "isMini"){
		$('aside').addClass('min-sidebar');
		$('.content-wrapper').addClass('min-content-wrapper');
		$(".sidebar-menu li a img").css('margin','25px 0 0 15px');
	}
	//隐藏一级菜单提示项
	function minSecondLevelMenuShow(){
		$(".min-sidebar > section > .sidebar-menu > .menu1").each(function(){//循环二级菜单
			var secondLevelLength = $(this).find("li").length;//二级菜单的长度
			if(secondLevelLength > 0){//有子菜单时
				$(this).find("font").hide();//隐藏 一级菜单 提示项
				$(this).find(".menu-ul").removeClass("none");
			}
		});
	}
	minSecondLevelMenuShow();
	//点击汉堡图 左菜单栏收起 
	$('.hamburgerImg').click(function(){
//		window.parent.document.getElementsByTagName("frameset")[1].cols="50,*"; 
		var menuWhetherMini = $('.main-sidebar').hasClass('min-sidebar');//左菜单栏 是否缩进
		if(menuWhetherMini == false){//缩进
			$('.main-sidebar').addClass('min-sidebar');
			$('.content-wrapper').addClass('min-content-wrapper');
			sessionStorage.menuWhetherMini = "isMini"; //false
			$(".sidebar-menu li a img").css('margin','25px 0 0 15px');
			minSecondLevelMenuShow();
			
		}else if(menuWhetherMini == true){//展开
//			window.parent.document.getElementsByTagName("frameset")[1].cols="220,*"; 
			$('.main-sidebar').removeClass('min-sidebar');
			$('.content-wrapper').removeClass('min-content-wrapper');
			sessionStorage.menuWhetherMini = "noMini"; //true
			$(".menu1").find("font").show();//显示一级文字 菜单项
			$(".active").siblings().find(".menu-ul").addClass("none");//除选中项展示子菜单 其他子菜单 全部隐藏
			$(".sidebar-menu li a img").css('margin','0 15px 0 30px');
			
		}
	});
	/*----------------------------------------end aside JS部分---------------------------------------------*/  


//	//点击 蓝色加号图标 事件
//	$('.add-btn').click(function(){
//    	var $html=$(this).parent().clone();//克隆标签模块
//    	$(this).parents('.info').append($html);//添加克隆的内容
//    	$html.find('.add-btn').remove();
//    	$html.append('<i class="remove-btn"></i>');
//	});
//
//	//点击 蓝色叉号图标 事件
//	$(".info").on("click", ".remove-btn", function(){
//		$(this).parent().remove();//删除 对相应的本模块
//	});
//
//	//点击 灯泡 变亮
//	$('.bulb').click(function(){
//		$(this).toggleClass('bulb1');
//	});
//	
	
	

	
	
	