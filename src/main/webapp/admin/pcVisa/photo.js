function uploadImg(){
	var that = this;
    lrz(that.files[0])
    .then(function (rst) {
        /* $('.bacimg').attr('src',rst.base64);*/
        $('#upload').hide(); 
         sourceSize = (that.files[0].size / 1024).toFixed(2);
        resultSize = (rst.fileLen / 1024).toFixed(2);
        scale = parseInt(100 - (resultSize / sourceSize * 100));
        rst.formData.append('image',rst.file);
        alert('压缩后大小为：'+resultSize+'K  压缩率：'+scale+'%');
        uploadPositive(rst,rst.formData);  
        // 处理成功会执行
    })
    .catch(function (err) {
        console.log(err);
    });
};

//上传正面
function uploadPositive(rst, formData){
	if(!formData){
		formData = new FormData();
	}
    formData.append("image", rst.file); 
    console.log('------------------------------');
    console.log(formData); 
    $.ajax({
		type : "POST",//提交类型  
		//dataType : "json",//返回结果格式  
		url : '/admin/pcVisa/savePositive',//请求地址  
		async : true,
		processData : false, //当FormData在jquery中使用的时候需要设置此项
		contentType : false,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
		//请求数据  
		data : formData,
		success : function(obj) {//请求成功后的函数 
			console.log('=====成功========');
			//关闭加载层
			layer.closeAll('loading');
			//layer.close(layerIndex);
			if (true === obj.success) {
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//console.log('-----------------失败-------------');
			layer.closeAll('loading');
		}
	}); // end of ajaxSubmit
}