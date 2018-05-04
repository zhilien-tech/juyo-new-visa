var staffid=GetQueryString('staffid');
var sessionid=GetQueryString('sessionid');
var flag=GetQueryString('flag');

//获取URL地址参数
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}

//返回上一级
function returnPage(){
	window.location.href='/appmobileus/USFilming.html?staffid='+ staffid+'&sessionid='+sessionid+'&flag='+flag;
}


//回显
function getImage(staffid){
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : 4,
			staffid : staffid,
		},
		dataType : "json",
		type : 'post',
		async:false,
		success : function(data) {
			if(data.query.length > 0){
				$(".homePage").remove();
				$(".vicePage").remove();
			console.log(data);
			for(var i=0;i<data.query.length;i++){
				var url=data.query[i].url;
				var num = "#"+data.query[i].mainid+data.query[i].sequence;
					sequence = data.query[i].sequence-1;
					num = "#"+data.query[i].mainid+sequence;
					var id=data.query[i].mainid+""+data.query[i].sequence;
					
					if(data.query[i].mainid == 1){
						/* 第一条主页 */
						if(data.query[i].sequence==1){
							$(".householdExpain").after('<div class="householdPage homePage" id="1" name="1" onclick="chooseImage(1,1)">'+
														'<span class="homePageTitle">户主页</span>'+
									 '<img src="img/camera.png" class="camera" />'+
									 '<img id="'+id+'" src="'+url+'" class="pageImg" />'+
									 '</div>')
						}else{
							$(".1").before('<div class="householdPage vicePage" id="2" name="1" onclick="chooseImage(2,1)">'+
									'<span class="homePageTitle">副页</span><img src="img/camera.png" class="camera" />'+
									'<img id="'+id+'" src="'+url+'" class="pageImg" />'+
									'</div>');
							}
					}else{
						/* 其他的页面 */
						if(data.query[i].sequence ==1){
							$(".addSetOfBtn").before(
									'<div class="household"><div class="householdPage homePage" id="'+data.query[i].sequence+'" name="'+data.query[i].mainid+'" onclick="chooseImage('+data.query[i].sequence+','+data.query[i].mainid+')">'+
														'<span class="homePageTitle">户主页</span>'+
									 '<img src="img/camera.png" class="camera" />'+
									 '<img id="'+id+'" src="'+url+'" class="pageImg" />'+
									 '</div>'+
									 
									 '<div class="addPage '+data.query[i].mainid+'" onclick="addPage(this)">'+
									 '<span class="homePageTitle">添加副页</span>'+
									 '<span class="plus">+</span>'+
									 '</div>'+
									 '</div>'
							)
						}else{
							var group = "."+data.query[i].mainid;
							//if(hasClass("data[i].mainid")){
								$(group).before('<div class="householdPage vicePage" id="'+data.query[i].sequence+'" name="'+data.query[i].mainid+'" onclick="chooseImage('+data.query[i].sequence+','+data.query[i].mainid+')">'+
										'<span class="homePageTitle">副页</span><img src="img/camera.png" class="camera" />'+
										'<img id="'+id+'" src="'+url+'" class="pageImg" />'+
										'</div>');
										/* '<div class="addPage '+data[i].mainid+'">'+
								 		'<span class="homePageTitle">添加副页</span>'+
								 		'<span class="plus">+</span>'+ 
								 		'</div>'*/
								//}
								
							}
					}
			}
			}
		}
	});
}

$(function(){
	getImage(staffid);
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

//点击上传
 function chooseImage(sequence,mainid){
	 var num = mainid+""+sequence;
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
					$("#"+num).attr("src",localIds[i]);
				}
			}

			images.localId = res.localIds;
			uploadImage(res.localIds,mainid,sequence);

		}
	});
};

var uploadImage = function(localIds,mainid,sequence) {
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
				
				uploadToQiniu(staffid,serverIdStr,mainid,sequence);

			}
		}
	});
};

//从微信服务器保存到七牛云服务器 
function uploadToQiniu(staffid,serverIds,mainid,sequence){

	$.ajax({
		type : "post",
		url : "/admin/weixinToken/wechatJsSDKNewploadToQiniu",
		dataType : "json",
		async : false,
		data:{
			staffId:staffid,
			mediaIds:serverIds,
			sessionid:sessionid,
			type:4,
			mainid:mainid,
			sequence:sequence
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

