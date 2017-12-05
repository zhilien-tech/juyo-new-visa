//VUE准备数据
var backMailObj;
new Vue({
	el: '#backmail_wrapper',
	data: {
		backmailinfo:""
	},
	created:function(){
		backMailObj=this;
		var url = '/admin/backMailJp/getBackMailInfo.html';
		$.ajax({ 
			url: url,
			type:'post',
			dataType:"json",
			data:{
				applicantId:applicantId
			},
			success: function(data){
				backMailObj.backmailinfo = data.backmailinfo
			}
		});
	}
});

//保存回邮信息
function save(){
	backMailObj.backmailinfo.id = $("#id").val();
	backMailObj.backmailinfo.applicantJPId = $("#applicantJPId").val();
	backMailObj.backmailinfo.applicantId = applicantId;
	backMailObj.backmailinfo.source = $("#source").val();
	backMailObj.backmailinfo.expressNum = $("#expressNum").val();
	backMailObj.backmailinfo.teamName = $("#teamName").val();
	backMailObj.backmailinfo.expressType = $("#expressType").val();
	backMailObj.backmailinfo.linkman = $("#linkman").val();
	backMailObj.backmailinfo.telephone = $("#telephone").val();
	backMailObj.backmailinfo.expressAddress = $("#expressAddress").val();
	backMailObj.backmailinfo.invoiceContent = $("#invoiceContent").val();
	backMailObj.backmailinfo.invoiceHead = $("#invoiceHead").val();
	backMailObj.backmailinfo.taxNum = $("#taxNum").val();
	backMailObj.backmailinfo.remark = $("#remark").val();
	var editdata = backMailObj.backmailinfo;
	$.ajax({
		url: '/admin/backMailJp/saveBackMailInfo.html',
		dataType:"json",
		data:editdata,
		type:'post',
		success: function(data){
			layer.closeAll('loading');
			closeWindow();
			parent.successCallBack(1);
		}
	});
}