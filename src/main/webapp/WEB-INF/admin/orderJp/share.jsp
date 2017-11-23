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
							<tr v-for="data in shareInfo">
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
			var sharetype = $("#shareType").val();
			if(sharetype == 1){//为1时单选
				
			}else{//多选
			$("#datatableId tbody tr").click(function(){
				var isStyle = $(this).attr("style");
				if(isStyle == "color: rgb(48, 135, 240);"){//不被选中
					$(this).css("color","#333333");
					$(this).removeClass('btnState-true');
				}else{//选中
					$(this).css("color","#3087f0");
					$(this).addClass('btnState-true').siblings("tr").addClass('btnState-true');
					
				$(this).children().eq(0).html();
				}
			
			});
			}

			
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
			var name,telephone,email;
			var flag;
			if(sharetype == 1){
				if($("#datatableId tbody tr").attr("style") == "color: rgb(48, 135, 240);"){
					alert(11);
					applicantId = $(this).children().eq(0).html();
					name = $(this).children().eq(1).html();
					telephone = $(this).children().eq(2).html();
					email = $(this).children().eq(3).html();
					
					$.ajax({ 
						url: BASE_PATH+'/admin/orderJp/getShare',
						type:'post',
						data:{
							orderid:orderId
						},
						success: function(data){
							for(var i = 0; i < data.length; i++){
								if(data[i].telephone == "" || data[i].email == ""){
									layer.open({
										type: 2,
										title: false,
										closeBtn:false,
										fix: false,
										maxmin: false,
										shadeClose: false,
										scrollbar: false,
										area: ['900px', '551px'],
										content:'${base}/admin/orderJp/getApplicantInfoValid.html?applicantId='+data[i].id+'&telephone='+data[i].telephone+'&email='+data[i].email
									});
								}
							}
							$.ajax({ 
								url: BASE_PATH+'/admin/orderJp/sendEmailUnified',
								type:'post',
								data:{
									orderid:orderId,
									applicantid:applicantId
								},
								success: function(data){
									if(data.status == 200){
										layer.close(layerIndex);
									}
									closeWindow();
								}
							});
						}
					});
					
				}
				/* $("#datatableId tbody tr").each(function(){
						applicantId = $(this).children().eq(0).html();
						name = $(this).children().eq(1).html();
						telephone = $(this).children().eq(2).html();
						email = $(this).children().eq(3).html();
						if(email == "" || telephone == ""){
							flag = 2;
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
							flag = 1;
						}
				}); */
					/* if(flag == 1){
						alert(222);
					if($("#datatableId tbody tr").attr("style") == "color: rgb(48, 135, 240);"){
						var applicantId = $(this).children().eq(0).html();
						alert(applicantId);
						$.ajax({ 
							url: BASE_PATH+'/admin/orderJp/sendEmailUnified',
							type:'post',
							data:{
								orderid:orderId,
								applicantid:applicantId
							},
							success: function(data){
								if(data.status == 200){
									layer.close(layerIndex);
								}
								closeWindow();
							}
						});
					}
					} */
			}else{
				$("#datatableId tbody tr").each(function(){
					if($(this).attr("style") == "color: rgb(48, 135, 240);"){
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
	</script>
</body>
</html>
