<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE HTML>
<html lang="en-US" id="addHtml">
<head>
	<meta charset="UTF-8">
	<title>添加申请人</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/bootstrapValidator.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
</head>
<body>
	<div class="modal-content">
		<form id="applicantInfo">
			<div class="modal-header">
				<span class="heading">添加申请人</span> 
				<input id="backBtn" type="button" onclick="closeWindow()" class="btn btn-primary pull-right btn-sm" data-dismiss="modal" value="取消" /> 
				<input id="addBtn" type="button"  class="btn btn-primary pull-right btn-sm btn-right" value="保存" onclick="saveApplicant();"/>
			</div>
			<div class="modal-body">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"><!-- 身份证 正面 -->
							
						</div><!-- end 身份证 正面 -->
						
						<div class="info-imgUpload front"><!-- 身份证 正面 -->

						</div><!-- end 身份证 正面 -->
						
						<div class="info-imgUpload back"><!-- 身份证 反面 -->

						</div><!-- end 身份证 反面 -->
						
						<div class="row"><!-- 签发机关 -->
							<div class="col-sm-11 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发机关：</label>
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 签发机关 -->
					</div>
						
					<div class="col-sm-6 padding-right-0">
						<div class="row"><!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>姓/拼音：</label>
									<input id="firstName" name="firstName" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓/拼音 -->
						<div class="row"><!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>名/拼音：</label>
									<input id="lastName" name="lastName" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 名/拼音 -->	
						<div class="row"><!-- 手机号/邮箱 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>手机号：</label>
									<input id="telephone" name="telephone" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>邮箱：</label>
									<input id="email" name="email" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 手机号/邮箱 -->
						<div class="row"><!-- 现居住地址省份/现居住地址城市 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>现居住地址省份：</label>
									<input id="province" name="province" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>现居住地址城市：</label>
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 现居住地址省份/现居住地址城市 -->
						<div class="row"><!-- 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间  -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>详细地址/区(县)/街道/小区(社区)/楼号/单元/房间：</label>
									<input id="detailedAddress" name="detailedAddress" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 详细地址/区(县)/街道/小区(社区)/楼号/单元/房间 -->
						<div class="row"><!-- 公民身份证 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>公民身份证：</label>
									<input id="cardId" name="cardId" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 公民身份证 -->
						<div class="row"><!-- 姓名/民族 -->
							<div class="col-sm-3 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label>
									<select class="form-control input-sm selectHeight" id="sex" name="sex">
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-3 padding-right-0">
								<div class="form-group">
									<label><span>*</span>民族：</label>
									<input id="nation" name="nation" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期：</label>
									<input id="birthday" name="birthday" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 姓名/民族 -->
						<div class="row"><!-- 住宅 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>住宅：</label>
									<input id="address" name="address" type="text" class="form-control input-sm" placeholder=" " />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 住宅 -->						
						<div class="row"><!-- 有效期限 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>有效期限：</label>
									<input id="validStartDate" name="validStartDate" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label> &nbsp; &nbsp;</label>
									<input id="validEndDate" name="validEndDate" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div><!-- end 有效期限 -->
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
	<!-- DataTables -->
	<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/admin/orderJp/applicant.js"></script>
	
	<script type="text/javascript">
		var base = "${base}";
		function saveApplicant(){
			var applicantInfo = $("#applicantInfo").serialize();
			$.ajax({
				type : 'POST',
				data : applicantInfo,
				url : '${base}/admin/orderJp/saveAddApplicant',
				success : function(data) {
					console.log(JSON.stringify(data));
					
					//alert( $("#principalApplicantTable td").attr());
					//parent.location.reload();
					//parent.datatable.ajax.reload();
				},
				error : function() {
					alert("error");
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
