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
			"url": BASE_PATH + "/admin/function/listData.html",
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
		            {"data": "parentname", "bSortable": false,render: function(data, type, row, meta) {
		            	var parentname = row.parentname;
		            	if(null==parentname || ""==parentname){
		            		return "";
		            	}else{
		            		/*parentname = '<span data-toggle="tooltip" data-placement="right" title="'+parentname+'">'+parentname+'<span>';*/
		            		return parentname;
		            	}
		            } 	
		            },
		            {"data": "url", "bSortable": false,render: function(data, type, row, meta) {
		            	var url = row.url;
		            	if(null==url || ""==url){
		            		return "";
		            	}else{
		            		/*url = '<span data-toggle="tooltip" data-placement="right" title="'+url+'">'+url+'<span>';*/
		            		return url;
		            	}
		            } 	
		            },
		            {"data": "funname", "bSortable": false,render: function(data, type, row, meta) {
		            	var funname = row.funname;
		            	if(null==funname || ""==funname){
		            		return "";
		            	}else{
		            		/*funname = '<span data-toggle="tooltip" data-placement="right" title="'+funname+'">'+funname+'<span>';*/
		            		return funname;
		            	}
		            } 	
		            },
		            {"data": "level", "bSortable": false,render: function(data, type, row, meta) {
		            	var level = row.level;
		            	if(null==level || ""==level){
		            		return "";
		            	}else{
		            		/*level = '<span data-toggle="tooltip" data-placement="right" title="'+level+'">'+level+'<span>';*/
		            		return level;
		            	}
		            } 	
		            },
		            {"data": "comtype", "bSortable": false,render: function(data, type, row, meta) {
		            	var comtype = row.comtype;
		            	if(null==comtype || ""==comtype){
		            		return "";
		            	}else{
		            		if("1" == comtype){
		            			comtype = "送签社";
		            		}else if("2" == comtype){
		            			comtype = "地接社";
		            		}
		            		return comtype;
		            	}
		            } 	
		            },
		            {"data": "bscope", "bSortable": false,render: function(data, type, row, meta) {
		            	var bscope = row.bscope;
		            	if(null==bscope || ""==bscope){
		            		return "";
		            	}else{
		            		if("1"==bscope){
		            			bscope = "日本";
		            		}
		            		return bscope;
		            	}
		            } 	
		            },
		            {"data": "remark", "bSortable": false,render: function(data, type, row, meta) {
		            	var remark = row.remark;
		            	if(null==remark || ""==remark){
		            		return "";
		            	}else{
		            		/*remark = '<span data-toggle="tooltip" data-placement="right" title="'+remark+'">'+remark+'<span>';*/
		            		return remark;
		            	}
		            } 	
		            },
		            {"data": "sort", "bSortable": false,render: function(data, type, row, meta) {
		            	var sort = row.sort;
		            	if(null==sort || ""==sort){
		            		return "";
		            	}else{
		            		/*sort = '<span data-toggle="tooltip" data-placement="right" title="'+sort+'">'+sort+'<span>';*/
		            		return sort;
		            	}
		            } 	
		            },
		            {"data": " ", "bSortable": false, "width":120,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a title="编辑" style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
		            		/*var judge = '<a style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
		            		return modify+delete;*/
		            		return modify;
		            	}	
		            } 
		            ],
		            columnDefs: [{}]
	});
}

//搜索
$("#searchBtn").on('click', function () {
	var funName = $("#searchStr").val();
	var funId = $("#funId").val();
	var param = {
        "searchStr": funName,
		"funId":funId
    };
    datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
});

//搜索回车事件
function onkeyEnter(){
	 if(event.keyCode==13){
		 $("#searchBtn").click();
	 }
}
//筛选条件自动切换
function selectFunName(){
	$("#searchBtn").click();
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
		content: BASE_PATH + '/admin/function/add.html'
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
		content: BASE_PATH + '/admin/function/update.html?id='+id
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
		var url = BASE_PATH + '/admin/function/delete.html';
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