//旅伴信息
$(".companyInfo").change(function(){
	var companyVal = $(".companyInfo:checked").val();
	if(companyVal == 1){
		$(".teamture").show();
		$(".teamnamefalse").show();
	}else {
		$(".teamture").hide();
	}
});
//旅伴信息--是否作为团队或组织的一部分旅游
$(".team").change(function(){
	var teamVal = $("input[name=team]:checked").val(); 
	if(teamVal == 1){
		$(".teamnameture").show();
		$(".teamnamefalse").hide();
		emptyContentById($("div.teamnameture"));
	}else {
		$(".teamnameture").hide();
		$(".teamnamefalse").show();
		deleteBrotherEle($("div.teamnamefalse"));
		emptyContentById($("div.teamnamefalse"));
	}
});
























//删除同类型的其他兄弟节点
function deleteBrotherEle(obj){
	obj.nextAll().remove();
}

//清空子元素内容
function emptyContentById(obj){
	obj.find("input").each(function() {
		$(this).val("");
	});
	obj.find("select").each(function() {
		$(this).val(0);
	});
	obj.find("textarea").each(function() {
		$(this).val("");
	});
	obj.find("checkbox").each(function() {
		$(this).attr("checked",false);
	});
	
}





//取消关闭窗口
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}