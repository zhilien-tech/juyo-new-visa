var staffid=GetQueryString('staffid');
var sessionid=GetQueryString('sessionid');
var flag=GetQueryString('flag');

//获取URL地址参数
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}

//返回上一级，开始扫描
function returnPage(){
	//扫描正面
	$.ajax({
		type : "POST",//提交类型  
		url : '/admin/orderUS/IDCardRecognition.html',
		data : {
			staffid : staffid
		},
		success : function(obj) {
			
		}
	});
	//扫描背面
	$.ajax({
		type : "POST",//提交类型  
		url : '/admin/orderUS/IDCardRecognitionBack.html',
		data : {
			staffid : staffid
		},
		success : function(obj) {
			
		}
	});
	window.location.href='/appmobileus/USFilming.html?staffid='+ staffid+'&sessionid='+sessionid+'&flag='+flag;
}


//回显
function getImageFront(staffid){
	$.ajax({
		url : "/admin/mobileVisa/getIDcardphoto.html",
		data : {
			type : 3,
			staffid : staffid,
			status : 1
		},
		dataType : "json",
		type : 'post',
		success : function(data) {
			if(data != null){
				$(".uploadImgFace").attr("src",data.url);
			}
		}
	});
}
function getImageBack(staffid){
	$.ajax({
		url : "/admin/mobileVisa/getIDcardphoto.html",
		data : {
			type : 3,
			staffid : staffid,
			status : 2
		},
		dataType : "json",
		type : 'post',
		success : function(data) {
			if(data != null){
				$(".uploadImgBack").attr("src",data.url);
			}
		}
	});
}

$(function(){
	getImageFront(staffid);
	getImageBack(staffid);
	$.ajax({
		type : "post",
		url : "/admin/weixinToken/getJsApiTicket",
		dataType : "json",
		async : false,
		success : function(data) {
			var jsApiTicket = data.ticket;
			var url = location.href.split('#').toString();//url不能写死

			$.ajax({
				type : "post",
				url : "/admin/weixinToken/makeWXTicket",
				dataType : "json",
				async : false,
				data:{
					jsApiTicket:jsApiTicket,
					url:url
				},
				success : function(data) {
					wx.config({
						debug: false,//生产环境需要关闭debug模式
						appId: data.appid,//appId通过微信服务号后台查看
						timestamp: data.timestamp,//生成签名的时间戳
						nonceStr: data.nonceStr,//生成签名的随机字符串
						signature: data.signature,//签名
						jsApiList: [//需要调用的JS接口列表
							'chooseImage',
							'previewImage',
							'uploadImage'
							]
					});
				},
				error: function(xhr, status, error) {
					//alert(status);
					//alert(xhr.responseText);
				}
			});
		},
		error: function(xhr, status, error) {

		}
	})
});

wx.ready(function(){
	//页面加载时就调用相关接口
});

var images = {
		localId : [],
		serverId : []
};
$('.uploadIDFace').on('click', function() {
	images.serverId = [];//清空serverid集合
	wx.chooseImage({
		count : 1, // 默认9   
		sizeType : [ 'compressed' ], // 压缩图
		sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有   
		success : function(res) {
			$('.wxChooseImages ').remove();
			var localIds = res.localIds;
			if(localIds != ""){
				for(var i = 0;i<localIds .length;i++){
					//YEMIAN HUIXIAN
					$(".uploadImgFace").attr("src",localIds[i]);
				}
			}

			images.localId = res.localIds;
			uploadImage(res.localIds,1);

		}
	});
});
$('.uploadIDBack').on('click', function() {
	images.serverId = [];//清空serverid集合
	wx.chooseImage({
		count : 1, // 默认9   
		sizeType : [ 'compressed' ], // 压缩图
		sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有   
		success : function(res) {
			$('.wxChooseImages ').remove();
			var localIds = res.localIds;
			if(localIds != ""){
				for(var i = 0;i<localIds .length;i++){
					//YEMIAN HUIXIAN
					$(".uploadImgBack").attr("src",localIds[i]);
				}
			}
			
			images.localId = res.localIds;
			uploadImage(res.localIds,2);
			
		}
	});
});

var uploadImage = function(localIds,status) {
	var localId = localIds.pop();
	wx.uploadImage({
		localId : localId,
		isShowProgressTips : 1,
		success : function(res) {
			var serverId = res.serverId; // 返回图片的服务器端ID
			
			images.serverId.push(serverId);

			//其他对serverId做处理的代码
			if (localIds.length > 0) {
				uploadImage(localIds);

			}else if(localIds.length == 0 && images.serverId != ""){
				var serverId = images.serverId;
				var serverIdStr = "";
				for(var i = 0;i<serverId.length;i++){
					serverIdStr += serverId[i]+",";
				}
				
				uploadToQiniu(staffid,serverIdStr,status);

			}
		}
	});
};

//从微信服务器保存到七牛云服务器 
function uploadToQiniu(staffid,serverIds,status){

	$.ajax({
		type : "post",
		url : "/admin/weixinToken/wechatJsSDKUploadToQiniu",
		dataType : "json",
		async : false,
		data:{
			staffId:staffid,
			mediaIds:serverIds,
			sessionid:sessionid,
			type:3,
			status:status
		},
		success : function(data) {

		},
		error: function(xhr, status, error) {

		}
	});
}

//返回上一级
$(".savebutton").click(function(){
	returnPage();
});

