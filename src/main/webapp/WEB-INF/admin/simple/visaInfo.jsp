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

</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="leftNav" onclick="passportBtn();">
			<span></span>
		</a>
		<form id="passportInfo">
			<input id="orderProcessType" name="orderProcessType" type="hidden" value="${obj.orderProcessType }">
			<div class="modal-header">
				<span class="heading">签证信息 </span>
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
					<%-- <div class="info">
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
					</div> --%>
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
						<div class="info-head">财产信息</div>
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
							<input id="addWealthInfoBtn" name="" value="+" type="button" class="btn btn-sm btnState" />
						</div>
						<div class="wealth-input-group">
							
						</div>
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
	<script>
		var wealthDeftData = [
			{
				200001: '银行流水'
			},{
				200002: '车产'
			},{
				200003: '房产'
			},{
				200004: '理财'
			},{
				200005: '在职证明'
			},{
				200006: '银行存款'
			},{
				200007: '税单'
			},{
				200008: '完税证明'
			},{
				200009: '银行金卡'
			},{
				200010: '特定高校在读生'
			},{
				200011: '特定高校毕业生'
			}
		];
		function getPlaceholderAndExtenText(id) {
			var p = '';
			var e = '';
			var n = '';
			switch(id) {
				case 200001:
					p = '工资对账单收入';
					e = '万';
					n = 'bankflowfree';
					break;
				case 200002:
					p = '例如:大众速腾';
					e = '';
					n = 'vehiclefree';
					break;
				case 200003:
					p = '';
					e = '平米';
					n = 'housepropertyfree';
					break;
				case 200004:
					p = '';
					e = '万';
					n = 'financialfree';
					break;
				case 200005:
					p = '年收入';
					e = '万';
					n = 'certificatefree';
					break;
				case 200006:
					p = '';
					e = '万';
					n = 'depositfree';
					break;
				case 200007:
					p = '年收入';
					e = '万';
					n = 'taxbillfree';
					break;
				case 200008:
					p = '年缴税';
					e = '元';
					n = 'taxprooffree';
					break;
				case 200009:
					p = '';
					e = '';
					n = 'goldcardfree';
					break;
				case 200010:
					p = '学信网学籍在线验证报告';
					e = '';
					n = 'readstudentfree';
					break;
				case 200011:
					p = '学信网电子学历认证书';
					e = '';
					n = 'graduatefree';
					break;
				default:
					p = '';
					e = '';
					n = '';
					break;
			}
			return {
				place: p,
				exten: e,
				name: n
			};
		}
	</script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {

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
			if(applicVal == "1"){ 
				//主申请人
				$(".applyvice").hide();
				$(".tripvice").hide();
				$(".wealthvice").hide();
				$(".applymain").show();
				$(".wealthmain").show();
				$("#mainApply").text("主申请人");
			}else{ 
				//副申请人
				$(".applyvice").show();
				$(".tripvice").show();
				$(".wealthvice").show();
				$(".applymain").hide();
				$(".wealthmain").hide();
				$("#mainApply").text("副申请人");
			}

			
			//主申请人 or 副申请人
			$("#applicant").change(function(){
				var applicVal = $(this).val();
				if(applicVal == "1"){//主申请人
					$(".applyvice").hide();
					$(".tripvice").hide();

					$(".wealthvice").hide();
					$(".applymain").show();

					$(".wealthmain").show();
					$("#mainApply").text("主申请人");
				}else{//副申请人
					$(".applyvice").show();
					$(".tripvice").show();
					$(".wealthvice").show();
					$("#wealth").val(1);
					$(".applymain").hide();
					$(".workmain").hide();
					$(".wealthmain").hide();
					$("#mainApply").text("副申请人");

					$('.wealth-input-group').hide();
				}
			});
			
			
			
	
			
			
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

			/** 2018_07_28 */
			(function() {
				'use strict';

				var $wealthmain 	  = $('.wealthmain');
				var $addWealthInfoBtn = $('#addWealthInfoBtn');
				var $wealthInputGroup = $('.wealth-input-group');

				var totalNum = 17;
				var wealthInputBtnLen = 0;

				var wealthData = JSON.parse('${obj.wealthJp}');
				var newAddId = getNewAddId();
				
				console.log(wealthData);
				function getNewAddId() {
					var newAddId = 200011;
					var len = wealthData.length;
					if (len != 0 && wealthData[len-1]['sequence'] > newAddId) {
						newAddId = wealthData[len-1]['sequence']
					}
					return newAddId;
				}
				
				createWealthInfo();
				createWealthBtnHtml(function() {
					var n = 0;
					wealthData.forEach(function(item) {
						var sequence = item['sequence'];
						if (sequence > 200011)n++;
						if (sequence < 12) sequence = 200000 + sequence;
						
						$wealthmain.find('input').each(function(idx, input) {
							if (sequence == $(input).attr('data-id')) {
								$(input).addClass('btnState-true');
								return 0;
							}
						});
					});

					wealthInputBtnLen = computeWealthIptBtnLen(n);
				});

				function computeWealthIptBtnLen(n) {
					var l = $wealthmain.children().length;
					if (n > 0) {
						l += n;
					}
					return l;
				}

				function isSelect($input) {
					if ($input.hasClass('btnState-true')) return 1;
					return 0;
				}

				$wealthmain.on('click', '.btn-ipt', function() {
					var $self = $(this);
					var _id = $self.attr('data-id');
					var obj = getPlaceholderAndExtenText(parseInt(_id));
					$wealthInputGroup.show();
					if (isSelect($self)) return removeOnceWealth('inputBtn', $self);
					
					$self.addClass('btnState-true');
					var $temp = createHtml({
						sequence: _id,
						extension: obj.exten,
						placeholder: obj.place,
						bankflowfree: $self.val(),
						type: $self.val(),
						details: ''
					});

					$wealthInputGroup.append($temp);
				});

				function removeOnceWealth(origin, $btn) {
					if (origin === 'inputBtn') {
						var id = $btn.attr('data-id');
						$btn.removeClass('btnState-true');
						$wealthInputGroup.children().each(function() {
							if ($(this).attr('data-id') == id) {
								$(this).remove();
							}
						});
					} else {
						var $parent = $btn.parent();
						var id = $parent.attr('data-id');
						$wealthmain.find('.btn-ipt').each(function() {
							if ($(this).attr('data-id') == id) {
								$(this).removeClass('btnState-true');
							}
						});
						if (id > 200011) wealthInputBtnLen--;
						$parent.remove();
					}
				}

				$addWealthInfoBtn.on('click', function(event) {
					if (wealthInputBtnLen < totalNum) {
						var $temp = createHtml({
							sequence: ++newAddId,
							extension: '',
							placeholder: '',
							bankflowfree: '',
							type: '',
							details: ''
						});

						$wealthInputGroup.append($temp);
						wealthInputBtnLen++;
						$wealthInputGroup.show();
					}
				});

				function createWealthBtnHtml(fn) {
					wealthDeftData.forEach(function(it, idx) {
						for (var k in it) {
							$addWealthInfoBtn.before(wealthBtnTemp(idx, k, it[k]));
						}
					});
					fn && fn();
				}
				
				function wealthBtnTemp(i, k, v) {
					var $temp = $('<input data-id="'+k+'" name="" data-index="'+i+'" value="'+v+'" type="button" class="btn btnState btn-ipt" />');
					return $temp;
				}


				function createHtml(it) {
					var key = '';
					if (it.sequence < 12) {
						it.sequence = 200000 + it.sequence;
						key = it[getPlaceholderAndExtenText(parseInt(it.sequence)).name];
					} else {
						key = it.bankflowfree;
					}

					if (it.sequence == 200010 || it.sequence == 200011) {
						if (it.details == '') {
							it.details = it.placeholder;
						}
					}

					var o =  getPlaceholderAndExtenText(parseInt(it.sequence));
						it.placeholder = o.place;
						it.extension   = o.exten;
						it.name 	   = o.name;
					
					var $temp = $(
						'<div data-id="'+ it.sequence +'" id="wealth-input-' + it.sequence + '" class="info-body-from clone-module cf">' +
							'<div class="row body-from-input">' +
								'<div class="col-sm-5">' +
									'<div class="form-group">' +
										'<label><span>*</span>' + it.type + '</label>' +
										'<input autocomplete="off" type="text" class="form-control input-sm" value="' + key + '"  />' +
									'</div>' +
								'</div>' +
								'<div class="col-sm-4">' +
									'<div class="form-group">' +
										'<label>&nbsp;</label>' +
										'<input type="text" autocomplete="off" class="form-control input-sm" placeholder="'+it.placeholder+'" value="'+it.details+'"/>' +
									'</div>' +
								'</div>' +
								'<div style="float:left; margin:40px 0 0 -10px;"> '+it.extension+' </div>' +
							'</div>' +
							'<i class="remove-btn delete-icon"></i>' +
						'</div>'
					);

					return $temp;
				}
				
				function createWealthInfo() {
					wealthData.forEach(function(it, idx) {
						$wealthInputGroup.append(createHtml(it, idx));
					});

					$wealthInputGroup.on('click', '.delete-icon', function() {
						removeOnceWealth('delete-icon', $(this));
					});
				}
			})();
			
		});
		/** 2018_08_10 */
		(() => {
			'use strict';

			
		})();
		
		$("#addBtn").click(function(){
			save(1);
		});

		function getWealthInfoObject() {
			var arr = [];
			var $wealthInputGroup = $('.wealth-input-group');
			$wealthInputGroup.children().each(function(index, el) {
				var ret 	= {};
				var $self 	= $(el);
				var $inputs = $self.find('input');
				var id 		= $self.attr('data-id');
				ret[id] = {wealthtitle: '', wealthvalue: '', 'wealthtype': ''};
				ret[id]['wealthtitle'] = $inputs.eq(0).val();
				ret[id]['wealthvalue'] = $inputs.eq(1).val();
				ret[id]['wealthtype'] = getwealthType(id);
				// ret[id]['wealth_name'] = getPlaceholderAndExtenText(parseInt(id)).name;

				arr.push(ret);
			});
			
			return arr;
		}

		function getwealthType(id) {
			var s = '';
			wealthDeftData.forEach(function(it) {
				if (!it[id]) return 0;
				s = it[id];
				return 0;
			});
			return s;
		}
		
		
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

			var wealthInfoObject = getWealthInfoObject();

			var passportInfo = $.param({
				wealthInfoObject: JSON.stringify(wealthInfoObject),
				wealthType: wealthType,
				visatype: visatype,
				visacounty: visacounty,
				isVisit: isVisit,
				threecounty: threecounty,
				isname: isname,
				isyaoqing: isyaoqing
			}) + "&" + $("#passportInfo").serialize();
			console.log(wealthInfoObject);

			ajaxConnection();
			var count = 0;
			function ajaxConnection(){
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
					}/* ,timeout:10000 */
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
		let $laststartdate = $('#laststartdate');
		let $lastreturndate = $('#lastreturndate');
		$laststartdate.on("change", function() {
			let fv = $(this).val();
			let lv = $lastreturndate.val();
			autoCalculateStaydays(fv, lv);
		});

		$lastreturndate.on("change", function() {
			let lv = $(this).val();
			let fv = $laststartdate.val();
			autoCalculateStaydays(fv, lv);
		});

		function autoCalculateStaydays(fv, lv) {
			if (fv.length > 9 && lv.length > 9) {
				$.ajax({ 
					url: '/admin/simple/autoCalculateStaydays.html',
					dataType:"json",
					data:{
						laststartdate: fv,
						lastreturndate: lv,
					},
					type:'post',
					success: function(data){
						if (data < 0) {
							alert('上次出行时间不能大于上次返回时间');	
						} else {
							$('#laststayday').val(data);
						}
					}
				});
			}
		}
		
		$('#isVisit').change(function(){
			var isvisit = $('#isVisit').val();
			
			if(isvisit == 0 ){
				$('#threexian input').removeClass('btnState-true');
				$('#laststartdate').val('');
				$('#laststayday').val('');
				$('#lastreturndate').val('');
			}
		});
	</script>
</body>
</html>
