//菜单显示隐藏效果
$(function(){
	$('.title').each(function(index){
		$(".menuson li").first().addClass("active");
		
		$(this).click(function(){
			let $ul = $(this).next('ul');
			let more = $('.more');
			$('dd').find('ul').slideUp('slow');
			more.css('transform','rotate(0deg)');
			$('dd').css({'background':'#252d35','color':'#71a3e3'});
			$(".menuOnly").css('color',"#71a3e3");
			
			if($ul.is(':visible')){
				//隐藏
				$(this).next('ul').slideUp('slow');
				more.eq(index).css('transform','rotate(0deg)');
				$('dd').eq(index).css({'background':'#252d35','color':'#71a3e3'});
				$('.menuOnly').css('color',"#71a3e3");
			}else{
				//显示
				$(this).next('ul').slideDown('slow');
				more.eq(index).css('transform','rotate(90deg)');
				$('dd').eq(index).css({'background':'#3c8dbc','color':'#FFF'});
				$('dd').eq(index).find('.menuOnly').css('color','#FFF');
			}
		})
	});
	//子导航点击添加active class 样式显示
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active");
		$(this).addClass("active");
	});
})