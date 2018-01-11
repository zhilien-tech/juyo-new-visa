<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>初审-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/firstTrialJp.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
    <link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css"><!-- 订单切换卡 样式 -->
    <style>
     [v-cloak]{display:none;}
	 .bold { font-weight:bold;font-size:16px;}
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header">
						<!-- 切换卡按钮 start -->
						<div class="btnGroups">
							<a onclick="searchAllOrder()" class="btnList bgColor">全部</a>
							<a onclick="searchMyOrder(2)" class="btnList">我的</a>
						</div>
						<!-- 切换卡按钮 end -->
						<!-- 检索条件 start -->
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
						<!-- end 检索条件 -->
					</div>
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in trialJapanData">
							<div class="card-head">
								<div><label>订单号：</label><span style="cursor:pointer" v-on:click="visaDetail(data.orderid,data.orderjpid)">{{data.ordernumber}}</span></div>	
								<div><label>出行时间：</label><span>{{data.gotriptime}}</span></div>	
								<div><label>返回时间：</label><span>{{data.backtriptime}}</span></div>	
								<div style="position:absolute;right:25%;"><label></label><span class="bold">{{data.orderstatus}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.orderid,data.orderjpid)"> </i>
									<i class="express" @click="expressFun(data.orderid,data.orderjpid)"> </i>
									<!-- <i class="return"> </i> -->
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" v-for="(item,index) in data.everybodyinfo">
								
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>申请人：</label><span>{{item.applicantname}}</span></div>
										<div><label>护照号：</label><span>{{item.passportnum}}</span></div>
										<div><label>手机号：</label><span>{{item.telephone}}</span></div>
										<div><label>状态：</label><span>{{item.applicantstatus}}</span></div>
										<div>
											<i class="basicInfo" @click="basicInfoFun(item.applyid,data.orderid)"> </i>
											<i class="passportInfo" @click="passportFun(item.applyid,data.orderid)"> </i>
											<i class="visaInfo" @click="visaInfoFun(item.applyid,data.orderid)"> </i>
											<i class="qualified" @click="qualifiedFun(item.applyid,data.orderid,data.orderjpid)"> </i>
											<i class="unqualified" @click="unqualifiedFun(item.applyid,data.orderid)"> </i>
										</div>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label>　　　　</label><span>{{item.applicantname}}</span></div>
										<div><label>　　　　</label><span>{{item.passportnum}}</span></div>
										<div><label>　　　　</label><span>{{item.telephone}}</span></div>
										<div><label>　　　</label><span>{{item.applicantstatus}}</span></div>
										<div>
											<i class="basicInfo" @click="basicInfoFun(item.applyid,data.orderid)"> </i>
											<i class="passportInfo" @click="passportFun(item.applyid,data.orderid)"> </i>
											<i class="visaInfo" @click="visaInfoFun(item.applyid,data.orderid)"> </i>
											<i class="qualified" @click="qualifiedFun(item.applyid,data.orderid,data.orderjpid)"> </i>
											<i class="unqualified" @click="unqualifiedFun(item.applyid,data.orderid)"> </i>
										</div>
									</span>
								
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>

			<input type="hidden" id="pageNumber" name="pageNumber" value="1">
			<input type="hidden" id="pageTotal" name="pageTotal">
			<input type="hidden" id="pageListCount" name="pageListCount">


	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/base/cardList.js"></script>
	<script src="${base}/admin/firstTrialJp/trialList.js"></script><!-- 本页面js文件 -->
	<script src="${base}/references/common/js/switchCardOfOrder.js"></script><!-- 订单切换卡 js -->
	<script type="text/javascript">
		function searchMyOrder(orderProcessType){
			
		}
	</script>
</body>
</html>
