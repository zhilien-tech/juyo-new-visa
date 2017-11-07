<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/company" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>公司管理</title>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper"  style="min-height: 848px;">
				<!-- <ul class="title">
						<li>公司管理</li>
						<li class="arrow"></li>
						<li></li>
				</ul> -->
				<section class="content">
					<div class="box">
						<div class="box-header">
						
							<div class="row form-right">
								<div class="col-md-2 left-5px right-0px">
									<select id="comType" name="comType" class="form-control input-sm inpImportant" onchange="selectListData();">
										<option value="">请选择</option>
										<c:forEach var="map" items="${obj.companyTypeEnum}">
											<option value="${map.key}">${map.value}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-3 left-5px right-0px">
									<input id="searchStr" name="searchStr" type="text" class="input-sm input-class" placeholder="公司全称/用户名/联系人/电话/邮箱" onkeypress="onkeyEnter();"/>
								</div>
								<!-- <div class="col-md-3 left-5px right-0px">
									<input id="" name="" type="text" class="input-sm input-class picker" onClick="WdatePicker()"/>
									<span class="picker-span">至</span>
									<input id="" name="" type="text" class="input-sm input-class picker" onClick="WdatePicker()"/>
								</div> -->
								<div class="col-md-7 left-5px">
									<a id="searchBtn" type="button" class="btn btn-primary btn-sm pull-left">搜索</a>
									<a id="emptyBtn" onclick="emptySearch();" class="btn btn-primary btn-sm pull-left">清空</a> 
									<a id="addBtn" class="btn btn-primary btn-sm pull-right" onclick="add();">添加</a>
								</div>
							</div>
							
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="datatableId" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>公司全称</span></th>
										<th><span>公司简称</span></th>
										<th><span>用户名</span></th>
										<th><span>公司类型</span></th>
										<th><span>经营范围</span></th>
										<th><span>联系人</span></th>
										<th><span>电话</span></th>
										<th><span>邮箱</span></th>
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
			</div><!-- /.content-wrapper -->
	
			<!-- Main Footer -->
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
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
		<script src="${base}/admin/company/listTable.js"></script>
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			$(function () {
			    initDatatable();
			});
		</script>
	</body>
</html>
