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
//-------------------------------------------家庭信息 end----------------------------------

//---------------------------------------以前的美国旅游信息 start----------------------------------
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
//---------------------------------------以前的美国旅游信息 end----------------------------------




//-------------------------------------------美国联络点 Start----------------------------------

//-------------------------------------------美国联络点 end------------------------------------




//-------------------------------------------家庭信息 Start----------------------------------
//亲属信息
$(".fatherUS").change(function(){
	var fatherUS = $("input[class=fatherUS]:checked").val();
	if(fatherUS == 1){
		$(".fatherUSYes").show();
	}else {
		$(".fatherUSYes").hide();
		emptyContentByObj($("div.fatherUSYes"));
	}
});
$(".motherUS").change(function(){
	var motherUS = $("input[class=motherUS]:checked").val();
	if(motherUS == 1){
		$(".motherUSYes").show();
	}else {
		$(".motherUSYes").hide();
		emptyContentByObj($("div.motherUSYes"));
	}
});
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
});

function changeSpouse(){
	var opt = $("#spouseaddress").val();
	if(opt == 5){
		$(".otherSpouseInfo").show();
	}else{
		$(".otherSpouseInfo").hide();
		emptyContentByObj($("div.otherSpouseInfo"));
	}
}
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
});

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
});

//是否属于氏族或部落
$(".isclan").change(function(){
	var isclan = $("input[class=isclan]:checked").val();
	if(isclan == 1){
		$(".isclanYes").show();
	}else {
		$(".isclanYes").hide();
		deleteBrotherEle($("div.clannameDiv"));
		emptyContentByObj($("div.clannameDiv"));
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
});
//是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织
$(".isservedinrebelgroup").change(function(){
	var isDinrebel = $("input[class=isservedinrebelgroup]:checked").val();
	if(isDinrebel == 1){
		$(".dinrebelDiv").show();
	}else {
		$(".dinrebelDiv").hide();
		emptyContentByObj($("div.dinrebelDiv"));
	}
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
safeInfoRadioClick("isTerroristActivities");

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