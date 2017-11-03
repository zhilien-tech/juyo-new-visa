<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>下单</title>
<style type="text/css">
.form-control {
	height: 30px;
}

.add-btn {
	top: -35px;
	right: -1.5%;
}

.remove-btn {
	top: -35px;
	right: -1.5%;
}

.multiPass_roundTrip-div {
	width: 120px;
	float: right;
	position: relative;
	top: 5px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper" style="min-height: 848px;">
			<div class="qz-head">
				<span class="">订单号：
					<p>170202-JP0001</p>
				</span> <span class="">受付番号：
					<p>JDY27163</p>
				</span> <span class="">状态：
					<p>下单</p>
				</span> <input type="button" value="取消"
					class="btn btn-primary btn-sm pull-right" /> <input type="button"
					value="保存" class="btn btn-primary btn-sm pull-right" id="saveOrder" v-on:click="order()"/> <input
					type="button" value="回邮" class="btn btn-primary btn-sm pull-right" />
				<input type="button" value="初审"
					class="btn btn-primary btn-sm pull-right" /> <input type="button"
					value="分享" class="btn btn-primary btn-sm pull-right" /> <input
					type="button" value="日志" class="btn btn-primary btn-sm pull-right" />
			</div>
			<section class="content">
				<!-- 客户信息 -->
				<div class="info" id="customerInfo" ref="customerInfo">
					<p class="info-head">客户信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 公司全称 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>客户来源：</label> <select id="customerType"
										name="source" class="form-control input-sm" v-model="customerInfo.source" >
										<option value="">--请选择--</option>
										<c:forEach var="map" items="${obj.customerTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>

									<!-- <select
										class="form-control input-sm" id="source" name="source"
										v-model="source">
										<option>--请选择--</option>
										<option v-for="data in orderJpData" v-bind:value="data.customerTypeEnum.key">
											{{data.customerTypeEnum.value}}</option>
									</select>  -->
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-9">
								<div class="form-group">
									<label><span>*</span>公司全称：</label> <input id="compName" name="compName"
										type="text" class="form-control input-sm" placeholder=" " v-model="customerInfo.name"/>
									<i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 公司全称 -->
						<div class="row body-from-input">
							<!-- 客户来源/联系人/手机号/邮箱 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>公司简称：</label> <input id="comShortName" name="comShortName"
										type="text" class="form-control input-sm" placeholder=" " v-model="customerInfo.shortname"/>
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>联系人：</label> <input id="linkman" name="linkman"
										type="text" class="form-control input-sm" placeholder=" " v-model="customerInfo.linkman"/>
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>手机号：</label> <input id="telephone" name="telephone"
										type="text" class="form-control input-sm" placeholder=" " v-model="customerInfo.mobile"/>
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>邮箱：</label> <input id="email" name="email"
										type="text" class="form-control input-sm" placeholder=" " v-model="customerInfo.email"/>
									<i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 客户来源/联系人/手机号/邮箱 -->
					</div>
				</div>
				<!-- end 客户信息 -->

				<!-- 订单信息 -->
				<div class="orderInfo" id="orderInfo">
					<p class="info-head">订单信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 人数/领区/加急 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>人数：</label> <input id="number" name="number"
										type="text" class="form-control input-sm" placeholder=" " v-model="orderInfo.number"/>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>领区：</label> 
									<select id="cityId"
										name="cityId" class="form-control input-sm" v-model="orderInfo.cityId">
										<c:forEach var="map" items="${obj.collarAreaEnum}">
											<option value="${map.key}" >${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>加急：</label> 
									<select id="urgentType"
										name="urgentType" class="form-control input-sm" onchange="selectListData();" v-model="orderInfo.urgentType">
										<c:forEach var="map" items="${obj.mainSaleUrgentEnum}">
											<option value="${map.key}"  ${map.key==1?'selected':''}>${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3 none" id="urgentDay">
								<div class="form-group">
									<label>&nbsp;</label> 
									<select id="urgentDay"
										name="urgentDay" class="form-control input-sm" v-model="orderInfo.urgentDay">
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
									<label><span>*</span>行程：</label> 
									<select id="travel"
										name="travel" class="form-control input-sm" v-model="orderInfo.travel">
										<c:forEach var="map" items="${obj.mainSaleTripTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>付款方式：</label> 
									<select id="payType"
										name="payType" class="form-control input-sm" v-model="orderInfo.payType">
										<c:forEach var="map" items="${obj.mainSalePayTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>金额：</label> <input id="money" name="money"
										type="text" class="form-control input-sm" placeholder=" " v-model="orderInfo.money"/>
								</div>
							</div>
						</div>
						<!-- end 行程/付款方式/金额 -->
						<div class="row body-from-input">
							<!-- 签证类型 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>签证类型：</label> 
									<select id="visaType"
										name="visaType" class="form-control input-sm" onchange="selectListData();" v-model="orderInfo.visaType">
										<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-9 none" id="sixCounty" v-model="orderInfo.visaCounty">
								<div class="form-group viseType-btn">
									<label style="display: block;">&nbsp;</label> <input
										type="button" value="冲绳县" class="btn btn-sm btnState">
									<input type="button" value="青森县" class="btn btn-sm btnState">
									<input type="button" value="岩手县" class="btn btn-sm btnState">
									<input type="button" value="宫城县" class="btn btn-sm btnState">
									<input type="button" value="秋田县" class="btn btn-sm btnState">
									<input type="button" value="山形县" class="btn btn-sm btnState">
									<input type="button" value="福鸟县" class="btn btn-sm btnState">
								</div>
							</div>
						</div>
						<!-- end 签证类型 -->
						<div class="row body-from-input">
							<!-- 过去三年是否访问过 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>过去三年是否访问过：</label> 
									<select id="isVisit"
										name="isVisit" class="form-control input-sm" onchange="selectListData();" v-model="orderInfo.isvisit">
										<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
											<option value="${map.key}" ${map.key==0?'selected':''}>${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-9 none" id="isVisited" v-model="orderInfo.threeCounty">
								<div class="form-group">
									<label style="display: block;">&nbsp;</label> <input
										type="button" value="岩手县"
										class="btn btn-sm btnState btnState-true"> <input
										type="button" value="秋田县"
										class="btn btn-sm btnState btnState-true"> <input
										type="button" value="山形县"
										class="btn btn-sm btnState btnState-true">
								</div>
							</div>
						</div>
						<!-- end 过去三年是否访问过 -->
						<div class="row body-from-input">
							<!-- 出行时间/停留天数/返回时间 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>出行时间：</label> <input id="goTripDate" name="goTripDate"
										type="text" class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()" v-model="orderInfo.goTripDate"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>停留天数：</label> <input id="stayDay" name="stayDay"
										type="text" class="form-control input-sm" placeholder=" " v-model="orderInfo.stayDay"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>返回时间：</label> <input id="backTripDate" name="backTripDate"
										type="text" class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()" v-model="orderInfo.backTripDate"/>
								</div>
							</div>
						</div>
						<!-- end 出行时间/停留天数/返回时间 -->
						<div class="row body-from-input">
							<!-- 送签时间/出签时间 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>送签时间：</label> <input id="sendVisaDate" name="sendVisaDate"
										type="text" class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()" v-model="orderInfo.sendVisaDate"/>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>出签时间：</label> <input id="outVisaDate" name="outVisaDate"
										type="text" class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()" v-model="orderInfo.outVisaDate"/>
								</div>
							</div>
						</div>
						<!-- end 送签时间/出签时间 -->
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

						<div class="row body-from-input" id="backmailInfo">
							<!-- 添加回邮信息 -->
							<div class="col-sm-12">
								<div class="form-group">
									<button type="button"
										class="btn btn-primary btn-sm addExpressInfoBtn">添加回邮信息</button>
								</div>
							</div>
						</div>
						<!-- end 添加回邮信息 -->
					</div>
				</div>
				<!-- end 订单信息 -->

				<!-- 快递信息 -->
				<div class="info expressInfo none" id="expressInfo" name="backmailInfo">
					<p class="info-head">快递信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 资料来源/回邮方式/回邮地址 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>资料来源：</label>
									<select id="datasour"
										name="datasour" class="form-control input-sm" v-for="">
										<c:forEach var="map" items="${obj.mainBackMailSourceTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								 <i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>回邮方式：</label> 
									<select id="expressType"
										name="expressType" class="form-control input-sm" v-for="">
										<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>回邮地址：</label> <input id="expressAddress" name="expressAddress"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 资料来源/回邮方式/回邮地址 -->

						<div class="row body-from-input">
							<!-- 联系人/电话/发票项目内容/发票抬头 -->
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>联系人：</label> <input id="linkman" name="linkman"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>电话：</label> <input id="telephone" name="telephone"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票项目内容：</label> <input id="invoiceContent" name="invoiceContent"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>发票抬头：</label> <input id="invoiceHead" name="invoiceHead"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 联系人/电话/发票项目内容/发票抬头 -->

						<div class="row body-from-input">
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
									<label><span>*</span>快递号：</label> <input id="expressNum" name="expressNum"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label><span>*</span>备注：</label> <input id="remark" name="remark"
										type="text" class="form-control input-sm" placeholder=" " />
									<i class="bulb"></i>
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
	<script
		src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<!-- 公用js文件 -->
	<script
		src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/admin/orderJp/order.js"></script>
	<!-- 本页面js文件 -->
	<script type="text/javascript">
		function selectListData(){
			var isVisited = $("#isVisit").val();
			var visaType = $("#visaType").val();
			var mainSaleUrgentEnum = $("#urgentType").val();
			if(isVisited == 1){
				$("#isVisited").removeClass("none");
			}else{
				$("#isVisited").addClass("none");
			}
			
			if(visaType == 2){
				$("#sixCounty").removeClass("none");
			}else{
				$("#sixCounty").addClass("none");
			}
			
			if(mainSaleUrgentEnum != 1){
				$("#urgentDay").removeClass("none");
			}else{
				$("#urgentDay").addClass("none");
			}
		}
		
		
		var url = "${base}/admin/orderJp/getOrder.html";
		var orderobj;
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
					data:{id : id},
					success : function(data) {
						orderobj.customerInfo = data.customerInfo;
						orderobj.orderInfo = data.orderInfo;
						orderobj.applicantInfo = data.applicantInfo;
						//this.backmailInfo = data.backmailInfo;
						console.log(JSON.stringify(orderobj.customerInfo));
						console.log(JSON.stringify(orderobj.orderInfo.urgentType));
						console.log(JSON.stringify(orderobj.applicantInfo));
					},
					error:function(){
						alert("error");
					}
				});
			},
			methods:{
	        	order:function(){
	        		/* //orderobj.orderInfo = $("#orderInfo").serialize();
	        		var customer = this.$refs.customerInfo;
	        		//var customer = orderobj.customerInfo;
	        		var order = orderobj.orderInfo;
	        		var applicant = orderobj.applicantInfo;
	        		console.log(order);
	        		var backmail;
	        		$.ajax({
	        			type:'POST',
	        			data : {
	        				customerInfo:customer,
	        				orderInfo:order,
	        				applicantInfo:applicant,
	        				backmailInfo:backmail
	        				
	        			},
	        			url:'${base}/admin/orderJp/order',
	        			success:function(data){
	        				alert(JSON.stringify(data));
	        				console.log(JSON.stringify(data));
	        				//window.location.href = '${base}/admin/orderJp/order';
	        			},
	        			error:function(){
	        				alert("error");
	        			}
	        		});
	        		//window.location.href = '${base}/admin/orderJp/order';
	        		//console.log(message);
	        		//alert(JSON.stringify(event.target)); */
	        	}
	        }
		});      
	</script>
</body>
</html>