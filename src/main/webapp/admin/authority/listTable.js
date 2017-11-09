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
			"url": BASE_PATH + "/admin/authority/listData.html",
			"type": "post",
			/* "data": function (d) {

            } */
		},
		/* 列表序号 */
		/*"fnDrawCallback"    : function(){
			var api = this.api();
			var startIndex= api.context[0]._iDisplayStart;
			api.column(0).nodes().each(function(cell, i) {
				cell.innerHTML = startIndex + i + 1;
			});
		},*/

		"columns": [
		            /*{"data": "", "bSortable": false,render: function(data, type, row, meta) {

		            	return "";
		            } 	
		            },*/
		            {"data": "deptname", "bSortable": false,render: function(data, type, row, meta) {
		            	var deptname = row.deptname;
		            	if(null==deptname || ""==deptname){
		            		return "";
		            	}else{
		            		/*deptname = '<span data-toggle="tooltip" data-placement="right" title="'+deptname+'">'+deptname+'<span>';*/
		            		return deptname;
		            	}
		            } 	
		            },
		            {"data": "modulename", "bSortable": false,render: function(data, type, row, meta) {
		            	var modulename = row.modulename;
		            	if(null==modulename || ""==modulename){
		            		return "";
		            	}else{
		            		//return modulename;
		            		if(modulename.indexOf(",")>=0){
		            			var arr = modulename.split(',');
		            			var module = "";
		            			for(var i=0;i<arr.length;i++){
				            		module += '<span class="limitTwo">'+ arr[i] +',</span>';
				            	}
				            	return module;
		            		}else{
		            			return modulename;
		            		}
		            	}
		            } 	
		            },
		            {"data": "jobname", "bSortable": false,render: function(data, type, row, meta) {
		            	var jobname = row.jobname;
		            	if(null==jobname || ""==jobname){
		            		return "";
		            	}else{
		            		//return jobname;
		            		
		            		if(jobname.indexOf(",")>=0){
		            			var arr = jobname.split(',');
		            			var job = "";
		            			for(var i=0;i<arr.length;i++){
		            				job += '<span class="limitTwo">'+ arr[i] +',</span>';
				            	}
				            	return job;
		            		}else{
		            			return jobname;
		            		}
		            		
		            	}
		            } 	
		            },
		            {"data": " ", "bSortable": false, "width":120,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a title="编辑" style="cursor:pointer;" class="edit-icon" onclick="edit('+row.deptid+');"></a>';
		            		/*var judge = '<a style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.deptId+');"></a>';
		            		return modify+judge;*/
		            		return modify;
		            	}	
		            } 
		            ],
		            columnDefs: [{}]
	});
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
		area: ['900px', '550px'],
		content: BASE_PATH + '/admin/authority/add.html'
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
		area: ['900px', '550px'],
		content: BASE_PATH + '/admin/authority/update.html?id='+id
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
		var url = BASE_PATH + '/admin/authority/delete.html';
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