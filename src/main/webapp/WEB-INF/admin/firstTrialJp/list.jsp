<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>初审-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/firstTrialJp.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<div class="content-wrapper" style="min-height: 848px;">
				<!-- <ul class="title">
					<li>初审</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul> -->
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row"> 
							<div class="col-md-2 left-5px right-0px">
								<select id="status" name="status" onchange="selectListData();" class="input-class input-sm" >
									<option value="">状态</option>
									<c:forEach var="map" items="${obj.searchStatus}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input id="searchStr" name="searchStr" onkeypress="onkeyEnter();" type="text" class="input-sm input-class"  placeholder="订单号/护照号/电话/申请人" />
							</div>
							<div class="col-md-6 left-5px">
								<a id="searchBtn" class="btn btn-primary btn-sm pull-left">搜索</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in trialJapanData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.ordernumber}}</span></div>	
								<div><label>出行时间：</label><span>{{data.gotriptime}}</span></div>	
								<div><label>返回时间：</label><span>{{data.backtriptime}}</span></div>	
								<div><label>状态：</label><span>{{data.orderstatus}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.orderid,data.orderjpid)"> </i>
									<i class="express" @click="expressFun(data.orderid,data.orderjpid)"> </i>
									<i class="return"> </i>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" v-for="item in data.everybodyinfo">
									<div><label>申请人：</label><span>{{item.applicantname}}</span></div>
									<div><label>护照号：</label><span>{{item.passportnum}}</span></div>
									<div><label>手机号：</label><span>{{item.telephone}}</span></div>
									<div><label>状态：</label><span>{{item.applicantstatus}}</span></div>
									<div>
										<i class="basicInfo" @click="basicInfoFun(item.applyid)"> </i>
										<i class="passportInfo" @click="passportFun(item.applyid)"> </i>
										<i class="visaInfo" @click="visaInfoFun(item.applyid,data.orderjpid)"> </i>
										<i class="qualified" @click="qualifiedFun(item.applyid,data.orderid,data.orderjpid)"> </i>
										<i class="unqualified" @click="unqualifiedFun(item.applyid,data.orderid)"> </i>
									</div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
		</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/base/cardList.js"></script>
	<script src="${base}/admin/firstTrialJp/trialList.js"></script><!-- 本页面js文件 -->
</body>
</html>
