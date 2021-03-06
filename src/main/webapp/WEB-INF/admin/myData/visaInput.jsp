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
		<title>出境记录</title>
		<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/plugins/datatables/dataTables.bootstrap.css">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v='20180510'">
		<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
	    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
		<link rel="stylesheet" href="${base}/references/public/css/pikaday.css?v='20180510'">
		<link rel="stylesheet" href="${base}/references/public/css/style.css?v='20180510'">
		<style type="text/css">
			[v-cloak]{display : none}
			.multiPass_roundTrip-div{width: 120px;float: right;position: relative;top: 5px;}
			.content-wrapper, .right-side, .main-footer{margin-left: 0;}
			.btnState{color: #b0b0b0 !important;border: solid 1px #d2d6de;background-color: #fff;margin-right: 2.26rem;}
			.btnState-true{color: #287ae7 !important;border-color: #cee1ff;}
			.card-head div:nth-child(1){width:20.8%;}
			.card-head div:nth-child(2){width:12.5%;}
			.card-head div:nth-child(3){ width: 66px;float: right;}
			.everybody-info div:nth-child(1){width:10%;}
			.everybody-info div:nth-child(2){width:12%;}
			.everybody-info div:nth-child(3){width:12%;}
			.everybody-info div:nth-child(4){width:14%;}
			.everybody-info div:nth-child(5){width:11%;}
			.card-list{height: 87px; margin-top: 10px;}
			.card-list:hover{height: 87px !important;min-height: 87px !important;}
			.box-header{padding-right: 16px;}
			.box-body{padding-top:0;}
			.qz-head { border-bottom:2px solid #deecff; padding:15px 20px; display: table; width: 100%;}
			.btn-sm { width:110px !important;}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<div class="content-wrapper"  style="min-height: 848px;">
				<!-- <ul class="title">
					<li> </li>
				</ul> -->
				<div class="qz-head">
				<!-- <input type="button" value="编辑" id="editbasic" class="btn btn-primary btn-sm pull-right editbasic" onclick="editBtn();"/>  -->
				<a class="btn btn-primary btn-sm pull-right" href="javascript:add();" id="">添加已有签证</a>
			</div>
				<section class="content">
					<!-- <div class="box-header">检索条件
						<div class="row">
							<div class="col-md-12">
								<a class="btn btn-primary btn-sm pull-right" href="javascript:add();" id="">添加已有签证</a>
							</div>
						</div>
					</div>end 检索条件 -->
					
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-cloak v-for="data in visaInputData">
							<div class="card-head">
								<div><label>国家：</label><span>{{data.visacountry}}</span></div>	
								<div><label>签证号：</label><span>{{data.visanum}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="edit(data.id)"> </i>
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
		        var url = '${base}/admin/touristVisa/getTouristVisaInput.html';
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
		    	      /* layer.open({
		    	    	    type: 2,
		    	    	    title: false,
		    	    	    closeBtn:false,
		    	    	    fix: false,
		    	    	    maxmin: false,
		    	    	    shadeClose: false,
		    	    	    scrollbar: false,
		    	    	    area: ['900px', '550px'],
		    	    	    content: '${base}/admin/visaJapan/visainput/visaInputUpdate.html?id='+visainputid
		    	    	  }); */
		    	      window.location.href = '${base}/admin/visaJapan/visainput/visaInputUpdate.html?id='+visainputid+'&orderid='+orderJpId+'&isvisa=${isvisa}&tourist=1';
		    	}
		    }
		});
		//新增签证录入
		function add(){
			window.location.href = '${base}/admin/visaJapan/visainput/visaInputAdd.html?applicantId='+applyid+'&orderid='+orderJpId+'&isvisa=${isvisa}&tourist=1';
	      /* layer.open({
	    	    type: 2,
	    	    title: false,
	    	    closeBtn:false,
	    	    fix: false,
	    	    maxmin: false,
	    	    shadeClose: false,
	    	    scrollbar: false,
	    	    area: ['900px', '550px'],
	    	    content: '${base}/admin/visaJapan/visainput/visaInputAdd.html?applicantId='+applyid
	    	  }); */
		 }
		
		function successCallBack(){
			 var url = '${base}/admin/visaJapan/getJpVisaInputListData.html';
	        $.ajax({ 
	        	url: url,
	        	dataType:"json",
	        	data:{applyid:applyid},
	        	type:'post',
	        	success: function(data){
	        		orderobj.visaInputData = data.visaInputData;
	          	}
	        });
		}
		</script>
		
	</body>
</html>