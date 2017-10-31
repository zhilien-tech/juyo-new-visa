<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>销售-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/saleJapan.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<div class="content-wrapper"  style="min-height: 848px;">
				<ul class="title">
					<li>销售</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul>
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm">
									<option>状态</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm">
									<option>客户来源</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm">
									<option>签证类型</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" placeholder="订单号/护照/公司简介/联系人/电话/邮箱/申请人" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" placeholder="2017-10-10 ~ 2017-11-11" />
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" placeholder="送签时间" />
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" placeholder="出签时间" />
							</div>
							<div class="col-md-6 left-5px">
								<a class="btn btn-primary btn-sm pull-left" onclick="" id="">搜索</a>
								<a class="btn btn-primary btn-sm pull-right" onclick="" id="addBtn">拍视频</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<a v-bind:href="url"></a>
						<div class="card-list" v-for="orderInfo in orderData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{orderInfo.orderNumb}}</span></div>	
								<div><label>订单状态：</label><span>{{orderInfo.state}}</span></div>	
								<div><label>订单人数：</label><span>{{orderInfo.number}}</span></div>	
								<div>
									<label>操作：</label>
									<i> </i>
									<i> </i>
									<i> </i>
									<i> </i>
									<i> </i>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" v-for="item in data.everybodyInfo">
									<div><label>公司简称：</label><span>{{item.comShortName}}</span></div>
									<div><label>客户来源：</label><span>直客</span></div>
									<div><label>联系人：</label><span>张三</span></div>
									<div><label>电话：</label><span>{{item.passportNo}}</span></div>
									<div><label>申请人：</label><span>张望&nbsp;锚斯&nbsp;马六</span></div>
									<div><!-- <i> </i> --></div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
		</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/admin/orderJp/listCard.js"></script>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
</body>
</html>
