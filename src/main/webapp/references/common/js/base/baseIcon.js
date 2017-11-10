/*
 *
 *公用的js文件  baseIcon.js
 * 
 */
	
	$(document).on("mouseover",".edit-icon",function(){
		var t = "编辑";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	$(document).on("mouseover",".delete-icon",function(){
		var t = "删除";
		layer.tips("<span style='font-size:12px;height:20px;line-height:20px;'>"+t+"</span>", ($(this)),{ tips: [1, '#242d34'],time:1000});
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
