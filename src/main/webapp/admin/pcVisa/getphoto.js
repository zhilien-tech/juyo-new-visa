//单张图片上传
function uploadPositive(rst, formData, staffid, usertype) {
	if (!formData) {
		formData = new FormData();
	}
	formData.append("uploadFileImg", rst.file);
	formData.append("type", 13);
	formData.append("staffid", staffid);
	console.log('------------------------------');
	console.log(formData);
	/*
	 * var layerIndex = layer.load(1, { shade : "#000" });
	 */
	$.ajax({
		type : "POST",// 提交类型
		// dataType : "json",//返回结果格式
		url : '/admin/mobileVisa/uploadImage.html',// 请求地址
		async : true,
		processData : false, // 当FormData在jquery中使用的时候需要设置此项
		contentType : false,// 如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
		// 请求数据
		data : formData,
		success : function(obj) {// 请求成功后的函数
			// console.log('=====成功========');
			// 关闭加载层
			layer.closeAll('loading');
			if (obj != null) {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// console.log('-----------------失败-------------');
			layer.closeAll('loading');
		}
	}); // end of ajaxSubmit
}
// 2寸照片回显
function twonichphoto(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if (data != null) {
				if (13 == data.type) {
					$("#twonichphoto").attr("src", data.url);
				}
			}
		}
	});
};
// 护照回显
function huzhao(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if (data != null) {
				$("#huzhao").attr("src", data.url);
			}
		}
	});
};
// 老护照回显
function oldhuzhao(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if (data.query.length > 0) {
				for(var i=0;i<data.query.length;i++){
					var url=data.query[i].url;
					$(".beforeInfo").before('<div class="uploadPassports">'+
							'<div>上传</div>'+
							'<img src='+url+' class="loadImg" width="100%" height="170px;"/>'+
						'</div>');
					$(".oldpassports").css("display","none");
				}	
			}
		}
	});
};
//身份证回显
function card(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data.query.length == 1){
					var url=data.query[0].url;
					if(data.query[0].status == 1){
						$("#card").attr('src',url);
						//$("#cardBack").attr('src',data.query[1].url);
					}else{
						$("#cardBack").attr('src',url);
						//$("#card").attr('src',data.query[1].url);
					}
			}
			if(data.query.length == 2){
				var url=data.query[0].url;
				if(data.query[0].status == 1){
					$("#card").attr('src',url);
					$("#cardBack").attr('src',data.query[1].url);
				}else{
					$("#cardBack").attr('src',url);
					$("#card").attr('src',data.query[1].url);
				}
			}
		}
	});
};
//结婚证回显
function marray(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
			$("#marray").attr("src",data.url);
			}
		}
	});
};
//在职证回显
function jobCertificate(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
			$("#jobCertificate").attr("src",data.url);
			}
		}
	});
};
//营业执照回显
function business(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
			$("#business").attr("src",data.url);
			}
		}
	});
};
//美国出签回显
function chuqian(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
				$("#chuqian").attr("src",data.url);
			}
		}
	});
};
//行驶证回显
function drive(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : type,
			staffid : staffid
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data.query.length > 0){
				var url=data.query[0].url;
				if(data.query.length == 1){
					$("#drive").attr('src',url);
				}
				if(data.query.length == 2){
					$("#drive").attr('src',data.query[1].url);
					$("#drive2").attr('src',url);
				}
				
			}
		}
	});
};
//房产证回显
function housecard(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data.query.length > 0){
				for(var i = 0;i<data.query.length;i++){
				$(".housecard").after('<div class="uploadReleases hoursecardMain housecard'+i+'" >'+
					'<div>上传</div>'+
					'<img src="'+data.query[i].url+'" class="longitudinal"/>'+
				'</div>');
				}
				$(".1").remove();
			}
			if(data.explain != null){
				$("#propertyholder").val(data.explain.propertyholder);
				$("#area").val(data.explain.area);
				$("#address").val(data.explain.address);
			}
		}
	});
};
//户口回显
function household(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getMuchPhotoByStaffid.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data.query.length > 0){
			//	console.log(data);
			//$("#household").attr("src",data.url);
				$(".householdBack").next().remove();
				for(var i = data.query.length-1;i>=0;i--){
					console.log(data.query[i].url);
					$(".householdBack").after('<div class="uploadReleases">'+
					'<div>户主页</div>'+
					'<img src="'+data.query[i].url+'" class="longitudinal" />'+
				'</div>');
				}
			}
		}
	});
};

//银行流水
function bankflow(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getWxMorePhotos.html",
		data : {
			type : type,
			staffid : staffid
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
				for(var i = 0;i<data.length;i++){
					$(".bankflow_moreImaage_WX_jssdk").after(
						'<div class="uploadReleases bankGroup"><img src="'+data[i].url+'" class="sampleImg"/></div>'
					);
				}
				
			}
		}
	});
}

//过期美签
function oldsigned(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getWxMorePhotos.html",
		data : {
			type : type,
			staffid : staffid
		},
		dataType : "json",
		async: false,
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			if(data!=null){
				for(var i = 0;i<data.length;i++){
					$(".oldsigned_moreImaage_WX_jssdk").after(
						'<div class="uploadReleases"><img src="'+data[i].url+'" class="longitudinal"/></div>'
					);
				}
				
			}
		}
	});
}


// 返回
function closeWindow() {
	parent.successCallback(2);
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

// 保存跳转下一页
function savePhoto() {
	/*var staffid = $("#staffid").val();
	var passportId = $("#passportId").val();
	window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='
			+ passportId;*/
	parent.successCallback(2);
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

/* 删除二寸免冠照 */
function deleteApplicantFrontImg() {

	$('#twonichphoto').attr('src', "");
	$("#uploadPhoto .delete").hide();
	/* $("#uploadPhoto").siblings("i").css("display","none"); */
}

//右箭头跳转
function saveApplicant(status){
	if(status == 2){
		window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId+'&isDisable='+isDisable;
		parent.successCallback(2);
	}
}
