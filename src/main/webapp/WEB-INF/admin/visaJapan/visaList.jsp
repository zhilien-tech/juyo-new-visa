<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>签证-日本</title>
	<link rel="stylesheet" href="${base}/references/public/css/visaJapan.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<div class="content-wrapper"  style="min-height: 848px;">
				<!-- <ul class="title">
					<li>签证</li>
					<li class="arrow"></li>
					<li>日本</li>
				</ul> -->
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="sendSignDate" name="sendSignDate" placeholder="送签时间" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-2 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="signOutDate" name="signOutDate" placeholder="出签时间" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-3 left-5px">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-for="data in visaJapanData">
							<div class="card-head">
								<div><label>订单号：</label><span>{{data.japannumber}}</span></div>	
								<div><label>受付番号：</label><span>{{data.number}}</span></div>	
								<div><label>送签时间：</label><span>{{data.sendingtime}}</span></div>
								<div><label>出签时间：</label><span>{{data.signingtime}}</span></div>
								<div><label>状态：</label><span>{{data.japanstate}}</span></div>	
								<div><label>人数：</label><span>{{data.peoplenumber}}</span></div>	
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="shiShou" v-on:click="revenue(data.id)"> </i>
									<i class="sendZB"> </i>
									<i class="ZBchange"> </i>
									<i class="ZBcancel"> </i>
									<i class="download"> </i>
									<i class="handoverTable"> </i>
									<i class="visaInput"> </i>
									<i class="afterSales"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="item in data.everybodyinfo">
									<div><label>申请人：</label><span>{{item.applicant}}</span></div>
									<div><label>护照号：</label><span>{{item.passportno}}</span></div>
									<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
									<div><label>资料：</label><span>{{item.data}}</span></div>
									<div><!-- <i> </i> --></div>
								</li>
							</ul>
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
	<%-- <script src="${base}/admin/visaJapan/visaList.js"></script> --%>
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/visaJapan/visaListData.html";
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
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到签证详情页面
        		window.open('${base}/admin/visaJapan/visaDetail.html?orderid='+orderid);
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
        	}
        }
	});
	//跳转 签证详情页
	function edit(orderid){
		window.location.href = '${base}/admin/visaJapan/visaDetail.html?orderid='+orderid;
	}
	
	function search(){
		var status = $('#status').val();
		var sendSignDate = $('#sendSignDate').val();
		var signOutDate = $('#signOutDate').val();
		var searchStr = $('#searchStr').val();
		$.ajax({ 
        	url: url,
        	data:{status:status,sendSignDate:sendSignDate,signOutDate:signOutDate,searchStr:searchStr},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
          	}
        });
	}
	//回车事件
	function onkeyEnter(){
	    var e = window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode == 13){
	    	search();
		 }
	}
	
	function successCallBack(status){
		$.ajax({ 
        	url: url,
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
          	}
        });
		if(status){
			layer.msg('保存成功');
		}
	}
	</script>
</body>
</html>
