$(function(){
	
	
	
	

	

	
	

	//以前工作经历
	$(".beforeWork").change(function(){
		var beforeWork = $("input[name=beforeWork]:checked").val();
		if(beforeWork == 1){
			$(".beforeWorkInfo").show();
		}else {
			$(".beforeWorkInfo").hide();
		}
	});
});