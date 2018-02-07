<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/personalInfo" />
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>个人资料</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
			<section class="content">
				<div class="box">
					<div class="box-header">

						<div class="row form-right">
							<div class="col-md-12 left-5px">
								<a id="" class="btn btn-primary btn-sm pull-right" onclick="updatePassword();" style=" width:70px !important;">修改密码</a>
								<a id="" class="btn btn-primary btn-sm pull-right" onclick="updateInfo();" style="margin-right: 15px;">编辑</a>
							</div>
						</div>

					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="" class="table table-hover" style="width: 100%;">
							<tbody>
								<tr>
									<td>用户名称</td>
									<td id="name">${obj.name}</td>
								</tr>
								<tr>
									<td>用户名/手机号码</td>
									<td id="mobile">${obj.mobile}</td>
								</tr>
								<tr>
									<td>联系QQ</td>
									<td id="qq">${obj.qq}</td>
								</tr>
								<tr>
									<td>电子邮箱</td>
									<td id="email">${obj.email}</td>
								</tr>
								<tr>
									<td>所属部门</td>
									<%-- <td id="deptname">${obj.deptname}</td> --%>
									<td id="deptname">${obj.usertype eq 1 ? obj.deptname :""}</td>
								</tr>
								<tr>
									<td>用户职位</td>
									<%-- <td id="jobname">${obj.jobname}</td> --%>
									<td id="jobname">${obj.usertype eq 1 ? obj.jobname :""}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</section>
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
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		//刷新个人信息
		function personalInfo(){
			$.ajax({
				type : 'POST',
				url : '${base}/admin/personalInfo/personalInfo.html',
				success : function(resp) {
					var name = resp.name;
					if(null == name){
						name = "";
					}
					var qq = resp.qq;
					if(null == qq){
						qq = "";
					}
					var email = resp.email;
					if(null == email){
						email = "";
					}
					$("#name").text(name);
					$("#qq").text(qq);
					$("#email").text(email);
				},
				error : function(xhr) {
					layer.msg("编辑失败", "", 3000);
				}
			});
		}
		
		function updateInfo(){
			 layer.open({
		    	    type: 2,
		    	    title: false,
		    	    closeBtn:false,
		    	    fix: false,
		    	    maxmin: false,
		    	    shadeClose: false,
		    	    scrollbar: false,
		    	    area: ['900px', '80%'],
		    	    content: BASE_PATH + '/admin/personalInfo/update.html'
		    	  });
		}
		
		function updatePassword(){
			 layer.open({
		    	    type: 2,
		    	    title: false,
		    	    closeBtn:false,
		    	    fix: false,
		    	    maxmin: false,
		    	    shadeClose: false,
		    	    scrollbar: false,
		    	    area: ['900px', '80%'],
		    	    content: BASE_PATH + '/admin/personalInfo/updatePassword.html'
		    	  });
		}
		
	</script>
</body>
</html>
