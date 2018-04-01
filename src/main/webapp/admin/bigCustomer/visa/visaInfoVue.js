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
	
	layer.load(1);
	
	var visadata = {};
	
	//旅伴信息
	visadata.travelCompanionInfo = visaInfo.travelCompanionInfo;
	//以前的美国旅游信息
	visadata.previUSTripInfo = visaInfo.previUSTripInfo;
	//美国联络点
	visadata.contactPointInfo = visaInfo.contactPointInfo;
	//家庭信息
	visadata.familyInfo = visaInfo.familyInfo;
	//工作/教育/培训信息
	visadata.workEducationInfo = visaInfo.workEducationInfo;
	
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

	$.ajax({ 
		type: 'POST', 
		data: {
			data:JSON.stringify(visadata)
		}, 
		url: '/admin/bigCustomer/updateVisaInfos.html',
		success: function (data) { 
			layer.closeAll('loading');
			//window.location.reload();
			layer.msg("保存成功");
		},
		error: function (xhr) {
			layer.msg("保存失败");
		} 
	});

}

