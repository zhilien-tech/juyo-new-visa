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
		            {"data": "name", "bSortable": false,render: function(data, type, row, meta) {
		            	var name = row.name;
		            	if(null==name || ""==name){
		            		return "";
		            	}else{
		            		return name;
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
		            {"data": "job", "bSortable": false,
		            	render: function(data, type, row, meta) {
			            	var job = row.job;
			            	if(null==job || ""==job){
			            		return "";
			            	}else{
			            		/*job = '<span data-toggle="tooltip" data-placement="right" title="'+job+'">'+job+'<span>';*/
			            		return job;
			            	}
			            } 	
		            },
		            {"data": " ", "bSortable": false, 
		            	render: function(data, type, row, meta) {
		            		var updateApplicant = '<a style="cursor:pointer;" class="updateApplicant" onclick="edit('+row.staffid+');"></a>';
		            		var passport = '<a style="cursor:pointer;" class="passport" onclick=""></a>';
		            		var visa = '<a class="visa" onclick=""></a>';
		            		var otherVisa = '<a class="otherVisa" onclick=""></a>';
		            		var deleteIcon = '<a style="cursor:pointer;" class="deleteIcon" onclick="deleteById('+row.staffid+');"></a>';
		            		return updateApplicant+passport+visa+otherVisa+deleteIcon;
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
		area: ['900px', '80%'],
		content: BASE_PATH + '/admin/bigCustomer/add.html'
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
		area: ['900px', '80%'],
		content: BASE_PATH + '/admin/bigCustomer/update.html?staffId='+id
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
		var url = BASE_PATH + '/admin/bigCustomer/delete.html';
		$.ajax({
			type : 'POST',
			data : {
				id : id
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				layer.msg("删除成功");
				datatable.ajax.reload();
			},
			error : function(xhr) {
				layer.msg("删除失败");
			}
		});
	}, function(){
		// 取消之后不用处理
	});
}

$("#searchBtn").on('click', function () {
	var searchStr = $("#searchStr").val();
    var param = {
		"searchStr": searchStr
	};
    datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
});

function successCallback(status){
	if(status==1){
		layer.msg("添加成功");
	}else if(status==2){
		layer.msg("编辑成功");
	}else if(status==3){
		layer.msg("上传成功");
	}
	 var param = {
		"searchStr": ""
	};
    datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
}

/*回车事件*/
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		$("#searchBtn").click();
	}
}