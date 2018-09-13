<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equlv="proma" content="no-cache" />
	<meta http-equlv="cache-control" content="no-cache" />
	<meta http-equlv="expires" content="0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>地接社-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
    <!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simpleList.css?v=<%=System.currentTimeMillis() %>">
	<!-- 加载中。。。样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/spinner.css?v=<%=System.currentTimeMillis() %>">
  	<link rel="stylesheet" href="${base}/references/public/css/visaJapan.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<style type="text/css">
		.card-head div:nth-child(1){width:17%;}
		.card-head div:nth-child(2){width:15%;}
		.card-head div:nth-child(3){width: 23%;}
		.card-head div:nth-child(4){width: 10%;}
		.card-head div:nth-child(5){width: 10%; text-align:right;}
		.card-head div:nth-child(6){width:24%;}
		.everybody-info div:nth-child(1){width:13%;}
		.everybody-info div:nth-child(2){width:20%;}
		.everybody-info div:nth-child(3){width:8%;}
		.everybody-info div:nth-child(4){width:12%;}
		.everybody-info div:nth-child(5){width:19%;}
		.everybody-info div:nth-child(5){width:19%;}
		.everybody-info div:nth-child(6){width:19%;}
		/* .everybody-info div:last-child{float:right;width:74px;} */
		.cf span { font-size:12px;}
		[v-cloak] {
		  display: none;
		}
		.card-djtj{
		    top: 70px;
		    left: 1px;
		    width: 100%;
		    padding: 0 25px;
		    height: 50px;
		    line-height: 50px;
		    background: #fff;
		    z-index: 99999;
		}
		.card-djtj span {
			margin-right: 20px;
		}
	</style>
	<!-- <style>
		[class*=" datetimepicker-dropdown"]:before{
			top:-8px;
		}
			.form-control{
				height: 30px;
			}
			.datetimepicker{
				top:67px!important;
				position: fixed;
				
			}
			
			/* 2018_07_30 */
			/* .card-content {
	
			} */
			.card-content {
				width: 72%!important;
			}
			.card-content .visaListSpan div{
				width: 19.5%!important;			
			}
	
			.everybody-info div:nth-child(4) {
				margin-left: 0;
			}
	
			/* .card-content {
				float: left;
			} */
	
			.card-content-2{
				top: 0;
				right: 0;
				position: absolute;
				width: 28%;
				height: 100%;
			}

			.card-content-2 span {
				top: 50%;
				left: 0;
				position: absolute;
				margin-top: -12px;
				height: 24px;
				line-height: 24px;
				font-size: 18px;
			}
			.card-content-2 span b{
				font-size: 14px;
				font-weight: 400;
			}
			.everybody-info div:nth-child(3){
				width: 16.5%!important;
			}
			.everybody-info div:nth-child(2){
				width: 22.5%!important;
			}
			
		</style> -->
</head>
<body class="hold-transition skin-blue sidebar-mini">
				
					<div class="box-header" style="margin-left:16px;"><!-- 检索条件 -->
						<div class="row searchMar tab-header">
						
							<div class="col-md col-md-2" style="width:12%;margin-left:2px;">
								<select class="input-class input-sm" style="width:110px;" id="status" name="status" onchange="changestatus()">
									<option value="">状态</option>
									<c:forEach items="${obj.orderstatus }" var="orstatus">
										<option value="${orstatus.key }">${orstatus.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="songqianshe" style="width:110px;margin-left:-2px;" name="songqianshe" onchange="changestatus()">
									<option value="">送签社简称</option>
									<c:forEach items="${obj.songqianlist }" var="songqianlist">
										<option value="${songqianlist.id}">${songqianlist.shortName }</option>
									</c:forEach>
								</select>
							</div>
							<%-- <div class="col-md col-md-1 left-5px right-0px">
								<select class="input-class input-sm" style="margin-left:-64px;" id="employee" name="employee" onchange="changestatus()">
									<option value="">员工</option>
									<c:forEach items="${obj.employees }" var="employees">
										<option value="${employees.userid }">${employees.username }</option>
									</c:forEach>
								</select>
							</div> --%>
							<div class="col-md col-md-1 left-5px right-0px">
								<select class="input-class input-sm" style="margin-left:-64px;width:100px;" id="visatype" name="visatype" onchange="changestatus()">
									<option value="">签证类型</option>
									<c:forEach items="${obj.mainsalevisatypeenum }" var="visatype">
										<option value="${visatype.key }">${visatype.value }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md col-md-1 left-5px right-0px card-list-date" style="width:18.5%;margin-left:-47px;">
								<input type="text" class="input-sm input-class form-control" style="height:30px;" id="orderDate" name="orderDate" placeholder="下单时间" onkeypress="onkeyEnter()"/>
								<input type="hidden" id="orderstartdate" name="orderstartdate"/>
								<input type="hidden" id="orderenddate" name="orderenddate"/>
							</div>
							<div class="col-md col-md-1 left-5px right-0px card-list-date" style="width:9.5%;margin-left:-6px;">
								<input type="text" class="input-sm input-class form-control" style="height:30px;" id="sendSignDate" name="sendSignDate" placeholder="送签时间" onkeypress="onkeyEnter()"/>
								<input type="hidden" id="sendstartdate" name="sendstartdate"/>
								<input type="hidden" id="sendenddate" name="sendenddate"/>
							</div>
							<div class="col-md col-md-3 left-5px right-0px" style="margin-left:-6px;">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/护照号/电话/邮箱/申请人/受付番号" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md col-md-3 left-5px right-0px" style="margin-left:916px;margin-top:-30px;">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
							</div>
						
						
							<!-- <div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="zhaobaotime" name="zhaobaotime" placeholder="2017-10-22 ~ 2017-11-19" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/申请人/护照号/电话/邮箱" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-3 left-5px">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
							</div> -->
						</div>
					</div><!-- end 检索条件 -->
					<section class="content" id="card"  v-cloak>
					<div class = "card-djtj" style="margin-top:1px;margin-left:6px;">
						<span>订单总数(单)： {{visaJapanDataS.orderscount}}</span>
						<span>总人数(人)： {{visaJapanDataS.peopletotal}}</span>
						<!-- <span>作废数(单)： {{visaJapanDataS.disableorder}}</span>
						<span>作废人数(人)： {{visaJapanDataS.disablepeople}}</span> -->
						<span>招宝成功(单)： {{visaJapanDataS.zhaobaoorder}}</span>
						<span>招宝成功(人)： {{visaJapanDataS.zhaobaopeople}}</span>
						<span>单组单人： {{visaJapanDataS.singleperson}}</span>
						<span>单组多人： {{visaJapanDataS.multiplayer}}</span>
					</div>
					<div class="box-body" style="margin-top:5px !important;"  v-cloak><!-- 卡片列表 -->
						<div class="card-list" v-for="data in visaJapanData">
							<div class="card-head cf">
								<div><label>订单号：</label><span>{{data.japannumber}}</span></div>	
								<div><label>受付番号：</label><span>{{data.number}}</span></div>	
								<div><label>送签社：</label><span>{{data.comname}}</span></div>
								<div><label>操作人：</label><span>{{data.username}}</span></div>
								<div><label></label><span style="font-weight:bold;font-size:16px;">{{data.visastatus}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="sendZB" v-on:click="sendzhaobao(data.id)"> </i>
									<i class="ZBchange" v-on:click="sendInsurance(data.id,19)"> </i>
									<i class="ZBcancel" v-on:click="sendInsurance(data.id,22)"> </i>
									<i class="download" v-on:click="downLoadFile(data.id)"> </i>
									<i class="guiGuoUpload"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="(item,index) in data.applyinfo">
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>申请人：</label><span>{{item.applicant}}</span></div>
										<div><label>申请人英文：</label><span>{{item.applicanten}}</span></div>
										<div><label>性别：</label><span>{{item.sex}}</span></div>
										<div><label>居住地域：</label><span>{{item.province}}</span></div>
										<div><label>出生日期：</label><span>{{item.birthday}}</span></div>
										<div><label>护照号：</label><span>{{item.passport}}</span></div>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label>　　　　</label><span>{{item.applicant}}</span></div>
										<div><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><span>{{item.applicanten}}</span></div>
										<div><label>　　　</label><span>{{item.sex}}</span></div>
										<div><label>　　　　　</label><span>{{item.province}}</span></div>
										<div><label>　　　　　</label><span>{{item.birthday}}</span></div>
										<div><label>　　　　</label><span>{{item.passport}}</span></div>
									</span>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
		<input type="hidden" id="pageNumber" name="pageNumber" value="1">
		<input type="hidden" id="pagetotal" name="pagetotal">
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js?v=<%=System.currentTimeMillis() %>"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<!-- <script src="${base}/admin/visaJapan/visaList.js"></script> -->
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/admin/simple/listpagejs.js"></script>
	<script type="text/javascript">
	
	/* $("#orderstartdate").val(getToday());
	$("#orderenddate").val(getToday());
	$("#sendstartdate").val(getToday());
	$("#sendenddate").val(getToday()); */ 
	//异步加载的URL地址
    var url="${base}/admin/JapanDijie/listData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {
			visaJapanData:[],
			visaJapanDataS:{}
		},
		created:function(){
            _self=this;
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		console.log(data);
					_self.visaJapanData = data.visaJapanData;
					if(data.visaJapanData.length != 0){
	            		_self.visaJapanDataS = data.entity;
            		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				/* disableorder:0,
            				disablepeople:0, */
            				zhaobaoorder:0,
            				zhaobaopeople:0,
            				singleperson:0,
            				multiplayer:0
            			}
            		}
					$('#pagetotal').val(data.pagetotal);
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到签证详情页面
        		window.open('${base}/admin/JapanDijie/orderdetail.html?orderid='+orderid);
        	},
        	//招宝变更、招宝取消
        	sendInsurance:function(orderid,visastatus){
	       		 $.ajax({
                	url: '${base}/admin/visaJapan/sendInsurance',
                	data:{orderid:orderid,visastatus:visastatus},
                	dataType:"json",
                	type:'post',
                	success: function(data){
                		if(visastatus == 16){
	                 		layer.msg('发招宝');
                		}else if(visastatus == 19){
	                 		layer.msg('招宝变更');
                		}else if(visastatus == 22){
	                 		layer.msg('招宝取消');
                		}else if(visastatus == 27){
	                 		layer.msg('报告拒签');
                		}
                		//更新列表数据
                		$.ajax({ 
                       	url: url,
                       	dataType:"json",
                       	type:'post',
                       	success: function(data){
                       		_self.visaJapanData = data.visaJapanData;
                         	}
                       });
                  	}
                });
	       	},
	       	//发招宝
	       	sendzhaobao:function(orderid){
        		layer.open({
        		    type: 2,
        		    title: false,
        		    closeBtn:false,
        		    fix: false,
        		    maxmin: false,
        		    shadeClose: false,
        		    scrollbar: false,
        		    area: ['400px', '300px'],
        		    content: '${base}/admin/JapanDijie/sendZhaoBao.html?orderid='+orderid
        		  });
        	},
        	downLoadFile:function(orderid){
        		layer.load(1);
        		$.fileDownload("${base}/admin/visaJapan/downloadFile.html?orderid=" + orderid, {
			         successCallback: function (url) {
			        	 layer.closeAll('loading');
			         },
			         failCallback: function (html, url) {
			        	layer.closeAll('loading');
			        	layer.msg("下载失败");
			         }
			     });
        	}
        }
	});
	$(function(){
		//招宝时间
		$('#zhaobaotime').daterangepicker(null, function(start, end, label) {
		  	console.log(start.toISOString(), end.toISOString(), label);
		});
	});
	//跳转 签证详情页
	function edit(orderid){
		window.location.href = '${base}/admin/visaJapan/visaDetail.html?orderid='+orderid;
	}
	
	function search(){
		/* var status = $('#status').val();
		var zhaobaotime = $('#zhaobaotime').val();
		var searchStr = $('#searchStr').val(); */
		var status = $('#status').val();
		var sendSignDate = $('#sendSignDate').val();
		var sendstartdate = $('#sendstartdate').val();
		var sendenddate = $('#sendenddate').val();
		var orderDate = $('#orderDate').val();
		var orderstartdate = $('#orderstartdate').val();
		var orderenddate = $('#orderenddate').val();
		var songqianshe = $("#songqianshe").val();
		var employee = $("#employee").val();
		var visatype = $("#visatype").val();
		var searchStr = $('#searchStr').val();
		var pageNumber = $('#pageNumber').val();
		$.ajax({ 
        	url: url,
        	data:{
        		status:status,
        		visatype:visatype,
        		songqianshe:songqianshe,
        		employee:employee,
        		sendSignDate:sendSignDate,
        		orderDate:orderDate,
        		searchStr:searchStr,
        		sendstartdate:sendstartdate,
        		sendenddate:sendenddate,
        		orderstartdate:orderstartdate,
        		orderenddate:orderenddate
        	},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		$('#pageNumber').val(1);
        		_self.visaJapanData = data.visaJapanData;
        		if(data.visaJapanData.length != 0){
            		_self.visaJapanDataS = data.entity;
        		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				/* disableorder:0,
            				disablepeople:0, */
            				zhaobaoorder:0,
            				zhaobaopeople:0,
            				singleperson:0,
            				multiplayer:0
            			}
            		}
          	}
        });
	}
	//回车事件
	function onkeyEnter(){
	    var e = window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode == 13){
	    	search();
		 }
	}
	
	function changestatus(){
		search();
	}
	
	function successCallBack(status){
		search();
		/* $.ajax({
        	url: url,
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
          	}
        });
		if(status){
			layer.msg('保存成功');
		} */
	}
	
	// 注册scroll事件并监听 
	$(window).scroll(function(){
	　　var scrollTop = $(this).scrollTop();
	　　var scrollHeight = $(document).height();
	　　var windowHeight = $(this).height();
		// 判断是否滚动到底部  

	　　if(scrollTop + windowHeight == scrollHeight){
			
			//分页条件
			var pageNumber = $('#pageNumber').val();
			pageNumber = parseInt(pageNumber) + 1;
			$('#pageNumber').val(pageNumber);
			var pagetotal = parseInt($('#pagetotal').val());
			//搜索条件
			/* var status = $('#status').val();
			var zhaobaotime = $('#zhaobaotime').val(); */
			var status = $('#status').val();
			var sendSignDate = $('#sendSignDate').val();
			var visatype = $("#visatype").val();
			var sendstartdate = $('#sendstartdate').val();
			var sendenddate = $('#sendenddate').val();
			var orderstartdate = $('#orderstartdate').val();
			var orderenddate = $('#orderenddate').val();
			
			var orderDate = $('#orderDate').val();
			var songqianshe = $("#songqianshe").val();
			var searchStr = $('#searchStr').val();
			//异步加载数据
			console.log(pageNumber);
			console.log(pagetotal);
			if(pageNumber <= pagetotal){
				//遮罩
				layer.load(1);
				$.ajax({ 
			    	url: url,
			    	data:{status:status,visatype:visatype,sendstartdate:sendstartdate,sendenddate:sendenddate,orderstartdate:orderstartdate,orderenddate:orderenddate,sendSignDate:sendSignDate,orderDate:orderDate,songqianshe:songqianshe,searchStr:searchStr,pageNumber:pageNumber},
			    	dataType:"json",
			    	type:'post',
			    	success: function(data){
			    		//关闭遮罩
			    		layer.closeAll('loading');
			    		$.each(data.visaJapanData,function(index,item){
			    			_self.visaJapanData.push(item);
			    		});
			    		//没有更多数据
			      	}
			    });
			}
	　　}
	});
	
	//时间插件处理
	$('#sendSignDate').daterangepicker(null, function(start, end, label) {
	  	console.log(start.toISOString(), end.toISOString(), label);
	  	console.log(start.format('YYYY-MM-DD HH:mm:ss'));
	  	$("#sendstartdate").val(start.format('YYYY-MM-DD HH:mm:ss'));
	  	$("#sendenddate").val(end.format('YYYY-MM-DD HH:mm:ss'));
	  	search();
	});
	$('#sendSignDate').on('cancel.daterangepicker', function(ev, picker) {
		$('#sendSignDate').val('');
		$("#sendstartdate").val('');
	  	$("#sendenddate").val('');
	  	search();
	});
	$('#sendSignDate').on('apply.daterangepicker', function(ev, picker) {
		$("#sendstartdate").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
	  	$("#sendenddate").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
	  	search();
	});
	$('#orderDate').daterangepicker(null, function(start, end, label) {
	  	console.log(start.toISOString(), end.toISOString(), label);
	  	$("#orderstartdate").val(start.format('YYYY-MM-DD HH:mm:ss'));
	  	$("#orderenddate").val(end.format('YYYY-MM-DD HH:mm:ss'));
	  	search();
	});
	$('#orderDate').on('cancel.daterangepicker', function(ev, picker) {
		$('#orderDate').val('');
		$("#orderstartdate").val('');
	  	$("#orderenddate").val('');
	  	search();
	});
	$('#orderDate').on('apply.daterangepicker', function(ev, picker) {
		$("#orderstartdate").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
	  	$("#orderenddate").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
	  	search();
	});
	
	//获取当前日期
	function getToday(){
	    var date = new Date();
	    var seperator1 = "-";
	    var year = date.getFullYear();
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = year + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	}
	
	</script>
</body>
</html>
