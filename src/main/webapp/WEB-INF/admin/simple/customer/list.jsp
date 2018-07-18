<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/customer" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>客户管理</title>
				  <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
          <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
          <link rel="stylesheet" href="${base}/references/public/css/style.css?v=<%=System.currentTimeMillis() %>">
          <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-2 left-5px right-0px">
								<input id="searchStr" name="searchStr" type="text" class="input-sm input-class"
									placeholder="公司名称/联系人/电话" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-10 left-5px">
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
									<th><span>序号</span></th>
									<th><span>公司名称</span></th>
									<th><span>公司简称</span></th>
									<th><span>客户来源</span></th>
									<th><span>联系人</span></th>
									<th><span>支付方式</span></th>
									<th><span>手机</span></th>
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
			</section>
			<!-- /.content -->
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
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
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
			var BASE_PATH = '${base}';
			var datatable;
			function initDatatable() {
				datatable = $('#datatableId').DataTable({
					"searching":false,
					"bLengthChange": false,
					"processing": true,
					"serverSide": true,
					"stripeClasses": [ 'strip1','strip2' ],
					"language": {
						"url": BASE_PATH + "/references/public/plugins/datatables/cn.json"
					},
					"ajax": {
						"url": BASE_PATH + "/admin/simple/customer/listData.html",
						"type": "post",
						/* "data": function (d) {

			            } */
					},
					/* 列表序号 */
					"fnDrawCallback"    : function(){
						var api = this.api();
						var startIndex= api.context[0]._iDisplayStart;
						api.column(0).nodes().each(function(cell, i) {
							cell.innerHTML = startIndex + i + 1;
						});
					},

					"columns": [
					            {"data": "", "bSortable": false,render: function(data, type, row, meta) {

					            	return "";
					            } 	
					            },
					            {"data": "name", "bSortable": false,render: function(data, type, row, meta) {
					            	var name = row.name;
					            	if(null==name || ""==name){
					            		return "";
					            	}else{
					            		/*name = '<span data-toggle="tooltip" data-placement="right" title="'+name+'">'+name+'<span>';*/
					            		return name;
					            	}
					            } 	
					            },
					            {"data": "shortname", "bSortable": false,render: function(data, type, row, meta) {
					            	var shortname = row.shortname;
					            	if(null==shortname || ""==shortname){
					            		return "";
					            	}else{
					            		/*shortname = '<span data-toggle="tooltip" data-placement="right" title="'+shortname+'">'+shortname+'<span>';*/
					            		return shortname;
					            	}
					            } 	
					            },
					            {"data": "source", "bSortable": false,render: function(data, type, row, meta) {
					            	var source = row.source;
					            	if(null==source || ""==source){
					            		return "";
					            	}else{
					            		/*source = '<span data-toggle="tooltip" data-placement="right" title="'+source+'">'+source+'<span>';*/
					            		if(source == 1){
					            			return "线上";
					            		}
					            		if(source == 2){
					            			return "OTS";
					            		}
					            		if(source == 3){
					            			return "直客";
					            		}
					            		if(source == 4){
					            			return "线下";
					            		}
					            		
					            	}
					            } 	
					            },
					            {"data": "linkman", "bSortable": false,render: function(data, type, row, meta) {
					            	var linkman = row.linkman;
					            	if(null==linkman || ""==linkman){
					            		return "";
					            	}else{
					            		/*linkman = '<span data-toggle="tooltip" data-placement="right" title="'+linkman+'">'+linkman+'<span>';*/
					            		return linkman;
					            	}
					            } 	
					            },
					            {"data": "paytypestr", "bSortable": false,render: function(data, type, row, meta) {
					            	var paytypestr = row.paytypestr;
					            	if(null==paytypestr || ""==paytypestr){
					            		return "";
					            	}else{
					            		/*linkman = '<span data-toggle="tooltip" data-placement="right" title="'+linkman+'">'+linkman+'<span>';*/
					            		return paytypestr;
					            	}
					            } 	
					            },
					            {"data": "mobile", "bSortable": false,render: function(data, type, row, meta) {
					            	var mobile = row.mobile;
					            	if(null==mobile || ""==mobile){
					            		return "";
					            	}else{
					            		/*mobile = '<span data-toggle="tooltip" data-placement="right" title="'+mobile+'">'+mobile+'<span>';*/
					            		return mobile;
					            	}
					            } 	
					            },
					            {"data": "email", "bSortable": false,render: function(data, type, row, meta) {
					            	var email = row.email;
					            	if(null==email || ""==email){
					            		return "";
					            	}else{
					            		/*email = '<span data-toggle="tooltip" data-placement="right" title="'+email+'">'+email+'<span>';*/
					            		return email;
					            	}
					            } 	
					            },
					            {"data": " ", "bSortable": false, "width":120,
					            	render: function(data, type, row, meta) {
					            		var modify = '<a title="编辑" style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
					            		//var judge = '<a title="删除" style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
					            		//return modify+judge;
					            		return modify;
					            	}	
					            } 
					            ],
					            columnDefs: [{}]
				});
			}
			$("#searchBtn").on('click',function(){
				var searchStr = $("#searchStr").val();
				var param = {
					"searchStr": searchStr,
				};
				datatable.settings()[0].ajax.data = param;
				datatable.ajax.reload();
			});
			function onkeyEnter(){
			    var e = window.event || arguments.callee.caller.arguments[0];
			    if(e && e.keyCode == 13){
					 $("#searchBtn").click();
				 }
			}

			/* layer添加 */
			function add(){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['800px', '80%'],
					content: BASE_PATH + '/admin/simple/customer/add.html?isCustomerAdd=1'
				});
			}
			/* layer编辑 */
			function edit(id){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['800px', '80%'],
					content: BASE_PATH + '/admin/simple/customer/update.html?id='+id
				});
			}


			$(function () {
			    initDatatable();
			});
			function successAddCustomer(){
				self.datatable.ajax.reload()
			}
		</script>
</body>
</html>
