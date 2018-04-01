/*
 ************************VUE 准备数据
 * 
 * 
 *旅伴信息	travelCompanionInfo
 *
 *以前的美国旅游信息	previUSTripInfo
 *
 *美国联络点	contactPointInfo
 *
 *家庭信息	familyInfo
 *
 *工作/教育/培训信息 	workEducationInfo
 *
 */

var visaInfo;
new Vue({
	el: '#wrapper',
	data: {
		travelCompanionInfo:"",
		previUSTripInfo:"",
		contactPointInfo:"",
		familyInfo:"",
		workEducationInfo:""
	},
	created:function(){
		visaInfo=this;
		var url = '/admin/bigCustomer/getVisaInfos.html';
		$.ajax({ 
			url: url,
			data:{
				staffId:staffId
			},
			type:'post',
			//async:false,
			success: function(data){
				visaInfo.travelCompanionInfo = data.travelCompanionInfo;
				visaInfo.previUSTripInfo = data.previUSTripInfo;
				visaInfo.contactPointInfo = data.contactPointInfo;
				visaInfo.familyInfo = data.familyInfo;
				visaInfo.workEducationInfo = data.workEducationInfo;
				
				openYesOrNoPage();
				
			}
		});
	},
	methods:{
		
	}
});

//签证信息页保存
function save(){
	var visadata = {};
	
	visadata.travelCompanionInfo = visaInfo.travelCompanionInfo;
	visadata.previUSTripInfo = visaInfo.previUSTripInfo;
	visadata.contactPointInfo = visaInfo.contactPointInfo;
	visadata.familyInfo = visaInfo.familyInfo;
	visadata.workEducationInfo = visaInfo.workEducationInfo;
	
	//同伴信息
	var companionList = [];
	$('.teamnamefalseDiv').each(function(i){
		var companionLength = '';
		var companion = {};
		var firstname = $(this).find('[name=firstname]').val();
		var lastname = $(this).find('[name=lastname]').val();
		var relationship = $(this).find('[name=relationship]').val();
		
		companionLength += firstname;
		companionLength += lastname;
		if (relationship != 0) {
			companionLength += relationship;
		}else{
			companionLength += '';
		}
		
		if(companionLength.length > 0){
			companion.firstname = firstname;
			companion.lastname = lastname;
			companion.relationship = relationship;
			companionList.push(companion);
		}
	});
	visadata.companionList = companionList;
	
	//去过美国信息
	var gousList = [];
	$('.goUS_Country').each(function(i){
		var gousLength = '';
		var gous = {};
		
		var arrivedate = $(this).find('[name=arrivedate]').val();
		var staydays = $(this).find('[name=staydays]').val();
		var dateunit = $(this).find('[name=dateunit]').val();
		
		gousLength += arrivedate;
		gousLength += staydays;
		if (dateunit != 0) {
			gousLength += dateunit;
		}else{
			gousLength += '';
		}
		if(gousLength.length > 0){
			gous.arrivedate = arrivedate;
			gous.staydays = staydays;
			gous.dateunit = dateunit;
			gousList.push(gous);
		}
	});
	visadata.gousList = gousList;
	
	//美国驾照信息
	var driverList = [];
	$('.goUS_drivers').each(function(i){
		var driverLength = '';
		var driver = {};
		
		var drivernumber = $(this).find('[name=driverlicensenumber]').val();
		var isknownumber = $(this).find('[name=isknowdrivernumber]').is(':checked');
		if(isknownumber){
			isknownumber = 1;
		}else{
			isknownumber = 0;
		}
		var stateofdriver = $(this).find('[name=witchstateofdriver]').val();
		driverLength += drivernumber;
		if(stateofdriver != 0){
			driverLength += stateofdriver;
		}else{
			driverLength += '';
		}
		
		if(driverLength.length >0){
			driver.driverlicensenumber = drivernumber;
			driver.isknowdrivernumber = isknownumber;
			driver.witchstateofdriver = stateofdriver;
			driverList.push(driver);
		}
	});
	visadata.driverList = driverList;
	
	
	
	//直系亲属信息 
	var directList = [];
	$('.directRelativesYes').each(function(i){
		var directLength = '';
		var direct = {};
		
		var relativesfirstname = $(this).find('[name=relativesfirstname]').val();
		var relativeslastname = $(this).find('[name=relativeslastname]').val();
		var relationship = $(this).find('[name=relationship]').val();
		var relativesstatus = $(this).find('[name=relativesstatus]').val();
		directLength += relativesfirstname;
		directLength += relativeslastname;
		if(relationship != 0){
			directLength += relationship;
		}else{
			directLength += '';
		}
		if(relativesstatus != 0){
			directLength += relativesstatus;
		}else{
			directLength += '';
		}
		
		if(directLength.length > 0){
			direct.relativesfirstname = relativesfirstname;
			direct.relativeslastname = relativeslastname;
			direct.relationship = relationship;
			direct.relativesstatus = relativesstatus;
			directList.push(direct);
		}
	});
	visadata.directList = directList;
	
	
	//以前工作信息
	var beforeWorkList = [];
	$('.workBeforeInfosDiv').each(function(i){
		var beforeWorkLength = '';
		var beforeWork = {};
		
		var employername = $(this).find('[name=employername]').val();
		var employeraddress = $(this).find('[name=employeraddress]').val();
		var employeraddressSec = $(this).find('[name=employeraddressSec]').val();
		var employercity = $(this).find('[name=employercity]').val();
		var employerprovince = $(this).find('[name=employerprovince]').val();
		var employerzipcode = $(this).find('[name=employerzipcode]').val();
		var employercountry = $(this).find('[name=employercountry]').val();
		if(employercountry != 0){
			beforeWorkLength += employercountry;
		}else{
			beforeWorkLength += '';
		}
		var employertelephone = $(this).find('[name=employertelephone]').val();
		var jobtitle = $(this).find('[name=jobtitle]').val();
		var isemployerzipcodeapply = $(this).find('[name=isemployerzipcodeapply]').is(':checked');
		if(isemployerzipcodeapply){
			isemployerzipcodeapply = 1;
		}else{
			isemployerzipcodeapply = 0;
		}
		var supervisorlastname = $(this).find('[name=supervisorlastname]').val();
		var isknowsupervisorlastname = $(this).find('[name=isknowsupervisorlastname]').is(':checked');
		if(isknowsupervisorlastname){
			isknowsupervisorlastname = 1;
		}else{
			isknowsupervisorlastname = 0;
		}
		var employstartdate = $(this).find('[name=employstartdate]').val();
		var employenddate = $(this).find('[name=employenddate]').val();
		var previousduty = $(this).find('[name=previousduty]').val();
		
		beforeWorkLength += employername;
		beforeWorkLength += employeraddress;
		beforeWorkLength += employeraddressSec;
		beforeWorkLength += employercity;
		beforeWorkLength += employerprovince;
		beforeWorkLength += employerzipcode;
		beforeWorkLength += employercountry;
		beforeWorkLength += employertelephone;
		beforeWorkLength += jobtitle;
		beforeWorkLength += supervisorlastname;
		beforeWorkLength += employstartdate;
		beforeWorkLength += employenddate;
		beforeWorkLength += previousduty;
		if(beforeWorkLength.length >0){
			beforeWork.employername = employername;
			beforeWork.employeraddress = employeraddress;
			beforeWork.employeraddressSec = employeraddressSec;
			beforeWork.employercity = employercity;
			beforeWork.employerprovince = employerprovince;
			beforeWork.employerzipcode = employerzipcode;
			beforeWork.employercountry = employercountry;
			beforeWork.employertelephone = employertelephone;
			beforeWork.jobtitle = jobtitle;
			beforeWork.isemployerzipcodeapply = isemployerzipcodeapply;
			beforeWork.supervisorlastname = supervisorlastname;
			beforeWork.isknowsupervisorlastname = isknowsupervisorlastname;
			beforeWork.employstartdate = employstartdate;
			beforeWork.employenddate = employenddate;
			beforeWork.previousduty = previousduty;
			beforeWorkList.push(beforeWork);
		}
	});
	visadata.beforeWorkList = beforeWorkList;
	
	//以前教育信息
	var beforeEducationList = [];
	$('.midSchoolEduDiv').each(function(i){
		var beforeEducationLength = '';
		var beforeEducation = {};
		
		var institution = $(this).find('[name=institution]').val();
		var institutionaddress = $(this).find('[name=institutionaddress]').val();
		var secinstitutionaddress = $(this).find('[name=secinstitutionaddress]').val();
		var institutioncity = $(this).find('[name=institutioncity]').val();
		var institutionprovince = $(this).find('[name=institutionprovince]').val();
		var institutionzipcode = $(this).find('[name=institutionzipcode]').val();
		var isinstitutionzipcodeapply = $(this).find('[name=isinstitutionzipcodeapply]').is(':checked');
		if(isinstitutionzipcodeapply){
			isinstitutionzipcodeapply = 1;
		}else{
			isinstitutionzipcodeapply = 0;
		}
		var course = $(this).find('[name=course]').val();
		var coursestartdate = $(this).find('[name=coursestartdate]').val();
		var courseenddate = $(this).find('[name=courseenddate]').val();
		var institutioncountry = $(this).find('[name=institutioncountry]').val();
		if(institutioncountry != 0){
			beforeEducationLength += institutioncountry;
		}else{
			institutioncountry += '';
		}
		beforeEducationLength += institution;
		beforeEducationLength += institutionaddress;
		beforeEducationLength += secinstitutionaddress;
		beforeEducationLength += institutioncity;
		beforeEducationLength += institutionprovince;
		beforeEducationLength += institutionzipcode;
		beforeEducationLength += course;
		beforeEducationLength += coursestartdate;
		beforeEducationLength += courseenddate;
		beforeEducationLength += institutioncountry;
		if(beforeEducationLength.length > 0 ){
			beforeEducation.institution = institution;
			beforeEducation.institutionaddress = institutionaddress;
			beforeEducation.secinstitutionaddress = secinstitutionaddress;
			beforeEducation.institutioncity = institutioncity;
			beforeEducation.institutionprovince = institutionprovince;
			beforeEducation.institutionzipcode = institutionzipcode;
			beforeEducation.isinstitutionzipcodeapply = isinstitutionzipcodeapply;
			beforeEducation.course = course;
			beforeEducation.coursestartdate = coursestartdate;
			beforeEducation.courseenddate = courseenddate;
			beforeEducation.institutioncountry = institutioncountry;
			beforeEducationList.push(beforeEducation);
		}
	});
	visadata.beforeEducationList = beforeEducationList;
	
	//使用过的语言信息
	var languageList = [];
	$('.languagenameDiv').each(function(i){
		var languageLength = '';
		var language = {};
		
		var languagename = $(this).find('[name=languagename]').val();
		if(languagename.length > 0 ){
			language.languagename = languagename;
			languageList.push(language);
		}
	});
	visadata.languageList = languageList;
	
	//过去五年去过的国家信息
	var countryList = [];
	$('.travelCountry').each(function(i){
		var countryLength = '';
		var country = {};
		
		var traveledcountry = $(this).find('[name=traveledcountry]').val();
		if(traveledcountry.length > 0 ){
			country.traveledcountry = traveledcountry;
			countryList.push(country);
		}
	});
	visadata.countryList = countryList;
	
	
	alert(JSON.stringify(countryList));
	
}
	

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

