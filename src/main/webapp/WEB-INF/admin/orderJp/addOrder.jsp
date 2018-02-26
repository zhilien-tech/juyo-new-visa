<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>下单</title>
<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/references/public/css/style.css">
<style type="text/css">
#wrapper { background:#FFF !important;}
.form-control { height: 30px;}
.add-btn { top: -35px; right: -1.5%; }
.remove-btn { top: -35px; right: -1.5%; }
.content-wrapper, .right-side, .main-footer { margin-left: 0; }
.multiPass_roundTrip-div { width: 120px; float: right; position: relative; top: 5px;}
.sm { width:100%; }
.none-sm { width:100%; }
.show-select { width:110px; }
.none-select { padding-left:0px;}
.qz-head { position:fixed;top:0;left:0;z-index:99999; width:100%;}
.content { margin-top:50px;}
.info { position:relative;}
#addCustomer { position:absolute; top:5px; right:10px;}
.col-sm-3 { width:30%;}
.col-sm-1 { width:11.5%;} 
.select2 { width:100% !important;}
.addApplicantBtn { width:30% !important;}
#applicantsTable tr td:nth-child(1) { width:4%;}
#applicantsTable tr td:nth-child(2) { width:10%;}
#applicantsTable tr td:nth-child(3) { width:10%;}
#applicantsTable tr td:nth-child(4) { width:15%;}
#applicantsTable tr td:nth-child(5) { width:15%;}
#applicantsTable tr td:nth-child(6) { width:4%;}
#applicantsTable tr td:nth-child(7) { width:42%;}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper">
			<div class="qz-head">
				<!-- <span class="">订单号：<p>170202-JP0001</p></span> -->
				<!-- <span class="">受付番号：<p>JDY27163</p></span> -->
				<span class="">状态：
					<p>下单</p>
				</span> <input type="button" value="取消"
					class="btn btn-primary btn-sm pull-right" onclick="cancelAddOrder();"/> <input type="button"
					value="保存" class="btn btn-primary btn-sm pull-right"
					onclick="saveAddOrder(1);" /> <!-- <input type="button" value="回邮"
					class="btn btn-primary btn-sm pull-right" /> <input type="button"
					value="初审" class="btn btn-primary btn-sm pull-right" /> <input
					type="button" value="分享" class="btn btn-primary btn-sm pull-right" />
				<input type="button" value="日志"
					class="btn btn-primary btn-sm pull-right" /> -->
			</div>
			<section class="content">
				<form id="orderInfo">
					<div class="info" id="customerInfo" ref="customerInfo">
						<p class="info-head">客户信息</p><input id="addCustomer"
					type="button" value="添加" class="btn btn-primary btn-sm pull-right" />
						<!-- *** -->
						<div class="info-body-from"  style="margin-left:6%;">
							<div class="row body-from-input">
								<!-- 公司全称 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>客户来源：</label> <select id="customerType"
											name="cuSource" class="form-control input-sm">
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.customerTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="on-line">
									<!-- select2 线上/OTS/线下 -->
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司全称：</label> 
											<input type="hidden" id="compNameSelect2" value=""/>
											<select id="compName"
												name="name" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="">
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> 
											<input type="hidden" id="orderid" name="id" value=""/>
											<input type="hidden" id="comShortNameSelect2" value=""/>
											<select id="comShortName"
												name="shortname" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="">
											</select>
										</div>
									</div>
								</div>
								<!-- end select2 线上/OTS/线下 -->

								<div class="zhiKe none">
									<!-- input 直客 -->
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司全称：</label> <input id="compName2"
												name="name" type="text" class="form-control input-sm"
												placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> <input id="comShortName2"
												name="shortname" type="text" class="form-control input-sm"
												placeholder=" " />
										</div>
									</div>
								</div>
								<!-- end input 直客 -->
							</div>
							<div class="row body-from-input on-line">
								<!-- select2 线上/OTS/线下 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label>
										<input type="hidden" id="linkmanSelect2" value=""/>
										 <select id="linkman"
											name="cusLinkman" class="form-control select2 cityselect2 "
											multiple="multiple" data-placeholder="">
										</select>
									</div>
								</div>
								<div class="col-sm-3 compTel">
									<div class="form-group">
										<label><span>*</span>手机号：</label>
										<input type="hidden" id="telephoneSelect2" value=""/>
										 <select id="mobile"
											name="mobile" class="form-control select2 cityselect2 "
											multiple="multiple" data-placeholder="">
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label> 
										<input type="hidden" id="emailSelect2" value=""/>
										<select id="email"
											name="cusEmail" class="form-control select2 cityselect2 "
											multiple="multiple" data-placeholder="">
										</select>
									</div>
								</div>
							</div>
							<!-- end select2 线上/OTS/线下 -->


							<div class="row body-from-input zhiKe none">
								<!-- input 直客 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> <input id="linkman2"
											name="cusLinkman" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label> <input id="mobile2"
											name="mobile" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label> <input id="email2"
											name="cusEmail" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
							</div>
							<!-- end input 直客 -->
						</div>
					</div>
					<!-- end 客户信息 -->
					<!-- 订单信息 -->
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">订单信息</p>
						<div class="info-body-from" style="margin-left:6%;">
							<div class="row body-from-input">
								<!-- 人数/领区/加急 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>人数(此人数只是参考人数)：</label> <input id="number"
											name="number" type="text" class="form-control input-sm"
											placeholder=" " />
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label> <select
											class="form-control input-sm" id="cityid" name="cityid">
											<c:forEach var="map" items="${obj.collarAreaEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-1 show-select">
									<div class="form-group">
										<label><span>*</span>加急：</label> <select id="urgentType"
											name="urgenttype" class="form-control input-sm sm">
											<c:forEach var="map" items="${obj.mainSaleUrgentEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-2 none none-select" id="urgentDays">
									<div class="form-group">
										<label>&nbsp;</label> <select id="urgentDay" name="urgentday"
											class="form-control input-sm none-sm">
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
											name="travel" class="form-control input-sm">
											<c:forEach var="map" items="${obj.mainSaleTripTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>付款方式：</label> <select id="payType"
											name="paytype" class="form-control input-sm">
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>金额：</label> <input id="money"
											name="money" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
							</div>
							<!-- end 行程/付款方式/金额 -->
							<div class="row body-from-input">
								<!-- 签证类型 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label> <select id="visaType"
											name="visatype" class="form-control input-sm" >
											<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-8 none" id="sixCounty">
									<div class="form-group viseType-btn">
										<label style="display: block;">&nbsp;</label> <input
											type="button" name="visacounty" value="冲绳县"
											class="btn btn-sm btnState"> <input type="button"
											name="visacounty" value="青森县" class="btn btn-sm btnState">
										<input type="button" name="visacounty" value="岩手县"
											class="btn btn-sm btnState"> <input type="button"
											name="visacounty" value="宫城县" class="btn btn-sm btnState">
										<input type="button" name="visacounty" value="秋田县"
											class="btn btn-sm btnState"> <input type="button"
											name="visacounty" value="山形县" class="btn btn-sm btnState">
										<input type="button" name="visacounty" value="福鸟县"
											class="btn btn-sm btnState">
									</div>
								</div>
							</div>
							<!-- end 签证类型 -->
							<div class="row body-from-input none" id="threefangwen">
								<!-- 过去三年是否访问过 -->
								<div class="col-sm-3 ">
									<div class="form-group">
										<label><span>*</span>过去三年是否访问过：</label> <select id="isVisit"
											name="isvisit" class="form-control input-sm" >
											<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
												<option value="${map.key}" ${map.key==0?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-8" v-model="orderInfo.threeCounty">
									<div class="form-group viseType-btn none" id="threeCounty">
										<label style="display: block;">&nbsp;</label> <input
											type="button" name="threecounty" value="岩手县"
											class="btn btn-sm btnState"> <input type="button"
											name="threecounty" value="秋田县" class="btn btn-sm btnState">
										<input type="button" name="threecounty" value="山形县"
											class="btn btn-sm btnState">
									</div>
								</div>
							</div>
							<!-- end 过去三年是否访问过 -->
							<div class="row body-from-input">
								<!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label> <input id="goTripDate"
											name="gotripdate" type="text" class="form-control input-sm"
											placeholder=" "  />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程天数：</label> <input id="stayDay"
											name="stayday" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label> <input id="backTripDate"
											name="backtripdate" type="text" class="form-control input-sm"
											placeholder=" "  />
									</div>
								</div>
							</div>
							<!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input">
								<!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计送签时间：</label> <input id="sendVisaDate"
											name="sendvisadate" type="text" class="form-control input-sm"
											placeholder=" "  />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出签时间：</label> <input id="outVisaDate"
											name="outvisadate" type="text" class="form-control input-sm"
											placeholder=" "  />
									</div>
								</div>
							</div>
							<!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<!-- end 订单信息 -->

					<div class="row body-from-input" id="applicantInfo">
						<!-- 添加申请人 -->
						<div class="col-sm-12">
							<div class="form-group">
								<button type="button"
									class="btn btn-primary btn-sm addApplicantBtn">添加申请人</button>
							</div>
						</div>
					</div>
					<!-- end 添加申请人 -->

					<div class="info none" id="mySwitch">
						<!-- 主申请人 -->
						<input type="hidden" id="appId" value="" name="appId"/>
						<p class="info-head"><span>申请人</span>
							<input type="button" name="" value="添加"
								class="btn btn-primary btn-sm pull-right "
								onclick="addApplicant();" />
						</p>
						<div class="info-table" style="padding-bottom: 1px;">
							<table id="principalApplicantTable" class="table table-hover"
								style="width: 100%;">
								<thead>
									<tr>
										<th><span>&nbsp;</span></th>
										<th><span>姓名</span></th>
										<th><span>电话</span></th>
										<th><span>邮箱</span></th>
										<th><span>护照号</span></th>
										<th><span>性别</span></th>
										<th><span>操作</span></th>
									</tr>
								</thead>
								<tbody name="applicantsTable" id="applicantsTable">

									<%-- <tr>

									<td>${applicant.firstName }</td>
									<td>${applicant.telephone }</td>
									<td>${applicant.email }</td>
									<td>${applicant.passport }</td>
									<td>${applicant.sex }</td>
									<td>
										<a v-on:click="">基本信息</a>&nbsp;&nbsp;
										<a v-on:click="passport(apply.applyid)">护照</a>&nbsp;&nbsp;
										<a v-on:click="visa(apply.applyid)">签证</a><br>
										<a v-on:click="">回邮</a>&nbsp;&nbsp;
										<a v-on:click="passport(apply.applyid)">删除</a></br>
									</td>
								</tr> --%>
								</tbody>
							</table>
						</div>
					</div>
					<!-- end 主申请人 -->
				</form>
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
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/admin/orderJp/addOrder.js"></script>
	<!-- 本页面js文件 -->
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/orderJp/searchCustomerInfo.js"></script>
	<%-- <script type="text/javascript" src="${base}/admin/common/commonjs.js"></script> --%>

	<script type="text/javascript">

	</script>
</body>
</html>
