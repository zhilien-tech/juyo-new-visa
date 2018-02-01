$(function() {
	$("#addBtn").attr('disabled', true);
	$(document).on("click",".tableTr",function(){
		var sharetype = $("#shareType").val();
		if(sharetype == 2){//单独分享
			/* if($(this).hasClass("trColor")){
						$(this).removeClass("trColor");
						if($("#datatableId tbody tr").hasClass("trColor")){
							$("#addBtn").attr('disabled', false);
						}else{
							$("#addBtn").attr('disabled', true);
						}
					}else{
						$(this).addClass("trColor");
						$("#datatableId tbody tr").each(function(){
							if($(this).hasClass("trColor")){
								$("#addBtn").attr('disabled', false);
							}
						});
					} */
		}else if(sharetype == 1){//统一联系人
			$(this).addClass("trColor").siblings("tr").removeClass("trColor");
			if($(this).hasClass("trColor")){
				$("#addBtn").attr('disabled', false);
			}else{
				$("#addBtn").attr('disabled', true);
			}
		}
	});

	$("#shareType").change(function(){
		var shareType = $(this).val();
		if(shareType == 1){//统一分享
			$("#datatableId tbody tr").each(function(){
				if($(this).hasClass("trColor")){
					$(this).removeClass("trColor");
					$("#addBtn").attr('disabled', true);
				}
			});
		}else{
			$("#datatableId tbody tr").each(function(){
				if(!$(this).hasClass("trColor")){
					$(this).addClass("trColor");
					$("#addBtn").attr('disabled', false);
				}
			});
		}
	});

});

//vue表格数据对象
var _self;
new Vue({
	el: '#datatableId',
	data: {shareInfo:""},
	created:function(){
        _self=this;
        $.ajax({ 
        	url: '/admin/orderJp/getShare.html',
        	data:{orderid:orderid},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.shareInfo = data;
        		//console.log(JSON.stringify(_self.shareInfo));
          	}
        });
    }
});

//分享
function save(){
	var sharetype = $("#shareType").val();
	var applicantMainId,applicantId,name,telephone,email;

	if(sharetype == 1){//统一联系人
		$("#datatableId tbody tr").each(function(){
			if($(this).hasClass("trColor")){
				applicantId = $(this).children().eq(0).html();
				telephone = $(this).children().eq(2).html();
				email = $(this).children().eq(3).html();
				if(email == "" || telephone == ""){
					//$("#backBtn").click();
					//window.location.href = '/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email;
					layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '100%'],
						content:'/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email,
						success : function(index, layero){

							var iframeWin = window[index.find('iframe')[0]['name']]; 
						}
					}); 
				}else{
					layer.load(1);
					$.ajax({ 
						url: '/admin/orderJp/sendEmailUnified',
						type:'post',
						data:{
							orderid:orderid,
							applicantid:applicantId
						},
						success: function(data){
							layer.closeAll('loading');
							layer.msg("分享成功", {
								time: 1000,
								end: function () {
									var index = parent.layer.getFrameIndex(window.name);
									parent.layer.close(index);
								}
							});
						}
					});
				}
			}
		});
	}else{
		var flag = 0;
		var trcount = 0;
		var applyId = "";
		$("#datatableId tbody tr").each(function(){
			if($(this).hasClass("trColor")){
				applicantId = $(this).children().eq(0).html();
				trcount++;
				applyId += applicantId + ',';
			}
		});
		$("#datatableId tbody tr").each(function(){
			if($(this).hasClass("trColor")){
				applicantId = $(this).children().eq(0).html();
				name = $(this).children().eq(1).html();
				telephone = $(this).children().eq(2).html();
				email = $(this).children().eq(3).html();
				if(email == "" || telephone == ""){
					//window.location.href = '/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email;
					layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '100%'],
						content:'/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email
					}); 
				}else{
					flag++;
					if(flag == trcount){
						layer.load(1);
						$.ajax({ 
							url: '/admin/orderJp/sendEmail',
							//async:false,
							type:'post',
							data:{
								orderid:orderid,
								applicantid:applyId
							},
							success: function(data){
								//flag++;
								//判断选中的是否都已发送邮件
								if(data.sendResult == "success"){
									layer.closeAll('loading');
									layer.msg("分享成功", {
										time: 1000,
										end: function () {
											var index = parent.layer.getFrameIndex(window.name);
											parent.layer.close(index);
										}
									});
								}
							}
						});
					}
				}
			}
		});
	}
}

//返回 
function closeWindow() {
	parent.successCallBack(4);
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

function closeSelf(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

function successCallBack(status){
	if(status == 1){
		//layer.msg('修改成功');
	}
	$.ajax({ 
    	url: '/admin/orderJp/getShare.html',
    	data:{orderid:orderid},
    	dataType:"json",
    	type:'post',
    	success: function(data){
    		_self.shareInfo = data;
    		console.log(JSON.stringify(_self.shareInfo));
      	}
    });
	parent.successCallBack(4);
	//save();
	//parent.location.reload();
}

function cancelCallBack(status){
	//$("#cancel").click();
	successCallBack(1);
}