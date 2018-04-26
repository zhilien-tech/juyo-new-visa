<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>签证录入</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
		<link rel="stylesheet" href="${base}/references/public/css/style.css">
		<style type="text/css">
			[v-cloak]{display : none}
			.content { padding:0 !important;min-height:auto !important;}
			.box-header { padding:0 !important;}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.btnState{color: #b0b0b0 !important;border: solid 1px #d2d6de;background-color: #fff;margin-right: 2.26rem;}
			.btnState-true{color: #287ae7 !important;border-color: #cee1ff;}
			.card-head div:nth-child(1){width:33%; float: left;}
			.card-head div:nth-child(2){width:20%;}
			.card-head div:nth-child(3){ float: right; height: 31px;margin-top:-6px;}
			.everybody-info div:nth-child(1){width:20%;}
			.everybody-info div:nth-child(2){width:22%;}
			.everybody-info div:nth-child(3){width:14%;}
			.everybody-info div:nth-child(4){width:15%;}
			.everybody-info div:nth-child(5){width:11%;}
			.liClose { float:right !important;}
			.liClose a { display:block; width:40px; height:51px; cursor:pointer;}
			.box-header{padding-right: 16px;}
			.box-body{padding-top:0;}
			.qz-head { border-bottom:2px solid #deecff; padding:15px 20px; display: table; width: 100%;}
			.btn-sm { width:110px !important;}
			.btn-Add { margin: 10px 15px 10px 0;}
			.cf { clear:both;}
			.closed { font-size:30px; text-align:center;}
			.edit { margin-right:20px !important;}
			.delete { height: 25px !important;background-position:0 !important; top:8px !important; border:0 !important; margin-right:20px !important;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<div class="content-wrapper">
				<ul class="title">
					<li>签证录入</li>
					<li class="liClose">
						<a onclick="closeWindow()">
							<div class="closed">×</div>
						</a>
					</li>
				</ul>
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
							<div class="col-md-12">
								<a class="btn btn-primary btn-sm pull-right btn-Add" href="javascript:add();" id="">添加已有签证</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in visaInputData">
							<div class="card-head">
								<div><label>国家：</label><span>{{data.visacountry}}</span></div>	
								<div><label>签证号：</label><span>{{data.visanum}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="edit(data.id)"> </i>
									<i class="delete" v-on:click="deleteVisainput(data.id)"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf">
									<div><label>签发地：</label><span>{{data.visaaddress}}</span></div>
									<div><label>签发编号：</label><span>{{data.visanum}}</span></div>
									<div><label>签证类型：</label><span>{{data.visatypestr}} {{data.visayears}}年</span></div>
									<div><label>签发时间：</label><span>{{data.visadatestr }}</span></div>
									<div><label>停留时间：</label><span>{{data.staydays}}天</span></div>
									<div><label>有效期至：</label><span>{{data.validdatestr }}</span></div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
			<%-- <%@include file="/WEB-INF/public/footer.jsp"%> --%>
	
		</div>
	
		<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
		<script src="${base}/references/public/bootstrap/js/bootstrap.js"></script>
		<script src="${base}/references/public/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="${base}/references/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="${base}/references/common/js/layer/layer.js"></script>
		<script src="${base}/references/common/js/vue/vue.min.js"></script>
		<script src="${base}/references/public/dist/newvisacss/js/bootstrapValidator.js"></script>
		<script type="text/javascript">
		var orderobj;
		var userid = '${obj.userId}';
		new Vue({
			el: '#card',
			data: {
				visaInputData:""
			},
			created:function(){
		        orderobj=this;
		        var url = '${base}/admin/myData/touristVisainput/getTouristVisaInput.html';
		        $.ajax({ 
		        	url: url,
		        	dataType:"json",
		        	data:{userId:userid},
		        	type:'post',
		        	success: function(data){
		        		orderobj.visaInputData = data.visaInputData;
		          	}
		        });
		    },
		    methods:{
		    	edit:function(visainputid){
		    	      window.location.href = '${base}/admin/myData/touristVisainput/visaInputUpdate.html?id='+visainputid;
		    	},
		    	deleteVisainput:function(visainputid){
		    		layer.confirm("您确认要删除吗？", {
						title:"删除",
						btn: ["是","否"], //按钮
						shade: false //不显示遮罩
					}, function(index){
					$.ajax({ 
				    	url: '/admin/myData/touristVisainput/deleteVisainput',
				    	dataType:"json",
				    	data:{id:visainputid},
				    	type:'post',
				    	success: function(data){
				    		layer.close(index);
				    		successCallBack(2);
				      	}
				    }); 
					});
		    	}
		    }
		});
		//新增签证录入
		function add(){
			window.location.href = '${base}/admin/myData/touristVisainput/visaInputAdd.html?userId='+userid;
		 }
		
		function successCallBack(){
			 var url = '${base}/admin/myData/touristVisainput/getTouristVisaInput.html';
	        $.ajax({ 
	        	url: url,
	        	dataType:"json",
	        	data:{userId:userid},
	        	type:'post',
	        	success: function(data){
	        		orderobj.visaInputData = data.visaInputData;
	          	}
	        });
		}
		function closeWindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		</script>
		
	</body>
</html>