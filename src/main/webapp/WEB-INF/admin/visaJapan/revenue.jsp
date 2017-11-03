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
							<c:forEach items="${obj.applicant }" var="apply">
								<tr>
									<td>${apply.applicant }</td>
									<td>${apply.telephone }</td>
									<td>${apply.email }</td>
									<td>${apply.dataType }</td>
									<td class="certificates">
										<c:forEach items="${apply.revenue }" var="revenue">
											<span>${revenue.realInfo }</span>
											
										</c:forEach>
										<input id="" name="" type="text" class="addInp none">
										<span class="addText">+</span>
										<input type="hidden" id="applicatid" name="applicatid" value="${apply.applicatid }">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="row" id="orderremark">
						<div class="col-sm-12">
							<div class="form-group">
								<label>备注：</label> 
								<input id="remark" name="remark" type="text" class="form-control input-sm" v-model="orderinfo.remark"/>
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
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			/* $('#datatableId').DataTable({
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
			}); */
			
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
				var applicatid = $(this).parent().find('#applicatid').val();
				var inputVal = $(this).siblings(".addInp").val();
				if(inputVal != null && inputVal != ""){
					$(this).siblings(".addInp").before('<span>'+ inputVal +'</span>');//在input前面 添加span标签
					$(this).siblings(".addInp").val("");
					$.ajax({ 
		            	url: '${base}/admin/visaJapan/saveApplicatRevenue.html',
		            	data:{applicatid:applicatid,realInfo:inputVal},
		            	dataType:"json",
		            	type:'post',
		            	success: function(data){
		            		
		              	}
		            });
				}
			});
		});

		//vue表格数据对象
	    var _self;
		new Vue({
			el: '#orderremark',
			data: {orderinfo:""},
			created:function(){
	            _self=this;
	            var orderid = '${obj.orderid}';
	            $.ajax({ 
	            	url: '${base}/admin/visaJapan/visaRevenue.html',
	            	data:{orderid:orderid},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.orderinfo = data.orderinfo;
	            		console.log(JSON.stringify(_self.orderinfo));
	              	}
	            });
	        }
		});
		//保存
		function save(){
			layer.load(1);
			$.ajax({
            	url: '${base}/admin/visaJapan/saveRealInfoData.html',
            	data:_self.orderinfo,
            	dataType:"json",
            	type:'post',
            	success: function(data){
					layer.closeAll('loading');
					parent.successCallBack(1);
					closeWindow();

              	}
            });
		}

		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
