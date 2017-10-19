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
            "url": BASE_PATH + "/admin/flight/listData.html",
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
                    						{"data": "flightnum", "bSortable": false,render: function(data, type, row, meta) {
		                		 var flightnum = row.flightnum;
		                		 if(null==flightnum || ""==flightnum){
		                			 return "";
		                		 }else{
		                		 	/*flightnum = '<span data-toggle="tooltip" data-placement="right" title="'+flightnum+'">'+flightnum+'<span>';*/
		                		 	return flightnum;
		                		 }
		                    } 	
		                },
											{"data": "airlinecomp", "bSortable": false,render: function(data, type, row, meta) {
		                		 var airlinecomp = row.airlinecomp;
		                		 if(null==airlinecomp || ""==airlinecomp){
		                			 return "";
		                		 }else{
		                		 	/*airlinecomp = '<span data-toggle="tooltip" data-placement="right" title="'+airlinecomp+'">'+airlinecomp+'<span>';*/
		                		 	return airlinecomp;
		                		 }
		                    } 	
		                },
											{"data": "takeOffName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeOffName = row.takeOffName;
		                		 if(null==takeOffName || ""==takeOffName){
		                			 return "";
		                		 }else{
		                		 	/*takeOffName = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffName+'">'+takeOffName+'<span>';*/
		                		 	return takeOffName;
		                		 }
		                    } 	
		                },
											{"data": "takeOffCode", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeOffCode = row.takeOffCode;
		                		 if(null==takeOffCode || ""==takeOffCode){
		                			 return "";
		                		 }else{
		                		 	/*takeOffCode = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffCode+'">'+takeOffCode+'<span>';*/
		                		 	return takeOffCode;
		                		 }
		                    } 	
		                },
											{"data": "landingName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingName = row.landingName;
		                		 if(null==landingName || ""==landingName){
		                			 return "";
		                		 }else{
		                		 	/*landingName = '<span data-toggle="tooltip" data-placement="right" title="'+landingName+'">'+landingName+'<span>';*/
		                		 	return landingName;
		                		 }
		                    } 	
		                },
											{"data": "landingCode", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingCode = row.landingCode;
		                		 if(null==landingCode || ""==landingCode){
		                			 return "";
		                		 }else{
		                		 	/*landingCode = '<span data-toggle="tooltip" data-placement="right" title="'+landingCode+'">'+landingCode+'<span>';*/
		                		 	return landingCode;
		                		 }
		                    } 	
		                },
											{"data": "takeOffCityId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeOffCityId = row.takeOffCityId;
		                		 if(null==takeOffCityId || ""==takeOffCityId){
		                			 return "";
		                		 }else{
		                		 	/*takeOffCityId = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffCityId+'">'+takeOffCityId+'<span>';*/
		                		 	return takeOffCityId;
		                		 }
		                    } 	
		                },
											{"data": "landingCityId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingCityId = row.landingCityId;
		                		 if(null==landingCityId || ""==landingCityId){
		                			 return "";
		                		 }else{
		                		 	/*landingCityId = '<span data-toggle="tooltip" data-placement="right" title="'+landingCityId+'">'+landingCityId+'<span>';*/
		                		 	return landingCityId;
		                		 }
		                    } 	
		                },
											{"data": "takeOffTime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeOffTime = row.takeOffTime;
		                		 if(null==takeOffTime || ""==takeOffTime){
		                			 return "";
		                		 }else{
		                		 	/*takeOffTime = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffTime+'">'+takeOffTime+'<span>';*/
		                		 	return takeOffTime;
		                		 }
		                    } 	
		                },
											{"data": "landingTime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingTime = row.landingTime;
		                		 if(null==landingTime || ""==landingTime){
		                			 return "";
		                		 }else{
		                		 	/*landingTime = '<span data-toggle="tooltip" data-placement="right" title="'+landingTime+'">'+landingTime+'<span>';*/
		                		 	return landingTime;
		                		 }
		                    } 	
		                },
											{"data": "takeOffTerminal", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeOffTerminal = row.takeOffTerminal;
		                		 if(null==takeOffTerminal || ""==takeOffTerminal){
		                			 return "";
		                		 }else{
		                		 	/*takeOffTerminal = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffTerminal+'">'+takeOffTerminal+'<span>';*/
		                		 	return takeOffTerminal;
		                		 }
		                    } 	
		                },
											{"data": "landingTerminal", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingTerminal = row.landingTerminal;
		                		 if(null==landingTerminal || ""==landingTerminal){
		                			 return "";
		                		 }else{
		                		 	/*landingTerminal = '<span data-toggle="tooltip" data-placement="right" title="'+landingTerminal+'">'+landingTerminal+'<span>';*/
		                		 	return landingTerminal;
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
    	    content: BASE_PATH + '/admin/flight/add.html'
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
    	    content: BASE_PATH + '/admin/flight/update.html?id='+id
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
		var url = BASE_PATH + '/admin/flight/delete.html';
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