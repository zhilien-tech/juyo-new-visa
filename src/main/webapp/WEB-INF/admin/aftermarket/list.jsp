<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>签证-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/css/aftermarketjp.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<style>
	.box-header { position:fixed; top:0;left:0; width:100%; height:70px; background:#FFF; z-index:99999; padding:20px 30px 20px 40px;}
	.box-body {  overflow:hidden;margin-top:60px;}
	.card-head span { font-size:12px;}
	[v-cloak] {
	  display: none;
	}
	</style>
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<!-- <ul class="title">
					<li>签证</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul> -->
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
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
								<div><label></label><span style="font-size:16px;font-weight:bold;">售后</span></div>	
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="(item,index) in data.applicats">
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>收件人：</label><span>{{item.linkman}}</span></div>
										<div><label>电话：</label><span>{{item.backtelephone}}</span></div>
										<div><label>地址：</label><span>{{item.expressaddress}}</span></div>
										<div><label>状态：</label><span></span></div>
										<div><label></label><span><a href="javascript:;" v-on:click="backpost(item.id)">回邮</a></span></div>
										<div><!-- <i> </i> --></div>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label>　　　　</label><span>{{item.linkman}}</span></div>
										<div><label>　　　</label><span>{{item.backtelephone}}</span></div>
										<div><label>　　　</label><span>{{item.expressaddress}}</span></div>
										<div><label>　　　</label><span></span></div>
										<div><label></label><span><a href="javascript:;" v-on:click="backpost(item.id)">回邮</a></span></div>
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
        	backpost:function(applyId){
        		layer.open({
    				type: 2,
    				title: false,
    				closeBtn:false,
    				fix: false,
    				maxmin: false,
    				shadeClose: false,
    				scrollbar: false,
    				area: ['900px', '551px'],
    				content:'/admin/backMailJp/backMailInfo.html?applicantId='+applyId+'&isAfterMarket=1'
    			});
        	}
        }
	});
	// 注册scroll事件并监听 
	$(window).scroll(function(){
	　　/* var scrollTop = $(this).scrollTop();
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
	　　} */
	});
	

	
	function search(){
		var status = $('#status').val();
		var signDateStr = $('#signDateStr').val();
		var searchStr = $('#searchStr').val();
		$.ajax({ 
        	url: url,
        	data:{signDateStr:signDateStr,searchstr:searchStr,status:status},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.aftermarketData = data.aftermarketData;
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
          	}
        });
		if(status == 1){
			layer.msg('保存成功');
		}else if(status == 2){
			layer.msg('发送成功');
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
