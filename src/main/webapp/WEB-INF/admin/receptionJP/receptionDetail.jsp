<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>前台详情</title>
		<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			.wrapper { background:#f9f9f9 !important; }
			.form-control{height: 30px;}
			.add-btn{top: -35px;right:-1.5%;}
			.remove-btn{top: -35px;right: -1.5%;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.btnState{color: #b0b0b0 !important;border: solid 1px #d2d6de;background-color: #fff;margin-right: 2.26rem;}
			.btnState-true{color: #287ae7 !important;border-color: #cee1ff;}
			.deposit,.vehicle,.houseProperty{display:none;}
			#urgentDays { width:16.5%;}
			.info-body-from { margin-left:12%;}
			.qz-head { position:fixed;top:0;left:0;z-index:99999; width:100%;}
			.content { margin-top:50px;}
			#applicantTable tbody tr td:nth-child(1){width: 4%;}
			#applicantTable tbody tr td:nth-child(2){width: 10%;}
			#applicantTable tbody tr td:nth-child(3){width: 10%;}
			#applicantTable tbody tr td:nth-child(4){width: 10%;}
			#applicantTable tbody tr td:nth-child(5){width: 10%;}
			#applicantTable tbody tr td:nth-child(6){width: 56%;}
			#schedulingTable thead tr th:nth-child(1){width:8%;}
			#schedulingTable thead tr th:nth-child(2){width:10%;}
			#schedulingTable thead tr th:nth-child(3){width:12%;}
			#schedulingTable thead tr th:nth-child(4){width:24%;}
			#schedulingTable thead tr th:nth-child(5){width:24%;}
			#schedulingTable thead tr th:nth-child(6){width:8%;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper" >
			<div class="content-wrapper"  style="min-height: 848px;">
				<div class="qz-head">
					<span class="">订单号：<p>${obj.orderinfo.orderNum }</p></span>
					<span class="">受付番号：<p></p></span>
					<span class="">状态：<p id="orStatus_p">${obj.orStatus }</p></span>
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" onclick="javascript:window.close()"/>
					<input type="button" value="保存" class="btn btn-primary btn-sm pull-right" onclick="commitdata();"/>
					<input type="button" value="短信" class="btn btn-primary btn-sm pull-right" onclick="sendSms(${obj.orderinfo.id});"/>
					<input type="button" value="签证" class="btn btn-primary btn-sm pull-right" onclick="visaTransfer();"/>
					<input type="button" value="实收" class="btn btn-primary btn-sm pull-right" onclick="revenue();"/>
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" onclick="log(${obj.orderinfo.id});" />
				</div>
				<section class="content" id="wrapper">
					<!-- 订单信息 -->
					<div class="info">
						<p class="info-head">订单信息</p>
						<div class="info-body-from">
							<div class="row body-from-input"><!-- 人数/领区/加急 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>人数：</label>
										<input id="number" name="number" type="text" class="form-control input-sm mustNumber" placeholder=" " v-model="orderinfo.number"/>
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
								<div class="col-sm-1">
									<div class="form-group">
										<label><span>*</span>加急：</label>
										<select class="form-control input-sm" v-model="orderinfo.urgenttype" id="urgentType" name="urgenttype">
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
										<select class="form-control input-sm" v-model="orderinfo.urgentday" name="urgentday">
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
										<input id="money" name="money" type="text" class="form-control input-sm mustNumberPoint" placeholder=" " v-model="orderinfo.money" />
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
									<c:when test="${obj.jporderinfo.visaType == 2 || obj.jporderinfo.visaType == 3 || obj.jporderinfo.visaType == 4 }">
										<div class="col-sm-9" id="visacounty">
									</c:when>
									<c:otherwise>
										<div class="col-sm-9 none" id="visacounty">
									</c:otherwise>
								</c:choose>
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
							</div><!-- end 签证类型 -->
							<c:choose>
								<c:when test="${obj.jporderinfo.visaType == 2 || obj.jporderinfo.visaType == 3 || obj.jporderinfo.visaType == 4 }">
									<div class="row body-from-input" id="threefangwen"><!-- 过去三年是否访问过 -->
								</c:when>
								<c:otherwise>
									<div class="row body-from-input none" id="threefangwen"><!-- 过去三年是否访问过 -->
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
										<div class="col-sm-9">
										<c:choose>
											<c:when test="${obj.jporderinfo.isVisit == 1 }">
												<div id="threexian" class="form-group viseType-btn">
											</c:when>
											<c:otherwise>
												<div id="threexian" class="form-group viseType-btn none">
											</c:otherwise>
										</c:choose>
												<label style="display:block;">&nbsp;</label>
												<input name="threecounty" type="button" value="岩手县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="秋田县" class="btn btn-sm btnState">
												<input name="threecounty" type="button" value="山形县" class="btn btn-sm btnState">
											</div>
										</div>
									</div><!-- end 过去三年是否访问过 -->
							<div class="row body-from-input target"><!-- 出行时间/停留天数/返回时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="gotripdate" type="text" class="form-control input-sm"  v-model="orderinfo.gotripdate"/>
										<!-- <date-picker field="myDate" placeholder="选择日期"
											 :no-today="true"
											 :value.sync="date3"
											 :format="format"
											 v-model="orderinfo.gotripdate"></date-picker> -->
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>行程天数：</label>
										<input id="stayday" name="stayday" type="text" class="form-control input-sm mustNumber" v-model="orderinfo.stayday"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="backtripdate" type="text" class="form-control input-sm"  v-model="orderinfo.backtripdate"/>
									</div>
								</div>
							</div><!-- end 出行时间/停留天数/返回时间 -->
							<div class="row body-from-input"><!-- 送签时间/出签时间 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计送签时间：</label>
										<input id="sendvisadate" type="text" class="form-control input-sm"  v-model="orderinfo.sendvisadate"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>预计出签时间：</label>
										<input id="outvisadate" type="text" class="form-control input-sm"  v-model="orderinfo.outvisadate"/>
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
										<th><span>&nbsp; </span></th>
										<th><span>姓名</span></th>
										<th><span>电话</span></th>
										<th><span>护照号</span></th>
										<th><span>资料类型</span></th>
										<th><span>审核确认资料</span></th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="apply in applyinfo">
										<td>
											<div v-if="apply.applicantid==apply.mainid">
												<font color="blue">主</font> 
											</div>
											<div v-else></div>
										</td>
										<td>{{apply.applyname}}</td>
										<td>{{apply.telephone}}</td>
										<td>{{apply.passport}}</td>
										<td>{{apply.type}}</td>
										<td>{{apply.realinfo}}</td>
										<!-- <td><a v-on:click="passport(apply.applyid)">护照</a>&nbsp;&nbsp;<a v-on:click="visa(apply.applyid)">签证</a></td> -->
									</tr>
								</tbody>
							</table>
							<!-- end 申请人 -->
							<!-- <div class="row" id="orderremark">
								<div class="col-sm-12">
									<div class="form-group">
										<label>备注：</label> 
										<input id="remark" name="remark" type="text" class="form-control input-sm" v-model="orderinfo.remark"/>
									</div>
								</div>
							</div> -->
						</div>
					</div>
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
		<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
		<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
		<script src="${base}/references/common/js/vue/vue-multiselect.min.js"></script>
		<script type="text/javascript" src="${base}/admin/common/commonjs.js"></script>
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script src="${base}/admin/receptionJP/receptionDetail.js"></script><!-- 本页面js文件 -->
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
			
			//日志
			function log(orderid){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['700px', '80%'],
					content:'/admin/orderJp/log.html?id='+orderid
				});
			}
			
			//移交签证
			function visaTransfer(){
				var orderid = ${obj.orderid};
				$.ajax({ 
			     	url: BASE_PATH +'/admin/receptionJP/visaTransfer',
			     	dataType:"json",
			     	data:{orderid:orderid},
			     	type:'post',
			     	success: function(data){
			     		successCallBack(3);
			       	}
			     });
			}
			
			//实收
			function revenue(){
				var orderid = ${obj.orderid};
				layer.open({
        		    type: 2,
        		    title: false,
        		    closeBtn:false,
        		    fix: false,
        		    maxmin: false,
        		    shadeClose: false,
        		    scrollbar: false,
        		    area: ['900px', '80%'],
        		    content: '${base}/admin/receptionJP/revenue.html?orderid='+orderid
        		  });
			}
			
			//发短信
			function sendSms(orderid){
				layer.load(1);
        		$.ajax({ 
                 	url: '${base}/admin/receptionJP/sendSms.html',
                 	dataType:"json",
                 	data:{orderid:orderid},
                 	type:'post',
                 	success: function(data){
                 		layer.closeAll("loading");
                 		layer.msg("发送成功");
                   	}
                 });
			}
			
			$("#stayday").keyup(function(){
				var go = $("#gotripdate").val();
				var back = $("#backtripdate").val();
				var day = $("#stayday").val();
				if(go != "" && day != ""){
					var days = getNewDay(go,day-1);
					$("#backtripdate").val(days); 
					orderobj.orderInfo.backtripdate = days;
				}
			});
			//日期转换
			function getNewDay(dateTemp, days) {  
			    var dateTemp = dateTemp.split("-");  
			    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
			    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
			    var rDate = new Date(millSeconds);  
			    var year = rDate.getFullYear();  
			    var month = rDate.getMonth() + 1;  
			    if (month < 10) month = "0" + month;  
			    var date = rDate.getDate();  
			    if (date < 10) date = "0" + date;  
			    return (year + "-" + month + "-" + date);  
			} 
			
			
			$("#money").blur(function(){
				var money = $("#money").val();
				if(money != "" ){
					var moneys = returnFloat(money);
					$("#money").val(moneys); 
				}
			});
			//数字保留两位小数
			function returnFloat(value){
				var value=Math.round(parseFloat(value)*100)/100;
				var xsd=value.toString().split(".");
				if(xsd.length==1){
					value=value.toString()+".00";
				 	return value;
				}
				if(xsd.length>1){
					if(xsd[1].length<2){
				  		value=value.toString()+"0";
				 	}
				 	return value;
				 }
			}
			
			//时间插件格式化  出行时间>今天>送签时间 
			var now = new Date();
			$("#gotripdate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate:now,
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			}).on("click",function(){  
			    $("#gotripdate").datetimepicker("setEndDate",$("#backtripdate").val());  
			}); 
			$("#backtripdate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate:now,
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			});

			$("#sendvisadate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				startDate: now,//日期小于今天
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			}).on("click",function(){  
			    $("#sendvisadate").datetimepicker("setEndDate",$("#gotripdate").val());  
			}).on("changeDate",function(){
				//自动计算预计出签时间
				var stayday = 7;
				var sendvisadate = $("#sendvisadate").val();
				var days = getNewDay(sendvisadate,stayday);
				$("#outvisadate").val(days); 
			}); 
			$("#outvisadate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
				autoclose: true,//选中日期后 自动关闭
				pickerPosition:"top-left",//显示位置
				minView: "month"//只显示年月日
			});
			
		
			
		</script>
	</body>
</html>
