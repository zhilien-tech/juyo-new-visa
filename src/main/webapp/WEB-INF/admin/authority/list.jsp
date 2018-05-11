<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/authority" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>权限管理</title>
		  <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
          <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
          <link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
          <style>
          tr th:nth-child(1) { width:15%;}
          tr th:nth-child(2) { width:55%;}
          tr th:nth-child(3) { width:15%;}
          tr th:nth-child(4) { width:15%;}
          /* td { border-right:1px dotted #eee;} */
          td span { display:inline-block; width:auto; margin:0 5px;}
          </style>
          <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
				<!-- <ul class="title">
					<li>权限管理</li>
				</ul> -->
				<section class="content">
					<div class="box">
						<div class="box-header">
						
							<div class="row form-right">
								<div class="col-md-12 left-5px">
									<a id="addBtn" class="btn btn-primary btn-sm pull-right" onclick="add();">添加</a>
								</div>
							</div>
							
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="datatableId" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>部门名称</span></th>
										<th><span>模块</span></th>
										<th><span>职位名称</span></th>
										<th><span>操作</span></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</section>
	
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
		<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
		<!-- 本页js -->
		<script src="${base}/admin/authority/listTable.js"></script>
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			$(function () {
			    initDatatable();
			});
		</script>
	</body>
</html>