//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) { 
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
	bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
}

$('#uploadFile').change(function(){
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
					$('#license').val(obj.data);
					$('#sqImg').attr('src',obj.data);
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


/*function uploadFile(){
	//文件上传
	$.fileupload = $('#uploadFile').uploadify({
		'auto' : true,//选择文件后自动上传
		'formData' : {
			'fcharset' : 'uft-8',
			'action' : 'uploadimage'
		},
		'buttonText' : '上传营业执照',//按钮显示的文字
		'buttonClass': "", //设置上传按钮的class
		'fileSizeLimit' : '3000MB',
		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
		'fileTypeExts' : '*.png; *.jpg; *.bmp; *.gif; *.jpeg;',//上传文件的类型
		'swf' : BASE_PATH + '/references/public/plugins/uploadify/uploadify.swf',//指定swf文件
		'multi' : false,//multi设置为true将允许多文件上传
		'successTimeout' : 1800,
		'queueSizeLimit' : 100,
		'uploader' : BASE_PATH + '/admin/company/uploadFile.html;',
		'onUploadStart' : function(file) {
			$("#addBtn").attr('disabled',true);
			$("#updateBtn").attr('disabled',true);
		},
		'onUploadSuccess' : function(file, data, response) {
			var jsonobj = eval('(' + data + ')');
			var url  = jsonobj;//地址
			var fileName = file.name;//文件名称
			$('#license').val(url);
			$('#sqImg').attr('src',url);
			$("#addBtn").attr('disabled',false);
			$("#updateBtn").attr('disabled',false);
		},
		//加上此句会重写onSelectError方法【需要重写的事件】
		'overrideEvents': ['onSelectError', 'onDialogClose'],
		//返回一个错误，选择文件的时候触发
		'onSelectError':function(file, errorCode, errorMsg){
			switch(errorCode) {
			case -110:
				alert("文件 ["+file.name+"] 大小超出系统限制");
				break;
			case -120:
				alert("文件 ["+file.name+"] 大小异常！");
				break;
			case -130:
				alert("文件 ["+file.name+"] 类型不正确！");
				break;
			}
		},
		onError: function(event, queueID, fileObj) {
			$("#submit").attr('disabled',false);
		}
	});
}*/