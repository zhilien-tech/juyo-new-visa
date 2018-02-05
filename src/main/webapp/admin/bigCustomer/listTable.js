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
			"url": BASE_PATH + "/admin/bigcustomer/listData.html",
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
		            {"data": "firstName", "bSortable": false,render: function(data, type, row, meta) {
		            	var firstName = row.firstName;
		            	if(null==firstName || ""==firstName){
		            		return "";
		            	}else{
		            		/*firstName = '<span data-toggle="tooltip" data-placement="right" title="'+firstName+'">'+firstName+'<span>';*/
		            		return firstName;
		            	}
		            } 	
		            },
		            {"data": "firstNameEn", "bSortable": false,render: function(data, type, row, meta) {
		            	var firstNameEn = row.firstNameEn;
		            	if(null==firstNameEn || ""==firstNameEn){
		            		return "";
		            	}else{
		            		/*firstNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+firstNameEn+'">'+firstNameEn+'<span>';*/
		            		return firstNameEn;
		            	}
		            } 	
		            },
		            {"data": "lastName", "bSortable": false,render: function(data, type, row, meta) {
		            	var lastName = row.lastName;
		            	if(null==lastName || ""==lastName){
		            		return "";
		            	}else{
		            		/*lastName = '<span data-toggle="tooltip" data-placement="right" title="'+lastName+'">'+lastName+'<span>';*/
		            		return lastName;
		            	}
		            } 	
		            },
		            {"data": "lastNameEn", "bSortable": false,render: function(data, type, row, meta) {
		            	var lastNameEn = row.lastNameEn;
		            	if(null==lastNameEn || ""==lastNameEn){
		            		return "";
		            	}else{
		            		/*lastNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+lastNameEn+'">'+lastNameEn+'<span>';*/
		            		return lastNameEn;
		            	}
		            } 	
		            },
		            {"data": "telephone", "bSortable": false,render: function(data, type, row, meta) {
		            	var telephone = row.telephone;
		            	if(null==telephone || ""==telephone){
		            		return "";
		            	}else{
		            		/*telephone = '<span data-toggle="tooltip" data-placement="right" title="'+telephone+'">'+telephone+'<span>';*/
		            		return telephone;
		            	}
		            } 	
		            },
		            {"data": "email", "bSortable": false,render: function(data, type, row, meta) {
		            	var email = row.email;
		            	if(null==email || ""==email){
		            		return "";
		            	}else{
		            		/*email = '<span data-toggle="tooltip" data-placement="right" title="'+email+'">'+email+'<span>';*/
		            		return email;
		            	}
		            } 	
		            },
		            {"data": "department", "bSortable": false,render: function(data, type, row, meta) {
		            	var department = row.department;
		            	if(null==department || ""==department){
		            		return "";
		            	}else{
		            		/*department = '<span data-toggle="tooltip" data-placement="right" title="'+department+'">'+department+'<span>';*/
		            		return department;
		            	}
		            } 	
		            },
		            {"data": "job", "bSortable": false,render: function(data, type, row, meta) {
		            	var job = row.job;
		            	if(null==job || ""==job){
		            		return "";
		            	}else{
		            		/*job = '<span data-toggle="tooltip" data-placement="right" title="'+job+'">'+job+'<span>';*/
		            		return job;
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
		content: BASE_PATH + '/admin/bigcustomer/add.html'
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
		content: BASE_PATH + '/admin/bigcustomer/update.html?id='+id
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
		var url = BASE_PATH + '/admin/bigcustomer/delete.html';
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