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
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
		<style type="text/css">
			.form-control{height: 30px;}
			.add-btn{top:-225px;right:-1%;}
			.remove-btn{top: -225px;right: -1%;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" id="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>{{orderinfo.ordernum}}</p></span>
					<!-- <span class="">受付番号：<p>JDY27163</p></span> -->
					<span class="">状态：<p>{{orderinfo.status}}</p></span>
					<input type="button" value="取消" onclick="javascript:window.close()" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="保存" onclick="saveorder()" class="btn btn-primary btn-sm pull-right"/>
					<input type="button" value="回邮" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="快递" onclick="expressFun()" class="btn btn-primary btn-sm pull-right" />
					<input type="button" value="日志" @click="logs()" class="btn btn-primary btn-sm pull-right" />
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
										<input id="number" name="number" v-model="orderinfo.number" type="text" class="form-control input-sm" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();" placeholder=" "  />
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

							<!-- start 签证类型 -->
							<div class="row body-from-input">
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
										<div class="col-sm-8" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-8 none" id="visacounty">
									</c:otherwise>
								</c:choose>
											<div class="form-group viseType-btn">
												<label style="display: block;">&nbsp;</label> 
												<input name="visacounty" type="button" value="冲绳县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="青森县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="宫城县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="山形县" class="btn btn-sm btnState">
												<input name="visacounty" type="button" value="福鸟县" class="btn btn-sm btnState">
											</div>
										</div>
							</div>
							<!-- end 签证类型 -->
							
							<!-- start 过去三年是否访问过 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType == 2 }">
									<div class="row body-from-input" id="threefangwen">
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen">
								</c:otherwise>
							</c:choose>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>过去三年是否访问过：</label> 
											<select id="isVisit" class="form-control input-sm" v-model="orderinfo.isvisit">
												<c:forEach var="map" items="${obj.isyesornoenum}">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
										</div>
									</div>
										
									<div class="col-sm-8">
									<c:choose>
										<c:when test="${obj.jporderinfo.isVisit == 1 }">
											<div id="threexian" class="form-group viseType-btn">
										</c:when>
										<c:otherwise>
											<div id="threexian" class="form-group viseType-btn none">
										</c:otherwise>
									</c:choose>
												<label style="display: block;">&nbsp;</label> 
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState"> 
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState"> 
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
									</div>
							</div><!-- end 过去三年是否访问过 -->

							<div class="row body-from-input">
								<!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label> 
										<input id="gotripdate" type="text" class="form-control input-sm" v-model="orderinfo.gotripdate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="stayday" name="stayday" type="text" class="form-control input-sm mustNumber" v-model="orderinfo.stayday" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="this.v();"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="backtripdate" type="text" class="form-control input-sm" v-model="orderinfo.backtripdate"/>
									</div>
								</div>
							</div>
							<!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input">
								<!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>送签时间：</label>
										<input id="sendvisadate" type="text" class="form-control input-sm" v-model="orderinfo.sendvisadate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出签时间：</label>
										<input id="outvisadate" type="text" class="form-control input-sm" v-model="orderinfo.outvisadate"/>
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
									<tr v-cloak v-for="apply in applyinfo">
										<td>{{apply.applyname}}</td>
										<td>{{apply.telephone}}</td>
										<td>{{apply.passport}}</td>
										<td>{{apply.datatype}}</td>
										<td>{{apply.realinfo}}</td>
										<td>{{apply.sex}}</td>
										<td>{{apply.applicantstatus}}</td>
										<td>
											<a v-on:click="basicInfo(apply.applyid)">基本信息</a>&nbsp;&nbsp;
											<a v-on:click="passport(apply.applyid)">护照</a>&nbsp;&nbsp;
											<a v-on:click="visaInfo(apply.applyid)">签证信息</a><br>
											<a v-on:click="qualified(apply.applyid)">合格</a>&nbsp;&nbsp;
											<a v-on:click="unqualified(apply.applyid)">不合格</a>
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
	
					<!-- 回邮信息 -->
            		
						<c:choose>
	               		<c:when test="${fn:length(obj.backinfo)>0}">
	               			<div class="info expressInfo" id="expressInfo" name="backmailInfo">
	               			<p class="info-head">回邮信息</p>
			            	<c:forEach var="backmail" items="${obj.backinfo}" varStatus="status">
								<div class="info-body-from backmail-div">
									<div class="row body-from-input">
										<!-- 资料来源/快递号/团队名称/回邮方式 -->
										<input name="obmId" type="hidden" value="${backmail.id }">
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>资料来源：</label> 
												<select name="source" class="form-control input-sm">
													<c:forEach var="map" items="${obj.mainBackMailSourceTypeEnum}">
														<c:choose>
							                    			<c:when test="${backmail.source eq map.key }">
																<option value="${map.key}" selected="selected">${map.value}</option>
							                    			</c:when>
							                    			<c:otherwise>
																<option value="${map.key}">${map.value}</option>
							                    			</c:otherwise>
							                    		</c:choose>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>快递号：</label> 
												<input name="expressNum" type="text" value="${backmail.expressNum }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>团队名称：</label> 
												<input name="teamName" type="text" value="${backmail.teamName }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>回邮方式：</label> 
												<select name="expressType" class="form-control input-sm">
													<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
														<c:choose>
							                    			<c:when test="${backmail.expressType eq map.key }">
																<option value="${map.key}" selected="selected">${map.value}</option>
							                    			</c:when>
							                    			<c:otherwise>
																<option value="${map.key}">${map.value}</option>
							                    			</c:otherwise>
							                    		</c:choose>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<!-- end 资料来源/快递号/团队名称/回邮方式 -->
			
									<div class="row body-from-input" style="padding-left:0;">
										<!-- 回邮地址/联系人/电话 -->
										<div class="col-sm-6">
											<div class="form-group">
												<label><span>*</span>回邮地址：</label> 
												<input name="expressAddress" type="text" value="${backmail.expressAddress }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>联系人：</label> 
												<input name="linkman" type="text" value="${backmail.linkman }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>电话：</label> 
												<input name="telephone" type="text" value="${backmail.telephone }"  class="form-control input-sm" placeholder=" " />
											</div>
										</div>
									</div>
									<!-- end 回邮地址/联系人/电话/ -->
			
									<div class="row body-from-input" style="padding-left:0;">
										<!-- 发票项目内容/发票抬头/税号/备注 -->
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>发票项目内容：</label> 
												<input name="invoiceContent" type="text" value="${backmail.invoiceContent }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>发票抬头：</label> 
												<input name="invoiceHead" type="text" value="${backmail.invoiceHead }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>税号：</label> 
												<input name="taxNum" type="text" value="${backmail.taxNum }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
										<div class="col-sm-3">
											<div class="form-group">
												<label><span>*</span>备注：</label> 
												<input name="remark" type="text" value="${backmail.remark }" class="form-control input-sm" placeholder=" " />
											</div>
										</div>
									</div>
									<!-- end 发票项目内容/发票抬头/税号/备注 -->
									<c:if test="${status.index <= 0 }">
										<i class="add-btn"></i>
									</c:if>
									<c:if test="${status.index > 0 }">
										<i class="remove-btn"></i>
									</c:if>
									
								</div>
							</c:forEach>
						</div>
		            </c:when>
	            	<c:otherwise>
	            		<div class="info none expressInfo" id="expressInfo" name="backmailInfo">
	               			<p class="info-head">回邮信息</p>
		            		<div class="info-body-from backmail-div">
								<div class="row body-from-input">
									<!-- 资料来源/快递号/团队名称/回邮方式 -->
									<div class="col-sm-3">
										<div class="form-group">
											<input name="obmId" type="hidden" value="">
											<label><span>*</span>资料来源：</label> 
											<select name="source" class="form-control input-sm">
												<c:forEach var="map" items="${obj.mainBackMailSourceTypeEnum}">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>快递号：</label> 
											<input name="expressNum" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>团队名称：</label> 
											<input name="teamName" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>回邮方式：</label> 
											<select name="expressType" class="form-control input-sm">
												<c:forEach var="map" items="${obj.mainBackMailTypeEnum}">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<!-- end 资料来源/快递号/团队名称/回邮方式 -->
		
								<div class="row body-from-input" style="padding-left:0;">
									<!-- 回邮地址/联系人/电话 -->
									<div class="col-sm-6">
										<div class="form-group">
											<label><span>*</span>回邮地址：</label> 
											<input name="expressAddress" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>联系人：</label> 
											<input name="linkman" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>电话：</label> 
											<input name="telephone" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
								</div>
								<!-- end 回邮地址/联系人/电话/ -->
		
								<div class="row body-from-input" style="padding-left:0;">
									<!-- 发票项目内容/发票抬头/税号/备注 -->
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>发票项目内容：</label> 
											<input name="invoiceContent" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>发票抬头：</label> 
											<input name="invoiceHead" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>税号：</label> 
											<input name="taxNum" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
									<div class="col-sm-3">
										<div class="form-group">
											<label><span>*</span>备注：</label> 
											<input name="remark" type="text" class="form-control input-sm" placeholder=" " />
										</div>
									</div>
								</div>
								<!-- end 发票项目内容/发票抬头/税号/备注 -->
								<i class="add-btn"></i>
							</div>
						</div>
						<!-- end 快递信息 -->
	            	</c:otherwise>
           		</c:choose>
					
				</section>
			</div>
	
		</div>
		
		<script type="text/javascript">
			var BASE_PATH = '${base}';
			var orderid = '${obj.orderid}';
			var backinfo = '${obj.backinfo}';
			var visaCounty = '${obj.jporderinfo.visaCounty}';
			var threecounty = '${obj.jporderinfo.threeCounty}';
		</script>
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/vue/vue.min.js"></script>
		<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
		<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<!-- 本页面js文件 -->
		<script src="${base}/admin/firstTrialJp/backMailInfo.js"></script>
		<script src="${base}/admin/firstTrialJp/trialDetail.js"></script>
		<script src="${base}/admin/firstTrialJp/trialDate.js"></script>
		
	</body>
</html>
