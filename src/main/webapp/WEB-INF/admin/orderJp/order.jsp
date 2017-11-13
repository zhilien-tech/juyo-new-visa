<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>编辑</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top: -35px;right: -1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
		</style>
	</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper" style="min-height: 848px;">
			<div class="qz-head">
				<span class="">订单号：<p>${obj.orderInfo.orderNum}</p></span> 
				<span class="">受付番号：<p></p></span> 
				<span class="">状态：<p>下单</p></span> 
				<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" /> 
				<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" id="saveOrder" v-on:click="order()" /> 
			</div>
			<section class="content">
				<!-- 客户信息 -->
				<%-- <div class="info" id="customerInfo" ref="customerInfo">
					<p class="info-head">客户信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 公司全称 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>客户来源：</label> 
									<select id="customerType" name="source" class="form-control input-sm"
										v-model="customerInfo.source">
										<option value="">--请选择--</option>
										<c:forEach var="map" items="${obj.customerTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>公司全称：</label> <input id="compName"
										name="compName" type="text" class="form-control input-sm"
										placeholder=" " v-model="customerInfo.name" /> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>公司简称：</label>
									<input type="hidden" id="orderid" name="orderid" value="${obj.orderId }"/>
									 <input id="comShortName"
										name="comShortName" type="text" class="form-control input-sm"
										placeholder=" " v-model="customerInfo.shortname" /> <i
										class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 公司全称 -->
						<div class="row body-from-input"><!-- 客户来源/联系人/手机号/邮箱 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>联系人：</label> <input id="linkman"
										name="linkman" type="text" class="form-control input-sm"
										placeholder=" " v-model="customerInfo.linkman" /> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>手机号：</label> <input id="telephone"
										name="telephone" type="text" class="form-control input-sm"
										placeholder=" " v-model="customerInfo.mobile" /> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>邮箱：</label> <input id="email" name="email"
										type="text" class="form-control input-sm" placeholder=" "
										v-model="customerInfo.email" /> <i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 客户来源/联系人/手机号/邮箱 -->
					</div>
				</div> --%>
				<!-- end 客户信息 -->

				
				<div class="info" id="customerInfo" ref="customerInfo">
						<p class="info-head">客户信息</p>
						<div class="info-body-from">
							<div class="row body-from-input">
								<!-- 公司全称 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>客户来源：</label> 
										<select id="customerType" name="source" class="form-control input-sm" v-model="customerInfo.source">
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.customerTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="on-line"><!-- select2 线上/OTS/线下 -->
									<div class="col-sm-3">
										<div class="form-group" style="padding-right: 3%;">
											<label><span>*</span>公司全称：</label> 
											<select id ="compName" name="name"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" v-model="customerInfo.name">
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.name }</option>
												</c:if>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> 
											<select id = "comShortName" name="shortname"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" v-model="customerInfo.shortname">
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.shortname }</option>
												</c:if>
											</select>
										</div>
									</div>
								</div><!-- end select2 线上/OTS/线下 -->
								
								<div class="zhiKe none"><!-- input 直客 -->
									<div class="col-sm-3">
										<div class="form-group" style="padding-right: 3%;">
											<label><span>*</span>公司全称：</label> 
											<input id="compName" name="name" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label>
											<input id="comShortName" name="shortname" type="text" class="form-control input-sm" placeholder=" "  />
										</div>
									</div>
								</div><!-- end input 直客 -->
							</div>
							<div class="row body-from-input on-line"><!-- select2 线上/OTS/线下 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> 
										<select id = "linkman" name="linkman"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" v-model="customerInfo.linkman">
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.linkman }</option>
												</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label> 
										<select id = "telephone" name="mobile"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" v-model="customerInfo.mobile">
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.mobile }</option>
												</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label> 
										<select id = "email" name="email" class="form-control select2 cityselect2 " multiple="multiple" data-placeholder="" v-model="customerInfo.email">
											<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.email }</option>
												</c:if>
										</select>
									</div>
								</div>
							</div><!-- end select2 线上/OTS/线下 -->
							
							
							<div class="row body-from-input zhiKe none"><!-- input 直客 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> 
										<input id="linkman" name="linkman" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label> 
										<input id="telephone" name="mobile" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label>
										<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
							</div><!-- end input 直客 -->
						</div>
					</div><!-- end 客户信息 -->
				
				
				<!-- 订单信息 -->
				<div class="orderInfo info" id="orderInfo">
					<p class="info-head">订单信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 人数/领区/加急 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>人数：</label> <input id="number"
										name="number" type="text" class="form-control input-sm"
										placeholder=" " v-model="orderInfo.number" />
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>领区：</label> <select
										class="form-control input-sm" v-model="orderInfo.cityid">
										<c:forEach var="map" items="${obj.collarAreaEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>加急：</label> <select id="urgentType"
										name="urgentType" class="form-control input-sm"
										onchange="selectListData();" v-model="orderInfo.urgenttype">
										<c:forEach var="map" items="${obj.mainSaleUrgentEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3" id="urgentDay">
								<div class="form-group">
									<label>&nbsp;</label> <select id="urgentDay" name="urgentDay"
										class="form-control input-sm" v-model="orderInfo.urgentday">
										<c:forEach var="map" items="${obj.mainSaleUrgentTimeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<!-- end 人数/领区/加急 -->

						<div class="row body-from-input">
							<!-- 行程/付款方式/金额 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>行程：</label> <select id="travel"
										name="travel" class="form-control input-sm"
										v-model="orderInfo.travel">
										<c:forEach var="map" items="${obj.mainSaleTripTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>付款方式：</label> <select id="payType"
										name="payType" class="form-control input-sm"
										v-model="orderInfo.paytype">
										<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>金额：</label> <input id="money" name="money"
										type="text" class="form-control input-sm" placeholder=" "
										v-model="orderInfo.money" />
								</div>
							</div>
						</div>
						<!-- end 行程/付款方式/金额 -->
						<div class="row body-from-input">
							<!-- 签证类型 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>签证类型：</label> <select id="visaType"
										name="visaType" class="form-control input-sm"
										onchange="selectListData();" v-model="orderInfo.visatype">
										<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- <div class="col-sm-8 none" id="sixCounty" v-model="orderInfo.visacounty">
								<div class="form-group viseType-btn"> -->
								
								<c:choose>
									<c:when test="${obj.orderJpinfo.visaType == 2 || obj.orderJpinfo.visaType == 3}">
										<div class="col-sm-8" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-8 none" id="visacounty">
									</c:otherwise>
								</c:choose>
									<div class="form-group viseType-btn">
									<label style="display: block;">&nbsp;</label> 
									<input type="button" name="visacounty" value="冲绳县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="青森县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="岩手县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="宫城县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="秋田县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="山形县" class="btn btn-sm btnState">
									<input type="button" name="visacounty" value="福鸟县" class="btn btn-sm btnState">
								</div>
							</div>
						</div>
						<!-- end 签证类型 -->
						<!-- <div class="row body-from-input">
							过去三年是否访问过 -->
							
							<c:choose>
								<c:when test="${obj.orderJpinfo.visaType == 2 || obj.orderJpinfo.visaType == 3 }">
									<div class="row body-from-input" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:otherwise>
							</c:choose>
							<div class="col-sm-3 " id="isVisited" >
								<div class="form-group">
									<label><span>*</span>过去三年是否访问过：</label> 
									<select id="isVisit"
										name="isVisit" class="form-control input-sm"
										onchange="selectListData();" v-model="orderInfo.isvisit">
										<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
											<option value="${map.key}" >${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-9">
							<c:choose>
								<c:when test="${obj.orderJpinfo.isVisit == 1 }">
									<div id="threexian" class="form-group viseType-btn">
								</c:when>
								<c:otherwise>
									<div id="threexian" class="form-group viseType-btn none">
								</c:otherwise>
							</c:choose>
									<label style="display: block;">&nbsp;</label> 
									<input type="button" name="threecounty" value="岩手县" class="btn btn-sm btnState"> 
									<input type="button" name="threecounty" value="秋田县" class="btn btn-sm btnState"> 
									<input type="button" name="threecounty" value="山形县" class="btn btn-sm btnState">
						</div>
						</div>
						</div><!-- end 过去三年是否访问过 -->
						<div class="row body-from-input">
							<!-- 出行时间/停留天数/返回时间 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>出行时间：</label> <input id="goTripDate"
										name="goTripDate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										v-model="orderInfo.gotripdate" />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>停留天数：</label> <input id="stayDay"
										name="stayDay" type="text" class="form-control input-sm"
										placeholder=" " v-model="orderInfo.stayday" />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>返回时间：</label> <input id="backTripDate"
										name="backTripDate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										v-model="orderInfo.backtripdate" />
								</div>
							</div>
						</div>
						<!-- end 出行时间/停留天数/返回时间 -->
						<div class="row body-from-input">
							<!-- 送签时间/出签时间 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>送签时间：</label> <input id="sendVisaDate"
										name="sendVisaDate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										v-model="orderInfo.sendvisadate" />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>出签时间：</label> <input id="outVisaDate"
										name="outVisaDate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										v-model="orderInfo.outvisadate" />
								</div>
							</div>
						</div>
						<!-- end 送签时间/出签时间 -->
					</div>
				</div>
				<!-- end 订单信息 -->
				<div class="row body-from-input" id="applicantInfo"><!-- 添加申请人 -->
					<div class="col-sm-12">
						<div class="form-group">
							<button type="button" class="btn btn-primary btn-sm addApplicantBtn">添加申请人</button>
						</div>
					</div>
				</div><!-- end 添加申请人 -->

							
				<!-- 主申请人 -->
				<div class="info none" id="mySwitch">
					<input type="hidden" id="appId" value="" name="appId"/>
					<p class="info-head">
						<input type="button" name="" value="添加"
							class="btn btn-primary btn-sm pull-right" v-on:click="addApplicant(${obj.orderId })" />
					</p>
					<div class="info-table" style="padding-bottom: 1px;">
						<table id="principalApplicantTable" class="table table-hover"
							style="width: 100%;">
							<thead>
								<tr>
									<th><span>姓名<span></th>
									<th><span>电话<span></th>
									<th><span>邮箱<span></th>
									<th><span>护照号<span></th>
									<th><span>性别<span></th>
									<th><span>操作<span></th>
								</tr>
							</thead>
							<tbody v-for="applicant in applicantInfo"  >
								<tr>
									<td>{{applicant.applyname}}</td>
									<td>{{applicant.telephone}}</td>
									<td>{{applicant.email}}</td>
									<td>{{applicant.passport}}</td>
									
									<td>{{applicant.sex}}</td>
									<td><a v-on:click="updateApplicant(applicant.id);">基本信息</a>&nbsp;&nbsp;<a
										v-on:click="passport(applicant.id)">护照</a>&nbsp;&nbsp;<a
										v-on:click="visa(applicant.id)">签证</a> <br>
									<a v-on:click="">回邮</a>&nbsp;&nbsp;<a
										v-on:click="deleteApplicant(applicant.id)">删除</a></br></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!-- end 主申请人 -->

				<div class="row body-from-input" id="backmailInfo"><!-- 添加回邮信息 -->
					<div class="col-sm-12">
						<div class="form-group">
							<button type="button" class="btn btn-primary btn-sm addExpressInfoBtn">添加回邮信息</button>
						</div>
					</div>
				</div><!-- end 添加回邮信息 -->

				<!-- 快递信息 -->
				<div class="info expressInfo none" id="expressInfo"
					name="backmailInfo">
					<p class="info-head">快递信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 资料来源/回邮方式/回邮地址 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>资料来源：</label> <select id="datasour"
										name="datasour" class="form-control input-sm">
										<c:forEach var="map" items="${obj.mainBackMailSourceTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select> <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>回邮方式：</label> <select id="expressType"
										name="expressType" class="form-control input-sm">
										<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select> <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>回邮地址：</label> <input id="expressAddress"
										name="expressAddress" type="text"
										class="form-control input-sm" placeholder=" " /> <i
										class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 资料来源/回邮方式/回邮地址 -->

						<div class="row body-from-input" style="padding-left:0;">
							<!-- 联系人/电话/发票项目内容/发票抬头 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>联系人：</label> <input id="linkman"
										name="linkman" type="text" class="form-control input-sm"
										placeholder=" " /> <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>电话：</label> <input id="telephone"
										name="telephone" type="text" class="form-control input-sm"
										placeholder=" " /> <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票项目内容：</label> <input id="invoiceContent"
										name="invoiceContent" type="text"
										class="form-control input-sm" placeholder=" " /> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票抬头：</label> <input id="invoiceHead"
										name="invoiceHead" type="text" class="form-control input-sm"
										placeholder=" " /> <i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 联系人/电话/发票项目内容/发票抬头 -->

						<div class="row body-from-input" style="padding-left:0;">
							<!-- 团队名称/快递号/备注 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>团队名称：</label> <input id="" name=""
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>快递号：</label> <input id="expressNum"
										name="expressNum" type="text" class="form-control input-sm"
										placeholder=" " /> <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>备注：</label> <input id="remark"
										name="remark" type="text" class="form-control input-sm"
										placeholder=" " /> <i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 团队名称/快递号/备注 -->
					</div>
				</div>
				<!-- end 快递信息 -->

			</section>
		</div>
		<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<!-- select2 -->
	<script
		src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/orderJp/searchCustomerInfo.js"></script>
	<!-- 公用js文件 -->
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/admin/orderJp/order.js"></script>
	<!-- 本页面js文件 -->
	<script type="text/javascript">
		//签证六县，访问三县选中状态
		var threecounty = '${obj.orderJpinfo.visaCounty}';
		if(threecounty){
			var threecountys = threecounty.split(",");
			for (var i = 0; i < threecountys.length; i++) {
				$('[name=visacounty]').each(function(){
					if(threecountys[i] == $(this).val()){
						$(this).addClass('btnState-true');
					}
				});
			}
		}
		var threecounty = '${obj.orderJpinfo.threeCounty}';
		if(threecounty){
			var threecountys = threecounty.split(",");
			for (var i = 0; i < threecountys.length; i++) {
				$('[name=threecounty]').each(function(){
					if(threecountys[i] == $(this).val()){
						$(this).addClass('btnState-true');
					}
				});
			}
		}
	
		$(function(){
			customerTypeSelect2();
			//客户来源
			$("#customerType").change(function(){
				var customerVal = $(this).val();
				if(customerVal == 4){//直客
					$(".on-line").hide();//隐藏select2部分字段
					$(".zhiKe").removeClass("none");
				}else{
					$(".on-line").show();//显示select2部分字段
					$(".zhiKe").addClass("none");
				}
			});
		});
		
		var url = "${base}/admin/orderJp/getOrder.html";
		var orderobj;
		var orderid = ${obj.orderId};
		new Vue({
			el : '#wrapper',
			data : {
				customerInfo : "",
				orderInfo : "",
				applicantInfo : ""
			//backmailInfo : ""
			},
			created : function() {
				orderobj = this;
				var id = '${obj.orderId}';
				$.ajax({
					url : url,
					dataType : 'json',
					type : 'POST',
					data : {
						id : id
					},
					success : function(data) {
						console.log(JSON.stringify(data));
						orderobj.customerInfo = data.customerInfo;
						orderobj.orderInfo = data.orderInfo;
						orderobj.applicantInfo = data.applicantInfo;
						//this.backmailInfo = data.backmailInfo;
						var isVisited = orderobj.orderInfo.isvisit;
						var visaType = orderobj.orderInfo.visatype;
						var mainSaleUrgentEnum = orderobj.orderInfo.urgenttype;
						if(orderobj.applicantInfo == null || orderobj.applicantInfo == undefined || orderobj.applicantInfo.length == 0){
							$("#mySwitch").addClass("none");
							$("#applicantInfo").show();
							$(".addApplicantBtn").click(function(id){
								alert(id);
								layer.open({
									type: 2,
									title: false,
									closeBtn:false,
									fix: false,
									maxmin: false,
									shadeClose: false,
									scrollbar: false,
									area: ['900px', '551px'],
									content:'/admin/orderJp/addApplicant.html'
								});
								
							});
						}else{
							$("#mySwitch").removeClass("none");//显示申请人信息列表
							$("#applicantInfo").hide();//添加申请人 按钮 隐藏
						}
						
						//签证类型  按钮的点击状态
						$(".viseType-btn input").click(function(){
							if($(this).hasClass('btnState-true')){
								$(this).removeClass('btnState-true');
							}else{
								$(this).addClass('btnState-true');
								var btnInfo=$(this).val();//获取按钮的信息
								console.log(btnInfo);
							}
						});
						$('#visaType').change(function(){
							var thisval = $(this).val();
							if(thisval == 2 || thisval == 3){
								$('#visacounty').show();
								$('#threefangwen').show();
							}else{
								$('#visacounty').hide();
								$('#threefangwen').hide();
							}
						});
						
						$('#isVisit').change(function(){
							var thisval = $(this).val();
							if(thisval == 1){
								$('#threexian').show();
							}else{
								$('#threexian').hide();
							}
						});
						
						$('#urgentType').change(function(){
							var thisval = $(this).val();
							if(thisval != 1){
								$("#urgentDay").removeClass("none");
							}else{
								$("#urgentDay").addClass("none");
							}
						});
					},
					error : function() {
						alert("error");
					}
				});
			},
			methods : {
				order : function() {
					//绑定签证城市
					var visacounty = "";
					$('[name=visacounty]').each(function(){
						if($(this).hasClass('btnState-true')){
							visacounty += $(this).val() + ',';
						}
					});
					if(visacounty){
						visacounty = visacounty.substr(0,visacounty.length-1);
					}
					orderobj.orderInfo.visacounty = visacounty;
					//绑定三年城市
					var threecounty = "";
					$('[name=threecounty]').each(function(){
						if($(this).hasClass('btnState-true')){
							threecounty += $(this).val() + ',';
						}
					});
					if(threecounty){
						threecounty = threecounty.substr(0,threecounty.length-1);
					}
					orderobj.orderInfo.threecounty = threecounty;
					var editdata = orderobj.orderInfo;
					editdata.customerinfo = JSON.stringify(orderobj.customerInfo);
					//var applicant = orderobj.applicantInfo;
					var backmail;
					$.ajax({
						type : 'POST',
						data : editdata,
						url : '${base}/admin/orderJp/order',
						success : function(data) {
							layer.closeAll('loading');
				    		window.location.reload();
							window.location.href = '${base}/admin/orderJp/list';
						},
						error : function() {
							alert("error");
						}
					}); 
					//window.location.href = '${base}/admin/orderJp/order';
					//console.log(message);
					//alert(JSON.stringify(event.target)); 
				},
			addApplicant : function(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '551px'],
					content:'/admin/orderJp/addApplicant.html?id='+id
				});
			},
			updateApplicant : function(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '551px'],
					content:'/admin/orderJp/updateApplicant.html?id='+id
				});
			},
			passport : function(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '551px'],
					content:'/admin/orderJp/passportInfo.html?id='+id
				});
			},
			deleteApplicant : function(id){
				$.ajax({ 
			    	url: '${base}/admin/orderJp/deleteApplicant',
			    	dataType:"json",
			    	data:{applicantId:id},
			    	type:'post',
			    	success: function(data){
			    		successCallBack(2);
			      	}
			    }); 
			}
			}
		});
		//添加申请人
		var id = ${obj.orderId};
		
		
		//刷新申请人表格
		function successCallBack(status){
			if(status == 1){
				layer.msg('修改成功');
			}
			if(status == 2){
				layer.msg('删除成功');
			}
			if(status == 3){
				layer.msg('添加成功');
			}
				$.ajax({ 
			    	url: '${base}/admin/orderJp/getEditApplicant',
			    	dataType:"json",
			    	data:{orderid:orderid},
			    	type:'post',
			    	success: function(data){
			    		orderobj.applicantInfo = data;
			      	}
			    }); 
			
		}
		
		 
	</script>
</body>
</html>
