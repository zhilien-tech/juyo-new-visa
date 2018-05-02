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
	el: '#section',
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

			}
		});
	},
	methods:{
		//change事件名字
		istravelwithother:function(){
			var istravelwithother = visaInfo.travelCompanionInfo.istravelwithother; //中文
			visaInfo.travelCompanionInfo.istravelwithotheren = istravelwithother;//英文
			
		},
		ispart:function(){
			var ispart = visaInfo.travelCompanionInfo.ispart;
			visaInfo.travelCompanionInfo.isparten = ispart;
		},
		isknowspousecity:function(){
			visaInfo.familyInfo.spousecity = "";
			visaInfo.familyInfo.spousecityen = "";
			
		},
		changeSpouse:function(){
			changeSpouseShow();
			visaInfo.familyInfo.firstaddress = "";
			visaInfo.familyInfo.secondaddress = "";
			visaInfo.familyInfo.city = "";
			visaInfo.familyInfo.province = "";
			visaInfo.familyInfo.isprovinceapply = 0;
			visaInfo.familyInfo.zipcode="";
			visaInfo.familyInfo.iszipcodeapply = 0;
			visaInfo.familyInfo.country = 0;
		},
		idknowvisanumberChange:function(){
			var isKnowNum = visaInfo.previUSTripInfo.idknowvisanumber;
			if(isKnowNum){
				visaInfo.previUSTripInfo.visanumber = "";
				visaInfo.previUSTripInfo.visanumberen = "";
				visaInfo.previUSTripInfo.idknowvisanumberen = true;
			}
		},
		hasvisanumber:function(){
			var visanumber = visaInfo.previUSTripInfo.visanumber;
			visaInfo.previUSTripInfo.visanumberen = visanumber;
		},
		hasbeeninus:function(){
			var hasbeeninus = visaInfo.previUSTripInfo.hasbeeninus;
			visaInfo.previUSTripInfo.hasbeeninusen = hasbeeninus;
		},
		hasdriverlicense:function(){
			var hasdriverlicense = visaInfo.previUSTripInfo.hasdriverlicense;
			visaInfo.previUSTripInfo.hasdriverlicenseen = hasdriverlicense;
		},
		isissuedvisa:function(){
			var isissuedvisa = visaInfo.previUSTripInfo.isissuedvisa;
			visaInfo.previUSTripInfo.isissuedvisaen = isissuedvisa;
		},
		islost:function(){
			var islost = visaInfo.previUSTripInfo.islost;
			visaInfo.previUSTripInfo.islosten = islost;
		},
		iscancelled:function(){
			var iscancelled =  visaInfo.previUSTripInfo.iscancelled;
			visaInfo.previUSTripInfo.iscancelleden = iscancelled;
		},
		isrefused:function(){
			var isrefused = visaInfo.previUSTripInfo.isrefused;
			visaInfo.previUSTripInfo.isrefuseden = isrefused;
		},
		islegalpermanentresident:function(){
			var islegalpermanentresident = visaInfo.previUSTripInfo.islegalpermanentresident;
			visaInfo.previUSTripInfo.islegalpermanentresidenten = islegalpermanentresident;
		},
		isfiledimmigrantpetition:function(){
			var isfiledimmigrantpetition = visaInfo.previUSTripInfo.isfiledimmigrantpetition;
			visaInfo.previUSTripInfo.isfiledimmigrantpetitionen = isfiledimmigrantpetition;
		},
		visaNotLost:function(){
			visaInfo.previUSTripInfo.lostyear = "";
			visaInfo.previUSTripInfo.lostexplain = "";
		},
		visaNotCancel:function(){
			visaInfo.previUSTripInfo.cancelexplain="";
		},
		visaNotRefused:function(){
			visaInfo.previUSTripInfo.refusedexplain="";
		},
		visaNotIegal:function(){
			visaInfo.previUSTripInfo.permanentresidentexplai="";
		},
		visaNotfiledimmigrantpetition:function(){
			visaInfo.previUSTripInfo.immigrantpetitionexplain="";
		},
		isKnowContactPointName:function(){
			var isknowname = visaInfo.contactPointInfo.isknowname;
			if(isknowname){
				visaInfo.contactPointInfo.firstname = "";
				visaInfo.contactPointInfo.lastname = "";
				visaInfo.contactPointInfo.firstnameen = "";
				visaInfo.contactPointInfo.lastnameen = "";
				visaInfo.contactPointInfo.isknownameen = true;
			}
		},
		isfirstname:function(){
			var firstname = visaInfo.contactPointInfo.firstname;
			visaInfo.contactPointInfo.firstnameen = firstname;
		},
		islastname:function(){
			var lastname = visaInfo.contactPointInfo.lastname;
			visaInfo.contactPointInfo.lastnameen = lastname;
		},
		isKnowOrganizationName:function(){
			var isknoworganizationname = visaInfo.contactPointInfo.isknoworganizationname;
			if(isknoworganizationname){
				visaInfo.contactPointInfo.organizationname = "";
				visaInfo.contactPointInfo.organizationnameen = "";
			}
			visaInfo.contactPointInfo.isknoworganizationnameen = isknoworganizationname;
		},
		isorganizationname:function(){
			var organizationname = visaInfo.contactPointInfo.organizationname;
			visaInfo.contactPointInfo.organizationnameen = organizationname;
		},
		isralationship:function(){
			var ralationship = visaInfo.contactPointInfo.ralationship;
			visaInfo.contactPointInfo.ralationshipen = ralationship;
		},
		isaddress:function(){
			var address = isaInfo.contactPointInfo.address;
			isaInfo.contactPointInfo.addressen = address;
		},
		issecaddress:function(){
			var secaddress = visaInfo.contactPointInfo.secaddress;
			visaInfo.contactPointInfo.secaddressen = secaddress;
		},
		isKnowEmailAddress:function(){
			var isknowemail = visaInfo.contactPointInfo.isknowemail;
			if(isknowemail){
				visaInfo.contactPointInfo.email = "";
				visaInfo.contactPointInfo.emailen = "";
			}
			visaInfo.contactPointInfo.isknowemailen = isknowemail;

		},
		isknowfatherfirstname:function(){
			var isknowfatherfirstname = visaInfo.familyInfo.isknowfatherfirstname;
			if(isknowfatherfirstname){
				visaInfo.familyInfo.fatherfirstname = "";
				visaInfo.familyInfo.fatherfirstnameen = "";
			}
			visaInfo.familyInfo.isknowfatherfirstnameen = isknowfatherfirstname;
		},
		isknowfatherlastname:function(){
			var isknowfatherlastname = visaInfo.familyInfo.isknowfatherlastname;
			if(isknowfatherlastname){
				visaInfo.familyInfo.fatherlastname = "";
				visaInfo.familyInfo.fatherlastnameen = "";
			}
			visaInfo.familyInfo.isknowfatherlastnameen = isknowfatherlastname;
		},
		isfatherinus:function(){
			var isfatherinus = visaInfo.familyInfo.isfatherinus;
			visaInfo.familyInfo.isfatherinusen = isfatherinus;
		},
		hasimmediaterelatives:function(){
			var hasimmediaterelatives = visaInfo.familyInfo.hasimmediaterelatives;
			visaInfo.familyInfo.hasimmediaterelativesen = hasimmediaterelatives;
		},
		isknowmotherfirstname:function(){
			var isknowmotherfirstname = visaInfo.familyInfo.isknowmotherfirstname;
			if(isknowmotherfirstname){
				visaInfo.familyInfo.motherfirstname = "";
				visaInfo.familyInfo.motherfirstnameen = "";
			}
			visaInfo.familyInfo.isknowmotherfirstnameen = isknowmotherfirstname;
		},
		isknowmotherlastname:function(){
			var isknowmotherlastname = visaInfo.familyInfo.isknowmotherlastname;
			if(isknowmotherlastname){
				visaInfo.familyInfo.motherlastname = "";
				visaInfo.familyInfo.motherlastnameen = "";
			}
			visaInfo.familyInfo.isknowmotherlastnameen = isknowmotherlastname;
		},
		isfatherinus:function(){
			var isfatherinus = visaInfo.familyInfo.isfatherinus;
			if(isfatherinus){
				visaInfo.familyInfo.fatherstatus = 0;
			}
		},
		ismotherinus:function(){
			var ismotherinus = visaInfo.familyInfo.ismotherinus;
			if(ismotherinus){
				visaInfo.familyInfo.motherstatus = 0;
			}
			visaInfo.familyInfo.ismotherinusen = ismotherinus;
		},
		ismotherinues:function(){
			var ismotherinus = visaInfo.familyInfo.ismotherinus;
			visaInfo.familyInfo.ismotherinusen = ismotherinus;
		},
		isclan:function(){
			visaInfo.workEducationInfo.clanname = "";
			var isclan = visaInfo.workEducationInfo.isclan;
			visaInfo.workEducationInfo.isclanen = isclan;
		},
		isemployed:function(){
			var isemployed = visaInfo.workEducationInfo.isemployed;
			visaInfo.workEducationInfo.isemployeden = isemployed;
		},
		issecondarylevel:function(){
			var issecondarylevel = visaInfo.workEducationInfo.issecondarylevel;
			visaInfo.workEducationInfo.issecondarylevelen = issecondarylevel;
		},
		istraveledanycountry:function(){
			var istraveledanycountry = visaInfo.workEducationInfo.istraveledanycountry;
			visaInfo.workEducationInfo.istraveledanycountryen = istraveledanycountry;
		},
//		isservedinrebelgroup:function(){
//			var isservedinrebelgroup = visaInfo.workEducationInfo.isservedinrebelgroup;
//			visaInfo.workEducationInfo.isservedinrebelgroupen = isservedinrebelgroup;
//		},
		isworkedcharitableorganization:function(){
			var isworkedcharitableorganization = visaInfo.workEducationInfo.isworkedcharitableorganization;
			visaInfo.workEducationInfo.isworkedcharitableorganizationen = isworkedcharitableorganization;
		},
		hasspecializedskill:function(){
			var hasspecializedskill = visaInfo.workEducationInfo.hasspecializedskill;
			visaInfo.workEducationInfo.hasspecializedskillen = hasspecializedskill;
		},
		hasservedinmilitary:function(){
			var hasservedinmilitary = visaInfo.workEducationInfo.hasservedinmilitary;
			visaInfo.workEducationInfo.hasservedinmilitaryen = hasservedinmilitary;
		},
		hasspecializedskill:function(){
			var hasspecializedskill = visaInfo.workEducationInfo.hasspecializedskill;
			if(hasspecializedskill){
				visaInfo.workEducationInfo.skillexplain = "";
				visaInfo.workEducationInfo.skillexplainen = "";
			}
			visaInfo.workEducationInfo.hasspecializedskillen = hasspecializedskill;
		},
		isstate:function(){
			
			//美国联络人州/省
			var state = visaInfo.contactPointInfo.state;
			
			visaInfo.contactPointInfo.stateen = state;
		},
		iscity:function(){
			var city = visaInfo.contactPointInfo.city;
			visaInfo.contactPointInfo.cityen = city;
		},
		iszipcode:function(){
			var zipcode = visaInfo.contactPointInfo.zipcode;
			visaInfo.contactPointInfo.zipcodeen = zipcode;
		},
		istelephone:function(){
			var telephone = visaInfo.contactPointInfo.telephone;
			visaInfo.contactPointInfo.telephoneen = telephone;
		},
		isemail:function(){
			var email = visaInfo.contactPointInfo.email;
			visaInfo.contactPointInfo.emailen = email;
		},
		hasspousenationalityen:function(){
			var spousenationality = visaInfo.familyInfo.spousenationality;
			visaInfo.familyInfo.spousenationalityen = spousenationality;
		},
		isspousecountry:function(){
			var spousecountry = visaInfo.familyInfo.spousecountry;
			visaInfo.familyInfo.spousecountryen = spousecountry;
		},
		occupationChange:function(){
			visaInfo.workEducationInfo.unitname ="";
			visaInfo.workEducationInfo.address ="";
			visaInfo.workEducationInfo.secaddress="";
			visaInfo.workEducationInfo.city="";
			visaInfo.workEducationInfo.province="";
			visaInfo.workEducationInfo.isprovinceapply="";
			visaInfo.workEducationInfo.zipcode="";
			visaInfo.workEducationInfo.iszipcodeapply="";
			visaInfo.workEducationInfo.telephone="";
			visaInfo.workEducationInfo.country=0;
			visaInfo.workEducationInfo.salary="";
			visaInfo.workEducationInfo.issalaryapply="";
			visaInfo.workEducationInfo.workstartdate="";
			visaInfo.workEducationInfo.duty="";
			
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
		},
		translateZhToEnVue:function(from, to, vueObj){
			var toval = $("#" + from).val();
			$.ajax({
				url : '/admin/translate/translate',
				data : {
					api : 'google',
					strType : to,
					en : 'en',
					q : toval
				},
				type : 'POST',
				dataType : 'json',
				success : function(result) {
					$("#" + to).val(result).change();
					visaInfo.travelCompanionInfo.groupnameen = result;
				}
			});
		}
	}
});

//签证信息页保存
function save(status){

	layer.load(1);
	
	var visadata = {};
	
	//staffId
	visadata.staffId = staffId;
	
	//旅伴信息
	visadata.travelCompanionInfo = visaInfo.travelCompanionInfo;
	//以前的美国旅游信息
	visadata.previUSTripInfo = visaInfo.previUSTripInfo;
	visaInfo.previUSTripInfo.issueddate = formatDate($('#issueddate').val());//最后一次签证的签发日期
	visaInfo.previUSTripInfo.issueddateen = formatDate($('#issueddateen').val());
	//美国联络点
	visadata.contactPointInfo = visaInfo.contactPointInfo;
	//家庭信息
	visadata.familyInfo = visaInfo.familyInfo;
	visaInfo.familyInfo.spousebirthday = formatDate($('#spousebirthday').val());//配偶生日
	visaInfo.familyInfo.spousebirthdayen = formatDate($('#spousebirthdayen').val());
	//工作/教育/培训信息
	visadata.workEducationInfo = visaInfo.workEducationInfo;
	visaInfo.workEducationInfo.workstartdate = formatDate($('#workstartdate').val());//工作开始日期
	visaInfo.workEducationInfo.workstartdateen = formatDate($('#workstartdateen').val());
	//同伴信息
	visadata.companionList = getCompanionList();
	//去过美国信息
	visadata.gousList = getGoUSList();
	//美国驾照信息
	visadata.driverList = getDriverList();
	//直系亲属信息 
	visadata.directList = getDirectList();
	//以前工作信息
	visadata.beforeWorkList = getBeforeWorkList();
	//以前教育信息
	visadata.beforeEducationList = getBeforeEducationList();
	//使用过的语言信息
	visadata.languageList = getLanguageList();
	//过去五年去过的国家信息
	visadata.countryList = getCountryList();
	//参加过的组织信息
	visadata.organizationList = getOrganizationList();
	//服兵役信息
	visadata.militaryInfoList = getMilitaryInfoList();

	if(status ==1){
		//点击保存按钮
		closeWindow();
	}else if(status ==2){
		//左箭头跳转
		window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable;
	}
	
	$.ajax({ 
		type: 'POST', 
		data: {
			data:JSON.stringify(visadata)
		}, 
		url: '/admin/bigCustomer/updateVisaInfos.html',
		success: function (data) { 
			if(data.status == 200){
				layer.msg("保存成功","", 2000);
			}else{
				layer.msg("保存失败","", 2000);
			}
			layer.closeAll('loading');
		},
		error: function (xhr) {
			layer.msg("保存失败");
		} 
	});

}

