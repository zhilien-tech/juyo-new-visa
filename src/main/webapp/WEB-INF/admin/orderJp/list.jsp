<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>销售-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/saleJapan.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<style type="text/css">
	 [v-cloak]{display:none;}
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" id="wrapper">
		<div class="content-wrapper"  style="min-height: 848px;">
				<!-- <ul class="title">
					<li>销售</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul> -->
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
									<c:forEach var="map" items="${data.status}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="source" name="source" onchange="countryChange();">
									<option value="">客户来源</option>
									<c:forEach var="map" items="${obj.customerTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="visaType" name="visaType" onchange="countryChange();">
									<option value="">签证类型</option>
									<c:forEach var="map" items="${obj.mainSaleVisaTypeEnum}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/护照/公司简称/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter();"/>
							</div>
							<div class="col-md-3 left-5px" >
								<a class="btn btn-primary btn-sm pull-left"  id="searchbtn">搜索</a>
								<a id="emptyBtn" class="btn btn-primary btn-sm pull-left">清空</a> 
								<a class="btn btn-primary btn-sm pull-right" id="orderBtn" onclick="addOrder();" v-on:click="">下单</a>
							</div>
						</div>
						<div class="row" style="margin-top:15px;"> 
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="start_time" name="start_time" placeholder="创建日期" onchange="countryChange();"/>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="sendSignDate" name="sendSignDate" placeholder="送签时间" onchange="countryChange();"/>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="signOutDate" name="signOutDate" placeholder="出签时间" onchange="countryChange();"/>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in orderJpData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.ordernum}}</span></div>	
								<div><label>人数：</label><span>{{data.peoplenum}}</span></div>	
								<div><label>状态：</label><span>{{data.status}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="order(data.orderid)"> </i>
									<i class="share" v-on:click="share(data.orderid)"> </i>
									<i class="theTrial" v-on:click="theTrial(data.orderid)"> </i>
									<i class="return" > </i>
									<i class="toVoid" > </i>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" >
									<div><label>公司简称：</label><span>{{data.comshortname}}</span></div>
									<div><label>客户来源：</label><span>{{data.source}}</span></div>
									<div><label>联系人：</label><span>{{data.linkman}}</span></div>
									<div><label>电话：</label><span>{{data.telephone}}</span></div>
									<div><label>申请人：</label><span>{{data.applicants}}</span></div>
									<div><!-- <i> </i> --></div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
		</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<%-- <script src="${base}/admin/orderJp/listCard.js"></script> --%>
	<!-- 公用js文件 -->
		<script src="${base}/references/public/bootstrap/js/moment.js"></script>
	<script src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		//异步加载的URL地址
	    var url="${base}/admin/orderJp/listData";
	    //vue表格数据对象
	    var _self;
		new Vue({
			el: '#wrapper',
			data: {orderJpData:""},
			created:function(){
	            _self=this;
	            $.ajax({ 
	            	url: url,
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.orderJpData = data.orderJp;
	              	}
	            });
	        },
	        methods:{
	        	order:function(id){
	        			window.open('${base}/admin/orderJp/order.html'+(id > 0?('?id='+id):''));//跳转到更新页面
	        			//window.location.href = '${base}/admin/orderJp/order.html?id='+id;
	        	},
	        	share:function(id){//分享
					layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '551px'],
						content:'/admin/orderJp/share.html?id='+id
					});
				},
				theTrial:function(id){
					$.ajax({ 
				    	url: '${base}/admin/orderJp/firtTrialJp',
				    	dataType:"json",
				    	data:{orderId:id},
				    	type:'post',
				    	success: function(data){
				    		
				      	}
				    }); 
				}
	        	
	        } 
		});
		
		/*清空*/
		$("#emptyBtn").click(function(){
			$("#searchStr").val("");
			$("#status").val("");
			$("#source").val("");
			$("#visaType").val("");
			$("#sendSignDate").val("");
			$("#signOutDate").val("");
			$("#start_time").val("");
			$("#searchbtn").click();
		});
		$("#searchbtn").click(function(){
			var status = $('#status').val();
			var source = $('#source').val();
			var visaType = $('#visaType').val();
			var sendSignDate = $('#sendSignDate').val();
			var signOutDate = $('#signOutDate').val();
			var searchStr = $('#searchStr').val();
			var startTime = $('#start_time').val();
			$.ajax({ 
	        	url: url,
	        	data:{status:status,source:source,starttime:startTime,visaType:visaType,sendSignDate:sendSignDate,signOutDate:signOutDate,searchStr:searchStr},
	        	dataType:"json",
	        	type:'post',
	        	success: function(data){
	        		_self.orderJpData = data.orderJp;
	        		//console.log(JSON.stringify(data));
	          	}
	        });
		});
		/* function search(){
			var status = $('#status').val();
			var source = $('#source').val();
			var visaType = $('#visaType').val();
			var sendSignDate = $('#sendSignDate').val();
			var signOutDate = $('#signOutDate').val();
			var searchStr = $('#searchStr').val();
			$.ajax({ 
	        	url: url,
	        	data:{status:status,source:source,visaType:visaType,sendSignDate:sendSignDate,signOutDate:signOutDate,searchStr:searchStr},
	        	dataType:"json",
	        	type:'post',
	        	success: function(data){
	        		_self.orderJpData = data.orderJp;
	        		//console.log(JSON.stringify(data));
	          	}
	        });
		} */
		//跳转添加页
		 function addOrder(){
			window.location.href = '${base}/admin/orderJp/addOrder';
		}  
		
		function countryChange(){
			$("#searchbtn").click();
		}
		
		//搜索回车事件
		 function onkeyEnter(){
			    var e = window.event || arguments.callee.caller.arguments[0];
			    if(e && e.keyCode == 13){
					 $("#searchbtn").click();
				 }
			}
		
		 $("#sendSignDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
		        weekStart: 1,
		        todayBtn: 1,
				autoclose: true,
				todayHighlight: true,//高亮
				startView: 4,//从年开始选择
				forceParse: 0,
		        showMeridian: false,
				pickerPosition:"bottom-left",//显示位置
				minView: "month"//只显示年月日
			});
		 $("#signOutDate").datetimepicker({
				format: 'yyyy-mm-dd',
				language: 'zh-CN',
		        weekStart: 1,
		        todayBtn: 1,
				autoclose: true,
				todayHighlight: true,//高亮
				startView: 4,//从年开始选择
				forceParse: 0,
		        showMeridian: false,
				pickerPosition:"bottom-left",//显示位置
				minView: "month"//只显示年月日
			});
		 
		 $("#start_time").daterangepicker(null, function(start, end, label) {
             console.log(start.toISOString(), end.toISOString(), label);
         });
	</script>
</body>
</html>
