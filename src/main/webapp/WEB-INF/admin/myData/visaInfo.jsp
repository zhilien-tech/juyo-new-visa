<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/css/style.css">
	<style type="text/css">
	.modal-body { height:100% !important; background: #FFF !important; padding:0px 70px !important;}
	.info-imgUpload {width: 100%;}
	.NoInfo { width:100%; height:30px; margin-top:0px; transtion:height 1s; -webkit-transtion:height 1s; -moz-transtion:height 1s; }
	.ipt-info { display:none; margin-top:15px;}
	.tab-content { margin-top:15px;}
    .Unqualified, .qualified { margin-right:10px; }
 	.input-box { position: relative; display: inline-block; }
    .input-box input { background-color: transparent;  background-image: none; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset; color: #555;  display: block;  font-size: 14px; line-height: 1.42857; padding: 6px 6px; transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;  width: 200px; display: inline; position: relative; z-index: 1;}
    .tip-l {  width: 0;  height: 0; border-left: 5px solid transparent; border-right: 5px solid transparent; border-top: 10px solid #555; display: inline-block; right: 10px; z-index: 0;  position: absolute;  top: 12px; }
    .dropdown { position: absolute; top: 32px; left: 0px; width: 200px; background-color: #FFF; border: 1px solid #23a8ce; border-top: 0; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;  z-index: 999; padding: 0;  margin: 0; }
    .dropdown li { display: block; line-height: 1.42857; padding: 0 6px; min-height: 1.2em; cursor: pointer; }
    .dropdown li:hover {  background-color: #23a8ce;  color: #FFF; }
    .colSm {  display:block; float:left; width:200px; }
    .padding-right-0 { margin-left:10%; width:323px; height:200px; border:1px solid #eee; }
    .delete { right:0; }
    /*左右导航样式*/
    .leftNav { position:absolute;top:61px;left:0;z-index:999; width:40px;height:489px; cursor:pointer;}
	.leftNav span { width: 24px; height: 24px; position: absolute;top:50%;margin-left:10px; border-right: 4px solid #999;  border-top: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
</style>
	<style type="text/css">
		body {min-width:auto;}
		.tab-content {background-color: #f8f8f8;}
		.remove-btn {right: 0;}
		.modal-body{background-color:#f9f9f9;}
		.houseProperty,.vehicle,.deposit,.financial,.vice{display:none;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="passportInfo">
			<div class="modal-header">
				<input type="hidden" value="${obj.applyId }" name="applyId"/>
				<input type="hidden" value="${obj.contact }" name="contact"/>
				<input type="hidden" value="${obj.applicantid }" name="applicantId"/>
				<input type="hidden" value="${obj.orderid }" name="orderid"/>
				<c:choose>
					<c:when test="${empty obj.contact }">
						<input type="button" value="编辑" id="editbasic" class="btn btn-primary btn-sm pull-right editbasic" onclick="editBtn();"/> 
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm basic" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right basic" value="保存" />
			</div>
			<div class="modal-body">
			<div class="ipt-info">
					<input id="visaRemark" name="visaRemark"  type="text" value="${obj.unqualified.visaRemark }" class="NoInfo form-control input-sm" />
				</div>
				<div class="tab-content row total">
					<!-- 结婚状况 -->
					<div class="info">
						<div class="info-head">结婚状况 </div>
						<div class="info-body-from cf ">
							<div class="row colSm">
								<div class="">
									<div class="form-group">
										<select id="marryStatus" name="marryStatus" class="form-control input-sm selectHeight">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.marryStatus}">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<option value="${map.key}" >${map.value}</option>
												</c:when>
												<c:otherwise>
													<option value="${map.key}" ${map.key==obj.visaInfo.marryStatus?'selected':''}>${map.value}</option>
												</c:otherwise>
											</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="info-imgUpload front " style="width:40% !important; float: left; margin-left:10%; ">
							<div class="col-sm-4 padding-right-0" id="borderColor">
								<div class="cardFront-div">
									<span>上传结婚证/离婚证</span>
										<c:choose>
											<c:when test="${empty obj.visaInfo}">
												<input id="marryUrl" name="marryUrl" type="hidden" />
												<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
												<img id="sqImg" alt="" src="" >
											</c:when>
											<c:otherwise>
												<input id="marryUrl" name="marryUrl" type="hidden" value="${obj.visaInfo.marryUrl }"/>
												<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
												<img id="sqImg" alt="" src="${obj.visaInfo.marryUrl }" >
											</c:otherwise>
										</c:choose>
									<i class="delete" id="deleteApplicantFrontImg"></i>
								</div>
							</div>
							<div class="col-xs-6 front " style="clear:both;width:320px; height:30px; border:0 !important; color:red; margin-left:10%;">
								<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="marryUrl" data-bv-result="IVVALID" style="display: none;">结婚证/离婚证必须上传</small>
							</div>
							</div>
							
						</div>
					</div>
					<!-- 申请人 -->
					<div class="info">
						<div id="mainApply" class="info-head">主申请人 </div>
						<div class="info-body-from"><!--class=" cf "-->
							<div class="row"><!-- 申请人/备注 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>申请人</label>
										<select id="applicant" name="applicant" class="form-control input-sm selectHeight">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<c:forEach var="map" items="${obj.mainOrVice}">
														<option value="${map.key}" >${map.value}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="map" items="${obj.mainOrVice}">
														<option value="${map.key}" ${map.key==obj.visaInfo.isMainApplicant?'selected':''}>${map.value}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="col-sm-4 applymain">
									<div class="form-group">
										<label><span>*</span>备注</label>
										</br>
										<div class="input-box">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<input type="text" id="relationRemark" name="relationRemark" class="input" >
												</c:when>
												<c:otherwise>
													<input type="text" id="relationRemark" name="relationRemark" class="input" value="${obj.visaInfo.relationRemark}">
												</c:otherwise>
											</c:choose>
											<ul class="dropdown">
												<li>主卡</li>
												<li>朋友</li>
												<li>同事</li>
												<li>同学</li>
											</ul>
										</div>
									</div>
								</div>
								
								<div class="applyvice">
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>主申请人</label>
											<select id="mainApplicant" name="mainApplicant" class="form-control input-sm selectHeight">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<c:forEach var="map" items="${obj.mainApply}">
														<option value="${map.id}"  >${map.applyname}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="map" items="${obj.mainApply}">
														<option value="${map.id}" ${map.id==obj.visaInfo.mainId?'selected':'' } >${map.applyname}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>与主申请人关系</label>
											</br>
										<div class="input-box">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<input type="text" id="mainRelation" name="mainRelation" class="input" >
												</c:when>
												<c:otherwise>
													<input type="text" id="mainRelation" name="mainRelation" class="input" value="${obj.visaInfo.mainRelation}">
												</c:otherwise>
											</c:choose>
												<ul class="dropdown">
													<li>之妻</li>
													<li>之夫</li>
													<li>之子</li>
													<li>之女</li>
													<li>之父</li>
													<li>之母</li>
													<li>朋友</li>
													<li>同事</li>
													<li>同学</li>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div><!-- end 申请人/备注-->
						</div>
					</div>
					<!-- end 申请人 -->
					
					<!-- 出行信息 -->
					<div class="info tripvice">
						<div class="info-head">出行信息 </div>
						<div class="info-body-from cf ">
							<div class="row"><!-- 是否同主申请人 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人</label>
										<input id="trip" name="sameMainTrip" class="form-control input-sm selectHeight" value="是" disabled="disabled"/>
									</div>
								</div>
							</div><!-- end 是否同主申请人 -->
						</div>
					</div>
					<!-- end 出行信息 -->
					
					<!-- 工作信息 -->
					<div class="info">
						<div class="info-head">工作信息 </div>
						<div class="info-body-from cf ">
							<div class="row "><!-- 我的职业/单位名称/单位电话 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>我的职业</label>
										<select id="careerStatus" name="careerStatus" class="form-control input-sm selectHeight">
											<option value="">--请选择--</option>
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<c:forEach var="map" items="${obj.jobStatusEnum}">
														<option value="${map.key}" >${map.value}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="map" items="${obj.jobStatusEnum}">
														<option value="${map.key}" ${map.key==obj.visaInfo.careerStatus?'selected':''}>${map.value}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="schoolName"><span>*</span>单位名称</label>
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<input id="name" name="name" type="text" class="form-control input-sm" placeholder=" " />
												</c:when>
												<c:otherwise>
													<input id="name" name="name" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaInfo.name }"/>
												</c:otherwise>
											</c:choose>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="schoolTelephone"><span>*</span>单位电话</label>
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
												</c:when>
												<c:otherwise>
													<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaInfo.telephone }"/>
												</c:otherwise>
											</c:choose>
									</div>
								</div>
							</div><!-- end 我的职业/单位名称/单位电话 -->
							<div class="row"><!-- 单位地址 -->
								<div class="col-sm-8">
									<div class="form-group">
										<label id="schoolAddress"><span>*</span>单位地址</label>
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
												</c:when>
												<c:otherwise>
													<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaInfo.address }"/>
												</c:otherwise>
											</c:choose>
									</div>
								</div>
							</div>
							
							<!-- end 单位地址 -->
						</div>
					</div>
					<!-- end 工作信息 -->
					
					<!-- 财产信息 -->
					<div class="info" style="padding-bottom: 15px;">
						<div class="info-head">财产信息 </div>
						<div class="wealthvice row info-body-from clone-module cf">
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>是否同主申请人</label>
										<select id="wealth" name="sameMainWealth" class="form-control input-sm selectHeight">
											<c:choose>
												<c:when test="${empty obj.visaInfo}">
													<c:forEach var="map" items="${obj.isOrNo}">
														<option value="${map.key}" >${map.value}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach var="map" items="${obj.isOrNo}">
														<option value="${map.key}" ${map.key==obj.visaInfo.sameMainWealth?'selected':''}>${map.value}</option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
						</div>
						<div class="info-body-from finance-btn wealthmain">
							<input id="depositType" name="wealthType" value="银行存款" type="button" class="btn btn-sm btnState" />
							<input id="vehicleType" name="wealthType" value="车产" type="button" class="btn btn-sm btnState" />
							<input id="housePropertyType" name="wealthType" value="房产" type="button" class="btn btn-sm btnState" />
							<input id="financialType" name="wealthType" value="理财" type="button" class="btn btn-sm btnState" />
						</div>
						<div class="info-body-from  clone-module cf deposit">
							<div class="row body-from-input"><!-- 银行存款 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>银行存款</label>
										<input id="" name="" type="text" class="form-control input-sm" value="银行存款" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="deposit" name="deposit" type="text" class="form-control input-sm" placeholder="万"  />
									</div>
								</div>
							</div><!-- end 银行存款 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 deposits " data-bv-result="IVVALID" style=" display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockdeposit" data-bv-validator="notEmpty" data-bv-for="financial" >银行存款不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf vehicle">
							<div class="row body-from-input"><!-- 车产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>车产</label>
										<input id="" name="" type="text" class="form-control input-sm" value="车产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder=" "/>
									</div>
								</div>
							</div><!-- end 车产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 vehicles " style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockvehicle" data-bv-validator="notEmpty" data-bv-for="vehicle" data-bv-result="IVVALID" >车产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf houseProperty">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>房产</label>
										<input id="" name="" type="text" class="form-control input-sm" value="房产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="houseProperty" name="houseProperty" type="text" class="form-control input-sm" placeholder="平米"  />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 housePropertys " style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockhouseProperty" data-bv-validator="notEmpty" data-bv-for="houseProperty" data-bv-result="IVVALID" >房产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf financial">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>理财</label>
										<input id="" name="" type="text" class="form-control input-sm" value="理财" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="financial" name="financial" type="text" class="form-control input-sm" placeholder="万"  />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 financials " style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockfinancial" data-bv-validator="notEmpty" data-bv-for="financial" data-bv-result="IVVALID" >理财不能为空</small>
						</div>
						<!-- 提示End -->
					</div>
					<!-- end 财产信息 -->
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript" src="${base}/admin/orderJp/visaInfo.js"></script>
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
					relationRemark : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '备注不能为空'
							}
						}
					},
					careerStatus : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '职业不能为空'
							}
						}
					},
					marryStatus : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '结婚状况不能为空'
							}
						}
					},
					mainRelation : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '与主申请人关系不能为空'
							}
						}
					},
					name : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '名称不能为空'
							}
						}
					},
					telephone : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '电话不能为空'
							}
						}
					},
					address : {
						trigger:"change keyup",
						validators : {
							notEmpty : {
								message : '地址不能为空'
							}
						}
					},
				}
			});
		$('#passportInfo').bootstrapValidator('validate');
			
			if(!contact){
				var form = document.forms[0]; 
				for ( var i = 0; i < form.length; i++) { 
					var element = form.elements[i]; 
					if(element.id != "editbasic")
						element.disabled = true; 
				} 
				document.getElementById("visaRemark").style.backgroundColor = "#eee";
				document.getElementById("mainRelation").style.backgroundColor = "#eee";
				document.getElementById("relationRemark").style.backgroundColor = "#eee";
				$(".basic").hide();
			}else{
				//结婚证图片验证
				var marryUrl = $("#marryUrl").val();
				if(marryUrl == ""){
					$(".front").attr("class", "info-imgUpload front has-error");  
			        $(".help-blockFront").attr("data-bv-result","INVALID");  
			        $(".help-blockFront").attr("style","display: block;");  
			        $("#borderColor").attr("style","border-color:#ff1a1a");
				}else{
					$(".front").attr("class", "info-imgUpload front has-success");  
			        $(".help-blockFront").attr("data-bv-result","IVALID");  
			        $(".help-blockFront").attr("style","display: none;");
			        $("#borderColor").attr("style",null);
				}
				var bootstrapValidator = $("#passportInfo").data(
				'bootstrapValidator');
				// 执行表单验证 
				bootstrapValidator.validate();
				$("#deleteApplicantFrontImg").click(function(){
					$('#marryUrl').val("");
					$('#sqImg').attr('src', "");
					$("#uploadFile").siblings("i").css("display","none");
					$(".front").attr("class", "info-imgUpload front has-error");  
			        $(".help-blockFront").attr("data-bv-result","INVALID");  
			        $(".help-blockFront").attr("style","display: block;");
			        $("#borderColor").attr("style","border-color:#ff1a1a");
				});
				
				$(".remove-btn").click(function(){
					//$(this).parent().css("display","none");
					if($(this).parent().is(".deposit")){
						$(".deposit").css("display","none");
						$("#depositType").removeClass("btnState-true");
						$("#deposit").val("");
						$(".deposits").css({"display":"none"});
						$(".deposits").attr("class", "col-xs-6 deposits has-success");
						$("#deposite").attr("style", null);
					}
					if($(this).parent().is(".vehicle")){
						$(".vehicle").css("display","none");
						$("#vehicleType").removeClass("btnState-true");
						$("#vehicle").val("");
						$(".vehicles").css({"display":"none"});
						$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
						$("#vehicle").attr("style", null);
					}
					if($(this).parent().is(".houseProperty")){
						$(".houseProperty").css("display","none");
						$("#housePropertyType").removeClass("btnState-true");
						$("#houseProperty").val("");
						$(".housePropertys").css({"display":"none"});
						$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
						$("#houseProperty").attr("style", null);
					}
					if($(this).parent().is(".financial")){
						$(".financial").css("display","none");
						$("#financialType").removeClass("btnState-true");
						$("#financial").val("");
						$(".financials").css({"display":"none"});
						$(".financials").attr("class", "col-xs-6 financials has-success");
						$("#financial").attr("style", null);
					}
				});
			}
			
			
			var remark = $("#visaRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			}
			$("#visaRemark").attr("disabled", true);
			
			var marry = $("#marryUrl").val();
			if(marry != ""){
				$("#uploadFile").siblings("i").css("display","block");
			}else{
				$("#uploadFile").siblings("i").css("display","none");
			}
			
			
			
			var career = $("#careerStatus").val();
			if(career == 4){
				$("#schoolName").html("<span>*</span>学校名称：");
				$("#schoolTelephone").html("<span>*</span>学校电话：");
				$("#schoolAddress").html("<span>*</span>学校地址：");
			}
			$("#careerStatus").change(function(){
				$("#name").val("").change();
				$("#telephone").val("").change();
				$("#address").val("").change();
				var career = $(this).val();
				if(career == 4){
					$("#schoolName").html("<span>*</span>学校名称：");
					$("#schoolTelephone").html("<span>*</span>学校电话：");
					$("#schoolAddress").html("<span>*</span>学校地址：");
				}else{
					$("#schoolName").html("<span>*</span>单位名称：");
					$("#schoolTelephone").html("<span>*</span>单位电话：");
					$("#schoolAddress").html("<span>*</span>单位地址：");
				}
			});
			
			//主申请人 or 副申请人
			var applicVal = $("#applicant").val();
			if(applicVal == "1"){//主申请人
				$(".applyvice").hide();
				$(".tripvice").hide();
				//$(".workvice").hide();
				$(".wealthvice").hide();
				$(".applymain").show();
				//$(".workmain").show();
				$(".wealthmain").show();
				$("#mainApply").text("主申请人");
			}else{//副申请人
				$(".applyvice").show();
				$(".tripvice").show();
				$(".wealthvice").show();
				//$(".workvice").show();
				$(".applymain").hide();
				//$(".workmain").hide();
				$(".wealthmain").hide();
				$("#mainApply").text("副申请人");
			}
			
			//主申请人 or 副申请人
			$("#applicant").change(function(){
				var applicVal = $(this).val();
				if(applicVal == "1"){//主申请人
					$(".applyvice").hide();
					$(".tripvice").hide();
					//$(".workvice").hide();
					$(".wealthvice").hide();
					$(".applymain").show();
					//$(".workmain").show();
					$(".wealthmain").show();
					$("#mainApply").text("主申请人");
				}else{//副申请人
					$(".applyvice").show();
					$(".tripvice").show();
					$(".wealthvice").show();
					//$(".workvice").show();
					$("#wealth").val(1);
					$(".applymain").hide();
					$(".workmain").hide();
					$(".wealthmain").hide();
					$("#mainApply").text("副申请人");
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}
			});
			
			if('${obj.visaInfo.deposit}' != ""){//银行存款
				$(".deposit").css("display","block");
				$("#depositType").addClass("btnState-true");
				$("#deposit").val('${obj.visaInfo.deposit}');
			}
			if('${obj.visaInfo.vehicle}' != ""){//车产
				$(".vehicle").css("display","block");
				$("#vehicleType").addClass("btnState-true");
				$("#vehicle").val('${obj.visaInfo.vehicle}');
			}
			if('${obj.visaInfo.houseProperty}' != ""){//房产
				$(".houseProperty").css("display","block");
				$("#housePropertyType").addClass("btnState-true");
				$("#houseProperty").val('${obj.visaInfo.houseProperty}');
			}
			if('${obj.visaInfo.financial}' != ""){//理财
				$(".financial").css("display","block");
				$("#financialType").addClass("btnState-true");
				$("#financial").val('${obj.visaInfo.financial}');
			}
			
			var wealth = $("#wealth").val();
			if(wealth == 0){
				$(".wealthmain").show();
				//$(".address").show();
			}else{
				if(applicVal == 1){
					
				}else{
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}
			}
			$("#wealth").change(function(){
				if($(this).val() == 1){
					$(".wealthmain").hide();
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
				}else{
					if('${obj.visaInfo.deposit}' != ""){//银行存款
						$(".deposit").css("display","block");
						$("#depositType").addClass("btnState-true");
						$("#deposit").val('${obj.visaInfo.deposit}');
					}
					if('${obj.visaInfo.vehicle}' != ""){//车产
						$(".vehicle").css("display","block");
						$("#vehicleType").addClass("btnState-true");
						$("#vehicle").val('${obj.visaInfo.vehicle}');
					}
					if('${obj.visaInfo.houseProperty}' != ""){//房产
						$(".houseProperty").css("display","block");
						$("#housePropertyType").addClass("btnState-true");
						$("#houseProperty").val('${obj.visaInfo.houseProperty}');
					}
					if('${obj.visaInfo.financial}' != ""){//理财
						$(".financial").css("display","block");
						$("#financialType").addClass("btnState-true");
						$("#financial").val('${obj.visaInfo.financial}');
					}
					$(".wealthmain").show();
					/* $('[name=wealthType]').each(function(){
						$(this).removeClass("btnState-true");
					}); */
				}
			});
			
			
			//财务信息 部分按钮效果
			$(".finance-btn input").click(function(){
				var financeBtnInfo=$(this).val();
				if(financeBtnInfo == "银行存款"){
					if($(this).hasClass("btnState-true")){
						$(".deposit").css("display","none");
						$(this).removeClass("btnState-true");
						$("#deposit").val("");
						$(".deposits").css({"display":"none"});
						$(".deposits").attr("class", "col-xs-6 deposits has-success");
						$("#deposit").attr("style", null);
					}else{
						$(".deposit").css("display","block");
						$(this).addClass("btnState-true");
						$("#deposit").val("万");
						 $("#deposit").attr("style", null);
						//$("#deposit").placeholder("万");
					}
				}else if(financeBtnInfo == "车产"){
					if($(this).hasClass("btnState-true")){
						$(".vehicle").css("display","none");
						$(this).removeClass("btnState-true");
						$("#vehicle").val("");
						$(".vehicles").css({"display":"none"});
						$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
						$("#vehicle").attr("style", null);
					}else{
						$(".vehicle").css("display","block");
						$(this).addClass("btnState-true");
						$("#vehicle").val("");
				        $(".help-blockvehicle").attr("data-bv-result","INVALID");  
				        $(".vehicles").css({"display":"block"});
				        $(".vehicles").attr("class", "col-xs-6 vehicles has-error");
				        $("#vehicle").attr("style", "border-color:#ff1a1a");
					}
				}else if(financeBtnInfo == "房产"){
					if($(this).hasClass("btnState-true")){
						$(".houseProperty").css("display","none");
						$(this).removeClass("btnState-true");
						$("#houseProperty").val("");
						$(".housePropertys").css({"display":"none"});
						$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
						$("#houseProperty").attr("style", null);
					}else{
						$(".houseProperty").css("display","block");
						$(this).addClass("btnState-true");
						$("#houseProperty").val("平米");
						$("#houseProperty").attr("style", null);
						//$("#houseProperty").placeholder("平米");
					}
				}else if(financeBtnInfo == "理财"){
					if($(this).hasClass("btnState-true")){
						$(".financial").css("display","none");
						$(this).removeClass("btnState-true");
						$("#financial").val("");
						$(".financials").css({"display":"none"});
						$(".financials").attr("class", "col-xs-6 financials has-success");
						$("#financial").attr("style", null);
					}else{
						$(".financial").css("display","block");
						$(this).addClass("btnState-true");
						$("#financial").val("万");
						$("#financial").attr("style", null);
						//$("#financial").placeholder("万");
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
	                  var applicantId = '${obj.applicantid}';
	                  if(received_msg){
		                  var receiveMessage = JSON.parse(received_msg);
		                  if(receiveMessage.messagetype == 3 && receiveMessage.applicantid == applicantId){
		                	  window.location.reload();
		                  }
	                  }
	                  console.log('message received!');  
	                  //showMessage(received_msg);  
	             };  

	          } else {  
	            console.log('Websocket not supported');  
	          }  
		}
		
		
		//保存
		function save(){
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			if($(".front").hasClass("has-error")){
				return;
			}
			if($(".deposits").hasClass("has-error")){
				return;
			}
			if($(".vehicles").hasClass("has-error")){
				return;
			}
			if($(".housePropertys").hasClass("has-error")){
				return;
			}
			if($(".financials").hasClass("has-error")){
				return;
			}
			
			
			//绑定财产类型
			var wealthType = "";
			$('[name=wealthType]').each(function(){
				if($(this).hasClass('btnState-true')){
					wealthType += $(this).val() + ',';
				}
			});
			if(wealthType){
				wealthType = wealthType.substr(0,wealthType.length-1);
			}
			//var passportInfo = $("#passportInfo").serialize();
			var applicVal = $("#applicant").val();
			//主申请人时，是否同主申请人设置为空，不然默认为1
			if(applicVal == 1){
				//$("#work").val(0);
				$("#wealth").val(0);
			}
			var orderid = '${obj.orderid}';
			var applicantId = '${obj.applicantid}';
			//var passportInfo = $.param({"wealthType":wealthType}) + "&" +  $("#passportInfo").serialize();
			var passportInfo = $("#passportInfo").serialize();
			$.ajax({
				type: 'POST',
				async: false,
				data : passportInfo,
				url: '${base}/admin/myData/saveEditVisa',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					layer.load(1);
					$.ajax({
						type: 'POST',
						async : false,
						data : {
							orderid : orderid,
							applicantid : applicantId,
							completeType : 'visa'
						},
						url: '${base}/admin/myData/changeStatus.html',
						success :function(data) {
							console.log(JSON.stringify(data));
							layer.closeAll('loading');
						}
					});
					layer.msg("修改成功", {
						time: 500,
						end: function () {
							self.location.reload();
						}
					});
				}
			});
		}
		
		//上传结婚证
		
		function dataURLtoBlob(dataurl) { 
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
			bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
			while(n--){
				u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([u8arr], {type:mime});
		}
		
		$('#uploadFile').change(function() {
			var layerIndex = layer.load(1, {
				shade : "#000"
			});
			$("#addBtn").attr('disabled', true);
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
					url : BASE_PATH + '/admin/orderJp/marryUpload',//请求地址  
					async : true,
					processData : false, //当FormData在jquery中使用的时候需要设置此项
					contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
					//请求数据  
					data : formData,
					success : function(obj) {//请求成功后的函数 
						//关闭加载层
						layer.close(layerIndex);
						if (200 == obj.status) {
							$('#marryUrl').val(obj.data);
							$('#sqImg').attr('src', obj.data);
							$("#uploadFile").siblings("i").css("display","block");
							$(".front").attr("class", "info-imgUpload front has-success");  
					        $(".help-blockFront").attr("data-bv-result","IVALID");  
					        $(".help-blockFront").attr("style","display: none;");
					        $("#borderColor").attr("style",null);
						}
						$("#addBtn").attr('disabled', false);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						layer.close(layerIndex);
						$("#addBtn").attr('disabled', false);
					}
				}); // end of ajaxSubmit
			};
			reader.readAsDataURL(file);
		});
		
		//编辑按钮
		function editBtn(){
			$(".basic").show();
			$(".editbasic").hide();
			var form = document.forms[0]; 
			for ( var i = 0; i < form.length; i++) { 
				var element = form.elements[i]; 
				element.disabled = false; 
			} 
			document.getElementById("mainRelation").style.backgroundColor = "#fff";
			document.getElementById("relationRemark").style.backgroundColor = "#fff";
			$("#trip").attr("disabled", true);
			$("#visaRemark").attr("disabled", true);
			//结婚证图片验证
			var marryUrl = $("#marryUrl").val();
			if(marryUrl == ""){
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        $(".help-blockFront").attr("style","display: block;");  
		        $("#borderColor").attr("style","border-color:#ff1a1a");
			}else{
				$(".front").attr("class", "info-imgUpload front has-success");  
		        $(".help-blockFront").attr("data-bv-result","IVALID");  
		        $(".help-blockFront").attr("style","display: none;");
		        $("#borderColor").attr("style",null);
			}
			var bootstrapValidator = $("#passportInfo").data(
			'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			$("#deleteApplicantFrontImg").click(function(){
				$('#marryUrl').val("");
				$('#sqImg').attr('src', "");
				$("#uploadFile").siblings("i").css("display","none");
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        $(".help-blockFront").attr("style","display: block;");
		        $("#borderColor").attr("style","border-color:#ff1a1a");
			});
			
			$(".remove-btn").click(function(){
				//$(this).parent().css("display","none");
				if($(this).parent().is(".deposit")){
					$(".deposit").css("display","none");
					$("#depositType").removeClass("btnState-true");
					$("#deposit").val("");
					$(".deposits").css({"display":"none"});
					$(".deposits").attr("class", "col-xs-6 deposits has-success");
					$("#deposite").attr("style", null);
				}
				if($(this).parent().is(".vehicle")){
					$(".vehicle").css("display","none");
					$("#vehicleType").removeClass("btnState-true");
					$("#vehicle").val("");
					$(".vehicles").css({"display":"none"});
					$(".vehicles").attr("class", "col-xs-6 vehicles has-success");
					$("#vehicle").attr("style", null);
				}
				if($(this).parent().is(".houseProperty")){
					$(".houseProperty").css("display","none");
					$("#housePropertyType").removeClass("btnState-true");
					$("#houseProperty").val("");
					$(".housePropertys").css({"display":"none"});
					$(".housePropertys").attr("class", "col-xs-6 housePropertys has-success");
					$("#houseProperty").attr("style", null);
				}
				if($(this).parent().is(".financial")){
					$(".financial").css("display","none");
					$("#financialType").removeClass("btnState-true");
					$("#financial").val("");
					$(".financials").css({"display":"none"});
					$(".financials").attr("class", "col-xs-6 financials has-success");
					$("#financial").attr("style", null);
				}
			});
		}
		
		//返回 
		function closeWindow() {
			layer.msg("已取消", {
				time: 500,
				end: function () {
					self.location.reload();
					//window.location.href = '/admin/myData/visaCountry.html';
				}
			});
		}
	</script>


</body>
</html>
