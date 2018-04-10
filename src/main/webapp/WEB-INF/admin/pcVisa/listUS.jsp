<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>美国列表页</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
    <!-- 加载中。。。样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
	<!-- 本页样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/viasDetailUS.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<!-- 切换卡按钮 start -->
						<div class="btnGroups">
							<a name="myOrder"  class="searchOrderBtn btnList bgColor">我的</a>
							<a name="allOrder"  class="searchOrderBtn btnList">全部</a>
						</div>
						<div class="row searchMar">
							<div class="col-md-2 left-5px right-0px">
								<select id="status" name="status" onchange="selectListData();" class="input-class input-sm" >
									<option value="">状态</option>
									<c:forEach var="map" items="${obj.searchStatus}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select id="cityid" name="cityid" onchange="selectListData();" class="input-class input-sm" >
									<option value="">领区</option>
									<c:forEach var="map" items="${obj.cityid}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select id="ispayed" name="ispayed" onchange="selectListData();" class="input-class input-sm" >
									<option value="">是否付款</option>
									<c:forEach var="map" items="${obj.ispayed}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/AA码/申请人/护照号/电话/邮箱" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-1 left-5px">
								<a class="btn btn-primary btn-sm pull-left"  id="searchBtn">搜索</a>
							</div>
							<div class="col-md-1 left-5px">
								<a id="emptyBtn" class="btn btn-primary btn-sm pull-left">清空</a> 
							</div>
							<div class="col-md-1 left-5px pull-right">
								<a class="btn btn-primary btn-sm"  id="">下单</a>
							</div>
						</div>
					</div>
					<!-- end 检索条件 -->
					<!-- 卡片列表 -->
					<div class="box-body" id="card">
						<div class="card-list"  v-cloak v-for="data in orderUSData"><!-- v-cloak v-for="data in receptionJpData" -->
							<div class="card-head cf">
								<div><label>订单号：</label><span>{{data.ordernumber}}</span></div>
								<div><label>领区：</label><span></span></div>
								<div><label>面试时间：</label><span></span></div>
								<div><label>是否付款：</label><span>未付款</span></div>
								<div><label></label><span></span>
								</div>
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="order(data.ordernumber,data.staffid,data.id)" > </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf">
									<span>
										<div><label>申请人：</label><span>{{data.staffname}}</span></div>
										<div><label>AA码：</label><span></span></div>
										<div><label>手机号：</label><span>{{data.telephone}}</span></div>
										<div><label>护照号：</label><span></span></div>
										<div><label>资料类型：</label><span></span></div>
										<div class="whiteSpace"><label>资料：</label><span class="showInfo"></span></div>
										<span class="hideInfo"></span>
										<div><span></span></div>
									</span>
								</li>
							</ul>
						</div>
					</div>
					<!-- end 卡片列表 -->
				</section>
<script type="text/javascript">
	var BASE_PATH = '${base}';
	//异步加载的URL地址
	var url = "${base}/admin/pcVisa/listDetailUSData";
</script>
<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>		
<script src="${base}/references/common/js/base/baseIcon.js"></script>
<script src="${base}/references/common/js/layer/layer.js"></script>
<script src="${base}/references/common/js/vue/vue.min.js"></script>
<script src="${base}/admin/pcVisa/listUS.js"></script>
<script>
//资料鼠标移入事件
$(document).on('mouseover','.showInfo',function(){
	
	let text = $(this).html();
	$(this).parent().next().show();
	$(this).parent().next().html(text);
});
//资料鼠标移出事件
$(document).on('mouseleave','.showInfo',function(){
	$(".hideInfo").hide();
});

$(function(){
	$(".btnList").click(function(){
		$(this).addClass('bgColor').siblings().removeClass('bgColor');
		clearSearchEle();
		$("#searchBtn").trigger("click");
	})
	
});
	
	
	function clearSearchEle(){
		//检索框
		$("#status").val("");
		$("#searchStr").val("");
		$("#cityid").val("");
		$("#ispayed").val("");
		//分页项
		$("#pageNumber").val(1);
		$("#pageTotal").val("");
		$("#pageListCount").val("");
	}
	
	/*清空*/
	$("#emptyBtn").click(function() {
		$("#searchStr").val("");
		$("#status").val("");
		$("#cityid").val("");
		$("#ispayed").val("");
		$('#pageNumber').val(1);
		$("#searchbtn").click();
	});

</script>	
</body>
</html>
