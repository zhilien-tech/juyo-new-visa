//单张图片上传
function uploadPositive(rst, formData, staffid) {
	if (!formData) {
		formData = new FormData();
	}
	formData.append("image", rst.file);
	formData.append("type", 13);
	formData.append("staffid", staffid);
	console.log('------------------------------');
	console.log(formData);
	console.log(rst.file);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			if (data != null) {
				
				for(var i=0;i<data.length;i++){
					var url=data[i].url;
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			for(var i=0;i<data.length;i++){
				var url=data[i].url;
				$("#card").attr('src',url);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#marray").attr("src",data.url);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#jobCertificate").attr("src",data.url);
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
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#business").attr("src",data.url);
		}
	});
};
//驾驶证回显
function drive(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#drive").attr("src",data.url);
		}
	});
};
//房产证回显
function housecard(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#housecard").attr("src",data.url);
		}
	});
};
//户口回显
function household(staffid, type) {
	$.ajax({
		url : "/admin/mobileVisa/getInfoByType.html",
		data : {
			type : type,
			staffid : staffid,
		},
		dataType : "json",
		type : 'post',
		success : function(data) {
			/* _self.passportdata = data.passportdata; */
			console.log(data);
			$("#household").attr("src",data.url);
		}
	});
};

// 返回
function closeWindow() {
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

// 保存跳转下一页
function nextWindow() {
	/*var staffid = $("#staffid").val();
	var passportId = $("#passportId").val();
	window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='
			+ passportId;*/
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
		window.location.href = '/admin/bigCustomer/updatePassportInfo.html?passportId='+passportId;
	}
}