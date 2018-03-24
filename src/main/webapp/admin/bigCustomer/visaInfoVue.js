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
		travelcompanioninfo:"",
		previustripinfo:"",
		contactpointinfo:"",
		familyinfo:"",
		workeducationinfo:""

	},
	created:function(){
		visaInfo=this;
		var url = '/admin/bigCustomer/getVisaInfos.html';
		$.ajax({ 
			url: url,
			dataType:"json",
			data:{
				staffId:staffId
			},
			type:'post',
			success: function(data){
				visaInfo.travelcompanioninfo = data.travelcompanioninfo;
				visaInfo.previustripinfo = data.previustripinfo;
				visaInfo.contactpointinfo = data.contactpointinfo;
				visaInfo.familyinfo = data.familyinfo;
				visaInfo.workeducationinfo = data.workeducationinfo;
				
				console.log(JSON.stringify(data));
			}
		});
	},
	methods:{
		
	}
});