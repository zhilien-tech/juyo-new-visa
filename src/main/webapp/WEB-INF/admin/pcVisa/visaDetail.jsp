<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/pcVisa" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>签证详情</title>
<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/font-awesome.min.css">
<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/ionicons.min.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/references/public/css/style.css">
<!-- 签证详情样式 -->
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/visaDetail.css">
<!-- 加载中。。。样式 -->
<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper">
			<!-- 头部 -->
			<div class="qz-head">
				<span class="orderNum">订单号：
					<span>${obj.orderInfo.ordernumber}</span>
				</span>
				<!-- <span class="">受付番号：<p>{{orderinfo.acceptdesign}}</p></span> -->
				<span class="state">状态： 
					<c:if test="${obj.orderInfo.status == '1'}">
						<p>下单</p>
					</c:if> 
					<c:if test="${obj.orderInfo.status == '0'}">
						<p>0</p>
					</c:if>
				</span> 
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" /> 
					<input type="button" onclick="save()" value="保存并返回" class="btn btn-primary btn-sm pull-right btn-Big" /> 
					<input type="button" value="下载" class="btn btn-primary btn-sm pull-right" />
			</div>
			<!-- 头部END -->
			<!-- form -->
			<form id="orderUpdateForm">
				<!-- 主体 -->
				<section class="content">
					<!-- 订单信息 -->
					<div id="save" class="info">
						<p class="info-head">订单信息</p>
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
												<option >${obj.travelInfo.travelpurpose}</option>
											</c:if>
											<c:if test="${empty obj.travelInfo.travelpurpose}">
												<option value="">--请选择--</option>
											</c:if>
											<option >外国政府官员（A）</option>
											<option >商务旅游游客(B)</option>
											<option >过境的外国公民(C)</option>
											<option >CNMI工作者或投资者(CW/E2C)</option>
											<option >机船组人员(D)</option>
											<option >贸易协议国贸易人员或投资者(E)</option>
											<option >学术或语言学生(F)</option>
											<option >国际组织代表/雇员(G)</option>
											<option >临时工作(H)</option>
											<option >外国媒体代表</option>
											<option >交流访问者</option>
											<option >美国公民的未婚夫（妻）或配偶（K）</option>
											<option >公司内部调派人员(L)</option>
											<option >职业/非学术学校的学生(M)</option>
											<option >其他(N)</option>
											<option >北约工作人员(NATO)</option>
											<option >具有特殊才能的人员(O)</option>
											<option >国际承认的外国人士(P)</option>
											<option >文化交流访问者(Q)</option>
											<option >宗教人士(R)</option>
											<option >提供信息者或证人(S)</option>
											<option >人口贩运的受害者(T)</option>
											<option >北美自由贸易协议专业人员(TD/TN)</option>
											<option >犯罪活动的受害者(U)</option>
											<option >假释收益者(PARCIS)</option>
										</select>
									</div>
								</div>
								<!-- 出行目的END -->
								<!-- 是否有具体的旅行计划 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>是否有具体的旅行计划</label>
										<div>
											<input type="radio" name="tripPlan" class="tripPlan" value="1">是 
											<input type="radio" name="tripPlan" class="tripPlan" value="2" checked>否
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
										<label><span>*</span>预计出发日期：</label> 
										<input id="goDate" name="godate" type="text" class="form-format input-sm datetimepickercss" value="<fmt:formatDate value="${obj.travelInfo.godate }" pattern="yyyy-MM-dd" />" />
									</div>
								</div>
								<!-- 预计出发时间END -->
								<!-- 抵达美国日期 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达美国日期：</label> <input name="arrivedate"
											type="text" class="form-format input-sm datetimepickercss"
											value="<fmt:formatDate value="${obj.travelInfo.arrivedate }" pattern="yyyy-MM-dd" />" />
									</div>
								</div>
								<!-- 抵达美国日期END -->
								<!-- 停留天数 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label> 
										<input id="stayday" name="staydays" class="input-sm" value="${obj.travelInfo.staydays}" type="text" />
									</div>
								</div>
								<!-- 停留天数END -->
								<!-- 离开美国日期 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>离开美国日期：</label> <input id="returnDate" name="leavedate"
											type="text" class="form-format input-sm datetimepickercss"
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
										<input id="gocity" name="godeparturecity" type="hidden">
										<label><span>*</span>出发城市：</label> 
										<select id="goDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple">
											<c:forEach items="${obj.citylist }" var="city">
												<c:choose>
													<c:when test="${city.id eq obj.travelInfo.goDepartureCity }">
														<option value="${city.id }" selected="selected">${city.city }</option>
													</c:when>
													<c:otherwise>
														<option value="${city.id }">${city.city }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:if test="${!empty obj.orderInfo.goDepartureCity}">
												<option value="${obj.travelInfo.godeparturecity}" selected="selected">${obj.orderInfo.goDepartureCity}</option>
											</c:if> 
										</select>
									</div>
								</div>
								<!-- 出发城市END -->
								<!-- 抵达城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label> 
										<select id="goArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple">
											<c:if test="${not empty obj.orderInfo.goArrivedCity }">
												<option value="${obj.travelInfo.goArrivedCity}" selected="selected">${obj.orderInfo.goArrivedCity}</option>
											</c:if>
											<input id="goarrivecity" name="goArrivedCity" type="hidden" />
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
										<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple">
											<c:if test="${!empty obj.goFlightInfo }">
												<option value="${obj.goFlightInfo.flightnum }" selected="selected">${obj.goFlightInfo.takeOffName }-${obj.goFlightInfo.landingName } ${obj.goFlightInfo.flightnum } ${obj.goFlightInfo.takeOffTime }/${obj.goFlightInfo.landingTime }</option>
											</c:if>
											<input id="goflightnum" name="goFlightNum" type="hidden" />
											<option id="goFlightNum" name="goFlightNum"></option> 
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if test="${obj.tripinfo.goFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName } ${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach>
										</select>
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
										<label><span>*</span>出发城市：</label> 
										<select id="returnDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple">
											<c:if test="${!empty obj.orderInfo.returnDepartureCity}">
												<option value="${obj.travelInfo.returnDepartureCity}" selected="selected">${obj.orderInfo.returnDepartureCity}</option>
											</c:if>
										<input id="returncity" name="returnDepartureCity" type="hidden" />
										</select>
									</div>
								</div>
								<!-- 出发城市END -->
								<!-- 返回城市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label> 
										<select id="returnArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple">
											<c:if test="${!empty obj.orderInfo.returnArrivedCity}">
												<option value="${obj.travelInfo.returnArrivedCity}" selected="selected">${obj.orderInfo.returnArrivedCity}</option>
											</c:if>
										<input id="returnarrivecity" name="returnArrivedCity" type="hidden" >
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
									</div>
								</div>
								<!-- 返回城市END -->
								<!-- 航班号 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>航班号：</label> 
										<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple">
											<c:if test="${!empty obj.returnFlightInfo }">
												<option value="${obj.returnFlightInfo.flightnum }" selected="selected">${obj.returnFlightInfo.takeOffName }-${obj.returnFlightInfo.landingName } ${obj.returnFlightInfo.flightnum } ${obj.returnFlightInfo.takeOffTime }/${obj.returnFlightInfo.landingTime }</option>
											</c:if>
											<input id="returnflightnum" name="returnFlightNum" type="hidden" >
											<c:forEach items="${obj.flightlist }" var="flight">
												<c:if test="${obj.tripinfo.returnFlightNum eq  flight.flightnum}">
													<option selected="selected" value="${flight.flightnum }">${flight.takeOffName }-${flight.landingName } ${flight.flightnum } ${flight.takeOffTime }/${flight.landingTime }</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								<!-- 航班号END -->
							</div>
							<!-- 模块3END -->
							<!-- 隐藏域 -->
							<input type="hidden" name="orderid" value="${obj.travelInfo.orderid}">
							<!-- 隐藏域END -->
							<!-- 模块4 -->
							<div class="row body-from-input">
								<!-- 送签计划去美国地点 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签计划去美国地点：</label> <input name="planstate"
											type="text" value="${obj.travelInfo.state}"
											class="form-control input-sm" placeholder="州/省" />
									</div>
								</div>
								<!-- 送签计划去美国地点END -->
								<!-- 市 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span></span></label> 
										<input name="plancity" type="text" value="${obj.travelInfo.city}" class="form-control input-sm" placeholder="市" />
									</div>
								</div>
								<!-- 市END -->
								<!-- 街道 -->
								<div class="col-sm-6">
									<div class="form-group">
										<label><span></span></label> 
										<input name="planaddress" type="text" value="${obj.travelInfo.address}" class="form-control input-sm" placeholder="街道" />
									</div>
								</div>
								<!-- 街道END -->
							</div>
							<!-- 模块4END -->
						</div>
						<!-- 大模块END -->
					</div>
					<!-- 订单信息END -->
					<!-- 大模块二 -->
					<div class="info" id="mySwitch">
						<!-- 标题以及按钮组 -->
						<p class="info-head">申请人</p>
						<div class="dataInfoGroup">
							<a v-on:click="updatePhoto(${obj.summaryInfo.staffid })">拍照资料</a> 
							<a>护照信息</a> 
							<a>基本信息</a> 
							<a>签证信息</a>
						</div>
						<!-- 标题以及按钮组END -->
						
						<div class="info-body-from">
							<!-- 模块1 -->
							<div class="row body-from-input">
								<!-- 申请人左侧 -->
								<div class="col-sm-3">
									<!-- 二寸免冠照片 -->
									<div class="col-xs-10 picturesInch">
										<div class="form-group pictureTop">
											<div class="uploadInfo">
												<span class="inchInfo">二寸免冠照片</span> 
												<input id="cardInch" name="cardfront" type="hidden" value="" /> 
												<img id="imgInch" name="imgInch" alt="" src=""> 
												<input id="uploadFileInchImg" name="uploadFileInchImg" class="btn btn-primary btn-sm" type="file" value="上传" /> 
												<i class="delete" onclick="deleteApplicantInchImg()"></i>
											</div>
										</div>
									</div>
									<!-- 二寸免冠照片END -->
									<!-- 出行目的 -->
									<div class="col-sm-12 purpose">
										<div class="form-group">
											<label>出行目的</label> <input type="text"
												class="form-control input-sm" placeholder="" />
										</div>
									</div>
									<!-- 出行目的END -->
								</div>
								<!-- 申请人左侧END -->
								<!-- 申请人右侧 -->
								<div class="col-sm-9">
									<!-- 右侧模块1 -->
									<div class="row body-from-input">
										<!-- 姓名/拼音 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>姓名/拼音</label> 
												<input disabled="true" value="${obj.summaryInfo.staffname }" type="text" class="form-control input-sm" />
											</div>
										</div>
										<!-- 姓名/拼音END -->
										<!-- 性别 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>性别</label> <input name="sex" disabled="true"
													value="${obj.summaryInfo.sex }" type="text"
													class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 性别END -->
										<!-- 出生日期 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>出生日期</label> <input name="birthday" type="text" disabled="true"
													value="${obj.summaryInfo.birthday }"
													class="form-format form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 出生日期END -->
									</div>
									<!-- 右侧模块1END -->
									<!-- 右侧模块2 -->
									<div class="row body-from-input">
										<div class="col-sm-12">
											<div class="form-group">
												<label>所需资料</label> 
												<input name="realinfo" disabled="true" value="${obj.realinfo }" type="text" class="form-control input-sm" /> 
												<input name="staffid" type="hidden" value="${obj.summaryInfo.staffid }">
											</div>
										</div>
									</div>
									<!-- 右侧模块2END -->
									<!-- 右侧模块3 -->
									<div class="row body-from-input">
										<!-- AA码 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>AA码</label> 
												<input name="aacode" type="text" disabled="true" value="${obj.summaryInfo.aacode }" class="form-control input-sm" placeholder="" /> 
											</div>
										</div>
										<!-- AA码END -->
										<!-- 护照号 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>护照号</label> 
												<input name="passport" type="text" disabled="true" value="${obj.summaryInfo.passport }" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 护照号END -->
										<!-- 面试时间 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>面试时间</label> 
												<input name="Interviewdate" type="text" disabled="true" value="${obj.summaryInfo.Interviewdate }" class="form-format form-control input-sm" placeholder="" />
											</div>
										</div>
										<!-- 面试时间END -->
									</div>
									<!-- 右侧模块3END -->
									<!-- 右侧模块4 -->
									<div class="row body-from-input">
										<!-- 出行目的 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行目的</label> 
												<input id="" type="text" class="form-control input-sm" />
											</div>
										</div>
										<!-- 出行目的END -->
										<!-- 出行时间 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行时间</label> 
												<input id="" type="text" class="form-format form-control input-sm" />
											</div>
										</div>
										<!-- 出行时间END -->
										<!-- 停留天数 -->
										<div class="col-sm-4">
											<div class="form-group">
												<label>停留天数</label> 
												<input id="" type="text" class="form-control input-sm" />
											</div>
										</div>
										<!-- 停留天数END -->
									</div>
									<!-- 右侧模块4END -->
								</div>
								<!-- 申请人右侧END -->
							</div>
							<!-- 模块1END -->
						</div>
					</div>
					<!-- 大模块2END -->
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
	<script src="${base}/admin/pcVisa/updatePhoto.js"></script>
	<script type="text/javascript">
		$(".form-format").datetimepicker({
			format : "yyyy-mm-dd",
			showMeridian : true,
			autoclose : true,
			todayBtn : true
		});
		$(function() {

			$(".tripPlan").change(function() {

				var tripPlan = $("input[name='tripPlan']:checked").val();

				if (tripPlan == 1) {

					$(".checkShowORHide").show();
				} else {
					$(".checkShowORHide").hide();
				}
			});
		});
		/* 获取form下所有值 */
		 function getFormJson(form) {
			  var o = {};
			  var a = $(form).serializeArray();
			  $.each(a, function (){
				  if (o[this.name] != undefined) {
				  	if (!o[this.name].push) {
			  	  		o[this.name] = [o[this.name]];
			  		}
			  		o[this.name].push(this.value || '');
			  	  } else {
			  	  	o[this.name] = this.value || '';
			  	  }
			  });
			  return o;
		  }

		
		/* 异步保存数据 */
		function save() {
			var goDepartureCity = $('#goDepartureCity').val();
			var goArrivedCity = $('#goArrivedCity').val();
			var returnDepartureCity = $('#returnDepartureCity').val();
			var returnArrivedCity = $('#returnArrivedCity').val();
			var goflightnum =  $('#goFlightNum').val();
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
				success : function(data) {
					if (1 == data) {
						layer.msg('添加成功');
					} else {
						layer.msg('添加失败');
					}
					/* window.location.href = '/admin/pcVisa/visaDetail.html'; */
				}
			});
		};
		
		
		//加载城市的select2
		$('.select2City').select2({
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
					var selectdata = $.map(data, function (obj) {
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
			tags : false //设置必须存在的选项 才能选中
		});
		
		//出发航班select2
		$('#goFlightNum').select2({
			ajax : {
				url : "/admin/tripairline/getTripAirlineSelect.html",
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
					}else{
						goDepartureCity = '';
					}
					//去程抵达城市
					var goArrivedCity = $('#goArrivedCity').val();
					if (goArrivedCity) {
						goArrivedCity = goArrivedCity.join(',');
					}else{
						goArrivedCity = '';
					}
					var date = $('#goDate').val();
					return {
						date:date,
						//exname : cArrivalcity,
						gocity:goDepartureCity,
						arrivecity:goArrivedCity,
						flight : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						//obj.id = obj.id; // replace pk with your identifier
						obj.id = obj.flightnum; // replace pk with your identifier
						obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
						/*obj.text = obj.dictCode;*/
						goflightnum = obj.flightnum;
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
			tags : false //设置必须存在的选项 才能选中
		});
		
		//返程航班select2
		$('#returnFlightNum').select2({
			ajax : {
				url : "/admin/tripairline/getTripAirlineSelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					/*var cArrivalcity = $('#cArrivalcity').val();
					if(cArrivalcity){
						cArrivalcity = cArrivalcity.join(',');
					}*/
					//去程出发城市
					var returnDepartureCity = $('#returnDepartureCity').val();
					if (returnDepartureCity) {
						returnDepartureCity = returnDepartureCity.join(',');
					}else{
						returnDepartureCity += '';
					}
					//去程抵达城市
					var returnArrivedCity = $('#returnArrivedCity').val();
					if (returnArrivedCity) {
						returnArrivedCity = returnArrivedCity.join(',');
					}else{
						returnArrivedCity += '';
					}
					var date = $('#returnDate').val();
					return {
						//exname : cArrivalcity,
						date:date,
						gocity:returnDepartureCity,
						arrivecity:returnArrivedCity,
						flight : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						//obj.id = obj.id; // replace pk with your identifier
						obj.id = obj.flightnum; // replace pk with your identifier
						obj.text = obj.takeOffName + '-' + obj.landingName + ' ' +  obj.flightnum + ' '+ obj.takeOffTime + '/' +obj.landingTime;
						/*obj.text = obj.dictCode;*/
						returnflightnum = obj.flightnum;
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
			tags : false //设置必须存在的选项 才能选中
		});
		
		//去程出发城市
		$("#goDepartureCity").on("select2:select",function(e){
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			}else{
				thisval += '';
			}
			//设置回程抵达城市
			var thistext = $(this).text();
			$("#returnArrivedCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
			var goArrivedCity = $('#goArrivedCity').val();
			if (goArrivedCity) {
				goArrivedCity = goArrivedCity.join(',');
			}else{
				goArrivedCity += '';
			}
			var goDate = $('#goDate').val();
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			initFlightByInterface(goDate,thisval,goArrivedCity);
			initFlightByInterface(returnDate,goArrivedCity,thisval);
		});
		$("#goDepartureCity").on("select2:unselect",function(e){
			$(this).text('');
			$("#returnArrivedCity").html('');
		});
		//去程抵达城市
		$("#goArrivedCity").on("select2:select",function(e){
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			}else{
				thisval += '';
			}
			//设置回程出发城市
			var thistext = $(this).text();
			$("#returnDepartureCity").html('<option selected="selected" value="'+thisval+'">'+thistext+'</option>');
			var goDepartureCity = $('#goDepartureCity').val();
			if (goDepartureCity) {
				goDepartureCity = goDepartureCity.join(',');
			}else{
				goDepartureCity += '';
			}
			var goDate = $('#goDate').val();
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			initFlightByInterface(goDate,goDepartureCity,thisval);
			initFlightByInterface(returnDate,thisval,goDepartureCity);
		});
		$("#goArrivedCity").on("select2:unselect",function(e){
			$(this).text('');
			$("#returnDepartureCity").html('');
		});
		//返程出发城市
		$("#returnDepartureCity").on("select2:select",function(e){
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			}else{
				thisval += '';
			}
			var returnArrivedCity = $('#returnArrivedCity').val();
			if (returnArrivedCity) {
				returnArrivedCity = returnArrivedCity.join(',');
			}else{
				returnArrivedCity += '';
			}
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			initFlightByInterface(returnDate,thisval,returnArrivedCity);
		});
		//返程抵达城市
		$("#returnArrivedCity").on("select2:select",function(e){
			var thisval = $(this).val();
			if (thisval) {
				thisval = thisval.join(',');
			}else{
				thisval += '';
			}
			var returnDepartureCity = $('#returnDepartureCity').val();
			if (returnDepartureCity) {
				returnDepartureCity = returnDepartureCity.join(',');
			}else{
				returnDepartureCity += '';
			}
			var returnDate = $('#returnDate').val();
			//查询航班接口到缓存
			initFlightByInterface(returnDate,returnDepartureCity,thisval);
		});

		//加载航班号到缓存并同步到数据库
		function initFlightByInterface(departuredate,departurecity,arrivedcity){
			$.ajax({ 
				url: '/admin/tripairline/getAirLineByInterfate.html',
				dataType:"json",
				data:{date:departuredate,gocity:departurecity,arrivecity:arrivedcity},
				type:'post',
				success: function(data){
				}
			});
		}
		
		//连接websocket
		connectWebSocket();
		function connectWebSocket(){
			 if ('WebSocket' in window){  
	            console.log('Websocket supported');  
	            socket = new WebSocket('ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');   

	            console.log('Connection attempted');  

	            socket.onopen = function(){  
	                 console.log('Connection open!');  
	                 //setConnected(true);  
	             };

	            socket.onclose = function(){  
	                console.log('Disconnecting connection');  
	            };

	            socket.onmessage = function (evt){
	            		var type = evt.type;
	            		var staffid = evt.staffid;
	                  if(evt){
		                  console.log(evt);
		                		  window.location.href = '/admin/pcVisa/updatePhoto.html?staffid='+staffid;
	                  }
	                  console.log('message received!');  
	                  //showMessage(received_msg);  
	             };  

	          } else {  
	            console.log('Websocket not supported');  
	          }  
		} 
	</script>
</body>
</html>
