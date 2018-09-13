
$(document).on("input","#orderDate",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	if(temp == ""){
		$("#orderstartdate").val("");
		$("#orderenddate").val("");
	}
});
$(document).on("input","#sendSignDate",function(){
	if(event.shiftKey||event.altKey||event.ctrlKey||event.keyCode==16||event.keyCode==17||event.keyCode==18||(event.shiftKey&&event.keyCode==36)){
		return;
	}
	var temp = $(this).val();
	if(temp == ""){
		$("#sendstartdate").val("");
		$("#sendenddate").val("");
	}
});
