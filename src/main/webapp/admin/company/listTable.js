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
            "url": BASE_PATH + "/admin/company/listData.html",
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
											{"data": "shortName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var shortName = row.shortName;
		                		 if(null==shortName || ""==shortName){
		                			 return "";
		                		 }else{
		                		 	/*shortName = '<span data-toggle="tooltip" data-placement="right" title="'+shortName+'">'+shortName+'<span>';*/
		                		 	return shortName;
		                		 }
		                    } 	
		                },
											{"data": "adminId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var adminId = row.adminId;
		                		 if(null==adminId || ""==adminId){
		                			 return "";
		                		 }else{
		                		 	/*adminId = '<span data-toggle="tooltip" data-placement="right" title="'+adminId+'">'+adminId+'<span>';*/
		                		 	return adminId;
		                		 }
		                    } 	
		                },
											{"data": "linkman", "bSortable": false,render: function(data, type, row, meta) {
		                		 var linkman = row.linkman;
		                		 if(null==linkman || ""==linkman){
		                			 return "";
		                		 }else{
		                		 	/*linkman = '<span data-toggle="tooltip" data-placement="right" title="'+linkman+'">'+linkman+'<span>';*/
		                		 	return linkman;
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
											{"data": "comType", "bSortable": false,render: function(data, type, row, meta) {
		                		 var comType = row.comType;
		                		 if(null==comType || ""==comType){
		                			 return "";
		                		 }else{
		                		 	/*comType = '<span data-toggle="tooltip" data-placement="right" title="'+comType+'">'+comType+'<span>';*/
		                		 	return comType;
		                		 }
		                    } 	
		                },
											{"data": "license", "bSortable": false,render: function(data, type, row, meta) {
		                		 var license = row.license;
		                		 if(null==license || ""==license){
		                			 return "";
		                		 }else{
		                		 	/*license = '<span data-toggle="tooltip" data-placement="right" title="'+license+'">'+license+'<span>';*/
		                		 	return license;
		                		 }
		                    } 	
		                },
											{"data": "opId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var opId = row.opId;
		                		 if(null==opId || ""==opId){
		                			 return "";
		                		 }else{
		                		 	/*opId = '<span data-toggle="tooltip" data-placement="right" title="'+opId+'">'+opId+'<span>';*/
		                		 	return opId;
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
											{"data": "deletestatus", "bSortable": false,render: function(data, type, row, meta) {
		                		 var deletestatus = row.deletestatus;
		                		 if(null==deletestatus || ""==deletestatus){
		                			 return "";
		                		 }else{
		                		 	/*deletestatus = '<span data-toggle="tooltip" data-placement="right" title="'+deletestatus+'">'+deletestatus+'<span>';*/
		                		 	return deletestatus;
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
    	    content: BASE_PATH + '/admin/company/add.html'
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
    	    content: BASE_PATH + '/admin/company/update.html?id='+id
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
		var url = BASE_PATH + '/admin/company/delete.html';
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