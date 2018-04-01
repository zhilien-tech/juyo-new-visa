/**
 * 页面首次加载时，打开默认Yes No 开关
 */
function openYesOrNoPage(){
	//是否与其他人一起旅游
	var istravelwithother = visaInfo.travelCompanionInfo.istravelwithother;
	if(istravelwithother == 1){
		$(".teamture").show();
		$(".teamnamefalse").show();
		
		//是否作为团队或组织的一部分旅游
		var ispart = visaInfo.travelCompanionInfo.ispart; 
		if(ispart == 1){
			$(".teamnameture").show();
			$(".teamnamefalseDiv").hide();
		}else {
			$(".teamnameture").hide();
			$(".teamnamefalseDiv").show();
		}
	}else {
		$(".teamture").hide();
	}
	
	//是否去过美国
	var goUS = visaInfo.previUSTripInfo.hasbeeninus;
	if(goUS == 1){
		$(".goUSInfo").show();
		//是否有美国驾照
		var license = visaInfo.previUSTripInfo.hasdriverlicense;
		if(license == 1){
			$(".driverInfo").show();
		}else{
			$(".driverInfo").hide();
		}
	}else{
		$(".goUSInfo").hide();
	}
	
	//是否有美国签证
	var visaUS = visaInfo.previUSTripInfo.isissuedvisa;
	if(visaUS == 1){
		$(".dateIssue").show();
		//是否丢失签证
		var lose = visaInfo.previUSTripInfo.islost;
		if(lose == 1){
			$(".yearExplain").show();
		}else {
			$(".yearExplain").hide();
		}
		//是否被取消或撤销过
		var iscancelled = visaInfo.previUSTripInfo.iscancelled;
		if(iscancelled == 1){
			$(".explain").show();
		}else {
			$(".explain").hide();
		}
	}else {
		$(".dateIssue").hide();
	}
	
	//是否被拒绝过美国签证
	var refuse = visaInfo.previUSTripInfo.isrefused;
	if(refuse == 1){
		$(".refuseExplain").show();
	}else {
		$(".refuseExplain").hide();
	}
	
	//是否合法永久居民
	var islegal = visaInfo.previUSTripInfo.islegalpermanentresident;
	if(islegal == 1){
		$(".onceExplain").show();
	}else {
		$(".onceExplain").hide();
	}
	
	//有没有人曾代表您向美国公民和移民服务局提交过移民申请
	var islegal = visaInfo.previUSTripInfo.isfiledimmigrantpetition;
	if(islegal == 1){
		$(".islegal").show();
	}else {
		$(".islegal").hide();
	}
	
	
	//父亲是否在美国
	var fatherUS = visaInfo.familyInfo.isfatherinus;
	if(fatherUS == 1){
		$(".fatherUSYes").show();
	}else {
		$(".fatherUSYes").hide();
	}
	
	//母亲是否在美国
	var motherUS = visaInfo.familyInfo.ismotherinus;
	if(motherUS == 1){
		$(".motherUSYes").show();
	}else {
		$(".motherUSYes").hide();
	}
	
	//除了父母还有没有直系亲属
	var directUSRelatives = visaInfo.familyInfo.hasimmediaterelatives;
	if(directUSRelatives == 1){
		$(".directRelativesYes").show();
		$(".directRelativesNo").hide();
	}else {
		$(".directRelativesYes").hide();
		$(".directRelativesNo").show();
	}
	
	//主要职业
	var occupation = visaInfo.workEducationInfo.occupation;
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
	
	//以前是否工作过
	var isemployed = visaInfo.workEducationInfo.isemployed;
	if(isemployed == 1){
		$(".beforeWorkInfo").show();
	}else {
		$(".beforeWorkInfo").hide();
	}
	
	//是否上过中学或以上的任何教育
	var education = visaInfo.workEducationInfo.issecondarylevel;
	if(education == 1){
		$(".educationInfo").show();
	}else {
		$(".educationInfo").hide();
	}
	
	//是否属于氏族或部落
	var isclan = visaInfo.workEducationInfo.isclan;
	if(isclan == 1){
		$(".isclanYes").show();
	}else {
		$(".isclanYes").hide();
	}
	
	//过去五年是否曾去过任何国家/地区旅游
	var istraveledanycountry = visaInfo.workEducationInfo.istraveledanycountry;
	if(istraveledanycountry == 1){
		$(".isTravelYes").show();
	}else {
		$(".isTravelYes").hide();
	}
	
	//是否属于、致力于、或为任何专业、社会或慈善组织而工作
	var isorganization = visaInfo.workEducationInfo.isworkedcharitableorganization;
	if(isorganization == 1){
		$(".isOrganizationYes").show();
	}else {
		$(".isOrganizationYes").hide();
	}
	
	//是否有专业技能或培训，如强制、爆炸物、核能、生物或化学
	var isSkill = visaInfo.workEducationInfo.hasspecializedskill;
	if(isSkill == 1){
		$(".skillDiv").show();
	}else {
		$(".skillDiv").hide();
	}
	
	//是否曾服兵役
	var isMilitary = visaInfo.workEducationInfo.hasservedinmilitary;
	if(isMilitary == 1){
		$(".militaryServiceYes").show();
	}else {
		$(".militaryServiceYes").hide();
	}
	
	
	//checkbox勾选时回显，设置input--->disabled           TODO
	/*$("input[type='checkbox']").each(function(index,ele){
		/*var beforeEle = $(this).prev();
		if($(this).is(":checkbox")){
			beforeEle.attr("disabled",true);
		}else{
			beforeEle.attr("disabled",false);
		}
	});*/

	
}

//勾选checkbox("不知道")，回显
function showEleBeforeCheckbox(obj){
	var beforeEle = obj.prev();
	beforeEle.val("");
	if(obj.is(':checked')){
		beforeEle.attr("disabled",true);
	}else{
		beforeEle.attr("disabled",false);
	}
}
