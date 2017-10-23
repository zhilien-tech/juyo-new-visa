<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/function" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>功能管理</title>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper"  style="min-height: 848px;">
				<!-- Main content -->
				<section class="content">
				
					<ul class="title">
						<li>功能管理</li>
						<!-- <li class="arrow"></li>
						<li></li> -->
					</ul>
				
					<div class="box">
						<div class="box-header">
						
							<div class="row form-right">
								<div class="col-md-2 left-5px right-0px">
									<select id="funId" name="funId" onchange="selectFunName();" class="form-control input-sm">
										<option value="-1">全部</option>
										<c:forEach items="${obj.functions}" var="pro">
											<c:choose>
												<c:when test="${pro.id == obj.parentId}">
													<option value="${pro.id}" selected="selected">${pro.funName}</option>
												</c:when>
												<c:otherwise>
													<option value="${pro.id}">${pro.funName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-2 left-5px right-0px">
									<input id="searchStr" name="searchStr" onkeypress="onkeyEnter();" type="text" class="input-sm input-class" placeholder="功能名称" />
								</div>
								<!-- <div class="col-md-3 left-5px right-0px">
									<input id="" name="" type="text" class="input-sm input-class picker" onClick="WdatePicker()"/>
									<span class="picker-span">至</span>
									<input id="" name="" type="text" class="input-sm input-class picker" onClick="WdatePicker()"/>
								</div> -->
								<div class="col-md-5 left-5px">
									<a id="searchBtn" class="btn btn-primary btn-sm pull-left">搜索</a>
									<a id="addBtn" class="btn btn-primary btn-sm pull-right" onclick="add();">添加</a>
								</div>
							</div>
							
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="datatableId" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>上级功能</span></th>
										<th><span>访问地址</span></th>
										<th><span>功能名称</span></th>
										<th><span>功能等级</span></th>
										<th><span>公司类型</span></th>
										<th><span>经营范围</span></th>
										<th><span>创建时间</span></th>
										<th><span>备注</span></th>
										<th><span>序号</span></th>
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
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
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
		<script src="${base}/admin/function/listTable.js"></script>
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			$(function () {
			    initDatatable();
			});
		</script>
	</body>
</html>