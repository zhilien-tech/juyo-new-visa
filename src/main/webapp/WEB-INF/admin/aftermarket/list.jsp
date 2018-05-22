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
	<title>售后-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/css/aftermarketjp.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css"><!-- 订单切换卡 样式 -->
	<!-- 加载中。。。样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/spinner.css">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/afterMarketList.css?v='20180510'">
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<!-- 切换卡按钮 start -->
						<div class="btnGroups">
							<a name="myOrder" class="searchOrderBtn btnList  bgColor">我的</a>
							<a name="allOrder" class="searchOrderBtn btnList">全部</a>
						</div>
						<!-- 切换卡按钮 end -->
						<div class="row searchMar">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status" onchange="changestatus()">
									<option value="">状态</option>
									<c:forEach items="${obj.orderstatus }" var="orstatus">
										<option value="${orstatus.key }">${orstatus.value }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="signDateStr" name="signDateStr" placeholder="送签时间 - 出签时间" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<!-- <input type="text" class="input-sm input-class" id="signOutDate" name="signOutDate" placeholder="出签时间" onkeypress="onkeyEnter()"/> -->
							</div> 
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-3 left-5px">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card" v-cloak><!-- 卡片列表 -->
						<div class="card-list" v-for="data in aftermarketData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.ordernum}}</span></div>	
								<div><label>送签时间：</label><span>{{data.sendingtime}}</span></div>
								<div><label>出签时间：</label><span>{{data.signingtime}}</span></div>
								<div v-if="data.orderstatus === '发招宝中'"><label></label><span style="font-size:16px;font-weight:bold;">{{data.orderstatus}}</span>
								<!-- 加载中 -->
								<div class="spinner">
									<div class="bounce1"></div>
									<div class="bounce2"></div>
								    <div class="bounce3"></div>
								</div>
								</div>	
								<div v-else><label></label><span style="font-size:16px;font-weight:bold;">{{data.orderstatus}}</span></div>	
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="(item,index) in data.applicats">
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>收件人：</label><span>{{item.linkman}}</span></div>
										<div><label>电话：</label><span>{{item.backtelephone}}</span></div>
										<div><label>地址：</label><span>{{item.expressaddress}}</span></div>
										<div><label>状态：</label><span></span></div>
										<div><label></label>
											<span>
												<a href="javascript:;" v-on:click="sendMail(item.applicatid);">回邮通知</a>&nbsp;
												<a href="javascript:;" v-on:click="backpost(item.id,item.orderid)">回邮</a>
											</span>
										</div>
										<div><!-- <i> </i> --></div>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label>　　　　</label><span>{{item.linkman}}</span></div>
										<div><label>　　　</label><span>{{item.backtelephone}}</span></div>
										<div><label>　　　</label><span>{{item.expressaddress}}</span></div>
										<div><label>　　　</label><span></span></div>
										<div>
											<label></label>
											<span>
												<a href="javascript:;" v-on:click="sendMail(item.applicatid);">回邮通知</a>&nbsp;
												<a href="javascript:;" v-on:click="backpost(item.id,item.orderid)">回邮</a>
											</span>
										</div>
										<div><!-- <i> </i> --></div>
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
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<%-- <script src="${base}/admin/visaJapan/visaList.js"></script> --%>
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/switchCardOfOrder.js"></script><!-- 订单切换卡 js -->
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/aftermarket/aftermarketListData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {aftermarketData:""},
		created:function(){
            _self=this;
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		_self.aftermarketData = data.aftermarketData;
            		$('#pagetotal').val(data.pagetotal);
              	}
            });
        },
        methods:{
        	sendMail:function(applicantJPId){
        		layer.load(1);
        		$.ajax({
        			url: '/admin/aftermarket/sendMailAndMessage.html',
        			dataType:"json",
        			data:{applicantid:applicantJPId},
        			type:'post',
        			success: function(data){
        				layer.closeAll('loading');
        				if(data.status == 200){
        					successCallBack(2);
        				}else if(data.status == 500){
        					layer.msg(data.msg);
        				}
        				//layer.msg('发送成功');
        			}
        		});
        	},
        	backpost:function(applyId,orderid){
        		layer.open({
    				type: 2,
    				title: false,
    				closeBtn:false,
    				fix: false,
    				maxmin: false,
    				shadeClose: false,
    				scrollbar: false,
    				area: ['900px', '80%'],
    				content:'/admin/backMailJp/backMailInfo.html?applicantId='+applyId+'&orderId='+orderid+'&isAfterMarket=1&orderProcessType=5'
    			});
        	}
        }
	});
	// 注册scroll事件并监听 
	$(window).scroll(function(){
	　　var scrollTop = $(this).scrollTop();
	　　var scrollHeight = $(document).height();
	　　var windowHeight = $(this).height();
		// 判断是否滚动到底部  
	　　if(scrollTop + windowHeight == scrollHeight){
	　　　　// alert("滚到底了");
			//分页条件
			var pageNumber = $('#pageNumber').val();
			pageNumber = parseInt(pageNumber) + 1;
			$('#pageNumber').val(pageNumber);
			var pagetotal = parseInt($('#pagetotal').val());
			//搜索条件
			var status = $('#status').val();
			var sendSignDate = $('#sendSignDate').val();
			var signOutDate = $('#signOutDate').val();
			var searchStr = $('#searchStr').val();
			//异步加载数据
			if(pageNumber <= pagetotal){
				//遮罩
				layer.load(1);
				$.ajax({ 
			    	url: url,
			    	data:{status:status,sendSignDate:sendSignDate,signOutDate:signOutDate,searchStr:searchStr,pageNumber:pageNumber},
			    	dataType:"json",
			    	type:'post',
			    	success: function(data){
			    		//关闭遮罩
			    		layer.closeAll('loading');
			    		$.each(data.aftermarketData,function(index,item){
			    			_self.aftermarketData.push(item);
			    		});
			    		//没有更多数据
			      	}
			    });
			}
	　　}
	});
	
	$(function(){
		$(".btnList").click(function(){
			$(this).addClass('bgColor').siblings().removeClass('bgColor');
			//clearSearchEle();
			//检索框
			$('#status').val("");
			$('#signDateStr').val("");
			$('#searchStr').val("");
			search();
		})
		
	});
	
	function clearSearchEle(){
		//检索框
		$('#status').val("");
		$('#signDateStr').val("");
		$('#searchStr').val("");
		//分页项
		$("#pageNumber").val(1);
		$("#pageTotal").val("");
		$("#pageListCount").val("");
	}
	
	
	function search(){
		var status = $('#status').val();
		var signDateStr = $('#signDateStr').val();
		var searchStr = $('#searchStr').val();
		var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$(window).scrollTop(0);
		$.ajax({ 
        	url: url,
        	data:{
        		signDateStr:signDateStr,
        		searchstr:searchStr,
        		status:status,
        		orderAuthority:orderAuthority
        	},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.aftermarketData = data.aftermarketData;
        		$('#pagetotal').val(data.pagetotal);
        		$("#pageNumber").val(1);
          	}
        });
	}
	function changestatus(){
		search();
	}
	//回车事件
	function onkeyEnter(){
	    var e = window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode == 13){
	    	search();
		 }
	}
	
	function successCallBack(status){
		$.ajax({ 
        	url: url,
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.aftermarketData = data.aftermarketData;
        		$('#pagetotal').val(data.pagetotal);
        		$("#pageNumber").val(1);
          	}
        });
		if(status == 1){
			layer.msg('保存成功<br>订单进入"我的"标签页');
		}else if(status == 2){
			layer.msg('发送成功<br>订单进入"我的"标签页');
		}
		if(status == 88){
			layer.msg('负责人变更成功');
		}
	}
	
	$(function(){
		$('#signDateStr').daterangepicker(null, function(start, end, label) {
		  	console.log(start.toISOString(), end.toISOString(), label);
		});
	});
	</script>
</body>
</html>
