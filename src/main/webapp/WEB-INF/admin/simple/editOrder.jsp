<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑订单</title>
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
.schedulingBtn ,.addApplicantBtn { width:30% !important;}
#applicantsTable tr td:nth-child(1) { width:4%;}
#applicantsTable tr td:nth-child(2) { width:9%;}
#applicantsTable tr td:nth-child(3) { width:9%;}
#applicantsTable tr td:nth-child(4) { width:9%;}
#applicantsTable tr td:nth-child(5) { width:9%;}
#applicantsTable tr td:nth-child(6) { width:42%;}
#applicantsTable tr td:nth-child(7) { width:4%;}
#applicantsTable tr td:nth-child(8) { width:12%;}

#schedulingTable thead tr th:nth-child(1){width:8%;}
#schedulingTable thead tr th:nth-child(2){width:10%;}
#schedulingTable thead tr th:nth-child(3){width:12%;}
#schedulingTable thead tr th:nth-child(4){width:24%;}
#schedulingTable thead tr th:nth-child(5){width:24%;}
#schedulingTable thead tr th:nth-child(6){width:8%;}
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
					onclick="saveAddOrder(2);" />
				<input type="hidden" id="orderid" name="orderid" value="${obj.orderjpinfo.id }"/>
			</div>
			<section class="content">
				<form id="orderInfo">
					<div class="info" id="customerInfo" ref="customerInfo">
						<p class="info-head">客户信息</p>
						<!-- *** -->
						<div class="info-body-from"  style="margin-left:6%;">
							<div class="row body-from-input">
								<!-- 公司全称 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>客户来源：</label> <select id="customerType"
											name="customerType" class="form-control input-sm">
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.customerTypeEnum}">
												<c:choose>
													<c:when test="${map.key eq obj.customerinfo.source }">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="on-line">
									<!-- select2 线上/OTS/线下 -->
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司全称：</label> 
											<input type="hidden" id="customerid" name="customerid" value="${obj.customerinfo.id }">
											<select id="compName"
												name="name" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="">
												<option value="${obj.customerinfo.name }" selected="selected">${obj.customerinfo.name }</option>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> 
											<select id="comShortName"
												name="shortname" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="">
												<option value="${obj.customerinfo.shortname }" selected="selected">${obj.customerinfo.shortname }</option>
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
												placeholder=" " value="${obj.customerinfo.name }"/>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> <input id="comShortName2"
												name="shortname" type="text" class="form-control input-sm"
												placeholder=" " ${obj.customerinfo.shortname }/>
										</div>
									</div>
								</div>
								<!-- end input 直客 -->
							</div>
							<div class="row body-from-input">
								<!-- input 直客 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>付款方式：</label> 
											<select id="payType"
												name="payType" type="text" class="form-control input-sm"
												placeholder=" " >
												<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
													<c:choose>
														<c:when test="${map.key eq obj.customerinfo.payType }">
															<option value="${map.key}" selected="selected">${map.value}</option>
														</c:when>
														<c:otherwise>
															<option value="${map.key}">${map.value}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label> <select id="visatype"
											name="visatype" type="text" class="form-control input-sm"
											placeholder=" " >
												<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
													<c:choose>
														<c:when test="${map.key eq obj.orderjpinfo.visastatus }">
															<option value="${map.key}" selected="selected">${map.value}</option>
														</c:when>
														<c:otherwise>
															<option value="${map.key}">${map.value}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>金额：</label> <input id="amount"
											name="amount" type="text" class="form-control input-sm"
											placeholder=" " value="${obj.orderjpinfo.amount }"/>
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
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label> <select
											class="form-control input-sm" id="cityid" name="cityid">
											<c:forEach var="map" items="${obj.collarAreaEnum}">
												<c:choose>
													<c:when test="${map.key eq obj.orderinfo.cityId}">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
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
												<c:choose>
													<c:when test="${map.key eq obj.orderinfo.urgentType }">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<c:choose>
									<c:when test="${obj.orderinfo.urgentType eq 1 }">
										<div class="col-sm-2 none none-select" id="urgentDays">
									</c:when>
									<c:otherwise>
										<div class="col-sm-2 none-select" id="urgentDays">
									</c:otherwise>
								</c:choose>
									<div class="form-group">
										<label>&nbsp;</label> <select id="urgentDay" name="urgentday"
											class="form-control input-sm none-sm">
											<c:forEach var="map" items="${obj.mainSaleUrgentTimeEnum}">
												<c:choose>
													<c:when test="${map.key eq obj.orderinfo.urgentDay }">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<!-- end 人数/领区/加急 -->

							<div class="row body-from-input">
								<!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计送签时间：</label> <input id="sendVisaDate"
											name="sendvisadate" type="text" class="form-control input-sm"
											placeholder=" "  value="<fmt:formatDate value="${obj.orderinfo.sendVisaDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出签时间：</label> <input id="outVisaDate"
											name="outvisadate" type="text" class="form-control input-sm datetimepickercss"
											placeholder=" "  value="<fmt:formatDate value="${obj.orderinfo.outVisaDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
							</div>
							<!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">出行信息</p>
						<div class="info-body-from" style="margin-left:6%;">
							<div class="row body-from-input"><!-- 往返/多程 / 出行目的 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的：</label>
										<input id="tripPurpose" name="tripPurpose" type="text" class="form-control input-sm" placeholder=" " value="${obj.tripinfo.tripPurpose }"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>往返/多程：</label>
										<select id="triptype" class="form-control input-sm">
											<option value="1">往返</option>
											<!-- <option value="2">多程</option> -->
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 往返/多程 / 出行目的 -->
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="goDate" name="" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.tripinfo.goDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="stayday" type="text" class="form-control input-sm" value="${obj.orderinfo.stayDay }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="returnDate" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.tripinfo.returnDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
							</div>
							<div class="row body-from-input"><!-- 出发日期/出发城市/抵达城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="goDepartureCity" class="form-control select2 select2City" multiple="multiple" >
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.tripinfo.goDepartureCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label>
										<select id="goArrivedCity" class="form-control input-sm select2City" multiple="multiple">
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.tripinfo.goArrivedCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3 paddingRight">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" >
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if test="${obj.tripinfo.goFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName } ${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
							
							<div class="row body-from-input"><!-- 返回日期/出发城市/返回城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="returnDepartureCity" class="form-control select2 select2City" multiple="multiple">
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.tripinfo.returnDepartureCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label>
										<select id="returnArrivedCity" class="form-control input-sm select2City" multiple="multiple">
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.tripinfo.returnArrivedCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3 paddingRight">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple">
											<%-- <c:if test="${!empty obj.tripinfo.returnFlightNum }">
												<option value="${obj.tripinfo.returnFlightNum }" selected="selected">${obj.tripinfo.returnFlightNum }</option>
											</c:if> --%>
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if test="${obj.tripinfo.returnFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName } ${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 返回日期/出发城市/返回城市/航班号 -->
						</div>
					</div>
					<!-- end 订单信息 -->
					<div class="row body-from-input"><!-- 生成行程安排 -->
						<div class="col-sm-12">
							<div class="form-group">
								<button type="button" class="btn btn-primary btn-sm schedulingBtn">生成行程安排</button>
								<table id="schedulingTable" class="table table-hover" style="width:100%;">
									<thead>
										<tr>
											<th><span>天数</span></th>
											<th><span>日期</span></th>
											<th><span>城市</span></th>
											<th><span>景区</span></th>
											<th><span>酒店</span></th>
											<th><span>操作</span></th>
										</tr>
									</thead>
									<tbody id="travelplantbody">
									</tbody>
								</table>
							</div>
						</div>
					</div><!-- end 生成行程安排 -->
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
										<th><span>护照号</span></th>
										<th><span>资料类型</span></th>
										<th><span>真实资料</span></th>
										<th><span>备注</span></th>
										<th><span>操作</span></th>
									</tr>
								</thead>
								<tbody name="applicantsTable" id="applicantsTable">

								</tbody>
							</table>
						</div>
					</div>

				</form>
			</section>
		</div>

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
	<script src="${base}/admin/orderJp/order.js"></script>
	<!-- 本页面js文件 -->
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/simple/customerInfo.js"></script>
	<script src="${base}/admin/simple/travelinfo.js"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/initpagedata.js"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/addsimpleorder.js"></script><!-- 本页面js文件 -->

	<script type="text/javascript">
		//加载申请人表格数据
		initApplicantTable();
		//加载行程安排表格数据
		initTravelPlanTable();
		$(function(){
			$('#urgentType').change(function(){
				var urgentType = $(this).val();
				if(urgentType != 1){
					$("#urgentDays").removeClass("none");
				}else{
					$("#urgentDays").addClass("none");
				}
			});
			
		});
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
					content: '/admin/simple/customer/add.html?isCustomerAdd=0'
				});
		});
			//其他页面回调
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
				initApplicantTable();
				initTravelPlanTable();
			}
			
			function cancelCallBack(status){
				successCallBack(6);
			}
			
			//修改申请人基本信息
			function updateApplicant(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/updateApplicant.html?applicantid='+id+'&orderid='+orderid
				});
			}
				
			//修改申请人护照信息
			
			function passportInfo(applicantId){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/passportInfo.html?applicantid='+applicantId+'&orderid='+orderid
				});
			}
			
			//签证信息
			function visaInfo(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/visaInfo.html?applicantid='+id+'&orderid='+orderid
				});
			}
			
			//签证录入
			function visaInput(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content: '/admin/visaJapan/visaInput.html?applyid='+id+'&orderid='+orderid
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
			
			//下单取消
			function cancelAddOrder(){
				window.close();
			}
			function successAddCustomer(data){
				
			}
		</script>
</body>
</html>