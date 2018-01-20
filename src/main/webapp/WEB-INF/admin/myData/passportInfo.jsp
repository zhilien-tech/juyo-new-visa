<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>我的资料 - 护照信息</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
		<style type="text/css">
			img[src=""],img:not([src]) { opacity:0;}
			input[type="file"] { z-index:999999;}
			.delete { z-index:1000000;}
			.qz-head { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:9999; padding:0px 15px;}
			.content { margin-top:50px; height:100%; padding:15px 37px 15px 40px;}
			.rightNav { position:fixed;top:60px;right:0;z-index:999; width:40px;height:calc(100% - 50px); cursor:pointer;}
			.rightNav span { width: 24px; height: 24px; position: absolute;top:50%; border-left: 4px solid #999;  border-bottom: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
		    .leftNav { position:fixed;top:60px;left:4px;z-index:999; width:40px;height:calc(100% - 50px); cursor:pointer;}
			.leftNav span { width: 24px; height: 24px; position: absolute;top:50%;margin-left:10px; border-right: 4px solid #999;  border-top: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
			.ipt-info { display:none; margin-top:15px;}
			.NoInfo { width:95%; height:30px; margin-left:3.5%; transtion:height 1s; -webkit-transtion:height 1s; -moz-transtion:height 1s; }
			.form-control{height: 30px;}
			.tab-content{padding: 0px 30px 10px 0;margin: 0 0px;}
			.info-QRcode{width: 150px;height: 150px;margin: 15px auto;border: #edefef solid 1px;}
			.front, .back {width: 320px;margin: 10px auto;}
			.qz-head { border-bottom:2px solid #deecff; padding:15px 20px; display: table; width: 100%;}
			.basic { margin-left: 10px;}
		</style>
	</head>

<body class="hold-transition skin-blue sidebar-mini">
	<c:choose>
		<c:when test="${!empty obj.contact }">
			<a id="toVisa" class="rightNav" onclick="visaBtn();">
				<span></span>
			</a>
			<a id="toApply" class="leftNav" onclick="applyBtn();">
				<span></span>
			</a>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<form id="passportInfo">
			<div class="qz-head">
				<c:choose>
					<c:when test="${empty obj.contact }">
						<input type="button" value="编辑" id="editbasic" class="btn btn-primary btn-sm pull-right editbasic" onclick="editBtn();"/> 
						<input type="button" value="取消" class="btn btn-primary btn-sm pull-right basic" onclick="cancelBtn(1);"/> 
						<input type="button" value="保存" class="btn btn-primary btn-sm pull-right basic" onclick="save(1);"/> 
						<input type="button" value="清除" class="btn btn-primary btn-sm pull-right basic" onclick="clearAll();"/>
					</c:when>
					<c:otherwise>
						<input type="button" value="取消" class="btn btn-primary btn-sm pull-right basic" onclick="cancelBtn(1);"/> 
						<input type="button" value="保存" class="btn btn-primary btn-sm pull-right basic" onclick="save(4);"/> 
					</c:otherwise>
				</c:choose>
			</div>
			<section class="content">
			<div class="ipt-info">
					<input id="passRemark" name="passRemark"  type="text"  class="NoInfo form-control input-sm" />
				</div>
				<div class="tab-content row">
					<div class="col-sm-5 padding-right-0">
						<div class="info-QRcode"> <!-- 身份证 正面 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div> <!-- end 身份证 正面 -->

						<div class="info-imgUpload front has-error" id="borderColor"><!-- 护照 -->
							<div class="col-xs-6">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传护照</span>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="passportUrl" name="passportUrl" type="hidden" value=""/>
											<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
											<img id="sqImg" alt="" src="" >
										</c:when>
										<c:otherwise>
											<input id="passportUrl" name="passportUrl" type="hidden" value="${obj.passport.passportUrl }"/>
											<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
											<img id="sqImg" alt="" src="${obj.passport.passportUrl }" >
										</c:otherwise>
									</c:choose>
									<i class="delete" id="deletePassportImg" ></i>
								</div>
							</div>
						</div>
						</div><!-- end 护照 -->
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">护照必须上传</small>
						</div>
					</div>
					

					<div class="col-sm-7 padding-right-0">
						<div class="row"><!-- 类型/护照号 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>类型</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input type="hidden" id="id" name="id" />
											<input id="type" name="type" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input type="hidden" id="id" name="id" value="${obj.passport.id }"/>
											<input id="type" name="type" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.type }"/>
										</c:otherwise>
									</c:choose>
									<input type="hidden"  name="contact" value="${obj.contact }">
									<input type="hidden"  name="applyId" value="${obj.applyId }">
									<input type="hidden" id="OCRline1" name="OCRline1" value="">
									<input type="hidden" id="OCRline2" name="OCRline2" value="">
									<input type="hidden" id="tourist" name="tourist" value="1"/>
									<input type="hidden" id="applicantId" name="applicantId" value="${obj.applicantId }"/>
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照号</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="passport" name="passport" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.passport }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 类型/护照 -->
						<%-- <div class="row">
							<!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>姓/拼音：</label> <input id="firstName"
										name="firstName" style="position:relative;" type="text" class="form-control input-sm "
										placeholder=" " value="${obj.passport.firstName }" />
										<input type="text" id="firstNameEn" style="position:absolute;top:35px;border:none;left:150px;"  name="firstNameEn" value="${obj.firstNameEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 姓/拼音 -->
						<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>名/拼音：</label> <input id="lastName"
										name="lastName" style="position:relative;" type="text" class="form-control input-sm "
										placeholder=" " value="${obj.passport.lastName }" />
										<input type="text" id="lastNameEn" style="position:absolute;top:35px;border:none;left:150px;" name="lastNameEn" value="${obj.lastNameEn }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 名/拼音 --> --%>
						
						<div class="row"><!-- 性别/ 出生地点 拼音 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<label><span>*</span>性别</label>
									<select class="form-control input-sm" id="sex" name="sex">
									<c:choose>
										<c:when test="${empty obj.passport}">
											<option value="男" >男</option>
											<option value="女" >女</option>
										</c:when>
										<c:otherwise>
											<option value="男" ${obj.passport.sex == "男"?"selected":"" }>男</option>
											<option value="女" ${obj.passport.sex == "女"?"selected":"" }>女</option>
										</c:otherwise>
									</c:choose>
									</select>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="sexEn" class="form-control input-sm" name="sexEn" type="text"/>
										</c:when>
										<c:otherwise>
											<input id="sexEn" class="form-control input-sm" name="sexEn" type="text" value="${obj.passport.sexEn }"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;">
									<label><span>*</span>出生地点/拼音</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="birthAddress" name="birthAddress"  type="text" class="form-control input-sm " placeholder=" " />
											<input id="birthAddressEn" name="birthAddressEn" style="position:absolute;top:36px; width:110px;border:0px;left:66px;" type="text"  placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="birthAddress" name="birthAddress"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.birthAddress }"/>
											<input id="birthAddressEn" name="birthAddressEn" style="position:absolute;top:36px; width:110px;border:0px;left:66px;" type="text"  placeholder=" " value="${obj.passport.birthAddressEn }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 性别/出生地点 拼音 -->
						<!-- end 性别/出生地点 -->
						<div class="row"><!-- 出生日期/签发地点 拼音 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " value="${obj.birthday}"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" style="position:relative;">
									<label><span>*</span>签发地点/拼音</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="issuedPlace" name="issuedPlace"  type="text" class="form-control input-sm " placeholder=" " />
											<input id="issuedPlaceEn" name="issuedPlaceEn" type="text" style="position:absolute;top:36px; width:110px;border:0px;left:66px;" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="issuedPlace" name="issuedPlace"  type="text" class="form-control input-sm " placeholder=" " value="${obj.passport.issuedPlace }"/>
											<input id="issuedPlaceEn" name="issuedPlaceEn" type="text" style="position:absolute;top:36px; width:110px;border:0px;left:66px;" placeholder=" " value="${obj.passport.issuedPlaceEn }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 出生日期/签发地点 拼音 -->
						
						<div class="row"><!-- 签发日期/有效期至 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发日期</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="issuedDate" name="issuedDate" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="issuedDate" name="issuedDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.issuedDate }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-2 col-sm-offset 2 padding-right-0">
								<div class="form-group">
									<label>&nbsp;&nbsp;</label>
									<select id="validType" name="validType" class="form-control input-sm " >
									<c:choose>
										<c:when test="${empty obj.passport}">
											<c:forEach var="map" items="${obj.passportType}">
												<option value="${map.key}" >${map.value}</option>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="map" items="${obj.passportType}">
												<option value="${map.key}" ${map.key == obj.passport.validType?'selected':'' }>${map.value}</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>有效期至</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " value="${obj.validEndDate }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发日期/有效期至 -->
						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发机关</label>
									<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="issuedOrganization" name="issuedOrganization" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="issuedOrganization" name="issuedOrganization" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganization }"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div><!-- end 签发机关 -->
						<div class="row none">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
								<c:choose>
										<c:when test="${empty obj.passport}">
											<input id="issuedOrganizationEn" name="issuedOrganizationEn" type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input id="issuedOrganizationEn" name="issuedOrganizationEn" type="text" class="form-control input-sm" placeholder=" " value="${obj.passport.issuedOrganizationEn }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
					</div>

				</div>
			</section>
	</form>
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
	
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	
	<!-- 本页面js文件 -->
	<script type="text/javascript">
	var base = "${base}";
	var contact = '${obj.contact}';
	$(function() {
		
		//校验
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
						notEmpty : {
							message : '护照号不能为空'
						},
	                   /*  remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
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
						} */
					}
				},
				type : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '类型不能为空'
						}
					}
				},
				birthAddress : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '出生地点不能为空'
						}
					}
				},
				birthday : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '出生日期不能为空'
						}
					}
				},
				issuedPlace : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '签发地点不能为空'
						}
					}
				},
				issuedDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '签发日期不能为空'
						}
					}
				},
				validEndDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效日期不能为空'
						}
					}
				}
			}
		});
	$('#passportInfo').bootstrapValidator('validate');
		
		if(!contact){
			//将页面所有元素设置为disabled
			var form = document.forms[0]; 
			for ( var i = 0; i < form.length; i++) { 
				var element = form.elements[i]; 
				if(element.id != "editbasic")
					element.disabled = true; 
			} 
			$(".basic").hide();
			document.getElementById("passRemark").style.backgroundColor = "#eee";
			document.getElementById("birthAddressEn").style.backgroundColor = "#eee";
			document.getElementById("issuedPlaceEn").style.backgroundColor = "#eee";
			var remark = $("#passRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			}
			$("#passRemark").attr("disabled", true);
		}else{
			//护照图片验证
			var passportUrl = $("#passportUrl").val();
			if(passportUrl == ""){
				$("#borderColor").attr("style", "border-color:#ff1a1a");  
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        $(".help-blockFront").attr("style","display: block;");  
			}else{
				$("#borderColor").attr("style", null);
				$(".front").attr("class", "info-imgUpload front has-success");  
		        $(".help-blockFront").attr("data-bv-result","IVALID");  
		        $(".help-blockFront").attr("style","display: none;");  
			}
			var bootstrapValidator = $("#passportInfo").data(
			'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			$("#deletePassportImg").click(function(){
				$('#passportUrl').val("");
				$('#sqImg').attr('src', "");
				$("#uploadFile").siblings("i").css("display","none");
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        $(".help-blockFront").attr("style","display: block;");
		        $("#borderColor").attr("style", "border-color:#ff1a1a");
			});
			$("#passRemark").attr("disabled", true);
		}
		
		
		
		
		if($("#sex").val() == "男"){
			$("#sexEn").val("M");
		}else{
			$("#sexEn").val("F");
		}
		
		$("#issuedDate").change(function(){
			if($("#issuedDate").val() != ""){
				if($("#validType").val() == 1){
					$('#validEndDate').val(getNewDay($('#issuedDate').val(), 5));
				}else{
					$('#validEndDate').val(getNewDay($('#issuedDate').val(), 10));
				}
			}
		});
	});
	
	//护照上传,扫描
	
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
						$('#passportUrl').val(obj.url);
						$('#sqImg').attr('src', obj.url);
						$("#uploadFile").siblings("i").css("display","block");
						$("#borderColor").attr("style", null);
						$(".front").attr("class", "info-imgUpload front has-success");  
				        $(".help-blockFront").attr("data-bv-result","IVALID");  
				        $(".help-blockFront").attr("style","display: none;");
						$('#type').val(obj.type).change();
						$('#passport').val(obj.num).change();
						$('#sex').val(obj.sex);
						$('#sexEn').val(obj.sexEn);
						$('#birthAddress').val(obj.birthCountry).change();
						$('#birthAddressEn').val("/"+getPinYinStr(obj.birthCountry));
						$('#birthday').val(obj.birth).change();
						$('#issuedPlace').val(obj.visaCountry).change();
						$('#issuedPlaceEn').val("/"+getPinYinStr(obj.visaCountry));
						$('#issuedDate').val(obj.issueDate).change();
						$('#validEndDate').val(obj.expiryDay).change();
						$('#OCRline1').val(obj.OCRline1);
						$('#OCRline2').val(obj.OCRline2);
						var years = getDateYearSub($('#issuedDate').val(),$('#validEndDate').val());
						if(years == 5){
							$("#validType").val(1);
						}else{
							$("#validType").val(2);
						}
						
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
	
	
	
	//保存
	function save(status){
		//得到获取validator对象或实例 
		var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
		// 执行表单验证 
		bootstrapValidator.validate();
		if(status != 2){
			if (!bootstrapValidator.isValid()) {
				return;
			}
			if($(".front").hasClass("has-error")){
				return;
			}
		}
		var passportInfo = $("#passportInfo").serialize();
		var orderid = '${obj.orderid}';
		var applicantId = '${obj.applicantId}';
		layer.load(1);
		$.ajax({
			type: 'POST',
			data : passportInfo,
			url: '${base}/admin/myData/saveEditPassport',
			success :function(data) {
				layer.closeAll('loading');
				if(status == 1){
					cancelBtn(2);
					parent.successCallBack();
				}else if(status == 2 || status == 3){
					
				}else{
					layer.msg("修改成功", {
						time: 500,
						end: function () {
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							parent.successCallBack();
						}
					});
				}
			}
		});
	}
	
	//编辑按钮
	function editBtn(){
		$(".basic").show();
		$(".editbasic").hide();
		var form = document.forms[0]; 
		for ( var i = 0; i < form.length; i++) { 
			var element = form.elements[i]; 
			element.disabled = false; 
		} 
		document.getElementById("birthAddressEn").style.backgroundColor = "#fff";
		document.getElementById("issuedPlaceEn").style.backgroundColor = "#fff";
		//护照图片验证
		var passportUrl = $("#passportUrl").val();
		if(passportUrl == ""){
			$("#borderColor").attr("style", "border-color:#ff1a1a");  
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        $(".help-blockFront").attr("style","display: block;");  
		}else{
			$("#borderColor").attr("style", null);
			$(".front").attr("class", "info-imgUpload front has-success");  
	        $(".help-blockFront").attr("data-bv-result","IVALID");  
	        $(".help-blockFront").attr("style","display: none;");  
		}
		var bootstrapValidator = $("#passportInfo").data(
		'bootstrapValidator');
		// 执行表单验证 
		bootstrapValidator.validate();
		$("#deletePassportImg").click(function(){
			$('#passportUrl').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        $(".help-blockFront").attr("style","display: block;");
	        $("#borderColor").attr("style", "border-color:#ff1a1a");
		});
		$("#passRemark").attr("disabled", true);
	}
	
	 function applyBtn(){
		/* var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
		bootstrapValidator.validate();
		if (!bootstrapValidator.isValid()) {
			return;
		}
		if($(".front").hasClass("has-error")){
			return;
		} */
		save(2);
		//关闭socket连接
		//socket.onclose();
		var id = '${obj.applyId}';
		window.location.href = '/admin/myData/basic.html?contact=1&applyId='+id;
	 }
		
	 function visaBtn(){
		var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
		bootstrapValidator.validate();
		if (!bootstrapValidator.isValid()) {
			return;
		}
		
		if($(".front").hasClass("has-error")){
			return;
		}
		save(3);
		//关闭socket连接
		//socket.onclose();
		var id = '${obj.applyId}';
		window.location.href = '/admin/myData/visa.html?contact=1&applyId='+id;
	 }
	
	//取消按钮
	function cancelBtn(status){
		if(!contact){
			if(status == 1){
				layer.msg("已取消", {
					time: 500,
					end: function () {
						self.location.reload();
					}
				});
			}else{
				layer.msg("修改成功", {
					time: 500,
					end: function () {
						self.location.reload();
					}
				});
			}
		}else{
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
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
		if(type == 1){
			$('#validEndDate').val(getNewDay($('#issuedDate').val(), 5));
		}else{
			$('#validEndDate').val(getNewDay($('#issuedDate').val(), 10));
		}
		
	});
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
	$("#issuedDate").datetimepicker({
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
	 function clearAll(){
		$("#passportUrl").val("");
		$(".front").attr("class", "info-imgUpload front has-error");  
        $(".help-blockFront").attr("data-bv-result","INVALID");  
        $(".help-blockFront").attr("style","display: block;"); 
        $("#borderColor").attr("style", "border-color:#ff1a1a");
		$('#sqImg').attr('src', "");
		$("#type").val("").change();
		$("#passport").val("").change();
		$("#birthAddress").val("").change();
		$("#birthAddressEn").val("").change();
		$("#birthday").val("").change();
		$("#issuedPlace").val("").change();
		$("#issuedPlaceEn").val("").change();
		$("#issuedDate").val("").change();
		$("#validEndDate").val("").change();
	 }
	</script>
</body>
</html>
