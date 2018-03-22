$(function(){
	
	
	
	$(".revoke").change(function(){
		var revoke = $("input[name=revoke]:checked").val();
		if(revoke == 1){
			$(".explain").show();
		}else {
			$(".explain").hide();
		}
	});
	$(".refuse").change(function(){
		var refuse = $("input[name=refuse]:checked").val();
		if(refuse == 1){
			$(".refuseExplain").show();
		}else {
			$(".refuseExplain").hide();
		}
	});
	$(".onceLegitimate").change(function(){
		var onceLegitimate = $("input[name=onceLegitimate]:checked").val();
		if(onceLegitimate == 1){
			$(".onceExplain").show();
		}else {
			$(".onceExplain").hide();
		}
	});
	$(".onceImmigration").change(function(){
		var onceImmigration = $("input[name=onceImmigration]:checked").val();
		if(onceImmigration == 1){
			$(".immigrationExplain").hide();
		}else {
			$(".immigrationExplain").show();
		}
	});

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