<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>签证详情</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/font-awesome.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/ionicons.min.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
		<!-- 签证详情样式 -->
		<link rel="stylesheet" href="${base}/references/common/css/visaDetail.css?v='20180510'">
		<!-- 加载中。。。样式 -->
		<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper" v-cloak>
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>{{orderinfo.ordernum}}</p></span>
					<span class="">受付番号：<p>{{orderinfo.acceptdesign}}</p></span>
					<span v-if="orderinfo.visastatus === '发招宝中'">状态：<p>{{orderinfo.visastatus}}</p>
						<!-- 加载中 -->
						<div class="spinner">
						  <div class="bounce1"></div>
						  <div class="bounce2"></div>
						  <div class="bounce3"></div>
						</div>
					</span>
					<span v-else>状态：<p>{{orderinfo.visastatus}}</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" onclick="javascript:window.close()"/>
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" onclick="commitdata();"/>
					<input type="button" value="下载" class="btn btn-primary btn-sm pull-right" onclick="downLoadFile()"/>
					<input type="button" value="拒签" class="btn btn-primary btn-sm pull-right" onclick="sendInsurance(27)"/>
					<input type="button" value="招宝取消" class="btn btn-primary btn-sm pull-right btn-Big" onclick="sendInsurance(22)"/>
					<input type="button" value="招宝变更" class="btn btn-primary btn-sm pull-right btn-Big" onclick="sendInsurance(19)"/>
					<input type="button" value="发招宝" class="btn btn-primary btn-sm pull-right" onclick="sendzhaobao()"/>
					<input type="button" value="实收" class="btn btn-primary btn-sm pull-right" onclick="revenue()"/>
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" onclick="log()"/>
				</div>
				<section class="content">
					<!-- 订单信息 -->
					<div class="info">
						<p class="info-head">订单信息</p>
						<div class="info-body-from bodyMargin">
							<div class="row body-from-input"><!-- 人数/领区/加急 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>人数：</label>
										<input id="number" name="number" type="text" class="form-control input-sm mustNumber" placeholder=" " v-model="orderinfo.number"/>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label>
										<select class="form-control input-sm" v-model="orderinfo.cityid">
											<c:forEach var="map" items="${obj.collarareaenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label><span>*</span>加急：</label>
										<select class="form-control input-sm" id="urgenttype" v-model="orderinfo.urgenttype">
											<c:forEach var="map" items="${obj.mainsaleurgentenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3" id="urgentday">
									<div class="form-group">
										<label>&nbsp;</label>
										<select class="form-control input-sm" v-model="orderinfo.urgentday">
											<c:forEach var="map" items="${obj.mainsaleurgenttimeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div><!-- end 人数/领区/加急 -->
						
							<div class="row body-from-input"><!-- 行程/付款方式/金额 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程：</label>
										<select class="form-control input-sm" v-model="orderinfo.travel">
											<c:forEach var="map" items="${obj.mainsaletriptypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>付款方式：</label>
										<select class="form-control input-sm" v-model="orderinfo.paytype">
											<c:forEach var="map" items="${obj.mainsalepaytypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>金额：</label>
										<input id="money" name="money" type="text" class="form-control input-sm mustNumberPoint" placeholder=" " v-model="orderinfo.money" />
									</div>
								</div>
							</div><!-- end 行程/付款方式/金额 -->
							<div class="row body-from-input"><!-- 签证类型 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label>
										<select id="visatype" class="form-control input-sm" v-model="orderinfo.visatype">
											<c:forEach var="map" items="${obj.mainsalevisatypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<c:choose>
									<c:when test="${obj.jporderinfo.visaType != 1 }">
										<div class="col-sm-9" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-9 none" id="visacounty">
									</c:otherwise>
								</c:choose>
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="visacounty" type="button" value="冲绳县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="青森县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="宫城县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="山形县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="福岛县" class="btn btn-sm btnState">
											</div>
										</div>
							</div><!-- end 签证类型 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType != 1 }">
									<div class="row body-from-input" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:otherwise>
							</c:choose>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>过去三年是否访问过：</label>
												<select id="isVisit" class="form-control input-sm" v-model="orderinfo.isvisit">
													<c:forEach var="map" items="${obj.isyesornoenum}">
														<option value="${map.key}">${map.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-9">
										<c:choose>
											<c:when test="${obj.jporderinfo.isVisit == 1 }">
												<div id="threexian" class="form-group viseType-btn">
											</c:when>
											<c:otherwise>
												<div id="threexian" class="form-group viseType-btn none">
											</c:otherwise>
										</c:choose>
												<label style="display:block;">&nbsp;</label>
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
										</div>
									</div><!-- end 过去三年是否访问过 -->
							<div class="row body-from-input target"><!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="gotripdate" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.orderinfo.goTripDate }" pattern="yyyy-MM-dd" />"/>
										<!-- <date-picker field="myDate" placeholder="选择日期"
											 :no-today="true"
											 :value.sync="date3"
											 :format="format"
											 v-model="orderinfo.gotripdate"></date-picker> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程天数：</label>
										<input id="stayday" name="stayday" type="text" class="form-control input-sm mustNumber" value="${obj.orderinfo.stayDay }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="backtripdate" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.orderinfo.backTripDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
							</div><!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input"><!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签时间：</label>
										<input id="sendvisadate" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.orderinfo.sendVisaDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出签时间：</label>
										<input id="outvisadate" type="text" class="form-control input-sm datetimepickercss" value="<fmt:formatDate value="${obj.orderinfo.outVisaDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签编号：</label>
										<input id="sendvisanum" type="text" class="form-control input-sm" value="${obj.orderinfo.sendVisaNum }"/>
									</div>
								</div>
							</div><!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<!-- end 订单信息 -->

					<!-- 申请人 -->
					<div class="info" id="mySwitch">
						<p class="info-head">申请人</p>
						<div class="info-table" style="padding-bottom: 1px;">
							<table id="applicantTable" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>&nbsp; </span></th>
										<th><span>姓名</span></th>
										<th><span>电话</span></th>
										<th><span>护照号</span></th>
										<th><span>资料类型</span></th>
										<th><span>所需资料</span></th>
										<th><span>递送方式</span></th>
										<th><span>备注</span></th>
										<th><span>操作</span></th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="apply in applyinfo">
										<td>
											<div v-if="apply.id==apply.mainid">
												<font color="blue">主</font> 
											</div>
											<div v-else></div>
										</td>
										<td>{{apply.applyname}}</td>
										<td>{{apply.telephone}}</td>
										<td>{{apply.passport}}</td>
										<td>{{apply.type}}</td>
										<td v-html="apply.realinfo"></td>
										<td> <span v-if="(apply.expresstype == 1)"><a href="https://www.ickd.cn/" target="view_window">{{apply.expressnum}}</a></span></td>
										<td>
											<span v-if="apply.id == apply.mainid">
												{{apply.relationremark}}
											</span>
											<span v-else>
												{{apply.mainrelation}}
											</span>
										</td>
										<td><a v-on:click="updateApplicant(apply.id)">基本信息</a>&nbsp;
											<a v-on:click="passport(apply.id)">护照信息</a>&nbsp;
											<a v-on:click="visa(apply.id)">签证信息</a>&nbsp;
											<a v-on:click="noticeSale(apply.id)">通知销售</a>&nbsp;
											<a v-on:click="visainput(apply.applyid)">出境记录</a>
										</td>
									</tr>
								</tbody>
							</table>
							<!-- end 申请人 -->
							<div class="row" id="orderremark">
								<div class="col-sm-12">
									<div class="form-group">
										<label>备注：</label> 
										<input id="remark" name="remark" type="text" class="form-control input-sm" v-model="orderinfo.remark"/>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 出行信息 -->
					<div class="info">
						<p class="info-head">出行信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 往返/多程 / 出行目的 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>往返/多程：</label>
										<select id="triptype" class="form-control input-sm" v-model="travelinfo.triptype">
											<option value="1">往返</option>
											<option value="2">多程</option>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="travelinfo.trippurpose"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 往返/多程 / 出行目的 -->
								<div id="wangfan" class="none">
									<div class="row body-from-input"><!-- 出发日期/出发城市/抵达城市/航班号 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>出发日期：</label>
												<input id="goDate" name="" type="text" class="form-control input-sm datetimepickertoday departuredate" value="<fmt:formatDate value="${obj.travelinfo.goDate}" pattern="yyyy-MM-dd" />"/>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>出发城市：</label>
												<select id="goDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple" v-model="travelinfo.goDepartureCity">
													<c:if test="${!empty obj.goleavecity.id}">
														<option value="${obj.goleavecity.id}" selected="selected">${obj.goleavecity.city}</option>
													</c:if>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>抵达城市：</label>
												<select id="goArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple" v-model="travelinfo.goArrivedCity">
													<c:if test="${!empty obj.goarrivecity.id}">
														<option value="${obj.goarrivecity.id}" selected="selected">${obj.goarrivecity.city}</option>
													</c:if>
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" v-model="travelinfo.goFlightNum">
													<c:if test="${!empty obj.goflightnum.id }">
														<option value="${obj.goflightnum.id }" selected="selected">${obj.goflightnum.takeOffName }-${obj.goflightnum.landingName } ${obj.goflightnum.flightnum } ${obj.goflightnum.takeOffTime }/${obj.goflightnum.landingTime }</option>
													</c:if>
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
									</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
									
									<div class="row body-from-input"><!-- 返回日期/出发城市/返回城市/航班号 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>返回日期：</label>
												<input id="returnDate" type="text" class="form-control input-sm datetimepickertoday departuredate" value="<fmt:formatDate value="${obj.travelinfo.returnDate}" pattern="yyyy-MM-dd" />"/>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>出发城市：</label>
												<select id="returnDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple" v-model="travelinfo.returnDepartureCity">
													<c:if test="${!empty obj.backleavecity.id}">
														<option value="${obj.backleavecity.id}" selected="selected">${obj.backleavecity.city}</option>
													</c:if>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>返回城市：</label>
												<select id="returnArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple" v-model="travelinfo.returnArrivedCity">
													<c:if test="${!empty obj.backarrivecity.id}">
														<option value="${obj.backarrivecity.id}" selected="selected">${obj.backarrivecity.city}</option>
													</c:if>
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
										<div class="col-sm-3 paddingRight">
											<div class="form-group">
												<label><span>*</span>航班号：</label>
												<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" v-model="travelinfo.returnFlightNum">
													<c:if test="${!empty obj.returnflightnum.id }">
														<option value="${obj.returnflightnum.id }" selected="selected">${obj.returnflightnum.takeOffName }-${obj.returnflightnum.landingName } ${obj.returnflightnum.flightnum } ${obj.returnflightnum.takeOffTime }/${obj.returnflightnum.landingTime }</option>
													</c:if>
												</select>
												<!-- <i class="bulb"></i> -->
											</div>
										</div>
									</div><!-- end 返回日期/出发城市/返回城市/航班号 -->
								</div>
								<!-- 多程 -->
								<div id="duocheng" class="none">
									<c:choose>
										<c:when test="${fn:length(obj.multitrip)>0}">
											<c:forEach items="${obj.multitrip }" var="mutil" varStatus="status">
											<div class="row body-from-input duochengdiv"><!-- 返回日期/出发城市/返回城市/航班号 -->
												<div class="col-sm-3">
													<div class="form-group">
														<label><span>*</span>出发日期：</label>
														<input name="departuredate" type="text" class="form-control input-sm datetimepickertoday departuredate" value="<fmt:formatDate value="${mutil.departureDate}" pattern="yyyy-MM-dd" />"/>
													</div>
												</div>
												<div class="col-sm-3">
													<div class="form-group">
														<label><span>*</span>出发城市：</label>
														<select name="departurecity" class="form-control select2 duochengselectcity departurecity" multiple="multiple" v-model="travelinfo.returnDepartureCity">
															<%-- <c:if test="${!empty obj.backleavecity.id}">
																<option value="${obj.backleavecity.id}" selected="selected">${obj.backleavecity.city}</option>
															</c:if> --%>
															<c:forEach items="${obj.citys }" var="city">
																<c:if test="${city.id eq mutil.departureCity }">
																	<option selected="selected" value="${city.id }">${city.city }</option>
																</c:if>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-sm-3"> 
													<div class="form-group">
														<label><span>*</span>抵达城市：</label>
														<select name="arrivedcity" class="form-control input-sm duochengselectcity arrivedcity" multiple="multiple" v-model="travelinfo.returnArrivedCity">
															<c:forEach items="${obj.citys }" var="city">
																<c:if test="${city.id eq mutil.arrivedCity }">
																	<option selected="selected" value="${city.id }">${city.city }</option>
																</c:if>
															</c:forEach>
														</select>
														<!-- <i class="bulb"></i> -->
													</div>
												</div>
												
												<div class="col-sm-3 paddingRight">
													<div class="form-group">
														<label><span>*</span>航班号：</label>
														<select name="flightnum" class="form-control input-sm" multiple="multiple" v-model="travelinfo.returnFlightNum">
															<c:forEach items="${obj.flights }" var="flight">
																<c:if test="${flight.id eq  mutil.flightNum}">
																	<%-- <option selected="selected" value="${flight.id }">${flight.flightnum }</option> --%>
																	<option selected="selected" value="${flight.id }">${flight.takeOffName }-${flight.landingName } ${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
																</c:if>
															</c:forEach>
														</select>
														<!-- <i class="bulb"></i> -->
													</div>
												</div>
												<c:choose>
													<c:when test="${status.index eq 0 }">
														<a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a>
													</c:when>
													<%-- <c:when test="${status.index eq 2 or status.index eq 1 }"> --%>
													<c:when test="${status.index eq (fn:length(obj.multitrip) - 1) or status.index eq (fn:length(obj.multitrip) - 2) }">
													</c:when>
													<c:otherwise>
														<a href="javascript:;" class="glyphicon glyphicon-minus removeIcon"></a>
													</c:otherwise>
												</c:choose>
											</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach begin="0" end="2" varStatus="status">
											<div class="row body-from-input duochengdiv"><!-- 返回日期/出发城市/返回城市/航班号 -->
												<div class="col-sm-3">
													<div class="form-group">
														<label><span>*</span>出发日期：</label>
														<input name="departuredate" type="text" class="form-control input-sm datetimepickertoday departuredate" />
													</div>
												</div>
												<div class="col-sm-3">
													<div class="form-group">
														<label><span>*</span>出发城市：</label>
														<select name="departurecity" class="form-control select2 duochengselectcity departurecity" multiple="multiple" v-model="travelinfo.returnDepartureCity">
															<%-- <c:if test="${!empty obj.backleavecity.id}">
																<option value="${obj.backleavecity.id}" selected="selected">${obj.backleavecity.city}</option>
															</c:if> --%>
														</select>
													</div>
												</div>
												<div class="col-sm-3">
													<div class="form-group">
														<label><span>*</span>抵达城市：</label>
														<select name="arrivedcity" class="form-control input-sm duochengselectcity arrivedcity" multiple="multiple" v-model="travelinfo.returnArrivedCity">
															<%-- <c:if test="${!empty obj.backarrivecity.id}">
																<option value="${obj.backarrivecity.id}" selected="selected">${obj.backarrivecity.city}</option>
															</c:if> --%>
														</select>
														<!-- <i class="bulb"></i> -->
													</div>
												</div>
												<div class="col-sm-3 paddingRight">
													<div class="form-group">
														<label><span>*</span>航班号：</label>
														<select name="flightnum" class="form-control input-sm" multiple="multiple" v-model="travelinfo.returnFlightNum">
															<%-- <c:if test="${!empty obj.returnflightnum.id }">
																<option value="${obj.returnflightnum.id }" selected="selected">${obj.returnflightnum.flightnum }</option>
															</c:if> --%>
														</select>
														<!-- <i class="bulb"></i> -->
													</div>
												</div>
												<c:if test="${status.index eq 0 }">
													<a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a>
												</c:if>
											</div>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</div>
							
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
											<tbody>
												<tr v-for="plan in travelplan">
													<td>第{{plan.day}}天</td>
													<td>{{plan.outdate}}</td>
													<td>{{plan.cityname}}</td>
													<td>{{plan.scenic}}</td>
													<td>{{plan.hotelname}}</td>
													<td>
														<i class="editHui" v-on:click="schedulingEdit(plan.id)"></i>
														<i class="resetHui" v-on:click="resetPlan(plan.id)"></i>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div><!-- end 生成行程安排 -->
						</div>
					</div>
					<!-- end 出行信息 -->
				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
		<!-- 遮罩层 -->
		<div id="bg" style="display:none;position: fixed;top: 0;right: 0;bottom: 0;left: 0;z-index: 1040;background-color:black;opacity: 0.5">生成行程安排中</div>
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			var orderid = '${obj.orderid}';
			var orderinfoid = '${obj.orderinfo.id}';
			var triptype = '${obj.travelinfo.tripType}';
			var multitripjson = '${obj.multitripjson}';
		</script>
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
		<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/references/common/js/vue/vue.min.js"></script>
		<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
		<!-- 公用js文件 -->
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
		<script src="${base}/admin/visaJapan/visaDetail.js"></script><!-- 本页面js文件 -->
		<script src="${base}/admin/visaJapan/visaDetailSelect2.js"></script><!-- 本页面js文件 -->
		<script type="text/javascript">
			var threecounty = '${obj.jporderinfo.visaCounty}';
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
			var threecounty = '${obj.jporderinfo.threeCounty}';
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
			
			function successCallBack(status){
				if(status == 1){
					layer.msg('修改成功<br>订单进入"我的"标签页');
					$.ajax({ 
				    	url: BASE_PATH + '/admin/visaJapan/getTrvalPlanData.html',
				    	dataType:"json",
				    	data:{orderid:orderid},
				    	type:'post',
				    	success: function(data){
				    		orderobj.travelplan = data;
				      	}
				    }); 
				}else if(status == 2){
					layer.msg('保存成功<br>订单进入"我的"标签页');
				}else if(status == 88){
					layer.msg('负责人变更成功');
				}
				$.ajax({ 
					url: '/admin/visaJapan/getVisaDetailApply.html',
					dataType:"json",
					data:{orderid:orderid},
					type:'post',
					success: function(data){
						orderobj.applyinfo = data.applyinfo;
					}
				});
			}
			
			function reloaddata(){
				$.ajax({ 
					url: '/admin/visaJapan/getJpVisaDetailData.html',
					dataType:"json",
					data:{orderid:orderid},
					type:'post',
					success: function(data){
						orderobj.orderinfo = data.orderinfo;
						orderobj.travelinfo = data.travelinfo;
						orderobj.applyinfo = data.applyinfo;
						orderobj.travelplan = data.travelplan;
					}
				});
			}
			function cancelCallBack(status){
			}
			function log(orderid){//日志
				var orderinfoid = '${obj.orderinfo.id}';
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['700px', '80%'],
					content:'/admin/orderJp/log.html?id='+orderinfoid+'&orderProcessType=4'
				});
			}
			
			//实收弹框
			function revenue(){
				layer.open({
        		    type: 2,
        		    title: false,
        		    closeBtn:false,
        		    fix: false,
        		    maxmin: false,
        		    shadeClose: false,
        		    scrollbar: false,
        		    area: ['900px', '550px'],
        		    content: '${base}/admin/visaJapan/revenue.html?orderid='+orderid
        		  });
			}
			//发招宝
			function sendzhaobao(){
				$.ajax({
                 	url: '${base}/admin/visaJapan/validateInfoIsFull.html',
                 	data:{orderjpid:orderid},
                 	dataType:"json",
                 	type:'post',
                 	async:false,
                 	success: function(data){
                 		var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
                 		if(data.data){
                 			url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data;
                 		}
		        		layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['400px', '300px'],
		        		    content: url
		        		  });
                   	}
                 });
			}
			//招宝变更、招宝取消、拒签
			function sendInsurance(visastatus){
				$.ajax({
                 	url: '${base}/admin/visaJapan/sendInsurance',
                 	data:{orderid:orderid,visastatus:visastatus},
                 	dataType:"json",
                 	type:'post',
                 	success: function(data){
                 		reloaddata();
                 		if(visastatus == 16){
	                 		layer.msg('发招宝');
                 		}else if(visastatus == 19){
	                 		layer.msg('招宝变更');
                 		}else if(visastatus == 22){
	                 		layer.msg('招宝取消');
                 		}else if(visastatus == 27){
	                 		layer.msg('报告拒签');
                 		}
                   	}
                 });
			}
		</script>
	</body>
</html>
