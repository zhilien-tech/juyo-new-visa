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

$(function(){
	//校验
	$('#backmail_wrapper').bootstrapValidator({
		message : '验证不通过',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			telephone : {
				validators : {
					/*regexp: {
						regexp: /^[1][34578][0-9]{9}$/,
						message: '电话号格式错误'
					}*/
				}
			}
		}
	});
	$('#backmail_wrapper').bootstrapValidator('validate');

});

//保存回邮信息
function save(){

	var bootstrapValidator = $("#backmail_wrapper").data('bootstrapValidator');
	// 执行表单验证 
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()){
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
}