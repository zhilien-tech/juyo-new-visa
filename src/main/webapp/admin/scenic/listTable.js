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
			"url": BASE_PATH + "/admin/scenic/listData.html",
			"type": "post",
			/* "data": function (d) {

            } */
		},
		/* 列表序号 */
//		"fnDrawCallback"    : function(){
//			var api = this.api();
//			var startIndex= api.context[0]._iDisplayStart;
//			api.column(0).nodes().each(function(cell, i) {
//				cell.innerHTML = startIndex + i + 1;
//			});
//		},

		"columns": [
		            {"data": "name", "bSortable": false,render: function(data, type, row, meta) {
		            	var name = row.name;
		            	if(null==name || ""==name){
		            		return "";
		            	}else{
		            		/*name = '<span data-toggle="tooltip" data-placement="right" title="'+name+'">'+name+'<span>';*/
		            		return name;
		            	}
		            } 	
		            },
		            {"data": "namejp", "bSortable": false,render: function(data, type, row, meta) {
		            	var namejp = row.namejp;
		            	if(null==namejp || ""==namejp){
		            		return "";
		            	}else{
		            		/*namejp = '<span data-toggle="tooltip" data-placement="right" title="'+namejp+'">'+namejp+'<span>';*/
		            		return namejp;
		            	}
		            } 	
		            },
		            {"data": "cityid", "bSortable": false,render: function(data, type, row, meta) {
		            	var cityid = row.cityid;
		            	if(null==cityid || ""==cityid){
		            		return "";
		            	}else{
		            		/*cityId = '<span data-toggle="tooltip" data-placement="right" title="'+cityId+'">'+cityId+'<span>';*/
		            		return cityid;
		            	}
		            } 	
		            },
		            {"data": " ", "bSortable": false, "width":120,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
		            		var judge = '<a style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
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
		area: ['900px', '380px'],
		content: BASE_PATH + '/admin/scenic/add.html'
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
		area: ['900px', '380px'],
		content: BASE_PATH + '/admin/scenic/update.html?id='+id
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
		var url = BASE_PATH + '/admin/scenic/delete.html';
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