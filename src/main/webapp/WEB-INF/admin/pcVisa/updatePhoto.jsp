<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>拍摄资料</title>
<link rel="stylesheet" href="/references/public/dist/newvisacss/css/photograph.css" />
</head>
<body>
	<div class="head">
		<span>拍摄资料</span>
		<input id="staffid" type="hidden" value="${obj.basicInfo.id }">
		<input id="passportId" type="hidden" value="${obj.passportId }">
		<div class="btnGroup">
			<a class="btnSave" onclick="savePhoto()">保存</a> <a class="btnCancel" onclick="closeWindow()">取消</a>
		</div>
	</div>
	
	<a id="toBase" class="rightNav" onclick="passportBtn();">
		<span></span>
	</a>
	<div class="topHide"></div>
	<div class="section">
		<div class="QRCode">
			<div class="explain">微信扫描二维码上传识别</div>
			<div class="scan">
				<img src="${obj.encodeQrCode }"  width="100%" height="auto"/>
			</div>
		</div>
		<!--二寸免冠照-->
		<div class="photo">
			<div class="sectionHead">照片</div>
			<div class="explain">
				<span>二寸免冠照片注意事项</span> <span>1.白底</span> <span>2.摘掉帽子</span> <span>3.漏出耳朵</span>
			</div>
			<div class="samplePhoto">
				<img src="${base}/references/public/dist/newvisacss/img/picture.png" />
			</div>
			<div id="uploadPhoto" class="uploadPhoto" >
				<div>上传</div>
				<img id="twonichphoto" class="loadImg" src=""/>
				<i class="delete" onclick="deleteApplicantFrontImg();"></i>
				<%-- <c:if test="${not empty obj.twoinchphoto }">
					<img src="${obj.twoinchphoto }" class="loadImg" width="100%"
						height="100%" />
				</c:if> --%>
			</div>
			<input id="1" type="file" class="publicFile uploadFileImg" name="" />
		</div>
		<!--护照首页-->
		<div class="passport">
			<div class="sectionHead">护照首页</div>
			<div class="explain"></div>
			<div class="samplePassport">
				<img src="${base}/references/public/dist/newvisacss/img/passport.png" />
			</div>
			<div class="uploadPassport alignment">
				<div>上传</div>
				<img id = "huzhao" class="transverseImg"/>
			</div>
			<input type="file" class="publicFile" name="" />
		</div>
		<!--旧护照-->
		<div class="passport">
			<div class="sectionHead">旧护照</div>
			<div class="explainLeft"></div>
			<div class="imgInfoRight">
				<div class="samplePassportImg">
					<img src="${base}/references/public/dist/newvisacss/img/passport.png" />
				</div>
				<div class="beforeInfo"></div>
				<div class="uploadPassports oldpassports alignment" >
					<div>上传</div>
					<img id="oldhuzhao" class="transverseImg" />
				</div>
				<input type="file" class="publicFile" name="" />
			</div>
			
		</div>
		<!--身份证-->
		<div class="passport">
			<div class="sectionHead">身份证</div>
			<div class="explainLeft"></div>
			<div class="imgInfoRight">
				<div class="samplePassportImg">
					<img src="${base}/references/public/dist/newvisacss/img/passport.png" />
				</div>
				<!-- <div class="beforeIDInfo"></div> -->
				<div class="uploadPassports alignment" >
					<div>上传</div>
					<img id="card" src="" class="transverseImg" >
				</div>
				<div class="uploadPassports alignment" >
					<div>上传</div>
					<img id="cardBack" src="" class="transverseImg" >
				</div>
			</div>	
		</div>
		<!--户口本-->
		<div class="huKouBook">
			<div class="sectionHead">户口本</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeHuKouInfo"></div> -->
				<div class="uploadReleases" >
					<div>户主页</div>
					<img id="household" class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>		
		</div>
		<!--房产证-->
		<div class="huKouBook">
			<div class="sectionHead">房产证</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeHouseInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img id="housecard" class="longitudinal"/>
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>			
		</div>
		<!--婚姻状况-->
		<div class="huKouBook">
			<div class="sectionHead">婚姻状况</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<div class="uploadReleases" >
					<div>上传</div>
					<img id="marray" class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>	
		</div>
		<!--银行流水-->
		<div class="huKouBook">
			<div class="sectionHead">银行流水</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeBankInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img class="longitudinal"  />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>	
		</div>
		<!--在职证明-->
		<div class="huKouBook">
			<div class="sectionHead">在职证明</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeJobInfo"></div> -->
				<div class="uploadReleases">
					<div>上传</div>
					<img id="jobCertificate" class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>	
		</div>
		<!--营业执照或机构代码证-->
		<div class="huKouBook">
			<div class="sectionHead">营业执照或机构代码证</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeOZCardInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img id="business" class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>
		</div>
		<!--行驶证-->
		<div class="huKouBook">
			<div class="sectionHead">行驶证</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeDriverInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img id="drive" class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>
		</div>
		<!--过期美签-->
		<div class="huKouBook">
			<div class="sectionHead">过期美签</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeOverdueInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img class="longitudinal" />
				</div>
				<input type="file" class="publicFile" name="" multiple />
			</div>
		</div>
		<!--美国出签-->
		<div class="huKouBook">
			<div class="sectionHead">美国出签</div>
			<div class="explain"></div>
			<div class="imgInfoRight">
				<div class="sampleReleaseImg">
					<img src="${base}/references/public/dist/newvisacss/img/hukouBook.png" />
				</div>
				<!-- <div class="beforeSignInfo"></div> -->
				<div class="uploadReleases" >
					<div>上传</div>
					<img class="longitudinal" />
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
	$(function() {
		var staffid = $("#staffid").val();
		twonichphoto(staffid,13);
		huzhao(staffid,1);
		oldhuzhao(staffid,2);
		card(staffid,3);
		marray(staffid,6);
		business(staffid,9);
		drive(staffid,10);
		jobCertificate(staffid,8);
		housecard(staffid,5);
		household(staffid,4);
		$(".uploadPhoto").click(function(){
			$(".uploadFileImg").click();
			$(".uploadFileImg").change(function(){
				layer.load(1,{
					shade : "#000"
				});
				var that = this;
	            lrz(this.files[0]).then(function (rst) {
	                /* $('.bacimg2').attr('src',rst.base64);*/
	                $('#upload').hide(); 
	                // 处理成功会执行
	                sourceSize = (that.files[0].size / 1024).toFixed(2);
	                resultSize = (rst.fileLen / 1024).toFixed(2);
	                scale = parseInt(100 - (resultSize / sourceSize * 100));
	                rst.formData.append('image',rst.file);
//	                alert(rst);
//	                alert(JSON.stringify(rst.formData));
//	                alert(rst.file);
//	                alert('sourceSize:'+sourceSize+' resultSize:'+resultSize+' scale:'+scale);
	                uploadPositive(rst,rst.formData,staffid); 
	            }).catch(function (err) {
	                console.log(err);
	            });
			});
		})
	});
	//连接websocket
	connectWebSocket();
	function connectWebSocket() {
		if ('WebSocket' in window) {
			console.log('Websocket supported');
			socket = new WebSocket(
					'ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');

			console.log('Connection attempted');

			socket.onopen = function() {
				console.log('Connection open!');
				//setConnected(true);  
			};

			socket.onclose = function() {
				console.log('Disconnecting connection');
			};

			socket.onmessage = function(state) {
				
				console.log("state");
				console.log(state);
				console.log(state.data);
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
