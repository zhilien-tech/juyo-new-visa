<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>签证信息</title>
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
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
		<a id="toPassport" class="leftNav" onclick="passportInfoBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第三步</i>
			<span></span>
		</a>

		<a id="toWorkinfo" class="rightNav" onclick="workinfoBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第五步</i>
			<span></span>
		</a>


		<!-- <a class="nextstep">
			<span></span>
		</a> -->
		<div class="topHide"></div>
		<form id="familyinfo">
		<div id="wrapper"  class="section">
			<div class="dislogHide"></div>
            <!--家庭信息-->
            <!--配偶-->
			<div class="paddingTop">
                <div class="titleInfo marginbottom-6">配偶信息</div>
                <div class="floatLeft groupInputInfo">
                    <label><span class="s">*</span> 配偶姓</label>
                    <input type="hidden" name="staffid" value="${obj.staffid}"/>
                    <input name="spousefirstname" id="spousefirstname"  value="${obj.familyinfo.spousefirstname }" type="text" />
                </div>
                
                <div class="floatRight groupInputInfo">
                    <label><span class="s">*</span> 配偶名</label>
                    <input name="spouselastname" id="spouselastname"  value="${obj.familyinfo.spouselastname }" type="text" />
                </div>
                <div class="clear" style="height: 15px;"></div>
				
				<div class="floatLeft groupInputInfo">
					<label><span class="s">*</span> 配偶生日</label>
					<input id="spousebirthday" onchange="translateZhToEn(this,'spousebirthdayen','')" name="spousebirthday" value="${obj.spousebirthday}" class="datetimepickercss form-control" type="text" placeholder="" />
				</div>

				
				<div class="floatRight groupSelectInfo">
					<label><span class="s">*</span> 配偶国籍</label>
					<select id="spousenationality" class="publicMarNone select2" multiple="multiple"  name="spousenationality" >
						<c:forEach items="${obj.gocountryfivelist }" var="country">
							<c:choose>
								<c:when test="${country.id eq obj.familyinfo.spousenationality }">
									<option value="${country.id }" selected="selected">${country.chinesename }</option>
								</c:when>
								<c:otherwise>
									<option value="${country.id }">${country.chinesename }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
               
				


                <div class="clear" style="height: 15px;"></div>
                <div class="floatLeft groupcheckBoxInfo">
                    <label><span class="s">*</span> 配偶出生城市</label>
                    <select name="spousecity" class="form-control input-sm select2" multiple="multiple"  id="spousecity" >
	                   <option selected="selected" value="${obj.familyinfo.spousecity }">${obj.familyinfo.spousecity}</option>
                    </select>
                </div>
                <div class="floatRight groupSelectInfo" >
                    <label><span class="s">*</span> 配偶出生国家</label>
                    <select  id="spousecountry"  name="spousecountry" class="form-control input-sm select2" multiple="multiple">
                     <c:forEach items="${obj.gocountryfivelist }" var="country">
							<c:choose>
								<c:when test="${country.id eq obj.familyinfo.spousenationality }">
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
                <div class="paddingTop groupSelectInfo padding-left" >
                    <label><span class="s">*</span> 配偶的联系地址</label>
                    <select id="spouseaddress"  name="spouseaddress" value="${obj.familyinfo.spouseaddress }" onchange="changeSpouseShow()" class="spouse_Address">
                        <option value="0">请选择</option>
                        <c:forEach items="${obj.spousecontactaddressenum }" var="map">
                            <c:choose>
								<c:when test="${map.key eq obj.familyinfo.spouseaddress }">
									<option value="${map.key }" selected="selected">${map.value }</option>
								</c:when>
								<c:otherwise>
									<option value="${map.key }">${map.value }</option>
								</c:otherwise>
							</c:choose>
                        </c:forEach>
                    </select>
                </div>
                
                <!--配偶的联系地址select选择其他-->
                <div class="otherSpouseInfo elementHide paddingTop paddingbottom-65" >
                    <div class="groupInputInfo prvPadL">
                        <label>街道地址(首选)</label>
                        <input name="firstaddress" id="otherfrstaddress" onchange="translateZhToEn(this,'otherfrstaddressen','')" value="${obj.familyinfo.firstaddress }" type="text" />
                    </div>
                    <div class="groupInputInfo prvPadLT">
                        <label>街道地址(次要)*可选</label>
                        <input name="secondaddress" id="othersecondaddress" onchange="translateZhToEn(this,'othersecondaddressen','')" value="${obj.familyinfo.secondaddress }" type="text" />
                    </div>
                    <div class="clear"></div>
                    <div class="paddingLeft groupcheckBoxInfo">
                        <label>州/省</label>
                        <input name="province" value="${obj.familyinfo.province }" id="otherprovince" onchange="translateZhToEn(this,'otherprovinceen','')" type="text" />
                        <!-- <input id="otherapply" name="otherapply" onchange="AddSingle(this,'otherapplyen')" @click="isotherapply" value="visaInfo.familyInfo.apply" type="checkbox" /> -->
                    </div>
                    <div class="paddingRight groupInputInfo">
                        <label>市</label>
                        <input name="othercity" value="${obj.familyinfo.city }" id="othercity" onchange="translateZhToEn(this,'othercityen','')" type="text"/>
                    </div>
                    <div class="clear"></div>
                    <div class="groupcheckBoxInfo paddingLeft">
                        <label>邮政编码</label>
                        <input name="zipcode" value="${obj.familyinfo.zipcode }" id="otherzipcode"  type="text" />
                       <!--  <input id="selectcodeapply" name="selectcodeapply" onchange="AddSingle(this,'selectcodeapplyen')" @click="isselectcodeapply" value="visaInfo.familyInfo.selectcodeapply" type="checkbox" /> -->
                    </div>
                    <div class="groupSelectInfo paddingRight">
                        <label>国家/地区</label>
                        <select name="othercountry" id="othercountry"  value="visaInfo.familyInfo.country">
                            <option value="0">请选择</option>
                            <c:forEach items="${obj.gocountryfivelist }" var="country">
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
					<input name="fatherfirstname" id="fatherfirstname" value="${obj.familyinfo.fatherfirstname }"  type="text"/>
					<!-- <input id="isKnowFatherXing" name="isknowfatherfirstname" onchange="AddSingle(this,'isknowfatherfirstnameen')"  v-on:click="isknowfatherfirstname"  value="visaInfo.familyInfo.isknowfatherfirstname" type="checkbox" /> -->
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label><span class="s">*</span> 父亲名</label>
					<input name="fatherlastname" id="fatherlastname" value="${obj.familyinfo.fatherlastname }"  type="text" />
					<!-- <input id="isKnowFatherMing" name="isknowfatherlastname" onchange="AddSingle(this,'isknowfatherlastnameen')" v-on:click="isknowfatherlastname" value="visaInfo.familyInfo.isknowfatherlastname" type="checkbox" /> -->
				</div>
				<div class="clear"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> 父亲生日</label>
					<input type="text" id="fatherbirthday" name="fatherbirthday" onchange="translateZhToEn(this,'fatherbirthdayen','')" value="${obj.fatherbirthday}" class="datetimepickercss form-control" placeholder="" />
					<!-- <input id="isknowfatherbirthday" name="isknowfatherbirthday" onchange="AddSingle(this,'isknowfatherbirthdayen')" v-on:click="isknowfatherbirthday" value="visaInfo.familyInfo.isknowfatherbirthday" type="checkbox" /> -->
				</div>
				<div class="paddingTop paddingRight">
					<div class="groupRadioInfo" style=" margin-right: 40px; padding-top: 0px;">
						<label style="margin-bottom: 10px; "><span class="s">*</span> 你的父亲是否在美国</label>
						<input type="radio" name="isfatherinus" value="${obj.familyinfo.isfatherinus }" onchange="isfatherinus()" class="fatherUS" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="isfatherinus"  v-on:click="isfatherinus" value="${obj.familyinfo.isfatherinus }" onchange="isfatherinus()" class="fatherUS" value="2" checked />否
					</div>
					
				</div>
				<div class="clear"></div>
				<!--yes-->
				<div class="fatherUSYes groupSelectInfo" style="padding-left: 15px;margin-top: 10px;">
					<label><span class="s">*</span> 身份状态</label>
					<select style="width: 100%!important" id="fatherstatus"  name="fatherstatus">
						<option value="0">请选择</option>
						<c:forEach items="${obj.familyinfoenum }" var="map">
							<option value="${map.key }" ${map.key==obj.familyinfo.fatherstatus?"selected":"" }>${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="titleInfo" style="margin-top: 20px;">母亲信息</div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> 母亲姓</label>
					<input id="motherfirstname" name="motherfirstname"  value="${obj.familyinfo.motherfirstname }" type="text" />
					<!-- <input id="isKnowMotherXing" name="isknowmotherfirstname" onchange="AddSingle(this,'isknowmotherfirstnameen')" v-on:click="isknowmotherfirstname" value="visaInfo.familyInfo.isknowmotherfirstname" type="checkbox" /> -->
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label><span class="s">*</span> 母亲名</label>
					<input id="motherlastname" name="motherlastname" value="${obj.familyinfo.motherlastname }"  type="text" />
					<!-- <input id="isKnowMotherMing" name="isknowmotherlastname" onchange="AddSingle(this,'isknowmotherlastnameen')" v-on:click="isknowmotherlastname" value="visaInfo.familyInfo.isknowmotherlastname" type="checkbox" /> -->
				</div>
				<div class="clear"></div>
				<div class="groupcheckBoxInfo prvPadLT paddingLeft">
					<label><span class="s">*</span> 母亲生日</label>
					<input type="text" id="motherbirthday" name="motherbirthday"  value="${obj.motherbirthday}" class="datetimepickercss form-control" placeholder="" />
					<!-- <input id="isknowmotherbirthday" name="isknowmotherbirthday" onchange="AddSingle(this,'isknowmotherbirthdayen')" v-on:click="isknowmotherbirthday" value="visaInfo.familyInfo.isknowmotherbirthday" type="checkbox" /> -->
				</div>

				<div class="paddingTop padding-left paddingRight">
					<div class="groupRadioInfo" style="margin-right: 40px;padding-top: 0px;">
						<label style="margin-bottom: 10px;"><span class="s">*</span> 你的母亲是否在美国</label>
						<input type="radio" name="ismotherinus" value="${obj.familyinfo.ismotherinus }" onchange="ismotherinues()" class="motherUS" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="ismotherinus" v-on:click="ismotherinus" value="${obj.familyinfo.ismotherinus }" onchange="ismotherinues()" class="motherUS" value="2" checked />否
					</div>
				</div>
				<div class="clear"></div>
				<div class="motherUSYes paddingNone groupSelectInfo" style="padding-left: 15px;">
					<label><span class="s">*</span> 身份状态</label>
					<select id="motherstatus" style="width: 100%;"  name="motherstatus" >
						<option value="0">请选择</option>
						<c:forEach items="${obj.familyinfoenum }" var="map">
							<option value="${map.key }" ${map.key==obj.familyinfo.motherstatus?"selected":"" }>${map.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="titleInfo" style="margin-top: 20px;">其他亲属</div>
				<div class="paddingTop padding-left">
					<div class="groupRadioInfo">
						<label><span class="s">*</span> 在美国除了父母还有没有直系亲属</label>
						<input type="radio" name="hasimmediaterelatives" value="${obj.familyinfo.hasimmediaterelatives }" onchange="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="1" />是
						<input type="radio" style="margin-left: 20px;" name="hasimmediaterelatives" value="${obj.familyinfo.hasimmediaterelatives }" onchange="hasimmediaterelatives()" class="directRelatives directUSRelatives" value="2" checked/>否
					</div>
					<div class="directRelatives margtop">
						<!--yes-->
							<div class="directRelativesYes">
								<div class="floatLeft leftNo groupInputInfo">
									<label>姓</label>
									<input name="relativesfirstname" id="relativesfirstname" value="${obj.immediaterelatives.relativesfirstname }"  type="text" />
								</div>
								<div class="floatRight groupInputInfo">
									<label>名</label>
									<input name="relativeslastname" id="relativeslastname" value="${obj.immediaterelatives.relativeslastname }"  type="text" />
								</div>
								<div class="clear"></div>
								<div class="paddingLeft leftNo groupSelectInfo">
									<label>与你的关系</label>
									<select name="relationship" >
										<option value="0">请选择</option>
										<c:forEach items="${obj.immediaterelationshipenum }" var="map">
											<option value="${map.key }" ${map.key==obj.immediaterelatives.relationship?"selected":"" }>${map.value }</option>
										</c:forEach>
									</select>
								</div>
								<div class="paddingRight groupSelectInfo">
									<label>亲属的身份</label>
									<select id="relativesstatus"  name="relativesstatus">
										<option value="0">请选择</option>
										<c:forEach items="${obj.familyinfoenum }" var="map">
											<option value="${map.key }" ${map.key==obj.immediaterelatives.relativesstatus?"selected":"" }>${map.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
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
                    <input name="spousefirstnameen" id="spousefirstnameen" value="${obj.familyinfo.spousefirstnameen }" type="text" />
                </div>
                <div class="floatRight groupInputInfo">
                    <label><span class="s">*</span> Spouse's Given Names</label>
                    <input name="spouselastnameen" id="spouselastnameen" value="${obj.familyinfo.spouselastnameen }" type="text" />
                </div>
                <div class="clear"></div>
            </div>
			<!--亲属-->
			<div class="familyInfo">
				<div class="paddingLeft groupcheckBoxInfo" >
					<label><span class="s">*</span> father's Surnames</label>
					<input name="fatherfirstnameen" id="fatherfirstnameen" value="${obj.familyinfo.fatherfirstnameen }" type="text"/>
					<!-- <input id="isKnowFatherXing" name="isknowfatherfirstnameen" class="isknowfatherfirstnameen"   v-on:click="isknowfatherfirstname"  value="visaInfo.familyInfo.isknowfatherfirstnameen" type="checkbox" /> -->
				</div>
				<div class="paddingRight groupcheckBoxInfo" >
					<label><span class="s">*</span> father's Given Names </label>
					<input name="fatherlastnameen" id="fatherlastnameen" value="${obj.familyinfo.fatherlastnameen }" type="text" />
					<!-- <input id="isKnowFatherMing" name="isknowfatherlastnameen" class="isknowfatherlastnameen"  v-on:click="isknowfatherlastname" value="visaInfo.familyInfo.isknowfatherlastnameen" type="checkbox" /> -->
				</div>
				<div class="clear" style="height: 118px;"></div>
				<div class="paddingLeft groupcheckBoxInfo">
					<label><span class="s">*</span> Mother's Surnames</label>
					<input id="motherfirstnameen" name="motherfirstnameen" value="${obj.familyinfo.motherfirstnameen }" type="text" />
					<!-- <input id="isKnowMotherXing" name="isknowmotherfirstnameen" class="isknowmotherfirstnameen"  v-on:click="isknowmotherfirstname" value="visaInfo.familyInfo.isknowmotherfirstnameen" type="checkbox" /> -->
				</div>
				<div class="paddingRight groupcheckBoxInfo">
					<label><span class="s">*</span> Mother's Given Names</label>
					<input id="motherlastnameen" name="motherlastnameen" value="${obj.familyinfo.motherlastnameen }" type="text" />
					<!-- <input id="isKnowMotherMing" name="isknowmotherlastnameen" class="isknowmotherlastnameen"  v-on:click="isknowmotherlastname" value="visaInfo.familyInfo.isknowmotherlastnameen" type="checkbox" /> -->
				</div>
				<div class="clear"></div>
				<div class="paddingTop padding-left" style="margin-top: 183px;">
					<div class="directRelatives">
						<!--yes-->
							<div class="directRelativesYesen">
								<div class="floatLeft leftNo groupInputInfo">
									<label><span class="s">*</span> Surnames</label>
									<input name="relativesfirstnameen" id="relativesfirstnameen" value="${obj.immediaterelatives.relativesfirstnameen }" type="text" />
								</div>
								<div class="floatRight groupInputInfo prvPadL">
									<label><span class="s">*</span> Given Names</label>
									<input name="relativeslastnameen" id="relativeslastnameen" value="${obj.immediaterelatives.relativeslastnameen }"  type="text" />
								</div>
								<div class="clear"></div>
								
								
							</div>
						<div class="clear"></div>
						</div>
						<!--NO-->
						
					</div>
					
				</div>
			
			<!--家庭信息END-->

		</div>
		<div class="clear"></div>
		<div style="height: 50px;"></div>
		</form>
	
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffid}';
		var isDisable = '${obj.isDisable}';
		var flag = '${obj.flag}';
	</script>
	<!-- 公共js -->
	<script src="${base}/references/common/js/jquery-1.10.2.js" ></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<!-- 本页js -->
	<%-- <script src="${base}/admin/bigCustomer/visa/openPageYesOrNo.js"></script><!-- 本页面 打开默认开关 js -->
	<script src="${base}/admin/bigCustomer/visa/visaGetInfoList.js"></script><!-- 本页面 获取一对多信息 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfoVue.js"></script><!-- 本页面 Vue加载页面内容 js -->
	<script src="${base}/admin/bigCustomer/visa/visaInfo.js"></script><!-- 本页面 开关交互 js --> --%>
	<script src="${base}/admin/bigCustomer/visa/initDatetimepicker.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面 初始化时间插件 js -->
	<script src="${base}/admin/bigCustomer/updateFamilyinfo.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面  js -->
	<script src="${base}/references/common/js/pinyin.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
		
	/* var spouseaddress = '${obj.familyinfo.spouseaddress}';
	if(spouseaddress == 5){
		$(".otherSpouseInfo").show();
	} */
	
		//跳转到护照信息页
		function passportInfoBtn(){
			save(2);
		}
		//跳转到工作教育页
		function workinfoBtn(){
			save(3);
		}
		
	</script>
</body>
</html>

