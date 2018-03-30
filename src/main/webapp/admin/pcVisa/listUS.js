//vue表格数据对象
var _self;
new Vue({
	el : '#card',
	data : {
		orderUSData : ""
	},
	created : function() {
		_self = this;
		$.ajax({
			url : url,
			dataType : "json",
			type : 'post',
			success : function(data) {
				_self.orderUSData = data.visaListData;
				//$('#pageTotal').val(data.pageTotal);
				//$('#pageListCount').val(data.pageListCount);
				console.log(JSON.stringify(data.visaListData));
			}
		});
	},
	methods : {
		order : function(ordernum, staffid, telephone, email) {
			window.open('/admin/pcVisa/listDetailUS.html?ordernum='+ordernum + '&staffid='+staffid + '&telephone='+telephone + '&email='+email);
					//跳转到更新页面
			//window.location.href = '${base}/admin/orderJp/order.html?id='+id;
		}
	}
});