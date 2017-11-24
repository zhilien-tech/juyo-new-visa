$(function(){
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
});

//回邮信息
function backMailInfos(){
	var backMails = [];
	$('.info-body-from').each(function(i){
		var infoLength = '';
		var backInfo = {};
		
		var datasour = $(this).find('[name=datasour]').val();
		if(datasour != 1){
			infoLength += datasour;
		}
		backInfo.datasour = datasour;
		
		var expressType = $(this).find('[name=expressType]').val();
		if(expressType != 1){
			infoLength += expressType;
		}
		backInfo.expressType = expressType;
		
		var expressAddress = $(this).find('[name=expressAddress]').val();
		infoLength += expressAddress;
		backInfo.expressAddress = expressAddress;
		
		var linkman = $(this).find('[name=linkman]').val();
		infoLength += linkman;
		backInfo.linkman = linkman;
		
		var telephone = $(this).find('[name=telephone]').val();
		infoLength += telephone;
		backInfo.telephone = telephone;
		
		var invoiceContent = $(this).find('[name=invoiceContent]').val();
		infoLength += invoiceContent;
		backInfo.invoiceContent = invoiceContent;
		
		var invoiceHead = $(this).find('[name=invoiceHead]').val();
		infoLength += invoiceHead;
		backInfo.invoiceHead = invoiceHead;
		
		var teamName = $(this).find('[name=teamName]').val();
		infoLength += teamName;
		backInfo.teamName = teamName;
		
		var expressNum = $(this).find('[name=expressNum]').val();
		infoLength += expressNum;
		backInfo.expressNum = expressNum;
		
		var taxNum = $(this).find('[name=taxNum]').val();
		infoLength += taxNum;
		backInfo.taxNum = taxNum;
		
		var remark = $(this).find('[name=remark]').val();
		infoLength += remark;
		backInfo.remark = remark;

		if(infoLength.length > 0){
			backMails.push(backInfo);
		}
	});
	
	return backMails;
}

//“+”号 回邮寄信息
function clearBackMailInfo(newDiv){
	newDiv.find('[name=datasour]').val(1);
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
}