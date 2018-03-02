<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>我的资料 - 基本信息</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
		<!-- 基本信息样式 -->
		<link rel="stylesheet" href="${base}/references/common/css/basicInfo.css">
	</head>

<body class="hold-transition skin-blue sidebar-mini">
	<c:choose>
		<c:when test="${!empty obj.contact }">
			<a id="toPassport" class="rightNav" onclick="passportBtn();">
				<span></span>
			</a>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<form id="applicantInfo">
			<div class="qz-head">
				<c:choose>
					<c:when test="${empty obj.contact }">
						<input  type="button" value="编辑" id="editbasic" class="btn btn-primary btn-sm pull-right editbasic" onclick="editBtn();"/> 
						<input  type="button" value="取消" class="btn btn-primary btn-sm pull-right basic none" onclick="cancelBtn(1);"/> 
						<input  type="button" value="保存" class="btn btn-primary btn-sm pull-right basic none" onclick="saveApplicant(1);"/> 
						<input  type="button" value="清除" class="btn btn-primary btn-sm pull-right basic none" onclick="clearAll();"/>
					</c:when>
					<c:otherwise>
						<input  type="button" value="取消" class="btn btn-primary btn-sm pull-right basic" onclick="cancelBtn(1);"/> 
						<input  type="button" value="保存" class="btn btn-primary btn-sm pull-right basic" onclick="saveApplicant(3);"/> 
					</c:otherwise>
				</c:choose>
			</div>
			<section class="content">
			<div class="ipt-info">
					<input disabled id="baseRemark" name="baseRemark" type="text"   class="NoInfo form-control input-sm" />
			</div>
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"> <!-- 身份证 正面 -->
							<img width="100%" height="100%" alt="" src="${obj.qrCode }">
						</div> <!-- end 身份证 正面 -->

						<div class="info-imgUpload front has-error" id="borderColorFront">
							<!-- 身份证 正面 -->
							<div class="col-xs-6 mainWidth">
							<div class="form-group">
								<div class="cardFront-div">
									<span>点击上传身份证正面</span>
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="cardFront" name="cardFront" type="hidden" />
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
											<img id="sqImg" alt="" src="" >
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="cardFront" name="cardFront" type="hidden" value="${obj.applicant.cardFront }"/>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="uploadFile" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
											<img id="sqImg" alt="" src="${obj.applicant.cardFront }" >
										</c:otherwise>
									</c:choose>
									
									
									<i class="delete" id="deleteApplicantFrontImg" ></i>
								</div>
							</div>
						</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;">
							<small class="help-blockFront" data-bv-validator="notEmpty" data-bv-for="cardFront" data-bv-result="IVVALID" style="display: none; ">身份证正面必须上传</small>
						</div>
					
						<!-- end 身份证 正面 -->

						<div class="info-imgUpload back has-error" id="borderColorBack">
							<!-- 身份证 反面 -->
							<div class="col-xs-6 mainWidth">
								<div class="form-group">
									<div class="cardFront-div">
										<span>点击上传身份证背面</span>
										<c:choose>
											<c:when test="${empty obj.applicant}">
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 id="cardBack" name="cardBack" type="hidden" />
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
												<img id="sqImgBack" alt="" src="" >
											</c:when>
											<c:otherwise>
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 id="cardBack" name="cardBack" type="hidden" value="${obj.applicant.cardBack }"/>
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 id="uploadFileBack" name="uploadFile" class="btn btn-primary btn-sm" type="file"  value="1111"/>
												<img id="sqImgBack" alt="" src="${obj.applicant.cardBack }" >
											</c:otherwise>
										</c:choose>
										<i class="delete" id="deleteApplicantBackImg" ></i>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 front has-error" style="width:320px; height:30px; border:0 !important; color:red;">
							<small class="help-blockBack" data-bv-validator="notEmpty" data-bv-for="cardBack" data-bv-result="IVVALID" style="display: none;">身份证背面必须上传</small>
						</div>
						<!-- end 身份证 反面 -->
						<%-- <div class="row">
							<!-- 签发机关 -->
							<div class="col-sm-10 col-sm-offset-1 padding-right-0 marginL" style="margin-top:6px;">
								<div class="form-group">
									<label><span>*</span>签发机关</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="issueOrganization" name="issueOrganization"
												type="text" class="form-control input-sm" placeholder=" " />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="issueOrganization" name="issueOrganization"
												type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.issueOrganization }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 签发机关 --> --%>
						
						</div>


					<div class="col-sm-6 padding-right-0">
						
						<div class="row">
							<!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>手机号</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="telephone"
												name="telephone" type="text" class="form-control input-sm"
												placeholder=" "  />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											
											 id="telephone"
												name="telephone" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.applicant.telephone }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>邮箱</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="email" name="email"
												type="text" class="form-control input-sm" placeholder=" "
												/>
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="email" name="email"
												type="text" class="form-control input-sm" placeholder=" "
												value="${obj.applicant.email }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 手机号/邮箱 -->
						<div class="row">
							<!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>公民身份号码</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="cardId"
												name="cardId" type="text" class="form-control input-sm"
												placeholder=" "  />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="cardId"
												name="cardId" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.applicant.cardId }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 公民身份证 -->
						<div class="row">
							<!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别</label> 
										<select	
											<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
										 class="form-control input-sm" id="sex" name="sex" >
											<c:choose>
												<c:when test="${empty obj.applicant}">
													<option value="男" >男</option>
													<option value="女" >女</option>
												</c:when>
												<c:otherwise>
													<option value="男" ${obj.applicant.sex == "男"?"selected":"" }>男</option>
													<option value="女" ${obj.applicant.sex == "女"?"selected":"" }>女</option>
												</c:otherwise>
											</c:choose>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label><span>*</span>民族</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="nation"
												name="nation" type="text" class="form-control input-sm"
												placeholder=" "  />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="nation"
												name="nation" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.applicant.nation }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期</label>
									<c:choose>
										<c:when test="${empty obj.applicant}">
											 <input 
											 	<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											  id="birthday"
												name="birthday" type="text" class="form-control input-sm"
												placeholder=" "  />
										</c:when>
										<c:otherwise>
											 <input 
											 	<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											  id="birthday"
												name="birthday" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.birthday }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 姓名/民族 -->
						<div class="row">
							<!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>住址</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="address"
												name="address" type="text" class="form-control input-sm"
												placeholder=" "  />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="address"
												name="address" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.applicant.address }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 住宅 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>有效期限</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="validStartDate" name="validStartDate"  type="text" class="form-control input-sm" />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="validStartDate" name="validStartDate"  type="text" class="form-control input-sm" value="${obj.validStartDate }"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="validEndDate" type="text" name="validEndDate"  class="form-control input-sm" >
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="validEndDate" type="text" name="validEndDate"  class="form-control input-sm" value="${obj.validEndDate }">
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<!-- end 有效期限 -->
						<div class="row">
							<!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地是否与身份证相同</label> 
									<input 
										<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
									 class="nowProvince" type="checkbox" name="addressIsSameWithCard" value="1" /> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="province"
												name="province" type="text" class="form-control input-sm"
												placeholder="省" />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="province"
												name="province" type="text" class="form-control input-sm"
												placeholder="省" value="${obj.applicant.province }" />
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="hidden" name="cardProvince" id="cardProvince" value="${obj.applicant.cardProvince }"/>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="hidden" name="cardCity" id="cardCity" value="${obj.applicant.cardCity }"/>
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>现居住地址城市</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											id="city"
												name="city" type="text" class="form-control input-sm"
												placeholder="市"  />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="city"
												name="city" type="text" class="form-control input-sm"
												placeholder="市" value="${obj.applicant.city }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 现居住地址省份/现居住地址城市 -->
						
						<div class="row">
							<!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>详细地址</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											
												id="detailedAddress" name="detailedAddress" type="text"
												class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间"
												/>
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												id="detailedAddress" name="detailedAddress" type="text"
												class="form-control input-sm" placeholder="区(县)/街道/小区(社区)/楼号/单元/房间"
												value="${obj.applicant.detailedAddress }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->	
						
						
						<div class="row">
							<!-- 是否有曾用名/曾有的或另有的国际(或公民身份) -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0 nameBeforeTop">
								<div class="form-group">
									<label>是否有曾用名</label> 
									<div>
										<span class="nameBeforeYes ">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="radio" name="hasOtherName" class="nameBefore"  value="1"
											/>是
										</span>
										<span>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="radio" name="hasOtherName" class="nameBefore"  value="2"
											/>否
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 姓/名 拼音 -->
							<div class="nameBeforeHide">
							    <div class="col-sm-11 col-sm-offset-1 padding-right-0 marginL">
									<div class="form-group" style="position:relative;">
										<label>姓/拼音</label> 
										<c:choose>
											<c:when test="${empty obj.applicant}">
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												
												id="otherFirstName" name="otherFirstName" type="text" class="form-control input-sm " placeholder=" "  />
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 type="text" id="otherFirstNameEn" 
												 <c:choose>
														<c:when test="${empty obj.contact}">
														style="position:absolute;top:30px;border:none;left:150px;background-color:#eee;" 
														</c:when>
														<c:otherwise>
														style="position:absolute;top:30px;border:none;left:150px;"
														</c:otherwise>
													
													</c:choose >
												 
												   name="otherFirstNameEn" />
											</c:when>
											<c:otherwise>
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 id="otherFirstName"	name="otherFirstName" style="position:relative;" type="text" class="form-control input-sm "	placeholder=" " value="${obj.applicant.otherFirstName }" />
												<input 
													<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
												 type="text" id="otherFirstNameEn" 
												 <c:choose>
														<c:when test="${empty obj.contact}">
														style="position:absolute;top:30px;border:none;left:150px;background-color:#eee;" 
														</c:when>
														<c:otherwise>
														style="position:absolute;top:30px;border:none;left:150px;"
														</c:otherwise>
													
													</c:choose >
												 
												 
												   name="otherFirstNameEn" value="${obj.otherFirstNameEn }"/>
											</c:otherwise>
										</c:choose>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								
							</div>
						</div>	
							<!-- 名/拼音 -->
						<div class="row wordSpell">
							<div class="col-sm-11 col-sm-offset-1 padding-right-0 col-sm-offset-1">
								<div class="form-group" style="position:relative;">
									<label>名/拼音</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="otherLastName" name="otherLastName" style="position:relative;" type="text" class="form-control input-sm otherLastName" placeholder=" "  />
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="text" id="otherLastNameEn" 
											 <c:choose>
														<c:when test="${empty obj.contact}">
														style="position:absolute;top:30px;border:none;left:150px;background-color:#eee;" 
														</c:when>
														<c:otherwise>
														style="position:absolute;top:30px;border:none;left:150px;"
														</c:otherwise>
													
												</c:choose >
											 			 name="otherLastNameEn" />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="otherLastName" name="otherLastName" style="position:relative;" type="text" class="form-control input-sm otherLastName" placeholder=" " value="${obj.applicant.otherLastName }" />
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="text" id="otherLastNameEn" 
											 <c:choose>
												<c:when test="${empty obj.contact}">
													style="position:absolute;top:30px;border:none;left:150px;background-color:#eee;" 
												</c:when>
												<c:otherwise>
													style="position:absolute;top:30px;border:none;left:150px;"
												</c:otherwise>
													
											</c:choose >
											  name="otherLastNameEn" value="${obj.otherLastNameEn }"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
							<!-- 姓/名 拼音 end -->
						<div class="row">	
							<div class="col-sm-7 col-sm-offset-1 padding-right-0 onceIDTop">
								<div class="form-group">
									<label>曾有的或另有的国籍(或公民身份)</label> 
									<div>
										<span class="onceIDYes ">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="radio" name="hasOtherNationality" class="onceID" value="1" />是
										</span>
										<span>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 type="radio" name="hasOtherNationality" class="onceID"  value="2" />否
										</span>
									</div>
								</div>
							</div>
							<!-- 曾用国籍 -->
							<div class="col-sm-5 padding-right-0 nationalityHide" >
								<div class="form-group" id="nationalityDiv">
									<label>国籍</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="nationality" name="nationality"  type="text" class="form-control input-sm"/>
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="nationality" name="nationality" value="${obj.applicant.nationality}" type="text" class="form-control input-sm"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="row">
							<!-- 紧急联系人姓名/手机 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人姓名</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="emergencyLinkman"
												name="emergencyLinkman" type="text" class="form-control input-sm"
												placeholder=" " />
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="emergencyLinkman"
												name="emergencyLinkman" type="text" class="form-control input-sm"
												placeholder=" " value="${obj.applicant.emergencyLinkman }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label>紧急联系人手机</label> 
									<c:choose>
										<c:when test="${empty obj.applicant}">
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="emergencyTelephone" name="emergencyTelephone"
												type="text" class="form-control input-sm" placeholder=" "
												/>
										</c:when>
										<c:otherwise>
											<input 
												<c:choose>
														<c:when test="${empty obj.contact}">
														disabled 
														</c:when>
														<c:otherwise>
														</c:otherwise>
													
													</c:choose >
											 id="emergencyTelephone" name="emergencyTelephone"
												type="text" class="form-control input-sm" placeholder=" "
												value="${obj.applicant.emergencyTelephone }" />
										</c:otherwise>
									</c:choose>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
					</div>

				</div>
			</section>
	</form>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var contact = '${obj.contact}';
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
	
	<!-- 本页面js文件 -->
	<script type="text/javascript">
	$(function(){
		
		$('#applicantInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {

				telephone : {
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
				emergencyTelephone : {
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
				email : {
					validators : {
						regexp: {
	                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	                        message: '邮箱格式错误'
	                    }
					}
				}
			}
		});
		$('#applicantInfo').bootstrapValidator('validate');
		
		if(!contact){
			//页面所有元素设置为disabled
			/* var form = document.forms[0]; 
			for ( var i = 0; i < form.length; i++) { 
				var element = form.elements[i]; 
				if(element.id != "editbasic")
					element.readOnly = true; 
			}  */
			//$(".editbasic").removeClass("none");
			/* var remark = $("#baseRemark").val();
			if(remark != ""){
				$(".ipt-info").show();
			} */
			/* document.getElementById("baseRemark").style.backgroundColor = "#eee";
			document.getElementById("firstNameEn").style.backgroundColor = "#eee";
			document.getElementById("lastNameEn").style.backgroundColor = "#eee";
			document.getElementById("otherFirstNameEn").style.backgroundColor = "#eee";
			document.getElementById("otherLastNameEn").style.backgroundColor = "#eee"; */
			//$("#baseRemark").attr("disabled", true);
		}else{
			$("#deleteApplicantFrontImg").click(function(){
				$('#cardFront').val("");
				$('#sqImg').attr('src', "");
				$("#uploadFile").siblings("i").css("display","none");
				$(".front").attr("class", "info-imgUpload front has-error");  
		        $(".help-blockFront").attr("data-bv-result","INVALID");  
		        //$(".help-blockFront").attr("style","display: block;");
		        //$("#borderColorFront").attr("style", "border-color:#ff1a1a");
			});
			$("#deleteApplicantBackImg").click(function(){
				$('#cardBack').val("");
				$('#sqImgBack').attr('src', "");
				$("#uploadFileBack").siblings("i").css("display","none");
				$(".back").attr("class", "info-imgUpload back has-error");  
		        $(".help-blockBack").attr("data-bv-result","INVALID");  
		        //$(".help-blockBack").attr("style","display: block;");
		        //$("#borderColorBack").attr("style", "border-color:#ff1a1a");
			});
		}
		
			var nation = '${obj.applicant.hasOtherNationality}';
			var otherName = '${obj.applicant.hasOtherName}';
			var address = '${obj.applicant.addressIsSameWithCard}';
			$("input[name='hasOtherNationality'][value='"+nation+"']").attr("checked",'checked');
			$("input[name='hasOtherName'][value='"+otherName+"']").attr("checked",'checked');
			if(nation == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nationalityHide").show();
				$(".onceIDTop").css('float','left');
			}else {
				$(".nationalityHide").hide();
				$("input[name='hasOtherNationality'][value='2']").attr("checked",'checked');
			}
			
			if(otherName == 1){
				$(".nameBeforeTop").css('float','none');
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
				//$(".onceIDTop").removeClass('col-sm-offset-1');
				//$(".onceIDTop").css('padding-left','15px');
			}else {
				$(".wordSpell").hide();
				$(".nameBeforeHide").hide();
				$("input[name='hasOtherName'][value='2']").attr("checked",'checked');
			}
			
			if(address == 1){
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",true);
			}else{
				var boxObj = $("input:checkbox[name='addressIsSameWithCard']").attr("checked",false);
			}
			
		
		
		
		
		var front = $("#cardFront").val();
		var back = $("#cardBack").val();
		if(front != ""){
			$("#uploadFile").siblings("i").css("display","block");
		}else{
			$("#uploadFile").siblings("i").css("display","none");
		}
		
		if(back != ""){
			$("#uploadFileBck").siblings("i").css("display","block");
		}else{
			$("#uploadFileBack").siblings("i").css("display","none");
		} 
	});
	
	function applicantValidate(){
		
		//身份证图片验证
		var cardFront = $("#cardFront").val();
		if(cardFront == ""){
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        $(".help-blockFront").attr("style","display: block;");  
	        $("#borderColorFront").attr("style", "border-color:#ff1a1a");
		}else{
			$(".front").attr("class", "info-imgUpload front has-success");  
	        $(".help-blockFront").attr("data-bv-result","IVALID");  
	        $(".help-blockFront").attr("style","display: none;");  
	        $("#borderColorFront").attr("style", null);
		}
		
		var cardBack = $("#cardBack").val();
		if(cardBack == ""){
			$(".back").attr("class", "info-imgUpload back has-error");  
	        $(".help-blockBack").attr("data-bv-result","INVALID");  
	        $(".help-blockBack").attr("style","display: block;");
	        $("#borderColorBack").attr("style", "border-color:#ff1a1a");
		}else{
			$(".back").attr("class", "info-imgUpload back has-success");  
	        $(".help-blockBack").attr("data-bv-result","IVALID");  
	        $(".help-blockBack").attr("style","display: none;");
	        $("#borderColorBack").attr("style", null);
		}
		
		//校验
		$('#applicantInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {

				
				telephone : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '手机号不能为空'
						},
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
				email : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '邮箱不能为空'
						},
						regexp: {
	                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	                        message: '邮箱格式错误'
	                    }
					}
				},
				cardId : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '公民身份证不能为空'
						}
					}
				},
				nation : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '民族不能为空'
						}
					}
				},
				otherFirstName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '曾用姓不能为空'
						}
					}
				},
				otherLastName : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '曾用名不能为空'
						}
					}
				},
				nationality : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '国籍不能为空'
						}
					}
				},
				birthday : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '出生日期不能为空'
						}
					}
				},
				address : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '住址不能为空'
						}
					}
				},
				validStartDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效期限不能为空'
						}
					}
				},
				validEndDate : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '有效期限不能为空'
						}
					}
				},
				province : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '现居住地省份不能为空'
						}
					}
				},
				city : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '现居住地城市不能为空'
						}
					}
				},
				detailedAddress : {
					trigger:"change keyup",
					validators : {
						notEmpty : {
							message : '详细地址不能为空'
						}
					}
				},
				emergencyTelephone : {
					trigger:"change keyup",
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
			}
		});
	$('#applicantInfo').bootstrapValidator('validate');
	
	
	}
	
	//var base = "${base}";
	function saveApplicant(status){
		$("#applicantInfo").data('bootstrapValidator').destroy();
		$("#applicantInfo").data('bootstrapValidator', null);
		applicantValidate();
		//得到获取validator对象或实例 
		var bootstrapValidator = $("#applicantInfo").data(
				'bootstrapValidator');
		// 执行表单验证 
		bootstrapValidator.validate();

		if (!bootstrapValidator.isValid()){
			return;
		}
		
		if($(".back").hasClass("has-error")){
			return;
		}
		if($(".front").hasClass("has-error")){
			return;
		}
		layer.load(1);
			
		var str="";
		var applicantInfo;
		$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
		    str=$(this).val();     
		});
		if(str != 1){
			applicantInfo = $.param({"addressIsSameWithCard":0}) + "&" + $("#applicantInfo").serialize();
		}else{
			applicantInfo = $("#applicantInfo").serialize();
		}
		
		var applicantId = '${obj.applicantId}';
		var orderid = '${obj.orderid}';
		//applicantInfo.id = applicantId;
		$.ajax({
			async: false,
			type: 'POST',
			data : applicantInfo,
			url: '${base}/admin/myData/saveEditApplicant',
			success :function(data) {
				layer.closeAll('loading');
				if(status == 1){
					cancelBtn(2);
					parent.successCallBack();
				}else if(status == 2){
					window.location.href = '/admin/myData/passport.html?contact=1&applyId='+applicantId;
				}else{
					layer.msg("修改成功", {
						time: 500,
						end: function () {
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);
							parent.successCallBack();
						}
					});
				}
			}
		});
	}
	//编辑按钮
	function editBtn(){
		$(".basic").show();
		$(".editbasic").hide();
		var form = document.forms[0]; 
		for ( var i = 0; i < form.length; i++) { 
			var element = form.elements[i]; 
			element.disabled = false; 
		} 
		document.getElementById("firstNameEn").style.backgroundColor = "#fff";
		document.getElementById("lastNameEn").style.backgroundColor = "#fff";
		document.getElementById("otherFirstNameEn").style.backgroundColor = "#fff";
		document.getElementById("otherLastNameEn").style.backgroundColor = "#fff";
		//身份证图片验证
		var cardFront = $("#cardFront").val();
		if(cardFront == ""){
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        //$(".help-blockFront").attr("style","display: block;");  
	        //$("#borderColorFront").attr("style", "border-color:#ff1a1a");
		}else{
			$(".front").attr("class", "info-imgUpload front has-success");  
	        $(".help-blockFront").attr("data-bv-result","IVALID");  
	        $(".help-blockFront").attr("style","display: none;");  
	        $("#borderColorFront").attr("style", null);
		}
		
		var cardBack = $("#cardBack").val();
		if(cardBack == ""){
			$(".back").attr("class", "info-imgUpload back has-error");  
	        $(".help-blockBack").attr("data-bv-result","INVALID");  
	        //$(".help-blockBack").attr("style","display: block;");
	        //$("#borderColorBack").attr("style", "border-color:#ff1a1a");
		}else{
			$(".back").attr("class", "info-imgUpload back has-success");  
	        $(".help-blockBack").attr("data-bv-result","IVALID");  
	        $(".help-blockBack").attr("style","display: none;");
	        $("#borderColorBack").attr("style", null);
		}
		
		//校验
		/* $('#applicantInfo').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				telephone : {
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
				emergencyTelephone : {
					validators : {
						regexp: {
	                	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '手机号格式错误'
	                    }
					}
				},
				email : {
					validators : {
						regexp: {
	                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	                        message: '邮箱格式错误'
	                    }
					}
				}
			}
		}); */
		
		/* var bootstrapValidator = $("#applicantInfo").data(
		'bootstrapValidator');
		// 执行表单验证 
		bootstrapValidator.validate(); */
		$("#deleteApplicantFrontImg").click(function(){
			$('#cardFront').val("");
			$('#sqImg').attr('src', "");
			$("#uploadFile").siblings("i").css("display","none");
			$(".front").attr("class", "info-imgUpload front has-error");  
	        $(".help-blockFront").attr("data-bv-result","INVALID");  
	        //$(".help-blockFront").attr("style","display: block;");
	        //$("#borderColorFront").attr("style", "border-color:#ff1a1a");
		});
		$("#deleteApplicantBackImg").click(function(){
			$('#cardBack').val("");
			$('#sqImgBack').attr('src', "");
			$("#uploadFileBack").siblings("i").css("display","none");
			$(".back").attr("class", "info-imgUpload back has-error");  
	        $(".help-blockBack").attr("data-bv-result","INVALID");  
	        //$(".help-blockBack").attr("style","display: block;");
	        //$("#borderColorBack").attr("style", "border-color:#ff1a1a");
		});
		$("#baseRemark").attr("disabled", true);
	} 
	
	//取消按钮
	function cancelBtn(status){
		if(!contact){
			if(status == 1){
				layer.msg("已取消", {
					time: 500,
					end: function () {
						self.location.reload();
					}
				});
			}else{
				layer.msg("修改成功", {
					time: 500,
					end: function () {
						self.location.reload();
					}
				});
			}
		}else{
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	}
	
	//国籍检索
	$("#nationality").on('input',function(){
		$("#nationality").nextAll("ul.ui-autocomplete").remove();
		$.ajax({
			type : 'POST',
			async: false,
			data : {
				searchStr : $("#nationality").val()
			},
			url : BASE_PATH+'/admin/orderJp/getNationality.html',
			success : function(data) {
				if(data != ""){
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setNationality("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#nationality").after(liStr);
				}
			}
		});
	});
	//国籍检索下拉项
	function setNationality(nationality){
		$("#nationality").nextAll("ul.ui-autocomplete").remove();
		$("#nationality").val(nationality).change();
	}
	
	//省份检索
	$("#province").on('input',function(){
		$("#province").nextAll("ul.ui-autocomplete").remove();
		$.ajax({
			type : 'POST',
			async: false,
			data : {
				searchStr : $("#province").val()
			},
			url : BASE_PATH+'/admin/orderJp/getProvince.html',
			success : function(data) {
				if(data != ""){
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setProvince("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#province").after(liStr);
				}
			}
		});
	});
	
	//省份 检索下拉项
	function setProvince(province){
		$("#province").nextAll("ul.ui-autocomplete").remove();
		$("#province").val(province).change();
	}
	
	//市检索
	$("#city").on('input',function(){
		$("#city").nextAll("ul.ui-autocomplete").remove();
		$.ajax({
			type : 'POST',
			async: false,
			data : {
				province : $("#province").val(),
				searchStr : $("#city").val()
			},
			url : BASE_PATH+'/admin/orderJp/getCity.html',
			success : function(data) {
				if(data != ""){
					var liStr = "<ul class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content ui-corner-all IdInfo' id='ui-id-1' role='null' tabindex='0' width: 167px;position: relative;top: -16px;left: 0px;'>";
					$.each(data,function(index,element) { 
						liStr += "<li onclick='setCity("+JSON.stringify(element)+")' class='ui-menu-item' role='presentation'><a id='ui-id-3' class='ui-corner-all' tabindex='-1'>"+element+"</a></li>";
					});
					liStr += "</ul>";
					$("#city").after(liStr);
				}
			}
		});
	});
	
	//市 检索下拉项
	function setCity(city){
		$("#city").nextAll("ul.ui-autocomplete").remove();
		$("#city").val(city).change();
	}

	//正面上传,扫描
	
	$('#uploadFile').change(function() {
		var layerIndex = layer.load(1, {
			shade : "#000"
		});
		$("#addBtn").attr('disabled', true);
		$("#updateBtn").attr('disabled', true);
		var file = this.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			var dataUrl = e.target.result;
			var blob = dataURLtoBlob(dataUrl);
			var formData = new FormData();
			formData.append("image", blob, file.name);
			$.ajax({
				type : "POST",//提交类型  
				//dataType : "json",//返回结果格式  
				url : BASE_PATH + '/admin/orderJp/IDCardRecognition',//请求地址  
				async : true,
				processData : false, //当FormData在jquery中使用的时候需要设置此项
				contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
				//请求数据  
				data : formData,
				success : function(obj) {//请求成功后的函数 
					//关闭加载层
					layer.close(layerIndex);
					if (true === obj.success) {
						layer.msg("识别成功");
						$('#cardFront').val(obj.url);
						$('#sqImg').attr('src', obj.url);
						$("#uploadFile").siblings("i").css("display","block");
						$(".front").attr("class", "info-imgUpload front has-success");  
				        $(".help-blockFront").attr("data-bv-result","IVALID");  
				        $(".help-blockFront").attr("style","display: none;");
				        $("#borderColorFront").attr("style", null);
						$('#address').val(obj.address).change();
						$('#nation').val(obj.nationality).change();
						$('#cardId').val(obj.num).change();
						var str="";  
						//是否同身份证相同
						$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
							str=$(this).val();     
						});     
						if(str == 1){//相同
							searchByCard();
						}
						$('#cardProvince').val(obj.province).change();
						$('#cardCity').val(obj.city).change();
						$('#birthday').val(obj.birth).change();
						$('#sex').val(obj.sex);
					}
					$("#addBtn").attr('disabled', false);
					$("#updateBtn").attr('disabled', false);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.close(layerIndex);
					$("#addBtn").attr('disabled', false);
					$("#updateBtn").attr('disabled', false);
				}
			}); // end of ajaxSubmit
		};
		reader.readAsDataURL(file);
	});
	
	//背面上传,扫描
	$('#uploadFileBack').change(function() {
		var layerIndex = layer.load(1, {
			shade : "#000"
		});
		$("#addBtn").attr('disabled', true);
		$("#updateBtn").attr('disabled', true);
		var file = this.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			var dataUrl = e.target.result;
			var blob = dataURLtoBlob(dataUrl);
			var formData = new FormData();
			formData.append("image", blob, file.name);
			$.ajax({
				type : "POST",//提交类型  
				//dataType : "json",//返回结果格式  
				url : BASE_PATH + '/admin/orderJp/IDCardRecognitionBack',//请求地址  
				async : true,
				processData : false, //当FormData在jquery中使用的时候需要设置此项
				contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
				//请求数据  
				data : formData,
				success : function(obj) {//请求成功后的函数 
					//关闭加载层
					layer.close(layerIndex);
					if (true === obj.success) {
						layer.msg("识别成功");
						$('#cardBack').val(obj.url);
						$('#sqImgBack').attr('src', obj.url);
						$("#uploadFileBack").siblings("i").css("display","block");
						$(".back").attr("class", "info-imgUpload back has-success");  
				        $(".help-blockBack").attr("data-bv-result","IVALID");  
				        $(".help-blockBack").attr("style","display: none;");
				        $("#borderColorBack").attr("style", null);
						$('#validStartDate').val(obj.starttime).change();
						$('#validEndDate').val(obj.endtime).change();
						$('#issueOrganization').val(obj.issue).change();
					}
					$("#addBtn").attr('disabled', false);
					$("#updateBtn").attr('disabled', false);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.close(layerIndex);
					$("#addBtn").attr('disabled', false);
					$("#updateBtn").attr('disabled', false);
				}
			}); // end of ajaxSubmit
		};
		reader.readAsDataURL(file);
	});
	
	//把dataUrl类型的数据转为blob
	function dataURLtoBlob(dataurl) {
		var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
				n);
		while (n--) {
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([ u8arr ], {
			type : mime
		});
	}
	
	function passportBtn(){
		/* var applicantId = '${obj.applyId}';
			var bootstrapValidator = $("#applicantInfo").data(
			'bootstrapValidator');
			// 执行表单验证 
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return;
			}
			
			if($(".front").hasClass("has-error")){
				return;
			}
			if($(".back").hasClass("has-error")){
				return;
			} */
		saveApplicant(2);
		//socket.onclose();
		//window.location.href = '/admin/myData/passport.html?contact=1&applyId='+applicantId;
	}
								
	$(function(){
		$("#validStartDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
		$("#validEndDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"top-left",//显示位置
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
			pickerPosition:"top-left",//显示位置
			minView: "month"//只显示年月日
		});
	});
	//checkbox 曾用名
	$(".nameBefore").change(function(){

			let checked = $("input[name='hasOtherName']:checked").val();
			if(checked == 1){
				$(".nameBeforeHide").show();
				$(".wordSpell").show();
			}else {
				
				$(".nameBeforeHide").hide();
				$(".wordSpell").hide();
			}
		});
		//曾用国籍
		$(".onceID").change(function(){
			let checked = $("input[name='hasOtherNationality']:checked").val();
			if(checked == 1){
				$(".nationalityHide").show();
				$("#nationality").val("").change();
			}else {
				
				$(".nationalityHide").hide();
			}
		});
		//曾用名
		$(".nameBeforeTop").change(function(){
			let checked2 = $("input[name='hasOtherName']:checked").val();
			if(checked2 == 1){
				$("#otherFirstName").val("").change();
				$("#otherFirstNameEn").val("");
				$("#otherLastName").val("").change();
				$("#otherLastNameEn").val("");
			}else {
			}
		});
		
	//居住地与身份证相同
	$(".nowProvince").change(function(){
		var str="";  
		//是否同身份证相同
		$("input:checkbox[name='addressIsSameWithCard']:checked").each(function(){     
			str=$(this).val();     
		});     
		if(str == 1){//相同
			searchByCard();
		}else{
			$("#province").val("").change();
			$("#city").val("").change();
			$("#detailedAddress").val("").change();
		}
	});
	
	/* $("#cardId").change(function(){
		searchByCard();
	}); */
	
	function searchByCard(){
			var cardId = $("#cardId").val();
			layer.load(1);
			$.ajax({
				type: 'POST',
				data : {
					cardId : cardId
				},
				url: '${base}/admin/orderJp/getInfoByCard',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					$("#province").val(data.province).change();
					$("#city").val(data.city).change();
					$("#detailedAddress").val($("#address").val()).change();
				}
			});
	}
	
	function successCallBack(){
		if(contact){
			parent.successCallBack();
		}
	}
	
	function clearAll(){
		$(".front").attr("class", "info-imgUpload front has-error");  
        $(".help-blockFront").attr("data-bv-result","INVALID");  
        //$(".help-blockFront").attr("style","display: block;");
        $(".back").attr("class", "info-imgUpload back has-error");  
        $(".help-blockBack").attr("data-bv-result","INVALID");  
        //$(".help-blockBack").attr("style","display: block;"); 
       // $("#borderColorFront").attr("style", "border-color:#ff1a1a");
        //$("#borderColorBack").attr("style", "border-color:#ff1a1a");
		$("input[name='hasOtherName'][value='2']").prop("checked","checked");
        $("input[name='hasOtherNationality'][value='2']").prop("checked","checked");
		$("input:checkbox[name='addressIsSameWithCard']").attr("checked", false);
		$(".nationalityHide").hide();
		$(".nameBeforeHide").hide();
		$("#cardFront").val("");
		$('#sqImg').attr('src', "");
		$("#cardBack").val("");
		$("#sqImgBack").attr('src', "");
		$("#uploadFile").siblings("i").css("display","none");
		$("#uploadFileBack").siblings("i").css("display","none");
		$("#issueOrganization").val("").change();
		$("#province").val("").change();
		$("#city").val("").change();
		$("#otherFirstName").val("").change();
		$("#otherFirstNameEn").val("");
		$("#otherLastName").val("").change();
		$("#otherLastNameEn").val("");
		$("#firstName").val("").change();
		$("#firstNameEn").val("");
		$("#lastName").val("").change();
		$("#lastNameEn").val("");
		$("#telephone").val("").change();
		$("#email").val("").change();
		$("#nation").val("").change();
		$("#nationality").val("").change();
		$("#birthday").val("").change();
		$("#address").val("").change();
		$("#issueOrganization").val("").change();
		$("#cardId").val("").change();
		$("#validStartDate").val("").change();
		$("#validEndDate").val("").change();
		$("#detailedAddress").val("").change();
		$("#emergencyLinkman").val("").change();
		$("#emergencyTelephone").val("").change();
		$(".wordSpell").hide();
	}
	</script>
</body>
</html>
