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
		.trColor{color: rgb(48, 135, 240)}
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">分享</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="确定" />
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="row" id="orderremark">
						<div class="col-sm-4">
							<div class="form-group">
								<label>请选择分享方式：</label> 
								<select id="shareType" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.shareTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<table id="datatableId" class="table table-hover" style="width:100%;" >
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="data in shareInfo" class="tableTr">
								<td style="display: none">{{data.id}}</td>
								<td>{{data.applyname}}</td>
								<td>{{data.telephone}}</td>
								<td>{{data.email}}</td>
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
			$("#addBtn").attr('disabled', true);
			$(document).on("click",".tableTr",function(){
				var sharetype = $("#shareType").val();
				if(sharetype == 2){//单独分享
					if($(this).hasClass("trColor")){
						$(this).removeClass("trColor");
					}else{
						$(this).addClass("trColor");
					}
				}else if(sharetype == 1){//统一联系人
					$(this).addClass("trColor").siblings("tr").removeClass("trColor");
				}
				
				if($(this).hasClass("trColor")){
					$("#addBtn").attr('disabled', false);
				}else{
					$("#addBtn").attr('disabled', true);
				}
				
			});
			
		});

		//vue表格数据对象
	    var _self;
		new Vue({
			el: '#datatableId',
			data: {shareInfo:""},
			created:function(){
	            _self=this;
	            var orderid = '${obj.orderId}';
	            $.ajax({ 
	            	url: '${base}/admin/orderJp/getShare.html',
	            	data:{orderid:orderid},
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.shareInfo = data;
	            		console.log(JSON.stringify(_self.shareInfo));
	              	}
	            });
	        }
		});
		//分享
		function save(){
			var sharetype = $("#shareType").val();
			var orderId = ${obj.orderId};
			var applicantMainId,applicantId,name,telephone,email;
			if(sharetype == 1){
				$("#datatableId tbody tr").each(function(){
					applicantId = $(this).children().eq(0).html();
					telephone = $(this).children().eq(2).html();
					email = $(this).children().eq(3).html();
					if(email == "" || telephone == ""){
						layer.open({
							type: 2,
							title: false,
							closeBtn:false,
							fix: false,
							maxmin: false,
							shadeClose: false,
							scrollbar: false,
							area: ['900px', '551px'],
							content:'${base}/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email
						});
						/* layer.confirm("手机号、邮箱不能为空，请及时补充", {
						//title:"",
						btn: ["马上补充","以后再说"], //按钮
						shade: false //不显示遮罩
						}, function(){
							alert(111);
							layer.open({
								type: 2,
								title: false,
								closeBtn:false,
								fix: false,
								maxmin: false,
								shadeClose: false,
								scrollbar: false,
								area: ['900px', '551px'],
								content:'${base}/admin/orderJp/updateApplicant.html?id='+applicantId
							});
						}); */
							}
						});
						
				$("#datatableId tbody tr").each(function(){
					if($(this).hasClass("trColor")){
						applicantMainId = $(this).children().eq(0).html();
					}
				});
						
				$.ajax({ 
					async:false,
					url: BASE_PATH+'/admin/orderJp/applicantComplete',
					type:'post',
					data:{
						orderid:orderId
					},
					success: function(data){
						if(data == "yes"){
							$.ajax({ 
								url: BASE_PATH+'/admin/orderJp/sendEmailUnified',
								type:'post',
								data:{
									orderid:orderId,
									applicantid:applicantMainId
								},
								success: function(data){
									layer.msg('分享成功');
									layer.closeAll('loading');
									layer.close(layerIndex);
									closeWindow();
								}
							});
							
						}
						
					}
				});
			}else{
				$("#datatableId tbody tr").each(function(){
					if($(this).hasClass("trColor")){
						applicantId = $(this).children().eq(0).html();
						name = $(this).children().eq(1).html();
						telephone = $(this).children().eq(2).html();
						email = $(this).children().eq(3).html();
						if(email == "" || telephone == ""){
							layer.open({
								type: 2,
								title: false,
								closeBtn:false,
								fix: false,
								maxmin: false,
								shadeClose: false,
								scrollbar: false,
								area: ['900px', '551px'],
								content:'${base}/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email
							});
						}else{
							$.ajax({ 
								url: BASE_PATH+'/admin/orderJp/sendEmail',
								type:'post',
								data:{
									orderid:orderId,
									applicantid:applicantId
								},
								success: function(data){
									layer.msg('分享成功');
									layer.closeAll('loading');
									if(data.status == 200){
										layer.close(layerIndex);
									}
									closeWindow();
								}
							});
						}
					}
			});
				
			}
			
		}
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		function successCallBack(status){
			if(status == 1){
				layer.msg('修改成功');
			}
			var orderid = '${obj.orderId}';
			$.ajax({ 
            	url: '${base}/admin/orderJp/getShare.html',
            	data:{orderid:orderid},
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		_self.shareInfo = data;
            		console.log(JSON.stringify(_self.shareInfo));
              	}
            });
			save();
			//parent.location.reload();
		}
	</script>
</body>
</html>
