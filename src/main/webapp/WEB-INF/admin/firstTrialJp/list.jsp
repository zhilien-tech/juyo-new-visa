<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>初审-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/firstTrialJp.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<div class="content-wrapper" style="min-height: 848px;">
				<!-- <ul class="title">
					<li>初审</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul> -->
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row" style="margin-top:15px;"> 
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
									<c:forEach var="map" items="${data.status}">
										<option value="${map.key}">${map.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="" name="" placeholder="订单号/护照号/电话/申请人" />
							</div>
							<div class="col-md-6 left-5px">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:;" id="searchbtn">搜索</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-for="data in trialJapanData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.orderNumber}}</span></div>	
								<div><label>出行时间：</label><span>{{data.goTripTime}}</span></div>	
								<div><label>返回时间：</label><span>{{data.backTripTime}}</span></div>	
								<div><label>状态：</label><span>{{data.orderState}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="express" @click="expressFun"> </i>
									<i class="return"> </i>
								</div>
							</div>
							<ul class="card-content">
								<li class="everybody-info" v-for="item in data.everybodyinfo">
									<div><label>申请人：</label><span>{{item.applicantname}}</span></div>
									<div><label>护照号：</label><span>{{item.passportnum}}</span></div>
									<div><label>手机号：</label><span>{{item.telephone}}</span></div>
									<div><label>状态：</label><span>{{item.applicantstatus}}</span></div>
									<div>
										<i class="basicInfo"> </i>
										<i class="passportInfo" @click="passportFun"> </i>
										<i class="visaInfo" @click="visaInfoFun"> </i>
										<i class="qualified"> </i>
										<i class="unqualified" @click="unqualifiedFun"> </i>
									</div>
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>
			</div>
		</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/references/common/js/base/cardList.js"></script>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		//异步加载的URL地址
	    var url="${base}/admin/firstTrialJp/trialListData.html";
	    //vue表格数据对象
	    var _self;
	   /*  var firstTrial = {
	    		FTdata:[
	    		       {orderNumber:"170808-JP0001",
	    		    	travelDate:"2017-10-22",
	    		    	returnDate:"2017-12-22",
	    		    	state:"不合格",
		    		    people:[
		    		       {name:"宋仲基",passportNo:"G73635124",phone:"15132636399",state:"初审"},
		    		       {name:"马斯洛",passportNo:"G73602220",phone:"15132336388",state:"不合格"},
		    		       {name:"宋仲基",passportNo:"G73635124",phone:"15132636399",state:"初审"}
		    		    ]
	    		       },
	    		],
	    }; */
	    var firstTrial = {trialJapanData:""};
		new Vue({
			el: '#card',
			data: firstTrial,
			created:function(){
	            _self=this;
	            $.ajax({ 
	            	url: url,
	            	dataType:"json",
	            	type:'post',
	            	success: function(data){
	            		_self.trialJapanData = data.trialJapanData;
	              	}
	            });
	        },
			methods:{
				/* editClick:function(){//编辑图标  页面跳转
					window.location.href = '${base}/admin/firstTrialJp/edit.html';
				} */
				visaDetail:function(orderid){
	        		//跳转到签证详情页面
	        		window.open('${base}/admin/firstTrialJp/trialDetail.html?orderid='+orderid);
	        		//console.log(message);
	        		//alert(JSON.stringify(event.target));
	        	},
	        	expressFun:function(){//跳转快递弹层页面
	        		layer.open({
	        		    type: 2,
	        		    title: false,
	        		    closeBtn:false,
	        		    fix: false,
	        		    maxmin: false,
	        		    shadeClose: false,
	        		    scrollbar: false,
	        		    area: ['900px', '550px'],
	        		    content: '${base}/admin/firstTrialJp/express.html'
	        	    });
	        	},
	        	passportFun:function(){
	        		layer.open({
	        		    type: 2,
	        		    title: false,
	        		    closeBtn:false,
	        		    fix: false,
	        		    maxmin: false,
	        		    shadeClose: false,
	        		    scrollbar: false,
	        		    area: ['900px', '550px'],
	        		    content: '${base}/admin/firstTrialJp/passport.html'
	        	    });
	        	},
	        	visaInfoFun:function(){
	        		layer.open({
	        		    type: 2,
	        		    title: false,
	        		    closeBtn:false,
	        		    fix: false,
	        		    maxmin: false,
	        		    shadeClose: false,
	        		    scrollbar: false,
	        		    area: ['900px', '550px'],
	        		    content: '${base}/admin/firstTrialJp/visaInfo.html'
	        	    });
	        	},
	        	unqualifiedFun:function(){
	        		layer.open({
	        		    type: 2,
	        		    title: false,
	        		    closeBtn:false,
	        		    fix: false,
	        		    maxmin: false,
	        		    shadeClose: false,
	        		    scrollbar: false,
	        		    area: ['800px', '402px'],
	        		    content: '${base}/admin/firstTrialJp/unqualified.html'
	        	    });
	        	}
			}
		});
		/* function search(){
			var status = $('#status').val();
			var source = $('#source').val();
			var visaType = $('#visaType').val();
			var sendSignDate = $('#sendSignDate').val();
			var signOutDate = $('#signOutDate').val();
			var searchStr = $('#searchStr').val();
			$.ajax({ 
	        	url: url,
	        	data:{status:status,source:source,visaType:visaType,sendSignDate:sendSignDate,signOutDate:signOutDate,searchStr:searchStr},
	        	dataType:"json",
	        	type:'post',
	        	success: function(data){
	        		_self.orderJpData = data.orderJp;
	        		//console.log(JSON.stringify(data));
	          	}
	        });
		}*/
	</script>
	
</body>
</html>
