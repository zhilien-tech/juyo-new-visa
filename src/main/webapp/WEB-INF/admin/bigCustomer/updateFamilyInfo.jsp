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
			<div class="title">
				<span>家庭信息</span>
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

		<a id="" class="rightNav" onclick="">
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
            <!--家庭信息-->
            <!--配偶-->
			<div class="paddingTop">
                <div class="titleInfo marginbottom-6">配偶信息</div>
                <div class="floatLeft groupInputInfo">
                    <label><span class="s">*</span> 配偶姓</label>
                    <input name="spousefirstname" id="spousefirstname" @change="spousefirstname('spousefirstname','spousefirstnameen','visaInfo.familyInfo.spousefirstnameen')" v-model="visaInfo.familyInfo.spousefirstname" type="text" />
                </div>
                
                <div class="floatRight groupInputInfo">
                    <label><span class="s">*</span> 配偶名</label>
                    <input name="spouselastname" id="spouselastname" @change="spouselastname('spouselastname','spouselastnameen','visaInfo.familyInfo.spouselastnameen')" v-model="visaInfo.familyInfo.spouselastname" type="text" />
                </div>
                <div class="clear" style="height: 15px;"></div>
				
				<div class="floatLeft groupInputInfo">
					<label><span class="s">*</span> 配偶生日</label>
					<input id="spousebirthday" onchange="translateZhToEn(this,'spousebirthdayen','')" name="spousebirthday" value="${obj.spousebirthday}" class="datetimepickercss form-control" type="text" placeholder="日/月/年" />
				</div>

				
				<div class="floatRight groupSelectInfo">
					<label><span class="s">*</span> 配偶国籍</label>
					<select id="spousenationality" class="publicMarNone" @change="spousenationality('spousenationality','spousenationalityen','visaInfo.familyInfo.spousenationalityen')" name="spousenationality" v-model="visaInfo.familyInfo.spousenationality">
						<option value="0">请选择</option>
						<c:forEach items="${obj.gocountryFiveList }" var="country">
							<option value="${country.id }">${country.chinesename }</option>
						</c:forEach>
					</select>
				</div>
               
				


                <div class="clear" style="height: 15px;"></div>
                <div class="floatLeft groupcheckBoxInfo">
                    <label><span class="s">*</span> 配偶出生城市</label>
                    <input name="spousecity" v-model="visaInfo.familyInfo.spousecity" id="spousefcity"  @change="spousecity('spousefcity','spousefcityen','visaInfo.familyInfo.spousecityen')" type="text" />
                    <input id="isKnowMatecity" name="isknowspousecity" onchange="AddSingle(this,'isknowspousecityen')" v-on:click="isknowspousecity" v-model="visaInfo.familyInfo.isknowspousecity" type="checkbox" />
                </div>
                <div class="floatRight groupSelectInfo" >
                    <label><span class="s">*</span> 配偶出生国家</label>
                    <select style="width: 182px!important;" id="spousecountry" @change="spousecountry('spousecountry','spousecountryen','visaInfo.familyInfo.spousecountryen')" name="spousecountry" v-model="visaInfo.familyInfo.spousecountry">
                        <option value="0">请选择</option>
                        <c:forEach items="${obj.gocountryFiveList }" var="country">
                            <option value="${country.id }">${country.chinesename }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="clear"></div>
                <div class="paddingTop groupSelectInfo padding-left" >
                    <label><span class="s">*</span> 配偶的联系地址</label>
                    <select id="spouseaddress" @change="spouseaddress('spouseaddress','spouseaddressen','visaInfo.familyInfo.spouseaddressen')" name="spouseaddress" v-model="visaInfo.familyInfo.spouseaddress" class="spouse_Address">
                        <option value="0">请选择</option>
                        <c:forEach items="${obj.VisaSpouseContactAddressEnum }" var="map">
                            <option value="${map.key }">${map.value }</option>
                        </c:forEach>
                    </select>
                </div>
                
                <!--配偶的联系地址select选择其他-->
                <div class="otherSpouseInfo elementHide paddingTop paddingbottom-65" >
                    <div class="groupInputInfo prvPadL">
                        <label>街道地址(首选)</label>
                        <input name="firstaddress" id="otherfrstaddress" @change="spouseotherstreet('otherfrstaddress','otherfrstaddressen','visaInfo.familyInfo.firstaddressen')" v-model="visaInfo.familyInfo.firstaddress" type="text" />
                    </div>
                    <div class="groupInputInfo prvPadLT">
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
                    <div class="groupcheckBoxInfo paddingLeft">
                        <label>邮政编码</label>
                        <input name="zipcode" v-model="visaInfo.familyInfo.zipcode" id="otherzipcode" @change="spouseothercode('otherzipcode','otherzipcodeen','visaInfo.familyInfo.zipcodeen')" type="text" />
                        <input id="selectcodeapply" name="selectcodeapply" onchange="AddSingle(this,'selectcodeapplyen')" @click="isselectcodeapply" v-model="visaInfo.familyInfo.selectcodeapply" type="checkbox" />
                    </div>
                    <div class="groupSelectInfo paddingRight">
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
			<!--亲属-->
			<div class="familyInfo">
				<div class="titleInfo" style="margin-top: 20px;">父亲信息</div>
				<div class="paddingLeft groupcheckBoxInfo" >
					<label><span class="s">*</span>	父亲姓</label>
					<input name="fatherfirstname" id="fatherfirstname" v-model="visaInfo.familyInfo.fatherfirstname" @change="familyinfofirstname('fatherfirstname','fatherfirstnameen','visaInfo.familyInfo.fatherfirstnameen')" type="text"/>
					<input id="isKnowFatherXing" name="isknowfatherfirstname" onchange="AddSingle(this,'isknowfatherfirstnameen')"  v-on:click="isknowfatherfirstname"  v-model="visaInfo.familyInfo.isknowfatherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label><span class="s">*</span> 父亲名</label>
					<input name="fatherlastname" id="fatherlastname" v-model="visaInfo.familyInfo.fatherlastname" @change="familyinfolastname('fatherlastname','fatherlastnameen','visaInfo.familyInfo.fatherlastnameen')" type="text" />
					<input id="isKnowFatherMing" name="isknowfatherlastname" onchange="AddSingle(this,'isknowfatherlastnameen')" v-on:click="isknowfatherlastname" v-model="visaInfo.familyInfo.isknowfatherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> 父亲生日</label>
					<input type="text" id="fatherbirthday" onchange="translateZhToEn('#fatherbirthday','fatherbirthdayen','')" value="${obj.fatherbirthday}" class="datetimepickercss form-control" placeholder="日/月/年" />
					<input id="isknowfatherbirthday" name="isknowfatherbirthday" onchange="AddSingle(this,'isknowfatherbirthdayen')" v-on:click="isknowfatherbirthday" v-model="visaInfo.familyInfo.isknowfatherbirthday" type="checkbox" />
				</div>
				<div class="paddingTop paddingRight">
					<div class="groupRadioInfo" style=" margin-right: 40px; padding-top: 0px;">
						<label style="margin-bottom: 10px; "><span class="s">*</span> 你的父亲是否在美国</label>
						<input type="radio" name="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" @change="isfatherinus()" class="fatherUS" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="isfatherinus"  v-on:click="isfatherinus" v-model="visaInfo.familyInfo.isfatherinus" @change="isfatherinus()" class="fatherUS" value="2" checked />否
					</div>
					
				</div>
				<div class="clear"></div>
				<!--yes-->
				<div class="fatherUSYes groupSelectInfo" style="padding-left: 15px;margin-top: 10px;">
					<label><span class="s">*</span> 身份状态</label>
					<select style="width: 100%!important" id="fatherstatus" @change="familyinfostatus('fatherstatus','fatherstatusen','visaInfo.familyInfo.fatherstatusen')" v-model="visaInfo.familyInfo.fatherstatus" name="fatherstatus">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="titleInfo" style="margin-top: 20px;">母亲信息</div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> 母亲姓</label>
					<input id="motherfirstname" name="motherfirstname" @change="familyinfomofirstname('motherfirstname','motherfirstnameen','visaInfo.familyInfo.motherfirstnameen')" v-model="visaInfo.familyInfo.motherfirstname" type="text" />
					<input id="isKnowMotherXing" name="isknowmotherfirstname" onchange="AddSingle(this,'isknowmotherfirstnameen')" v-on:click="isknowmotherfirstname" v-model="visaInfo.familyInfo.isknowmotherfirstname" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label><span class="s">*</span> 母亲名</label>
					<input id="motherlastname" name="motherlastname" v-model="visaInfo.familyInfo.motherlastname" @change="familyinfomolastname('motherlastname','motherlastnameen','visaInfo.familyInfo.motherlastnameen')"  type="text" />
					<input id="isKnowMotherMing" name="isknowmotherlastname" onchange="AddSingle(this,'isknowmotherlastnameen')" v-on:click="isknowmotherlastname" v-model="visaInfo.familyInfo.isknowmotherlastname" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="groupcheckBoxInfo prvPadLT paddingLeft">
					<label><span class="s">*</span> 母亲生日</label>
					<input type="text" id="motherbirthday" onchange="translateZhToEn('#motherbirthday','motherbirthdayen','')" value="${obj.motherbirthday}" class="datetimepickercss form-control" placeholder="日/月/年" />
					<input id="isknowmotherbirthday" name="isknowmotherbirthday" onchange="AddSingle(this,'isknowmotherbirthdayen')" v-on:click="isknowmotherbirthday" v-model="visaInfo.familyInfo.isknowmotherbirthday" type="checkbox" />
				</div>

				<div class="paddingTop padding-left paddingRight">
					<div class="groupRadioInfo" style="margin-right: 40px;padding-top: 0px;">
						<label style="margin-bottom: 10px;"><span class="s">*</span> 你的母亲是否在美国</label>
						<input type="radio" name="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" @change="ismotherinues()" class="motherUS" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="ismotherinus" v-on:click="ismotherinus" v-model="visaInfo.familyInfo.ismotherinus" @change="ismotherinues()" class="motherUS" value="2" checked />否
					</div>
				</div>
				<div class="clear"></div>
				<div class="motherUSYes paddingNone groupSelectInfo" style="padding-left: 15px;">
					<label><span class="s">*</span> 身份状态</label>
					<select id="motherstatus" style="width: 100%;" @change="familyinfomostatus('motherstatus','motherstatusen','visaInfo.familyInfo.motherstatusen')" name="motherstatus" v-model="visaInfo.familyInfo.motherstatus">
						<option value="0">请选择</option>
						<c:forEach items="${obj.VisaFamilyInfoEnum }" var="map">
							<option value="${map.key }">${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="titleInfo" style="margin-top: 20px;">其他亲属</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label><span class="s">*</span> 在美国除了父母还有没有直系亲属</label>
						<input type="radio" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" @change="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="hasimmediaterelatives" v-model="visaInfo.familyInfo.hasimmediaterelatives" @change="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="2" checked/>否
					</div>
					<div class="directRelatives margtop">
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
					</div>
					
				</div>
			
			<!--家庭信息END-->
		</div>
		<div class="English">
            <!--家庭信息-->
            <!--配偶-->
			<div class="paddingTop" style="    margin-top: 45px;margin-bottom: 260px;">
                <div class="floatLeft groupInputInfo">
                    <label><span class="s">*</span> Spouse's Surnames </label>
                    <input name="spousefirstnameen" id="spousefirstnameen" v-model="visaInfo.familyInfo.spousefirstnameen" type="text" />
                </div>
                <div class="floatRight groupInputInfo">
                    <label><span class="s">*</span> Spouse's Given Names</label>
                    <input name="spouselastnameen" id="spouselastnameen" v-model="visaInfo.familyInfo.spouselastnameen" type="text" />
                </div>
                <div class="clear"></div>
            </div>
			<!--亲属-->
			<div class="familyInfo">
				<div class="paddingLeft groupcheckBoxInfo" >
					<label><span class="s">*</span> father's Surnames</label>
					<input name="fatherfirstnameen" id="fatherfirstnameen" v-model="visaInfo.familyInfo.fatherfirstnameen" type="text"/>
					<input id="isKnowFatherXing" name="isknowfatherfirstnameen" class="isknowfatherfirstnameen"   v-on:click="isknowfatherfirstname"  v-model="visaInfo.familyInfo.isknowfatherfirstnameen" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label><span class="s">*</span> father's Given Names </label>
					<input name="fatherlastnameen" id="fatherlastnameen" v-model="visaInfo.familyInfo.fatherlastnameen" type="text" />
					<input id="isKnowFatherMing" name="isknowfatherlastnameen" class="isknowfatherlastnameen"  v-on:click="isknowfatherlastname" v-model="visaInfo.familyInfo.isknowfatherlastnameen" type="checkbox" />
				</div>
				<div class="clear" style="height: 118px;"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> Mother's Surnames</label>
					<input id="motherfirstnameen" name="motherfirstnameen" v-model="visaInfo.familyInfo.motherfirstnameen" type="text" />
					<input id="isKnowMotherXing" name="isknowmotherfirstnameen" class="isknowmotherfirstnameen"  v-on:click="isknowmotherfirstname" v-model="visaInfo.familyInfo.isknowmotherfirstnameen" type="checkbox" />
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label><span class="s">*</span> Mother's Given Names</label>
					<input id="motherlastnameen" name="motherlastnameen" v-model="visaInfo.familyInfo.motherlastnameen" type="text" />
					<input id="isKnowMotherMing" name="isknowmotherlastnameen" class="isknowmotherlastnameen"  v-on:click="isknowmotherlastname" v-model="visaInfo.familyInfo.isknowmotherlastnameen" type="checkbox" />
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left" style="margin-top: 183px;">
					<div class="directRelatives">
						<!--yes-->
						<c:if test="${!empty obj.zhiFamilyList }">
							<c:forEach var="zhifamily" items="${obj.zhiFamilyList }">
								<div class="directRelativesYesen">
									<div class="floatLeft leftNo groupInputInfo">
										<label><span class="s">*</span> Surnames</label>
										<input name="relativesfirstnameen" id="relativesfirtstnameen" value="${zhifamily.relativesfirstnameen }" type="text" />
									</div>
									<div class="floatRight groupInputInfo prvPadL">
										<label><span class="s">*</span> Given Names</label>
										<input name="relativeslastnameen" id="relativeslastnameen" value="${zhifamily.relativeslastnameen }"  type="text" />
									</div>
									<div class="clear"></div>
									
									
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${empty obj.zhiFamilyList}">
							<div class="directRelativesYesen">
								<div class="floatLeft leftNo groupInputInfo">
									<label><span class="s">*</span> Surnames</label>
									<input name="relativesfirstnameen" id="relativesfirtstnameen" value="${zhifamily.relativesfirstnameen }" type="text" />
								</div>
								<div class="floatRight groupInputInfo prvPadL">
									<label><span class="s">*</span> Given Names</label>
									<input name="relativeslastnameen" id="relativeslastnameen" value="${zhifamily.relativeslastnameen }"  type="text" />
								</div>
								<div class="clear"></div>
								
								
							</div>
						</c:if>
						<div class="clear"></div>
						</div>
						<!--NO-->
						
					</div>
					
				</div>
			
			<!--家庭信息END-->

		</div>
		<div class="clear"></div>
		<div style="height: 50px;"></div>
	</div>	
	</body>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffId}';
		var isDisable = '${obj.isDisable}';
		var flag = '${obj.flag}';
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
	<script src="${base}/admin/bigCustomer/visa/openPageYesOrNo.js"></script><!-- 本页面 打开默认开关 js -->
	<script src="${base}/admin/bigCustomer/visa/visaGetInfoList.js"></script><!-- 本页面 获取一对多信息 js -->
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
</html>

