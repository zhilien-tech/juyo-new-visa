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
            .vhide{visibility: hidden;}
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
		.rightNav{
				position: fixed;
				top: 15px;
				right: 0;
				z-index: 999;
				width: 40px;
				height: 100%;
				cursor: pointer;
			}

			.rightNav span{
				width: 24px;
				height: 24px;
				position: absolute;
				top: 50%;
				border-left: 4px solid #999;
				border-bottom: 4px solid #999;
				-webkit-transform: translate(0,-50%) rotate(-135deg);
				transform: translate(0,-50%) rotate(-135deg);
			}
		
    </style>
</head>
<body>
	<div class="head">
        <div class="title"><span>工作教育信息</span></div>
        <div class="btnRight">
            <a class="saveVisa" onclick="save(1)">保存</a>
            <a class="cancelVisa" onclick="closeWindow()">取消</a>
        </div>
    </div>
    <!-- 左右按钮 -->
    <a id="toPassport" class="leftNav" onclick="baseInfoBtn();">
        <i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第四步</i>
        <span></span>
	</a>
	<a id="" class="rightNav" onclick="">
		<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第六步</i>
		<span></span>
	</a>
	<div class="topHide"></div>
	<div id="section">
		<div id="wrapper" v-cloak class="section">
			<div class="dislogHide"></div>
			<!--工作/教育/培训信息-->
			<div class="experience paddingTop">
				<div class="titleInfo">职业信息</div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<label><span class="s">*</span>主要职业</label>
					<select id="occupation" @change="jobprofession('occupation','occupationen','visaInfo.workEducationInfo.occupationen')" name="occupation" v-model="visaInfo.workEducationInfo.occupation">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaCareersEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="paddingTop elementHide jobEduLearningInfoDiv">
					<div class="groupInputInfo draBig">
                        <label><span class="s">*</span>目前的工作单位名称</label>
						<input name="unitname" id="unitname" @change="jobprofessionunit('unitname','unitnameen','visaInfo.workEducationInfo.unitnameen')" v-model="visaInfo.workEducationInfo.unitname" type="text" />
                    </div>
                    <div class="clear"></div>
                    <div class="paddingLeft groupInputInfo">
                        <label><span class="s">*</span>电话号码</label>
                        <input name="jobtelephone" v-model="visaInfo.workEducationInfo.telephone" id="jobtelphone" @change="jobprofessiontelphone('jobtelphone','jobtelphoneen','visaInfo.workEducationInfo.telephoneen')" type="text" />
                    </div>
					<div class="paddingRight groupSelectInfo" >
                        <label><span class="s">*</span>工作国家</label>
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
                    <div class="clear"></div>
                    <div class="paddingLeft groupcheckBoxInfo">
                        <label><span class="s">*</span>单位地址（省）</label>
                        <input name="province" v-model="visaInfo.workEducationInfo.province" id="jobprovince" @change="jobprofessionprovince('jobprovince','jobprovinceen','visaInfo.workEducationInfo.provinceen')" type="text"/>
                        <input name="isprovinceapply" id="isprovinceapplywork" onchange="AddSingle(this,'isprovinceapplyworken')" @click="isprovinceapplywork" v-model="visaInfo.workEducationInfo.isprovinceapply" type="checkbox"/>
                    </div>
                    <div class="paddingRight groupInputInfo">
                        <label><span class="s">*</span>单位地址（市）</label>
                        <input name="jobcity" v-model="visaInfo.workEducationInfo.city" id="jobcity" @change="jobprofessioncity('jobcity','jobcityen','visaInfo.workEducationInfo.cityen')" type="text"/>
                    </div>
                    <div class="clear"></div>
					<div class="groupInputInfo draBig marginLS">
                        <label><span class="s">*</span>详细地址</label>
						<input name="address" id="jobaddress" @change="jobprofessionaddress('jobaddress','jobaddressen','visaInfo.workEducationInfo.addressen')" v-model="visaInfo.workEducationInfo.address" type="text" />
					</div>
					<div class="clear"></div>
                    <div class="paddingLeft groupInputInfo">
                        <label><span class="s">*</span>入职日期</label>
                        <input id="workstartdate" onchange="translateZhToEn(this,'workstartdateen','')" name="workstartdate" value="${obj.workstartdate}" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
                    </div>
                    <div class=" paddingRight groupcheckBoxInfo">
                        <label><span class="s">*</span>职位</label>
                        <input name="zipcode" v-model="visaInfo.workEducationInfo.zipcode" id="jobzipcode" @change="jobprofessioncode('jobzipcode','jobzipcodeen','visaInfo.workEducationInfo.zipcodeen')" type="text" />
                        <input name="jobcodechecked" id="jobcodechecked" onchange="AddSingle(this,'jobcodecheckeden')" @click="isjobcodechecked" v-model="visaInfo.workEducationInfo.iszipcodeapply" type="checkbox" /><!-- iszipcodeapply -->
                    </div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo" >
                        <label><span class="s">*</span>当前月收入</label>
						<input name="salary" v-model="visaInfo.workEducationInfo.salary" id="salary" type="text" @change="jobprofessionsalary('salary','salaryen','visaInfo.workEducationInfo.salaryen')" onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"/>
						<input name="moneychecked" id="moneychecked" onchange="AddSingle(this,'moneycheckeden')" @click="ismoneychecked" v-model="visaInfo.workEducationInfo.issalaryapply" type="checkbox" />
					</div>
					<div class="clear"></div>
					<div class="grouptextareaInfo groupPM">
                        <label><span class="s">*</span>简述你的职责</label>
						<input name="jobduty" class='areaInputPic' id="jobduty" @change="jobprofessionduty('jobduty','jobdutyen','visaInfo.workEducationInfo.dutyen')" v-model="visaInfo.workEducationInfo.duty" />
					</div>
				</div>
				<div class="grouptextareaInfo elementHide jobEduLearningInfoTextarea"></div>
			</div>
			<!--以前-->
			<div>
				<div class="paddingTop padding-left">
					<div>
						<div class="groupRadioInfo">
                            <label><span class="s">*</span>是否有上份工作</label>
							<input 
								type="radio" 
								name="isemployed" 
								v-model="visaInfo.workEducationInfo.isemployed" 
								@change="isemployed()" 
								class="beforeWork" 
								value="1" 
							/>是
							<input 
								type="radio" 
								style="margin-left: 20px;" 
								name="isemployed" 
								v-model="visaInfo.workEducationInfo.isemployed" 
								@change="isemployed()" 
								class="beforeWork" 
								value="2" 
								checked
							/>否
						</div>
						<!--yes-->
						<div class="beforeWorkInfo elementHide">
						  <div class="beforeWorkYes">
							<c:if test="${!empty obj.beforeWorkList }">
								<c:forEach var="beforeWork" items="${obj.beforeWorkList }">
									<div class="workBeforeInfosDiv">
										<div class="leftNo marginLS groupInputInfo" >
                                            <label><span class="s">*</span>单位名称</label>
											<input name="employername" style="width: 100%" onchange="addSegmentsTranslateZhToPinYin(this,'employernameen','')" value="${beforeWork.employername }" type="text" />
										</div>
                                      
                                        <div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">

                                            <label><span class="s">*</span>电话号码</label>
											<input name="employertelephone" onchange="addSegmentsTranslateZhToEn(this,'employertelephoneen','')" value="${beforeWork.employertelephone }" type="text" />
                                        </div>
                                        
                                        <div class="paddingRight groupSelectInfo">
                                            <label><span class="s">*</span>工作国家</label>
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
										
									
										
										<div class="paddingLeft leftNo groupInputInfo">
                                            <label><span class="s">*</span>单位地址（省）</label>
											<input name="employerprovince" onchange="addSegmentsTranslateZhToEn(this,'employerprovinceen','')" value="${beforeWork.employerprovince }" type="text" />
										</div>
										<div class="paddingRight leftNo groupInputInfo" >
                                            <label><span class="s">*</span>单位地址（市）</label>
											<input name="employercity" id="employercitybefore" onchange="addSegmentsTranslateZhToEn(this,'employercityen','')" value="${beforeWork.employercity }" type="text" />
											
										</div>
                                        <div class="clear"></div>
                                        
                                        <div class="draBig leftNo marginLS groupInputInfo">
                                            <label><span class="s">*</span>详细地址</label>
                                            <input name="employeraddress" onchange="addSegmentsTranslateZhToEn(this,'employeraddressen','')" value="${beforeWork.employeraddress }" type="text" />
                                        </div>

                                        <div class="paddingLeft leftNo groupInputInfo" >
                                            <label><span class="s">*</span>入职时间</label>
                                            <input id="employstartdate" onchange="addSegmentsTranslateZhToEn(this,'employstartdateen','')" name="employstartdate" value="<fmt:formatDate value="${beforeWork.employstartdate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
                                        </div>
                                        <div class="paddingRight groupInputInfo">

                                            <label><span class="s">*</span>离职时间</label>
                                            <input id="employenddate" onchange="addSegmentsTranslateZhToEn(this,'employenddateen','')" name="employenddate" value="<fmt:formatDate value="${beforeWork.employenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
                                        </div>

										<div class="paddingLeft groupInputInfo" style="padding-left: 0;">
                                            <label><span class="s">*</span>职位</label>
                                            <input name="jobtitle" value="${beforeWork.jobtitle }" onchange="addSegmentsTranslateZhToEn(this,'jobtitleen','')" type="text"/>
                                        </div>
										
										<div class="clear"></div>
									
										<div class="draBig leftNo marginLS grouptextareaInfo">
                                            <label><span class="s">*</span>简述你的职责</label>
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
						</div>
					</div>
                </div>
                <div class="titleInfo" style="margin-top: 20px;">教育信息</div>
				<div class="padding-left">
					<div class="paddingTop">
						<div class="groupRadioInfo" style="padding-bottom: 10px;">
                            <label><span class="s">*</span> 是否有初中及以上教育经历（最高学历）</label>
                            
							<input type="radio" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" @change="issecondarylevel()" class="education" value="1" />是
							<input type="radio" style="margin-left: 20px;" name="issecondarylevel" v-model="visaInfo.workEducationInfo.issecondarylevel" @change="issecondarylevel()" class="education" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="educationInfo elementHide">
							<div class="educationYes">
							<c:if test="${!empty obj.beforeEducationList }">
								<c:forEach var="education" items="${obj.beforeEducationList }">
                                    <div class="paddingTop groupSelectInfo padding-left" style="padding-top: 0;padding-left: 0;">
                                        <label><span class="s">*</span>最高学历</label>
                                        <select id="" value="0">请选择</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>
									<div class="midSchoolEduDiv margintop-10">
										<div class="draBig leftNo marginLS groupInputInfo">
                                            <label><span class="s">*</span>学校名称</label>
											<input name="institution" onchange="addSegmentsTranslateZhToEn(this,'institutionen','')" value="${education.institution }" type="text"/>
										</div>                                        
                                        <div class="paddingLeft leftNo groupInputInfo">
                                            <label><span class="s">*</span> 专业名称</label>
                                            <input name="course" onchange="addSegmentsTranslateZhToEn(this,'courseen','')" value="${education.course }" type="text" />
                                        </div>
                                        
                                        
                                        <div class="paddingRight leftNo groupSelectInfo" >
                                            <label><span class="s">*</span> 所在国家</label>
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

										<div class="paddingLeft leftNo groupcheckBoxInfo" >
                                            <label><span class="s">*</span> 单位地址（省）</label>
											<input name="institutionprovince" onchange="addSegmentsTranslateZhToEn(this,'institutionprovinceen','')" value="${education.institutionprovince }" type="text" />
											<c:if test="${education.isinstitutionprovinceapply == 1}">
												<input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }"  checked="checked" type="checkbox"/>
											</c:if>
											<c:if test="${education.isinstitutionprovinceapply != 1}">
												<input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }" type="checkbox" />
											</c:if>
											
										</div>
										<div class="paddingRight leftNo groupInputInfo">
                                            <label><span class="s">*</span> 单位地址（市）</label>
											<input name="institutioncity" onchange="addSegmentsTranslateZhToEn(this,'institutioncityen','')" value="${education.institutioncity }" type="text" />
										</div>
										<div class="clear"></div>
										
										<div class="draBig leftNo margintop-10 groupInputInfo">
                                            <label><span class="s">*</span>详细地址</label>
                                            <input name="institutionaddress" onchange="addSegmentsTranslateZhToEn(this,'institutionaddressen','')" value="${education.institutionaddress }" type="text" />
                                        </div>


										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
                                            <label><span class="s">*</span>开始时间</label>
											<input id="coursestartdate" onchange="addSegmentsTranslateZhToEn(this,'coursestartdateen','')" name="coursestartdate" value="<fmt:formatDate value="${education.coursestartdate }" pattern="dd/MM/yyyy" />"  class="datetimepickercss form-control margintop-10" type="text" placeholder="日/月/年" />
										</div>
										
										<div class="paddingRight leftNo groupInputInfo">
                                            <label><span class="s">*</span>结束时间</label>
											<input id="courseenddate" onchange="addSegmentsTranslateZhToEn(this,'courseenddateen','')" name="courseenddate" value="<fmt:formatDate value="${education.courseenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control margintop-10" type="text" placeholder="日/月/年" />
                                        </div>
                                        <div class="clear"></div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeEducationList }">
                                <div class="midSchoolEduDiv margintop-10">
                                    <div class="draBig leftNo marginLS groupInputInfo">
                                        <label><span class="s">*</span>学校名称</label>
                                        <input name="institution" onchange="addSegmentsTranslateZhToEn(this,'institutionen','')" value="${education.institution }" type="text"/>
                                    </div>                                        
                                    <div class="paddingLeft leftNo groupInputInfo">
                                        <label><span class="s">*</span> 专业名称</label>
                                        <input name="course" onchange="addSegmentsTranslateZhToEn(this,'courseen','')" value="${education.course }" type="text" />
                                    </div>
                                    
                                    
                                    <div class="paddingRight leftNo groupSelectInfo" >
                                        <label><span class="s">*</span> 所在国家</label>
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

                                    <div class="paddingLeft leftNo groupcheckBoxInfo" >
                                        <label><span class="s">*</span> 单位地址（省）</label>
                                        <input name="institutionprovince" onchange="addSegmentsTranslateZhToEn(this,'institutionprovinceen','')" value="${education.institutionprovince }" type="text" />
                                        <c:if test="${education.isinstitutionprovinceapply == 1}">
                                            <input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }"  checked="checked" type="checkbox"/>
                                        </c:if>
                                        <c:if test="${education.isinstitutionprovinceapply != 1}">
                                            <input name="isinstitutionprovinceapply" onchange="AddSegment(this,'isschoolprovinceen')" value="${education.isinstitutionprovinceapply }" type="checkbox" />
                                        </c:if>
                                        
                                    </div>
                                    <div class="paddingRight leftNo groupInputInfo">
                                        <label><span class="s">*</span> 单位地址（市）</label>
                                        <input name="institutioncity" onchange="addSegmentsTranslateZhToEn(this,'institutioncityen','')" value="${education.institutioncity }" type="text" />
                                    </div>
                                    <div class="clear"></div>
                                    
                                    <div class="draBig leftNo margintop-10 groupInputInfo">
                                        <label><span class="s">*</span>详细地址</label>
                                        <input name="institutionaddress" onchange="addSegmentsTranslateZhToEn(this,'institutionaddressen','')" value="${education.institutionaddress }" type="text" />
                                    </div>


                                    <div class="clear"></div>
                                    <div class="paddingLeft leftNo groupInputInfo">
                                        <label><span class="s">*</span>开始时间</label>
                                        <input id="coursestartdate" onchange="addSegmentsTranslateZhToEn(this,'coursestartdateen','')" name="coursestartdate" value="<fmt:formatDate value="${education.coursestartdate }" pattern="dd/MM/yyyy" />"  class="datetimepickercss form-control margintop-10" type="text" placeholder="日/月/年" />
                                    </div>
                                    
                                    <div class="paddingRight leftNo groupInputInfo">
                                        <label><span class="s">*</span>结束时间</label>
                                        <input id="courseenddate" onchange="addSegmentsTranslateZhToEn(this,'courseenddateen','')" name="courseenddate" value="<fmt:formatDate value="${education.courseenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control margintop-10" type="text" placeholder="日/月/年" />
                                    </div>
                                    <div class="clear"></div>
                                </div>
							</c:if>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					
				</div>
			</div>
			<!--工作/教育/培训信息END-->
		</div>
		<div class="English">

			<!--工作/教育/培训信息-->
			<div class="experience paddingTop" style="margin-bottom: 243px;">
				
				<div class="paddingTop elementHide jobEduLearningInfoDiv" style="margin-top: 107px;">
					<div class="groupInputInfo draBig">
                            <label><span class="s">*</span> Present Employer Name</label>
						<input name="unitnameen" id="unitnameen" v-model="visaInfo.workEducationInfo.unitnameen" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS" style="margin-top: 147px;">
						<label ><span class="s">*</span> Street Address</label>
						<input name="addressen" id="jobaddressen" v-model="visaInfo.workEducationInfo.addressen" type="text" />
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
				<div id="yiqian" class="paddingTop padding-left" style="margin-bottom: 369px;">
					<div>
						<!--yes-->
						<div class="beforeWorkInfo beforeWorkInfoen elementHide">
						  <div class="beforeWorkYes marginbottom-36">
							<c:if test="${!empty obj.beforeWorkList }">
								<c:forEach var="beforeWork" items="${obj.beforeWorkList }">
									<div class="workBeforeInfosDiven">
										<div class="leftNo marginLS groupInputInfo" >
											<label><span class="s">*</span>Present Employer Name</label>
											<input name="employernameen" id="employernameen" class="employernameen" value="${beforeWork.employernameen }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo" style="margin-top: 148px;">
                                            <label><span class="s">*</span> Street Address</label>
											<input name="employeraddressen" class="employeraddressen" value="${beforeWork.employeraddressen }" type="text" />
										</div>
										<div class="clear"></div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeWorkList }">
								<div class="workBeforeInfosDiven">
									<div class="leftNo marginLS groupInputInfo" >
										<label><span class="s">*</span>Present Employer Name</label>
										<input name="employernameen" id="employernameen" class="employernameen" value="${beforeWork.employernameen }" type="text" />
									</div>
									<div class="draBig leftNo marginLS groupInputInfo" style="margin-top: 145px;">
										<label><span class="s">*</span> Street Address</label>
										<input name="employeraddressen" class="employeraddressen" value="${beforeWork.employeraddressen }" type="text" />
									</div>
									<div class="clear"></div>
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
					
						<!--yes-->
						<div class="educationInfo educationInfoen elementHide">
							<div class="educationYes marginbottom-36">
							<c:if test="${!empty obj.beforeEducationList }">
								<c:forEach var="education" items="${obj.beforeEducationList }">
									<div class="midSchoolEduDiven margintop-10">
										<div class="draBig leftNo marginLS groupInputInfo">
											<label><span class="s">*</span> School Name</label>
											<input name="institutionen" class="institutionen" value="${education.institutionen }" type="text"/>
										</div>
										<div class="draBig leftNo margintop-10 groupInputInfo" style="margin-top: 145px!important;">
                                            <label><span class="s">*</span> Street Address</label>
											<input name="institutionaddressen" class="institutionaddressen" value="${education.institutionaddressen }" type="text" />
										</div>
									
										<div class="clear"></div>
									
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty obj.beforeEducationList }">
								<div class="midSchoolEduDiven margintop-10">
									<div class="draBig leftNo marginLS groupInputInfo">
										<label><span class="s">*</span> School Name</label>
										<input name="institutionen" class="institutionen" type="text"/>
									</div>

									<div class="draBig leftNo margintop-10 groupInputInfo" style="margin-top: 145px!important;">
										<label><span class="s">*</span> Street Address</label>
										<input name="institutionaddressen" class="institutionaddressen" value="${education.institutionaddressen }" type="text" />
									</div>
									<div class="clear"></div>
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
			
			<!--工作/教育/培训信息END-->
        </div>
        <!-- //************ -->
        <div class="clear"></div>
        <div style="height: 50px;"></div>
	</div>	

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
        
    </script>
</body>
</html>

