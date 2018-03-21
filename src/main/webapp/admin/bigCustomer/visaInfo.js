//旅伴信息
$(".companyInfo").change(function(){
	var companyVal = $(".companyInfo:checked").val();
	if(companyVal == 1){
		$(".teamture").show();
		$(".teamnamefalse").show();
	}else {
		$(".teamture").hide();
		deleteBrotherEle($("div.teamnamefalse"));
		emptyContentById($("div.teamnamefalse"));
		//触发单选按钮的点击事件
		$(".team").eq(1).click();
	}
});
//旅伴信息--是否作为团队或组织的一部分旅游
$(".team").change(function(){
	var teamVal = $("input[class=team]:checked").val(); 
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

//以前的美国旅游信息
//(1)是否去过美国
$(".goUS").change(function(){
	var goUS = $("input[class=goUS]:checked").val();
	if(goUS == 1){
		$(".goUSInfo").show();
	}else{
		$(".goUSInfo").hide();
		deleteBrotherEle($("div.goUS_Country"));
		emptyContentById($("div.goUS_Country"));
		//触发单选按钮的点击事件
		$(".license").eq(1).click();
	}

});
//(2)是否有美国驾照
$(".license").change(function(){
	var license = $("input[class=license]:checked").val();
	if(license == 1){
		$(".driverInfo").show();
	}else{
		$(".driverInfo").hide();
		deleteBrotherEle($("div.goUS_drivers"));
		emptyContentById($("div.goUS_drivers"));
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
	obj.find("radio").each(function() {
		$(this).eq(1).attr("checked",true);
	});
	
}

//勾选checkbox("不知道")，设置前一个兄弟级元素disable和no edit
function editEleBeforeCheckbox(obj){
	var beforeEle = obj.prev();
	beforeEle.val("");
	beforeEle.attr("disabled","disabled");
}




//取消关闭窗口
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}