//获取同伴信息集合
function getCompanionList(){
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
	
	return companionList;
}

//获取去过美国信息
function getGoUSList(){
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
	return gousList;
}

//获取驾照信息
function getDriverList(){
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
	
	return driverList;
}

//获取直系亲属信息
function getDirectList(){
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
	return directList;
}

//获取以前工作信息
function getBeforeWorkList(){
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
	
	return beforeWorkList;
}

//获取以前教育信息
function getBeforeEducationList(){
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
			beforeEducationLength += '';
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
	
	return beforeEducationList;
}

//获取以前使用的语言信息
function getLanguageList(){
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
	
	return languageList;
}

//获取过去五年去过的国家
function getCountryList(){
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
	return countryList;
}

//获取参加过的组织信息
function getOrganizationList(){
	var organizationList = [];
	$('.organizationDiv').each(function(i){
		var organizationLength = '';
		var organization = {};
		
		var organizationname = $(this).find('[name=organizationname]').val();
		if(organizationname.length > 0 ){
			organization.organizationname = organizationname;
			organizationList.push(organization);
		}
	});
	return organizationList;
}

//获取曾服兵役信息
function getMilitaryInfoList(){
	var militaryInfoList = [];
	$('.militaryInfoDiv').each(function(i){
		var militaryInfoLength = '';
		var militaryInfo = {};
		
		var militarycountry = $(this).find('[name=militarycountry]').val();
		if(militarycountry != 0){
			militaryInfoLength += militarycountry;
		}else{
			militaryInfoLength += '';
		}
		var servicebranch = $(this).find('[name=servicebranch]').val();
		var rank = $(this).find('[name=rank]').val();
		var militaryspecialty = $(this).find('[name=militaryspecialty]').val();
		var servicestartdate = $(this).find('[name=servicestartdate]').val();
		var serviceenddate = $(this).find('[name=serviceenddate]').val();
		militaryInfoLength += servicebranch;
		militaryInfoLength += rank;
		militaryInfoLength += militaryspecialty;
		militaryInfoLength += servicestartdate;
		militaryInfoLength += serviceenddate;
		
		if(militaryInfoLength.length >0){
			militaryInfo.militarycountry = militarycountry;
			militaryInfo.servicebranch = servicebranch;
			militaryInfo.rank = rank;
			militaryInfo.militaryspecialty = militaryspecialty;
			militaryInfo.servicestartdate = servicestartdate;
			militaryInfo.serviceenddate = serviceenddate;
			militaryInfoList.push(militaryInfo);
		}
	});
	
	return militaryInfoList;
}