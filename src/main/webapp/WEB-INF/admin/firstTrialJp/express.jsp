<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/sale" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>初审 - 快递</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-switch.min.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		#datatableId tbody tr td:nth-child(1){width:10%;}
		#datatableId tbody tr td:nth-child(2){width:18%;}
		#datatableId tbody tr td:nth-child(3){width:13%;}
		#datatableId tbody tr td:nth-child(4){width:12%;}
		#datatableId tbody tr td:nth-child(5){width:37%;}
		#datatableId tbody tr td:nth-child(6){width:10%;}
		#tableId{position: relative;top: 15px;}
		/*左右滑块*/
		.switch{width:41px; display:inline-block;padding-left: 8px;}
	</style>
</head>
<body >

	<div class="modal-content">
		<form id="expressForm" >
			<div class="modal-header">
				<span class="heading">快递</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(${obj.orderid});" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
			<div class="modal-body">
				<div class="tab-content" id="el">
					<div class="row">
						<div class="col-sm-3">
							<div class="form-group">
								<select id="express" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.expressType}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="row form-div">
						<div class="col-sm-3">
							<div class="form-group">
								<input id="receiveAddressId" type="hidden" >
								<label>收件人：</label> 
								<select id="receiver" class="form-control select2 select2City" multiple="multiple">
								</select>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>电话：</label> 
								<select id="mobile" class="form-control select2 select2City" multiple="multiple">
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>邮寄地址：</label> 
								<input id="address" name="address" readonly="readonly" type="text" class="form-control input-sm"/>
							</div>
						</div>
					</div>
					<table id="tableId" class="table table-hover" style="width:100%;">
						<thead>
							<tr>
								<th><span>姓名</span></th>
								<th><span>电话</span></th>
								<th><span>邮箱</span></th>
								<th><span>资料类型</span></th>
								<th><span>审核确认资料</span></th>
								<!-- <th><span>统一联系人</span></th> -->
							</tr>
						</thead>
						<tbody>
							<tr v-cloak v-for="apply in applyinfo">
								<td>{{apply.applicantname}}</td>
								<td>{{apply.telephone}}</td>
								<td>{{apply.email}}</td>
								<td>{{apply.datatype}}</td>
								<td>{{apply.data}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var orderid = '${obj.orderid}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap-switch.min.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/admin/firstTrialJp/expressSelect2.js"></script><!-- 本页面js文件 -->
	
	<script type="text/javascript">
		var orderobj;
		//VUE准备数据
		//applyinfo申请人信息
		new Vue({
			el: '#tableId',
			data: {
				applyinfo:""
			},
			created:function(){
				orderobj=this;
				var url = '${base}/admin/firstTrialJp/getmainApplicantByOrderid.html';
				$.ajax({ 
					url: url,
					type:'post',
					dataType:"json",
					data:{
						id:orderid
					},
					success: function(data){
						orderobj.applyinfo = data.applicant;
						//alert(JSON.stringify(orderobj.applyinfo));
					}
				});
			}
		});
		
		/* var url = '${base}/admin/firstTrialJp/getmainApplicantByOrderid.html';
		$.ajax({ 
			url: url,
			type:'post',
			dataType:"json",
			data:{
				id:orderid
			},
			success: function(data){
				var applicants = data.applicant;
				if(applicants != null || applicants != "" ){
					
				}
			}
		}); */
		
	</script>
	
</body>
</html>
