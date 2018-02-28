<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>销售详情</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
		<style type="text/css">
			.modal-body { height:489px;}
			.form-control{height: 30px; }
			.add-btn{top:-225px;right:-1%;}
			.remove-btn{top: -225px;right: -1%;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.qz-head { position:fixed;top:0;left:0;z-index:99999; width:100%;}
			.content { margin-top:50px;}
			.info { position:relative;}
			#addCustomer { position:absolute; top:5px; right:10px;}
			.info-body-from { margin-left:8%;}
			#urgentDays { width:14.2%;}
			.wrapper { background-color:#f9f9f9 !important;}
			.col-sm-3 { width:28%;}
			.col-sm-1 { width:11.5% !important;}
			.select2 { width:100% !important;}
			.addApplicantBtn { width:30% !important;}
			[v-cloak]{display:none;}
			.btn-Big { width:82px !important;}
		</style>
	</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper" >
		<div class="content-wrapper">
			<div class="qz-head">
				<span class="">订单号：<p>${obj.orderInfo.orderNum}</p></span> 
				<span class="">受付番号：<p></p></span> 
				<span class="">状态：<p id="spanStatus">${obj.orderstatus }</p></span> 
				<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" onclick="cancel();"/> 
				<input type="button" value="保存并返回" class="btn btn-primary btn-sm pull-right btn-Big" id="saveOrder" v-on:click="order()" /> 
				<!-- <input type="button" value="回邮" class="btn btn-primary btn-sm pull-right" /> -->
				<input type="button" value="初审" class="btn btn-primary btn-sm pull-right" @click="firtTrialJp(orderInfo.id)"/>
				<input type="button" value="分享" class="btn btn-primary btn-sm pull-right" @click="share(orderInfo.id)" />
				<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" @click="log(orderInfo.id)" />
			</div>
			<section class="content">
				<!-- 客户信息 -->
				<div class="info" id="customerInfo" ref="customerInfo">
						<p class="info-head">客户信息</p><input id="addCustomer"
					type="button" value="添加" class="btn btn-primary btn-sm pull-right" @click="addCustomer()"/>
						<div class="info-body-from">
							<div class="row body-from-input">
								<!-- 公司全称 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>客户来源：</label> 
										<select id="customerType" name="cuSource" class="form-control input-sm" >
											<option value="">--请选择--</option>
											<c:forEach var="map" items="${obj.customerTypeEnum}">
												<option value="${map.key}" 
													<c:choose>
													   <c:when test="${obj.orderInfo.isDirectCus == 1}">  
													          ${map.key==4?"selected":"" }
													   </c:when>
													   <c:otherwise> 
													   ${map.key==obj.customer.source?"selected":"" }
													   </c:otherwise>
													</c:choose>
												<%-- <c:if test="${obj.orderInfo.isDirectCus == 1}">${map.key==4?"selected":"" }</c:if><c:if test="${obj.orderInfo.isDirectCus != 1}">${map.key==obj.customerInfo.source?"selected":"" }</c:if>> --%>
													>${map.value}
												</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="on-line"><!-- select2 线上/OTS/线下 -->
									<div class="col-sm-3">
										<div class="form-group" >
											<label><span>*</span>公司全称：</label> 
											<select id ="compName" name="name"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" >
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
												data-placeholder="" >
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.shortname }</option>
												</c:if>
											</select>
										</div>
									</div>
								</div><!-- end select2 线上/OTS/线下 -->
								<div class="zhiKe none"><!-- input 直客 -->
									<div class="col-sm-3">
										<div class="form-group" >
											<label><span>*</span>公司全称：</label> 
											<input id="compName2" name="name" type="text" class="form-control input-sm" placeholder=" " value="${obj.orderInfo.comName }"/>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>公司简称：</label>
											<input id="comShortName2" name="shortname" type="text" class="form-control input-sm" placeholder=" " value="${obj.orderInfo.comShortName }" />
										</div>
									</div>
								</div><!-- end input 直客 -->
							</div>
							<div class="row body-from-input on-line"><!-- select2 线上/OTS/线下 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> 
										<select id = "linkman" name="cusLinkman"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" >
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.linkman }</option>
												</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label> 
										<select id = "mobile" name="mobile"
												class="form-control select2 cityselect2 " multiple="multiple"
												data-placeholder="" >
												<c:if test="${ !empty obj.customer.id }">
													<option value="${obj.customer.id }" selected="selected">${obj.customer.mobile }</option>
												</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label> 
										<select id = "email" name="cusEmail" class="form-control select2 cityselect2 " multiple="multiple" data-placeholder="" >
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
										<input id="linkman2" name="cusLinkman" type="text" class="form-control input-sm" placeholder=" " value="${obj.orderInfo.linkman }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label> 
										<input id="mobile2" name="mobile" type="text" class="form-control input-sm" placeholder=" " value="${obj.orderInfo.telephone }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label>
										<input id="email2" name="cusEmail" type="text" class="form-control input-sm" placeholder=" " value="${obj.orderInfo.email }"/>
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
									<label><span>*</span>人数(此人数只是参考人数)：</label> <input id="number"
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
							<div class="col-sm-1">
								<div class="form-group">
									<label><span>*</span>加急：</label> <select id="urgentType"
										name="urgentType" class="form-control input-sm"
										 v-model="orderInfo.urgenttype">
										<c:forEach var="map" items="${obj.mainSaleUrgentEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>

									<div class="col-sm-2 none" id="urgentDays">
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
										>
										<option value="">--请选择--</option>
										<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
											<option value="${map.key}" ${map.key==obj.orderInfo.payType?"selected":"" }>${map.value}</option>
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
										 v-model="orderInfo.visatype">
										<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- <div class="col-sm-8 none" id="sixCounty" v-model="orderInfo.visacounty">
								<div class="form-group viseType-btn"> -->
								
								<c:choose>
									<c:when test="${obj.orderJpinfo.visaType == 2 || obj.orderJpinfo.visaType == 3 || obj.orderJpinfo.visaType == 4}">
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
								<c:when test="${obj.orderJpinfo.visaType == 2 || obj.orderJpinfo.visaType == 3 || obj.orderJpinfo.visaType == 4}">
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
										 v-model="orderInfo.isvisit">
										<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
											<option value="${map.key}" >${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5">
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
										 type="text" class="form-control input-sm"
										placeholder=" " 
										v-model="orderInfo.gotripdate" />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>行程天数：</label> <input id="stayDay"
										name="stayday" type="text" class="form-control input-sm"
										placeholder=" " v-model="orderInfo.stayday" />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>返回时间：</label> <input id="backTripDate"
										 type="text" class="form-control input-sm"
										placeholder=" " 
										v-model="orderInfo.backtripdate" />
								</div>
							</div>
						</div>
						<!-- end 出行时间/停留天数/返回时间 -->
						<div class="row body-from-input">
							<!-- 送签时间/出签时间 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>预计送签时间：</label> 
									<input id="sendVisaDate"  type="text" class="form-control input-sm"
										placeholder=" "  v-model="orderInfo.sendvisadate" />
									<!-- <input id="sendVisaDate" name="sendVisaDate" type="text" class="form-control input-sm"  /> -->
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>预计出签时间：</label> <input id="outVisaDate"
										 type="text" class="form-control input-sm"
										placeholder=" " 
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
							<button type="button" class="btn btn-primary btn-sm addApplicantBtn" onclick="addApplicant(${obj.orderId})">添加申请人</button>
						</div>
					</div>
				</div><!-- end 添加申请人 -->

							
				<!-- 主申请人 -->
				<div class="info none" id="mySwitch">
					<input type="hidden" id="appId" value="" name="appId"/>
					<p class="info-head"><span>申请人</span>
						<input type="button" name="" value="添加"
							class="btn btn-primary btn-sm pull-right" onclick="addApplicant(${obj.orderId})" />
					</p>
					<div class="info-table" style="padding-bottom: 1px;">
						<table id="principalApplicantTable" class="table table-hover"
							style="width: 100%;">
							<thead>
								<tr>
									<th><span>&nbsp; <span></th>
									<th><span>姓名<span></th>
									<th><span>电话<span></th>
									<th><span>邮箱<span></th>
									<th><span>护照号<span></th>
									<th><span>性别<span></th>
									<th><span>操作<span></th>
								</tr>
							</thead>
							<tbody>
								<tr v-cloak v-for="applicant in applicantInfo" >
									<td width="4%">
										<div v-if="applicant.id==applicant.mainid">
											<font color="blue">主</font> 
										</div>
										<div v-else></div>
									</td>
									
									<td width="10%">{{applicant.applyname}}</td>
									<td width="10%">{{applicant.telephone}}</td>
									<td width="15%">{{applicant.email}}</td>
									<td width="15%">{{applicant.passport}}</td>
									<td width="4%">{{applicant.sex}}</td>
									<td width="42%">
										<a v-on:click="updateApplicant(applicant.id);">基本信息</a>&nbsp;&nbsp;
										<a v-on:click="passport(applicant.id,orderInfo.id)">护照信息</a>&nbsp;&nbsp;
										<a v-on:click="visa(applicant.id,orderInfo.id)">签证信息</a>&nbsp;&nbsp;
										<a v-on:click="visaInput(applicant.applicantjpid)">签证录入</a>&nbsp;&nbsp;
										<a v-on:click="backmailInfo(applicant.id)">回邮</a>&nbsp;&nbsp;
										<a v-on:click="deleteApplicant(applicant.id)">删除</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<!-- end 主申请人 -->
			</section>
		</div>
		<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var visacounty = '${obj.orderJpinfo.visaCounty}';
		var threecounty = '${obj.orderJpinfo.threeCounty}';
		var orderid = '${obj.orderId}';
		var orderJpId = '${obj.orderJpId}';
		var localAddr = '${obj.localAddr}';
		var localPort = '${obj.localPort}';
		var websocketaddr = '${obj.websocketaddr}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/moment.js"></script>
	<script src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/orderJp/searchCustomerInfo.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<%-- <script type="text/javascript" src="${base}/admin/common/commonjs.js"></script> --%>
	<script src="${base}/admin/orderJp/order.js"></script>
	
	<!-- 本页面js文件 -->
	<script type="text/javascript">
	
	/* //连接websocket
	connectWebSocket();
	function connectWebSocket(){
		 if ('WebSocket' in window){  
	        console.log('Websocket supported');  
	        socket = new WebSocket('ws://'+localAddr+':'+localPort+'/'+websocketaddr);   
	        console.log('Connection attempted');  
	        socket.onopen = function(){  
	             console.log('Connection open!');  
	             //setConnected(true);  
	         };
	        socket.onclose = function(){  
	            console.log('Disconnecting connection');  
	        };
	        socket.onmessage = function (evt){   
	              var received_msg = evt.data;  
	              if(received_msg){
	                  var receiveMessage = JSON.parse(received_msg);
	                  if(receiveMessage.applicantid == applicantId){
	                	  if(receiveMessage.messagetype == 1){
		                	  window.location.reload();
	                	  }else if(receiveMessage.messagetype == 2){
	                		  window.location.href = '/admin/orderJp/passportInfo.html?applicantId='+applicantId+'&orderid='+orderid+'&isTrial='+isTrailOrder+'&orderProcessType='+orderProcessType;
	                	  }
	                  }
	              }
	              console.log('message received!');  
	              //showMessage(received_msg);  
	          };  

	      } else {  
	        console.log('Websocket not supported');  
	      }  
	} */
	
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
			$("#sendVisaDate").datetimepicker("setEndDate",getNewDaySub($("#goTripDate").val(),8));
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
	</script>
</body>
</html>
