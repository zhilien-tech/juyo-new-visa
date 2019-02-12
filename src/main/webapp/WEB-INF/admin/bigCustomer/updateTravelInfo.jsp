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
.select2-container{
	border: 1px solid #aaa;
}
input:focus{
	border: none!important;

	outline: 0;
	border-color: #3087f1!important;
	border: 1px solid #3087f1!important;
	box-shadow: none!important;
}
.select2 input:focus{
	border: none!important;

	outline: 0;

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
								<input autocomplete="new-password" type="hidden" name="staffid" value="${obj.staffId }" /> <label><span
									class="s">*</span> 是否与其他人同行</label> <input autocomplete="new-password" type="radio"
									class="companyInfo" name="istravelwithother" value="1" />是 <input autocomplete="new-password"
									type="radio" class="companyInfo" name="istravelwithother"
									style="margin-left: 20px;" value="2" checked />否
							</div>
							<div style="float: left; margin: 20px 0 0 185px;">
								<a class="save companysave" style="display:none;">添加</a>
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
													<label><span class="s">*</span>同行人姓</label> <input autocomplete="new-password"
														id="firstname" name="firstname"
														value="${companion.firstname}"
														type="text" placeholder="" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> 同行人名</label> <input autocomplete="new-password"
														id="lastname" name="lastname"
														value="${companion.lastname}"
														type="text" placeholder="" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> Surnames</label> <input autocomplete="new-password"
														id="firstnameen" class="firstnameen"
														value="${companion.firstnameen}" name="firstnameen"
														type="text" placeholder="" />
												</div>
												<div class="col-sm-3">
													<label><span class="s">*</span> Given Names</label> <input autocomplete="new-password"
														id="lastnameen" class="lastnameen"
														value="${companion.lastnameen}" name="lastnameen"
														type="text" placeholder=" " />
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
													<label><span class="s">*</span> 说明</label> <input autocomplete="new-password"
														style="height: 34px;padding-left:10px;" id="explain" name="explain"
														value="${companion.companionexplain }" type="text" placeholder="" />
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
												<label><span class="s">*</span>同行人姓</label> <input autocomplete="new-password"
													id="firstname" name="firstname" type="text"
													placeholder="" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> 同行人名</label> <input autocomplete="new-password"
													id="lastname" name="lastname" type="text"
													placeholder="" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> Surnames</label> <input autocomplete="new-password"
													id="firstnameen" class="firstnameen" name="firstnameen"
													type="text" placeholder="" />
											</div>
											<div class="col-sm-3">
												<label><span class="s">*</span> Given Names</label> <input autocomplete="new-password"
													id="lastnameen" class="lastnameen" name="lastnameen"
													type="text" placeholder=" " />
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
												<label><span class="s">*</span> 说明</label> <input autocomplete="new-password"
													style="height: 34px;padding-left: 10px;" id="" name="" type="text"
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
				
				<div class="companyPay" style="padding-left: 18px;">
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 费用支付公司/组织的名称</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="comname" name="comname" value="${obj.tripinfo.comname }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship" style="width:382px;">
							<label><span class="s">*</span> Name of Company/Organization Paying for Trip</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="comname" name="comname" value="${obj.tripinfo.comname }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 电话号码</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="comtelephone" name="comtelephone" value="${obj.tripinfo.comtelephone }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 与你的关系</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="comrelationwithyou" name="comrelationwithyou" value="${obj.tripinfo.comrelationwithyou }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> Relation to You</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="comrelationwithyou" name="comrelationwithyou" value="${obj.tripinfo.comrelationwithyou }"/>
						</div>
					</div>
				
				</div>
				
				<div class="otherPay" style="padding-left: 18px;">
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 费用支付人的姓</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="payfirstname" name="payfirstname" value="${obj.tripinfo.payfirstname }"/>
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 费用支付人的名</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="paylastname" name="paylastname" value="${obj.tripinfo.paylastname }"/>
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> Surnames</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="payfirstnameen" name="payfirstnameen" value="${obj.tripinfo.payfirstnameen }"/>
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> Given Names</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="paylastnameen" name="paylastnameen" value="${obj.tripinfo.paylastnameen }"/>
						</div>
						
					</div>
					
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 电话</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="paytelephone" name="paytelephone" value="${obj.tripinfo.paytelephone }"/>
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 邮箱</label>
							<input style="width:182px;height:30px;padding:0 0 0 10px;" type="text" id="paymail" name="paymail" value="${obj.tripinfo.paymail }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 与你的关系</label>
							<select id="payrelationwithyou" name="payrelationwithyou">
								<c:forEach items="${obj.payrelationshipenum }"
															var="map">
									<option value="${map.key }"
										${map.key==obj.tripinfo.payrelationwithyou?"selected":"" }>${map.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6" style="padding-top: 10px;">
							<label><span class="s">*</span> 费用支付人的地址是否与你的居住地址或者邮寄地址一样</label>
							
							<input autocomplete="new-password" type="radio"
							name="payaddressissamewithyou" class="payaddressissamewithyou" value="1" checked />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="payaddressissamewithyou"
							class="payaddressissamewithyou" value="2" />否
							
						</div>
					</div>
					
				</div>
				
				<div class="addressPay" style="padding-left: 18px;">
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 国家</label>
							
							<select name="paycountry" class=" select2" multiple="multiple" id="paycountry">
                            	<c:forEach items="${obj.gocountryfivelist }" var="country">
									<c:choose>
										<c:when test="${country.chinesename eq obj.tripinfo.paycountry }">
											<option value="${country.chinesename }" selected="selected">${country.chinesename }</option>
										</c:when>
										<c:otherwise>
											<option value="${country.chinesename }">${country.chinesename }</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
                            </select>
							
						</div>
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 省</label>
							
							<select name="employerprovince" class="form-control input-sm select2" multiple="multiple"  id="payprovince" >
		                   		<c:if test="${not empty obj.tripinfo.payprovince }">
									<option value="${obj.tripinfo.payprovince }" selected="selected">${obj.tripinfo.payprovince }</option>
								</c:if>
	                        </select>
							
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 市</label>
							
							<select name="paycity" class="form-control input-sm select2"  multiple="multiple"  id="paycity" >
		                   		<c:if test="${not empty obj.tripinfo.paycity }">
									<option value="${obj.tripinfo.paycity }" selected="selected">${obj.tripinfo.paycity }</option>
								</c:if>
	                        </select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 地址</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="payaddress" onchange="translateZhToEn(this,'payaddressen','')" name="payaddress" value="${obj.tripinfo.payaddress }"/>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> address</label>
							<input style="width:382px; height: 30px;padding:0 0 0 10px;" type="text" id="payaddressen" name="payaddressen" value="${obj.tripinfo.payaddressen }"/>
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
									<label><span class="s">*</span> 是否去过美国</label> <input autocomplete="new-password"
										type="radio" name="hasbeeninus" class="goUS" value="1" />是
									<input autocomplete="new-password" type="radio" style="margin-left: 20px;"
										name="hasbeeninus" class="goUS" value="2" checked />否
								</div>
							</div>

							<!--yes-->
							<div class="goUSInfo goUSYes">
								<div class="gotousInfo">
									<div class="goUS_CountryDiv">
										<div class="row">
											<div class="col-sm-3 groupInputInfo">
												<label><span class="s">*</span> 抵达日期</label> <input autocomplete="new-password"
													type="text" id="arrivedate" value="${obj.arrivedate }"
													name="arrivedate" class="datetimepickercss form-control"
													placeholder="">
											</div>
											<div class="col-sm-3 groupInputInfo stopDate"
												style="margin: 0;">
												<label><span class="s">*</span> 停留时间</label> <input autocomplete="new-password"
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
									<label><span class="s">*</span> 是否有美国驾照</label> <input autocomplete="new-password"
										type="radio" name="hasdriverlicense" class="license" value="1" />是
									<input autocomplete="new-password" type="radio" style="margin-left: 20px;"
										name="hasdriverlicense" class="license" value="2" checked />否
								</div>
								<div class="driverInfo elementHide">
									<div class="driverYes">
										<div class="goUS_drivers">
											<div class="groupcheckBoxInfo driverMain">
												<label><span class="s">*</span> 驾照号</label> <input autocomplete="new-password"
													style="width: 180px;"
													id="driverlicensenumber"
													value="${obj.driverinfo.driverlicensenumber }"
													name="driverlicensenumber" type="text">
												<%-- <c:if test="${driver.isknowdrivernumber == 1}">
														<input autocomplete="new-password"  id="isknowdrivernumber" class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input autocomplete="new-password"  id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" type="checkbox"/>
													</c:if> --%>
											</div>
											<div class="groupSelectInfo driverR paddingleft-15">
												<label style="margin-bottom: 10px;"><span class="s">*</span> 哪个州的驾照</label> <select
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
							<label><span class="s">*</span> 是否有美国签证</label> <input autocomplete="new-password"
								type="radio" name="isissuedvisa" class="visaUS" value="1" />是 <input autocomplete="new-password"
								type="radio" style="margin-left: 20px;" name="isissuedvisa"
								class="visaUS" value="2" checked />否
						</div>
						<div>
							<div class="dateIssue goUS_visa">
								<div class="row">
									<div class="col-sm-3 groupInputInfo lastVisaDate">
										<label><span class="s">*</span> 签发日期</label> <input autocomplete="new-password"
											id="issueddate" name="issueddate" value="${obj.issueddate}"
											class="datetimepickercss form-control" placeholder=""
											type="text" />
									</div>
									<div class="col-sm-4 groupcheckBoxInfo visaisnumber">
										<label><span class="s">*</span> 签证号码</label> <input autocomplete="new-password"
											style="width: 180px;"
											name="visanumber" maxlength="8" class="visanumber"
											value="${obj.tripinfo.visanumber }" type="text" />
										<!-- <input autocomplete="new-password" id="idknowvisanumber" onchange="AddSingle(this,'idknowvisanumberen')" name="idknowvisanumber" v-on:click="idknowvisanumberChange" value="visaInfo.previUSTripInfo.idknowvisanumber" type="checkbox"/> -->
									</div>
								</div>


								<div class="clear"></div>
								<div class="Alike groupRadioInfo paddingTop">
									<label><span class="s">*</span> 本次是否申请同类的签证</label> <input autocomplete="new-password"
										type="radio" name="isapplyingsametypevisa" value="1" />是 <input autocomplete="new-password"
										type="radio" style="margin-left: 20px;"
										name="isapplyingsametypevisa" value="2" checked />否
								</div>
								<div class="paddingTop groupRadioInfo">
									<label><span class="s">*</span> 是否采集过指纹</label> <input autocomplete="new-password"
										type="radio" name="istenprinted" value="1" />是 <input autocomplete="new-password"
										type="radio" style="margin-left: 20px;" name="istenprinted"
										value="2" checked />否
								</div>
								<div class="paddingTop">
									<div class="groupRadioInfo">
										<label><span class="s">*</span> 是否丢失过美国签证</label> <input autocomplete="new-password"
											type="radio" name="islost" class="lose" value="1" />是 <input autocomplete="new-password"
											type="radio" style="margin-left: 20px;" name="islost"
											class="lose" value="2" checked />否
									</div>
									<div class="lostExplain grouptextareaInfo paddingTop-9">
										<label><span class="s">*</span>丢失年份</label> <input autocomplete="new-password" maxlength="4"
											name="lostyear" style="padding-left: 10px; width: 382px !important;"
											class="areaInputPic" id='lostyear'
											value="${obj.tripinfo.lostyear }" />
									</div>
									<div class="lostExplain grouptextareaInfo paddingTop-9">
										<label><span class="s">*</span> 说明</label> <input autocomplete="new-password"
											name="lostexplain" style="padding-left: 10px; width: 382px !important;"
											class="areaInputPic" onchange="translateZhToEn(this,'lostexplainen','')" id='lostexplain'
											value="${obj.tripinfo.lostexplain }" />
									</div>
									<div class="lostExplain grouptextareaInfo paddingTop-9">
										<label><span class="s">*</span> Explain</label> <input autocomplete="new-password"
											name="lostexplainen" style="padding-left: 10px; width: 382px !important;"
											class="areaInputPic" id='lostexplainen'
											value="${obj.tripinfo.lostexplainen }" />
									</div>
								</div>
								<div class="clear"></div>
								<div class="paddingTop">
									<div>
										<div class="groupRadioInfo">
											<label><span class="s">*</span> 是否被取消过</label> <input autocomplete="new-password"
												type="radio" name="iscancelled" class="revoke" value="1" />是
											<input autocomplete="new-password" type="radio" style="margin-left: 20px;"
												name="iscancelled" class="revoke" value="2" checked />否
										</div>
										<div class="cancelExplain grouptextareaInfo paddingTop-9">
											<label><span class="s">*</span> 说明</label> <input autocomplete="new-password"
												name="cancelexplain" style="padding-left: 10px; width: 382px !important;"
												class="areaInputPic" onchange="translateZhToEn(this,'cancelexplainen','')" id='cancelexplain'
												value="${obj.tripinfo.cancelexplain }" />
										</div>
										<div class="cancelExplain grouptextareaInfo paddingTop-9">
											<label><span class="s">*</span> Explain</label> <input autocomplete="new-password"
												name="cancelexplainen" style="padding-left: 10px; width: 382px !important;"
												class="areaInputPic" onchange="translateZhToEn(this,'refusedexplainen','')" id='cancelexplainen'
												value="${obj.tripinfo.cancelexplainen }" />
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
						<label><span class="s">*</span> 是否被拒签过</label> <input autocomplete="new-password" type="radio"
							name="isrefused" class="refuse" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="isrefused"
							class="refuse" value="2" checked />否
					</div>
					<div class="refuseExplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 说明</label> <input autocomplete="new-password"
							name="refusedexplain" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'refusedexplainen','')" id='refusedexplain'
							value="${obj.tripinfo.refusedexplain }" />
					</div>
					<div class="refuseExplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Explain</label> <input autocomplete="new-password"
							name="refusedexplainen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='refusedexplainen'
							value="${obj.tripinfo.refusedexplainen }" />
							<%-- <input type="hidden" id="refusedexplainen" name="refusedexplainen" value="${obj.tripinfo.refusedexplainen }"/> --%>
					</div>
				</div>

				<div class="paddingBottom">
					<div class="groupRadioInfo drivingUS">
						<label><span class="s">*</span> 是否在申请美国移民</label> <input autocomplete="new-password"
							type="radio" class="isfiledimmigrantpetition"
							name="isfiledimmigrantpetition" value="1" />是 <input autocomplete="new-password"
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
						
					</div>
					<div class="row immigrantpetition_US">
						<div class=" youRelationship ">
						
							<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> 其他理由</label> <input autocomplete="new-password"
								style="width: 382px;height: 30px;padding-left: 10px" id="immigrantpetitionexplain" onchange="translateZhToEn(this,'immigrantpetitionexplainen','')"
								value="${obj.tripinfo.immigrantpetitionexplain }"
								name="immigrantpetitionexplain" type="text" placeholder="" />
						</div>
						</div>
						
					</div>
					<div class="row immigrantpetition_US">
						<div class=" youRelationship ">
						
							<div class="col-sm-3 youRelationship">
							<label><span class="s">*</span> Other reasons</label> <input autocomplete="new-password"
								style="width: 382px;height: 30px;padding-left: 10px" id="immigrantpetitionexplainen" value="${obj.tripinfo.immigrantpetitionexplainen }"
								name="immigrantpetitionexplainen" type="text" placeholder="" />
								<%-- <input id="immigrantpetitionexplainen" name="immigrantpetitionexplainen" type="hidden" value="${obj.tripinfo.immigrantpetitionexplainen }"/> --%>
						</div>
						
						
						
						</div>
						
					</div>
				</div>

				<div class="paddingBottom">
					<div style="height: 70px;">
						<div class="groupRadioInfo drivingUS" style="float: left;">
							<label><span class="s">*</span> 是否有出境记录</label> <input autocomplete="new-password"
								type="radio" class="istraveledanycountry"
								name="istraveledanycountry" value="1" />是 <input autocomplete="new-password" type="radio"
								class="istraveledanycountry" name="istraveledanycountry"
								style="margin-left: 20px;" value="2" checked />否
						</div>
						<div style="float: left; margin: 20px 0 0 185px;">
							<a class="save cjjlSave" style="display:none;">添加</a>
							<!-- <a class="save">添加</a> -->
						</div>
					</div>
					<div class="row saveOutboundContent">
						<div class="col-sm-3 youRelationship cjjlBox">
						
							<c:if test="${!empty obj.gocountry }">
									<c:forEach var="gocountry" items="${obj.gocountry }">
										<div class="travelCountry paddingTop groupInputInfo">
											<label>国家/地区</label>
											<div class="groupInputInfo groupSelectInfo" style="position: relative;">
												<input id="traveledcountry"  name="traveledcountry" value="${gocountry.traveledcountry}"/>
												<a class="cjjlRemove"  onclick="cjjlRemove(this)" style="display: inline-block; position: absolute; right: -74px;top: 7px; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;" >删除</a>
												<%-- <select name="traveledcountry" id='traveledcountry'  class="form-control input-sm select2" multiple="multiple" >
													<c:forEach items="${obj.gocountryfivelist }" var="country">
															<c:if test="${gocountry.traveledcountry != country.id}">
																<option value="${country.id }">${country.chinesename }</option>
															</c:if>
															<c:if test="${gocountry.traveledcountry == country.id}">
																<option value="${country.id }" selected="selected">${country.chinesename }</option>
															</c:if>
													</c:forEach>
												</select> --%>
											</div>
										</div>
									</c:forEach>
								</c:if>
								<c:if test="${empty obj.gocountry }">
									<div class="paddingTop travelCountry groupInputInfo">
										<label>国家/地区</label>
										<div class="groupInputInfo groupSelectInfo" style="position: relative;">
											<input autocomplete="new-password" name="traveledcountry" id="traveledcountry" type="text"/>
											<a class="cjjlRemove"  onclick="cjjlRemove(this)" style="display: inline-block; position: absolute; right: -74px;top: 7px; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;" >删除</a>
											<%-- <select name="traveledcountry" id='traveledcountry'  class="form-control input-sm select2" multiple="multiple" >
												<c:forEach items="${obj.gocountryfivelist }" var="country">
													<option value="${country.id }">${country.chinesename }</option>
												</c:forEach>
											</select> --%>
										</div>
									</div>
								</c:if>
							
						
						
							<!-- <div class="travelCountry paddingTop groupInputInfo">
								<label>国家/地区</label>
								<div class="groupInputInfo groupSelectInfo" style="position: relative;">
									<input autocomplete="new-password" name="traveledcountry" id="traveledcountry" value="" type="text" />
									<a class="cjjlRemove"  onclick="cjjlRemove(this)" style="display: inline-block; position: absolute; right: -74px;top: 7px; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;" >删除</a>
								</div>
							</div> -->
						</div>
						
					</div>
					<div style="height: 50px;"></div>
				</div>
				<!--以前的美国旅游信息END-->
				<!-- 横线分隔 -->
				<hr style="height:1px;background-color:black;margin-left:15px;" width="380px;" />
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否为任何慈善组织而工作</label> <input autocomplete="new-password" type="radio"
							name="isworkedcharitableorganization" class="isworkedcharitableorganization" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="isworkedcharitableorganization"
							class="isworkedcharitableorganization" value="2" checked />否
					</div>
					<div class="organizationName grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 机构名称</label> <input autocomplete="new-password"
							name="organizationname" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'organizationnameen','')" id='organizationname'
							value="${obj.organization.organizationname }" />
					</div>
					<div class="organizationName grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Organization name</label> <input autocomplete="new-password"
							name="organizationnameen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='organizationnameen'
							value="${obj.organization.organizationnameen }" />
					</div>
				</div>
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否曾服兵役</label> <input autocomplete="new-password" type="radio"
							name="hasservedinmilitary" class="hasservedinmilitary" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="hasservedinmilitary"
							class="hasservedinmilitary" value="2" checked />否
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 国家/地区</label> 
						
						<select id='militarycountry' style="padding-left: 10px; width: 382px !important;"  class="form-control input-sm select2" multiple="multiple" name="militarycountry">
                        
                        	<c:forEach items="${obj.gocountryfivelist }" var="country">
							<c:choose>
								<c:when test="${country.id eq obj.conscientious.militarycountry }">
									<option value="${country.id }" selected="selected">${country.chinesename }</option>
								</c:when>
								<c:otherwise>
									<option value="${country.id }">${country.chinesename }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
                        
                        </select>
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 军种</label> <input autocomplete="new-password"
							name="servicebranch" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'servicebranchen','')" id='servicebranch'
							value="${obj.conscientious.servicebranch }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Branch of Service</label> <input autocomplete="new-password"
							name="servicebranchen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='servicebranchen'
							value="${obj.conscientious.servicebranchen }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 级别</label> <input autocomplete="new-password"
							name="rank" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'ranken','')" id='rank'
							value="${obj.conscientious.rank }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Rank/Position</label> <input autocomplete="new-password"
							name="ranken" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='ranken'
							value="${obj.conscientious.ranken }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 特长</label> <input autocomplete="new-password"
							name="militaryspecialty" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'militaryspecialtyen','')" id='militaryspecialty'
							value="${obj.conscientious.militaryspecialty }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Military Specialty</label> <input autocomplete="new-password"
							name="militaryspecialtyen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='militaryspecialtyen'
							value="${obj.conscientious.militaryspecialtyen }" />
					</div>
					<div class="military grouptextareaInfo paddingTop-9">
						<div class="row">
							<div class="col-sm-3">
								<label><span class="s">*</span> 服兵役开始时间</label> <input autocomplete="new-password"
									name="servicestartdate" style="padding-left: 10px; width: 180px !important;"
									class="areaInputPic" id='servicestartdate'
									value="${obj.servicestartdate }" />
							</div>
							<div class="col-sm-3">
								<label><span class="s">*</span> 结束时间</label> <input autocomplete="new-password"
							name="serviceenddate" style="padding-left: 10px; width: 180px !important;"
							class="areaInputPic" id='serviceenddate'
							value="${obj.serviceenddate }" />
							</div>
						
						</div>
					</div>
				</div>
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否属于一个部落宗教</label> <input autocomplete="new-password" type="radio"
							name="isclan" class="isclan" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="isclan"
							class="isclan" value="2" checked />否
					</div>
					<div class="clanName grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 部落名称</label> <input autocomplete="new-password"
							name="clanname" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'clannameen','')" id='clanname'
							value="${obj.workinfo.clanname }" />
					</div>
					<div class="clanName grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Clan name</label> <input autocomplete="new-password"
							name="clannameen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='clannameen'
							value="${obj.workinfo.clannameen }" />
					</div>
				</div>
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 您是否拥有美国社会安全号</label> <input autocomplete="new-password" type="radio"
							name="issecuritynumberapply" class="issecuritynumberapply" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="issecuritynumberapply"
							class="issecuritynumberapply" value="2" checked />否
					</div>
					<div class="socialsecuritynumber grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 美国社会安全号</label> <input autocomplete="new-password"
							name="socialsecuritynumber" maxlength="9" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='socialsecuritynumber'
							value="${obj.basic.socialsecuritynumber }" />
					</div>
				</div>
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 您是否拥有美国纳税人身份号码</label> <input autocomplete="new-password" type="radio"
							name="istaxpayernumberapply" class="istaxpayernumberapply" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="istaxpayernumberapply"
							class="istaxpayernumberapply" value="2" checked />否
					</div>
					<div class="taxpayernumber grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 美国纳税人身份号码</label> <input autocomplete="new-password"
							name="taxpayernumber" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='taxpayernumber'
							value="${obj.basic.taxpayernumber }" />
					</div>
				</div>
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否有特殊技能</label> <input autocomplete="new-password" type="radio"
							name="hasspecializedskill" class="hasspecializedskill" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="hasspecializedskill"
							class="hasspecializedskill" value="2" checked />否
					</div>
					<div class="skillexplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 特殊技能说明</label> <input autocomplete="new-password"
							name="skillexplain" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'skillexplainen','')" id='skillexplain'
							value="${obj.workinfo.skillexplain }" />
					</div>
					<div class="skillexplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Explain</label> <input autocomplete="new-password"
							name="skillexplainen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='skillexplainen'
							value="${obj.workinfo.skillexplainen }" />
					</div>
				</div>
				
				<div class="paddingBottom">
					<div class="groupRadioInfo" style="margin-top: 10px;">
						<label><span class="s">*</span> 是否参与过准军事单位</label> <input autocomplete="new-password" type="radio"
							name="isservedinrebelgroup" class="isservedinrebelgroup" value="1" />是 <input autocomplete="new-password"
							type="radio" style="margin-left: 20px;" name="isservedinrebelgroup"
							class="isservedinrebelgroup" value="2" checked />否
					</div>
					<div class="paramilitaryunitexplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> 参与准军事单位说明</label> <input autocomplete="new-password"
							name="paramilitaryunitexplain" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" onchange="translateZhToEn(this,'paramilitaryunitexplainen','')" id='paramilitaryunitexplain'
							value="${obj.workinfo.paramilitaryunitexplain }" />
					</div>
					<div class="paramilitaryunitexplain grouptextareaInfo paddingTop-9">
						<label><span class="s">*</span> Explain</label> <input autocomplete="new-password"
							name="paramilitaryunitexplainen" style="padding-left: 10px; width: 382px !important;"
							class="areaInputPic" id='paramilitaryunitexplainen'
							value="${obj.workinfo.paramilitaryunitexplainen }" />
					</div>
				</div>
				
				
				
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
	<!--<script src="${base}/admin/bigCustomer/visa/visaInfoVue.js"></script>--><!-- 本页面 Vue加载页面内容 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfo.js"></script><!-- 本页面 开关交互 js --> --%>
	<script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js?v=<%=System.currentTimeMillis() %>"></script> 
<!-- 本页面 初始化时间插件 js -->
<script
	src="${base}/admin/bigCustomer/updateTravelinfo.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${base}/references/common/js/pinyin.js?v=<%=System.currentTimeMillis() %>"></script>
<!-- 本页面  js -->
<script type="text/javascript">
		

			
	
		//是否与其他人同行
		var istravelwithother = '${obj.travelwithother}';
		$("input[name='istravelwithother'][value='" + istravelwithother + "']").attr("checked", 'checked');
        if (istravelwithother == 1) {
        	$(".teamnamefalse").show();
        	$(".companysave").show();
		} else {
			$(".teamnamefalse").hide();
			$(".companysave").hide();
		}
        
        //费用支付人
        var payer = '${obj.tripinfo.costpayer}';
        if(payer == 2){
        	$(".otherPay").show();
        	var payaddressissamewithyou = '${obj.tripinfo.payaddressissamewithyou}';
        	$("input[name='payaddressissamewithyou'][value='" + payaddressissamewithyou + "']").attr("checked", 'checked');
        	if(payaddressissamewithyou == 1){
        		$(".addressPay").hide();
        	}else{
        		$(".addressPay").show();
        	}
        	
        	$(".companyPay").hide();
        	
        }else if(payer == 3){
        	$(".companyPay").show();
        	$(".addressPay").show();
        	$(".otherPay").hide();
        }else{
        	$(".companyPay").hide();
        	$(".addressPay").hide();
        	$(".otherPay").hide();
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
		if(islost == 1){
			$(".lostExplain").show();
		}else{
			$(".lostExplain").hide();
		}
        //是否被取消过
        var iscancelled = '${obj.tripinfo.iscancelled}';
		$("input[name='iscancelled'][value='" + iscancelled + "']").attr("checked", 'checked');
		if(iscancelled == 1){
			$(".cancelExplain").show();
		}else{
			$(".cancelExplain").hide();
		}
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
			$('.cjjlSave').show();
		} else {
			$(".travelCountry").hide();
			$('.cjjlSave').hide();
		}

		$('.istraveledanycountry').change(function() {
			console.log($(this).val());

			if ($(this).val() == 1) {
				$('.cjjlSave').show();
			} else {
				$('.cjjlSave').hide();
			}
		});
		//是否工作于慈善组织
        var isworkedcharitableorganization = '${obj.isworkedcharitableorganization}';
		$("input[name='isworkedcharitableorganization'][value='" + isworkedcharitableorganization + "']").attr("checked", 'checked');
		if (isworkedcharitableorganization == 1) {
			$(".organizationName").show();
		} else {
			$(".organizationName").hide();
		}
		//是否服兵役
        var hasservedinmilitary = '${obj.hasservedinmilitary}';
		$("input[name='hasservedinmilitary'][value='" + hasservedinmilitary + "']").attr("checked", 'checked');
		if (hasservedinmilitary == 1) {
			$(".military").show();
		} else {
			$(".military").hide();
		}
		
		var isclan = '${obj.workinfo.isclan}';
		$("input[name='isclan'][value='" + isclan + "']").attr("checked", 'checked');
		if (isclan == 1) {
			$(".clanName").show();
		} else {
			$(".clanName").hide();
		}
		
		var issecuritynumberapply = '${obj.basic.issecuritynumberapply}';
		$("input[name='issecuritynumberapply'][value='" + issecuritynumberapply + "']").attr("checked", 'checked');
		if (issecuritynumberapply == 1) {
			$(".socialsecuritynumber").show();
		} else {
			$(".socialsecuritynumber").hide();
		}
		
		var istaxpayernumberapply = '${obj.basic.istaxpayernumberapply}';
		$("input[name='istaxpayernumberapply'][value='" + istaxpayernumberapply + "']").attr("checked", 'checked');
		if (istaxpayernumberapply == 1) {
			$(".taxpayernumber").show();
		} else {
			$(".taxpayernumber").hide();
		}
		
		var hasspecializedskill = '${obj.workinfo.hasspecializedskill}';
		$("input[name='hasspecializedskill'][value='" + hasspecializedskill + "']").attr("checked", 'checked');
		if (hasspecializedskill == 1) {
			$(".skillexplain").show();
		} else {
			$(".skillexplain").hide();
		}
		
		var isservedinrebelgroup = '${obj.workinfo.isservedinrebelgroup}';
		$("input[name='isservedinrebelgroup'][value='" + isservedinrebelgroup + "']").attr("checked", 'checked');
		if (isservedinrebelgroup == 1) {
			$(".paramilitaryunitexplain").show();
		} else {
			$(".paramilitaryunitexplain").hide();
		}
		
		//跳转到基本信息页
		function workInfoBtn(){
			save(2);
		}
		
		function cjjlRemove(_this) {
			console.log($('.cjjlBox').length);
			if ($('.travelCountry ').length < 2) return 0;
			$(_this).parent().parent().remove();
		}
		
		let $temp = '<div class="travelCountry paddingTop groupInputInfo">' +
						'<label>国家/地区</label>' +
						'<div class="groupInputInfo groupSelectInfo" style="position: relative;">' +
							'<input autocomplete="new-password" name="traveledcountry" id="traveledcountry" value="" type="text" />' +
							'<a class="cjjlRemove" onclick="cjjlRemove(this)" style="display: inline-block; position: absolute; right: -74px;top: 7px; border-radius: 6px; font-size: 12px; text-decoration: none; padding: 3px 15px; color: #FFFFFF; background: #ca1818; cursor: pointer;" >删除</a>' +
					'</div></div>';

		$('.cjjlSave').click(ev => {
			console.log('...emit');
			$('.cjjlBox').append($temp);
		});
        
        /** 2018_08_20 */
        let removeFun = (self, len) => {
            if (len < 2) return 0;
            $(self).parent().parent().remove();
        };

        let removeTongXingRen = self => removeFun(self, $('.removeTongXingRen').length);
        let removeDiDaDate = self => removeFun(self, $('.removeDiDaDate').length);
        let removeChuJingJiLu = self => removeFun(self, $('.removeChuJingJiLu').length);
	</script>
</html>

