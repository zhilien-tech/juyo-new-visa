<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>下单</title>
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top: -35px;right: -1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>170202-JP0001</p></span>
					<span class="">受付番号：<p>JDY27163</p></span>
					<span class="">状态：<p>下单</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="回邮" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="初审" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="分享" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" />
				</div>
				<section class="content">
					<!-- 客户信息 -->
					<div class="info">
						<p class="info-head">客户信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 公司全称 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>客户来源：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-9" style="padding-right: 3.5%;">
									<div class="form-group">
										<label><span>*</span>公司全称：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
							</div><!-- end 公司全称 -->
							<div class="row body-from-input"><!-- 客户来源/联系人/手机号/邮箱 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>公司简称：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>手机号：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>邮箱：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
							</div><!-- end 客户来源/联系人/手机号/邮箱 -->
						</div>
					</div>
					<!-- end 客户信息 -->
	
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
							
							<div class="row body-from-input"><!-- 添加申请人 -->
								<div class="col-sm-12">
									<div class="form-group">
										<button type="button" class="btn btn-primary btn-sm addApplicantBtn">添加申请人</button>
									</div>
								</div>
							</div><!-- end 添加申请人 -->
							
							<div class="row body-from-input"><!-- 添加回邮信息 -->
								<div class="col-sm-12">
									<div class="form-group">
										<button type="button" class="btn btn-primary btn-sm addExpressInfoBtn">添加回邮信息</button>
									</div>
								</div>
							</div><!-- end 添加回邮信息 -->
						</div>
					</div>
					<!-- end 订单信息 -->
					
					<!-- 主申请人 -->
					<div class="info" id="mySwitch">
						<p class="info-head">
							主申请人
							<input type="button" name="" value="添加" class="btn btn-primary btn-sm pull-right">
						</p>
						<div class="info-table">
							<table id="principalApplicantTable" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>姓名<span></th>
										<th><span>性别<span></th>
										<th><span>电话<span></th>
										<th><span>护照号<span></th>
										<th><span>现住详细地址<span></th>
										<th><span>操作<span></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
					<!-- end 主申请人 -->
					
					<!-- 快递信息 -->
					<div class="info expressInfo none">
						<p class="info-head">快递信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 资料来源/回邮方式/回邮地址 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>资料来源：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>回邮方式：</label>
										<select class="form-control input-sm">
											<option></option>
											<option></option>
											<option></option>
										</select>
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>回邮地址：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
							</div><!-- end 资料来源/回邮方式/回邮地址 -->
							
							<div class="row body-from-input"><!-- 联系人/电话/发票项目内容/发票抬头 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>电话：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票项目内容：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票抬头：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
							</div><!-- end 联系人/电话/发票项目内容/发票抬头 -->

							<div class="row body-from-input"><!-- 团队名称/快递号/备注 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>团队名称：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>快递号：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>备注：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
							</div><!-- end 团队名称/快递号/备注 -->
						</div>
					</div>
					<!-- end 快递信息 -->

				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
		<script src="${base}/references/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/admin/orderJp/order.js"></script><!-- 本页面js文件 -->
	</body>
</html>