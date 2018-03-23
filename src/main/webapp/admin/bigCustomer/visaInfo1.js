$(function(){
	//旅伴信息
	$(".companyInfo").change(function(){
		var companyVal = $(".companyInfo:checked").val();
		if(companyVal == 1){
			$(".teamture").show();
		}else {
			$(".teamture").hide();
		}
	});
	//旅伴信息--是否作为团队或组织的一部分旅游
	$(".team").change(function(){
		var teamVal = $("input[name=team]:checked").val(); 
		if(teamVal == 1){
			$(".teamnameture").show();
			$(".teamnamefalse").hide();
		}else {
			$(".teamnameture").hide();
			$(".teamnamefalse").show();
		}
	});
	//旅伴信息END

	//以前的美国旅游信息
	//(1)是否去过美国
	$(".goUS").change(function(){
		var goUS = $("input[name=goUS]:checked").val();
		if(goUS == 1){
			$(".goUSInfo").show();
		}else{
			$(".goUSInfo").hide();
		}

	});
	$(".license").change(function(){
		var license = $("input[name=license]:checked").val();
		if(license == 1){
			$(".driverInfo").show();
		}else{
			$(".driverInfo").hide();
		}
	});
	//(2)是否有美国签证
	$(".visaUS").change(function(){
		var visaUS = $("input[name=visaUS]:checked").val();
		if(visaUS == 1){
			$(".dateIssue").show();
		}else {
			$(".dateIssue").hide();
		}
	});
	$(".lose").change(function(){
		var lose = $("input[name=lose]:checked").val();
		if(lose == 1){
			$(".yearExplain").show();
		}else {
			$(".yearExplain").hide();
		}
	});
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