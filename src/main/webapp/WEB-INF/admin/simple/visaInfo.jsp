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
	img[src=""],img:not([src]) { opacity:0;}
	input[type="file"] { z-index:999;}
	.delete { z-index:9999;}
	.info-imgUpload {width: 100%;}
	#sqImg { z-index:1099;}
	.NoInfo { width:100%; height:30px; transtion:height 1s; -webkit-transtion:height 1s; -moz-transtion:height 1s; }
	.ipt-info { display:none; margin:15px -15px;}
    .Unqualified, .qualified { margin-right:10px; }
 	.input-box { position: relative; display: inline-block; }
    .input-box input { background-color: transparent;  background-image: none; border: 1px solid #ccc; border-radius: 4px; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset; color: #555;  display: block;  font-size: 14px; line-height: 1.42857; padding: 6px 6px; transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;  width: 200px; display: inline; position: relative; z-index: 1;}
    .tip-l {  width: 0;  height: 0; border-left: 5px solid transparent; border-right: 5px solid transparent; border-top: 10px solid #555; display: inline-block; right: 10px; z-index: 0;  position: absolute;  top: 12px; }
    .dropdown { position: absolute; top: 32px; left: 0px; width: 200px; background-color: #FFF; border: 1px solid #23a8ce; border-top: 0; box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;  z-index: 999; padding: 0;  margin: 0; }
    .dropdown li { display: block; line-height: 1.42857; padding: 0 6px; min-height: 1.2em; cursor: pointer; }
    .dropdown li:hover {  background-color: #23a8ce;  color: #FFF; }
    .colSm {  display:block; float:left; width:200px; }
    .padding-right-0 { margin-left:10%; width:323px; border:1px solid #eee; }
    .delete { right:0; display:none;}
    .cardFront-div { height:176px;}
    .cardFront-div #uploadFile { top:0;width:100% !important;height:200px;left:0;position:absolute;}
    .btnBank { width:70px !important;}
    /*弹框头部固定*/
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:10000; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    .modal-body { background-color:#FFF !important; margin-top:50px; height:100%; padding:15px 37px 15px 53px;}  
    #sqImg { top:0px; margin-top:-176px; height:auto;}  
    /*左右导航样式*/
    .leftNav { position:fixed;top:15px;left:4px;z-index:999; width:40px;height:100%; cursor:pointer;}
	.leftNav span { width: 24px; height: 24px; position: absolute;top:50%;margin-left:10px; border-right: 4px solid #999;  border-top: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
	/*父母/配偶输入框*/
	.parentsUnitNameRow ,.spouseUnitNameRow { display:none;}
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
		<a id="toPassport" class="leftNav" onclick="passportBtn();">
			<span></span>
		</a>
		<form id="passportInfo">
			<input id="orderProcessType" name="orderProcessType" type="hidden" value="${obj.orderProcessType }">
			<div class="modal-header">
				<span class="heading">签证信息</span>
				<input type="hidden" value="${obj.visaInfo.applicantid }" name="applicantId"/>
				<input type="hidden" value="${obj.orderid }" name="orderid"/>
				<input type="hidden" id="isTrailOrder" name="isTrailOrder" value="${obj.isTrailOrder }"/>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
			</div>
			<div class="modal-body">
			<div class="ipt-info">
				</div>
				<div class="tab-content row">
					<!-- 结婚状况 -->
					<div class="info">
						<div class="info-head">婚姻状况 </div>
						<div class="info-body-from cf ">
							<div class="row colSm">
								<div class="">
									<div class="form-group">
										<select id="marryStatus" name="marryStatus" class="form-control input-sm selectHeight">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.marryStatusEnum}">
												<option value="${map.key}" ${map.key==obj.marryStatus?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							
							<div class="info-imgUpload front has-error" style="width:40% !important; float: left; margin-left:10%; ">
								<div class="col-sm-4 padding-right-0" id="borderColor">
									<div class="cardFront-div">
										<span>上传结婚证/离婚证</span>
										<input id="marryUrl" name="marryUrl" type="hidden" value="${obj.marryUrl }"/>
										<input id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
									</div>
									<img id="sqImg" alt="" src="${obj.marryUrl }" >
									<i class="delete" onclick="deleteApplicantFrontImg();"></i>
								</div>
								<div class="col-xs-6 front has-error" style="clear:both;width:320px; height:30px; border:0 !important; color:red; margin-left:10%;">
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
											<c:forEach var="map" items="${obj.mainOrVice}">
												<option value="${map.key}" ${map.key==obj.visaInfo.isMainApplicant?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-4 applymain">
									<div class="form-group">
										<label><span>*</span>备注</label>
										</br>
										<div class="input-box">
											<input type="text" id="relationRemark" name="relationRemark" class="input" value="${obj.visaInfo.relationRemark}">
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
											<!-- <input id="mainApplicant" name="mainApplicant" type="text" class="form-control input-sm" placeholder=" " /> -->
											<select id="mainApplicant" name="mainApplicant" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.mainApply}">
												<option value="${map.id}" ${map.id==obj.mainApplicant.id?'selected':'' } >${map.applyname}</option>
											</c:forEach>
										</select>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>与主申请人关系</label>
											</br>
										<div class="input-box">
											<input type="text" id="mainRelation" name="mainRelation" class="input" value="${obj.visaInfo.mainRelation}">
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
							<div class="row body-from-input applymain"><!-- 签证类型 -->
								<div class="col-sm-3" style="padding-right:0px !important;">
									<div class="form-group">
										<label><span>*</span>签证类型：</label>
										<select id="visatype" class="form-control input-sm">
											<c:forEach var="map" items="${obj.mainsalevisatypeenum}">
												<c:choose>
													<c:when test="${map.key eq obj.jporderinfo.visaType}">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<c:choose>
									<c:when test="${obj.jporderinfo.visaType != 1 }">
										<div class="col-sm-9" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-9 none" id="visacounty">
									</c:otherwise>
								</c:choose>
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="visacounty" type="button" value="冲绳县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="青森县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="宫城县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="山形县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="福鸟县" class="btn btn-sm btnState">
											</div>
										</div>
							</div><!-- end 签证类型 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType != 1 }">
									<div class="row body-from-input" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:otherwise>
							</c:choose>
										<div class="col-sm-3" style="padding-right:0px !important;">
											<div class="form-group">
												<label><span>*</span>过去三年是否访问过：</label>
												<select id="isVisit" class="form-control input-sm">
													<c:forEach var="map" items="${obj.isyesornoenum}">
														<c:choose>
															<c:when test="${map.key eq obj.jporderinfo.isVisit}">
																<option value="${map.key}" selected="selected">${map.value}</option>
															</c:when>
															<c:otherwise>
																<option value="${map.key}">${map.value}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-9">
										<c:choose>
											<c:when test="${obj.jporderinfo.isVisit == 1 }">
												<div id="threexian" class="form-group viseType-btn">
											</c:when>
											<c:otherwise>
												<div id="threexian" class="form-group viseType-btn none">
											</c:otherwise>
										</c:choose>
												<label style="display:block;">&nbsp;</label>
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
										</div>
									</div><!-- end 过去三年是否访问过 -->
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
										<%-- <select id="trip" name="sameMainTrip" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainTrip?'selected':''}>${map.value}</option>
											</c:forEach>
										</select> --%>
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
										<!-- <input id="occupation"  name="occupation" type="text" class="form-control input-sm" placeholder=" " /> -->
										<select id="careerStatus" name="careerStatus" class="form-control input-sm selectHeight">
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.jobStatusEnum}">
												<option value="${map.key}" ${map.key==obj.workJp.careerStatus?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-4 preSchool">
									<div class="form-group">
										<label id="schoolName"><span>*</span>单位名称</label>
										<input id="name" name="name" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.name }"/>
									</div>
								</div>
								<div class="col-sm-4 preSchool">
									<div class="form-group">
										<label id="schoolTelephone"><span>*</span>单位电话</label>
										<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.telephone }"/>
									</div>
								</div>
							</div><!-- end 我的职业/单位名称/单位电话 -->
							<div class="row"><!-- 单位地址 -->
								<div class="col-sm-8 preSchool">
									<div class="form-group">
										<label id="schoolAddress"><span>*</span>单位地址</label>
										<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " value="${obj.workJp.address }"/>
									</div>
								</div>
							</div>
							<!-- end 单位地址 -->
							<!-- 父母单位名称/配偶单位名称 -->
							<div class="row parentsUnitNameRow">
								<div class="col-sm-8 preSchool">
									<div class="form-group">
										<label id="parentsUnitNameLabel"><span>*</span>父母单位名称</label>
										<input id="parentsUnitName" name="" type="text" class="form-control input-sm" value=""/>
									</div>
								</div>
							</div>
							<div class="row spouseUnitNameRow">
								<div class="col-sm-8 preSchool">
									<div class="form-group">
										<label id="spouseUnitNameLabel"><span>*</span>配偶单位名称</label>
										<input id="spouseUnitName" name="" type="text" class="form-control input-sm" value=""/>
									</div>
								</div>
							</div>
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
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainWealth?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
						</div>
						<div class="info-body-from finance-btn wealthmain">
							<input id="depositType" name="wealthType" value="银行存款" type="button" class="btn btn-sm btnState btnBank" />
							<input id="vehicleType" name="wealthType" value="车产" type="button" class="btn btn-sm btnState" />
							<input id="housePropertyType" name="wealthType" value="房产" type="button" class="btn btn-sm btnState" />
							<input id="financialType" name="wealthType" value="理财" type="button" class="btn btn-sm btnState" />
						</div>
						<div class="info-body-from  clone-module cf deposit">
							<div class="row body-from-input"><!-- 银行存款 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>银行存款</label>
										<input id="" name="" type="text" class="form-control input-sm" value="银行存款" disabled />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="deposit" name="deposit" type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left; margin:40px 0 0 -23px;">
								万
								</div>
							</div><!-- end 银行存款 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 deposits" style="display: none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockdeposit" data-bv-validator="notEmpty" data-bv-for="deposit" data-bv-result="IVVALID" >银行存款不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf vehicle">
							<div class="row body-from-input"><!-- 车产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>车产</label>
										<input id="" name=""  type="text" class="form-control input-sm" value="车产" disabled />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder=" "/>
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -23px;">
								例如：大众速腾
								</div>
							</div><!-- end 车产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 vehicles" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockvehicle" data-bv-validator="notEmpty" data-bv-for="vehicle" data-bv-result="IVVALID" >车产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf houseProperty">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>房产</label>
										<input id="" name="" type="text" class="form-control input-sm" value="房产" disabled/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="houseProperty" name="houseProperty" type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -23px;">
								平米
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 housePropertys" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockhouseProperty" data-bv-validator="notEmpty" data-bv-for="houseProperty" data-bv-result="IVVALID" >房产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf financial">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>理财</label>
										<input id="" name="" type="text" class="form-control input-sm" value="理财" disabled/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="financial" name="financial" type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -23px;">
								万
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-6 financials" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
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
		$(function() {
			
			$('#passportInfo').bootstrapValidator('validate');
			
			var remark = $("#visaRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			}
			
			var marry = $("#marryUrl").val();
			if(marry != ""){
				$(".delete").css("display","block");
			}else{
				$(".delete").css("display","none");
			}
			
			//婚姻状况为单身和丧偶时没有上传图片接口
			var marryStatus = $("#marryStatus").val();
			if(marryStatus == 3 || marryStatus == 4 || !marryStatus){
				$(".info-imgUpload").hide();
			}
			$("#marryStatus").change(function(){
				var status = $(this).val();
				if(status == 3 || status == 4 || !status){
					$(".info-imgUpload").hide();
					$('#marryUrl').val("");
					$('#sqImg').attr('src', "");
					$("#uploadFile").siblings("i").css("display","none");
				}else{
					$(".info-imgUpload").show();
				}
			});
			
			var career = $("#careerStatus").val();
			if(career == 4){
				$("#schoolName").html("<span>*</span>学校名称");
				$("#schoolTelephone").html("<span>*</span>学校电话");
				$("#schoolAddress").html("<span>*</span>学校地址");
			}
			if(career == 5){
				$(".preSchool").hide();
				$("#name").val("").change();
				$("#telephone").val("").change();
				$("#address").val("").change();
			}
			$("#careerStatus").change(function(){
				$("#name").val("").change();
				$("#telephone").val("").change();
				$("#address").val("").change();
				var career = $(this).val();
				if(career == 5){
					$(".preSchool").hide();
				}else if(career == 4){
					$(".preSchool").show();
					$("#schoolName").html("<span>*</span>学校名称");
					$("#schoolTelephone").html("<span>*</span>学校电话");
					$("#schoolAddress").html("<span>*</span>学校地址");
				}else{
					$(".preSchool").show();
					$("#schoolName").html("<span>*</span>单位名称");
					$("#schoolTelephone").html("<span>*</span>单位电话");
					$("#schoolAddress").html("<span>*</span>单位地址");
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
			
			var wealthType = '${obj.wealthJp}';
			console.log(wealthType);
			if(wealthType){
				$('[name=wealthType]').each(function(){
					var wealth = $(this);
					$.each(JSON.parse(wealthType), function(i, item){     
						if(item.type == wealth.val()){
							if(wealth.val() == "银行存款"){
								$(".deposit").css("display","block");
								wealth.addClass("btnState-true");
								$("#deposit").val(item.details);
							}
							if(wealth.val() == "车产"){
								$(".vehicle").css("display","block");
								wealth.addClass("btnState-true");
								$("#vehicle").val(item.details);
							}
							if(wealth.val() == "房产"){
								$(".houseProperty").css("display","block");
								wealth.addClass("btnState-true");
								$("#houseProperty").val(item.details);
							}
							if(wealth.val() == "理财"){
								$(".financial").css("display","block");
								wealth.addClass("btnState-true");
								$("#financial").val(item.details);
							}
						}
						});
					});
				
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
					$(".wealthmain").show();
					$('[name=wealthType]').each(function(){
						$(this).removeClass("btnState-true");
					});
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
						$("#deposit").val("");
						if(userType == 2){
							$(".help-blockdeposit").attr("data-bv-result","INVALID");  
						    $(".deposits").css({"display":"block"});
						    $(".deposits").attr("class", "col-xs-6 deposits has-error");
						    $("#deposit").attr("style", "border-color:#ff1a1a");
						}
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
						if(userType == 2){
					        $(".help-blockvehicle").attr("data-bv-result","INVALID");  
					        $(".vehicles").css({"display":"block"});
					        $(".vehicles").attr("class", "col-xs-6 vehicles has-error");
					        $("#vehicle").attr("style", "border-color:#ff1a1a");
						}
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
						$("#houseProperty").val("");
						if(userType == 2){
							$(".help-blockhouseProperty").attr("data-bv-result","INVALID");  
						    $(".housePropertys").css({"display":"block"});
						    $(".housePropertys").attr("class", "col-xs-6 housePropertys has-error");
						    $("#houseProperty").attr("style", "border-color:#ff1a1a");
						}
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
						$("#financial").val("");
						if(userType == 2){
							$(".help-blockfinancial").attr("data-bv-result","INVALID");  
						    $(".financials").css({"display":"block"});
						    $(".financials").attr("class", "col-xs-6 financials has-error");
						    $("#financial").attr("style", "border-color:#ff1a1a");
						}
						//$("#financial").placeholder("万");
					}
				}
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
	                  var applicantid = '${obj.visaInfo.applicantid}';
	                  if(received_msg){
		                  var receiveMessage = JSON.parse(received_msg);
		                  if(receiveMessage.messagetype == 3 && receiveMessage.applicantid == applicantid){
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
		$("#addBtn").click(function(){
			save(1);
		});
		
		
		//保存
		function save(status){
			var applicantid = '${obj.applicant.id}';
			var orderid = '${obj.orderid}';
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			if(status == 1){
				if (!bootstrapValidator.isValid()) {
					return;
				}
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
			var applicVal = $("#applicant").val();
			//主申请人时，是否同主申请人设置为空，不然默认为1
			if(applicVal == 1){
				$("#wealth").val(0);
			}
			//绑定签证城市
			var visacounty = "";
			$('[name=visacounty]').each(function(){
				if($(this).hasClass('btnState-true')){
					visacounty += $(this).val() + ',';
				}
			});
			if(visacounty){
				visacounty = visacounty.substr(0,visacounty.length-1);
			}
			//绑定三年城市
			var threecounty = "";
			$('[name=threecounty]').each(function(){
				if($(this).hasClass('btnState-true')){
					threecounty += $(this).val() + ',';
				}
			});
			if(threecounty){
				threecounty = threecounty.substr(0,threecounty.length-1);
			}
			
			var visatype = $('#visatype').val();
			var isVisit = $('#isVisit').val();
			var passportInfo = $.param({"wealthType":wealthType,'visatype':visatype,'visacounty':visacounty,'isVisit':isVisit,'threecounty':threecounty}) + "&" +  $("#passportInfo").serialize();
				layer.load(1);
				$.ajax({
					type: 'POST',
					async: false,
					data : passportInfo,
					url: '${base}/admin/simple/saveEditVisa.html',
					success :function(data) {
						layer.closeAll("loading");
						console.log(JSON.stringify(data));
						if(status == 1){
							parent.successCallBack(1);
							closeWindow();
						}else if(status == 2){
							socket.onclose();
							window.location.href = '/admin/simple/updateApplicant.html?applicantid='+applicantid+'&orderid='+orderid;
						}
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
							$(".delete").css("display","block");
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
		
		function deleteApplicantFrontImg(){
			$('#marryUrl').val("");
			$('#sqImg').attr('src', "");
			$(".delete").css("display","none");
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.cancelCallBack(1);
		}
		function passportBtn(){
			save(2);
		}
		//签证类型
		var threecounty = '${obj.jporderinfo.visaCounty}';
		if(threecounty){
			var threecountys = threecounty.split(",");
			for (var i = 0; i < threecountys.length; i++) {
				$('[name=visacounty]').each(function(){
					if(threecountys[i] == $(this).val()){
						$(this).addClass('btnState-true');
					}
				});
			}
		}
		var threecounty = '${obj.jporderinfo.threeCounty}';
		if(threecounty){
			var threecountys = threecounty.split(",");
			for (var i = 0; i < threecountys.length; i++) {
				$('[name=threecounty]').each(function(){
					if(threecountys[i] == $(this).val()){
						$(this).addClass('btnState-true');
					}
				});
			}
		}
		
		//签证类型  按钮的点击状态
		$(".viseType-btn input").click(function(){
			if($(this).hasClass('btnState-true')){
				$(this).removeClass('btnState-true');
			}else{
				$(this).addClass('btnState-true');
				var btnInfo=$(this).val();//获取按钮的信息
				console.log(btnInfo);
			}
		});
		$('#visatype').change(function(){
			var thisval = $(this).val();
			if(thisval != 1){
				$('#visacounty').show();
				$('#threefangwen').show();
			}else{
				$('#visacounty').hide();
				$('#threefangwen').hide();
			}
		});

		$('#isVisit').change(function(){
			var thisval = $(this).val();
			if(thisval == 1){
				$('#threexian').show();
			}else{
				$('#threexian').hide();
			}
		});
	</script>
</body>
</html>
