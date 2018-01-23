<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>前台 - 日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css"><!-- 订单切换卡 样式 -->
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	<style>
		[v-cloak]{display:none;}
		.card-head { overflow:hidden; white-space:nowrap;}
		.card-head div:nth-child(1){width:20%;}
		.card-head div:nth-child(2){width:10%; text-align:right;}
		.card-head div:nth-child(3){width: 135px;float: right;position: relative;right: 0; height:31px;}
		.everybody-info div:nth-child(1){width:11%;}
		.everybody-info div:nth-child(2){width:14%;}
		.everybody-info div:nth-child(3){width:14%;}
		.everybody-info div:nth-child(4){width:10%;}
		.everybody-info div:nth-child(5){width:12%;}
		/*顶部 不随导航移动*/
		/* .box-header { position:fixed; top:0;left:0; width:100%; height:70px; background:#FFF; z-index:99999; padding:20px 30px 20px 40px;}
	    .box-body {  overflow:hidden;margin-top:60px;} */
	    .everybody-info {position:relative; }
	    .cf { overflow:visible !important;}
	    .whiteSpace {  overflow:hidden; text-overflow:ellipsis; white-space:nowrap; width:39%;}
	    .showInfo { cursor:pointer; }
	    .hideInfo { display:none; position:absolute; top:-33px;right:0;background:#eee;height:30px;line-height:30px; font-size:12px; padding:0 10px; border-radius:10px;}
	</style>
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
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
									<c:forEach var="map" items="${obj.receptionSearchStatus}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-7 left-5px">
								<a class="btn btn-primary btn-sm pull-left"  id="searchbtn">搜索</a>
								<!-- <a class="btn btn-primary btn-sm pull-right" href="javascript:;" id="">拍视频</a> -->
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in receptionJpData">
							<div class="card-head cf">
								<div><label>订单号：</label><span style="cursor:pointer;font-size:12px;" @click="visaDetail(data.id)">{{data.ordernumber}}</span></div>	
								<div style="position:absolute;right:25%;"><label></label><span style="font-size:16px;font-weight:bold;">{{data.orderstatus}}</span></div>		
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="shiShou" v-on:click="revenue(data.id)"> </i>
									<i class="sendSms" v-on:click="sendSms(data.id)"> </i>
									<i class="visaTransfer" v-on:click="visaTransfer(data.id)"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<!-- <li class="everybody-info cf" v-for="item in data.everybodyinfo">
									<div><label>申请人：</label><span>{{item.applicant}}</span></div>
									<div><label>护照号：</label><span>{{item.passportno}}</span></div>
									<div><label>快递号：</label><span>{{item.expressnum}}</span></div>
									<div><label>方式：</label><span>{{item.expresstype}}</span></div>
									<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
									<div><label>资料：</label><span>{{item.data}}</span></div>
									<div><i class="videoShoot"> </i></div>
								</li> -->
								
								<li class="everybody-info cf" v-for="(item,index) in data.everybodyinfo">
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>申请人：</label><span>{{item.applicant}}</span></div>
										<div><label>护照号：</label><span>{{item.passportno}}</span></div>
										<div><label>快递号：</label><span>{{item.expressnum}}</span></div>
										<div><label>方式：</label><span>{{item.expresstype}}</span></div>
										<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label>资料：</label><span v-html="item.data" class="showInfo"><!-- {{item.data}} --></span></div>
										<span class="hideInfo"></span>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label style="width:48px;">   </label><span>{{item.applicant}}</span></div>
										<div><label style="width:48px;">   </label><span>{{item.passportno}}</span></div>
										<div><label style="width:48px;">   </label><span>{{item.expressnum}}</span></div>
										<div><label style="width:36px;">   </label><span>{{item.expresstype}}</span></div>
										<div><label style="width:60px;">   </label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label style="width:36px;">&nbsp;&nbsp;</label><span v-html="item.data" class="showInfo"></span></div>
										<span class="hideInfo"></span>
									</span>
								</li>
								
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			<input type="hidden" id="pageNumber" name="pageNumber" value="1">
				<input type="hidden" id="pageTotal" name="pageTotal">
				<input type="hidden" id="pageListCount" name="pageListCount">

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<%-- <script src="${base}/admin/visaJapan/visaList.js"></script> --%>
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/switchCardOfOrder.js"></script><!-- 订单切换卡 js -->
	<script type="text/javascript">
	
	//异步加载的URL地址
    var url="${base}/admin/receptionJP/listData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {receptionJpData:""},
		created:function(){
            _self=this;
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		_self.receptionJpData = data.receptionJpData;
            		console.log(_self.receptionJpData);
            		$('#pageTotal').val(data.pageTotal);
					$('#pageListCount').val(data.pageListCount);
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到详情页面
        		window.open('${base}/admin/receptionJP/receptionDetail.html?orderid='+orderid);
        		//console.log(message);
        		//alert(JSON.stringify(event.target));
        	},
        	revenue:function(orderid){
        		layer.open({
        		    type: 2,
        		    title: false,
        		    closeBtn:false,
        		    fix: false,
        		    maxmin: false,
        		    shadeClose: false,
        		    scrollbar: false,
        		    area: ['900px', '550px'],
        		    content: '${base}/admin/receptionJP/revenue.html?orderid='+orderid
        		  });
        	},
        	visaTransfer:function(orderid){
        		 $.ajax({ 
                 	url: '${base}/admin/receptionJP/visaTransfer.html',
                 	dataType:"json",
                 	data:{orderid:orderid},
                 	type:'post',
                 	success: function(data){
                 		successCallBack(3)
                   	}
                 });
        	},
        	sendSms:function(orderid){
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
        }
	});
	
	$(function(){
		$(".btnList").click(function(){
			$(this).addClass('bgColor').siblings().removeClass('bgColor');
			clearSearchEle();
			$("#searchbtn").trigger("click");
		})
		
	});
	
	function clearSearchEle(){
		//检索框
		$("#status").val("");
		$("#searchStr").val("");
		//分页项
		$("#pageNumber").val(1);
		$("#pageTotal").val("");
		$("#pageListCount").val("");
	}
	
	$("#searchbtn").click(function(){
		var status = $('#status').val();
		var searchStr = $('#searchStr').val();
		var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		
		$.ajax({ 
        	url: url,
        	data:{
        		status:status,
        		searchStr:searchStr,
        		orderAuthority:orderAuthority
        	},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.receptionJpData = data.receptionJpData;
          	}
        });
	}); 
	
	$("#status").change(function(){
		$("#searchbtn").click();
		$('#pageNumber').val(1);
	});
	
	//注册scroll事件并监听 
	$(window).scroll(function(){
		var scrollTop = $(this).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = $(this).height();
		var pageTotal = parseInt($('#pageTotal').val());
		var pageListCount = parseInt($('#pageListCount').val());
		// 判断是否滚动到底部  
		if(Math.ceil(scrollTop + windowHeight) == scrollHeight){
			//分页条件
			var pageNumber = $('#pageNumber').val();
			pageNumber = parseInt(pageNumber) + 1;
			$('#pageNumber').val(pageNumber);
			//搜索条件
			var searchStr = $('#searchStr').val();
			var status = $('#status').val();
			//异步加载数据
			if(pageNumber <= pageTotal){
				//遮罩
				layer.load(1);
				$.ajax({ 
					url: url,
					data:{
						status : status,
						searchStr : searchStr
					},
					dataType:"json",
					type:'post',
					success: function(data){
						//关闭遮罩
						layer.closeAll('loading');
						$.each(data.receptionJpData,function(index,item){
							_self.receptionJpData.push(item);
						});
						//没有更多数据
					}
				});
			}/* else{
				//没有更多数据，底部提示语
				if($("#card-bottom-line").length <= 0 && pageListCount>=6){
					$(".card-list").last().after("<div id='card-bottom-line' class='bottom-line'><span style='margin-left: 38%; color:#999'>-------  没有更多数据可以加载  -------</span></div>");
				}
			} */
		}
	});
	
	//回车事件
	function onkeyEnter(){
	    var e = window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode == 13){
	    	$("#searchbtn").click();
		 }
	}
	
	function successCallBack(status){
		if(status == 1){
			layer.msg('保存成功');
		}
		if(status == 2){
			layer.msg('修改成功');
		}
		if(status == 3){
			layer.msg('移交签证成功');
		}
		if(status == 88){
			layer.msg('负责人变更成功');
		}
		
		$("#searchbtn").click();
	}

	//鼠标移入事件
	$(document).on('mouseover','.showInfo',function(){
		
		let text = $(this).html();
		$(this).parent().next().show();
		$(this).parent().next().html(text);
	});
	//鼠标移出事件
	$(document).on('mouseleave','.showInfo',function(){
		$(".hideInfo").hide();
	});
	</script>
</body>
</html>
