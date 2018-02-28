<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>精简版-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	<link rel="stylesheet" href="${base}/references/public/css/visaJapan.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<!-- 订单切换卡 样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simpleList.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<!-- 切换卡按钮 start -->
						<!-- <div class="btnGroups">
							<a name="myOrder" class="searchOrderBtn btnList  bgColor">我的</a>
							<a name="allOrder" class="searchOrderBtn btnList">全部</a>
						</div> -->
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
							<div class="col-md-2 left-5px right-0px marginLR">
								<input type="text" class="input-sm input-class" id="sendSignDate" name="sendSignDate" placeholder="送签时间 - 出签时间" onkeypress="onkeyEnter()"/>
							</div>
							<!-- <div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="signOutDate" name="signOutDate" placeholder="出签时间" onkeypress="onkeyEnter()"/>
							</div> -->
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-1 left-5px btnSearch">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
							</div>
							<div class="col-md-1 right-5px btnSearch">
								<a class="btn btn-primary btn-sm pull-right" onclick="addOrder();">下单</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card" v-cloak><!-- 卡片列表 -->
						<div class="card-list" v-for="data in visaJapanData">
							<div class="card-head">
								<div><label>订单号：</label><span><a v-on:click="visaDetail(data.id)" href="javascript:;">{{data.japannumber}}</a></span></div>	
								<div><label>送签编号：</label><span>{{data.sendvisanum}}</span></div>	
								<div><label>送签时间：</label><span>{{data.sendingtime}}</span></div>
								<div><label>出签时间：</label><span>{{data.signingtime}}</span></div>
								<div><label>人数：</label><span>{{data.peoplenumber}}</span></div>	
								<div><label></label><span style="font-weight:bold;font-size:16px;">
									<span v-if="data.visastatus === '已发招宝'">
										<font color="red">{{data.visastatus}}</font>
									</span>
									<span v-else>
										{{data.visastatus}}
									</span>
								
								</span></div>	
								
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="shiShou" v-on:click="revenue(data.id)"> </i>
									<i class="sendZB" v-on:click="sendzhaobao(data.id)"> </i>
									<i class="ZBchange" v-on:click="sendInsurance(data.id,19)"> </i>
									<i class="ZBcancel" v-on:click="sendInsurance(data.id,22)"> </i>
									<i class="Refusal" v-on:click="sendInsurance(data.id,27)"></i>
									<i class="download" v-on:click="downLoadFile(data.id)"> </i>
									<i class="handoverTable"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="(item,index) in data.everybodyinfo">
									<span v-if="index === 0" class="visaListSpan">
										<div><label>申请人：</label><span>{{item.applicant}}</span></div>
										<div><label>护照号：</label><span>{{item.passportno}}</span></div>
										<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label>资料：</label><span v-html="item.data" class="showInfo"></span></div>
										<span class="hideInfo"></span>
										<div class="visaBtn"><a v-on:click="visainput(item.applicatid,data.orderid)">签证录入</a></div>
									</span>
									<span v-else class="visaListSpan">
										<div><label style="width:48px;">      </label><span>{{item.applicant}}</span></div>
										<div><label style="width:48px;">　　　　</label><span>{{item.passportno}}</span></div>
										<div><label style="width:60px;">　　　　　</label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label style="width:36px;">　　　</label><span v-html="item.data" class="showInfo"></span></div>
										<span class="hideInfo"></span>
										<div class="visaBtn"><a v-on:click="visainput(item.applicatid,data.orderid)">签证录入</a></div>
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
	<script src="${base}/admin/simple/listpagejs.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/simple/listData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {visaJapanData:""},
		created:function(){
            _self=this;
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		_self.visaJapanData = data.visaJapanData;
            		$('#pagetotal').val(data.pagetotal);
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到签证详情页面
        		window.open('${base}/admin/simple/editOrder.html?orderid='+orderid);
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
        		    content: '${base}/admin/visaJapan/revenue.html?orderid='+orderid
        		  });
        	},
        	sendInsurance:function(orderid,visastatus){
        		 $.ajax({
                 	url: '${base}/admin/visaJapan/sendInsurance',
                 	data:{orderid:orderid,visastatus:visastatus},
                 	dataType:"json",
                 	type:'post',
                 	success: function(data){
                 		if(visastatus == 16){
                 			parent.successCallBack(4);
	                 		//layer.msg('发招宝');
                 		}else if(visastatus == 19){
                 			parent.successCallBack(5);
	                 		//layer.msg('招宝变更');
                 		}else if(visastatus == 22){
                 			parent.successCallBack(6);
	                 		//layer.msg('招宝取消');
                 		}else if(visastatus == 27){
                 			parent.successCallBack(7);
	                 		//layer.msg('报告拒签');
                 		}
                 		//更新列表数据
                 		var orderAuthority = "allOrder";
						$(".searchOrderBtn").each(function(){
							if($(this).hasClass("bgColor")){
								orderAuthority = $(this).attr("name");
							}
						});
                 		$.ajax({ 
                        	url: url,
                        	data:{orderAuthority:orderAuthority},
                        	dataType:"json",
                        	type:'post',
                        	success: function(data){
                        		_self.visaJapanData = data.visaJapanData;
                          	}
                        });
                   	}
                 });
        	},
        	sendzhaobao:function(orderid){
        		$.ajax({
                 	url: '${base}/admin/visaJapan/validateInfoIsFull.html',
                 	data:{orderjpid:orderid},
                 	dataType:"json",
                 	type:'post',
                 	async:false,
                 	success: function(data){
                 		var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
                 		if(data.data){
                 			url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data;
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
        	},
        	//签证录入
    		visainput:function(applyId,orderid){
    			layer.open({
    				type: 2,
    				title: false,
    				closeBtn:false,
    				fix: false,
    				maxmin: false,
    				shadeClose: false,
    				scrollbar: false,
    				area: ['900px', '80%'],
    				content: '/admin/visaJapan/visaInput.html?applyid='+applyId+'&orderid='+orderid+'&isvisa=1'
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
			/* var signOutDate = $('#signOutDate').val(); */
			var searchStr = $('#searchStr').val();
			//异步加载数据
			if(pageNumber <= pagetotal){
				//遮罩
				layer.load(1);
				var orderAuthority = "allOrder";
				$(".searchOrderBtn").each(function(){
					if($(this).hasClass("bgColor")){
						orderAuthority = $(this).attr("name");
					}
				});
				$.ajax({ 
			    	url: url,
			    	data:{status:status,sendSignDate:sendSignDate,searchStr:searchStr,pageNumber:pageNumber,orderAuthority:orderAuthority},
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
	

	//跳转 签证详情页
	function edit(orderid){
		window.location.href = '${base}/admin/simple/visaDetail.html?orderid='+orderid;
	}
	
	
	$(function(){
		$(".btnList").click(function(){
			$(this).addClass('bgColor').siblings().removeClass('bgColor');
			clearSearchEle();
			search();
		})
		
	});
	
	function clearSearchEle(){
		//检索框
		$("#status").val("");
		$("#sendSignDate").val("");
		$("#searchStr").val("");
		//分页项
		$("#pageNumber").val(1);
		$("#pageTotal").val("");
		$("#pageListCount").val("");
	}
	
	function search(){
		var status = $('#status').val();
		var sendSignDate = $('#sendSignDate').val();
		//var signOutDate = $('#signOutDate').val();
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
        		sendSignDate:sendSignDate,
        		searchStr:searchStr,
        		orderAuthority:orderAuthority
        	},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
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
	function loadListData(){
		var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$.ajax({ 
        	url: url,
        	data:{orderAuthority:orderAuthority},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
          	}
        });
	}
	
	function successCallBack(status){
		var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$.ajax({ 
        	url: url,
        	data:{orderAuthority:orderAuthority},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
          	}
        });
		if(status == 1){
			//layer.msg('发招宝');
			parent.successCallBack(4);
		}else{
			//layer.msg('保存成功');
			parent.successCallBack(2);
		}
	}
	
	$(function(){
		//送签时间
		/* $("#sendSignDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-left",//显示位置
			minView: "month"
		});
		//出签时间
		$("#signOutDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-left",//显示位置
			minView: "month"
			
		}); */
		
		$('#sendSignDate').daterangepicker(null, function(start, end, label) {
		  	console.log(start.toISOString(), end.toISOString(), label);
		});
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
	});
	
	//连接websocket
	connectWebSocket();
	function connectWebSocket(){
		 if ('WebSocket' in window){  
            console.log('Websocket supported');  
            socket = new WebSocket('ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');   

            console.log('Connection attempted');  

            socket.onopen = function(){  
                 console.log('Connection open!');  
                 //setConnected(true);  
             };

            socket.onclose = function(){
                console.log('Disconnecting connection'); 
            };

            socket.onmessage = function (evt){
                console.log('message received!');  
            	loadListData();
             };  

          } else {  
            console.log('Websocket not supported');  
          }  
	}
	</script>
</body>
</html>
