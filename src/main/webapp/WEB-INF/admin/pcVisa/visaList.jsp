<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>办理中签证</title>
		<link rel="stylesheet" href="${base}/references/public/css/handleVisa.css">
		
		<style>
			[v-cloak]{display:none}
		</style>
	</head>
	<body>
		<div class="head">
			<span>
				办理中签证 >>申请人
			</span>
		</div>
		<div class="section">
			<div id="card" class="box-body"><!-- start 卡片列表 -->
				<div class="main" v-on:click="toInProcess(data.orderid)" v-cloak v-for="data in visaListData">
					<div class="title">
						<span class="titleName">{{data.firstbodyinfo.staffname}}</span>
						<span class="titleOrderNum">{{data.ordernumber}}</span>
					</div>
					<div class="infomation">
						<div class="moduleOnce module">
							<dl>
								<dd>
									<label>姓&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
									<span>{{data.firstbodyinfo.staffname}}</span>
								</dd>
								<dd>
									<label>手机号:</label>
									<span>{{data.firstbodyinfo.telephone}}</span>
								</dd>
								<dd>
									<label>护照号:</label>
									<span>{{data.firstbodyinfo.passport}}</span>
								</dd>
							</dl>
						</div>
						<div class="moduleSecond module">
							<dl>
								<dd>
									<label>订单号:</label>
									<span>{{data.firstbodyinfo.ordernumber}}</span>
								</dd>
								<dd>
									<label>A&nbsp;A&nbsp;码:</label>
									<span>{{data.firstbodyinfo.aacode}}</span>
								</dd>
								<dd>
									<label>卡&nbsp;&nbsp;&nbsp;号:</label>
									<span>{{data.firstbodyinfo.cardnum}}</span>
								</dd>
							</dl>
						</div>
						<div class="moduleThird">
						<span>{{data.firstbodyinfo.visastatus}}</span>
						</div>
						<div class="moduleFourth">
							<div class="btnGroup">
								<a class="edit"></a>
								<a class="download">下载</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end 卡片列表 -->
		</div>
		<input type="hidden" id="pageNumber" name="pageNumber" value="1">
		<input type="hidden" id="pagetotal" name="pagetotal">
		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
		<script type="text/javascript" src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${base}/references/common/js/vue/vue.min.js"></script>
		<script type="text/javascript" src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
		<script type="text/javascript">
			//异步加载的URL地址
		    var url="${base}/admin/pcvisa/visaListData.html";
		    //vue表格数据对象
		    var _self;
			new Vue({
				el: '#card',
				data: {visaListData:""},
				created:function(){
		            _self=this;
		            var start = new Date().getTime();//起始时间
		            $.ajax({ 
		            	url: url,
		            	dataType:"json",
		            	type:'post',
		            	success: function(data){
		            		var end = new Date().getTime();//接受时间
		            		_self.visaListData = data.visaListData;
		            		$('#pagetotal').val(data.pagetotal);
		                    //alert((end - start)+"ms");//返回函数执行需要时间
		              	}
		            });
		        },
		        methods:{
		        	toInProcess:function(id,){
		        		window.location.href = '/admin/pcVisa/visaDetail.html?orderid=' + id;
		        	}
		        }
			});
			
			function successCallBack(){
				$.ajax({ 
		        	url: url,
		        	dataType:"json",
		        	type:'post',
		        	success: function(data){
		        		_self.visaListData = data.visaListData;
		        		$('#pagetotal').val(data.pagetotal);
		          	}
		        });
			}
			
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
					/* var status = $('#status').val();
					var sendSignDate = $('#sendSignDate').val();
					var searchStr = $('#searchStr').val(); */
					//异步加载数据
					if(pageNumber <= pagetotal){
						//遮罩
						layer.load(1);
						$.ajax({ 
					    	url: url,
					    	data:{status:status,sendSignDate:sendSignDate,searchStr:searchStr,pageNumber:pageNumber},
					    	dataType:"json",
					    	type:'post',
					    	success: function(data){
					    		//关闭遮罩
					    		layer.closeAll('loading');
					    		$.each(data.visaListData,function(index,item){
					    			_self.visaListData.push(item);
					    		});
					    		//没有更多数据
					      	}
					    });
					}
			　　}
			});
			
		
			/* function successCallBack(status){
				$.ajax({ 
		        	url: url,
		        	dataType:"json",
		        	type:'post',
		        	success: function(data){
		        		_self.visaListData = data.visaListData;
		          	}
		        });
				if(status){
					layer.msg('保存成功');
				}
			} */
			
			/* $(function(){
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
			}); */
		</script>
	</body>
</html>
