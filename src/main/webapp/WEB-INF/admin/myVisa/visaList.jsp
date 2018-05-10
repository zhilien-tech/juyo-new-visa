<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title></title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css">
	<link rel="stylesheet" href="${base}/references/public/css/visaJapan.css">
	<style>
	[v-cloak]{display:none}
	.box-body {  overflow:hidden;}
	.card-head { overflow:hidden; white-space:nowrap;}
	.card-head span { font-size:12px;}
	.everybody-info {position:relative; }
	.cf { overflow:visible !important;}
	.whiteSpace {  overflow:hidden; text-overflow:ellipsis; white-space:nowrap; width:390px;}
	.showInfo { cursor:pointer; }
	.card-list { cursor: pointer;}
	.hideInfo { display:none; position:absolute; top:-33px; right:10%;background:#eee;height:30px;line-height:30px; font-size:12px; padding:0 10px; border-radius:10px;}
	</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="head">
			<span>
				办理中签证 >>申请人
			</span>
		</div>
				<section class="content">
					<div class="box-body" id="card"><!-- 卡片列表 -->
					<div v-on:click="toInProcess(data.id)" class="card-list" v-cloak v-for="data in visaJapanData">
						<!-- <div class="card-list" v-for="data in visaJapanData"> -->
							<div class="card-head">
								<div><label>订单号：</label><span><!-- <a v-on:click="visaDetail(data.id)"  href="javascript:;"> -->{{data.japannumber}}<!-- </a> --></span></div>	
								<div><label>受付番号：</label><span>{{data.number}}</span></div>	
								<div><label>送签时间：</label><span>{{data.sendingtime}}</span></div>
								<div><label>出签时间：</label><span>{{data.signingtime}}</span></div>
								<div><label>人数：</label><span>{{data.peoplenumber}}</span></div>	
								<div style="text-align: center;"><label></label><span style="font-weight:bold;font-size:16px;">{{data.visastatus}}</span></div>	
								
							</div>
							<ul class="card-content cf">
								<li class="everybody-info cf" v-for="(item,index) in data.everybodyinfo">
									<span v-if="index === 0" style="display:block; height:31px;">
										<div><label>申请人：</label><span>{{item.applicant}}</span></div>
										<div><label>护照号：</label><span>{{item.passportno}}</span></div>
										<div><label>资料类型：</label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label>资料：</label><span v-html="item.data" class="showInfo"></span></div>
										<span class="hideInfo"></span>
									</span>
									<span v-else  style="display:block; height:31px;">
										<div><label style="width:48px;">      </label><span>{{item.applicant}}</span></div>
										<div><label style="width:48px;">　　　　</label><span>{{item.passportno}}</span></div>
										<div><label style="width:60px;">　　　　　</label><span>{{item.datatype}}</span></div>
										<div class="whiteSpace"><label style="width:36px;">　　　</label><span v-html="item.data" class="showInfo"></span></div>
										<span class="hideInfo"></span>
									</span>
								</li>
							</ul> 
						</div>
					</div><!-- end 卡片列表 -->
				</section>
		<input type="hidden" id="pageNumber" name="pageNumber" value="1">
		<input type="hidden" id="pagetotal" name="pagetotal">
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script type="text/javascript" src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${base}/references/common/js/vue/vue.min.js"></script>
	<script type="text/javascript" src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<script type="text/javascript" src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script type="text/javascript" src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/myVisa/visaListData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {visaJapanData:""},
		created:function(){
            _self=this;
            var start = new Date().getTime();//起始时间
            $.ajax({ 
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
            		 var end = new Date().getTime();//接受时间
            		_self.visaJapanData = data.visaJapanData;
            		$('#pagetotal').val(data.pagetotal);
                     
                     //alert((end - start)+"ms");//返回函数执行需要时间
              	}
            });
        },
        methods:{
        	toInProcess:function(id){
        		window.location.href = '/admin/myVisa/inProcessVisa.html?orderJpId=' + id;
        	}
        }
	});
	
	function successCallBack(){
		$.ajax({ 
        	url: url,
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
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
			var status = $('#status').val();
			var sendSignDate = $('#sendSignDate').val();
			/* var signOutDate = $('#signOutDate').val(); */
			var searchStr = $('#searchStr').val();
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
			    		$.each(data.visaJapanData,function(index,item){
			    			_self.visaJapanData.push(item);
			    		});
			    		//没有更多数据
			      	}
			    });
			}
	　　}
	});
	

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
	
	$(function(){
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
	});
	</script>
</body>
</html>
