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
	input[type="file"] { z-index:99999;}
	#sqImg { z-index:999999;}
	.delete { z-index:1000000;}
	.info-imgUpload {width: 100%;}
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
    .modal-header { position:fixed; top:0;left:0; width:100%; height:50px; line-height:50px; background:#FFF; z-index:10000000; padding:0px 15px;}
    .btn-margin { margin-top:10px;}
    .modal-body { background-color:#FFF !important; margin-top:50px; height:100%; padding:15px 37px 15px 53px;}  
    #sqImg { top:0px; margin-top:-176px; height:auto;}  
    /*左右导航样式*/
    .leftNav { position:fixed;top:15px;left:4px;z-index:999; width:40px;height:100%; cursor:pointer;}
	.leftNav span { width: 24px; height: 24px; position: absolute;top:50%;margin-left:10px; border-right: 4px solid #999;  border-top: 4px solid #999;  -webkit-transform: translate(0,-50%) rotate(-135deg);  transform: translate(0,-50%) rotate(-135deg);}
	/*父母/配偶输入框*/
	.unitNameRow { display:block;}
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
				<input type="hidden" name="userType" value="${obj.userType }"/> 
				<input type="hidden" value="${obj.visaInfo.applicantId }" name="applicantId"/>
				<input type="hidden" value="${obj.isOrderUpTime }" name="isOrderUpTime"/>
				<input type="hidden" value="${obj.orderid }" name="orderid"/>
				<input type="hidden" value="${obj.addApply }" name="addApply"/>
				<input type="hidden" id="isTrailOrder" name="isTrailOrder" value="${obj.isTrailOrder }"/>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
				<c:choose>
						<c:when test="${(obj.orderStatus > 4 && obj.orderStatus < 9 )|| (obj.orderStatus ==88 && obj.isTrailOrder==1)}"> 
							<input id="unqualifiedBtn" style="display:none" type="button"  class="btn btn-primary pull-right btn-sm btn-right Unqualified btn-margin" value="不合格" />
							<input id="qualifiedBtn" style="display:none" type="button"  class="btn btn-primary pull-right btn-sm btn-right qualifiedBtn btn-margin" value="合格" />
						</c:when>
						<c:otherwise> 
						</c:otherwise>
					</c:choose>
			</div>
			<div class="modal-body">
			<div class="ipt-info">
					<input id="visaRemark" name="visaRemark" placeholder="请输入不合格原因" type="text" value="${obj.unqualified.visaRemark }" class="NoInfo" />
				</div>
				<div class="tab-content row">
					<!-- 结婚状况 -->
					<div class="info">
						<div class="info-head">结婚状况 </div>
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
							
							<div class="info-imgUpload front has-error none" style="width:40% !important; float: left; margin-left:10%; ">
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
							<div class="row unitNameRow">
								<div class="col-sm-8 preSchool">
									<div class="form-group">
										<label id="unitNameLabel"></label>
										<input id="unitName" name="unitName" type="text" class="form-control input-sm" value="${obj.workJp.unitName }"/>
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
										<input id="" name="" type="text" class="form-control input-sm" readonly="readonly" value="银行存款" />
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
										<input id="" name="" type="text" class="form-control input-sm" readonly="readonly" value="车产" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder="大众速腾 "/>
									</div>
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
										<input id="" name="" type="text" class="form-control input-sm" readonly value="房产" />
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
										<input id="" name="" type="text" class="form-control input-sm" readonly="readonly" value="理财" />
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
		var userType = '${obj.userType}';
		var isTrail = '${obj.isTrail}';
		var wealthType = '${obj.wealthJp}';
		var sessionId = '${obj.sessionid}';
		var localAddr = '${obj.localAddr}';
		var localPort = '${obj.localPort}';
		var websocketaddr = '${obj.websocketaddr}';
		var applicantId = '${obj.applicant.id}';
		var orderid = '${obj.orderid}';
		var orderJpId = '${obj.orderJpId}';
		var infoType = '${obj.infoType}';
		var addApply = '${obj.addApply}';
		var orderProcessType = '${obj.orderProcessType}';
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
	<script type="text/javascript" src="${base}/admin/orderJp/visa.js"></script>
	<script type="text/javascript">
		
	</script>


</body>
</html>
