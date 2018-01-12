//异步加载的URL地址
var url="/admin/firstTrialJp/trialListData.html";
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
				$('#pageTotal').val(data.pageTotal);
				$('#pageListCount').val(data.pageListCount);
			}
		});
	},
	methods:{
		/* editClick:function(){//编辑图标  页面跳转
					window.location.href = '${base}/admin/firstTrialJp/edit.html';
				} */
		visaDetail:function(orderid,orderjpid){
			//跳转到签证详情页面
			window.open('/admin/firstTrialJp/trialDetail.html?orderid='+orderid+'&orderjpid='+orderjpid);
			//console.log(message);
			//alert(JSON.stringify(event.target));
		},
		basicInfoFun:function(applyid,orderid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content: '/admin/orderJp/updateApplicant.html?id='+applyid+'&orderid='+orderid+'&isTrial=1'
			});
		},
		expressFun:function(orderid,orderjpid){//跳转快递弹层页面
			$.ajax({
				type : 'POST',
				data : {
					orderjpid:orderjpid
				},
				url : '/admin/firstTrialJp/getCareerStatus.html',
				success : function(data) {
					var isEmpty = data.isEmpty;
					if(isEmpty == false){
						/*layer.msg('申请人：'+data.names+' 职业未选择');*/
						var applyids = data.applyids;
						$.each(applyids, function(i, applyid) { 
							layer.open({
								type: 2,
								title: false,
								closeBtn:false,
								fix: false,
								maxmin: false,
								shadeClose: false,
								scrollbar: false,
								area: ['900px', '80%'],
								content:'/admin/firstTrialJp/validApplicantInfo.html?applicantId='+applyid+'&orderid='+orderid,
								success : function(index, layero){
									var iframeWin = window[index.find('iframe')[0]['name']]; 
								}
							}); 
						}); 
						
						return;
					}else{
						$.ajax({
							type : 'POST',
							data : {
								orderjpid:orderjpid
							},
							url : '/admin/firstTrialJp/isQualified.html',
							success : function(data) {
								if(data){
									layer.open({
										type: 2,
										title: false,
										closeBtn:false,
										fix: false,
										maxmin: false,
										shadeClose: false,
										scrollbar: false,
										area: ['900px', '80%'],
										content: '/admin/firstTrialJp/express.html?orderid='+orderid+'&orderjpid='+orderjpid
									});
								}else{
									layer.msg('申请人不合格');
									return;
								}
							}
						});
					}
				}
			});
		},
		passportFun:function(applyid,orderid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/passportInfo.html?applicantId='+applyid+'&orderid='+orderid+'&isTrial=1'
			});
		},
		visaInfoFun:function(applyid,orderid){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '80%'],
				content:'/admin/orderJp/visaInfo.html?id='+applyid+'&orderid='+orderid+'&isOrderUpTime=1&isTrial=1'
			});
		},
		qualifiedFun:function(applyid,orderid,orderjpid){
			//判断申请人是否合格
			$.ajax({
				type : 'POST',
				data : {
					applicantId:applyid
				},
				url : '/admin/firstTrialJp/isQualifiedByApplicantId.html',
				success : function(data) {
					//遮罩
					layer.load(1);
					var isQualified = data.isQualified;
					var applicantName = data.name;
					if(isQualified){
						layer.confirm('您是确定要合格吗？', {
							btn: ['是','否'] //按钮
						}, function(){
							$.ajax({
								type : 'POST',
								data : {
									applyid:applyid,
									orderid:orderid,
									orderjpid:orderjpid
								},
								url : '/admin/firstTrialJp/qualified.html',
								success : function(data) {
									layer.closeAll('loading');
									qualifiedCallBack(data);
								},
								error : function(xhr) {
									layer.closeAll('loading');
									layer.msg("操作失败");
								}
							});
						}, function(){
							//否 取消
							layer.closeAll('loading');
						});
						
					}else{
						layer.closeAll('loading');
						layer.msg(applicantName+" 信息不合格");
					}
				},
				error : function(xhr) {
					layer.msg("操作失败");
				}
			});
			

		},
		unqualifiedFun:function(applyid,orderid){
			//遮罩
			layer.load(1);
			/*layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['800px', '402px'],
				content: '/admin/firstTrialJp/unqualified.html?applyid='+applyid+'&orderid='+orderid
			});*/
			$.ajax({
				type : 'POST',
				data : {
					applicantId:applyid,
					orderId:orderid
				},
				url : '/admin/firstTrialJp/sendUnqualifiedMsg.html',
				success : function(data) {
					//关闭遮罩
					layer.closeAll('loading');
					unqualifiedCallBack(data);
				},
				error : function(xhr) {
					//关闭遮罩
					layer.closeAll('loading');
					layer.msg("操作失败");
				}
			});
		}
	}
});

$("#searchBtn").on('click', function () {
	var status = $('#status').val();
	var searchStr = $('#searchStr').val();
	
	var orderAuthority = "allOrder";
	$(".searchOrderBtn").each(function(){
		if($(this).hasClass("bgColor")){
			orderAuthority = $(this).attr("name");
		}
	});
	console.log("orderAuthority------》"+orderAuthority);
	
	$.ajax({ 
		url: url,
		data:{
			status:status,
			searchStr:searchStr,
			orderAuthority:orderAuthority
		},
		dataType:"json",
		type:'post',
		success: function(data){
			_self.trialJapanData = data.trialJapanData;
		}
	});
});

//注册scroll事件并监听 
$(window).scroll(function(){
	var scrollTop = $(this).scrollTop();
	var scrollHeight = $(document).height();
	var windowHeight = $(this).height();
	var pageTotal = parseInt($('#pageTotal').val());
	var pageListCount = parseInt($('#pageListCount').val());
	// 判断是否滚动到底部  
	if(scrollHeight - (scrollTop + windowHeight)  <= 1.75){
		//分页条件
		var pageNumber = $('#pageNumber').val();
		//搜索条件
		var status = $('#status').val();
		var searchStr = $('#searchStr').val();
		//异步加载数据
		if(pageNumber <= pageTotal){
			pageNumber = parseInt(pageNumber) + 1;
			$('#pageNumber').val(pageNumber);
			//遮罩
			layer.load(1);
			$.ajax({ 
				url: url,
				data:{
					status:status,
					searchStr:searchStr,
					pageNumber:pageNumber
				},
				dataType:"json",
				type:'post',
				success: function(data){
					//关闭遮罩
					layer.closeAll('loading');
					$.each(data.trialJapanData,function(index,item){
						_self.trialJapanData.push(item);
					});
					//没有更多数据
				}
			});
		}else{
			//没有更多数据，底部提示语
			if($("#card-bottom-line").length <= 0 && pageListCount>=6){
				$(".card-list").last().after("<div id='card-bottom-line' class='bottom-line'><span style='margin-left: 38%; color:#999'>-------  没有更多数据可以加载  -------</span></div>");
			}
		}
	}
});

/*回车事件*/
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		$("#searchBtn").click();
	}
}

/*状态改变事件*/
function selectListData(){
	$("#searchBtn").click();
}

//加载列表数据
function reloadData(){
	$.ajax({ 
		url: url,
		/* data:{status:status,searchStr:searchStr}, */
		dataType:"json",
		type:'post',
		success: function(data){
			_self.trialJapanData = data.trialJapanData;
		}
	});
}

function successCallBack(status){
	if(status == 1){
		layer.msg('修改成功');
	}else if(status == 2){
		layer.msg('发送成功');
	}
	reloadData();
}


function qualifiedCallBack(username){
	layer.msg('合格 已短信邮件通知 '+username);
	reloadData();
}

function unqualifiedCallBack(username){
	layer.msg('不合格 已短信邮件通知 '+username);
	reloadData();
}

function cancelCallBack(status){

}
