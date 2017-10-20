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
//        "fnDrawCallback"    : function(){
//        	var api = this.api();
//        	var startIndex= api.context[0]._iDisplayStart;
//		　　    api.column(0).nodes().each(function(cell, i) {
//			   　　　　cell.innerHTML = startIndex + i + 1;
//			});
//      	},

        "columns": [
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
											{"data": "takeoffname", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeoffname = row.takeoffname;
		                		 if(null==takeoffname || ""==takeoffname){
		                			 return "";
		                		 }else{
		                		 	/*takeOffName = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffName+'">'+takeOffName+'<span>';*/
		                		 	return takeoffname;
		                		 }
		                    } 	
		                },
											{"data": "takeoffcode", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeoffcode = row.takeoffcode;
		                		 if(null==takeoffcode || ""==takeoffcode){
		                			 return "";
		                		 }else{
		                		 	/*takeOffCode = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffCode+'">'+takeOffCode+'<span>';*/
		                		 	return takeoffcode;
		                		 }
		                    } 	
		                },
											{"data": "landingname", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingname = row.landingname;
		                		 if(null==landingname || ""==landingname){
		                			 return "";
		                		 }else{
		                		 	/*landingName = '<span data-toggle="tooltip" data-placement="right" title="'+landingName+'">'+landingName+'<span>';*/
		                		 	return landingname;
		                		 }
		                    } 	
		                },
											{"data": "landingcode", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingcode = row.landingcode;
		                		 if(null==landingcode || ""==landingcode){
		                			 return "";
		                		 }else{
		                		 	/*landingCode = '<span data-toggle="tooltip" data-placement="right" title="'+landingCode+'">'+landingCode+'<span>';*/
		                		 	return landingcode;
		                		 }
		                    } 	
		                },
											{"data": "takeoffcityid", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeoffcityid = row.takeoffcityid;
		                		 if(null==takeoffcityid || ""==takeoffcityid){
		                			 return "";
		                		 }else{
		                		 	/*takeOffCityId = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffCityId+'">'+takeOffCityId+'<span>';*/
		                		 	return takeoffcityid;
		                		 }
		                    } 	
		                },
											{"data": "landingcityid", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingcityid = row.landingcityid;
		                		 if(null==landingcityid || ""==landingcityid){
		                			 return "";
		                		 }else{
		                		 	/*landingCityId = '<span data-toggle="tooltip" data-placement="right" title="'+landingCityId+'">'+landingCityId+'<span>';*/
		                		 	return landingcityid;
		                		 }
		                    } 	
		                },
											{"data": "takeofftime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeofftime = row.takeofftime;
		                		 if(null==takeofftime || ""==takeofftime){
		                			 return "";
		                		 }else{
		                		 	/*takeOffTime = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffTime+'">'+takeOffTime+'<span>';*/
		                		 	return takeofftime;
		                		 }
		                    } 	
		                },
											{"data": "landingtime", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingtime = row.landingtime;
		                		 if(null==landingtime || ""==landingtime){
		                			 return "";
		                		 }else{
		                		 	/*landingTime = '<span data-toggle="tooltip" data-placement="right" title="'+landingTime+'">'+landingTime+'<span>';*/
		                		 	return landingtime;
		                		 }
		                    } 	
		                },
											{"data": "takeoffterminal", "bSortable": false,render: function(data, type, row, meta) {
		                		 var takeoffterminal = row.takeoffterminal;
		                		 if(null==takeoffterminal || ""==takeoffterminal){
		                			 return "";
		                		 }else{
		                		 	/*takeOffTerminal = '<span data-toggle="tooltip" data-placement="right" title="'+takeOffTerminal+'">'+takeOffTerminal+'<span>';*/
		                		 	return takeoffterminal;
		                		 }
		                    } 	
		                },
											{"data": "landingterminal", "bSortable": false,render: function(data, type, row, meta) {
		                		 var landingterminal = row.landingterminal;
		                		 if(null==landingterminal || ""==landingterminal){
		                			 return "";
		                		 }else{
		                		 	/*landingTerminal = '<span data-toggle="tooltip" data-placement="right" title="'+landingTerminal+'">'+landingTerminal+'<span>';*/
		                		 	return landingterminal;
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