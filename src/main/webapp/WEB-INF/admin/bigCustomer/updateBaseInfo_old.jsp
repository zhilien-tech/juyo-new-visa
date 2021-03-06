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
</head>
<body>
	<div class="modal-content">
		<a id="toPassport" class="leftNav" onclick="passportBtn();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第二步</i>
			<span></span>
		</a>
		<a id="toVisa" class="rightNav" onclick="toVisaInfo();">
			<i style="position:absolute;top:20%;width:1.5em;left:10px;font-family: 'microsoft yahei';">第四步</i>
			<span></span>
		</a>
		
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">基本信息</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm btn-margin" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right btn-margin" value="保存" onclick="saveApplicant(1)" />
			</div>
			<div class="modal-body">
				<div class="dislogHide"></div>
				<div class="tab-content row">
					<input id="comId" name="comid" type="hidden" value="${obj.applicant.comid }">
					<input id="userId" name="userid" type="hidden" value="${obj.applicant.userid }">
					<input id="staffId" name="id" type="hidden" value="${obj.staffId }">
					<div class="col-sm-12 padding-right-0">
						<!-- start 身份证 正面 -->
							<div class="col-xs-4 pictures">
								<div class="form-group pictureTop">
									<div class="uploadInfo">
										<span class="promptInfo">点击上传身份证正面</span>
										<input id="cardFront" name="cardfront" type="hidden" value="${obj.front.url }"/>
										<img id="imgShow" name="sqimg" alt="" src="${obj.front.url }" >
										<input id="uploadFileImg" name="uploadfile" class="btn btn-primary btn-sm" type="file" value="上传" />
										<i class="delete" onclick="deleteApplicantFrontImg();"></i>
									</div>
								</div>
								<div class="front has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">身份证正面必须上传</small>
								</div>
							</div>
						
						<!-- end 身份证 正面 -->
						<!-- start 身份证 反面 -->
							<div class="col-xs-4 picturesBack">
								<div class="form-group pictureTop">
									<div class="uploadInfo">
										<span class="promptInfo">点击上传身份证背面</span>
										<input id="cardBack" name="cardback" type="hidden" value="${obj.back.url }"/>
										<img id="imgShowBack" alt="" src="${obj.back.url }" >
										<input id="uploadFileImgBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="上传"/>
										<i class="delete" onclick="deleteApplicantBackImg();"></i>
									</div>
								</div>
								<div class="front has-error" style="width:100%; height:20px; border:0 !important; color:red;margin:0px 0 0 0px !important">
									<small class="help-blockBack" data-bv-validator="notEmpty" data-bv-for="cardBack" data-bv-result="IVVALID" style="display: none;">身份证背面必须上传</small>
								</div> 
							</div>
						<!-- end 身份证 反面 -->
						<!-- start 二寸免冠照片 -->
							<div class="col-xs-3 picturesInch">
								<div class="form-group pictureTop">
									<div class="uploadInfo">
										<span class="inchInfo">二寸免冠照片</span>
										<input id="cardInch" name="twoinchphoto" type="hidden" value="${obj.twoinch.url }"/>
										<img id="imgInch" name="imgInch" alt="" src="${obj.twoinch.url }" >
										<input id="uploadFileInchImg" name="uploadFileInchImg" class="btn btn-primary btn-sm" type="file"  value="上传"/>
										<i class="delete" onclick="deleteApplicantInchImg()"></i>
									</div>
								</div>
								 <div class="front has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blockInch" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">二寸免冠照片必须上传</small>
								</div>
							</div>
						<!-- 验证 -->
						<!-- end 二寸免冠照片 -->
						
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
									<input id="telephone" name="telephone" type="text"  class="form-control input-sm"  tabIndex="4" placeholder=" " value="${obj.applicant.telephone }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>邮箱</label> 
									<input id="email" name="email" type="text"  class="form-control input-sm" placeholder=" "  tabIndex="5" value="${obj.applicant.email }" />
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
									<input id="cardId" name="cardId"  type="text" class="form-control input-sm"  tabIndex="6" placeholder=" " value="${obj.applicant.cardId }" />
								</div>
							</div>
						</div>
						<!-- 民族 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>卡号</label> 
									<input id="cardnum" name="cardnum"  type="text" class="form-control input-sm"  value="${obj.applicant.cardnum }" />
								</div>
							</div>
							<!-- 民族 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>民族</label> 
									<input id="nation" name="nation" onchange="translateZhToEn(this,'nationen','')" type="text" class="form-control input-sm"  value="${obj.applicant.nation }" />
								</div>
							</div>
						</div>
						<!-- 住址 -->
						<div class="row">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>住址</label> 
									<input id="address" name="address" onchange="translateZhToEn(this,'addressen','')" type="text" class="form-control input-sm"  tabIndex="9" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" value="${obj.applicant.address }" />
								</div>
							</div>
						</div>
						<!-- 现居住地是否与身份证相同  和  现居住地址城市 -->
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group" id="provinceDiv">
									<label>现居住地是否与身份证相同</label>
									<input type="hidden" name="cardprovince" id="cardProvince" value="${obj.applicant.cardprovince }"/>
									<input type="hidden" name="cardcity" id="cardCity" value="${obj.applicant.cardcity }"/>
									<input type="hidden" id="sameAddress" value=""/>
									<input class="nowProvince" type="checkbox" name="addressIssamewithcard" value="1" /> 
									<input id="province" autocomplete="off" name="province" onchange="translateZhToEn(this,'provinceen','')" type="text" class="form-control input-sm"  tabIndex="12" placeholder="省" value="${obj.applicant.province }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" id="cityDiv">
									<label>现居住地址城市</label> 
									<input id="city" name="city" autocomplete="off" type="text" onchange="translateZhToEn(this,'cityen','')" class="form-control input-sm" tabIndex="13" placeholder="市" value="${obj.applicant.city }" />
								</div>
							</div>
						</div>
						<!-- 详细地址 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> 
									<input id="detailedAddress" name="detailedaddress" onchange="translateZhToEn(this,'detailedAddressen','')" type="text"  tabIndex="14" class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间" value="${obj.applicant.detailedaddress }" />
								</div>
							</div>
						</div>
						<!-- 婚姻状况  和说明 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>婚姻状况
									</label> 
									<select id="marrystatus" name="marrystatus" class="form-control input-sm selectHeight">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.marryStatusEnum}">
												<option value="${map.key}" ${map.key==obj.marryStatus?'selected':''}>${map.value}</option>
											</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group marryexplain">
									<label>说明</label> 
									<input id="marryexplain" onchange="translateZhToEn(this,'marryexplainen','')" name="marryexplain" type="text" class="form-control input-sm" value="${obj.applicant.marryexplain }"/>
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
											<input type="radio" name="hasothername" class="usedBefore" value="1"/>是
										</span>
										<span>
											<input type="radio" name="hasothername" class="usedBefore" value="2" checked />否
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
									<input id="otherfirstname"  name="otherfirstname" type="text" class="form-control input-sm " value="${obj.applicant.otherfirstname }"/>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>名</label> 
									<input id="otherlastname" name="otherlastname"  type="text" class="form-control input-sm" value="${obj.applicant.otherlastname }"/>
								</div>
							</div>
						</div>
						<!-- 曾用国籍 -->
						<div class="row">	
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasothernationality" class="usedNationality" value="1" />是
										</span>
										<span>
											<input type="radio" name="hasothernationality" class="usedNationality"  value="2" checked />否
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1 usedNationalityTrue">
								<div class="form-group" id="">
									<label>国籍</label> 
									<input id="nationality" name="nationality" autocomplete="off" onchange="translateZhToEn(this,'nationalityen','')" class="form-control input-sm" value="${obj.applicant.nationality }"/>
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
											<input type="radio" name="isothercountrypermanentresident" class="permanent" value="1" />是
										</span>
										<span>
											<input type="radio" name="isothercountrypermanentresident" class="permanent"  value="2" checked />否
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0 permanentTrue">
								<div class="form-group">
									<input id="othercountry" name="othercountry" onchange="translateZhToEn(this,'othercountryen','')" type="text" class="form-control input-sm" value="${obj.applicant.othercountry }"/>
								</div>
							</div>
						</div>
						<!-- 国家注册号码 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>国家注册号码</label> 
									<input id="nationalidentificationnumber"  name="nationalidentificationnumber" type="text" class="form-control input-sm" value="${obj.applicant.nationalidentificationnumber }"/>
								</div>
								
								<div class="countryNum has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-countryNum" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">国家注册号码不能为空</small>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMargin">
								<div class="form-group">
									<input type="checkbox" class="vertical isidentificationnumberapply" name="isidentificationnumberapply" value="1"/>
									<label>不适用</label> 
								</div>
							</div>
						</div>
						<!-- 美国社会安全号码 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>美国社会安全号码</label> 
									<input id="socialsecuritynumber"  name="socialsecuritynumber" type="text" class="form-control input-sm" value="${obj.applicant.socialsecuritynumber }" />
								</div>
								<div class="safeNum has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blocksafe" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">美国社会安全号码</small>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMargin">
								<div class="form-group">
									<input type="checkbox" class="vertical issecuritynumberapply" name="issecuritynumberapply" value="1"/>
									<label>不适用</label> 
								</div>
							</div>
						</div>
						<!-- 美国纳税人证件号 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>美国纳税人证件号</label> 
									<input id="taxpayernumber"  name="taxpayernumber" type="text" class="form-control input-sm" value="${obj.applicant.taxpayernumber }" />
								</div>
								<div class="safepay has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-ratepaying" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">美国纳税人证件号</small>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMargin">
								<div class="form-group">
									<input type="checkbox" class="vertical istaxpayernumberapply" name="istaxpayernumberapply" value="1"/>
									<label>不适用</label> 
								</div>
							</div>
						</div>
					</div>
					<!-- 左侧结束 -->
					<div class="col-sm-6 padding-right-0">
						<!-- 手机号和邮箱 -->
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>Phone Number
									</label> 
									<input id="telephoneen" name="telephoneen" type="text" class="form-control input-sm" value="${obj.telephoneen }"/>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Email Address</label> 
									<input id="emailen" name="emailen" type="text" class="form-control input-sm" value="${obj.emailen }"/>
								</div>
							</div>
						</div>
						<!-- 手机号和邮箱 END -->
						
						<!-- 身份证 -->
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>The identity card of the people's Republic of China</label> 
									<input id="cardIden" name="cardIden" type="text" class="form-control input-sm" value="${obj.applicant.cardIden }" />
								</div>
							</div>
						</div>
						<!-- 民族 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Cardnum</label> 
									<input id="cardnumen" name="cardnumen" type="text" class="form-control input-sm" value="${obj.applicant.cardnumen }" />
								</div>
							</div>
							<!-- 民族 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Nationality</label> 
									<input id="nationen" name="nationen" type="text" class="form-control input-sm" value="${obj.applicant.nationen }" />
								</div>
							</div>
						</div>
						<!-- 住址 -->
						<div class="row">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Address</label> 
									<input id="addressen" name="addressen" type="text" class="form-control input-sm" value="${obj.applicant.addressen }"/>
								</div>
							</div>
						</div>
						<!-- 现居住地是否与身份证相同  和  现居住地址城市 -->
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<label class="EngLabel">
							Is your Mailing Address the same as your Home Address
							<input class="nowProvinceen" type="checkbox" name="addressIssamewithcarden" value="1" />
							</label>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group" id="provinceDiv">
									<input type="hidden" name="cardprovinceen" id="cardProvinceen" />
									<input type="hidden" name="cardcityen" id="cardCityen" />
									<input type="hidden" id="sameAddress" value=""/>
									<input id="provinceen" name="provinceen" autocomplete="off" type="text" class="form-control input-sm" placeholder="省" value="${obj.applicant.provinceen }" />
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group" id="cityDiv">
									<input id="cityen" name="cityen" autocomplete="off" type="text" class="form-control input-sm" tabIndex="13" placeholder="市" value="${obj.applicant.cityen }" />
								</div>
							</div>
						</div>
						<!-- 详细地址 -->
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>Street Address</label> 
									<input id="detailedAddressen" name="detailedaddressen" type="text"  tabIndex="14" class="form-control input-sm" placeholder=" " value="${obj.applicant.detailedaddressen }" />
								</div>
							</div>
						</div>
						<!-- 婚姻状况  和说明 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>
										<span>*</span>Marital Status
									</label>
									<select id="marrystatusen" name="marrystatusen" class="form-control input-sm selectHeight">
											<option value="">please choose</option>
											<c:forEach var="map" items="${obj.marryStatusEnEnum}">
												<option value="${map.key}" ${map.key==obj.marryStatusEn?'selected':''}>${map.value}</option>
											</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group marryexplainen">
									<label>Expain</label> 
									<input id="marryexplainen" name="marryexplainen" type="text" class="form-control input-sm" value="${obj.applicant.marryexplainen }" />
								</div>
							</div>
						</div>
						<!-- 曾用名 -->
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国籍(或公民身份) -->
							<div class="col-sm-10 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Have you ever used other names</label> 
									<div>
										<span class="nameBeforeYes">
											<input type="radio" name="hasothernameen" class="usedBeforeUS" value="1"/>yes
										</span>
										<span>
											<input type="radio" name="hasothernameen" class="usedBeforeUS" value="2" checked />No
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
									<input id="otherfirstnameen" name="otherfirstnameen" type="text" class="form-control input-sm " value="${obj.applicant.otherfirstnameen }" />
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<label>Other Given Names Used</label> 
									<input id="otherlastnameen" name="otherlastnameen" type="text" class="form-control input-sm" value="${obj.applicant.otherlastnameen }" />
								</div>
							</div>
						</div>
						<!-- 曾用国籍 -->
						<div class="row">
							<label class="EngLabel">Do you hold or have you held any nationality other than the one indicated above on nationality</label> 	
							<div class="col-sm-5 padding-right-0 col-sm-offset-1">
								<div class="form-group">
									<div>
										<span class="onceIDYes">
											<input type="radio" name="hasothernationalityen" class="usedNationalityUS" value="1" />yes
										</span>
										<span>
											<input type="radio" name="hasothernationalityen" class="usedNationalityUS"  value="2" checked />No
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 padding-right-0 col-sm-offset-1 usedNationalityUSTrue">
								<div class="form-group" id="">
									<input id="nationalityen" autocomplete="off" name="nationalityen" class="form-control input-sm" value="${obj.applicant.nationalityen }"/>
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
											<input type="radio" name="isothercountrypermanentresidenten" class="permanentUS" value="1" />yes
										</span>
										<span>
											<input type="radio" name="isothercountrypermanentresidenten" class="permanentUS"  value="2" checked />No
										</span>
									</div>
								</div>
							</div>
							<div class="col-sm-5 col-sm-offset-1 padding-right-0 permanentUSTrue">
								<div class="form-group">
									<input id="othercountryen" name="othercountryen" type="text" class="form-control input-sm" value="${obj.applicant.othercountryen }" />
								</div>
							</div>
						</div>
						<!-- 国家注册号码 -->
						<div class="row">
							<label class="EngLabel">National Identification Number</label> 
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<input id="nationalidentificationnumberen" name="nationalidentificationnumberen" type="text" class="form-control input-sm" value="${obj.applicant.nationalidentificationnumberen }" />
								</div>
								<div class="countryNumen has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-countryNumen" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">国家注册号码不能为空</small>
								</div>	
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMarginEng">
								<div class="form-group">
									<input type="checkbox" class="vertical isidentificationnumberapplyen" name="isidentificationnumberapplyen" value="1"/>
									<label>Does Not Apply</label> 
								</div>
							</div>
						</div>
						<!-- 美国社会安全号码 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>U.S. Social Security Number</label> 
									<input id="socialsecuritynumberen" name="socialsecuritynumberen" type="text" class="form-control input-sm" value="${obj.applicant.socialsecuritynumberen }" />
								</div>
								<div class="safeNumen has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-blocksafeen" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">美国社会安全号码</small>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMargin">
								<div class="form-group">
									<input type="checkbox" class="vertical issecuritynumberapplyen" name="issecuritynumberapplyen" value="1"/>
									<label>Does Not Apply</label> 
								</div>
							</div>
						</div>
						<!-- 美国纳税人证件号 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>U.S. Taxpayer ID Number</label> 
									<input id="taxpayernumberen" name="taxpayernumberen" type="text" class="form-control input-sm" / value="${obj.applicant.taxpayernumberen }">
								</div>
								<div class="safepayen has-error" style="width:100%; height:30px; border:0 !important; color:red;margin:0px 0 -20px 0px !important">
									<small class="help-ratepayingen" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none;">美国纳税人证件号</small>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0 topMargin">
								<div class="form-group">
									<input type="checkbox" class="vertical istaxpayernumberapplyen" name="istaxpayernumberapplyen" value="1"/>
									<label>Does Not Apply</label> 
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var staffId = '${obj.staffId}';
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
	<script type="text/javascript" src="${base}/admin/bigCustomer/updateStaff.js"></script>
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
			var otherName = '${obj.applicant.hasothername}';
			var otherNameen = '${obj.applicant.hasothernameen}';
			var permanent = '${obj.applicant.isothercountrypermanentresident}';
			var permanenten = '${obj.applicant.isothercountrypermanentresidenten}';
			var address = '${obj.applicant.addressIssamewithcard}';
			var addressen = '${obj.applicant.addressIssamewithcarden}';
			var identification = '${obj.applicant.isidentificationnumberapply}';
			var identificationen = '${obj.applicant.isidentificationnumberapplyen}';
			var security = '${obj.applicant.issecuritynumberapply}';
			var securityen = '${obj.applicant.issecuritynumberapplyen}';
			var taxpayer = '${obj.applicant.istaxpayernumberapply}';
			var taxpayeren = '${obj.applicant.istaxpayernumberapplyen}';
			$("input[name='hasothernationality'][value='"+nation+"']").attr("checked",'checked');
			$("input[name='hasothername'][value='"+otherName+"']").attr("checked",'checked');
			$("input[name='isothercountrypermanentresident'][value='"+permanent+"']").attr("checked",'checked');
			$("input[name='hasothernationalityen'][value='"+nationen+"']").attr("checked",'checked');
			$("input[name='hasothernameen'][value='"+otherNameen+"']").attr("checked",'checked');
			$("input[name='isothercountrypermanentresidenten'][value='"+permanenten+"']").attr("checked",'checked');
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
			//是否是其他国家永久居民
			if(permanent == 1){
				$(".permanentTrue").show();
			}else{
				$(".permanentTrue").hide();
			}
			if(permanenten == 1){
				$(".permanentUSTrue").show();
			}else{
				$(".permanentUSTrue").hide();
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
			//国家注册码
			if(identification == 1){
				var boxObj = $("input:checkbox[name='isidentificationnumberapply']").attr("checked",true);
				$("#nationalidentificationnumber").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='isidentificationnumberapply']").attr("checked",false);
			}
			if(identificationen == 1){
				var boxObj = $("input:checkbox[name='isidentificationnumberapplyen']").attr("checked",true);
				$("#nationalidentificationnumberen").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='isidentificationnumberapplyen']").attr("checked",false);
			}
			//美国社会安全码
			if(security == 1){
				var boxObj = $("input:checkbox[name='issecuritynumberapply']").attr("checked",true);
				$("#socialsecuritynumber").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='issecuritynumberapply']").attr("checked",false);
			}
			if(securityen == 1){
				var boxObj = $("input:checkbox[name='issecuritynumberapplyen']").attr("checked",true);
				$("#socialsecuritynumberen").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='issecuritynumberapplyen']").attr("checked",false);
			}
			//美国纳税人证件号
			if(taxpayeren == 1){
				var boxObj = $("input:checkbox[name='istaxpayernumberapplyen']").attr("checked",true);
				$("#taxpayernumberen").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='istaxpayernumberapplyen']").attr("checked",false);
			}
			if(taxpayer == 1){
				var boxObj = $("input:checkbox[name='istaxpayernumberapply']").attr("checked",true);
				$("#taxpayernumber").attr("disabled",true);
			}else{
				var boxObj = $("input:checkbox[name='istaxpayernumberapply']").attr("checked",false);
			}
		});
		function successCallBack(status){
			if(status == 1){
				parent.successCallBack(1);
				closeWindow();
			}
		}
		function cancelCallBack(status){
			closeWindow();
		}
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
