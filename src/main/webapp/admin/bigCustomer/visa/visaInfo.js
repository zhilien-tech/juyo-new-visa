//---------------------------------------工具方法 start-------------------------------------------
//克隆添加多段
function cloneMoreDiv(divClass){
	var cloneDiv = $("."+divClass).eq(0).clone();
	emptyContentByObj(cloneDiv);
	$("."+divClass).last().after(cloneDiv);
	initDateTimePicker($("."+divClass));
}
//删除多段
function deleteMoreDiv(divClass){
	if($("."+divClass).length>1){
		$("."+divClass+":last").remove();
	}
}

//克隆时，查看当前div下是否有DateTimePicker，如果有则进行初始化
function initDateTimePicker(obj){
	obj.find("input[type='text'][class *='datetimepickercss']").each(function() {
		$(this).datetimepicker({
			format: 'dd/mm/yyyy',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		}); 
	});
}

initDateTimePicker($(document));

//checkbox添加多段checked
function AddSegment(obj,objen){
	//获取当前在那个div下
	var IndexCheckbox = $(obj).parent().parent().index();
	$(obj).prev().val("");
	$("."+objen).eq(IndexCheckbox).prev().val("");
	
	if($(obj).is(':checked')){
		
		$(obj).prev().prop("disabled",true);
		$("."+objen).eq(IndexCheckbox).prop("checked",true);
		$("."+objen).eq(IndexCheckbox).prev().prop("disabled",true);
	}else{
		
		$(obj).prev().prop("disabled",false);
		$("."+objen).eq(IndexCheckbox).prop("checked",false);
		$("."+objen).eq(IndexCheckbox).prev().prop("disabled",false);
	}
}
//checkbox其他
function AddSingle(obj,objen){
	//获取当前在那个div下
	var IndexCheckbox = $(obj).parent().index();
	$(obj).prev().val("");
	$("."+objen).prev().val("");
	
	if($(obj).is(':checked')){
		
		$(obj).prev().prop("disabled",true);
		$("."+objen).prop("checked",true);
		$("."+objen).prev().prop("disabled",true);
	}else{
		
		$(obj).prev().prop("disabled",false);
		$("."+objen).prop("checked",false);
		$("."+objen).prev().prop("disabled",false);
	}
}
//checkbox英文 暂时不用
function disableden(obj){
	$(obj).prev().val("");
	if($(obj).is(':checked')){
		$(obj).prev().prop("disabled",true);
	}else{
		$(obj).prev().prop("disabled",false);
	}
}

//删除同类型的其他兄弟节点
function deleteBrotherEle(obj){
	obj.nextAll().remove();
}

//清空子元素内容
function emptyContentByObj(obj){
	obj.find("input[type='text']").each(function() {
		$(this).val("");
		$(this).prop("disabled",false);
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


//获取拼音字符串
document.write('<script language=javascript src="/references/common/js/pinyin.js"></script>');
function getPinYinStr(hanzi){
	var onehanzi = hanzi.split('');
	var pinyinchar = '';
	for(var i=0;i<onehanzi.length;i++){
		pinyinchar += PinYin.getPinYin(onehanzi[i]);
	}
	return pinyinchar.toUpperCase();
}

//---------------------------------------工具方法 end-------------------------------------------



//-------------------------------------------旅伴信息 Start----------------------------------
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
		deleteBrotherEle($("div.teamnamefalseDiv"));
		emptyContentByObj($("div.teamnamefalse"));
	}
	$(".companyInfoen").eq(companyVal-1).click();
});
//旅伴信息英文
$(".companyInfoen").change(function(){
	var companyValen = $(".companyInfoen:checked").val();
	if(companyValen == 1){
		$(".teamtureen").show();
		$(".teamnamefalseen").show();
	}else {
		$(".teamtureen").hide();
		deleteBrotherEle($("div.teamnamefalseen"));
		emptyContentByObj($("div.teamnamefalseen"));
		//触发单选按钮的点击事件
		$(".teamen").eq(1).click();
		deleteBrotherEle($("div.teamnamefalseDiven"));
		emptyContentByObj($("div.teamnamefalseen"));
	}
});
//旅伴信息--是否作为团队或组织的一部分旅游
$(".team").change(function(){
	var teamVal = $("input[class=team]:checked").val(); 
	if(teamVal == 1){
		$(".teamnameture").show();
		$(".teamnamefalse").hide();
		$(".companyGroup").hide();
		emptyContentByObj($("div.teamnameture"));
	}else {
		$(".teamnameture").hide();
		$(".teamnamefalse").show();
		$(".teamnamefalseDiv").show();
		$(".companyGroup").show();
		deleteBrotherEle($("div.teamnamefalseDiv"));
		emptyContentByObj($("div.teamnamefalse"));
	}
	$(".teamen").eq(teamVal-1).click();
});
//旅伴信息--是否作为团队或组织的一部分旅游英文
$(".teamen").change(function(){
	var teamValen = $("input[class=teamen]:checked").val(); 
	if(teamValen == 1){
		$(".teamnametureen").show();
		$(".teamnamefalseen").hide();
		$(".companyGroupen").hide();
		emptyContentByObj($("div.teamnametureen"));
	}else {
		$(".teamnametureen").hide();
		$(".teamnamefalseen").show();
		$(".teamnamefalseDiven").show();
		$(".companyGroupen").show();
		deleteBrotherEle($("div.teamnamefalseDiven"));
		emptyContentByObj($("div.teamnamefalseen"));
	}
});
//旅伴信息多段操作
$(".companysave").click(function(){
	cloneMoreDiv("teamaddfalse");
	$(".companysaveen").trigger("click");
});
//旅伴信息多段操作英文
$(".companysaveen").click(function(){
	cloneMoreDiv("teamaddfalseen");
});
//删除
$(".companycancel").click(function(){
	deleteMoreDiv("teamaddfalse");
	$(".companycancelen").trigger("click");
});
//删除英文 
$(".companycancelen").click(function(){
	deleteMoreDiv("teamaddfalseen");
});
//-------------------------------------------家庭信息 end----------------------------------

//---------------------------------------以前的美国旅游信息 start----------------------------------
//(1)是否去过美国
$(".goUS").change(function(){
	var goUS = $("input[class=goUS]:checked").val();
	if(goUS == 1){
		$(".goUSInfo").show();
	}else{
		$(".goUSInfo").hide();
		deleteBrotherEle($("div.goUS_CountryDiv"));
		emptyContentByObj($("div.goUS_CountryDiv"));
		//触发单选按钮的点击事件
		$(".license").eq(1).click();
	}
	$(".goUSen").eq(goUS-1).click();
});
//(1)是否去过美国英文
$(".goUSen").change(function(){
	var goUSen = $("input[class=goUSen]:checked").val();
	if(goUSen == 1){
		$(".goUSInfoen").show();
	}else{
		$(".goUSInfoen").hide();
		deleteBrotherEle($("div.goUS_CountryDiven"));
		emptyContentByObj($("div.goUS_CountryDiven"));
		//触发单选按钮的点击事件
		$(".licenseen").eq(1).click();
	}
});
//是否有美国驾照
$(".license").change(function(){
	var license = $("input[class=license]:checked").val();
	if(license == 1){
		$(".driverInfo").show();
	}else{
		$(".driverInfo").hide();
		deleteBrotherEle($("div.goUS_drivers"));
		emptyContentByObj($("div.goUS_drivers"));
	}
	$(".licenseen").eq(license-1).click();
});
//是否有美国驾照英文
$(".licenseen").change(function(){
	var licenseen = $("input[class=licenseen]:checked").val();
	if(licenseen == 1){
		$(".driverInfoen").show();
	}else{
		$(".driverInfoen").hide();
		deleteBrotherEle($("div.goUS_driversen"));
		emptyContentByObj($("div.goUS_driversen"));
	}
});
//不知道驾照号
//editEleBeforeCheckbox(".isknowdrivernumber",".isknowdrivernumberen");

//是否有美国签证
$(".visaUS").change(function(){
	var visaUS = $("input[class=visaUS]:checked").val();
	if(visaUS == 1){
		$(".dateIssue").show();
	}else {
		$(".dateIssue").hide();
		emptyContentByObj($("div.goUS_visa"));
	}
	$(".visaUSen").eq(visaUS-1).click();
});
//是否有美国签证英文
$(".visaUSen").change(function(){
	var visaUSen = $("input[class=visaUSen]:checked").val();
	if(visaUSen == 1){
		$(".dateIssueen").show();
	}else {
		$(".dateIssueen").hide();
		emptyContentByObj($("div.goUS_visaen"));
	}
});
//不知道签证号
//editEleBeforeCheckbox($("#idknowvisanumber"));

//是否丢失签证
$(".lose").change(function(){
	var lose = $("input[class=lose]:checked").val();
	if(lose == 1){
		$(".yearExplain").show();
	}else {
		$(".yearExplain").hide();
		emptyContentByObj($("div.yearExplain"));
	}
	$(".loseen").eq(lose-1).click();
});
//是否丢失签证英文
$(".loseen").change(function(){
	var loseen = $("input[class=loseen]:checked").val();
	if(loseen == 1){
		$(".yearExplainen").show();
	}else {
		$(".yearExplainen").hide();
		emptyContentByObj($("div.yearExplainen"));
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
	$(".revokeen").eq(revoke-1).click();
});
//是否取消、撤销签证英文
$(".revokeen").change(function(){
	var revokeen = $("input[class=revokeen]:checked").val();
	if(revokeen == 1){
		$(".explainen").show();
	}else {
		$(".explainen").hide();
		emptyContentByObj($("div.explainen"));
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
	$(".refuseen").eq(refuse-1).click();
});
//是否被拒绝过英文
$(".refuseen").change(function(){
	var refuseen = $("input[class=refuseen]:checked").val();
	if(refuseen == 1){
		$(".refuseExplainen").show();
	}else {
		$(".refuseExplainen").hide();
		emptyContentByObj($("div.refuseExplainen"));
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
	$(".onceLegitimateen").eq(onceLegitimate-1).click();
});
//曾经是否是美国合法永久居民英文
$(".onceLegitimateen").change(function(){
	var onceLegitimateen = $("input[class=onceLegitimateen]:checked").val();
	if(onceLegitimateen == 1){
		$(".onceExplainen").show();
	}else {
		$(".onceExplainen").hide();
		emptyContentByObj($("div.onceExplainen"));
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
	$(".onceImmigrationen").eq(onceImmigration-1).click();
});
//有没有人曾代表您向美国公民和移民服务局提交过移民申请英文
$(".onceImmigrationen").change(function(){
	var onceImmigrationen = $("input[class=onceImmigrationen]:checked").val();
	if(onceImmigrationen == 1){
		$(".immigrationExplainen").show();
	}else {
		$(".immigrationExplainen").hide();
		emptyContentByObj($("div.immigrationExplainen"));
	}
});

//去过美国多段
$(".beforesave").click(function(){
	cloneMoreDiv("goUS_CountryDiv");
	$(".beforesaveen").trigger("click");
});		
//去过美国多段英文
$(".beforesaveen").click(function(){
	cloneMoreDiv("goUS_CountryDiven");
});	
//删除
$(".beforecancel").click(function(){
	deleteMoreDiv("goUS_CountryDiv");
	$(".beforecancelen").trigger("click");
});
//删除英文
$(".beforecancelen").click(function(){
	deleteMoreDiv("goUS_CountryDiven");
});

//是否有驾照
$(".driversave").click(function(){
	cloneMoreDiv("goUS_drivers");
	$(".driversaveen").trigger("click");
});
//是否有驾照英文
$(".driversaveen").click(function(){
	cloneMoreDiv("goUS_driversen");
});
//删除
$(".drivercancel").click(function(){
	deleteMoreDiv("goUS_drivers");
	$(".drivercancelen").trigger("click");
});
//删除英文
$(".drivercancelen").click(function(){
	deleteMoreDiv("goUS_driversen");
});
//---------------------------------------以前的美国旅游信息 end----------------------------------




//-------------------------------------------美国联络点 Start----------------------------------
//美国联络人姓名

	$("#isknowname").change(function(){
		var beforeEle = $("#isknowname").prev();
		var beforeEleen = $("#isknownameen").prev();
		var linkageinput = $("#isknowname").parent().prev().children("input");
		var linkageinputen = $("#isknownameen").parent().prev().children("input");
		console.log(linkageinput);
		console.log(linkageinputen);
		beforeEle.val("");
		beforeEleen.val("");
		if($("#isknowname").is(':checked')){
			linkageinput.prop("disabled",true);
			beforeEle.prop("disabled",true);
			$("#isknownameen").prop('checked',true);
			linkageinputen.prop("disabled",true);
			beforeEleen.prop("disabled",true);
		}else{
			linkageinput.prop("disabled",false);
			beforeEle.prop("disabled",false);
			$("#isknownameen").prop('checked',false);
			linkageinputen.prop("disabled",false);
			beforeEleen.prop("disabled",false);
		}
	});

//-------------------------------------------美国联络点 end------------------------------------




//-------------------------------------------家庭信息 Start----------------------------------
//亲属信息
//父亲是否在美国 
$(".fatherUS").change(function(){
	var fatherUS = $("input[class=fatherUS]:checked").val();
	if(fatherUS == 1){
		$(".fatherUSYes").show();
	}else {
		$(".fatherUSYes").hide();
		emptyContentByObj($("div.fatherUSYes"));
	}
	$(".fatherUSen").eq(fatherUS-1).click();
});
//父亲是否在美国英文
$(".fatherUSen").change(function(){
	var fatherUSen = $("input[class=fatherUSen]:checked").val();
	if(fatherUSen == 1){
		$(".fatherUSYesen").show();
	}else {
		$(".fatherUSYesen").hide();
		emptyContentByObj($("div.fatherUSYesen"));
	}
});
//不知道父亲的姓 
//editEleBeforeCheckbox($("#isKnowFatherXing"));
//不知道父亲的名
//editEleBeforeCheckbox($("#isKnowFatherMing"));
//母亲是否在美国
$(".motherUS").change(function(){
	var motherUS = $("input[class=motherUS]:checked").val();
	if(motherUS == 1){
		$(".motherUSYes").show();
	}else {
		$(".motherUSYes").hide();
		emptyContentByObj($("div.motherUSYes"));
	}
	$(".motherUSen").eq(motherUS-1).click();
});
//母亲是否在美国英文
$(".motherUSen").change(function(){
	var motherUSen = $("input[class=motherUSen]:checked").val();
	if(motherUSen == 1){
		$(".motherUSYesen").show();
	}else {
		$(".motherUSYesen").hide();
		emptyContentByObj($("div.motherUSYesen"));
	}
});
//不知道母亲的姓 
//editEleBeforeCheckbox($("#isKnowMotherXing"));
//不知道父亲的名
//editEleBeforeCheckbox($("#isKnowMotherMing"));
//在美国除了父母还有没有直系亲属
$(".directRelatives.directUSRelatives").change(function(){
	var directUSRelatives = $("input[class='directRelatives directUSRelatives']:checked").val();
	if(directUSRelatives == 1){
		$(".directRelativesYes").show();
		$(".directRelativesNo").hide();
	}else {
		$(".directRelativesYes").hide();
		$(".directRelativesNo").show();
		emptyContentByObj($("div.directRelatives"));
	}
	$(".directUSRelativesen").eq(directUSRelatives-1).click();
});
//在美国除了父母还有没有直系亲属英文
$(".directUSRelativesen").change(function(){
	var directUSRelativesen = $("input[class='directRelatives directUSRelativesen']:checked").val();
	if(directUSRelativesen == 1){
		$(".directRelativesYesen").show();
		$(".directRelativesNoen").hide();
	}else {
		$(".directRelativesYesen").hide();
		$(".directRelativesNoen").show();
		emptyContentByObj($("div.directRelativesen"));
	}
});
//配偶信息
function changeSpouseShow(){
	var opt = $("#spouseaddress").val();
	if(opt == 5){
		$(".otherSpouseInfo").show();
	}else{
		$(".otherSpouseInfo").hide();
		emptyContentByObj($("div.otherSpouseInfo"));
	}
}

//不知道配偶的城市
//editEleBeforeCheckbox($("#isKnowMateCity"));

//-------------------------------------------家庭信息 end------------------------------------




//-------------------------------------------工作/教育/培训信息 start----------------------------------
//主要职业Change事件
function occupationChange(){
	emptyContentByObj($("div.jobEduLearningInfoDiv"));
	emptyContentByObj($(".jobEduLearningInfoTextarea"));
	var occupation = $("#occupation").val();
	if(occupation==10 || occupation==19){
		//家庭主妇和退休人员
		$("div.jobEduLearningInfoDiv").hide();
		$(".jobEduLearningInfoTextarea").hide();
	}else if(occupation==15){
		//不受雇
		$("div.jobEduLearningInfoDiv").hide();
		$(".jobEduLearningInfoTextarea").show();
	}else if(occupation==22){
		//其他
		$("div.jobEduLearningInfoDiv").show();
		$(".jobEduLearningInfoTextarea").show();
	}else{
		//农民、艺术家等等
		$("div.jobEduLearningInfoDiv").show();
		$(".jobEduLearningInfoTextarea").hide();
	}
	
}
//不知道州、省				*********有问题
//editEleBeforeCheckbox($("#isKnowOrtherSpouseProvince"));
//配偶联系地址 select 其他	州/省
//editEleBeforeCheckbox($("#isprovinceapply"));
//工作/教育	州/省
//editEleBeforeCheckbox($("#isprovinceapplywork"));

//不知道邮政编码			*********有问题
//editEleBeforeCheckbox($("#isKonwOrtherZipCode"));
//配偶联系地址 select 其他	邮政编码
//editEleBeforeCheckbox($("#iszipcodeapply"));
//工作/教育	邮政编码
//editEleBeforeCheckbox($("#iszipcodeapplywork"));
//工作/教育	当地月收入
//editEleBeforeCheckbox($("#issalaryapplywork"));

//以前工作	市
//editEleBeforeCheckbox($("#employercitybefore"));
//editEleBeforeCheckbox($("#isknowsupervisorlastnamebefore"));
//editEleBeforeCheckbox($("#isknowsupervisorfirstnamebefore"));
//editEleBeforeCheckbox($("#employercitybefore"));

//以前是否工作过			
$(".beforeWork").change(function(){
	var beforeWork = $("input[class=beforeWork]:checked").val();
	if(beforeWork == 1){
		$(".beforeWorkInfo").show();
	}else {
		$(".beforeWorkInfo").hide();
		deleteBrotherEle($("div.workBeforeInfosDiv"));
		emptyContentByObj($("div.beforeWorkInfo"));
	}
	$(".beforeWorken").eq(beforeWork-1).click();
});
//以前是否工作过英文		
$(".beforeWorken").change(function(){
	var beforeWorken = $("input[class=beforeWorken]:checked").val();
	if(beforeWorken == 1){
		$(".beforeWorkInfoen").show();
	}else {
		$(".beforeWorkInfoen").hide();
		deleteBrotherEle($("div.workBeforeInfosDiven"));
		emptyContentByObj($("div.beforeWorkInfoen"));
	}
});
//
//editEleBeforeCheckbox($("#institutioncityedu"));
//editEleBeforeCheckbox($("#codeEdu"));

//是否上过中学或以上的任何教育
$(".education").change(function(){
	var education = $("input[class=education]:checked").val();
	if(education == 1){
		$(".educationInfo").show();
	}else {
		$(".educationInfo").hide();
		deleteBrotherEle($("div.midSchoolEduDiv"));
		emptyContentByObj($("div.educationInfo"));
	}
	$(".educationen").eq(education-1).click();
});
//是否上过中学或以上的任何教育英文
$(".educationen").change(function(){
	var educationen = $("input[class=educationen]:checked").val();
	if(educationen == 1){
		$(".educationInfoen").show();
	}else {
		$(".educationInfoen").hide();
		deleteBrotherEle($("div.midSchoolEduDiven"));
		emptyContentByObj($("div.educationInfoen"));
	}
});
//是否属于氏族或部落
$(".isclan").change(function(){
	var isclan = $("input[class=isclan]:checked").val();
	if(isclan == 1){
		$(".isclanYes").show();
	}else {
		$(".isclanYes").hide();
		deleteBrotherEle($("div.languagename"));
		emptyContentByObj($("div.languagename"));
		deleteBrotherEle($("div.clannameDiv"));
		emptyContentByObj($("div.clannameDiv"));
	}
	$(".isclanen").eq(isclan-1).click();
});
//是否属于氏族或部落英文
$(".isclanen").change(function(){
	var isclanen = $("input[class=isclanen]:checked").val();
	if(isclanen == 1){
		$(".isclanYesen").show();
	}else {
		$(".isclanYesen").hide();
		deleteBrotherEle($("div.languagenameen"));
		emptyContentByObj($("div.languagenameen"));
		deleteBrotherEle($("div.clannameDiven"));
		emptyContentByObj($("div.clannameDiven"));
	}
});
//过去五年是否曾去过任何国家/地区旅游
$(".istraveledanycountry").change(function(){
	var istraveledanycountry = $("input[class=istraveledanycountry]:checked").val();
	if(istraveledanycountry == 1){
		$(".isTravelYes").show();
	}else {
		$(".isTravelYes").hide();
		deleteBrotherEle($("div.travelCountry"));
		emptyContentByObj($("div.travelCountry"));
	}
	$(".istraveledanycountryen").eq(istraveledanycountry-1).click();
});
//过去五年是否曾去过任何国家/地区旅游英文
$(".istraveledanycountryen").change(function(){
	var istraveledanycountryen = $("input[class=istraveledanycountryen]:checked").val();
	if(istraveledanycountryen == 1){
		$(".isTravelYesen").show();
	}else {
		$(".isTravelYesen").hide();
		deleteBrotherEle($("div.travelCountryen"));
		emptyContentByObj($("div.travelCountryen"));
	}
});
//是否属于、致力于、或为任何专业、社会或慈善组织而工作
$(".isworkedcharitableorganization").change(function(){
	var isorganization = $("input[class=isworkedcharitableorganization]:checked").val();
	if(isorganization == 1){
		$(".isOrganizationYes").show();
	}else {
		$(".isOrganizationYes").hide();
		deleteBrotherEle($("div.organizationDiv"));
		emptyContentByObj($("div.organizationDiv"));
	}
	$(".isworkedcharitableorganizationen").eq(isworkedcharitableorganization-1).click();
});
//是否属于、致力于、或为任何专业、社会或慈善组织而工作英文
$(".isworkedcharitableorganizationen").change(function(){
	var isorganizationen = $("input[class=isworkedcharitableorganizationen]:checked").val();
	if(isorganizationen == 1){
		$(".isOrganizationYesen").show();
	}else {
		$(".isOrganizationYesen").hide();
		deleteBrotherEle($("div.organizationDiven"));
		emptyContentByObj($("div.organizationDiven"));
	}
});
//是否有专业技能或培训，如强制、爆炸物、核能、生物或化学
$(".hasspecializedskill").change(function(){
	var isSkill = $("input[class=hasspecializedskill]:checked").val();
	if(isSkill == 1){
		$(".skillDiv").show();
	}else {
		$(".skillDiv").hide();
		emptyContentByObj($("div.skillDiv"));
	}
	$(".hasspecializedskillen").eq(isSkill-1).click();
});
//是否有专业技能或培训，如强制、爆炸物、核能、生物或化学英文
$(".hasspecializedskillen").change(function(){
	var isSkillen = $("input[class=hasspecializedskillen]:checked").val();
	if(isSkillen == 1){
		$(".skillDiven").show();
	}else {
		$(".skillDiven").hide();
		emptyContentByObj($("div.skillDiven"));
	}
});
//是否曾服兵役
$(".hasservedinmilitary").change(function(){
	var isMilitary = $("input[class=hasservedinmilitary]:checked").val();
	if(isMilitary == 1){
		$(".militaryServiceYes").show();
	}else {
		$(".militaryServiceYes").hide();
		deleteBrotherEle($("div.militaryInfoDiv"));
		emptyContentByObj($("div.militaryInfoDiv"));
	}
	$(".hasservedinmilitaryen").eq(isMilitary-1).click();
});
//是否曾服兵役英文
$(".hasservedinmilitaryen").change(function(){
	var isMilitaryen = $("input[class=hasservedinmilitaryen]:checked").val();
	if(isMilitaryen == 1){
		$(".militaryServiceYesen").show();
	}else {
		$(".militaryServiceYesen").hide();
		deleteBrotherEle($("div.militaryInfoDiven"));
		emptyContentByObj($("div.militaryInfoDiven"));
	}
});
////是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织
//$(".isservedinrebelgroup").change(function(){
//	var isDinrebel = $("input[class=isservedinrebelgroup]:checked").val();
//	if(isDinrebel == 1){
//		$(".dinrebelDiv").show();
//	}else {
//		$(".dinrebelDiv").hide();
//		emptyContentByObj($("div.dinrebelDiv"));
//	}
//	$(".hasservedinmilitaryen").eq(isDinrebel-1).click();
//});
////是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织英文
//$(".isservedinrebelgroupen").change(function(){
//	var isDinrebelen = $("input[class=isservedinrebelgroupen]:checked").val();
//	if(isDinrebelen == 1){
//		$(".dinrebelDiven").show();
//	}else {
//		$(".dinrebelDiven").hide();
//		emptyContentByObj($("div.dinrebelDiven"));
//	}
//});
//以前工作信息多段操作
$(".beforeWorksave").click(function(){
	cloneMoreDiv("workBeforeInfosDiv");
	$(".beforeWorksaveen").trigger("click");
});
//以前工作信息多段操作英文
$(".beforeWorksaveen").click(function(){
	cloneMoreDiv("workBeforeInfosDiven");
});
//删除
$(".beforeWorkcancel").click(function(){
	deleteMoreDiv("workBeforeInfosDiv");
	$(".beforeWorkcancelen").trigger("click");
});
//删除英文
$(".beforeWorkcancelen").click(function(){
	deleteMoreDiv("workBeforeInfosDiven");
});

//教育多段操作
$(".educationsave").click(function(){
	cloneMoreDiv("midSchoolEduDiv");
	$(".educationsaveen").trigger("click");
});
//教育多段操作英文
$(".educationsaveen").click(function(){
	cloneMoreDiv("midSchoolEduDiven");
});
//删除
$(".educationcancel").click(function(){
	deleteMoreDiv("midSchoolEduDiv");
	$(".educationcancelen").trigger("click");
});
//删除英文
$(".educationcancelen").click(function(){
	deleteMoreDiv("midSchoolEduDiven");
});

//语言多段操作
$(".languagesave").click(function(){
	cloneMoreDiv("languagenameDiv");
	$(".languagesaveen").trigger("click");
});
//语言多段操作英文
$(".languagesaveen").click(function(){
	cloneMoreDiv("languagenameDiven");
});
//删除
$(".languagecancel").click(function(){
	deleteMoreDiv("languagenameDiv");
	$(".languagecancelen").trigger("click");
});
//删除英文
$(".languagecancelen").click(function(){
	deleteMoreDiv("languagenameDiven");
});

//国家多段操作
$(".gocountrysave").click(function(){
	cloneMoreDiv("travelCountry");
	$(".gocountrysaveen").trigger("click");
});
//国家多段操作
$(".gocountrysaveen").click(function(){
	cloneMoreDiv("travelCountryen");
});
//删除
$(".gocountrycancel").click(function(){
	deleteMoreDiv("travelCountry");
	$(".gocountrycancelen").trigger("click");
});
//删除英文
$(".gocountrycancelen").click(function(){
	deleteMoreDiv("travelCountryen");
});


//组织多段操作
$(".organizationsave").click(function(){
	cloneMoreDiv("organizationDiv");
	$(".organizationsaveen").trigger("click");
});
//组织多段操作英文
$(".organizationsaveen").click(function(){
	cloneMoreDiv("organizationDiven");
});
//删除
$(".organizationcancel").click(function(){
	deleteMoreDiv("organizationDiv");
	$(".organizationcancelen").trigger("click");
});
//删除英文
$(".organizationcancelen").click(function(){
	deleteMoreDiv("organizationDiven");
});

//服兵役多段操作
$(".militarysave").click(function(){
	cloneMoreDiv("militaryInfoDiv");
	$(".militarysaveen").trigger("click");
});
//服兵役多段操作
$(".militarysaveen").click(function(){
	cloneMoreDiv("militaryInfoDiven");
});
//删除
$(".militarycancel").click(function(){
	deleteMoreDiv("militaryInfoDiv");
	$(".militarycancelen").trigger("click");
});
//删除英文
$(".militarycancelen").click(function(){
	deleteMoreDiv("militaryInfoDiven");
});

//-------------------------------------------工作/教育/培训信息 end------------------------------------



//-------------------------------------------安全和背景 start------------------------------------
//是否患有传染性疾病
safeInfoRadioClick("isPestilence");

//是否有精神或身体疾病，可能对他人安全和福利构成威胁
safeInfoRadioClick("isThreatIllness");

//是否吸毒或曾经吸毒
safeInfoRadioClick("isDrug");

//是否因犯罪或违法而逮捕或被判刑，即使后来受到赦免、宽恕或其他类似的裁决
safeInfoRadioClick("isSentenced");

//是否违反过有关管控物资方面法律
safeInfoRadioClick("isMaterialLaw");

//是否从事卖淫活动
safeInfoRadioClick("isProstitution");

//是否曾经彩玉或意图从事洗钱活动
safeInfoRadioClick("isLaundering");

//是否曾在美国或美国意外的地方犯有或密谋人口走私罪
safeInfoRadioClick("isSmuggling");

//是否故意资助、教唆、协助或勾结某个人，而这个人在美国或美国以外的地方曾犯有或密谋了严重的人口走私案
safeInfoRadioClick("isThreateningOthers");

//是否是曾在美国或美国以外犯有或密谋人口走私案的配偶或子女？是否在最近5年里从走私活动中获得过好处
safeInfoRadioClick("isSmugglingBenefits");

//是否在美国企图从事间谍活动，破坏活动，违反出口管制或任何其他非法活动
safeInfoRadioClick("isSpyActivities");

//是否在美国企图从事恐怖活动，还是曾经参与过恐怖行动
safeInfoRadioClick("isTerroristActivities");

//是否有意向向恐怖分子或组织提供财政支援或其他支持
safeInfoRadioClick("isSupportTerrorists");

//是否是恐怖组织的成员或代表
safeInfoRadioClick("isTerrorist");

//是否曾经下令、煽动、承诺、协助或以其他方式参与种族灭绝
safeInfoRadioClick("isTakeGenocide");

//是否曾经犯下、下令、煽动协助或以其他方式参与过酷刑
safeInfoRadioClick("isOrderedThreat");

//是否曾经犯下、下令、煽动协助或以其他方式参与过法外杀戮、政治杀戮或其他暴力行为
safeInfoRadioClick("isPoliticalKilling");

//是否参与招募或使用童兵
safeInfoRadioClick("isRecruitChildSoldier");

//担任官员时，是否曾经负责或直接执行特别严重的侵犯宗教自由的行为
safeInfoRadioClick("isInroadReligion");

//是否曾经直接参与或建立执行人口控制措施，强迫妇女对其自由选择进行堕胎，或是否有男人或女人违反其自由意志进行绝育
safeInfoRadioClick("isForcedControlPopulation");

//是否曾经直接参与人体器官或身体组织的强制移植
safeInfoRadioClick("isForcedOrganTake");

//是否曾通过欺诈或故意虚假陈述或其他非法手段获得或协助他人获得美国签证，入境美国或获取任何其他移民福利
safeInfoRadioClick("isIllegalVisaUS");

//是否曾被驱逐出境
safeInfoRadioClick("isDeported");

//在过去的5年中是否参加过远程访问或不可受理的听证会
safeInfoRadioClick("isRemoteHearing");

//是否曾经非法存在，超出了入境官员的时间，或以其他方式违反了美国的签证条款
safeInfoRadioClick("isViolationVisaConditions");

//您是否曾经拒绝将一在美国境外的美籍儿童的监护权移交给一被美国法庭批准享有法定监护权的人
safeInfoRadioClick("isRefusalTransferCustody");

//是否违反了法律或规定在美国进行过投票选举
safeInfoRadioClick("isIllegalVoting");

//是否为逃税放弃过美国公民身份
safeInfoRadioClick("isTaxEvasion");

//是否曾在1996年11月30日之后以学生身份到美国的一公立小学或公立中学就读而没有向学校补交费用
safeInfoRadioClick("isNotPayTuitionFees");


//安全信息 单选点击事件
function safeInfoRadioClick(eltClass){
	$("."+eltClass).change(function(){
		var isElt = $("input[class="+eltClass+"]:checked").val();
		if(isElt == 1){
			$("."+eltClass+"Div").show();
		}else {
			$("."+eltClass+"Div").hide();
			emptyContentByObj($("div."+eltClass+"Div"));
		}
		
	});
}
	

//-------------------------------------------安全和背景 end------------------------------------




//-------------------------------------------功能函数 勿删------------------------------------
//取消关闭窗口
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

//双向数据通信
function translateZhToEn(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	$.ajax({
		//async : false,
		url : '/admin/translate/translate',
		data : {
			api : 'google',
			strType : to,
			en : 'en',
			q : toval
		},
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			$("#" + to).val(data).change();
		}
	});
	/*$.getJSON("/admin/translate/google", {q: $(from).val()}, function (result) {
        $("#" + to).val(result.data);
    });*/
}

//姓名翻译
function translateZhToPinYin(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	
	var pinyinchar = getPinYinStr(toval);
	$("#" + to).val(pinyinchar).change();
}

function translateZhToEnVueVisanumber(from, to, param){
	var toval = "";
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	$.ajax({
		//async : false,
		url : '/admin/translate/translate',
		data : {
			api : 'google',
			strType : to,
			en : 'en',
			q : toval
		},
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			$("#" + to).val(data).change();
			visaInfo.previUSTripInfo.visanumberen = data;
		}
	});
}

//添加多段中英文
function addSegmentsTranslateZhToEn(from, to, param){
	var toval = "";
	
	var Index = $(from).parent().parent().index();
	//var Indexen = $("#"+to).parent().parent().index();
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	
	$.ajax({
		//async : false,
		url : '/admin/translate/translate',
		data : {
			api : 'google',
			strType : to,
			en : 'en',
			q : toval
		},
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			
			$("." + to).eq(Index).val(data).change();
		}
	});
	/*$.getJSON("/admin/translate/google", {q: $(from).val()}, function (result) {
        $("#" + to).val(result.data);
    });*/
}


function addSegmentsTranslateZhToPinYin(from, to, param){
	var toval = "";
	
	var Index = $(from).parent().parent().index();
	//var Indexen = $("#"+to).parent().parent().index();
	if(param != ""){
		toval = param;
	}else{
		toval = $(from).val();
	}
	var pinyinchar = getPinYinStr(toval);
	$("." + to).eq(Index).val(pinyinchar).change();
}