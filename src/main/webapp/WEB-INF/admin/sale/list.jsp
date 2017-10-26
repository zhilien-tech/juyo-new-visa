<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>销售-日本</title>
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
						<div class="row form-right">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm">
									<option>状态</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" placeholder="订单号/联系人/电话/邮箱/申请人" />
							</div>
							<div class="col-md-7 left-5px">
								<a class="btn btn-primary btn-sm pull-left" onclick="" id="">搜索</a>
								<a class="btn btn-primary btn-sm pull-right" onclick="" id="addBtn">拍视频</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-for="data in orderData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.orderNumber}}</span></div>	
								<div><label>订单状态：</label><span>{{data.orderState}}</span></div>	
								<div><label>订单人数：</label><span>{{data.number}}</span></div>	
								<div>
									<label>操作：</label>
									<i> </i>
									<i> </i>
									<i> </i>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" v-for="item in data.everybodyInfo">
									<div><label>申请人：</label><span>{{item.applicant}}</span></div>
									<div><label>护照号：</label><span>{{item.passportNo}}</span></div>
									<div><label>快递号：</label><span>316252136</span></div>
									<div><label>方式：</label><span>门市送</span></div>
									<div><label>资料类型：</label><span>在职</span></div>
									<div><label>真实资料：</label><span>护照&nbsp;身份证&nbsp;一寸照片&nbsp;退休证明&nbsp;学生证&nbsp;健康证明</span></div>
									<div><label>状态：</label><span>{{item.state}}</span></div>
									<div><i> </i></div>
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
	<script src="${base}/admin/sale/listCard.js"></script>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
</body>
</html>
