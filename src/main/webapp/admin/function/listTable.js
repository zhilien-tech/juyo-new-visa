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
                    						{"data": "parentId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var parentId = row.parentId;
		                		 if(null==parentId || ""==parentId){
		                			 return "";
		                		 }else{
		                		 	/*parentId = '<span data-toggle="tooltip" data-placement="right" title="'+parentId+'">'+parentId+'<span>';*/
		                		 	return parentId;
		                		 }
		                    } 	
		                },
											{"data": "funName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var funName = row.funName;
		                		 if(null==funName || ""==funName){
		                			 return "";
		                		 }else{
		                		 	/*funName = '<span data-toggle="tooltip" data-placement="right" title="'+funName+'">'+funName+'<span>';*/
		                		 	return funName;
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
											{"data": "createTime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var createTime = row.createTime;
		                		 if(null==createTime || ""==createTime){
		                			 return "";
		                		 }else{
		                		 	/*createTime = '<span data-toggle="tooltip" data-placement="right" title="'+createTime+'">'+createTime+'<span>';*/
		                		 	return createTime;
		                		 }
		                    } 	
		                },
											{"data": "updateTime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var updateTime = row.updateTime;
		                		 if(null==updateTime || ""==updateTime){
		                			 return "";
		                		 }else{
		                		 	/*updateTime = '<span data-toggle="tooltip" data-placement="right" title="'+updateTime+'">'+updateTime+'<span>';*/
		                		 	return updateTime;
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
											{"data": "portrait", "bSortable": false,render: function(data, type, row, meta) {
		                		 var portrait = row.portrait;
		                		 if(null==portrait || ""==portrait){
		                			 return "";
		                		 }else{
		                		 	/*portrait = '<span data-toggle="tooltip" data-placement="right" title="'+portrait+'">'+portrait+'<span>';*/
		                		 	return portrait;
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