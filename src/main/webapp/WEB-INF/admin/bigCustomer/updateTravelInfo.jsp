<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>签证信息</title>
<link rel="stylesheet"
	href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet"
	href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
<!-- 本页样式 -->
<link rel="stylesheet"
	href="${base}/references/public/css/updateVisaInfo.css">
<style>
[v-cloak] {
	display: none;
}

.s {
	color: #de4b4b;
	font-weight: 600;
	width: 15px;
	font-size: 20px;
	display: inline-block;
	height: 10px;
	line-height: 15px;
	position: relative;
	top: 6px;
}

.section, .English {
	width: 90%;
	float: none;
}
</style>
</head>
<body>
	<div class="head">
		<div class="title">
			<span>旅行信息</span>
		</div>
		<div class="btnRight">
			<a class="saveVisa" onclick="save(1)">保存</a> <a class="cancelVisa"
				onclick="closeWindow()">取消</a>
		</div>
	</div>
	<!-- 左右按钮 -->
	<a id="toWorkinfo" class="leftNav" onclick="workInfoBtn();"> <i
		style="position: absolute; top: 20%; width: 1.5em; left: 10px; font-family: 'microsoft yahei';">第五步</i>
		<span></span>
	</a>
	<!-- <a class="nextstep">
			<span></span>
		</a> -->
	<div class="topHide"></div>
	<div id="section">
		<form id="travelinfo">
			<div id="wrapper" class="section">
				<div class="dislogHide"></div>
				<!--旅伴信息-->
				<div class="companyInfoModule">
					<div class="companyMain" style="margin-top: 10px;">
						<div style="overflow: hidden;">
							<div class="companyMainInfo groupRadioInfo" style="float: left;">
								<input type="hidden" name="staffid" value="${obj.staffId }" /> <label><span
									class="s">*</span> 是否与其他人同行</label> <input type="radio"
									class="companyInfo" name="istravelwithother" value="1" />是 <input
									type="radio" class="companyInfo" name="istravelwithother"
									style="margin-left: 20px;" value="2" checked />否
							</div>
							<div style="float: left; margin: 20px 0 0 185px;">
								<a class="save companysave">添加</a>
							</div>
						</div>
						<!--yes-->
						<!--第二部分No-->
						<div class="teamnamefalse groupInputInfo">
							<div>
								<c:if test="${!empty obj.companioninfo }">
									<c:forEach var="companion" items="${obj.companioninfo }">
										<div
											class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV">
											<div class="row">
												<div class="col-sm-3">
													<label><span class="s">*</span>同行人姓</label> <input
														id="firstname" name="firstname"
														value="${companion.firstname}"
														onchange="addSegmentsTranslateZhToPinYin(this,'firstnameen','')"
														type="text" placeholder="同行人姓" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> 同行人名</label> <input
														id="lastname" name="lastname"
														value="${companion.lastname}"
														onchange="addSegmentsTranslateZhToPinYin(this,'lastnameen','')"
														type="text" placeholder="同行人名" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> Surnames</label> <input
														id="firstnameen" class="firstnameen"
														value="${companion.firstnameen}" name="firstnameen"
														type="text" placeholder="Surnames" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> Given Names</label> <input
														id="lastnameen" class="lastnameen"
														value="${companion.lastnameen}" name="lastnameen"
														type="text" placeholder="Given Names" />
												</div>
											</div>
											<div class="row">
												<div class="col-sm-3 youRelationship ">
													<label><span class="s">*</span> 与您的关系</label> <select
														id="relationship" class="relationshipSelect"
														name="relationship">
														<option value="0">请选择</option>
														<c:forEach items="${obj.travelcompanionrelationshipenum }"
															var="map">
															<option value="${map.key }"
																${map.key==companion.relationship?"selected":"" }>${map.value }</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-sm-3 youRelationship" style="display: none;">
													<label><span class="s">*</span> 说明</label> <input
														style="height: 34px;" id="explain" name="explain"
														value="${companion.explain }" type="text" placeholder="" />
												</div>
											</div>
											<div>
												<a class="removeTongXingRen" data-index="0"
													style="margin-left: 325px; margin-top: 10px; display: inline-block; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;"
													onclick="removeTongXingRen(this)">删除</a>
											</div>
										</div>
									</c:forEach>
								</c:if>
								<c:if test="${empty obj.companioninfo }">
									<div
										class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV">
										<div class="row">
											<div class="col-sm-3">
												<label><span class="s">*</span>同行人姓</label> <input
													id="firstname" name="firstname" type="text"
													placeholder="同行人姓" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> 同行人名</label> <input
													id="lastname" name="lastname" type="text"
													placeholder="同行人名" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> Surnames</label> <input
													id="firstnameen" class="firstnameen" name="firstnameen"
													type="text" placeholder="Surnames" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> Given Names</label> <input
													id="lastnameen" class="lastnameen" name="lastnameen"
													type="text" placeholder="Given Names" />
											</div>
										</div>
										<div class="row">
											<div class="col-sm-3 youRelationship ">
												<label><span class="s">*</span> 与您的关系</label> <select
													id="relationship" class="relationshipSelect"
													name="relationship">
													<option value="0">请选择</option>
													<c:forEach items="${obj.travelcompanionrelationshipenum }"
														var="map">
														<option value="${map.key }"
															${map.key==companion.relationship?"selected":"" }>${map.value }</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-3 youRelationship" style="display: none;">
												<label><span class="s">*</span> 说明</label> <input
													style="height: 34px;" id="" name="" type="text"
													placeholder="" />
											</div>
										</div>
										<div>
											<a class="removeTongXingRen" data-index="0"
												style="margin-left: 325px; margin-top: 10px; display: inline-block; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;"
												onclick="removeTongXingRen(this)">删除</a>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<!--旅伴信息END-->
				<div class="companyInfoModule" style="padding-left: 18px;">
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 费用支付人</label> <select
								id="costpayer" class="relationshipSelect"
								 name="costpayer">
								<option value="1" <c:if test="${'1' eq obj.tripinfo.costpayer}">selected</c:if>>自己</option>
								<option value="2" <c:if test="${'2' eq obj.tripinfo.costpayer}">selected</c:if>>其他人</option>
								<option value="3" <c:if test="${'3' eq obj.tripinfo.costpayer}">selected</c:if>>公司组织</option>
							</select>
						</div>
					</div>
				</div>

				<!--以前的美国旅游信息-->
				<div class="beforeTourism">
					<!--是否去过美国-->
					<div class="goUSModule">
						<div class="goUSMain">
							<div style="height: 80px; margin-top: 20px;">
								<div class="groupRadioInfo goUSPad paddingbottom-14"
									style="float: left;">
									<label><span class="s">*</span> 是否去过美国</label> <input
										type="radio" name="hasbeeninus" class="goUS" value="1" />是
									<input type="radio" style="margin-left: 20px;"
										name="hasbeeninus" class="goUS" value="2" checked />否
								</div>
							</div>

							<!--yes-->
							<div class="goUSInfo goUSYes">
								<div class="gotousInfo">
									<div class="goUS_CountryDiv">
										<div class="row">
											<div class="col-sm-3 groupInputInfo">
												<label><span class="s">*</span> 抵达日期</label> <input
													type="text" id="arrivedate" value="${obj.arrivedate }"
													name="arrivedate" class="datetimepickercss form-control"
													placeholder="">
											</div>
											<div class="col-sm-3 groupInputInfo stopDate"
												style="margin: 0;">
												<label><span class="s">*</span> 停留时间</label> <input
													id="staydays" style="width: 50%; margin: 0;"
													name="staydays" value="${obj.gousinfo.staydays }"
													type="text" /> <select id="dateunit"
													style="width: 50% !important; margin: 0;" name="dateunit">
													<option value="">请选择</option>
													<c:forEach items="${obj.timeunitstatusenum }" var="map">
														<option value="${map.key }"
															${map.key==obj.gousinfo.dateunit?"selected":"" }>${map.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>

								</div>

								<div class="groupRadioInfo drivingUS">
									<label><span class="s">*</span> 是否有美国驾照</label> <input
										type="radio" name="hasdriverlicense" class="license" value="1" />是
									<input type="radio" style="margin-left: 20px;"
										name="hasdriverlicense" class="license" value="2" checked />否
								</div>
								<div class="driverInfo elementHide">
									<div class="driverYes">
										<div class="goUS_drivers">
											<div class="groupcheckBoxInfo driverMain">
												<label><span class="s">*</span> 驾照号</label> <input
													id="driverlicensenumber"
													value="${obj.driverinfo.driverlicensenumber }"
													name="driverlicensenumber" type="text">
												<%-- <c:if test="${driver.isknowdrivernumber == 1}">
														<input id="isknowdrivernumber" class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" type="checkbox"/>
													</c:if> --%>
											</div>
											<div class="groupSelectInfo driverR paddingleft-15">
												<label><span class="s">*</span> 哪个州的驾照</label> <select
													id="witchstateofdriver"
													name="witchstateofdriver" class=" select2" multiple="multiple">
													
													<c:forEach items="${obj.usstatesenum }" var="state">
													<c:choose>
														<c:when test="${state.key eq obj.driverinfo.witchstateofdriver }">
															<option value="${state.key }" selected="selected">${state.value }</option>
														</c:when>
														<c:otherwise>
															<option value="${state.key }">${state.value }</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!--是否有美国签证-->
				<div class="visaUSMain">
					<div>
						<div class="groupRadioInfo" style="clear: both; margin-top: 10px;">
							<label><span class="s">*</span> 是否有美国签证</label> <input
								type="radio" name="isissuedvisa" class="visaUS" value="1" />是 <input
								type="radio" style="margin-left: 20px;" name="isissuedvisa"
								class="visaUS" value="2" checked />否
						</div>
						<div>
							<div class="dateIssue goUS_visa">
								<div class="row">
									<div class="col-sm-3 groupInputInfo lastVisaDate">
										<label><span class="s">*</span> 签发日期</label> <input
											id="issueddate" name="issueddate" value="${obj.issueddate}"
											class="datetimepickercss form-control" placeholder=""
											type="text" />
									</div>
									<div class="col-sm-4 groupcheckBoxInfo visaisnumber">
										<label><span class="s">*</span> 签证号码</label> <input
											name="visanumber" class="visanumber"
											value="${obj.tripinfo.visanumber }" type="text" />
										<!-- <input id="idknowvisanumber" onchange="AddSingle(this,'idknowvisanumberen')" name="idknowvisanumber" v-on:click="idknowvisanumberChange" value="visaInfo.previUSTripInfo.idknowvisanumber" type="checkbox"/> -->
									</div>
								</div>


								<div class="clear"></div>
								<div class="Alike groupRadioInfo paddingTop">
									<label><span class="s">*</span> 本次是否申请同类的签证</label> <input
										type="radio" name="isapplyingsametypevisa" value="1" />是 <input
										type="radio" style="margin-left: 20px;"
										name="isapplyingsametypevisa" value="2" checked />否
								</div>
								<div class="paddingTop groupRadioInfo">
									<label><span class="s">*</span> 是否采集过指纹</label> <input
										type="radio" name="istenprinted" value="1" />是 <input
										type="radio" style="margin-left: 20px;" name="istenprinted"
										value="2" checked />否
								</div>
								<div class="paddingTop">
									<div class="groupRadioInfo">
										<label><span class="s">*</span> 是否丢失过美国签证</label> <input
											type="radio" name="islost" class="lose" value="1" />是 <input
											type="radio" style="margin-left: 20px;" name="islost"
											class="lose" value="2" checked />否
									</div>
								</div>
								<div class="clear"></div>
								<div class="paddingTop">
									<div>
										<div class="groupRadioInfo">
											<label><span class="s">*</span> 是否被取消过</label> <input
												type="radio" name="iscancelled" class="revoke" value="1" />是
											<input type="radio" style="margin-left: 20px;"
												name="iscancelled" class="revoke" value="2" checked />否
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境-->
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否被拒签过</label> <input type="radio"
							name="isrefused" class="refuse" value="1" />是 <input
							type="radio" style="margin-left: 20px;" name="isrefused"
							class="refuse" value="2" checked />否
					</div>
					<div class="refuseExplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 说明</label> <input
							name="refusedexplain" style="width: 182px !important;"
							class="areaInputPic" id='refusedexplain'
							value="${obj.tripinfo.refusedexplain }" />
					</div>
				</div>

				<div class="paddingBottom">
					<div class="groupRadioInfo drivingUS">
						<label><span class="s">*</span> 是否在申请美国移民</label> <input
							type="radio" class="isfiledimmigrantpetition"
							name="isfiledimmigrantpetition" value="1" />是 <input
							type="radio" class="isfiledimmigrantpetition"
							name="isfiledimmigrantpetition" style="margin-left: 20px;"
							value="2" checked />否
					</div>
					<div class="row immigrantpetition_US">
						<div class="col-sm-3 youRelationship ">
							<label><span class="s">*</span> 申请理由</label> <select
								id="emigrationreason" name="emigrationreason"
								class="relationshipSelect" style="margin: 0;">
								<option value="0">请选择</option>
								<c:forEach items="${obj.emigrationreasonenumenum }" var="map">
									<c:choose>
										<c:when test="${map.key eq obj.tripinfo.emigrationreason }">
											<option value="${map.key }" selected="selected">${map.value }</option>
										</c:when>
										<c:otherwise>
											<option value="${map.key }">${map.value }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>

							</select>
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 其他理由</label> <input
								style="height: 34px;" id="immigrantpetitionexplain"
								value="${obj.tripinfo.immigrantpetitionexplain }"
								name="immigrantpetitionexplain" type="text" placeholder="" />
						</div>
					</div>
				</div>

				<div class="paddingBottom">
					<div style="height: 70px;">
						<div class="groupRadioInfo drivingUS" style="float: left;">
							<label><span class="s">*</span> 是否有出境记录</label> <input
								type="radio" class="istraveledanycountry"
								name="istraveledanycountry" value="1" />是 <input type="radio"
								class="istraveledanycountry" name="istraveledanycountry"
								style="margin-left: 20px;" value="2" checked />否
						</div>
						<div style="float: left; margin: 20px 0 0 185px;">
							<a class="save gocountrysave">添加</a>
						</div>
					</div>
					<div class="row saveOutboundContent">
						<div class="col-sm-3 youRelationship ">
						
						<c:if test="${!empty obj.gocountry }">
								<c:forEach var="gocountry" items="${obj.gocountry }">
									<div class="travelCountry paddingTop groupInputInfo">
										<label>国家/地区</label>
										<div class="groupInputInfo groupSelectInfo">
										
											<select name="traveledcountry" id='traveledcountry'  class="form-control input-sm select2" multiple="multiple" >
												<c:forEach items="${obj.gocountryfivelist }" var="country">
														<c:if test="${gocountry.traveledcountry != country.id}">
															<option value="${country.id }">${country.chinesename }</option>
														</c:if>
														<c:if test="${gocountry.traveledcountry == country.id}">
															<option value="${country.id }" selected="selected">${country.chinesename }</option>
														</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.gocountry }">
								<div class="paddingTop travelCountry groupInputInfo">
									<label>国家/地区</label>
									<div class="groupInputInfo groupSelectInfo">
										<select name="traveledcountry" id='traveledcountry'  class="form-control input-sm select2" multiple="multiple" >
											<c:forEach items="${obj.gocountryfivelist }" var="country">
												<option value="${country.id }">${country.chinesename }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
						
						</div>
						<div class="col-sm-3">
							<a class="removeChuJingJiLu cancel gocountrycancel"
								style="margin-top: 40px; display: inline-block; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;"
								onclick="gocountrycancel">删除</a>
						</div>
					</div>
				</div>
				<!--以前的美国旅游信息END-->
				<div style="height: 50px;"></div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId   = '${obj.staffId}';
		var isDisable = '${obj.isDisable}';
		var flag      = '${obj.flag}';
	</script>
<!-- 公共js -->
<script src="${base}/references/common/js/jquery-1.10.2.js"></script>
<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
<script src="${base}/references/common/js/layer/layer.js"></script>
<script src="${base}/references/common/js/base/base.js"></script>
<!-- 公用js文件 -->
<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
<script
	src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
<script
	src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script
	src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<!-- 本页js -->
<%-- <script src="${base}/admin/bigCustomer/visa/openPageYesOrNo.js"></script><!-- 本页面  打开默认开关 js -->
	<script src="${base}/admin/bigCustomer/visa/visaGetInfoList.js"></script><!-- 本页面  获取一对多信息 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfoVue.js"></script><!-- 本页面 Vue加载页面内容 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfo.js"></script><!-- 本页面 开关交互 js --> --%>
<script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js?v=<%=System.currentTimeMillis() %>"></script>
<!-- 本页面 初始化时间插件 js -->
<script
	src="${base}/admin/bigCustomer/updateTravelinfo.js?v=<%=System.currentTimeMillis() %>"></script>
<!-- 本页面  js -->
<script type="text/javascript">
	
		//是否与其他人同行
		var istravelwithother = '${obj.travelwithother}';
		$("input[name='istravelwithother'][value='" + istravelwithother + "']").attr("checked", 'checked');
        if (istravelwithother == 1) {
        	$(".teamnamefalse").show();
		} else {
			$(".teamnamefalse").hide();
		}
		//是否去过美国
		var hasbeeninus = '${obj.tripinfo.hasbeeninus}';
		$("input[name='hasbeeninus'][value='" + hasbeeninus + "']").attr("checked", 'checked');
        if (hasbeeninus == 1) {
        	$(".goUSInfo").show();
		} else {
			$(".goUSInfo").hide();
		}
      	//是否有美国驾照
      	var hasdriverlicense = '${obj.tripinfo.hasdriverlicense}';
		$("input[name='hasdriverlicense'][value='" + hasdriverlicense + "']").attr("checked", 'checked');
        if (hasdriverlicense == 1) {
        	$(".driverInfo").show();
		} else {
			$(".driverInfo").hide();
		}
        //是否有美国签证
        var isissuedvisa = '${obj.tripinfo.isissuedvisa}';
		$("input[name='isissuedvisa'][value='" + isissuedvisa + "']").attr("checked", 'checked');
        if (isissuedvisa == 1) {
        	$(".dateIssue").show();
		} else {
			$(".dateIssue").hide();
		}
        //本次是否申请同类的签证
        var isapplyingsametypevisa = '${obj.tripinfo.isapplyingsametypevisa}';
		$("input[name='isapplyingsametypevisa'][value='" + isapplyingsametypevisa + "']").attr("checked", 'checked');
        //是否采集过指纹
        var istenprinted = '${obj.tripinfo.istenprinted}';
		$("input[name='istenprinted'][value='" + istenprinted + "']").attr("checked", 'checked');
        //是否丢失过美国签证
        var islost = '${obj.tripinfo.islost}';
		$("input[name='islost'][value='" + islost + "']").attr("checked", 'checked');
        //是否被取消过
        var iscancelled = '${obj.tripinfo.iscancelled}';
		$("input[name='iscancelled'][value='" + iscancelled + "']").attr("checked", 'checked');
        //是否被拒签过
        var isrefused = '${obj.tripinfo.isrefused}';
		$("input[name='isrefused'][value='" + isrefused + "']").attr("checked", 'checked');
		if (isrefused == 1) {
			$(".refuseExplain").show();
		} else {
			$(".refuseExplain").hide();
		}
        //是否在申请美国移民
        var isfiledimmigrantpetition = '${obj.tripinfo.isfiledimmigrantpetition}';
		$("input[name='isfiledimmigrantpetition'][value='" + isfiledimmigrantpetition + "']").attr("checked", 'checked');
		if (isfiledimmigrantpetition == 1) {
			$(".immigrantpetition_US").show();
		} else {
			$(".immigrantpetition_US").hide();
		}
        //是否有出境记录
        var istraveledanycountry = '${obj.istraveledanycountry}';
		$("input[name='istraveledanycountry'][value='" + istraveledanycountry + "']").attr("checked", 'checked');
		if (istraveledanycountry == 1) {
			$(".travelCountry").show();
		} else {
			$(".travelCountry").hide();
		}
		
		
		//跳转到基本信息页
		function workInfoBtn(){
			save(2);
        }
        
        /** 2018_08_20 */
        let removeFun = (self, len) => {
            if (len < 2) return 0;
            $(self).parent().parent().remove();
        };

        let removeTongXingRen = self => {
            let len = $('.removeTongXingRen').length;
            removeFun(self, len);
        };
        
        let removeDiDaDate = self => {
            let len = $('.removeDiDaDate').length;
            removeFun(self, len);
        };

        let removeChuJingJiLu = self => {
            let len = $('.removeChuJingJiLu').length;
            removeFun(self, len);
        };
	</script>
</html>

