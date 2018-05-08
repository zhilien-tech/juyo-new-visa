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
var visaVue = new Vue({
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
				console.log(data.familyInfo);
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
			
			var isknowspousecity = visaInfo.familyInfo.isknowspousecity;
			visaInfo.familyInfo.isknowspousecityen = isknowspousecity;
			visaInfo.familyInfo.spousecity = "";
			visaInfo.familyInfo.spousecityen = "";
		},
		isprovincesapply:function(){
			
			var isprovinceapply = visaInfo.familyInfo.isprovinceapply;
			visaInfo.familyInfo.isprovinceapplyen = isprovinceapply;
			visaInfo.familyInfo.isprovinceapply = "";
			visaInfo.familyInfo.isprovinceapplyen = "";
		},
		isprovinceapplywork:function(){
			
			var isprovinceapply = visaInfo.workEducationInfo.isprovinceapply;
			
			visaInfo.workEducationInfo.isprovinceapplyen = isprovinceapply;
			visaInfo.workEducationInfo.province = "";
			visaInfo.workEducationInfo.provinceen = "";
		},
		isjobcodechecked:function(){
			
			var codechecked = visaInfo.workEducationInfo.iszipcodeapply;
			visaInfo.workEducationInfo.iszipcodeapplyen = codechecked;
			visaInfo.workEducationInfo.zipcode = "";
			visaInfo.workEducationInfo.zipcodeen = "";
		},
		ismoneychecked:function(){
			
			var moneychecked = visaInfo.workEducationInfo.issalaryapply;
			visaInfo.workEducationInfo.issalaryapplyen = moneychecked;
			visaInfo.workEducationInfo.salary = "";
			visaInfo.workEducationInfo.salaryen = "";
		},
		isotherapply:function(){
			
			visaInfo.familyInfo.apply = "";
			visaInfo.familyInfo.applyen = "";
			var otherapply = visaInfo.familyInfo.apply;
			//alert(otherapply);
			visaInfo.familyInfo.applyen = otherapply;
		},
		isselectcodeapply:function(){
			
			visaInfo.familyInfo.selectcodeapply = "";
			visaInfo.familyInfo.selectcodeapplyen = "";
			var selectcodeapply = visaInfo.familyInfo.selectcodeapply;
			visaInfo.familyInfo.selectcodeapplyen = selectcodeapply;
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
		isapplyingsametypevisa:function(){
//			是否在申请相同类型的签证
			var isapplyingsametypevisa = visaInfo.previUSTripInfo.isapplyingsametypevisa;
			visaInfo.previUSTripInfo.isapplyingsametypevisaen = isapplyingsametypevisa;
		},
		issamecountry:function(){
			//是否在签发上述签证的国家或地区申请并且是您所在的国家或地区的居住地
			var issamecountry = visaInfo.previUSTripInfo.issamecountry;
			visaInfo.previUSTripInfo.issamecountryen = issamecountry;
		},
		istenprinted:function(){
			//是否采集过指纹
			var istenprinted = visaInfo.previUSTripInfo.istenprinted;
			visaInfo.previUSTripInfo.istenprinteden = istenprinted;
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
			visaInfo.contactPointInfo.isknownameen = isknowname;
			if(isknowname){
				visaInfo.contactPointInfo.firstname = "";
				visaInfo.contactPointInfo.lastname = "";
				visaInfo.contactPointInfo.firstnameen = "";
				visaInfo.contactPointInfo.lastnameen = "";
				//visaInfo.contactPointInfo.isknownameen = true;
			}
		},
//		isfirstname:function(){
//			var firstname = visaInfo.contactPointInfo.firstname;
//			visaInfo.contactPointInfo.firstnameen = firstname;
//		},
//		islastname:function(){
//			var lastname = visaInfo.contactPointInfo.lastname;
//			visaInfo.contactPointInfo.lastnameen = lastname;
//		},
		isKnowOrganizationName:function(){
			var isknoworganizationname = visaInfo.contactPointInfo.isknoworganizationname;
			visaInfo.contactPointInfo.isknoworganizationnameen = isknoworganizationname;
			if(isknoworganizationname){
				visaInfo.contactPointInfo.organizationname = "";
				visaInfo.contactPointInfo.organizationnameen = "";
			}
			
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
			visaInfo.workEducationInfo.jobcity="";
			visaInfo.workEducationInfo.province="";
			visaInfo.workEducationInfo.isprovinceapply="";
			visaInfo.workEducationInfo.zipcode="";
			visaInfo.workEducationInfo.codechecked="";
			visaInfo.workEducationInfo.telephone="";
			visaInfo.workEducationInfo.country=0;
			visaInfo.workEducationInfo.salary="";
			visaInfo.workEducationInfo.issalaryapply="";
			visaInfo.workEducationInfo.workstartdate="";
			visaInfo.workEducationInfo.jobduty="";
			
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
		lostyear:function(from, to, vueObj){
//			美国签证丢失或被盗--年份
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
					visaInfo.previUSTripInfo.lostyearen = result;
				}
			});
		},
		lostexplain:function(from, to, vueObj){
//			美国签证丢失或被盗--说明
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
					visaInfo.previUSTripInfo.lostexplainen = result;
				}
			});
		},
		cancelexplainen:function(from, to, vueObj){
//			美国签证是否被取消或撤销--说明
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
					visaInfo.previUSTripInfo.cancelexplainen = result;
				}
			});
		},
		refusedexplainen:function(from, to, vueObj){
//			被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境--说明
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
					visaInfo.previUSTripInfo.refusedexplainen = result;
				}
			});
		},
		permanentresidentexplain:function(from, to, vueObj){
//			曾经是否是美国合法永久居民--说明
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
					visaInfo.previUSTripInfo.permanentresidentexplainen = result;
				}
			});
		},
		immigrantpetitionexplain:function(from, to, vueObj){
//			有没有人曾代表您向美国公民和移民服务局提交过移民申请--说明
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
					visaInfo.previUSTripInfo.immigrantpetitionexplainen = result;
				}
			});
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
		},
		liaisonfirstname:function(from){
//			联络人姓   
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.contactPointInfo.firstnameen = pinyinchar;
		},
		liaisonlastname:function(from){
//			联络人名   
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.contactPointInfo.lastnameen = pinyinchar;
			
		},
		liaisonOrg:function(from, to, vueObj){
//			联络人组织名称   
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
					visaInfo.contactPointInfo.organizationnameen = result;
				}
			});
		},
		liaisonrelative:function(from, to, vueObj){
//			联络人与你的关系   
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
					visaInfo.contactPointInfo.ralationshipen = result;
				}
			});
		},
		liaisonfirststreet:function(from, to, vueObj){
//			联络人街道首选  
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
					visaInfo.contactPointInfo.addressen = result;
				}
			});
		},
		liaisonlaststreet:function(from, to, vueObj){
//			联络人街道次选  
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
					visaInfo.contactPointInfo.secaddressen = result;
				}
			});
		},
		liaisonstate:function(from, to, vueObj){
//			联络人州/省 
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
					visaInfo.contactPointInfo.stateen = result;
				}
			});
		},
		liaisoncity:function(from, to, vueObj){
//			联络人市 
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
					visaInfo.contactPointInfo.cityen = result;
				}
			});
		},
		liaisoncode:function(from, to, vueObj){
//			联络人邮政编码
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
					visaInfo.contactPointInfo.zipcodeen = result;
				}
			});
		},
		liaisontelphone:function(from, to, vueObj){
//			联络人电话号吗
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
					visaInfo.contactPointInfo.telephoneen = result;
				}
			});
		},
		liaisonemail:function(from, to, vueObj){
//			联络人邮件地址
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
					visaInfo.contactPointInfo.emailen = result;
				}
			});
		},
		familyinfofirstname:function(from, to, vueObj){
//			家庭信息 父亲的姓   拼音显示  需要修改
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.fatherfirstnameen = pinyinchar;
			
		},
		familyinfolastname:function(from, to, vueObj){
//			家庭信息 父亲的名   拼音显示  需要修改
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.fatherlastnameen = pinyinchar;
			
		},
		isknowfatherbirthday:function(){
//			不知道父亲的生日
			var isknowfatherbirthday = visaInfo.familyInfo.isknowfatherbirthday;
			if(isknowfatherbirthday){
				visaInfo.familyInfo.fatherbirthday = "";
				visaInfo.familyInfo.fatherbirthdayen = "";
			}
			visaInfo.familyInfo.isknowfatherbirthdayen = isknowfatherbirthday;
			
		},
		isknowmotherbirthday:function(){
//			不知道母亲的生日
			var isknowmotherbirthday = visaInfo.familyInfo.isknowmotherbirthday;
			if(isknowmotherbirthday){
				visaInfo.familyInfo.motherbirthday = "";
				visaInfo.familyInfo.motherbirthdayen = "";
			}
			visaInfo.familyInfo.isknowmotherbirthdayen = isknowmotherbirthday;
			
		},
		familyinfostatus:function(from, to, vueObj){
//			家庭信息 父亲身份
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
					visaInfo.familyInfo.fatherstatusen = result;
				}
			});
		},
		familyinfomofirstname:function(from, to, vueObj){
//			家庭信息 母亲的名   拼音显示  需要修改
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.motherfirstnameen = pinyinchar;
			
		},
		familyinfomolastname:function(from, to, vueObj){
//			家庭信息 母亲的名   拼音显示  需要修改
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.motherlastnameen = pinyinchar;
			
		},
		familyinfomostatus:function(from, to, vueObj){
//			家庭信息 母亲身份
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
					visaInfo.familyInfo.motherstatusen = result;
				}
			});
		},
		spousefirstname:function(from, to, vueObj){
//			配偶姓
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.spousefirstnameen = pinyinchar;
		
		},
		spouselastname:function(from, to, vueObj){
//			配偶名
			var toval = $("#" + from).val();
			var pinyinchar = getPinYinStr(toval);
			visaInfo.familyInfo.spouselastnameen = pinyinchar;
			
		},
		spousenationality:function(from, to, vueObj){
//			配偶国籍
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
					visaInfo.familyInfo.spousenationalityen = result;
				}
			});
		},
		spousecity:function(from, to, vueObj){
//			配偶出生城市
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
					visaInfo.familyInfo.spousecityen = result;
				}
			});
		},
		spousecountry:function(from, to, vueObj){
//			配偶出生国家
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
					visaInfo.familyInfo.spousecountryen = result;
				}
			});
		},
		spouseaddress:function(from, to, vueObj){
//			配偶联系地址
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
					if(toval == 5){
						$(".otherSpouseInfo").show();
					}else{
						$(".otherSpouseInfo").hide();
						emptyContentByObj($("div.otherSpouseInfo"));
					}
					visaInfo.familyInfo.spouseaddressen = result;
				}
			});
		},
		spouseotherstreet:function(from, to, vueObj){
//			配偶联系地址其他--街道首选
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
					visaInfo.familyInfo.firstaddressen = result;
				}
			});
		},
		spouseotherlaststreet:function(from, to, vueObj){
//			配偶联系地址其他--街道次要
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
					visaInfo.familyInfo.secondaddressen = result;
				}
			});
		},
		spouseotherprovince:function(from, to, vueObj){
//			配偶联系地址其他--州/省
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
					visaInfo.familyInfo.provinceen = result;
				}
			});
		},
		spouseothercity:function(from, to, vueObj){
//			配偶联系地址其他--市 
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
					visaInfo.familyInfo.cityen = result;
				}
			});
		},
		spouseothercode:function(from, to, vueObj){
//			配偶联系地址其他--邮政编码 
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
					visaInfo.familyInfo.zipcodeen = result;
				}
			});
		},
		spouseothercountry:function(from, to, vueObj){
//			配偶联系地址其他--国家
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
					visaInfo.familyInfo.countryen = result;
				}
			});
		},
		jobprofession:function(from, to, vueObj){
//			工作/教育/培训信息--主要职业
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
					visaInfo.workEducationInfo.occupationen = result;
				}
			});
		},
		jobprofessionunit:function(from, to, vueObj){
//			工作/教育/培训信息--单位或学校名称
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
					visaInfo.workEducationInfo.unitnameen = result;
				}
			});
		},
		jobprofessionaddress:function(from, to, vueObj){
//			工作/教育/培训信息--街道地址首选
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
					visaInfo.workEducationInfo.addressen = result;
				}
			});
		},
		jobprofessionlastaddress:function(from, to, vueObj){
//			工作/教育/培训信息--街道地址次要
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
					visaInfo.workEducationInfo.secaddressen = result;
				}
			});
		},
		jobprofessionprovince:function(from, to, vueObj){
//			工作/教育/培训信息--州/省
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
					visaInfo.workEducationInfo.provinceen = result;
				}
			});
		},
		jobprofessioncity:function(from, to, vueObj){
//			工作/教育/培训信息--市
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
					visaInfo.workEducationInfo.cityen = result;
				}
			});
		},
		jobprofessioncode:function(from, to, vueObj){
//			工作/教育/培训信息--邮政编码
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
					visaInfo.workEducationInfo.zipcodeen = result;
				}
			});
		},
		jobprofessiontelphone:function(from, to, vueObj){
//			工作/教育/培训信息--电话号吗
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
					visaInfo.workEducationInfo.telephoneen = result;
				}
			});
		},
		jobprofessioncountry:function(from, to, vueObj){
//			工作/教育/培训信息--国家地区
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
					visaInfo.workEducationInfo.countryen = result;
				}
			});
		},
		jobprofessionsalary:function(from, to, vueObj){
//			工作/教育/培训信息--当月收入
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
					visaInfo.workEducationInfo.salaryen = result;
				}
			});
		},
		jobprofessionduty:function(from, to, vueObj){
//			工作/教育/培训信息--描述你的职责
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
					visaInfo.workEducationInfo.dutyen = result;
				}
			});
		},
		clanname:function(from, to, vueObj){
//			氏族或部落名称
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
					visaInfo.workEducationInfo.clannameen = result;
				}
			});
		},
		skillexplains:function(from, to, vueObj){
//			是否有专业技能或培训，如强制、爆炸物、核能、生物或化学---说明
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
					visaInfo.workEducationInfo.skillexplainen = result;
				}
			});
		},
		isservedinrebelgroup:function(){
			//是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织
			var isservedinrebelgroup = visaInfo.workEducationInfo.isservedinrebelgroup;
			visaInfo.workEducationInfo.isservedinrebelgroupen = isservedinrebelgroup;
		},
//		iswitnessname:function(){
////			证明人姓名
//			var toval = $("#" + from).val();
//			var pinyinchar = getPinYinStr(toval);
//			visaInfo.workEducationInfo.witnessnameen = pinyinchar;
//		},
//		iswitnesstelphone:function(){
////			证明人电话
//			var toval = $("#" + from).val();
//			$.ajax({
//				url : '/admin/translate/translate',
//				data : {
//					api : 'google',
//					strType : to,
//					en : 'en',
//					q : toval
//				},
//				type : 'POST',
//				dataType : 'json',
//				success : function(result) {
//					$("#" + to).val(result).change();
//					visaInfo.workEducationInfo.witnesstelphoneen = result;
//				}
//			});
//		},
//		iswitnesspost:function(){
////			证明人职务
//			var toval = $("#" + from).val();
//			$.ajax({
//				url : '/admin/translate/translate',
//				data : {
//					api : 'google',
//					strType : to,
//					en : 'en',
//					q : toval
//				},
//				type : 'POST',
//				dataType : 'json',
//				success : function(result) {
//					$("#" + to).val(result).change();
//					visaInfo.workEducationInfo.witnessposten = result;
//				}
//			});
//		}
		
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
	//美国联络人
	visadata.contactPointInfo = visaInfo.contactPointInfo;
	//家庭信息
	visadata.familyInfo = visaInfo.familyInfo;
	//父亲的生日
	visaInfo.familyInfo.fatherbirthday = formatDate($('#fatherbirthday').val());
	visaInfo.familyInfo.fatherbirthdayen = formatDate($('#fatherbirthdayen').val());
	//母亲的生日
	visaInfo.familyInfo.motherbirthday = formatDate($('#motherbirthday').val());
	visaInfo.familyInfo.motherbirthdayen = formatDate($('#motherbirthdayen').val());
	//配偶生日
	visaInfo.familyInfo.spousebirthday = formatDate($('#spousebirthday').val());
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
		$.ajax({ 
			type: 'POST', 
			data: {
				data:JSON.stringify(visadata)
			}, 
			url: '/admin/bigCustomer/updateVisaInfos.html',
			success: function (data) { 
				if(data.status == 200){
					layer.msg("保存成功", {
						time: 1000,
						end: function () {
							parent.successCallback();
							closeWindow();
						}
					});
				}else{
					layer.msg("保存失败","", 2000);
				}
				layer.closeAll('loading');
			},
			error: function (xhr) {
				layer.msg("保存失败");
			} 
		});
		
	}else if(status ==2){
		layer.closeAll('loading');
		//左箭头跳转
		window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable;
		$.ajax({ 
			type: 'POST', 
			data: {
				data:JSON.stringify(visadata)
			}, 
			url: '/admin/bigCustomer/updateVisaInfos.html',
			success: function (data) { 
				if(data.status == 200){
					parent.successCallback();
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
	

}

