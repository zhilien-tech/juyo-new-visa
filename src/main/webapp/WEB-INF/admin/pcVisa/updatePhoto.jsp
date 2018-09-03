<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>拍摄资料</title>
<link rel="stylesheet" href="/references/public/dist/newvisacss/css/photograph.css?v='20180510'" />
<style>
	.photo{
		overflow: hidden;
	}
	.samplePhoto{
		margin-right: 7%;
	}
	.explain{
		width: auto;
		float: right;
		font-size: 12px;
	}
	.QRCode{
		font-size: 12px;
		height: 200px;
		line-height: 200px;
	}
	.QRCode .text{float: left;margin-left: 40px;}
	.QRCode .scan{
		margin-top: 0;
		margin-left: 70px;
	}
	.sectionHead{
		margin-top: 20px;
	}
	.imgInfoRight{
		width: 100%;
		float: left;
	}
</style>
</head>
<body>
	<div class="head">
		<span>拍摄资料</span>
		<input id="staffid" type="hidden" value="${obj.staffid }">
		<input id="passportId" type="hidden" value="${obj.passportId }">
		<div class="btnGroup">
			<a class="btnSave" onclick="savePhoto()">保存</a> <a class="btnCancel" onclick="closeWindow()">取消</a>
		</div>
	</div>
	
	<a id="toBase" class="rightNav" onclick="passportBtn();">
		<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';color:#3c8dbc;">第二步</i>
		<span></span>
	</a>
	<div class="topHide"></div>
	<div class="section">
		<div class="dislogHide"></div>
		<div class="QRCode">
			<span class="text">微信扫描二维码上传识别</span>
			<img class="scan" src="${obj.encodeQrCode }"  width="100%" height="auto"/>
		</div>
		<!--二寸免冠照-->
		<div class="photo">
			<div class="sectionHead">照片</div>
			<div class="samplePhoto">
				<img class="sampleImg" src="${base}/references/public/dist/newvisacss/img/icon-photo.jpg" />
			</div>
			<div id="uploadPhoto" class="uploadPhoto" >
				<div>上传</div>
				<img id="twonichphoto" onclick="toUpperPhoto(this)" class="loadImg" src=""/>
				<!-- <i class="delete" onclick="deleteApplicantFrontImg();"></i> -->
				<%-- <c:if test="${not empty obj.twoinchphoto }">
					<img src="${obj.twoinchphoto }" class="loadImg" width="100%"
						height="100%" />
				</c:if> --%>
			</div>
			<div class="explain">
				<span>资料要求：</span> 
				<span>1.白底，51×51mm</span> 
				<span>2.拍摄时注意光线均匀，不要佩戴眼镜，露出双耳</span> 
				<span>3.请用纯色墙做背景，避免衣服与背景色相同</span>
				<span>4.手机拍摄的照片会经过程序检测，但最终结果由签</span>
				<span>5.证专家人工审核，请严格根据要求拍摄</span>
			</div>
			<!-- <input type="file" id="uploadFileImg" class="publicFile uploadFileImg" name="uploadFileImg" /> -->
		</div>
		<!--护照首页-->
		<div class="passport">
			<div class="sectionHead">护照首页</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<img class="sampleImg" src="${base}/references/public/dist/newvisacss/img/icon-visa.jpg" />
			</div>
			<div class="uploadPassport alignment">
				<div>上传</div>
				<img id = "huzhao" onclick="toUpperPhoto(this)" class="transverseImg" src=""/>
			</div>
			<div class="explain">
				<span>资料要求：</span> 
				<span>拍摄的字体清晰可见、不要反光</span> 
			</div>
			<input type="file" class="publicFile" name="" />
		</div>
		
		<!--户口本-->
		<div class="huKouBook">
			<div class="sectionHead">户口本</div>
			<!-- 首页 -->
			<div class="imgInfoRight">
				<div class="sampleReleaseImg householdBack">
					<img class="sampleImg" src="${base}/references/public/dist/newvisacss/img/icon-hold.jpeg" />
				</div>
				<div class="uploadReleases" style="margin-right: 10px;">
					<div>首页</div>
					<img id="household" onclick="toUpperPhoto(this)" class="longitudinal" src=""/>
				</div>
				<div class="explain" style="margin-right: 0;">
					<span>资料要求：</span> 
					<span>1.家庭户口的：拍摄整本户口本的所有页</span> 
					<span>2.集体户口的：拍摄申请人本人页</span>
					<span>3.要保证照片上的字体清晰可见，露出四个边角，不要压边拍摄</span>
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>		
			<!-- 户主页 -->
			<div class="imgInfoRight">
				<div class="sampleReleaseImg householdBack">
					<img class="sampleImg" src="${base}/references/public/dist/newvisacss/img/icon-hold.jpeg" />
				</div>
				<div class="uploadReleases" style="margin-right: 10px;">
					<div>户主页</div>
					<img id="household" onclick="toUpperPhoto(this)" class="longitudinal" src=""/>
				</div>
				
				<input type="file" class="publicFile" name="" multiple />
			</div>		
			<!-- 本人页 -->
			<div class="imgInfoRight">
				<div class="sampleReleaseImg householdBack">
					<img class="sampleImg" src="${base}/references/public/dist/newvisacss/img/icon-hold.jpeg" />
				</div>
				<div class="uploadReleases" style="margin-right: 10px;">
					<div>本人页</div>
					<img id="household" onclick="toUpperPhoto(this)" class="longitudinal" src=""/>
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>		
		</div>
	</div>
</body>
<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
<script src="/references/common/js/layer/layer.js"></script>
<script type="text/javascript" src="/admin/common/commonjs.js"></script>
<script src="/appmobileus/js/jquery-1.10.2.js"></script>
<script src="/appmobileus/js/lrz.bundle.js"></script>
<script src="/admin/pcVisa/getphoto.js"></script>
<script>
	var passportId = $("#passportId").val();
	var staffid = '${obj.staffid}';
	var usertype = '${obj.userType}';
	var isDisable = '${obj.isDisable}';
	$(function() {
		//页面不可编辑
		if(isDisable == 1){
			$(".section").attr('readonly', true);
			$(".dislogHide").show();
			$(".btnSave").hide();
		}
		
		twonichphoto(staffid,13);
		chuqian(staffid,12);
		huzhao(staffid,1);
		oldhuzhao(staffid,2);
		card(staffid,3);
		marray(staffid,6);
		business(staffid,9);
		drive(staffid,10);
		jobCertificate(staffid,8);
		housecard(staffid,5);
		household(staffid,4);
		bankflow(staffid,7);
		oldsigned(staffid,11);
		
		$(".uploadPhoto").click(function(){
			$("#uploadFileImg").click();
			$("#uploadFileImg").change(function(){
				layer.load(1,{
					shade : "#000"
				});
				var that = this;
	            lrz(this.files[0])
	            	.then(function (rst) {
	                /* $('.bacimg2').attr('src',rst.base64);*/
	                $('#upload').hide(); 
	                // 处理成功会执行
	                sourceSize = (that.files[0].size / 1024).toFixed(2);
	                resultSize = (rst.fileLen / 1024).toFixed(2);
	                scale = parseInt(100 - (resultSize / sourceSize * 100));
	                rst.formData.append('uploadFileImg',rst.file);
	                uploadPositive(rst,rst.formData,staffid); 
	            }).catch(function (err) {
	                console.log(err);
	            });
			});
		})
	});
	
	function toUpperPhoto(photo){
		var url = $(photo).attr("src");
		if(url != ""){
			window.open('/admin/pcVisa/toUpperPhoto.html?url='+url);
		}
	}
	
	//连接websocket
	connectWebSocket();
	function connectWebSocket() {
		if ('WebSocket' in window) {
			console.log('Websocket supported');
			socket = new WebSocket(
					'wss://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');

			console.log('Connection attempted');

			socket.onopen = function() {
				console.log('Connection open!');
				//setConnected(true);  
			};

			socket.onclose = function() {
				console.log('Disconnecting connection');
			};

			socket.onmessage = function(state) {
				if (state.data==200) {
					window.location.reload();
				}
				console.log('message received!');
				//showMessage(received_msg);  
			};

		} else {
			console.log('Websocket not supported');
		}
	}
	
	function passportBtn(){
		saveApplicant(2);
	}
</script>
</html>
