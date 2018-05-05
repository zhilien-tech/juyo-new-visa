<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>签证信息</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
		<!-- 本页样式 -->
		<link rel="stylesheet" href="${base}/references/public/css/updateVisaInfo.css">
		<style>
     		[v-cloak]{display:none;}
     	</style>
	</head>
	<body>
		<div class="head">
			<div class="title">
				<span>游客签证信息</span>
			</div>
			<div class="btnRight">
				<a class="saveVisa" onclick="save(1)">保存</a>
				<a class="cancelVisa" onclick="closeWindow()">取消</a>
			</div>
		</div>
		<!-- 左右按钮 -->
		<a id="toPassport" class="leftNav" onclick="baseInfoBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第三步</i>
			<span></span>
		</a>
		<!-- <a class="nextstep">
			<span></span>
		</a> -->
		<div class="topHide"></div>
		<div id="section">
		<div id="wrapper" v-cloak class="section">
			<div class="dislogHide"></div>
			<!--旅伴信息-->
			<div class="companyInfoModule">
				<div class="titleInfo">旅伴信息</div>
				<div class="companyMain">
					<div class="companyMainInfo groupRadioInfo">
						<label>是否与其他人一起旅游</label>
						<input type="radio" class="companyInfo" v-model="visaInfo.travelCompanionInfo.istravelwithother" @change="istravelwithother()" value="1" />是
						<input type="radio" class="companyInfo" v-model="visaInfo.travelCompanionInfo.istravelwithother" @change="istravelwithother()" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="teamture elementHide">
						<div class="groupRadioInfo">
							<label>是否作为团队或组织的一部分旅游</label>
							<input type="radio" class="team" name="ispart" v-model="visaInfo.travelCompanionInfo.ispart"  @change="ispart()" value="1" />是
							<input type="radio" class="team" name="ispart" v-model="visaInfo.travelCompanionInfo.ispart" @change="ispart()" value="2" checked/>否
						</div>
						<!--第二部分yes-->
						<div class="teamnameture groupInputInfo">
							<label>团队名称</label>
							<input id="groupname" name="groupname" @change="translateZhToEnVue('groupname','groupnameen','visaInfo.travelCompanionInfo.groupnameen')" v-model="visaInfo.travelCompanionInfo.groupname" type="text" placeholder="团队名称" />
						</div>
						<!--第二部分No-->
						<div class="teamnamefalse groupInputInfo">
							<div>
							<c:if test="${!empty obj.companionList }">
								<c:forEach var="companion" items="${obj.companionList }">
									<div class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV" >
										<div class="companionSurnName">
											<label>同伴姓</label>
											<input id="firstname" name="firstname" onchange="addSegmentsTranslateZhToPinYin(this,'firstnameen','')" value="${companion.firstname }" type="text" placeholder="同伴姓" />
										</div>
										<div class="companionName">
											<label>同伴名</label>
											<input id="lastname" name="lastname" onchange="addSegmentsTranslateZhToPinYin(this,'lastnameen','')" value="${companion.lastname }" type="text" placeholder="同伴名" />
										</div>
										<div class="clear"></div>
										<div class="youRelationship AndYouRS" style="padding-bottom:0px !important;">
											<label>与你的关系</label>
											<select id="relationship" onchange="addSegmentsTranslateZhToEn(this,'relationshipen','')" value="${companion.relationship }" name="relationship">
												<option value="0">请选择</option>
												<c:forEach items="${obj.TravelCompanionRelationshipEnum }" var="map">
													<c:if test="${companion.relationship != map.key}">
														<option value="${map.key }">${map.value }</option>
													</c:if>
													<c:if test="${companion.relationship == map.key}">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.companionList }">
								<div class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV">
									<div class="companionSurnName">
										<label>同伴姓</label>
										<input id="firstname" name="firstname" onchange="addSegmentsTranslateZhToPinYin(this,'firstnameen','')" type="text" placeholder="同伴姓" />
									</div>
									<div class="companionName">
										<label>同伴名</label>
										<input id="lastname" name="lastname" onchange="addSegmentsTranslateZhToPinYin(this,'lastnameen','')" type="text" placeholder="同伴名" />
									</div>
									<div class="clear"></div>
									<div class="youRelationship AndYouRS">
										<label>与你的关系</label>
										<select id="relationship" onchange="addSegmentsTranslateZhToEn(this,'relationshipen','')" name="relationship">
											<option value="0">请选择</option>
											<c:forEach items="${obj.TravelCompanionRelationshipEnum }" var="map">
												<option value="${map.key }">${map.value }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
							</div>
							<div class="btnGroup companyGroup">
								<a class="save companysave">添加</a>
								<a class="cancel companycancel">去掉 </a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--旅伴信息END-->
			<!--以前的美国旅游信息-->
			<div class="beforeTourism">
				<!--是否去过美国-->
				<div class="goUSModule">
					<div class="titleInfo">以前的美国旅游信息</div>
					<div class="goUSMain">
						<div class="groupRadioInfo goUSPad">
							<label>是否去过美国</label>
					 		<input type="radio" id="hasbeeninus" name="hasbeeninus" v-model="visaInfo.previUSTripInfo.hasbeeninus" @change="hasbeeninus()" class="goUS" value="1" />是
							<input type="radio" id="hasbeeninus" name="hasbeeninus" v-model="visaInfo.previUSTripInfo.hasbeeninus" @change="hasbeeninus()" class="goUS" value="2" checked />否
						</div>
						<!--yes-->
						<div class="goUSInfo goUSYes">
							<div class="gotousInfo">
								<c:if test="${!empty obj.gousList }">
									<c:forEach var="gous" items="${obj.gousList }">
										<div class="goUS_CountryDiv">
											<div class="groupInputInfo">
												<label>抵达日期</label>
												<input type="text" id="arrivedate" onchange="addSegmentsTranslateZhToEn(this,'arrivedateen','')" value="<fmt:formatDate value="${gous.arrivedate }" pattern="dd/MM/yyyy" />" name="arrivedate" class="datetimepickercss form-control" placeholder="日/月/年">
											</div>
											<div class="groupInputInfo stopDate goUS_Country">
												<label>停留时间</label>
												<input id="staydays" name="staydays" onchange="addSegmentsTranslateZhToEn(this,'staydaysen','')" value="${gous.staydays }" type="text" />
												<select id="dateunit" onchange="addSegmentsTranslateZhToEn(this,'dateuniten','')" name="dateunit">
													<option value="0">请选择</option>
													<c:forEach items="${obj.TimeUnitStatusEnum }" var="map">
														<c:if test="${gous.dateunit != map.key}">
															<option value="${map.key }">${map.value }</option>
														</c:if>
														<c:if test="${gous.dateunit == map.key}">
															<option value="${map.key }" selected="selected">${map.value }</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:forEach>
								</c:if>
								<c:if test="${empty obj.gousList }">
									<div class="goUS_CountryDiv">
										<div class="groupInputInfo">
											<label>抵达日期</label>
											<input type="text" id="arrivedate" onchange="addSegmentsTranslateZhToEn(this,'arrivedateen','')" name="arrivedate" class="datetimepickercss form-control" placeholder="日/月/年">
										</div>
										<div class="groupInputInfo stopDate">
											<label>停留时间</label>
											<input id="staydays" name="staydays" onchange="addSegmentsTranslateZhToEn(this,'staydaysen','')" type="text" />
											<select id="dateunit" onchange="addSegmentsTranslateZhToEn(this,'dateuniten','')" name="dateunit">
												<option value="0">请选择</option>
												<c:forEach items="${obj.TimeUnitStatusEnum }" var="map">
													<option value="${map.key }">${map.value }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:if>
							</div>
							<div class="btnGroup beforeGroup">
								<a class="save beforesave">添加</a>
								<a class="cancel beforecancel">去掉</a>
							</div>
							<div class="groupRadioInfo drivingUS">
								<label>是否有美国驾照</label>
								<input type="radio" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" @change="hasdriverlicense()" class="license" value="1" />是
								<input type="radio" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" @change="hasdriverlicense()" class="license" value="2" checked />否
							</div>
							<div class="driverInfo elementHide">
								<div class="driverYes">
									<c:if test="${!empty obj.driverList }">
										<c:forEach var="driver" items="${obj.driverList }">
											<div class="goUS_drivers">
												<div class="groupcheckBoxInfo driverMain">
													<label>驾照号</label>
													<input id="driverlicensenumber" value="${driver.driverlicensenumber }"  onchange="addSegmentsTranslateZhToEn(this,'driverlicensenumberen','')" name="driverlicensenumber" type="text" >
													<c:if test="${driver.isknowdrivernumber == 1}">
														<input id="isknowdrivernumber" class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" type="checkbox"/>
													</c:if>
												</div>
												<div class="groupSelectInfo driverR">
													<label>哪个州的驾照</label>
													<select id="witchstateofdriver" onchange="addSegmentsTranslateZhToEn(this,'witchstateofdriveren','')" name="witchstateofdriver">
														<option value="0" selected="selected">请选择</option>
														<c:forEach items="${obj.stateUsList }" var="state">
															<c:if test="${driver.witchstateofdriver != state.id}">
																<option value="${state.id }">${state.name }</option>
															</c:if>
															<c:if test="${driver.witchstateofdriver == state.id}">
																<option value="${state.id }" selected="selected">${state.name }</option>
															</c:if>
														</c:forEach>
													</select>
												</div>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${empty obj.driverList }">
										<div class="goUS_drivers">
											<div class="groupcheckBoxInfo driverMain">
												<label>驾照号</label>
												<input id="driverlicensenumber" name="driverlicensenumber" onchange="addSegmentsTranslateZhToEn(this,'driverlicensenumberen','')" type="text" >
												<input id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" name="isknowdrivernumber" type="checkbox"/>
											</div>
											<div class="groupSelectInfo driverR">
												<label>哪个州的驾照</label>
												<select id="witchstateofdriver" onchange="addSegmentsTranslateZhToEn(this,'witchstateofdriveren','')" name="witchstateofdriver">
													<option value="0">请选择</option>
													<c:forEach items="${obj.stateUsList }" var="state">
														<option value="${state.id }">${state.name }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:if>
								</div>
								<div class="btnGroup driverInfo driverGroup">
									<a class="save driversave">添加</a>
									<a class="cancel drivercancel">去掉</a>
								</div>
							</div>
							
							
						</div>
					</div>
				</div>
			</div>
			<!--是否有美国签证-->
			<div class="visaUSMain">
				<div>
					<div class="groupRadioInfo" style="clear: both;">
						<label>是否有美国签证</label>
						<input type="radio" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" @change="isissuedvisa()" class="visaUS" value="1" />是
						<input type="radio" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" @change="isissuedvisa()" class="visaUS" value="2" checked />否
					</div>
					<div>
						<div class="dateIssue goUS_visa">
							<div class="groupInputInfo lastVisaDate">
								<label>最后一次签证的签发日期</label>
								<input id="issueddate" name="issueddate" onchange="translateZhToEn(this,'issueddateen','')" value="${obj.previUSTripInfo_issueddate}" class="datetimepickercss form-control" placeholder="日/月/年" type="text"/>
							</div>
							<div class="groupcheckBoxInfo visaNum visaisnumber">
								<label>签证号码</label>
								<input name="visanumber" class="visanumber" @change="hasvisanumber()" v-model="visaInfo.previUSTripInfo.visanumber" onchange="translateZhToEnVueVisanumber(this,'visanumberen','')" type="text" />
								<input id="idknowvisanumber" :value="visaInfo.previUSTripInfo.idknowvisanumber" onchange="AddSingle(this,'idknowvisanumberen')" name="idknowvisanumber" v-on:click="idknowvisanumberChange" v-model="visaInfo.previUSTripInfo.idknowvisanumber" type="checkbox"/>
							</div>
							<div class="clear"></div>
							<div class="Alike groupRadioInfo paddingTop">
								<label>是否在申请相同类型的签证</label>
								<input type="radio" name="isapplyingsametypevisa" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisa" value="1" />是
								<input type="radio" name="isapplyingsametypevisa" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisa" value="2" checked />否
							</div>
							<div class="describleCountry groupRadioInfo paddingTop">
								<label>是否在签发上述签证的国家或地区申请并且是您所在的国家或地区的居住地</label>
								<input type="radio"  name="issamecountry" v-model="visaInfo.previUSTripInfo.issamecountry" value="1" />是
								<input type="radio"  name="issamecountry" v-model="visaInfo.previUSTripInfo.issamecountry" value="2" checked />否
							</div>
							<div class="paddingTop groupRadioInfo">
								<label>是否采集过指纹</label>
								<input type="radio" name="istenprinted" v-model="visaInfo.previUSTripInfo.istenprinted"  value="1"/>是
								<input type="radio" name="istenprinted" v-model="visaInfo.previUSTripInfo.istenprinted"  value="2" checked />否
							</div>
							<div class="paddingTop">
								<div class="groupRadioInfo">
									<label style="display: block;">你的美国签证是否丢失或被盗过</label>
									<input type="radio" name="islost" v-model="visaInfo.previUSTripInfo.islost" @change="islost()" class="lose" value="1" />是
									<input type="radio" name="islost" v-model="visaInfo.previUSTripInfo.islost" v-on:click="visaNotLost" @change="islost()" class="lose" value="2" checked />否
								</div>
								<div class="yearExplain displayTop elementHide"><!-- 默认隐藏 -->
									<div class="groupInputInfo">
										<label>年份</label>
										<input name="lostyear" id="lostyear" @change="lostyear('lostyear','lostyearen','visaInfo.previUSTripInfo.lostyearen')" v-model="visaInfo.previUSTripInfo.lostyear" type="text" />
									</div>
									<div class="paddingTop grouptextareaInfo">
										<label>说明</label>
										<input class="areaInputPic" name="lostexplain" id="lostexplain" @change="lostexplain('lostexplain','lostexplainen','visaInfo.previUSTripInfo.lostexplainen')" v-model="visaInfo.previUSTripInfo.lostexplain" />
									</div>
								</div>
							</div>
							<div class="clear"></div>
							<div class="paddingTop">
								<div>
									<div class="groupRadioInfo">
										<label>你的美国签证是否被取消或撤销过</label>
										<input type="radio" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" @change="iscancelled()" class="revoke" value="1" />是
										<input type="radio" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" v-on:click="visaNotCancel" @change="iscancelled()" class="revoke" value="2" checked />否
									</div>
									<div class="explain grouptextareaInfo paddingTop">
										<label>说明</label>
										<input name="cancelexplain" class="areaInputPic" id="cancelexplain" @change="cancelexplainen('cancelexplain','cancelexplainen','visaInfo.previUSTripInfo.cancelexplainen')" v-model="visaInfo.previUSTripInfo.cancelexplain" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境-->
			<div class="paddingBottom">
				<div class="groupRadioInfo">
					<label>被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境</label>
					<input type="radio" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" @change="isrefused()" class="refuse" value="1" />是
					<input type="radio" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" @change="isrefused()" v-on:click="visaNotRefused" class="refuse" value="2" checked />否
				</div>
				<div class="refuseExplain grouptextareaInfo">
					<label>说明</label>
					<input name="refusedexplain" class="areaInputPic" id='refusedexplain' @change="refusedexplainen('refusedexplain','refusedexplainen','visaInfo.previUSTripInfo.refusedexplainen')" v-model="visaInfo.previUSTripInfo.refusedexplain" />
				</div>
			</div>
			<!--曾经是否是美国合法永久居民-->
			<div class="paddingBottom ">
				<div class="groupRadioInfo">
					<label>曾经是否是美国合法永久居民</label>
					<input type="radio" name="islegalpermanentresident" v-model="visaInfo.previUSTripInfo.islegalpermanentresident" @change="islegalpermanentresident()" class="onceLegitimate" value="1" />是
					<input type="radio" name="islegalpermanentresident" v-model="visaInfo.previUSTripInfo.islegalpermanentresident" @change="islegalpermanentresident()" v-on:click="visaNotIegal" class="onceLegitimate" value="2" checked />否
				</div>
				<div class="onceExplain grouptextareaInfo">
					<label>说明</label>
					<input name="permanentresidentexplain" id="permanentresidentexplain" @change="permanentresidentexplain('permanentresidentexplain','permanentresidentexplainen','visaInfo.previUSTripInfo.permanentresidentexplainen')" class="areaInputPic" v-model="visaInfo.previUSTripInfo.permanentresidentexplain" />
				</div>
			</div>
			<!--有没有人曾代表您向美国公民和移民服务局提交过移民申请-->
			<div class="paddingBottom">
				<div class="groupRadioInfo">
					<label>有没有人曾代表您向美国公民和移民服务局提交过移民申请</label>
					<input type="radio" name="isfiledimmigrantpetition" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetition" @change="isfiledimmigrantpetition()" class="onceImmigration" value="1" />是
					<input type="radio" name="isfiledimmigrantpetition" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetition" @change="isfiledimmigrantpetition()" v-on:click="visaNotfiledimmigrantpetition" class="onceImmigration" value="2" checked />否
				</div>
				<div class="immigrationExplain grouptextareaInfo">
					<label>说明</label>
					<input name="immigrantpetitionexplain" id="immigrantpetitionexplain" @change="immigrantpetitionexplain('immigrantpetitionexplain','immigrantpetitionexplainen','visaInfo.previUSTripInfo.immigrantpetitionexplainen')" class="areaInputPic" v-model="visaInfo.previUSTripInfo.immigrantpetitionexplain" />
				</div>
			</div>
			<!--以前的美国旅游信息END-->
			<!--美国联络人-->
			<div class="contactPoint">
				<div class="titleInfo">美国联络人</div>
				<div class="groupInputInfo paddingLeft">
					<label>联系人姓</label>
					<input name="firstname" id="firstnameus" class="firstnameus" v-model="visaInfo.contactPointInfo.firstname" @change="liaisonfirstname('firstnameus','firstnameusen','visaInfo.contactPointInfo.firstnameen')" type="text" />
				</div>
				<div class="groupcheckBoxInfo paddingRight">
					<label>联系人名</label>
					<input name="lastname" id="lastnameus" class="lastnameus" v-model="visaInfo.contactPointInfo.lastname" @change="liaisonlastname('lastnameus','lastnameusen','visaInfo.contactPointInfo.lastnameen')" type="text" />
					<input id="isknowname" v-model="visaInfo.contactPointInfo.isknowname" v-on:click="isKnowContactPointName" :value="visaInfo.contactPointInfo.isknowname" name="isknowname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop groupcheckBoxInfo cbox">
					<label>组织名称</label>
					<input id="organizationname" name="organizationname" @change="liaisonOrg('organizationname','organizationnameusen','visaInfo.contactPointInfo.organizationnameen')" v-model="visaInfo.contactPointInfo.organizationname" type="text" />
					<input id="isknoworganizationname" name="isknoworganizationname" onchange="AddSingle(this,'isknoworganizationnameen')" v-on:click="isKnowOrganizationName" v-model="visaInfo.contactPointInfo.isknoworganizationname"  class="groupname_us" type="checkbox" />
				</div>
				<div class="paddingLeft groupSelectInfo">
					<label>与你的关系</label>
					<select id="ralationship" @change="liaisonrelative('ralationship','ralationshipen','visaInfo.contactPointInfo.ralationship')" v-model="visaInfo.contactPointInfo.ralationship" name="ralationship">
						<option value="0" selected="selected">请选择</option>
						<c:forEach items="${obj.ContactPointRelationshipStatusEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="" style="padding: 10px 0;">
					<div class="groupInputInfo draBig">
						<label>美国街道地址(首选)</label>
						<input name="address" id="address" class="address" @change="liaisonfirststreet('address','addressusen','visaInfo.contactPointInfo.addressen')" v-model="visaInfo.contactPointInfo.address" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label>美国街道地址(次选)*可选</label>
						<input name="secaddress" id="secaddress" @change="liaisonlaststreet('secaddress','secaddressusen','visaInfo.contactPointInfo.secaddressen')" v-model="visaInfo.contactPointInfo.secaddress" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupSelectInfo " >
					<label>州/省</label>
						<select id="state" @change="liaisonstate('state','stateen','visaInfo.contactPointInfo.stateen')" name="state" v-model="visaInfo.contactPointInfo.state">
							<option value="0">请选择</option>
							<c:forEach items="${obj.stateUsList }" var="state">
								<option value="${state.id }">${state.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="paddingRight groupInputInfo" >
						<label>市</label>
						<input id="city" name="city" @change="liaisoncity('city','cityusen','visaInfo.contactPointInfo.cityen')" v-model="visaInfo.contactPointInfo.city" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupInputInfo" >
						<label>邮政编码</label>
						<input name="zipcode" id="zipcode" @change="liaisoncode('zipcode','zipcodeusen','visaInfo.contactPointInfo.zipcodeen')" v-model="visaInfo.contactPointInfo.zipcode" type="text" />
					</div>
					<div class="paddingRight groupInputInfo" >
						<label>电话号码</label>
						<input name="telephone" id="telphoneus" @change="liaisontelphone('telphoneus','telephoneusen','visaInfo.contactPointInfo.telephoneen')" v-model="visaInfo.contactPointInfo.telephone" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingTop groupcheckBoxInfo cbox">
						<label>邮件地址</label>
						<input name="email" id="emailus" v-model="visaInfo.contactPointInfo.email" @change="liaisonemail('emailus','emailusen','visaInfo.contactPointInfo.emailen')"  type="text" />
						<input id="isKnowEmailAddress" name="isknowemail" onchange="AddSingle(this,'isKnowEmailAddressen')" v-on:click="isKnowEmailAddress" v-model="visaInfo.contactPointInfo.isknowemail" type="checkbox" />
					</div>
				</div>
			</div>
			<!--美国联络点END-->
			<!--家庭信息-->
			<!--亲属-->
			<div class="familyInfo">
				<div class="titleInfo">家庭信息</div>
				<div class="paddingLeft groupcheckBoxInfo" >
					<label>父亲的姓</label>
					<input name="fatherfirstname" id="fatherfirstname" v-model="visaInfo.familyInfo.fatherfirstname" @change="familyinfofirstname('fatherfirstname','fatherfirstnameen','visaInfo.familyInfo.fatherfirstnameen')" type="text"/>
					<input id="isKnowFatherXing" name="isknowfatherfirstname" onchange="AddSingle(this,'isknowfatherfirstnameen')"  v-on:click="isknowfatherfirstname"  v-model="visaInfo.familyInfo.isknowfatherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label>父亲的名</label>
					<input name="fatherlastname" id="fatherlastname" v-model="visaInfo.familyInfo.fatherlastname" @change="familyinfolastname('fatherlastname','fatherlastnameen','visaInfo.familyInfo.fatherlastnameen')" type="text" />
					<input id="isKnowFatherMing" name="isknowfatherlastname" onchange="AddSingle(this,'isknowfatherlastnameen')" v-on:click="isknowfatherlastname" v-model="visaInfo.familyInfo.isknowfatherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>你的父亲是否在美国</label>
						<input type="radio" name="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" @change="isfatherinus()" class="fatherUS" value="1" />是
						<input type="radio" name="isfatherinus"  v-on:click="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" @change="isfatherinus()" class="fatherUS" value="2" checked />否
					</div>
					<!--yes-->
					<div class="fatherUSYes groupSelectInfo paddingNone">
						<label>身份状态</label>
						<select id="fatherstatus" @change="familyinfostatus('fatherstatus','fatherstatusen','visaInfo.familyInfo.fatherstatusen')" v-model="visaInfo.familyInfo.fatherstatus" name="fatherstatus">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>母亲的姓</label>
					<input id="motherfirstname" name="motherfirstname" @change="familyinfomofirstname('motherfirstname','motherfirstnameen','visaInfo.familyInfo.motherfirstnameen')" v-model="visaInfo.familyInfo.motherfirstname" type="text" />
					<input id="isKnowMotherXing" name="isknowmotherfirstname" onchange="AddSingle(this,'isknowmotherfirstnameen')" v-on:click="isknowmotherfirstname" v-model="visaInfo.familyInfo.isknowmotherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label>母亲的名</label>
					<input id="motherlastname" name="motherlastname" v-model="visaInfo.familyInfo.motherlastname" @change="familyinfomolastname('motherlastname','motherlastnameen','visaInfo.familyInfo.motherlastnameen')"  type="text" />
					<input id="isKnowMotherMing" name="isknowmotherlastname" onchange="AddSingle(this,'isknowmotherlastnameen')" v-on:click="isknowmotherlastname" v-model="visaInfo.familyInfo.isknowmotherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>你的母亲是否在美国</label>
						<input type="radio" name="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" @change="ismotherinues()" class="motherUS" value="1" />是
						<input type="radio" name="ismotherinus" v-on:click="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" @change="ismotherinues()" class="motherUS" value="2" checked />否
					</div>
					<div class="motherUSYes paddingNone groupSelectInfo">
						<label>身份状态</label>
						<select id="motherstatus" @change="familyinfomostatus('motherstatus','motherstatusen','visaInfo.familyInfo.motherstatusen')" name="motherstatus" v-model="visaInfo.familyInfo.motherstatus">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>在美国除了父母还有没有直系亲属</label>
						<input type="radio" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" @change="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="1" />是
						<input type="radio" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" @change="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="2" checked/>否
					</div>
					<div class="directRelatives">
						<!--yes-->
						<c:if test="${!empty obj.zhiFamilyList }">
							<c:forEach var="zhifamily" items="${obj.zhiFamilyList }">
								<div class="directRelativesYes">
									<div class="floatLeft leftNo groupInputInfo">
										<label>姓</label>
										<input name="relativesfirstname" onchange="translateZhToPinYin(this,'relativesfirtstnameen','')" value="${zhifamily.relativesfirstname }" type="text" />
									</div>
									<div class="floatRight groupInputInfo">
										<label>名</label>
										<input name="relativeslastname" onchange="translateZhToPinYin(this,'relativeslastnameen','')" value="${zhifamily.relativeslastname }"  type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>与你的关系</label>
										<select name="relationship" onchange="translateZhToEn(this,'exceptrelationshipen','')">
											<option value="0">请选择</option>
											<c:forEach items="${obj.ImmediateRelationshipEnum }" var="map">
												<c:if test="${zhifamily.relationship != map.key}">
													<option value="${map.key }">${map.value }</option>
												</c:if>
												<c:if test="${zhifamily.relationship == map.key}">
													<option value="${map.key }" selected="selected">${map.value }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="paddingRight groupSelectInfo">
										<label>亲属的身份</label>
										<select id="relativesstatus" onchange="translateZhToEn(this,'exceptrelativesstatusen','')"  name="relativesstatus" >
											<option value="0">请选择</option>
											<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
												<c:if test="${zhifamily.relativesstatus != map.key}">
													<option value="${map.key }">${map.value }</option>
												</c:if>
												<c:if test="${zhifamily.relativesstatus == map.key}">
													<option value="${map.key }" selected="selected">${map.value }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty obj.zhiFamilyList}">
							<div class="directRelativesYes">
								<div class="floatLeft leftNo groupInputInfo">
									<label>姓</label>
									<input name="relativesfirstname" onchange="translateZhToPinYin(this,'relativesfirtstnameen','')" type="text" />
								</div>
								<div class="floatRight groupInputInfo">
									<label>名</label>
									<input name="relativeslastname" onchange="translateZhToPinYin(this,'relativeslastnameen','')" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>与你的关系</label>
									<select name="relationship"  onchange="translateZhToEn(this,'exceptrelationshipen','')">
										<option value="0">请选择</option>
										<c:forEach items="${obj.ImmediateRelationshipEnum }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight groupSelectInfo">
									<label>亲属的身份</label>
									<select id="relativesstatus" onchange="translateZhToEn(this,'exceptrelativesstatusen','')" name="relativesstatus">
										<option value="0">请选择</option>
										<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:if>
						<div class="clear"></div>
						</div>
						<!--NO-->
						<div class="directRelativesNo groupRadioInfo">
							<label>在美国是否还有别的亲属</label>
							<input type="radio" name="hasotherrelatives" v-model="visaInfo.familyInfo.hasotherrelatives" value="1"/>是
							<input type="radio" name="hasotherrelatives" v-model="visaInfo.familyInfo.hasotherrelatives" value="2" checked/>否
						</div>
					</div>
					
				</div>
			<!--配偶-->
			<div class="paddingTop">
				<div class="titleInfo">配偶信息</div>
				<div class="floatLeft groupInputInfo">
					<label>配偶的姓</label>
					<input name="spousefirstname" id="spousefirstname" @change="spousefirstname('spousefirstname','spousefirstnameen','visaInfo.familyInfo.spousefirstnameen')" v-model="visaInfo.familyInfo.spousefirstname" type="text" />
				</div>
				<div class="floatRight groupInputInfo">
					<label>配偶的名</label>
					<input name="spouselastname" id="spouselastname" @change="spouselastname('spouselastname','spouselastnameen','visaInfo.familyInfo.spouselastnameen')" v-model="visaInfo.familyInfo.spouselastname" type="text" />
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupInputInfo">
					<label>配偶的生日</label>
					<input id="spousebirthday" onchange="translateZhToEn(this,'spousebirthdayen','')" name="spousebirthday" value="${obj.spousebirthday}" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
				</div>
				<div class="paddingRight groupSelectInfo">
					<label>配偶的国籍</label>
					<select id="spousenationality" id="spousenationality" @change="spousenationality('spousenationality','spousenationalityen','visaInfo.familyInfo.spousenationalityen')" name="spousenationality" v-model="visaInfo.familyInfo.spousenationality">
						<option value="0">请选择</option>
						<c:forEach items="${obj.gocountryFiveList }" var="country">
							<option value="${country.id }">${country.chinesename }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>配偶的出生城市</label>
					<input name="spousecity" v-model="visaInfo.familyInfo.spousecity" id="spousefcity"  @change="spousecity('spousefcity','spousefcityen','visaInfo.familyInfo.spousecityen')" type="text" />
					<input id="isKnowMatecity" name="isknowspousecity" onchange="AddSingle(this,'isknowspousecityen')" v-on:click="isknowspousecity" v-model="visaInfo.familyInfo.isknowspousecity" type="checkbox" />
				</div>
				<div class="paddingRight groupSelectInfo" >
					<label>配偶的出生国家</label>
					<select id="spousecountry" @change="spousecountry('spousecountry','spousecountryen','visaInfo.familyInfo.spousecountryen')" name="spousecountry" v-model="visaInfo.familyInfo.spousecountry">
						<option value="0">请选择</option>
						<c:forEach items="${obj.gocountryFiveList }" var="country">
							<option value="${country.id }">${country.chinesename }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label>配偶的联系地址</label>
					<select id="spouseaddress" @change="spouseaddress('spouseaddress','spouseaddressen','visaInfo.familyInfo.spouseaddressen')" name="spouseaddress" v-model="visaInfo.familyInfo.spouseaddress" class="spouse_Address">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaSpouseContactAddressEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				
				<!--配偶的联系地址select选择其他-->
				<div class="otherSpouseInfo elementHide paddingTop" >
					<div class="floatLeft groupInputInfo">
						<label>街道地址(首选)</label>
						<input name="firstaddress" id="otherfrstaddress" @change="spouseotherstreet('otherfrstaddress','otherfrstaddressen','visaInfo.familyInfo.firstaddressen')" v-model="visaInfo.familyInfo.firstaddress" type="text" />
					</div>
					<div class="floatRight groupInputInfo">
						<label>街道地址(次要)*可选</label>
						<input name="secondaddress" id="othersecondaddress" @change="spouseotherlaststreet('othersecondaddress','othersecondaddressen','visaInfo.familyInfo.secondaddressen')" v-model="visaInfo.familyInfo.secondaddress" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>州/省</label>
						<input name="province" v-model="visaInfo.familyInfo.province" id="otherprovince" @change="spouseotherprovince('otherprovince','otherprovinceen','visaInfo.familyInfo.provinceen')" type="text" />
						<input id="otherapply" name="otherapply" onchange="AddSingle(this,'otherapplyen')" @click="isotherapply" v-model="visaInfo.familyInfo.apply" type="checkbox" />
					</div>
					<div class="paddingRight groupInputInfo">
						<label>市</label>
						<input name="othercity" v-model="visaInfo.familyInfo.city" id="othercity" @change="spouseothercity('othercity','othercityen','visaInfo.familyInfo.cityen')" type="text"/>
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>邮政编码</label>
						<input name="zipcode" v-model="visaInfo.familyInfo.zipcode" id="otherzipcode" @change="spouseothercode('otherzipcode','otherzipcodeen','visaInfo.familyInfo.zipcodeen')" type="text" />
						<input id="selectcodeapply" name="selectcodeapply" onchange="AddSingle(this,'selectcodeapplyen')" @click="isselectcodeapply" v-model="visaInfo.familyInfo.selectcodeapply" type="checkbox" />
					</div>
					<div class="paddingRight groupSelectInfo">
						<label>国家/地区</label>
						<select name="othercountry" id="othercountry" @change="spouseothercountry('othercountry','othercountryen','visaInfo.familyInfo.countryen')" v-model="visaInfo.familyInfo.country">
							<option value="0">请选择</option>
							<c:forEach items="${obj.gocountryFiveList }" var="country">
								<c:if test="${beforeWork.employercountry != country.id}">
								<option value="${country.id }">${country.chinesename }</option>
								</c:if>
								<c:if test="${beforeWork.employercountry == country.id}">
								<option value="${country.id }" selected="selected">${country.chinesename }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!--家庭信息END-->
			<!--工作/教育/培训信息-->
			<div class="experience paddingTop">
				<div class="titleInfo">工作/教育/培训信息</div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label>主要职业</label>
					<select id="occupation" @change="jobprofession('occupation','occupationen','visaInfo.workEducationInfo.occupationen')" name="occupation" v-model="visaInfo.workEducationInfo.occupation">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaCareersEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="paddingTop elementHide jobEduLearningInfoDiv">
					<div class="groupInputInfo draBig">
						<label>目前的工作单位或者学校名称</label>
						<input name="unitname" id="unitname" @change="jobprofessionunit('unitname','unitnameen','visaInfo.workEducationInfo.unitnameen')" v-model="visaInfo.workEducationInfo.unitname" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label >街道地址(首选)</label>
						<input name="address" id="jobaddress" @change="jobprofessionaddress('jobaddress','jobaddressen','visaInfo.workEducationInfo.addressen')" v-model="visaInfo.workEducationInfo.address" type="text" />
					</div>
					<div class="clear"></div>
					<div class="groupInputInfo draBig marginLS">
						<label>街道地址(次要)*可选</label>
						<input name="secaddress" id="jobsecondaddress" @change="jobprofessionlastaddress('jobsecondaddress','jobsecondaddressen','visaInfo.workEducationInfo.secaddressen')" v-model="visaInfo.workEducationInfo.secaddress" type="text" />
					</div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>州/省</label>
						<input name="province" v-model="visaInfo.workEducationInfo.province" id="jobprovince" @change="jobprofessionprovince('jobprovince','jobprovinceen','visaInfo.workEducationInfo.provinceen')" type="text"/>
						<input name="isprovinceapply" id="isprovinceapplywork" onchange="AddSingle(this,'isprovinceapplyworken')" @click="isprovinceapplywork" v-model="visaInfo.workEducationInfo.isprovinceapply" type="checkbox"/>
					</div>
					<div class="paddingRight groupInputInfo">
						<label>市</label>
						<input name="jobcity" v-model="visaInfo.workEducationInfo.city" id="jobcity" @change="jobprofessioncity('jobcity','jobcityen','visaInfo.workEducationInfo.cityen')" type="text"/>
					</div>
					
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>邮政编码</label>
						<input name="zipcode" v-model="visaInfo.workEducationInfo.zipcode" id="jobzipcode" @change="jobprofessioncode('jobzipcode','jobzipcodeen','visaInfo.workEducationInfo.zipcodeen')" type="text" />
						<input name="jobcodechecked" id="jobcodechecked" onchange="AddSingle(this,'jobcodecheckeden')" @click="isjobcodechecked" v-model="visaInfo.workEducationInfo.iszipcodeapply" type="checkbox" /><!-- iszipcodeapply -->
					</div>
					<div class="paddingRight groupInputInfo">
						<label>电话号吗</label>
						<input name="jobtelephone" v-model="visaInfo.workEducationInfo.telephone" id="jobtelphone" @change="jobprofessiontelphone('jobtelphone','jobtelphoneen','visaInfo.workEducationInfo.telephoneen')" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupSelectInfo" >
						<label>国家/地区</label>
						<select name="jobcountry" id='jobcountry' @change="jobprofessioncountry('jobcountry','jobcountryen','visaInfo.workEducationInfo.jobcountryen')" v-model="visaInfo.workEducationInfo.country">
							<option value="0">请选择</option>
							<c:forEach items="${obj.gocountryFiveList }" var="country">
								<c:if test="${beforeWork.employercountry != country.id}">
								<option value="${country.id }">${country.chinesename }</option>
								</c:if>
								<c:if test="${beforeWork.employercountry == country.id}">
								<option value="${country.id }" selected="selected">${country.chinesename }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					
					<div class="paddingRight groupInputInfo">
						<label>开始日期</label>
						<input id="workstartdate" onchange="translateZhToEn(this,'workstartdateen','')" name="workstartdate" value="${obj.workstartdate}" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo" >
						<label>当地月收入(如果雇佣)</label>
						<input name="salary" v-model="visaInfo.workEducationInfo.salary" id="salary" type="text" @change="jobprofessionsalary('salary','salaryen','visaInfo.workEducationInfo.salaryen')" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"/>
						<input name="moneychecked" id="moneychecked" onchange="AddSingle(this,'moneycheckeden')" @click="ismoneychecked" v-model="visaInfo.workEducationInfo.issalaryapply" type="checkbox" />
					</div>
					<div class="clear"></div>
					<div class="grouptextareaInfo groupPM">
						<label>简要描述你的职责</label>
						<input name="jobduty" class='areaInputPic' id="jobduty" @change="jobprofessionduty('jobduty','jobdutyen','visaInfo.workEducationInfo.dutyen')" v-model="visaInfo.workEducationInfo.duty" />
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="grouptextareaInfo elementHide jobEduLearningInfoTextarea">
					<!-- <label>说明</label>
					<input class="areaInputPic" /> -->
				</div>
			</div>
			<!--以前-->
			<div>
				<div class="paddingTop padding-left">
					<div>
						<div class="groupRadioInfo">
							<label>以前是否工作过</label>
							<input type="radio" name="isemployed" v-model="visaInfo.workEducationInfo.isemployed" @change="isemployed()" class="beforeWork" value="1" />是
							<input type="radio" name="isemployed" v-model="visaInfo.workEducationInfo.isemployed" @change="isemployed()" class="beforeWork" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="beforeWorkInfo elementHide">
						  <div class="beforeWorkYes">
							<c:if test="${!empty obj.beforeWorkList }">
								<c:forEach var="beforeWork" items="${obj.beforeWorkList }">
									<div class="workBeforeInfosDiv">
										<div class="leftNo marginLS groupInputInfo" >
											<label>雇主名字</label>
											<input name="employername" onchange="addSegmentsTranslateZhToPinYin(this,'employernameen','')" value="${beforeWork.employername }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>雇主街道地址(首选)</label>
											<input name="employeraddress" onchange="addSegmentsTranslateZhToEn(this,'employeraddressen','')" value="${beforeWork.employeraddress }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>雇主街道地址(次选)*可选</label>
											<input name="employeraddressSec" onchange="addSegmentsTranslateZhToEn(this,'employeraddressSecen','')" value="${beforeWork.employeraddressSec }" type="text" />
										</div>
										
										<div class="paddingLeft leftNo groupInputInfo">
											<label>州/省</label>
											<input name="employerprovince" onchange="addSegmentsTranslateZhToEn(this,'employerprovinceen','')" value="${beforeWork.employerprovince }" type="text" />
										</div>
										<div class="paddingRight leftNo groupInputInfo" >
											<label>市</label>
											<input name="employercity" id="employercitybefore" onchange="addSegmentsTranslateZhToEn(this,'employercityen','')" value="${beforeWork.employercity }" type="text" />
											
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>邮政编码</label>
											<input name="employerzipcode" onchange="addSegmentsTranslateZhToEn(this,'isKonwOrtherZipCodeen','')" value="${beforeWork.employerzipcode }" type="text" />
											<c:if test="${beforeWork.isemployerzipcodeapply == 1}">
												<input id="isKonwOrtherZipCode" name="isemployerzipcodeapply" onchange="AddSegment(this,'isemployerzipcodeapplyen')" value="${beforeWork.isemployerzipcodeapply }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isemployerzipcodeapply != 1}">
												<input id="isKonwOrtherZipCode" name="isemployerzipcodeapply" onchange="AddSegment(this,'isemployerzipcodeapplyen')" value="${beforeWork.isemployerzipcodeapply }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight groupSelectInfo">
											<label>国家/地区</label>
											<select name="employercountry" onchange="addSegmentsTranslateZhToEn(this,'employercountryen','')">
												<option value="0">请选择</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${beforeWork.employercountry != country.id}">
														<option value="${country.id }">${country.chinesename }</option>
													</c:if>
													<c:if test="${beforeWork.employercountry == country.id}">
														<option value="${country.id }" selected="selected">${country.chinesename }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>电话号码</label>
											<input name="employertelephone" onchange="addSegmentsTranslateZhToEn(this,'employertelephoneen','')" value="${beforeWork.employertelephone }" type="text" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>职称</label>
											<input name="jobtitle" value="${beforeWork.jobtitle }" onchange="addSegmentsTranslateZhToEn(this,'jobtitleen','')" type="text"/>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>主管的姓</label>
											<input name="supervisorfirstname" onchange="addSegmentsTranslateZhToPinYin(this,'isknowsupervisorfirstnamebeforeen','')"  value="${beforeWork.supervisorfirstname }" type="text" />
											<c:if test="${beforeWork.isknowsupervisorfirstname == 1}">
												<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" onchange="AddSegment(this,'isknowjobfirstnameen')" value="${beforeWork.isknowsupervisorfirstname }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorfirstname != 1}">
												<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" onchange="AddSegment(this,'isknowjobfirstnameen')" value="${beforeWork.isknowsupervisorfirstname }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight groupcheckBoxInfo">
											<label>主管的名</label>
											<input name="supervisorlastname" value="${beforeWork.supervisorlastname }"  onchange="addSegmentsTranslateZhToPinYin(this,'isknowsupervisorlastnamebeforeen','')" type="text" />
											<c:if test="${beforeWork.isknowsupervisorlastname == 1}">
												<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" onchange="AddSegment(this,'isknowjoblastnameen')" value="${beforeWork.isknowsupervisorlastname }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorlastname != 1}">
												<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" onchange="AddSegment(this,'isknowjoblastnameen')" value="${beforeWork.isknowsupervisorlastname }" type="checkbox" />
											</c:if>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo" >
											<label>入职时间</label>
											<input id="employstartdate" onchange="addSegmentsTranslateZhToEn(this,'employstartdateen','')" name="employstartdate" value="<fmt:formatDate value="${beforeWork.employstartdate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>离职时间</label>
											<input id="employenddate" onchange="addSegmentsTranslateZhToEn(this,'employenddateen','')" name="employenddate" value="<fmt:formatDate value="${beforeWork.employenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
										</div>
										<div class="clear"></div>
										<div class="draBig leftNo marginLS grouptextareaInfo">
											<label>简要描述你的职责</label>
											<input type="text" name="previousduty" onchange="addSegmentsTranslateZhToEn(this,'previousdutyen','')" class="areaInputPic previousduty" value="${beforeWork.previousduty }" />
											<%-- <textarea name="previousduty" class="bigArea previousduty" value="${beforeWork.previousduty }"></textarea> --%>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeWorkList }">
								<div class="workBeforeInfosDiv">
									<div class="leftNo marginLS groupInputInfo" >
										<label>雇主名字</label>
										<input name="employername" onchange="addSegmentsTranslateZhToPinYin(this,'employernameen','')" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>雇主街道地址(首选)</label>
										<input name="employeraddress" onchange="addSegmentsTranslateZhToEn(this,'employeraddressen','')" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>雇主街道地址(次选)*可选</label>
										<input name="employeraddressSec" onchange="addSegmentsTranslateZhToEn(this,'employeraddressSecen','')" type="text" />
									</div>
									
									<div class="paddingLeft leftNo groupInputInfo">
										<label>州/省</label>
										<input name="employerprovince" onchange="addSegmentsTranslateZhToEn(this,'employerprovinceen','')" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo" >
										<label>市</label>
										<input name="employercity" type="text" onchange="addSegmentsTranslateZhToEn(this,'employercityen','')" />
										
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>邮政编码</label>
										<input name="employerzipcode" type="text" onchange="addSegmentsTranslateZhToEn(this,'employerzipcodeen','')" />
										<input name="isemployerzipcodeapply" onchange="AddSegment(this,'isemployerzipcodeapplyen')" id="isKonwOrtherZipCode" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo">
										<label>国家/地区</label>
										<select name="employercountry" onchange="addSegmentsTranslateZhToEn(this,'employercountryen','')">
											<option value="0">请选择</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<option value="${country.id }">${country.chinesename }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>电话号码</label>
										<input name="employertelephone" onchange="addSegmentsTranslateZhToEn(this,'employertelephoneen','')" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>职称</label>
										<input name="jobtitle" onchange="addSegmentsTranslateZhToEn(this,'jobtitleen','')"  type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>主管的姓</label>
										<input name="supervisorfirstname" onchange="addSegmentsTranslateZhToPinYin(this,'isknowsupervisorfirstnamebeforeen','')"  type="text" />
										<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" onchange="AddSegment(this,'isknowjobfirstnameen')" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupcheckBoxInfo">
										<label>主管的名</label>
										<input name="supervisorlastname" onchange="addSegmentsTranslateZhToPinYin(this,'isknowsupervisorlastnamebeforeen','')"  type="text" />
										<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" onchange="AddSegment(this,'isknowjoblastnameen')" type="checkbox" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>入职时间</label>
										<input id="employstartdate" onchange="addSegmentsTranslateZhToEn(this,'employstartdateen','')" name="employstartdate" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>离职时间</label>
										<input id="employenddate" onchange="addSegmentsTranslateZhToEn(this,'employenddateen','')" name="employenddate" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
									</div>
									<div class="clear"></div>
									<div class="draBig leftNo marginLS grouptextareaInfo">
										<label>简要描述你的职责</label>
										<input type="text" name="previousduty" onchange="addSegmentsTranslateZhToEn(this,'previousdutyen','')" class="areaInputPic previousduty" />
										<!-- <textarea class="bigArea" name="previousduty"></textarea> -->
									</div>
								</div>
							</c:if>
							</div>
							<div class="clear"></div>
							<div class="btnGroup marginLS beforeWorkGroup">
								<a class="save beforeWorksave">添加</a>
								<a class="cancel beforeWorkcancel">去掉</a>
							</div>
						</div>
					</div>
				</div>
				<div class="padding-left">
					<div class="paddingTop">
						<div class="groupRadioInfo">
							<label>是否上过高中或以上的任何教育</label>
							<input type="radio" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" @change="issecondarylevel()" class="education" value="1" />是
							<input type="radio" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" @change="issecondarylevel()" class="education" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="educationInfo elementHide">
							<div class="educationYes">
							<c:if test="${!empty obj.beforeEducationList }">
								<c:forEach var="education" items="${obj.beforeEducationList }">
									<div class="midSchoolEduDiv">
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>机构名称</label>
											<input name="institution" onchange="addSegmentsTranslateZhToEn(this,'institutionen','')" value="${education.institution }" type="text"/>
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>街道地址(首选)</label>
											<input name="institutionaddress" onchange="addSegmentsTranslateZhToEn(this,'institutionaddressen','')" value="${education.institutionaddress }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>街道地址(次选)*可选</label>
											<input name="secinstitutionaddress" onchange="addSegmentsTranslateZhToEn(this,'secinstitutionaddressen','')" type="text" value="${education.secinstitutionaddress }" />
										</div>
										<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>州/省</label>
											<input name="institutionprovince" onchange="addSegmentsTranslateZhToEn(this,'isinstitutionprovinceapplyen','')" value="${education.institutionprovince }" type="text" />
											<c:if test="${education.isinstitutionprovinceapply == 1}">
												<input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionprovinceapply != 1}">
												<input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >市</label>
											<input name="institutioncity" onchange="addSegmentsTranslateZhToEn(this,'institutioncityen','')" value="${education.institutioncity }" type="text" />
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>邮政编码</label>
											<input name="institutionzipcode" onchange="addSegmentsTranslateZhToEn(this,'institutionzipcodeen','')" value="${education.institutionzipcode }" type="text" />
											<c:if test="${education.isinstitutionzipcodeapply == 1}">
													<input name="isinstitutionzipcodeapply"  id="codeEdu" onchange="AddSegment(this,'codeEduen')" value="${education.isinstitutionzipcodeapply }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionzipcodeapply != 1}">
												<input name="isinstitutionzipcodeapply" id="codeEdu" onchange="AddSegment(this,'codeEduen')" value="${education.isinstitutionzipcodeapply }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight leftNo groupSelectInfo" >
											<label>国家/地区</label>
											<select name="institutioncountry" onchange="addSegmentsTranslateZhToEn(this,'institutioncountryen','')">
												<option value="0">请选择</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${education.institutioncountry != country.id}">
														<option value="${country.id }">${country.chinesename }</option>
													</c:if>
													<c:if test="${education.institutioncountry == country.id}">
														<option value="${country.id }" selected="selected">${country.chinesename }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>学科</label>
											<input name="course" onchange="addSegmentsTranslateZhToEn(this,'courseen','')" value="${education.course }" type="text" />
										</div>
										
										<div class="paddingRight leftNo groupInputInfo">
											<label>参加课程开始时间</label>
											<input id="coursestartdate" onchange="addSegmentsTranslateZhToEn(this,'coursestartdateen','')" name="coursestartdate" value="<fmt:formatDate value="${education.coursestartdate }" pattern="dd/MM/yyyy" />"  class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
										</div>
										<div class="clear"></div>
										<div class="leftNo paddingTop groupInputInfo">
											<label>结束时间</label>
											<input id="courseenddate" onchange="addSegmentsTranslateZhToEn(this,'courseenddateen','')" name="courseenddate" value="<fmt:formatDate value="${education.courseenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeEducationList }">
								<div class="midSchoolEduDiv">
									<div class="draBig leftNo groupInputInfo">
										<label>机构名称</label>
										<input name="institution" onchange="addSegmentsTranslateZhToEn(this,'institutionen','')" type="text"/>
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>街道地址(首选)</label>
										<input name="institutionaddress" onchange="addSegmentsTranslateZhToEn(this,'institutionaddressen','')" type="text" />
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>街道地址(次选)*可选</label>
										<input name="secinstitutionaddress" onchange="addSegmentsTranslateZhToEn(this,'secinstitutionaddressen','')" type="text" />
									</div>
									<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>州/省</label>
											<input name="institutionprovince" onchange="addSegmentsTranslateZhToEn(this,'institutionprovinceen','')" type="text" />
											<input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" type="checkbox" />
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >市</label>
											<input name="institutioncity" onchange="addSegmentsTranslateZhToEn(this,'institutioncityen','')" type="text" />
										</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>邮政编码</label>
										<input name="institutionzipcode" onchange="addSegmentsTranslateZhToEn(this,'institutionzipcodeen','')" type="text" />
										<input name="isinstitutionzipcodeapply" onchange="AddSegment(this,'codeEduen')" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo" >
										<label>国家/地区</label>
										<select name="institutioncountry" onchange="addSegmentsTranslateZhToEn(this,'institutioncountryen','')">
											<option value="0">请选择</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<option value="${country.id }">${country.chinesename }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>学科</label>
										<input name="course" onchange="addSegmentsTranslateZhToEn(this,'courseen','')" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>参加课程开始时间</label>
										<input id="coursestartdate" onchange="addSegmentsTranslateZhToEn(this,'coursestartdateen','')" name="coursestartdate" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
									</div>
									<div class="clear"></div>
									<div class="leftNo groupInputInfo">
										<label>结束时间</label>
										<input id="courseenddate" onchange="addSegmentsTranslateZhToEn(this,'courseenddateen','')" name="courseenddate" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
									</div>
								</div>
							</c:if>
							</div>
							<div class="clear"></div>
							<div class="btnGroup educationGroup">
								<a class="save educationsave" >添加</a>
								<a class="cancel educationcancel" >去掉</a>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<!--额外-->
			<div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否属于氏族或部落</label>
						<input type="radio" name="isclan" v-model="visaInfo.workEducationInfo.isclan" @change="isclan()" class="isclan" value="1"/>是
						<input type="radio" name="isclan" v-on:click="isclan()" v-model="visaInfo.workEducationInfo.isclan" @change="isclan()" class="isclan" value="2" checked/>否
					</div>
					
					<!--yes-->
					<div class="isclanYes elementHide">
						<div>
							<div class="clannameDiv">
								<div class="draBig leftNo groupInputInfo" >
									<label>氏族或部落名称</label>
									<input name="clanname" id="clanname" @change="clanname('clanname','clannameen','visaInfo.workEducationInfo.clannameen')" v-model="visaInfo.workEducationInfo.clanname"  type="text"  />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="languageYes">
					<c:if test="${!empty obj.languageList }">
						<c:forEach var="language" items="${obj.languageList }">
							<div class="languagename languagenameDiv paddingTop padding-left">
								<label>使用的语言名称</label>
								<div class="groupInputInfo">
									<input name="languagename" onchange="addSegmentsTranslateZhToEn(this,'languagenamewen','')" value="${language.languagename }" type="text" />
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty obj.languageList }">
						<div class="languagename languagenameDiv paddingTop padding-left">
							<label>使用的语言名称</label>
							<div class="groupInputInfo">
								<input name="languagename" onchange="addSegmentsTranslateZhToEn(this,'languagenamewen','')" type="text" />
							</div>
						</div>
					</c:if>
				</div>	
				<div class="btnGroup draBig languageGroup">
					<a class="save languagesave">添加</a>
					<a class="cancel languagecancel">去掉</a>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>过去五年是否曾去过任何国家/地区旅游</label>
						<input type="radio" name="istraveledanycountry" v-model="visaInfo.workEducationInfo.istraveledanycountry"  @change="istraveledanycountry()"  class="istraveledanycountry" value="1" />是
						<input type="radio" name="istraveledanycountry" v-model="visaInfo.workEducationInfo.istraveledanycountry" @change="istraveledanycountry()" class="istraveledanycountry" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="isTravelYes elementHide">
						<div class="gocountryYes">
							<c:if test="${!empty obj.gocountryList }">
								<c:forEach var="gocountry" items="${obj.gocountryList }">
									<div class="travelCountry paddingTop groupInputInfo">
										<label>国家/地区</label>
										<div class="groupInputInfo groupSelectInfo">
										
											<select name="traveledcountry" onchange="addSegmentsTranslateZhToEn(this,'traveledcountryen','')"  >
												<option value="0">请选择</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
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
							<c:if test="${empty obj.gocountryList }">
								<div class="paddingTop travelCountry groupInputInfo">
									<label>国家/地区</label>
									<div class="groupInputInfo groupSelectInfo">
										<select name="traveledcountry" onchange="addSegmentsTranslateZhToEn(this,'traveledcountryen','')">
											<option value="0">请选择</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<option value="${country.id }">${country.chinesename }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
						</div>
						<div class="btnGroup gocountryGroup">
							<a class="save gocountrysave">添加</a>
							<a class="cancel gocountrycancel">去掉</a>
						</div>
					</div>
				</div>
				<div class="padding-left paddingTop">
					<div class="groupRadioInfo">
						<label>是否属于、致力于、或为任何专业、社会或慈善组织而工作</label>
						<input type="radio" name="isworkedcharitableorganization" v-model="visaInfo.workEducationInfo.isworkedcharitableorganization" @change="isworkedcharitableorganization()"  class="isworkedcharitableorganization" value="1"/>是
						<input type="radio" name="isworkedcharitableorganization" v-model="visaInfo.workEducationInfo.isworkedcharitableorganization" @change="isworkedcharitableorganization()" class="isworkedcharitableorganization" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="isOrganizationYes elementHide">
						<div class="organizationYes">
							<c:if test="${!empty obj.organizationList }">
								<c:forEach var="organization" items="${obj.organizationList }">
								<div class="organizationDiv">
									<div class="paddingTop draBig leftNo groupInputInfo">
										<label>组织名称</label>
										<input name="organizationname" onchange="addSegmentsTranslateZhToEn(this,'organizationnameen','')" value="${organization.organizationname }" type="text"/>
									</div>
								</div>	
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.organizationList }">
							<div class="organizationDiv">
								<div class="paddingTop draBig leftNo groupInputInfo">
									<label>组织名称</label>
									<input name="organizationname" onchange="addSegmentsTranslateZhToEn(this,'organizationnameen','')" type="text"/>
								</div>
							</div>	
							</c:if>
						</div>
						<div class="btnGroup organizationGroup">
							<a class="save organizationsave">添加</a>
							<a class="cancel organizationcancel">去掉</a>
						</div>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否有专业技能或培训，如强制、爆炸物、核能、生物或化学</label>
						<input type="radio" name="hasspecializedskill" v-model="visaInfo.workEducationInfo.hasspecializedskill" @change="hasspecializedskill()" class="hasspecializedskill" value="1" />是
						<input type="radio"name="hasspecializedskill" v-on:click="hasspecializedskill()" v-model="visaInfo.workEducationInfo.hasspecializedskill" @change="hasspecializedskill()"  class="hasspecializedskill" value="2" checked />否
					</div>
					<!--yes-->
					<div class="paddingTop skillDiv elementHide grouptextareaInfo">
						<label>说明</label>
						<input type="text" name="skillexplain" id='skillexplain' @change="skillexplains('skillexplain','skillexplainen','visaInfo.workEducationInfo.skillexplainen')" class="areaInputPic" v-model="visaInfo.workEducationInfo.skillexplain" />
					</div>
				</div>
				<div class="padding-left paddingTop">
					<div class="groupRadioInfo">
						<label style="display: block;">是否曾服兵役</label>
						<input type="radio" name="hasservedinmilitary" v-model="visaInfo.workEducationInfo.hasservedinmilitary" @change="hasservedinmilitary()" class="hasservedinmilitary" value="1"/>是
						<input type="radio"name="hasservedinmilitary" v-model="visaInfo.workEducationInfo.hasservedinmilitary"@change="hasservedinmilitary()" class="hasservedinmilitary" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="paddingTop elementHide militaryServiceYes">
					  <div class="militaryYes">
						<c:if test="${!empty obj.conscientiousList }">
							<c:forEach var="conscientious" items="${obj.conscientiousList }">
								<div class="militaryInfoDiv">
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>国家/地区</label>
										<select name="militarycountry" onchange="addSegmentsTranslateZhToEn(this,'militarycountryen','')" >
											<option value="0">请选择</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${conscientious.militarycountry != country.id}">
														<option value="${country.id }">${country.chinesename }</option>
													</c:if>
													<c:if test="${conscientious.militarycountry == country.id}">
														<option value="${country.id }" selected="selected">${country.chinesename }</option>
													</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>服务分支</label>
										<input name="servicebranch"  onchange="addSegmentsTranslateZhToEn(this,'servicebranchen','')" value="${conscientious.servicebranch }" type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>排名/位置</label>
										<input name="rank" onchange="addSegmentsTranslateZhToEn(this,'ranken','')" value="${conscientious.rank }" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>军事专业</label>
										<input name="militaryspecialty" onchange="addSegmentsTranslateZhToEn(this,'militaryspecialtyen','')" value="${conscientious.militaryspecialty }" type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>服兵役开始时间日期</label>
										<input id="servicestartdate" onchange="addSegmentsTranslateZhToEn(this,'servicestartdateen','')" name="servicestartdate" value="<fmt:formatDate value="${conscientious.servicestartdate }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss form-control" placeholder="日/月/年"/>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>结束时间</label>
										<input id="serviceenddate" onchange="addSegmentsTranslateZhToEn(this,'serviceenddateen','')" name="serviceenddate" value="<fmt:formatDate value="${conscientious.serviceenddate }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss form-control" placeholder="日/月/年"/>
									</div>
								</div>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty obj.conscientiousList }">
							<div class="militaryInfoDiv">
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>国家/地区</label>
									<select name="militarycountry"  onchange="addSegmentsTranslateZhToEn(this,'militarycountryen','')">
										<option value="0">请选择</option>
										<c:forEach items="${obj.gocountryFiveList }" var="country">
											<option value="${country.id }">${country.chinesename }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>服务分支</label>
									<input name="servicebranch" onchange="addSegmentsTranslateZhToEn(this,'servicebranchen','')" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo" >
									<label>排名/位置</label>
									<input name="rank" onchange="addSegmentsTranslateZhToEn(this,'ranken','')" type="text" />
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>军事专业</label>
									<input name="militaryspecialty" onchange="addSegmentsTranslateZhToEn(this,'militaryspecialtyen','')" type="text"/>
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo">
									<label>服兵役开始时间日期</label>
									<input id="servicestartdate" onchange="addSegmentsTranslateZhToEn(this,'servicestartdateen','')" name="servicestartdate" type="text" class="datetimepickercss form-control" placeholder="日/月/年"/>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>结束时间</label>
									<input id="serviceenddate" onchange="addSegmentsTranslateZhToEn(this,'serviceenddateen','')" name="serviceenddate" type="text" class="datetimepickercss form-control" placeholder="日/月/年"/>
								</div>
							</div>
						</c:if>	
						</div>
						<div class="clear"></div>
						<div class="btnGroup militaryGroup">
							<a class="save militarysave">添加</a>
							<a class="cancel militarycancel">去掉</a>
						</div>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织</label>
						<input type="radio" name="isservedinrebelgroup" v-model="visaInfo.workEducationInfo.isservedinrebelgroup" class="isservedinrebelgroup" value="1"/>是
						<input type="radio" name="isservedinrebelgroup" v-model="visaInfo.workEducationInfo.isservedinrebelgroup" class="isservedinrebelgroup" value="2" checked/>否
					</div>
					<!--yes-->
					<!-- <div class="paddingTop elementHide dinrebelDiv grouptextareaInfo">
						<label>说明</label>
						<input />
					</div> -->
				</div>
			</div>	
			<!--工作/教育/培训信息END-->
			<!--安全和背景-->
			<div class="safe paddingTop">
				<div class="titleInfo">安全和背景</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否患有传染性疾病</label>
						<input type="radio" name="isPestilence" class="isPestilence" value="1"/>是
						<input type="radio" name="isPestilence" class="isPestilence" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isPestilenceDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否有精神或身体疾病，可能对他人安全和福利构成威胁</label>
						<input type="radio" name="isThreatIllness" class="isThreatIllness" value="1"/>是
						<input type="radio" name="isThreatIllness" class="isThreatIllness" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isThreatIllnessDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否吸毒或曾经吸毒</label>
						<input type="radio" name="isDrug" class="isDrug" value="1"/>是
						<input type="radio" name="isDrug" class="isDrug" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isDrugDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否因犯罪或违法而逮捕或被判刑，即使后来受到赦免、宽恕或其他类似的裁决</label>
						<input type="radio" name="isSentenced" class="isSentenced" value="1"/>是
						<input type="radio" name="isSentenced" class="isSentenced" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isSentencedDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否违反过有关管控物资方面法律</label>
						<input type="radio" name="isMaterialLaw" class="isMaterialLaw" value="1"/>是
						<input type="radio" name="isMaterialLaw" class="isMaterialLaw" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isMaterialLawDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">您是来美国从事卖淫或非法商业性交吗？在过去十年中，您是否从事过卖淫或组织介绍过卖淫</label>
						<input type="radio" name="isProstitution" class="isProstitution" value="1"/>是
						<input type="radio" name="isProstitution" class="isProstitution" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isProstitutionDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经彩玉或意图从事洗钱活动</label>
						<input type="radio" name="isLaundering" class="isLaundering" value="1"/>是
						<input type="radio" name="isLaundering" class="isLaundering" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isLaunderingDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">是否曾在美国或美国意外的地方犯有或密谋人口走私罪</label>
						<input type="radio" name="isSmuggling" class="isSmuggling" value="1"/>是
						<input type="radio" name="isSmuggling" class="isSmuggling" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isSmugglingDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否故意资助、教唆、协助或勾结某个人，而这个人在美国或美国以外的地方曾犯有或密谋了严重的人口走私案</label>
						<input type="radio" name="isThreateningOthers" class="isThreateningOthers" value="1"/>是
						<input type="radio" name="isThreateningOthers" class="isThreateningOthers" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isThreateningOthersDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否是曾在美国或美国以外犯有或密谋人口走私案的配偶或子女？是否在最近5年里从走私活动中获得过好处</label>
						<input type="radio" name="isSmugglingBenefits" class="isSmugglingBenefits" value="1"/>是
						<input type="radio" name="isSmugglingBenefits" class="isSmugglingBenefits" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isSmugglingBenefitsDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否在美国企图从事间谍活动，破坏活动，违反出口管制或任何其他非法活动</label>
						<input type="radio" name="isSpyActivities" class="isSpyActivities" value="1"/>是
						<input type="radio" name="isSpyActivities" class="isSpyActivities" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isSpyActivitiesDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否在美国企图从事恐怖活动，还是曾经参与过恐怖行动</label>
						<input type="radio" name="isTerroristActivities" class="isTerroristActivities" value="1"/>是
						<input type="radio" name="isTerroristActivities" class="isTerroristActivities" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isTerroristActivitiesDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否有意向向恐怖分子或组织提供财政支援或其他支持</label>
						<input type="radio" name="isSupportTerrorists" class="isSupportTerrorists" value="1"/>是
						<input type="radio" name="isSupportTerrorists" class="isSupportTerrorists" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isSupportTerroristsDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否是恐怖组织的成员或代表</label>
						<input type="radio" name="isTerrorist" class="isTerrorist" value="1"/>是
						<input type="radio" name="isTerrorist" class="isTerrorist" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isTerroristDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经下令、煽动、承诺、协助或以其他方式参与种族灭绝</label>
						<input type="radio" name="isTakeGenocide" class="isTakeGenocide" value="1"/>是
						<input type="radio" name="isTakeGenocide" class="isTakeGenocide" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isTakeGenocideDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经犯下、下令、煽动协助或以其他方式参与过酷刑</label>
						<input type="radio" name="isOrderedThreat" class="isOrderedThreat" value="1"/>是
						<input type="radio" name="isOrderedThreat" class="isOrderedThreat" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isOrderedThreatDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经犯下、下令、煽动协助或以其他方式参与过法外杀戮、政治杀戮或其他暴力行为</label>
						<input type="radio" name="isPoliticalKilling" class="isPoliticalKilling" value="1"/>是
						<input type="radio" name="isPoliticalKilling" class="isPoliticalKilling" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isPoliticalKillingDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否参与招募或使用童兵</label>
						<input type="radio" name="isRecruitChildSoldier" class="isRecruitChildSoldier" value="1"/>是
						<input type="radio" name="isRecruitChildSoldier" class="isRecruitChildSoldier" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isRecruitChildSoldierDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>担任官员时，是否曾经负责或直接执行特别严重的侵犯宗教自由的行为</label>
						<input type="radio" name="isInroadReligion" class="isInroadReligion" value="1"/>是
						<input type="radio" name="isInroadReligion" class="isInroadReligion" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isInroadReligionDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经直接参与或建立执行人口控制措施，强迫妇女对其自由选择进行堕胎，或是否有男人或女人违反其自由意志进行绝育</label>
						<input type="radio" name="isForcedControlPopulation" class="isForcedControlPopulation" value="1"/>是
						<input type="radio" name="isForcedControlPopulation" class="isForcedControlPopulation" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isForcedControlPopulationDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾经直接参与人体器官或身体组织的强制移植</label>
						<input type="radio" name="isForcedOrganTake" class="isForcedOrganTake" value="1"/>是
						<input type="radio" name="isForcedOrganTake" class="isForcedOrganTake" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isForcedOrganTakeDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾通过欺诈或故意虚假陈述或其他非法手段获得或协助他人获得美国签证，入境美国或获取任何其他移民福利</label>
						<input type="radio" name="isIllegalVisaUS" class="isIllegalVisaUS" value="1"/>是
						<input type="radio" name="isIllegalVisaUS" class="isIllegalVisaUS" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isIllegalVisaUSDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾被驱逐出境</label>
						<input type="radio" name="isDeported" class="isDeported" value="1"/>是
						<input type="radio" name="isDeported" class="isDeported" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isDeportedDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>在过去的5年中是否参加过远程访问或不可受理的听证会</label>
						<input type="radio" name="isRemoteHearing" class="isRemoteHearing" value="1"/>是
						<input type="radio" name="isRemoteHearing" class="isRemoteHearing" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isRemoteHearingDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">是否曾经非法存在，超出了入境官员的时间，或以其他方式违反了美国的签证条款</label>
						<input type="radio" name="isViolationVisaConditions" class="isViolationVisaConditions" value="1"/>是
						<input type="radio" name="isViolationVisaConditions" class="isViolationVisaConditions" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isViolationVisaConditionsDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>您是否曾经拒绝将一在美国境外的美籍儿童的监护权移交给一被美国法庭批准享有法定监护权的人</label>
						<input type="radio" name="isRefusalTransferCustody" class="isRefusalTransferCustody" value="1"/>是
						<input type="radio" name="isRefusalTransferCustody" class="isRefusalTransferCustody" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isRefusalTransferCustodyDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否违反了法律或规定在美国进行过投票选举</label>
						<input type="radio" name="isIllegalVoting" class="isIllegalVoting" value="1"/>是
						<input type="radio" name="isIllegalVoting" class="isIllegalVoting" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isIllegalVotingDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否为逃税放弃过美国公民身份</label>
						<input type="radio" name="isTaxEvasion" class="isTaxEvasion" value="1"/>是
						<input type="radio" name="isTaxEvasion" class="isTaxEvasion" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isTaxEvasionDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>是否曾在1996年11月30日之后以学生身份到美国的一公立小学或公立中学就读而没有向学校补交费用</label>
						<input type="radio" name="isNotPayTuitionFees" class="isNotPayTuitionFees" value="1"/>是
						<input type="radio" name="isNotPayTuitionFees" class="isNotPayTuitionFees" value="2" checked/>否
					</div>
					<div class="paddingTop elementHide isNotPayTuitionFeesDiv grouptextareaInfo">
						<label>说明</label>
						<textarea></textarea>
					</div>
				</div>
			</div>
			<!--安全和背景END-->
		</div>
		<div class="English" style="visibility:hidden;">
			<!--旅伴信息-->
			<div class="companyInfoModule">
				<div class="titleInfo">Company information</div>
				<div class="companyMain">
					<div class="companyMainInfo groupRadioInfo">
						<label>Are there other persons traveling with you</label>
						<input type="radio" class="companyInfoen" name="istravelwithotheren" v-model="visaInfo.travelCompanionInfo.istravelwithotheren" value="1" />YES
						<input type="radio" class="companyInfoen" name="istravelwithotheren" v-model="visaInfo.travelCompanionInfo.istravelwithotheren" value="2" checked/>NO
					</div>
					<!--yes-->
					<div class="teamture elementHide teamtureen">
						<div class="groupRadioInfo">
							<label>Are you traveling as part of a group or organization</label>
							<input type="radio" class="teamen" name="isparten" v-model="visaInfo.travelCompanionInfo.isparten" value="1" />YES
							<input type="radio" class="teamen" name="isparten" v-model="visaInfo.travelCompanionInfo.isparten" value="2" checked/>NO
						</div>
						<!--第二部分yes-->
						<div class="teamnameture teamnametureen groupInputInfo">
							<label>Group Name</label>
							<input id="groupnameen" name="groupnameen" v-model="visaInfo.travelCompanionInfo.groupnameen" type="text" placeholder="Group Name" />
						</div>
						<!--第二部分No-->
						<div class="teamnamefalse teamnamefalseen groupInputInfo">
							<div>
							<c:if test="${!empty obj.companionList }">
								<c:forEach var="companion" items="${obj.companionList }">
									<div class="teamnamefalseDiv teamnamefalseDiven teamaddfalseen" >
										<div class="companionSurnNameen">
											<label class="companyLabel">Surnames of Person Traveling With You</label>
											<input id="firstnameen" class="firstnameen" name="firstnameen" value="${companion.firstnameen }" type="text" placeholder="Surnames of Person Traveling With You" />
										</div>
										<div class="companionNameen">
											<label>Given Names of Person Traveling With You</label>
											<input id="lastnameen" class="lastnameen" name="lastnameen" value="${companion.lastnameen }" type="text" placeholder="Given Names of Person Traveling With You" />
										</div>
										<div class="clear"></div>
										<div class="youRelationship">
											<label>Relationship with Person</label>
											<select id="relationshipen" class="relationshipen" value="${companion.relationshipen }" name="relationshipen">
												<option value="0">Please choose</option>
												<c:forEach items="${obj.TravelCompanionRelationshipEnumen }" var="map">
													<c:if test="${companion.relationshipen != map.key}">
														<option value="${map.key }">${map.value }</option>
													</c:if>
													<c:if test="${companion.relationshipen == map.key}">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.companionList }">
								<div class="teamnamefalseDiv teamnamefalseDiven teamaddfalseen">
									<div class="companionSurnNameen">
										<label>Surnames of Person Traveling With You</label>
										<input id="firstnameen" class="firstnameen" name="firstnameen" type="text" placeholder="Surnames of Person Traveling With You" />
									</div>
									<div class="companionNameen">
										<label>Given Names of Person Traveling With You</label>
										<input id="lastnameen" class="lastnameen" name="lastnameen" type="text" placeholder="Given Names of Person Traveling With You" />
									</div>
									<div class="clear"></div>
									<div class="youRelationship">
										<label>Relationship with Person</label>
										<select id="relationshipen" class="relationshipen" name="relationshipen">
											<option value="0">Please choose</option>
											<c:forEach items="${obj.TravelCompanionRelationshipEnumen }" var="map">
												<option value="${map.key }">${map.value }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:if>
							</div>
							<div class="btnGroup companyGroupen ">
								<a class="save companysaveen">Add Another</a>
								<a class="cancel companycancelen">Remove </a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--旅伴信息END-->
			<!--以前的美国旅游信息-->
			<div class="beforeTourism">
				<!--是否去过美国-->
				<div class="goUSModule">
					<div class="titleInfo">Previous American tourism information</div>
					<div class="goUSMain">
						<div class="groupRadioInfo goUSPad">
							<label>Have you ever been in the U.S.</label>
					 		<input type="radio" id="hasbeeninusen" name="hasbeeninusen" v-model="visaInfo.previUSTripInfo.hasbeeninusen" class="goUSen" value="1" />YES
							<input type="radio" id="hasbeeninusen" name="hasbeeninusen" v-model="visaInfo.previUSTripInfo.hasbeeninusen" class="goUSen" value="2" checked />NO
						</div>
						<!--yes-->
						<div class="goUSInfo goUSInfoen goUSYes">
							<div class="gotousInfo gotousInfoen">
								<c:if test="${!empty obj.gousList }">
									<c:forEach var="gous" items="${obj.gousList }">
										<div class="goUS_CountryDiven">
											<div class="groupInputInfo">
												<label>Date Arrived</label>
												<input type="text" id="arrivedateen" value="<fmt:formatDate value="${gous.arrivedateen }" pattern="dd/MM/yyyy" />" name="arrivedateen" class="datetimepickercss form-control arrivedateen" placeholder="Day / month / year">
											</div>
											<div class="groupInputInfo stopDate goUS_Country">
												<label>Length of Stay</label>
												<input id="staydaysen" name="staydaysen" class="staydaysen" value="${gous.staydaysen }" type="text" />
												<select id="dateuniten" class="dateuniten" name="dateuniten">
													<option value="0">Please choose</option>
													<c:forEach items="${obj.TimeUnitStatusEnumen }" var="map">
														<c:if test="${gous.dateuniten != map.key}">
															<option value="${map.key }">${map.value }</option>
														</c:if>
														<c:if test="${gous.dateuniten == map.key}">
															<option value="${map.key }" selected="selected">${map.value }</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:forEach>
								</c:if>
								<c:if test="${empty obj.gousList }">
									<div class="goUS_CountryDiven">
										<div class="groupInputInfo">
											<label>Date Arrived</label>
											<input type="text" id="arrivedateen" name="arrivedateen" class="datetimepickercss form-control arrivedateen" placeholder="Day / month / year">
										</div>
										<div class="groupInputInfo stopDate">
											<label>Length of Stay</label>
											<input id="staydaysen" class="staydaysen" name="staydaysen" type="text" />
											<select id="dateuniten" class="dateuniten" name="dateuniten">
												<option value="0">Please choose</option>
												<c:forEach items="${obj.TimeUnitStatusEnumen }" var="map">
													<option value="${map.key }">${map.value }</option>
												</c:forEach>
											</select>
        										</div>   
								 	      </div>
								</c:if>                                                                                                                                                                                        
							</div>
							<div class="btnGroup beforeAGroup companyGroupen everyMarTop">                    
								<a class="save beforesaveen">Add Another</a>                    
								<a class="cancel beforecancelen">Remove</a>
							</div>
							<div class="groupRadioInfo drivingUS">
								<label>Do you or did you ever hold a U.S. Driver’s License</label>
								<input type="radio" name="hasdriverlicenseen" v-model="visaInfo.previUSTripInfo.hasdriverlicenseen" class="licenseen" value="1" />YES
								<input type="radio" name="hasdriverlicenseen" v-model="visaInfo.previUSTripInfo.hasdriverlicenseen" class="licenseen" value="2" checked />NO
							</div>
							<div class="driverInfo driverInfoen elementHide">
								<div class="driverYes">
									<c:if test="${!empty obj.driverList }">
										<c:forEach var="driver" items="${obj.driverList }">
											<div class="goUS_driversen">
												<div class="groupcheckBoxInfo driverMain">
													<label>Driver's License Number</label>
													<input id="driverlicensenumberen" class="driverlicensenumberen" value="${driver.driverlicensenumberen }" name="driverlicensenumberen" type="text" >
													<c:if test="${driver.isknowdrivernumber == 1}">
														<input id="isknowdrivernumberen"  class="isknowdrivernumberen"  value="${driver.isknowdrivernumberen }" name="isknowdrivernumberen" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input id="isknowdrivernumberen"  class="isknowdrivernumberen"  value="${driver.isknowdrivernumberen }" name="isknowdrivernumberen" type="checkbox"/>
													</c:if>
												</div>
												<div class="groupSelectInfo driverR">
													<label>State of Driver's License</label>
													<select id="witchstateofdriveren" class="witchstateofdriveren" name="witchstateofdriveren">
														<option value="0" selected="selected">Please choose</option>
														<c:forEach items="${obj.stateUsList }" var="state">
															<c:if test="${driver.witchstateofdriveren != state.id}">
																<option value="${state.id }">${state.nameen }</option>
															</c:if>
															<c:if test="${driver.witchstateofdriveren == state.id}">
																<option value="${state.id }" selected="selected">${state.nameen }</option>
															</c:if>
														</c:forEach>
													</select>
												</div>
											</div>
										</c:forEach>
									</c:if>
									<c:if test="${empty obj.driverList }">
										<div class="goUS_driversen">
											<div class="groupcheckBoxInfo driverMain">
												<label>Driver's License Number</label>
												<input id="driverlicensenumberen" class="driverlicensenumberen" name="driverlicensenumberen" type="text" >
												<!-- checkbox 调用实现单点onchange="disableden(this)" -->
												<input id="isknowdrivernumberen"  class="isknowdrivernumberen"  name="isknowdrivernumberen" type="checkbox"/>
											</div>
											<div class="groupSelectInfo driverR">
												<label>State of Driver's License</label>
												<select id="witchstateofdriveren" class="witchstateofdriveren" name="witchstateofdriveren">
													<option value="0">Please choose</option>
													<c:forEach items="${obj.stateUsList }" var="state">
														<option value="${state.id }">${state.nameen }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</c:if>
								</div>
								<div class="btnGroup driverInfo driverInfoen companyGroupen driverGroup">
									<a class="save driversaveen">Add Another</a>
									<a class="cancel drivercancelen">Remove</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--是否有美国签证-->
			<div class="visaUSMain">
				<div>
					<div class="groupRadioInfo" style="clear: both; padding-top:35px;">
						<label>Have you ever been issued a U.S. Visa</label>
						<input type="radio" name="isissuedvisaen" v-model="visaInfo.previUSTripInfo.isissuedvisaen" class="visaUSen" value="1" />YES
						<input type="radio" name="isissuedvisaen" v-model="visaInfo.previUSTripInfo.isissuedvisaen" class="visaUSen" value="2" checked />NO
					</div>
					<div>
						<div class="dateIssue dateIssueen goUS_visa goUS_visaen">
							<div class="groupInputInfo lastVisaDateen">
								<label>Date Last Visa Was Issued  Click here for more information</label>
								<input id="issueddateen" name="issueddateen" value="${obj.previUSTripInfo_issueddateen}" class="datetimepickercss form-control" placeholder="Day / month / year" type="text"/>
							</div>
							<div class="groupcheckBoxInfo visaNum visaNumen">
								<label>Visa Number</label>
								<input id="visanumberen" name="visanumberen" v-model="visaInfo.previUSTripInfo.visanumberen" type="text" />
								<input id="idknowvisanumberen" class="idknowvisanumberen" name="idknowvisanumberen" v-on:click="idknowvisanumberChange" v-model="visaInfo.previUSTripInfo.idknowvisanumberen" type="checkbox"/>
							</div>
							<div class="clear"></div>
							<div class="Alike groupRadioInfo paddingTop">
								<label>Are you applying for the same type of visa</label>
								<input type="radio" name="isapplyingsametypevisaen" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisaen" value="1" />YES
								<input type="radio" name="isapplyingsametypevisaen" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisaen" value="2" checked />NO
							</div>
							<div class="describleCountry groupRadioInfo paddingTop">
								<label>Are you applying in the same country or location where the visa above was issued, and is this country or location your place of principal of residence</label>
								<input type="radio"  name="issamecountryen" v-model="visaInfo.previUSTripInfo.issamecountryen" value="1" />YES
								<input type="radio"  name="issamecountryen" v-model="visaInfo.previUSTripInfo.issamecountryen" value="2" checked />NO
							</div>
							<div class="paddingTop groupRadioInfo">
								<label>Have you been ten-printed</label>
								<input type="radio" name="istenprinteden" v-model="visaInfo.previUSTripInfo.istenprinteden"  value="1"/>YES
								<input type="radio" name="istenprinteden" v-model="visaInfo.previUSTripInfo.istenprinteden"  value="2" checked />NO
							</div>
							<div class="paddingTop">
								<div class="groupRadioInfo">
									<label style="display: block;">Has your U.S. Visa ever been lost or stolen</label>
									<input type="radio" name="islosten" v-model="visaInfo.previUSTripInfo.islosten" class="loseen" value="1" />YES
									<input type="radio" name="islosten" v-model="visaInfo.previUSTripInfo.islosten" v-on:click="visaNotLost" class="loseen" value="2" checked />NO
								</div>
								<div class="yearExplain yearExplainen displayTop elementHide"><!-- 默认隐藏 -->
									<div class="groupInputInfo">
										<label>Year</label>
										<input name="lostyearen" id="lostyearen" v-model="visaInfo.previUSTripInfo.lostyearen" type="text" />
									</div>
									<div class="paddingTop groupInputInfo">
										<label>Explain</label>
										<input name="lostexplainen" class="areaInputPic" id="lostexplainen" v-model="visaInfo.previUSTripInfo.lostexplainen" />
									</div>
								</div>
							</div>
							<div class="clear"></div>
							<div class="paddingTop">
								<div>
									<div class="groupRadioInfo">
										<label>Has your U.S. Visa ever been lost or stolen</label>
										<input type="radio" name="iscancelleden" v-model="visaInfo.previUSTripInfo.iscancelleden" class="revokeen" value="1" />YES
										<input type="radio" name="iscancelleden" v-model="visaInfo.previUSTripInfo.iscancelleden" v-on:click="visaNotCancel" class="revokeen" value="2" checked />NO
									</div>
									<div class="explain explainen grouptextareaInfo paddingTop">
										<label>Explain</label>
										<input name="cancelexplainen" id="cancelexplainen" class="areaInputPic" v-model="visaInfo.previUSTripInfo.cancelexplainen" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--被拒绝过美国签证，或被拒绝入境美国，或撤回入境口岸的入境-->
			<div class="paddingBottom">
				<div class="groupRadioInfo">
					<label>Have you ever been refused a U.S. Visa, or been refused admission to the United States, or withdrawn your application for admission at the port of entry</label>
					<input type="radio" name="isrefuseden" v-model="visaInfo.previUSTripInfo.isrefuseden" class="refuseen" value="1" />YES
					<input type="radio" name="isrefuseden" v-model="visaInfo.previUSTripInfo.isrefuseden" v-on:click="visaNotRefused" class="refuseen" value="2" checked />NO
				</div>
				<div class="refuseExplain refuseExplainen paddingTop grouptextareaInfo">
					<label>Explain</label>
					<input name="refusedexplainen" id="refusedexplainen" class="areaInputPic" v-model="visaInfo.previUSTripInfo.refusedexplainen" />
				</div>
			</div>
			<!--曾经是否是美国合法永久居民-->
			<div class="paddingBottom ">
				<div class="groupRadioInfo">
					<label>Are you or have you ever been a U.S. legal permanent resident</label>
					<input type="radio" name="islegalpermanentresidenten" v-model="visaInfo.previUSTripInfo.islegalpermanentresidenten" class="onceLegitimateen" value="1" />YES
					<input type="radio" name="islegalpermanentresidenten" v-model="visaInfo.previUSTripInfo.islegalpermanentresidenten" v-on:click="visaNotIegal" class="onceLegitimateen" value="2" checked />NO
				</div>
				<div class="onceExplain onceExplainen paddingTop grouptextareaInfo">
					<label>Explain</label>
					<input name="permanentresidentexplainen" id="permanentresidentexplainen" class="areaInputPic" v-model="visaInfo.previUSTripInfo.permanentresidentexplainen" />
				</div>
			</div>
			<!--有没有人曾代表您向美国公民和移民服务局提交过移民申请-->
			<div class="paddingBottom">
				<div class="groupRadioInfo">
					<label>Has anyone ever filed an immigrant petition on your behalf with the United States Citizenship and Immigration Services</label>
					<input type="radio" name="isfiledimmigrantpetitionen" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetitionen" class="onceImmigrationen" value="1" />YES
					<input type="radio" name="isfiledimmigrantpetitionen" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetitionen" v-on:click="visaNotfiledimmigrantpetition" class="onceImmigrationen" value="2" checked />NO
				</div>
				<div class="immigrationExplain immigrationExplainen paddingTop grouptextareaInfo">
					<label>Explain</label>
					<input name="immigrantpetitionexplainen" id="immigrantpetitionexplainen" class="areaInputPic" v-model="visaInfo.previUSTripInfo.immigrantpetitionexplainen" />
				</div>
			</div>
			<!--以前的美国旅游信息END-->
			<!--美国联络人-->
			<div class="contactPoint">
				<div class="titleInfo">Us contact point</div>
				<div class="groupInputInfo paddingLeft">
					<label>Surnames</label>
					<input name="firstnameen" id="firstnameusen" :value="visaInfo.contactPointInfo.firstname" v-model="visaInfo.contactPointInfo.firstnameen" type="text" />
				</div>
				<div class="groupcheckBoxInfo paddingRight">
					<label>Given Names  </label>
					<input name="lastnameen" id="lastnameusen" v-model="visaInfo.contactPointInfo.lastnameen" type="text"  />
					<input id="isknownameen" v-model="visaInfo.contactPointInfo.isknownameen" v-on:click="isKnowContactPointName" :value="visaInfo.contactPointInfo.isknowname" name="isknownameen" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop groupcheckBoxInfo cbox">
					<label>Organization Name </label>
					<input id="organizationnameusen" name="organizationnameen" v-model="visaInfo.contactPointInfo.organizationnameen" type="text" />
					<input id="isknoworganizationname" name="isknoworganizationnameen" class="isknoworganizationnameen"  v-on:click="isKnowOrganizationName" v-model="visaInfo.contactPointInfo.isknoworganizationnameen"  class="groupname_us" type="checkbox" />
				</div>
				<div class="paddingLeft groupSelectInfo">
					<label>Relationship to You</label>
					<select id="ralationshipen" v-model="visaInfo.contactPointInfo.ralationshipen" name="ralationshipen">
						<option value="0" selected="selected">Please choose</option>
						<c:forEach items="${obj.ContactPointRelationshipStatusEnumen }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="" style="padding: 10px 0;">
					<div class="groupInputInfo draBig">
						<label>U.S. Street Address(Line 1)</label>
						<input name="addressen" id="addressusen" v-model="visaInfo.contactPointInfo.addressen" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label>U.S. Street Address(Line 2) *Optional</label>
						<input name="secaddressen" id="secaddressusen" v-model="visaInfo.contactPointInfo.secaddressen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupSelectInfo " >
					<label>State</label>
						<select id="stateen" name="stateen" v-model="visaInfo.contactPointInfo.stateen">
							<option value="0">Please choose</option>
							<c:forEach items="${obj.stateUsList }" var="state">
								<option value="${state.id }">${state.nameen }</option>
							</c:forEach>
						</select>
					</div>
					<div class="paddingRight groupInputInfo" >
						<label>City</label>
						<input id="cityusen" name="cityen" v-model="visaInfo.contactPointInfo.cityen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupInputInfo" >
						<label>ZIP Code</label>
						<input name="zipcodeen" id="zipcodeusen" v-model="visaInfo.contactPointInfo.zipcodeen" type="text" />
					</div>
					<div class="paddingRight groupInputInfo" >
						<label>Phone Number</label>
						<input name="telephoneen" id="telephoneusen" v-model="visaInfo.contactPointInfo.telephoneen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingTop groupcheckBoxInfo cbox">
						<label>Email Address</label>
						<input name="emailen" id="emailusen" v-model="visaInfo.contactPointInfo.emailen" type="text" />
						<input id="isKnowEmailAddress" class="isKnowEmailAddressen"  name="isknowemailen" v-on:click="isKnowEmailAddress" v-model="visaInfo.contactPointInfo.isknowemailen" type="checkbox" />
					</div>
				</div>
			</div>
			<!--美国联络人END-->
			<!--家庭信息-->
			<!--亲属-->
			<div class="familyInfo">
				<div class="titleInfo">Family information</div>
				<div class="paddingLeft groupcheckBoxInfo" >
					<label>father's Surnames</label>
					<input name="fatherfirstnameen" id="fatherfirstnameen" v-model="visaInfo.familyInfo.fatherfirstnameen" type="text"/>
					<input id="isKnowFatherXing" name="isknowfatherfirstnameen" class="isknowfatherfirstnameen"   v-on:click="isknowfatherfirstname"  v-model="visaInfo.familyInfo.isknowfatherfirstnameen" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label>father's Given Names </label>
					<input name="fatherlastnameen" id="fatherlastnameen" v-model="visaInfo.familyInfo.fatherlastnameen" type="text" />
					<input id="isKnowFatherMing" name="isknowfatherlastnameen" class="isknowfatherlastnameen"  v-on:click="isknowfatherlastname" v-model="visaInfo.familyInfo.isknowfatherlastnameen" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Is your father in the U.S.</label>
						<input type="radio" name="isfatherinusen" v-model="visaInfo.familyInfo.isfatherinusen" class="fatherUSen" value="1" />YES
						<input type="radio" name="isfatherinusen"  v-on:click="isfatherinus" v-model="visaInfo.familyInfo.isfatherinusen" class="fatherUSen" value="2" checked />NO
					</div>
					<!--yes-->
					<div class="fatherUSYes fatherUSYesen groupSelectInfo paddingNone">
						<label>Father's Status</label>
						<select id="fatherstatusen" v-model="visaInfo.familyInfo.fatherstatusen" name="fatherstatusen">
							<option value="0">Please choose</option>
							<c:forEach items="${obj.VisaFamilyInfoEnumen }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>Mother's Surnames</label>
					<input id="motherfirstnameen" name="motherfirstnameen" v-model="visaInfo.familyInfo.motherfirstnameen" type="text" />
					<input id="isKnowMotherXing" name="isknowmotherfirstnameen" class="isknowmotherfirstnameen"  v-on:click="isknowmotherfirstname" v-model="visaInfo.familyInfo.isknowmotherfirstnameen" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label>Mother's Given Names</label>
					<input id="motherlastnameen" name="motherlastnameen" v-model="visaInfo.familyInfo.motherlastnameen" type="text" />
					<input id="isKnowMotherMing" name="isknowmotherlastnameen" class="isknowmotherlastnameen"  v-on:click="isknowmotherlastname" v-model="visaInfo.familyInfo.isknowmotherlastnameen" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Is your mother in the U.S.</label>
						<input type="radio" name="ismotherinusen" v-model="visaInfo.familyInfo.ismotherinusen" class="motherUSen" value="1" />YES
						<input type="radio" name="ismotherinusen" v-on:click="ismotherinus" v-model="visaInfo.familyInfo.ismotherinusen" class="motherUSen" value="2" checked />NO
					</div>
					<div class="motherUSYes motherUSYesen paddingNone groupSelectInfo">
						<label>Mother's Status</label>
						<select id="motherstatusen" name="motherstatusen" v-model="visaInfo.familyInfo.motherstatusen">
							<option value="0">Please choose</option>
							<c:forEach items="${obj.VisaFamilyInfoEnumen }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you have any immediate relatives, not including parents, in the United States</label>
						<input type="radio" name="hasimmediaterelativesen" v-model="visaInfo.familyInfo.hasimmediaterelativesen" class="directRelatives directUSRelativesen" value="1" />YES
						<input type="radio" name="hasimmediaterelativesen" v-model="visaInfo.familyInfo.hasimmediaterelativesen" class="directRelatives directUSRelativesen" value="2" checked/>NO
					</div>
					<div class="directRelatives">
						<!--yes-->
						<c:if test="${!empty obj.zhiFamilyList }">
							<c:forEach var="zhifamily" items="${obj.zhiFamilyList }">
								<div class="directRelativesYesen">
									<div class="floatLeft leftNo groupInputInfo">
										<label>Surnames</label>
										<input name="relativesfirstnameen" id="relativesfirtstnameen" value="${zhifamily.relativesfirstnameen }" type="text" />
									</div>
									<div class="floatRight groupInputInfo">
										<label>Given Names</label>
										<input name="relativeslastnameen" id="relativeslastnameen" value="${zhifamily.relativeslastnameen }"  type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>Relationship to You</label>
										<select name="relationshipen" id="exceptrelationshipen">
											<option value="0">Please choose</option>
											<c:forEach items="${obj.ImmediateRelationshipEnumen }" var="map">
												<c:if test="${zhifamily.relationshipen != map.key}">
													<option value="${map.key }">${map.value }</option>
												</c:if>
												<c:if test="${zhifamily.relationshipen == map.key}">
													<option value="${map.key }" selected="selected">${map.value }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="paddingRight groupSelectInfo">
										<label>Relative's Status</label>
										<select id="exceptrelativesstatusen" name="relativesstatusen" >
											<option value="0">Please choose</option>
											<c:forEach items="${obj.VisaFamilyInfoEnumen }" var="map">
												<c:if test="${zhifamily.relativesstatusen != map.key}">
													<option value="${map.key }">${map.value }</option>
												</c:if>
												<c:if test="${zhifamily.relativesstatusen == map.key}">
													<option value="${map.key }" selected="selected">${map.value }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty obj.zhiFamilyList}">
							<div class="directRelativesYesen">
								<div class="floatLeft leftNo groupInputInfo">
									<label>Surnames</label>
									<input name="relativesfirstnameen" id="relativesfirtstnameen"  type="text" />
								</div>
								<div class="floatRight groupInputInfo">
									<label>Given Names</label>
									<input name="relativeslastnameen" id="relativeslastnameen" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>Relationship to You</label>
									<select name="relationshipen"  id="exceptrelationshipen">
										<option value="0">Please choose</option>
										<c:forEach items="${obj.ImmediateRelationshipEnumen }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight groupSelectInfo">
									<label>Relative's Status</label>
									<select id="exceptrelativesstatusen" name="relativesstatusen">
										<option value="0">Please choose</option>
										<c:forEach items="${obj.VisaFamilyInfoEnumen }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:if>
						<div class="clear"></div>
						</div>
						<!--NO-->
						<div class="directRelativesNo directRelativesNoen groupRadioInfo">
							<label>Do you have any other relatives in the United States</label>
							<input type="radio" name="hasotherrelativesen" v-model="visaInfo.familyInfo.hasotherrelativesen" value="1"/>YES
							<input type="radio" name="hasotherrelativesen" v-model="visaInfo.familyInfo.hasotherrelativesen" value="2" checked/>NO
						</div>
					</div>
					
				</div>
			<!--配偶-->
			<div class="paddingTop">
				<div class="titleInfo">Spousal information</div>
				<div class="floatLeft groupInputInfo">
					<label>Spouse's Surnames </label>
					<input name="spousefirstnameen" id="spousefirstnameen" v-model="visaInfo.familyInfo.spousefirstnameen" type="text" />
				</div>
				<div class="floatRight groupInputInfo">
					<label>Spouse's Given Names</label>
					<input name="spouselastnameen" id="spouselastnameen" v-model="visaInfo.familyInfo.spouselastnameen" type="text" />
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupInputInfo">
					<label>Spouse's Date of Birth</label>
					<input id="spousebirthdayen" name="spousebirthdayen" value="${obj.spousebirthdayen}" class="datetimepickercss form-control" type="text" placeholder="Day / month / year" />
				</div>
				<div class="groupSelectInfo selectInfoen">
					<label>Spouse's Country/Region of Origin (Nationality)</label>
					<select id="spousenationalityen" class="spousenationalityen" name="spousenationalityen" v-model="visaInfo.familyInfo.spousenationalityen">
						<option value="0">Please choose</option>
						<c:forEach items="${obj.gocountryFiveList }" var="country">
							<option value="${country.id }" >${country.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>City</label>
					<input name="spousecityen" id="spousefcityen" v-model="visaInfo.familyInfo.spousecityen" type="text" />
					<input id="isKnowMatecityen" name="isknowspousecityen" class="isknowspousecityen" v-model="visaInfo.familyInfo.isknowspousecityen" type="checkbox" />
				</div>
				<div class="paddingRight groupSelectInfo" >
					<label>Country/Region</label>
					<select id="spousecountryen" class="spousecountryen" name="spousecountryen" v-model="visaInfo.familyInfo.spousecountryen">
						<option value="0">Please choose</option>
						<c:forEach items="${obj.gocountryFiveList }" var="country">
							<option value="${country.id }" >${country.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label>Spouse's Address</label>
					<select id="spouseaddressen" name="spouseaddressen" v-model="visaInfo.familyInfo.spouseaddressen" class="spouse_Address" >
						<option value="0">Please choose</option>
						<c:forEach items="${obj.VisaSpouseContactAddressEnumen }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				
				<!--配偶的联系地址select选择其他-->
				<div class="otherSpouseInfo elementHide paddingTop" >
					<div class="floatLeft groupInputInfo">
						<label>U.S. Street Address(Line 1)</label>
						<input name="firstaddressen" id="otherfrstaddressen"  v-model="visaInfo.familyInfo.firstaddressen" type="text" />
					</div>
					<div class="floatRight groupInputInfo">
						<label>U.S. Street Address(Line 2) *Optional</label>
						<input name="secondaddressen" id="othersecondaddressen" v-model="visaInfo.familyInfo.secondaddressen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>State</label>
						<input name="provinceen" class="otherprovinceen" id="otherprovinceen" v-model="visaInfo.familyInfo.provinceen" type="text" />
						<input id="otherapplyen" class="otherapplyen" name="otherapplyen"  v-model="visaInfo.familyInfo.otherapplyen" type="checkbox" />
					</div>
					<div class="paddingRight groupInputInfo">
						<label>City</label>
						<input name="othercityen" id="othercityen" v-model="visaInfo.familyInfo.cityen" type="text"/>
					</div>
					
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>ZIP Code</label>
						<input name="zipcodeen" v-model="visaInfo.familyInfo.zipcodeen" id="otherzipcodeen" type="text" />
						<input type="checkbox" id="selectcodeapplyen" class='selectcodeapplyen' name="selectcodeapplyen" v-model="visaInfo.familyInfo.selectcodeapplyen"  />
					</div>
					<div class="paddingRight groupSelectInfo">
						<label>Country/Region</label>
						<select name="othercountryen" id="othercountryen" v-model="visaInfo.familyInfo.countryen">
							<option value="0">Please choose</option>
							<c:forEach items="${obj.gocountryFiveList }" var="country">
								<option value="${country.id }">${country.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!--家庭信息END-->
			<!--工作/教育/培训信息-->
			<div class="experience paddingTop">
				<div class="titleInfo">Work / education / training information</div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label>Primary Occupation</label>
					<select id="occupationen" name="occupationen" v-model="visaInfo.workEducationInfo.occupationen" @change="occupationChange()">
						<option value="0">Please choose</option>
						<c:forEach items="${obj.VisaCareersEnumen }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="paddingTop elementHide jobEduLearningInfoDiv">
					<div class="groupInputInfo draBig">
						<label>Present Employer or School Name</label>
						<input name="unitnameen" id="unitnameen" v-model="visaInfo.workEducationInfo.unitnameen" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label >U.S. Street Address(Line 1)</label>
						<input name="addressen" id="jobaddressen" v-model="visaInfo.workEducationInfo.addressen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="groupInputInfo draBig marginLS">
						<label>U.S. Street Address(Line 2) *Optional</label>
						<input name="secaddressen" id="jobsecondaddressen" v-model="visaInfo.workEducationInfo.secaddressen" type="text" />
					</div>
					
					<div class="paddingLeft groupcheckBoxInfo">
						<label>State</label>
						<input name="provinceen" class="provinceen" id="provinceen" v-model="visaInfo.workEducationInfo.provinceen" type="text"/>
						<input name="isprovinceapplyworken" id="isprovinceapplyworken" class="isprovinceapplyworken"  v-model="visaInfo.workEducationInfo.isprovinceapplyen" type="checkbox"/>
					</div>
					<div class="paddingRight groupInputInfo">
						<label>City</label>
						<input name="jobcityen" id="jobcityen" v-model="visaInfo.workEducationInfo.cityen" type="text"/>
					</div>
					
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>ZIP Code</label>
						<input name="zipcodeen" id="jobzipcodeen" v-model="visaInfo.workEducationInfo.zipcodeen" type="text" />
						<input name="jobcodecheckeden" id="jobcodecheckeden" class="jobcodecheckeden"  v-model="visaInfo.workEducationInfo.iszipcodeapplyen" type="checkbox" />
					</div>
					<div class="paddingRight groupInputInfo">
						<label>Phone Number</label>
						<input name="jobtelephoneen" id="jobtelphoneen" v-model="visaInfo.workEducationInfo.telephoneen" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupSelectInfo" >
						<label>Country/Region</label>
						<select name="jobcountryen" id="jobcountryen" v-model="visaInfo.workEducationInfo.countryen">
							<option value="0">Please choose</option>
							<c:forEach items="${obj.gocountryFiveList }" var="country">
								<option value="${country.id }">${country.name }</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="paddingRight groupInputInfo">
						<label>Start Date</label>
						<input id="workstartdateen" name="workstartdateen" value="${obj.workstartdateen}" class="datetimepickercss form-control" type="text" placeholder="Day / month / year" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo" >
						<label>Monthly Income in Local Currency (if employed)</label>
						<input name="salaryen" id="salaryen" v-model="visaInfo.workEducationInfo.salaryen" type="text" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"/>
						<input name="moneycheckeden" id="moneycheckeden" class="moneycheckeden"  v-model="visaInfo.workEducationInfo.issalaryapplyen" type="checkbox" />
					</div>
					<div class="clear"></div>
					<div class="grouptextareaInfo groupPM">
						<label>Briefly describe your duties</label>
						<input name="jobdutyen" id="jobdutyen" class="bigArea" v-model="visaInfo.workEducationInfo.dutyen" />
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="grouptextareaInfo elementHide jobEduLearningInfoTextarea">
					<!-- <label>说明</label>
					<input /> -->
				</div>
			</div>
			<!--以前-->
			<div>
				<div class="paddingTop padding-left">
					<div>
						<div class="groupRadioInfo">
							<label>Were you previously employed</label>
							<input type="radio" name="isemployeden" v-model="visaInfo.workEducationInfo.isemployeden" class="beforeWorken" value="1" />YES
							<input type="radio" name="isemployeden" v-model="visaInfo.workEducationInfo.isemployeden" class="beforeWorken" value="2" checked/>NO
						</div>
						<!--yes-->
						<div class="beforeWorkInfo beforeWorkInfoen elementHide">
						  <div class="beforeWorkYes">
							<c:if test="${!empty obj.beforeWorkList }">
								<c:forEach var="beforeWork" items="${obj.beforeWorkList }">
									<div class="workBeforeInfosDiven">
										<div class="leftNo marginLS groupInputInfo" >
											<label>Employer Name</label>
											<input name="employernameen" id="employernameen" class="employernameen" value="${beforeWork.employernameen }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>Employer Street Address (Line 1)</label>
											<input name="employeraddressen" class="employeraddressen" value="${beforeWork.employeraddressen }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>Employer Street Address (Line 2) *Option</label>
											<input name="employeraddressSecen" class="employeraddressSecen" value="${beforeWork.employeraddressSecen }" type="text" />
										</div>
										
										<div class="paddingLeft leftNo groupInputInfo">
											<label>State/Province</label>
											<input name="employerprovinceen" class="employerprovinceen" value="${beforeWork.employerprovinceen }" type="text" />
										</div>
										<div class="paddingRight leftNo groupcheckBoxInfo" >
											<label>City</label>
											<input name="employercityen" class="employercityen"  value="${beforeWork.employercityen }" type="text" />
											<!-- <input type="checkbox" /> -->
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>Postal Zone/ZIP Code</label>
											<input name="employerzipcodeen" value="${beforeWork.employerzipcodeen }" classs="employerzipcodeen" type="text" />
											<c:if test="${beforeWork.isemployerzipcodeapplyen == 1}">
												<input id="isKonwOrtherZipCodeen" class="isemployerzipcodeapplyen"  name="isemployerzipcodeapplyen" value="${beforeWork.isemployerzipcodeapplyen }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isemployerzipcodeapplyen != 1}">
												<input id="isKonwOrtherZipCodeen" class="isemployerzipcodeapplyen"  name="isemployerzipcodeapplyen" value="${beforeWork.isemployerzipcodeapplyen }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight groupSelectInfo">
											<label>Country/Region</label>
											<select name="employercountryen" class="employercountryen">
												<option value="0">Please choose</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${beforeWork.employercountryen != country.id}">
														<option value="${country.id }">${country.name }</option>
													</c:if>
													<c:if test="${beforeWork.employercountryen == country.id}">
														<option value="${country.id }" selected="selected">${country.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>Telephone Number</label>
											<input name="employertelephoneen" class="employertelephoneen" value="${beforeWork.employertelephoneen }" type="text" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>Job Title</label>
											<input name="jobtitleen" class="jobtitleen" value="${beforeWork.jobtitleen }" type="text"/>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>Supervisor's Surname</label>
											<input name="supervisorfirstnameen" class="isknowsupervisorfirstnamebeforeen" value="${beforeWork.supervisorfirstnameen }" type="text" />
											<c:if test="${beforeWork.isknowsupervisorfirstnameen == 1}">
												<input name="isknowsupervisorfirstnameen" id="isknowsupervisorfirstnamebefore" class="isknowjobfirstnameen"  value="${beforeWork.isknowsupervisorfirstnameen }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorfirstnameen != 1}">
												<input name="isknowsupervisorfirstnameen" id="isknowsupervisorfirstnamebefore" class="isknowjobfirstnameen"  value="${beforeWork.isknowsupervisorfirstnameen }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight groupcheckBoxInfo">
											<label>Supervisor's Given Name</label>
											<input name="supervisorlastnameen" class="isknowsupervisorlastnamebeforeen" value="${beforeWork.supervisorlastnameen }" type="text" />
											<c:if test="${beforeWork.isknowsupervisorlastnameen == 1}">
												<input name="isknowsupervisorlastnameen" id="isknowsupervisorlastnamebefore" class="isknowjoblastnameen"  value="${beforeWork.isknowsupervisorlastnameen }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorlastnameen != 1}">
												<input name="isknowsupervisorlastnameen" id="isknowsupervisorlastnamebefore" class="isknowjoblastnameen"  value="${beforeWork.isknowsupervisorlastnameen }" type="checkbox" />
											</c:if>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo" >
											<label>Employment Date From</label>
											<input id="employstartdateen" name="employstartdateen" value="<fmt:formatDate value="${beforeWork.employstartdateen }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control employstartdateen" type="text" placeholder="Day / month / year" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>Employment Date To</label>
											<input id="employenddateen" name="employenddateen" value="<fmt:formatDate value="${beforeWork.employenddateen }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control employenddateen" type="text" placeholder="Day / month / year" />
										</div>
										<div class="clear"></div>
										<div class="draBig leftNo marginLS grouptextareaInfo">
											<label>Briefly describe your duties</label>
											<input type="text" id="previousdutyen" name="previousdutyen" class="bigArea previousduty previousdutyen" value="${beforeWork.previousdutyen }" />
											<%-- <textarea name="previousduty" class="bigArea previousduty" value="${beforeWork.previousduty }"></textarea> --%>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeWorkList }">
								<div class="workBeforeInfosDiven">
									<div class="leftNo marginLS groupInputInfo" >
										<label>Employer Name  </label>
										<input name="employernameen" class="employernameen" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>Employer Street Address (Line 1)</label>
										<input name="employeraddressen" class="employeraddressen" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>Employer Street Address (Line 2) *Option</label>
										<input name="employeraddressSecen" class="employeraddressSecen" type="text" />
									</div>
									
									<div class="paddingLeft leftNo groupInputInfo">
										<label>State/Province</label>
										<input name="employerprovinceen" class="employerprovinceen" type="text" />
									</div>
									<div class="paddingRight leftNo groupcheckBoxInfo" >
										<label>City</label>
										<input name="employercityen" class="employercityen" type="text" />
										<input type="checkbox" name="employercitybeforeen" class="employercitybeforeen"  id="employercitybefore" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>ostal Zone/ZIP Code</label>
										<input name="employerzipcodeen" class="employerzipcodeen" type="text" />
										<input name="isemployerzipcodeapplyen" id="isKonwOrtherZipCodeen" class="isemployerzipcodeapplyen"  type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo">
										<label>Country/Region</label>
										<select name="employercountryen" class="employercountryen">
											<option value="0">Please choose</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<option value="${country.id }">${country.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>Telephone Number</label>
										<input name="employertelephoneen" class="employertelephoneen" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Job Title</label>
										<input name="jobtitleen" class="jobtitleen" type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>Supervisor's Surname</label>
										<input name="supervisorfirstnameen" class="isknowsupervisorfirstnamebeforeen" type="text" />
										<input name="isknowsupervisorfirstnameen" class="isknowjobfirstnameen"  id="isknowsupervisorfirstnamebefore" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupcheckBoxInfo">
										<label>Supervisor's Given Name</label>
										<input name="supervisorlastnameen" class="isknowsupervisorlastnamebeforeen" type="text" />
										<input name="isknowsupervisorlastnameen" class="isknowjoblastnameen"  id="isknowsupervisorlastnamebefore" type="checkbox" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>Employment Date From</label>
										<input id="employstartdateen" name="employstartdateen" class="datetimepickercss form-control employstartdateen" type="text" placeholder="Day / month / year" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Employment Date To</label>
										<input id="employenddateen" name="employenddateen" class="datetimepickercss form-control employenddateen" type="text" placeholder="Day / month / year" />
									</div>
									<div class="clear"></div>
									<div class="draBig leftNo marginLS grouptextareaInfo">
										<label>Briefly describe your duties</label>
										<input type="text" id="previousdutyen" name="previousdutyen" class="bigArea previousduty previousdutyen" />
										<!-- <textarea class="bigArea" name="previousduty"></textarea> -->
									</div>
								</div>
							</c:if>
							</div>
							<div class="clear"></div>
							<div class="btnGroup companyGroupen marginLS beforeWorkGroup">
								<a class="save beforeWorksaveen">Add Another</a>
								<a class="cancel beforeWorkcancelen">Remove</a>
							</div>
						</div>
					</div>
				</div>
				<div class="padding-left">
					<div class="paddingTop">
						<div class="groupRadioInfo">
							<label>Have you attended any educational institutions at a secondary level or above</label>
							<input type="radio" name="issecondarylevelen" v-model="visaInfo.workEducationInfo.issecondarylevelen" class="educationen" value="1" />YES
							<input type="radio" name="issecondarylevelen" v-model="visaInfo.workEducationInfo.issecondarylevelen" class="educationen" value="2" checked/>NO
						</div>
						<!--yes-->
						<div class="educationInfo educationInfoen elementHide">
							<div class="educationYes">
							<c:if test="${!empty obj.beforeEducationList }">
								<c:forEach var="education" items="${obj.beforeEducationList }">
									<div class="midSchoolEduDiven">
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>Name of Institution</label>
											<input name="institutionen" class="institutionen" value="${education.institutionen }" type="text"/>
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>Street Address (Line 1)</label>
											<input name="institutionaddressen" class="institutionaddressen" value="${education.institutionaddressen }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>Street Address (Line 2) *Optional</label>
											<input name="secinstitutionaddressen" class="secinstitutionaddressen" type="text" value="${education.secinstitutionaddressen }" />
										</div>
										<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>State/Province</label>
											<input name="institutionprovinceen" class="institutionprovinceen" value="${education.institutionprovinceen }" type="text" />
											<c:if test="${education.isinstitutionprovinceapplyen == 1}">
												<input name="isinstitutionprovinceapplyen" class="isschoolprovinceen"  value="${education.isinstitutionprovinceapplyen }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionprovinceapplyen != 1}">
												<input name="isinstitutionprovinceapplyen" class="isschoolprovinceen"  value="${education.isinstitutionprovinceapplyen }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >City</label>
											<input name="institutioncityen" class="institutioncityen" value="${education.institutioncityen }" type="text" />
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>Postal Zone/ZIP Code</label>
											<input name="institutionzipcodeen" class="institutionzipcodeen" value="${education.institutionzipcodeen }" type="text" />
											<c:if test="${education.isinstitutionzipcodeapplyen == 1}">
													<input name="isinstitutionzipcodeapplyen" id="codeEdu" class="codeEduen"  value="${education.isinstitutionzipcodeapplyen }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionzipcodeapplyen != 1}">
												<input name="isinstitutionzipcodeapplyen" id="codeEdu" class="codeEduen"  value="${education.isinstitutionzipcodeapplyen }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight leftNo groupSelectInfo" >
											<label>Country/Region</label>
											<select name="institutioncountryen" class="institutioncountryen" id="institutioncountryen">
												<option value="0">Please choose</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${education.institutioncountryen != country.id}">
														<option value="${country.id }">${country.name }</option>
													</c:if>
													<c:if test="${education.institutioncountryen == country.id}">
														<option value="${country.id }" selected="selected">${country.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>Course of Study</label>
											<input name="courseen" class="courseen" value="${education.courseen }" type="text" />
										</div>
										
										<div class="paddingRight leftNo groupInputInfo">
											<label>Date of Attendance From</label>
											<input id="coursestartdateen" name="coursestartdateen" value="<fmt:formatDate value="${education.coursestartdateen }" pattern="dd/MM/yyyy" />"  class="datetimepickercss form-control coursestartdateen" type="text" placeholder="Day / month / year" />
										</div>
										<div class="clear"></div>
										<div class="leftNo groupInputInfo">
											<label>Date of Attendance To</label>
											<input id="courseenddateen" name="courseenddateen" value="<fmt:formatDate value="${education.courseenddateen }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control courseenddateen" type="text" placeholder="Day / month / year" />
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeEducationList }">
								<div class="midSchoolEduDiven">
									<div class="draBig leftNo groupInputInfo">
										<label>Name of Institution</label>
										<input name="institutionen" class="institutionen" type="text"/>
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>Street Address (Line 1)</label>
										<input name="institutionaddressen" class="institutionaddressen" type="text" />
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>Street Address (Line 2) *Optional</label>
										<input name="secinstitutionaddressen" class="secinstitutionaddressen" type="text" />
									</div>
									<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>State/Province</label>
											<input name="institutionprovinceen" class="institutionprovinceen" type="text" />
											<input name="isinstitutionprovinceapplyen" class="isschoolprovinceen"  type="checkbox" />
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >City</label>
											<input name="institutioncityen" class="institutioncityen" type="text" />
										</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>Postal Zone/ZIP Code</label>
										<input name="institutionzipcodeen" class="institutionzipcodeen" type="text" />
										<input name="isinstitutionzipcodeapplyen" class="codeEduen"  type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo" >
										<label>Country/Region</label>
										<select name="institutioncountryen" class="institutioncountryen" id="institutioncountryen">
											<option value="0">Please choose</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<option value="${country.id }">${country.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>Course of Study</label>
										<input name="courseen" class="courseen" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Date of Attendance From</label>
										<input id="coursestartdateen" name="coursestartdateen" class="datetimepickercss form-control coursestartdateen" type="text" placeholder="Day / month / year" />
									</div>
									<div class="clear"></div>
									<div class="leftNo groupInputInfo">
										<label>Date of Attendance To</label>
										<input id="courseenddateen" name="courseenddateen" class="datetimepickercss form-control courseenddateen" type="text" placeholder="Day / month / year" />
									</div>
								</div>
							</c:if>
							</div>
							<div class="clear"></div>
							<div class="btnGroup companyGroupen educationGroup">
								<a class="save educationsaveen" >Add Another</a>
								<a class="cancel educationcancelen" >Remove</a>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<!--额外-->
			<div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you belong to a clan or tribe</label>
						<input type="radio" name="isclanen" v-model="visaInfo.workEducationInfo.isclanen" class="isclanen" value="1"/>YES
						<input type="radio" name="isclanen" v-on:click="isclan()" v-model="visaInfo.workEducationInfo.isclanen" class="isclanen" value="2" checked/>NO
					</div>
					
					<!--yes-->
					<div class="isclanYes isclanYesen elementHide">
						<div>
							<div class="clannameDiv clannameDiven">
								<div class="draBig leftNo groupInputInfo" >
									<label>Clan or Tribe Name</label>
									<input name="clannameen" id="clannameen" v-model="visaInfo.workEducationInfo.clannameen"  type="text"  />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="languageYes">
					<c:if test="${!empty obj.languageList }">
						<c:forEach var="language" items="${obj.languageList }">
							<div class="languagename languagenameen languagenameDiven paddingTop padding-left">
								<label>Language Name</label>
								<div class="groupInputInfo">
									<input name="languagenameen" class="languagenamewen" value="${language.languagenameen }" type="text" />
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty obj.languageList }">
						<div class="languagename languagenameen languagenameDiven paddingTop padding-left">
							<label>Language Name</label>
							<div class="groupInputInfo">
								<input name="languagenameen" class="languagenamewen" type="text" />
							</div>
						</div>
					</c:if>
				</div>	
				<div class="btnGroup companyGroupen draBig languageGroup">
					<a class="save languagesaveen">Add Another</a>
					<a class="cancel languagecancelen">Remove</a>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you traveled to any countries/regions within the last five years</label>
						<input type="radio" name="istraveledanycountryen" v-model="visaInfo.workEducationInfo.istraveledanycountryen" class="istraveledanycountryen" value="1" />YES
						<input type="radio" name="istraveledanycountryen" v-model="visaInfo.workEducationInfo.istraveledanycountryen" class="istraveledanycountryen" value="2" checked/>NO
					</div>
					<!--yes-->
					<div class="isTravelYes isTravelYesen elementHide">
						<div class="gocountryYes">
							<c:if test="${!empty obj.gocountryList }">
								<c:forEach var="gocountry" items="${obj.gocountryList }">
									<div class="travelCountryen paddingTop groupInputInfo">
										<label>Country/Region</label>
										<div class="groupInputInfo groupSelectInfo">
										
											<select name="traveledcountryen" class="traveledcountryen">
												<option value="0">Please choose</option>
												<c:forEach items="${obj.gocountryFiveList }" var="country">
													<c:if test="${gocountry.traveledcountryen != country.id}">
														<option value="${country.id }">${country.name }</option>
													</c:if>
													<c:if test="${gocountry.traveledcountryen == country.id}">
														<option value="${country.id }" selected="selected">${country.name }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.gocountryList }">
								<div class="paddingTop travelCountryen groupInputInfo">
									<label>Country/Region</label>
									<!-- <input name="traveledcountryen" class="traveledcountryen" type="text"/> -->
									<select name="traveledcountryen" class="traveledcountryen">
										<option value="0">Please choose</option>
									<c:forEach items="${obj.gocountryFiveList }" var="country">
											<option value="${country.id }">${country.name }</option>
									</c:forEach>
									</select>
								</div>
							</c:if>
						</div>
						<div class="btnGroup companyGroupen gocountryGroup">
							<a class="save gocountrysaveen">Add Another</a>
							<a class="cancel gocountrycancelen">Remove</a>
						</div>
					</div>
				</div>
				<div class="padding-left paddingTop">
					<div class="groupRadioInfo">
						<label>Have you belonged to, contributed to, or worked for any professional, social, or charitable organization</label>
						<input type="radio" name="isworkedcharitableorganizationen" v-model="visaInfo.workEducationInfo.isworkedcharitableorganizationen" class="isworkedcharitableorganizationen" value="1"/>YES
						<input type="radio" name="isworkedcharitableorganizationen" v-model="visaInfo.workEducationInfo.isworkedcharitableorganizationen" class="isworkedcharitableorganizationen" value="2" checked/>NO
					</div>
					<!--yes-->
					<div class="isOrganizationYes isOrganizationYesen elementHide">
						<div class="organizationYes">
							<c:if test="${!empty obj.organizationList }">
								<c:forEach var="organization" items="${obj.organizationList }">
								<div class="organizationDiven">
									<div class="paddingTop draBig leftNo organizationDiven groupInputInfo">
										<label>Organization Name</label>
										<input name="organizationnameen" class="organizationnameen" value="${organization.organizationnameen }" type="text"/>
									</div>
								</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.organizationList }">
							<div class="organizationDiven">
								<div class="paddingTop draBig leftNo organizationDiven groupInputInfo">
									<label>Organization Name</label>
									<input name="organizationnameen" class="organizationnameen" type="text"/>
								</div>
							</div>	
							</c:if>
						</div>
						<div class="btnGroup companyGroupen organizationGroup">
							<a class="save organizationsaveen">Add Another</a>
							<a class="cancel organizationcancelen">Remove</a>
						</div>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you have any specialized skills or training, such as firearms, explosives, nuclear, biological, or chemical experience</label>
						<input type="radio" name="hasspecializedskillen" v-model="visaInfo.workEducationInfo.hasspecializedskillen" class="hasspecializedskillen" value="1" />YES
						<input type="radio"name="hasspecializedskillen" v-on:click="hasspecializedskill()" v-model="visaInfo.workEducationInfo.hasspecializedskillen" class="hasspecializedskillen" value="2" checked />NO
					</div>
					<!--yes-->
					<div class="paddingTop skillDiv skillDiven elementHide grouptextareaInfo">
						<label>Explain</label>
						<input type="text" name="skillexplainen" id="skillexplainen" class="bigArea" v-model="visaInfo.workEducationInfo.skillexplainen" />
					</div>
				</div>
				<div class="padding-left paddingTop">
					<div class="groupRadioInfo">
						<label style="display: block;">Have you ever served in the military</label>
						<input type="radio" name="hasservedinmilitaryen" v-model="visaInfo.workEducationInfo.hasservedinmilitaryen" class="hasservedinmilitaryen" value="1"/>YES
						<input type="radio"name="hasservedinmilitaryen" v-model="visaInfo.workEducationInfo.hasservedinmilitaryen" class="hasservedinmilitaryen" value="2" checked/>NO
					</div>
					<!--yes-->
					<div class="paddingTop elementHide militaryServiceYes militaryServiceYesen">
					  <div class="militaryYes">
						<c:if test="${!empty obj.conscientiousList }">
							<c:forEach var="conscientious" items="${obj.conscientiousList }">
								<div class="militaryInfoDiven">
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>Name of Country/Region </label>
										<select name="militarycountryen" id="militarycountryen" class="militarycountryen">
											<option value="0">Please choose</option>
											<c:forEach items="${obj.gocountryFiveList }" var="country">
												<c:if test="${conscientious.militarycountryen != country.id}">
													<option value="${country.id }">${country.name }</option>
												</c:if>
												<c:if test="${conscientious.militarycountryen == country.id}">
													<option value="${country.id }" selected="selected">${country.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Branch of Service</label>
										<input name="servicebranchen" class="servicebranchen" value="${conscientious.servicebranchen }" type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>Rank/Position</label>
										<input name="ranken" class="ranken" value="${conscientious.ranken }" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Military Specialty</label>
										<input name="militaryspecialtyen" class="militaryspecialtyen" value="${conscientious.militaryspecialtyen }" type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>Date of Service From</label>
										<input id="servicestartdateen" name="servicestartdateen" value="<fmt:formatDate value="${conscientious.servicestartdateen }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss form-control servicestartdateen" placeholder="日/月/年"/>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>Date of Service To</label>
										<input id="serviceenddateen" name="serviceenddateen" value="<fmt:formatDate value="${conscientious.serviceenddateen }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss form-control serviceenddateen" placeholder="日/月/年"/>
									</div>
								</div>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty obj.conscientiousList }">
							<div class="militaryInfoDiven">
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>Name of Country/Region</label>
									<select name="militarycountryen" id="militarycountryen" class="militarycountryen">
										<option value="0">Please choose</option>
										<c:forEach items="${obj.gocountryFiveList }" var="country">
											<option value="${country.id }">${country.name }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>Branch of Service</label>
									<input name="servicebranchen" class="servicebranchen" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo" >
									<label>Rank/Position</label>
									<input name="ranken" class="ranken" type="text" />
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>Military Specialty</label>
									<input name="militaryspecialtyen" class="militaryspecialtyen" type="text"/>
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo">
									<label>Date of Service From</label>
									<input id="servicestartdateen" name="servicestartdateen" type="text" class="datetimepickercss form-control servicestartdateen" placeholder="Day / month / year"/>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>Date of Service To</label>
									<input id="serviceenddateen" name="serviceenddateen" type="text" class="datetimepickercss form-control serviceenddateen" placeholder="Day / month / year"/>
								</div>
							</div>
						</c:if>	
						</div>
						<div class="clear"></div>
						<div class="btnGroup companyGroupen militaryGroup">
							<a class="save militarysaveen">Add Another</a>
							<a class="cancel militarycancelen">Remove</a>
						</div>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever served in, been a member of, or been involved with a paramilitary unit, vigilante unit, rebel group, guerrilla group, or insurgent organization</label>
						<input type="radio" name="isservedinrebelgroupen" v-model="visaInfo.workEducationInfo.isservedinrebelgroupen" class="isservedinrebelgroupen" value="1"/>YES
						<input type="radio" name="isservedinrebelgroupen" v-model="visaInfo.workEducationInfo.isservedinrebelgroupen" class="isservedinrebelgroupen" value="2" checked/>NO
					</div>
					<!--yes-->
					<!-- <div class="paddingTop elementHide dinrebelDiv dinrebelDiven grouptextareaInfo">
						<label>说明</label>
						<input />
					</div> -->
				</div>
			</div>	
			<!--工作/教育/培训信息END-->
			<!--安全和背景-->
			<div class="safe paddingTop">
				<div class="titleInfo">Safety and background</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you have a communicable disease of public health significance? (Communicable diseases of public significance include chancroid, gonorrhea, granuloma inguinale, infectious leprosy, lymphogranuloma venereum, infectious stage syphilis, active tuberculosis, and other diseases as determined by the Department of Health and Human Services.)</label>
						<input type="radio" name="isPestilenceen" class="isPestilence" value="1"/>YES
						<input type="radio" name="isPestilenceen" class="isPestilence" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isPestilenceDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you have a mental or physical disorder that poses or is likely to pose a threat to the safety or welfare of yourself or others</label>
						<input type="radio" name="isThreatIllnessen" class="isThreatIllness" value="1"/>YES
						<input type="radio" name="isThreatIllnessen" class="isThreatIllness" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isThreatIllnessDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Are you or have you ever been a drug abuser or addict</label>
						<input type="radio" name="isDrugen" class="isDrug" value="1"/>YES
						<input type="radio" name="isDrugen" class="isDrug" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isDrugDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever been arrested or convicted for any offense or crime, even though subject of a pardon, amnesty, or other similar action</label>
						<input type="radio" name="isSentenceden" class="isSentenced" value="1"/>YES
						<input type="radio" name="isSentenceden" class="isSentenced" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isSentencedDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever violated, or engaged in a conspiracy to violate, any law relating to controlled substances</label>
						<input type="radio" name="isMaterialLawen" class="isMaterialLaw" value="1"/>YES
						<input type="radio" name="isMaterialLawen" class="isMaterialLaw" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isMaterialLawDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">Are you coming to the United States to engage in prostitution or unlawful commercialized vice or have you been engaged in prostitution or procuring prostitutes within the past 10 years</label>
						<input type="radio" name="isProstitutionen" class="isProstitution" value="1"/>YES
						<input type="radio" name="isProstitutionen" class="isProstitution" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isProstitutionDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever been involved in, or do you seek to engage in, money laundering</label>
						<input type="radio" name="isLaunderingen" class="isLaundering" value="1"/>YES
						<input type="radio" name="isLaunderingen" class="isLaundering" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isLaunderingDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">Have you ever committed or conspired to commit a human trafficking offense in the United States or outside the United States</label>
						<input type="radio" name="isSmugglingen" class="isSmuggling" value="1"/>YES
						<input type="radio" name="isSmugglingen" class="isSmuggling" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isSmugglingDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever knowingly aided, abetted, assisted or colluded with an individual who has committed, or conspired to commit a severe human trafficking offense in the United States or outside the United States</label>
						<input type="radio" name="isThreateningOthersen" class="isThreateningOthers" value="1"/>YES
						<input type="radio" name="isThreateningOthersen" class="isThreateningOthers" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isThreateningOthersDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever committed or conspired to commit a human trafficking offense in the United States or outside the United States</label>
						<input type="radio" name="isSmugglingBenefitsen" class="isSmugglingBenefits" value="1"/>YES
						<input type="radio" name="isSmugglingBenefitsen" class="isSmugglingBenefits" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isSmugglingBenefitsDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you seek to engage in espionage, sabotage, export control violations, or any other illegal activity while in the United States</label>
						<input type="radio" name="isSpyActivitiesen" class="isSpyActivities" value="1"/>YES
						<input type="radio" name="isSpyActivitiesen" class="isSpyActivities" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isSpyActivitiesDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Do you seek to engage in terrorist activities while in the United States or have you ever engaged in terrorist activities</label>
						<input type="radio" name="isTerroristActivitiesen" class="isTerroristActivities" value="1"/>YES
						<input type="radio" name="isTerroristActivitiesen" class="isTerroristActivities" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isTerroristActivitiesDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever or do you intend to provide financial assistance or other support to terrorists or terrorist organizations</label>
						<input type="radio" name="isSupportTerroristsen" class="isSupportTerrorists" value="1"/>YES
						<input type="radio" name="isSupportTerroristsen" class="isSupportTerrorists" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isSupportTerroristsDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Are you a member or representative of a terrorist organization</label>
						<input type="radio" name="isTerroristen" class="isTerrorist" value="1"/>YES
						<input type="radio" name="isTerroristen" class="isTerrorist" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isTerroristDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever ordered, incited, committed, assisted, or otherwise participated in genocide</label>
						<input type="radio" name="isTakeGenocideen" class="isTakeGenocide" value="1"/>YES
						<input type="radio" name="isTakeGenocideen" class="isTakeGenocide" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isTakeGenocideDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever committed, ordered, incited, assisted, or otherwise participated in torture</label>
						<input type="radio" name="isOrderedThreaten" class="isOrderedThreat" value="1"/>YES
						<input type="radio" name="isOrderedThreaten" class="isOrderedThreat" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isOrderedThreatDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you committed, ordered, incited, assisted, or otherwise participated in extrajudicial killings, political killings, or other acts of violence</label>
						<input type="radio" name="isPoliticalKillingen" class="isPoliticalKilling" value="1"/>YES
						<input type="radio" name="isPoliticalKillingen" class="isPoliticalKilling" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isPoliticalKillingDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever engaged in the recruitment or the use of child soldiers</label>
						<input type="radio" name="isRecruitChildSoldieren" class="isRecruitChildSoldier" value="1"/>YES
						<input type="radio" name="isRecruitChildSoldieren" class="isRecruitChildSoldier" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isRecruitChildSoldierDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you, while serving as a government official, been responsible for or directly carried out, at any time, particularly severe violations of religious freedom</label>
						<input type="radio" name="isInroadReligionen" class="isInroadReligion" value="1"/>YES
						<input type="radio" name="isInroadReligionen" class="isInroadReligion" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isInroadReligionDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever been directly involved in the establishment or enforcement of population controls forcing a woman to undergo an abortion against her free choice or a man or a woman to undergo sterilization against his or her free will</label>
						<input type="radio" name="isForcedControlPopulationen" class="isForcedControlPopulation" value="1"/>YES
						<input type="radio" name="isForcedControlPopulationen" class="isForcedControlPopulation" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isForcedControlPopulationDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever been directly involved in the coercive transplantation of human organs or bodily tissue</label>
						<input type="radio" name="isForcedOrganTakeen" class="isForcedOrganTake" value="1"/>YES
						<input type="radio" name="isForcedOrganTakeen" class="isForcedOrganTake" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isForcedOrganTakeDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever sought to obtain or assist others to obtain a visa, entry into the United States, or any other United States immigration benefit by fraud or willful misrepresentation or other unlawful means</label>
						<input type="radio" name="isIllegalVisaUSen" class="isIllegalVisaUS" value="1"/>YES
						<input type="radio" name="isIllegalVisaUSen" class="isIllegalVisaUS" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isIllegalVisaUSDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label> Have you ever been the subject of a removal or deportation hearing</label>
						<input type="radio" name="isDeporteden" class="isDeported" value="1"/>YES
						<input type="radio" name="isDeporteden" class="isDeported" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isDeportedDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you failed to attend a hearing on removability or inadmissibility within the last five years</label>
						<input type="radio" name="isRemoteHearingen" class="isRemoteHearing" value="1"/>YES
						<input type="radio" name="isRemoteHearingen" class="isRemoteHearing" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isRemoteHearingDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;"> Have you ever been unlawfully present, overstayed the amount of time granted by an immigration official or otherwise violated the terms of a U.S. visa</label>
						<input type="radio" name="isViolationVisaConditionsen" class="isViolationVisaConditions" value="1"/>YES
						<input type="radio" name="isViolationVisaConditionsen" class="isViolationVisaConditions" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isViolationVisaConditionsDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever withheld custody of a U.S. citizen child outside the United States from a person granted legal custody by a U.S. court</label>
						<input type="radio" name="isRefusalTransferCustodyen" class="isRefusalTransferCustody" value="1"/>YES
						<input type="radio" name="isRefusalTransferCustodyen" class="isRefusalTransferCustody" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isRefusalTransferCustodyDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you voted in the United States in violation of any law or regulation</label>
						<input type="radio" name="isIllegalVotingen" class="isIllegalVoting" value="1"/>YES
						<input type="radio" name="isIllegalVotingen" class="isIllegalVoting" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isIllegalVotingDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>Have you ever renounced United States citizenship for the purposes of avoiding taxation</label>
						<input type="radio" name="isTaxEvasionen" class="isTaxEvasion" value="1"/>YES
						<input type="radio" name="isTaxEvasionen" class="isTaxEvasion" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isTaxEvasionDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label> Have you attended a public elementary school on student (F) status or a public secondary school after November 30, 1996 without reimbursing the school</label>
						<input type="radio" name="isNotPayTuitionFeesen" class="isNotPayTuitionFees" value="1"/>YES
						<input type="radio" name="isNotPayTuitionFeesen" class="isNotPayTuitionFees" value="2" checked/>NO
					</div>
					<div class="paddingTop elementHide isNotPayTuitionFeesDiv grouptextareaInfo">
						<label>Explain</label>
						<textarea></textarea>
					</div>
				</div>
			</div>
			<!--安全和背景END-->
		</div>
	</div>	
	</body>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffId}';
		var isDisable = '${obj.isDisable}';
	</script>
	<!-- 公共js -->
	<script src="${base}/references/common/js/jquery-1.10.2.js" ></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<!-- 本页js -->
	<script src="${base}/admin/bigCustomer/visa/openPageYesOrNo.js"></script><!-- 本页面  打开默认开关 js -->
	<script src="${base}/admin/bigCustomer/visa/visaGetInfoList.js"></script><!-- 本页面  获取一对多信息 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfoVue.js"></script><!-- 本页面 Vue加载页面内容 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfo.js"></script><!-- 本页面 开关交互 js -->
	<script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js"></script><!-- 本页面 初始化时间插件 js -->
	<script type="text/javascript">
		$(function(){
			//页面不可编辑
			if(isDisable == 1){
				$(".section").attr('readonly', true);
				$(".dislogHide").show();
				$(".saveVisa").hide();
			}
			
			openYesOrNoPage();
		});
	
		//跳转到基本信息页
		function baseInfoBtn(){
			if(isDisable == 1){
				window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable;
			}else{
				//保存签证信息
				save(2);
			}
		}
		
	</script>
</html>

