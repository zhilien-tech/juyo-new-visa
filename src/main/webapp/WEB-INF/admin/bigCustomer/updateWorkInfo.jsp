<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>签证信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
    <link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
    <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
    <!-- 本页样式 -->
    <link rel="stylesheet" href="${base}/references/public/css/updateVisaInfo.css">
    <style>
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
			.aaa .select2 .select2-search__field{
				margin:0!important;
			}
			.select2 {
				width: 100%!important;
			}
			.select2-container{
				height: 34px;
				border: 1px solid #aaa;
			}
			.select2-selection,
			.selection{
				height: 100%!important;
			}
			.select2-container .select2-selection--multiple{
				height: 28px!important;
				min-height: 28px!important;
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
    <a id="toFamilyinfo" class="leftNav" onclick="familyInfoBtn();">
        <i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第四步</i>
        <span></span>
	</a>
	<a id="toTravelinfo" class="rightNav" onclick="travelInfoBtn();">
		<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第六步</i>
		<span></span>
	</a>
	<form id="workinfo">
	<div id="section">
		<div id="wrapper" class="section">
			<div class="dislogHide"></div>
			<!--工作/教育/培训信息-->
			<div class="experience paddingTop">
				<div class="titleInfo">职业信息</div>
				<div class="paddingTop groupSelectInfo padding-left" >
					<input autocomplete="new-password" type="hidden" value="${obj.staffId }" name="staffid"/>
					<label><span class="s">*</span>主要职业</label>
					<select id="occupation"  name="occupation" style="width: 100%;">
						<option value="0">请选择</option>
						<c:forEach items="${obj.careersenum }" var="map">
							<option value="${map.key}" ${map.key==obj.workinfo.occupation?"selected":"" } >${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="paddingTop  jobEduLearningInfoDiv">
					<div class="groupInputInfo draBig">
                        <label><span class="s">*</span>目前的工作单位名称</label>
						<input autocomplete="new-password" name="unitname" id="unitname" onchange="translateZhToEn(this,'unitnameen','')"  type="text" value="${obj.workinfo.unitname }" />
                    </div>
                    <div class="clear"></div>
                    <div class="paddingLeft groupInputInfo">
                        <label><span class="s">*</span>电话号码</label>
                        <input autocomplete="new-password" name="telephone" value="${obj.workinfo.telephone }" id="jobtelphone"  type="text" />
                    </div>
					<div class="paddingRight groupSelectInfo" style="width: 190px;padding-left: 15px;">
                        <label><span class="s">*</span>工作国家</label>
                        <select id='jobcountry'  class="form-control input-sm select2" multiple="multiple" name="country">
                        
                        	<c:forEach items="${obj.gocountryfivelist }" var="country">
							<c:choose>
								<c:when test="${country.id eq obj.workinfo.country }">
									<option value="${country.id }" selected="selected">${country.chinesename }</option>
								</c:when>
								<c:otherwise>
									<option value="${country.id }">${country.chinesename }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
                        
                        </select>
                    </div>
                    <div class="clear"></div>
                    <div class="paddingLeft groupcheckBoxInfo" style="width: 200px;">
                        <label><span class="s">*</span>单位地址（省）</label>
                        <select name="province" class="form-control input-sm select2" onchange="translateZhToEn(this,'jobprovinceen','select2')" multiple="multiple"  id="jobprovince" >
	                   		<%-- <option selected="selected" value="${obj.workinfo.province }">${obj.workinfo.province}</option> --%>
	                        <c:if test="${not empty obj.workinfo.province }">
								<option value="${obj.workinfo.province }" selected="selected">${obj.workinfo.province }</option>
							</c:if>
                        </select>
                        <input type="hidden" id="jobprovinceen" name="provinceen" value="${obj.workinfo.provinceen }"/>
                    </div>
                    <div class="paddingRight groupInputInfo aaa" style="width: 180px;">
                        <label><span class="s">*</span>单位地址（市）</label>
                         <select name="city" class="form-control input-sm select2" onchange="translateZhToEn(this,'cityen','select2')" multiple="multiple"  id="city" >
	                   		<c:if test="${not empty obj.workinfo.city }">
								<option value="${obj.workinfo.city }" selected="selected">${obj.workinfo.city }</option>
							</c:if>
                        </select>
                        <input id="cityen" name="cityen" type="hidden" value="${obj.workinfo.cityen }"/>
                    </div>
                    <div class="clear"></div>
					<div class="groupInputInfo draBig marginLS">
                        <label><span class="s">*</span>详细地址</label>
						<input autocomplete="new-password" name="address" id="jobaddress" onchange="translateZhToEn(this,'jobaddressen','')" value="${obj.workinfo.address }" type="text" />
					</div>
					<div class="clear"></div>
                    <div class="paddingLeft groupInputInfo">
                        <label><span class="s">*</span>入职日期</label>
                        <input autocomplete="new-password" id="workstartdate"  name="workstartdate" value="${obj.workstartdate}" class="datetimepickercss form-control" type="text" placeholder="" />
                    </div>
                    <div class=" paddingRight groupcheckBoxInfo">
                        <label><span class="s">*</span>职位</label>
                        <input autocomplete="new-password" onchange="translateZhToEn(this,'positionen','')" name="position" value="${obj.workinfo.position }" style="width: 180px;" id="position" type="text" />
                        <input type="hidden" id="positionen" name="positionen" value="${obj.workinfo.positionen }"/>
                    </div>
					<div class="clear"></div>
					<div class="paddingLeft groupcheckBoxInfo" >
                        <label><span class="s">*</span>当前月收入</label>
						<input autocomplete="new-password" name="salary" value="${obj.workinfo.salary }" style="width: 180px;" id="salary" type="text"  onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"/>
					</div>
					<div class="clear"></div>
					<div class="grouptextareaInfo groupPM">
                        <label><span class="s">*</span>简述你的职责</label>
						<input name="duty" class='areaInputPic' id="jobduty" onchange="translateZhToEn(this,'jobdutyen','')" value="${obj.workinfo.duty }" />
						<%-- <input id="jobdutyen" name="dutyen" type="hidden" value="${obj.workinfo.dutyen }"/> --%>
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
							<input autocomplete="new-password" 
								type="radio" 
								name="isemployed" 
								class="beforeWork" 
								value="1" 
							/>是
							<input autocomplete="new-password" 
								type="radio" 
								style="margin-left: 20px;" 
								name="isemployed" 
								class="beforeWork" 
								value="2" 
								checked
							/>否
						</div>
						<!--yes-->
						<div class="beforeWorkInfo elementHide">
						  <div class="beforeWorkYes">
									<div class="workBeforeInfosDiv">
										<div class="leftNo marginLS groupInputInfo" >
                                            <label><span class="s">*</span>单位名称</label>
											<input autocomplete="new-password" name="employername" style="width: 100%" onchange="translateZhToEn(this,'employernameen','')" value="${obj.beforework.employername }" type="text" />
										</div>
                                      
                                        <div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">

                                            <label><span class="s">*</span>电话号码</label>
											<input autocomplete="new-password" name="employertelephone"  value="${obj.beforework.employertelephone }" type="text" />
                                        </div>
                                        
                                        <div class="paddingRight groupSelectInfo" style="width: 180px;">
                                            <label><span class="s">*</span>工作国家</label>
                                            <select name="employercountry" class=" select2" multiple="multiple"  id="employercountry">
                                                <c:forEach items="${obj.gocountryfivelist }" var="country">
													<c:choose>
														<c:when test="${country.id eq obj.beforework.employercountry }">
															<option value="${country.id }" selected="selected">${country.chinesename }</option>
														</c:when>
														<c:otherwise>
															<option value="${country.id }">${country.chinesename }</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
                                            </select>
                                        </div>
										
										<div class="clear"></div>
										
										<div class="paddingLeft leftNo groupInputInfo" style="width: 180px;">
                                            <label><span class="s">*</span>单位地址（省）</label>
											<select name="employerprovince" class="form-control input-sm select2" onchange="translateZhToEn(this,'employerprovinceen','select2')" multiple="multiple"  id="employerprovince" >
						                   		<c:if test="${not empty obj.beforework.employerprovince }">
													<option value="${obj.beforework.employerprovince }" selected="selected">${obj.beforework.employerprovince }</option>
												</c:if>
					                        </select>
					                        <input id="employerprovinceen" name="employerprovinceen" type="hidden" value="${obj.beforework.employerprovinceen }"/>
										</div>
										<div class="paddingRight leftNo groupInputInfo aaa" style="width: 180px;">
                                            <label><span class="s">*</span>单位地址（市）</label>
											<select name="employercity" class="form-control input-sm select2" onchange="translateZhToEn(this,'employercityen','select2')" multiple="multiple"  id="employercity" >
						                   		<c:if test="${not empty obj.beforework.employercity }">
													<option value="${obj.beforework.employercity }" selected="selected">${obj.beforework.employercity }</option>
												</c:if>
					                        </select>
					                        <input id="employercityen" name="employercityen" type="hidden" value="${obj.beforework.employercityen }"/>
										</div>
                                        <div class="clear"></div>
                                        
                                        <div class="draBig leftNo marginLS groupInputInfo">
                                            <label><span class="s">*</span>详细地址</label>
                                            <input autocomplete="new-password" id="employeraddress" name="employeraddress" onchange="translateZhToEn(this,'employeraddressen','')" value="${obj.beforework.employeraddress }" type="text" />
                                        </div>

                                        <div class="paddingLeft leftNo groupInputInfo" >
                                            <label><span class="s">*</span>入职时间</label>
                                            <input autocomplete="new-password" id="employstartdate"  name="employstartdate"  value="${obj.employestartdate }"  class="datetimepickercss form-control" type="text" placeholder="" />
                                        </div>
                                        <div class="paddingRight groupInputInfo">

                                            <label><span class="s">*</span>离职时间</label>
                                            <input autocomplete="new-password" id="employenddate"  name="employenddate"  value="${obj.employenddate }"  class="datetimepickercss form-control" type="text" placeholder="" />
                                        </div>

										<div class="paddingLeft groupInputInfo" style="padding-left: 0;">
                                            <label><span class="s">*</span>职位</label>
                                            <input autocomplete="new-password" onchange="translateZhToEn(this,'jobtitleen','')" name="jobtitle" value="${obj.beforework.jobtitle }"  type="text"/>
                                            <input id="jobtitleen" name="jobtitleen" type="hidden" value="${obj.beforework.jobtitleen }"/>
                                        </div>
										
										<div class="clear"></div>
									
										<div class="draBig leftNo marginLS grouptextareaInfo">
                                            <label><span class="s">*</span>简述你的职责</label>
											<input autocomplete="new-password" type="text" onchange="translateZhToEn(this,'previousdutyen','')" name="previousduty"  class="areaInputPic previousduty" value="${obj.beforework.previousduty }" />
											<%-- <input id="previousdutyen" name="previousdutyen" type="hidden" value="${obj.beforework.previousdutyen }"/> --%>
											<%-- <textarea name="previousduty" class="bigArea previousduty" value="${beforeWork.previousduty }"></textarea> --%>
										</div>
									</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
                </div>
                <div class="titleInfo" style="margin-top: 20px;">教育信息</div>
				<div class="padding-left">
					<div class="paddingTop">
						<div class="groupRadioInfo" style="padding-bottom: 10px;">
                            <label><span class="s">*</span> 是否有高中及以上教育经历（最高学历）</label>
                            
							<input autocomplete="new-password" type="radio" name="issecondarylevel" class="education" value="1" />是
							<input autocomplete="new-password" type="radio" style="margin-left: 20px;" name="issecondarylevel"  class="education" value="2" checked/>否
						</div>
						<!--yes-->
						<div class="educationInfo elementHide">
							<div class="educationYes">
                                    <div class="paddingTop groupSelectInfo padding-left" style="padding-top: 0;padding-left: 0;">
                                        <label><span class="s">*</span>最高学历</label>
                                        <select id="highesteducation" name="highesteducation" >
                                            <option value="0">请选择</option>
                                            <c:forEach items="${obj.highesteducationenum }" var="map">
					                            <c:choose>
													<c:when test="${map.key eq obj.beforeeducate.highesteducation }">
														<option value="${map.key }" selected="selected">${map.value }</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key }">${map.value }</option>
													</c:otherwise>
												</c:choose>
					                        </c:forEach>
                                        </select>
                                    </div>
									<div class="midSchoolEduDiv margintop-10">
										<div class="draBig leftNo marginLS groupInputInfo">
                                            <label><span class="s">*</span>学校名称</label>
											<input autocomplete="new-password" name="institution" onchange="translateZhToEn(this,'institutionen','')" value="${obj.beforeeducate.institution }" type="text"/>
										</div>                                        
                                        <div class="paddingLeft leftNo groupInputInfo courseClass" style="margin-right: 20px;">
                                            <label><span class="s">*</span> 专业名称</label>
                                            <input autocomplete="new-password" onchange="translateZhToEn(this,'courseen','')" id="course" name="course" value="${obj.beforeeducate.course }" type="text" />
                                            <input id="courseen" name="courseen" type="hidden" value="${obj.beforeeducate.courseen }"/>
                                        </div>
                                        
                                        
                                        <div class="paddingLeft leftNo groupSelectInfo" style="width: 180px;">
                                            <label><span class="s">*</span> 所在国家</label>
                                            <select name="institutioncountry" class=" select2" multiple="multiple" id="institutioncountry">
                                            	<c:forEach items="${obj.gocountryfivelist }" var="country">
													<c:choose>
														<c:when test="${country.id eq obj.beforeeducate.institutioncountry }">
															<option value="${country.id }" selected="selected">${country.chinesename }</option>
														</c:when>
														<c:otherwise>
															<option value="${country.id }">${country.chinesename }</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
                                            </select>
                                        </div>
                                        <div class="clear"></div>

										<div class="paddingLeft leftNo groupcheckBoxInfo" style="width: 180px;">
                                            <label><span class="s">*</span> 学校地址（省）</label>
                                            
                                            <%-- <select name="institutionprovince" class="form-control input-sm select2" multiple="multiple"  id="institutionprovince" >
							                   <option selected="selected" value="${obj.beforeeducate.institutionprovince }">${obj.beforeeducate.institutionprovince}</option>
						                    </select> --%>
						                    <input id="insprovince" autocomplete="new-password" style="width:182px;" name="institutionprovince" onchange="translateZhToEn(this,'institutionprovinceen','')" type="text" value="${obj.beforeeducate.institutionprovince }"/>
						                    <input id="institutionprovinceen" name="institutionprovinceen" type="hidden" value="${obj.beforeeducate.institutionprovinceen }"/>
										</div>
										<div class="paddingRight leftNo groupInputInfo aaa" style="width: 180px;">
                                            <label><span class="s">*</span> 学校地址（市）</label>
                                            <%-- <select name="institutioncity" class="form-control input-sm select2" multiple="multiple"  id="institutioncity" >
							                   <option selected="selected" value="${obj.beforeeducate.institutioncity }">${obj.beforeeducate.institutioncity}</option>
						                    </select> --%>
						                    <input id="inscity" autocomplete="new-password" style="width:182px;" name="institutioncity" onchange="translateZhToEn(this,'institutioncityen','')" type="text" value="${obj.beforeeducate.institutioncity }"/>
						                    <input id="institutioncityen" name="institutioncityen" type="hidden" value="${obj.beforeeducate.institutioncityen }"/>
										</div>
										<div class="clear"></div>
										
										<div class="draBig leftNo margintop-10 groupInputInfo">
                                            <label><span class="s">*</span>详细地址</label>
                                            <input autocomplete="new-password" id="institutionaddress" name="institutionaddress" onchange="translateZhToEn(this,'institutionaddressen','')" value="${obj.beforeeducate.institutionaddress }" type="text" />
                                        </div>


										<div class="clear"></div>
										<div class="paddingLeft leftNo groupInputInfo">
                                            <label><span class="s">*</span>开始时间</label>
											<input autocomplete="new-password" id="coursestartdate"  name="coursestartdate" value="${obj.coursestartdate }"  class="datetimepickercss form-control margintop-10" type="text" placeholder="" />
										</div>
										
										<div class="paddingRight leftNo groupInputInfo">
                                            <label><span class="s">*</span>结束时间</label>
											<%-- <input autocomplete="new-password" id="courseenddate" onchange="addSegmentsTranslateZhToEn(this,'courseenddateen','')" name="courseenddate" value="<fmt:formatDate value="${education.courseenddate }" pattern="dd/MM/yyyy" />" class="datetimepickercss form-control margintop-10" type="text" placeholder="日/月/年" /> --%>
											<input autocomplete="new-password" id="courseenddate" name="courseenddate" value="${obj.courseenddate }"  class="datetimepickercss form-control margintop-10" type="text" placeholder="" />
                                        </div>
                                        <div class="clear"></div>
									</div>
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
			<div class="experience paddingTop">
				
				<div class="paddingTop  jobEduLearningInfoDiv" style="margin-top: 107px;">
					<div class="groupInputInfo draBig">
                            <label><span class="s">*</span> Present Employer Name</label>
						<input autocomplete="new-password" name="unitnameen" id="unitnameen" style="width: 100%;" value="${obj.workinfo.unitnameen }" type="text" />
					</div>
					<div class="groupInputInfo draBig marginLS" style="margin-top: 147px;">
						<label ><span class="s">*</span> Street Address</label>
						<input autocomplete="new-password" name="addressen" id="jobaddressen" value="${obj.workinfo.addressen }" type="text" />
					</div>
					<div class="clear"></div>
				</div>

				<div class="groupInputInfo draBig marginLS" style="margin-top: 144px;">
					<label ><span class="s">*</span> Duty</label>
					<input name="dutyen" id="jobdutyen" value="${obj.workinfo.dutyen }" type="text" />
				</div>
				
				<div class="grouptextareaInfo elementHide jobEduLearningInfoTextarea">
					<!-- <label>说明</label>
					<input autocomplete="new-password" /> -->
				</div>
			</div>
			<div id="box1" style="width: 100px;height: 48px;"></div>
			<!--以前-->
			<div>
				<div id="yiqian" class="paddingTop padding-left">
					<div>
						<!--yes-->
						<div class="beforeWorkInfo beforeWorkInfoen elementHide">
						  <div class="beforeWorkYes marginbottom-36">
									<div class="workBeforeInfosDiven">
										<div class="leftNo marginLS groupInputInfo" >
											<label><span class="s">*</span>Present Employer Name</label>
											<input autocomplete="new-password" name="employernameen" id="employernameen" style="width: 100%;" class="employernameen" value="${obj.beforework.employernameen }" type="text" />
										</div>
										<div class="draBig leftNo marginLS groupInputInfo" style="margin-top: 147px;">
                                            <label><span class="s">*</span> Street Address</label>
											<input autocomplete="new-password" id="employeraddressen" name="employeraddressen" class="employeraddressen" value="${obj.beforework.employeraddressen }" type="text" />
										</div>
										<div class="draBig leftNo margintop-10 groupInputInfo" style="margin-top: 138px!important;">
                                            <label><span class="s">*</span> Duty</label>
											<input autocomplete="new-password" id="previousdutyen" name="previousdutyen" class="institutionaddressen" value="${obj.beforework.previousdutyen }" type="text" />
										</div>
										<div class="clear"></div>
									</div>
							</div>
							<div class="clear"></div>
							<div class="btnGroup companyGroupen marginLS beforeWorkGroup">
								<a class="save beforeWorksaveen">Add Another</a>
								<a class="cancel beforeWorkcancelen">Remove</a>
							</div>
						</div>
					</div>
				</div>

				<div id="box2" style="width: 100px;height: 337px;"></div>
				<div class="padding-left">
					<div class="paddingTop">
					
						<!--yes-->
						<div class="educationInfo educationInfoen elementHide">
							<div class="educationYes marginbottom-36">
									<div class="midSchoolEduDiven margintop-10">
										<div class="draBig leftNo marginLS groupInputInfo">
											<label><span class="s">*</span> School Name</label>
											<input autocomplete="new-password" id="institutionen" name="institutionen" class="institutionen" value="${obj.beforeeducate.institutionen }" type="text"/>
										</div>
										<div class="draBig leftNo margintop-10 groupInputInfo" style="margin-top: 145px!important;">
                                            <label><span class="s">*</span> Street Address</label>
											<input autocomplete="new-password" id="institutionaddressen" name="institutionaddressen" class="institutionaddressen" value="${obj.beforeeducate.institutionaddressen }" type="text" />
										</div>
									
										<div class="clear"></div>
									
									</div>
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
	</form>
    <script type="text/javascript">
        var BASE_PATH = '${base}';
        var staffId   = '${obj.staffId}';
        var isDisable = '${obj.isDisable}';
        var flag      = '${obj.flag}';
    </script>
   <!-- 公共js -->
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
    
    <script src="${base}/references/common/js/layer/layer.js"></script>
    <script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
    
    <script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
    <script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
    
    <script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <!-- 本页js -->
    <script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js"></script><!-- 本页面 初始化时间插件 js -->
    <script src="${base}/admin/bigCustomer/updateWorkinfo.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面 js -->
	<script type="text/javascript">
        /* $(function(){
            //页面不可编辑
            if(isDisable == 1){
                $(".section").attr('readonly', true);
                $(".dislogHide").show();
                $(".saveVisa").hide();
            }
            
            openYesOrNoPage();
            
        }); */
        

        //是否有上份工作
        var beforework='${obj.workinfo.isemployed}';
        $("input[name='isemployed'][value='" + beforework + "']").attr("checked", 'checked');
        if (beforework == 1) {
			$(".beforeWorkInfo").show();
			$('#box1').show();
				$('#box2').height(337);
		} else {
			$(".beforeWorkInfo").hide();
			$('#box1').hide();
				$('#box2').height(224);
		}

		$('.beforeWork').change(function() {
			console.log($(this).val());

			if ($(this).val()==1) {
				$('#box1').show();
				$('#box2').height(337);
			} else {
				$('#box1').hide();
				$('#box2').height(224);
			}
		});
		
        //以前的教育信息
        var beforework='${obj.workinfo.issecondarylevel}';
        $("input[name='issecondarylevel'][value='" + beforework + "']").attr("checked", 'checked');
        if (beforework == 1) {
        	$(".educationInfo").show();
		} else {
			$(".educationInfo").hide();
		}
        
        //专业处理
        var highesteducation = '${obj.beforeeducate.highesteducation}';
        if(highesteducation > 2){
        	$(".courseClass").show();
        }else{
        	$(".courseClass").hide();
        }
        
       /*  var country = '${obj.workinfo.country}';
        if(country == ""){
        	$("#jobcountry").val(45).trigger('change');
        } */
        
        //跳转到基本信息页
        function familyInfoBtn(){
        	save(2);
        }
        function travelInfoBtn(){
        	save(3);
        }
        
    </script>
</body>
</html>

