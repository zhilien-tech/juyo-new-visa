<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>大客户后台</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
	    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
	    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	    <!-- 本页css -->
	    <link rel="stylesheet" href="${base}/references/common/css/bigCustomer.css">
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
								<th><span>订单号</span></th>
								<th><span>姓名</span></th>
								<th><span>邮箱</span></th>
								<th><span>面式领区</span></th>
								<th><span>面试时间</span></th>
								<th><span>签证结果</span></th>
								<th><span>进度</span></th>
								<th><span>操作</span></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>180410-US0003</td>
								<td>李智慧</td>
								<td>82347344@qq.com</td>
								<td>北京</td>
								<td>2018-05-01</td>
								<td>审核中</td>
								<td>办理中</td>
								<td><a style="cursor:pointer;" class="updateApplicant" ></a><a style="cursor:pointer;" class="passport" ></a><a class="visa"></a><a class="otherVisa" onclick=""></a><a style="cursor:pointer;" class="deleteIcon" ></a></td>
							</tr>
							<tr>
								<td>180410-US0002</td>
								<td>李芳</td>
								<td>3489459@qq.com</td>
								<td>上海</td>
								<td>2018-05-12</td>
								<td>审核中</td>
								<td>办理中</td>
								<td><a style="cursor:pointer;" class="updateApplicant" ></a><a style="cursor:pointer;" class="passport" ></a><a class="visa"></a><a class="otherVisa" onclick=""></a><a style="cursor:pointer;" class="deleteIcon" ></a></td>
							</tr>
							<tr>
								<td>180410-US0001</td>
								<td>王晓</td>
								<td>839675723@qq.com</td>
								<td>北京</td>
								<td>2018-05-15</td>
								<td>审核中</td>
								<td>办理中</td>
								<td><a style="cursor:pointer;" class="updateApplicant" ></a><a style="cursor:pointer;" class="passport" ></a><a class="visa"></a><a class="otherVisa" onclick=""></a><a style="cursor:pointer;" class="deleteIcon" ></a></td>
							</tr>
						</tbody>
					</table>
					<div class="row"><div class="col-sm-5"><div class="dataTables_info" id="datatableId_info" role="status" aria-live="polite">显示第 1 至 3 条结果，共 3 条 (每页显示 10 条)</div></div><div class="col-sm-7"><div class="dataTables_paginate paging_simple_numbers" id="datatableId_paginate"><ul class="pagination"><li class="paginate_button previous disabled" id="datatableId_previous"><a href="#" aria-controls="datatableId" data-dt-idx="0" tabindex="0">上页</a></li><li class="paginate_button active"><a href="#" aria-controls="datatableId" data-dt-idx="1" tabindex="0">1</a></li><li class="paginate_button next disabled" id="datatableId_next"><a href="#" aria-controls="datatableId" data-dt-idx="2" tabindex="0">下页</a></li></ul></div></div></div>
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
		<%-- <script src="${base}/admin/bigCustomer/listTable.js"></script> --%>
		<!-- 上传excel -->
		<%-- <script src="${base}/admin/bigCustomer/uploadFile.js"></script> --%>
		<!-- <script type="text/javascript">
			$(function () {
			    initDatatable();
			});
		</script> -->
	</body>
</html>