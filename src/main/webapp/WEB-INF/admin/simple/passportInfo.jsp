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
	<title>护照信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css?v=<%=System.currentTimeMillis() %>">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simplePassportInfo.css?v=<%=System.currentTimeMillis() %>">
</head>
<body>
	<div class="modal-content">
		<a id="toVisa" class="rightNav" onclick="applyBtn();">
			<span></span>
		</a>
		<form id="passportInfo">
			<div class="modal-header">
				<span class="heading">护照信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(1);" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-main"></div>
			<div class="modal-body">
			<div class="ipt-info">
				</div>
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						<div class="info-QRcode"> <!-- 二维码 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div><!-- end 二维码 -->
						
						<div class="info-imgUpload front has-error" id="borderColor"><!-- 护照 -->
							<div class="col-xs-6 widthBig">
							<div class="form-group" style="margin-top: 0!important;">
								<div class="cardFront-div">
									<span></span>
									<input id="passportUrl" name="passportUrl" type="hidden" value="${obj.passport.passportUrl }"/>
									<!-- <input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/> -->
									<img id="sqImg" alt="" src="${obj.passport.passportUrl }" >
									<!-- <i class="delete" onclick="deleteApplicantFrontImg();"></i> -->
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;margin:-20px 0 0 8px !important;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="passportUrl" data-bv-result="IVVALID" style="display: none;">护照必须上传</small>
						</div>
						
					</div>
						
					<div class="col-sm-7 padding-right-0">
						<div class="row">
						<!-- 姓/拼音 -->
							<div class="col-sm-10 col-sm-offset-1 padding-right-0">
									<div class="form-group" style="position:relative;">
									<label><span>*</span>姓/拼音</label> 
									
									<input id="firstName"
										name="firstName" type="text" class="form-control input-sm " tabIndex="1" autocomplete="off"
										placeholder=" " value="${obj.passport.firstName }" />
										
										<input type="text" id="firstNameEn" name="firstNameEn"  autocomplete="off" style="position:absolute;top:32px;border:none;left:150px;" value="${obj.firstNameEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
									<input type="hidden" id="id" name="id" value="${obj.passport.id }"/>
									<input type="hidden" id="OCRline1" name="OCRline1" value="">
									<input type="hidden" id="OCRline2" name="OCRline2" value="">
									<input type="hidden" id="applicantid" name="applicantid" value="${obj.applicantid }"/>
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									
									<!-- <i class="bulb"></i> -->
							</div>
						</div>
						<!-- end 姓/拼音 -->
					<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-10 col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;">
									<label><span>*</span>名/拼音</label> <input id="lastName"
										name="lastName" type="text" class="form-control input-sm" tabIndex="2" autocomplete="off"
										placeholder=" " value="${obj.passport.lastName }" />
										<input type="text" id="lastNameEn" autocomplete="off" style="position:absolute;top:32px;border:none;left:150px;" name="lastNameEn" value="${obj.lastNameEn }"/>

									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 名/拼音 -->
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照号</label>
									
									<input 
										id="passport" 
										name="passport" 
										type="text" 
										class="form-control input-sm" 
										autocomplete="off" 
										maxlength="9" 
										tabIndex="3" 
										value="${obj.passport.passport }"
									/>
									
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 类型/护照号 -->
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<label><span>*</span>性别</label>
									<select class="form-control input-sm selectHeight" id="sex" name="sex" tabIndex="4">
											<option value="男" ${obj.passport.sex == "男"?"selected":"" }>男</option>
										<option value="女" ${obj.passport.sex == "女"?"selected":"" }>女</option>
									</select>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<input id="sexEn" class="form-control input-sm" autocomplete="off" name="sexEn" tabIndex="5" type="text" value="${obj.passport.sexEn }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
							 	<div class="form-group groupWidth" style="position:relative;">
									<label><span>*</span>出生地点/拼音</label>
									<input id="birthAddress" name="birthAddress" autocomplete="off"  type="text" class="form-control input-sm " tabIndex="6" value="${obj.passport.birthAddress }"/>
									<input id="birthAddressEn" name="birthAddressEn" style="position:absolute;top:30px;border:0px;left:80px; width:120px;" type="text"  placeholder=" " value="${obj.passport.birthAddressEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 性别/出生地点 拼音 -->
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期</label>
									<input id="birthday" name="birthday" type="text" autocomplete="off" class="form-control input-sm" tabIndex="7" value="${obj.birthday}"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth" style="position:relative;">
									<label><span>*</span>签发地点/拼音</label>
									<input id="issuedPlace" name="issuedPlace" autocomplete="off" type="text" class="form-control input-sm " tabIndex="8" value="${obj.passport.issuedPlace }"/>
									<input id="issuedPlaceEn" name="issuedPlaceEn" type="text" style="position:absolute;top:30px;border:0px;left:80px;width:120px;" placeholder=" " value="${obj.passport.issuedPlaceEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发日期</label>
									<input id="issuedDate" name="issuedDate"  autocomplete="off" type="text" class="form-control input-sm" tabIndex="9" value="${obj.issuedDate }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validType" class="form-control input-sm selectHeight" tabIndex="10">
									<c:forEach var="map" items="${obj.passportType}">
										<option value="${map.key}" ${map.key == obj.passport.validType?'selected':'' }>${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>有效期至</label>
									<input id="validEndDate" name="validEndDate" autocomplete="off" type="text" class="form-control input-sm" tabIndex="11" value="${obj.validEndDate }"/>
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row"><!-- 签发机关 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发机关</label>
									<input id="issuedOrganization" name="issuedOrganization" type="text" autocomplete="off" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganization }"/>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group groupWidth">
									<label><span>*</span>Exit & Entry Administration</label>
									<input id="issuedOrganizationEn" name="issuedOrganizationEn" type="text" autocomplete="off" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganizationEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发机关 -->
						
						<!-- <div class="row">
						</div> -->
					</div>	
						
				</div>
			</div>
			<!-- 订单流程枚举 -->
			<input id="orderProcessType" name="orderProcessType" type="hidden" value="${obj.orderProcessType }">
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>

	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
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
	                            regexp: /\/{1}[a-zA-Z]+$/,
	                            message: '拼音中不能包含汉字或其他特殊符号'
	                        },
						}
					},
					lastName : {
						validators : {
							notEmpty : {
								message : '名不能为空'
							}
						}
					}
				}
			});

			$('#passportInfo').bootstrapValidator('validate');

			

			
			
			var remark = $("#passRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			}
			
			if($("#sex").val() == "男"){
				$("#sexEn").val("M");
			}else{
				$("#sexEn").val("F");
			}
			
			$("#issuedDate").change(function(){
				if($("#issuedDate").val() != ""){
					if($("#validType").val() == 1){
						$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10));
					}else{
						$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5));
					}
					
				}
			});
		});
		//连接websocket
		connectWebSocket();
		function connectWebSocket(){
			 if ('WebSocket' in window){  
	            console.log('Websocket supported');  
	            socket = new WebSocket('ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');   

	            console.log('Connection attempted');  

	            socket.onopen = function(){  
	                 console.log('Connection open!');  
	                 //setConnected(true);  
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
		}
		
		//护照上传,扫描
		
		/* $('#uploadFile').change(function() {
			var layerIndex = layer.load(1, {
				shade : "#000"
			});
			$("#addBtn").attr('disabled', true);
			$("#updateBtn").attr('disabled', true);
			var file = this.files[0];
			var reader = new FileReader();
			reader.onload = function(e) {
				var dataUrl = e.target.result;
				var blob = dataURLtoBlob(dataUrl);
				var formData = new FormData();
				formData.append("image", blob, file.name);
				var dt = new Date();  
				var tm = dt.getTime();
				$.ajax({
					type : "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url : BASE_PATH + '/admin/orderJp/passportRecognition',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							layer.msg("识别成功");
						}else{
							layer.msg("识别失败");
						}
							$('#firstName').val(obj.xingCn).change();
							if(obj.xingCn != undefined){
							$('#firstNameEn').val("/"+getPinYinStr(obj.xingCn));
							}
							$('#lastName').val(obj.mingCn).change();
							if(obj.mingCn != undefined){
							$('#lastNameEn').val("/"+getPinYinStr(obj.mingCn));
							}
							$('#passportUrl').val(obj.url);
							$('#sqImg').attr('src', obj.url);
							$("#uploadFile").siblings("i").css("display","block");
							$(".front").attr("class", "info-imgUpload front has-success");  
					        $(".help-blockFront").attr("data-bv-result","IVALID");  
					        $(".help-blockFront").attr("style","display: none;");
							$('#type').val(obj.type).change();
							$('#passport').val(obj.num).change();
							$('#sex').val(obj.sex);
							$('#sexEn').val(obj.sexEn);
							$('#birthAddress').val(obj.birthCountry).change();
							if(obj.birthCountry != undefined){
							$('#birthAddressEn').val("/"+getPinYinStr(obj.birthCountry));
							}
							$('#birthday').val(obj.birth).change();
							$('#issuedPlace').val(obj.visaCountry).change();
							if(obj.visaCountry != undefined){
							$('#issuedPlaceEn').val("/"+getPinYinStr(obj.visaCountry));
							}
							$('#issuedDate').val(obj.issueDate).change();
							$('#validEndDate').val(obj.expiryDay).change();
							$('#OCRline1').val(obj.OCRline1);
							$('#OCRline2').val(obj.OCRline2);
							$("#borderColor").attr("style", null);
							var years = getDateYearSub($('#issuedDate').val(),$('#validEndDate').val());
							if(years == 5){
								$("#validType").val(2);
							}else{
								$("#validType").val(1);
							}
							
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
						var dt2 = new Date();  
						var tm2 = dt2.getTime();
						var tm3 = tm2 - tm;
						console.log(tm3);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
				
			};
			reader.readAsDataURL(file);
		}); */
		
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
			/* if (status == 1) {
				var firstName = $('#firstNameEn').val();
				var lastName = $('#lastNameEn').val();
				
				().test('ASDAASD按时ASDAD')
				
				var reg = new RegExp(/^[A-Z]*$/g)
				
				if (!reg.test(firstName)) {
					layer.msg("");
					return 0;
				}
				
			} */
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
								socket.onclose();
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
			
		});
		
		$("#sex").change(function(){
			var sex = $(this).val();
			if(sex == "男"){
				$("#sexEn").val("M");
			}else{
				$("#sexEn").val("F");
			}
		});
		
		$("#validType").change(function(){
			var type = $(this).val();
			if($("#issuedDate").val() != ""){
				if(type == 1){
					$('#validEndDate').val(getNewDates($('#issuedDate').val(), 10));
				}else{
					$('#validEndDate').val(getNewDates($('#issuedDate').val(), 5));
				}
			}
		});
		
		function getNewDates(dateTemp, days){
			var d1 = new Date(dateTemp);
			var d2 = new Date(d1);
			d2.setFullYear(d2.getFullYear()+days);
			d2.setDate(d2.getDate()-1);
			var year = d2.getFullYear();  
			var month = d2.getMonth() + 1;  
			if (month < 10) month = "0" + month;  
			var date = d2.getDate();  
			if (date < 10) date = "0" + date;  
			return (year + "-" + month + "-" + date);
		}
		
		function getNewDay(dateTemp, days) {  
		    var dateTemp = dateTemp.split("-");  
		    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
		    var millSeconds = Math.abs(nDate) + (days * 365.2 * 24 * 60 * 60 * 1000);  
		    var rDate = new Date(millSeconds);  
		    var year = rDate.getFullYear();  
		    var month = rDate.getMonth() + 1;  
		    if (month < 10) month = "0" + month;  
		    var date = rDate.getDate();  
		    if (date < 10) date = "0" + date;  
		    return (year + "-" + month + "-" + date);  
		}
		
		function returnYears(year){
			if(((year%4==0)&&(year%100!=0))||(year%400==0)){
		    	return 366;
		 	}else{
		    	return 365; 
			}
		}
		
		function deleteApplicantFrontImg(){
			$('#passportUrl').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
		}
		
		var now = new Date();
		$("#birthday").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
	        weekStart: 1,
	        todayBtn: 1,
			autoclose: true,
			todayHighlight: true,//高亮
			startView: 4,//从年开始选择
			forceParse: 0,
	        showMeridian: false,
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		});
		$("#issuedDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			endDate: now,
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		});
		$("#validEndDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		});
		function getPinYinStr(hanzi){
			var onehanzi = hanzi.split('');
			var pinyinchar = '';
			for(var i=0;i<onehanzi.length;i++){
				pinyinchar += PinYin.getPinYin(onehanzi[i]);
			}
			return pinyinchar.toUpperCase();
		}
		
		 function getDateYearSub(startDateStr, endDateStr) {
		        var day = 24 * 60 * 60 *1000; 

		        var sDate = new Date(Date.parse(startDateStr.replace(/-/g, "/")));
		        var eDate = new Date(Date.parse(endDateStr.replace(/-/g, "/")));

		        //得到前一天(算头不算尾)
		        sDate = new Date(sDate.getTime() - day);

		        //获得各自的年、月、日
		        var sY  = sDate.getFullYear();     
		        var sM  = sDate.getMonth()+1;
		        var sD  = sDate.getDate();
		        var eY  = eDate.getFullYear();
		        var eM  = eDate.getMonth()+1;
		        var eD  = eDate.getDate();

		        if(eY > sY && sM == eM && sD == eD) {
		            return eY - sY;
		        } else {
		            //alert("两个日期之间并非整年，请重新选择");
		            return 0;
		        }
		    }
		 
		 function applyBtn(){
			var id = '${obj.applicantid}';
			var orderid = '${obj.orderid}';
			save(2);
			//关闭socket连接
			//socket.onclose();
			//window.location.href = '/admin/orderJp/updateApplicant.html?id='+id+'&orderid='+'&isTrial=${obj.isTrailOrder}';
		 }
	</script>


</body>
</html>
