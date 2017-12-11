<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>前台 - 日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
			  <link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  		  <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
          <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
          <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
          <link rel="stylesheet" href="${base}/references/public/css/style.css">
          <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	<style>
		.card-head div:nth-child(1){width:20%;}
		.card-head div:nth-child(2){width:20%;}
		.card-head div:nth-child(3){width: 135px;float: right;position: relative;right: -15px;}
		.everybody-info div:nth-child(1){width:11%;}
		.everybody-info div:nth-child(2){width:14%;}
		.everybody-info div:nth-child(3){width:14%;}
		.everybody-info div:nth-child(4){width:10%;}
		.everybody-info div:nth-child(5){width:10%;}
		.everybody-info div:last-child{float:right;width:85px;}
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
				<section class="content">
					<div class="box-header"><!-- 检索条件 -->
						<div class="row">
							<div class="col-md-2 left-5px right-0px">
								<select class="input-class input-sm" id="status" name="status">
									<option value="">状态</option>
									<option></option>
								</select>
							</div>
							<div class="col-md-3 left-5px right-0px">
								<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="订单号/联系人/电话/邮箱/申请人" onkeypress="onkeyEnter()"/>
							</div>
							<div class="col-md-7 left-5px">
								<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
								<a class="btn btn-primary btn-sm pull-right" href="javascript:;" id="">拍视频</a>
							</div>
						</div>
					</div><!-- end 检索条件 -->
					<div class="box-body" id="card"><!-- 卡片列表 -->
						<div class="card-list" v-for="data in receptionJpData">
							<div class="card-head cf">
								<div><label>订单号：</label><span>{{data.ordernumber}}</span></div>	
								<div><label>状态：</label><span>{{data.orderstatus}}</span></div>		
								<div>
									<label>操作：</label>
									<i class="edit" v-on:click="visaDetail(data.id)"> </i>
									<i class="shiShou" v-on:click="revenue(data.id)"> </i>
									<i class="visaTransfer" v-on:click="visaTransfer(data.id)"> </i>
								</div>
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="item in data.everybodyinfo">
									<div><label>申请人：</label><span>{{item.applicant}}</span></div>
									<div><label>护照号：</label><span>{{item.passportno}}</span></div>
									<div><label>快递号：</label><span>{{item.expressnum}}</span></div>
									<div><label>方式：</label><span>{{item.expresstype}}</span></div>
									<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
									<div><label>资料：</label><span>{{item.data}}</span></div>
									<!-- <div><i class="videoShoot"> </i></div> -->
								</li>
							</ul>
						</div>
					</div><!-- end 卡片列表 -->
				</section>

	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<%-- <script src="${base}/admin/visaJapan/visaList.js"></script> --%>
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/receptionJP/listData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {receptionJpData:""},
		created:function(){
            _self=this;
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		_self.receptionJpData = data.receptionJpData;
            		console.log(JSON.stringify(_self.receptionJpData));
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到详情页面
        		window.open('${base}/admin/receptionJP/receptionDetail.html?orderid='+orderid);
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
        		    content: '${base}/admin/receptionJP/revenue.html?orderid='+orderid
        		  });
        	},
        	visaTransfer:function(orderid){
        		 $.ajax({ 
                 	url: '${base}/admin/receptionJP/visaTransfer',
                 	dataType:"json",
                 	data:{orderid:orderid},
                 	type:'post',
                 	success: function(data){
                 		layer.msg("移交签证成功");
                   	}
                 });
        	}
        }
	});
	
	function search(){
		var status = $('#status').val();
		var searchStr = $('#searchStr').val();
		$.ajax({ 
        	url: url,
        	data:{status:status,searchStr:searchStr},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.receptionJpData = data.receptionJpData;
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
        		_self.receptionJpData = data.receptionJpData;
          	}
        });
		if(status){
			layer.msg('保存成功');
		}
	}
	
	
	</script>
</body>
</html>
