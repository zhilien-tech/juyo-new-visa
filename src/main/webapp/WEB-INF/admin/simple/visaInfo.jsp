	<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<title>签证信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<link rel="stylesheet" href="${base}/references/public/css/style.css?v=<%=System.currentTimeMillis() %>">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simpleVisaInfo.css?v=<%=System.currentTimeMillis() %>">
	<style type="text/css">
		.clearConnectBtn{
		    position: fixed;
		    top: 0;
		    right: 15px;
		    background:red;
		}

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
				<!-- <input type="button" class="clearConnectBtn btn btn-primary pull-right btn-sm btn-margin" value="取消" /> -->
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" />
				
			</div> 
			
			<div class="modal-main"></div>
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
											<input type="text" id="relationRemark" autocomplete="off" name="relationRemark" class="input" value="${obj.visaInfo.relationRemark}">
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
									<%-- <div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>主申请人</label>
											<!-- <input id="mainApplicant" name="mainApplicant" type="text" class="form-control input-sm" placeholder=" " /> -->
											<select id="mainApplicant" name="mainApplicant" class="form-control input-sm selectHeight">
											<c:forEach var="map" items="${obj.mainApply}">
												<option value="${map.id}" ${map.id==obj.mainApplicant.id?'selected':'' } >${map.applyname}</option>
											</c:forEach>
										</select>
										</div>
									</div> --%>
									<div class="col-sm-4">
										<div class="form-group">
											<label><span>*</span>与主申请人关系</label>
											</br>
										<div class="input-box">
											<input type="text" id="mainRelation" autocomplete="off" name="mainRelation" class="input" value="${obj.visaInfo.mainRelation}">
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
								
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>出境记录</label>
										</br>
										<select id="outboundrecord" name="outboundrecord" class="form-control input-sm selectHeight">
											<option value="良好" ${obj.outboundrecord=="良好"?'selected':''}>良好</option>
											<option value="无" ${obj.outboundrecord=="无"?'selected':''}>无</option>
										</select>
									</div>
								</div>
								
							</div><!-- end 申请人/备注-->
							<div class="row body-from-input applymain"><!-- 签证类型 -->
								<input type="hidden" id="visatype" value="${obj.jporderinfo.visaType}">
								<%-- <div class="col-sm-3 none" style="padding-right:0px !important;">
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
								</div> --%>
								<c:choose>
									<c:when test="${obj.jporderinfo.visaType != 2 }">
										<div class="col-sm-9 none" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-9 none" id="visacounty">
									</c:otherwise>
								</c:choose>
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input id="csvisacounty" name="visacountys" type="text" value="冲绳县" class="btn btn-sm btnState">
												<input id="qsvisacounty" name="visacountys" type="text" value="青森县" class="btn btn-sm btnState">
												<input id="ysvisacounty" name="visacountys" type="text" value="岩手县" class="btn btn-sm btnState">
												<input id="gcvisacounty" name="visacountys" type="text" value="宫城县" class="btn btn-sm btnState">
												<input id="qtvisacounty" name="visacountys" type="text" value="秋田县" class="btn btn-sm btnState">
												<input id="sxvisacounty" name="visacountys" type="text" value="山形县" class="btn btn-sm btnState">
												<input id="fdvisacounty" name="visacountys" type="text" value="福岛县" class="btn btn-sm btnState">
											</div>
										</div>
							</div><!-- end 签证类型 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType > 6 && obj.jporderinfo.visaType < 14 }">
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
									<c:choose>
											<c:when test="${obj.jporderinfo.isVisit == 1 }">
												<div class="alignment cf viseType-btn">
											</c:when>
											<c:otherwise>
												<div class="alignment cf viseType-btn">
											</c:otherwise>
										</c:choose>
										
											<div class="row body-from-input">
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次出行时间</label>
														<c:choose>
															<c:when test="${empty obj.applyorderinfo.laststartdate and obj.jporderinfo.laststartdate != '' }">
																<input id="laststartdate" name="laststartdate" autocomplete="off" style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.jporderinfo.laststartdate }" pattern="yyyy-MM-dd" />"/>
															</c:when>
															<c:otherwise>
																<input id="laststartdate" name="laststartdate" autocomplete="off" style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.applyorderinfo.laststartdate }" pattern="yyyy-MM-dd" />"/>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次停留天数</label>

														<c:choose>
															<c:when test="${empty obj.applyorderinfo.laststayday and obj.jporderinfo.laststayday != '' }">
																<input id="laststayday" name="laststayday" autocomplete="off" style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm" value="${obj.jporderinfo.laststayday }"/>
															</c:when>
															<c:otherwise>
																<input id="laststayday" name="laststayday" autocomplete="off" style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm" value="${obj.applyorderinfo.laststayday }"/>
															</c:otherwise>
														</c:choose>														
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次返回时间</label>
														<c:choose>
															<c:when test="${empty obj.applyorderinfo.lastreturndate and obj.jporderinfo.lastreturndate != '' }">
																<input id="lastreturndate" name="lastreturndate" autocomplete="off" style="left:605.328px !important; color:#555 !important;border-color:#d2d6de !important;" type="text" class="lastreturndate form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.jporderinfo.lastreturndate }" pattern="yyyy-MM-dd" />"/>
															</c:when>
															<c:otherwise>
																<input id="lastreturndate" name="lastreturndate" autocomplete="off" style="left:605.328px !important; color:#555 !important;border-color:#d2d6de !important;" type="text" class="lastreturndate form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.applyorderinfo.lastreturndate }" pattern="yyyy-MM-dd" />"/>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
										</div>	
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
										<label><span>*</span>类型</label>
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
								<!-- 父母单位名称/配偶单位名称 -->
								<div class="col-sm-4">
									<div class="form-group">
										<label><span>*</span>职业</label>
										<input id="position" name="position"  type="text" class="form-control input-sm" value="${obj.workJp.position }"/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="unitNameLabel"><span></span>配偶职业</label>
										<input id="unitName" name="unitName" type="text" class="form-control input-sm" placeholder="如无职业，不必填写" value="${obj.unitName }"/>
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
											<c:forEach var="map" items="${obj.isOrNo}">
												<option value="${map.key}" ${map.key==obj.visaInfo.sameMainWealth?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
						</div>
						<div class="info-body-from finance-btn wealthmain">
							<input id="bankflowType" name="wealthType" value="银行流水" type="button" class="btn btn-sm btnState btnBank" />
							<input id="vehicleType" name="wealthType" value="车产" type="button" class="btn btn-sm btnState" />
							<input id="housePropertyType" name="wealthType" value="房产" type="button" class="btn btn-sm btnState" />
							<input id="financialType" name="wealthType" value="理财" type="button" class="btn btn-sm btnState" />
							<input id="certificateType" name="wealthType" value="在职证明" type="button" class="btn btn-sm btnState btnBank" />
							<input id="depositType" name="wealthType" value="银行存款" type="button" class="btn btn-sm btnState btnBank" />
							<input id="taxbillType" name="wealthType" value="税单" type="button" class="btn btn-sm btnState" />
							<input id="taxproofType" name="wealthType" value="完税证明" type="button" class="btn btn-sm btnState btnBank" />
							<input id="goldcardType" name="wealthType" value="银行金卡" type="button" class="btn btn-sm btnState btnBank" />
							<input id="readstudentType" name="wealthType" value="特定高校在读生" type="button" class="btn btn-sm btnState btnReadstudent" />
							<input id="graduateType" name="wealthType" value="特定高校毕业生" type="button" class="btn btn-sm btnState btnGraduate" />
							<!-- <input id="financialType" name="wealthType" value="其他" type="button" class="btn btn-sm btnState" /> -->
						</div>
						<div class="info-body-from  clone-module cf bankflow">
							<div class="row body-from-input"><!-- 银行流水 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>银行流水</label>
										<input id="bankflowfree" autocomplete="off" name="bankflowfree" type="text" class="form-control input-sm" value="银行流水"  />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="bankflow" name="bankflow" autocomplete="off" type="text" class="form-control input-sm" placeholder="工资对账单收入"  />
									</div>
								</div>
								<div style="float:left; margin:40px 0 0 -10px;">
								万
								</div>
							</div><!-- end 银行流水 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 bankflows" style="display: none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockbankflow" data-bv-validator="notEmpty" data-bv-for="bankflow" data-bv-result="IVVALID" >银行流水不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf vehicle">
							<div class="row body-from-input"><!-- 车产 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>车产</label>
										<input id="vehiclefree" name="vehiclefree" autocomplete="off" type="text" class="form-control input-sm" value="车产"  />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="vehicle" name="vehicle" type="text" autocomplete="off" class="form-control input-sm" placeholder="例如:大众速腾"/>
									</div>
								</div>
								<!-- <div style="float:left;  margin:40px 0 0 -23px;">
								例如：大众速腾
								</div> -->
							</div><!-- end 车产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 vehicles" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockvehicle" data-bv-validator="notEmpty" data-bv-for="vehicle" data-bv-result="IVVALID" >车产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf houseProperty">
							<div class="row body-from-input"><!-- 房产 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>房产</label>
										<input id="housePropertyfree" autocomplete="off" name="housePropertyfree" type="text" class="form-control input-sm" value="房产" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="houseProperty" name="houseProperty" autocomplete="off"  type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								平米
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 housePropertys" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockhouseProperty" data-bv-validator="notEmpty" data-bv-for="houseProperty" data-bv-result="IVVALID" >房产不能为空</small>
						</div>
						<!-- 提示End -->
						<div class="info-body-from clone-module cf financial">
							<div class="row body-from-input"><!-- 理财 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>理财</label>
										<input id="financialfree" autocomplete="off" name="financialfree" type="text" class="form-control input-sm" value="理财" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="financial" name="financial" autocomplete="off" type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								万
								</div>
							</div><!-- end 理财 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 financials" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockfinancial" data-bv-validator="notEmpty" data-bv-for="financial" data-bv-result="IVVALID" >理财不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 在职证明 -->
						<div class="info-body-from clone-module cf certificate">
							<div class="row body-from-input"><!-- 在职证明 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>在职证明</label>
										<input id="certificatefree" name="certificatefree" autocomplete="off" type="text" class="form-control input-sm" value="在职证明" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="certificate" name="certificate" type="text" autocomplete="off" class="form-control input-sm" placeholder="年收入"  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								万
								</div>
							</div><!-- end 在职证明 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 certificates" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockcertificate" data-bv-validator="notEmpty" data-bv-for="certificate" data-bv-result="IVVALID" >在职证明不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 银行存款 -->
						<div class="info-body-from clone-module cf deposit">
							<div class="row body-from-input"><!-- 银行存款 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>银行存款</label>
										<input id="depositfree" name="depositfree" autocomplete="off" type="text" class="form-control input-sm" value="银行存款"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="deposit" name="deposit" type="text" autocomplete="off" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								万
								</div>
							</div><!-- end 银行存款 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 deposits" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockdeposit" data-bv-validator="notEmpty" data-bv-for="deposit" data-bv-result="IVVALID" >银行存款不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 税单 -->
						<div class="info-body-from clone-module cf taxbill">
							<div class="row body-from-input"><!-- 税单 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>税单</label>
										<input id="taxbillfree" name="taxbillfree" type="text" autocomplete="off" class="form-control input-sm" value="税单"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="taxbill" name=taxbill type="text" autocomplete="off" class="form-control input-sm" placeholder="年收入"  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								万
								</div>
							</div><!-- end 税单 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 taxbills" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blocktaxbill" data-bv-validator="notEmpty" data-bv-for="taxbill" data-bv-result="IVVALID" >税单不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 完税证明 -->
						<div class="info-body-from clone-module cf taxproof">
							<div class="row body-from-input"><!-- 完税证明 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>完税证明</label>
										<input id="taxprooffree" name="taxprooffree" autocomplete="off" type="text" class="form-control input-sm" value="完税证明"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="taxproof" name="taxproof" type="text" autocomplete="off" class="form-control input-sm" placeholder="年缴税"  />
									</div>
								</div>
								<div style="float:left;  margin:40px 0 0 -10px;">
								元
								</div>
							</div><!-- end 完税证明 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 taxproofs" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blocktaxproof" data-bv-validator="notEmpty" data-bv-for="taxproof" data-bv-result="IVVALID" >完税证明不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 银行金卡 -->
						<div class="info-body-from clone-module cf goldcard">
							<div class="row body-from-input"><!-- 银行金卡-->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>银行金卡</label>
										<input id="goldcardfree" name="goldcardfree" autocomplete="off" type="text" class="form-control input-sm" value="银行金卡"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="goldcard" name="goldcard" type="text" autocomplete="off" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
								<!-- <div style="float:left;  margin:40px 0 0 -10px;">
								元
								</div> -->
							</div><!-- end 银行金卡 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 goldcards" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockgoldcard" data-bv-validator="notEmpty" data-bv-for="goldcard" data-bv-result="IVVALID" >银行金卡不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 特定高校在读生 -->
						<div class="info-body-from clone-module cf readstudent">
							<div class="row body-from-input"><!-- 特定高校在读生 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>特定高校在读生</label>
										<input id="readstudentfree" name="readstudentfree" autocomplete="off" type="text" class="form-control input-sm" value="特定高校在读生" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="readstudent" name="readstudent" type="text" autocomplete="off" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
							</div><!-- end 特定高校在读生 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 readstudents" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockreadstudent" data-bv-validator="notEmpty" data-bv-for="readstudent" data-bv-result="IVVALID" >特定高校在读生不能为空</small>
						</div>
						<!-- 提示End -->
						<!-- 特定高校毕业生 -->
						<div class="info-body-from clone-module cf graduate">
							<div class="row body-from-input"><!-- 特定高校毕业生 -->
								<div class="col-sm-5">
									<div class="form-group">
										<label><span>*</span>特定高校毕业生</label>
										<input id="graduatefree" name="graduatefree" autocomplete="off" type="text" class="form-control input-sm" value="特定高校毕业生" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label>&nbsp;</label>
										<input id="graduate" name="graduate" autocomplete="off" type="text" class="form-control input-sm" placeholder=""  />
									</div>
								</div>
							</div><!-- end 房产 -->
							<i class="remove-btn delete-icon"></i>
						</div>
						<!-- 提示 -->
						<div class="col-xs-5 graduates" style="display:none;width:320px; height:30px; border:0 !important; color:red; margin-left:52%;">
							<small class="help-blockgraduate" data-bv-validator="notEmpty" data-bv-for="graduate" data-bv-result="IVVALID" >特定高校毕业生不能为空</small>
						</div>
						<!-- 提示End -->
						
					</div>
					<!-- end 财产信息 -->
					<!-- 在日拟入住九点名称或友人姓名及地址 -->
					<div class='info'>
						<div class="info-head">在日拟入住九点名称或友人姓名及地址</div>
						<div class="info-body-from cf ">
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanFriendName"><span>*</span>酒店名称或友人姓名</label>
										<input id="hotelname" name="hotelname" autocomplete="off" type="text" class="form-control input-sm" placeholder="参照'赴日予定表'" value="${obj.visaother.hotelname }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanTelNumber">电话</label>
										<input id="hotelphone" name="hotelphone" autocomplete="off" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.hotelphone }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanAddress">地址</label>
										<input id="hoteladdress" name="hoteladdress" autocomplete="off" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.hoteladdress }"/>
									</div>
								</div>
							</div>
						</div>	
					</div>
					<!-- end在日拟入住九点名称或友人姓名及地址 -->
					<!-- 在日担保人guarantee -->
					<div class='info'>
						<div class="info-head">在日担保人</div>
						<div class="info-body-from">
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="guaranteeHead"><span>*</span>申元保证书/姓名-拼音</label>
										<div class="guaranteeCB">
											 <input type="checkbox" value="1" ${obj.visaother.isname == 1?'checked':'' } id="guaranteeCBInput" name="" />
											 <label for="guaranteeCBInput"></label>
										</div>
										<input id="vouchname" name="vouchname" autocomplete="off" type="text" class="form-control input-sm guaranty" placeholder="参照'申元保证书" value="${obj.visaother.vouchname }"/>
										<%-- <input id="vouchname" name="vouchname" type="text" class="form-control input-sm guaranteeName" placeholder="姓名/拼音" value="${obj.visaother.vouchname }"/> --%>
									</div>
								</div>
								
								
								<div class="col-sm-4 hideLabel">
									<div class="form-group">
										<label id="guaranteeHead">电话</label>
										<input id="vouchphone" name="vouchphone" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.vouchphone }"/>
									</div>
								</div>
								<div class="col-sm-4 hideLabel">
									<div class="form-group">
										<label id="guaranteeHead">地址</label>
										<input id="vouchaddress" name="vouchaddress" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.vouchaddress }"/>
									</div>
								</div>
							</div>
							<!-- 下一行 -->
							<div class="row hideLabel">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="guaranteeHead">出生日期</label>
										<input id="vouchbirth" name="vouchbirth" type="text" class="form-control input-sm"  value="<fmt:formatDate value="${obj.visaother.vouchbirth }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="vouchsex" class="guaranteeHead">性别</label>
										<input id="" class="sexBoy sex" name="vouchsex" ${obj.visaother.vouchsex == '男'? 'checked':'' } type="radio" value="男"/><label class="sexInfo">男</label>
										<input id="" class="sexGirl sex" name="vouchsex" ${obj.visaother.vouchsex == '女'? 'checked':'' } type="radio" value="女"/><label class="sexInfo">女</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="guaranteeHead">与主申请人的关系</label>
										<div class="input-box">
											<input type="text" id="vouchmainrelation" name="vouchmainrelation" class="input" value="${obj.visaother.vouchmainrelation }">
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
							
							<!-- 下一行 -->
							<div class="row hideLabel">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="guaranteeHead">职业和职务</label>
										<input id="vouchjob" name="vouchjob" type="text" class="form-control input-sm" placeholder="" value="${obj.visaother.vouchjob }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="guaranteeHead">国籍(或公民身份)及签证种类</label>
										<input id="vouchcountry" name="vouchcountry" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.vouchcountry }"/>
									</div>
								</div>
								<%-- <c:choose>
									<c:when test="${obj.visaother.isname == 1}">
										<div class="col-sm-4">
											<div class="form-group">
												<label id="guaranteeHead">姓名拼音</label>
												<input id="vouchnameen" name="vouchnameen" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.vouchnameen }"/>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-sm-4 none vouchnameen">
											<div class="form-group">
												<label id="guaranteeHead">姓名拼音</label>
												<input id="vouchnameen" name="vouchnameen" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.vouchnameen }"/>
											</div>
										</div>
									</c:otherwise>
								</c:choose> --%>
							</div>
							
						</div>	
					</div>
					<!-- end在日担保人 -->
					<!-- 在日邀请人inviter -->
					<div class='info'>
						<div class="info-head">在日邀请人</div>
						<div class="info-body-from">
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead"><span>*</span>申元保证书/姓名-拼音</label>
										<div class="inviterCB">
											 <input type="checkbox" value="1" ${obj.visaother.isyaoqing == 1?'checked':'' } id="inviterCBInput" name="" />
											 <label for="inviterCBInput"></label>
										</div>
										<input id="invitename" autocomplete="off" name="invitename" type="text" class="form-control input-sm " placeholder="参照'申元保证书" value="${obj.visaother.invitename }"/>
										<%-- <input id="invitename" name="invitename" type="text" class="form-control input-sm inviterName" placeholder="姓名/拼音" value="${obj.visaother.invitename }"/> --%>
									</div>
								</div>
								<div class="col-sm-4 hideInviter">
									<div class="form-group">
										<label id="inviterHead">电话</label>
										<input id="invitephone" name="invitephone" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.invitephone }"/>
									</div>
								</div>
								<div class="col-sm-4 hideInviter">
									<div class="form-group">
										<label id="inviterHead">地址</label>
										<input id="inviteaddress" name="inviteaddress" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.inviteaddress }"/>
									</div>
								</div>
							</div>
							<!-- 下一行 -->
							<div class="row hideInviter">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead">出生日期</label>
										<input id="invitebirth" name="invitebirth" type="text" class="form-control input-sm"  value="<fmt:formatDate value="${obj.visaother.invitebirth }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead">性别</label>
										
										<input id="" name="invitesex" type="radio" class="sex" ${obj.visaother.invitesex == '男'? 'checked':'' } value="男"/><label class="sexInfo">男</label>
										<input id="" name="invitesex" type="radio" class="sex" ${obj.visaother.invitesex == '女'? 'checked':'' } value="女"/><label class="sexInfo">女</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead">与主申请人的关系</label>
										
										<div class="input-box">
											<input type="text" id="invitemainrelation" name="invitemainrelation" class="input" value="${obj.visaother.invitemainrelation }">
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
							
							<!-- 下一行 -->
							<div class="row hideInviter">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead">职业和职务</label>
										<input id="invitejob" name="invitejob" type="text" class="form-control input-sm" placeholder="" value="${obj.visaother.invitejob }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="inviterHead">国籍(或公民身份)及签证种类</label>
										<input id="invitecountry" name="invitecountry" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.invitecountry }"/>
									</div>
								</div>
							</div>
							
						</div>	
					</div>
					<!-- end在日邀请人 -->
					<!-- 其他信息 otherInfo -->
					<div class='info'>
						<div class="info-head">其他信息</div>
						<div class="info-body-from cf ">
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="otherInfoHead"><span>*</span>旅行社意见</label>
										<input id="traveladvice" autocomplete="off" name="traveladvice" type="text" class="form-control input-sm" placeholder="推荐" value="${obj.visaother.traveladvice }"/>
									</div>
								</div>
								
							</div>
						</div>	
					</div>
					<!-- end其他信息 -->
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
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>	
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>	
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script type="text/javascript" src="${base}/admin/orderJp/visaInfo.js"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			
			/* var laststartdate = '${obj.jporderinfo.laststartdate}';
			var laststayday = '${obj.jporderinfo.laststayday}';
			var lastreturndate = '${obj.jporderinfo.lastreturndate}';
			
			var newlaststartdate = '${obj.applyorderinfo.laststartdate}';
			var newlaststayday = '${obj.applyorderinfo.laststayday}';
			var newlastreturndate = '${obj.applyorderinfo.lastreturndate}';
			
			if(newlaststartdate == "" && laststartdate != ""){
				$("#laststartdate").val(laststartdate);
			}
			if(newlaststayday == "" && laststayday != ""){
				$("#laststayday").val(laststayday);
			}
			if(newlastreturndate == "" && lastreturndate != ""){
				$("#lastreturndate").val(lastreturndate);
			} */
			
			
			var visatype = $('#visatype').val();
			
			if(visatype == 7){//冲绳
				if(!$("#csvisacounty").hasClass('btnState-true')){
					$("#csvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 8){//宫城
				if(!$("#gcvisacounty").hasClass('btnState-true')){
					$("#gcvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 9){//福岛
				if(!$("#fdvisacounty").hasClass('btnState-true')){
					$("#fdvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 10){//岩手
				if(!$("#ysvisacounty").hasClass('btnState-true')){
					$("#ysvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 11){//青森
				if(!$("#qsvisacounty").hasClass('btnState-true')){
					$("#qsvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 12){//秋田
				if(!$("#qtvisacounty").hasClass('btnState-true')){
					$("#qtvisacounty").addClass('btnState-true');
				}
			}
			if(visatype == 13){//山形
				if(!$("#sxvisacounty").addClass('btnState-true')){
					$("#sxvisacounty").addClass('btnState-true');
				}
			}
			
			
			
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
			//删除——————————————————————————————————————————————
			//婚姻状况为单身和丧偶时没有上传图片接口
			var marryStatus = $("#marryStatus").val();
			if(marryStatus == 3 || marryStatus == 4 || !marryStatus){
				$(".info-imgUpload").hide();
				$("#unitNameLabel").html("<span></span>父母职业");
			}else{
				$(".info-imgUpload").show();
				if(marryStatus == 1){
					$("#unitNameLabel").html("<span></span>配偶职业");
				}else{
					$("#unitNameLabel").html("<span></span>父母职业");
				}
			}
			$("#marryStatus").change(function(){
				var status = $(this).val();
				if(status == 3 || status == 4 || !status){
					$("#unitNameLabel").html("<span></span>父母职业");
					$(".info-imgUpload").hide();
					$('#marryUrl').val("");
					$('#sqImg').attr('src', "");
					$("#uploadFile").siblings("i").css("display","none");
				}else{
					if(status == 1){
						$("#unitNameLabel").html("<span></span>配偶职业");
					}else{
						$("#unitNameLabel").html("<span></span>父母职业");
					}
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
					$(".bankflow").css("display","none");
					$(".certificate").css("display","none");
					$(".taxbill").css("display","none");
					$(".taxproof").css("display","none");
					$(".readstudent").css("display","none");
					$(".graduate").css("display","none");
					$(".goldcard").css("display","none");
				}
			});
			
			var wealthType = '${obj.wealthJp}';
			//console.log(wealthType);
			if(wealthType){
				$('[name=wealthType]').each(function(){
					var wealth = $(this);
					$.each(JSON.parse(wealthType), function(i, item){     
						if(item.type == wealth.val()){
							if(wealth.val() == "银行流水"){
								$(".bankflow").css("display","block");
								wealth.addClass("btnState-true");
								$("#bankflow").val(item.details);
								if(item.bankflowfree == "" || item.bankflowfree == null){
									$("#bankflowfree").val(item.type);
								}else{
									$("#bankflowfree").val(item.bankflowfree);
								}
							}
							if(wealth.val() == "车产"){
								$(".vehicle").css("display","block");
								wealth.addClass("btnState-true");
								$("#vehicle").val(item.details);
								if(item.vehiclefree == "" || item.vehiclefree == null){
									$("#vehiclefree").val(item.type);
								}else{
									$("#vehiclefree").val(item.vehiclefree);
								}
							}
							if(wealth.val() == "房产"){
								$(".houseProperty").css("display","block");
								wealth.addClass("btnState-true");
								$("#houseProperty").val(item.details);
								if(item.housepropertyfree == "" || item.housepropertyfree == null){
									$("#housePropertyfree").val(item.type);
								}else{
									$("#housePropertyfree").val(item.housepropertyfree);
								}
							}
							if(wealth.val() == "理财"){
								$(".financial").css("display","block");
								wealth.addClass("btnState-true");
								$("#financial").val(item.details);
								if(item.financialfree == "" || item.financialfree == null){
									$("#financialfree").val(item.type);
								}else{
									$("#financialfree").val(item.financialfree);
								}
							}
							if(wealth.val() == "银行存款"){
								$(".deposit").css("display","block");
								wealth.addClass("btnState-true");
								$("#deposit").val(item.details);
								if(item.depositfree == "" || item.depositfree == null){
									$("#depositfree").val(item.type);
								}else{
									$("#depositfree").val(item.depositfree);
								}
							}
							if(wealth.val() == "在职证明"){
								$(".certificate").css("display","block");
								wealth.addClass("btnState-true");
								$("#certificate").val(item.details);
								if(item.certificatefree == "" || item.certificatefree == null){
									$("#certificatefree").val(item.type);
								}else{
									$("#certificatefree").val(item.certificatefree);
								}
							}
							if(wealth.val() == "税单"){
								$(".taxbill").css("display","block");
								wealth.addClass("btnState-true");
								$("#taxbill").val(item.details);
								if(item.taxbillfree == "" || item.taxbillfree == null){
									$("#taxbillfree").val(item.type);
								}else{
									$("#taxbillfree").val(item.taxbillfree);
								}
							}
							if(wealth.val() == "完税证明"){
								$(".taxproof").css("display","block");
								wealth.addClass("btnState-true");
								$("#taxproof").val(item.details);
								if(item.taxprooffree == "" || item.taxprooffree == null){
									$("#taxprooffree").val(item.type);
								}else{
									$("#taxprooffree").val(item.taxprooffree);
								}
							}
							if(wealth.val() == "银行金卡"){
								$(".goldcard").css("display","block");
								wealth.addClass("btnState-true");
								$("#goldcard").val(item.details);
								if(item.goldcardfree == "" || item.goldcardfree == null){
									$("#goldcardfree").val(item.type);
								}else{
									$("#goldcardfree").val(item.goldcardfree);
								}
							}
							if(wealth.val() == "特定高校在读生"){
								$(".readstudent").css("display","block");
								wealth.addClass("btnState-true");
								$("#readstudent").val(item.details);
								if(item.readstudentfree == "" || item.readstudentfree == null){
									$("#readstudentfree").val(item.type);
								}else{
									$("#readstudentfree").val(item.readstudentfree);
								}
							}
							if(wealth.val() == "特定高校毕业生"){
								$(".graduate").css("display","block");
								wealth.addClass("btnState-true");
								$("#graduate").val(item.details);
								if(item.graduatefree == "" || item.graduatefree == null){
									$("#graduatefree").val(item.type);
								}else{
									$("#graduatefree").val(item.graduatefree);
								}
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
					$(".bankflow").css("display","none");
					$(".certificate").css("display","none");
					$(".taxbill").css("display","none");
					$(".taxproof").css("display","none");
					$(".goldcard").css("display","none");
					$(".readstudent").css("display","none");
					$(".graduate").css("display","none");
				}
			}
			$("#wealth").change(function(){
				if($(this).val() == 1){
					$(".wealthmain").hide();
					$(".deposit").css("display","none");
					$(".vehicle").css("display","none");
					$(".houseProperty").css("display","none");
					$(".financial").css("display","none");
					$(".bankflow").css("display","none");
					$(".certificate").css("display","none");
					$(".taxbill").css("display","none");
					$(".taxproof").css("display","none");
					$(".readstudent").css("display","none");
					$(".goldcard").css("display","none");
					$(".graduate").css("display","none");
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
						$("#depositfree").val("银行存款");
						/* if(userType == 2){
							$(".help-blockdeposit").attr("data-bv-result","INVALID");  
						    $(".deposits").css({"display":"block"});
						    $(".deposits").attr("class", "col-xs-6 deposits has-error");
						    $("#deposit").attr("style", "border-color:#ff1a1a");
						} */
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
						$("#vehiclefree").val("车产");
						/* if(userType == 2){
					        $(".help-blockvehicle").attr("data-bv-result","INVALID");  
					        $(".vehicles").css({"display":"block"});
					        $(".vehicles").attr("class", "col-xs-6 vehicles has-error");
					        $("#vehicle").attr("style", "border-color:#ff1a1a");
						} */
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
						$("#housePropertyfree").val("房产");
						/* if(userType == 2){
							$(".help-blockhouseProperty").attr("data-bv-result","INVALID");  
						    $(".housePropertys").css({"display":"block"});
						    $(".housePropertys").attr("class", "col-xs-6 housePropertys has-error");
						    $("#houseProperty").attr("style", "border-color:#ff1a1a");
						} */
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
						$("#financialfree").val("理财");
						/* if(userType == 2){
							$(".help-blockfinancial").attr("data-bv-result","INVALID");  
						    $(".financials").css({"display":"block"});
						    $(".financials").attr("class", "col-xs-6 financials has-error");
						    $("#financial").attr("style", "border-color:#ff1a1a");
						} */
						//$("#financial").placeholder("万");
					}
				}else if(financeBtnInfo == "在职证明"){
					if($(this).hasClass("btnState-true")){
						$(".certificate").css("display","none");
						$(this).removeClass("btnState-true");
						$("#certificate").val("");
						$(".certificates").css({"display":"none"});
						$(".certificates").attr("class", "col-xs-6 certificates has-success");
						$("#certificate").attr("style", null);
					}else{
						$(".certificate").css("display","block");
						$(this).addClass("btnState-true");
						$("#certificate").val("");
						$("#certificatefree").val("在职证明");
					}
				}else if(financeBtnInfo == "银行流水"){
					if($(this).hasClass("btnState-true")){
						$(".bankflow").css("display","none");
						$(this).removeClass("btnState-true");
						$("#bankflow").val("");
						$(".bankflows").css({"display":"none"});
						$(".bankflows").attr("class", "col-xs-6 bankflows has-success");
						$("#bankflow").attr("style", null);
					}else{
						$(".bankflow").css("display","block");
						$(this).addClass("btnState-true");
						$("#bankflow").val("");
						$("#bankflowfree").val("银行流水");
					}
				}else if(financeBtnInfo == "税单"){
					if($(this).hasClass("btnState-true")){
						$(".taxbill").css("display","none");
						$(this).removeClass("btnState-true");
						$("#taxbill").val("");
						$(".taxbills").css({"display":"none"});
						$(".taxbills").attr("class", "col-xs-6 taxbills has-success");
						$("#taxbill").attr("style", null);
					}else{
						$(".taxbill").css("display","block");
						$(this).addClass("btnState-true");
						$("#taxbill").val("");
						$("#taxbillfree").val("税单");
					}
				}else if(financeBtnInfo == "完税证明"){
					if($(this).hasClass("btnState-true")){
						$(".taxproof").css("display","none");
						$(this).removeClass("btnState-true");
						$("#taxproof").val("");
						$(".taxproofs").css({"display":"none"});
						$(".taxproofs").attr("class", "col-xs-6 taxproofs has-success");
						$("#taxproof").attr("style", null);
					}else{
						$(".taxproof").css("display","block");
						$(this).addClass("btnState-true");
						$("#taxproof").val("");
						$("#taxprooffree").val("完税证明");
					}
				}else if(financeBtnInfo == "银行金卡"){
					if($(this).hasClass("btnState-true")){
						$(".goldcard").css("display","none");
						$(this).removeClass("btnState-true");
						$("#goldcard").val("");
						$(".goldcards").css({"display":"none"});
						$(".goldcards").attr("class", "col-xs-6 goldcards has-success");
						$("#goldcard").attr("style", null);
					}else{
						$(".goldcard").css("display","block");
						$(this).addClass("btnState-true");
						$("#goldcard").val("");
						$("#goldcardfree").val("银行金卡");
					}
				}else if(financeBtnInfo == "特定高校在读生"){
					if($(this).hasClass("btnState-true")){
						$(".readstudent").css("display","none");
						$(this).removeClass("btnState-true");
						$("#readstudent").val("");
						$(".readstudents").css({"display":"none"});
						$(".readstudents").attr("class", "col-xs-6 readstudents has-success");
						$("#readstudent").attr("style", null);
					}else{
						$(".readstudent").css("display","block");
						$(this).addClass("btnState-true");
						$("#readstudent").val("学信网学籍在线验证报告");
						$("#readstudentfree").val("特定高校在读生");
					}
				}else if(financeBtnInfo == "特定高校毕业生"){
					if($(this).hasClass("btnState-true")){
						$(".graduate").css("display","none");
						$(this).removeClass("btnState-true");
						$("#graduate").val("");
						$(".graduates").css({"display":"none"});
						$(".graduates").attr("class", "col-xs-6 graduates has-success");
						$("#graduate").attr("style", null);
					}else{
						$(".graduate").css("display","block");
						$(this).addClass("btnState-true");
						$("#graduate").val("学信网电子学历认证书");
						$("#graduatefree").val("特定高校毕业生");
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
				if($(this).parent().is(".bankflow")){
					$(".bankflow").css("display","none");
					$("#bankflowType").removeClass("btnState-true");
					$("#bankflow").val("");
					$(".bankflows").css({"display":"none"});
					$(".bankflows").attr("class", "col-xs-6 bankflows has-success");
					$("#bankflow").attr("style", null);
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
				if($(this).parent().is(".certificate")){
					$(".certificate").css("display","none");
					$("#certificateType").removeClass("btnState-true");
					$("#certificate").val("");
					$(".certificates").css({"display":"none"});
					$(".certificates").attr("class", "col-xs-6 certificates has-success");
					$("#certificate").attr("style", null);
				}
				if($(this).parent().is(".taxbill")){
					$(".taxbill").css("display","none");
					$("#taxbillType").removeClass("btnState-true");
					$("#taxbill").val("");
					$(".taxbills").css({"display":"none"});
					$(".taxbills").attr("class", "col-xs-6 taxbills has-success");
					$("#taxbill").attr("style", null);
				}
				if($(this).parent().is(".taxproof")){
					$(".taxproof").css("display","none");
					$("#taxproofType").removeClass("btnState-true");
					$("#taxproof").val("");
					$(".taxproofs").css({"display":"none"});
					$(".taxproofs").attr("class", "col-xs-6 taxproofs has-success");
					$("#taxproof").attr("style", null);
				}
				if($(this).parent().is(".goldcard")){
					$(".goldcard").css("display","none");
					$("#goldcardType").removeClass("btnState-true");
					$("#goldcard").val("");
					$(".goldcards").css({"display":"none"});
					$(".goldcards").attr("class", "col-xs-6 goldcards has-success");
					$("#goldcard").attr("style", null);
				}
				if($(this).parent().is(".readstudent")){
					$(".readstudent").css("display","none");
					$("#readstudentType").removeClass("btnState-true");
					$("#readstudent").val("");
					$(".readstudents").css({"display":"none"});
					$(".readstudents").attr("class", "col-xs-6 readstudents has-success");
					$("#readstudent").attr("style", null);
				}
				if($(this).parent().is(".graduate")){
					$(".graduate").css("display","none");
					$("#graduateType").removeClass("btnState-true");
					$("#graduate").val("");
					$(".graduates").css({"display":"none"});
					$(".graduates").attr("class", "col-xs-6 graduates has-success");
					$("#graduate").attr("style", null);
				}
			});
			
			
			
		});
		//连接websocket
		/* connectWebSocket();
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
		} */
		$("#addBtn").click(function(){
			save(1);
		});
		
		
		//保存
		function save(status){
			layer.load(1);
			var applicantid = '${obj.applicant.id}';
			var orderid = '${obj.orderid}';
			//得到获取validator对象或实例 
			var bootstrapValidator = $("#passportInfo").data('bootstrapValidator');
			bootstrapValidator.validate();
			var applicVal = $("#applicant").val();
			//主申请人时，是否同主申请人设置为空，不然默认为1
			if(applicVal == 1){
				$("#wealth").val(0);
			}
			if(status == 1){
				if (!bootstrapValidator.isValid()) {
					layer.closeAll("loading");
					return;
				}
				var applicant = $('#applicant').val();
				/* var relationRemark = $('#relationRemark').val();
				if(applicant == 1 && !relationRemark){
					layer.msg('主申请人备注不能为空');
					return;
				} */
				if(applicVal == 1){
					var position = $('#position').val();
					if(!position){
						layer.msg('职位不能为空');
						layer.closeAll("loading");
						return;
					}
				}
			}
			
			var unitName = $('#unitName').val();
			/* if(!unitName){
				layer.msg('父母（配偶）职业不能为空');
				return;
			} */
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
			
			//绑定签证城市
			var visacounty = "";
			$('[name=visacountys]').each(function(){
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
			var isname = $("#guaranteeCBInput").prop("checked");
			var isyaoqing = $("#inviterCBInput").prop("checked");
			if(isname){
				isname = 1;
			}else{
				isname = 0;
			}
			if(isyaoqing){
				isyaoqing = 1;
			}else{
				isyaoqing = 0;
			}
			
			var passportInfo = $.param({"wealthType":wealthType,'visatype':visatype,'visacounty':visacounty,'isVisit':isVisit,'threecounty':threecounty,'isname':isname,'isyaoqing':isyaoqing}) + "&" +  $("#passportInfo").serialize();
			
			ajaxConnection();
			var count = 0;
			function ajaxConnection(){
				console.log("要进入ajax请求了");
				$.ajax({
					type: 'POST',
					//async: false,
					data : passportInfo,
					url: '${base}/admin/simple/saveEditVisa.html',
					success :function(data) {
						layer.closeAll("loading");
						//console.log(JSON.stringify(data));
						if(status == 1){
							parent.successCallBack(1);
							closeWindow();
						}else if(status == 2){
							//socket.onclose();
							window.location.href = '/admin/simple/updateApplicant.html?applicantid='+applicantid+'&orderid='+orderid;
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
		/* var threecounty = '${obj.jporderinfo.visaCounty}';
		if(threecounty){
			var threecountys = threecounty.split(",");
			for (var i = 0; i < threecountys.length; i++) {
				$('[name=visacounty]').each(function(){
					if(threecountys[i] == $(this).val()){
						$(this).addClass('btnState-true');
					}
				});
			}
		} */
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
		/* $('#visatype').change(function(){
			var thisval = $(this).val();
			var visit = $('#isVisit').val();
			if(thisval == 2){
				//$('#visacounty').show();
				$('#threefangwen').show();
				$('#visacounty input').removeClass('btnState-true');
				if(visit == 1){
					  $('#threexian').show();
						$('.alignment').show(); 
				}
			}else{
				$('#visacounty').hide();
				$('#threefangwen').hide();
				$('.row body-from-input').hide();
				$('#threexian').hide();
				$('.alignment').hide();
				$('#laststartdate').val('');
				$('#laststayday').val('');
				$('#lastreturndate').val('');
			}
		}); */
		
		$('#isVisit').change(function(){
			var thisval = $(this).val();
			if(thisval == 1){
				$('#threexian').show();
				//$('.alignment').show();
			}else{
				$('#threexian input').removeClass('btnState-true');
				//$('#laststartdate').val('');
				//$('#laststayday').val('');
				//$('#lastreturndate').val('');
				$('#threexian').hide();
				//$('.alignment').hide();
				
			}
		});
		/* 在日担保人出生日期 */
		var now = new Date();
		$("#vouchbirth").datetimepicker({
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
		/* 在日邀请人 */
		$("#invitebirth").datetimepicker({
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
		/* 担保人开关 */
		$("#guaranteeCBInput").click(function(){
			var guaranteeCBInput = $("#guaranteeCBInput").prop("checked");
			if(guaranteeCBInput){
				/* $(".vouchnameen").show(); */
				$(".hideLabel").show();
			}else{
				/* $(".vouchnameen").hide(); */
				$(".hideLabel").hide();
			}
		});
		/* 邀请人开关 */
		$("#inviterCBInput").click(function(){
			var inviterCBInput = $("#inviterCBInput").prop("checked");
			if(inviterCBInput){
				$(".inviterName").show();
				/* $(".inviterSY").hide(); */
				$(".hideInviter").show();
			}else{
				$(".inviterSY").show();
				/* $(".inviterName").hide(); */
				$(".hideInviter").hide();
			}
		});
		initPageData();
		function initPageData(){
			var guaranteeCBInput = $("#guaranteeCBInput").prop("checked");
			if(guaranteeCBInput){
				/* $(".vouchnameen").show(); */
				$(".hideLabel").show();
			}else{
				/* $(".vouchnameen").hide(); */
				$(".hideLabel").hide();
			}
			var inviterCBInput = $("#inviterCBInput").prop("checked");
			if(inviterCBInput){
				$(".inviterName").show();
				/* $(".inviterSY").hide(); */
				$(".hideInviter").show();
			}else{
				$(".inviterSY").show();
				/* $(".inviterName").hide(); */
				$(".hideInviter").hide();
			}
		}
		$('#vouchname').on('input propertychange',function(){
			var vouchnameen = getPinYinStr($(this).val()).toUpperCase();
			$('#vouchnameen').val(vouchnameen);
		});
		var now = new Date();
		//出行时间
		$("#laststartdate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		});
		//返回时间
		$("#lastreturndate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-right",//显示位置
			minView: "month"//只显示年月日
		});
		$(document).on("input","#laststayday",function(){
			var gotripdate = $('#laststartdate').val();
			var thisval = $(this).val();
			thisval = thisval.replace(/[^\d]/g,'');
			$(this).val(thisval);
			if(!thisval){
				$('#lastreturndate').val('');
			}
			if(gotripdate && thisval){
				$.ajax({ 
					url: '/admin/visaJapan/autoCalculateBackDate.html',
					dataType:"json",
					data:{gotripdate:gotripdate,stayday:thisval},
					type:'post',
					success: function(data){
						//往返设置返回日期
						$('#lastreturndate').val(data);
					}
				});
			}
		});
		$('#isVisit').change(function(){
			var isvisit = $('#isVisit').val();
			
			if(isvisit == 0 ){
				$('#threexian input').removeClass('btnState-true');
				$('#laststartdate').val('');
				$('#laststayday').val('');
				$('#lastreturndate').val('');
			}
		})
/* 		$('#visatype').change(function(){
				$('#threefangwen input').removeClass('btnState-true');
		}) */
	</script>
</body>
</html>
