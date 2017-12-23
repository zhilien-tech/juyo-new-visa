<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加申请人</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
<style type="text/css">
.modal-body { height:588px;}	
.modal-content { position:relative; }
.modal-body { padding:15px 100px 15px 20px; }
.nameBeforeYes { margin-right:20px; }
.onceIDYes { margin-right:30px; }
.nameBeforeHide ,.nationalityHide{ display:none; }
.wordSpell { display:none; }
.nationalityHide { margin-left:3%;}
<!--  -->
.rightNav { position:absolute;top:61px;right:2%;z-index:999; width:40px;height:489px; cursor:pointer;}
.rightNav span { width: 24px; height: 24px; position: absolute;top:50%; border-left: 4px solid #999;  border-bottom: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
<!--  -->
.front, .back { margin-bottom:8px !important;}
</style>
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="rightNav" onclick="toPassport();">
			<span></span>
		</a>
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">添加申请人</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right" value="保存" onclick="saveApplicant(1);"/>
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"> <!-- 身份证 正面 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div> <!-- end 身份证 正面 -->
						
						
						<div class="info-imgUpload front"><!-- 身份证 正面 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证正面</span>
									<input id="cardFront" name="cardFront" type="hidden"/>
									<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImg" alt="" src="" >
									<i class="delete" style="display:none;" onclick="deleteApplicantFrontImg(${obj.orderid});"></i>
								</div>
							</div>
						</div>
						</div><!-- end 身份证 正面 -->
						
						<div class="info-imgUpload back"><!-- 身份证 反面 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证背面</span>
									<input id="cardBack" name="cardBack" type="hidden"/>
									<input id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									<img id="sqImgBack" alt="" src="" >
									<i class="delete" style="display:none;" onclick="deleteApplicantBackImg(${obj.orderid});"></i>
								</div>
							</div>
						</div>

						</div><!-- end 身份证 反面 -->
						
						<div class="row"><!-- 签发机关 -->
							<div class="col-sm-11 padding-right-0">
								<div class="form-group">
									<label>签发机关：</label>
									<input id="issueOrganization" name="issueOrganization" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发机关 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-5 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasOtherName" class="nameBefore" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherName" class="nameBefore" checked value="2" />否
										</span>
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-11 padding-right-0">
									<div class="form-group">
										<label>姓/拼音：</label> <input id="otherFirstName"
											name="otherFirstName" style="position:relative;" type="text" class="form-control input-sm "
											placeholder=" " value="" />
											<input type="text" id="otherFirstNameEn" style="position:absolute;top:45px;border:none;left:150px;"  name="otherFirstNameEn" value=""/>

										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div>
							<!-- 姓/名 拼音 end -->
							<div class="col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasOtherNationality" class="onceID" checked value="2"  />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide">
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<input id="nationality" name="nationality" type="text" class="form-control input-sm"/>
								</div>
							</div>
						</div>
					</div>
						
					<div class="col-sm-6 padding-right-0">
						<div class="row"><!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>姓/拼音：</label>
									<input id="firstName" style="position:relative;" name="firstName" type="text" class="form-control input-sm req " placeholder=" " />
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<input type="hidden" id="applyId"/>
									<input type="text" id="firstNameEn" style="position:absolute;top:35px;border:none;left:150px;" name="firstNameEn" value=""/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓/拼音 -->
						<div class="row"><!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<label><span>*</span>名/拼音：</label>
									<input id="lastName" name="lastName" style="position:relative;" type="text" class="form-control input-sm " placeholder=" " />
									<input type="text" id="lastNameEn" style="position:absolute;top:35px;border:none;left:150px;" name="lastNameEn" value=""/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 名/拼音 -->	
						<div class="row"><!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>手机号：</label>
									<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱：</label>
									<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 手机号/邮箱 -->
						<div class="row"><!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证：</label>
									<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 公民身份证 -->
						<div class="row"><!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<!-- <input id="sex" name="sex" type="text" class="form-control input-sm" placeholder=" " value=""/> -->
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label>民族：</label>
									<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label>出生日期：</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓名/民族 -->
						<div class="row"><!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址：</label>
									<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 住宅 -->						
						<div class="row"><!-- 有效期限 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限：</label>
									<input id="validStartDate" name="validStartDate" type="text" class="form-control input-sm" placeholder=" "  />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label>
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" "  />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 有效期限 -->
						<div class="row"><!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地是否与身份证相同：</label><input type="checkbox" class="nowProvince" name="addressIsSameWithCard" value="1"/>
									<input type="hidden" name="cardProvince" id="cardProvince"/>
									<input type="hidden" name="cardCity" id="cardCity"/>
									<input id="province" name="province" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址城市：</label>
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row"><!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址/区(县)/街道/小区(社区)/楼号/单元/房间：</label>
									<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						<div class="row wordSpell">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>名/拼音：</label> 
									<input id="otherLastName" name="otherLastName" style="position:relative;" type="text" class="form-control input-sm" placeholder=" " value="" />
									<input type="text" id="otherLastNameEn" style="position:absolute;top:45px;border:none;left:150px;" name="otherLastNameEn" value=""/>
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> <input id="emergencyLinkman"
										name="emergencyLinkman" type="text" class="form-control input-sm"
										placeholder=" " value="${obj.applicant.emergencyLinkman }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> <input id="emergencyTelephone" name="emergencyTelephone"
										type="text" class="form-control input-sm" placeholder=" "
										value="${obj.applicant.emergencyTelephone }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						
						
					</div>	
						
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function(){
			//校验
			$('#applicantInfo').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {

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
					telephone : {
						validators : {
							regexp: {
		                	 	regexp: /^[1][34578][0-9]{9}$/,
		                        message: '电话号格式错误'
		                    }
						}
					},
					email : {
						validators : {
							regexp: {
		                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		                        message: '邮箱格式错误'
		                    }
						}
					}
				}
			});
			$('#applicantInfo').bootstrapValidator('validate');
			
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
	                  if(received_msg){
		                  var receiveMessage = JSON.parse(received_msg);
		                  if(receiveMessage.messagetype == 4){
		                	  window.location.href = '/admin/orderJp/updateApplicant.html?id='+receiveMessage.applicantid+'&orderid&isTrial=0';
		                	  socket.onclose();
		                  }
	                  }
	                  console.log('message received!');  
	                  //showMessage(received_msg);  
	             };  

	          } else {  
	            console.log('Websocket not supported');  
	          }  
		}
		//居住地与身份证相同
		$(".nowProvince").change(function(){
			searchByCard();
		});
		
		$("#cardId").change(function(){
			searchByCard();
		});
		
		function searchByCard(){
			var str=""; 
			//是否同身份证相同
			$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
				str=$(this).val();     
			});     
			if(str == 1){//相同
				var cardId = $("#cardId").val();
				layer.load(1);
				$.ajax({
					type: 'POST',
					data : {
						cardId : cardId
					},
					dataType : 'json',
					url: '${base}/admin/orderJp/getInfoByCard',
					success :function(data) {
						console.log(JSON.stringify(data));
						layer.closeAll('loading');
						$("#province").val(data.province);
						$("#city").val(data.city);
						$("#detailedAddress").val($("#address").val());
					}
				});
			}else{
				$("#province").val("");
				$("#city").val("");
				$("#detailedAddress").val("");
			}
		}
		
		function saveApplicant(status){
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#applicantInfo").data(
					'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()){
				//获取必填项信息
				var firstName = $("#firstName").val();
				if (firstName == "") {
					layer.msg('姓不能为空');
					return;
				}
				var lastName = $("#lastName").val();
				if (lastName == "") {
					layer.msg('名不能为空');
					return;
				}
				var str="";     
			    $("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
			    	str=$(this).val();     
			    });     
				if(str != 1){
					var applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
				}else{
					var applicantInfo = $("#applicantInfo").serialize();
				}
				
				
				$.ajax({
					type : 'POST',
					data : applicantInfo,
					async : false,
					url : '${base}/admin/orderJp/saveAddApplicant',
					success : function(data) {
						var applicantIdParent = window.parent.document.getElementById("appId").value;
						applicantIdParent += data.id +",";
						window.parent.document.getElementById("appId").value = applicantIdParent;
						$("#applyId").val(data.id);
						layer.closeAll('loading');
						if(status == 1){
							parent.successCallBack(3,data);
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
						}
						if(status == 2){
							//console.log(data);
							parent.successCallBack(4,data);
						}
					},
					error : function() {
						console.log("error");
					}
				}); 
			}
			
		}
		
		//国籍检索
		$("#nationality").on('input',function(){
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$.ajax({
				type : 'POST',
				async: false,
				data : {
					searchStr : $("#nationality").val()
				},
				url : BASE_PATH+'/admin/orderJp/getNationality.html',
				success : function(data) {
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setNationality("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#nationality").after(liStr);
				}
			});
		});
		//国籍检索下拉项
		function setNationality(nationality){
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
			$("#nationality").val(nationality);
		} 
		$("#nationalityDiv").mouseleave(function(){
			$("#nationality").nextAll("ul.ui-autocomplete").remove();
		});
		
		//省份检索
		$("#province").on('input',function(){
			$("#province").nextAll("ul.ui-autocomplete").remove();
			$.ajax({
				type : 'POST',
				async: false,
				data : {
					searchStr : $("#province").val()
				},
				url : BASE_PATH+'/admin/orderJp/getProvince.html',
				success : function(data) {
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setProvince("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#province").after(liStr);
				}
			});
		});
		
		//省份 检索下拉项
		function setProvince(province){
			$("#province").nextAll("ul.ui-autocomplete").remove();
			$("#province").val(province);
		} 
		$("#provinceDiv").mouseleave(function(){
			$("#province").nextAll("ul.ui-autocomplete").remove();
		});
		
		//市检索
		$("#city").on('input',function(){
			$("#city").nextAll("ul.ui-autocomplete").remove();
			$.ajax({
				type : 'POST',
				async: false,
				data : {
					province : $("#province").val(),
					searchStr : $("#city").val()
				},
				url : BASE_PATH+'/admin/orderJp/getCity.html',
				success : function(data) {
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setCity("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#city").after(liStr);
				}
			});
		});
		
		//市 检索下拉项
		function setCity(city){
			$("#city").nextAll("ul.ui-autocomplete").remove();
			$("#city").val(city);
		} 
		$("#cityDiv").mouseleave(function(){
			$("#city").nextAll("ul.ui-autocomplete").remove();
		});
		
		
		//正面上传,扫描
		$('#uploadFile').change(function() {
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
				$.ajax({
					type : "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url : BASE_PATH + '/admin/orderJp/IDCardRecognition',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							$('#cardFront').val(obj.url);
							$('#sqImg').attr('src', obj.url);
							$("#uploadFile").siblings("i").css("display","block");
							$('#address').val(obj.address);
							$('#nation').val(obj.nationality);
							$('#cardId').val(obj.num);
							searchByCard();
							$('#cardProvince').val(obj.province);
							$('#cardCity').val(obj.city);
							$('#birthday').val(obj.birth);
							$('#sex').val(obj.sex);
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
		//背面上传,扫描
		$('#uploadFileBack').change(function() {
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
				$.ajax({
					type : "POST",//提交类型  
					//dataType : "json",//返回结果格式  
					url : BASE_PATH + '/admin/orderJp/IDCardRecognitionBack',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (true === obj.success) {
							$('#cardBack').val(obj.url);
							$('#sqImgBack').attr('src', obj.url);
							$("#uploadFileBack").siblings("i").css("display","block");
							$('#validStartDate').val(obj.starttime);
							$('#validEndDate').val(obj.endtime);
							$('#issueOrganization').val(obj.issue);
						}
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
						$("#updateBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
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

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		function deleteApplicantFrontImg(id){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
		}
		function deleteApplicantBackImg(id){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
		}
		
		$("#validStartDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		$("#validEndDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
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
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		
		//checkbox 曾用名
		$(".nameBefore").change(function(){
			let checked = $("input[name='hasOtherName']:checked").val();
			let checked2 = $("input[name='hasOtherNationality']:checked").val();
			if(checked == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
				$(".onceIDTop").removeClass('col-sm-offset-1');
				$(".onceIDTop").css('padding-left','15px');
			}else {
				
				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
				if(checked2 == 1){
					
				}else {
					$(".nameBeforeTop").css('float','left');
				}
			}
		});
		//曾用国籍
		$(".onceID").change(function(){
			let checked = $("input[name='hasOtherNationality']:checked").val();
			let checked2 = $("input[name='hasOtherName']:checked").val();
			if(checked == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nationalityHide").show();
				$(".onceIDTop").css('float','left');
				$(".onceIDTop").removeClass('col-sm-offset-1');
				$(".onceIDTop").css('padding-left','15px');
			}else {
				
				$(".nationalityHide").hide();
				if(checked2 == 1){
					
				}else {
					$(".nameBeforeTop").css('float','left');
				}
			}
		});	
		//点击右侧箭头，跳转到护照信息
		function toPassport(){
			var bootstrapValidator = $("#applicantInfo").data(
			'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			saveApplicant(2);
			var applyId = $("#applyId").val();
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '551px'],
				content:'/admin/orderJp/passportInfo.html?applicantId='+applyId+'&orderid'+'&isTrial=0'
			});
		}
		function successCallBack(status){
			parent.successCallBack(1);
			closeWindow();
		}
		function cancelCallBack(status){
			closeWindow();
		}
	</script>


</body>
</html>
