//旅伴信息
$(".companyInfo").change(function(){
	var companyVal = $(".companyInfo:checked").val();
	if(companyVal == 1){
		$(".teamture").show();
		$(".teamnamefalse").show();
	}else {
		$(".teamture").hide();
		deleteBrotherEle($("div.teamnamefalse"));
		emptyContentByObj($("div.teamnamefalse"));
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
		emptyContentByObj($("div.teamnameture"));
	}else {
		$(".teamnameture").hide();
		$(".teamnamefalse").show();
		deleteBrotherEle($("div.teamnamefalse"));
		emptyContentByObj($("div.teamnamefalse"));
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
		emptyContentByObj($("div.goUS_Country"));
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
		emptyContentByObj($("div.goUS_drivers"));
	}
});

//是否有美国签证
$(".visaUS").change(function(){
	var visaUS = $("input[class=visaUS]:checked").val();
	if(visaUS == 1){
		$(".dateIssue").show();
	}else {
		$(".dateIssue").hide();
		emptyContentByObj($("div.goUS_visa"));
	}
});
//是否丢失签证
$(".lose").change(function(){
	var lose = $("input[class=lose]:checked").val();
	if(lose == 1){
		$(".yearExplain").show();
	}else {
		$(".yearExplain").hide();
		emptyContentByObj($("div.yearExplain"));
	}
});
//是否取消、撤销签证
$(".revoke").change(function(){
	var revoke = $("input[class=revoke]:checked").val();
	if(revoke == 1){
		$(".explain").show();
	}else {
		$(".explain").hide();
		emptyContentByObj($("div.explain"));
	}
});
//是否被拒绝过
$(".refuse").change(function(){
	var refuse = $("input[class=refuse]:checked").val();
	if(refuse == 1){
		$(".refuseExplain").show();
	}else {
		$(".refuseExplain").hide();
		emptyContentByObj($("div.refuseExplain"));
	}
});
//曾经是否是美国合法永久居民
$(".onceLegitimate").change(function(){
	var onceLegitimate = $("input[class=onceLegitimate]:checked").val();
	if(onceLegitimate == 1){
		$(".onceExplain").show();
	}else {
		$(".onceExplain").hide();
		emptyContentByObj($("div.onceExplain"));
	}
});
//有没有人曾代表您向美国公民和移民服务局提交过移民申请
$(".onceImmigration").change(function(){
	var onceImmigration = $("input[class=onceImmigration]:checked").val();
	if(onceImmigration == 1){
		$(".immigrationExplain").show();
	}else {
		$(".immigrationExplain").hide();
		emptyContentByObj($("div.immigrationExplain"));
	}
});
























//删除同类型的其他兄弟节点
function deleteBrotherEle(obj){
	obj.nextAll().remove();
}

//清空子元素内容
function emptyContentByObj(obj){
	obj.find("input[type='text']").each(function() {
		$(this).val("");
	});
	obj.find("select").each(function() {
		$(this).val(0);
	});
	obj.find("textarea").each(function() {
		$(this).val("");
	});
	obj.find("input[type='checkbox']").each(function() {
		$(this).prop('checked', false);
	});
	obj.find("input[type='radio']").each(function() {
		if($(this).val() == 2){
			$(this).prop("checked", "checked");
		}
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