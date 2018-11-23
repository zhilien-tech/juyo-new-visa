<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/pcVisa" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>订单详情</title>
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
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/visaDetail.css?v='20180510'">
<!-- 加载中。。。样式 -->
<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
<style>
	.icon-line{
		width: 2.2%;
	}
	#streetspan{
		display: inline;
		font-size: 12px;
		position: inherit;
	}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper">
			<!-- 头部 -->
			<div class="qz-head">
				<span  class="orderNum">订单号：
					<span>${obj.orderinfo.ordernumber}</span>
				</span>
				<span  class="state">状态： 
					<c:choose>
						<c:when test="${obj.isaddorder == 1 }">
							<p id="orderstatus_US">${obj.orderstatus }</p>
						</c:when>
						<c:otherwise>
							<p id="orderstatus_US">${obj.orderstatus }</p>
						</c:otherwise>
					</c:choose>
				
				</span>
				<a id="errorimgPhoto" style="display:  none;" onclick="toErrorphoto('${obj.orderinfo.errorurl}')"><span style="color:red;"><strong>错误信息图片</strong></span></a>
				<a id="reviewimgPhoto" style="display:  none;" onclick="toReviewphoto('${obj.orderinfo.reviewurl}')"><span style="color:red;"><strong>预览信息图片</strong></span></a>
				<%-- <c:choose>
					<c:when test="${!empty obj.orderinfo.errorurl }">
						<a id="errorimgPhoto" onclick="toErrorphoto()"><span style="color:red;"><strong>错误信息图片</strong></span></a>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${!empty obj.orderinfo.reviewurl }">
						<a id="reviewimgPhoto" onclick="toReviewphoto()"><span style="color:red;"><strong>预览信息图片</strong></span></a>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose> --%>
				<!-- <span class="">受付番号：<p>{{orderinfo.acceptdesign}}</p></span> -->
				<%-- <span class="state">状态： 
					<c:if test="${obj.orderInfo.status == '1'}">
						<p>下单</p>
					</c:if> 
					<c:if test="${obj.orderInfo.status == '0'}">
						<p>0</p>
					</c:if>
				</span>  --%>
				<c:choose>
				<c:when test="${obj.isaddorder == 1 }">
					<input type="button" onclick="closeWindow()" value="取消" class="btn btn-primary btn-sm pull-right" /> 
					<input type="button" onclick="save()" value="保存" class="btn btn-primary btn-sm pull-right" /> 
				</c:when>
				<c:otherwise>
					<input type="button" onclick="closeWindow()" value="取消" class="btn btn-primary btn-sm pull-right" /> 
					<input type="button" onclick="save()" value="保存" class="btn btn-primary btn-sm pull-right" /> 
					<input type="button" id="daturl" style="width:92px !important;" onclick="download(3)" value="下载DAT文件" class="btn btn-primary btn-sm pull-right" />
					<input type="button" id="pdfurl" style="width:80px !important;" onclick="download(2)" value="下载确认页" class="btn btn-primary btn-sm pull-right" />
					<input type="button" id="reviewurl" style="width:80px !important;" onclick="download(1)" value="下载预览页" class="btn btn-primary btn-sm pull-right" />
					<input type="button" onclick="refuse()" value="拒签" class="btn btn-primary btn-sm pull-right" />
					<input type="button" onclick="pass()" value="通过" class="btn btn-primary btn-sm pull-right" />
					<c:choose>
						<c:when test="${!empty obj.orderinfo.reviewurl && obj.orderinfo.ispreautofilling == 0  }">
							<input type="button" id="autofill" onclick="autofill()" value="正式填写" class="btn btn-primary btn-sm pull-right btn-Big" />
						</c:when>
						<c:otherwise>
							<input type="button" disabled="disabled" id="autofill" onclick="autofill()" value="正式填写" class="btn btn-primary btn-sm pull-right btn-Big" />
						</c:otherwise>
					</c:choose>
					<%-- <c:choose>
						<c:when test="${!empty obj.orderinfo.reviewurl }">
							<input type="button" id="preautofill" disabled="disabled" onclick="preautofill()" value="预检查" class="btn btn-primary btn-sm pull-right btn-Big" />
						</c:when>
						<c:otherwise>
							<input type="button" id="preautofill" onclick="preautofill()" value="预检查" class="btn btn-primary btn-sm pull-right btn-Big" />
						</c:otherwise>
					</c:choose> --%>
					<input type="button" id="preautofill" onclick="preautofill()" value="预检查" class="btn btn-primary btn-sm pull-right btn-Big" />
					<input type="button" value="通知" onclick="sendEmailUS()" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="日志" onclick="toLog()" class="btn btn-primary btn-sm pull-right" />
				</c:otherwise>
				</c:choose>
			</div>
			<!-- 头部END -->
			<!-- form -->
			<form id="orderUpdateForm">
				<!-- 主体 -->
				<section class="content listDetailContent">
					<!-- 订单信息 -->
					<p class="info-head">订单信息</p>
					<div class="info-body-from">
						<!-- 模块 -->
						<div class="row body-from-input">
							<%-- <div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>团名</label>
									<input type="text" class="input-sm form-control" name="groupname" value="${obj.orderinfo.groupname }" />
								</div>
							</div> --%>
							
							<c:choose>
								<c:when test="${obj.company.comType == 5 }">
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>大客户公司名称</label>
											<select id ="bigcustomername" name="bigcustomername"
														class="form-control select2 cityselect2 " multiple="multiple"
														data-placeholder="" >
														<c:if test="${ !empty obj.bigcom.id }">
															<option value="${obj.bigcom.id }" selected="selected">${obj.bigcom.name }</option>
														</c:if>
													</select>
										</div>
									</div>
								</c:when>
							
							</c:choose>
							
							
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>领区</label>
									<select id="cityid" name="cityid"  class="form-control input-sm" >
										<option value="">--请选择--</option>
									<c:forEach var="map" items="${obj.cityidenum}">
										<option value="${map.key}"  ${map.key==obj.orderinfo.cityid?"selected":"" } >${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>是否付款</label>
									<select id="ispayed" name="ispayed"  class="form-control input-sm" >
									<c:forEach var="map" items="${obj.ispayedenum}">
										<option value="${map.key}"  ${map.key==obj.orderinfo.ispayed?"selected":"" } >${map.value}</option>
									</c:forEach>
								</select>
								</div>
							</div>
							
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>面签时间</label>
									<input id="Interviewdate" type="text" class="interviewformat input-sm form-control" name="Interviewdate" value="${obj.Interviewdate }" />
								</div>
							</div>
						</div>
						<!-- 模块END -->
					</div>
					<!-- 订单信息END -->
					<!-- 出行信息 -->
					<div id="save" class="info">
						<p class="info-head">出行信息</p>
						<!-- 大模块 -->
						<div class="info-body-from">
							<!-- 模块1 -->
							<div class="row body-from-input">
								<!-- 出行目的 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的</label> <select name="travelpurpose"
											class="form-control input-sm" onchange="change()">
											<c:if test="${not empty obj.travelInfo.travelpurpose}">
												<option>${obj.travelInfo.travelpurpose}</option>
											</c:if>
											<c:if test="${empty obj.travelInfo.travelpurpose}">
												<option value="">商务旅游游客(B)</option>
											</c:if>
											<!-- <option>外国政府官员（A）</option>
											<option>商务旅游游客(B)</option>
											<option>过境的外国公民(C)</option>
											<option>CNMI工作者或投资者(CW/E2C)</option>
											<option>机船组人员(D)</option>
											<option>贸易协议国贸易人员或投资者(E)</option>
											<option>学术或语言学生(F)</option>
											<option>国际组织代表/雇员(G)</option>
											<option>临时工作(H)</option>
											<option>外国媒体代表</option>
											<option>交流访问者</option>
											<option>美国公民的未婚夫（妻）或配偶（K）</option>
											<option>公司内部调派人员(L)</option>
											<option>职业/非学术学校的学生(M)</option>
											<option>其他(N)</option>
											<option>北约工作人员(NATO)</option>
											<option>具有特殊才能的人员(O)</option>
											<option>国际承认的外国人士(P)</option>
											<option>文化交流访问者(Q)</option>
											<option>宗教人士(R)</option>
											<option>提供信息者或证人(S)</option>
											<option>人口贩运的受害者(T)</option>
											<option>北美自由贸易协议专业人员(TD/TN)</option>
											<option>犯罪活动的受害者(U)</option>
											<option>假释收益者(PARCIS)</option> -->
										</select>
									</div>
								</div>
								<!-- 出行目的END -->
								<!-- 是否有具体的旅行计划 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>是否有具体的旅行计划</label>
										<div>
											<input type="radio" name="hastripplan" class="tripPlan tripYes" value="1">是 
											<input type="radio" name="hastripplan" class="tripPlan tripNo" value="2" checked>否
										</div>
									</div>
								</div>
								<!-- 是否有具体的旅行计划END -->
							</div>
							<!-- 模块1END -->
							<!-- 模块2 -->
							<div class="row body-from-input">
								<!-- 预计出发时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出发日期：</label> <input id="goDate"
											name="godate" type="text"
											class="form-format form-control input-sm"
											value="<fmt:formatDate value="${obj.travelInfo.godate }" pattern="yyyy-MM-dd" />" />
									</div>
								</div>
								<!-- 预计出发时间END -->
								<!-- 抵达美国日期 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达美国日期：</label> <input id="sendVisaDate"
											name="arrivedate" type="text"
											class="form-format form-control input-sm datetimepickercss"
											value="<fmt:formatDate value="${obj.travelInfo.arrivedate }" pattern="yyyy-MM-dd" />" />
									</div>
								</div>
								<!-- 抵达美国日期END -->
								<!-- 停留天数 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label> <input id="stayday"
											 name="staydays" class="input-sm"
											value="${obj.travelInfo.staydays}" type="text" />
									</div>
								</div>
								<!-- 停留天数END -->
								<!-- 离开美国日期 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label>离开美国日期：</label> <input id="returnDate"
											name="leavedate" type="text"
											class="form-format form-control input-sm datetimepickercss"
											value="<fmt:formatDate value="${obj.travelInfo.leavedate }" pattern="yyyy-MM-dd" />" />
									</div>
								</div>
								<!-- 离开美国日期END -->
							</div>
							<!-- 模块2END -->
							<!-- 模块3 -->
							<div class="row body-from-input checkShowORHide">
								<!-- 出发城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label> 
										<select
											id="goDepartureCity" name="godeparturecity"
											class="form-control select2 select2City departurecity"
											multiple="multiple">
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when
														test="${city.id eq obj.travelInfo.goDepartureCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:if test="${!empty obj.orderInfo.goDepartureCity}">
												<option value="${obj.travelInfo.godeparturecity}"
													selected="selected">${obj.orderInfo.goDepartureCity}</option>
											</c:if>
										</select>
									</div>
								</div>
								<!-- 出发城市END -->
								<!-- 抵达城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label> <select id="goArrivedCity" name="goArrivedCity"
											class="form-control input-sm select2City arrivedcity"
											multiple="multiple">
											<c:if test="${not empty obj.orderInfo.goArrivedCity }">
												<option value="${obj.travelInfo.goArrivedCity}"
													selected="selected">${obj.orderInfo.goArrivedCity}</option>
											</c:if>
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.travelInfo.goArrivedCity }">
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
								<!-- 抵达城市END -->
								<!-- 航班号 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>航班号：</label> 
										<input name="goFlightNum" type="text" value="${obj.travelInfo.goFlightNum }" class="form-control input-sm flightSelect2" />
										<%-- <select id="goFlightNum" name="goFlightNum"
											class="form-control input-sm flightSelect2"
											multiple="multiple">
											<option selected="selected" value="${obj.travelInfo.goFlightNum }">${obj.travelInfo.goFlightNum}</option>
										</select> --%>
											<%-- <c:if test="${!empty obj.goFlightInfo }">
												<option value="${obj.goFlightInfo.flightnum }"
													selected="selected">${obj.goFlightInfo.takeOffName }-${obj.goFlightInfo.landingName }
													${obj.goFlightInfo.flightnum }
													${obj.goFlightInfo.takeOffTime }/${obj.goFlightInfo.landingTime }</option>
											</c:if>
											<input id="goflightnum" name="goFlightNum" type="hidden" />
											<option id="goFlightNum" name="goFlightNum"></option>
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if
													test="${obj.tripinfo.goFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName }
														${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach> --%>
										
									</div>
								</div>
								<!-- 航班号END -->
							</div>
							<!-- 模块3END -->
							<!-- 模块4 -->
							<div class="row body-from-input checkShowORHide">
								<!-- 出发城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label> <select
											id="returnDepartureCity" name="returnDepartureCity"
											class="form-control select2 select2City departurecity"
											multiple="multiple">
											<c:if test="${!empty obj.orderInfo.returnDepartureCity}">
												<option value="${obj.travelInfo.returnDepartureCity}"
													selected="selected">${obj.orderInfo.returnDepartureCity}</option>
											</c:if>
										</select>
									</div>
								</div>
								<!-- 出发城市END -->
								<!-- 返回城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label> <select
											id="returnArrivedCity" name="returnArrivedCity"
											class="form-control input-sm select2City arrivedcity"
											multiple="multiple">
											<c:if test="${!empty obj.orderInfo.returnArrivedCity}">
												<option value="${obj.travelInfo.returnArrivedCity}"
													selected="selected">${obj.orderInfo.returnArrivedCity}</option>
											</c:if>
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when
														test="${city.id eq obj.tripinfo.returnArrivedCity }">
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
								<!-- 返回城市END -->
								<!-- 航班号 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>航班号：</label> 
										<input type="text" name="returnFlightNum" value="${obj.travelInfo.returnFlightNum }" class="form-control input-sm flightSelect2"/>
										<%-- <select id="returnFlightNum" name="returnFlightNum"
											class="form-control input-sm flightSelect2"
											multiple="multiple">
											<option selected="selected" value="${obj.travelInfo.returnFlightNum }">${obj.travelInfo.returnFlightNum}</option>
											</select> --%>
											<%-- <c:if test="${!empty obj.returnFlightInfo }">
												<option value="${obj.returnFlightInfo.flightnum }"
													selected="selected">${obj.returnFlightInfo.takeOffName }-${obj.returnFlightInfo.landingName }
													${obj.returnFlightInfo.flightnum }
													${obj.returnFlightInfo.takeOffTime }/${obj.returnFlightInfo.landingTime }</option>
											</c:if>
											<input id="returnflightnum" name="returnFlightNum"
											type="hidden">
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if
													test="${obj.tripinfo.returnFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName }
														${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach> --%>
										
									</div>
								</div>
								<!-- 航班号END -->
							</div>
							<!-- 模块4END -->
							<!-- 隐藏域 -->
							<input type="hidden" name="orderid" value="${obj.orderid}">
							<!-- 隐藏域END -->
							<!-- 模块5 -->
							<div class="row body-from-input">
								<!-- 送签计划去美国地点 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签计划去美国地点：</label> <select id="planstate" name="planstate"
											class="form-control input-sm">
											<!-- <span>*</span> -->
											<c:forEach items="${obj.state }" var="planstate" >
												<option <c:if test="${obj.travelInfo.state==planstate.key}">selected</c:if>  value="${planstate.key }" >${planstate.value }</option>
											</c:forEach>
										</select>

									</div>
								</div>
								<!-- 送签计划去美国地点END -->
								<!-- 市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label></label>
										
										<select
											id="plancity" name="plancity"
											class="form-control input-sm select2City arrivedcity"
											multiple="multiple">
										<c:if test="${!empty obj.travelInfo.city}">
												<option value="${obj.travelInfo.city}"
													selected="selected">${obj.travelInfo.city}</option>
											</c:if>
										
										
										 
										<%-- <input name="plancity" id="plancity" type="text" onchange="translateZhToEn(this,'plancityen','')" value="${obj.travelInfo.city}"
											class="form-control input-sm" placeholder="市" /> --%>
											<input type="hidden" id="plancityen" name="plancityen" value="${obj.travelInfo.cityen }"/>
									</div>
								</div>
								<!-- 市END -->
								<!-- 街道 -->
								<%-- <div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span><span id="streetspan">只能填写英文和数字</span></label> <input id="planaddress" name="planaddress" onchange="translateZhToEn(this,'planaddressen','')"
											type="text" value="${obj.travelInfo.address}"
											class="form-control input-sm" placeholder="街道英文" />
											<input id="planaddressen" name="planaddressen" type="hidden" value="${obj.travelInfo.addressen }"/>
									</div>
								</div> --%>
								<div class="col-sm-3">
									<div class="form-group">
										<label></label> 
										<select
											id="hotelname" name="hotelname"
											class="form-control input-sm select2City arrivedcity"
											multiple="multiple">
										<c:if test="${!empty obj.travelInfo.hotelname}">
												<option value="${obj.travelInfo.hotelname}"
													selected="selected">${obj.travelInfo.hotelname}</option>
											</c:if>
										
											<input id=""hotelnameen"" name="hotelnameen" type="hidden" value="${obj.travelInfo.hotelnameen }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label></label> <input id="planaddress" name="planaddress" onchange="translateZhToEn(this,'planaddressen','')"
											type="text" value="${obj.travelInfo.address}"
											class="form-control input-sm" placeholder="街道英文" />
											<input id="planaddressen" name="planaddressen" type="hidden" value="${obj.travelInfo.addressen }"/>
									</div>
								</div>
								<!-- 街道END -->
							</div>
							<!-- 模块5END -->
						</div>
						<!-- 大模块END -->
					</div>
					<!-- 订单信息END -->
					<!-- 大模块二 -->
					<div class="info" id="mySwitch">
						<!-- 标题以及按钮组 -->
						<p class="info-head">申请人</p>
						<div class="dataInfoGroup orderInfoGroup">
							<input id="mypassportId" type="hidden" value="${obj.passport.id }">

							<a id="photoInfo" onclick="updatePhoto(${obj.basicinfo.id })">第一步：拍照资料</a>
							<span class="icon-line"></span>
							<a onclick="baseInfo(${obj.basicinfo.id })">第二步：基本信息</a>

							<span class="icon-line"></span>
							<a onclick="passport(${obj.basicinfo.id })">第三步：护照信息</a>
							
							<!-- <span class="icon-line"></span>
							<a onclick="visa(${obj.basicinfo.id })">第四步：签证信息</a> -->

							<span class="icon-line"></span>
							<a onclick="FamilyInfo(${obj.basicinfo.id })">第四步：家庭信息</a>
							<span class="icon-line"></span>
							<a onclick="WorkInfo(${obj.basicinfo.id })">第五步：工作教育信息</a>
							<span class="icon-line"></span>
							<a onclick="TravelInfo(${obj.basicinfo.id })">第六步：旅行信息</a>
						</div>
						<!-- 标题以及按钮组END -->

						<div class="info-body-from infobody">
							<!-- 模块1 -->
							<div class="row body-from-input">
								<!-- 申请人左侧 -->
								<div class="col-sm-3">
									<!-- 二寸免冠照片 -->
									<div class="col-xs-10 picturesInch">
										<div class="form-group pictureTop">
											<div class="uploadInfo">
												<span class="inchInfo">美签照片51*51mm</span>  
												<img id="imgInch" name="imgInch" alt="" src="${obj.twoinchphoto.url }"> 
												<!-- <input id="uploadFileInchImg" name="uploadFileInchImg" disable="true"
													class="btn btn-primary btn-sm" type="file" value="上传" />  -->
													<i class="delete" ></i>
											</div>
										</div>
									</div>
									<!-- 二寸免冠照片END -->
									<!-- 出行目的 -->
									<!-- <div class="col-sm-12 purpose">
										<div class="form-group">
											<label>出行目的</label> <input type="text"
												class="form-control input-sm" placeholder="" />
										</div>
									</div> -->
									<!-- 出行目的END -->
								</div>
								<!-- 申请人左侧END -->
								<!-- 申请人右侧 -->
								<div class="col-sm-9 colWidth">
									<!-- 右侧模块1 -->
									<div class="row body-from-input">
										<!-- 姓名/拼音 -->
										<div class="col-sm-4 colwidthsm">
											<div class="form-group">
												<label>姓名/拼音</label> <input id="allname" disabled="true"
													value="${obj.passport.firstname }${obj.passport.lastname }/${obj.passport.firstnameen }${obj.passport.lastnameen }" type="text"
													class="form-control input-sm" />
											</div>
										</div>
										<!-- 姓名/拼音END -->
										<!-- 性别 -->
										<div class="col-sm-4 colwidthsm colwidthLR">
											<div class="form-group">
												<label>性别</label> <input id="sex" name="sex" disabled="true"
													value="${obj.passport.sex }" type="text"
													class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 性别END -->
										<!-- 出生日期 -->
										<div class="col-sm-4 colwidthsm">
											<div class="form-group">
												<label>出生日期</label> <input id="birthday" name="birthday" type="text"
													disabled="true" value="${obj.birthday }" 
													class="form-format form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 出生日期END -->
									</div>
									<!-- 右侧模块1END -->
									<!-- 右侧模块2 -->
									<div class="row body-from-input">
										<div class="col-sm-8 col8Width">
											<div class="form-group">
												<label>所需资料</label> <input id="realinfo" name="realinfo" disabled="true"
													value="${obj.realinfo }" type="text"
													class="form-control input-sm" /> <input id="staffid" name="staffid"
													type="hidden" value="${obj.basicinfo.id }">
											</div>
										</div>
										<div class="col-sm-4 colwidthsm">
											<div class="form-group">
												<label>卡号</label> 
												<input id="cardnum" name="cardnum" type="text" disabled value="${obj.basicinfo.cardnum }" class="form-control input-sm"  />
											</div>
										</div>
										
									</div>
									<!-- 右侧模块2END -->
									<!-- 右侧模块3 -->
									<div class="row body-from-input">
										<!-- AA码 -->
										<div class="col-sm-4 colwidthsm">
											<div class="form-group">
												<label>AA码</label> <input id="aacode" name="aacode" type="text"
													disabled="true" value="${obj.summaryInfo.aacode }"
													class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- AA码END -->
										<!-- 护照号 -->
										<div class="col-sm-4 colwidthsm colwidthLR">
											<div class="form-group">
												<label>护照号</label> <input id="passport" name="passport" type="text"
													disabled="true" value="${obj.summaryInfo.passport }"
													class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 护照号END -->
										<!-- 面试时间 -->
										<div class="col-sm-4 colwidthsm">
											<div class="form-group">
												<label>面签时间</label> <input id="interviewdate2"  type="text"
													disabled="true" value="${obj.Interviewdate }"
													class="form-format form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 面试时间END -->
									</div>
									<!-- 右侧模块3END -->
									<!-- 右侧模块4 -->
									<!-- <div class="row body-from-input">
										出行目的
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行目的</label> 
												<input id="" type="text" class="form-control input-sm" />
											</div>
										</div>
										出行目的END
										出行时间
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行时间</label> 
												<input id="" type="text" class="form-format form-control input-sm" />
											</div>
										</div>
										出行时间END
										停留天数
										<div class="col-sm-4">
											<div class="form-group">
												<label>停留天数</label> 
												<input id="" type="text" class="form-control input-sm" />
											</div>
										</div>
										停留天数END
									</div> -->
									<!-- 右侧模块4END -->
								</div>
								<!-- 申请人右侧END -->
							</div>
							<!-- 模块1END -->
						</div>
					</div>
					<!-- 大模块2END -->
					<!-- 大模块3 -->
					<!-- 标题以及按钮组 -->
					<div class="info solveContent" id="mySwitch">
						<p class="info-head followUp">
							跟进
							<div class="dataInfoGroup">
								<a onclick="addFollow()">加跟进</a> 
							</div>
						</p>
						<!-- 标题以及按钮组END -->
						<div class="followUpMain">
							<div class="row body-from">
								<div class="fixedheight">
									<ul  id="forFollow">
										<!-- 循环 -->
										<!-- 循环END -->
									</ul>
								</div>
							</div>
						</div>
					</div>	
					<!-- 大模块3END -->
				</section>
			</form>
		</div>
	</div>
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
	<%-- <script src="${base}/admin/pcVisa/updatePhoto.js"></script> --%>
	<script type="text/javascript">
		var staffid = '${obj.basicinfo.id}';
		var orderid = '${obj.orderid}';
		var addorder = '${obj.isaddorder}';
		
		var isautofilling = '${obj.orderinfo.isautofilling}';
		if(isautofilling == 1){
			$("#autofill").attr("disabled",true);
		}
		
		var ispreautofilling = '${obj.orderinfo.ispreautofilling}';
		if(ispreautofilling == 1){
			$("#preautofill").attr("disabled",true);
		}
		
		if('${obj.orderinfo.errorurl}'){
			$("#errorimgPhoto").show();
			//$("#errorimgPhoto").attr("src",'${obj.orderinfo.errorurl}');
		}
		if('${obj.orderinfo.reviewurl}'){
			$("#reviewimgPhoto").show();
			$("#reviewurl").attr("disabled",false);
			//$("#reviewimgPhoto").attr("src",'${obj.orderinfo.reviewurl}');
		}else{
			$("#reviewurl").attr("disabled",true);
		}
		if(!'${obj.orderinfo.pdfurl}'){
			$("#pdfurl").attr("disabled",true);
		}
		if(!'${obj.orderinfo.daturl}'){
			$("#daturl").attr("disabled",true);
		}
		
		//将汉字转为拼音
		function getPinyinStr(hanzi){
			var onehanzi = hanzi.split('');
			var pinyinchar = '';
			for(var i=0;i<onehanzi.length;i++){
				pinyinchar += PinYin.getPinYin(onehanzi[i]);
			}
			return pinyinchar.toUpperCase();
		}
		dataReload(addorder);
		
		//是否有旅行计划radio处理
		var hasplan = '${obj.travelInfo.hastripplan}';
		$("input[name='hastripplan'][value='"+hasplan+"']").attr("checked",'checked');
		if(hasplan == 1){
			$(".checkShowORHide").show();
		}else {
			$(".checkShowORHide").hide();
		}
		//日期格式处理
		var now = new Date();
		//预计出发日期
		$("#goDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			startDate: now,//日期小于今天
			autoclose: true,//选中日期后 自动关闭
			pickerPosition: "bottom-right",//显示位置
			minView: "month"//只显示年月日
		}).on('changeDate', function (ev) {
			$("#sendVisaDate").datetimepicker("setStartDate", $("#goDate").val());
		});
		
		//抵达美国日期
		$("#sendVisaDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			startDate: now,//日期小于今天
			autoclose: true,//选中日期后 自动关闭
			pickerPosition: "bottom-right",//显示位置
			minView: "month"//只显示年月日
		}).on('changeDate', function (ev) {
			console.log('change..');
			var stayday = $("#stayday").val();
			var startDate = $("#sendVisaDate").val();
			var returnDate = $("#returnDate").val();
			if(stayday != ""){
				$.ajax({
					url: '/admin/neworderUS/autoCalculateBackDate.html',
					dataType: "json",
					data: { gotripdate: startDate, stayday: stayday },
					type: 'post',
					success: function (data) {
						$("#returnDate").val(data);
					}
				});
			}
			if(returnDate != "" && stayday == ""){
				$.ajax({
					url: '/admin/neworderUS/autoCalCulateStayday.html',
					dataType: "json",
					data: { gotripdate: startDate, returnDate: returnDate },
					type: 'post',
					success: function (data) {
						$("#stayday").val(data);
					}
				});
			}
		});
		//离开美国日期
		$("#returnDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			startDate: now,
			autoclose: true,//选中日期后 自动关闭
			pickerPosition: "bottom-right",//显示位置
			minView: "month"//只显示年月日
		}).on("click", function () {
			console.log('click..');
			
			$(this).datetimepicker(
				"setStartDate", $("#sendVisaDate").val()
			);
			
		}).on('changeDate', function (ev) {
			console.log('change..');
			var stayday = $("#stayday").val();
			var startDate = $("#sendVisaDate").val();
			var returnDate = $("#returnDate").val();
			/* if(stayday != ""){
				$.ajax({
					url: '/admin/neworderUS/autoCalculateBackDate.html',
					dataType: "json",
					data: { gotripdate: startDate, stayday: stayday },
					type: 'post',
					success: function (data) {
						$("#returnDate").val(data);
					}
				});
			} */
			if(returnDate != "" && startDate != ""){
				$.ajax({
					url: '/admin/neworderUS/autoCalCulateStayday.html',
					dataType: "json",
					data: { gotripdate: startDate, returnDate: returnDate },
					type: 'post',
					success: function (data) {
						$("#stayday").val(data);
					}
				});
			}
		});
		
		$(document).on("input","#stayday",function(){
			var thisval = $(this).val();
			thisval = thisval.replace(/[^\d]/g,'');
			$(this).val(thisval);
			if(!thisval){
				$('#returnDate').val('');
			}
			
			var sendvisadate = $("#sendVisaDate").val();
			var returnDate = $("#returnDate").val();
			if(sendvisadate != "" && thisval){
				$.ajax({
					url: '/admin/neworderUS/autoCalculateBackDate.html',
					dataType: "json",
					data: { gotripdate: sendvisadate, stayday: thisval },
					type: 'post',
					success: function (data) {
						$("#returnDate").val(data);
					}
				});
			}
		});
		
		/* $(".form-format").datetimepicker({
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
		});  */
		//面签时间日期格式处理
		$(".interviewformat").datetimepicker({
			format: 'yyyy-mm-dd H:i',
			language: 'zh-CN',
	        weekStart: 1,
	        todayBtn: 1,
			autoclose: true,
			todayHighlight: true,//高亮
			startView: 4,//从年开始选择
			forceParse: 0,
	        showMeridian: false,
			pickerPosition:"bottom-right",//显示位置
			minView: 0,//显示时分
			hourMin: 8,
			hourMax: 16,
		    minTime: '08:00',              // 设置timepicker最小的限制时间   如：08:00
		    maxTime: '18:00',
			//timepicker: true,
		    allowTimes:['08:00','09:00','10:00','11:00','12:00','13:00','14:00','15:00'],
		    startTime:'08:00',
		    endTime:'18:00',
		    onChangeDateTime:logic,
		    onShow:logic,
		    minuteStep:15//间隔为十五分钟
			//minView: "month"//只显示年月日
		}); 
		var logic = function( currentDateTime ){
		        this.setOptions({
		            minTime:'11:00'
		        });
		};
		
		//抵达美国日期和预计出发日期一致
		$("#goDate").change(function(){
			var godate = $("#goDate").val(); //出发日期
			var sendvisadate = $("#sendVisaDate").val(); //抵达美国日期
			if(sendvisadate == ""){
				$("#sendVisaDate").val(godate);
			}
		});
		
		//计划去美国的州改变，城市自动清空
		$("#planstate").change(function(){
			$("#plancity").empty();
			$("#hotelname").empty();
			$("#planaddress").val("");
		});


		//日期转换 加上指定天数
		function getNewDay(dateTemp, days) {
			var dateTemp = dateTemp.split("-");
			var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-'
					+ dateTemp[0]); //转换为MM-DD-YYYY格式
			console.log(nDate);
			var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
			var rDate = new Date(millSeconds);
			var year = rDate.getFullYear();
			var month = rDate.getMonth() + 1;
			if (month < 10)
				month = "0" + month;
			var date = rDate.getDate();
			if (date < 10)
				date = "0" + date;
			return (year + "-" + month + "-" + date);
		}
		$(function() {
			var flag = $("#flag").val();
			var staffid = $("#staffid").val();
			if(0==flag){
				//有课第一次登陆弹出拍摄资料
				$("#photoInfo").trigger("click");
			}
			$(".tripPlan").change(function() {

				var tripPlan = $("input[name='hastripplan']:checked").val();

				if (tripPlan == 1) {

					$(".checkShowORHide").show();
				} else {
					$(".checkShowORHide").hide();
					$("#goDepartureCity").empty();
					$("#goArrivedCity").empty();
					$("#goFlightNum").empty();
					$("#returnDepartureCity").empty();
					$("#returnArrivedCity").empty();
					$("#returnFlightNum").empty();
				}
			});
			
		});
		/* 获取form下所有值 */
		function getFormJson(form) {
			var o = {};
			var a = $(form).serializeArray();
			$.each(a, function() {
				if (o[this.name] != undefined) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		}

		//取消返回上个页面
		function returnBack(){
			window.history.go(-1);
		}
		
		//大客户公司名称select2
		$("#bigcustomername").select2({
			ajax : {
				url : "/admin/orderUS/getBigcustomerSelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					/*var cArrivalcity = $('#cArrivalcity').val();
					if(cArrivalcity){
						cArrivalcity = cArrivalcity.join(',');
					}*/
					return {
						//exname : cArrivalcity,
						bigcustomername : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.id; // replace pk with your identifier
						obj.text = obj.name; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		});

		//加载城市的select2
		$('#goDepartureCity,#returnArrivedCity').select2({
			ajax : {
				url : "/admin/city/getCustomerCitySelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					/*var cArrivalcity = $('#cArrivalcity').val();
					if(cArrivalcity){
						cArrivalcity = cArrivalcity.join(',');
					}*/
					return {
						//exname : cArrivalcity,
						cityname : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.id; // replace pk with your identifier
						obj.text = obj.city; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		//设置必须存在的选项 才能选中
		});
		//加载美国城市的select2
		$('#goArrivedCity,#returnDepartureCity').select2({
			ajax : {
				url : "/admin/neworderUS/selectUScity.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					/*var cArrivalcity = $('#cArrivalcity').val();
					if(cArrivalcity){
						cArrivalcity = cArrivalcity.join(',');
					}*/
					return {
						//exname : cArrivalcity,
						searchstr : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.id; // replace pk with your identifier
						obj.text = obj.cityname; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		//设置必须存在的选项 才能选中
		});
		$('#plancity').select2({
			ajax : {
				url : "/admin/neworderUS/selectUSstateandcity.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					var province = $('#planstate').val();
					/* alert(province);
				    if(province){
				    	province = province.join(',');
					} */
					return {
						province : province,
						searchstr : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.cityname; // replace pk with your identifier
						obj.text = obj.cityname; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		//设置必须存在的选项 才能选中
		});
		//酒店名称
		$('#hotelname').select2({
			ajax : {
				url : "/admin/neworderUS/selectUSHotel.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					var cArrivalcity = $('#plancity').val();
					if(cArrivalcity){
						cArrivalcity = cArrivalcity.join(',');
					}
					return {
						plancity : cArrivalcity,
						searchstr : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.name; // replace pk with your identifier
						obj.text = obj.name; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		//设置必须存在的选项 才能选中
		});
		
		$("#plancity").on('select2:unselect', function (evt) {
			$("#hotelname").empty();
			$("#planaddress").val("");
		}); 
		$("#hotelname").on('select2:unselect', function (evt) {
			$("#planaddress").val("");
		}); 
		
		$("#hotelname").on('select2:select', function (evt) {
			var hotelname = $(this).select2("val");
			if(hotelname){
				hotelname = hotelname.join(',');
			}
			$.ajax({
				url : '/admin/neworderUS/getHoteladdress',
				type : 'POST',
				data : {
					'hotelname' : hotelname
				},
				dataType:'json',
				success : function(data) {
					$("#planaddress").val(data);
				},
				error : function() {
				}
			});

		});
		
		$('#plancity').select2({
			ajax : {
				url : "/admin/neworderUS/selectUSstateandcity.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					var province = $('#planstate').val();
					/* alert(province);
				    if(province){
				    	province = province.join(',');
					} */
					return {
						province : province,
						searchstr : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function(obj) {
						obj.id = obj.cityname; // replace pk with your identifier
						obj.text = obj.cityname; // replace pk with your identifier
						/*obj.text = obj.dictCode;*/
						return obj;
					});
					return {
						results : selectdata
					};
				},
				cache : false
			},
			//templateSelection: formatRepoSelection,
			escapeMarkup : function(markup) {
				return markup;
			}, // let our custom formatter work
			minimumInputLength : 1,
			maximumInputLength : 20,
			language : "zh-CN", //设置 提示语言
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false
		//设置必须存在的选项 才能选中
		});
		
		//出发航班select2
		$('#goFlightNum').select2(
				{
					ajax : {
						url : "/admin/tripairline/getAirLineByInterfateUS.html",
						dataType : 'json',
						delay : 250,
						type : 'post',
						data : function(params) {
							/*var cArrivalcity = $('#cArrivalcity').val();
							if(cArrivalcity){
								cArrivalcity = cArrivalcity.join(',');
							}*/
							//去程出发城市
							var goDepartureCity = $('#goDepartureCity').val();
							if (goDepartureCity) {
								goDepartureCity = goDepartureCity.join(',');
							} else {
								goDepartureCity = '';
							}
							//去程抵达城市
							var goArrivedCity = $('#goArrivedCity').val();
							if (goArrivedCity) {
								goArrivedCity = goArrivedCity.join(',');
							} else {
								goArrivedCity = '';
							}
							var date = $('#goDate').val();
							return {
								flag : 1,
								date : date,
								//exname : cArrivalcity,
								gocity : goDepartureCity,
								arrivecity : goArrivedCity,
								flight : params.term, // search term
								page : params.page
							};
						},
						processResults : function(data, params) {
							params.page = params.page || 1;
							var selectdata = $.map(data, function (obj) {
								//obj.id = obj.id; // replace pk with your identifier
								if(obj.zhuanflightnum != undefined){
									obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
									obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
								}else{
									//obj.id = obj.flightnum; // replace pk with your identifier
									obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
									obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
								}
								/*obj.text = obj.dictCode;*/
								return obj;
							});
							return {
								results : selectdata
							};
						},
						cache : false
					},
					//templateSelection: formatRepoSelection,
					escapeMarkup : function(markup) {
						return markup;
					}, // let our custom formatter work
					minimumInputLength : 1,
					maximumInputLength : 20,
					language : "zh-CN", //设置 提示语言
					maximumSelectionLength : 1, //设置最多可以选择多少项
					tags : false
				//设置必须存在的选项 才能选中
				});

		//返程航班select2
		$('#returnFlightNum')
				.select2(
						{
							ajax : {
								url : "/admin/tripairline/getAirLineByInterfateUS.html",
								dataType : 'json',
								delay : 250,
								type : 'post',
								data : function(params) {
									/*var cArrivalcity = $('#cArrivalcity').val();
									if(cArrivalcity){
										cArrivalcity = cArrivalcity.join(',');
									}*/
									//去程出发城市
									var returnDepartureCity = $(
											'#returnDepartureCity').val();
									if (returnDepartureCity) {
										returnDepartureCity = returnDepartureCity
												.join(',');
									} else {
										returnDepartureCity += '';
									}
									//去程抵达城市
									var returnArrivedCity = $(
											'#returnArrivedCity').val();
									if (returnArrivedCity) {
										returnArrivedCity = returnArrivedCity
												.join(',');
									} else {
										returnArrivedCity += '';
									}
									var date = $('#returnDate').val();
									return {
										//exname : cArrivalcity,
										flag : 2,
										date : date,
										gocity : returnDepartureCity,
										arrivecity : returnArrivedCity,
										flight : params.term, // search term
										page : params.page
									};
								},
								processResults : function(data, params) {
									params.page = params.page || 1;
									var selectdata = $.map(data, function (obj) {
										//obj.id = obj.id; // replace pk with your identifier
										//obj.id = obj.flightnum; // replace pk with your identifier
										//obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
										if(obj.zhuanflightnum != undefined){
											obj.id = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
											obj.text = obj.goflightname + '-' + obj.arrflightname + '-' +  obj.zhuanflightname + ' '+ obj.flightnum + '//' +obj.zhuanflightnum +' '+obj.takeofftime + '/' + obj.landingofftime + '//' + obj.zhuantakeofftime + '/' + obj.zhuanlandingofftime;
										}else{
											//obj.id = obj.flightnum; // replace pk with your identifier
											obj.id = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
											obj.text = obj.goflightname + '-' + obj.arrflightname + ' ' +  obj.flightnum + ' '+ obj.takeofftime + '/' +obj.landingofftime;
										}
										/*obj.text = obj.dictCode;*/
										return obj;
									});
									return {
										results : selectdata
									};
								},
								cache : false
							},
							//templateSelection: formatRepoSelection,
							escapeMarkup : function(markup) {
								return markup;
							}, // let our custom formatter work
							minimumInputLength : 1,
							maximumInputLength : 20,
							language : "zh-CN", //设置 提示语言
							maximumSelectionLength : 1, //设置最多可以选择多少项
							tags : false
						//设置必须存在的选项 才能选中
						});

		//去程出发城市
		$("#goDepartureCity").on(
				"select2:select",
				function(e) {
					var thisval = $(this).val();
					if (thisval) {
						thisval = thisval.join(',');
					} else {
						thisval += '';
					}
					//设置回程抵达城市
					var thistext = $(this).text();
					$("#returnArrivedCity").html(
							'<option selected="selected" value="'+thisval+'">'
									+ thistext + '</option>');
					var goArrivedCity = $('#goArrivedCity').val();
					if (goArrivedCity) {
						goArrivedCity = goArrivedCity.join(',');
					} else {
						goArrivedCity += '';
					}
					var goDate = $('#goDate').val();
					var returnDate = $('#returnDate').val();
					//查询航班接口到缓存
					//initFlightByInterface(goDate, thisval, goArrivedCity);
					//initFlightByInterface(returnDate, goArrivedCity, thisval);
				});
		$("#goDepartureCity").on("select2:unselect", function(e) {
			$(this).text('');
			$("#returnArrivedCity").html('');
			$('#goFlightNum').empty();
			$('#returnFlightNum').empty();
		});
		
		//去程抵达城市
		$("#goArrivedCity")
				.on(
						"select2:select",
						function(e) {
							var thisval = $(this).val();
							if (thisval) {
								thisval = thisval.join(',');
							} else {
								thisval += '';
							}
							//设置回程出发城市
							var thistext = $(this).text();
							$("#returnDepartureCity").html(
									'<option selected="selected" value="'+thisval+'">'
											+ thistext + '</option>');
							var goDepartureCity = $('#goDepartureCity').val();
							if (goDepartureCity) {
								goDepartureCity = goDepartureCity.join(',');
							} else {
								goDepartureCity += '';
							}
							var goDate = $('#goDate').val();
							var returnDate = $('#returnDate').val();
							//查询航班接口到缓存
							//initFlightByInterface(goDate, goDepartureCity, thisval);
							//initFlightByInterface(returnDate, thisval, goDepartureCity);
						});
		$("#goArrivedCity").on("select2:unselect", function(e) {
			$(this).text('');
			$("#returnDepartureCity").html('');
			$('#goFlightNum').empty();
			$('#returnFlightNum').empty();
		});
		//返程出发城市
		$("#returnDepartureCity").on("select2:select", function(e) {
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			} else {
				thisval += '';
			}
			var returnArrivedCity = $('#returnArrivedCity').val();
			if (returnArrivedCity) {
				returnArrivedCity = returnArrivedCity.join(',');
			} else {
				returnArrivedCity += '';
			}
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			//initFlightByInterface(returnDate, thisval, returnArrivedCity);
		});
		$("#returnDepartureCity").on("select2:unselect", function(e) {
			$(this).text('');
			$("#returnDepartureCity").html('');
			$('#returnFlightNum').empty();
		});
		//返程抵达城市
		$("#returnArrivedCity").on("select2:select", function(e) {
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			} else {
				thisval += '';
			}
			var returnDepartureCity = $('#returnDepartureCity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			} else {
				returnDepartureCity += '';
			}
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			//initFlightByInterface(returnDate, returnDepartureCity, thisval);
		});
		$("#returnArrivedCity").on("select2:unselect", function(e) {
			$(this).text('');
			$("#returnArrivedCity").html('');
			$('#returnFlightNum').empty();
		});

		//加载航班号到缓存并同步到数据库
		function initFlightByInterface(departuredate, departurecity,
				arrivedcity) {
			$.ajax({
				url : '/admin/tripairline/getAirLineByInterfateUS.html',
				dataType : "json",
				data : {
					date : departuredate,
					gocity : departurecity,
					arrivecity : arrivedcity
				},
				type : 'post',
				success : function(data) {
				}
			});
		}

		//日志
		function toLog(){
			var orderid = '${obj.orderid}';
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['700px', '80%'],
				content:'/admin/orderUS/log.html?orderid='+orderid
			});
		}
		
		//通知
		function sendEmailUS(){
			var staffid = '${obj.basicinfo.id}';
			var orderid = '${obj.orderid}';
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '400px'],
				content: '/admin/orderUS/notice.html?orderid='+orderid+'&staffid='+staffid
			});
		}
		//通知
		/* function sendEmailUS(){
			var staffid = '${obj.basicinfo.id}';
			var orderid = '${obj.orderinfo.id}';
			layer.load(1);
			$.ajax({
				url : '/admin/orderUS/sendShareMsg.html',
				data : {
					staffId : staffid,
					orderid : orderid
				},
				dataType : "json",
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					console.log(data);
					if(data){
						layer.msg("发送成功");
					}
				}
			});
		} */
		
		//刷新订单详情页面数据
		function dataReload(status){
			var orderid = '${obj.orderid}';
			//var addorder = '${obj.isaddorder}';
			$.ajax({
				url : '/admin/orderUS/getOrderRefresh.html',
				data : {
					orderid : orderid,
					addOrder : status
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					console.log(data);
					//刷新订单状态
					if(data.isaddorder == 1){
						
					}else{
						$("#orderstatus_US").html(data.orderstatus);
						//刷新跟进信息
						var followinfos = data.followinfo;
						if(followinfos.length > 0){
							var Str = "";
							for(var i = 0;i < followinfos.length;i++){
								if(followinfos[i].status == 1){
									Str += '<li> <div class="dateNameBtn">'+
									'<span class="dateInfo">'+followinfos[i].createtime+'</span>'+
									'<span class="nameInfo">'+followinfos[i].name+'</span>&nbsp;'+
									'<span class="gray"><span>'+followinfos[i].solvetime+'</span>&nbsp;&nbsp;由&nbsp;&nbsp;<span>'+followinfos[i].solveid+'</span>&nbsp;&nbsp;解决&nbsp;&nbsp;</span></span></div>'+
									'<div class="errorInfo">'+
									'<div>'+followinfos[i].content+'</div></div></li>';
								}else{
									Str += '<li> <div class="dateNameBtn">'+
									'<span class="dateInfo">'+followinfos[i].createtime+'</span>'+
									'<span class="nameInfo">'+followinfos[i].name+'</span>'+
									'<a class="solve" onclick="solveClick('+followinfos[i].id+')">解决</a></div>'+
									'<div class="errorInfo">'+
									'<div>'+followinfos[i].content+'</div></div></li>';
								}
	/* 								Str += '<li> <div class="dateNameBtn">'+
									'<span class="dateInfo">'+followinfos[i].createtime+'</span>'+
									'<span class="nameInfo">'+followinfos[i].name+'</span>'+
									'<c:choose><c:when test="{'+followinfos[i].status+' == 1}"><span>'+followinfos[i].solvetime+'</span>由<span>'+followinfos[i].solveid+'</span>解决</span></c:when><c:otherwise><a class="solve" onclick="solveClick('+followinfos[i].id+')">解决</a></c:otherwise></c:choose></div>'+
									'<div class="errorInfo">'+
									'<span>'+followinfos[i].content+'</span></div></li>'; */
							}
							$("#forFollow").html(Str);
						}
						//刷新申请人信息
						if(data.twoinchphoto != null){
							$('#imgInch').attr('src', data.twoinchphoto.url);
						}else{
							$('#imgInch').attr('src', '');
						}
						$('#aacode').val(data.summaryInfo.aacode);
						//$('#allname').val(data.passport.firstname+data.passport.lastname+'/'+data.passport.firstnameen+data.passport.lastnameen);
						$('#sex').val(data.passport.sex);
						$('#birthday').val(data.birthday);
						$('#realinfo').val(data.realinfo);
						$('#cardnum').val(data.basicinfo.cardnum);
						$('#passport').val(data.passport.passport);
						$('#interviewdate2').val(data.Interviewdate);
						
						if(data.orderinfo.isautofilling == 1){
							$("#autofill").attr("disabled",true);
						}
						//姓名处理
						var firstname = data.passport.firstname;
						var lastname = data.passport.lastname;
						var firstnameen = data.passport.firstnameen;
						var lastnameen = data.passport.lastnameen;
						if((firstname != "" || lastname != "") && (firstnameen == undefined || lastnameen == undefined)){
							$("#allname").val(firstname+lastname+"/"+getPinyinStr(firstname)+getPinyinStr(lastname));
						}
						if((firstname != undefined && lastname != undefined) && (firstnameen != undefined && lastnameen != undefined)){
							$('#allname').val(data.passport.firstname+data.passport.lastname+'/'+data.passport.firstnameen+data.passport.lastnameen);
						}
					}
				}
			});
		}
		
		//添加跟进
		function addFollow(){
			var orderid = '${obj.orderid}';
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '350px'],
				content: '/admin/orderUS/addFollow.html?orderid='+orderid+'&addorder='+addorder
			});
		}
		
		//跟进中的解决按钮
		function solveClick(id){
			$.ajax({
				url : '/admin/orderUS/solveFollow.html',
				data : {
					id : id
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					dataReload(addorder);
				}
			});
		}
		
		//自动填表
		/* function autofill(){
			var orderid = '${obj.orderid}';
			$.ajax({
				url : '/admin/orderUS/validateInfoIsFull.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					if(data.errMsg){
						layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['400px', '300px'],
		        		    content: '${base}/admin/orderUS/autofillError.html?errData='+data.errMsg
		        		  });
					}else{
						layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['100%', '100%'],
		        		    content: '${base}/admin/orderUS/testUS.html'
		        		  });
					}
				}
			});
		} */
		var count = 0;
		
		/* function autofill(){
			var interval = setInterval("autofill1()","1");
			
		} */
		
		function autofill1(){
			autofill1(4749, 4776);
			autofill1(4750, 4777);
			autofill1(4751, 4778); 
			autofill1(4752, 4779);
			autofill1(4753, 4780);
			autofill1(4755, 4782);
			autofill1(4756, 4783);
			
		}
		
		//正式填写
		function autofill(orderid){
			$("#orderstatus_US").html("自动填表中");
 			$("#autofill").attr("disabled",true);
			
			var orderid = '${obj.orderid}';
			var staffid = '${obj.basicinfo.id}';
			$.ajax({
				url : '/admin/orderUS/autofill.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					
					var getstatus = setInterval(function(){
			 			console.log("轮询开始");
			 			console.log(count);
			 			$.ajax({
							url : '/admin/orderUS/isAutofilled.html',
							data : {
								orderid : orderid,
								staffid : staffid
							},
							dataType : "json",
							type : 'POST',
							success : function(data) {
								if(data.orderstatus == 9){
									$("#autofill").attr("disabled",false);
									$("#orderstatus_US").html("自动填表成功");
									clearInterval(getstatus);
									console.log("自动填表成功，轮询停止了~~~");
									layer.msg("自动填表成功");
									if(data.pdf_url){
										$("#pdfurl").attr("disabled",false);
										//$("#pdfurl").attr('onclick', '').unbind('click').click( function () { toUpperPhoto(data.pdfurl); });
									}else{
										$("#pdfurl").attr("disabled",true);
									}
									if(data.dat_url){
										$("#daturl").attr("disabled",false);
										//$("#daturl").attr('onclick', '').unbind('click').click( function () { toUpperPhoto(data.daturl); });
									}else{
										$("#daturl").attr("disabled",true);
									}
								}
								if(data.orderstatus == 10){
									$("#autofill").attr("disabled",false);
									$("#orderstatus_US").html("自动填表失败");
									clearInterval(getstatus);
									console.log("正式填写失败，轮询停止了~~~");
									layer.msg("自动填表失败");
									if(data.pdf_url){
										$("#pdfurl").attr("disabled",false);
										//$("#pdfurl").attr('onclick', '').unbind('click').click( function () { toUpperPhoto(data.pdfurl); });
									}else{
										$("#pdfurl").attr("disabled",true);
									}
									if(data.dat_url){
										$("#daturl").attr("disabled",false);
										//$("#daturl").attr('onclick', '').unbind('click').click( function () { toUpperPhoto(data.daturl); });
									}else{
										$("#daturl").attr("disabled",true);
									}
								}
								count++;
							}
			 			});
			 		},"10000");
					
				}
			});
		}
		
		//预检查
 		function preautofill(orderid, staffid){
 			$("#orderstatus_US").html("预检查中");
 			$("#preautofill").attr("disabled",true);
 			$("#errorimgPhoto").hide();
 			$("#autofill").attr("disabled",true);
			var orderid = '${obj.orderid}';
			var staffid = '${obj.basicinfo.id}';
			console.log(count);
			$.ajax({
				url : '/admin/orderUS/validateInfoIsFull.html',
				data : {
					orderid : orderid,
					staffid : staffid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					if(data.errMsg){
						console.log(data.errMsg);
						$("#preautofill").attr("disabled",false);
						$("#orderstatus_US").html("预检查失败");
						$("#downloadButton").attr("disabled",false);
						layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['450px', '300px'],
		        		    content: '${base}/admin/orderUS/autofillError.html?errData='+data.errMsg
		        		    //content: '${base}/admin/orderUS/autofillError.html?errData='+data.errMsg
		        		  });
					}else{
						
						$.ajax({
							url : '/admin/orderUS/preautofill.html',
							data : {
								orderid : orderid
							},
							dataType : "json",
							type : 'POST',
							success : function(data) {
								//alert("申请人识别码为："+data);
								count++;
								console.log(data);
								//每隔10秒查一次自动填表是否完成
						 		var getstatus = setInterval(function(){
						 			console.log("轮询开始");
						 			console.log(count);
						 			$.ajax({
										url : '/admin/orderUS/isAutofilled.html',
										data : {
											orderid : orderid,
											staffid : staffid
										},
										dataType : "json",
										type : 'POST',
										success : function(data) {
											if(data.orderstatus == 6){
												$("#preautofill").attr("disabled",false);
												$("#orderstatus_US").html("预检查成功");
												clearInterval(getstatus);
												console.log("预检查成功，轮询停止了~~~");
												layer.msg("预检查成功");
												console.log(data.review_url);
												if(data.review_url){
													console.log("预检查成功，进入图片展示环节");
													$("#reviewimgPhoto").show();
													$("#errorimgPhoto").hide();
													$("#autofill").attr("disabled",false);
													$("#reviewimgPhoto").attr('onclick', '').unbind('click').click( function () { toReviewphoto(data.review_url); });
													//$("#reviewurl").attr('onclick', '').unbind('click').click( function () { toUpperPhoto(data.reviewurl); });
													$("#reviewurl").attr("disabled",false);
												}else{
													$("#reviewurl").attr("disabled",true);
												}
												if(data.AAcode){
													$("#aacode").val(data.AAcode);
												}
											}
											if(data.orderstatus == 7){
												$("#preautofill").attr("disabled",false);
												$("#orderstatus_US").html("预检查失败");
												clearInterval(getstatus);
												console.log("预检查失败，轮询停止了~~~");
												layer.msg("预检查失败");
												console.log(data.error_url);
												$("#reviewimgPhoto").hide();
												if(data.error_url){
													console.log("预检查失败，进入图片展示环节");
													$("#errorimgPhoto").show();
													$("#errorimgPhoto").attr('onclick', '').unbind('click').click( function () { toReviewphoto(data.error_url); });
													$("#reviewurl").attr("disabled",true);
												}
											}
											count++;
										}
						 			});
						 		},"10000");
								
						 		
								
								/* if(data.errorMsg == ""){
									console.log("走了"+count+"次终于成功了☺");
									console.log("applyidcode:"+data.applyidcode);
									console.log("AAcode:"+data.AAcode);
									console.log("reviewurl:"+data.reviewurl);
									console.log("pdfurl:"+data.pdfurl);
									console.log("avatorurl:"+data.avatorurl);
									console.log("daturl:"+data.daturl);
									//$("#autofill").attr("disabled",false);
								}else{
									console.log(data.errorMsg);
									console.log(data.error_url);
									console.log(data.code);
									//$("#autofill").attr("disabled",false);
								} */
							}
						});
					}
				}
			});
		}
 		
 		
 		/* function isAutofilled(){
 			console.log("轮询开始");
 			console.log(count);
 			var orderid = '${obj.orderid}';
 			$.ajax({
				url : '/admin/orderUS/isAutofilled.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					if(data == 6){
						$("#autofill").attr("disabled",false);
						$("#downloadButton").attr("disabled",false);
						$("#orderstatus_US").html("自动填表成功");
						//clearInterval(getstatus);
					}
					if(data == 7){
						$("#autofill").attr("disabled",false);
						$("#downloadButton").attr("disabled",true);
						$("#orderstatus_US").html("自动填表失败");
						//clearInterval(getstatus);
					}
					count++;
				}
 			});
 		} */
 		
		
		//下载
		function download(type){
			var orderid = '${obj.orderid}';
			
			layer.load(1);
    		$.fileDownload("${base}/admin/orderUS/downloadFile.html?orderid=" + orderid+"&type="+type, {
		         successCallback: function (url) {
		        	 layer.closeAll('loading');
		        	 layer.msg("下载成功");
		         },
		         failCallback: function (html, url) {
		        	layer.closeAll('loading');
		        	layer.msg("下载失败");
		         }
		     });
			
			
			/* $.ajax({
				url : '/admin/orderUS/downloadFile.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'GET',
				success : function(data) {
					
				}
			}); */
		}
		
		//通过
		function pass(){
			var orderid = '${obj.orderid}';
			
			layer.confirm("您确认要<font color='red'>通过</font>吗？", {
				title:"通过",
				btn: ["是","否"], //按钮
				shade: false //不显示遮罩
			}, function(){
				$.ajax({
					url : '/admin/orderUS/passUS.html',
					data : {
						orderid : orderid
					},
					dataType : "json",
					type : 'POST',
					success : function(data) {
						layer.msg("操作成功", {
							time: 500,
							end: function () {
								dataReload(addorder);
							}
						});
					}
				}); 
			});
			
			
			
			/* $.ajax({
				url : '/admin/orderUS/passUS.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					layer.msg("操作成功", {
						time: 500,
						end: function () {
							dataReload(addorder);
						}
					});
				}
			}); */
		}
		
		//拒绝
		function refuse(){
			var orderid = '${obj.orderid}';
			layer.confirm("您确认要<font color='red'>拒签</font>吗？", {
				title:"拒签",
				btn: ["是","否"], //按钮
				shade: false //不显示遮罩
			}, function(){
				$.ajax({
					url : '/admin/orderUS/refuseUS.html',
					data : {
						orderid : orderid
					},
					dataType : "json",
					type : 'POST',
					success : function(data) {
						layer.msg("操作成功", {
							time: 500,
							end: function () {
								dataReload(addorder);
							}
						});
					}
				}); 
			});
			
			
			
			/* $.ajax({
				url : '/admin/orderUS/refuseUS.html',
				data : {
					orderid : orderid
				},
				dataType : "json",
				type : 'POST',
				success : function(data) {
					layer.msg("操作成功", {
						time: 500,
						end: function () {
							dataReload(addorder);
						}
					});
				}
			}); */
		}
		
		/* //异步保存数据 
		function save() {
			var goDepartureCity = $('#goDepartureCity').val();
			var goArrivedCity = $('#goArrivedCity').val();
			var returnDepartureCity = $('#returnDepartureCity').val();
			var returnArrivedCity = $('#returnArrivedCity').val();
			var goflightnum = $('#goFlightNum').val();
			var returnflightnum = $('#returnFlightNum').val();
			$('#gocity').val(goDepartureCity);
			$('#goarrivecity').val(goArrivedCity);
			$('#goflightnum').val(goflightnum);
			$('#returncity').val(returnDepartureCity);
			$('#returnarrivecity').val(returnArrivedCity);
			$('#returnflightnum').val(returnflightnum);
			var data = getFormJson("#orderUpdateForm");
			$.ajax({
				url : "${base}/admin/pcVisa/visaSave",
				dataType : "json",
				data : $("#orderUpdateForm").serialize(),
				type : 'POST',
				async:false,
				success : function(data) {
					//window.location.href = '/admin/pcVisa/VisaList';
					window.history.go(-1);
				}
			});
		}; */
		
		//保存并返回
		function save(){
			layer.load(1);
			$.ajax({
				url : "${base}/admin/orderUS/orderSave",
				dataType : "json",
				data : $("#orderUpdateForm").serialize(),
				type : 'POST',
				success : function(data) {
					layer.closeAll();
					layer.msg("保存成功", {
						time: 1000,
						end: function () {
							//self.window.close();
							//location.reload();
						}
					});
					// window.location.href = '/admin/pcVisa/visaDetail.html'; 
				}
			});
		}
		
		//取消
		function closeWindow(){
			if(addorder == 1){
				window.location.href = '/admin/orderUS/listUS.html';
			}else{
				self.window.close();
			}
			//parent.window.reload();
		}
		
		//拍摄资料
		function updatePhoto(staffid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/neworderUS/updatePhoto.html?staffid='+staffid
			});
		}
		
		//护照信息
		function passport(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/neworderUS/updatePassportInfo.html?staffid='+id
			});
		}
		
		//基本信息
		function baseInfo(id){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/neworderUS/updateBaseInfo.html?staffid='+id
			});
		}
		
		//签证信息
		function visa(staffid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/bigCustomer/updateVisaInfo.html?staffId='+staffid
			});
		}

		//家庭信息
		function FamilyInfo(staffid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/neworderUS/updateFamilyInfo.html?staffid='+staffid
			});
		}

		//工作教育信息
		function WorkInfo(staffid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/neworderUS/updateWorkInfo.html?staffid='+staffid
			});
		}

		//旅行信息
		function TravelInfo(staffid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/neworderUS/updateTravelInfo.html?staffid='+staffid
			});
		}
		
		//错误图片信息
		function toErrorphoto(errorurl){
			//var errorurl = '${obj.orderinfo.errorurl }';
			console.log("错误图片:"+errorurl);
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/orderUS/toErrorphoto.html?errorurl='+errorurl+'&type=1'
			});
		}
		//预览图片信息
		function toReviewphoto(errorurl){
			//var errorurl = '${obj.orderinfo.reviewurl }';
			console.log("预览图片:"+errorurl);
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/orderUS/toErrorphoto.html?errorurl='+errorurl+'&type=2'
			});
		}
		
		function toUpperPhoto(url){
			if(url != ""){
				window.open(url);
			}
		}
		
		//翻译
		function translateZhToEn(from, to, param){
			var toval = "";
			if(param != ""){
				toval = param;
			}else{
				toval = $(from).val();
			}
			$.ajax({
				//async : false,
				url : '/admin/translate/translate',
				data : {
					api : 'google',
					strType : to,
					en : 'en',
					q : toval
				},
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					$("#" + to).val(data).change();
				}
			});
			/*$.getJSON("/admin/translate/google", {q: $(from).val()}, function (result) {
		        $("#" + to).val(result.data);
		    });*/
		}
		
		function successCallback(status){
			dataReload(status);
		}
	</script>
</body>
</html>
