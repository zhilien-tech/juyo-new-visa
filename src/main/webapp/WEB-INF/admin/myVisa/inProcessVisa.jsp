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
	<title>我的签证 - 办理中签证</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
	<link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
	<link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
	<style type="text/css">
		[v-cloak]{display:none;}
		.card-head div:nth-child(1){width:130px;}
		.card-head div:nth-child(2){width:200px;}
		.card-head div:nth-child(4){position:absolute;right:35px;}
		.everybody-info div:nth-child(1){width:10%;}
		.everybody-info div:nth-child(2){width:13%;}
		.everybody-info div:nth-child(3){width:15%;}
		.everybody-info div:nth-child(4){width:15%;}
		.everybody-info div:nth-child(5){width:15%;}
		.box-body { cursor:pointer;position: relative;z-index:999;}
		.card-list { height:153px;}
		.content-title { height:30px; line-height:30px; margin-bottom:10px; }
		.content-title span { margin-right:30px; font-size:20px; }
		.content-main-left { margin-left:0px; }
		.content-main-center { float:left; width:25%; height:93px; }
		.center-right { font-size:30px; text-align:center; color:green; }
		.main-right { margin-top:22%; text-align:center; }
		.main-right a { color:#0099FF; font-size:16px; margin-right:15px; }
		.main-right a:hover { cursor:pointer; }
		.editInfo a { cursor:pointer;}
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content" id="card">
					<div class="box-body"  v-cloak v-for="data in myVisaData"><!-- 卡片列表 -->
						<div  v-on:click="toFlowChart(data.orderid,data.applicantid)" class="card-list" >
							<div class="content-title">
								<span>{{data.applicantname}}</span>
								<span>{{data.ordernum}}</span>
							</div>
							<div class="content-main">
								<div class="content-main-center content-main-left">
									<dl>
										<dd><label>姓&nbsp;&nbsp;&nbsp;名：</label><span>{{data.applicantname}}</span></dd>
										<dd><label>手机号：</label><span>{{data.telephone}}</span></dd>
										<dd><label>护照号：</label><span>{{data.passport}}</span></dd>
									</dl>
								</div>
									
								<div class="content-main-center">
									<dl>
										<dd><label>订&nbsp;&nbsp;单&nbsp;号：</label><span>{{data.ordernum}}</span></dd>
										<dd><label>送签时间：</label><span>{{data.sendvisadate}}</span></dd>
										<dd><label>出签时间：</label><span>{{data.outvisadate}}</span></dd>
									</dl>
								</div>
								<div class="content-main-center center-right">{{data.orderstatus}}</div>
							</div> 
						</div>
						<div style="position: absolute;top:111px;right:0; width:20%;height:auto;z-index:9999;" class="" >
							<div class="content-main-center" style="width:100%; height:20px;">
								<div class="editInfo">
									<a v-on:click="edit(data.applicantid)" >资料编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a v-on:click="download(data.applicantid)">下载</a>
								</div>
							</div>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
		//异步加载的URL地址
	    var url="${base}/admin/myVisa/myVisaListData.html";
	    //vue表格数据对象
	    var _self;
	    var orderJpId = '${obj.orderJpId}';
		new Vue({
			el: '#card',
			data: {myVisaData:""},
			created:function(){
	            _self=this;
	            $.ajax({ 
	            	url: url,
	            	dataType:"json",
	            	type:'post',
	            	data:{
	            		orderJpId : orderJpId
	            	},
	            	success: function(data){
	            		_self.myVisaData = data.myVisaData;
	              	}
	            });
	        },
	        methods:{
	        	toFlowChart:function(orderid,applicantid){
	    			//跳转到签证进度页面
	    			window.location.href = '/admin/myVisa/flowChart.html?orderid='+orderid+'&applicantid='+applicantid;
	    		},
	    		edit:function(applyid){
	    			layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '551px'],
						content:'/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='+'&isTrial=0&tourist=1'
					});
	    			/* $.ajax({ 
	    				url: "/admin/myVisa/isChanged.html",
	    				dataType:"json",
	    				async:false,
	    				type:'post',
	    				data:{
	                		applyid : applyid
	                	},
	    				success: function(data){
	    					if(data == 1){//如果返回1则说明游客信息改变，提示是否更新
	    						layer.confirm("信息已改变，您是否要更新？", {
	    							title:"提示",
	    							btn: ["是","否"], //按钮
	    							shade: false //不显示遮罩
	    						}, function(){
	    						$.ajax({ 
	    					    	url: '${base}/admin/myVisa/copyInfoToPersonnel',
	    					    	dataType:"json",
	    					    	async:false,
	    					    	data:{applyid:applyid},
	    					    	type:'post',
	    					    	success: function(data){
						    			layer.open({
											type: 2,
											title: false,
											closeBtn:false,
											fix: false,
											maxmin: false,
											shadeClose: false,
											scrollbar: false,
											area: ['900px', '80%'],
											content:'/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='+'&isTrial=0'
										});
	    					      	}
	    					    }); 
	    						},
	    						function(){
	    							$.ajax({ 
		    					    	url: '${base}/admin/myVisa/toSetUnsame',
		    					    	dataType:"json",
		    					    	async:false,
		    					    	data:{applyid:applyid},
		    					    	type:'post',
		    					    	success: function(data){
							    			layer.open({
												type: 2,
												title: false,
												closeBtn:false,
												fix: false,
												maxmin: false,
												shadeClose: false,
												scrollbar: false,
												area: ['900px', '80%'],
												content:'/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='+'&isTrial=0'
											});
		    					      	}
		    					    }); 
	    							
	    						});
	    					}else{
	    						layer.open({
									type: 2,
									title: false,
									closeBtn:false,
									fix: false,
									maxmin: false,
									shadeClose: false,
									scrollbar: false,
									area: ['900px', '80%'],
									content:'/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='+'&isTrial=0'
								});
	    					}
	    				}
	    			});*/
	        	} ,
	        }
		});
		
		function successCallBack(status){
			if(status == 1){
				layer.msg('修改成功');
			}else if(status == 2){
				layer.msg('发送成功');
			}else if(status == 3){
				layer.msg('合格成功');
			}
			else if(status == 4){
				layer.msg('不合格成功');
			}
			$.ajax({ 
				url: url,
				dataType:"json",
				type:'post',
				data:{
            		orderJpId : orderJpId
            	},
				success: function(data){
					_self.myVisaData = data.myVisaData;
				}
			});
		}
		function cancelCallBack(status){
			successCallBack(5);
		}
	
	</script>
</body>
</html>
