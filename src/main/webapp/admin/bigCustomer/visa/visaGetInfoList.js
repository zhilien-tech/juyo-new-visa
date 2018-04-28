//获取旅伴信息集合
function getCompanionList(){
	var companionList = [];
	$('.teamnamefalseDiv').each(function(index,i){
		
		var companionLength = '';
		var companion = {};
		var firstname = $(this).find('[name=firstname]').val();
		var lastname = $(this).find('[name=lastname]').val();
		var relationship = $(this).find('[name=relationship]').val();
		
		companionLength += firstname;
		companionLength += lastname;
		//下拉框
		if (relationship != 0) {
			companionLength += relationship;
		}else{
			companionLength += '';
		}
		
		//英文
		var firstnameen = $(".teamnamefalseDiven").eq(index).find('[name=firstnameen]').val();
		var lastnameen = $(".teamnamefalseDiven").eq(index).find('[name=lastnameen]').val();
		var relationshipen = $(".teamnamefalseDiven").eq(index).find('[name=relationshipen]').val();
		
		if (relationshipen != 0) {
			companionLength += relationshipen;
		}else{
			companionLength += '';
		}
		
		companionLength += firstnameen;
		companionLength += lastnameen;
		companionLength += relationshipen;
		
		if(companionLength.length > 0){
			companion.staffid = staffId;
			companion.firstname = firstname;
			companion.lastname = lastname;
			companion.relationship = relationship;
			
			//英文
			companion.firstnameen = firstnameen;
			companion.lastnameen = lastnameen;
			companion.relationshipen = relationshipen;
			
			
			companionList.push(companion);
		}
	});
	console.log(JSON.stringify(companionList));
	return companionList;
}

//获取去过美国信息
function getGoUSList(){
	var gousList = [];
	$('.goUS_CountryDiv').each(function(index,i){
		var gousLength = '';
		var gous = {};
		
		var arrivedate = $(this).find('[name=arrivedate]').val();
		arrivedate = formatDate(arrivedate);
		var staydays = $(this).find('[name=staydays]').val();
		var dateunit = $(this).find('[name=dateunit]').val();
		
		//英文
		var arrivedateen = $(".goUS_CountryDiven").eq(index).find('[name=arrivedateen]').val();
		arrivedateen = formatDate(arrivedateen);
		var staydaysen = $(".goUS_CountryDiven").eq(index).find('[name=staydaysen]').val();
		var dateuniten = $(".goUS_CountryDiven").eq(index).find('[name=dateuniten]').val();
		
		
		gousLength += arrivedate;
		gousLength += staydays;
		if (dateunit != 0) {
			gousLength += dateunit;
		}else{
			gousLength += '';
		}
		
		//英文
		gousLength += arrivedateen;
		gousLength += staydaysen;
		if (dateuniten != 0) {
			gousLength += dateuniten;
		}else{
			gousLength += '';
		}
		
		
		if(gousLength.length > 0){
			gous.staffid = staffId;
			gous.arrivedate = arrivedate;
			gous.staydays = staydays;
			gous.dateunit = dateunit;
			
			//英文
			gous.arrivedateen = arrivedateen;
			gous.staydaysen = staydaysen;
			gous.dateuniten = dateuniten;
			
			gousList.push(gous);
		}
	});
	return gousList;
}

  
//获取驾照信息
function getDriverList(){
	var driverList = [];
	$('.goUS_drivers').each(function(index,i){
		var driverLength = '';
		var driver = {};
		
		var drivernumber = $(this).find('[name=driverlicensenumber]').val();
		var isknownumber = $(this).find('[name=isknowdrivernumber]').is(':checked');
		if(isknownumber){
			isknownumber = 1;
		}else{
			isknownumber = 0;
		}
		
		//英文
		var drivernumberen = $('.goUS_driversen').eq(index).find('[name=driverlicensenumberen]').val();
		var isknownumberen = $('.goUS_driversen').eq(index).find('[name=isknowdrivernumberen]').is(':checked');
		if(isknownumberen){
			isknownumberen = 1;
		}else{
			isknownumberen = 0;
		}
		var stateofdriveren = $(this).eq(index).find('[name=witchstateofdriveren]').val();
		
		var stateofdriver = $(this).find('[name=witchstateofdriver]').val();
		
		driverLength += drivernumber;
		if(stateofdriver != 0){
			driverLength += stateofdriver;
		}else{
			driverLength += '';
		}
		
		//英文
		driverLength += drivernumberen;
		if(stateofdriveren != 0){
			driverLength += stateofdriveren;
		}else{
			driverLength += '';
		}
		
		if(driverLength.length >0 || isknownumber==1){
			driver.staffid = staffId;
			driver.driverlicensenumber = drivernumber;
			driver.isknowdrivernumber = isknownumber;
			driver.witchstateofdriver = stateofdriver;
			
			//英文
			driver.driverlicensenumberen = drivernumberen;
			driver.isknowdrivernumberen = isknownumberen;
			driver.witchstateofdriveren = stateofdriveren;
			driverList.push(driver);
		}
	});
	
	return driverList;
}
/********没有多段添加  ********/
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
			direct.staffid = staffId;
			direct.relativesfirstname = relativesfirstname;
			direct.relativeslastname = relativeslastname;
			direct.relationship = relationship;
			direct.relativesstatus = relativesstatus;
			directList.push(direct);
		}
	});
	return directList;
}

/******/
//获取以前工作信息
function getBeforeWorkList(){
	var beforeWorkList = [];
	$('.workBeforeInfosDiv').each(function(index,i){
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
		//checkbox
		var isemployerzipcodeapply = $(this).find('[name=isemployerzipcodeapply]').is(':checked');
		if(isemployerzipcodeapply){
			isemployerzipcodeapply = 1;
		}else{
			isemployerzipcodeapply = 0;
		}
		var supervisorlastname = $(this).find('[name=supervisorlastname]').val();
		var isknowsupervisorfirstname = $(this).find('[name=isknowsupervisorfirstname]').is(':checked');
		if(isknowsupervisorfirstname){
			isknowsupervisorfirstname = 1;
		}else{
			isknowsupervisorfirstname = 0;
		}
		var supervisorfirstname = $(this).find('[name=supervisorfirstname]').val();
		var isknowsupervisorlastname = $(this).find('[name=isknowsupervisorlastname]').is(':checked');
		if(isknowsupervisorlastname){
			isknowsupervisorlastname = 1;
		}else{
			isknowsupervisorlastname = 0;
		}
		
		var employstartdate = $(this).find('[name=employstartdate]').val();
		employstartdate = formatDate(employstartdate);
		var employenddate = $(this).find('[name=employenddate]').val();
		employenddate = formatDate(employenddate);
		var previousduty = $(this).find('[name=previousduty]').val();
		
		//英文
		var employernameen = $('.workBeforeInfosDiven').eq(index).find('[name=employernameen]').val();
		var employeraddressen = $('.workBeforeInfosDiven').eq(index).find('[name=employeraddressen]').val();
		var employeraddressSecen = $('.workBeforeInfosDiven').eq(index).find('[name=employeraddressSecen]').val();
		var employercityen = $('.workBeforeInfosDiven').eq(index).find('[name=employercityen]').val();
		var employerprovinceen = $('.workBeforeInfosDiven').eq(index).find('[name=employerprovinceen]').val();
		var employerzipcodeen = $('.workBeforeInfosDiven').eq(index).find('[name=employerzipcodeen]').val();
		var employercountryen = $('.workBeforeInfosDiven').eq(index).find('[name=employercountryen]').val();
		if(employercountryen != 0){
			beforeWorkLength += employercountryen;
		}else{
			beforeWorkLength += '';
		}
		var employertelephoneen = $('.workBeforeInfosDiven').eq(index).find('[name=employertelephoneen]').val();
		var jobtitleen = $(this).eq(index).find('[name=jobtitleen]').val();
		//checkbox
		var isemployerzipcodeapplyen = $('.workBeforeInfosDiven').eq(index).find('[name=isemployerzipcodeapplyen]').is(':checked');
		if(isemployerzipcodeapplyen){
			isemployerzipcodeapplyen = 1;
		}else{
			isemployerzipcodeapplyen = 0;
		}
		var supervisorlastnameen = $('.workBeforeInfosDiven').eq(index).find('[name=supervisorlastnameen]').val();
		var isknowsupervisorfirstnameen = $('.workBeforeInfosDiven').eq(index).find('[name=isknowsupervisorfirstnameen]').is(':checked');
		if(isknowsupervisorfirstnameen){
			isknowsupervisorfirstnameen = 1;
		}else{
			isknowsupervisorfirstnameen = 0;
		}
		var supervisorfirstnameen = $('.workBeforeInfosDiven').eq(index).find('[name=supervisorfirstnameen]').val();
		var isknowsupervisorlastnameen = $('.workBeforeInfosDiven').eq(index).find('[name=isknowsupervisorlastnameen]').is(':checked');
		if(isknowsupervisorlastnameen){
			isknowsupervisorlastnameen = 1;
		}else{
			isknowsupervisorlastnameen = 0;
		}
		
		var employstartdateen = $('.workBeforeInfosDiven').eq(index).find('[name=employstartdateen]').val();
		employstartdateen = formatDate(employstartdateen);
		var employenddateen = $('.workBeforeInfosDiven').eq(index).find('[name=employenddateen]').val();
		employenddateen = formatDate(employenddateen);
		var previousdutyen = $('.workBeforeInfosDiven').eq(index).find('[name=previousdutyen]').val();
		
		
		beforeWorkLength += employername;
		beforeWorkLength += employeraddress;
		beforeWorkLength += employeraddressSec;
		beforeWorkLength += employercity;
		beforeWorkLength += employerprovince;
		beforeWorkLength += employerzipcode;
		beforeWorkLength += employertelephone;
		beforeWorkLength += jobtitle;
		beforeWorkLength += supervisorfirstname;
		beforeWorkLength += supervisorlastname;
		beforeWorkLength += employstartdate;
		beforeWorkLength += employenddate;
		beforeWorkLength += previousduty;
		
		//英文
		beforeWorkLength += employernameen;
		beforeWorkLength += employeraddressen;
		beforeWorkLength += employeraddressSecen;
		beforeWorkLength += employercityen;
		beforeWorkLength += employerprovinceen;
		beforeWorkLength += employerzipcodeen;
		beforeWorkLength += employertelephoneen;
		beforeWorkLength += jobtitleen;
		beforeWorkLength += supervisorfirstnameen;
		beforeWorkLength += supervisorlastnameen;
		beforeWorkLength += employstartdateen;
		beforeWorkLength += employenddateen;
		beforeWorkLength += previousdutyen;
		
		
		if(beforeWorkLength.length >0){
			beforeWork.staffid = staffId;
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
			beforeWork.supervisorfirstname = supervisorfirstname;
			beforeWork.supervisorlastname = supervisorlastname;
			beforeWork.isknowsupervisorfirstname = isknowsupervisorfirstname;
			beforeWork.isknowsupervisorlastname = isknowsupervisorlastname;
			beforeWork.employstartdate = employstartdate;
			beforeWork.employenddate = employenddate;
			beforeWork.previousduty = previousduty;
			beforeWorkList.push(beforeWork);
			
			//英文
			beforeWork.employernameen = employernameen;
			beforeWork.employeraddressen = employeraddressen;
			beforeWork.employeraddressSecen = employeraddressSecen;
			beforeWork.employercityen = employercityen;
			beforeWork.employerprovinceen = employerprovinceen;
			beforeWork.employerzipcodeen = employerzipcodeen;
			beforeWork.employercountryen = employercountryen;
			beforeWork.employertelephoneen = employertelephoneen;
			beforeWork.jobtitleen = jobtitleen;
			beforeWork.isemployerzipcodeapplyen = isemployerzipcodeapplyen;
			beforeWork.supervisorfirstnameen = supervisorfirstnameen;
			beforeWork.supervisorlastnameen = supervisorlastnameen;
			beforeWork.isknowsupervisorfirstnameen = isknowsupervisorfirstnameen;
			beforeWork.isknowsupervisorlastnameen = isknowsupervisorlastnameen;
			beforeWork.employstartdateen = employstartdateen;
			beforeWork.employenddateen = employenddateen;
			beforeWork.previousdutyen = previousdutyen;
			
		}
	});
	
	return beforeWorkList;
}

//获取以前教育信息
function getBeforeEducationList(){
	var beforeEducationList = [];
	$('.midSchoolEduDiv').each(function(index,i){
		var beforeEducationLength = '';
		var beforeEducation = {};
		
		var institution = $(this).find('[name=institution]').val();
		var institutionaddress = $(this).find('[name=institutionaddress]').val();
		var secinstitutionaddress = $(this).find('[name=secinstitutionaddress]').val();
		var institutioncity = $(this).find('[name=institutioncity]').val();
		var institutionprovince = $(this).find('[name=institutionprovince]').val();
		var institutionzipcode = $(this).find('[name=institutionzipcode]').val();
		var isinstitutionprovinceapply = $(this).find('[name=isinstitutionprovinceapply]').is(':checked');
		if(isinstitutionprovinceapply){
			isinstitutionprovinceapply = 1;
		}else{
			isinstitutionprovinceapply = 0;
		}
		var isinstitutionzipcodeapply = $(this).find('[name=isinstitutionzipcodeapply]').is(':checked');
		if(isinstitutionzipcodeapply){
			isinstitutionzipcodeapply = 1;
		}else{
			isinstitutionzipcodeapply = 0;
		}
		var course = $(this).find('[name=course]').val();
		var coursestartdate = $(this).find('[name=coursestartdate]').val();
		coursestartdate = formatDate(coursestartdate);
		var courseenddate = $(this).find('[name=courseenddate]').val();
		courseenddate = formatDate(courseenddate);
		var institutioncountry = $(this).find('[name=institutioncountry]').val();
		if(institutioncountry != 0){
			beforeEducationLength += institutioncountry;
		}else{
			beforeEducationLength += '';
		}
		
		//英文
		var institutionen = $('.midSchoolEduDiven').eq(index).find('[name=institutionen]').val();
		var institutionaddressen = $('.midSchoolEduDiven').eq(index).find('[name=institutionaddressen]').val();
		var secinstitutionaddressen = $('.midSchoolEduDiven').eq(index).find('[name=secinstitutionaddressen]').val();
		var institutioncityen = $('.midSchoolEduDiven').eq(index).find('[name=institutioncityen]').val();
		var institutionprovinceen = $('.midSchoolEduDiven').eq(index).find('[name=institutionprovinceen]').val();
		var institutionzipcodeen = $('.midSchoolEduDiven').eq(index).find('[name=institutionzipcodeen]').val();
		var isinstitutionprovinceapplyen = $('.midSchoolEduDiven').eq(index).find('[name=isinstitutionprovinceapplyen]').is(':checked');
		if(isinstitutionprovinceapplyen){
			isinstitutionprovinceapplyen = 1;
		}else{
			isinstitutionprovinceapplyen = 0;
		}
		var isinstitutionzipcodeapplyen = $('.midSchoolEduDiven').eq(index).find('[name=isinstitutionzipcodeapplyen]').is(':checked');
		if(isinstitutionzipcodeapplyen){
			isinstitutionzipcodeapplyen = 1;
		}else{
			isinstitutionzipcodeapplyen = 0;
		}
		var courseen = $('.midSchoolEduDiven').eq(index).find('[name=courseen]').val();
		var coursestartdateen = $('.midSchoolEduDiven').eq(index).find('[name=coursestartdateen]').val();
		coursestartdateen = formatDate(coursestartdateen);
		var courseenddateen = $('.midSchoolEduDiven').eq(index).find('[name=courseenddateen]').val();
		courseenddateen = formatDate(courseenddateen);
		var institutioncountryen = $('.midSchoolEduDiven').eq(index).find('[name=institutioncountryen]').val();
		if(institutioncountryen != 0){
			beforeEducationLength += institutioncountryen;
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
		
		//英文
		beforeEducationLength += institutionen;
		beforeEducationLength += institutionaddressen;
		beforeEducationLength += secinstitutionaddressen;
		beforeEducationLength += institutioncityen;
		beforeEducationLength += institutionprovinceen;
		beforeEducationLength += institutionzipcodeen;
		beforeEducationLength += courseen;
		beforeEducationLength += coursestartdateen;
		beforeEducationLength += courseenddateen;
		
		if(beforeEducationLength.length > 0 ){
			beforeEducation.staffid = staffId;
			beforeEducation.institution = institution;
			beforeEducation.institutionaddress = institutionaddress;
			beforeEducation.secinstitutionaddress = secinstitutionaddress;
			beforeEducation.institutioncity = institutioncity;
			beforeEducation.institutionprovince = institutionprovince;
			beforeEducation.institutionzipcode = institutionzipcode;
			beforeEducation.isinstitutionprovinceapply = isinstitutionprovinceapply;
			beforeEducation.isinstitutionzipcodeapply = isinstitutionzipcodeapply;
			beforeEducation.course = course;
			beforeEducation.coursestartdate = coursestartdate;
			beforeEducation.courseenddate = courseenddate;
			beforeEducation.institutioncountry = institutioncountry;
			
			//英文
			beforeEducation.institutionen = institutionen;
			beforeEducation.institutionaddressen = institutionaddressen;
			beforeEducation.secinstitutionaddressen = secinstitutionaddressen;
			beforeEducation.institutioncityen = institutioncityen;
			beforeEducation.institutionprovinceen = institutionprovinceen;
			beforeEducation.institutionzipcodeen = institutionzipcodeen;
			beforeEducation.isinstitutionprovinceapplyen = isinstitutionprovinceapplyen;
			beforeEducation.isinstitutionzipcodeapplyen = isinstitutionzipcodeapplyen;
			beforeEducation.courseen = courseen;
			beforeEducation.coursestartdateen = coursestartdateen;
			beforeEducation.courseenddateen = courseenddateen;
			beforeEducation.institutioncountryen = institutioncountryen;
			
			beforeEducationList.push(beforeEducation);
		}
	});
	
	return beforeEducationList;
}

//获取以前使用的语言信息
function getLanguageList(){
	var languageList = [];
	$('.languagenameDiv').each(function(index,i){
		var languageLength = '';
		var language = {};
		
		var languagename = $(this).find('[name=languagename]').val();
		var languagenameen = $('.languagenameDiven').eq(index).find('[name=languagenameen]').val();
		
		languageLength += languagename;
		languageLength += languagenameen;
		
		if(languageLength.length > 0 ){
			language.staffid = staffId;
			language.languagename = languagename;
			language.languagenameen = languagenameen;
			languageList.push(language);
		}
	});
	
	return languageList;
}

//获取过去五年去过的国家
function getCountryList(){
	var countryList = [];
	$('.travelCountry').each(function(index,i){
		var countryLength = '';
		var country = {};
		
		var traveledcountry = $(this).find('[name=traveledcountry]').val();
		var traveledcountryen = $('.travelCountryen').eq(index).find('[name=traveledcountryen]').val();
		
		countryLength += traveledcountry;
		countryLength += traveledcountryen;
		
		if(countryLength.length > 0 ){
			country.staffid = staffId;
			country.traveledcountry = traveledcountry;
			country.traveledcountryen = traveledcountryen;
			countryList.push(country);
		}
	});
	return countryList;
}

//获取参加过的组织信息
function getOrganizationList(){
	var organizationList = [];
	$('.organizationDiv').each(function(index,i){
		var organizationLength = '';
		var organization = {};
		
		var organizationname = $(this).find('[name=organizationname]').val();
		var organizationnameen = $('.organizationDiven').eq(index).find('[name=organizationnameen]').val();
		
		organizationLength += organizationname;
		organizationLength += organizationnameen;
		
		if(organizationLength.length > 0 ){
			organization.staffid = staffId;
			organization.organizationname = organizationname;
			organization.organizationnameen = organizationnameen;
			organizationList.push(organization);
		}
	});
	return organizationList;
}

//获取曾服兵役信息
function getMilitaryInfoList(){
	var militaryInfoList = [];
	$('.militaryInfoDiv').each(function(index,i){
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
		servicestartdate = formatDate(servicestartdate);
		var serviceenddate = $(this).find('[name=serviceenddate]').val();
		serviceenddate = formatDate(serviceenddate);
		
		//英文
		var militarycountryen = $('.militaryInfoDiven').eq(index).find('[name=militarycountryen]').val();
		
		if(militarycountryen != 0){
			militaryInfoLength += militarycountryen;
		}else{
			militaryInfoLength += '';
		}
		var servicebranchen = $('.militaryInfoDiven').eq(index).find('[name=servicebranchen]').val();
		var ranken = $('.militaryInfoDiven').eq(index).find('[name=ranken]').val();
		var militaryspecialtyen = $('.militaryInfoDiven').eq(index).find('[name=militaryspecialtyen]').val();
		var servicestartdateen = $('.militaryInfoDiven').eq(index).find('[name=servicestartdate]').val();
		servicestartdateen = formatDate(servicestartdateen);
		var serviceenddateen = $('.militaryInfoDiven').eq(index).find('[name=serviceenddateen]').val();
		serviceenddateen = formatDate(serviceenddateen);
		
		militaryInfoLength += servicebranch;
		militaryInfoLength += rank;
		militaryInfoLength += militaryspecialty;
		militaryInfoLength += servicestartdate;
		militaryInfoLength += serviceenddate;
		//英文
		militaryInfoLength += servicebranchen;
		militaryInfoLength += ranken;
		militaryInfoLength += militaryspecialtyen;
		militaryInfoLength += servicestartdateen;
		militaryInfoLength += serviceenddateen;
		
		if(militaryInfoLength.length >0){
			militaryInfo.staffid = staffId;
			militaryInfo.militarycountry = militarycountry;
			militaryInfo.servicebranch = servicebranch;
			militaryInfo.rank = rank;
			militaryInfo.militaryspecialty = militaryspecialty;
			militaryInfo.servicestartdate = servicestartdate;
			militaryInfo.serviceenddate = serviceenddate;
			
			//英文
			militaryInfo.militarycountryen = militarycountryen;
			militaryInfo.servicebranchen = servicebranchen;
			militaryInfo.ranken = ranken;
			militaryInfo.militaryspecialtyen = militaryspecialtyen;
			militaryInfo.servicestartdateen = servicestartdate;
			militaryInfo.serviceenddateen = serviceenddateen;
			
			militaryInfoList.push(militaryInfo);
		}
	});
	
	return militaryInfoList;
}


//格式化日期
function formatDate(dataStr) { 
	 var date = dataStr;
	 if(date != "" && date != null && date != undefined){
		 var date = dataStr.split('/');
			if(date != null || date !="")
		    var y = date[2];  
		    var m = date[1];  
		    var d = date[0];  
		    return y + '-' + m + '-' + d;  
	 }else{
		 return "";
	 }
	
};