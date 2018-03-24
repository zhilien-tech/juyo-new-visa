<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/pcVisa" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>签证详情</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/font-awesome.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/bootstrapcss/css/ionicons.min.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<!-- 签证详情样式 -->
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/visaDetail.css">
		<!-- 加载中。。。样式 -->
		<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper" >
			<div class="content-wrapper">
				<div class="qz-head">
					<span class="orderNum">订单号：<p>3213132131313123</p></span>
					<!-- <span class="">受付番号：<p>{{orderinfo.acceptdesign}}</p></span> -->
					<span class="state">状态：<p>下单</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存并返回" class="btn btn-primary btn-sm pull-right btn-Big" />
					<input type="button" value="下载" class="btn btn-primary btn-sm pull-right"/>
				</div>
				<section class="content">
					<!-- 订单信息 -->
					<div class="info">
						<p class="info-head">订单信息</p>
						<div class="info-body-from">
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行目的</label>
										<select class="form-control input-sm">
											<option>--请选择--</option>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>是否有具体的旅行计划</label>
										<div>
											<input type="radio" name="tripPlan" class="tripPlan" value="1">是
											<input type="radio" name="tripPlan" class="tripPlan" value="2" checked >否
										</div>
									</div>
								</div>
							</div>
						
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出发日期：</label>
										<input name="" type="text" class="form-control input-sm datetimepickertoday departuredate" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达美国日期：</label>
										<input name="" type="text" class="form-control input-sm datetimepickertoday departuredate" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="" name="" type="text" class="form-control input-sm mustNumberPoint" />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>离开美国日期：</label>
										<input name="" type="text" class="form-control input-sm datetimepickertoday departuredate" />
									</div>
								</div>
							</div>
							<div class="row body-from-input checkShowORHide">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="goDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple" >
											<option>231321</option>
											<%-- <c:if test="${!empty obj.goleavecity.id}">
												<option value="${obj.goleavecity.id}" selected="selected">${obj.goleavecity.city}</option>
											</c:if> --%>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label>
										<select id="goArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple" >
											<option>231321</option>
											<%-- <c:if test="${!empty obj.goarrivecity.id}">
												<option value="${obj.goarrivecity.id}" selected="selected">${obj.goarrivecity.city}</option>
											</c:if> --%>
										</select>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" >
											<option>231321</option>
											<%-- <c:if test="${!empty obj.goflightnum.id }">
												<option value="${obj.goflightnum.id }" selected="selected">${obj.goflightnum.takeOffName }-${obj.goflightnum.landingName } ${obj.goflightnum.flightnum } ${obj.goflightnum.takeOffTime }/${obj.goflightnum.landingTime }</option>
											</c:if> --%>
										</select>
									</div>
								</div>
							</div>
							
							<div class="row body-from-input checkShowORHide">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="returnDepartureCity" class="form-control select2 select2City departurecity" multiple="multiple">
											<option>231321</option>
											<%-- <c:if test="${!empty obj.backleavecity.id}">
												<option value="${obj.backleavecity.id}" selected="selected">${obj.backleavecity.city}</option>
											</c:if>--%>
										</select> 
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label>
										<select id="returnArrivedCity" class="form-control input-sm select2City arrivedcity" multiple="multiple">
											<option>231321</option>
											<%-- <c:if test="${!empty obj.backarrivecity.id}">
												<option value="${obj.backarrivecity.id}" selected="selected">${obj.backarrivecity.city}</option>
											</c:if>--%>
										</select> 
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple">
											<option>231321</option>
											<%-- <c:if test="${!empty obj.returnflightnum.id }">
												<option value="${obj.returnflightnum.id }" selected="selected">${obj.returnflightnum.takeOffName }-${obj.returnflightnum.landingName } ${obj.returnflightnum.flightnum } ${obj.returnflightnum.takeOffTime }/${obj.returnflightnum.landingTime }</option>
											</c:if> --%>
										</select>
									</div>
								</div>
							</div>
							
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签计划去美国地点：</label>
										<input id="" type="text" class="form-control input-sm" placeholder="州/省"  />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span></span></label>
										<input id="" type="text" class="form-control input-sm" placeholder="市" />
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span></span></label>
										<input id="" type="text" class="form-control input-sm" placeholder="街道" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- end 订单信息 -->

					<!-- 申请人 -->
					<div class="info" id="mySwitch">
						<p class="info-head">申请人</p>
						<div class="dataInfoGroup">
							<a>拍照资料</a>
							<a>护照信息</a>
							<a>基本信息</a>
							<a>签证信息</a>
						</div>
						<div class="info-body-from">
							<div class="row body-from-input">
								<div class="col-sm-3">
									<!-- start 二寸免冠照片 -->
									<div class="col-xs-10 picturesInch">
										<div class="form-group pictureTop">
											<div class="uploadInfo">
												<span class="inchInfo">二寸免冠照片</span>
												<input id="cardInch" name="cardfront" type="hidden" value=""/>
												<img id="imgInch" name="imgInch" alt="" src="" >
												<input id="uploadFileInchImg" name="uploadFileInchImg" class="btn btn-primary btn-sm" type="file"  value="上传"/>
												<i class="delete" onclick="deleteApplicantInchImg()"></i>
											</div>
										</div>
									</div>
									<div class="col-sm-12 purpose">
										<div class="form-group">
											<label>出行目的</label>
											<input id="" type="text" class="form-control input-sm" placeholder="" />
										</div>
									</div>
								</div>
								
								<div class="col-sm-9">
									<div class="row body-from-input">
										<div class="col-sm-4">
											<div class="form-group">
												<label>姓名/拼音</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>性别</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>出生日期</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
									</div>
									<div class="row body-from-input">
										<div class="col-sm-12">
											<div class="form-group">
												<label>所需资料</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
									</div>
									<div class="row body-from-input">
										<div class="col-sm-4">
											<div class="form-group">
												<label>AA码</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>护照号</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>面试时间</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
									</div>
									<div class="row body-from-input">
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行目的</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>出行时间</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label>停留天数</label>
												<input id="" type="text" class="form-control input-sm" placeholder="" />
											</div>
										</div>
										
									</div>
									
								</div>
							</div>
						</div>	
					</div>
					
				</section>
			</div>
		</div>
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
		<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/references/common/js/vue/vue.min.js"></script>
		<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
		<!-- 公用js文件 -->
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
		<script type="text/javascript">
			$(function(){
				
				$(".tripPlan").change(function(){
					
					var tripPlan = $("input[name='tripPlan']:checked").val();
					
					if(tripPlan == 1){
						
						$(".checkShowORHide").show();
					}else{
						$(".checkShowORHide").hide();
					}
				});
			})
		</script>
	</body>
</html>