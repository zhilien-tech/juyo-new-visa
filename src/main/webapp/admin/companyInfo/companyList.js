//异步加载的URL地址
var url=BASE_PATH + "/admin/companyInfo/companyInfoListData.html";
//vue表格数据对象
var _self;
new Vue({
	el: '#cardList',
	data: {companyInfoData:""},
	created:function(){
		_self=this;
		$.ajax({ 
			url: url,
			dataType:"json",
			type:'post',
			success: function(data){
				_self.companyInfoData = data.companyInfoData;
				$('#pageTotal').val(data.pageTotal);
				$('#pageListCount').val(data.pageListCount);
			}
		});
	},
	methods:{
		updateComInfo:function(comInfoId){
			layer.open({
				type: 2,
				title: false,
				closeBtn:false,
				fix: false,
				maxmin: false,
				shadeClose: false,
				scrollbar: false,
				area: ['900px', '550px'],
				content: BASE_PATH + '/admin/companyInfo/edit.html?id='+comInfoId
			});
		}
	}
});

function updatePassword(){
	 layer.open({
   	    type: 2,
   	    title: false,
   	    closeBtn:false,
   	    fix: false,
   	    maxmin: false,
   	    shadeClose: false,
   	    scrollbar: false,
   	    area: ['900px', '550px'],
   	    content: BASE_PATH + '/admin/personalInfo/updatePassword.html'
   	  });
}

function addCompanyOfSqs(){
	layer.open({
	    type: 2,
	    title: false,
	    closeBtn:false,
	    fix: false,
	    maxmin: false,
	    shadeClose: false,
	    scrollbar: false,
	    area: ['900px', '550px'],
	    content: BASE_PATH + '/admin/companyInfo/add.html'
	});
}

//注册scroll事件并监听 
$(window).scroll(function(){
	
	var scrollTop = $(this).scrollTop();
	var windowHeight = $(this).height();
	var scrollHeight = $(document).height();
	var pageTotal = parseInt($('#pageTotal').val());
	var pageListCount = parseInt($('#pageListCount').val());
	// 判断是否滚动到底部  
	if(scrollHeight - (scrollTop + windowHeight)  <= 1.75){
		//分页条件
		var pageNumber = $('#pageNumber').val();
		
		//异步加载数据
		if(pageNumber <= pageTotal){
			pageNumber = parseInt(pageNumber) + 1;
			$('#pageNumber').val(pageNumber);
			//遮罩
			layer.load(1);
			$.ajax({ 
				url: url,
				data:{
					pageNumber:pageNumber
				},
				dataType:"json",
				type:'post',
				success: function(data){
					//关闭遮罩
					layer.closeAll('loading');
					$.each(data.companyInfoData,function(index,item){
						_self.companyInfoData.push(item);
					});
					//没有更多数据
				}
			});
		}else{
			//没有更多数据，底部提示语
			if($("#card-bottom-line").length <= 0 && pageListCount>=10){
				$(".card_list_line").last().after("<div id='card-bottom-line' class='bottom-line'><span style='margin-left: 38%; color:#999'>-------  没有更多数据可以加载  -------</span></div>");
			}
		}
	}
});

function successCallBack(status){
	if(status == 1){
		layer.msg('添加成功');
	}else if(status == 2){
		layer.msg('修改成功');
	}
	$.ajax({ 
		url: url,
		/* data:{status:status,searchStr:searchStr}, */
		dataType:"json",
		type:'post',
		success: function(data){
			_self.companyInfoData = data.companyInfoData;
		}
	});
}
