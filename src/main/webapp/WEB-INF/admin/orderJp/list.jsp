<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>销售-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css"> 
	<link rel="stylesheet" href="${base}/references/public/css/saleJapan.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css"><!-- 订单切换卡 样式 -->
	<style type="text/css">
	 body { font-size:12px;}
	 [v-cloak]{display:none;}
	 #hideOrder:hover { text-decoration: none;cursor:pointer;}
	 /*头导航不随下拉移动*/
	 .box-header { position:fixed; top:0;left:0; width:100%; height:120px; background:#FFF; z-index:99999; padding:20px 30px 20px 40px;}
	 .box-body {  overflow:hidden;margin-top:120px;}
	 .card-head div { font-weight:normal;}
	 .box-body { padding:0 15px 0 15px;}
	 .allDiv { width:15%;}
	 .searchStrWidth { width: 31% !important;}
	 .BtnWidth { width: 24% !important;}
	 label { margin-bottom:0;}
	</style>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<!-- 切换卡按钮 start -->
						<!-- <div class="btnGroups">
							<a class="btnList bgColor">全部</a>
							<a class="btnList">我的</a>
						</div> -->
						<!-- 切换卡按钮 end -->
						<div class="row">
							<div class="col-md-2 left-5px right-0px allDiv">
								<select class="input-class input-sm" id="status" name="status" onchange="countryChange();">
									<option value="">状态</option>
									<c:forEach var="map" items="${obj.orderStatus}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px allDiv">
								<select class="input-class input-sm" id="source" name="source" onchange="countryChange();">
									<option value="">客户来源</option>
									<c:forEach var="map" items="${obj.customerTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px allDiv">
								<select class="input-class input-sm" id="visaType" name="visaType" onchange="countryChange();">
									<option value="">签证类型</option>
									<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px searchStrWidth searchAll">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/护照/公司简称/联系人/电话/邮箱/申请人/申请人电话" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-3 left-5px BtnWidth searchBtn" >
								<a class="btn btn-primary btn-sm pull-left"  id="searchbtn">搜索</a>
								<a id="emptyBtn" class="btn btn-primary btn-sm pull-left">清空</a> 
								<a class="btn btn-primary btn-sm pull-right" id="orderBtn" onclick="addOrder();" v-on:click="">下单</a>
							</div>
						</div>
						<div class="row" style="margin-top:15px;"> 
							<div class="col-md-2 left-5px right-0px allDiv">
								<input type="text" class="input-sm input-class" id="start_time" name="start_time" placeholder="创建日期" onchange="countryChange();"/>
							</div>
							<div class="col-md-2 left-5px right-0px allDiv">
								<input type="text" class="input-sm input-class" id="sendSignDate" name="sendSignDate" placeholder="送签时间" onchange="countryChange();"/>
							</div>
							<div class="col-md-2 left-5px right-0px allDiv">
								<input type="text" class="input-sm input-class" id="signOutDate" name="signOutDate" placeholder="出签时间" onchange="countryChange();"/>
								
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in orderJpData">
							<div class="card-head">
								<div><label>订单号：</label><span style="cursor:pointer" v-on:click="order(data.orderid)">{{data.ordernum}}</span></div>	
								<div><label>人数：</label><span>{{data.peoplenum}}</span></div>	
								<div v-if="data.isdisabled==1" style="position:absolute;right:25%;">
								<label></label><span  style="font-size:16px;font-weight:bold;">作废</span>
								</div>	
								<div v-else style="position:absolute;right:25%;">
								<label></label><span  style="font-size:16px;font-weight:bold;">{{data.status}}</span>
								</div>
								<div class="btnGroup">
									<label>操作：</label>
									<div v-if="data.isdisabled==1">
										<i class="edit1"  v-on:click="" > </i>
										<i class="share1" v-on:click=""> </i>
										<i class="theTrial1" v-on:click=""> </i>
										<!-- <i class="return" > </i> -->
										<i class="toVoid1" v-on:click="undisabled(data.orderid)"> </i>
									</div>
									<div v-else>
										<i class="edit"  v-on:click="order(data.orderid)"> </i>
										<i class="share" v-on:click="share(data.orderid)"> </i>
										<i class="theTrial" v-on:click="theTrial(data.orderid)"> </i>
										<!-- <i class="return" > </i> -->
										<i class="toVoid" v-on:click="disabled(data.orderid, data.status)"> </i>
									</div>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" >
									<div><label>公司简称：</label><span>{{data.comshortname}}</span></div>
									<div><label>客户来源：</label><span>{{data.source}}</span></div>
									<div><label>联系人：</label><span>{{data.linkman}}</span></div>
									<div><label>电话：</label><span>{{data.telephone}}</span></div>
									<div><label>申请人：</label><span>{{data.applicants}}</span></div>
									<div><!-- <i> </i> --></div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
					<a id="hideOrder" style="color:#bbbbbb; font-size:26px; position: absolute;top:35%;left:30%;" class="orderJp none" onclick="addOrder();">您还没有添加任何数据，快去下单吧</a>
					
				</section>
				<input type="hidden" id="pageNumber" name="pageNumber" value="1">
				<input type="hidden" id="pageTotal" name="pageTotal">
				<input type="hidden" id="pageListCount" name="pageListCount">
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		//异步加载的URL地址
		var url = "${base}/admin/orderJp/listData";
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/admin/orderJp/list.js"></script>
	<!-- 公用js文件 -->
		<script src="${base}/references/public/bootstrap/js/moment.js"></script>
	<script src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/switchCardOfOrder.js"></script><!-- 订单切换卡 js -->
	<script type="text/javascript">
		var timeStart = "";
		var timeEnd = "";
		var sendDateStart = "";
		var sendDateEnd = "";
		var outDateStart = "";
		var outDateEnd = "";
		var ordersta = "";
		var days = new Date();
		days.setTime(days.getTime());
		var s = days.getFullYear()+"-" + (days.getMonth()+1) + "-" + days.getDate();

		$("#start_time").daterangepicker({
			autoapply: true,
			timepicker: false,
			startDate:s,
			endDate:s,
			locale: {
				format: "YYYY-MM-DD", //设置显示格式
				applyLabel: "确定", //确定按钮文本
				cancelLabel: '取消', //取消按钮文本
			},
		}, function(start, end, label) {
			timeStart = start.format('YYYY-MM-DD');
			timeEnd = end.format('YYYY-MM-DD');
			$("#searchbtn").click();
		});
		$("#sendSignDate").daterangepicker(null, function(start, end, label) {
			sendDateStart = start.format('YYYY-MM-DD');
			sendDateEnd = end.format('YYYY-MM-DD');
			$("#searchbtn").click();
		});
		$("#signOutDate").daterangepicker(null, function(start, end, label) {
			outDateStart = start.format('YYYY-MM-DD');
			outDateEnd = end.format('YYYY-MM-DD');
			$("#searchbtn").click();
		});

		/*清空*/
		$("#emptyBtn").click(function() {
			$("#searchStr").val("");
			$("#status").val("");
			$("#source").val("");
			$("#visaType").val("");
			$("#sendSignDate").val("");
			$("#signOutDate").val("");
			$("#start_time").val("");
			$('#pageNumber').val(1);
			timeStart = "";
			timeEnd = "";
			sendDateStart = "";
			sendDateEnd = "";
			outDateStart = "";
			outDateEnd = "";
			$("#searchbtn").click();
		});
		$("#searchbtn").click(function() {
			var status = $('#status').val();
			var source = $('#source').val();
			var visaType = $('#visaType').val();
			var sendSignDateStart = sendDateStart;
			var sendSignDateEnd = sendDateEnd;
			var signOutDateStart = outDateStart;
			var signOutDateEnd = outDateEnd;
			var searchStr = $('#searchStr').val();
			var startTimeStart = timeStart;
			var startTimeEnd = timeEnd;
			console.log(searchStr);
			$.ajax({
				url : url,
				data : {
					status : status,
					source : source,
					startTimeStart : startTimeStart,
					startTimeEnd : startTimeEnd,
					visaType : visaType,
					sendSignDateStart : sendDateStart,
					sendSignDateEnd : sendDateEnd,
					signOutDateStart : outDateStart,
					signOutDateEnd : outDateEnd,
					searchStr : searchStr
				},
				dataType : "json",
				type : 'post',
				success : function(data) {
					_self.orderJpData = data.orderJp;
				}
			});
		});
	</script>
</body>
</html>
