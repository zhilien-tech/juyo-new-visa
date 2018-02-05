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
											{"data": "userId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var userId = row.userId;
		                		 if(null==userId || ""==userId){
		                			 return "";
		                		 }else{
		                		 	/*userId = '<span data-toggle="tooltip" data-placement="right" title="'+userId+'">'+userId+'<span>';*/
		                		 	return userId;
		                		 }
		                    } 	
		                },
											{"data": "firstName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var firstName = row.firstName;
		                		 if(null==firstName || ""==firstName){
		                			 return "";
		                		 }else{
		                		 	/*firstName = '<span data-toggle="tooltip" data-placement="right" title="'+firstName+'">'+firstName+'<span>';*/
		                		 	return firstName;
		                		 }
		                    } 	
		                },
											{"data": "firstNameEn", "bSortable": false,render: function(data, type, row, meta) {
		                		 var firstNameEn = row.firstNameEn;
		                		 if(null==firstNameEn || ""==firstNameEn){
		                			 return "";
		                		 }else{
		                		 	/*firstNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+firstNameEn+'">'+firstNameEn+'<span>';*/
		                		 	return firstNameEn;
		                		 }
		                    } 	
		                },
											{"data": "lastName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var lastName = row.lastName;
		                		 if(null==lastName || ""==lastName){
		                			 return "";
		                		 }else{
		                		 	/*lastName = '<span data-toggle="tooltip" data-placement="right" title="'+lastName+'">'+lastName+'<span>';*/
		                		 	return lastName;
		                		 }
		                    } 	
		                },
											{"data": "lastNameEn", "bSortable": false,render: function(data, type, row, meta) {
		                		 var lastNameEn = row.lastNameEn;
		                		 if(null==lastNameEn || ""==lastNameEn){
		                			 return "";
		                		 }else{
		                		 	/*lastNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+lastNameEn+'">'+lastNameEn+'<span>';*/
		                		 	return lastNameEn;
		                		 }
		                    } 	
		                },
											{"data": "status", "bSortable": false,render: function(data, type, row, meta) {
		                		 var status = row.status;
		                		 if(null==status || ""==status){
		                			 return "";
		                		 }else{
		                		 	/*status = '<span data-toggle="tooltip" data-placement="right" title="'+status+'">'+status+'<span>';*/
		                		 	return status;
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
											{"data": "sex", "bSortable": false,render: function(data, type, row, meta) {
		                		 var sex = row.sex;
		                		 if(null==sex || ""==sex){
		                			 return "";
		                		 }else{
		                		 	/*sex = '<span data-toggle="tooltip" data-placement="right" title="'+sex+'">'+sex+'<span>';*/
		                		 	return sex;
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
											{"data": "job", "bSortable": false,render: function(data, type, row, meta) {
		                		 var job = row.job;
		                		 if(null==job || ""==job){
		                			 return "";
		                		 }else{
		                		 	/*job = '<span data-toggle="tooltip" data-placement="right" title="'+job+'">'+job+'<span>';*/
		                		 	return job;
		                		 }
		                    } 	
		                },
											{"data": "nation", "bSortable": false,render: function(data, type, row, meta) {
		                		 var nation = row.nation;
		                		 if(null==nation || ""==nation){
		                			 return "";
		                		 }else{
		                		 	/*nation = '<span data-toggle="tooltip" data-placement="right" title="'+nation+'">'+nation+'<span>';*/
		                		 	return nation;
		                		 }
		                    } 	
		                },
											{"data": "birthday", "bSortable": false,render: function(data, type, row, meta) {
		                		 var birthday = row.birthday;
		                		 if(null==birthday || ""==birthday){
		                			 return "";
		                		 }else{
		                		 	/*birthday = '<span data-toggle="tooltip" data-placement="right" title="'+birthday+'">'+birthday+'<span>';*/
		                		 	return birthday;
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
											{"data": "cardId", "bSortable": false,render: function(data, type, row, meta) {
		                		 var cardId = row.cardId;
		                		 if(null==cardId || ""==cardId){
		                			 return "";
		                		 }else{
		                		 	/*cardId = '<span data-toggle="tooltip" data-placement="right" title="'+cardId+'">'+cardId+'<span>';*/
		                		 	return cardId;
		                		 }
		                    } 	
		                },
											{"data": "cardFront", "bSortable": false,render: function(data, type, row, meta) {
		                		 var cardFront = row.cardFront;
		                		 if(null==cardFront || ""==cardFront){
		                			 return "";
		                		 }else{
		                		 	/*cardFront = '<span data-toggle="tooltip" data-placement="right" title="'+cardFront+'">'+cardFront+'<span>';*/
		                		 	return cardFront;
		                		 }
		                    } 	
		                },
											{"data": "cardBack", "bSortable": false,render: function(data, type, row, meta) {
		                		 var cardBack = row.cardBack;
		                		 if(null==cardBack || ""==cardBack){
		                			 return "";
		                		 }else{
		                		 	/*cardBack = '<span data-toggle="tooltip" data-placement="right" title="'+cardBack+'">'+cardBack+'<span>';*/
		                		 	return cardBack;
		                		 }
		                    } 	
		                },
											{"data": "issueOrganization", "bSortable": false,render: function(data, type, row, meta) {
		                		 var issueOrganization = row.issueOrganization;
		                		 if(null==issueOrganization || ""==issueOrganization){
		                			 return "";
		                		 }else{
		                		 	/*issueOrganization = '<span data-toggle="tooltip" data-placement="right" title="'+issueOrganization+'">'+issueOrganization+'<span>';*/
		                		 	return issueOrganization;
		                		 }
		                    } 	
		                },
											{"data": "validStartDate", "bSortable": false,render: function(data, type, row, meta) {
		                		 var validStartDate = row.validStartDate;
		                		 if(null==validStartDate || ""==validStartDate){
		                			 return "";
		                		 }else{
		                		 	/*validStartDate = '<span data-toggle="tooltip" data-placement="right" title="'+validStartDate+'">'+validStartDate+'<span>';*/
		                		 	return validStartDate;
		                		 }
		                    } 	
		                },
											{"data": "validEndDate", "bSortable": false,render: function(data, type, row, meta) {
		                		 var validEndDate = row.validEndDate;
		                		 if(null==validEndDate || ""==validEndDate){
		                			 return "";
		                		 }else{
		                		 	/*validEndDate = '<span data-toggle="tooltip" data-placement="right" title="'+validEndDate+'">'+validEndDate+'<span>';*/
		                		 	return validEndDate;
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
											{"data": "detailedAddress", "bSortable": false,render: function(data, type, row, meta) {
		                		 var detailedAddress = row.detailedAddress;
		                		 if(null==detailedAddress || ""==detailedAddress){
		                			 return "";
		                		 }else{
		                		 	/*detailedAddress = '<span data-toggle="tooltip" data-placement="right" title="'+detailedAddress+'">'+detailedAddress+'<span>';*/
		                		 	return detailedAddress;
		                		 }
		                    } 	
		                },
											{"data": "otherFirstName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var otherFirstName = row.otherFirstName;
		                		 if(null==otherFirstName || ""==otherFirstName){
		                			 return "";
		                		 }else{
		                		 	/*otherFirstName = '<span data-toggle="tooltip" data-placement="right" title="'+otherFirstName+'">'+otherFirstName+'<span>';*/
		                		 	return otherFirstName;
		                		 }
		                    } 	
		                },
											{"data": "otherFirstNameEn", "bSortable": false,render: function(data, type, row, meta) {
		                		 var otherFirstNameEn = row.otherFirstNameEn;
		                		 if(null==otherFirstNameEn || ""==otherFirstNameEn){
		                			 return "";
		                		 }else{
		                		 	/*otherFirstNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+otherFirstNameEn+'">'+otherFirstNameEn+'<span>';*/
		                		 	return otherFirstNameEn;
		                		 }
		                    } 	
		                },
											{"data": "otherLastName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var otherLastName = row.otherLastName;
		                		 if(null==otherLastName || ""==otherLastName){
		                			 return "";
		                		 }else{
		                		 	/*otherLastName = '<span data-toggle="tooltip" data-placement="right" title="'+otherLastName+'">'+otherLastName+'<span>';*/
		                		 	return otherLastName;
		                		 }
		                    } 	
		                },
											{"data": "otherLastNameEn", "bSortable": false,render: function(data, type, row, meta) {
		                		 var otherLastNameEn = row.otherLastNameEn;
		                		 if(null==otherLastNameEn || ""==otherLastNameEn){
		                			 return "";
		                		 }else{
		                		 	/*otherLastNameEn = '<span data-toggle="tooltip" data-placement="right" title="'+otherLastNameEn+'">'+otherLastNameEn+'<span>';*/
		                		 	return otherLastNameEn;
		                		 }
		                    } 	
		                },
											{"data": "emergencyLinkman", "bSortable": false,render: function(data, type, row, meta) {
		                		 var emergencyLinkman = row.emergencyLinkman;
		                		 if(null==emergencyLinkman || ""==emergencyLinkman){
		                			 return "";
		                		 }else{
		                		 	/*emergencyLinkman = '<span data-toggle="tooltip" data-placement="right" title="'+emergencyLinkman+'">'+emergencyLinkman+'<span>';*/
		                		 	return emergencyLinkman;
		                		 }
		                    } 	
		                },
											{"data": "emergencyTelephone", "bSortable": false,render: function(data, type, row, meta) {
		                		 var emergencyTelephone = row.emergencyTelephone;
		                		 if(null==emergencyTelephone || ""==emergencyTelephone){
		                			 return "";
		                		 }else{
		                		 	/*emergencyTelephone = '<span data-toggle="tooltip" data-placement="right" title="'+emergencyTelephone+'">'+emergencyTelephone+'<span>';*/
		                		 	return emergencyTelephone;
		                		 }
		                    } 	
		                },
											{"data": "hasOtherNationality", "bSortable": false,render: function(data, type, row, meta) {
		                		 var hasOtherNationality = row.hasOtherNationality;
		                		 if(null==hasOtherNationality || ""==hasOtherNationality){
		                			 return "";
		                		 }else{
		                		 	/*hasOtherNationality = '<span data-toggle="tooltip" data-placement="right" title="'+hasOtherNationality+'">'+hasOtherNationality+'<span>';*/
		                		 	return hasOtherNationality;
		                		 }
		                    } 	
		                },
											{"data": "hasOtherName", "bSortable": false,render: function(data, type, row, meta) {
		                		 var hasOtherName = row.hasOtherName;
		                		 if(null==hasOtherName || ""==hasOtherName){
		                			 return "";
		                		 }else{
		                		 	/*hasOtherName = '<span data-toggle="tooltip" data-placement="right" title="'+hasOtherName+'">'+hasOtherName+'<span>';*/
		                		 	return hasOtherName;
		                		 }
		                    } 	
		                },
											{"data": "marryUrl", "bSortable": false,render: function(data, type, row, meta) {
		                		 var marryUrl = row.marryUrl;
		                		 if(null==marryUrl || ""==marryUrl){
		                			 return "";
		                		 }else{
		                		 	/*marryUrl = '<span data-toggle="tooltip" data-placement="right" title="'+marryUrl+'">'+marryUrl+'<span>';*/
		                		 	return marryUrl;
		                		 }
		                    } 	
		                },
											{"data": "marryStatus", "bSortable": false,render: function(data, type, row, meta) {
		                		 var marryStatus = row.marryStatus;
		                		 if(null==marryStatus || ""==marryStatus){
		                			 return "";
		                		 }else{
		                		 	/*marryStatus = '<span data-toggle="tooltip" data-placement="right" title="'+marryStatus+'">'+marryStatus+'<span>';*/
		                		 	return marryStatus;
		                		 }
		                    } 	
		                },
											{"data": "marryurltype", "bSortable": false,render: function(data, type, row, meta) {
		                		 var marryurltype = row.marryurltype;
		                		 if(null==marryurltype || ""==marryurltype){
		                			 return "";
		                		 }else{
		                		 	/*marryurltype = '<span data-toggle="tooltip" data-placement="right" title="'+marryurltype+'">'+marryurltype+'<span>';*/
		                		 	return marryurltype;
		                		 }
		                    } 	
		                },
											{"data": "nationality", "bSortable": false,render: function(data, type, row, meta) {
		                		 var nationality = row.nationality;
		                		 if(null==nationality || ""==nationality){
		                			 return "";
		                		 }else{
		                		 	/*nationality = '<span data-toggle="tooltip" data-placement="right" title="'+nationality+'">'+nationality+'<span>';*/
		                		 	return nationality;
		                		 }
		                    } 	
		                },
											{"data": "addressIsSameWithCard", "bSortable": false,render: function(data, type, row, meta) {
		                		 var addressIsSameWithCard = row.addressIsSameWithCard;
		                		 if(null==addressIsSameWithCard || ""==addressIsSameWithCard){
		                			 return "";
		                		 }else{
		                		 	/*addressIsSameWithCard = '<span data-toggle="tooltip" data-placement="right" title="'+addressIsSameWithCard+'">'+addressIsSameWithCard+'<span>';*/
		                		 	return addressIsSameWithCard;
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
    	    content: BASE_PATH + '/admin/bigcustomer/add.html'
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
    	    content: BASE_PATH + '/admin/bigcustomer/update.html?id='+id
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
		var url = BASE_PATH + '/admin/bigcustomer/delete.html';
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