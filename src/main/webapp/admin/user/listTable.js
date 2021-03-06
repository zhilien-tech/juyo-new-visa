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
			"url": BASE_PATH + "/admin/user/listData.html",
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
		            {"data": "deptname", "bSortable": false,render: function(data, type, row, meta) {
		            	var deptname = row.deptname;
		            	if(null==deptname || ""==deptname){
		            		return "";
		            	}else{
		            		/*departmentId = '<span data-toggle="tooltip" data-placement="right" title="'+departmentId+'">'+departmentId+'<span>';*/
		            		return deptname;
		            	}
		            } 	
		            },
		            {"data": "jobname", "bSortable": false,render: function(data, type, row, meta) {
		            	var jobname = row.jobname;
		            	if(null==jobname || ""==jobname){
		            		return "";
		            	}else{
		            		/*jobId = '<span data-toggle="tooltip" data-placement="right" title="'+jobId+'">'+jobId+'<span>';*/
		            		return jobname;
		            	}
		            } 	
		            },
		            {"data": " ", "bSortable": false, "width":120,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
		            		/*var judge = '<a style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
		            		return modify+judge;*/
		            		return modify;
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
		area: ['900px', '60%'],
		content: BASE_PATH + '/admin/user/add.html'
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
		area: ['900px', '60%'],
		content: BASE_PATH + '/admin/user/update.html?id='+id
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
		var url = BASE_PATH + '/admin/user/delete.html';
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