<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>销售 - 日志</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
	</style>
</head>
<body>
	<div class="modal-content">
			<div class="modal-header">
				<span class="heading">分享</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="关闭" /> 
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>时间</span></th>
								<th><span>状态</span></th>
								<th><span>操作人</span></th>
							</tr>
						</thead>
						<tbody >
							<tr v-for="orderin in orderinfo">
								<td>{{orderin.comId }}</td>
								<td>{{orderin.status}}</td>
								<td>{{orderin.userId }}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";

		//vue表格数据对象
	    var _self;
		new Vue({
			el: '#datatableId',
			data: {orderinfo:""},
			created:function(){
	            _self=this;
	            var orderid = '${obj.orderid}';
	            $.ajax({ 
	            	url: '${base}/admin/orderJp/getLogs.html',
	            	data:{orderid:orderid},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.orderinfo = data;
	            		console.log(JSON.stringify(_self.orderinfo));
	              	}
	            });
	        }
		});
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>
