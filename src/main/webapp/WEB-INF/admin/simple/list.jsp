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
	<title>精简版-日本</title>
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap.css">
  	<link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/AdminLTE.css?v=<%=System.currentTimeMillis() %>">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/skin-blue.css">
    <link rel="stylesheet" href="${base}/references/public/dist/newvisacss/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${base}/references/public/css/pikaday.css">
    <link rel="stylesheet" href="${base}/references/public/css/style.css?v=<%=System.currentTimeMillis() %>">
    <script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.js"></script>
	<link rel="stylesheet" href="${base}/references/public/css/visaJapan.css?v=<%=System.currentTimeMillis() %>">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" href="${base}/references/public/bootstrap/css/daterangepicker-bs3.css">
	<!-- 订单切换卡 样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/switchCardOfOrder.css">
	<!-- 本页css -->
	<link rel="stylesheet" href="${base}/references/common/css/simpleList.css?v=<%=System.currentTimeMillis() %>">
	<!-- 加载中。。。样式 -->
	<link rel="stylesheet" href="${base}/references/common/css/spinner.css?v=<%=System.currentTimeMillis() %>">
	<style>
		[class*=" datetimepicker-dropdown"]:before{
			top:-8px;
		}
			.form-control{
				height: 30px;
			}
			.datetimepicker{
				top:67px!important;
				position: fixed;

			}

			/* 2018_07_30 */
			/* .card-content {

			} */
			.card-content {
				width: 72%!important;
			}
			.card-content .visaListSpan div{
				width: 19.5%!important;
			}

			.everybody-info div:nth-child(4) {
				margin-left: 0;
			}

			/* .card-content {
				float: left;
			} */

			.card-content-2{
				top: 0;
				right: 0;
				position: absolute;
				width: 28%;
				height: 100%;
			}

			.card-content-2 span {
				top: 50%;
				left: 0;
				position: absolute;
				margin-top: -12px;
				height: 24px;
				line-height: 24px;
				font-size: 18px;
			}
			.card-content-2 span b{
				font-size: 14px;
				font-weight: 400;
			}
			.everybody-info div:nth-child(3){
				width: 16.5%!important;
			}
			.everybody-info div:nth-child(2){
				width: 22.5%!important;
			}

		</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="box-header"style=" padding-top:25px!important"><!-- 检索条件 -->
		<!-- 切换卡按钮 start -->

		<!-- 切换卡按钮 end -->
		<div class="row searchMar tab-header">
			<div class="col-md col-md-2" style="width:11%">
				<select class="input-class input-sm" style="width:110px;" id="status" name="status" onchange="changestatus()">
					<option value="">状态</option>
					<c:forEach items="${obj.orderstatus }" var="orstatus">
						<option value="${orstatus.key }">${orstatus.value}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md col-md-2 left-5px right-0px" style="width:16%;margin-left:-3.6%;">
				<select class="input-class input-sm" id="songqianshe" style="width:110px;" name="songqianshe" onchange="changestatus()">
					<option value="">送签社简称</option>
					<c:forEach items="${obj.songqianlist }" var="songqianlist">
						<option value="${songqianlist.id}">${songqianlist.shortName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md col-md-1 left-5px right-0px" style="width:8.5%;margin-left:-8%;">
				<select class="input-class input-sm" id="employee" name="employee" onchange="changestatus()">
					<option value="">员工</option>
					<c:forEach items="${obj.employees }" var="employees">
						<option value="${employees.userid }">${employees.username }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md col-md-1 left-5px right-0px" style="width:2.3%;margin-left:0.5%;">
				<select class="input-class input-sm" style="width:100px;" id="visatype" name="visatype" onchange="changestatus()">
					<option value="">签证类型</option>
					<c:forEach items="${obj.mainsalevisatypeenum }" var="visatype">
						<option value="${visatype.key }">${visatype.value }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md col-md-1 left-5px right-0px card-list-date" style="width:18.5%;margin-left:4.5%;">
				<input type="text" class="input-sm input-class form-control" id="orderDate" name="orderDate" placeholder="下单时间" onkeypress="onkeyEnter()"/>
				<input type="hidden" id="orderstartdate" name="orderstartdate"/>
				<input type="hidden" id="orderenddate" name="orderenddate"/>
			</div>
			<div class="col-md col-md-1 left-5px right-0px card-list-date" style="width:9.5%;margin-left:0.5%;">
				<input type="text" class="input-sm input-class form-control" id="sendSignDate" name="sendSignDate" placeholder="送签时间" onkeypress="onkeyEnter()"/>
				<input type="hidden" id="sendstartdate" name="sendstartdate"/>
				<input type="hidden" id="sendenddate" name="sendenddate"/>
			</div>
			<div class="col-md col-md-3 left-5px right-0px" style="margin-left:0.5%;width:15.5%">
				<input type="text" class="input-sm input-class" id="searchStr" name="searchStr" placeholder="护照号/申请人/受付番号" onkeypress="onkeyEnter()"/>
			</div>
			<div class="col-md col-md-1 ">
				<a class="btn btn-primary btn-sm pull-left" href="javascript:search();" id="searchbtn">搜索</a>
			</div>
			<div class="col-md col-md-1  " style="    margin-left: 10px;">
				<a class="btn btn-primary btn-sm pull-right" onclick="addOrder();">下单</a>
			</div>
		</div>
	</div><!-- end 检索条件 -->
	<section class="card-list-content content">

		<div id="card" style="margin-left: -5px;" v-cloak>
			<div class = "card-tj">
				<span>订单总数(单)： {{visaJapanDataS.orderscount}}</span>
				<span>总人数(人)： {{visaJapanDataS.peopletotal}}</span>
				<!-- <span>作废数(单)： {{visaJapanDataS.disableorder}}</span>
				<span>作废人数(人)： {{visaJapanDataS.disablepeople}}</span> -->
				<span>招宝成功(单)： {{visaJapanDataS.zhaobaoorder}}</span>
				<span>招宝成功(人)： {{visaJapanDataS.zhaobaopeople}}</span>
				<span>单组单人： {{visaJapanDataS.singleperson}}</span>
				<span>单组多人： {{visaJapanDataS.multiplayer}}</span>
			</div>

			<div style="margin-top:123px!important;" class="box-body" v-cloak><!-- 卡片列表 -->

				<div class="card-list" v-for="data in visaJapanData">
					<div class="card-head">
						<div><label>订单号：</label><span><a v-on:click="visaDetail(data.id)" href="javascript:;">{{data.japannumber}}</a></span></div>
						<div><label>受付番号：</label><span>{{data.acceptdesign}}</span></div>
						<div><label>送签时间：</label><span>{{data.sendingtime}}</span></div>
						<div><label>操作人：</label><span>{{data.opname}}</span></div>
						<div><label>送签社：</label><span>{{data.shortname}}</span></div>
						<div><label></label><span>{{data.visatype}}</span></div>
						<!-- <div><label></label><span style="font-weight:bold;font-size:16px;">
							<span v-if="data.visastatus === '招宝成功'">
								<font color="red">{{data.visastatus}}</font>
							</span>
							<span v-else-if="data.visastatus === '发招宝中'">
								<font>{{data.visastatus}}</font>

								<div class="spinner">
								  <div class="bounce1"></div>
								  <div class="bounce2"></div>
								  <div class="bounce3"></div>
								</div>
							</span>
							<span v-else-if="data.isdisabled == 1">
									作废
								</span>
								<span v-else>
									{{data.visastatus}}
								</span>

							</span>
						</div>	 -->
						<div v-if="data.isdisabled != 1">
							<label>操作：</label>
							<i class="edit" v-on:click="visaDetail(data.id)"> </i>
							<!-- <i class="shiShou" v-on:click="revenue(data.id)"> </i> -->

							<span v-if="data.zhaobaocomplete == 0 && data.visatype != 14">
								<i class="sendZB" v-on:click="sendzhaobao(data.id)"> </i>
							</span>
							<!-- <span v-else>
								<i class="theTrial1"> </i>
							</span> -->

							<span v-else-if="data.zhaobaocomplete ==1">
								<i class="ZBchange" v-on:click="sendInsurance(data.id,19)"> </i>
								<i class="ZBcancel" v-on:click="sendInsurance(data.id,22)"> </i>
							</span>

							<!-- <span v-else>
								<i class="theTrial1"> </i>
								<i class="theTrial1"> </i>
							</span> -->

							<!-- <i class="Refusal" v-on:click=""></i> -->
							<i class="download" v-on:click="downLoadFile(data.id)"> </i>
							<!-- <i class="handoverTable"> </i> -->
							<!-- 作废按钮 -->
							<i class="toVoid" v-on:click="disabled(data.id,data.zhaobaoupdate)"> </i>
						</div>
						<div v-else>
							<label>操作：</label>
							<i class="toVoid1" v-on:click="undisabled(data.id)"> </i>
						</div>
					</div>
					<div style="position: relative;">
						<ul class="card-content">
							<li class="everybody-info" v-for="(item,index) in data.everybodyinfo">
								<span v-if="index === 0" class="visaListSpan">
									<div class="div-s1"><label>申请人：</label><span>{{item.applicant}}</span></div>

									<div><label>英文：</label><span>{{item.applicanten}}</span></div>
									<!-- <div><label>性别：</label><span>{{item.sex}}</span></div> -->
									<div><label>现居住地：</label><span>{{item.province}}</span></div>
									<div><label>护照号：</label><span>{{item.passportno}}</span></div>

									<div><label>手机号：</label><span>{{item.telephone}}</span></div>


									<!-- <span class="hideInfo"></span> -->
								</span>
								<span v-else class="visaListSpan">
									<div class="div-s2"><label style="opacity:0;">申请人：</label><span>{{item.applicant}}</span></div>
									<div><label>英文：</label><span>{{item.applicanten }}</span></div>
									<!-- <div><label>性别：</label><span>{{item.sex}}</span></div> -->
									<div><label>现居住地：</label><span>{{item.province}}</span></div>
									<div><label style="opacity:0;">护照号：</label><span>{{item.passportno}}</span></div>

									<div><label style="opacity:0;">手机号：</label><span>{{item.telephone}}</span></div>


									<!-- <span class="hideInfo"></span> -->
								</span>
							</li>
						</ul>
						<div class="card-content-2">
							<span v-if="data.visastatus === '招宝成功'">
								<font color="red">{{data.visastatus}}</font>
							</span>

							<span v-else-if="data.visastatus === '发招宝中'">
								<font>{{data.visastatus}}</font>
								<div class="spinner">
									<div class="bounce1"></div>
									<div class="bounce2"></div>
									<div class="bounce3"></div>
								</div>
							</span>

							<span v-else-if="data.isdisabled == 1">
								作废
							</span>

							<span v-else-if="data.visastatus === '发招宝失败'">
								{{data.visastatus}}
								<b>{{data.errormsg}}</b>
							</span>


							<span v-else>
								{{data.visastatus}}
							</span>

						</div>
					</div>
				</div>
			</div><!-- end 卡片列表 -->
		</div>
	</section>
		<input type="hidden" id="pageNumber" name="pageNumber" value="1">
		<input type="hidden" id="pagetotal" name="pagetotal">
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/references/public/plugins/jQuery/jquery-3.2.1.min.js"></script>
	<script src="${base}/references/public/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/moment.min.js"></script>
	<script type="text/javascript" src="${base}/references/public/bootstrap/js/daterangepicker.js"></script>
	<script src="${base}/references/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/references/common/js/layer/layer.js"></script>
	<script src="${base}/references/common/js/vue/vue.min.js"></script>
	<script src="${base}/references/public/plugins/jquery.fileDownload.js"></script>
	<script src="${base}/references/common/js/base/base.js"></script><!-- 公用js文件 -->
	<%-- <script src="${base}/admin/visaJapan/visaList.js"></script> --%>
	<script src="${base}/references/common/js/base/cardList.js"></script><!-- 卡片式列表公用js文件 -->
	<script src="${base}/references/common/js/base/baseIcon.js"></script><!-- 图标提示语 -->
	<script src="${base}/admin/simple/listpagejs.js?v=<%=System.currentTimeMillis() %>"></script>
	<script type="text/javascript">
	//异步加载的URL地址
    var url="${base}/admin/simple/listData.html";
    //vue表格数据对象
    var _self;
	new Vue({
		el: '#card',
		data: {
			visaJapanData:[],
			visaJapanDataS:{}
		},
		created:function(){
            _self=this;
            $.ajax({
            	url: url,
            	dataType:"json",
            	type:'post',
            	success: function(data){
	            	_self.visaJapanData = data.visaJapanData;
            		if(data.visaJapanData.length != 0){
	            		_self.visaJapanDataS = data.entity;
            		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				/* disableorder:0,
            				disablepeople:0, */
            				zhaobaoorder:0,
            				zhaobaopeople:0,
            				singleperson:0,
            				multiplayer:0
            			}
            		}

            		$('#pagetotal').val(data.pagetotal);
              	}
            });
        },
        methods:{
        	visaDetail:function(orderid){
        		//跳转到签证详情页面
        		window.open('${base}/admin/simple/editOrder.html?orderid='+orderid);
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
        		    content: '${base}/admin/visaJapan/revenue.html?orderid='+orderid+'&type=1'
        		  });
        	},
        	sendInsurance:function(orderid,visastatus){
        		var title = '';
        		if(visastatus == 19 || visastatus == 22|| visastatus == 27|| visastatus == 26){
        			if(visastatus == 19){
        				title = '确定要招宝变更吗？';
        			}else if(visastatus == 22){
        				title = '确定要招宝取消吗？';
        			}else if(visastatus == 27){
        				title = '确定要拒签吗？';
        			}else if(visastatus == 26){
        				title = '确定要作废吗？';
        			}
        			layer.confirm(title, {
    					title:"提示",
    					btn: ["是","否"], //按钮
    					shade: false //不显示遮罩
    				}, function(index){
    					$.ajax({
    	                 	url: '${base}/admin/visaJapan/sendInsurance',
    	                 	data:{orderid:orderid,visastatus:visastatus},
    	                 	dataType:"json",
    	                 	type:'post',
    	                 	success: function(data){
    	                 		if(visastatus == 19){
    	                 			parent.successCallBack(5);
    		                 		//layer.msg('招宝变更');
    	                 		}else if(visastatus == 22){
    	                 			parent.successCallBack(6);
    		                 		//layer.msg('招宝取消');
    	                 		}else if(visastatus == 27){
    	                 			parent.successCallBack(7);
    		                 		//layer.msg('报告拒签');
    	                 		}else if(visastatus == 26){
    	                 			parent.successCallBack(10);
    	                 		}
    	                 		//更新列表数据
    	                 		var orderAuthority = "allOrder";
    							$(".searchOrderBtn").each(function(){
    								if($(this).hasClass("bgColor")){
    									orderAuthority = $(this).attr("name");
    								}
    							});
    	                 		$.ajax({
    	                        	url: url,
    	                        	data:{orderAuthority:orderAuthority},
    	                        	dataType:"json",
    	                        	type:'post',
    	                        	success: function(data){
    	                        		_self.visaJapanData = data.visaJapanData;
    	                          	}
    	                        });
    	                 		layer.close(index);
    	                   	}
    	                 });
    				});
         		}else{
         			$.ajax({
	                 	url: '${base}/admin/visaJapan/sendInsurance.html',
	                 	data:{orderid:orderid,visastatus:visastatus},
	                 	dataType:"json",
	                 	type:'post',
	                 	success: function(data){
                 			parent.successCallBack(11);
	                 		//更新列表数据
	                 		var orderAuthority = "allOrder";
							$(".searchOrderBtn").each(function(){
								if($(this).hasClass("bgColor")){
									orderAuthority = $(this).attr("name");
								}
							});
	                 		$.ajax({
	                        	url: url,
	                        	data:{orderAuthority:orderAuthority},
	                        	dataType:"json",
	                        	type:'post',
	                        	success: function(data){
	                        		_self.visaJapanData = data.visaJapanData;
	                          	}
	                        });
	                   	}
	                 });
         		}

        	},
        	sendzhaobao:function(orderid){
        		$.ajax({
                 	url: '${base}/admin/visaJapan/validateInfoIsFull.html',
                 	data:{orderjpid:orderid},
                 	dataType:"json",
                 	type:'post',
                 	async:false,
                 	success: function(data){
                 		var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
                 		if(data.data){
                 			url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data+'&type=1';
                 		}
		        		layer.open({
		        		    type: 2,
		        		    title: false,
		        		    closeBtn:false,
		        		    fix: false,
		        		    maxmin: false,
		        		    shadeClose: false,
		        		    scrollbar: false,
		        		    area: ['400px', '300px'],
		        		    content: url
		        		  });
                   	}
                 });
        	},
        	disabled:function(orderid,zhaobaoupdate){//作废
        		if(zhaobaoupdate == 1){
        			layer.msg("请先取消招宝，再进行作废操作");
        		}else{
	        		layer.confirm("您确认要<font color='red'>作废</font>吗？", {
	    				title:"作废",
	    				btn: ["是","否"], //按钮
	    				shade: false //不显示遮罩
	    			}, function(){
	    				layer.load(1);
	    				$.ajax({
	    					url : '/admin/simple/disabled',
	    					dataType : "json",
	    					data : {
	    						orderId : orderid
	    					},
	    					type : 'post',
	    					success : function(data) {
	    						layer.closeAll("loading");
	    						layer.msg("操作成功", {
	    							time: 500,
	    							end: function () {
	    								successCallBack(12);
	    							}
	    						});
	    					}
	    				});
	    			});
        		}
        	},
        	undisabled:function(orderid){//还原
        		layer.confirm("您确认要<font color='red'>还原</font>吗？", {
    				title:"还原",
    				btn: ["是","否"], //按钮
    				shade: false //不显示遮罩
    			}, function(){
    				layer.load(1);
    				$.ajax({
    					url : '/admin/simple/undisabled',
    					dataType : "json",
    					data : {
    						orderId : orderid,
    					},
    					type : 'post',
    					success : function(data) {
    						layer.closeAll("loading");
    						layer.msg("操作成功", {
    							time: 1000,
    							end: function () {
    								successCallBack(12);
    							}
    						});
    					}
    				});
    			});
        	},
        	downLoadFile:function(orderid){
        		$.ajax({
                 	url: '${base}/admin/visaJapan/validateDownLoadInfoIsFull.html',
                 	data:{orderjpid:orderid},
                 	dataType:"json",
                 	type:'post',
                 	async:false,
                 	success: function(data){
                 		//var url = '${base}/admin/visaJapan/sendZhaoBao.html?orderid='+orderid;
                 		console.log(data.data);
                 		if(data.data){
                 			var url = '${base}/admin/visaJapan/sendZhaoBaoError.html?orderid='+orderid+'&data='+data.data+'&type=1';
			        		layer.open({
			        		    type: 2,
			        		    title: false,
			        		    closeBtn:false,
			        		    fix: false,
			        		    maxmin: false,
			        		    shadeClose: false,
			        		    scrollbar: false,
			        		    area: ['400px', '300px'],
			        		    content: url
			        		  });
                 		}else{
                 			layer.load(1);
                    		$.fileDownload("${base}/admin/visaJapan/downloadFile.html?orderid=" + orderid, {
            			         successCallback: function (url) {
            			        	 layer.closeAll('loading');
            			         },
            			         failCallback: function (html, url) {
            			        	layer.closeAll('loading');
            			        	layer.msg("下载失败");
            			         }
            			     });
                 		}
                   	}
                 });
        	},
        	//签证录入
    		visainput:function(applyId,orderid){
    			layer.open({
    				type: 2,
    				title: false,
    				closeBtn:false,
    				fix: false,
    				maxmin: false,
    				shadeClose: false,
    				scrollbar: false,
    				area: ['900px', '80%'],
    				content: '/admin/visaJapan/visaInput.html?applyid='+applyId+'&orderid='+orderid+'&isvisa=1'
    			});
    		}
        }
	});
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
			var visatype = $("#visatype").val();
			var sendstartdate = $('#sendstartdate').val();
			var sendenddate = $('#sendenddate').val();
			var orderstartdate = $('#orderstartdate').val();
			var orderenddate = $('#orderenddate').val();

			var orderDate = $('#orderDate').val();
			var songqianshe = $("#songqianshe").val();
			var employee = $("#employee").val();
			/* var signOutDate = $('#signOutDate').val(); */
			var searchStr = $('#searchStr').val();
			console.log("pageNumber:"+pageNumber);
			console.log("pagetotal:"+pagetotal);
			//异步加载数据
			if(pageNumber <= pagetotal){
				//遮罩
				layer.load(1);
				var orderAuthority = "allOrder";
				$(".searchOrderBtn").each(function(){
					if($(this).hasClass("bgColor")){
						orderAuthority = $(this).attr("name");
					}
				});
				$.ajax({
			    	url: url,
			    	data:{status:status,visatype:visatype,sendstartdate:sendstartdate,sendenddate:sendenddate,orderstartdate:orderstartdate,orderenddate:orderenddate,sendSignDate:sendSignDate,orderDate:orderDate,employee:employee,songqianshe:songqianshe,searchStr:searchStr,pageNumber:pageNumber,orderAuthority:orderAuthority},
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


	//跳转 签证详情页
	function edit(orderid){
		window.location.href = '${base}/admin/simple/visaDetail.html?orderid='+orderid;
	}


	$(function(){
		$(".btnList").click(function(){
			$(this).addClass('bgColor').siblings().removeClass('bgColor');
			clearSearchEle();
			search();
		});


	});

	function clearSearchEle(){
		//检索框
		$("#status").val("");
		$("#sendSignDate").val("");
		$("#searchStr").val("");
		$("#visatype").val("");
		$("#sendstartdate").val("");
		$("#sendenddate").val("");
		$('#orderstartdate').val("");
		$('#orderenddate').val("");
		//分页项
		$("#pageNumber").val(1);
		$("#pageTotal").val("");
		$("#pageListCount").val("");
	}

	function search(){
		var status = $('#status').val();
		var sendSignDate = $('#sendSignDate').val();
		var sendstartdate = $('#sendstartdate').val();
		var sendenddate = $('#sendenddate').val();
		var orderDate = $('#orderDate').val();
		var orderstartdate = $('#orderstartdate').val();
		var orderenddate = $('#orderenddate').val();
		var songqianshe = $("#songqianshe").val();
		var employee = $("#employee").val();
		var visatype = $("#visatype").val();
		//var signOutDate = $('#signOutDate').val();
		var searchStr = $('#searchStr').val();
		var orderAuthority = "allOrder";
		var pageNumber = $('#pageNumber').val();
		console.log('#searchPageNumber:'+pageNumber);
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});

		$.ajax({
        	url: url,
        	data:{
        		status:status,
        		visatype:visatype,
        		songqianshe:songqianshe,
        		employee:employee,
        		sendSignDate:sendSignDate,
        		orderDate:orderDate,
        		searchStr:searchStr,
        		sendstartdate:sendstartdate,
        		sendenddate:sendenddate,
        		orderstartdate:orderstartdate,
        		orderenddate:orderenddate,
        		orderAuthority:orderAuthority
        	},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		$('#pageNumber').val(1);
        		_self.visaJapanData = data.visaJapanData;
        		if(data.visaJapanData.length != 0){
            		_self.visaJapanDataS = data.entity;
        		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				/* disableorder:0,
            				disablepeople:0, */
            				zhaobaoorder:0,
            				zhaobaopeople:0,
            				singleperson:0,
            				multiplayer:0
            			}
            		}
          	}
        });
	}
	function changestatus(){
		search();
	}
	//回车事件
	function onkeyEnter(){
	    var e = window.event || arguments.callee.caller.arguments[0];
	    if(e && e.keyCode == 13){
	    	search();
		 }
	}
	function loadListData(){
		var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$.ajax({
        	url: url,
        	data:{orderAuthority:orderAuthority},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		console.log(data.visaJapanData);
        		_self.visaJapanData = data.visaJapanData;
        		if(data.visaJapanData.length != 0){
            		_self.visaJapanDataS = data.entity;
        		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				/* disableorder:0,
            				disablepeople:0, */
            				zhaobaoorder:0,
            				zhaobaopeople:0,
            				singleperson:0,
            				multiplayer:0
            			}
            		}
          	}
        });
	}

	function successCallBack(status){
		search();




		/* var orderAuthority = "allOrder";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$.ajax({
        	url: url,
        	data:{orderAuthority:orderAuthority},
        	dataType:"json",
        	type:'post',
        	success: function(data){
        		_self.visaJapanData = data.visaJapanData;
        		if(data.visaJapanData.length != 0){
            		_self.visaJapanDataS = data.visaJapanData[0];
        		}else{
            			_self.visaJapanDataS = {
            				orderscount:0,
            				peopletotal:0,
            				disableorder:0,
            				disablepeople:0,
            				zhaobaoorder:0,
            				zhaobaopeople:0
            			}
            		}
          	}
        }); */
		/* if(status == 1){
			//layer.msg('发招宝');
			parent.successCallBack(4);
		}else if(status == 12){

		}else{
			//layer.msg('保存成功');
			parent.successCallBack(2);
		} */
	}

	$(function(){
		//送签时间
		/* $("#sendSignDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-left",//显示位置
			minView: "month"
		});
		//出签时间
		$("#signOutDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
			autoclose: true,//选中日期后 自动关闭
			pickerPosition:"bottom-left",//显示位置
			minView: "month"

		}); */

		/* //送签时间
		$("#sendSignDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
	        weekStart: 1,
	        todayBtn: 1,
			autoclose: true,
			todayHighlight: true,//高亮
			startView: 2,//从天开始选择
			forceParse: 0,
	        showMeridian: false,
			pickerPosition:"bottom",//显示位置
			minView: "month"//只显示年月日
		}).on("changeDate",function(){
			search();
		});

		//下单时间
		$("#orderDate").datetimepicker({
			format: 'yyyy-mm-dd',
			language: 'zh-CN',
	        weekStart: 1,
	        todayBtn: 1,
			autoclose: true,
			todayHighlight: true,//高亮
			startView: 2,//从天开始选择
			forceParse: 0,
	        showMeridian: false,
			pickerPosition:"bottom",//显示位置
			minView: "month"//只显示年月日
		}).on("changeDate",function(){
			search();
		}); */

		//时间插件处理
		$('#sendSignDate').daterangepicker(null, function(start, end, label) {
		  	console.log(start.toISOString(), end.toISOString(), label);
		  	console.log(start.format('YYYY-MM-DD HH:mm:ss'));
		  	$("#sendstartdate").val(start.format('YYYY-MM-DD HH:mm:ss'));
		  	$("#sendenddate").val(end.format('YYYY-MM-DD HH:mm:ss'));
		  	search();
		});
		$('#sendSignDate').on('cancel.daterangepicker', function(ev, picker) {
			$('#sendSignDate').val('');
			$("#sendstartdate").val('');
		  	$("#sendenddate").val('');
		  	search();
		});
		$('#sendSignDate').on('apply.daterangepicker', function(ev, picker) {
			$("#sendstartdate").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
		  	$("#sendenddate").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
		  	search();
		});
		$('#orderDate').daterangepicker(null, function(start, end, label) {
		  	console.log(start.toISOString(), end.toISOString(), label);
		  	$("#orderstartdate").val(start.format('YYYY-MM-DD HH:mm:ss'));
		  	$("#orderenddate").val(end.format('YYYY-MM-DD HH:mm:ss'));
		  	search();
		});
		$('#orderDate').on('cancel.daterangepicker', function(ev, picker) {
			$('#orderDate').val('');
			$("#orderstartdate").val('');
		  	$("#orderenddate").val('');
		  	search();
		});
		$('#orderDate').on('apply.daterangepicker', function(ev, picker) {
			$("#orderstartdate").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
		  	$("#orderenddate").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
		  	search();
		});


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

	function addOrder(){
		window.location.href = "/admin/simple/addOrder.html";
	}

	//连接websocket
	/* connectWebSocket();
	function connectWebSocket(){
		 if ('WebSocket' in window){
            console.log('Websocket supported');
            socket = new WebSocket('ws://${obj.localAddr}:${obj.localPort}/${obj.websocketaddr}');

            console.log('Connection attempted');

            socket.onopen = function(){
                 console.log('Connection open!');
                 //setConnected(true);
             };

            socket.onclose = function(){
                console.log('Disconnecting connection');
            };

            socket.onmessage = function (evt){
                console.log('message received!');
                search();
             };

          } else {
            console.log('Websocket not supported');
          }
	} */

	</script>
</body>
</html>
