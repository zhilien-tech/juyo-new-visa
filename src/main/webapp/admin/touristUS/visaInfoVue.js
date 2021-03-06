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

			}
		});
	},
	methods:{
		isknowspousecity:function(){
			visaInfo.familyInfo.spousecity = "";
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
			}
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
				visaInfo.contactPointInfo.lastname = "";
			}
		},
		isKnowOrganizationName:function(){
			var isknoworganizationname = visaInfo.contactPointInfo.isknoworganizationname;
			if(isknoworganizationname){
				visaInfo.contactPointInfo.organizationname = "";
			}
		},
		isKnowEmailAddress:function(){
			var isknowemail = visaInfo.contactPointInfo.isknowemail;
			if(isknowemail){
				visaInfo.contactPointInfo.email = "";
			}
		},
		isknowfatherfirstname:function(){
			var isknowfatherfirstname = visaInfo.familyInfo.isknowfatherfirstname;
			if(isknowfatherfirstname){
				visaInfo.familyInfo.fatherfirstname = "";
			}
		},
		isknowfatherlastname:function(){
			var isknowfatherlastname = visaInfo.familyInfo.isknowfatherlastname;
			if(isknowfatherlastname){
				visaInfo.familyInfo.fatherlastname = "";
			}
		},
		isknowmotherfirstname:function(){
			var isknowmotherfirstname = visaInfo.familyInfo.isknowmotherfirstname;
			if(isknowmotherfirstname){
				visaInfo.familyInfo.motherfirstname = "";
			}
		},
		isknowmotherlastname:function(){
			var isknowmotherlastname = visaInfo.familyInfo.isknowmotherlastname;
			if(isknowmotherlastname){
				visaInfo.familyInfo.motherlastname = "";
			}
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
		},
		isclan:function(){
			visaInfo.workEducationInfo.clanname = "";
		},
		hasspecializedskill:function(){
			var hasspecializedskill = visaInfo.workEducationInfo.hasspecializedskill;
			if(hasspecializedskill){
				visaInfo.workEducationInfo.skillexplain = "";
			}
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
		}
	}
});

//签证信息页保存
function save(){

	layer.load(1);
	
	var visadata = {};
	
	//staffId
	visadata.staffId = staffId;
	
	//旅伴信息
	visadata.travelCompanionInfo = visaInfo.travelCompanionInfo;

	//以前的美国旅游信息
	visadata.previUSTripInfo = visaInfo.previUSTripInfo;
	visaInfo.previUSTripInfo.issueddate = formatDate($('#issueddate').val());//最后一次签证的签发日期
	//美国联络点
	visadata.contactPointInfo = visaInfo.contactPointInfo;
	//家庭信息
	visadata.familyInfo = visaInfo.familyInfo;
	visaInfo.familyInfo.spousebirthday = formatDate($('#spousebirthday').val());//配偶生日
	//工作/教育/培训信息
	visadata.workEducationInfo = visaInfo.workEducationInfo;
	visaInfo.workEducationInfo.workstartdate = formatDate($('#workstartdate').val());//工作开始日期
	//同伴信息
	visadata.companionList = getCompanionList();
	console.log(visadata.companionList);
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
			closeWindow();
		},
		error: function (xhr) {
			layer.msg("保存失败");
		} 
	});

}

