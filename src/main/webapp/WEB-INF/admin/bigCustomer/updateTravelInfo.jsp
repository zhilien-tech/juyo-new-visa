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
            .section,.English{
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
				<a class="saveVisa" onclick="save(1)">保存</a>
				<a class="cancelVisa" onclick="closeWindow()">取消</a>
			</div>
		</div>
		<!-- 左右按钮 -->
		<a id="toPassport" class="leftNav" onclick="baseInfoBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第五步</i>
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
				<div class="companyMain" style="margin-top: 10px;">
                    <div style="overflow: hidden;">
                        <div class="companyMainInfo groupRadioInfo" style="float: left;">
                            <label><span class="s">*</span> 是否与其他人同行</label>
                            <input type="radio" class="companyInfo" v-model="visaInfo.travelCompanionInfo.istravelwithother" @change="istravelwithother()" value="1" />是
                            <input type="radio" class="companyInfo" style="margin-left: 20px;" v-model="visaInfo.travelCompanionInfo.istravelwithother" @change="istravelwithother()" value="2" checked/>否
                        </div>
                        <div style="float: left; margin:20px 0 0 185px;"><a class="save companysave">添加</a></div>
                    </div>
					<!--yes-->
					<div class="teamture elementHide">
						<!--第二部分No-->
						<div class="teamnamefalse groupInputInfo">
							<div>
							<c:if test="${!empty obj.companionList }">
								<c:forEach var="companion" items="${obj.companionList }">
                                        <div class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV">
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <label><span class="s">*</span>同行人姓</label>
                                                    <input id="firstname" name="firstname" onchange="addSegmentsTranslateZhToPinYin(this,'firstnameen','')" type="text" placeholder="同行人姓" />
                                                </div>
                                                <div class="col-sm-3">
                                                    <label><span class="s">*</span> 同行人名</label>
                                                    <input id="lastname" name="lastname" onchange="addSegmentsTranslateZhToPinYin(this,'lastnameen','')" type="text" placeholder="同行人名" />
                                                </div>
                                                <div class="col-sm-3">
                                                    <label><span class="s">*</span> Surnames</label>
                                                    <input id="firstnameen" class="firstnameen" name="firstnameen" type="text" placeholder="Surnames" />
                                                </div>
                                                <div class="col-sm-3">
                                                    <label><span class="s">*</span> Given Names</label>
                                                    <input id="lastnameen" class="lastnameen" name="lastnameen" type="text" placeholder="Given Names" />
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-sm-3 youRelationship ">
                                                    <label><span class="s">*</span> 与您的关系</label>
                                                    <select id="relationship" class="relationshipSelect" onchange="addSegmentsTranslateZhToEn(this,'relationshipen','')" name="relationship">
                                                        <option value="0">请选择</option>
                                                        <c:forEach items="${obj.TravelCompanionRelationshipEnum }" var="map">
                                                            <option value="${map.key }">${map.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-sm-3 youRelationship" style="display: none;">
                                                    <label><span class="s">*</span> 说明</label>
                                                    <input style="height: 34px;" id="" name="" type="text" placeholder="" />
                                                </div>
                                            </div>
                                            <div><a 
                                                class="removeTongXingRen"
                                                data-index="0"
                                                style=" 
                                                margin-left: 325px;
                                                margin-top: 10px; 
                                                display: inline-block;
                                                border-radius: 6px;
                                                font-size: 12px;
                                                text-decoration: none;
                                                padding: 3px 15px;
                                                color: #FFFFFF;
                                                background: #ca1818;
                                                cursor: pointer;" 
                                                onclick="removeTongXingRen(this)">删除</a>
                                            </div>
                                        </div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.companionList }">
                                    <div class="teamnamefalseDiv teamnamefalseDivzh teamaddfalse teamnamefalseIndexDIV">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label><span class="s">*</span>同行人姓</label>
                                                <input id="firstname" name="firstname" onchange="addSegmentsTranslateZhToPinYin(this,'firstnameen','')" type="text" placeholder="同行人姓" />
                                            </div>
                                            <div class="col-sm-3">
                                                <label><span class="s">*</span> 同行人名</label>
                                                <input id="lastname" name="lastname" onchange="addSegmentsTranslateZhToPinYin(this,'lastnameen','')" type="text" placeholder="同行人名" />
                                            </div>
                                            <div class="col-sm-3">
                                                <label><span class="s">*</span> Surnames</label>
                                                <input id="firstnameen" class="firstnameen" name="firstnameen" type="text" placeholder="Surnames" />
                                            </div>
                                            <div class="col-sm-3">
                                                <label><span class="s">*</span> Given Names</label>
                                                <input id="lastnameen" class="lastnameen" name="lastnameen" type="text" placeholder="Given Names" />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-3 youRelationship ">
                                                <label><span class="s">*</span> 与您的关系</label>
                                                <select id="relationship" class="relationshipSelect" onchange="addSegmentsTranslateZhToEn(this,'relationshipen','')" name="relationship">
                                                    <option value="0">请选择</option>
                                                    <c:forEach items="${obj.TravelCompanionRelationshipEnum }" var="map">
                                                        <option value="${map.key }">${map.value }</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-sm-3 youRelationship" style="display: none;">
                                                <label><span class="s">*</span> 说明</label>
                                                <input style="height: 34px;" id="" name="" type="text" placeholder="" />
                                            </div>
                                        </div>
                                        <div><a 
                                            class="removeTongXingRen"
                                            data-index="0"
                                            style=" 
                                            margin-left: 325px;
                                            margin-top: 10px; 
                                            display: inline-block;
                                            border-radius: 6px;
                                            font-size: 12px;
                                            text-decoration: none;
                                            padding: 3px 15px;
                                            color: #FFFFFF;
                                            background: #ca1818;
                                            cursor: pointer;" 
                                            onclick="removeTongXingRen(this)">删除</a>
                                        </div>
                                    </div>
							</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
            <!--旅伴信息END-->
            <div class="companyInfoModule" style="padding-left: 18px;">
                <div class="row">
                    <div class="col-sm-3 youRelationship">
                        <label><span class="s">*</span> 费用支付人</label>
                        <select id="" class="relationshipSelect" onchange="" name="">
                            <option value="0">自己</option>
                            <option value="1">其他人</option>
                            <option value="2">公司组织</option>
                        </select>
                    </div>
                </div>
            </div>
            
			<!--以前的美国旅游信息-->
			<div class="beforeTourism">
				<!--是否去过美国-->
				<div class="goUSModule">
					<div class="goUSMain">
                        <div style="height: 80px;margin-top: 20px;">
                            <div class="groupRadioInfo goUSPad paddingbottom-14" style="float: left;">
                                <label><span class="s">*</span> 是否去过美国</label>
                                <input 
                                    type="radio" 
                                    id="hasbeeninus" 
                                    name="hasbeeninus" 
                                    @change="hasbeeninus()" 
                                    class="goUS" 
                                    value="1" 
                                />是
                                <input 
                                    type="radio" 
                                    style="margin-left: 20px;" 
                                    id="hasbeeninus" 
                                    name="hasbeeninus"  
                                    @change="hasbeeninus()" 
                                    class="goUS" 
                                    value="2" 
                                    checked
                                />否
                            </div>
                            <div style="float: left;margin: 20px 0px 0px 212px;">
                                <a class="save beforesave">添加</a>
                            </div>
                        </div>
						
						<!--yes-->
						<div class="goUSInfo goUSYes">
							<div class="gotousInfo">
								<c:if test="${!empty obj.gousList }">
									<c:forEach var="gous" items="${obj.gousList }">
                                            <div class="goUS_CountryDiv">
                                                <div class="row">
                                                    <div class="col-sm-3 groupInputInfo">
                                                        <label><span class="s">*</span> 抵达日期</label>
                                                        <input type="text" id="arrivedate" onchange="addSegmentsTranslateZhToEn(this,'arrivedateen','')" name="arrivedate" class="datetimepickercss form-control" placeholder="日/月/年">
                                                    </div>
                                                    <div class="col-sm-3 groupInputInfo stopDate" style="margin: 0;">
                                                        <label><span class="s">*</span> 停留时间</label>
                                                        <input id="staydays" style="width:50%;margin: 0;"  name="staydays" onchange="addSegmentsTranslateZhToEn(this,'staydaysen','')" type="text" />
                                                        <select id="dateunit" style="width:50%!important;margin: 0;" onchange="addSegmentsTranslateZhToEn(this,'dateuniten','')" name="dateunit">
                                                            <option value="0">请选择</option>
                                                            <c:forEach items="${obj.TimeUnitStatusEnum }" var="map">
                                                                <option value="${map.key }">${map.value }</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div><a 
                                                    class="removeDiDaDate"
                                                    data-index="0"
                                                    style=" 
                                                    margin-left: 325px;
                                                    margin-top: 10px; 
                                                    display: inline-block;
                                                    border-radius: 6px;
                                                    font-size: 12px;
                                                    text-decoration: none;
                                                    padding: 3px 15px;
                                                    color: #FFFFFF;
                                                    background: #ca1818;
                                                    cursor: pointer;" 
                                                    onclick="removeDiDaDate(this)">删除</a>
                                                </div>
                                            </div>
                                            
									</c:forEach>
								</c:if>
								<c:if test="${empty obj.gousList }">
                                        <div class="goUS_CountryDiv">
                                            <div class="row">
                                                <div class="col-sm-3 groupInputInfo">
                                                    <label><span class="s">*</span> 抵达日期123</label>
                                                    <input type="text" id="arrivedate" onchange="addSegmentsTranslateZhToEn(this,'arrivedateen','')" name="arrivedate" class="datetimepickercss form-control" placeholder="日/月/年">
                                                </div>
                                                <div class="col-sm-3 groupInputInfo stopDate" style="margin: 0;">
                                                    <label><span class="s">*</span> 停留时间</label>
                                                    <input id="staydays" style="width:50%;margin: 0;"  name="staydays" onchange="addSegmentsTranslateZhToEn(this,'staydaysen','')" type="text" />
                                                    <select id="dateunit" style="width:50%!important;margin: 0;" onchange="addSegmentsTranslateZhToEn(this,'dateuniten','')" name="dateunit">
                                                        <option value="0">请选择</option>
                                                        <c:forEach items="${obj.TimeUnitStatusEnum }" var="map">
                                                            <option value="${map.key }">${map.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div><a 
                                                class="removeDiDaDate"
                                                data-index="0"
                                                style=" 
                                                margin-left: 325px;
                                                margin-top: 10px; 
                                                display: inline-block;
                                                border-radius: 6px;
                                                font-size: 12px;
                                                text-decoration: none;
                                                padding: 3px 15px;
                                                color: #FFFFFF;
                                                background: #ca1818;
                                                cursor: pointer;" 
                                                onclick="removeDiDaDate(this)">删除</a>
                                            </div>
                                        </div>
								</c:if>
                            </div>
                           
							<div class="groupRadioInfo drivingUS">
								<label><span class="s">*</span> 是否有美国驾照</label>
								<input type="radio" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" @change="hasdriverlicense()" class="license" value="1" />是
								<input type="radio" style="margin-left: 20px;" name="hasdriverlicense" v-model="visaInfo.previUSTripInfo.hasdriverlicense" @change="hasdriverlicense()" class="license" value="2" checked />否
							</div>
							<div class="driverInfo elementHide">
								<div class="driverYes">
									<c:if test="${!empty obj.driverList }">
										<c:forEach var="driver" items="${obj.driverList }">
											<div class="goUS_drivers">
												<div class="groupcheckBoxInfo driverMain">
                                                    <label><span class="s">*</span> 驾照号</label>
													<input id="driverlicensenumber" value="${driver.driverlicensenumber }"  onchange="addSegmentsTranslateZhToEn(this,'driverlicensenumberen','')" name="driverlicensenumber" type="text" >
													<c:if test="${driver.isknowdrivernumber == 1}">
														<input id="isknowdrivernumber" class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" checked="checked" type="checkbox"/>
													</c:if>
													<c:if test="${driver.isknowdrivernumber != 1}">
														<input id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" value="${driver.isknowdrivernumber }" name="isknowdrivernumber" type="checkbox"/>
													</c:if>
												</div>
												<div class="groupSelectInfo driverR paddingleft-15">
                                                    <label><span class="s">*</span> 哪个州的驾照</label>
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
												<label><span class="s">*</span> 驾照号</label>
												<input id="driverlicensenumber" name="driverlicensenumber" onchange="addSegmentsTranslateZhToEn(this,'driverlicensenumberen','')" type="text" >
												<input id="isknowdrivernumber"  class="isknowdrivernumber" onchange="AddSegment(this,'isknowdrivernumberen')" name="isknowdrivernumber" type="checkbox"/>
											</div>
											<div class="groupSelectInfo driverR paddingleft-15">
												<label><span class="s">*</span> 哪个州的驾照</label>
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
							</div>
							
							
						</div>
					</div>
				</div>
			</div>
			<!--是否有美国签证-->
			<div class="visaUSMain">
				<div>
					<div class="groupRadioInfo" style="clear: both;margin-top: 10px;">
						<label><span class="s">*</span> 是否有美国签证</label>
						<input type="radio" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" @change="isissuedvisa()" class="visaUS" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="isissuedvisa" v-model="visaInfo.previUSTripInfo.isissuedvisa" @change="isissuedvisa()" class="visaUS" value="2" checked />否
					</div>
					<div>
						<div class="dateIssue goUS_visa">
                            <div class="row">
                                <div class="col-sm-3 groupInputInfo lastVisaDate">
                                    <label><span class="s">*</span> 签发日期</label>
                                    <input id="issueddate" name="issueddate" onchange="translateZhToEn(this,'issueddateen','')" value="${obj.previUSTripInfo_issueddate}" class="datetimepickercss form-control" placeholder="日/月/年" type="text"/>
                                </div>
                                <div class="col-sm-4 groupcheckBoxInfo visaisnumber">
                                    <label><span class="s">*</span> 签证号码</label>
                                    <input name="visanumber" class="visanumber" @change="hasvisanumber()" v-model="visaInfo.previUSTripInfo.visanumber" onchange="translateZhToEnVueVisanumber(this,'visanumberen','')" type="text" />
                                    <input id="idknowvisanumber" onchange="AddSingle(this,'idknowvisanumberen')" name="idknowvisanumber" v-on:click="idknowvisanumberChange" v-model="visaInfo.previUSTripInfo.idknowvisanumber" type="checkbox"/>
                                </div>
                            </div>
                            
                            
							<div class="clear"></div>
							<div class="Alike groupRadioInfo paddingTop">
								<label><span class="s">*</span> 本次是否申请同类的签证</label>
								<input type="radio" name="isapplyingsametypevisa" @change="isapplyingsametypevisa()" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisa" value="1" />是
								<input type="radio" style="margin-left: 20px;" name="isapplyingsametypevisa" @change="isapplyingsametypevisa()" v-model="visaInfo.previUSTripInfo.isapplyingsametypevisa" value="2" checked />否
							</div>
							<div class="paddingTop groupRadioInfo">
                                <label><span class="s">*</span> 是否采集过指纹</label>
								<input type="radio" name="istenprinted" @change="istenprinted()" v-model="visaInfo.previUSTripInfo.istenprinted"  value="1"/>是
								<input type="radio" style="margin-left: 20px;" name="istenprinted" @change="istenprinted()" v-model="visaInfo.previUSTripInfo.istenprinted"  value="2" checked />否
							</div>
							<div class="paddingTop">
								<div class="groupRadioInfo">
                                    <label><span class="s">*</span> 是否丢失过美国签证</label>
									<input type="radio" name="islost" v-model="visaInfo.previUSTripInfo.islost" @change="islost()" class="lose" value="1" />是
									<input type="radio" style="margin-left: 20px;" name="islost" v-model="visaInfo.previUSTripInfo.islost" v-on:click="visaNotLost" @change="islost()" class="lose" value="2" checked />否
								</div>
							</div>
							<div class="clear"></div>
							<div class="paddingTop">
								<div>
									<div class="groupRadioInfo">
										<label><span class="s">*</span> 是否被取消过</label>
										<input type="radio" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" @change="iscancelled()" class="revoke" value="1" />是
										<input type="radio" style="margin-left: 20px;" name="iscancelled" v-model="visaInfo.previUSTripInfo.iscancelled" v-on:click="visaNotCancel" @change="iscancelled()" class="revoke" value="2" checked />否
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
				<div class="groupRadioInfo" style="margin-top: 10px;">
					<label><span class="s">*</span> 是否被拒签过</label>
					<input type="radio" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" @change="isrefused()" class="refuse" value="1" />是
					<input type="radio" style="margin-left: 20px;" name="isrefused" v-model="visaInfo.previUSTripInfo.isrefused" @change="isrefused()" v-on:click="visaNotRefused" class="refuse" value="2" checked />否
				</div>
				<div class="refuseExplain grouptextareaInfo paddingTop-9">
                    <label><span class="s">*</span> 说明</label>
					<input name="refusedexplain" style="width: 182px!important;" class="areaInputPic" id='refusedexplain' @change="refusedexplainen('refusedexplain','refusedexplainen','visaInfo.previUSTripInfo.refusedexplainen')" v-model="visaInfo.previUSTripInfo.refusedexplain" />
				</div>
			</div>
		
            <div class="paddingBottom">
                 <div class="groupRadioInfo drivingUS">
                    <label><span class="s">*</span> 是否在申请美国移民</label>
                    <input type="radio" value="1" />是
                    <input type="radio" style="margin-left: 20px;" value="2" checked />否
                </div>
               <div class="row">
                    <div class="col-sm-3 youRelationship ">
                        <label><span class="s">*</span> 申请理由</label>
                        <select id="" class="relationshipSelect" style="margin: 0;">
                            <option value="0">请选择</option>
                            
                        </select>
                    </div>
                    <div class="col-sm-3 youRelationship">
                        <label><span class="s">*</span> 其他理由</label>
                        <input style="height: 34px;" id="" name="" type="text" placeholder="" />
                    </div>
               </div>
            </div>

            <div class="paddingBottom">
                <div style="height: 70px;">
                    <div class="groupRadioInfo drivingUS" style="float: left;">
                        <label><span class="s">*</span> 是否有出境记录</label>
                        <input type="radio" value="1" />是
                        <input type="radio" style="margin-left: 20px;" value="2" checked />否
                    </div>
                    <div style="float: left; margin:20px 0 0 185px;"><a class="save saveOutbound">添加</a></div>
                </div>
                <div class="row saveOutboundContent">
                    <div class="col-sm-3 youRelationship ">
                        <label><span class="s">*</span> 国家名</label>
                        <select id="" class="relationshipSelect" style="margin: 0;">
                            <option value="0">请选择</option>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <a 
                            class="removeChuJingJiLu"
                            style=" 
                            margin-top: 40px; 
                            display: inline-block;
                            border-radius: 6px;
                            font-size: 12px;
                            text-decoration: none;
                            padding: 3px 15px;
                            color: #FFFFFF;
                            background: #ca1818;
                            cursor: pointer;" 
                            onclick="removeChuJingJiLu(this)">删除</a>
                    </div>
                </div>
            </div>
            <!--以前的美国旅游信息END-->
            <div style="height: 50px;"></div>
		</div>
	</div>
	</body>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId   = '${obj.staffId}';
		var isDisable = '${obj.isDisable}';
		var flag      = '${obj.flag}';
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
				window.location.href = '/admin/bigCustomer/updateBaseInfo.html?staffId='+staffId+'&isDisable='+isDisable+'&flag='+flag;
			}else{
				//保存签证信息
				save(2);
			}
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

