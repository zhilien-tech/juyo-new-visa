<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
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
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top: -35px;right:-1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.btnState{color: #b0b0b0 !important;border: solid 1px #d2d6de;background-color: #fff;margin-right: 2.26rem;}
			.btnState-true{color: #287ae7 !important;border-color: #cee1ff;}
			.deposit,.vehicle,.houseProperty{display:none;}
			#applicantTable tbody tr td:nth-child(1){width: 10%;}
			#applicantTable tbody tr td:nth-child(2){width: 10%;}
			#applicantTable tbody tr td:nth-child(3){width: 10%;}
			#applicantTable tbody tr td:nth-child(4){width: 10%;}
			#applicantTable tbody tr td:nth-child(6){width: 8%;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>{{orderinfo.ordernum}}</p></span>
					<span class="">受付番号：<p>{{orderinfo.acceptdesign}}</p></span>
					<span class="">状态：<p>{{orderinfo.visastatus}}</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" onclick="javascript:history.go(-1)"/>
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" onclick="commitdata();"/>
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
										<input id="number" name="number" type="text" class="form-control input-sm" placeholder=" " v-model="orderinfo.number"/>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label>
										<select class="form-control input-sm" v-model="orderinfo.cityid">
											<c:forEach var="map" items="${obj.collarareaenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>加急：</label>
										<select class="form-control input-sm" v-model="orderinfo.urgenttype">
											<c:forEach var="map" items="${obj.mainsaleurgentenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>&nbsp;</label>
										<select class="form-control input-sm" v-model="orderinfo.urgentday">
											<c:forEach var="map" items="${obj.mainsaleurgenttimeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div><!-- end 人数/领区/加急 -->
						
							<div class="row body-from-input"><!-- 行程/付款方式/金额 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程：</label>
										<select class="form-control input-sm" v-model="orderinfo.travel">
											<c:forEach var="map" items="${obj.mainsaletriptypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>付款方式：</label>
										<select class="form-control input-sm" v-model="orderinfo.paytype">
											<c:forEach var="map" items="${obj.mainsalepaytypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>金额：</label>
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="orderinfo.money" />
									</div>
								</div>
							</div><!-- end 行程/付款方式/金额 -->
							<div class="row body-from-input"><!-- 签证类型 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label>
										<select id="visatype" class="form-control input-sm" v-model="orderinfo.visatype">
											<c:forEach var="map" items="${obj.mainsalevisatypeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<c:choose>
									<c:when test="${obj.jporderinfo.visaType == 2 }">
										<div class="col-sm-9" id="visacounty">
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="visacounty" type="button" value="冲绳县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="青森县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="宫城县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="山形县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="福鸟县" class="btn btn-sm btnState">
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-sm-9 none" id="visacounty">
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="visacounty" type="button" value="冲绳县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="青森县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="宫城县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="山形县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="福鸟县" class="btn btn-sm btnState">
											</div>
										</div>
									
									</c:otherwise>
								</c:choose>
							</div><!-- end 签证类型 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType == 2 }">
									<div class="row body-from-input" id="threefangwen"><!-- 过去三年是否访问过 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>过去三年是否访问过：</label>
												<select class="form-control input-sm" v-model="orderinfo.isvisit">
													<c:forEach var="map" items="${obj.isyesornoenum}">
														<option value="${map.key}">${map.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-9">
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
										</div>
									</div><!-- end 过去三年是否访问过 -->
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen"><!-- 过去三年是否访问过 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>过去三年是否访问过：</label>
												<select class="form-control input-sm" v-model="orderinfo.isvisit">
													<c:forEach var="map" items="${obj.isyesornoenum}">
														<option value="${map.key}">${map.value}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-9">
											<div class="form-group viseType-btn">
												<label style="display:block;">&nbsp;</label>
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
										</div>
									</div><!-- end 过去三年是否访问过 -->
								</c:otherwise>
							</c:choose>
							<div class="row body-from-input target"><!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="gotripdate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.gotripdate"/>
										<!-- <date-picker field="myDate" placeholder="选择日期"
											 :no-today="true"
											 :value.sync="date3"
											 :format="format"
											 v-model="orderinfo.gotripdate"></date-picker> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="" name="" type="text" class="form-control input-sm" v-model="orderinfo.stayday"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="backtripdate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.backtripdate"/>
									</div>
								</div>
							</div><!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input"><!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签时间：</label>
										<input id="sendvisadate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.sendvisadate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出签时间：</label>
										<input id="outvisadate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.outvisadate"/>
									</div>
								</div>
							</div><!-- end 送签时间/出签时间 -->
						</div>
					</div>
					<!-- end 订单信息 -->

					<!-- 申请人 -->
					<div class="info" id="mySwitch">
						<p class="info-head">申请人</p>
						<div class="info-table" style="padding-bottom: 1px;">
							<table id="applicantTable" class="table table-hover" style="width:100%;">
								<thead>
									<tr>
										<th><span>姓名<span></th>
										<th><span>电话<span></th>
										<th><span>护照号<span></th>
										<th><span>资料类型<span></th>
										<th><span>真实资料<span></th>
										<th><span>操作<span></th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="apply in applyinfo">
										<td>{{apply.applyname}}</td>
										<td>{{apply.telephone}}</td>
										<td>{{apply.passport}}</td>
										<td>{{apply.type}}</td>
										<td>{{apply.realinfo}}</td>
										<td><a v-on:click="passport(apply.applyid)">护照</a>&nbsp;&nbsp;<a v-on:click="visa(apply.applyid)">签证</a></td>
									</tr>
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
										<input id="" name="" type="text" class="form-control input-sm" placeholder=" " v-model="travelinfo.trippurpose"/>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 往返/多程 / 出行目的 -->
							
							<div class="row body-from-input"><!-- 出发日期/出发城市/抵达城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发日期：</label>
										<input id="goDate" name="" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="travelinfo.goDate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="goDepartureCity" class="form-control select2 select2City" multiple="multiple" v-model="travelinfo.goDepartureCity">
											<c:if test="${!empty obj.goleavecity.id}">
												<option value="${obj.goleavecity.id}" selected="selected">${obj.goleavecity.city}</option>
											</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>抵达城市：</label>
										<select id="goArrivedCity" class="form-control input-sm select2City" multiple="multiple" v-model="travelinfo.goArrivedCity">
											<c:if test="${!empty obj.goarrivecity.id}">
												<option value="${obj.goarrivecity.id}" selected="selected">${obj.goarrivecity.city}</option>
											</c:if>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="goFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" v-model="travelinfo.goFlightNum">
											<c:if test="${!empty obj.goflightnum.id }">
												<option value="${obj.goflightnum.id }" selected="selected">${obj.goflightnum.flightnum }</option>
											</c:if>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 出发日期/出发城市/抵达城市/航班号 -->
							
							<div class="row body-from-input"><!-- 返回日期/出发城市/返回城市/航班号 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回日期：</label>
										<input id="returnDate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="travelinfo.returnDate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出发城市：</label>
										<select id="returnDepartureCity" class="form-control select2 select2City" multiple="multiple" v-model="travelinfo.returnDepartureCity">
											<c:if test="${!empty obj.backleavecity.id}">
												<option value="${obj.backleavecity.id}" selected="selected">${obj.backleavecity.city}</option>
											</c:if>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回城市：</label>
										<select id="returnArrivedCity" class="form-control input-sm select2City" multiple="multiple" v-model="travelinfo.returnArrivedCity">
											<c:if test="${!empty obj.backarrivecity.id}">
												<option value="${obj.backarrivecity.id}" selected="selected">${obj.backarrivecity.city}</option>
											</c:if>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>航班号：</label>
										<select id="returnFlightNum" class="form-control input-sm flightSelect2" multiple="multiple" v-model="travelinfo.returnFlightNum">
											<c:if test="${!empty obj.returnflightnum.id }">
												<option value="${obj.returnflightnum.id }" selected="selected">${obj.returnflightnum.flightnum }</option>
											</c:if>
										</select>
										<!-- <i class="bulb"></i> -->
									</div>
								</div>
							</div><!-- end 返回日期/出发城市/返回城市/航班号 -->
							
							<div class="row body-from-input"><!-- 生成行程安排 -->
								<div class="col-sm-12">
									<div class="form-group">
										<button type="button" class="btn btn-primary btn-sm schedulingBtn">生成行程安排</button>
										<table id="schedulingTable" class="table table-hover" style="width:100%;">
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
												<tr v-for="plan in travelplan">
													<td>第{{plan.day}}天</td>
													<td>{{plan.outdate}}</td>
													<td>{{plan.cityname}}</td>
													<td>{{plan.scenic}}</td>
													<td>{{plan.hotelname}}</td>
													<td>
														<i class="editHui" v-on:click="schedulingEdit(plan.id)"></i>
														<i class="resetHui" v-on:click="resetPlan(plan.id)"></i>
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
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			var orderid = '${obj.orderid}';
		</script>
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
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
		<script src="${base}/admin/visaJapan/visaDetail.js"></script><!-- 本页面js文件 -->
		<script type="text/javascript">
			var threecounty = '${obj.jporderinfo.visaCounty}';
			if(threecounty){
				var threecountys = threecounty.split(",");
				for (var i = 0; i < threecountys.length; i++) {
					$('[name=visacounty]').each(function(){
						if(threecountys[i] == $(this).val()){
							$(this).addClass('btnState-true');
						}
					});
				}
			}
			var threecounty = '${obj.jporderinfo.threeCounty}';
			if(threecounty){
				var threecountys = threecounty.split(",");
				for (var i = 0; i < threecountys.length; i++) {
					$('[name=threecounty]').each(function(){
						if(threecountys[i] == $(this).val()){
							$(this).addClass('btnState-true');
						}
					});
				}
			}
			
			function successCallBack(status){
				if(status == 1){
					layer.msg('修改成功');
					$.ajax({ 
				    	url: BASE_PATH + '/admin/visaJapan/getTrvalPlanData.html',
				    	dataType:"json",
				    	data:{orderid:orderid},
				    	type:'post',
				    	success: function(data){
				    		orderobj.travelplan = data;
				      	}
				    }); 
				}
			}
		</script>
	</body>
</html>
