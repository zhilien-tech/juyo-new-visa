<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/flight" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>航班管理</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	  <!-- <link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css"> -->
	  <!-- <link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css"> -->
	  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
	  <!-- <script src="${base}/references/public/dist/newvisacss/js/html5shiv/html5shiv.js"></script>
	  <script src="${base}/references/public/dist/newvisacss/js/respond/respond.min.js"></script> -->
	  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
	  <link rel="stylesheet" href="${base}/references/public/css/style.css">
	  <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
				<!-- <ul class="title">
						<li>航班管理</li>
						<li class="arrow"></li>
						<li></li>
				</ul> -->
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-3 left-5px right-0px">
								<input id="searchStr" name="searchStr" type="text" class="input-sm input-class"
									placeholder="航班号/航空公司/起飞机场/降落机场" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-9 left-5px">
								<a id="searchBtn" type="button" class="btn btn-primary btn-sm pull-left" onclick="">搜索</a>
								<a id="addBtn" class="btn btn-primary btn-sm pull-right"
									onclick="add();">添加</a>
							</div>
						</div>

					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="datatableId" class="table table-hover"
							style="width: 100%;">
							<thead>
								<tr>
									<th><span>航班号</span></th>
									<th><span>航空公司</span></th>
									<th><span>起飞机场</span></th>
									<th><span>起飞机场三字代码</span></th>
									<th><span>降落机场</span></th>
									<th><span>降落机场三字代码</span></th>
									<th><span>起飞城市</span></th>
									<th><span>降落城市</span></th>
									<th><span>起飞时间</span></th>
									<th><span>降落时间</span></th>
									<th><span>起飞航站楼</span></th>
									<th><span>降落航站楼</span></th>
									<th><span>操作</span></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</section>
			<!-- /.content -->
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/base.js"></script>
	<!-- 引入DataTables JS -->
	<script src="${base}/admin/flight/listTable.js"></script>
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
			var BASE_PATH = '${base}';
			$(function () {
			    initDatatable();
			});
	</script>
</body>
</html>
