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
                    						{"data": "comId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var comId = row.comId;
		                		 if(null==comId || ""==comId){
		                			 return "";
		                		 }else{
		                		 	/*comId = '<span data-toggle="tooltip" data-placement="right" title="'+comId+'">'+comId+'<span>';*/
		                		 	return comId;
		                		 }
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
											{"data": "qq", "bSortable": false,render: function(data, type, row, meta) {
		                		 var qq = row.qq;
		                		 if(null==qq || ""==qq){
		                			 return "";
		                		 }else{
		                		 	/*qq = '<span data-toggle="tooltip" data-placement="right" title="'+qq+'">'+qq+'<span>';*/
		                		 	return qq;
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
											{"data": "departmentId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var departmentId = row.departmentId;
		                		 if(null==departmentId || ""==departmentId){
		                			 return "";
		                		 }else{
		                		 	/*departmentId = '<span data-toggle="tooltip" data-placement="right" title="'+departmentId+'">'+departmentId+'<span>';*/
		                		 	return departmentId;
		                		 }
		                    } 	
		                },
											{"data": "jobId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var jobId = row.jobId;
		                		 if(null==jobId || ""==jobId){
		                			 return "";
		                		 }else{
		                		 	/*jobId = '<span data-toggle="tooltip" data-placement="right" title="'+jobId+'">'+jobId+'<span>';*/
		                		 	return jobId;
		                		 }
		                    } 	
		                },
											{"data": "isDisable", "bSortable": false,render: function(data, type, row, meta) {
		                		 var isDisable = row.isDisable;
		                		 if(null==isDisable || ""==isDisable){
		                			 return "";
		                		 }else{
		                		 	/*isDisable = '<span data-toggle="tooltip" data-placement="right" title="'+isDisable+'">'+isDisable+'<span>';*/
		                		 	return isDisable;
		                		 }
		                    } 	
		                },
											{"data": "password", "bSortable": false,render: function(data, type, row, meta) {
		                		 var password = row.password;
		                		 if(null==password || ""==password){
		                			 return "";
		                		 }else{
		                		 	/*password = '<span data-toggle="tooltip" data-placement="right" title="'+password+'">'+password+'<span>';*/
		                		 	return password;
		                		 }
		                    } 	
		                },
											{"data": "userType", "bSortable": false,render: function(data, type, row, meta) {
		                		 var userType = row.userType;
		                		 if(null==userType || ""==userType){
		                			 return "";
		                		 }else{
		                		 	/*userType = '<span data-toggle="tooltip" data-placement="right" title="'+userType+'">'+userType+'<span>';*/
		                		 	return userType;
		                		 }
		                    } 	
		                },
											{"data": "lastLoginTime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var lastLoginTime = row.lastLoginTime;
		                		 if(null==lastLoginTime || ""==lastLoginTime){
		                			 return "";
		                		 }else{
		                		 	/*lastLoginTime = '<span data-toggle="tooltip" data-placement="right" title="'+lastLoginTime+'">'+lastLoginTime+'<span>';*/
		                		 	return lastLoginTime;
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
    	    area: ['900px', '550px'],
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