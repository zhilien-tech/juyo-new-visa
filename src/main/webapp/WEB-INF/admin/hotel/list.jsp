<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/hotel" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>酒店管理</title>
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
  <link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
  <link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
  <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
			<!-- <ul class="title">
				<li>酒店管理</li>
				<li class="arrow"></li>
				<li></li>
			</ul> -->
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-2 left-5px right-0px">
								<input id="hotelSearch" name="hotelSearch" type="text" class="input-sm input-class"
									placeholder="名称中文/名称原文/电话/城市" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-10 left-5px">
								<a id="searchBtn" type="button" class="btn btn-primary btn-sm pull-left" >搜索</a>
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
									<th><span>酒店名称(中文)</span></th>
									<th><span>酒店名称(原文)</span></th>
									<th><span>酒店地址(中文)</span></th>
									<th><span>酒店地址(原文)</span></th>
									<th><span>电话</span></th>
									<th><span>所属城市</span></th>
									<th><span>所属区域</span></th>
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
	<script
		src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script
		src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/base.js"></script>
	<!-- 引入DataTables JS -->
	<script src="${base}/admin/hotel/listTable.js"></script>
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		$(function() {
			initDatatable();
		});
	</script>
</body>
</html>
