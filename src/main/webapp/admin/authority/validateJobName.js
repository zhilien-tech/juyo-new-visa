//jobName keyup事件
function jobNameKeyup(e){
	var jobName = $(e).val();
	var jobId = $("#jobId").val();
	var jobDiv = $(e).parent().parent().parent();
	var jobSmall = $(e).parent().parent().next().children().eq(0);
	var jobSmallExist = $(e).parent().parent().next().children().eq(1);
	if(jobName.length>0){
		jobDiv.attr("class", "job_container form-group has-success");
		jobSmall.attr("data-bv-result","VALID");
		jobSmall.attr("style","display: none;");

		//职位是否存在
		$.ajax({
			type : 'POST',
			async:false, 
			data : {
				jobName:jobName,
				jobId:jobId
			},
			url : BASE_PATH+'/admin/authority/checkJobNameExist.html',
			success : function(data) {
				var isExist = data.valid+"";
				if(isExist=="true"){//不重复
					jobDiv.attr("class", "job_container form-group has-success");
					jobSmallExist.attr("data-bv-result","VALID");
					jobSmallExist.attr("style","display: none;");
				}else{//已存在
					jobDiv.attr("class", "job_container form-group has-error");
					jobSmallExist.attr("data-bv-result","INVALID");
					jobSmallExist.attr("style","display: block;");
				}
			}
		});
	}else{
		jobDiv.attr("class", "job_container form-group has-error");
		jobSmall.attr("data-bv-result","INVALID");
		jobSmall.attr("style","display: block;");
	}

}
//校验所有职位
function validateJobName(){
	//遍历所有jobName，校验
	$("input[name='jobName[]']").each(function(index,e){
		jobNameKeyup(e);
	});
}

//保存时校验
function validateSave(){
	var hasClass = false;
	$('div.job_container').each(function(index,e){
		hasClass = $(e).hasClass("has-error");
	});
	return hasClass;
}