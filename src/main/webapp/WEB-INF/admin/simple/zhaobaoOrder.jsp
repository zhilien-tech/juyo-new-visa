<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/orderJp" />
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equlv="proma" content="no-cache" />
<meta http-equlv="cache-control" content="no-cache" />
<meta http-equlv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>编辑订单</title>
<link rel="stylesheet" href="${base}/references/common/js/vue/vue-multiselect.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/references/public/css/style.css?v=<%=System.currentTimeMillis() %>">
<!-- 本页css -->
<link rel="stylesheet" href="${base}/references/common/css/simpleEditOrder.css?v=<%=System.currentTimeMillis() %>">
<!-- 加载中。。。样式 -->
<link rel="stylesheet" href="${base}/references/common/css/spinner.css?v=<%=System.currentTimeMillis() %>">
</head>

<body class="hold-transition skin-blue sidebar-mini">

	<div class="wrapper" id="wrapper">
		<div class="content-wrapper">
			<div class="qz-head">
				<!-- <span class="">订单号：<p>170202-JP0001</p></span> -->
				<!-- <span class="">受付番号：<p>JDY27163</p></span> -->
				<span class="">订单号：<p>${obj.orderinfo.orderNum }　</p></span>　　
				<span class="">受付番号：<p>${obj.orderjpinfo.acceptDesign }</p></span>
				<c:choose>
						<c:when test="${obj.orderstatus == '发招宝中'}">
							<span >状态：
						<p class="cateInfo">${obj.orderstatus }</p>
					</span> 
					<!-- 加载中 -->
					<div class="spinner">
					  <div class="bounce1"></div>
					  <div class="bounce2"></div>
					  <div class="bounce3"></div>
					</div>
					</c:when>
					<c:when test="${obj.orderstatus == '招宝成功'}">
						<span >状态：
							<p class="cateInfo"><font color="red">${obj.orderstatus }</font></p>
						</span> 
					</c:when>
					<c:otherwise>
						<span >状态：
							<p class="cateInfo">${obj.orderstatus }</p>
						</span> 
					</c:otherwise>
													
					</c:choose >
					<input type="button" value="取消" class="btn btn-primary btn-sm pull-right" onclick="cancelAddOrder();"/> 
					<input type="button" value="保存并返回" class="btn btn-primary btn-sm pull-right btn-ToBig" onclick="saveZhaobaoOrder(3);" />
					<input type="button" value="下载" class="btn btn-primary btn-sm pull-right download" onclick="downLoadFile()"/>
					<!-- <input type="button" value="拒签" class="btn btn-primary btn-sm pull-right" onclick="sendInsurance(27)"/> -->
					<c:choose>
						<c:when test="${obj.orderjpinfo.visaType == 14 }">
							<input type="button" value="招宝取消" class="btn btn_del btn-sm pull-right btn-Big" />
							<input type="button" value="招宝变更" class="btn btn_del btn-sm pull-right btn-Big" />
							<input type="button" value="发招宝" class="btn btn_del btn-sm pull-right"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${obj.orderinfo.status ==17 or obj.orderinfo.status == 20 }">
									<input type="button" value="招宝取消" class="btn btn-primary btn-sm pull-right btn-Big" onclick="sendInsurance(22)"/>
									<input type="button" value="招宝变更" class="btn btn-primary btn-sm pull-right btn-Big" onclick="sendInsurance(19)"/>
								</c:when>
								<c:otherwise>
									<input type="button" value="招宝取消" class="btn btn_del btn-sm pull-right btn-Big" />
									<input type="button" value="招宝变更" class="btn btn_del btn-sm pull-right btn-Big" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${obj.orderinfo.status !=17 && obj.orderinfo.status !=19 and obj.orderinfo.status !=20 and obj.orderinfo.status !=34 and obj.orderinfo.status !=35 and obj.orderinfo.status !=22}">
									<input type="button" value="发招宝" class="btn btn-primary btn-sm pull-right" onclick="sendzhaobao()"/>
								</c:when>
								<c:otherwise>
									<input type="button" value="发招宝" class="btn btn_del btn-sm pull-right"/>
								</c:otherwise>
							</c:choose>
						
						</c:otherwise>
					</c:choose>
					<!-- <input type="button" value="实收" class="btn btn-primary btn-sm pull-right" onclick="revenue()"/> -->
					<input type="button" value="日志" class="btn btn-primary btn-sm pull-right" onclick="log()"/>
				<input type="hidden" id="orderid" name="orderid" value="${obj.orderjpinfo.id }"/>
				
				
			</div>
			<section class="content">
				<form id="orderInfo">
					<div class="info" id="customerInfo" ref="customerInfo">
						<p class="info-head">客户信息</p>
						<!-- *** -->
						<div class="info-body-from"  style="margin-left:6%;">
							<div class="row body-from-input">
								<!-- input 直客 -->
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>签证类型：</label> <select id="visatype"
											name="visatype" type="text" class="form-control input-sm" tabindex="5">
											<option value="">请选择</option>
												<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
													<c:choose>
														<c:when test="${map.key eq obj.orderjpinfo.visaType }">
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
							<!-- end input 直客 -->
						</div>
					</div>
					<!-- end 客户信息 -->
					<!-- 订单信息 -->
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">订单信息</p>
						<div class="info-body-from" style="margin-left:6%;">
						<div class="row body-from-input">
						</div>
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>领区：</label> <select
											class="form-control input-sm" id="cityid" name="cityid" tabindex="7">
											<option value="">请选择</option>
											<c:forEach var="map" items="${obj.collarAreaEnum}">
												<c:choose>
													<c:when test="${map.key eq obj.orderinfo.cityId}">
														<option value="${map.key}" selected="selected">${map.value}</option>
													</c:when>
													<c:otherwise>
														<option value="${map.key}">${map.value}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
										<!-- <i class="bulb"></i> 小灯泡-->
									</div>
								</div>
								</div>
							</div>
							<!-- end 人数/领区/加急 -->

							<!-- end 送签时间/出签时间 -->
						</div>
					
					<div class="orderInfo info" id="orderInfo">
						<p class="info-head">出行信息</p>
						<div class="info-body-from" style="margin-left:6%;">
							<div class="row body-from-input">
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>出行时间：</label>
										<input id="goDate" name="" tabindex="14" type="text" class="form-control input-sm datetimepickercss" autocomplete="off" value="<fmt:formatDate value="${obj.tripinfo.goDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>停留天数：</label>
										<input id="stayday" type="text" tabindex="15" class="form-control input-sm" autocomplete="off" value="${obj.orderinfo.stayDay }"/>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label><span>*</span>返回时间：</label>
										<input id="returnDate" type="text" class="form-control input-sm datetimepickercss" autocomplete="off" tabindex="16" value="<fmt:formatDate value="${obj.tripinfo.returnDate }" pattern="yyyy-MM-dd" />"/>
									</div>
								</div>
							</div>
							
							
						</div>
					</div>
					<!-- end 订单信息 -->
					<div class="row body-from-input" id="applicantInfo">
						<!-- 添加申请人 -->
						<div class="col-sm-12">
							<div class="form-group">
								<button type="button"
									class="btn btn-primary btn-sm addApplicantBtn">添加申请人</button>
							</div>
						</div>
					</div>
					<!-- end 添加申请人 -->

					<div class="info none" id="mySwitch">
						<!-- 主申请人 -->
						<input type="hidden" id="appId" value="" name="appId"/>
						<p class="info-head"><span>申请人</span>
							<input type="button" name="" value="添加"
								class="btn btn-primary btn-sm pull-right "
								onclick="addApplicant();" />
						</p>
						<div class="info-table" style="padding-bottom: 1px;">
							<table id="principalApplicantTable" class="table table-hover"
								style="width: 100%;">
								<thead>
									<tr>
										<th><span>&nbsp;</span></th>
										<th><span>姓名</span></th>
										<th><span>手机号</span></th>
										<th><span>护照号</span></th>
										<th><span>资料类型</span></th>
										<th><span>签证所需资料</span></th>
										<th><span>备注</span></th>
										<th><span>操作</span></th>
									</tr>
								</thead>
								<tbody name="applicantsTable" id="applicantsTable">

								</tbody>
							</table>
						</div>
					</div>
				</form>
			</section>
		</div>

	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var orderid = '${obj.orderjpinfo.id}';
		var cityidstr = '${obj.orderinfo.cityId}';
		var visatype = '${obj.orderjpinfo.visaType}';
		let sendVisaDateVal = "${obj.orderinfo.sendVisaDate }";
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script>
	<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
	<!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<%-- <script src="${base}/admin/orderJp/order.js"></script> --%>
	<!-- 本页面js文件 -->
	<!-- select2 -->
	<script src="${base}/references/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/references/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/admin/simple/customerInfo.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${base}/admin/simple/travelinfo.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/initpagedata.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script src="${base}/admin/simple/addsimpleorder.js?v=<%=System.currentTimeMillis() %>"></script><!-- 本页面js文件 -->
	<script type="text/javascript" src="${base}/admin/common/commonjs.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
	
		if('${obj.ordertype}' == 1){
			$(".download").hide();
		}else{
			$(".download").show();
		}
		
		//加载申请人表格数据
		initApplicantTable();
		//加载行程安排表格数据
		initTravelPlanTable();
		
		if(cityidstr > 2){
			$(".transfer").show();
			$(".direct").hide();
		}else{
			$(".transfer").hide();
			$(".direct").show();
		}
		
		$("#cityid").change(function(){
			var cityid = $(this).val();
			$("#cityid").val(cityid);
			cityidstr = cityid;
			if($(this).val() == ""){
				
			}else if($(this).val() > 2){
				$(".transfer").show();
				$(".direct").hide();
				$("#goDepartureCity").empty();
				$("#goArrivedCity").empty();
				$("#goFlightNum").empty();
				$("#returnDepartureCity").empty();
				$("#returnArrivedCity").empty();
				$("#returnFlightNum").empty();
			}else{
				$(".transfer").hide();
				$(".direct").show();
				$("#newgodeparturecity").empty();
				$("#gotransferarrivedcity").empty();
				$("#gotransferflightnum").empty();
				$("#gotransferdeparturecity").empty();
				$("#newgoarrivedcity").empty();
				$("#newgoflightnum").empty();
				$("#newreturndeparturecity").empty();
				$("#returntransferarrivedcity").empty();
				$("#returntransferflightnum").empty();
				$("#returntransferdeparturecity").empty();
				$("#newreturnarrivedcity").empty();
				$("#newreturnflightnum").empty();
			}
		});
		
		$("#visatype").change(function(){
			var vtype = $(this).val();
			visatype = vtype;
			var goArrivedCity = $("#goArrivedCity").val();
			var returnDepartureCity = $("#returnDepartureCity").val();
			//冲绳
			if(vtype == 2 || vtype == 7){
				$("#goArrivedCity").html('<option selected="selected" value="'+77+'">'+'冲绳'+'</option>');
				$("#returnDepartureCity").html('<option selected="selected" value="'+77+'">'+'冲绳'+'</option>');
				if(goArrivedCity != 77){
					$('#goFlightNum').empty();
				}
				if(returnDepartureCity != 77){
					$('#returnFlightNum').empty();
				}
			}
			
			if(vtype == 14){
				$("input[type=button][value=招宝变更]").attr("class","btn btn_del btn-sm pull-right btn-Big");
				$("input[type=button][value=招宝取消]").attr("class","btn btn_del btn-sm pull-right btn-Big");
				$("input[type=button][value=发招宝]").attr("class","btn btn_del btn-sm pull-right");
			}else{
				$("input[type=button][value=招宝变更]").attr("class","btn btn-primary btn-sm pull-right btn-Big");
				$("input[type=button][value=招宝取消]").attr("class","btn btn-primary btn-sm pull-right btn-Big");
				$("input[type=button][value=发招宝]").attr("class","btn btn-primary btn-sm pull-right");
			}
			/* //宫城
			if(vtype == 3 || vtype == 8){
				$("#goArrivedCity").html('<option selected="selected" value="'+91+'">'+'宫城'+'</option>');
				if(goArrivedCity != 91){
					$('#goFlightNum').empty();
				}
			}
			//岩手
			if(vtype == 4 || vtype == 10){
				$("#goArrivedCity").html('<option selected="selected" value="'+92+'">'+'岩手'+'</option>');
				if(goArrivedCity != 92){
					$('#goFlightNum').empty();
				}
			}
			//福岛
			if(vtype == 5 || vtype == 9){
				$("#goArrivedCity").html('<option selected="selected" value="'+30+'">'+'福岛'+'</option>');
				if(goArrivedCity != 30){
					$('#goFlightNum').empty();
				}
			}
			//青森
			if(vtype == 11){
				$("#goArrivedCity").html('<option selected="selected" value="'+25+'">'+'青森'+'</option>');
				if(goArrivedCity != 25){
					$('#goFlightNum').empty();
				}
			}
			//秋田
			if(vtype == 12){
				$("#goArrivedCity").html('<option selected="selected" value="'+612+'">'+'秋田'+'</option>');
				if(goArrivedCity != 612){
					$('#goFlightNum').empty();
				}
			}
			//山形
			if(vtype == 13){
				$("#goArrivedCity").html('<option selected="selected" value="'+613+'">'+'山形'+'</option>');
				if(goArrivedCity != 613){
					$('#goFlightNum').empty();
				}
			} */
			var orderid = $('#orderid').val();
			$.ajax({ 
		    	url: '${base}/admin/simple/changeVisatype.html',
		    	dataType:"json",
		    	data:{
		    		orderid:orderid,
		    		visatype:vtype
		    		},
		    	type:'post',
		    	success: function(data){
		    		//window.location.reload();
		      	}
		    });
		});
		
		$(function(){
			$('#urgentType').change(function(){
				var urgentType = $(this).val();
				if(urgentType != 1){
					$("#urgentDays").removeClass("none");
				}else{
					$("#urgentDays").addClass("none");
				}
			});
			
			if("${obj.customerinfo.source}" == 4){
				$('.zhiKe').removeClass('none');
				$('.on-line').addClass('none');
				$('#customamount').removeClass('none');
			}else{
				$('.zhiKe').addClass('none');
				$('.on-line').removeClass('none');
				$('#customamount').addClass('none');
			}
			
			//$('#customerType').trigger('change');
		});
			$("#addCustomer").click(function(){
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['800px', '400px'],
					content: '/admin/simple/customer/add.html?isCustomerAdd=0'
				});
		});
			//其他页面回调
			function successCallBack(status){
				if(status == 1){
					layer.msg('修改成功');
				}
				if(status == 2){
					layer.msg('保存成功');
				}
				if(status == 3){
				    layer.msg('添加成功');
				}
				if(status == 4){
				    layer.msg('删除成功');
				}
				initApplicantTable();
				initTravelPlanTable();
				initOrderstatus();
			}
			
			//初始化页面状态
			function initOrderstatus(){
				var orderid = $('#orderid').val();
				$.ajax({ 
			    	url: '${base}/admin/visaJapan/initOrderstatus.html',
			    	dataType:"json",
			    	data:{orderid:orderid},
			    	type:'post',
			    	success: function(data){
			    		$('.cateInfo').html(data.orderstatus);
			    		if(data.orderstatus == '发招宝中'){
			    			$('.spinner').show();
			    		}else{
			    			$('.spinner').hide();
			    		}
			      	}
			    }); 
			}
			
			function cancelCallBack(status){
				successCallBack(6);
			}
			
			//修改申请人基本信息
			function updateApplicant(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/updateApplicant.html?applicantid='+id+'&orderid='+orderid
				});
			}
				
			//修改申请人护照信息
			
			function passportInfo(applicantId){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/passportInfo.html?applicantid='+applicantId+'&orderid='+orderid
				});
			}
			
			//签证信息
			function visaInfo(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content:'/admin/simple/visaInfo.html?applicantid='+id+'&orderid='+orderid
				});
			}
			
			//签证录入
			function visaInput(id){
				var orderid = $('#orderid').val();
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['900px', '80%'],
					content: '/admin/visaJapan/visaInput.html?applyid='+id+'&orderid='+orderid
				});
			}
			
			//删除申请人
			function deleteApplicant(id){
				layer.confirm("您确认要删除吗？", {
					title:"删除",
					btn: ["是","否"], //按钮
					shade: false //不显示遮罩
				}, function(){
				$.ajax({ 
			    	url: '${base}/admin/orderJp/deleteApplicant',
			    	dataType:"json",
			    	data:{applicantId:id},
			    	type:'post',
			    	success: function(data){
			    		successCallBack(4);
			      	}
			    }); 
			});
			}
			
			//下单取消
			function cancelAddOrder(){
				
				/* $.ajax({ 
			    	url: '${base}/admin/simple/getSomething.html',
			    	dataType:"json",
			    	type:'post',
			    	success: function(data){
			    		//window.location.reload();
			      	}
			    }); */
				
				window.close();
			}
			function successAddCustomer(data){
				
			}
			
			function log(orderid){//日志
				var orderinfoid = '${obj.orderinfo.id}';
				layer.open({
					type: 2,
					title: false,
					closeBtn:false,
					fix: false,
					maxmin: false,
					shadeClose: false,
					scrollbar: false,
					area: ['700px', '80%'],
					content:'/admin/orderJp/log.html?id='+orderinfoid+'&orderProcessType=1'
				});
			}
			//招宝变更、招宝取消、拒签
			function sendInsurance(visastatus){
				var title = '';
        		if(visastatus == 19 || visastatus == 22|| visastatus == 27|| visastatus == 26){
        			if(visastatus == 19){
        				title = '确定要招宝变更吗？';
        			}else if(visastatus == 22){
        				title = '确定要招宝取消吗？';
        			}else if(visastatus == 27){
        				title = '确定要拒签吗？';
        			}
        			layer.confirm(title, {
    					title:"提示",
    					btn: ["是","否"], //按钮
    					shade: false //不显示遮罩
    				}, function(index){
    					$.ajax({
    	                 	url: '${base}/admin/visaJapan/sendInsurance',
    	                 	data:{orderid:orderid,visastatus:visastatus},
    	                 	dataType:"json",
    	                 	type:'post',
    	                 	success: function(data){
    	                 		if(visastatus == 19){
    		                 		layer.msg('招宝变更');
    	                 		}else if(visastatus == 22){
    		                 		layer.msg('招宝取消');
    	                 		}else if(visastatus == 27){
    		                 		layer.msg('报告拒签');
    	                 		}else if(visastatus == 26){
    		                 		layer.msg('已作废');
    	                 		}
    	                 		initOrderstatus();
    	                 		layer.close(index);
    	                   	}
    	                 });
    				});
         		}else{
         			$.ajax({
	                 	url: '${base}/admin/visaJapan/sendInsurance.html',
	                 	data:{orderid:orderid,visastatus:visastatus},
	                 	dataType:"json",
	                 	type:'post',
	                 	success: function(data){
	                 		if(visastatus == 19){
		                 		layer.msg('招宝变更');
	                 		}else if(visastatus == 22){
		                 		layer.msg('招宝取消');
	                 		}else if(visastatus == 27){
		                 		layer.msg('报告拒签');
	                 		}
	                 		initOrderstatus();
	                 		layer.close(index);
	                   	}
	                 });
         		}
			}
			//实收弹框
			function revenue(){
				layer.open({
        		    type: 2,
        		    title: false,
        		    closeBtn:false,
        		    fix: false,
        		    maxmin: false,
        		    shadeClose: false,
        		    scrollbar: false,
        		    area: ['900px', '80%'],
        		    content: '${base}/admin/visaJapan/revenue.html?orderid='+orderid+'&type=1'
        		  });
			}
			//发招宝
			function sendzhaobao(){
				$.ajax({
                 	url: '${base}/admin/visaJapan/validateInfoIsFull.html',
                 	data:{orderjpid:orderid},
                 	dataType:"json",
                 	type:'post',
                 	async:false,
                 	success: function(data){
                 		var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
                 		if(data.data){
                 			url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data+'&type=1';
                 		}
		        		layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['400px', '300px'],
		        		    content: url
		        		  });
                   	}
                 });
			}
			
			function downLoadFile(){
				var visatype = $("#visatype").val();
				
				if(visatype == 14){
         			var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid+'&visatype='+visatype;
	        		layer.open({
	        		    type: 2,
	        		    title: false,
	        		    closeBtn:false,
	        		    fix: false,
	        		    maxmin: false,
	        		    shadeClose: false,
	        		    scrollbar: false,
	        		    area: ['400px', '300px'],
	        		    content: url
	        		  });
     				
     			}else{
					$.ajax({
	                 	url: '${base}/admin/visaJapan/validateDownLoadInfoIsFull.html',
	                 	data:{orderjpid:orderid},
	                 	dataType:"json",
	                 	type:'post',
	                 	async:false,
	                 	success: function(data){
	                 		//var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
	                 		if(data.data){
	                 			var url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data+'&type=1';
				        		layer.open({
				        		    type: 2,
				        		    title: false,
				        		    closeBtn:false,
				        		    fix: false,
				        		    maxmin: false,
				        		    shadeClose: false,
				        		    scrollbar: false,
				        		    area: ['400px', '300px'],
				        		    content: url
				        		  });
	                 		}else{
	                 				layer.load(1);
									$.fileDownload("/admin/visaJapan/downloadFile.html?orderid=${obj.orderjpinfo.id}", {
								        successCallback: function (url) {
								        	layer.closeAll('loading');
								        },
								        failCallback: function (html, url) {
								        	layer.closeAll('loading');
								       		layer.msg('下载失败');
								        }
								    });
	                 		}
	                   	}
	                 });
     				
     			}
				
				
			}
			function clearplan(){
					$( ".select2-selection__choice").val("");
			}
			
			function saveZhaobaoOrderFunction(data){
				saveZhaobaoOrder(2);
			}
			
		</script>
</body>
</html>
