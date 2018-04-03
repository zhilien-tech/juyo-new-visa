//vue表格数据对象
var _self;
new Vue({
	el: '#datatableId',
	data: {
		loginfo:""
	},
	created:function(){
		_self=this;
		$.ajax({ 
			url: '/admin/orderJp/getLogs.html',
			data:{
				orderid:orderid,
				orderProcessType:$("#orderProcessType").val()
			},
			dataType:"json",
			type:'post',
			success: function(data){
				//加载日志列表
				_self.loginfo = data.logs;
			}
		});
	}
});

//变更负责人
function savePrincipal(){
	var orderProcessType = $("#orderProcessType").val();
	var principal = $("#principal").val();
	if(principal == "" || principal==null || principal==undefined){
		layer.msg("负责人不能为空");
		return;
	}

	var layerIndex =  layer.load(1, {shade: "#000"});
	$.ajax({ 
		url: '/admin/orderJp/changePrincipal.html',
		data:{
			orderid:orderid,
			principal:principal,
			orderProcessType:orderProcessType
		},
		dataType:"json",
		type:'post',
		success: function(data){
			if(data>0){
				layer.close(layerIndex);
				parent.successCallBack(88);
			}
			closeWindow();
		}
	});
}

//返回 
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}