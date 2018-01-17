<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
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
		
		.card-list {
			height:153px;
		}
		.content-title {
			height:30px;
			line-height:30px;
			margin-bottom:10px;
		}
		.content-title span {
			margin-right:30px;
			font-size:20px;
		}
		.content-main-left {
			margin-left:0px;
		}
		.content-main-center {
			float:left;
			width:25%;
			height:93px;
		}
		.center-right {
			font-size:30px;
			text-align:center;
			color:green;
		}
		.main-right {
			margin-top:22%;
			text-align:center;
		}
		.main-right a {

			color:#0099FF;
			font-size:16px;
			margin-right:15px;
		}
		.main-right a:hover {
			cursor:pointer;

		}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<section class="content" id="card">
					<div class="box-body"  v-cloak v-for="data in myVisaData" style="cursor:pointer;position: relative;z-index:999;"><!-- 卡片列表 -->
						<div   class="card-list" >
							<div class="content-title">
								<span>{{data.applicantname}}</span>
								<span></span>
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
										<dd><label>&nbsp;</label><span></span></dd>
										<dd><label>送签时间：</label><span>{{data.sendvisadate}}</span></dd>
										<dd><label>出签时间：</label><span>{{data.outvisadate}}</span></dd>
									</dl>
								</div>
								<div class="content-main-center center-right"><!-- {{data.orderstatus}} --></div>
								
							</div>
						</div>
						<div style="position: absolute;top:111px;right:0; width:20%;height:auto;z-index:9999;" class="" >
						<div class="content-main-center" style="width:100%; height:20px;">
									<div class="">
										<a v-on:click="edit(data.applicantid)" style="cursor:pointer;">资料编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<!-- <a v-on:click="download(data.applicantid)" style="cursor:pointer;">下载</a> -->

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
	    var url="${base}/admin/myData/topContactsList.html";
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
	            	success: function(data){
	            		console.log(JSON.stringify(data.visaJapanData));
	            		_self.myVisaData = data.visaJapanData;
	              	}
	            });
	        },
	        methods:{
	        	toFlowChart:function(orderid,applicantid){
	    			//跳转到签证进度页面
	    			window.location.href = '/admin/myVisa/flowChart.html?orderid='+orderid+'&applicantid='+applicantid;
	    		},
	    		edit:function(applyId){
	    			layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '80%'],
						content:'/admin/myData/basic.html?contact=1&applyId='+applyId
					});
	        	} ,
	        }
		});
		
		function successCallBack(status){
			$.ajax({ 
		    	url: '${base}/admin/myData/topContactsList.html',
		    	dataType:"json",
		    	type:'post',
		    	success: function(data){
		    		_self.myVisaData = data.visaJapanData;
		    	}
			});
		}
		function cancelCallBack(status){
			
		}
	
	</script>
	</body>
</html>