<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>大客户后台</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
	    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
	    <link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
	    <!-- 本页css -->
	    <link rel="stylesheet" href="${base}/references/common/css/bigCustomer.css?v='20180510'">
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<!-- Main content -->
		<section class="content">
		
			<div class="box">
				<div class="box-header">
				
					<div class="row form-right listMain">
						<div class="col-md-2 left-5px right-0px groupLeft">
							<input id="searchStr" name="searchStr" onkeypress="onkeyEnter();"  type="text" class="searchInfo" placeholder="姓名/电话/部门/职位" />
							<a id="searchBtn" class="bigBtn bigSearchBtn" onclick="">搜索</a>
						</div>
						<div class="col-md-5 left-5px groupRight">
							<a id="addBtn" class="bigBtn bigAddBtn" onclick="add();">添加</a>
							<a class="bigBtn bigUploadBtn">
								上传
								<input id="uploadFile" name="uploadFile" class="" type="file" value="" />
							</a>
							
							<a id="downloadBtn" href="${obj.downloadurl }" class="bigBtn bigDownloadBtn" >模板下载</a>
						</div>
					</div>
					
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>序号</span></th>
								<th><span>姓名</span></th>
								<th><span>手机号</span></th>
								<th><span>邮箱</span></th>
								<th><span>部门</span></th>
								<th><span>职位</span></th>
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
		</section><!-- /.content -->
	
		<!-- jQuery 3.2.1 -->
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<!-- Bootstrap 3.3.6 -->
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<!-- DataTables -->
		<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/upload/jquery.uploadify.min.js"></script>
		<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
		<!-- 公用js文件 -->
		<script src="${base}/references/common/js/base/base.js"></script>
		<!-- 引入DataTables JS -->
		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
		<script src="${base}/admin/bigCustomer/listTable.js"></script>
		<!-- 上传excel -->
		<script src="${base}/admin/bigCustomer/uploadFile.js"></script>
		<script type="text/javascript">
			$(function () {
			    initDatatable();
			});
		</script>
	</body>
</html>