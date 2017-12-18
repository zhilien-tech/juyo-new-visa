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
	<script src="${base}/admin/orderJp/order.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			$("#addBtn").attr('disabled', true);
			$(document).on("click",".tableTr",function(){
				var sharetype = $("#shareType").val();
				if(sharetype == 2){//单独分享
					if($(this).hasClass("trColor")){
						$(this).removeClass("trColor");
						if($("#datatableId tbody tr").hasClass("trColor")){
							$("#addBtn").attr('disabled', false);
						}else{
							$("#addBtn").attr('disabled', true);
						}
					}else{
						$(this).addClass("trColor");
						$("#datatableId tbody tr").each(function(){
							if($(this).hasClass("trColor")){
								$("#addBtn").attr('disabled', false);
							}
						});
					}
				}else if(sharetype == 1){//统一联系人
					$(this).addClass("trColor").siblings("tr").removeClass("trColor");
				if($(this).hasClass("trColor")){
					$("#addBtn").attr('disabled', false);
				}else{
					$("#addBtn").attr('disabled', true);
				}
				}
			});
			
			$("#shareType").change(function(){
				$("#datatableId tbody tr").each(function(){
					if($(this).hasClass("trColor")){
						$(this).removeClass("trColor");
						$("#addBtn").attr('disabled', true);
					}
				});
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
	            		//console.log(JSON.stringify(_self.shareInfo));
	              	}
	            });
	        }
		});
		//分享
		function save(){
			var sharetype = $("#shareType").val();
			var orderId = ${obj.orderId};
			var applicantMainId,applicantId,name,telephone,email;
			
			if(sharetype == 1){//统一联系人
				$("#datatableId tbody tr").each(function(){
					if($(this).hasClass("trColor")){
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
								content:'${base}/admin/orderJp/getApplicantInfoValid.html?applicantId='+applicantId+'&telephone='+telephone+'&email='+email,
								success : function(index, layero){
									//var iframeWin = window[index.find('iframe')[0]['name']]; 
									// iframeWin.validate();
									//iframeWin.closeSelf();
								}
							});
						}else{
							layer.load(1);
							$.ajax({ 
								url: BASE_PATH+'/admin/orderJp/sendEmailUnified',
								type:'post',
								data:{
									orderid:orderId,
									applicantid:applicantId
								},
								success: function(data){
									layer.closeAll('loading');
									layer.msg("分享成功", {
										time: 1000,
										end: function () {
										var index = parent.layer.getFrameIndex(window.name);
										parent.layer.close(index);
										}
									});
								}
							});
						}
					}
				});
			}else{
				 var flag = 0;
				 var trcount = 0;
				 var applyId = "";
				 $("#datatableId tbody tr").each(function(){
					 if($(this).hasClass("trColor")){
						 applicantId = $(this).children().eq(0).html();
						 trcount++;
						 applyId += applicantId + ',';
					 }
				 });
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
							flag++;
							if(flag == trcount){
								layer.load(1);
								$.ajax({ 
									url: BASE_PATH+'/admin/orderJp/sendEmail',
									//async:false,
									type:'post',
									data:{
										orderid:orderId,
										applicantid:applyId
									},
									success: function(data){
										//flag++;
										//判断选中的是否都已发送邮件
										if(data.sendResult == "success"){
											layer.closeAll('loading');
											layer.msg("分享成功", {
												time: 1000,
												end: function () {
													var index = parent.layer.getFrameIndex(window.name);
													parent.layer.close(index);
												}
											});
										}
									}
								});
							}
						}
					}
			});
			}
		}
		
		//返回 
		function closeWindow() {
			parent.successCallBack(4);
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		function closeSelf(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		function successCallBack(status){
			if(status == 1){
				//layer.msg('修改成功');
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
			parent.successCallBack(4);
			//save();
			//parent.location.reload();
		}
	</script>
</body>
</html>
