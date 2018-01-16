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
											name="visatype" class="form-control input-sm"
											onchange="selectListData();">
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
											name="isvisit" class="form-control input-sm"
											onchange="selectListData();">
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


					<%-- <div class="row body-from-input" id="backmailInfo">
						<!-- 添加回邮信息 -->
						<div class="col-sm-12">
							<div class="form-group">
								<button type="button"
									class="btn btn-primary btn-sm addExpressInfoBtn">添加回邮信息</button>
							</div>
						</div>
					</div>
					<!-- end 添加回邮信息 -->

					<!-- 快递信息 -->
					<div class="info expressInfo none" id="expressInfo"
						name="backmailInfo">
						<p class="info-head">回邮信息</p>
						<div class="info-body-from backmail-div">
							<div class="row body-from-input">
								<!-- 资料来源/快递号/团队名称/回邮方式 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>资料来源：</label> <select id="datasour"
											name="source" class="form-control input-sm">
											<c:forEach var="map"
												items="${obj.mainBackMailSourceTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>快递号：</label> <input id="expressNum"
											name="expressNum" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>团队名称：</label> <input id="teamName" name="teamName"
											type="text" class="form-control input-sm" placeholder=" " />
										
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>回邮方式：</label> <select id="expressType"
											name="expressType" class="form-control input-sm">
											<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								</div>
								<!-- end 资料来源/快递号/团队名称/回邮方式 -->
								
							

							<div class="row body-from-input" style="padding-left:0;">
								<!-- 回邮地址/联系人/电话 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>回邮地址：</label> <input id="expressAddress"
											name="expressAddress" type="text"
											class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> <input id="expressLinkman"
											name="linkman" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>电话：</label> <input id="expressTelephone"
											name="telephone" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								</div>
								<!-- end 回邮地址/联系人/电话/ -->

							<div class="row body-from-input" style="padding-left:0;">
								<!-- 发票项目内容/发票抬头/税号/备注 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票项目内容：</label> <input
											id="invoiceContent" name="invoiceContent" type="text"
											class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票抬头：</label> <input id="invoiceHead"
											name="invoiceHead" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
								
								<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>税号：</label> 
											<input name="taxNum" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>备注：</label> <input id="remark"
											name="remark" type="text" class="form-control input-sm"
											placeholder=" " />
									</div>
								</div>
							</div>
							<!-- end 发票项目内容/发票抬头/税号/备注 -->
							<i class="add-btn"></i>
						</div>
					</div> --%>
					<!-- end 快递信息 -->

				</form>
			</section>
		</div>
		<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>

	</div>

	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/admin/orderJp/order.js"></script>
	<!-- 本页面js文件 -->
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/orderJp/searchCustomerInfo.js"></script>

	<script type="text/javascript">
		$(function(){
			customerTypeSelect2();
			//客户类型判断是不是直客
			/* $("#customerType").change(function(){
				var customerVal = $(this).val();
				if(customerVal == 4){//直客
					$(".on-line").hide();//隐藏select2部分字段
					$(".zhiKe").removeClass("none");
				}else{
					$(".on-line").show();//显示select2部分字段
					$(".zhiKe").addClass("none");
				}
			}); */
			
			
			$("#customerType").change(function(){
				$("#linkman2").val("");
				$("#compName2").val("");
				$("#comShortName2").val("");
				$("#mobile2").val("");
				$("#email2").val("");
				$("#payType").val("");
				//客户姓名清空
				$("#linkman").val(null).trigger("change");
				//电话清空
				$("#mobile").val(null).trigger("change");
				//公司全称
				$("#compName").val(null).trigger("change");
				//公司简称
				$("#comShortName").val(null).trigger("change");
				//邮箱清空
				$("#email").val(null).trigger("change");
				var thisval = $(this).val();
				if(thisval == 4){
					$(".on-line").hide();//隐藏select2部分字段
					$(".zhiKe").removeClass("none");
					
				}else{
					$(".on-line").show();//显示select2部分字段
					$(".zhiKe").addClass("none");
					//customerTypeSelect2();
				}
			});
			
			
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
				if(thisval == 2 || thisval == 3 || thisval == 4){
					$('#sixCounty').removeClass("none");
					$('#threefangwen').removeClass("none");
				}else{
					$('#sixCounty').addClass("none");
					$('#threefangwen').addClass("none");
				}
			});
			
			$('#isVisit').change(function(){
				var thisval = $(this).val();
				if(thisval == 1){
					$('#threeCounty').removeClass("none");
				}else{
					$('#threeCounty').addClass("none");
				}
			});
			
			$('#urgentType').change(function(){
				var urgentType = $(this).val();
				if(urgentType != 1){
					$("#urgentDays").removeClass("none");
				}else{
					$("#urgentDays").addClass("none");
				}
			});
			
		});
		
			/* //点击 蓝色加号图标 事件
			$('.add-btn').click(function(){
		    	var $html=$(this).parent().clone();//克隆标签模块
		    	$(this).parents('.info').append($html);//添加克隆的内容
		    	$html.find('.add-btn').remove();
		    	$html.append('<i class="remove-btn"></i>');
			});
			//点击 蓝色叉号图标 事件
			$(".info").on("click", ".remove-btn", function(){
				$(this).parent().remove();//删除 对相应的本模块
			}); */
			
			$("#addCustomer").click(function(){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['800px', '400px'],
					content: BASE_PATH + '/admin/customer/add.html?isCustomerAdd=0'
				});
		});
		
		function successAddCustomer(data){
			$(".on-line").show();//显示select2部分字段
			$(".zhiKe").addClass("none");
			$("#mobile").append('<option selected="true" value='+ data.id +'>'+data.mobile+'</option>'); 
			/*公司全称补全*/
			$("#compName").append('<option selected="true" value='+ data.id +'>'+data.name+'</option>'); 
			/*公司简称补全*/
			$("#comShortName").append('<option selected="true" value='+ data.id +'>'+data.shortname+'</option>');
			/*邮箱补全*/
			$("#email").append('<option selected="true" value='+ data.id +'>'+data.email+'</option>');
			$("#linkman").append('<option selected="true" value='+ data.id +'>'+data.linkman+'</option>');
			$("#customerType").val(data.source);
			$("#payType").val(data.payType);
		}	
		
		//添加申请人(大按钮)
		var BASE_PATH = '${base}';
		$(".addApplicantBtn").click(function(){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/addApplicantSale.html'
			});
			
		});
		var applData;
		//添加申请人(右上角小添加按钮)
		function addApplicant(){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/addApplicantSale.html'
			});
		}
			
			//刷新申请人列表
			function successCallBack(status,data){
				applData = data;
				if(status == 1){
					layer.msg('修改成功');
				}
				if(status == 2){
					layer.msg('删除成功');
				}
				if(status == 3){
				    layer.msg('添加成功');
					saveAddOrder(3);
				}
				if(status == 4){
					saveAddOrder(3);
				}
					var appId = document.getElementById("appId").value;
					$("#applicantsTable").each(function(){
						var applicants = $(this);
						var result = '';
						$.ajax({ 
					    	url: '${base}/admin/orderJp/getApplicant',
					    	async : false,
					    	dataType:"json",
					    	data:{applicantId:appId},
					    	type:'post',
					    	success: function(data){
					    		if( data.length > 0){
									$("#mySwitch").removeClass("none");//显示申请人信息列表
									$("#applicantInfo").hide();//添加申请人 按钮 隐藏
								for(var i = 0; i < data.length; i++){
									result += '<tr>';
									if(data[i].mainid == data[i].id){
										//为主申请人
										if(data[i].applyname != undefined){
											result += '<td><font color="blue">主   </font></td>';
											result += '<td> ' + data[i].applyname + '</td>';
										}
										else{
											result += '<td></td>';
											result += '<td></td>';
										}
									}else{
										if(data[i].applyname != undefined){
											result += '<td></td>';
											result += '<td>' + data[i].applyname + '</td>';
										}
										else{
											result += '<td></td>';
											result += '<td></td>';
										}
									}
									if(data[i].telephone != undefined){
										result += '<td>' + data[i].telephone + '</td>';
									}else{
										result += '<td></td>';
									}
									
									if(data[i].email != undefined){
										result += '<td>' + data[i].email + '</td>';
									}else{
										result += '<td></td>';
									}
									
									if(data[i].passport != undefined){
										result += '<td>' + data[i].passport + '</td>';
									}else{
										result += '<td></td>';
									}
									
									if(data[i].sex != undefined){
										result += '<td>' + data[i].sex + '</td>';
									}else{
										result += '<td></td>';
									}
									
									result += '<td>
									<a href="javascript:updateApplicant('+data[i].id+');">基本信息</a>&nbsp;&nbsp;
									<a href="javascript:passportInfo('+data[i].id+');">护照信息</a>&nbsp;&nbsp;
									<a href="javascript:visaInfo('+data[i].id+');">签证信息</a>&nbsp;&nbsp;
									<a href="javascript:visaInput('+data[i].applicantjpid+');">签证录入</a>&nbsp;&nbsp;
									<a href="javascript:backmailInfo('+data[i].id+');">回邮</a>&nbsp;&nbsp;
									<a href="javascript:deleteApplicant('+data[i].id+');">删除</a>
									</td>';
									
									result += '</tr>';
								}
								applicants.html(result);
								
								}else{
									$("#mySwitch").addClass("none");//显示申请人信息列表
									$("#applicantInfo").show();//添加申请人 按钮 隐藏
								}
					    	}
						});
				    }); 
				
			}
			
			//修改申请人基本信息
			function updateApplicant(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/orderJp/updateApplicant.html?id='+id+'&orderid='+'&isTrial=0'
				});
			}
				
			//修改申请人护照信息
			
			function passportInfo(applicantId){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid'+'&isTrial=0'
				});
			}
			
			//签证信息
			function visaInfo(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/orderJp/visaInfo.html?id='+id+'&orderid'+'&isOrderUpTime&isTrial=0'
				});
			}
			
			//签证录入
			function visaInput(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content: '/admin/visaJapan/visaInput.html?applyid='+id
				});
			}
			
			//回邮信息
			function backmailInfo(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/backMailJp/backMailInfo.html?applicantId='+id+'&isAfterMarket=0'

				});
			}
			
			//删除申请人
			function deleteApplicant(id){
				layer.confirm("您确认要删除吗？", {
					title:"删除",
					btn: ["是","否"], //按钮
					shade: false //不显示遮罩
				}, function(){
				$.ajax({ 
			    	url: '${base}/admin/orderJp/deleteApplicant',
			    	dataType:"json",
			    	data:{applicantId:id},
			    	type:'post',
			    	success: function(data){
			    		successCallBack(2);
			      	}
			    }); 
			});
			}
			
			
			//下单保存
			function saveAddOrder(status){
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
				
				if($("#urgentDays").hasClass("none") == true){
					$('#urgentDay').val("");
					console.log(JSON.stringify( $("#orderInfo").serialize()));
				}
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
				var backMailInfos = JSON.stringify(getMailInfos());
				var orderinfo = $.param({"backMailInfos":backMailInfos, "visacounty":visacounty, "threecounty":threecounty}) + "&" + $("#orderInfo").serialize();
				//orderinfo.backMailInfos = JSON.stringify(backMails);
				
				
				$.ajax({
					type : 'POST',
					data : orderinfo ,
					url : '${base}/admin/orderJp/saveAddOrderinfo',
					success : function(data) {
						console.log(JSON.stringify(data));
						$("#orderid").val(data.id);
						if(status == 1){
							window.location.href = '${base}/admin/orderJp/list';
						}
						if(status == 2){
							//var orderid = $("#orderid").val(data.id);
							window.location.href = '${base}/admin/orderJp/order?id='+data.id;
						}
						if(status == 3){
							
						}
					},
					error : function() {
						console.log("error");
					}
				}); 
			}
			
			//下单取消
			function cancelAddOrder(){
				window.location.href = '${base}/admin/orderJp/list';
			}
			
			$("#stayDay").keyup(function(){
				var go = $("#goTripDate").val();
				var back = $("#backTripDate").val();
				var day = $("#stayDay").val();
				if(go != "" && day != ""){
					var days = getNewDay(go,day-1);
					$("#backTripDate").val(days); 
					//orderobj.orderInfo.backtripdate = days;
				}
			});
			//日期转换
			function getNewDay(dateTemp, days) {  
			    var dateTemp = dateTemp.split("-");  
			    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
			    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
			    var rDate = new Date(millSeconds);  
			    var year = rDate.getFullYear();  
			    var month = rDate.getMonth() + 1;  
			    if (month < 10) month = "0" + month;  
			    var date = rDate.getDate();  
			    if (date < 10) date = "0" + date;  
			    return (year + "-" + month + "-" + date);  
			}  
			
			$("#money").blur(function(){
				var money = $("#money").val();
				if(money != "" ){
					var moneys = returnFloat(money);
					$("#money").val(moneys); 
				}
			});
			//数字保留两位小数
			function returnFloat(value){
				var value=Math.round(parseFloat(value)*100)/100;
				var xsd=value.toString().split(".");
				if(xsd.length==1){
					value=value.toString()+".00";
				 	return value;
				}
				if(xsd.length>1){
					if(xsd[1].length<2){
				  		value=value.toString()+"0";
				 	}
				 	return value;
				 }
			}
			
			//时间插件格式化  出行时间>送签时间 >今天
			var now = new Date();
			$("#goTripDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate:now,
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			}).on("click",function(){  
			    $("#goTripDate").datetimepicker("setEndDate",$("#backTripDate").val());  
			}); 
			$("#backTripDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate:now,
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			});

			$("#sendVisaDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate: now,//日期大于今天
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			}).on("click",function(){
				$("#sendVisaDate").datetimepicker("setEndDate",$("#goTripDate").val());
			}).on("changeDate",function(){
				//自动计算预计出签时间
				var stayday = 7;
				var sendvisadate = $("#sendVisaDate").val();
				var days = getNewDay(sendvisadate,stayday);
				$("#outVisaDate").val(days); 
			});  
			$("#outVisaDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			});
			
			$(function(){
				//点击 蓝色加号图标 事件
				$('.add-btn').click(function(){
					var newDiv=$(this).parent().clone();//克隆标签模块
					$(this).parents('.info').append(newDiv);//添加克隆的内容
					clearBackMailInfo(newDiv);
					newDiv.find('.add-btn').remove();
					newDiv.append('<i class="remove-btn"></i>');
				});
				//点击 蓝色叉号图标 事件
				$(".info").on("click", ".remove-btn", function(){
					$(this).parent().remove();//删除 对相应的本模块
				});
				
				//如果有数据，隐藏添加回邮信息按钮；同时，设置最有一个为减号按钮
				
			});

			//“+”号 回邮寄信息
			function clearBackMailInfo(newDiv){
				newDiv.find('[name=obmId]').val("");
				newDiv.find('[name=source]').val(1);
				newDiv.find('[name=expressType]').val(1);
				newDiv.find('[name=expressAddress]').val("");
				newDiv.find('[name=linkman]').val("");
				newDiv.find('[name=telephone]').val("");
				newDiv.find('[name=invoiceContent]').val("");
				newDiv.find('[name=invoiceHead]').val("");
				newDiv.find('[name=teamName]').val("");
				newDiv.find('[name=expressNum]').val("");
				newDiv.find('[name=taxNum]').val("");
				newDiv.find('[name=remark]').val("");
			}
	//回邮信息
	function getMailInfos(){
	var backMails = [];
	$('.backmail-div').each(function(i){
		var infoLength = '';
		var backInfo = {};
		
		var obmId = $(this).find('[name=obmId]').val();
		infoLength += obmId;
		backInfo.id = obmId;
		
		var source = $(this).find('[name=source]').val();
		if(source != 1){
			infoLength += source;
		}
		backInfo.source = source;
		
		var expressType = $(this).find('[name=expressType]').val();
		if(expressType != 1){
			infoLength += expressType;
		}
		backInfo.expressType = expressType;
		
		var expressAddress = $(this).find('[name=expressAddress]').val();
		infoLength += expressAddress;
		backInfo.expressAddress = expressAddress;
		
		var linkman = $(this).find('[name=linkman]').val();
		infoLength += linkman;
		backInfo.linkman = linkman;
		
		var telephone = $(this).find('[name=telephone]').val();
		infoLength += telephone;
		backInfo.telephone = telephone;
		
		var invoiceContent = $(this).find('[name=invoiceContent]').val();
		infoLength += invoiceContent;
		backInfo.invoiceContent = invoiceContent;
		
		var invoiceHead = $(this).find('[name=invoiceHead]').val();
		infoLength += invoiceHead;
		backInfo.invoiceHead = invoiceHead;
		
		var teamName = $(this).find('[name=teamName]').val();
		infoLength += teamName;
		backInfo.teamName = teamName;
		
		var expressNum = $(this).find('[name=expressNum]').val();
		infoLength += expressNum;
		backInfo.expressNum = expressNum;
		
		var taxNum = $(this).find('[name=taxNum]').val();
		infoLength += taxNum;
		backInfo.taxNum = taxNum;
		
		var remark = $(this).find('[name=remark]').val();
		infoLength += remark;
		backInfo.remark = remark;

		if(infoLength.length > 0){
			backMails.push(backInfo);
		}
	});
	
	return backMails;
}
		</script>
</body>
</html>
