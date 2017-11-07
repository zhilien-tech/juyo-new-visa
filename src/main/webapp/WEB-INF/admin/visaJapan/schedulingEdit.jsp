<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>签证详情 - 行程安排 编辑</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<style type="text/css">
		
	</style>
</head>
<body>
	<div class="modal-content">
		<form id="companyAddForm">
			<div class="modal-header">
				<span class="heading">编辑</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button" class="btn btn-primary pull-right btn-sm btn-right" value="保存" />
				<input type="hidden" id="id" name="id" value="${obj.travelplan.id }">
			</div>
			<div class="modal-body" style="height: 338px;overflow-y: auto;">
				<div class="tab-content">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>天数：</label> 
								<input id="day" name="day" type="text" class="form-control input-sm" placeholder=" " value="${obj.travelplan.day }"/>
							</div>
						</div>

						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>日期：</label> 
								<input id="outDate" name="outDate" type="text" onfocus="WdatePicker()" class="form-control input-sm" value="<fmt:formatDate value="${obj.travelplan.outDate }" pattern="yyyy-MM-dd" />" />
							</div>
						</div>
						
						<div class="col-sm-4">
							<div class="form-group">
								<label><span>*</span>城市：</label> 
								<select id="cityId" name="cityId" class="form-control input-sm" multiple="multiple" >
									<option value="${obj.travelplan.cityId }" selected="selected">${obj.travelplan.cityName }</option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>景区：</label> 
								<select id="scenic" name="scenic" class="form-control input-sm" multiple="multiple">
									<option value="${obj.travelplan.scenic }" selected="selected">${obj.travelplan.scenic }</option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label><span>*</span>酒店：</label> 
								<select id="hotel" name="hotel" class="form-control input-sm" multiple="multiple">
									<option value="${obj.hotel.id }" selected="selected">${obj.hotel.name }</option>
								</select>
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
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/references/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- 经营范围校验 -->
	<script src="${base}/admin/visaJapan/schedulingEdit.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {
			$(".select2-container--default").attr("style","auto");//select2 宽度修改
		});
		/* 页面初始化加载完毕 */

		/*保存页面*/
		 $('#addBtn').click(function(){
			var id  = $('#id').val();
			var day = $('#day').val();
			var outDate = $('#outDate').val();
			var cityId = $('#cityId').val();
			if (cityId) {
				cityId = cityId.join(',');
			}else{
				cityId += '';
			}
			
			var scenic = $('#scenic').val();
			if (scenic) {
				scenic = scenic.join(',');
			}else{
				scenic += '';
			}
			
			var hotel = $('#hotel').val();
			if (hotel) {
				hotel = hotel.join(',');
			}else{
				hotel += '';
			}
			layer.load(1);
			$.ajax({
				type : 'POST',
				data : {id:id,day:day,outDate:outDate,cityId:cityId,scenic:scenic,hotel:hotel},
				url : '${base}/admin/visaJapan/saveEditPlanData.html',
				success : function(data) {
					layer.closeAll('loading');
					parent.successCallBack(1);
					closeWindow();
				},
				error : function(xhr) {
					layer.msg("修改失败", "", 3000);
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
