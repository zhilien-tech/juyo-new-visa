<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
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
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
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
			.row.body-from-input {padding-left: 8%;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>170202-JP0001</p></span>
					<!-- <span class="">受付番号：<p>JDY27163</p></span> -->
					<span class="">状态：<p>下单</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" onclick="saveAddOrder();"/>
					<input type="button" value="回邮" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="初审" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="分享" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" />
				</div>
				<section class="content">
					<form id="orderInfo">
					<div class="info" id="customerInfo" ref="customerInfo">
					<p class="info-head">客户信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 公司全称 -->
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>客户来源：</label> 
									<select id="customerType" name="source" class="form-control input-sm">
										<option value="">--请选择--</option>
										<c:forEach var="map" items="${obj.customerTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
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
						<!-- end 公司全称 -->
						<div class="row body-from-input">
							<!-- 联系人/手机号/邮箱 -->
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>联系人：</label> 
									<select id = "linkman" name="linkman"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
									
									<!-- <input id="linkman"
										name="linkman" type="text" class="form-control input-sm"
										placeholder=" " /> --> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>手机号：</label> 
									<select id = "telephone" name="mobile"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
									
									<!-- <input id="telephone"
										name="mobile" type="text" class="form-control input-sm"
										placeholder=" " /> --> <i
										class="bulb"></i>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>邮箱：</label> 
									<select id = "email" name="email"
										class="form-control select2 cityselect2" multiple="multiple"
										data-placeholder="">
								</select>
									
									<!-- <input id="email" name="email"
										type="text" class="form-control input-sm" placeholder=" "
										 /> --> <i class="bulb"></i>
								</div>
							</div>
						</div>
						<!-- end 联系人/手机号/邮箱 -->
					</div>
				</div>
				<!-- end 客户信息 -->
				<!-- 订单信息 -->
				<div class="orderInfo info" id="orderInfo">
					<p class="info-head">订单信息</p>
					<div class="info-body-from">
						<div class="row body-from-input">
							<!-- 人数/领区/加急 -->
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>人数：</label> <input id="number"
										name="number" type="text" class="form-control input-sm"
										placeholder=" "  />
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
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>加急：</label> <select id="urgentType"
										name="urgenttype" class="form-control input-sm"
										onchange="selectListData();" >
										<c:forEach var="map" items="${obj.mainSaleUrgentEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
									<!-- <i class="bulb"></i> 小灯泡-->
								</div>
							</div>
							<div class="col-sm-3 none" id="urgentDay">
								<div class="form-group">
									<label>&nbsp;</label> <select id="urgentDay" name="urgentday"
										class="form-control input-sm" >
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
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>行程：</label> <select id="travel"
										name="travel" class="form-control input-sm"
										>
										<c:forEach var="map" items="${obj.mainSaleTripTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>付款方式：</label> <select id="payType"
										name="paytype" class="form-control input-sm"
										>
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
										 />
								</div>
							</div>
						</div>
						<!-- end 行程/付款方式/金额 -->
						<div class="row body-from-input">
							<!-- 签证类型 -->
							<div class="col-sm-3  col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>签证类型：</label> 
									<select id="visaType" name="visatype" class="form-control input-sm" onchange="selectListData();" >
										<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-8 none" id="sixCounty" v-model="orderInfo.visacounty">
								<div class="form-group viseType-btn">
									<label style="display: block;">&nbsp;</label> 
									<input type="button" value="冲绳县" class="btn btn-sm btnState">
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
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>过去三年是否访问过：</label> 
									<select id="isVisit" name="isvisit" class="form-control input-sm" onchange="selectListData();" >
										<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
											<option value="${map.key}" ${map.key==0?'selected':''}>${map.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-8 none" id="isVisited" v-model="orderInfo.threeCounty">
								<div class="form-group viseType-btn">
									<label style="display: block;">&nbsp;</label> 
									<input type="button" value="岩手县" class="btn btn-sm btnState"> 
									<input type="button" value="秋田县" class="btn btn-sm btnState"> 
									<input type="button" value="山形县" class="btn btn-sm btnState">
								</div>
							</div>
						</div>
						<!-- end 过去三年是否访问过 -->
						<div class="row body-from-input">
							<!-- 出行时间/停留天数/返回时间 -->
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>出行时间：</label> <input id="goTripDate"
										name="gotripdate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										 />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>停留天数：</label> <input id="stayDay"
										name="stayday" type="text" class="form-control input-sm"
										placeholder=" "  />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>返回时间：</label> <input id="backTripDate"
										name="backtripdate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										 />
								</div>
							</div>
						</div>
						<!-- end 出行时间/停留天数/返回时间 -->
						<div class="row body-from-input">
							<!-- 送签时间/出签时间 -->
							<div class="col-sm-3 col-sm-offset-1">
								<div class="form-group">
									<label><span>*</span>送签时间：</label> <input id="sendVisaDate"
										name="sendvisadate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										 />
								</div>
							</div>
							<div class="col-sm-3">
								<div class="form-group">
									<label><span>*</span>出签时间：</label> <input id="outVisaDate"
										name="outvisadate" type="text" class="form-control input-sm"
										placeholder=" " onClick="WdatePicker()"
										 />
								</div>
							</div>
						</div>
						<!-- end 送签时间/出签时间 -->
					</div>
				</div><!-- end 订单信息 -->
				
				<div class="row body-from-input" id="applicantInfo"><!-- 添加申请人 -->
					<div class="col-sm-12">
						<div class="form-group">
							<button type="button" class="btn btn-primary btn-sm addApplicantBtn">添加申请人</button>
						</div>
					</div>
				</div><!-- end 添加申请人 -->

				<div class="info none" id="mySwitch"><!-- 主申请人 -->
					<p class="info-head">
						主申请人 
						<input type="button" name="" value="添加" class="btn btn-primary btn-sm pull-right">
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
							<tbody >
								<tr>

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
								</tr>
							</tbody>
						</table>
					</div>
				</div><!-- end 主申请人 -->
				

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

						<div class="row body-from-input">
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

				</form>
				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
		
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/admin/orderJp/order.js"></script><!-- 本页面js文件 -->
		<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/admin/orderJp/applicant.js"></script>
		
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			function selectListData() {
				var isVisited = $("#isVisit").val();
				var visaType = $("#visaType").val();
				var mainSaleUrgentEnum = $("#urgentType").val();
				if (isVisited == 1) {
					$("#isVisited").removeClass("none");
				} else {
					$("#isVisited").addClass("none");
				}

				if (visaType == 2) {
					$("#sixCounty").removeClass("none");
				} else {
					$("#sixCounty").addClass("none");
				}

				if (mainSaleUrgentEnum != 1) {
					$("#urgentDay").removeClass("none");
				} else {
					$("#urgentDay").addClass("none");
				}
			}
			
			function shortname(){
				var content = $("#comShortName").val();
				alert(content);
				var sum = 0;
				re = /[\u4E00-\u9FA5]/g; //测试中文字符的正则
				if (content) {
				if (re.test(content)) //使用正则判断是否存在中文
				{
				if (content.match(re).length >= 6) { //返回中文的个数
				$.dialog.tips("帖子正文不能小于6个汉字！");
			}
			
			function saveAddOrder(){
				var orderinfo = $("#orderInfo").serialize();
				$.ajax({
					type : 'POST',
					data : orderinfo ,
					url : '${base}/admin/orderJp/saveAddOrderinfo',
					success : function(data) {
						console.log(JSON.stringify(data));
						window.location.href = '${base}/admin/orderJp/list';
					},
					error : function() {
						alert("error");
					}
				}); 
			}
		</script>
	</body>
</html>
