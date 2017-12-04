/**
 * 需求变更，回邮信息去除加减号
 */
/*$(function(){
	//点击 蓝色加号图标 事件
	$('.add-btn').click(function(){
		var newDiv=$(this).parent().clone();//克隆标签模块
		$(this).parents('.info').append(newDiv);//添加克隆的内容
		clearBackMailInfo(newDiv);
		newDiv.find('.add-btn').remove();
		newDiv.append('<i class="remove-btn"></i>');
	});
	//点击 蓝色叉号图标 事件
	$(".info").on("click", ".remove-btn", function(){
		$(this).parent().remove();//删除 对相应的本模块
	});
	
	//如果有数据，隐藏添加回邮信息按钮；同时，设置最有一个为减号按钮
	
});

//“+”号 回邮寄信息
function clearBackMailInfo(newDiv){
	newDiv.find('[name=obmId]').val("");
	newDiv.find('[name=source]').val(1);
	newDiv.find('[name=expressType]').val(1);
	newDiv.find('[name=expressAddress]').val("");
	newDiv.find('[name=linkman]').val("");
	newDiv.find('[name=telephone]').val("");
	newDiv.find('[name=invoiceContent]').val("");
	newDiv.find('[name=invoiceHead]').val("");
	newDiv.find('[name=teamName]').val("");
	newDiv.find('[name=expressNum]').val("");
	newDiv.find('[name=taxNum]').val("");
	newDiv.find('[name=remark]').val("");
}*/

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