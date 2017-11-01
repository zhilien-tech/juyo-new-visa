//jobName keyup事件
function jobNameKeyup(e){
	var jobDiv = $(e).parent().parent().parent();
	var jobSmall = $(e).parent().parent().next().children().first();
	if(($(e).val()).length>0){
		jobDiv.attr("class", "job_container form-group has-success");
		jobSmall.attr("data-bv-result","VALID");
		jobSmall.attr("style","display: none;");
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