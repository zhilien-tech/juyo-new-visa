<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE HTML>
<html lang="en-US" id="updateHtml">
<head>
	<meta charset="UTF-8">
	<title>更新基本信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appAddStaff.css?v='20180510'">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/appUpdateStaff.css?v='20180510'">
	<style>
		.row{position: relative;}
		.xx-en{
			top: 0;
			left: 425px;
			position: absolute;
		}
		.datetimepicker {
			top: 982.672px!important;
		}
	</style>
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="leftNav" onclick="passportBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第一步</i>
			<span></span>
		</a>
		<a id="toVisa" class="rightNav" onclick="toVisaInfo();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第三步</i>
			<span></span>
		</a>
		
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">基本信息</span> 
				<input autocomplete="new-password" id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input autocomplete="new-password" id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1)" />
			</div>
			<div class="modal-body">
				<div class="dislogHide"></div>
				<div class="tab-content row">
					<input autocomplete="new-password" id="comId" name="comid" type="hidden" value="${obj.applicant.comid }">
					<input autocomplete="new-password" id="userId" name="userid" type="hidden" value="${obj.applicant.userid }">
					<input autocomplete="new-password" id="staffId" name="staffid" type="hidden" value="${obj.staffid }">
					<div class="col-sm-12 padding-right-0">
						<!-- start 身份证 正面 -->
							<div class="col-xs-4 pictures" style="margin-right: 30px;">
								<div class="form-group pictureTop">
									<div class="uploadInfo">
										<span class="promptInfo">点击上传身份证正面</span>
										<input autocomplete="new-password" id="cardFront" name="cardfront" type="hidden" value="${obj.front.url }"/>
										<img id="imgShow" name="sqimg" alt="" src="${obj.front.url }" >
										<!-- <input autocomplete="new-password" id="uploadFileImg" name="uploadfile" class="btn btn-primary btn-sm" type="file" value="上传" /> -->
										<!-- <i class="delete" onclick="deleteApplicantFrontImg();"></i> -->
									</div>
								</div>
								<div class="front has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">身份证正面必须上传</small>
								</div>
							</div>
						
						<!-- end 身份证 正面 -->

						<!-- start 二寸免冠照片 -->
							<div class="col-xs-3 picturesInch" style="margin-left: 150px;">
								<div class="form-group pictureTop">
									<div class="uploadInfo">
										<span class="inchInfo">二寸免冠照片</span>
										<input autocomplete="new-password" id="cardInch" name="twoinchphoto" type="hidden" value="${obj.twoinch.url }"/>
										<img id="imgInch" name="imgInch" alt="" src="${obj.twoinch.url }" >
										<!-- <input autocomplete="new-password" id="uploadFileInchImg" name="uploadFileInchImg" class="btn btn-primary btn-sm" type="file"  value="上传"/> -->
										<!-- <i class="delete" onclick="deleteApplicantInchImg()"></i> -->
									</div>
								</div>
								 <div class="front has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blockInch" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">二寸免冠照片必须上传</small>
								</div>
							</div>
						<!-- 验证 -->
						<!-- end 二寸免冠照片 -->
						
					</div>
					<div class="row" style="padding-left: 50px;">
									<!-- 姓/拼音 -->
										<div class="col-sm-6">
												<div class="form-group" style="position:relative;">
												<label><span>*</span>姓/拼音</label> <input autocomplete="new-password" id="firstName"
													name="firstname" type="text" class="form-control input-sm " tabIndex="1"
													placeholder=" " value="${obj.basicinfo.firstname }" />
													<input autocomplete="new-password" type="text" id="firstNameEn" style="position:absolute;top:32px;border:none;left:150px;"  name="firstnameen" value="${obj.firstnameen }"/>
												<!-- <i class="bulb"></i> -->
											</div>
												<!-- <i class="bulb"></i> -->

									</div>
					</div>
					<div class="row" style="padding-left: 50px;">
							<!-- 名/拼音 -->
							<div class="col-sm-6">
								<div class="form-group" style="position:relative;">
									<label><span>*</span>名/拼音</label> <input autocomplete="new-password" id="lastName"
										name="lastname" type="text" class="form-control input-sm" tabIndex="2"
										placeholder=" " value="${obj.basicinfo.lastname }" />
										<input autocomplete="new-password" type="text" id="lastNameEn" style="position:absolute;top:32px;border:none;left:150px;" name="lastnameen" value="${obj.lastnameen }"/>

									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>

					<div class="row">
							<div class="col-sm-3 col-sm-offset-1 " style="margin-left: 50px;padding-right: 35px;">
									<div class="form-group">
										<label ><span>*</span>性别</label>
										<select class="form-control input-sm selectHeight" id="sex" name="sex"  tabIndex="3">
											<option value="男" ${obj.basicinfo.sex == "男"?"selected":"" }>男</option>
											<option value="女" ${obj.basicinfo.sex == "女"?"selected":"" }>女</option>
										</select>
									</div>
								</div>
					</div>

					<div class="col-sm-6 padding-right-0">
						<!-- 手机号和邮箱 -->
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>手机号
									</label> 
									<input autocomplete="new-password" id="telephone" name="telephone" type="text"  class="form-control input-sm"  tabIndex="4" placeholder=" " value="${obj.basicinfo.telephone }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label> 
									<input autocomplete="new-password" id="email" name="email" type="text"  class="form-control input-sm" placeholder=" "  tabIndex="5" value="${obj.basicinfo.email }" />
								</div>
							</div>
						</div>
						<!-- 手机号和邮箱 END -->
						
						<!-- 身份证 -->
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>公民身份证</label> 
									<input autocomplete="new-password" id="cardId" name="cardId"  type="text" class="form-control input-sm"  tabIndex="6" placeholder=" " value="${obj.basicinfo.cardId }" />
								</div>
							</div>
						</div>
					
						<!-- 现居住地是否与身份证相同  和  现居住地址城市 -->
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group" id="provinceDiv">
									<label>现居住地</label>
									<%-- <input autocomplete="new-password" type="hidden" name="cardprovince" id="cardProvince" value="${obj.applicant.cardprovince }"/>
									<input autocomplete="new-password" type="hidden" name="cardcity" id="cardCity" value="${obj.applicant.cardcity }"/>
									<input autocomplete="new-password" type="hidden" id="sameAddress" value=""/>
									<input autocomplete="new-password" class="nowProvince" type="checkbox" name="addressIssamewithcard" value="1" />  --%>
									<input autocomplete="new-password" id="province" autocomplete="new-password" name="province" onchange="translateZhToEn(this,'provinceen','')" type="text" class="form-control input-sm"  tabIndex="12" placeholder="省" value="${obj.basicinfo.province }" />
									<input id="provinceen" name="provinceen" value="${obj.basicinfo.provinceen }" type="hidden"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" id="cityDiv">
									<label>现居住地址城市</label> 
									<input autocomplete="new-password" id="city" name="city" autocomplete="new-password" type="text" onchange="translateZhToEn(this,'cityen','')" class="form-control input-sm" tabIndex="13" placeholder="市" value="${obj.basicinfo.city }" />
									<input id="cityen" name="cityen" value="${obj.basicinfo.cityen }" type="hidden" />
								</div>
							</div>
						</div>
						<!-- 详细地址 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> 
									<input autocomplete="new-password" id="detailedAddress" name="detailedaddress" onchange="translateZhToEn(this,'detailedAddressen','')" type="text"  tabIndex="14" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" value="${obj.basicinfo.detailedaddress }" />
								</div>
							</div>
							<!-- <div class="col-sm-12 xx-en">
								<div class="form-group">
									<label><span>*</span>Street Address</label>
									<input autocomplete="new-password" id="" name="" onchange="translateZhToEn(this,'detailedAddressen','')" type="text"  tabIndex="14" class="form-control input-sm" placeholder="Street Address" value="" />
								</div>
							</div> -->
						</div>
						<!-- 婚姻状况  和说明 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>婚姻状况
									</label> 
									<select id="marrystatus" name="marrystatus" class="form-control input-sm selectHeight"  tabIndex="15">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.marrystatusenum}">
												<option value="${map.key}" ${map.key==obj.basicinfo.marrystatus?'selected':''}>${map.value}</option>
											</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group none">
									<label>说明</label> 
									<input autocomplete="new-password" id="marryexplain" onchange="translateZhToEn(this,'marryexplainen','')" name="marryexplain" type="text" class="form-control input-sm" value="${obj.applicant.marryexplain }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group marryexplain">
									<label>结婚日期</label> 
									<input autocomplete="new-password" id="marrieddate" name="marrieddate" type="text" class="form-control input-sm" value="${obj.marrieddate }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group marryexplain">
									<label>离婚日期</label> 
									<input autocomplete="new-password" id="divorcedate" name="divorcedate" type="text" class="form-control input-sm" value="${obj.divorcedate }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group marryexplain">
									<label>离婚国家</label> 
									<input autocomplete="new-password" id="divorcecountry" onchange="translateZhToEn(this,'divorcecountryen','')" name="divorcecountry" type="text" class="form-control input-sm" value="${obj.familyinfo.divorcecountry }"/>
									<input type="hidden" id="divorcecountryen" name="divorcecountryen" value="${obj.familyinfo.divorcecountryen }"/>
								</div>
							</div>
						</div>
						<!-- 出生日期 && 出生国家 -->
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>出生日期
									</label> 
									<input autocomplete="new-password" id="birthday" name="birthday" type="text"  class="form-control input-sm" tabIndex="16" placeholder=" " value="${obj.birthday }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group"> 
									<label>
										<span>*</span>出生国家
									</label>
									<input autocomplete="new-password" id="birthcountry" onchange="translateZhToEn(this,'birthcountryen','')" name="birthcountry" type="text"  class="form-control input-sm" placeholder=" "  tabIndex="17" value="${obj.basicinfo.birthcountry }" />
									<input name="birthcountryen" id="birthcountryen" value="${obj.basicinfo.birthcountryen }" type="hidden"/>
								</div>
							</div>
						</div>

						<!-- 出生省 && 出生城市 -->
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>出生省
									</label> 
									<input autocomplete="new-password" onchange="translateZhToEn(this,'cardprovinceen','')" id="cardprovince" name="cardprovince" type="text"  class="form-control input-sm"  tabIndex="18" placeholder=" " value="${obj.basicinfo.cardprovince }" />
									<input id="cardprovinceen" name="cardprovinceen" value="${obj.basicinfo.cardprovinceen }" type="hidden"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group"> 
									<label>
										<span>*</span>出生城市
									</label>
									<input autocomplete="new-password" onchange="translateZhToEn(this,'cardcityen','')" id="cardcity" name="cardcity" type="text"  class="form-control input-sm" placeholder=" "  tabIndex="19" value="${obj.basicinfo.cardcity }" />
									<input id="cardcityen" name="cardcityen" value="${obj.basicinfo.cardcityen }" type="hidden"/>
								</div>
							</div>
						</div>
						
						<!-- 邮寄地址 -->
						<div class="row">
							<div class="col-sm-10 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>邮寄地址是否跟居住地址一样</label>
									<div>
										<span class="nameBeforeYes">
											<input autocomplete="new-password" type="radio" name="ismailsamewithlive" class="mailinfo" value="1"/>是
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="ismailsamewithlive" class="mailinfo" value="2" checked />否
										</span>
									</div>
								</div>
							</div>
						
						</div>
						
						<div class="row mailingAddressTrue">
							<!-- 姓/名 -->
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>街道地址</label> 
									<input autocomplete="new-password" onchange="translateZhToEn(this,'mailaddressen','')" id="mailaddress"  name="mailaddress" type="text" class="form-control input-sm " tabIndex="20" value="${obj.basicinfo.mailaddress }"/>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>市</label> 
									<input autocomplete="new-password" onchange="translateZhToEn(this,'mailcityen','')" id="mailcity" name="mailcity"  type="text" class="form-control input-sm" tabIndex="21" value="${obj.basicinfo.mailcity }"/>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>省</label> 
									<input autocomplete="new-password" onchange="translateZhToEn(this,'mailprovinceen','')" id="mailprovince" name="mailprovince"  type="text" class="form-control input-sm" tabIndex="21" value="${obj.basicinfo.mailprovince }"/>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>国家</label> 
									<input autocomplete="new-password" id="mailcountry" onchange="translateZhToEn(this,'mailcountryen','')" name="mailcountry"  type="text" class="form-control input-sm" tabIndex="21" value="${obj.basicinfo.mailcountry }"/>
								</div>
							</div>
						</div>
						

						<!-- 曾用名 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-10 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes">
											<input autocomplete="new-password" type="radio" name="hasothername" class="usedBefore" value="1"/>是
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="hasothername" class="usedBefore" value="2" checked />否
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row usedBeforeTrue">
							<!-- 姓/名 -->
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>姓</label> 
									<input autocomplete="new-password" id="otherfirstname"  name="otherfirstname" type="text" class="form-control input-sm " tabIndex="20" value="${obj.basicinfo.otherfirstname }"/>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>名</label> 
									<input autocomplete="new-password" id="otherlastname" name="otherlastname"  type="text" class="form-control input-sm" tabIndex="21" value="${obj.basicinfo.otherlastname }"/>
								</div>
							</div>
						</div>
						<!-- 曾用国籍 -->
						<%-- <div class="row">	
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input autocomplete="new-password" type="radio" name="hasothernationality" class="usedNationality" value="1" />是
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="hasothernationality" class="usedNationality"  value="2" checked />否
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1 usedNationalityTrue">
								<div class="form-group" id="">
									<label>国籍</label> 
									<input autocomplete="new-password" id="nationality" name="nationality" autocomplete="new-password" onchange="translateZhToEn(this,'nationalityen','')" class="form-control input-sm" value="${obj.applicant.nationality }"/>
								</div>
							</div>
						</div>
						<!-- 您是否与上述国家/地区(国籍)意外的国家/地区的永久居民 -->
						<div class="row">	
							<label class="EngLabel">您是否与上述国家/地区(国籍)以外的国家/地区的永久居民</label>
							<div class=" col-sm-5 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<div>
										<span class="onceIDYes">
											<input autocomplete="new-password" type="radio" name="isothercountrypermanentresident" class="permanent" value="1" />是
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="isothercountrypermanentresident" class="permanent"  value="2" checked />否
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0 permanentTrue">
								<div class="form-group">
									<input autocomplete="new-password" id="othercountry" name="othercountry" onchange="translateZhToEn(this,'othercountryen','')" type="text" class="form-control input-sm" value="${obj.applicant.othercountry }"/>
								</div>
							</div>
						</div> --%>
						
					</div>
					<!-- 左侧结束 -->
					<div class="col-sm-6 padding-right-0">
						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>
						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>
						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>
						
						<!-- 详细地址 -->
						<div class="row" style="    margin-top: 8px;">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Street Address</label> 
									<input autocomplete="new-password" id="detailedAddressen" name="detailedaddressen" type="text" class="form-control input-sm" placeholder=" " value="${obj.basicinfo.detailedaddressen }" />
								</div>
							</div>
						</div>

						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>

						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>

						<div class="row" style="opacity: 0;">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<div class="form-control input-sm"></div>
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-sm-10 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Is your Mailing Address the same as your Home Address</label> 
									<div>
										<span class="nameBeforeYes">
											<input autocomplete="new-password" type="radio" name="ismailsamewithliveen" class="mailinfoUS" value="1"/>yes
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="ismailsamewithliveen" class="mailinfoUS" value="2" checked />No
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row mailingAddressUSTrue">
							<!-- 姓/名 -->
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Street Address</label> 
									<input autocomplete="new-password" id="mailaddressen" name="mailaddressen" type="text" class="form-control input-sm " value="${obj.basicinfo.mailaddressen }" />
								</div>
							</div>
							
							<input autocomplete="new-password" id="mailcityen" name="mailcityen" type="hidden" class="form-control input-sm" value="${obj.basicinfo.mailcityen }" />
							<input autocomplete="new-password" id="mailprovinceen" name="mailprovinceen" type="hidden" class="form-control input-sm" value="${obj.basicinfo.mailprovinceen }" />
							<input autocomplete="new-password" id="mailcountryen" name="mailcountryen" type="hidden" class="form-control input-sm" value="${obj.basicinfo.mailcountryen }" />
							
							<%-- <div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>City </label> 
									<input autocomplete="new-password" id="mailcityen" name="mailcityen" type="text" class="form-control input-sm" value="${obj.basicinfo.mailcityen }" />
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>State/Province  </label> 
									<input autocomplete="new-password" id="mailprovinceen" name="mailprovinceen" type="text" class="form-control input-sm" value="${obj.basicinfo.mailprovinceen }" />
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Country/Region</label> 
									<input autocomplete="new-password" id="mailcountryen" name="mailcountryen" type="text" class="form-control input-sm" value="${obj.basicinfo.mailcountryen }" />
								</div>
							</div> --%>
						</div>
						
						<!-- 曾用名 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国籍(或公民身份) -->
							<div class="col-sm-10 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Have you ever used other names</label> 
									<div>
										<span class="nameBeforeYes">
											<input autocomplete="new-password" type="radio" name="hasothernameen" class="usedBeforeUS" value="1"/>yes
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="hasothernameen" class="usedBeforeUS" value="2" checked />No
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row usedBeforeUSTrue">
							<!-- 姓/名 -->
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Other Surnames Used</label> 
									<input autocomplete="new-password" id="otherfirstnameen" name="otherfirstnameen" type="text" class="form-control input-sm " value="${obj.basicinfo.otherfirstnameen }" />
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Other Given Names Used</label> 
									<input autocomplete="new-password" id="otherlastnameen" name="otherlastnameen" type="text" class="form-control input-sm" value="${obj.basicinfo.otherlastnameen }" />
								</div>
							</div>
						</div>
						<!-- 曾用国籍 -->
						<%-- <div class="row">
							<label class="EngLabel">Do you hold or have you held any nationality other than the one indicated above on nationality</label> 	
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<div>
										<span class="onceIDYes">
											<input autocomplete="new-password" type="radio" name="hasothernationalityen" class="usedNationalityUS" value="1" />yes
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="hasothernationalityen" class="usedNationalityUS"  value="2" checked />No
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1 usedNationalityUSTrue">
								<div class="form-group" id="">
									<input autocomplete="new-password" id="nationalityen" autocomplete="new-password" name="nationalityen" class="form-control input-sm" value="${obj.applicant.nationalityen }"/>
								</div>
							</div>
						</div>
						<!-- 您是否与上述国家/地区(国籍)意外的国家/地区的永久居民 -->
						<div class="row">	
							<label class="EngLabel">Are you a permanent resident of a country/region other than your country/region of origin (nationality) indicated above </label> 
							<div class=" col-sm-5 col-sm-offset-1 padding-right-0 ">
								<div class="form-group">
									<div>
										<span class="onceIDYes">
											<input autocomplete="new-password" type="radio" name="isothercountrypermanentresidenten" class="permanentUS" value="1" />yes
										</span>
										<span>
											<input autocomplete="new-password" type="radio" name="isothercountrypermanentresidenten" class="permanentUS"  value="2" checked />No
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0 permanentUSTrue">
								<div class="form-group">
									<input autocomplete="new-password" id="othercountryen" name="othercountryen" type="text" class="form-control input-sm" value="${obj.applicant.othercountryen }" />
								</div>
							</div>
						</div> --%>
						
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffid}';
		var passportId = '${obj.passportId}';
		var infoType = '${obj.infoType}';
		var marrystatus = '${obj.marryStatus}';
		var marrystatusen = '${obj.marryStatusEn}';
		var isDisable = '${obj.isDisable}';
		var flag = '${obj.flag}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
	<script type="text/javascript" src="${base}/admin/bigCustomer/updateStaff.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	
		$(function(){
			//页面不可编辑
			if(isDisable == 1){
				$(".modal-body").attr('disabled', true);
				$(".dislogHide").show();
				$("#addBtn").hide();
			}
			
			var nation = '${obj.applicant.hasothernationality}';
			var nationen = '${obj.applicant.hasothernationalityen}';
			var otherName = '${obj.basicinfo.hasothername}';
			var otherNameen = '${obj.basicinfo.hasothernameen}';
			var ismailsamewithlive = '${obj.basicinfo.ismailsamewithlive}';
			var ismailsamewithliveen = '${obj.basicinfo.ismailsamewithliveen}';
			var address = '${obj.applicant.addressIssamewithcard}';
			var addressen = '${obj.applicant.addressIssamewithcarden}';
			$("input[name='hasothernationality'][value='"+nation+"']").attr("checked",'checked');
			$("input[name='hasothername'][value='"+otherName+"']").attr("checked",'checked');
			$("input[name='hasothernationalityen'][value='"+nationen+"']").attr("checked",'checked');
			$("input[name='hasothernameen'][value='"+otherNameen+"']").attr("checked",'checked');
			$("input[name='ismailsamewithlive'][value='"+ismailsamewithlive+"']").attr("checked",'checked');
			$("input[name='ismailsamewithliveen'][value='"+ismailsamewithliveen+"']").attr("checked",'checked');
			//是否有其他国籍
			if(nation == 1){
				$(".nameBeforeTop").css('float','none');
				$(".usedNationalityTrue").show();
				$(".onceIDTop").css({'float':'left','margin-left':'45px','padding':'0px'});
			}else {
				$(".usedNationalityTrue").hide();
			}
			if(nationen == 1){
				$(".usedNationalityUSTrue").show();
			}else {
				$(".usedNationalityUSTrue").hide();
			}
			//曾用名
			if(otherName == 1){
				$(".usedBeforeTrue").show();
			}else {
				$(".usedBeforeTrue").hide();
			}
			if(otherNameen == 1){
				$(".usedBeforeUSTrue").show();
			}else {
				$(".usedBeforeUSTrue").hide();
			}
			//邮寄地址
			if(ismailsamewithlive == 2){
				$(".mailingAddressTrue").show();
			}else {
				$(".mailingAddressTrue").hide();
			}
			if(ismailsamewithliveen == 2){
				$(".mailingAddressUSTrue").show();
			}else {
				$(".mailingAddressUSTrue").hide();
			}
			//地址是否与身份证相同
			if(address == 1){
				var boxObj = $("input:checkbox[name='addressIssamewithcard']").attr("checked",true);
			}else{
				var boxObj = $("input:checkbox[name='addressIssamewithcard']").attr("checked",false);
			}
			if(addressen == 1){
				var boxObj = $("input:checkbox[name='addressIssamewithcarden']").attr("checked",true);
			}else{
				var boxObj = $("input:checkbox[name='addressIssamewithcarden']").attr("checked",false);
			}
		});
		//图片上的删除按钮
		var front = $("#cardFront").val();
		var back = $("#cardBack").val();
		var inch = $("#cardInch").val();
		if(front != ""){
			$("#uploadFileImg").siblings("i").css("display","block");
		}else{
			$("#uploadFileImg").siblings("i").css("display","none");
		}
		
		if(back != ""){
			$("#uploadFileImgBack").siblings("i").css("display","block");
		}else{
			$("#uploadFileImgBack").siblings("i").css("display","none");
		} 
		
		if(inch != ""){
			$("#uploadFileInchImg").siblings("i").css("display","block");
		}else{
			$("#uploadFileInchImg").siblings("i").css("display","none");
		} 
		
		//点击身份证图片上的删除按钮
		function deleteApplicantFrontImg(){
			$('#cardFront').val("");
			$('#imgShow').attr('src', "");
			$("#uploadFileImg").siblings("i").css("display","none");
		}
		function deleteApplicantBackImg(){
			$('#cardBack').val("");
			$('#imgShowBack').attr('src', "");
			$("#uploadFileImgBack").siblings("i").css("display","none");
		}
		function deleteApplicantInchImg(){
			$('#cardInch').val("");
			$('#imgInch').attr('src', "");
			$("#uploadFileInchImg").siblings("i").css("display","none");
		}
		
		
		$(function(){
			$("#validStartDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#validEndDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"bottom-right",//显示位置
				minView: "month"//只显示年月日
			});
			$("#birthday").datetimepicker({
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
			$("#marrieddate").datetimepicker({
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
			$("#divorcedate").datetimepicker({
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
		});
		function passportBtn(){
			saveApplicant(2);
		}
		function toVisaInfo(){
			saveApplicant(3);
		}
	</script>
</body>
</html>
