<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>签证详情</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top: -35px;right: -1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.btnState{color: #b0b0b0 !important;border: solid 1px #d2d6de;background-color: #fff;margin-right: 2.26rem;}
			.btnState-true{color: #287ae7 !important;border-color: #cee1ff;}
			.deposit,.vehicle,.houseProperty{display: none;}
			.schedulingBtn{width: 30%;margin: 15px auto;display: block;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>170202-JP0001</p></span>
					<span class="">受付番号：<p>JDY27163</p></span>
					<span class="">状态：<p>发招宝中</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="下载" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="自动填报" class="btn btn-primary btn-sm pull-right" />
				</div>
				<section class="content">
					<!-- 订单信息 -->
					<div class="info">
						<p class="info-head">订单信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 人数/领区/加急 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>人数：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>加急：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<select class="form-control input-sm">
											<option>三个工作日</option>
											<option>两个工作日</option>
											<option>一个工作日</option>
										</select>
									</div>
								</div>
							</div><!-- end 人数/领区/加急 -->
						
							<div class="row body-from-input"><!-- 行程/付款方式/金额 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>付款方式：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>金额：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
							</div><!-- end 行程/付款方式/金额 -->
							<div class="row body-from-input"><!-- 签证类型 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label>
										<select class="form-control input-sm">
											<option>冲绳东北六县三年多次</option>
											<option></option>
											<option></option>
										</select>
									</div>
								</div>
								<div class="col-sm-9">
									<div class="form-group viseType-btn">
										<label style="display:block;">&nbsp;</label>
										<input type="button" value="冲绳县" class="btn btn-sm btnState">
										<input type="button" value="青森县" class="btn btn-sm btnState">
										<input type="button" value="岩手县" class="btn btn-sm btnState">
										<input type="button" value="宫城县" class="btn btn-sm btnState">
										<input type="button" value="秋田县" class="btn btn-sm btnState">
										<input type="button" value="山形县" class="btn btn-sm btnState">
										<input type="button" value="福鸟县" class="btn btn-sm btnState">
									</div>
								</div>
							</div><!-- end 签证类型 -->
							<div class="row body-from-input"><!-- 过去三年是否访问过 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>过去三年是否访问过：</label>
										<select class="form-control input-sm">
											<option>有</option>
											<option></option>
											<option></option>
										</select>
									</div>
								</div>
								<div class="col-sm-9">
									<div class="form-group">
										<label style="display:block;">&nbsp;</label>
										<input type="button" value="岩手县" class="btn btn-sm btnState btnState-true">
										<input type="button" value="秋田县" class="btn btn-sm btnState btnState-true">
										<input type="button" value="山形县" class="btn btn-sm btnState btnState-true">
									</div>
								</div>
							</div><!-- end 过去三年是否访问过 -->
							<div class="row body-from-input"><!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									</div>
								</div>
							</div><!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input"><!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签时间：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出签时间：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " onClick="WdatePicker()" />
									</div>
								</div>
							</div><!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<!-- end 订单信息 -->

					<!-- 申请人 -->
					<div class="info" id="mySwitch">
						<p class="info-head">申请人</p>
						<div class="info-table">
							<table id="applicantTable" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>姓名<span></th>
										<th><span>电话<span></th>
										<th><span>护照号<span></th>
										<th><span>资料类型<span></th>
										<th><span>真是资料<span></th>
										<th><span>操作<span></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
					<!-- end 申请人 -->

					<!-- 出行信息 -->
					<div class="info">
						<p class="info-head">出行信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 往返/多程 / 出行目的 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>往返/多程：</label>
										<select class="form-control input-sm">
											<option>往返</option>
											<option>多程</option>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 往返/多程 / 出行目的 -->
							
							<div class="row body-from-input"><!-- 出发日期/出发城市/抵达城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发日期：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
							
							<div class="row body-from-input"><!-- 返回日期/出发城市/返回城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回日期：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 返回日期/出发城市/返回城市/航班号 -->
							
							<div class="row body-from-input"><!-- 生成行程安排 -->
								<div class="col-sm-12">
									<div class="form-group">
										<button type="button" class="btn btn-primary btn-sm schedulingBtn">生成行程安排</button>
										<table id="schedulingTable" class="table table-hover none" style="width:100%;">
											<thead>
												<tr>
													<th><span>天数<span></th>
													<th><span>日期<span></th>
													<th><span>城市<span></th>
													<th><span>景区<span></th>
													<th><span>酒店<span></th>
													<th><span>操作<span></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>D2</td>
													<td>2017-10-31</td>
													<td>东京</td>
													<td>KDFCHgkdvckgDVHG</td>
													<td>SLchbLKXCBljhdc</td>
													<td>
														<i class="editHui" onclick="schedulingEdit()"></i>
														<i class="resetHui"></i>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div><!-- end 生成行程安排 -->
						</div>
					</div>
					<!-- end 出行信息 -->
				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
		<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script src="${base}/admin/visaJapan/visaDetail.js"></script><!-- 本页面js文件 -->
	</body>
</html>