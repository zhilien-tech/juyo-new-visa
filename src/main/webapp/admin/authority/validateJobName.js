//jobName keyup事件
function jobNameKeyup(e){
	var jobName = $(e).val();
	var jobId = $(e).next().val();
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
		jobSmallExist.attr("data-bv-result","VALID");
		jobSmallExist.attr("style","display: none;");
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

//校验职位权限是否为空
function validateFuc(){
	var jobFunBoolean = "";
	$(".job_container").each(function(index,container){
		var jobName = $(container).find("input[id='jobName']").val();
		var treeObj = $.fn.zTree.getZTreeObj("tree_" + index);
		var nodes =  treeObj.getCheckedNodes(true);
		var funcIds = "" ;
		$(nodes).each(function(i,node){
			funcIds += node.id + ",";
		});
		if(funcIds==""){
			jobFunBoolean += jobName+",";//表示没有功能
		}
	});

	if(jobFunBoolean.length>1){
		jobFunBoolean = jobFunBoolean.substring(0, jobFunBoolean.length-1); //职位权限为空的职位
		jobFunBoolean = "职位："+jobFunBoolean +" 的职位权限不能为空";
	}else{
		jobFunBoolean = "";
	}
	return jobFunBoolean;
}