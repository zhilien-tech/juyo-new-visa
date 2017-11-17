<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>我的资料 - 护照信息</title>
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/addApplicant.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.tab-content{padding: 15px 30px 10px 0;margin: 0 0px;}
			.info-QRcode{width: 150px;height: 150px;margin: 15px auto;border: #edefef solid 1px;}
			.front, .back {width: 320px;margin: 10px auto;}
		</style>
	</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper" style="min-height: 848px;">
			<div class="qz-head">
				<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" /> 
				<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" /> 
				<input type="button" value="清除" class="btn btn-primary btn-sm pull-right" />
			</div>
			<section class="content">
				<div class="tab-content row">
					<div class="col-sm-6 padding-right-0">
						<div class="info-QRcode"><!-- 身份证 正面 -->

						</div><!-- end 身份证 正面 -->

						<div class="info-imgUpload front"><!-- 上传护照 -->

						</div><!-- end 上传护照 -->

						<div class="row none"><!-- 签发机关 -->
							<div class="col-sm-11 padding-right-0" style="width: 83.5%; margin-left: 7%;">
								<div class="form-group">
									<label><span>*</span>签发机关：</label> 
									<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
								</div>
							</div>
						</div>
						<!-- end 签发机关 -->
					</div>

					<div class="col-sm-6 padding-right-0">
						<div class="row"><!-- 类型/护照 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>类型：</label> <input id="telephone"
										name="telephone" type="text" class="form-control input-sm"
										placeholder=" " value="${obj.applicant.telephone }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>护照：</label> <input id="email" name="email"
										type="text" class="form-control input-sm" placeholder=" "
										value="${obj.applicant.email }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 类型/护照 -->
						<div class="row">
							<!-- 姓/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>姓/拼音：</label> <input id="firstName"
										name="firstName" type="text" class="form-control input-sm"
										placeholder=" " value="${obj.applicant.firstName }" />
										<input type="hidden" id="id" name="id" value="${obj.applicant.id }"/>
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 姓/拼音 -->
						<div class="row">
							<!-- 名/拼音 -->
							<div class="col-sm-11 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>名/拼音：</label> <input id="lastName"
										name="lastName" type="text" class="form-control input-sm"
										placeholder=" " value="${obj.applicant.lastName }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
						</div>
						<!-- end 名/拼音 -->
						
						<div class="row"><!-- 性别/出生地点 -->
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>性别：</label> 
									<select class="form-control input-sm">
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生地点/拼音：</label> 
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.city }" />
								</div>
							</div>
						</div>
						<!-- end 性别/出生地点 -->
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>出生日期：</label> 
									<input id="validStartDate" name="validStartDate" type="text"
										class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()"
										value="${obj.applicant.validStartDate }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发地点/拼音：</label> 
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.city }" />
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-5 col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>签发日期：</label> 
									<input id="validStartDate" name="validStartDate" type="text"
										class="form-control input-sm" placeholder=" "
										onClick="WdatePicker()"
										value="${obj.applicant.validStartDate }" />
									<!-- <i class="bulb"></i> -->
								</div>
							</div>
							<div class="col-sm-5  col-sm-offset-1 padding-right-0">
								<div class="form-group">
									<label><span>*</span>有效期至：</label> 
									<input id="city" name="city" type="text" class="form-control input-sm" placeholder=" " value="${obj.applicant.city }" />
								</div>
							</div>
						</div>
					</div>

				</div>
			</section>
		</div>
		<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/orderJp/searchCustomerInfo.js"></script>
	<!-- 公用js文件 -->
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<script src="${base}/admin/orderJp/order.js"></script>
	
	<!-- 本页面js文件 -->
	<script type="text/javascript">
		$(function(){
			
			
		});
	</script>
</body>
</html>
