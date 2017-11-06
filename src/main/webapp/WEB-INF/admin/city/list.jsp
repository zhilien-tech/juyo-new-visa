<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/city" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>城市管理</title>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper"  style="min-height: 848px;">
				<ul class="title">
						<li>城市管理</li>
				</ul>
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-1 left-5px right-0px">
								<select  id="country" name="country" class="form-control input-sm inpImportant" onchange="countryChange();">
									<option value="">国家</option>
									<c:forEach var="map" items="${obj.country}">
											<option value="${map.value}">${map.value}</option>
										</c:forEach>
								</select>
							</div>
							<div class="col-md-1 left-5px right-0px">
								<select  id="province" name="province" class="form-control input-sm inpImportant" onchange="provinceChange();">
									<option value="">省/州/县</option>
									<%-- <c:forEach var="map" items="${obj.province}">
											<option value="${map.value}">${map.value}</option>
										</c:forEach> --%>
								</select>
							</div>
							<div class="col-md-1 left-5px right-0px">
								<select  id="city" name="city" class="form-control input-sm inpImportant" onchange="cityChange();">
									<option value="">城市</option>
									<%-- <c:forEach var="map" items="${obj.city}">
											<option value="${map.value}">${map.value}</option>
										</c:forEach> --%>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input id="searchStr" name="searchStr" type="text" class="input-sm input-class"
									placeholder="国家/省/城市" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-7 left-5px">
								<a id="searchBtn" class="btn btn-primary btn-sm pull-left">搜索</a> <a id="addBtn"
									class="btn btn-primary btn-sm pull-right" onclick="add();">添加</a>
							</div>
						</div>

					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="datatableId" class="table table-hover"
							style="width: 100%;">
							<thead>
								<tr>
									<th><span>国家</span></th>
									<th><span>省/州/县</span></th>
									<th><span>城市</span></th>
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
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>

	</div>
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
	<script src="${base}/admin/city/listTable.js"></script>
	<script type="text/javascript">
	
		var BASE_PATH = '${base}';
		$(function() {
			initDatatable();
			  /*var country = $("#country").val();
			/*加载国家下拉选 
		    $(function () {  
		        $.ajax({  
		            type: "post",  
		            url: BASE_PATH + "/admin/city/list",  
		            success: function (data) { 
		            	$('#country').append("<option value=''  >" + '国家' + "</option>");
		            	$('#province').append("<option value=''  >" + '省/州/县' + "</option>");  
		                $('#city').append("<option value='' selected='selected' >" + '城市' + "</option>");
		                for (var i = 0; i < data.length; i++) {  
		                    $('#country').append("<option value='" + data[i] + "' >" + data[i] + "</option>");  
		                }  
		            },  
		            error: function () {  
		                alert("加载国家失败");  
		            }  
		        });  
		    }); */   
		      
		});
		/*加载省下拉选*/  
	     $("#country").change(function () {
	    	 $("#searchBtn").click();
		        $("#province").empty();  
		        $("#city").empty();  
		        var country = $("#country").val();
		        $.ajax({  
		            type: "post",  
		            url: BASE_PATH + "/admin/city/getProvince",  
		            data: {
		            	country: country
		            	},
		            success: function (data) {  
		                $('#province').append("<option value=''  >" + '省/州/县' + "</option>");  
		                $('#city').append("<option value=''  >" + '城市' + "</option>");  
		                for (var i = 0; i < data.length; i++) {  
		                    $('#province').append("<option value='" + data[i] + "' >" + data[i] + "</option>");  
		                }
		            },  
		            error: function () {  
		                alert("加载省/州/县失败");  
		            }  
		        });
	     });
	    /*加载城市下拉选*/  
	    $("#province").change(function () {
	    	var province = $("#province").val();
	        //$("#city").empty();  
	    	
	        $.ajax({  
	            type: "post",  
	            url: BASE_PATH + "/admin/city/getCity",  
	            data: {province: province},  
	            success: function (data) {
	            	var str = '<option value="" >城市</option>';
	                //$('#city').append("<option value=''  >" + '城市' + "</option>");  
	                for (var i = 0; i < data.length; i++) {  
	                    //$('#city').append("<option value='" + data[i] + "'  >" + data[i] + "</option>");  
	                    str += '<option value="' + data[i] + '"  >' + data[i] + '</option>';  
	                }  
	                $('#city').html(str);
	            },  
	            error: function () {  
	                alert("加载城市失败");  
	            }
	        });
	        $("#city").val('');
	    	$("#searchBtn").click();
	    });
	</script>
</body>
</html>
