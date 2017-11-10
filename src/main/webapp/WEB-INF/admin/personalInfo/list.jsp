<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/personalInfo" />
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>个人资料</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" style="min-height: 848px;">
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-12 left-5px">
								<a id="" class="btn btn-primary btn-sm pull-right" onclick="">修改密码</a>
								<a id="" class="btn btn-primary btn-sm pull-right" onclick="">编辑</a>
							</div>
						</div>

					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="" class="table table-hover" style="width: 100%;">
							<tbody>
								<tr>
									<td>用户名称</td>
									<td>abc</td>
								</tr>
								<tr>
									<td>用户名/手机号码</td>
									<td>6666</td>
								</tr>
								<tr>
									<td>座机号码</td>
									<td>010-5236212</td>
								</tr>
								<tr>
									<td>联系QQ</td>
									<td>123456678</td>
								</tr>
								<tr>
									<td>电子邮箱</td>
									<td>1313131313@163.com</td>
								</tr>
								<tr>
									<td>所属部门</td>
									<td>公司管理部</td>
								</tr>
								<tr>
									<td>用户职位</td>
									<td>公司管理员</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</section>
		</div>

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
	<%-- <script src="${base}/admin/hotel/listTable.js"></script> --%>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		$(function() {
			initDatatable();
		});
	</script>
</body>
</html>
