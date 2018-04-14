<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/bigCustomer" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>葆婴后台</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
	    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
	    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	    <!-- 本页css -->
	    <link rel="stylesheet" href="${base}/references/common/css/baoying.css">
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<!-- Main content -->
		<section class="content">
		
			<div class="box">
				<div class="box-header">
				
					<div class="row form-right listMain">
						<div class="col-md-4 left-5px right-0px ">
							<input id="searchStr" name="searchStr" onkeypress="onkeyEnter();"  type="text" class="searchInfo" placeholder="订单号/姓名/手机号/邮箱" />
						</div>
						<div class="col-md-1 left-5px right-0px selectJD">
							<select id="status" name="status" onchange="selectListData();" class="input-class input-sm" >
								<option value="">进度</option>
								<c:forEach var="map" items="${obj.searchStatus}">
									<option value="${map.key}">${map.value}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-1 left-5px right-0px selectFace">
							<select id="cityid" name="cityid" onchange="selectListData();" class="input-class input-sm" >
								<option value="">面试领区</option>
								<c:forEach var="map" items="${obj.cityid}">
									<option value="${map.key}">${map.value}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-2 left-5px right-0px ">
							<a id="searchBtn" class="bigBtn bigSearchBtn" >搜索</a>
						</div>
						<div class="col-md-2 left-5px right-0px ">
							<a id="emptyBtn" class="bigBtn bigSearchBtn">清空</a> 
						</div>
					</div>
					
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<!--业务要求： 葆婴  信息详情页不可编辑 -->
					<input id="disablePageInfo" type="hidden" value="${obj.isDisable}">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>订单号</span></th>
								<th><span>姓名</span></th>
								<th><span>手机号</span></th>
								<th><span>邮箱</span></th>
								<th><span>面式领区</span></th>
								<th><span>面试时间</span></th>
								<th><span>进度</span></th>
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
	
		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
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
		<script src="${base}/admin/baoying/listTable.js"></script>
		<!-- 上传excel -->
		<%-- <script src="${base}/admin/baoying/uploadFile.js"></script> --%>
		<script type="text/javascript">
			$(function () {
			    initDatatable();
			});
		</script>
	</body>
</html>