<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>我的签证 - 办理中签证</title>
	<style type="text/css">
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
	<div class="wrapper">
		<div class="content-wrapper"  style="min-height:848px;">
				<section class="content">
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div v-on:dblclick="toFlowChart(data.orderid,data.applicantid)" class="card-list" v-cloak v-for="data in myVisaData">
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
								<div class="content-main-center">
									<div class="main-right">
										<a v-on:click="edit(data.applicantid)">资料编辑</a>
										<a v-on:click="download(data.applicantid)">下载</a>

									</div>
								</div>
							</div>
							<!-- <div class="card-head">
								<div><label>申请人：</label><span>{{data.applicantname}}</span></div>
								<div><label>订单号：</label><span>{{data.ordernum}}</span></div>
								<div><label>状态：</label><span>{{data.orderstatus}}</span></div>		
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="edit(data.applicantid)"> </i>
									<i class="download" v-on:click="download(data.applicantid)"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" >
									<div><label>姓名：</label><span>{{data.applicantname}}</span></div>
									<div><label>订单号：</label><span>{{data.ordernum}}</span></div>
									<div><label>手机号：</label><span>{{data.telephone}}</span></div>
									<div><label>送签时间：</label><span>{{data.sendvisadate}}</span></div>
									<div><label>护照号：</label><span>{{data.passport}}</span></div>
									<div><label>出签时间：</label><span>{{data.outvisadate}}</span></div>
								</li>
							</ul>
							 -->
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
		</div>

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
						content:'/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='
					});
	        	},
	        }
		});
	
	</script>
</body>
</html>
