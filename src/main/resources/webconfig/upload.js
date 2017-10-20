var ioc = {
	tmpUpload : {
		type : "org.nutz.mvc.upload.UploadAdaptor",
		singleton : false,
		args : [ {
			refer : 'tmpUploadFileContext'
		} ]
	},
	tmpFilePool : {
		type : 'org.nutz.filepool.NutFilePool',
		args : [ "~/tmpuploadfiles", 1000 ]
	},
	tmpUploadFileContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ {
			refer : 'tmpFilePool'
		} ],
		fields : {
			ignoreNull : true,
			maxFileSize : 11048576,
			nameFilter : '^(.+[.])(jpg|jpeg|png|gif|bmp|doc|docx|xls|xlsx|ppt|zip|rar|caf|mp3|amr|3gp|mp4|wps|txt|acc)$'
		}
	},
	imgUpload : {
		type : "org.nutz.mvc.upload.UploadAdaptor",
		singleton : false,
		args : [ {
			refer : 'imgUploadFileContext'
		} ]
	},
	imgUploadFileContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ {
			refer : 'tmpFilePool'
		} ],
		fields : {
			ignoreNull : true,
			maxFileSize : 11048576,
			nameFilter : '^(.+[.])(jpg|jpeg|png|gif|bmp)$'
		}
	}
}