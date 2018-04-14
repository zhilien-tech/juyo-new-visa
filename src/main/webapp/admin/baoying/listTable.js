var datatable;
function initDatatable() {
	datatable = $('#datatableId').DataTable({
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": "/references/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": "/admin/baoying/listData.html",
			"type": "post",
			/* "data": function (d) {

            } */
		},
		"columns": [
			{"data": "ordernumber", "bSortable": false,render: function(data, type, row, meta) {
				var ordernumber = row.ordernumber;
				if(null==ordernumber || ""==ordernumber){
					return "";
				}else{
					return ordernumber;
				}
			} 	
			},
			{"data": "name", "name": false,render: function(data, type, row, meta) {
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
					return telephone;
				}
			} 	
			},
			{"data": "email", "bSortable": false,render: function(data, type, row, meta) {
				var email = row.email;
				if(null==email || ""==email){
					return "";
				}else{
					return email;
				}
			} 	
			},
			{"data": "cityid", "bSortable": false,render: function(data, type, row, meta) {
				var cityid = row.cityid;
				if(null==cityid || ""==cityid){
					return "";
				}else{
					return cityid;
				}
			} 	
			},
			{"data": "interviewdate", "bSortable": false,
				render: function(data, type, row, meta) {
					var interviewdate = row.interviewdate;
					if(null==interviewdate || ""==interviewdate){
						return "";
					}else{
						return interviewdate;
					}
				} 	
			},
			{"data": "orderstatus", "bSortable": false,
				render: function(data, type, row, meta) {
					var orderstatus = row.orderstatus;
					if(null==orderstatus || ""==orderstatus){
						return "";
					}else{
						return orderstatus;
					}
				} 	
			},
			{"data": " ", "bSortable": false, 
				render: function(data, type, row, meta) {
					var updateApplicant = '<a style="cursor:pointer;" class="updateApplicant" onclick="baseInfo('+row.staffid+');"></a>';
					var passport = '<a style="cursor:pointer;" class="passport" onclick="passport('+row.passportid+');"></a>';
					var visa = '<a class="visa" onclick="visa('+row.staffid+');"></a>';
					var otherVisa = '<a class="otherVisa" onclick="updatePhoto('+row.staffid+');"></a>';
					return otherVisa+passport+updateApplicant+visa;
				}	
			} 
			],
			columnDefs: [{}]
	});
}

/* 基本信息编辑 */
function baseInfo(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content: '/admin/bigCustomer/updateBaseInfo.html?staffId='+id+'&isDisable='+$("#disablePageInfo").val()
	});
}

/* 护照信息编辑 */
function passport(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content: '/admin/bigCustomer/updatePassportInfo.html?passportId='+id+'&isDisable='+$("#disablePageInfo").val()
	});
}

//修改申请人拍摄资料信息
function updatePhoto(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content:'/admin/pcVisa/updatePhoto.html?staffid='+id+'&flag=0'+'&isDisable='+$("#disablePageInfo").val()
	});
}

/* 签证信息编辑 */
function visa(id){
	layer.open({
		type: 2,
		title: false,
		closeBtn:false,
		fix: false,
		maxmin: false,
		shadeClose: false,
		scrollbar: false,
		area: ['900px', '80%'],
		content: '/admin/bigCustomer/updateVisaInfo.html?staffId='+id+'&isDisable='+$("#disablePageInfo").val()
	});
}

/*状态改变事件*/
function selectListData(){
	$("#searchBtn").click();
}

//搜索按钮
$("#searchBtn").on('click', function () {
	var searchStr = $("#searchStr").val();
	var orderstatus = $("#status").val();
	var cityid = $("#cityid").val();
    var param = {
		"searchStr": searchStr,
		"orderstatus":orderstatus,
		"cityid":cityid
	};
	datatable.settings()[0].ajax.data = param;
	datatable.ajax.reload();
});

//清空按钮
$("#emptyBtn").on('click', function () {
	$("#searchStr").val("");
	$("#status").val("");
	$("#cityid").val("");
	$("#searchBtn").click();
});

/*回车事件*/
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode == 13){
		$("#searchBtn").click();
	}
}