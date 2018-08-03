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
			background: rosybrown;
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
			display: none;
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
			<div class="modal-header">
				<span class="heading">资料上传</span>
				<input id="backBtn" type="button" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" />
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存"/>
			</div>
			<div class="modal-body">
				<div class="wrap qrcode-wrap">
					<div class="qrcode"></div>
					<div class="tips">微信扫描二维码上传识别</div>
				</div>
				<div class="wrap photo-wrap">
					<div class="title">护照首页</div>
					<div class="photo">
						等候上传..
						<div class="img"></div>
					</div>
					<div class="tips">资料要求：<br>拍摄的字体清晰可见、不要反光</div>
				</div>
				<div class="wrap photo-wrap">
					<div class="title">护照首页</div>
					<div class="photo">
						等候上传..
						<div class="img"></div>
					</div>
					<div class="tips">资料要求：<br>清晰拍摄在有效期内的身份证正反面</div>
				</div>
			</div>
		</form>
	</div>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
<<<<<<< HEAD
	<script src="${base}/admin/common/utils.js"></script>
	<script>
		const orderid 	  = '${obj.orderid}';
		const applicantid = '${obj.applicantid}';
		const BASEURL 	  = 'ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}';
		const REDIRECTURL = '/admin/simple/updateApplicant.html?applicantid=' + applicantid + '&orderid=' + orderid;

		const socket = new Socket().connect(BASEURL);

		socket.onopen = () => {  
            console.log('socket Connection open..');  
		};
		
		socket.onclose = () => {
            console.log('socket Connection close..');  
		};
		
		socket.onmessage = (ev) => {
			if (ev.data) {
				let ret = JSON.parse(ret);
				if (ret.applicantid == applicantid) {
					switch(ret.messagetype >> 0) {
						case 1:
							window.location.reload();
							break;
=======
	<script type="text/javascript" src="${base}/admin/common/commonjs.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript" src="${base}/admin/simple/validationZh.js?v=<%=System.currentTimeMillis() %>"></script>
	
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			var issuedOrganization = "${obj.passport.issuedOrganization }";
			var issuedOrganizationen = "${obj.passport.issuedOrganizationEn }";
			
			
			
			
			if(issuedOrganization == ""){
				$("#issuedOrganization").val("公安部出入境管理局");
			}
			if(issuedOrganizationen == ""){
				$("#issuedOrganizationEn").val("Ministry of Public Security");
			}
			
			//护照图片验证
			$('#passportInfo').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					passport : {
						trigger:"change keyup",
						validators : {
							stringLength: {
		                   	    min: 1,
		                   	    max: 9,
		                   	    message: '护照号不能超过9位'
		                   	},
		                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
								url: '${base}/admin/orderJp/checkPassport.html',
								message: '护照号已存在，请重新输入',//提示消息
								delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
								type: 'POST',//请求方式
								//自定义提交数据，默认值提交当前input value
								data: function(validator) {
									return {
										passport:$('#passport').val(),
										adminId:$('#id').val(),
										orderid:$('#orderid').val()
									};
								}
							}
						}
					},
					firstName : {
						validators : {
							notEmpty : {
								message : '姓不能为空'
							}
						}
					},
					lastName : {
						validators : {
							notEmpty : {
								message : '名不能为空'
							}
						}
					},
					firstNameEn: {
						trigger:"change keyup",
						validators : {
							regexp: {
	                            regexp: /^[\/a-zA-Z0-9_]{0,}$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					lastNameEn: {
						trigger:"change keyup",
						validators : {
							regexp: {
								// regexp: /\/{1}[a-zA-Z]+$/,
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					birthAddressEn: {
						trigger:"change keyup",
						validators : {
							regexp: {
	                            regexp: /\/{1}[a-z\s+A-Z]+$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					issuedPlaceEn: {
						trigger:"change keyup",
						validators : {
							regexp: {
	                            regexp: /\/{1}[a-z\s+A-Z]+$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					lastName : {
						validators : {
							regexp: {
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					issuedPlaceEn: {
						trigger:"change keyup",
						validators : {
							regexp: {
								regexp: /^[\/a-zA-Z0-9_]{0,}$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
>>>>>>> refs/remotes/origin/dev
					}
				}
			}
			console.log('socket Connection onmessage done..');
		};

		$('#backBtn').on('click', () => {
			layerFn.close(() => {
				socket.close();
			});
		});
<<<<<<< HEAD
=======
		//连接websocket
		/* connectWebSocket();
		function connectWebSocket(){
			 if ('WebSocket' in window){  
	            console.log('Websocket supported');  
	            socket = new WebSocket('ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');   
>>>>>>> refs/remotes/origin/dev

<<<<<<< HEAD
		$('#toVisa, #addBtn').on('click', () => {
			socket.close();
			window.location.href = REDIRECTURL;
=======
	            console.log('Connection attempted');  

	            socket.onopen = function(){  
	                 console.log('Connection open!');  
	     
	             };

	            socket.onclose = function(){  
	                console.log('Disconnecting connection');  
	            };

	            socket.onmessage = function (evt){
	                  var received_msg = evt.data;  
	                  var applicantid = '${obj.applicantid}';
	                  var orderid = '${obj.orderid}';
	                  if(received_msg){
		                  var receiveMessage = JSON.parse(received_msg);
		                  if(receiveMessage.applicantid == applicantid){
		                	  if(receiveMessage.messagetype == 2){
			                	  window.location.reload();
		                	  }else if(receiveMessage.messagetype == 1){
		                		  window.location.href = '/admin/simple/updateApplicant.html?applicantid='+applicantid+'&orderid='+orderid;
		                	  }
		                  }
	                  }
	                  console.log('message received!');  
	                  //showMessage(received_msg);  
	             };  

	          } else {  
	            console.log('Websocket not supported');  
	          }  
		} */
		
		
		//把dataUrl类型的数据转为blob
		function dataURLtoBlob(dataurl) {
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
					n);
			while (n--) {
				u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([ u8arr ], {
				type : mime
			});
		}
		
		//保存
		function save(status){
			
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			var passportInfo = $("#passportInfo").serialize();
			var id = '${obj.applicantid}';
			var orderid = '${obj.orderid}';
			layer.load(1);
			
			ajaxConnection();
			var count = 0;
			function ajaxConnection(){
				$.ajax({
					type: 'POST',
					//async : false,
					data : passportInfo,
					url: '${base}/admin/simple/saveEditPassport.html',
					success :function(data) {
						layer.closeAll("loading");
						console.log(JSON.stringify(data));
						if(data.msg){
							layer.msg(data.msg);
						}else{
							/* var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							layer.close(index); */
							if(status == 2){
								//socket.onclose();
								window.location.href = '/admin/simple/updateApplicant.html?applicantid='+id+'&orderid='+orderid;
							}
							if(status == 1){
								parent.successCallBack(1);
								closeWindow();
							}
						}
					},error:function(error,XMLHttpRequest,status){
						console.log("error:",error);
						console.log("XMLHttpRequest:",error);
						console.log("status:",error);
						if(status=='timeout'){//超时,status还有success,error等值的情况
						 　　　　　//ajaxTimeOut.abort(); //取消请求
							count++;
						　　　ajaxConnection();
							var index = layer.load(1, {content:'第'+count+'次重连中...<br/>取消重连请刷新！',success: function(layero){
								layero.find('.layui-layer-content').css({
									'width': '140px',
									'padding-top': '50px',
								    'background-position': 'center',
									'text-align': 'center',
									'margin-left': '-55px',
									'margin-top': '-10px'
								});
								
								
							}});
						　}
					},timeout:10000
				});
				
			}
			
		}
		
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.successCallBack();
		}
		$(function(){
			var passport = $("#passportUrl").val();
			if(passport != ""){
				$("#uploadFile").siblings("i").css("display","block");
			}else{
				$("#uploadFile").siblings("i").css("display","none");
			}
			
>>>>>>> refs/remotes/origin/dev
		});

	</script>
</body>
</html>
