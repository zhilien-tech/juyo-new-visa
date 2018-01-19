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
	if(flowChart == 1){
		$('#backmail_wrapper').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				linkman : {
					validators : {
						notEmpty : {
							message : '联系人不能为空'
						}
					}
				},
				telephone : {
					validators : {
						notEmpty : {
							message : '电话不能为空'
						}
					}
				},
				expressAddress : {
					validators : {
						notEmpty : {
							message : '回邮地址不能为空'
						}
					}
				}
			}
		});
	}else{
		$('#backmail_wrapper').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				teamName : {
					validators : {
						notEmpty : {
							message : '团队名称不能为空'
						}
					}
				},
				expressNum : {
					validators : {
						notEmpty : {
							message : '快递号不能为空'
						}
					}
				},
				linkman : {
					validators : {
						notEmpty : {
							message : '联系人不能为空'
						}
					}
				},
				telephone : {
					validators : {
						notEmpty : {
							message : '电话不能为空'
						}
					}
				},
				expressAddress : {
					validators : {
						notEmpty : {
							message : '回邮地址不能为空'
						}
					}
				}
			}
		});
		
	}

});

//保存回邮信息
function save(paramstatus){
	$('#backmail_wrapper').bootstrapValidator('validate');
	var bootstrapValidator = $("#backmail_wrapper").data('bootstrapValidator');
	// 执行表单验证 
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()){
		backMailObj.backmailinfo.id = $("#id").val();
		backMailObj.backmailinfo.orderProcessType = $("#orderProcessType").val();
		backMailObj.backmailinfo.orderId = $("#orderId").val();
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
		backMailObj.backmailinfo.invoiceAddress = $("#invoiceAddress").val();
		backMailObj.backmailinfo.invoiceMobile = $("#invoiceMobile").val();
		backMailObj.backmailinfo.taxNum = $("#taxNum").val();
		backMailObj.backmailinfo.remark = $("#remark").val();
		var editdata = backMailObj.backmailinfo;
		editdata.flowChart=$("#flowChart").val();
		$.ajax({
			url: '/admin/backMailJp/saveBackMailInfo.html',
			dataType:"json",
			data:editdata,
			type:'post',
			success: function(data){
				if(paramstatus == 1){
					layer.closeAll('loading');
					closeWindow();
					parent.successCallBack(1);
				}
			}
		});
		return true;
	}else{
		return false;
	}
}

//发送短信
function sendMail(){
	var status = save(2);
	if(status){
		var applicantJPId = $('#applicantJPId').val();
		$.ajax({
			url: '/admin/aftermarket/sendMailAndMessage.html',
			dataType:"json",
			data:{applicantid:applicantJPId},
			type:'post',
			success: function(data){
				layer.closeAll('loading');
				if(data.status == 200){
					closeWindow();
					parent.successCallBack(2);
				}else if(data.status == 500){
					layer.msg(data.msg);
				}
				//layer.msg('发送成功');
			}
		});
	}
}