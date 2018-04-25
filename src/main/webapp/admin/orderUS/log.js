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
			url: '/admin/orderUS/getLogs.html',
			data:{
				orderid:orderid
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
	var principal = $("#principal").val();
	if(principal == "" || principal==null || principal==undefined){
		layer.msg("负责人不能为空");
		return;
	}
	var layerIndex =  layer.load(1, {shade: "#000"});
	$.ajax({ 
		url: '/admin/orderUS/changePrincipal.html',
		data:{
			orderid:orderid,
			principal:principal,
		},
		dataType:"json",
		type:'post',
		success: function(data){
			dataReload();
			closeWindow();
		}
	});
}

//返回 
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}