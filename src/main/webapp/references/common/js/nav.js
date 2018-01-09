$(function(){
	//默认显示第一个
	$(".nav-item").first().addClass('nav-show');
	$(".navUl li").first().addClass('navLi');
	$(".nav-item li.nav-show").find('.navUl').show();
    // nav收缩展开
	$('.nav-item > a').on('click',function(){
		if (!$('.nav').hasClass('nav-mini')) {
            if ($(this).next().css('display') == "none") {
                //展开未展开
                $('.nav-item').children('ul').hide();
                $(this).next('ul').show();
                $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
            }else{
                //收缩已展开
                $(this).next('ul').hide();
                $('.nav-item.nav-show').removeClass('nav-show');
            }
        }
    });
//    二级导航点击事件
    $(".navUl li").on('click',function(){
    	$('.navLi').removeClass('navLi');
    	$(this).addClass('navLi').siblings().removeClass('navLi');
    });
    //nav-mini切换
    $('#mini').on('click',function(){
        if (!$('.nav').hasClass('nav-mini')) {
//        	点击缩进修改内容区域大小
        	$(".main").css('width','calc(100% - 60px)');
//        	点击缩进修改a标签样式
        	//$(".nav a").css('padding-left','20px');
            $('.nav-item.nav-show').removeClass('nav-show');
            $('.nav-item').children('ul').removeAttr('style');
            $('.nav').addClass('nav-mini');
        }else{
            $('.nav').removeClass('nav-mini');
            $(".main").css('width','calc(100% - 170px)');
           // $(".nav a").css('padding-left','50px');
        }
    });
});