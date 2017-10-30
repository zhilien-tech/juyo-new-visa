//自定义校验
function validateScope(){
	var parentClass = "";
	var nextDate = "";
	var nextStyle = "";
	var scopeSelStr = $("#businessScopes").val();
	if(scopeSelStr != ""){
		parentClass = "form-group has-feedback has-success";
		nextDate = "VALID";
		nextStyle = "display: none;";
	}else{
		parentClass = "form-group has-feedback has-error";
		nextDate = "INVALID";
		nextStyle = "display: block;";
	}
	$(".btnVal input").parent().parent().attr("class",parentClass);
	$(".btnVal input").parent().next().attr("data-bv-result",nextDate);
	$(".btnVal input").parent().next().attr("style",nextStyle);
}