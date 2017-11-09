var datatable;
function initDatatable() {
	datatable = $('#datatableId').DataTable({
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/references/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receiveaddress/listData.html",
			"type": "post",
			/* "data": function (d) {

            } */
		},
		/* 列表序号 */
		"fnDrawCallback"    : function(){
			var api = this.api();
			var startIndex= api.context[0]._iDisplayStart;
			api.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = startIndex + i + 1;
			});
		},

		"columns": [
		            {"data": "", "bSortable": false,render: function(data, type, row, meta) {

		            	return "";
		            } 	
		            },
		            {"data": "receiver", "bSortable": false,render: function(data, type, row, meta) {
		            	var receiver = row.receiver;
		            	if(null==receiver || ""==receiver){
		            		return "";
		            	}else{
		            		/*receiver = '<span data-toggle="tooltip" data-placement="right" title="'+receiver+'">'+receiver+'<span>';*/
		            		return receiver;
		            	}
		            } 	
		            },
		            {"data": "mobile", "bSortable": false,render: function(data, type, row, meta) {
		            	var mobile = row.mobile;
		            	if(null==mobile || ""==mobile){
		            		return "";
		            	}else{
		            		/*mobile = '<span data-toggle="tooltip" data-placement="right" title="'+mobile+'">'+mobile+'<span>';*/
		            		return mobile;
		            	}
		            } 	
		            },
		            {"data": "address", "bSortable": false,render: function(data, type, row, meta) {
		            	var address = row.address;
		            	if(null==address || ""==address){
		            		return "";
		            	}else{
		            		/*address = '<span data-toggle="tooltip" data-placement="right" title="'+address+'">'+address+'<span>';*/
		            		return address;
		            	}
		            } 	
		            },
		            {"data": " ", "bSortable": false, "width":120,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a title="编辑" style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
		            		var judge = '<a title="删除" style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
		            		return modify+judge;
		            	}	
		            } 
		            ],
		            columnDefs: [{}]
	});
}

$("#searchBtn").on('click',function(){
	var searchStr = $("#searchStr").val();
	var param = {
		"searchStr": searchStr,
	};
	datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
});

function onkeyEnter(){
    var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
		 $("#searchBtn").click();
	 }
}
/* layer添加 */
function add(){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['800px', '400px'],
		content: BASE_PATH + '/admin/receiveaddress/add.html'
	});
}
/* layer编辑 */
function edit(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['800px', '400px'],
		content: BASE_PATH + '/admin/receiveaddress/update.html?id='+id
	});
}

//删除提示
function deleteById(id) {
	layer.confirm("您确认要删除吗？", {
		title:"删除",
		btn: ["是","否"], //按钮
		shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = BASE_PATH + '/admin/receiveaddress/delete.html';
		$.ajax({
			type : 'POST',
			data : {
				id : id
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				layer.msg("删除成功",{time:2000});
				datatable.ajax.reload();
			},
			error : function(xhr) {
				layer.msg("删除失败",{time:2000});
			}
		});
	}, function(){
		// 取消之后不用处理
	});
}