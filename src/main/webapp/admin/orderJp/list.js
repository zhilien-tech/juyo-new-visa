//vue表格数据对象
var _self;
new Vue({
	el : '.content',
	data : {
		orderJpData : ""
	},
	created : function() {
		_self = this;
		$.ajax({
			url : url,
			dataType : "json",
			type : 'post',
			success : function(data) {
				if(data.orderJp == null || data.orderJp == "" || data.orderJp == undefined){
					$(".orderJp").removeClass("none");
				}else{
					_self.orderJpData = data.orderJp;
					$('#pageTotal').val(data.pageTotal);
					$('#pageListCount').val(data.pageListCount);
					console.log(JSON.stringify(data.orderJp));
				}
			}
		});
	},
	methods : {
		order : function(id) {
			window.open('/admin/orderJp/order.html'
					+ (id > 0 ? ('?id=' + id) : ''));//跳转到更新页面
			//window.location.href = '${base}/admin/orderJp/order.html?id='+id;
		},
		share : function(id) {//分享
			layer.open({
				type : 2,
				title : false,
				closeBtn : false,
				fix : false,
				maxmin : false,
				shadeClose : false,
				scrollbar : false,
				area : [ '900px', '551px' ],
				content : '/admin/orderJp/share.html?id=' + id
			});
		},
		theTrial : function(id) {
			layer.load(1);
			$.ajax({
				url : '/admin/orderJp/firtTrialJp',
				dataType : "json",
				data : {
					orderId : id
				},
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					layer.msg("进入初审", {
						time: 1000,
						end: function () {
							successCallBack();
						}
					});
				}
			});
		},
		disabled : function(orderid, status) {
			layer.load(1);
			$.ajax({
				url : '/admin/orderJp/disabled',
				dataType : "json",
				data : {
					orderId : orderid
				},
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					layer.msg("操作成功", {
						time: 1000,
						end: function () {
							successCallBack();
						}
					});
				}
			});
		},
		undisabled : function(orderid){
			layer.load(1);
			$.ajax({
				url : '/admin/orderJp/undisabled',
				dataType : "json",
				data : {
					orderId : orderid,
					status : $("#orderPreStatus").val()
				},
				type : 'post',
				success : function(data) {
					layer.closeAll("loading");
					layer.msg("操作成功", {
						time: 1000,
						end: function () {
							successCallBack();
						}
					});
				}
			});
		}

	}
});



function countryChange() {
	$("#searchbtn").click();
	$('#pageNumber').val(1);
}

//搜索回车事件
function onkeyEnter() {
	var e = window.event || arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) {
		$("#searchbtn").click();
	}
}

//注册scroll事件并监听 
$(window).scroll(function(){
	var scrollTop = $(this).scrollTop();
	var scrollHeight = $(document).height();
	var windowHeight = $(this).height();
	var pageTotal = parseInt($('#pageTotal').val());
	var pageListCount = parseInt($('#pageListCount').val());
	// 判断是否滚动到底部  
	if(Math.ceil(scrollTop + windowHeight) == scrollHeight){
		//分页条件
		var pageNumber = $('#pageNumber').val();
		pageNumber = parseInt(pageNumber) + 1;
		$('#pageNumber').val(pageNumber);
		//搜索条件
		var searchStr = $('#searchStr').val();
		var status = $('#status').val();
		var source = $('#source').val();
		var visaType = $('#visaType').val();
		var sendSignDateStart = sendDateStart;
		var sendSignDateEnd = sendDateEnd;
		var signOutDateStart = outDateStart;
		var signOutDateEnd = outDateEnd;
		var startTimeStart = timeStart;
		var startTimeEnd = timeEnd;
		//异步加载数据
		if(pageNumber <= pageTotal){
			//遮罩
			layer.load(1);
			$.ajax({ 
				url: url,
				data:{
					status : status,
					source : source,
					startTimeStart : startTimeStart,
					startTimeEnd : startTimeEnd,
					visaType : visaType,
					sendSignDateStart : sendDateStart,
					sendSignDateEnd : sendDateEnd,
					signOutDateStart : outDateStart,
					signOutDateEnd : outDateEnd,
					searchStr : searchStr,
					pageNumber:pageNumber
				},
				dataType:"json",
				type:'post',
				success: function(data){
					//关闭遮罩
					layer.closeAll('loading');
					$.each(data.orderJp,function(index,item){
						_self.orderJpData.push(item);
					});
					//没有更多数据
				}
			});
		}/* else{
			//没有更多数据，底部提示语
			if($("#card-bottom-line").length <= 0 && pageListCount>=6){
				$(".card-list").last().after("<div id='card-bottom-line' class='bottom-line'><span style='margin-left: 38%; color:#999'>-------  没有更多数据可以加载  -------</span></div>");
			}
		} */
	}
});

//跳转添加页
function addOrder() {
	window.location.href = '/admin/orderJp/addOrder';
}

function successCallBack(status){
	$("#searchbtn").click();
}