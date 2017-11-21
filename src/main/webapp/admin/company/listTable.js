var datatable;
function initDatatable() {
    datatable = $('#datatableId').DataTable({
    	"pageLength":20,
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
		"columns": [
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
		            {"data": "shortname", "bSortable": false,render: function(data, type, row, meta) {
		            	var shortname = row.shortname;
		            	if(null==shortname || ""==shortname){
		            		return "";
		            	}else{
		            		/*shortname = '<span data-toggle="tooltip" data-placement="right" title="'+shortname+'">'+shortname+'<span>';*/
		            		return shortname;
		            	}
		            } 	
		            },
		            {"data": "adminloginname", "bSortable": false,render: function(data, type, row, meta) {
		            	var adminloginname = row.adminloginname;
		            	if(null==adminloginname || ""==adminloginname){
		            		return "";
		            	}else{
		            		return adminloginname;
		            	}
		            } 	
		            },
		            {"data": "comtype", "bSortable": false,render: function(data, type, row, meta) {
		            	var comtype = row.comtype;
		            	if(null==comtype || ""==comtype){
		            		return "";
		            	}else{
		            		/*comtype = '<span data-toggle="tooltip" data-placement="right" title="'+comtype+'">'+comtype+'<span>';*/
		            		if(1==comtype){
		            			comtype="送签社";
		            		}else if(2==comtype){
		            			comtype="地接社";
		            		}else{
		            			comtype="undefined";
		            		}
		            		return comtype;
		            	}
		            } 	
		            },
		            {"data": "scopes", "bSortable": false,render: function(data, type, row, meta) {
		            	var scopes = row.scopes;
		            	if(null==scopes || ""==scopes){
		            		return "";
		            	}else{
		            		var arr = scopes.split(',');
		            		var scopeStr = "";
		            		for(var i=0;i<arr.length;i++){
		            			if(arr[i] == 1){
		            				scopeStr += "日";
		            			}else if(arr[i] == 2){
		            				
		            			}
		            		}
		            		return scopeStr;
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
		            {"data": " ", "bSortable": false, "width":50,
		            	render: function(data, type, row, meta) {
		            		var modify = '<a style="cursor:pointer;" class="edit-icon" onclick="edit('+row.id+');"></a>';
		            		var deleteC = '<a style="cursor:pointer;" class="delete-icon" onclick="deleteById('+row.id+');"></a>';
		            		/*return modify+deleteC;*/
		            		return modify;
		            	}	
		            } 
		            ],
		            columnDefs: [{}]
	});
}

$("#searchBtn").on('click', function () {
	var companyName = $("#searchStr").val();
	var comType = $('#comType').val();
	    var param = {
			        "searchStr": companyName,
			"comType" : comType
			    };
	    datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
});

/*回车事件*/
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		$("#searchBtn").click();
	}
}

/*公司类型change事件*/
function selectListData(){
	$("#searchBtn").click();
}

//置空检索项
function emptySearch(){
	$("#comType").val("");
	$("#searchStr").val("");
	selectListData();
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
