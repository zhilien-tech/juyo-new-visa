<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<c:set var="url" value="${base}/admin/visaJapan" />
<!DOCTYPE html>
<html lang="en-US">
	<head>
		<meta charset="utf-8">
		<meta http-equlv="proma" content="no-cache" />
		<meta http-equlv="cache-control" content="no-cache" />
		<meta http-equlv="expires" content="0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
		<link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
		<!-- 本页css -->
		<link rel="stylesheet" href="${base}/references/common/css/topContacts.css?v='20180510'">
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<section class="content" id="card">
					<div class="box-body"  v-cloak v-for="data in myVisaData" style="cursor:default;position: relative;z-index:999;"><!-- 卡片列表 -->
						<div class="card-list" >
							<div class="content-main">
								<div class="contactL">
									<span>{{data.applicantname}}</span>
									<span>{{data.passport}}</span>
									<span>{{data.telephone}}</span>
										
								</div>
								<div class="contactR">
									<span style="cursor:pointer;"><a v-on:click="updateApplicant(data.applicantid);">基本信息</a></span>
									<span style="cursor:pointer;"><a v-on:click="passport(data.applicantid)">护照信息</a></span>
									<span style="cursor:pointer;"><a v-on:click="visa(data.applicantid)">签证信息</a></span>
									<span style="cursor:pointer;"><a v-on:click="visaInput(data.userid)">出境记录</a></span>
									<span style="cursor:pointer;"><a v-on:click="deleteApply(data.userid)">删除</a></span>
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
	        	updateApplicant:function(applyId){
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
	        	},
	        	passport:function(applyId){
	        		layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '80%'],
						content:'/admin/myData/passport.html?contact=1&applyId='+applyId
					});
	        	},
	        	visa:function(applyId){
	        		layer.open({
						type: 2,
						title: false,
						closeBtn:false,
						fix: false,
						maxmin: false,
						shadeClose: false,
						scrollbar: false,
						area: ['900px', '80%'],
						content:'/admin/myData/visa.html?contact=1&applyId='+applyId
					});
	        	},
	        	visaInput:function(userId){
	        		layer.open({
	    				type: 2,
	    				title: false,
	    				closeBtn:false,
	    				fix: false,
	    				maxmin: false,
	    				shadeClose: false,
	    				scrollbar: false,
	    				area: ['1000px', '80%'],
	    				content: '/admin/myData/touristVisainput/visaInput.html?userId='+userId
	    			});
	        	},
	        	deleteApply(userId){
	        		layer.confirm("您确认要删除吗？", {
	    				title:"删除",
	    				btn: ["是","否"], //按钮
	    				shade: false //不显示遮罩
	    			}, function(index){
	    			$.ajax({ 
	    		    	url: '/admin/myData/deleteApplicant',
	    		    	dataType:"json",
	    		    	data:{userId:userId},
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