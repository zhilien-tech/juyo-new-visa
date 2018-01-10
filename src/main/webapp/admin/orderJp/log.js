function savePrincipal(){
	var orderProcessType = $("#orderProcessType").val();
	var principal = $("#principal").val();
	if(principal == "" || principal==null || principal==undefined){
		layer.msg("负责人不能为空");
		return;
	}
	
	var layerIndex =  layer.load(1, {shade: "#000"});
	$.ajax({ 
     	url: '${base}/admin/orderJp/changePrincipal.html',
     	data:{
     		orderid:orderid,
     		principal:principal,
     		orderProcessType:orderProcessType
     	},
     	dataType:"json",
     	type:'post',
     	success: function(data){
     		if(data.stauts == 200){
				layer.close(layerIndex);
			}
			closeWindow();
			parent.layer.msg("负责人变更成功");
       	}
     });
}