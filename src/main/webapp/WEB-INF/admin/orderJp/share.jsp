<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>销售 - 分享</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId{position: relative;top: 10px;}
		#datatableId tbody tr{cursor: pointer;}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">分享</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="row" id="orderremark">
						<div class="col-sm-4">
							<div class="form-group">
								<label>请选择分享方式：</label> 
								<select class="form-control input-sm selectHeight">
									<option>统一联系人</option>
									<option>单独分享</option>
								</select>
							</div>
						</div>
					</div>
					<table id="datatableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>说的不</td>
								<td>12345678987</td>
								<td>543513515@163.com</td>
							</tr>
							<tr>
								<td>说的不</td>
								<td>12345678987</td>
								<td>543513515@163.com</td>
							</tr>
							<tr>
								<td>说的不</td>
								<td>12345678987</td>
								<td>543513515@163.com</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
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
		$(function() {
			
			$("#datatableId tbody tr").click(function(){
				var isStyle = $(this).attr("style");
				if(isStyle == "color: rgb(48, 135, 240);"){//不被选中
					$(this).css("color","#333333");
				}else{//选中
					$(this).css("color","#3087f0");
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
			var applicatinfo = [];
			$('#applyinfo').find('tr').each(function(index,value){
				var applicatobj = {};
				var applicatid = $(this).find('#applicatid').val();
				applicatobj.applicatid = applicatid;
				var datatext = '';
				$(this).find('.titleStyle').each(function(index){
					datatext += $(this).text() + ',';
				});
				datatext = datatext.substring(0, datatext.length-1);
				applicatobj.datatext = datatext;
				applicatinfo.push(applicatobj);
			});
			layer.load(1);
			var orderinfo = _self.orderinfo;
			orderinfo.applicatinfo = JSON.stringify(applicatinfo);
			$.ajax({
            	url: '${base}/admin/visaJapan/saveRealInfoData.html',
            	data:orderinfo,
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
