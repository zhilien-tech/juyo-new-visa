<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/firstTrialJp" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>初审 - 编辑</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top: -35px;right: -1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>170202-JP0001</p></span>
					<!-- <span class="">受付番号：<p>JDY27163</p></span> -->
					<span class="">状态：<p>下单</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right"/>
					<input type="button" value="回邮" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="快递" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" />
				</div>
				<section class="content">
					<!-- 订单信息 -->
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">订单信息</p>
						<div class="info-body-from">
							<div class="row body-from-input">
								<!-- 人数/领区/加急 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>人数：</label> 
										<input id="number" name="number" v-model="orderinfo.number" type="text" class="form-control input-sm" placeholder=" "  />
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
								<div class="col-sm-3 none" id="urgentDays">
									<div class="form-group">
										<label>&nbsp;</label> 
										<select class="form-control input-sm" v-model="orderinfo.urgentday">
											<c:forEach var="map" items="${obj.mainsaleurgenttimeenum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<!-- end 人数/领区/加急 -->
	
							<div class="row body-from-input">
								<!-- 行程/付款方式/金额 -->
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
										<label><span>*</span>金额：</label> <input id="money" name="money" type="text" class="form-control input-sm mustNumberPoint" placeholder=" " v-model="orderinfo.money" />
									</div>
								</div>
							</div>
							<!-- end 行程/付款方式/金额 -->
							<div class="row body-from-input">
								<!-- 签证类型 -->
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
								
								<div class="col-sm-8 none" id="sixCounty" v-model="orderInfo.visacounty">
									<div class="form-group viseType-btn">
										<label style="display: block;">&nbsp;</label> 
										<input type="button" value="冲绳县" class="btn btn-sm btnState">
										<input type="button" value="青森县" class="btn btn-sm btnState">
										<input type="button" value="岩手县" class="btn btn-sm btnState">
										<input type="button" value="宫城县" class="btn btn-sm btnState">
										<input type="button" value="秋田县" class="btn btn-sm btnState">
										<input type="button" value="山形县" class="btn btn-sm btnState">
										<input type="button" value="福鸟县" class="btn btn-sm btnState">
									</div>
								</div>
							</div>
							<!-- end 签证类型 -->
							<div class="row body-from-input access none">
								<!-- 过去三年是否访问过 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>过去三年是否访问过：</label> 
										<select id="isVisit" name="isvisit" class="form-control input-sm" onchange="selectListData();" >
											<c:forEach var="map" items="${obj.threeYearsIsVisitedEnum}">
												<option value="${map.key}" ${map.key==0?'selected':''}>${map.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-8" id="isVisited" v-model="orderInfo.threeCounty">
									<div class="form-group viseType-btn">
										<label style="display: block;">&nbsp;</label> 
										<input type="button" value="岩手县" class="btn btn-sm btnState"> 
										<input type="button" value="秋田县" class="btn btn-sm btnState"> 
										<input type="button" value="山形县" class="btn btn-sm btnState">
									</div>
								</div>
							</div>
							<!-- end 过去三年是否访问过 -->
							<div class="row body-from-input">
								<!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label> 
										<input id="gotripdate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.gotripdate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="stayday" name="stayday" type="text" class="form-control input-sm mustNumber" v-model="orderinfo.stayday"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="backtripdate" type="text" class="form-control input-sm" onfocus="WdatePicker()" v-model="orderinfo.backtripdate"/>
									</div>
								</div>
							</div>
							<!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input">
								<!-- 送签时间/出签时间 -->
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
							</div>
							<!-- end 送签时间/出签时间 -->
						</div>
					</div><!-- end 订单信息 -->
					
					
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
										<th><span>性别<span></th>
										<th><span>状态<span></th>
										<th><span>操作<span></th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="apply in applyinfo">
										<td>{{apply.applyname}}</td>
										<td>{{apply.telephone}}</td>
										<td>{{apply.passport}}</td>
										<td></td>
										<td></td>
										<td>{{apply.sex}}</td>
										<td>{{apply.status}}</td>
										<td>
											<a v-on:click="basicInfoClick">基本信息</a>&nbsp;&nbsp;
											<a v-on:click="passport(apply.applyid)">护照</a>&nbsp;&nbsp;
											<a v-on:click="visa(apply.applyid)">签证信息</a><br>
											<a v-on:click="qualified">合格</a>&nbsp;&nbsp;
											<a v-on:click="unqualified">不合格</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<!-- end 申请人 -->
					
	
					<div class="row body-from-input" id="backmailInfo"><!-- 添加回邮信息 -->
						<div class="col-sm-12">
							<div class="form-group">
								<button type="button" class="btn btn-primary btn-sm addExpressInfoBtn">添加回邮信息</button>
							</div>
						</div>
					</div><!-- end 添加回邮信息 -->
	
					<!-- 快递信息 -->
					<div class="info expressInfo none" id="expressInfo" name="backmailInfo">
						<p class="info-head">快递信息</p>
						<div class="info-body-from">
							<div class="row body-from-input">
								<!-- 资料来源/回邮方式/回邮地址 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>资料来源：</label> 
										<select id="datasour" name="datasour" class="form-control input-sm">
											<c:forEach var="map" items="${obj.mainBackMailSourceTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select> <i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>回邮方式：</label> <select id="expressType"
											name="expressType" class="form-control input-sm">
											<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
												<option value="${map.key}">${map.value}</option>
											</c:forEach>
										</select> <i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>回邮地址：</label> <input id="expressAddress"
											name="expressAddress" type="text"
											class="form-control input-sm" placeholder=" " /> <i
											class="bulb"></i>
									</div>
								</div>
							</div>
							<!-- end 资料来源/回邮方式/回邮地址 -->
	
							<div class="row body-from-input">
								<!-- 联系人/电话/发票项目内容/发票抬头 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>联系人：</label> <input id="linkman"
											name="linkman" type="text" class="form-control input-sm"
											placeholder=" " /> <i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>电话：</label> <input id="telephone"
											name="telephone" type="text" class="form-control input-sm"
											placeholder=" " /> <i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票项目内容：</label> <input id="invoiceContent"
											name="invoiceContent" type="text"
											class="form-control input-sm" placeholder=" " /> <i
											class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>发票抬头：</label> <input id="invoiceHead"
											name="invoiceHead" type="text" class="form-control input-sm"
											placeholder=" " /> <i class="bulb"></i>
									</div>
								</div>
							</div>
							<!-- end 联系人/电话/发票项目内容/发票抬头 -->
	
							<div class="row body-from-input">
								<!-- 团队名称/快递号/备注 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>团队名称：</label> <input id="" name=""
											type="text" class="form-control input-sm" placeholder=" " />
										<i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>快递号：</label> <input id="expressNum"
											name="expressNum" type="text" class="form-control input-sm"
											placeholder=" " /> <i class="bulb"></i>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label><span>*</span>备注：</label> <input id="remark"
											name="remark" type="text" class="form-control input-sm"
											placeholder=" " /> <i class="bulb"></i>
									</div>
								</div>
							</div>
							<!-- end 团队名称/快递号/备注 -->
						</div>
					</div>
					<!-- end 快递信息 -->
				</section>
			</div>
	
		</div>
		
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/vue/vue.min.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/admin/orderJp/order.js"></script><!-- 本页面js文件 -->
		<!-- select2 -->
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			
			/* $(function(){
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
			}); */
			
			
			/* function saveAddOrder(){
				var orderinfo = $("#orderInfo").serialize();
				$.ajax({
					type : 'POST',
					data : orderinfo ,
					url : '${base}/admin/orderJp/saveAddOrderinfo',
					success : function(data) {
						console.log(JSON.stringify(data));
						window.location.href = '${base}/admin/orderJp/list';
					},
					error : function() {
						alert("error");
					}
				}); 
			} */
			
			/* new Vue({
				el: '#wrapper',
				methods:{
					visaTypeChange:function(){//签证类型
						var visaTypeVal = $('[name="visatype"]').val();
						if(visaTypeVal == 2){
							$("#sixCounty").removeClass("none");
							$(".access").removeClass("none");
						}else{
							$("#sixCounty").addClass("none");
							$(".access").addClass("none");
						}
					},
					basicInfoClick:function(){//基本信息 弹层
						layer.open({
							type: 2,
							title: false,
							closeBtn:false,
							fix: false,
							maxmin: false,
							shadeClose: false,
							scrollbar: false,
							area: ['900px', '551px'],
							content:'/admin/firstTrialJp/basicInfo.html'
						});
					}
				}
			}); */
		</script>
	</body>
</html>
