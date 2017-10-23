function initCustNeedsSelect2(){
	//加载城市下拉
	var cityName = $("#cityId").select2({
		ajax : {
			url : BASE_PATH + "/admin/city/getCustomerCitySelect",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
//				var cArrivalcity = $('#cArrivalcity').val();
//				if(cArrivalcity){
//					cArrivalcity = cArrivalcity.join(',');
//				}
				return {
//					exname : cArrivalcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.id; 
					obj.text = obj.city; 
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		templateSelection: formatRepoSelection,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false, //设置必须存在的选项 才能选中
	});
	
	$("#takeOffCityId").select2({
		ajax : {
			url : BASE_PATH + "/admin/city/getCustomerCitySelect",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
//				var cArrivalcity = $('#cArrivalcity').val();
//				if(cArrivalcity){
//					cArrivalcity = cArrivalcity.join(',');
//				}
				return {
					//exname : cArrivalcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.id; 
					obj.text = obj.city; 
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		templateSelection: formatRepoSelection,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	$("#landingCityId").select2({
		ajax : {
			url : BASE_PATH + "/admin/city/getCustomerCitySelect",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
//				var cArrivalcity = $('#cArrivalcity').val();
//				if(cArrivalcity){
//					cArrivalcity = cArrivalcity.join(',');
//				}
				return {
					//exname : cArrivalcity,
					cityname : params.term, // search term
					page : params.page
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;
				var selectdata = $.map(data, function (obj) {
					obj.id = obj.id; 
					obj.text = obj.city; 
					return obj;
				});
				return {
					results : selectdata
				};
			},
			cache : false
		},
		templateSelection: formatRepoSelection,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	
	//select2 选项渲染
	function formatRepoSelection(repo){
		var text =  repo.text;
		text = text.substr(0,3);
		return text;
	}

}