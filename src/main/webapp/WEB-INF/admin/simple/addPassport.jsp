<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>资料上传</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<style>
		.modal-header{position:fixed;top:0;left:0;width:100%;height:50px;line-height:50px;background:#FFF;z-index:10000;padding:0 15px}.heading{font-size:16px;line-height:28px}.btn-group-sm>.btn,.btn-sm{width:60px!important;font-size:12px!important;border-radius:3px!important}.btn-margin{margin-top:10px}.btn-right{margin-right:15px}
		.modal-body {
			padding: 20px 50px;
			height: 100%;
			margin-top: 50px;
			overflow-y: hidden;
		}
		.modal-content{
			box-shadow: none;
    		border: 0;
		}
		.rightNav {
			position: fixed;
			top: 15px;
			right: 0;
			z-index: 999;
			width: 40px;
			height: 100%;
			cursor: pointer;
		}
		.rightNav span {
			width: 24px;
			height: 24px;
			position: absolute;
			top: 50%;
			border-left: 4px solid #999;
			border-bottom: 4px solid #999;
			-webkit-transform: translate(0, -50%) rotate(-135deg);
			transform: translate(0, -50%) rotate(-135deg);
		}
		.wrap{
			overflow: hidden;
		}
		.qrcode-wrap .qrcode{
			float: left;
			width: 150px;
			height: 150px;
			margin-left: 50px;
    		margin-right: 55px;
		}
		.qrcode-wrap .tips{
			float: left;
			margin-left: 50px;
			line-height: 150px;
		}
		.photo-wrap .title {
			font-size: 16px;
			margin: 15px 0;
		}
		.photo-wrap .photo {
			position: relative;
			float: left;
			width: 257px;
			height: 162px;
			line-height: 162px;
			text-align: center;
			border: 1px solid #000;
		}
		.photo-wrap .tips{
			float: left;
			margin-top: 60px;
			margin-left: 50px;
		}
		.photo-wrap .photo .img{
			top: 0;
			left: 0;
			position: absolute;
			width: 100%;
			height: 100%;
		}
	</style>
</head>
<body>
	<div class="modal-content">
		<a id="toVisa" class="rightNav"><span></span></a>
		<form id="passportInfo">
			<input type="hidden" id="orderid" value="${obj.orderid }">
			<input type="hidden" id="applyid">
			<div class="modal-header">
				<span class="heading">资料上传</span>
				<input id="backBtn" type="button" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" />
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="wrap qrcode-wrap">
					<img class="qrcode" src="${obj.qrCode }">
					<div class="tips">微信扫描二维码上传识别</div>
				</div>
				<div class="wrap photo-wrap">
					<div class="title">护照首页</div>
					<div class="photo">
						等候上传..
						<img id="passurl" onclick="toUpperPhoto(this)" class="img" src=" ">
					</div>
					<div class="tips">资料要求：<br>拍摄的字体清晰可见、不要反光</div>
				</div>
				<div class="wrap photo-wrap">
					<div class="title">身份证</div>
					<div class="photo">
						等候上传..
						<img id="cardurl" onclick="toUpperPhoto(this)" class="img" src=" ">
					</div>
					<div class="tips">资料要求：<br>清晰拍摄在有效期内的身份证正反面</div>
				</div>
			</div>
		</form>
	</div>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/admin/common/utils.js"></script>
	<script>
		//const orderid 	  = '${obj.orderid}';
		//const applicantid = '${obj.applyid}';
		var userid = '${obj.userid}';
		console.log("userid:"+userid);
		const BASEURL 	  = 'wss://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}';
		const REDIRECTURL = '/admin/simple/updateApplicant.html?applicantid=' + $("#applyid").val() + '&orderid=' + $("#orderid").val();

		const socket = new Socket().connect(BASEURL);

		socket.onopen = () => {  
            console.log('socket Connection open..');  
		};
		
		socket.onclose = () => {
            console.log('socket Connection close..');  
		};
		
		socket.onmessage = (ev) => {
			console.log(ev);
			if (ev.data) {
				let ret = JSON.parse(ev.data);
				console.log(ret);
				if(ret.userid == userid){
					if(ret.orderid != ""){
						$("#orderid").val(ret.orderid);
					}
					if(ret.applyid != ""){
						$("#applyid").val(ret.applyid);
					}
					if(ret.passurl != ""){
						$("#passurl").attr("src",ret.passurl);
					}
					if(ret.applyurl != ""){
						$("#cardurl").attr("src",ret.applyurl);
					}
					 window.document.getElementById('orderid').value = ret.orderid;
					 window.document.getElementById('applyid').value = ret.applyid;
					 console.log($("#applyid").val());
					 console.log($("#orderid").val());
					 console.log("=============");
				}
				
			}
			console.log('socket Connection onmessage done..');
		};

		$('#backBtn').on('click', () => {
			layerFn.close(() => {
				socket.close();
			});
		});
		$('#addBtn').on('click', () => {
			$.ajax({
				type: 'POST',
				data: {
					applyid: $("#applyid").val(),
					orderid: $("#orderid").val()
				},
				url: '/admin/simple/hasApplyInfo.html',
				success: function (data) {
					layerFn.close(() => {
						socket.close();
					});
				}
			});
		});

		$('#toVisa').on('click', () => {
			$.ajax({
				type: 'POST',
				data: {
					applyid: $("#applyid").val(),
					orderid: $("#orderid").val()
				},
				url: '/admin/simple/hasApplyInfo.html',
				success: function (data) {
					window.parent.document.getElementById('orderid').value = data.orderid;
					console.log(data.orderid);
					console.log(data.applyid);
					window.document.getElementById('orderid').value = data.orderid;
					window.document.getElementById('applyid').value = data.applyid;
					socket.close();
					console.log($("#applyid").val());
					console.log($("#orderid").val());
					window.location.href = '/admin/simple/updateApplicant.html?applicantid=' + $("#applyid").val() + '&orderid=' + $("#orderid").val();
				}
			});
		});
		
		function toUpperPhoto(photo){
			var url = $(photo).attr("src");
			if(url != ""){
				window.open('/admin/pcVisa/toUpperPhoto.html?url='+url);
			}
		}
	</script>
</body>
</html>
