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
					<div class="row form-div">
						<div class="col-sm-12">
							<div class="form-group info-input">
								<input type="checkbox" class="infoCheck">
								<label>护照信息</label> 
								<input name="remark" type="text" class="form-control input-sm none" />
							</div>
						</div>
					</div>
					<div class="row form-div">
						<div class="col-sm-12">
							<div class="form-group info-input">
								<input type="checkbox" class="infoCheck">
								<label>基本信息</label> 
								<input id="" name="remark" type="text" class="form-control input-sm none"/>
							</div>
						</div>
					</div>
					<div class="row form-div">
						<div class="col-sm-12">
							<div class="form-group info-input">
								<input type="checkbox" class="infoCheck">
								<label>签证信息</label> 
								<input id="" name="remark" type="text" class="form-control input-sm none"/>
							</div>
						</div>
					</div>
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
		$(function() {
			$(".infoCheck").click(function(){
				var checkVal = $(this).is(':checked');
				if(checkVal){
					$(this).siblings(".input-sm").show();
				}else{
					$(this).siblings(".input-sm").hide();
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
