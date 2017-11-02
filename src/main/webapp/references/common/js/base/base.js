/*
 *
 *公用的js文件   base.js
 * 
 */
	var BASE_PATH = '${base}';

	/*------------------------------------------aside JS部分------------------------------------------------*/
	var _index = sessionStorage.currentPageIndex;//当前下标
	$(".sidebar-menu > li").click(function(){
		///_index = $(this).attr("id")-1;
		_index = $(this).attr("id");
		sessionStorage.currentPageIndex = _index;
		$(".sidebar-menu > li").removeClass('active');
	    $(this).addClass("active");//同时 添加记录样式
	    if($(this).hasClass("active")){//当选中一级菜单时
			$(this).siblings(".menu1").next(".menu-ul").hide();
			$(this).next('.menu-ul').toggle();
			
		}else{
			//alert("不含有.active");
		}
	});
	cookieMenu();
	function cookieMenu(){
		//cookie记录已点击的index
		if(_index!=null && undefined != _index && _index != ""){
			$("#"+_index).siblings().removeClass('active');
			$("#"+_index).addClass("active");//当前下标的元素添加样式
			
			var ulVal = $("#"+_index).next("ul").html();
			if(ulVal != null && ulVal != "" && ulVal != undefined){//有二级菜单时
				var liId = sessionStorage.menuSecondLevelID;
				if(liId != null && undefined != liId && liId != ""){
					$(".sidebar-menu > li").removeClass('active');//删除 一级菜单选中style
					$("#"+liId).parent().removeClass("none");//显示二级菜单
					 menu1Id = sessionStorage.currentPageIndex;
					$("#" + menu1Id).addClass("active");
					
					$("#"+liId).addClass("activeTwo");//添加二级选中的 样式
					$("#" + menu1Id).addClass("activeOne")//添加一级选中的 样式
				}
			}else{//没有二级菜单时
				//alert("无二级");
			}
			
			var imgUrl = $("#"+_index).find('img').attr('src');//获取当前icon的路径
		    var iconSrc = imgUrl.split(".");//图标 文件前文件路径
		    iconSrc.pop();//图标文件名之前的图标路径
		    var flieSuffix = imgUrl.substr(-4);//图标 文件后缀名
		    var newIconUrl = iconSrc + "1" + flieSuffix; //拼接新图标路径
		    $("#"+_index).eq(_index).find("img").attr("src",newIconUrl);//更改选中菜单状态的图标颜色
		}else{
		    $(".sidebar-menu > li").eq(0).siblings().removeClass('active');
		    $(".sidebar-menu > li").eq(0).addClass("active");//当前下标的元素添加样式
		}
	}
	
	//二级菜单  点击事件
	$(".menu-ul li").click(function(){
		liId = $(this).attr("id");
		sessionStorage.menuSecondLevelID = liId;
	});
	/*//鼠标滑过一级菜单栏时
	$(".sidebar-menu > li").mouseover(function(){
		var iconURL = $(this).find('img').attr('src');//获取 图标 路径
		//console.log("-----"+iconURL);
		var bl = /\b+/.test(iconURL);
		alert(bl);
		var frontName = iconURL.split(".");//图标 文件前文件路径
		frontName.pop();//图标文件名之前的图标路径
		var afterName = iconURL.substr(-4);//图标 文件后缀名
		var newFlieSrc = frontName+"1"+afterName;
		$(this).find('img').attr('src',newFlieSrc);
	}).mouseout(function(){
		var iconURL1 = $(this).find('img').attr('src');//获取 图标 路径
		var frontName1 = iconURL1.split("1");//图标 文件前文件路径
		frontName1.pop();//图标文件名之前的图标路径
		var afterName1 = iconURL1.substr(-4);//图标 文件后缀名
		var newFlieSrc1 = frontName1 + afterName1;
		$(this).find('img').attr('src',newFlieSrc1);
	});
	*/
	
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

