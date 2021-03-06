<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>下单</title>
<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/references/public/css/style.css?v=<%=System.currentTimeMillis() %>">
<!-- 本页css -->
<link rel="stylesheet" href="${base}/references/common/css/simpleAddOrder.css?v=<%=System.currentTimeMillis() %>">
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
					onclick="saveAddOrder(1);" />
				<input type="hidden" id="orderid" name="orderid" value=""/>
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
											name="customerType" class="form-control input-sm" tabindex="1">
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
											<input type="hidden" id="customerid" name="customerid" value="">
											<select id="compName"
												name="name" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="" tabindex="2">
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> 
											<select id="comShortName"
												name="shortname" class="form-control select2 cityselect2 "
												multiple="multiple" data-placeholder="" tabindex="3">
											</select>
										</div>
									</div>
								</div>
								<!-- end select2 线上/OTS/线下 -->

								<div class="zhiKe none">
									<!-- input 直客 -->
									<div class="col-sm-3">
										<input type="hidden" id="zhikecustomid" name="zhikecustomid" value="">
										<div class="form-group">
											<label><span>*</span>公司全称：</label> <input id="compName2"
												name="name" type="text" class="form-control input-sm"
												placeholder=" " tabindex="2"/>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label> <input id="comShortName2"
												name="shortname" type="text" class="form-control input-sm"
												placeholder=" " tabindex="3"/>
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
												placeholder=" " tabindex="4">
												<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label> <select id="visatype"
											name="visatype" type="text" class="form-control input-sm"
											placeholder=" " tabindex="5">
												<option value="">请选择</option>
												<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
									</div>
								</div>
								<div class="col-sm-3" id="customamount">
									<div class="form-group">
										<label><span>*</span>金额：</label> <input id="amount"
											name="amount" type="text" class="form-control input-sm"
											placeholder=" " tabindex="6"/>
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
											class="form-control input-sm" id="cityid" name="cityid" tabindex="7">
											<option value="">请选择</option>
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
											name="urgenttype" class="form-control input-sm sm" tabindex="8">
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
											class="form-control input-sm none-sm" tabindex="9">
											<c:forEach var="map" items="${obj.mainSaleUrgentTimeEnum}">
												<option value=${map.key}>${map.value}</option>
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
										<label><span>*</span>预计送签时间：</label> 
										<input 
											id="sendVisaDate" 
											autocomplete="off"
											name="sendvisadate" 
											type="text" 
											class="form-control input-sm"
											placeholder=" "  
											tabindex="10"
										/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出签时间：</label> <input id="outVisaDate" autocomplete="off"
											name="outvisadate" type="text" class="form-control input-sm datetimepickercss"
											placeholder=" "  tabindex="11"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>送签编号：</label>
										<input id="sendvisanum" type="text" autocomplete="off" class="form-control input-sm" tabindex="12"/>
									</div>
								</div>
							</div>
							<!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">出行信息</p>
						<div class="info-body-from" style="margin-left:6%;">
						
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<input id="historyOrdernum" class="form-control input-sm" value="" placeholder="输入需要复制行程的订单号，回车" onkeypress="onkeyEnter()"/>
									</div>
								</div>
							</div>
							<div class="row body-from-input"><!-- 往返/多程 / 出行目的 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的：</label>
										<input id="tripPurpose" name="tripPurpose" autocomplete="off" type="text" class="form-control input-sm" placeholder=" " tabindex="13" value="旅游"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>往返/多程：</label>
										<select id="triptype" class="form-control input-sm" tabindex="14">
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
										<input id="goDate" name="" type="text" autocomplete="off"  class="form-control input-sm datetimepickercss" tabindex="15"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="stayday" type="text" autocomplete="off" class="form-control input-sm" tabindex="16"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="returnDate" type="text" autocomplete="off" class="form-control input-sm datetimepickercss" tabindex="17"/>
									</div>
								</div>
							</div>
							
							
							<div class="row body-from-input transfer"><!-- 出发日期/出发城市/抵达城市/航班号 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>国内段出发城市：</label>
												<select id="newgodeparturecity" oninput="clearplan()" class="form-control select2 select2City" multiple="multiple" tabindex="17" >
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>抵达城市：</label>
												<select id="gotransferarrivedcity" oninput="clearplan()" class="form-control select2 select2City" multiple="multiple" tabindex="17" >
												</select>
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="gotransferflightnum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17" >
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
								
									</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
									<div class="row body-from-input transfer">
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>国际段出发城市：</label>
												<select id="gotransferdeparturecity" class="form-control input-sm select2City" oninput="clearplan()" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>抵达城市：</label>
												<select id="newgoarrivedcity" class="form-control input-sm select2City" oninput="clearplan()" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="newgoflightnum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17" >
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
									</div>
									
									<div class="row body-from-input transfer"><!-- 返回日期/出发城市/返回城市/航班号 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>国际段返回城市：</label>
												<select id="newreturndeparturecity" class="form-control select2 select2City" multiple="multiple" tabindex="17">
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>抵达城市：</label>
												<select id="returntransferarrivedcity" class="form-control select2 select2City" multiple="multiple" tabindex="27">
												</select>
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="returntransferflightnum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
									</div><!-- end 返回日期/出发城市/返回城市/航班号 -->
									<div class="row body-from-input transfer">
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>国内段返回城市：</label>
												<select id="returntransferdeparturecity" class="form-control input-sm select2City" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>抵达城市：</label>
												<select id="newreturnarrivedcity" class="form-control input-sm select2City" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="newreturnflightnum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17">
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
									</div>
							
							
							
							<div class="row body-from-input direct"><!-- 出发日期/出发城市/抵达城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="goDepartureCity" class="form-control select2 select2City" multiple="multiple" tabindex="17">
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label>
										<select id="goArrivedCity" class="form-control input-sm select2City" multiple="multiple" tabindex="17">
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3 paddingRight">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17">
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
							
							<div class="row body-from-input direct"><!-- 返回日期/出发城市/返回城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="returnDepartureCity" class="form-control select2 select2City" multiple="multiple" tabindex="17">
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label>
										<select id="returnArrivedCity" class="form-control input-sm select2City" multiple="multiple" tabindex="17">
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3 paddingRight">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" tabindex="17">
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
										<th><span>手机号</span></th>
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
		var cityidstr = '${obj.orderinfo.cityId}';
		var visatype = '';
		let sendVisaDateVal = "${obj.orderinfo.sendVisaDate }";
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<%-- <script src="${base}/admin/orderJp/order.js"></script> --%>
	<!-- 本页面js文件 -->
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/simple/customerInfo.js"></script>
	<script src="${base}/admin/simple/travelinfo.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/initpagedata.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/addsimpleorder.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script type="text/javascript" src="${base}/admin/common/commonjs.js?v=<%=System.currentTimeMillis() %>"></script>

	<script type="text/javascript">
	
		$(".transfer").hide();
		$(".direct").show();
	
	
		$("#cityid").change(function(){
			var cityid = $(this).val();
			$("#cityid").val(cityid);
			cityidstr = cityid;
			if($(this).val() == ""){
				
			}else if($(this).val() > 2){
				$(".transfer").show();
				$(".direct").hide();
				$("#goDepartureCity").empty();
				$("#goArrivedCity").empty();
				$("#goFlightNum").empty();
				$("#returnDepartureCity").empty();
				$("#returnArrivedCity").empty();
				$("#returnFlightNum").empty();
			}else{
				$(".transfer").hide();
				$(".direct").show();
				$("#newgodeparturecity").empty();
				$("#gotransferarrivedcity").empty();
				$("#gotransferflightnum").empty();
				$("#gotransferdeparturecity").empty();
				$("#newgoarrivedcity").empty();
				$("#newgoflightnum").empty();
				$("#newreturndeparturecity").empty();
				$("#returntransferarrivedcity").empty();
				$("#returntransferflightnum").empty();
				$("#returntransferdeparturecity").empty();
				$("#newreturnarrivedcity").empty();
				$("#newreturnflightnum").empty();
			}
			
			
		});
		
		$("#visatype").change(function(){
			var vtype = $(this).val();
			visatype = vtype;
			var goArrivedCity = $("#goArrivedCity").val();
			var returnDepartureCity = $("#returnDepartureCity").val();
			//冲绳
			if(vtype == 2 || vtype == 7){
				$("#goArrivedCity").html('<option selected="selected" value="'+77+'">'+'冲绳'+'</option>');
				$("#returnDepartureCity").html('<option selected="selected" value="'+77+'">'+'冲绳'+'</option>');
				if(goArrivedCity != 77){
					$('#goFlightNum').empty();
				}
				if(returnDepartureCity != 77){
					$('#returnFlightNum').empty();
				}
			}
			/* //宫城
			if(vtype == 3 || vtype == 8){
				$("#goArrivedCity").html('<option selected="selected" value="'+91+'">'+'宫城'+'</option>');
				if(goArrivedCity != 91){
					$('#goFlightNum').empty();
				}
			}
			//岩手
			if(vtype == 4 || vtype == 10){
				$("#goArrivedCity").html('<option selected="selected" value="'+92+'">'+'岩手'+'</option>');
				if(goArrivedCity != 92){
					$('#goFlightNum').empty();
				}
			}
			//福岛
			if(vtype == 5 || vtype == 9){
				$("#goArrivedCity").html('<option selected="selected" value="'+30+'">'+'福岛'+'</option>');
				if(goArrivedCity != 30){
					$('#goFlightNum').empty();
				}
			}
			//青森
			if(vtype == 11){
				$("#goArrivedCity").html('<option selected="selected" value="'+25+'">'+'青森'+'</option>');
				if(goArrivedCity != 25){
					$('#goFlightNum').empty();
				}
			}
			//秋田
			if(vtype == 12){
				$("#goArrivedCity").html('<option selected="selected" value="'+612+'">'+'秋田'+'</option>');
				if(goArrivedCity != 612){
					$('#goFlightNum').empty();
				}
			}
			//山形
			if(vtype == 13){
				$("#goArrivedCity").html('<option selected="selected" value="'+613+'">'+'山形'+'</option>');
				if(goArrivedCity != 613){
					$('#goFlightNum').empty();
				}
			} */
			var orderid = $('#orderid').val();
			if(orderid != ""){
				$.ajax({ 
			    	url: '${base}/admin/simple/changeVisatype.html',
			    	dataType:"json",
			    	data:{
			    		orderid:orderid,
			    		visatype:visatype
			    		},
			    	type:'post',
			    	success: function(data){
			      	}
			    });
			}
		});
		
	
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
		
		
			//刷新申请人列表
			function successCallBack(status,data){
				if(status == 1){
					layer.msg('修改成功');
				}
				if(status == 2){
					layer.msg('删除成功');
				}
				if(status == 3){
				    layer.msg('添加成功');
				}
				if(status == 4){
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
				var orderid = $('#orderid').val();
				$.ajax({ 
			    	url: '${base}/admin/simple/cancelOrder.html',
			    	dataType:"json",
			    	data:{orderid:orderid},
			    	type:'post',
			    	async:false,
			    	success: function(data){
						window.location.href = '${base}/admin/simple/list.html';
			      	}
			    }); 
			}
			function successAddCustomer(data){
				
			}
			
			function saveZhaobaoOrderFunction(data){
				saveAddOrder(2);
			}
		</script>
</body>
</html>
