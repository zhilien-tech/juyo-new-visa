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
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simpleVisaInfo.css?v='20180510'">
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
												<input name="visacounty" type="button" value="福岛县" class="btn btn-sm btnState">
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
									<c:choose>
											<c:when test="${obj.jporderinfo.isVisit == 1 }">
												<div class="alignment cf viseType-btn">
											</c:when>
											<c:otherwise>
												<div class="alignment cf viseType-btn none">
											</c:otherwise>
										</c:choose>
										
											<div class="row body-from-input">
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次出行时间</label>
														<input id="laststartdate" name="laststartdate"  style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.jporderinfo.laststartdate }" pattern="yyyy-MM-dd" />"/>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次停留天数</label>
														<input id="laststayday" name="laststayday"  style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm" value="${obj.jporderinfo.laststayday }"/>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label>上次返回时间</label>
														<input id="lastreturndate" name="lastreturndate"  style="color:#555 !important;border-color:#d2d6de !important;" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.jporderinfo.lastreturndate }" pattern="yyyy-MM-dd" />"/>
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
								<div class="col-sm-4 preSchool">
									<div class="form-group">
										<label><span>*</span>职业</label>
										<input id="position" name="position" type="text" class="form-control input-sm" value="${obj.workJp.position }"/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="unitNameLabel"><span>*</span>配偶职业</label>
										<input id="unitName" name="unitName" type="text" class="form-control input-sm" value="${obj.workJp.unitName }"/>
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
										<input id="vehicle" name="vehicle" type="text" class="form-control input-sm" placeholder="例如:大众速腾"/>
									</div>
								</div>
								<!-- <div style="float:left;  margin:40px 0 0 -23px;">
								例如：大众速腾
								</div> -->
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
					<!-- 在日拟入住九点名称或友人姓名及地址 -->
					<div class='info'>
						<div class="info-head">在日拟入住九点名称或友人姓名及地址</div>
						<div class="info-body-from cf ">
							<div class="row ">
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanFriendName"><span>*</span>酒店名称或友人姓名</label>
										<input id="hotelname" name="hotelname" type="text" class="form-control input-sm" placeholder="参照'赴日予定表'" value="${obj.visaother.hotelname }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanTelNumber">电话</label>
										<input id="hotelphone" name="hotelphone" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.hotelphone }"/>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label id="JapanAddress">地址</label>
										<input id="hoteladdress" name="hoteladdress" type="text" class="form-control input-sm" placeholder=" " value="${obj.visaother.hoteladdress }"/>
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
										<input id="vouchname" name="vouchname" type="text" class="form-control input-sm guaranty" placeholder="参照'申元保证书" value="${obj.visaother.vouchname }"/>
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
										<input id="invitename" name="invitename" type="text" class="form-control input-sm " placeholder="参照'申元保证书" value="${obj.visaother.invitename }"/>
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
										<input id="traveladvice" name="traveladvice" type="text" class="form-control input-sm" placeholder="推荐" value="${obj.visaother.traveladvice }"/>
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
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
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
			//删除——————————————————————————————————————————————
			//婚姻状况为单身和丧偶时没有上传图片接口
			var marryStatus = $("#marryStatus").val();
			if(marryStatus == 3 || marryStatus == 4 || !marryStatus){
				$(".info-imgUpload").hide();
				$("#unitNameLabel").html("<span>*</span>父母职业");
			}else{
				$(".info-imgUpload").show();
				if(marryStatus == 1){
					$("#unitNameLabel").html("<span>*</span>配偶职业");
				}else{
					$("#unitNameLabel").html("<span>*</span>父母职业");
				}
			}
			$("#marryStatus").change(function(){
				var status = $(this).val();
				if(status == 3 || status == 4 || !status){
					$("#unitNameLabel").html("<span>*</span>父母职业");
					$(".info-imgUpload").hide();
					$('#marryUrl').val("");
					$('#sqImg').attr('src', "");
					$("#uploadFile").siblings("i").css("display","none");
				}else{
					if(status == 1){
						$("#unitNameLabel").html("<span>*</span>配偶职业");
					}else{
						$("#unitNameLabel").html("<span>*</span>父母职业");
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
						/* if(userType == 2){
							$(".help-blockfinancial").attr("data-bv-result","INVALID");  
						    $(".financials").css({"display":"block"});
						    $(".financials").attr("class", "col-xs-6 financials has-error");
						    $("#financial").attr("style", "border-color:#ff1a1a");
						} */
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
			var relationRemark = $('#relationRemark').val();
			var applicant = $('#applicant').val();
			if(applicant == 1 && !relationRemark){
				layer.msg('主申请人备注不能为空');
				return;
			}
			var position = $('#position').val();
			if(!position){
				layer.msg('职位不能为空');
				return;
			}
			var unitName = $('#unitName').val();
			if(!unitName){
				layer.msg('父母（配偶）职业不能为空');
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
				$('.alignment').show();
			}else{
				$('#threexian').hide();
				$('.alignment').hide();
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
	</script>
</body>
</html>