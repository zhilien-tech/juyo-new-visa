//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) { 
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
	bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
}

$('#uploadFileSeal').change(function(){
	var layerIndex =  layer.load(1, {shade: "#000"});
	$("#addBtn").attr('disabled',true);
	$("#updateBtn").attr('disabled',true);
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl);
		var formData = new FormData();
		formData.append("image", blob,file.name);
		$.ajax({ 
			type: "POST",//提交类型  
			dataType: "json",//返回结果格式  
			url: BASE_PATH + '/admin/company/uploadFile.html',//请求地址  
			async: true  ,
			processData: false, //当FormData在jquery中使用的时候需要设置此项
			contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
			//请求数据  
			data:formData ,
			success: function (obj) {//请求成功后的函数 
				//关闭加载层
				layer.close(layerIndex);
				if('200' === obj.status){
					$('#seal').val(obj.data);
					$('#sqImgSeal').attr('src',obj.data);
				}
				$("#addBtn").attr('disabled',false);
				$("#updateBtn").attr('disabled',false);
			},  
			error: function (obj) {
				layer.close(layerIndex);
				$("#addBtn").attr('disabled',false);
				$("#updateBtn").attr('disabled',false);
			}
		});  // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

