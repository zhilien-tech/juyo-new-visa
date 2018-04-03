//异步加载的URL地址
var url="/admin/orderUS/listDetailUSData.html";
//vue表格数据对象
var _self;
var orderUSListData = {orderUSListData:""};
new Vue({
	el: '#card',
	data: orderUSListData,
	created:function(){
		_self=this;
		var status = $('#status').val();
		var searchStr = $('#searchStr').val();
		var cityid = $('#cityid').val();
		var ispayed = $('#ispayed').val()
		
		var orderAuthority = "";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
		$.ajax({ 
			url: url,
			data:{
				status:status,
				cityid:cityid,
				ispayed:ispayed,
				searchStr:searchStr,
				orderAuthority:orderAuthority
			},
			dataType:"json",
			type:'post',
			success: function(data){
				_self.orderUSListData = data.orderUSListData;
				console.log(_self.orderUSListData);
				$('#pageTotal').val(data.pageTotal);
				$('#pageListCount').val(data.pageListCount);
			}
		});
	},
	methods:{
		/* editClick:function(){//编辑图标  页面跳转
					window.location.href = '${base}/admin/firstTrialJp/edit.html';
				} */
		toDetail:function(orderid){
			//跳转到签证详情页面
			window.open('/admin/orderUS/listDetailUS.html?orderid='+orderid);
			//console.log(message);
			//alert(JSON.stringify(event.target));
		}
	}
});
$("#searchBtn").on('click', function () {
	var status = $('#status').val();
	var searchStr = $('#searchStr').val();
	var cityid = $('#cityid').val();
	var ispayed = $('#ispayed').val()
	var orderAuthority = "";
	$(".searchOrderBtn").each(function(){
		if($(this).hasClass("bgColor")){
			orderAuthority = $(this).attr("name");
		}
	});
	
	$.ajax({ 
		url: url,
		data:{
			status:status,
			cityid:cityid,
			ispayed:ispayed,
			searchStr:searchStr,
			orderAuthority:orderAuthority
		},
		dataType:"json",
		type:'post',
		success: function(data){
			_self.orderUSListData = data.orderUSListData;
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
	if(scrollHeight - (scrollTop + windowHeight)  == 0){
		//分页条件
		var pageNumber = $('#pageNumber').val();
		//搜索条件
		var status = $('#status').val();
		var searchStr = $('#searchStr').val();
		var cityid = $('#cityid').val();
		var ispayed = $('#ispayed').val();
		var orderAuthority = "";
		$(".searchOrderBtn").each(function(){
			if($(this).hasClass("bgColor")){
				orderAuthority = $(this).attr("name");
			}
		});
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
					cityid:cityid,
					ispayed:ispayed,
					searchStr:searchStr,
					pageNumber:pageNumber,
					orderAuthority:orderAuthority
				},
				dataType:"json",
				type:'post',
				success: function(data){
					//关闭遮罩
					layer.closeAll('loading');
					$.each(data.orderUSListData,function(index,item){
						_self.orderUSListData.push(item);
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
	var orderAuthority = "";
	$(".searchOrderBtn").each(function(){
		if($(this).hasClass("bgColor")){
			orderAuthority = $(this).attr("name");
		}
	});
	var status = $('#status').val();
	var searchStr = $('#searchStr').val();
	var cityid = $('#cityid').val();
	var ispayed = $('#ispayed').val();
	
	$.ajax({ 
		url: url,
		data:{
			status:status,
			cityid:cityid,
			ispayed:ispayed,
			searchStr:searchStr,
			orderAuthority:orderAuthority
		},
		dataType:"json",
		type:'post',
		success: function(data){
			_self.orderUSListData = data.orderUSListData;
		}
	});
}

function successCallBack(status){
	if(status == 1){
		layer.msg('修改成功<br>订单进入"我的"标签页');
	}else if(status == 2){
		layer.msg('发送成功<br>订单进入"我的"标签页');
	}
	reloadData();
}

function cancelCallBack(status){

}