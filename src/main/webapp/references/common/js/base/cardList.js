/*
 *
 *卡片式js文件 cardList.js
 * 
 */

//卡片式 label宽度设置
$(".everybody-info").each(function(){
	$(this).find('div').each(function(index){
		var $thisLabel = $(this).find('label');
		var labelLength = ($thisLabel.text()).length;
		if(labelLength == 3){//label 两个字时
			$thisLabel.css("width","36px");
		}else if(labelLength == 4){//label 三个字时
			$thisLabel.css("width","48px");
		}else if(labelLength == 5){//label 四个字时
			$thisLabel.css("width","60px");
		}
	});
});

//除第一行label有列名 其他行列名全部清除
$(".card-list").each(function(){
	$(this).find(".everybody-info").not(":first").find("label").empty();
});
	