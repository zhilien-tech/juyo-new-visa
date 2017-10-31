<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证日本-实收</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">实收资料</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
								<th><span>资料类型</span></th>
								<th><span>真实资料</span></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>张三四</td>
								<td>15132008217</td>
								<td>zahngsan@163.com</td>
								<td>退休</td>
								<td class="certificates">
									<span>护照</span>
									<span>身份证</span>
									<span>一寸照片</span>
									<span>退休证明</span>
									<span>健康证明</span>
									<input id="" name="" type="text" class="addInp none">
									<span class="addText">+</span>
								</td>
							</tr>
							<tr>
								<td>张三四</td>
								<td>15132008217</td>
								<td>zahngsan@163.com</td>
								<td>退休</td>
								<td class="certificates">
									<span>学生证</span>
									<span>健康证明</span>
									<input id="" name="" type="text" class="addInp none">
									<span class="addText">+</span>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label>备注：</label> 
								<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			$('#datatableId').DataTable({
				"autoWidth":true,
				"ordering": false,
				"searching":false,
				"bLengthChange": false,
				"processing": true,
				"serverSide": true,
				"stripeClasses": [ 'strip1','strip2' ],
				"language": {
					"url": BASE_PATH + "/references/public/plugins/datatables/cn.json"
				}
			});
			
			/*点击表格中的加号标签*/
			$(".addText").click(function(){
			    /* if($(".addInp").hasClass("none")){//input框隐藏时
					$(".addInp").removeClass("none");
				}else{//input框显示时
					var inputVal = $(".addInp").val();
					if(inputVal!=null&&inputVal!=""){
						$(".addInp").before('<span>'+ inputVal +'</span>');//在input前面 添加span标签
						$(".addInp").val("");
					}
				} */ 
				$(this).siblings(".addInp").removeClass("none");
				var inputVal = $(this).siblings(".addInp").val();
				if(inputVal != null && inputVal != ""){
					$(this).siblings(".addInp").before('<span>'+ inputVal +'</span>');//在input前面 添加span标签
					$(this).siblings(".addInp").val("");
				}
			});
		});


		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>


</body>
</html>
