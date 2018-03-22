$(function(){
	
	
	
	

	

	//亲属信息
	$(".fatherUS").change(function(){
		var fatherUS = $("input[name=fatherUS]:checked").val();
		if(fatherUS == 1){
			$(".fatherUSYes").show();
		}else {
			$(".fatherUSYes").hide();
		}
	});
	$(".motherUS").change(function(){
		var motherUS = $("input[name=motherUS]:checked").val();
		if(motherUS == 1){
			$(".motherUSYes").show();
		}else {
			$(".motherUSYes").hide();
		}
	});
	$(".directRelatives").change(function(){
		var directRelatives = $("input[name=directRelatives]:checked").val();
		if(directRelatives == 1){
			$(".directRelativesYes").show();
			$(".directRelativesNo").hide();
		}else {
			$(".directRelativesYes").hide();
			$(".directRelativesNo").show();
		}
	});

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