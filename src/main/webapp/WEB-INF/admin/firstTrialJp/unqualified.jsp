<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>初审 - 不合格</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		.infoCheck{position:relative;top: 2.5px;right: 7px;height: 14px;width: 14px;}
		.info-input .input-sm{width: 88%;margin-left: 2%;}
	</style>
</head>
<body id="unqualified">
	<div class="modal-content">
		<div class="modal-header">
				<span class="heading">不合格</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" onclick="save();" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
			</div>
		<div class="modal-body" style="height: 340px;">
				<div class="tab-content">
					<form id="unqualifiedForm">
						<div class="row form-div">
							<div class="col-sm-12">
								<div class="form-group info-input">
									<input id="isBase" name="isBase" type="checkbox" class="infoCheck">
									<label>基本信息</label> 
									<input id="baseRemark" name="baseRemark" type="text" class="form-control input-sm none"/>
								</div>
							</div>
						</div>
						<div class="row form-div">
							<div class="col-sm-12">
								<div class="form-group info-input">
									<input id="applicantId" name="applicantId" type="hidden" value="${obj.applyid}" >
									<input id="orderid" name="orderid" type="hidden" value="${obj.orderid}" >
									<input id="isPassport" name="isPassport" type="checkbox" class="infoCheck">
									<label>护照信息</label> 
									<input id="passRemark" name="passRemark" type="text" class="form-control input-sm none" />
								</div>
							</div>
						</div>
						<div class="row form-div">
							<div class="col-sm-12">
								<div class="form-group info-input">
									<input id="isVisa" name="isVisa" type="checkbox" class="infoCheck">
									<label>签证信息</label> 
									<input id="visaRemark" name="visaRemark" type="text" class="form-control input-sm none"/>
								</div>
							</div>
						</div>
					</form>
					
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
	<script src="${base}/references/common/js/layer/layer.js"></script>
	
	<script type="text/javascript">
		$(function() {
			$(".infoCheck").click(function(){
				var checkVal = $(this).is(':checked');
				if(checkVal){
					$(this).siblings(".input-sm").show();
				}else{
					$(this).siblings(".input-sm").hide();
					$(this).siblings(".input-sm").val("");
				}
			});
			
			if('${obj.hasUnqInfo}'){
				var isBase = '${obj.unqualifiedInfo.isBase}';
				if(1==isBase){
					$("#isBase").attr("checked",'true');
					$("#baseRemark").show();
					$("#baseRemark").val('${obj.unqualifiedInfo.baseRemark}');
				}
				var isPassport = '${obj.unqualifiedInfo.isPassport}';
				if(1==isPassport){
					$("#isPassport").attr("checked",'true');
					$("#passRemark").show();
					$("#passRemark").val('${obj.unqualifiedInfo.passRemark}');
				}
				var isVisa = '${obj.unqualifiedInfo.isVisa}';
				if(1==isVisa){
					$("#isVisa").attr("checked",'true');
					$("#visaRemark").show();
					$("#visaRemark").val('${obj.unqualifiedInfo.visaRemark}');
				}
				
			}
			
		}); 
		
		//保存
		function save(){
			layer.load(1);
			var passportInfo = $("#unqualifiedForm").serialize();
			$.ajax({
				type: 'POST',
				data : passportInfo,
				url: '${base}/admin/firstTrialJp/saveUnqualified',
				success :function(data) {
					console.log(JSON.stringify(data));
					layer.closeAll('loading');
					parent.successCallBack(4);
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
