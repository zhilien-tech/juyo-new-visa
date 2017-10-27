var datatable;
function initDatatable() {
	datatable = $('#datatableId').DataTable({
		"text-align":"center",
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/references/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/city/listData.html",
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
		            {"data": "country", "bSortable": false,render: function(data, type, row, meta) {
		            	var country = row.country;
		            	if(null==country || ""==country){
		            		return "";
		            	}else{
		            		/*country = '<span data-toggle="tooltip" data-placement="right" title="'+country+'">'+country+'<span>';*/
		            		return country;
		            	}
		            } 	
		            },
		            {"data": "province", "bSortable": false,render: function(data, type, row, meta) {
		            	var province = row.province;
		            	if(null==province || ""==province){
		            		return "";
		            	}else{
		            		/*province = '<span data-toggle="tooltip" data-placement="right" title="'+province+'">'+province+'<span>';*/
		            		return province;
		            	}
		            } 	
		            },
		            {"data": "city", "bSortable": false,render: function(data, type, row, meta) {
		            	var city = row.city;
		            	if(null==city || ""==city){
		            		return "";
		            	}else{
		            		/*city = '<span data-toggle="tooltip" data-placement="right" title="'+city+'">'+city+'<span>';*/
		            		return city;
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
		area: ['900px', '380px'],
		content: BASE_PATH + '/admin/city/add.html'
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
		content: BASE_PATH + '/admin/city/update.html?id='+id
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
		var url = BASE_PATH + '/admin/city/delete.html';
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
/*function countryChange(){
	  $("#searchBtn").click();
	  $("#province").change();
}
function provinceChange(){
	  $("#searchBtn").click();
	  $("#city").change();
}*/
function cityChange(){
	  $("#searchBtn").click();
}
$("#searchBtn").on('click',function(){
	var searchStr = $("#searchStr").val();
	var country = $("#country").val();
	var province = $("#province").val();
	var city = $("#city").val();
	var param = {
		"searchStr": searchStr,
		"country" : country,
		"province" : province,
		"city" : city
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
