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
		#tableId tbody tr td:nth-child(1){width:10%;}
		#tableId tbody tr td:nth-child(2){width:18%;}
		#tableId tbody tr td:nth-child(3){width:13%;}
		#tableId tbody tr td:nth-child(4){width:12%;}
		#tableId tbody tr td:nth-child(5){width:37%;}
		#tableId tbody tr td:nth-child(6){width:10%;}
		#tableId{position: relative;top: 15px;}
		/*左右滑块*/
		.switch{width:41px; display:inline-block;padding-left: 8px;}
		
		/*input检索框*/
		li {
			list-style: none;
		}
		.ui-helper-hidden-accessible {
			display: none;
		}
		#search {
			width: 200px;
			height: 30px;
			padding: 0;
			border: 1px solid #eee;
			font-size: 14px;
			padding-left: 8px;
		}
		.ui-menu {
			width: 167.5px !important;
			border: 1px solid #eee;
			padding: 0;
			border-top: 0px;
		}
		.bg{
			background: #4887f1;
			color:#fff;
		}
		.ui-menu-item {
			height:32px;
			line-height:32px;
			padding-left:8px;
		}
		
		#tableId{position: relative;top: 10px;}
		#tableId tbody tr{cursor: pointer;}
		.trColor{color: rgb(48, 135, 240)}
		
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="expressForm" >
			<div class="modal-header">
				<span class="heading">快递</span>
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save(${obj.orderid},${obj.orderjpid});" class="btn btn-primary pull-right btn-sm btn-right" value="发送" />
			</div>
			<div class="modal-body">
				<input id="orderid" type="hidden" value="${obj.orderid}">
				<input id="orderjpid" type="hidden" value="${obj.orderjpid}">
				<div class="tab-content" id="el">
					<div class="row">
						<div class="col-sm-3">
							<div class="form-group">
								<label>快递方式：</label>
								<select id="express" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.expressType}">
										<c:if test="${map.key == obj.orderReceive.expresstype }">
											<option value="${map.key}" selected="selected">${map.value}</option>
										</c:if>
										<c:if test="${map.key != obj.orderReceive.expresstype }">
											<option value="${map.key}">${map.value}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>请选择分享方式：</label>
								<select id="shareType" name="shareType" class="form-control input-sm selectHeight">
									<c:forEach var="map" items="${obj.shareType}">
										<c:if test="${map.key == obj.orderReceive.sharetype }">
											<option value="${map.key}" selected="selected">${map.value}</option>
										</c:if>
										<c:if test="${map.key != obj.orderReceive.sharetype }">
											<option value="${map.key}">${map.value}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="row form-div">
						<div id="receiverDiv" class="col-sm-3">
							<div class="form-group">
								<%-- <c:if test="${! empty obj.orderReceive.receiveaddressid }">
									<input id="receiveAddressId" type="hidden" value="${obj.orderReceive.receiveaddressid }">
								</c:if>
								<c:if test="${empty obj.orderReceive.receiveaddressid }">
									<input id="receiveAddressId" type="hidden" >
								</c:if>
								<label>收件人：</label> 
								<select id="receiver" class="form-control select2 select2City" multiple="multiple">
									<c:if test="${! empty obj.orderReceive.receiveaddressid }">
										<option value="${obj.orderReceive.receiveaddressid }" selected="selected">${obj.orderReceive.receiver }</option>
									</c:if>
								</select> --%>
								<label>收件人：</label>
								<input type="text" id="receiver" name="receiver" value="${obj.orderReceive.receiver}" class="form-control input-sm"/>
							</div>
						</div>
						<div id="mobileDiv" class="col-sm-3">
							<div class="form-group">
								<label>电话：</label> 
								<%-- <select id="mobile" class="form-control select2 select2City" multiple="multiple">
									<c:if test="${! empty obj.orderReceive.receiveaddressid }">
										<option value="${obj.orderReceive.receiveaddressid }" selected="selected">${obj.orderReceive.mobile }</option>
									</c:if>
								</select> --%>
								<input type="text" id="mobile" name="mobile" value="${obj.orderReceive.telephone}" class="form-control input-sm"/>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>邮寄地址：</label>
								<%-- <c:if test="${! empty obj.orderReceive.receiveaddressid }">
									<input id="address" name="address" value="${obj.orderReceive.address}"  readonly="readonly" type="text" class="form-control input-sm"/>
								</c:if>
								<c:if test="${ empty obj.orderReceive.receiveaddressid }">
									<input id="address" name="address" readonly="readonly" type="text" class="form-control input-sm"/>
								</c:if> --%>
								<input id="address" name="address" value="${obj.orderReceive.expressaddress}" type="text" class="form-control input-sm"/>
								
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
						<tbody id="applicant_tbody">
							<!-- <tr v-cloak v-for="apply in applyinfo" class="tableTr">
								<td class="applyidTd" style="display: none">{{apply.applyid}}</td>
								<td>{{apply.applicantname}}</td>
								<td>{{apply.telephone}}</td>
								<td>{{apply.email}}</td>
								<td>{{apply.datatype}}</td>
								<td>{{apply.data}}</td>
							</tr> -->
							
							
							
							<c:forEach var="apply" items="${obj.applicant}">
								<c:choose>
									<c:when test="${fn:contains(obj.shareIds,apply.applyid)}">
										<tr class="tableTr trColor">
											<td class="applyidTd" style="display: none">${apply.applyid }</td>
											<td>${apply.applicantname }</td>
											<td>${apply.telephone }</td>
											<td>${apply.email }</td>
											<td>${apply.datatype }</td>
											<td>${apply.data }</td>
										</tr>
									 </c:when> 
									 <c:otherwise>  
										<tr class="tableTr">
											<td class="applyidTd" style="display: none">${apply.applyid }</td>
											<td>${apply.applicantname }</td>
											<td>${apply.telephone }</td>
											<td>${apply.email }</td>
											<td>${apply.datatype }</td>
											<td>${apply.data }</td>
										</tr>
									 </c:otherwise> 
								</c:choose>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var orderid = '${obj.orderid}';
		var orderjpid = '${obj.orderjpid}';
		var shareIds = '${obj.shareIds}';
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
		/* var orderobj;
		//VUE准备数据
		//applyinfo申请人信息
		new Vue({
			el: '#tableId',
			data: {
				applyinfo:""
			},
			created:function(){
				orderobj=this;
				var url = '${base}/admin/firstTrialJp/getAllApplicantByOrderid.html';
				$.ajax({ 
					url: url,
					type:'post',
					dataType:"json",
					data:{
						orderjpid:orderjpid
					},
					success: function(data){
						orderobj.applyinfo = data.applicant;
					}
				});
			}
		}); */
		
		//返回 
		function closeWindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
	
</body>
</html>
