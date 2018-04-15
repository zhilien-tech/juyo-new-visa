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
				<span>美国签证信息</span>
			</div>
			<div class="btnRight">
				<a class="saveVisa" onclick="save()">保存</a>
				<a class="cancelVisa" onclick="closeWindow()">取消</a>
			</div>
		</div>
		<!-- 左右按钮 -->
		<a id="toPassport" class="leftNav" onclick="baseInfoBtn();">
			<span></span>
		</a>
		<!-- <a class="nextstep">
			<span></span>
		</a> -->
		<div class="topHide"></div>
		<div id="wrapper" v-cloak class="section">
			<div class="dislogHide"></div>
			<!--旅伴信息-->
			<div class="companyInfoModule">
				<div class="titleInfo">旅伴信息</div>
				<div class="companyMain">
					<div class="companyMainInfo groupRadioInfo">
						<label>是否与其他人一起旅游</label>
						<input type="radio" class="companyInfo" v-model="visaInfo.travelCompanionInfo.istravelwithother" value="1" />是
						<input type="radio" class="companyInfo" v-model="visaInfo.travelCompanionInfo.istravelwithother" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="teamture elementHide">
						<div class="groupRadioInfo">
							<label>是否作为团队或组织的一部分旅游</label>
							<input type="radio" class="team" name="ispart" v-model="visaInfo.travelCompanionInfo.ispart" value="1" />是
							<input type="radio" class="team" name="ispart" v-model="visaInfo.travelCompanionInfo.ispart" value="2" checked/>否
						</div>
						<!--第二部分yes-->
						<div class="teamnameture groupInputInfo">
							<label>团队名称</label>
							<input id="groupname" name="groupname" v-model="visaInfo.travelCompanionInfo.groupname" type="text" placeholder="团队名称" />
						</div>
						<!--第二部分No-->
						<div class="teamnamefalse groupInputInfo">
							<div>
							<c:if test="${!empty obj.companionList }">
								<c:forEach var="companion" items="${obj.companionList }">
									<div class="teamnamefalseDiv" >
										<div class="companionSurnName">
											<label>同伴姓</label>
											<input id="firstname" name="firstname" value="${companion.firstname }" type="text" placeholder="同伴姓" />
										</div>
										<div class="companionName">
											<label>同伴名</label>
											<input id="lastname" name="lastname" value="${companion.lastname }" type="text" placeholder="同伴名" />
										</div>
										<div class="clear"></div>
										<div class="youRelationship">
											<label>与你的关系</label>
											<select id="relationship" value="${companion.relationship }" name="relationship">
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
								<div class="teamnamefalseDiv">
									<div class="companionSurnName">
										<label>同伴姓</label>
										<input id="firstname" name="firstname" type="text" placeholder="同伴姓" />
									</div>
									<div class="companionName">
										<label>同伴名</label>
										<input id="lastname" name="lastname" type="text" placeholder="同伴名" />
									</div>
									<div class="clear"></div>
									<div class="youRelationship">
										<label>与你的关系</label>
										<select id="relationship" name="relationship">
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
					 		<input type="radio" id="hasbeeninus" name="hasbeeninus" v-model="visaInfo.previUSTripInfo.hasbeeninus" class="goUS" value="1" />是
							<input type="radio" id="hasbeeninus" name="hasbeeninus" v-model="visaInfo.previUSTripInfo.hasbeeninus" class="goUS" value="2" checked />否
						</div>
						<!--yes-->
						<div class="goUSInfo goUSYes">
							<div class="gotousInfo">
								<c:if test="${!empty obj.gousList }">
									<c:forEach var="gous" items="${obj.gousList }">
										<div class="goUS_CountryDiv">
											<div class="groupInputInfo">
												<label>抵达日期</label>
												<input type="text" id="arrivedate" value="<fmt:formatDate value="${gous.arrivedate }" pattern="dd/MM/yyyy" />" name="arrivedate" class="datetimepickercss" placeholder="日/月/年">
											</div>
											<div class="groupInputInfo stopDate goUS_Country">
												<label>停留时间</label>
												<input id="staydays" name="staydays" value="${gous.staydays }" type="text" />
												<select id="dateunit" name="dateunit">
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
											<input type="text" id="arrivedate" name="arrivedate" class="arrivedate datetimepickercss" placeholder="日/月/年">
										</div>
										<div class="groupInputInfo stopDate">
											<label>停留时间</label>
											<input id="staydays" name="staydays" type="text" />
											<select id="dateunit" name="dateunit">
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
								<input type="radio" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" class="license" value="1" />是
								<input type="radio" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" class="license" value="2" checked />否
							</div>
							<div class="driverInfo elementHide">
								<div class="driverYes">
									<c:if test="${!empty obj.driverList }">
										<c:forEach var="driver" items="${obj.driverList }">
											<div class="goUS_drivers">
												<div class="groupcheckBoxInfo driverMain">
													<label>驾照号</label>
													<input id="driverlicensenumber" value="${driver.driverlicensenumber }" name="driverlicensenumber" type="text" >
													<c:if test="${driver.isknowdrivernumber == 1}">
														<input id="isknowdrivernumber" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input id="isknowdrivernumber" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" type="checkbox"/>
													</c:if>
												</div>
												<div class="groupSelectInfo driverR">
													<label>哪个州的驾照</label>
													<select id="witchstateofdriver" name="witchstateofdriver">
														<option value="0" selected="selected">请选择</option>
														<c:forEach items="${obj.VisaUSStatesEnum }" var="map">
															<c:if test="${driver.witchstateofdriver != map.key}">
																<option value="${map.key }">${map.value }</option>
															</c:if>
															<c:if test="${driver.witchstateofdriver == map.key}">
																<option value="${map.key }" selected="selected">${map.value }</option>
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
												<input id="driverlicensenumber" name="driverlicensenumber" type="text" >
												<input id="isknowdrivernumber" name="isknowdrivernumber" type="checkbox"/>
											</div>
											<div class="groupSelectInfo driverR">
												<label>哪个州的驾照</label>
												<select id="witchstateofdriver" name="witchstateofdriver">
													<option value="0">请选择</option>
													<c:forEach items="${obj.VisaUSStatesEnum }" var="map">
														<option value="${map.key }">${map.value }</option>
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
						<input type="radio" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" class="visaUS" value="1" />是
						<input type="radio" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" class="visaUS" value="2" checked />否
					</div>
					<div>
						<div class="dateIssue goUS_visa">
							<div class="groupInputInfo lastVisaDate">
								<label>最后一次签证的签发日期</label>
								<input id="issueddate" name="issueddate" v-model="visaInfo.previUSTripInfo.issueddate" class="datetimepickercss" placeholder="日/月/年" type="text"/>
							</div>
							<div class="groupcheckBoxInfo visaNum">
								<label>签证号码</label>
								<input name="visanumber" v-model="visaInfo.previUSTripInfo.visanumber" type="text" />
								<input id="idknowvisanumber" :value="visaInfo.previUSTripInfo.idknowvisanumber" name="idknowvisanumber" v-on:click="idknowvisanumberChange" v-model="visaInfo.previUSTripInfo.idknowvisanumber" type="checkbox"/>
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
									<input type="radio" name="islost" v-model="visaInfo.previUSTripInfo.islost" class="lose" value="1" />是
									<input type="radio" name="islost" v-model="visaInfo.previUSTripInfo.islost" v-on:click="visaNotLost" class="lose" value="2" checked />否
								</div>
								<div class="yearExplain displayTop elementHide"><!-- 默认隐藏 -->
									<div class="displayLeft groupInputInfo">
										<label>年份</label>
										<input name="lostyear" v-model="visaInfo.previUSTripInfo.lostyear" type="text" />
									</div>
									<div class="displayRight grouptextareaInfo">
										<label>说明</label>
										<textarea name="lostexplain" v-model="visaInfo.previUSTripInfo.lostexplain"></textarea>
									</div>
								</div>
							</div>
							<div class="clear"></div>
							<div class="paddingTop">
								<div>
									<div class="groupRadioInfo">
										<label>你的美国签证是否被取消或撤销过</label>
										<input type="radio" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" class="revoke" value="1" />是
										<input type="radio" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" v-on:click="visaNotCancel" class="revoke" value="2" checked />否
									</div>
									<div class="explain grouptextareaInfo paddingTop">
										<label>说明</label>
										<textarea name="cancelexplain" class="bigArea" v-model="visaInfo.previUSTripInfo.cancelexplain"></textarea>
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
					<input type="radio" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" class="refuse" value="1" />是
					<input type="radio" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" v-on:click="visaNotRefused" class="refuse" value="2" checked />否
				</div>
				<div class="refuseExplain paddingTop grouptextareaInfo">
					<label>说明</label>
					<textarea name="refusedexplain" class="bigArea" v-model="visaInfo.previUSTripInfo.refusedexplain"></textarea>
				</div>
			</div>
			<!--曾经是否是美国合法永久居民-->
			<div class="paddingBottom ">
				<div class="groupRadioInfo">
					<label>曾经是否是美国合法永久居民</label>
					<input type="radio" name="islegalpermanentresident" v-model="visaInfo.previUSTripInfo.islegalpermanentresident" class="onceLegitimate" value="1" />是
					<input type="radio" name="islegalpermanentresident" v-model="visaInfo.previUSTripInfo.islegalpermanentresident" v-on:click="visaNotIegal" class="onceLegitimate" value="2" checked />否
				</div>
				<div class="onceExplain paddingTop grouptextareaInfo">
					<label>说明</label>
					<textarea name="permanentresidentexplain" class="bigArea" v-model="visaInfo.previUSTripInfo.permanentresidentexplain"></textarea>
				</div>
			</div>
			<!--有没有人曾代表您向美国公民和移民服务局提交过移民申请-->
			<div class="paddingBottom">
				<div class="groupRadioInfo">
					<label>有没有人曾代表您向美国公民和移民服务局提交过移民申请</label>
					<input type="radio" name="isfiledimmigrantpetition" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetition" class="onceImmigration" value="1" />是
					<input type="radio" name="isfiledimmigrantpetition" v-model="visaInfo.previUSTripInfo.isfiledimmigrantpetition" v-on:click="visaNotfiledimmigrantpetition" class="onceImmigration" value="2" checked />否
				</div>
				<div class="immigrationExplain paddingTop grouptextareaInfo">
					<label>说明</label>
					<textarea name="immigrantpetitionexplain" class="bigArea" v-model="visaInfo.previUSTripInfo.immigrantpetitionexplain"></textarea>
				</div>
			</div>
			<!--以前的美国旅游信息END-->
			<!--美国联络点-->
			<div class="contactPoint">
				<div class="titleInfo">美国联络点</div>
				<div class="groupInputInfo paddingLeft">
					<label>联系人姓</label>
					<input name="firstname" v-model="visaInfo.contactPointInfo.firstname" type="text" />
				</div>
				<div class="groupcheckBoxInfo paddingRight">
					<label>联系人名</label>
					<input name="lastname" v-model="visaInfo.contactPointInfo.lastname" type="text"  />
					<input id="isknowname" v-model="visaInfo.contactPointInfo.isknowname" v-on:click="isKnowContactPointName" :value="visaInfo.contactPointInfo.isknowname" name="isknowname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>组织名称</label>
					<input id="organizationname" name="organizationname" v-model="visaInfo.contactPointInfo.organizationname" type="text" />
					<input id="isknoworganizationname" name="isknoworganizationname" v-on:click="isKnowOrganizationName" v-model="visaInfo.contactPointInfo.isknoworganizationname"  class="groupname_us" type="checkbox" />
				</div>
				<div class="paddingRight groupSelectInfo">
					<label>与你的关系</label>
					<select id="ralationship" v-model="visaInfo.contactPointInfo.ralationship" name="ralationship">
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
						<input name="address" v-model="visaInfo.contactPointInfo.address" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label>美国街道地址(次选)*可选</label>
						<input name="secaddress" v-model="visaInfo.contactPointInfo.secaddress" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupInputInfo" >
						<label>市</label>
						<input id="city" name="city" v-model="visaInfo.contactPointInfo.city" type="text" />
					</div>
					<div class="paddingRight groupSelectInfo" >
						<label>州</label>
						<select id="state" name="state" v-model="visaInfo.contactPointInfo.state">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaUSStatesEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupInputInfo" >
						<label>邮政编码</label>
						<input name="zipcode" v-model="visaInfo.contactPointInfo.zipcode" type="text" />
					</div>
					<div class="paddingRight groupInputInfo" >
						<label>电话号码</label>
						<input name="telephone" v-model="visaInfo.contactPointInfo.telephone" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingTop groupcheckBoxInfo cbox">
						<label>邮件地址</label>
						<input name="email" v-model="visaInfo.contactPointInfo.email" type="text" />
						<input id="isKnowEmailAddress" name="isknowemail" v-on:click="isKnowEmailAddress" v-model="visaInfo.contactPointInfo.isknowemail" type="checkbox" />
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
					<input name="fatherfirstname" v-model="visaInfo.familyInfo.fatherfirstname" type="text"/>
					<input id="isKnowFatherXing" name="isknowfatherfirstname"  v-on:click="isknowfatherfirstname"  v-model="visaInfo.familyInfo.isknowfatherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label>父亲的名</label>
					<input name="fatherlastname" v-model="visaInfo.familyInfo.fatherlastname" type="text" />
					<input id="isKnowFatherMing" name="isknowfatherlastname" v-on:click="isknowfatherlastname" v-model="visaInfo.familyInfo.isknowfatherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>你的父亲是否在美国</label>
						<input type="radio" name="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" class="fatherUS" value="1" />是
						<input type="radio" name="isfatherinus"  v-on:click="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" class="fatherUS" value="2" checked />否
					</div>
					<!--yes-->
					<div class="fatherUSYes groupSelectInfo paddingNone">
						<label>身份状态</label>
						<select id="fatherstatus" v-model="visaInfo.familyInfo.fatherstatus" name="fatherstatus">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>母亲的姓</label>
					<input id="motherfirstname" name="motherfirstname" v-model="visaInfo.familyInfo.motherfirstname" type="text" />
					<input id="isKnowMotherXing" name="isknowmotherfirstname" v-on:click="isknowmotherfirstname" v-model="visaInfo.familyInfo.isknowmotherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label>母亲的名</label>
					<input id="motherlastname" name="motherlastname" v-model="visaInfo.familyInfo.motherlastname" type="text" />
					<input id="isKnowMotherMing" name="isknowmotherlastname" v-on:click="isknowmotherlastname" v-model="visaInfo.familyInfo.isknowmotherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label>你的母亲是否在美国</label>
						<input type="radio" name="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" class="motherUS" value="1" />是
						<input type="radio" name="ismotherinus" v-on:click="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" class="motherUS" value="2" checked />否
					</div>
					<div class="motherUSYes paddingNone groupSelectInfo">
						<label>身份状态</label>
						<select id="motherstatus" name="motherstatus" v-model="visaInfo.familyInfo.motherstatus">
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
						<input type="radio" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" class="directRelatives directUSRelatives" value="1" />是
						<input type="radio" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" class="directRelatives directUSRelatives" value="2" checked/>否
					</div>
					<div class="directRelatives">
						<!--yes-->
						<c:if test="${!empty obj.zhiFamilyList }">
							<c:forEach var="zhifamily" items="${obj.zhiFamilyList }">
								<div class="directRelativesYes">
									<div class="floatLeft leftNo groupInputInfo">
										<label>姓</label>
										<input name="relativesfirstname" value="${zhifamily.relativesfirstname }" type="text" />
									</div>
									<div class="floatRight groupInputInfo">
										<label>名</label>
										<input name="relativeslastname" value="${zhifamily.relativeslastname }"  type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>与你的关系</label>
										<select name="relationship">
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
										<select id="relativesstatus" name="relativesstatus" >
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
									<input name="relativesfirstname" type="text" />
								</div>
								<div class="floatRight groupInputInfo">
									<label>名</label>
									<input name="relativeslastname" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>与你的关系</label>
									<select name="relationship">
										<option value="0">请选择</option>
										<c:forEach items="${obj.ImmediateRelationshipEnum }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight groupSelectInfo">
									<label>亲属的身份</label>
									<select id="relativesstatus" name="relativesstatus">
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
					<input name="spousefirstname" v-model="visaInfo.familyInfo.spousefirstname" type="text" />
				</div>
				<div class="floatRight groupInputInfo">
					<label>配偶的名</label>
					<input name="spouselastname" v-model="visaInfo.familyInfo.spouselastname" type="text" />
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupInputInfo">
					<label>配偶的生日</label>
					<input id="spousebirthday" name="spousebirthday" v-model="visaInfo.familyInfo.spousebirthday" class="datetimepickercss" type="text" placeholder="日/月/年" />
				</div>
				<div class="paddingRight groupSelectInfo">
					<label>配偶的国籍</label>
					<select id="spousenationality" name="spousenationality" v-model="visaInfo.familyInfo.spousenationality">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label>配偶的出生城市</label>
					<input name="spousecity" v-model="visaInfo.familyInfo.spousecity" type="text" />
					<input id="isKnowMateCity" name="isknowspousecity" v-model="visaInfo.familyInfo.isknowspousecity" type="checkbox" />
				</div>
				<div class="paddingRight groupSelectInfo" >
					<label>配偶的出生国家</label>
					<select id="spousecountry" name="spousecountry" v-model="visaInfo.familyInfo.spousecountry">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="clear"></div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label>配偶的联系地址</label>
					<select id="spouseaddress" name="spouseaddress" v-model="visaInfo.familyInfo.spouseaddress" class="spouse_Address" onchange="changeSpouse()">
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
						<input name="firstaddress" v-model="visaInfo.familyInfo.firstaddress" type="text" />
					</div>
					<div class="floatRight groupInputInfo">
						<label>街道地址(次要)*可选</label>
						<input name="secondaddress" v-model="visaInfo.familyInfo.secondaddress" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupInputInfo">
						<label>市</label>
						<input name="city" v-model="visaInfo.familyInfo.city" type="text"/>
					</div>
					<div class="paddingRight groupcheckBoxInfo">
						<label>州/省</label>
						<input name="province" v-model="visaInfo.familyInfo.province" type="text" />
						<input id="isprovinceapply" name="isprovinceapply"  v-model="visaInfo.familyInfo.isprovinceapply" type="checkbox" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>邮政编码</label>
						<input name="zipcode" v-model="visaInfo.familyInfo.zipcode" type="text" />
						<input id="iszipcodeapply" name="iszipcodeapply" v-model="visaInfo.familyInfo.iszipcodeapply" type="checkbox" />
					</div>
					<div class="paddingRight groupSelectInfo">
						<label>国家/地区</label>
						<select name="country" v-model="visaInfo.familyInfo.country">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
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
					<select id="occupation" name="occupation" v-model="visaInfo.workEducationInfo.occupation" @change="occupationChange()">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaCareersEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="paddingTop elementHide jobEduLearningInfoDiv">
					<div class="groupInputInfo draBig">
						<label>目前的工作单位或者学校名称</label>
						<input name="unitname" v-model="visaInfo.workEducationInfo.unitname" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS">
						<label >街道地址(首选)</label>
						<input name="address" v-model="visaInfo.workEducationInfo.address" type="text" />
					</div>
					<div class="clear"></div>
					<div class="groupInputInfo draBig marginLS">
						<label>街道地址(次要)*可选</label>
						<input name="secaddress" v-model="visaInfo.workEducationInfo.secaddress" type="text" />
					</div>
					<div class="paddingLeft groupInputInfo">
						<label>市</label>
						<input name="city" v-model="visaInfo.workEducationInfo.city" type="text"/>
					</div>
					
					<div class="paddingRight groupcheckBoxInfo">
						<label>州/省</label>
						<input name="province" v-model="visaInfo.workEducationInfo.province" type="text"/>
						<input name="isprovinceapply" id="isprovinceapplywork" v-model="visaInfo.workEducationInfo.isprovinceapply" type="checkbox"/>
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo">
						<label>邮政编码</label>
						<input name="zipcode" v-model="visaInfo.workEducationInfo.zipcode" type="text" />
						<input name="iszipcodeapply" id="iszipcodeapplywork" v-model="visaInfo.workEducationInfo.iszipcodeapply" type="checkbox" />
					</div>
					<div class="paddingRight groupInputInfo">
						<label>电话号吗</label>
						<input name="telephone" v-model="visaInfo.workEducationInfo.telephone" type="text" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupSelectInfo" >
						<label>国家/地区</label>
						<select name="country" v-model="visaInfo.workEducationInfo.country">
							<option value="0">请选择</option>
							<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
								<option value="${map.key }">${map.value }</option>
							</c:forEach>
						</select>
					</div>
					
					<div class="paddingRight groupInputInfo">
						<label>开始日期</label>
						<input id="workstartdate" name="workstartdate" v-model="visaInfo.workEducationInfo.workstartdate" class="datetimepickercss" type="text" placeholder="日/月/年" />
					</div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo" >
						<label>当地月收入(如果雇佣)</label>
						<input name="salary" v-model="visaInfo.workEducationInfo.salary" type="text" />
						<input name="issalaryapply" id="issalaryapplywork" v-model="visaInfo.workEducationInfo.issalaryapply" type="checkbox" />
					</div>
					<div class="clear"></div>
					<div class="grouptextareaInfo groupPM">
						<label>简要描述你的指责</label>
						<textarea name="duty" class="bigArea" v-model="visaInfo.workEducationInfo.duty"></textarea>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="grouptextareaInfo elementHide jobEduLearningInfoTextarea">
					<!-- <label>说明</label>
					<textarea></textarea> -->
				</div>
			</div>
			<!--以前-->
			<div>
				<div class="paddingTop padding-left">
					<div>
						<div class="groupRadioInfo">
							<label>以前是否工作过</label>
							<input type="radio" name="isemployed" v-model="visaInfo.workEducationInfo.isemployed" class="beforeWork" value="1" />是
							<input type="radio" name="isemployed" v-model="visaInfo.workEducationInfo.isemployed" class="beforeWork" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="beforeWorkInfo elementHide">
						  <div class="beforeWorkYes">
							<c:if test="${!empty obj.beforeWorkList }">
								<c:forEach var="beforeWork" items="${obj.beforeWorkList }">
									<div class="workBeforeInfosDiv">
										<div class="leftNo marginLS groupInputInfo" >
											<label>雇主名字</label>
											<input name="employername" value="${beforeWork.employername }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>雇主街道地址(首选)</label>
											<input name="employeraddress" value="${beforeWork.employeraddress }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>雇主街道地址(次选)*可选</label>
											<input name="employeraddressSec" value="${beforeWork.employeraddressSec }" type="text" />
										</div>
										<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>市</label>
											<input name="employercity" id="employercitybefore" value="${beforeWork.employercity }" type="text" />
											<!-- <input type="checkbox" /> -->
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label>州/省</label>
											<input name="employerprovince" value="${beforeWork.employerprovince }" type="text" />
											<%-- <input name="isemployerprovinceapply" value="${beforeWork.isemployerprovinceapply }" type="text" /> --%>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>邮政编码</label>
											<input name="employerzipcode" value="${beforeWork.employerzipcode }" type="text" />
											<c:if test="${beforeWork.isemployerzipcodeapply == 1}">
												<input id="isKonwOrtherZipCode" name="isemployerzipcodeapply" value="${beforeWork.isemployerzipcodeapply }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isemployerzipcodeapply != 1}">
												<input id="isKonwOrtherZipCode" name="isemployerzipcodeapply" value="${beforeWork.isemployerzipcodeapply }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight groupSelectInfo">
											<label>国家/地区</label>
											<select name="employercountry">
												<option value="0">请选择</option>
												<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
													<c:if test="${beforeWork.employercountry != map.key}">
														<option value="${map.key }">${map.value }</option>
													</c:if>
													<c:if test="${beforeWork.employercountry == map.key}">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>电话号码</label>
											<input name="employertelephone" value="${beforeWork.employertelephone }" type="text" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>职称</label>
											<input name="jobtitle" value="${beforeWork.jobtitle }" type="text"/>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>主管的姓</label>
											<input name="supervisorfirstname" value="${beforeWork.supervisorfirstname }" type="text" />
											<c:if test="${beforeWork.isknowsupervisorfirstname == 1}">
												<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" value="${beforeWork.isknowsupervisorfirstname }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorfirstname != 1}">
												<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" value="${beforeWork.isknowsupervisorfirstname }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight groupcheckBoxInfo">
											<label>主管的名</label>
											<input name="supervisorlastname" value="${beforeWork.supervisorlastname }" type="text" />
											<c:if test="${beforeWork.isknowsupervisorlastname == 1}">
												<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" value="${beforeWork.isknowsupervisorlastname }" checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${beforeWork.isknowsupervisorlastname != 1}">
												<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" value="${beforeWork.isknowsupervisorlastname }" type="checkbox" />
											</c:if>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo" >
											<label>入职时间</label>
											<input id="employstartdate" name="employstartdate" value="<fmt:formatDate value="${beforeWork.employstartdate }" pattern="dd/MM/yyyy" />" class="datetimepickercss" type="text" placeholder="日/月/年" />
										</div>
										<div class="paddingRight groupInputInfo">
											<label>离职时间</label>
											<input id="employenddate" name="employenddate" value="<fmt:formatDate value="${beforeWork.employenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss" type="text" placeholder="日/月/年" />
										</div>
										<div class="clear"></div>
										<div class="draBig leftNo marginLS grouptextareaInfo">
											<label>简要描述你的职责</label>
											<input type="text" name="previousduty" class="bigArea previousduty" value="${beforeWork.previousduty }" />
											<%-- <textarea name="previousduty" class="bigArea previousduty" value="${beforeWork.previousduty }"></textarea> --%>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeWorkList }">
								<div class="workBeforeInfosDiv">
									<div class="leftNo marginLS groupInputInfo" >
										<label>雇主名字</label>
										<input name="employername" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>雇主街道地址(首选)</label>
										<input name="employeraddress" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo">
										<label>雇主街道地址(次选)*可选</label>
										<input type="text" />
									</div>
									<div class="paddingLeft leftNo groupcheckBoxInfo" >
										<label>市</label>
										<input name="employercity" type="text" />
										<input type="checkbox" id="employercitybefore" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>州/省</label>
										<input name="employerprovince" type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>邮政编码</label>
										<input name="employerzipcode" type="text" />
										<input name="isemployerzipcodeapply" id="isKonwOrtherZipCode" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo">
										<label>国家/地区</label>
										<select name="employercountry">
											<option value="0">请选择</option>
											<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
												<option value="${map.key }">${map.value }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>电话号码</label>
										<input name="employertelephone" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>职称</label>
										<input name="jobtitle" type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>主管的姓</label>
										<input name="supervisorfirstname" type="text" />
										<input name="isknowsupervisorfirstname" id="isknowsupervisorfirstnamebefore" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupcheckBoxInfo">
										<label>主管的名</label>
										<input name="supervisorlastname" type="text" />
										<input name="isknowsupervisorlastname" id="isknowsupervisorlastnamebefore" type="checkbox" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>入职时间</label>
										<input id="employstartdate" name="employstartdate" class="datetimepickercss" type="text" placeholder="日/月/年" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>离职时间</label>
										<input id="employenddate" name="employenddate" class="datetimepickercss" type="text" placeholder="日/月/年" />
									</div>
									<div class="clear"></div>
									<div class="draBig leftNo marginLS grouptextareaInfo">
										<label>简要描述你的职责</label>
										<input type="text" name="previousduty" class="bigArea previousduty" />
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
							<input type="radio" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" class="education" value="1" />是
							<input type="radio" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" class="education" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="educationInfo elementHide">
							<div class="educationYes">
							<c:if test="${!empty obj.beforeEducationList }">
								<c:forEach var="education" items="${obj.beforeEducationList }">
									<div class="midSchoolEduDiv">
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>机构名称</label>
											<input name="institution" value="${education.institution }" type="text"/>
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>街道地址(首选)</label>
											<input name="institutionaddress" value="${education.institutionaddress }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo">
											<label>街道地址(次选)*可选</label>
											<input name="secinstitutionaddress" type="text" value="${education.secinstitutionaddress }" />
										</div>
										<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>州/省</label>
											<input name="institutionprovince" value="${education.institutionprovince }" type="text" />
											<c:if test="${education.isinstitutionprovinceapply == 1}">
												<input name="isinstitutionprovinceapply" value="${education.isinstitutionprovinceapply }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionprovinceapply != 1}">
												<input name="isinstitutionprovinceapply" value="${education.isinstitutionprovinceapply }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >市</label>
											<input name="institutioncity" value="${education.institutioncity }" type="text" />
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupcheckBoxInfo">
											<label>邮政编码</label>
											<input name="institutionzipcode" value="${education.institutionzipcode }" type="text" />
											<c:if test="${education.isinstitutionzipcodeapply == 1}">
													<input name="isinstitutionzipcodeapply" id="codeEdu" value="${education.isinstitutionzipcodeapply }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionzipcodeapply != 1}">
												<input name="isinstitutionzipcodeapply" id="codeEdu" value="${education.isinstitutionzipcodeapply }" type="checkbox" />
											</c:if>
										</div>
										<div class="paddingRight leftNo groupSelectInfo" >
											<label>国家/地区</label>
											<select name="institutioncountry">
												<option value="0">请选择</option>
												<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
													<c:if test="${education.institutioncountry != map.key}">
														<option value="${map.key }">${map.value }</option>
													</c:if>
													<c:if test="${education.institutioncountry == map.key}">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
											<label>学科</label>
											<input name="course" value="${education.course }" type="text" />
										</div>
										
										<div class="paddingRight leftNo groupInputInfo">
											<label>参加课程开始时间</label>
											<input id="coursestartdate" name="coursestartdate" value="<fmt:formatDate value="${education.coursestartdate }" pattern="dd/MM/yyyy" />"  class="datetimepickercss" type="text" placeholder="日/月/年" />
										</div>
										<div class="clear"></div>
										<div class="leftNo groupInputInfo">
											<label>结束时间</label>
											<input id="courseenddate" name="courseenddate" value="<fmt:formatDate value="${education.courseenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss" type="text" placeholder="日/月/年" />
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeEducationList }">
								<div class="midSchoolEduDiv">
									<div class="draBig leftNo groupInputInfo">
										<label>机构名称</label>
										<input name="institution" type="text"/>
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>街道地址(首选)</label>
										<input name="institutionaddress" type="text" />
									</div>
									<div class="draBig leftNo groupInputInfo">
										<label>街道地址(次选)*可选</label>
										<input type="text" />
									</div>
									<div class="paddingLeft leftNo groupcheckBoxInfo" >
											<label>州/省</label>
											<input name="institutionprovince" type="text" />
											<input name="isinstitutionprovinceapply"  type="checkbox" />
										</div>
										<div class="paddingRight leftNo groupInputInfo">
											<label >市</label>
											<input name="institutioncity" type="text" />
										</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupcheckBoxInfo">
										<label>邮政编码</label>
										<input name="institutionzipcode" type="text" />
										<input name="isinstitutionzipcodeapply" type="checkbox" />
									</div>
									<div class="paddingRight leftNo groupSelectInfo" >
										<label>国家/地区</label>
										<select name="institutioncountry">
											<option value="0">请选择</option>
											<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
												<option value="${map.key }">${map.value }</option>
											</c:forEach>
										</select>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>学科</label>
										<input name="course" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>参加课程开始时间</label>
										<input id="coursestartdate" name="coursestartdate" class="datetimepickercss" type="text" placeholder="日/月/年" />
									</div>
									<div class="clear"></div>
									<div class="leftNo groupInputInfo">
										<label>结束时间</label>
										<input id="courseenddate" name="courseenddate" class="datetimepickercss" type="text" placeholder="日/月/年" />
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
						<input type="radio" name="isclan" v-model="visaInfo.workEducationInfo.isclan" class="isclan" value="1"/>是
						<input type="radio" name="isclan" v-on:click="isclan()" v-model="visaInfo.workEducationInfo.isclan" class="isclan" value="2" checked/>否
					</div>
					
					<!--yes-->
					<div class="isclanYes elementHide">
						<div>
							<div class="clannameDiv">
								<div class="draBig leftNo groupInputInfo" >
									<label>氏族或部落名称</label>
									<input name="clanname" v-model="visaInfo.workEducationInfo.clanname"  type="text"  />
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
									<input name="languagename" value="${language.languagename }" type="text" />
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${empty obj.languageList }">
						<div class="languagename languagenameDiv paddingTop padding-left">
							<label>使用的语言名称</label>
							<div class="groupInputInfo">
								<input name="languagename" type="text" />
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
						<input type="radio" name="istraveledanycountry" v-model="visaInfo.workEducationInfo.istraveledanycountry" class="istraveledanycountry" value="1" />是
						<input type="radio" name="istraveledanycountry" v-model="visaInfo.workEducationInfo.istraveledanycountry" class="istraveledanycountry" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="isTravelYes elementHide">
						<div class="gocountryYes">
							<c:if test="${!empty obj.gocountryList }">
								<c:forEach var="gocountry" items="${obj.gocountryList }">
									<div class="travelCountry paddingTop groupInputInfo">
										<label>国家/地区</label>
										<div class="groupInputInfo groupSelectInfo">
										
											<select name="traveledcountry">
												<option value="0">请选择</option>
												<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
														<c:if test="${gocountry.traveledcountry != map.key}">
															<option value="${map.key }">${map.value }</option>
														</c:if>
														<c:if test="${gocountry.traveledcountry == map.key}">
															<option value="${map.key }" selected="selected">${map.value }</option>
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
									<input name="traveledcountry" type="text"/>
								</div>
							</c:if>
						</div>
						<div class="btnGroup gocountryGroup">
							<a class="save gocountrysave">添加</a>
							<a class="cancel gocountrycancel">去掉</a>
						</div>
					</div>
				</div>
				<div class="padding-left">
					<div class="groupRadioInfo">
						<label>是否属于、致力于、或为任何专业、社会或慈善组织而工作</label>
						<input type="radio" name="isworkedcharitableorganization" v-model="visaInfo.workEducationInfo.isworkedcharitableorganization" class="isworkedcharitableorganization" value="1"/>是
						<input type="radio" name="isworkedcharitableorganization" v-model="visaInfo.workEducationInfo.isworkedcharitableorganization" class="isworkedcharitableorganization" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="isOrganizationYes elementHide">
						<div class="organizationYes">
							<c:if test="${!empty obj.organizationList }">
								<c:forEach var="organization" items="${obj.organizationList }">
									<div class="paddingTop draBig leftNo organizationDiv groupInputInfo">
										<label>组织名称</label>
										<input name="organizationname" value="${organization.organizationname }" type="text"/>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.organizationList }">
								<div class="paddingTop draBig leftNo organizationDiv groupInputInfo">
									<label>组织名称</label>
									<input name="organizationname" type="text"/>
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
						<input type="radio" name="hasspecializedskill" v-model="visaInfo.workEducationInfo.hasspecializedskill" class="hasspecializedskill" value="1" />是
						<input type="radio"name="hasspecializedskill" v-on:click="hasspecializedskill()" v-model="visaInfo.workEducationInfo.hasspecializedskill" class="hasspecializedskill" value="2" checked />否
					</div>
					<!--yes-->
					<div class="paddingTop skillDiv elementHide grouptextareaInfo">
						<label>说明</label>
						<input type="text" name="skillexplain" class="bigArea" v-model="visaInfo.workEducationInfo.skillexplain" />
						<!-- <textarea name="skillexplain" class="bigArea" v-model="visaInfo.workEducationInfo.skillexplain"></textarea> -->
					</div>
				</div>
				<div class="padding-left">
					<div class="groupRadioInfo">
						<label style="display: block;">是否曾服兵役</label>
						<input type="radio" name="hasservedinmilitary" v-model="visaInfo.workEducationInfo.hasservedinmilitary" class="hasservedinmilitary" value="1"/>是
						<input type="radio"name="hasservedinmilitary" v-model="visaInfo.workEducationInfo.hasservedinmilitary" class="hasservedinmilitary" value="2" checked/>否
					</div>
					<!--yes-->
					<div class="paddingTop elementHide militaryServiceYes">
					  <div class="militaryYes">
						<c:if test="${!empty obj.conscientiousList }">
							<c:forEach var="conscientious" items="${obj.conscientiousList }">
								<div class="militaryInfoDiv">
									<div class="paddingLeft leftNo groupSelectInfo">
										<label>国家/地区</label>
										<select name="militarycountry">
											<option value="0">请选择</option>
											<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
													<c:if test="${conscientious.militarycountry != map.key}">
														<option value="${map.key }">${map.value }</option>
													</c:if>
													<c:if test="${conscientious.militarycountry == map.key}">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>服务分支</label>
										<input name="servicebranch" value="${conscientious.servicebranch }" type="text" />
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo" >
										<label>排名/位置</label>
										<input name="rank" value="${conscientious.rank }" type="text" />
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>军事专业</label>
										<input name="militaryspecialty" value="${conscientious.militaryspecialty }" type="text"/>
									</div>
									<div class="clear"></div>
									<div class="paddingLeft leftNo groupInputInfo">
										<label>服兵役开始时间日期</label>
										<input id="servicestartdate" name="servicestartdate" value="<fmt:formatDate value="${conscientious.servicestartdate }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss" placeholder="日/月/年"/>
									</div>
									<div class="paddingRight leftNo groupInputInfo">
										<label>结束时间</label>
										<input id="serviceenddate" name="serviceenddate" value="<fmt:formatDate value="${conscientious.serviceenddate }" pattern="dd/MM/yyyy" />" type="text" class="datetimepickercss" placeholder="日/月/年"/>
									</div>
								</div>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty obj.conscientiousList }">
							<div class="militaryInfoDiv">
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>国家/地区</label>
									<select name="militarycountry">
										<option value="0">请选择</option>
										<c:forEach items="${obj.VisaCitizenshipEnum }" var="map">
											<option value="${map.key }">${map.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>服务分支</label>
									<input name="servicebranch" type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo" >
									<label>排名/位置</label>
									<input name="rank" type="text" />
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>军事专业</label>
									<input name="militaryspecialty" type="text"/>
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupInputInfo">
									<label>服兵役开始时间日期</label>
									<input id="servicestartdate" name="servicestartdate" type="text" class="datetimepickercss" placeholder="日/月/年"/>
								</div>
								<div class="paddingRight leftNo groupInputInfo">
									<label>结束时间</label>
									<input id="serviceenddate" name="serviceenddate" type="text" class="datetimepickercss" placeholder="日/月/年"/>
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
						<textarea></textarea>
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
		<div class="English">
			<!--旅伴信息-->
			
		</div>
	</body>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = ${obj.staffId};
	</script>
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
	
	<script src="${base}/admin/bigCustomer/visa/openPageYesOrNo.js"></script><!-- 本页面  打开默认开关 js -->
	<script src="${base}/admin/bigCustomer/visa/visaGetInfoList.js"></script><!-- 本页面  获取一对多信息 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfoVue.js"></script><!-- 本页面 Vue加载页面内容 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfo.js"></script><!-- 本页面 开关交互 js -->
	<script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js"></script><!-- 本页面 初始化时间插件 js -->
	<script type="text/javascript">
		var isDisable = '${obj.isDisable}';
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
			//左箭头跳转
			window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable;
			//保存签证信息
			save();
		}
		
	</script>
</html>

